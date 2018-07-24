

package com.zqfinance.system.common;

import java.util.Map;

public class DataMessage extends Message {
	private Map<String,String> data;

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}
	
	
}

