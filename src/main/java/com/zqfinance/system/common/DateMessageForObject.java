package com.zqfinance.system.common;

public class DateMessageForObject extends Message {
	public static String SUCCESS = "0";//操作成功
	public static String FAIL = "1";//操作失败
	public static String SYSTEMERR = "2"; //系统错误
	public static String SESSIONTIMEOUT = "3"; //会话过期
	public static String PARAMERR = "4"; //请求参数错误
	
	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
