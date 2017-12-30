package com.mobile.vnews.server;

import com.alibaba.fastjson.JSONObject;
import com.mobile.vnews.dao.Dao;
import com.mobile.vnews.module.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Create by xuantang
 * @date on 12/8/17
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * log
     */
    private final Logger logger = LogManager.getLogger(ChatServerHandler.class);

    /**
     * the group of channel
     */
    private static final ChannelGroup group = new DefaultChannelGroup(
            GlobalEventExecutor.INSTANCE);

    /**
     * map of the user_id to remote address
     */
    private static Map<String, String> URmap = new HashMap<String, String>();
    //
    /**
     * map of the remote address to channel
     */
    private static Map<String, Channel> RCmap = new HashMap<String, Channel>();

    private static final String TAG_LOGIN = "login";
    private static final int LOGIN_LENGTH = 5;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        String address = channel.remoteAddress().toString();

        if (msg.length() > LOGIN_LENGTH) {
            String type = msg.substring(0, LOGIN_LENGTH);
            String userId = msg.substring(LOGIN_LENGTH);
            // login info
            if (type.equals(TAG_LOGIN)) {
                logger.info("[user:" + userId + "  addr:" + channel.remoteAddress() + " online]");
                System.out.println("[user:" + userId + "  addr:" + channel.remoteAddress() + " online]");
                URmap.put(userId, address);
            }
            // notice
            else {
                JSONObject jsonObject = JSONObject.parseObject(msg);
                Message message = jsonObject.toJavaObject(Message.class);
                List<String> users = Dao.getRelationUsersOnNews(Integer.parseInt(message.getNewsID()));
                // to user
                if (URmap.keySet().contains(message.getToID())) {
                    RCmap.get(URmap.get(message.getToID())).writeAndFlush(msg);
                }
                // message all relational user
                for (String userID : users) {
                    if (URmap.keySet().contains(userID) && !userID.equals(message.getToID())) {
                        RCmap.get(URmap.get(userID)).writeAndFlush(msg);
                    }
                }
                // add message to db
                Dao.addNotice(message);
                Dao.addComment(message);
                logger.info("[user:" + message.getFromID() + " commit a message: " + message.getContent() + "]");
                System.out.println("[user:" + message.getFromID() + " commit a message: " + message.getContent() + "]");
            }
        }

    }
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception{
        // online
        Channel channel = ctx.channel();
        RCmap.put(channel.remoteAddress().toString(), channel);
        group.add(channel);
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String address = channel.remoteAddress().toString();
        for (String userId : URmap.keySet()){
            if (URmap.get(userId).equals(address)){
                logger.info("[user:" + userId + "  addr:" + channel.remoteAddress() + " offline]");
                System.out.println("[user:" + userId + "  addr:" + channel.remoteAddress() + " offline]");
                URmap.remove(userId);
                break;
            }
        }
        RCmap.remove(address);
        group.remove(channel);
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        logger.info("[" + channel.remoteAddress() + "] " + "online");
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        logger.info("[" + channel.remoteAddress() + "] " + "offline");
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error( "[" + ctx.channel().remoteAddress() + "]" + "exit the room");
        System.out.println("[" + ctx.channel().remoteAddress() + "]" + "exit the room");
        ctx.close().sync();
    }

}
