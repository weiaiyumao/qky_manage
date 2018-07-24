package com.zqfinance.module.auth.lib.security;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.zqfinance.module.auth.service.InitIndexService;
import com.zqfinance.module.sys.domain.Loginfo;
import com.zqfinance.module.sys.service.UserService;
import com.zqfinance.module.sys.service.LoginfoService;
import com.zqfinance.system.config.ManageConfig;
import com.zqfinance.system.service.SendRequestService;

/**
 * @Description: 用户验证成功处理
 * @author 许进
 * 
 */
public class UserAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginfoService LoginfoService;
	
	@Autowired
	private InitIndexService initIndexService;
	/**
	 * 请求统一入口
	 */
	@Autowired
	protected SendRequestService sendRequestService;

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
			//处理登录成功后相关内容
			UserDetailImpl user = null;
			Object o = authentication.getPrincipal();
			if (o != null) {
				user = (UserDetailImpl) o;
				//根据用户名取得用户的对象
				com.zqfinance.module.sys.domain.User sysUser = userService.getUserByUserName(user.getUsername());				
				HttpSession session =  request.getSession(false);
				if(null != session){
					session.setAttribute(ManageConfig.LOGIN_USER, sysUser);
				}
				//保存成功登陆日志
				Loginfo loginfo = new Loginfo();
				loginfo.setUserId(sysUser.getId());
				loginfo.setUserName(sysUser.getUserName());
				loginfo.setRequestIp(getIpAddr(request));
				loginfo.setModuleName("系统管理员登陆");
				loginfo.setLoginStatus(ManageConfig.loginStatus_SUCCESS);
				LoginfoService.addLoginfo(loginfo);
				//加载首页显示
				initIndexService.initIndex(request, response,user.getActionUrl());
			}
			
			handle(request, response, authentication);
	}
	
	/**
	 * 获取登陆客户端IP
	 * @param request
	 * @return
	 */
	private String getIpAddr(HttpServletRequest request) {
	     String ipAddress = null;
	     //ipAddress = this.getRequest().getRemoteAddr();
	     ipAddress = request.getHeader("x-forwarded-for");
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
	      ipAddress = request.getHeader("Proxy-Client-IP");
	     }
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
	         ipAddress = request.getHeader("WL-Proxy-Client-IP");
	     }
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
	      ipAddress = request.getRemoteAddr();
	      if(ipAddress.equals("127.0.0.1")){
	       //根据网卡取本机配置的IP
	       InetAddress inet=null;
	    try {
	     inet = InetAddress.getLocalHost();
	    } catch (UnknownHostException e) {
	     e.printStackTrace();
	    }
	    ipAddress= inet.getHostAddress();
	      }
	         
	     }
	     //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
	     if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
	         if(ipAddress.indexOf(",")>0){
	             ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
	         }
	     }
	     return ipAddress; 
	  }
}



