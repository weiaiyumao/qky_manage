/**
 * gson单例
 * @author 许进
 */

package com.zqfinance.system.util;

import com.google.gson.Gson;

public class GsonSingleton {
	
	private static  Gson gson;
	
	private GsonSingleton(){}
	
	public static synchronized Gson getGson(){
		if(null == gson){
			gson = new Gson();
		}
		return gson;
	}
	
}

