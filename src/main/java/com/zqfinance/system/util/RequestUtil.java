package com.zqfinance.system.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.zqfinance.system.config.ManageConfig;

import net.sf.json.JSONObject;

/**
 * 
 * @author 许进
 * @Date 2014-7-16
 */
public class RequestUtil {
	/**
	 * 从请求中获取参数装换为JSONObject对象
	 */
	public static JSONObject fromRequestToJson(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		Enumeration<?> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			String paraValue = request.getParameter(paraName) == null ? ""
					: (String) request.getParameter(paraName);
			try {
				paraValue = URLDecoder.decode(paraValue, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			jsonObject.put(paraName, paraValue);
		}
		//添加客户信息id查询
		HttpSession session =  request.getSession();
		String customerId =  session.getAttribute(ManageConfig.SESSION_CUSTOMER_ID)!=null?session.getAttribute(ManageConfig.SESSION_CUSTOMER_ID).toString():null;
		if(MStringUtil.isNotEmpty(customerId)){
			jsonObject.put("customerId", customerId);
		}	
		return jsonObject;
		
	}
	
}
