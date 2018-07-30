/**
 * 此类在初始化时，应该取到所有资源及其对应角色的定义
 * @author 许进
 */
package com.zqfinance.module.auth.lib.security;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.zqfinance.module.sys.domain.Action;
import com.zqfinance.module.sys.domain.Role;
import com.zqfinance.module.sys.service.ActionService;
import com.zqfinance.module.sys.service.RoleService;
import com.zqfinance.system.config.ManageConfig;


public class MyInvocationSecurityMetadataSource
		implements FilterInvocationSecurityMetadataSource {
	
		private PathMatcher urlMatcher = new AntPathMatcher();
		@Autowired
	    private RoleService roleService; 
		@Autowired
		private ActionService actionService;
		
	    @Override  
	    public Collection<ConfigAttribute> getAttributes(Object object)  
	            throws IllegalArgumentException { 
	    	
	        String url = ((FilterInvocation) object).getRequestUrl();  
	        int firstQuestionMarkIndex = url.indexOf("?");  
	        if (firstQuestionMarkIndex != -1) {  
	            url = url.substring(0, firstQuestionMarkIndex);  
	        }  
	        Map<String,List<ConfigAttribute>> configMap = new HashMap<String,List<ConfigAttribute>>();
	        
	        //取出所有的action
	        List<Action> actionList = ManageConfig.SERVLETCONTEXT_ACTIONLIST ;
	        for(Action action : actionList){
	        	//根据Action取得所有的Role
	        	List<Role> roleList = ManageConfig.SERVLETCONTEXT_ACTION_ROLE_MAP.get(String.valueOf(action.getId()));
	        	List<ConfigAttribute> result = new ArrayList<ConfigAttribute>();
	        	for(Role role :roleList ){
	        		ConfigAttribute attribute = new SecurityConfig(role.getRoleCode()); 
	        		result.add(attribute);
	        	}
	        	configMap.put(action.getActionUrl(), result);
	        }
	        Iterator<String> ite = configMap.keySet().iterator();
			while (ite.hasNext()) {
				String resURL = ite.next();
				if (urlMatcher.match(url, resURL)) {
					return configMap.get(resURL);
				}
			}
			if (!url.contains("login.htm")||!url.endsWith("js")||!url.endsWith("xml")||!url.contains("loginpage.jsp")) {
				UserDetails user = UserUtil.getUserDetail();
				if (user == null) {
					throw new UsernameNotFoundException("未登录不能访问！");
				}
			}
			return null;
	    }  
	  
	    @Override  
	    public Collection<ConfigAttribute> getAllConfigAttributes() {  
	        // TODO Auto-generated method stub  
	        return null;  
	    }  
	  
	    @Override  
	    public boolean supports(Class<?> clazz) {  
	        // TODO Auto-generated method stub  
	        return true;  
	    }  
}