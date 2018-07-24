/**
 * 用户和角色关联关系
 */

package com.zqfinance.module.sys.domain;

public class UserRoleMap {
	
	private long id;
	
	private long userId;
	
	private long roleId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	
	
}

