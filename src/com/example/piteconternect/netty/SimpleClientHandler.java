package com.example.piteconternect.netty;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.example.pite.server.SocketClient;
import com.example.pite.tool.IDModel;
import com.example.pite.tool.PiteModel;
import com.example.pite.tool.PiteModleID;
import com.example.pite.tool.ReadData;
import com.example.pite.tool.StopCMD;
import com.example.pite.tool.IDModel.ModelWorkID;
import com.example.pite.tool.PiteModel.ModelWork;
import com.example.pite.tool.PiteModleID.ModelWorkDT;
import com.example.piteconternect.ConcentratorActivity;
import com.example.piteconternect.exection.MyApplication;
import com.example.piteconternect.read.ReadDataHost;
import com.example.piteconternect.utils.SocketUtils;
import com.example.piteconternect.utils.TimerUtil;
import com.example.piteconternect.utils.nettyUtils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android_serialport_api.SerialPortService;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.handler.timeout.WriteTimeoutHandler;

/**
 * 
 * 发送消息
 */
public class SimpleClientHandler extends ChannelInboundHandlerAdapter {
	private ReadDataHost data = null;
	public static SocketClient client = null;

	public SimpleClientHandler(SocketClient client) {
		this.client = client;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// Log.e("3", "连接上主机发送数据 " + SerialPortService.bytesToHexString(bt));
		byte[] bt = StopCMD.setHostOneData(); // 发送0x35
		ByteBuf bts = ctx.alloc().buffer().writeBytes(bt);
		for (int i = 0; i < 2; i++) {
			ctx.writeAndFlush(bts);
			Log.e("7", "连接35命令发送");
		}
		data = new ReadDataHost();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		try {
			SocketClient.isSocketStutas = true;
			ByteBuf buf = (ByteBuf) msg;
			byte[] bt = new byte[buf.readableBytes()];
			buf.readBytes(bt);
			ReadDataHost.resartCnt = 0;
			// String message = new String(req);
			String str = SerialPortService.bytesToHexString(bt);
			Log.e("3", "接收到消息：" + str);
			data.addbytes(bt, ctx);
		} catch (Exception e) {
			Log.e("4", "duquyichang:  " + e);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		// Log.e(" 6", "异常关闭：" + cause);
		// ctx.close();
		// String Path = Environment.getExternalStorageDirectory() + "/" +
		// "3_pite" + "/";
		// File file = new File(Path);
		// if (!file.exists()) {
		// file.mkdirs();
		// }
		// BufferedWriter write = new BufferedWriter(new OutputStreamWriter(new
		// FileOutputStream(Path + "1.txt", true)));
		// try {
		// write.write(cause.toString());
		// // 换行
		// write.newLine();
		// // 需要删除数据的时候
		// write.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// final EventLoop loop = ctx.channel().eventLoop();
		// loop.schedule(new Runnable() {
		// public void run() {
		// client.setContent();
		// }
		// }, 2, TimeUnit.SECONDS);
		Log.e("5", "channelActive断线重连");

		// // 重启程序
		// Intent intent = new
		// Intent(TimerUtil.application.getApplicationContext(),
		// ConcentratorActivity.class);
		// PendingIntent restartIntent =
		// PendingIntent.getActivity(TimerUtil.application.getApplicationContext(),
		// 0,
		// intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		// // 退出程序
		// AlarmManager mgr = (AlarmManager)
		// TimerUtil.application.getSystemService(Context.ALARM_SERVICE);
		// mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 10000,
		// restartIntent); // 1秒钟后重启应用
		// TimerUtil.application.finishActivity();
		SocketUtils.context.setResrtConnect();
	}

}
