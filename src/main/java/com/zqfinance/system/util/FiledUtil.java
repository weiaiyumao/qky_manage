package com.zqfinance.system.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.zqfinance.system.config.ParseConfig;

public class FiledUtil {
	
	public static Map<String,String> changeFiled(String leafKey,String leafValue) {
		Map<String,String> fieldMap = new HashMap<String,String>();
		String strValue = "";
		
		if(ParseConfig.PARSER_TIMECREATE.equals(leafKey)){
			BigDecimal db = new BigDecimal(leafValue);
			leafValue = db.toPlainString();

			fieldMap.put(leafKey, strValue);
		}
		else if(ParseConfig.PARSER_TIMEMODIFIED.equals(leafKey)){
			BigDecimal db = new BigDecimal(leafValue);
			leafValue = db.toPlainString();
			fieldMap.put(leafKey, strValue);
		}
		else if(ParseConfig.PARSER_SENDTIME.equalsIgnoreCase(leafValue)){
			BigDecimal db = new BigDecimal(leafValue);
			leafValue = db.toPlainString();
		    if(!"null".equals(leafValue)){
				fieldMap.put(leafValue, strValue);
		    }

		}
	
		return fieldMap;
	}
}
