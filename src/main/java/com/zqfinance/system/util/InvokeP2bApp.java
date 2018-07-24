package com.zqfinance.system.util;

import com.zqfinance.system.config.ConfigManager;

import net.sf.json.JSONObject;

/**
 * 
 * @author 
 * 调用P2b系统的出口
 */
public class InvokeP2bApp {
	private InvokeP2bApp() {
		
	}
	
	public static String send(String url, JSONObject json) {
		HrdSignUtil.sign(json, ConfigManager.CFG_P2B_PRIKEY, ConfigManager.CFG_P2B_SIGNKEY, ConfigManager.CFG_P2B_SIGNPARAM);
		return MessageUtil.createHttpPost(url, json);
	}
}
