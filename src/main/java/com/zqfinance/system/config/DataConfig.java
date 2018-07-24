package com.zqfinance.system.config;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author xuj
 *
 */
public class DataConfig {
	
	/**
	 * 银行编码和名称对应关系
	 */
	public static final Map<String,String> BANK_MAP = new HashMap<String,String>();
	static {
		BANK_MAP.put("01020000", "工商银行");
		BANK_MAP.put("01030000", "农业银行");
		BANK_MAP.put("01040000", "中国银行");
		BANK_MAP.put("01050000", "建设银行");
		BANK_MAP.put("03080000", "招商银行");
		BANK_MAP.put("03100000", "浦发银行");
		BANK_MAP.put("03030000", "光大银行");
		BANK_MAP.put("03070000", "平安银行");
		BANK_MAP.put("03040000", "华夏银行");
		BANK_MAP.put("03090000", "兴业银行");
		BANK_MAP.put("03020000", "中信银行");
		BANK_MAP.put("01000000", "储蓄银行");
	}
	
	/**
	 * 提现状态
	 * 0:成功，1:失败，2:未处理，3:处理中
	 */
	public static final Map<String,String> OUTMONEY_FLAG  = new TreeMap<String,String>();
	static{
		OUTMONEY_FLAG.put("0","待处理");
		OUTMONEY_FLAG.put("1","成功");
		OUTMONEY_FLAG.put("2","失败");
		OUTMONEY_FLAG.put("3","审核通过");
		OUTMONEY_FLAG.put("4","连连放款");
	}
	
}
