/**
 * 角色和功能对应关系
 * @author 许进
 */

package com.zqfinance.module.sys.domain;

public class RoleActionMap {
	private long id;
	private long roleId;
	private long actionId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public long getActionId() {
		return actionId;
	}
	public void setActionId(long actionId) {
		this.actionId = actionId;
	}
	
}

