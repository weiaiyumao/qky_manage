package com.zqfinance.system.util;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.zqfinance.system.common.DataPageMessage;
/**
 * 获取spring bean公共类
 * @author xujin
 *
 */
@Component
public class SpringUtil implements ApplicationContextAware {

	private static  ApplicationContext applicationContext;
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringUtil.applicationContext = applicationContext;
	}
	
	public static Object getBean(String name){
		return applicationContext.getBean(name);
	}
	
	public static String formatNum(String num,int index){
		if(null == num){
			return null;
		}
		String str = "";
		for (int i = 0; i < num.length()-(index*2); i++) {
			str = str +"*";
		}
		return num.substring(0, index)+str+num.substring(num.length()-index, num.length());
	}
	
	public static String formatName(String name){
		if(null == name){
			return null;
		}
		String str = "";
		for (int i = 0; i < name.length()-1; i++) {
			str = str +"*";
		}
		return name.substring(0, 1)+str;
	}
	
	
	public static void formatDateByDataPageMessage(DataPageMessage datapagemessage){
		if(null!=datapagemessage){
			if(null!=datapagemessage.getContentList()){
				List<Map<String,String>> dateMap = datapagemessage.getContentList();
				for (Map<String, String> dateInMap : dateMap) {
					if (dateInMap.containsKey("name") && MStringUtil.isNotEmpty(dateInMap.get("name"))) {
						dateInMap.put("name", formatName(dateInMap.get("name")));
					}
					if (dateInMap.containsKey("mobile") && MStringUtil.isNotEmpty(dateInMap.get("mobile"))) {
						dateInMap.put("mobile", formatNum(dateInMap.get("mobile"),3));
					}
					if (dateInMap.containsKey("idcard") && MStringUtil.isNotEmpty(dateInMap.get("idcard"))) {
						dateInMap.put("idcard", formatNum(dateInMap.get("idcard"),4));
					}
					if (dateInMap.containsKey("cardNo") && MStringUtil.isNotEmpty(dateInMap.get("cardNo"))) {
						dateInMap.put("cardNo", formatNum(dateInMap.get("cardNo"),4));
					}
					if (dateInMap.containsKey("acctName") && MStringUtil.isNotEmpty(dateInMap.get("acctName"))) {
						dateInMap.put("acctName", formatName(dateInMap.get("acctName")));
					}
					if (dateInMap.containsKey("idNo") && MStringUtil.isNotEmpty(dateInMap.get("idNo"))) {
						dateInMap.put("idNo", formatNum(dateInMap.get("idNo"),4));
					}
					if (dateInMap.containsKey("cardNo") && MStringUtil.isNotEmpty(dateInMap.get("cardNo"))) {
						dateInMap.put("cardNo", formatNum(dateInMap.get("cardNo"),4));
					}
					if (dateInMap.containsKey("cardid") && MStringUtil.isNotEmpty(dateInMap.get("cardid"))) {
						dateInMap.put("cardid", formatNum(dateInMap.get("cardid"),4));
					}
					
				}
			}
		}
	}
}
