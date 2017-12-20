package com.mobile.vnews.client;

import com.alibaba.fastjson.JSONObject;
import com.mobile.vnews.module.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientMain1555555 {
	private String host;
	private int port;
	private boolean stop = false;

	public static final String HOST = "127.0.0.1";

	//public static final String HOST = "59.110.136.134";

	public ClientMain1555555(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public static void main(String[] args) throws IOException {
		new ClientMain1555555(HOST, 9999).run();
	}

	public void run() throws IOException {
		EventLoopGroup worker = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(worker);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.handler(new ClientInit());

		try {
			Channel channel = bootstrap.connect(host, port).sync().channel();
			channel.writeAndFlush("login1555555");
			while (true) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(System.in));
				String input = reader.readLine();
				if (input != null) {
					if ("quit".equals(input)) {
						System.exit(1);
					}
					Message message = new Message();
					message.setFromID("1555555");
					message.setToID("1552730");
					message.setFromImage("image");
					message.setFromUsername("Hadoop");
					message.setTitle("World War Start!");
					message.setNewsID(2);
					message.setContent(input);
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(message);
					channel.writeAndFlush(jsonObject.toString());
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

}
