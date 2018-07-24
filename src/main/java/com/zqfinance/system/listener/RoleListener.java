package com.zqfinance.system.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zqfinance.system.config.ManageConfig;
import com.zqfinance.system.util.RoleUtil;
/**
 * 将用户权限与功能的对应关系存在application作用域中，每次系统启动的时候进行处理
 * @author xujin
 *
 */
public class RoleListener implements ServletContextListener {
	
	private static final  Log log = LogFactory.getLog(RoleListener.class);
	
	public void contextDestroyed(ServletContextEvent arg0) {
		ManageConfig.SERVLETCONTEXT_ACTIONLIST = null;
		ManageConfig.SERVLETCONTEXT_ACTION_ROLE_MAP = null;
	}

	public void contextInitialized(ServletContextEvent event) {
		log.info("加载用户权限数据开始。。。");
		RoleUtil.loadRole();
		log.info("数据权限数据加载完成");
		
	}


}
