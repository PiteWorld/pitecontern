package com.example.piteconternect.Inspection;

import java.io.OutputStream;

public abstract class InspectionStatusBase {
	
	public static enum ComStatue{
		Stop,			//Í£Ö¹
		Inspection,			//¶¨¼ì
		FIND,			//²éÑ¯
		READ,			//¶ÁÈ¡
		DELET			//É¾³ý
	};
	protected int id=0;
	private InspectionStatusBase curStatus=null;
	private ComStatue curComStatus=null;
	public void setID(int id){
		this.id=id;
	}
	public abstract int getID();
	public abstract ComStatue getCurStatus();
	protected abstract boolean isStatusOK(byte[] bt);
	public abstract void setStatus(Object param);
	public abstract void StatusRun (OutputStream so)throws Exception;
	protected abstract InspectionStatusBase getOKStatus();
	protected abstract InspectionStatusBase getFailStatus();
}
