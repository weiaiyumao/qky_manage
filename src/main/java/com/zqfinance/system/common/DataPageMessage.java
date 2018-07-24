

package com.zqfinance.system.common;

import java.util.List;
import java.util.Map;

public class DataPageMessage extends Message {
	
	private List<Map<String,String>> contentList;
	
	private Page page;


	public List<Map<String, String>> getContentList() {
		return contentList;
	}

	public void setContentList(List<Map<String, String>> contentList) {
		this.contentList = contentList;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	
}

