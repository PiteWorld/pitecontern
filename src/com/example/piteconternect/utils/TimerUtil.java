package com.example.piteconternect.utils;

import java.util.concurrent.ScheduledExecutorService;

import com.example.piteconternect.exection.MyApplication;
import com.example.piteconternect.handler.SendHandler;
import com.example.piteconternect.kehua.tool.kehuaProl;
import com.example.piteconternect.pcs.BaseActivitys;

import android.content.Context;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;

public class TimerUtil {
	public static int time = 8000; // 定检时间
	public static int InspectionTime = 5000; // 巡检时间
	public static int hostID = 15; // 主机号
	public static int hostNum = 1; // 从机号
	public static int versionNum = 1;
	public static int KehuaTimer = 100000; // 科华定时
	public static int isSalve = 0; // 从机号判断
	public static kehuaProl kehuaProl = null;
	public static BaseActivitys base = null;
	public static String action = "com.example.piteconternect.pcs";
	public static Context context = null;
	public static MyApplication application = null;
	public static ScheduledExecutorService pool = null;
	public static SendHandler sendHandler = null;
	public static byte[] kehuaBt = null;
	public static EventLoopGroup group = null;
	public static ChannelFuture channelFuture = null;

}
