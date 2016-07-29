package com.example.piteconternect.netty;

import java.util.concurrent.TimeUnit;

import com.example.pite.server.SocketClient;

import android.util.Log;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;

/**
 * 断线重连监听
 */
public class ConnectLister implements ChannelFutureListener {
	private SocketClient client = null;
	public ConnectLister(SocketClient client) {
		this.client = client;
	}

	@Override
	public void operationComplete(ChannelFuture channelFuture) throws Exception {
		if (!channelFuture.isSuccess()) {
			final EventLoop loop = channelFuture.channel().eventLoop();
			loop.schedule(new Runnable() {
				public void run() {
					client.setContent();
					Log.e("5", "ConnectLister断线重连");
				}
			}, 2, TimeUnit.SECONDS);
		}
	}

}
