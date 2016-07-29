package com.example.piteconternect.utils;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.ImmediateEventExecutor;

public class nettyUtils {
	public static Map<Integer, ChannelHandlerContext> map = new HashMap<Integer, ChannelHandlerContext>();

	public static Map<Integer, ChannelHandlerContext> nettyMap() {
		return map;
	}

	public static ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);

	public static ChannelGroup getChannelGroup() {
		return channelGroup;
	}
}
