package com.ypei.loanpicker.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static String getDateTimeStrSdsm(Date d) {
		if (d == null)
			return "";

		if (d.getTime() == 0L)
			return "Never";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSSZ");
		
		String str = sdf.format(d);
		return str;
	}
	
	public static String getDateTimeStrConcise(Date d) {
		if (d == null)
			return "";

		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSZ");
		
		String str = sdf.format(d);
		return str;
	}
	
	public static String getNowDateTimeStrConcise() {

		return getDateTimeStrConcise(new Date());
	}
}
