package com.daxie.tool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Methods to handle date.
 * @author Daba
 *
 */
public class DateFunctions {
	public static String GetDateString() {
		Date date=new Date();
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String ret=sdf.format(date);
		
		return ret;
	}
	public static String GetDateStringToMilliseconds() {
		Date date=new Date();
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String ret=sdf.format(date);
		
		return ret;
	}
	public static String GetDateStringWithoutDelimiters() {
		Date date=new Date();
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String ret=sdf.format(date);
		
		return ret;
	}
	public static String GetTimeString() {
		Date date=new Date();
		
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String ret=sdf.format(date);
		
		return ret;
	}
}
