

package com.zqfinance.system.service.impl;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.zqfinance.system.config.ConfigManager;
import com.zqfinance.system.service.SendRequestService;
import com.zqfinance.system.util.HrdSignUtil;
import com.zqfinance.system.util.MessageUtil;

@Service
public class SendRequestServiceImpl implements SendRequestService {

	@Override
	public String sendRequest(String url,JSONObject jsonObject) {
		

		String pageSize = null;
		
		@SuppressWarnings("rawtypes")
		Iterator  iterator = jsonObject.keys();
		// 设置分页参数
		while(iterator.hasNext()){
			String key = (String) iterator.next();
			String value = jsonObject.getString(key); 
			
			if("pageNum".equals(key)){
				value = String.valueOf(Integer.parseInt(value) - 1);
				jsonObject.put(key, value);
			} else if("numPerPage".equals(key)){
				pageSize = value;
			}
		}
		
		if(null != pageSize) {
			jsonObject.put("pageSize", pageSize);
			jsonObject.remove("numPerPage");
		}
		//去掉"_"参数
		jsonObject.remove("_");
		HrdSignUtil.sign(jsonObject, ConfigManager.CFG_LCTRADING_PRIKEY, ConfigManager.CFG_LCTRADING_SIGNKEY, ConfigManager.CFG_LCTRADING_SIGNPARAM);
		
		String responseString = MessageUtil.createHttpPost(url, jsonObject);
		return responseString;
		
	}

	@Override
	public void redirectRequest(HttpServletResponse response,String url, JSONObject jsonObject) {
		String returnStr = HrdSignUtil.signForRedirect(jsonObject, ConfigManager.CFG_LCPAY_PRIKEY, ConfigManager.CFG_LCPAY_SIGNKEY, ConfigManager.CFG_LCPAY_SIGNPARAM);
		try {
			response.sendRedirect(url+"?"+returnStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}

