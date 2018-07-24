

package com.zqfinance.module.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zqfinance.module.sys.domain.Role;
import com.zqfinance.module.sys.domain.User;
import com.zqfinance.module.sys.domain.UserRoleMap;

public interface UserMapper {
	/**
	 * 根据条件查询User列表
	 * @author 许进
	 */
	public List<User> getUserList(User user);
	
	/**
	 * 根据用户名和工号查询User列表
	 * @author 许进
	 */
	public List<User> getUserListByNameOrCardNo(User user);
	/**
	 * 查询user总数
	 * @author 许进
	 */
	public int getUserCount(User user);
	/**
	 * 用户新增
	 * @author 许进
	 */
	public void addUser(User user);
	/**
	 * 用户修改
	 * @author 许进
	 */
	public void updateUser(User user);
	
	public void updateUserPass(User user);
	
	public void saveUserRoleMapList(@Param("userRoleMapList")List<UserRoleMap> userRoleMapList);
	
	public User getUserById(long id);
	
	public List<UserRoleMap> getRoleListByUserId(long userId);
	
	public void delUserRoleMapByUser(long userId);
	
	public List<Role> getRoleListByUserName(String userName);
	
	public User getUserByUserName(String userName);
	
	public void delUserByUserId(long userId);
	public User getUserByNameOrCardNoOrId(User user);
}

