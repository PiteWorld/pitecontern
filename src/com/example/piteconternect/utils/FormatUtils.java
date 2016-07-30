package com.example.piteconternect.utils;

import java.text.DecimalFormat;

/**
 * 格式化单位
 */
public class FormatUtils {
	private String dzq = "sb";
	public static String setFormatGroupU(double value, int name) {
		DecimalFormat df = null;
		if (name == 4) {
			if (value >= 100.0) {
				df = new DecimalFormat("0.0");
			} else if (value >= 10.0) {
				df = new DecimalFormat("0.00");
			} else {
				df = new DecimalFormat("0.000");
			}
		}
		if (name == 3) {
			if (value >= 100.0) {
				df = new DecimalFormat("0");
			} else if (value >= 10.0) {
				df = new DecimalFormat("0.0");
			} else {
				df = new DecimalFormat("0.00");
			}
		}
		return df.format(value);
	}
}
