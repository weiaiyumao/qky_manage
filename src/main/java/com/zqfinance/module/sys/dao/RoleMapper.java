/**
 * 角色 db
 * @author 许进
 */

package com.zqfinance.module.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zqfinance.module.sys.domain.Action;
import com.zqfinance.module.sys.domain.Role;
import com.zqfinance.module.sys.domain.RoleActionMap;

public interface RoleMapper {
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
	 * 保存角色和功能对应关系
	 * @author 许进
	 */
	public void saveRoleActionMap(@Param("roleActionMapList")List<RoleActionMap> roleActionMapList);
	
	/**
	 * 根据roleId查询出角色所拥有的权限
	 * @author 许进
	 */
	public List<RoleActionMap> getRoleActionMapByRoleId(long roleId);
	/**
	 * 删除角色和功能对应关系
	 * @author 许进
	 */
	public void delRoleActionByRoleId(long roleId);
	
	public List<Action> getActionListByRoleId(long roleId);
	
	public List<Role> getAllRoleList();
	
	public void delRoleUserByRoleId(long roleId);
	
	public Role getRoleByRoleNameOrRoleCodeOrId(Role role);
}

