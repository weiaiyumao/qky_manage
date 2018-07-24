

package com.zqfinance.module.auth.lib.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UserUtil {
	private final static Logger logger = LoggerFactory.getLogger(UserUtil.class);

	public static User getUserDetail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetails) {
				return (User) principal;
			}
		}
		logger.debug("获取用户信息失败", new Exception());
		return null;
	}
	
	public static void main(String [] args){
		Md5PasswordEncoder md5e = new Md5PasswordEncoder();
	
		System.out.println(md5e.encodePassword("123456", "wangjuan"));
	}
}
