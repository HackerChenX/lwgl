package com.hlzt.commons.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

	
	/**
	 * 获得当前时间秒 long
	 * @return
	 */
	public static Long getNowTimeLongS(){
		
		return new Date().getTime()/1000;
	}
	/**
	 * 得到当前时间格式化后时分秒的字符串
	 * 
	 * @return
	 */
	public static String getNowTimeToHMS() {
		Date date = new Date();
		SimpleDateFormat myFmt = new SimpleDateFormat("HH:mm:ss");
		return myFmt.format(date);
	}
	
	
	/**
	 * 时间转成ymd 字符串
	 * @param date
	 * @return
	 */
	public static String dateToStrYMD(Date date) {
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = myFmt.format(date);
		return dateStr;

	}
	
	/**
	 * 邯郸流水号的日期格式
	 * @param date
	 * @return
	 */
	public static String dateToTransIdYMD(Date date) {
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyyMMdd");
		String dateStr = myFmt.format(date);
		return dateStr;

	}


	/**
	 * 时间字符串转化成ymd date
	 * 
	 * @param str
	 * @return
	 */
	public static Date strToDateYMD(String str) {
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = myFmt.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;

	}

	/**
	 * YYYY-MM-dd HH:mm:ss
	 * @param time
	 * @return
	 */
	public static String  longToDate(Long time)
	{
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = myFmt.format(time);
		return dateStr;
	}
	
	/**
	 * YYYY-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String dateToDateTimeStr(Date date)
	{
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = myFmt.format(date);
		return dateStr;
	}
	/**
	 * 获得当前年份
	 * @return
	 */
	public static int getNowYear(){
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy");
		int year=Integer.valueOf(myFmt.format(new Date()));
		return year;
	}
	/**
	 * 当前时间转化成ymd 字符串
	 * 
	 * @return
	 */
	public static String nowDateToStr() {
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		return myFmt.format(new Date());

	}
	
	public static String dateToStrYMDHM(Date date){
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return myFmt.format(date);
	}
}
