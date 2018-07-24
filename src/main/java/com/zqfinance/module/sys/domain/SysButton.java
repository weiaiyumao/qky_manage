

package com.zqfinance.module.sys.domain;

public class SysButton {
	private long id;
	
	private String buttonCode;
	
	private String buttonName;
	
	private long menuId;
	
	private String buttonStatus;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getButtonCode() {
		return buttonCode;
	}

	public void setButtonCode(String buttonCode) {
		this.buttonCode = buttonCode;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public long getMenuId() {
		return menuId;
	}

	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}

	public String getButtonStatus() {
		return buttonStatus;
	}

	public void setButtonStatus(String buttonStatus) {
		this.buttonStatus = buttonStatus;
	}
	
	
}

