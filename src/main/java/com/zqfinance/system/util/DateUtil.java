/**
 * 日期工具类
 * @author 许进
 */

package com.zqfinance.system.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zqfinance.system.common.DataMessage;
import com.zqfinance.system.common.DataPageMessage;

public class DateUtil {
	/**
	 * 将日期转换为18位字符串类型
	 * 
	 * @param date
	 * @return
	 */
	public static String fmt14Date(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	private final SimpleDateFormat format;

	public DateUtil(SimpleDateFormat format) {
		this.format = format;
	}

	public SimpleDateFormat getFormat() {
		return format;
	}

	// 紧凑型日期格式，也就是纯数字类型yyyyMMdd
	public static final DateUtil COMPAT = new DateUtil(new SimpleDateFormat("yyyyMMdd"));
	public static final DateUtil COMPAT_FULL = new DateUtil(new SimpleDateFormat("yyyyMMddHHmmss"));

	// 常用日期格式，yyyy-MM-dd
	public static final DateUtil COMMON = new DateUtil(new SimpleDateFormat("yyyy-MM-dd"));
	public static final DateUtil COMMON_FULL = new DateUtil(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

	// 使用斜线分隔的，西方多采用，yyyy/MM/dd
	public static final DateUtil SLASH = new DateUtil(new SimpleDateFormat("yyyy/MM/dd"));

	// 中文日期格式常用，yyyy年MM月dd日
	public static final DateUtil CHINESE = new DateUtil(new SimpleDateFormat("yyyy年MM月dd日"));
	public static final DateUtil CHINESE_FULL = new DateUtil(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒"));

	/**
	 * 默认日期格式：yyyy-MM-dd
	 */
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

	/**
	 * 默认时间格式：yyyy-MM-dd HH:mm:ss
	 */
	public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 默认时间戳格式，到毫秒 yyyy-MM-dd HH:mm:ss SSS
	 */
	public static final String DEFAULT_DATEDETAIL_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";
	/**
	 * 默认时间戳格式，yyyyMMddHHmmss
	 */
	public static final String DEFAULT_DETES_PATTERN = "yyyyMMddHHmmss";

	/**
	 * 1天折算成毫秒数
	 */
	public static final long MILLIS_A_DAY = 24 * 3600 * 1000;

	private static Map<String, Object> parsers = new HashMap<String, Object>();

	private static SimpleDateFormat getDateParser(String pattern) {
		Object parser = parsers.get(pattern);
		if (parser == null) {
			parser = new SimpleDateFormat(pattern);
			parsers.put(pattern, parser);
		}
		return (SimpleDateFormat) parser;
	}

	/**
	 * 取得当前系统日期
	 * 
	 * @return
	 */
	public static java.util.Date currentDate() {
		return new java.util.Date();
	}

	/**
	 * 取得当前系统时间
	 * 
	 * @return
	 */
	public static long currentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 取得系统当前日期，返回默认日期格式的字符串。
	 * 
	 * @param strFormat
	 * @return
	 */
	public static String nowDate(String strFormat) {
		java.util.Date date = new java.util.Date();
		return getDateParser(strFormat).format(date);
	}

	/**
	 * 取得当前系统时间戳
	 * 
	 * @return
	 */
	public static Timestamp currentTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 将日期字符串转换为java.util.Date对象
	 * 
	 * @param dateString
	 * @param pattern
	 *            日期格式
	 * @return
	 * @throws Exception
	 */
	public static java.util.Date toDate(String dateString, String pattern)
			throws Exception {
		return getDateParser(pattern).parse(dateString);
	}

	/**
	 * 将日期字符串转换为java.util.Date对象，使用默认日期格式
	 * 
	 * @param dateString
	 * @return
	 * @throws Exception
	 */
	public static java.util.Date toDate(String dateString) throws Exception {
		return getDateParser(DEFAULT_DATE_PATTERN).parse(dateString);
	}

	/**
	 * 将时间字符串转换为java.util.Date对象
	 * 
	 * @param dateString
	 * @return
	 * @throws Exception
	 */
	public static java.util.Date toDateTime(String dateString) throws Exception {
		return getDateParser(DEFAULT_DATETIME_PATTERN).parse(dateString);
	}

	/**
	 * 将java.util.Date对象转换为字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String toDateString(java.util.Date date, String pattern) {
		return getDateParser(pattern).format(date);
	}

	/**
	 * 将java.util.Date对象转换为字符串，使用默认日期格式
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String toDateString(java.util.Date date) {
		return getDateParser(DEFAULT_DATE_PATTERN).format(date);
	}

	/**
	 * 将java.util.Date对象转换为时间字符串，使用默认日期格式
	 * 
	 * @param date
	 * @return
	 */
	public static String toDateTimeString(java.util.Date date) {
		return getDateParser(DEFAULT_DATETIME_PATTERN).format(date);
	}

	/**
	 * 日期相减
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date diffDate(java.util.Date date, int day) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) - ((long) day) * MILLIS_A_DAY);
		return c.getTime();
	}

	/**
	 * 返回毫秒
	 * 
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 * @author doumingjun create 2007-04-07
	 */
	public static long getMillis(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * 日期相加
	 * 
	 * @param date
	 *            日期
	 * @param day
	 *            天数
	 * @return 返回相加后的日期
	 * @author doumingjun create 2007-04-07
	 */
	public static java.util.Date addDate(java.util.Date date, int day) {
		java.util.Calendar c = java.util.Calendar.getInstance();

		c.setTimeInMillis(getMillis(date) + ((long) day) * MILLIS_A_DAY);
		return c.getTime();
	}

	public static List<String> getBetweenDate(String d1, String d2)
			throws Exception {
		List<Date> list = new ArrayList<Date>();
		List<String> dayList = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gc1 = new GregorianCalendar(), gc2 = new GregorianCalendar();
		gc1.setTime(sdf.parse(d1));
		gc2.setTime(sdf.parse(d2));
		do {
			GregorianCalendar gc3 = (GregorianCalendar) gc1.clone();
			list.add(gc3.getTime());
			gc1.add(Calendar.DAY_OF_MONTH, 1);
		} while (!gc1.after(gc2));

		for (Date date : list) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			dayList.add(parseDateToStr(c.getTime(),
					DateUtil.DEFAULT_DATE_PATTERN));
		}

		return dayList;
	}

	public static String parseDateToStr(Date date, String pattern) {
		// 设置日期格式
		DateFormat df = new SimpleDateFormat(pattern);
		// 返回格式化后的字符串
		return df.format(date);
	}

	public static Date parseStrToDate(String stringDate) throws ParseException {
		// 设置日期格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(stringDate);
		// 返回格式化后的字符串
		return date;
	}
	/**
	 * 日期转日历
	 * 
	 * @param date
	 * @return Calendar
	 */
	public static Calendar DateToCalendar(Date date) {
		//System.out.println("转换前类型为：" + date.getClass());
		Calendar startdate = Calendar.getInstance();
		startdate.setTime(date);
		//System.out.println("转换后类型为：" + startdate.getClass());
		return startdate;
	}

	/**
	 * 日历转日期
	 * 
	 * @param calendar
	 * @return Date
	 */
	public static Date CalendarToDate(Calendar calendar) {
		//System.out.println("转换前类型为：" + calendar.getClass());
		Date date = calendar.getTime();
		//System.out.println("转换后类型为：" + date.getClass());
		return date;
	}
	
	
	/**
	 * 计算相差 年、月、日、时、分、秒
	 * 
	 * @author 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Map<Integer, Integer> dateCompare(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d2);
		return calendarCompare(c1, c2);
	}
	
	/**
	 * 计算相差 年、月、日、时、分、秒
	 * 
	 * @author 
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static Map<Integer, Integer> calendarCompare(Calendar c1, Calendar c2) {
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
	 * 计算两个月相隔的月份
	 * @param c1 靠后的日期 //当前计息日期
	 * @param c2 靠前的日期
	 * @return
	 */
	public static int monthBetween(Calendar c1, Calendar c2){
		//如果最后计息日期是最后一天
		
		Calendar tempCalendar = Calendar.getInstance();
		tempCalendar.setTime(c1.getTime());
		int month = 0;
		while(true){
			c1.add(Calendar.MONTH, -1);
			if(c1.compareTo(c2)>=0){
				month ++ ;
			}else{
				break;
			}
		}
		if(month <=0){
			//判断计息日期是不是当前月份的最后一天
			if(isLastDayInMonth(toDateString(tempCalendar.getTime()))
					&&tempCalendar.compareTo(c2)>0
					 &&isLastDayInMonth(toDateString(c2.getTime()))){
				month = 1;
			}
		}
		return month;
	}
	
	
	public static List<String> getAprChangeDateList(String timeTrade){
		List<String> dateList = new ArrayList<String>();
		try{
			//如果交易日期是29号，判断计息日期是不是二月份 如果是二月份则
			String year = timeTrade.substring(0, 4);
			String month = timeTrade.substring(5, 7);
			String day = timeTrade.substring(8);			
			dateList.add(timeTrade);
			if("29".equals(day)||"30".equals(day)){
				int monthint = Integer.parseInt(month);
				while(dateList.size() < 12){
					
					if(monthint < 12){
						monthint ++ ;
					}else{
						monthint = 1;
					}
					//如果是二月份，取得二月份的最后一天
					if(monthint == 2){
						if(monthint - Integer.parseInt(month) >= 1){
							String lastDay = getLastDayInMonth(year+ "-0"+monthint+"-01");
							dateList.add(lastDay);
						}else{						
							String datestr = Integer.parseInt(year) + 1 + "-0"+monthint+"-01";
							String lastDay = getLastDayInMonth(datestr);
							dateList.add(lastDay);
						}
					}else{
						String aprchangeDate = dateList.get(dateList.size() - 1);
						Calendar cl = Calendar.getInstance();
						cl.setTime(toDate(aprchangeDate));
						cl.add(Calendar.MONTH,1);
						dateList.add(toDateString(cl.getTime()).substring(0,8)+day);
					}
					
				}

			}else if("31".equals(day)){
				int monthint = Integer.parseInt(month);
				int yearint = Integer.parseInt(year);
				while(dateList.size() < 12){
					if(monthint < 12){
						monthint ++ ;
					}else{
						monthint = 1;
						yearint ++ ;
					}
					//如果是二月份，取得二月份的最后一天
					if(monthint == 2 || monthint == 4 || monthint == 6 || monthint == 9 || monthint == 11){
						String monthStr = String.valueOf(monthint);
						if(monthint <=9){
							monthStr = "0"+monthint;
						}
						if(monthint - Integer.parseInt(month) >= 1){
							String lastDay = getLastDayInMonth(year+ "-"+monthStr+"-01");
							dateList.add(lastDay);
						}else{						
							String datestr = yearint + "-" + monthStr +"-01";
							String lastDay = getLastDayInMonth(datestr);
							dateList.add(lastDay);
						}
					}else{
						String monthStr = String.valueOf(monthint);
						if(monthint <=9){
							monthStr = "0"+monthint;
						}
						dateList.add(yearint + "-"+monthStr + "-" + day);
					}
				}

			}else{
				while(dateList.size() < 12){
					String aprchangeDate = dateList.get(dateList.size() - 1);
					Calendar cl = Calendar.getInstance();
					cl.setTime(toDate(aprchangeDate));
					cl.add(Calendar.MONTH,1);
					dateList.add(toDateString(cl.getTime()).substring(0,8)+day);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return dateList;
	}
	/**
	 * 判断一个月的最后一天
	 * @param date
	 * @return
	 */
	public static boolean isLastDayInMonth(String date) {
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month));
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDate = cal.getTime();
		String daystr = toDateString(lastDate);
		return date.equals(daystr);
	}
	
	public static String getLastDayInMonth(String date){
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month));
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDate = cal.getTime();
		String daystr = toDateString(lastDate);
		return daystr;
	}
	/**
	 * 计算指定时间 在经过了指定月份 天数 后的时间
	 * @param format
	 * @param StrDate
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String GetSysDate(String StrDate, int year,
			int month, int day) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sFmt = new SimpleDateFormat(DEFAULT_DATETIME_PATTERN);
		cal.setTime(sFmt.parse((StrDate), new ParsePosition(0)));

		if (day != 0) {
			cal.add(cal.DATE, day);
		}
		if (month != 0) {
			cal.add(cal.MONTH, month);
		}
		if (year != 0) {
			cal.add(cal.YEAR, year);

		}
		return sFmt.format(cal.getTime());
	}
	/**
	 * 计算指定时间 在经过了指定月份 天数 后的时间
	 * @param format
	 * @param StrDate
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getSyDate(Date date, int year,
			int month, int day){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sFmt = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
		cal.setTime(date);

		if (day != 0) {
			cal.add(cal.DATE, day);
		}
		if (month != 0) {
			cal.add(cal.MONTH, month);
		}
		if (year != 0) {
			cal.add(cal.YEAR, year);

		}
		return sFmt.format(cal.getTime());
	}
	
	/**
	 * 日期获取字符串
	 */
	public String getDateText(Date date) {

		return date == null ? "" : getFormat().format(date);
	}

	/**
	 * 字符串获取日期
	 * 
	 * @throws ParseException
	 */
	public Date getTextDate(String text) throws ParseException {
		return getFormat().parse(text);
	}

	/**
	 * 日期获取字符串
	 */
	public static String getDateText(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 字符串获取日期
	 * 
	 * @throws ParseException
	 */
	public static Date getTextDate(String dateText, String format) throws ParseException {
		return new SimpleDateFormat(format).parse(dateText);
	}

	/**
	 * 根据日期，返回其星期数，周一为1，周日为7
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int w = calendar.get(Calendar.DAY_OF_WEEK);
		int ret;
		if (w == Calendar.SUNDAY)
			ret = 7;
		else
			ret = w - 1;
		return ret;
	}

	public static List<String> getFiveYears() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		List<String> yearList = new ArrayList<String>();
		for (int i = year - 5; i <= year + 5; i++) {
			yearList.add(String.valueOf(i));
		}
		return yearList;
	}

	public static List<String> getMonths() {
		List<String> mList = new ArrayList<String>();
		for (int i = 1; i < 13; i++) {
			mList.add(String.valueOf(i));
		}
		return mList;
	}

	public static boolean datePattern(String date) {
		String eL = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(date);
		boolean dateFlag = m.matches();
		return dateFlag;
	}

	public static boolean compareDate(Date start, Date end) {
		long startMillions = start.getTime();
		long endMillions = end.getTime();
		if (endMillions >= startMillions) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	/**
	 * DataPageMessage对象    时间毫秒转换成 yyyy-MM-dd hh:mm:ss
	 * @param datapagemessage
	 * @author 宋宇
	 */
	public static void formatDateByDataPageMessage(DataPageMessage datapagemessage){
		if(null!=datapagemessage){
			if(null!=datapagemessage.getContentList()){
				List<Map<String,String>> dateMap = datapagemessage.getContentList();
				for (Map<String, String> dateInMap : dateMap) {
					if (dateInMap.containsKey("createTime") && MStringUtil.isNotEmpty(dateInMap.get("createTime"))) {
						dateInMap.put("createTime", dateToString(dateInMap.get("createTime")));
					}
					if(dateInMap.containsKey("modifyTime") && MStringUtil.isNotEmpty(dateInMap.get("modifyTime"))){
						dateInMap.put("modifyTime", dateToString(dateInMap.get("modifyTime")));
					}
					if(dateInMap.containsKey("repayTime") && MStringUtil.isNotEmpty(dateInMap.get("repayTime"))){
						dateInMap.put("repayTime", dateToString(dateInMap.get("repayTime")));
					}
					if(dateInMap.containsKey("timeCreate") && MStringUtil.isNotEmpty(dateInMap.get("timeCreate"))){
						dateInMap.put("timeCreate",dateToString(dateInMap.get("timeCreate")));
					}
					if(dateInMap.containsKey("timeTrading") && MStringUtil.isNotEmpty(dateInMap.get("timeTrading"))){
						dateInMap.put("timeTrading",dateToString(dateInMap.get("timeTrading")));
					}
					if(dateInMap.containsKey("sendtime") && MStringUtil.isNotEmpty(dateInMap.get("sendtime"))){
						dateInMap.put("sendtime",dateToString(dateInMap.get("sendtime")));
					}
					if(dateInMap.containsKey("sendTime") && MStringUtil.isNotEmpty(dateInMap.get("sendTime"))){
						dateInMap.put("sendTime",dateToString(dateInMap.get("sendTime")));
					}
					if(dateInMap.containsKey("matchTime") && MStringUtil.isNotEmpty(dateInMap.get("matchTime"))){
						dateInMap.put("matchTime",dateToString(dateInMap.get("matchTime")));
					}
					if(dateInMap.containsKey("enddate") && MStringUtil.isNotEmpty(dateInMap.get("enddate"))){
						dateInMap.put("enddate",dateToString(dateInMap.get("enddate")));
					}
					if(dateInMap.containsKey("begindate") && MStringUtil.isNotEmpty(dateInMap.get("begindate"))){
						dateInMap.put("begindate",dateToString(dateInMap.get("begindate")));
					}
					if(dateInMap.containsKey("requestTime") && MStringUtil.isNotEmpty(dateInMap.get("requestTime"))){
						dateInMap.put("requestTime",dateToString(dateInMap.get("requestTime")));
					}
					if(dateInMap.containsKey("updatetime") && MStringUtil.isNotEmpty(dateInMap.get("updatetime"))){
						dateInMap.put("updatetime",dateToString(dateInMap.get("updatetime")));
					}
				}
			}
		}
	}
	
	/**
	 * DataMessage对象    时间毫秒转换成 yyyy-MM-dd hh:mm:ss
	 * @param dataMessage
	 * @author rian
	 */
	public static void formatDateByDataMessage(DataMessage dataMessage){
		if(null!=dataMessage){
			if(null!=dataMessage.getData()){
				Map<String,String> dateMap = dataMessage.getData();
				if(MStringUtil.isNotEmpty(dateMap.get("createTime"))){
					dateMap.put("createTime",dateToString(dateMap.get("createTime")));
				}
				if(MStringUtil.isNotEmpty(dateMap.get("modifyTime"))){
					dateMap.put("modifyTime",dateToString(dateMap.get("modifyTime")));
				}
				if(MStringUtil.isNotEmpty(dateMap.get("timeCreate"))){
					dateMap.put("timeCreate",dateToString(dateMap.get("timeCreate")));
				}
				if(MStringUtil.isNotEmpty(dateMap.get("timeModified"))){
					dateMap.put("timeModified",dateToString(dateMap.get("timeModified")));
				}
				if(MStringUtil.isNotEmpty(dateMap.get("timeOrder"))){
					dateMap.put("timeOrder",dateToString(dateMap.get("timeOrder")));
				}
				if(MStringUtil.isNotEmpty(dateMap.get("timeClosing"))){
					dateMap.put("timeClosing",dateToString(dateMap.get("timeClosing")));
				}
				if(MStringUtil.isNotEmpty(dateMap.get("sendMoneyDate"))){
					dateMap.put("sendMoneyDate",dateToString(dateMap.get("sendMoneyDate")));
				}
				if(MStringUtil.isNotEmpty(dateMap.get("sendtime"))){
					dateMap.put("sendtime",dateToString(dateMap.get("sendtime")));
				}
			 	if(MStringUtil.isNotEmpty(dateMap.get("lasttime"))){
			 		dateMap.put("lasttime",dateToString(dateMap.get("lasttime")));
			 	}
			 	if(MStringUtil.isNotEmpty(dateMap.get("sendTime"))){
			 		dateMap.put("sendTime",dateToString(dateMap.get("sendTime")));
			 	}
			 	if(MStringUtil.isNotEmpty(dateMap.get("begindate"))){
			 		dateMap.put("begindate",dateToString(dateMap.get("begindate")));
			 	}
			 	if(MStringUtil.isNotEmpty(dateMap.get("enddate"))){
			 		dateMap.put("enddate",dateToString(dateMap.get("enddate")));
			 	}
			 	if(MStringUtil.isNotEmpty(dateMap.get("timeTrading"))){
			 		dateMap.put("timeTrading",dateToString(dateMap.get("timeTrading")));
			 	}
			}
		}
	}
	
	public static String dateToString(String date){
		Long modifyTime = Long.parseLong(date);
		Date dat = new Date(modifyTime);
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dat);
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return format.format(gc.getTime());
	}
	
	/**
	 * 根据日期获取获取某一个月份的天数
	 * 
	 * @param date
	 *            yyyy-MM-dd
	 * @return
	 */
	public static int getDayintForMonth(String date) {
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month));
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDate = cal.getTime();
		String daystr = DateUtil.COMMON.getFormat().format(lastDate)
				.substring(8);
		return Integer.parseInt(daystr);
	}
	
	public static boolean keyContainsDay(Set<String> keyset,String dayStr){
		Iterator<String> iterator =  keyset.iterator();
		while(iterator.hasNext()){
			String keys = iterator.next();
			if(keys.substring(8).equals(dayStr)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean keyContainsMonth(Set<String> keyset,String MonthStr){
		Iterator<String> iterator =  keyset.iterator();
		while(iterator.hasNext()){
			String keys = iterator.next();
			if(keys.substring(5,7).equals(MonthStr)){
				return true;
			}
		}
		return false;
	}
}

