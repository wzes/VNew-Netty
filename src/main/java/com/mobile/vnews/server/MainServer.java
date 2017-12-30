package com.mobile.vnews.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Create by xuantang
 * @date on 12/8/17
 */
public class MainServer {
    /**
     * log
     */
    private final Logger logger = LogManager.getLogger(MainServer.class);
    private int port;

    public MainServer(int port){
        this.port = port;
    }

    /**
     * Run the netty
     */
    public void run(){
        EventLoopGroup acceptor = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        bootstrap.group(acceptor, worker);
        bootstrap.channel(NioServerSocketChannel.class);
        // add ServerInitHandler
        bootstrap.childHandler(new ServerInitHandler());
        try {
            Channel channel = bootstrap.bind(port).sync().channel();
            logger.info("Server start running in port:" + port);
            System.out.println("Server start running in port:" + port);
            // close
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // exit
            acceptor.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new MainServer(10001).run();
    }
}
