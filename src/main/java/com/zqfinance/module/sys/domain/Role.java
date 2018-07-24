/**
 * 角色
 * @author 许进
 */

package com.zqfinance.module.sys.domain;

import com.zqfinance.system.common.Page;

public class Role extends Page {
	private long id;
	
	private String roleName;
	
	private String roleCode;
	
	private String roleStatus;
	
	private String roleActions;
	
	private String checkFlag;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleStatus() {
		return roleStatus;
	}

	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
	}

	public String getRoleActions() {
		return roleActions;
	}

	public void setRoleActions(String roleActions) {
		this.roleActions = roleActions;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}
	
	
}

