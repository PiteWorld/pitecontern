package com.example.piteconternect.utils;

import java.io.Serializable;

public class SettingData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Setting_Total_battery;
	private String Setting_group;
	private String Setting_et, Setting_et2, Setting_et3, Setting_et4, Setting_et5, Setting_et6, Setting_et7, Setting_et8,
			Setting_et9, Setting_et10, Setting_et11, Setting_et12, Setting_et13, Setting_et14;

	// 单例模式,提高性能
	private static SettingData instance = null;

	public static SettingData getInstart() {
		if (instance == null) {
			synchronized (SettingData.class) {
				if (instance == null)
					instance = new SettingData();
			}
		}
		return instance;
	}

	public String getSetting_Total_battery() {
		return Setting_Total_battery;
	}

	public void setSetting_Total_battery(String setting_Total_battery) {
		Setting_Total_battery = setting_Total_battery;
	}

	public String getSetting_group() {
		return Setting_group;
	}

	public void setSetting_group(String setting_group) {
		Setting_group = setting_group;
	}

	public String getSetting_et() {
		return Setting_et;
	}

	public void setSetting_et(String setting_et) {
		Setting_et = setting_et;
	}

	public String getSetting_et2() {
		return Setting_et2;
	}

	public void setSetting_et2(String setting_et2) {
		Setting_et2 = setting_et2;
	}

	public String getSetting_et3() {
		return Setting_et3;
	}

	public void setSetting_et3(String setting_et3) {
		Setting_et3 = setting_et3;
	}

	public String getSetting_et4() {
		return Setting_et4;
	}

	public void setSetting_et4(String setting_et4) {
		Setting_et4 = setting_et4;
	}

	public String getSetting_et5() {
		return Setting_et5;
	}

	public void setSetting_et5(String setting_et5) {
		Setting_et5 = setting_et5;
	}

	public String getSetting_et6() {
		return Setting_et6;
	}

	public void setSetting_et6(String setting_et6) {
		Setting_et6 = setting_et6;
	}

	public String getSetting_et7() {
		return Setting_et7;
	}

	public void setSetting_et7(String setting_et7) {
		Setting_et7 = setting_et7;
	}

	public String getSetting_et8() {
		return Setting_et8;
	}

	public void setSetting_et8(String setting_et8) {
		Setting_et8 = setting_et8;
	}

	public String getSetting_et9() {
		return Setting_et9;
	}

	public void setSetting_et9(String setting_et9) {
		Setting_et9 = setting_et9;
	}

	public String getSetting_et10() {
		return Setting_et10;
	}

	public void setSetting_et10(String setting_et10) {
		Setting_et10 = setting_et10;
	}

	public String getSetting_et11() {
		return Setting_et11;
	}

	public void setSetting_et11(String setting_et11) {
		Setting_et11 = setting_et11;
	}

	public String getSetting_et12() {
		return Setting_et12;
	}

	public void setSetting_et12(String setting_et12) {
		Setting_et12 = setting_et12;
	}

	public String getSetting_et13() {
		return Setting_et13;
	}

	public void setSetting_et13(String setting_et13) {
		Setting_et13 = setting_et13;
	}

	public String getSetting_et14() {
		return Setting_et14;
	}

	public void setSetting_et14(String setting_et14) {
		Setting_et14 = setting_et14;
	}
	
}
