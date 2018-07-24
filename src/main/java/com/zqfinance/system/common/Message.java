

package com.zqfinance.system.common;

import com.zqfinance.system.config.ManageConfig;

public class Message {

	private String result = ManageConfig.DATA_MESSAGE_MSG_SUCCESS;
	
	private String msg;

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
	
	
}

