package com.zqfinance.system.service.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.zqfinance.system.common.DataMessage;
import com.zqfinance.system.common.DateMessageForObject;
import com.zqfinance.system.config.ConfigManager;
import com.zqfinance.system.config.ManageConfig;
import com.zqfinance.system.service.CommonService;
import com.zqfinance.system.service.SendRequestService;
import com.zqfinance.system.util.MessageUtil;

@Service("commonService")	
public class CommonServiceImpl implements CommonService {
	
	private static final Log log = LogFactory.getLog(CommonServiceImpl.class);

	@Autowired
	private SendRequestService requestTradingAndPayService;
	
//	/**
//	 * 取得银行
//	 * @return
//	 */
//	public Map<String,String> getPaybankMap(){
//		Map<String,String> paybankMap = new TreeMap<String,String>();
//		String returnStr = requestTradingAndPayService.sendRequest(ConfigManager.PAYBANK_FINDALL, new JSONObject());
//		if(StringUtils.isNotEmpty(returnStr)){
//			DateMessageForObject dataMessage= JSON.parseObject(returnStr,DateMessageForObject.class);
//			if(null != dataMessage && ManageConfig.DATE_MESSAGE_SUCCESS.equals(dataMessage.getResult())){
//				String bankStr = dataMessage.getData().toString();
//				List<JSONObject> jsonObjList = JSON.parseArray(bankStr, JSONObject.class);
//				for(JSONObject jsonObject : jsonObjList){
//					paybankMap.put(jsonObject.getString("bankcode"), jsonObject.getString("bankname"));
//				}
//			}
//		}
//		return paybankMap;
//	}
}
