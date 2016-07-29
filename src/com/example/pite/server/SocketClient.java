package com.example.pite.server;

import java.net.InetSocketAddress;

import com.example.piteconternect.netty.ConnectLister;
import com.example.piteconternect.netty.SimpleClientHandler;
import com.example.piteconternect.utils.TimerUtil;

import android.util.Log;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SocketClient {
	private String host;
	private int port;
	public static boolean isSocketStutas = false;

	public SocketClient(String host, int port) {
		this.host = host;
		this.port = port;
		setContent();
	}

	public void setContent() {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group);
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new SimpleClientHandler(SocketClient.this));
				}
			});
			bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
			bootstrap.option(ChannelOption.ALLOW_HALF_CLOSURE, true);
			// bootstrap.option(ChannelOption.TCP_NODELAY, true);
			bootstrap.option(ChannelOption.SO_TIMEOUT, 5000);
			// 发起异步链接操作
			ChannelFuture channelFuture = null;
			try {
				channelFuture = bootstrap.connect(new InetSocketAddress(host, port)).sync();
				TimerUtil.channelFuture = channelFuture;
				TimerUtil.group = group;
			} catch (InterruptedException e) {
				isSocketStutas = false;
				e.printStackTrace();
			}
			if (channelFuture.isSuccess()) {
				// isSocketStutas = true;
				Log.e("7", "连接成功");
			}
			// channelFuture.addListener(new ConnectLister(SocketClient.this));
			// // 断线监听
		} finally {
			// group.shutdownGracefully();
		}

	}

}
