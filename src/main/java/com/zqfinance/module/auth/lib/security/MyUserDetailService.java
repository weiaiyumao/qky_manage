package com.zqfinance.module.auth.lib.security;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

import com.zqfinance.module.sys.domain.Action;
import com.zqfinance.module.sys.domain.Role;
import com.zqfinance.module.sys.service.RoleService;
import com.zqfinance.module.sys.service.UserService;

public class MyUserDetailService implements UserDetailsService {
	private static Logger logger = LoggerFactory.getLogger(MyUserDetailService.class);
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		UserDetailImpl user = null;
		try{
			//根据用户取得名称取得用户角色
			List<Role> roleList = userService.getRoleListByUserName(username);
			Collection<GrantedAuthority> auths=new ArrayList<GrantedAuthority>();
			for(Role role : roleList){
				SimpleGrantedAuthority auth=new SimpleGrantedAuthority(role.getRoleCode());
				auths.add(auth);
			}	
			//根据用户名取得用户的对象
			com.zqfinance.module.sys.domain.User sysUser = userService.getUserByUserName(username);
			
			user = new UserDetailImpl(username,
					sysUser.getUserPass(), true, true, true, true, auths);
			
			List<String> actionUrlList= new ArrayList<String>();
			if(!CollectionUtils.isEmpty(roleList)){
				for(Role role : roleList){
					List<Action> actionList = roleService.getActionListByRoleId(role.getId());
					for(Action action : actionList){
						if(!actionUrlList.contains(action.getActionUrl())){
							actionUrlList.add(action.getActionUrl());
						}
					}
				}
			}
			user.setActionUrl(actionUrlList);
		}catch(Exception e){
			logger.error("Error in retrieving user");
			throw new UsernameNotFoundException("Error in retrieving user");
		}

		return user;
	}
	
}