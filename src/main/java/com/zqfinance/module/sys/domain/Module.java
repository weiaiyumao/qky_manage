/**
 * 功能模块
 * @author 许进
 */

package com.zqfinance.module.sys.domain;

import com.zqfinance.system.common.Page;

public class Module extends Page {
	
	private long id;
	
	private String moduleName;
	
	private long parentId;
	
	private String parentName;
	
	private String mSort;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getmSort() {
		return mSort;
	}

	public void setmSort(String mSort) {
		this.mSort = mSort;
	}
	
	
	
}

