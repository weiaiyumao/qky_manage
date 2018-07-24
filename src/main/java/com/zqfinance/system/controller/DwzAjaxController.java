package com.zqfinance.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zqfinance.system.config.ManageConfig;

public class DwzAjaxController extends BaseController {
	
	private int statusCode = ManageConfig.STATUS_CODE_SUCCESS;// 请求状态码

	private String message = null;// 错误信息
	
	private String callbackType = null;
	
	private String forwardUrl = null;// 跳转URL
	
	private List<Object> objList = new ArrayList<Object>();
	
	private List<Map<String, String>> mapList=new ArrayList<Map<String,String>>();

	private DwzAjaxController ajaxForward(int statusCode, String message,String forwardUrl,String callbackType,List<Object> objList) {
		this.statusCode = statusCode;
		this.message = message;
		this.forwardUrl = forwardUrl;
		this.callbackType = callbackType;
		this.objList = objList;
		DwzAjaxController dwzAjax = new DwzAjaxController();
		dwzAjax.setStatusCode(statusCode);
		dwzAjax.setMessage(message);
		dwzAjax.setForwardUrl(forwardUrl);
		dwzAjax.setCallbackType(callbackType);
		dwzAjax.setObjList(objList);
		return dwzAjax;
	}
	
	private DwzAjaxController ajaxForwardMap(int statusCode, String message,String forwardUrl,String callbackType,List<Map<String, String>> mapList) {
		this.statusCode = statusCode;
		this.message = message;
		this.forwardUrl = forwardUrl;
		this.callbackType = callbackType;
		this.mapList=mapList;
		DwzAjaxController dwzAjax = new DwzAjaxController();
		dwzAjax.setStatusCode(statusCode);
		dwzAjax.setMessage(message);
		dwzAjax.setForwardUrl(forwardUrl);
		dwzAjax.setCallbackType(callbackType);
		dwzAjax.setMapList(mapList);
		return dwzAjax;
	}

	/**
	 * ajax 提交后跳转
	 * 
	 * @param message
	 * @return
	 */
	protected  DwzAjaxController ajaxForwardSuccess(String message,String forwardUrl,String callbackType,List<Object> objList) {
		return ajaxForward(ManageConfig.STATUS_CODE_SUCCESS, message,forwardUrl,callbackType,objList);
	}

	/**
	 * ajax 提交失败后跳转
	 * 
	 * @param message
	 * @return
	 */
	protected DwzAjaxController ajaxForwardError(String message,String forwardUrl,String callbackType,List<Object> objList) {
		return ajaxForward(ManageConfig.STATUS_CODE_ERROR, message,forwardUrl,callbackType,objList);
	}
	
	/**
	 * ajax 提交后跳转
	 * 
	 * @param message
	 * @return
	 */
	protected  DwzAjaxController ajaxForwardSuccessMap(String message,String forwardUrl,String callbackType,List<Map<String, String>> mapList) {
		return ajaxForwardMap(ManageConfig.STATUS_CODE_SUCCESS, message,forwardUrl,callbackType,mapList);
	}

	/**
	 * ajax 提交失败后跳转
	 * 
	 * @param message
	 * @return
	 */
	protected DwzAjaxController ajaxForwardErrorMap(String message,String forwardUrl,String callbackType,List<Map<String, String>> mapList) {
		return ajaxForwardMap(ManageConfig.STATUS_CODE_ERROR, message,forwardUrl,callbackType,mapList);
	}
	

	public int getStatusCode() {

		return statusCode;

	}

	public String getMessage() {

		return message;

	}

	public String getForwardUrl() {

		return forwardUrl;

	}

	public void setForwardUrl(String forwardUrl) {

		this.forwardUrl = forwardUrl;

	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCallbackType() {
		return callbackType;
	}

	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}

	public List<Object> getObjList() {
		return objList;
	}

	public void setObjList(List<Object> objList) {
		this.objList = objList;
	}

	public List<Map<String, String>> getMapList() {
		return mapList;
	}

	public void setMapList(List<Map<String, String>> mapList) {
		this.mapList = mapList;
	}
	
}
