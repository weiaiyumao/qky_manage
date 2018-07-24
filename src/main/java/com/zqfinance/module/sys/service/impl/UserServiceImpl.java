

package com.zqfinance.module.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zqfinance.module.sys.dao.UserMapper;
import com.zqfinance.module.sys.domain.Role;
import com.zqfinance.module.sys.domain.User;
import com.zqfinance.module.sys.domain.UserRoleMap;
import com.zqfinance.module.sys.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	/**
	 * 新增User对象
	 * (non-Javadoc)
	 * @see com.zqfinance.module.sys.service.UserService#addUser(com.zqfinance.module.sys.domain.User)
	 */
	public void addUser(User user) {
		userMapper.addUser(user);
		//新增用户和角色对应关系
		String[] roleIdArray = user.getRoleIds().split(",");
		List<UserRoleMap> userRoleList = new ArrayList<UserRoleMap>();
		for(String roleId : roleIdArray){
			UserRoleMap userRoleMap = new UserRoleMap();
			userRoleMap.setRoleId(Long.valueOf(roleId.trim()));
			userRoleMap.setUserId(user.getId());
			userRoleList.add(userRoleMap);
		}	
		userMapper.saveUserRoleMapList(userRoleList);
	}
	/**
	 * 查询user列表
	 * (non-Javadoc)
	 * @see com.zqfinance.module.sys.service.UserService#getUserList(com.zqfinance.module.sys.domain.User)
	 */
	public List<User> getUserList(User user) {
		return userMapper.getUserList(user);	
	}
	/**
	 * 用户修改
	 * (non-Javadoc)
	 * @see com.zqfinance.module.sys.service.UserService#updateUser(com.zqfinance.module.sys.domain.User)
	 */
	public void updateUser(User user) {
		//删除user role 对应关系
		userMapper.delUserRoleMapByUser(user.getId());
		String[] roleIdArray = user.getRoleIds().split(",");
		List<UserRoleMap> userRoleList = new ArrayList<UserRoleMap>();
		for(String roleId : roleIdArray){
			UserRoleMap userRoleMap = new UserRoleMap();
			userRoleMap.setRoleId(Long.valueOf(roleId.trim()));
			userRoleMap.setUserId(user.getId());
			userRoleList.add(userRoleMap);
		}
		
		userMapper.saveUserRoleMapList(userRoleList);
		userMapper.updateUser(user);
	}
	
	@Override
	public void updateUserPass(User user) {
		userMapper.updateUserPass(user);
	}
	/**
	 * 查询用户总数
	 * (non-Javadoc)
	 * @see com.zqfinance.module.sys.service.UserService#getUserCount(com.zqfinance.module.sys.domain.User)
	 */
	public int getUserCount(User user) {
		return userMapper.getUserCount(user);
		
	}
	@Override
	public User getUserById(long id) {	
		return userMapper.getUserById(id);
		
	}
	@Override
	public List<UserRoleMap> getRoleListByUserId(long userId) {
		return userMapper.getRoleListByUserId(userId);
		
	}
	

	public List<Role> getRoleListByUserName(String userName) {
		return userMapper.getRoleListByUserName(userName);
		
	}
	public User getUserByUserName(String userName) {
		return userMapper.getUserByUserName(userName);
		
	}
	
	public void delUser(long userId) {
		userMapper.delUserRoleMapByUser(userId);
		userMapper.delUserByUserId(userId);
	}
	@Override
	public List<User> getUserListByNameOrCardNo(User user) {
		return userMapper.getUserListByNameOrCardNo(user);	
	}
	@Override
	public User getUserByNameOrCardNoOrId(User user) {
		return userMapper.getUserByNameOrCardNoOrId(user);
	}
	
	
}

