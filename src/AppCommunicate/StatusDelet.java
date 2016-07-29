package AppCommunicate;

import java.io.OutputStream;
import java.net.Socket;

import com.example.pite.tool.StopCMD;

import android.system.Os;
import android.util.Log;

public class StatusDelet extends StatusBase {
	private byte[] mark = null;

	@Override
	protected boolean isStatusOK(byte[] bt) {
		if(bt[16]==0){
			Log.e("3",id+  "  É¾³ý³É¹¦");
			return true;
		}
		return false;
	}

	@Override
	public void setStatus(Object param) {
		mark = (byte[]) param;
	}

	@Override
	protected StatusBase getOKStatus() {
		return new StatusReading();
	}

	@Override
	protected StatusBase getFailStatus() {
		return new StatusCheck();
	}

	@Override
	public void StatusRun(OutputStream so) throws Exception {
		byte[] reBt = StopCMD.RecDataSuccess(8, id, mark);
		so.write(reBt);
		Log.e("3", "É¾³ýÃüÁî");
	}

	@Override
	public ComStatue getCurStatus() {
		return ComStatue.DELET;
	}

	@Override
	public int getID() {
		return id;
	}

}
