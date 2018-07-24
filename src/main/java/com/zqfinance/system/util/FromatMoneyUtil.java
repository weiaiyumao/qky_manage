package com.zqfinance.system.util;

public class FromatMoneyUtil {
	
	public static final String FMT ="#,###.00";
	
	public static String fromatMoney(double money){
		if(money<=0.0099){
			return "0.00";
		}
		return  new java.text.DecimalFormat(FMT).format(money).toString();
	}
}
