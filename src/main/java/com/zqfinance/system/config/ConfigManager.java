/**
 * 读取配置文件
 */

package com.zqfinance.system.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;


public class ConfigManager {
	private static final Log log = LogFactory
			.getLog(ConfigManager.class);

	private static Properties props = new Properties();
	
	public static String FILE_SEPARATOR = "/";

	private ConfigManager() {
	}

	static {
		Resource resource = new ClassPathResource("/app.properties");
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("解析url.properties出现异常", e);
		}
		
		String sp = System.getProperty("file.separator");
		if(null != sp) {
			if("\\".equals(sp)) {
				sp = "\\\\";
			}
			FILE_SEPARATOR = sp;
		}
	}

	
	public static final String CFG_LCTRADING_PRIKEY = props.getProperty("LCTRADING_PRIKEY");  // 签名私钥
	public static final String CFG_LCTRADING_SIGNKEY = props.getProperty("LCTRADING_SIGNKEY");  // 签名密码
	public static final String CFG_LCTRADING_SIGNPARAM = props.getProperty("LCTRADING_SIGNPARAM");    // 验签请求参数名
	
	
	public static final String CFG_P2B_PRIKEY = props.getProperty("P2B_PRIKEY");  // 签名私钥
	public static final String CFG_P2B_SIGNKEY = props.getProperty("P2B_SIGNKEY");  // 签名密码
	public static final String CFG_P2B_SIGNPARAM = props.getProperty("P2B_SIGNPARAM");    // 验签请求参数名
	public static final String CFG_P2B_COOKIE_SID = props.getProperty("P2B_COOKIE_SID");


}

