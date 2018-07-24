/**
 * 后台管理系统资源文件
 */

package com.zqfinance.system.config;

import java.util.List;
import java.util.Map;

import com.zqfinance.module.sys.domain.Action;
import com.zqfinance.module.sys.domain.Role;

public class ManageConfig {
	/** 请求正确状态码 **/
	public final static int STATUS_CODE_SUCCESS = 200;
	/** 请求错误状态码 **/
	public final static int STATUS_CODE_ERROR = 300;
	/** ajax请求后关闭当前页面 **/
	public final static String DWZ_CALLBACK_TYPE_CLOSECURRENT = "closeCurrent";
	/** ajaxtodo请求后刷新当前页面 **/
	public final static String DWZ_CALLBACK_TYPE_FORWARD = "forward";
	
	public final static String loginStatus_SUCCESS = "0";
	public final static String loginStatus_FAILURE = "1";
	
	/**数据放回result**/
	/**数据返回正常**/
	public final static String DATA_MESSAGE_MSG_SUCCESS = "0";
	/**数据返回异常**/
	public final static String DATA_MESSAGE_MSG_FAILURE = "1";
	
	/** 请求正确状态码 **/
	public final static String DATE_MESSAGE_SUCCESS = "0";
	/** 请求错误状态码 **/
	public final static String DATE_MESSAGE_FAIL = "1";
	
	/** 登陆的管理员 对象(获取的时候强转User对象)**/
	public final static String LOGIN_USER = "user";
	
	/**customer name in session**/
	public final static String SESSION_CUSTOMER= "customer";

	/**customerId name in session**/
	public final static String SESSION_CUSTOMER_ID= "customerId";
	
	public static List<Action> SERVLETCONTEXT_ACTIONLIST = null;
	
	public static Map<String,List<Role>> SERVLETCONTEXT_ACTION_ROLE_MAP = null;
	
	public  static final  String HASACCOUNTSOURCE = "1";

}

