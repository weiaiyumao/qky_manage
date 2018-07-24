/**
 * ztree domain
 * @author 许进
 */

package com.zqfinance.system.common;

public class TreeVo {
	private long id;
	
	private long pId;
	
	private String name;
	
	private boolean open = true;
	
	private boolean checked;
	
	private boolean nocheck;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public long getpId() {
		return pId;
	}

	public void setpId(long pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}
	
	
	
}

