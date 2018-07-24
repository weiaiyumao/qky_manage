package com.zqfinance.system.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zqfinance.module.sys.domain.Action;
import com.zqfinance.module.sys.domain.Role;
import com.zqfinance.module.sys.service.ActionService;
import com.zqfinance.system.config.ManageConfig;

public class RoleUtil {
	/**
	 * 权限加载
	 */
	public static void loadRole(){
		Map<String,List<Role>> actionRoleMap = new HashMap<String,List<Role>>();
		ActionService actionService = (ActionService)SpringUtil.getBean("actionService");
		List<Action> actionList = actionService.getAllActionList();
		for(Action action : actionList){
	    	List<Role> roleList = actionService.getRoleListByActionId(action.getId());
	    	actionRoleMap.put(String.valueOf(action.getId()), roleList);
		}
		ManageConfig.SERVLETCONTEXT_ACTIONLIST = actionList;
		ManageConfig.SERVLETCONTEXT_ACTION_ROLE_MAP = actionRoleMap;
	}
}
