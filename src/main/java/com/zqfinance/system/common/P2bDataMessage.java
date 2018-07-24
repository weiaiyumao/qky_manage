package com.zqfinance.system.common;


public class P2bDataMessage {
	public static String SUCCESS = "0";//操作成功
	public static String FAIL = "1";//操作失败
	public static String SYSTEMERR = "2"; //系统错误
	public static String SESSIONTIMEOUT = "3"; //会话过期
	public static String PARAMERR = "4"; //请求参数错误
	
	// ------ Message contents
	private String result = SUCCESS;//操作结果
	private String msg = "成功";// 说明信息
	private Object data = null;

	public P2bDataMessage() {
		result = "0";// 默认操作成功
		msg = "成功";
		data = null;
	}
	
	public P2bDataMessage(String result, String msg) {
		this.result = result;
		this.msg = msg;
		data = null;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	
}
