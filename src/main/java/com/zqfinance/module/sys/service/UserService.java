

package com.zqfinance.module.sys.service;

import java.util.List;

import com.zqfinance.module.sys.domain.Role;
import com.zqfinance.module.sys.domain.User;
import com.zqfinance.module.sys.domain.UserRoleMap;

public interface UserService {
	public void addUser(User user);
	
	public void updateUser(User user);
	
	public void updateUserPass(User user);
	
	public List<User> getUserList(User user);
	
	public List<User> getUserListByNameOrCardNo(User user);
	
	public int getUserCount(User user);
	
	public User getUserById(long id);
	
	public List<UserRoleMap> getRoleListByUserId(long userId);
	
	public List<Role> getRoleListByUserName(String userName);
	
	public User getUserByUserName(String userName);
	
	public void delUser(long userId);
	
	public User getUserByNameOrCardNoOrId(User user);
}

