package AppCommunicate;

import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.example.pite.model.PrcloModel;
import com.example.pite.tool.StopCMD;
import com.example.piteconternect.ConcentratorActivity;
import com.example.piteconternect.utils.SocketUtils;
import com.example.piteconternect.utils.TimerUtil;

import android.util.Log;
import android_serialport_api.SerialPortService;

public class StatusReading extends StatusBase {
	private byte[] decode = null;
	private int sizelength = 0;
	private List<byte[]> addDT = new ArrayList<byte[]>();
	private byte[] headbt = null;
	private int pakLength;// 数据包总长度
	@Override
	protected boolean isStatusOK(byte[] bt) {
		byte[] ProBt = new byte[bt.length]; // 解析数据
		decode = new byte[8];// 解码标志
		System.arraycopy(bt, 40, ProBt, 0, bt.length - 40);
		System.arraycopy(bt, 30, decode, 0, 8);//29
//		Log.e("3", id + "读取数据进来了：" + SerialPortService.bytesToHexString(bt));
		PrcloModel prclo = new PrcloModel();
		if (prclo.addByte(ProBt) != null) {
//			SocketUtils.context.setHandler(1, prclo, bt, ProBt);
			Log.e("4", id + "  读取成功，解析完成3" + SerialPortService.bytesToHexString(bt));
		}
		return true;
	}

	@Override
	public void setStatus(Object param) {

	}

	@Override
	protected StatusBase getOKStatus() {
		StatusBase sta = new StatusDelet();
		sta.setStatus(SocketUtils.setDecodFlag(decode));
		return sta;
	}

	@Override
	protected StatusBase getFailStatus() {
		return new StatusCheck();
	}

	@Override
	public void StatusRun(OutputStream so) throws Exception {
		byte[] bt = StopCMD.LookCMD(4, id);
		so.write(bt);
		// Log.e("4", id + " 读取命令"+SerialPortService.bytesToHexString(bt));
	}

	@Override
	public ComStatue getCurStatus() {
		return ComStatue.READ;
	}

	@Override
	public int getID() {
		return id;
	}

}
