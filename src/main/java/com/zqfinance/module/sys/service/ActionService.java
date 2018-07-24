/**
 * action service
 * @author 许进
 */

package com.zqfinance.module.sys.service;

import java.util.List;

import com.zqfinance.module.sys.domain.Action;
import com.zqfinance.module.sys.domain.Role;

public interface ActionService {
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
	 * 查询出Action的记录数
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
	
	public Action getActionByNameOrId(Action action);
}

