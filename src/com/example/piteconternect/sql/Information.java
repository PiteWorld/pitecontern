package com.example.piteconternect.sql;

public class Information {
	private String num;
	private String U;
	private String V;
	private String I;
	private String R1;
	private String T;
	private String C;
	private String R2;
	private String C2;
	private String time;

	public String getV() {
		return V;
	}

	public void setV(String v) {
		V = v;
	}

	public String getI() {
		return I;
	}

	public void setI(String i) {
		I = i;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getU() {
		return U;
	}

	public void setU(String u) {
		U = u;
	}

	public String getR1() {
		return R1;
	}

	public void setR1(String r1) {
		R1 = r1;
	}

	public String getT() {
		return T;
	}

	public void setT(String t) {
		T = t;
	}

	public String getC() {
		return C;
	}

	public void setC(String c) {
		C = c;
	}

	public String getR2() {
		return R2;
	}

	public void setR2(String r2) {
		R2 = r2;
	}

	public String getC2() {
		return C2;
	}

	public void setC2(String c2) {
		C2 = c2;
	}

	@Override
	public String toString() {
		return "Information [num=" + num + ", V=" + V+ ", I=" + I+", U=" + U + ", R1=" + R1 + ", T=" + T + ", C=" + C + ", R2=" + R2 + ", C2="
				+ C2 + ",time=" + time + "]";
	}

}
