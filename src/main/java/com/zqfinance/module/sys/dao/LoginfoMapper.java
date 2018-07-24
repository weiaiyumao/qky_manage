package com.zqfinance.module.sys.dao;

import java.util.List;

import com.zqfinance.module.sys.domain.Loginfo;

public interface LoginfoMapper {
	
	/**
	 * 查询出所有的登陆日志
	 * @author 宋宇
	 */
	public List<Loginfo> getLoginfoList(Loginfo loginfo);
	/**
	 * 获取总条数
	 * @param loginfo
	 * @return
	 */
	public int getLoginfoCount(Loginfo loginfo);
	/**
	 * 新增登陆日志
	 * @author 宋宇
	 */
	public void addLoginfo(Loginfo loginfo);
	/**
	 * 根据id或者名称查询Action对象
	 * @param action
	 * @return
	 * @author 宋宇
	 */
	public Loginfo getLoginfoByNameOrId(Loginfo loginfo);
}
