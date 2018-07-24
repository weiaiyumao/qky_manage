package com.zqfinance.module.sys.service;

import java.util.List;

import com.zqfinance.module.sys.domain.Loginfo;

public interface LoginfoService {
	/**
	 * 查询出所有的登陆日志
	 * @author 宋宇
	 */
	public List<Loginfo> getLoginfoList(Loginfo loginfo);
	/**
	 * 获取总条数
	 * @author 宋宇
	 */
	public int getLoginfoCount(Loginfo loginfo);
	/**
	 * 新增登陆日志
	 * @author 宋宇
	 */
	public void addLoginfo(Loginfo loginfo);
	/**
	 * 根据id查找登陆日志
	 * @author 宋宇
	 */
	public Loginfo getLoginfoByNameOrId(Loginfo loginfo);
}
