package com.mobine.vnews;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
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
        // 启动则在线
        if (msg.length() > LOGIN_LENGTH) {
            String type = msg.substring(0, LOGIN_LENGTH);
            String user_id = msg.substring(LOGIN_LENGTH);
            if (type.equals(TAG_LOGIN)) {
                logger.info("[user:" + user_id + "  addr:" + channel.remoteAddress() + " online]");
                URmap.put(user_id, address);
                /*String offline = DAO.getMessages(user_id);
                int result = DAO.deleteMessages(user_id);
                if(offline == null || offline.length() == 0){
                }else{
                    RCmap.get(address).writeAndFlush(offline);
                }*/
            } else {
//                // 普通消息
//                JSONObject jsonObject = JSONObject.fromObject(msg);
//                Message received = (Message)JSONObject.toBean(jsonObject ,Message.class);
//                //在线 通过用户->addr判断在线与否
//                if(URmap.keySet().contains(received.getTo_id())){
//                    RCmap.get(URmap.get(received.getTo_id())).writeAndFlush(msg);
//                }
//                DAO.addMessage(received);
//                System.out.println("[user:" + received.getFrom_id() + " said: to " + received.getTo_id() + ": " + received.getContent() + "]");
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
        for (String user_id : URmap.keySet()){
            if (URmap.get(user_id).equals(address)){
                logger.info("[user:" + user_id + "  addr:" + channel.remoteAddress() + " offline]");
                URmap.remove(user_id);
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
        logger.info( "[" + ctx.channel().remoteAddress() + "]" + "exit the room");
        ctx.close().sync();
    }

}
