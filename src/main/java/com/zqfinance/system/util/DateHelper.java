package com.zqfinance.system.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class DateHelper {
	public static final String CALENDER_DATE_FORMAT = "yyyy-MM-dd";
	public static final String CALENDER_MONTH_FORMAT = "yyyy-MM";
	public static final String CALENDER_YEAR_FORMAT = "yyyy";
	public static final String CALENDER_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String CALENDER_TIME14_FORMAT = "yyyyMMddHHmmss";

	public static SimpleDateFormat sdd = new SimpleDateFormat("dd");// add by shijx 时期格式
	public static SimpleDateFormat sdfd2 = new SimpleDateFormat("yyyyMMdd");// add by shijx 时期格式
	
	public static SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 时期格式
	public static SimpleDateFormat sdfs2 = new SimpleDateFormat("yyyyMMddHHmmss");// 时期格式
	public static SimpleDateFormat sdfs3= new SimpleDateFormat("yyyy-MM-dd HH:mm");// 时期格式
	
	
	public static SimpleDateFormat sdfs_ch = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");// 时期格式
	
	public static SimpleDateFormat sdfd_ch = new SimpleDateFormat("yyyy年MM月dd日");// 时期格式
	
	public static SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");// 时期格式,时期格式
	public static SimpleDateFormat sdfd6 = new SimpleDateFormat("yyyy-MM");// 时期格式,时期格式
	public static SimpleDateFormat sdfd3 = new SimpleDateFormat("yy-MM-dd");// 时期格式,时期格式
	
	public static SimpleDateFormat sdfms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");// 时期格式
	
	public static SimpleDateFormat sdfmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");// 时期格式
	
	public static SimpleDateFormat sdfdFold = new SimpleDateFormat("yyyy|MM|dd");// 时期格式,时期格式
	
	public static String  sdfdFoldPattern = new String("yyyy|MM|dd");// 时期格式,时期格式
	
	/**
	 * 修改日期格式 query list 开通日期  20100415 改为 10-04-15
	 * @param date
	 * @return
	 */
	public  static String ChangDateFormat7(String dateString)
	{
		String back=dateString;
		try
		{

			Date date=  sdfd2.parse(dateString);
			back=sdfd3.format(date);
            
		}
		catch(Exception ex)
		{
			//ex.printStackTrace();
			back=dateString;
		}
        return back;
	
	}
	
	
	/**
	 * 修改日期格式 华安 TA 服务中  20100415100141 改为 2010-04-15 10:01
	 * @param date
	 * @return
	 */
	public  static String ChangDateFormat6(String dateString)
	{
		String back=dateString;
		try
		{

			Date date=  sdfs2.parse(dateString);
			back=sdfs3.format(date);
            
		}
		catch(Exception ex)
		{
			//ex.printStackTrace();
			back=dateString;
		}
        return back;
	
	}
	
	/**
	 * 修改日期格式 query list 开通日期  20100415 改为 2010-04-15
	 * @param date
	 * @return
	 */
	public  static String ChangDateFormat5(String dateString)
	{
		String back=dateString;
		try
		{

			Date date=  sdfd2.parse(dateString);
			back=sdfd.format(date);
            
		}
		catch(Exception ex)
		{
			//ex.printStackTrace();
			back=dateString;
		}
        return back;
	
	}
	
	
	/**
	 * 修改日期格式 query list 开通日期  2010-04-15 改为 10-04-15
	 * @param date
	 * @return
	 */
	public  static String ChangDateFormat4(String dateString)
	{
		String back=dateString;
		try
		{

			Date date=  sdfd.parse(dateString);
			back=sdfd3.format(date);
            
		}
		catch(Exception ex)
		{
			//ex.printStackTrace();
			back=dateString;
		}
        return back;
	
	}
	
	
	/**
	 * 修改日期格式 华安 TA 服务中  20100415 改为 2010年04月15日
	 * @param date
	 * @return
	 */
	public  static String ChangDateFormat3(String dateString)
	{
		String back=dateString;
		try
		{

			Date date=new Date();
			
			if(dateString.indexOf("-")==-1)
			{
				date=sdfd2.parse(dateString);
			}
			else
			{
				date=sdfd.parse(dateString);
			}
			
			back=sdfd_ch.format(date);
            
		}
		catch(Exception ex)
		{
			//ex.printStackTrace();
			back=dateString;
		}
        return back;
	
	}
	
	
	/**
	 * 修改日期格式 华安 TA 服务中  20100415100141 改为 2010-04-15 10:01:41
	 * @param date
	 * @return
	 */
	public  static String ChangDateFormat2(String dateString)
	{
		String back=dateString;
		try
		{

			Date date=  sdfs2.parse(dateString);
			back=sdfs.format(date);
            
		}
		catch(Exception ex)
		{
			//ex.printStackTrace();
			back=dateString;
		}
        return back;
	
	}
	
	/**
	 * 修改日期格式 华安 TA 服务中  yyyy-MM-dd 改为 yyyyMMdd
	 * @param date
	 * @return
	 */
	public  static String ChangDateFormat(String dateString)
	{
		Date date=stringToDate(dateString);
		if(null != date)
		{
			return sdfd2.format(date);
		}else{
			return "";
		} 
	}
	
	/**
	 * 10位日期转为date类型
	 * @param date
	 * @return
	 */
	public static Date stringToDate(String date)
    {
        if(null != date &&!"".equals(date))
        {
            
            try {
                return  sdfd.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            } 
        }
        return null;
    }
	
	/**
	 * 8位日期转为date类型
	 * @param date
	 * @return
	 */
	public static Date stringIn8ToDate(String date)
    {
        if(null != date &&!"".equals(date))
        {
            
            try {
                return  sdfd2.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            } 
        }
        return null;
    }
	
	/**
	 * 根据传入的pattern格式化日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String dateToStrByPattern(Date date,String pattern)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/**
	 * 获取跟当日间隔n天的日期
	 * @param field {@link Calendar}中 DATE MONTH YEAR...
	 * @param amount 偏离量,正数为当日之后，负数为当日之前
	 * @return
	 */
	public static Date getDateByInterval(int field,int amount)
	{
		return getDateByInterval(new Date(),field,amount);
	}
	
	/**
	 * 获取跟当日间隔n天的日期
	 * @param field {@link Calendar}中 DATE MONTH YEAR...
	 * @param amount 偏离量,正数为当日之后，负数为当日之前
	 * @return
	 */
	public static Date getDateByInterval(Date date,int field,int amount)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		return cal.getTime();
	}
	
	/**
	 * 日期类型格式化为10位字符串
	 * @param date
	 * @return
	 */
	public static String getDate(java.util.Date date)
	{
		if(null != date)
		{
			return sdfd.format(date);
		}else{
			return "";
		} 
	}
	
	/**
	 * 精确值秒的字符转为日期，格式:yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static Date stringToTime(String date)
	{
		if(null != date &&!"".equals(date))
		{
			
			try {
				return  sdfs.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			} 
		}
		return null;
	}
	
	/**
	 * 精确值秒的字符转为日期，格式:yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String timeToString(Date date)
	{
		if(null != date &&!"".equals(date))
		{
			
			try {
				return  sdfs.format(date);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return null;
	}
	
	/**
	 * 精确值秒的字符转为日期，格式:yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return List<-String->该天 的开始时间和结束时间  get(0):2014-12-20 00:00:00  get(1):2014-12-20 23:59:59
	 */
	public static List<String> timeToStringTimeQuantum(Date date)
	{
		if(null != date &&!"".equals(date))
		{
			
			try {
				List<String> times = new ArrayList<String>();
				times.add(sdfd.format(date)+" 00:00:00");
				times.add(sdfd.format(date)+" 23:59:59");
				return times;
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return null;
	}
	
	/**
	 * 精确值毫秒的字符转为日期，格式:yyyy-MM-dd HH:mm:ss.SS
	 * @param date
	 * @return
	 */
	public static Date stringToSecond(String date)
	{
		if(null != date &&!"".equals(date))
		{
			
			try {
				return  sdfmss.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			} 
		}
		return null;
	}
	
	/**
	 * 验证起始日期是否早于截止日期
	 */
	public static String getDateRangeValidateMsg(Date start, Date end)
	{
		if (start.getTime() <= end.getTime())
		{
			return "";
		}else{
			return "起始日期 必需早于 截止日期！";
		}
	}
	
	/**
	 * 验证起始日期是否早于截止日期
	 * @param start yyyy-MM-dd
	 * @param end yyyy-MM-dd
	 * @return
	 */
	public static boolean judgeStartIsLessThanEnd(Date start, Date end)
	{
		start=stringToDate(getDate(start));
		end=stringToDate(getDate(end));
		if (start.getTime() <= end.getTime())
		{
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 返回指定格式的当前日期字符
	 * @param pattern
	 * @return
	 */
	public static String getCurrTimeByPattern(String pattern)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Timestamp(System.currentTimeMillis()));
	}
	
	/**
	 * 获取当前年的第一天
	 * @return
	 */
	public static Date getFirstDayOfYear()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return cal.getTime();
	}
	
	public static Date getFirstDayOfMonth()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
	
	public static Date getBeforeDate(int beforeDays){
		return getBeforeDate(new Date(),beforeDays);
	}
	
	public static Date getBeforeDate(Date date,int beforeDays){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, beforeDays);
		return cal.getTime();
	}
	
	public static Date getNextDateByWeek(Date date,int weekday){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.WEEK_OF_MONTH, +1);
		cal.set(Calendar.DAY_OF_WEEK, weekday);
		return cal.getTime();
	}
	
	public static Date getNextDateByMonth(Date date,int day){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if(cal.get(Calendar.DAY_OF_MONTH)<day){
			cal.set(Calendar.DAY_OF_MONTH, day);
			return cal.getTime();
		}
		cal.add(Calendar.MONTH, +1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}
	
	public static int getDateInterval(Date start,Date end)
	{
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		startCal.setTime(start);
		endCal.setTime(end);
		
		//设置时间为0时      
		startCal.set(java.util.Calendar.HOUR_OF_DAY, 0);      
		startCal.set(java.util.Calendar.MINUTE, 0);      
		startCal.set(java.util.Calendar.SECOND, 0);      
		endCal.set(java.util.Calendar.HOUR_OF_DAY, 0);      
		endCal.set(java.util.Calendar.MINUTE, 0);      
		endCal.set(java.util.Calendar.SECOND, 0);      
     
        //得到两个日期相差的天数      
        int days = ((int) (endCal.getTime().getTime() / 1000) - (int) (startCal      
                .getTime().getTime() / 1000)) / 3600 / 24;      
              
        return days;      
	}
	
	public static int getDayOfMonth(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}
	
	public static boolean isBothMonth(Date d1,Date d2){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		if(c1.get(Calendar.YEAR)==c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH)==c2.get(Calendar.MONTH)){
			return true;
		}
		return false;
	}
	
	public static boolean isBothDay(Date d1,Date d2){
		if(isBothMonth(d1,d2)){
			Calendar c1 = Calendar.getInstance();
			c1.setTime(d1);
			Calendar c2 = Calendar.getInstance();
			c2.setTime(d2);
			if(c1.get(Calendar.DAY_OF_MONTH)==c2.get(Calendar.DAY_OF_MONTH)){
				return true;
			}
		}
		return false;
	}
	
	public static Hashtable<String, Long> subtract(Date d1, Date d2) {
		long d3 = d2.getTime() - d1.getTime();
		long d = d3 / 24 / 60 / 60 / 1000;// 天
		long h = (d3 - d * 24 * 60 * 60 * 1000) / 60 / 60 / 1000;// 小时
		long m = (d3 - d * 24 * 60 * 60 * 1000 - h * 60 * 60 * 1000) / 60 / 1000;// 分
		long s = (d3 - d * 24 * 60 * 60 * 1000 - h * 60 * 60 * 1000 - m * 60 * 1000) / 1000;// 秒
		Hashtable<String, Long> map = new Hashtable<String, Long>();
		map.put("day", d);
		map.put("hour", h);
		map.put("minute", m);
		map.put("second", s);
		return map;
	}
	
	public static Date lastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		return cal.getTime();
	}
	
	/**
	 * 获取下一还款日
	 * @param loanDate
	 * @param beforeDate
	 * @return
	 */
	public static Date repayDate(Date loanDate, Date beforeDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(beforeDate);
		cal.add(Calendar.MONTH,1);
		Date result = cal.getTime();
		Date lastDate = lastDayOfMonth(result);
		int lastDay = lastDate.getDate();
		int loanDay = loanDate.getDate();
		int nextDay = result.getDate();
		if(nextDay>loanDay) {
			cal.add(Calendar.DATE, loanDay-nextDay);
			result = cal.getTime();
		} else if(lastDay>=loanDay) {
			cal.setTime(lastDate);
			cal.add(Calendar.DATE, loanDay-lastDay);
			result = cal.getTime();
		}

		return result;
	}
	
	public static String date2Str19(Date date) {
		return sdfs.format(date);
	}
	
	public static String date2Str10(Date date) {
		return sdfd.format(date);
	}
	
	public static int date2Int8(Date date) {
		return Integer.valueOf(sdfd2.format(date));
	}
	
	public static String date2Str6(Date date) {
		return sdfd6.format(date);
	}
	
	public static Date str2Date10(String str) {
		try {
			return sdfd.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String date2StrD(Date date) {
		return sdd.format(date);
	}
	
	public static Date date2date10(Calendar c) {
		/* -- calendar 实例出来的对象，毫秒并不精准，改为new Date -- 
		Calendar ret = Calendar.getInstance();
		ret.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 0, 0, 0);
		return ret.getTime();
		*/
		return new Date(c.get(Calendar.YEAR) - 1900, c.get(Calendar.MONTH), c.get(Calendar.DATE), 0, 0, 0);
	}
	
	public static Map<Integer, Integer> dateCompare(Calendar c1, Calendar c2) {
		Map<Integer, Integer> ret = new HashMap<Integer, Integer>();
		int year = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
		int month = c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
		int day = c1.get(Calendar.DAY_OF_MONTH) - c2.get(Calendar.DAY_OF_MONTH);
		int hour = c1.get(Calendar.HOUR_OF_DAY) - c2.get(Calendar.HOUR_OF_DAY);
		int min = c1.get(Calendar.MINUTE) - c2.get(Calendar.MINUTE);
		int second = c1.get(Calendar.SECOND) - c2.get(Calendar.SECOND);
		if (month < 0 && year > 0) {
			year--;
			month += 12;
		}
		if (day < 0 && month > 0) {
			month--;
			day = c2.getActualMaximum(Calendar.DAY_OF_MONTH) - c2.get(Calendar.DAY_OF_MONTH) + c1.get(Calendar.DAY_OF_MONTH);
		}
		if (hour < 0 && day > 0) {
			day--;
			hour += 24;
		}
		if (min < 0 && hour > 0) {
			hour--;
			min += 60;
		}
		if (second < 0 && min > 0) {
			min--;
			second += 60;
		}
		ret.put(Calendar.YEAR, year);
		ret.put(Calendar.MONTH, month);
		ret.put(Calendar.DATE, day);
		ret.put(Calendar.HOUR, hour);
		ret.put(Calendar.MINUTE, min);
		ret.put(Calendar.SECOND, second);
		return ret;
	}
	
	/**
	 * 计算所选日期的“日”，到指定年、月份时，是否超出本月最大“日”，是则返回本月最后一天，否则为当天
	 * @param args
	 */
	public static Calendar getDateForAssignMonthAndDay(int year, int month, int day) {
		Calendar c1 = Calendar.getInstance();
		c1.set(year, month, day);
		int day1 = c1.get(Calendar.DATE);
		if(day1 < day) {
			c1.set(year, c1.get(Calendar.MONTH), 1);
			c1.setTime(new Date(c1.getTimeInMillis() - 24 * 60 * 60 * 1000));
			return c1;
		}
		return c1;
	}
	
}
