/**
 * 根据数据库seq生成主键
 */

package com.zqfinance.module.sys.dao;


public interface IdMapper {
	public long getUserId();
	
	public long getModuleId();
	
	public long getActionId();
	
	public long getRoleId();
	
	public long getRoleActionMapId();
	
	public long getUserRoleMapId();
}

