

package com.zqfinance.system.util;

public class MStringUtil {
	
	/**
	 * 判断字符串是否为空
	 * @author 许进
	 */
	public static boolean isNotEmpty(String str){
		if(str != null && !"".equals(str.trim()) && !"null".equals(str)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static boolean isEmpty(String str){
		return !isNotEmpty(str);
	}
}

