package android_serialport_api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Locale;

import com.example.pite.server.SocketContent;
import com.example.pite.server.socketServer;
import com.example.pite.tool.DataManagerTool;
import com.example.pite.tool.PiteModel;
import com.example.pite.tool.PiteUpModel;
import com.example.pite.tool.PiteModel.ModelWork;
import com.example.pite.tool.PiteUpModel.ModelUpWork;
import com.example.pite.tool.ReadData;
import com.example.pite.tool.StopCMD;
import com.example.pite.tool.UpLoad;
import com.example.piteconternect.ConcentratorActivity;
import com.example.piteconternect.MainActivity;
import com.example.piteconternect.kehua.tool.keHuaCMDTool;
import com.example.piteconternect.read.ReadDataHost;
import com.example.piteconternect.read.ReadKeHuaData;
import com.example.piteconternect.utils.SetTime;
import com.example.piteconternect.utils.SocketUtils;
import com.example.piteconternect.utils.TimerUtil;

import AppCommunicate.AppServer;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import io.netty.buffer.Unpooled;

/**
 * 娑撴彃褰涢幙宥勭稊缁拷
 */
public class SerialPortService implements ModelWork, ModelUpWork {
	protected SerialPort mSerialPort;
	public static OutputStream mOutputStream;
	private InputStream mInputStream;
	private String path = "/dev/ttyS4"; // 鐠侯垰绶�
	private ReadThread thread;
	private static Context context;
	private int baudrate = 115200; // 濞夈垻澹掗悳锟�
	private int flags = 0; // 閺嶅洩鐦�
	private int databits = 8; // 閺佺増宓佹担锟�
	private int stopbits = 1; // 閸嬫粍顒涙担锟�
	private int parity = 'N'; // 閺嶏繝鐛欑猾璇茬��
	private static int initCmdCnt = 0; // AT閹稿洤鐣�.
	private static byte[] recvOK = { (byte) 79, (byte) 75 }; // AT閹稿洤鐣鹃弻銉﹀閸涙垝鎶�
	private static byte[] recvgps = { (byte) 43, (byte) 43, (byte) 43 };
	// private static MainActivity main = null;
	private PiteModel pmdata = new PiteModel(this); // 缁鳖垰濮炴稉锟介弶鈥崇暚閺佸瓨鏆熼幑锟� //閸ヨ棄顦�
	private PiteUpModel upModel = new PiteUpModel(this); // pite-bms.net
	private static final String[] datas = new String[] { "ate0\r\n", "at^DEBUG=0\r\n", "AT^SERVER=pite-bms.cn:6001\r\n",
			"AT^DELAY=-1\r\n", "AT^HEART=0 3031\r\n", "AT^UDPM=0\r\n", "AT^BAUD=115200\r\n", "AT^UTCF=810\r\n",
			"AT^PKMD=0\r\n", "AT^INDE=1\r\n", "AT^CAPN=internet -u  -p  -aPAP -d0.0.0.0\r\n", "AT^SAVE\r\n", "ate1\r\n",
			"AT^RESET\r\n" };
	private static final String[] gps = new String[] { "+++", "AT^RESET\r\n" };
	private static int gpsindex = 0;
	private int len = 33; // 閹鏆辨惔锟�
	private int hostID = 0; // 娑撶粯婧�閸欙拷
	private int paklen = 1; // 閸栧懘鏆辨惔锟�
	private int length = 1; // 闂�鍨
	private int num = 1; // 娴犲孩婧�閸欙拷
	private int numCnt = 1;
	private int isStopNum = 0; // 閺傤厾缍夐崚銈嗘焽
	public static boolean isStopNet = false; // 閺傤厾缍夐崚銈嗘焽
	private SocketContent socket = null;
	// private AppServer proNb;
	private int internetNum = 0; // 閺傤厾缍夐幒銉︽暪娑撳秴鍩岄弫鐗堝祦閸掋倖鏌�
	private boolean UpStatus = true;
	public static boolean isUpDecode = false;
	public static boolean upDataStatus = false;
	private int time38 = 0;
	private int time30 = 0;
	public static boolean initGpsStatus = false;
	private int bmsDecoBt = 0;
	private int bmscnt = 0;
	private int salve = 0;
	public static boolean isHostAndOther = false;
	public static boolean isUpData = false;
	private boolean bmsUpStatus = false; // BMS娑撳﹣绱堕悩鑸碉拷锟�
	private boolean kehuaUpStatus = false; // 缁夋垵宕曟稉濠佺炊閻樿埖锟斤拷
	private Handler handler = null;

	/**
	 * 娑撴彃褰涢崚婵嗩潗閸栵拷
	 * 
	 * @param context
	 */
	public SerialPortService(Context context, int hostID, int svlve) {
		this.context = context;
		this.numCnt = svlve;
		try {
			mSerialPort = new SerialPort(new File(path), baudrate, flags, databits, stopbits, parity);
			mOutputStream = mSerialPort.getOutputStream();
			mInputStream = mSerialPort.getInputStream();
			thread = new ReadThread();
			thread.start();
			SerialPortService.initGps(null);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 閸欐垿锟戒焦鏆熼幑锟�
	 * 
	 * @param bt
	 */
	public static synchronized void SendWirte(byte[] bt) {
		if (bt.length == 0 || mOutputStream == null) {
			return;
		}
		try {
			Thread.sleep(200);
			mOutputStream.write(bt);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	static int hadCmdok = 0;

	public static boolean initGps(byte[] bt) {
		try {
			if (bt == null) {
				mOutputStream.write(gps[hadCmdok].getBytes());
				return false;
			}
			if (hadCmdok < gps.length) {
				if (setCIMI(bt)) {
					hadCmdok++;
					if (hadCmdok == gps.length) {
						return true;
					}
					mOutputStream.write(gps[hadCmdok].getBytes());
				} else {
					hadCmdok = 0;
					mOutputStream.write(gps[1].getBytes());
				}
			}
			Thread.sleep(20);
		} catch (Exception e) {
		}
		return false;
	}

	public static boolean setCIMI(byte[] b) {
		if (b.length < 3 || b[2] != recvOK[0]) {
			return false;
		}
		for (int i = 0; i < b.length; i++) {
			if (b[2] == recvOK[0]) {
				if (b[3] == recvOK[1]) {
					return true;
				}
			} else {
				if (b[i] == recvgps[0]) {
					return true;
				}
			}
		}
		return false;

	}

	/**
	 * AT 娑撴彃褰涢幐鍥︽姢
	 * 
	 * @param bt
	 */
	@SuppressWarnings("unused")
	public static boolean initATModel(byte[] bt) {
		try {
			if (bt == null) {
				Log.e("2", "mOutputStream.write 0");
				mOutputStream.write(datas[initCmdCnt].getBytes());
				return false;
			}
			if (initCmdCnt < datas.length) {
				if (hadCMDOK(bt)) {
					initCmdCnt++;
					Log.e("2", "mOutputStream.write  " + initCmdCnt);
					if (initCmdCnt == datas.length) {
						Log.e("2", "artod");
						return true;
					}
					mOutputStream.write(datas[initCmdCnt].getBytes());
				} else {
					initCmdCnt = 0;
					// Log.e("2", "mOutputStream.write 0");
					mOutputStream.write(datas[initCmdCnt].getBytes());
				}
			}
			Thread.sleep(20);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 閺屻儲澹� OK
	 * 
	 * @param b
	 * @return
	 */
	private static boolean hadCMDOK(byte[] b) {
		boolean ret = false;
		for (int i = 0; i < b.length; i++) {
			if (b[i] == recvOK[0]) {
				if ((i + 1) == b.length) {
					break;
				}
				if (b[i + 1] == recvOK[1])
					ret = true;
			}
		}
		return ret;
	}

	/**
	 * 閹恒儲鏁圭痪璺ㄢ柤
	 */
	protected class ReadThread extends Thread {
		@Override
		public void run() {
			while (!isInterrupted()) {
				int size;
				try {
					byte[] buffer = new byte[mInputStream.available()];
					if (mInputStream == null)
						return;
					size = mInputStream.read(buffer);
					internetNum++;
					if (size > 0) {
						ConcentratorActivity.portRceCnt = 0;
						onDataReceived(buffer, size);
					}
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}

	/**
	 * byte16
	 * 
	 * @param b
	 * @return
	 */
	public static String bytesToHexString(byte[] b) {
		if (b == null || b.length < 1) {
			return "";
		}
		StringBuffer result = new StringBuffer();
		String hex;
		for (int i = 0; i < b.length; i++) {
			hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			result.append(hex.toUpperCase() + " ");
		}
		return result.toString();
	}

	/**
	 * byte16
	 * 
	 * @param b
	 * @return
	 */
	public static String bytesToHexStrings(byte[] b) {
		StringBuffer result = new StringBuffer();
		String hex;
		for (int i = 0; i < b.length; i++) {
			hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			result.append(hex.toUpperCase());
		}
		return result.toString();
	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * 閸忔娊妫存稉鎻掑經
	 */
	public void ClosePort() {
		if (thread != null) {
			thread.interrupt();
		}
		if (mSerialPort != null) {
			mSerialPort.close();
		}
	}

	@Override
	public void afterUpWork(byte[] bt) {
		if (bt.length < 5) {
			return;
		}
		if (ReadDataHost.ctx != null) {
			ReadDataHost.ctx.writeAndFlush(Unpooled.copiedBuffer(bt));
		}
	}

	@Override
	public void afterWork(byte[] bt) {
		Log.e("5", "服务器接收到的消息:"+bytesToHexString(bt));
		if (bt == null || bt.length < 2) {
			return;
		}
		switch (bt[4]) {
		case 0x35:
			if (TimerUtil.hostID != 0) {
				((ConcentratorActivity) context).setInternetTimer(60);
				DataManagerTool.CMDIndexof0x35(bt, 34, 1, 1, 1);
			}
			break;
		case 0x36:
			if (ReadDataHost.ctx != null) {
				ReadDataHost.ctx.writeAndFlush(Unpooled.copiedBuffer(bt));
			}
			break;
		case 0x37:
			break;
		case 0x31:

			break;
		case 0x38:
			break;
		case 0x30:
			if (bt[8] == 0x01) {
				bt[8] = 0x02;
			}
			if (bt[28] > 0) {
				isHostAndOther = true;
			}
			upDataStatus = true;
			isUpData = true;
			if (ReadDataHost.ctx != null) {
				ReadDataHost.ctx.writeAndFlush(Unpooled.copiedBuffer(bt));
			}
			break;
		case 0x39:
			int year = 0, mouth = 0, date = 0, hour = 0, mintues = 0, soceds = 0;
			if (bt[6] == 0x3B) {
				int check = 0x3B;
				for (int i = 7; i < bt.length - 2; i++) {
					check += bt[i] - 0x30;
				}
				check %= 100;
				byte[] checkBt = UpLoad.zeroBt(check + "", 2);
				if (checkBt[0] != bt[bt.length - 2] && checkBt[1] != bt[bt.length - 1]) {
					SocketUtils.context.sendHandler(StopCMD.setRestDataPacket());
					return;
				}
				for (int i = 7; i < 19; i++) {
					if (i < 9) {
						year *= 10;
						year += (bt[i] - 0x30);
					}
					if (i >= 9 && i < 11) {
						mouth *= 10;
						mouth += (bt[i] - 0x30);
					}
					if (i >= 11 && i < 13) {
						date *= 10;
						date += (bt[i] - 0x30);
					}
					if (i >= 13 && i < 15) {
						hour *= 10;
						hour += (bt[i] - 0x30);
					}
					if (i >= 15 && i < 17) {
						mintues *= 10;
						mintues += (bt[i] - 0x30);
					}
					if (i >= 17 && i < 19) {
						soceds *= 10;
						soceds += (bt[i] - 0x30);
					}
				}
				try {
					if (SetTime.setDateTime(year + 2000, mouth, date, hour, mintues, soceds)) {
						byte[] bttime = UpLoad.setRecDataTime();
						SocketUtils.context.sendHandler(bttime);
					}
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}

			} else if (bt[6] == 0x6F && bt[7] == 0x31) {
				int decoBt = DataManagerTool.DecodFlag(bt);
				try {
					if (decoBt == 11111111) {
						if (ReadKeHuaData.KeHuaFileSize()) {
							kehuaUpStatus = false;
							ConcentratorActivity.isKeHuaOk = false;
							ReadKeHuaData.DelKeHuaData(); // 缁夋垵宕曢崚鐘绘珟閺佺増宓�
							if (ReadData.FileSize()) {
								bmsUpStatus = true;
								byte[] upBt = UpLoad.setHostData(0);
								SocketUtils.context.sendHandler(upBt);
							}
						}
					} else {
						if (ReadData.FileSize()) {
							if (bmsDecoBt == decoBt) {
								bmscnt++;
								Thread.sleep(300);
								if (ReadData.FileSize()) {
									byte[] upBt = UpLoad.setHostData(0);
									SocketUtils.context.sendHandler(upBt);
								}
							}
							if (bmsDecoBt != decoBt || salve != TimerUtil.isSalve || bmscnt > 4) {
								salve = TimerUtil.isSalve;
								bmsDecoBt = decoBt;
								bmscnt = 0;
								ConcentratorActivity.isFileUp = false;
								ReadData.outfu(); // BMS閸掔娀娅庨弫鐗堝祦
								Thread.sleep(300);
								if (ReadData.FileSize()) {
									byte[] upBt = UpLoad.setHostData(0);
									// SerialPortService.SendWirte(upBt);
									SocketUtils.context.sendHandler(upBt);
								} else {
									bmsUpStatus = false;
									if (ReadKeHuaData.KeHuaFileSize() && !bmsUpStatus) {
										kehuaUpStatus = true;
										byte[] upBt = UpLoad.setHostData(0);
										// SerialPortService.SendWirte(upBt);
										SocketUtils.context.sendHandler(upBt);
									}
								}
							}
							// }
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else if (bt[6] == 0x5A || bt[6] == 0x5D || bt[6] == 0x60 || bt[6] == 0x5C || bt[6] == 0x26
					|| bt[6] == 0x25) { //// 鐎规碍顥呴弮鍫曟？
				if (ReadDataHost.ctx != null) {
					ReadDataHost.ctx.writeAndFlush(Unpooled.copiedBuffer(bt));
				}
			} else if (bt[6] == 0x6F && bt[7] == 0x30) {
				if (ReadData.FileSize() && !kehuaUpStatus) {
					bmsUpStatus = true;
					((ConcentratorActivity) context).setUpHandler(3); // BMS闁氨鐓￠張宥呭閸ｃ劍婀侀弫鐗堝祦閺囧瓨鏌�
				}
				if (ReadKeHuaData.KeHuaFileSize() && !bmsUpStatus) {
					bmsUpStatus = false ;
					kehuaUpStatus = true;
					((ConcentratorActivity) context).setUpHandler(7); // 缁夋垵宕曢柅姘辩叀閺堝秴濮熼崳銊︽箒閺佺増宓侀弴瀛樻煀
				}
			} else {
				if (ReadDataHost.ctx != null) {
					ReadDataHost.ctx.writeAndFlush(Unpooled.copiedBuffer(bt));
				}
			}
			break;
		}
	}

	/**
	 * 闁俺绻僢yte閺佹壆绮嶉崣鏍у煂short
	 * 
	 * @param b
	 * @param index
	 *            缁楊剙鍤戞担宥呯磻婵褰�
	 * @return
	 */
	public static short getShort(byte[] b, int index) {
		return (short) (((b[index + 1] << 8) | b[index + 0] & 0xff));
	}

	/**
	 * 鐏忓敄hort鏉烆剚鍨歜yte[2] 婢堆咁伂
	 * 
	 * @param a
	 * @return
	 */
	public static byte[] short2Byte(short a) {
		byte[] b = new byte[2];
		b[0] = (byte) (a >> 8);
		b[1] = (byte) (a);
		b[0] = (byte) (b[0] < 0 ? b[0] & 0xff : b[0]);
		b[1] = (byte) (b[1] < 0 ? b[1] & 0xff : b[1]);
		return b;
	}

	/**
	 * 閹恒儲鏁归弫鐗堝祦
	 */
	public void onDataReceived(final byte[] data, int size) {
		// initATModel(data);
		initGps(data);
		setInternet(data);
		pmdata.addBytes(data);
		upModel.addBytes(data);
	}

	/**
	 * 閺傤厾缍夐崚銈嗘焽
	 * 
	 * @param data
	 */
	public void setInternet(byte[] data) {
		if (ConcentratorActivity.isSendOK) {
			ConcentratorActivity.isSendOK = false;
			if (data[0] == 0x2A) {
				// DataManagerTool.isInternet(data, len, hostID, paklen, length,
				// num);
				isStopNum = 0;
			} else {
				isStopNum++;
			}
			if (isStopNum > 20) {
				isStopNet = true;
			} else {
				internetNum = 0;
				isStopNet = false;
			}
		}
	}
}
