package com.zqfinance.module.auth.lib.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserDetailImpl extends User {

	
	private static final long serialVersionUID = -4091478734399445664L;
	
	private List<String> actionUrl = new ArrayList<String>();

	public UserDetailImpl(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
		
	}

	public List<String> getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(List<String> actionUrl) {
		this.actionUrl = actionUrl;
	}
	
	

}
