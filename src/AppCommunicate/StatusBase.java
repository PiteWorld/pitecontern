package AppCommunicate;

import java.io.OutputStream;

public abstract class StatusBase {

	public static enum ComStatue {
		Stop, // ֹͣ
		CHECK, // Ѳ��
		FIND, // ��ѯ
		READ, // ��ȡ
		DELET // ɾ��
	};

	protected int id = 0;
	private StatusBase curStatus = null;
	private ComStatue curComStatus = null;

	public void setID(int id) {
		this.id = id;
	}

	public abstract int getID();

	public abstract ComStatue getCurStatus();

	protected abstract boolean isStatusOK(byte[] bt);

	public abstract void setStatus(Object param);

	public abstract void StatusRun(OutputStream so) throws Exception;

	protected abstract StatusBase getOKStatus();

	protected abstract StatusBase getFailStatus();
}
