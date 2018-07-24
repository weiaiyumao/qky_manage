/**
 * 系统功能
 * @author 许进
 */

package com.zqfinance.module.sys.domain;

import com.zqfinance.system.common.Page;

public class Action extends Page {
	
	private long id;
	
	private String actionName;
	
	private String actionUrl;
	
	private long moduleId;
	
	
	private String moduleName;
	
	/**
	 * 非数据库字段用来存放是否选中的标志
	 */
	private String checkFlag ;
	
	private String aSort;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public long getModuleId() {
		return moduleId;
	}

	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}



	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getaSort() {
		return aSort;
	}

	public void setaSort(String aSort) {
		this.aSort = aSort;
	}
	
	
	
}

