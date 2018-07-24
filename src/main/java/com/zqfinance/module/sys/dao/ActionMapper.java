/**
 * 系统功能DB
 * @author 许进
 */

package com.zqfinance.module.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zqfinance.module.sys.domain.Action;
import com.zqfinance.module.sys.domain.Role;

public interface ActionMapper {
	/**
	 * 查询出所有的系统功能
	 * @author 许进
	 */
	public List<Action> getActionList(Action action);
	/**
	 * 新增系统功能
	 * @author 许进
	 */
	public void addAction(Action action);
	/**
	 * 修改系统功能
	 * @author 许进
	 */
	public void updateAction(Action action);
	/**
	 * 删除系统功能
	 */
	public void delAction(long id);
	/**
	 * 根据id取得Action的对象
	 * @author 许进
	 */
	public Action getActionById(long id);
	/**
	 * 取得功能记录数
	 * @author 许进
	 */
	public int getActionCount();
	/**
	 * 根据模块的id取得功能的列表
	 * @author 许进
	 */
	public List<Action> getActionListByModule(long moduleId);
	
	public List<Action> getAllActionList();
	
	public List<Role> getRoleListByActionId(long actionId);
	
	public void delRoleActionMapByActionId(long actionId);
	
	public void delActionByModuleIdList(@Param("moduleIdList")List<Long> moduleIdList);
	
	public List<Long> getActionByModuleIdList(@Param("moduleIdList")List<Long> moduleIdList);
	
	public void delRoleActionMapByActionIdList(@Param("actionIdList")List<Long> actionIdList);
	/**
	 * 根据id或者名称查询Action对象
	 * @param action
	 * @return
	 * @author 宋宇
	 */
	public Action getActionByNameOrId(Action action);
}

