/**
 * 角色 service
 * @author 许进
 */
package com.zqfinance.module.sys.service;

import java.util.List;

import com.zqfinance.module.sys.domain.Action;
import com.zqfinance.module.sys.domain.Role;
import com.zqfinance.module.sys.domain.RoleActionMap;

public interface RoleService {
	/**
	 * 查询角色的列表
	 * @author 许进
	 */
	public List<Role> getRoleList(Role role);
	/**
	 * 新增角色
	 * @author 许进
	 */
	public void saveRole(Role role);
	/**
	 * 修改角色
	 * @author 许进
	 */
	public void updateRole(Role role);
	/**
	 * 删除角色
	 * @author 许进
	 */
	public void delRole(long id);
	/**
	 * 根据角色ID取得角色对象
	 * @author 许进
	 */
	public Role getRoleById(long id);
	/**
	 * 取得角色数量
	 * @author 许进
	 */
	public int getRoleCount();
	/**
	 * 根据roleId查询出角色所拥有的权限
	 * @author 许进
	 */
	public List<RoleActionMap> getRoleActionMapByRoleId(long roleId);
	
	public void deleteRoleActionMapByRoleId(long roleId);
	
	public List<Action> getActionListByRoleId(long roleId);
	
	public List<Role> getAllRoleList();
	
	public Role getRoleByRoleNameOrRoleCodeOrId(Role role);
}

