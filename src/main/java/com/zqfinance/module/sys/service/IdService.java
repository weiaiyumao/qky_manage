

package com.zqfinance.module.sys.service;

public interface IdService {
	public long getUserId();
	/**
	 * menu button主键使用同一个seq
	 * @author 许进
	 */
	public long getModuleId();
	
	public long getActionId();
	
	public long getRoleId();
	
	public long getRoleActionMapId();
	
	public long getUserRoleMapId();
}

