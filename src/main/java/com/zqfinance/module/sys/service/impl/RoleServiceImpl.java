package com.zqfinance.module.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zqfinance.module.sys.dao.RoleMapper;
import com.zqfinance.module.sys.domain.Action;
import com.zqfinance.module.sys.domain.Role;
import com.zqfinance.module.sys.domain.RoleActionMap;
import com.zqfinance.module.sys.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public List<Role> getRoleList(Role role) {
		return roleMapper.getRoleList(role);		
	}

	/**
	 * 新增角色
	 */
	public void saveRole(Role role) {
		roleMapper.saveRole(role);
		//保存role和action的对应关系
		String[] actionIds = role.getRoleActions().split(",");
		List<RoleActionMap> roleActionList = new ArrayList<RoleActionMap>();
		for(String actionId : actionIds){
			RoleActionMap roleActionMap = new RoleActionMap();
			roleActionMap.setRoleId(role.getId());
			roleActionMap.setActionId(Long.valueOf(actionId.trim()));
			roleActionList.add(roleActionMap);
		}
		roleMapper.saveRoleActionMap(roleActionList);
	}

	/**
	 * 修改角色
	 */
	public void updateRole(Role role) {	
		roleMapper.updateRole(role);
		//删除role和action关系
		roleMapper.delRoleActionByRoleId(role.getId());
		//保存role和action的对应关系
		String[] actionIds = role.getRoleActions().split(",");
		List<RoleActionMap> roleActionList = new ArrayList<RoleActionMap>();
		for(String actionId : actionIds){
			RoleActionMap roleActionMap = new RoleActionMap();
			roleActionMap.setRoleId(role.getId());
			roleActionMap.setActionId(Long.valueOf(actionId.trim()));
			roleActionList.add(roleActionMap);
		}
		roleMapper.saveRoleActionMap(roleActionList);
	}

	/**
	 * 删除角色
	 */
	public void delRole(long id) {
		roleMapper.delRole(id);
		//删除角色和功能对应关系
		roleMapper.delRoleActionByRoleId(id);
		//删除用户和角色对应关系
		roleMapper.delRoleUserByRoleId(id);
	}

	@Override
	public Role getRoleById(long id) {
		return roleMapper.getRoleById(id);
	}

	@Override
	public int getRoleCount() {
		return roleMapper.getRoleCount();		
	}

	@Override
	public List<RoleActionMap> getRoleActionMapByRoleId(long roleId) {
		return roleMapper.getRoleActionMapByRoleId(roleId);
		
	}

	@Override
	public void deleteRoleActionMapByRoleId(long roleId) {
		roleMapper.delRoleActionByRoleId(roleId);
	}

	@Override
	public List<Action> getActionListByRoleId(long roleId) {		
		return roleMapper.getActionListByRoleId(roleId);		
	}

	@Override
	public List<Role> getAllRoleList() {
		return roleMapper.getAllRoleList();
		
	}

	@Override
	public Role getRoleByRoleNameOrRoleCodeOrId(Role role) {
		return roleMapper.getRoleByRoleNameOrRoleCodeOrId(role);
	}

}

