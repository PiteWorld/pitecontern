package AppCommunicate;

import java.io.OutputStream;

import com.example.pite.tool.StopCMD;
import com.example.piteconternect.utils.TimerUtil;

import android.text.format.Time;
import android.util.Log;

public class StatusStop extends StatusBase {

	@Override
	public void setStatus(Object param) {
	}

	@Override
	protected boolean isStatusOK(byte[] bt) {
		return true;
	}

	@Override
	protected StatusBase getOKStatus() {
		return new StatusCheck();
	}

	@Override
	protected StatusBase getFailStatus() {
		return new StatusCheck();
	}

	@Override
	public void StatusRun(OutputStream so) throws Exception {
		for (int i = 1; i < TimerUtil.hostNum + 1; i++) {
			byte[] bt = StopCMD.stopShort(10, i);
			so.write(bt);
		}
		Log.e("3", "Í£Ö¹ÃüÁî");
	}

	@Override
	public ComStatue getCurStatus() {
		return ComStatue.Stop;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
}
