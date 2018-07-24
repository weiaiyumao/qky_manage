/**
 * Module db
 * @author 许进
 */

package com.zqfinance.module.sys.dao;

import java.util.List;

import com.zqfinance.module.sys.domain.Module;

public interface ModuleMapper {
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
	 * 取得二级Module
	 * @author 许进
	 */
	public List<Module> getSecondModule(long parentId);
	/**
	 * 取得所有的二级模块
	 */
	public List<Module> getAllSecondModule();
	/**
	 * 递归取得所有模块对象
	 * @author 许进
	 */
	public List<Module> getAllModule();
	/**
	 * 取得子集模块的id集合
	 * @author 许进
	 */
	public List<Long> getSubIdList(long id);
	/**
	 * 删除子模块
	 * @author 许进
	 */
	public void delSubModule(long id);
	/**
	 * 根据id或者name查询Module对象
	 * @param module
	 * @return
	 * @author 宋宇
	 */
	public Module getModuleByNameOrId(Module module);
}

