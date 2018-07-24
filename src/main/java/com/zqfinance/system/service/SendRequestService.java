
package com.zqfinance.system.service;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public interface SendRequestService {
	
	public String sendRequest(String url,JSONObject josnObject);
	
//	public void redirectRequest(HttpServletResponse response,String url, JSONObject jsonObject);
}

