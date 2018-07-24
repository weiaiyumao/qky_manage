/**
 * 功能模块管理
 * @author 许进
 */

package com.zqfinance.module.sys.service;

import java.util.List;

import com.zqfinance.module.sys.domain.Module;

public interface ModuleService {
	/**
	 * 保存模块
	 * @author 许进
	 */
	public void saveModule(Module module);
	/**
	 * 修改模块
	 * @author 许进
	 */
	public void updateModule(Module module);
	/**
	 * 查询列表
	 * @author 许进
	 */
	public List<Module> getModuleList(Module module);
	/**
	 * 删除模块
	 * @author 许进
	 */
	public void delModule(long moduleId);
	/**
	 * 取得功能模块记录数
	 * @author 许进
	 */
	public int getModuleCount();
	/**
	 * 根据ID取得Module对象
	 * @author 许进
	 */
	public Module getModuleById(long id);
	/**
	 * 取得所有的一级Module
	 * @author 许进
	 */
	public List<Module> getFirstLevelModule();
	/**
	 * 取得二级module
	 * @author 许进
	 */
	public List<Module> getSecondModule(long parentId);
	/**
	 * 取得所有的二级模块
	 * @author 许进
	 */
	public List<Module> getAllSecondModule();
	/**
	 * 取得module的菜单
	 * @author 许进
	 */
	public String getModuleJson(String id,String parentId);
	
	public String getModuleJsonForAction(String moduleId);
	
	public String getJsonForModuleAndAction(List<Long> actionIdList);
	
	public Module getModuleByNameOrId(Module module);
}

