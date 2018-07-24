package com.zqfinance.system.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.zqfinance.system.common.DataMessage;
import com.zqfinance.system.common.DataPageMessage;
import com.zqfinance.system.common.DatePageSumMoneyMessage;
import com.zqfinance.system.common.Page;
import com.zqfinance.system.config.ManageConfig;


public class MessageUtil {
	private static transient final Log log = LogFactory.getLog("MessageUtil");
	
	public static String createHttpPost(String url, JSONObject jsonObject) {

		Protocol myhttps = new Protocol("https", new HttpClientSSLSocketFactory(), 443);  
        Protocol.registerProtocol("https", myhttps);  
        
		PostMethod post = new PostMethod(url);
		try {
			@SuppressWarnings("rawtypes")
			Iterator  iterator = jsonObject.keys();
			// 设置参数
			while(iterator.hasNext()){
				String key = (String) iterator.next();  
				String value = jsonObject.getString(key); 
				post.addParameter(new NameValuePair(key,value));
			}

			HttpClient client = new HttpClient();
			client.getParams().setContentCharset("UTF-8");
			client.getHttpConnectionManager().getParams().setConnectionTimeout(60000); 
			client.getHttpConnectionManager().getParams().setSoTimeout(60000);
			
			int result = client.executeMethod(post);
			log.debug("createHttpPost, url:" + url);
			log.info("Response status code: " + result);
			byte[] responseBody = post.getResponseBody();
			// 得到返回数据
			String resultStr = new String(responseBody, "UTF-8");
			log.info("createHttpPost, result:"+resultStr);
			return resultStr;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("createHttpPost, 后台数据流请求异常, url:" + url);
			log.error("createHttpPost, 后台数据流请求异常, exception:", e.getCause());
		} finally {
			post.releaseConnection();
		}

		return "";
	}
	
	/**
	 * 查询返回数据列表信息
	 * @author 许进
	 */
	@SuppressWarnings("rawtypes")
	public static DataPageMessage parseDatePageMessage(String jsonString){
		DataPageMessage dataMessage = new DataPageMessage();
		try{
			if(MStringUtil.isNotEmpty(jsonString)){
				JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(jsonString, JSONObject.class);
				List<Map<String,String>> contentList= new ArrayList<Map<String,String>>();
				Iterator it = jsonObject.keys();
			    while (it.hasNext()) {  
		            String key = (String) it.next();  
		            String value = jsonObject.getString(key);
		            if(!value.equals("null")){
		            	 if("data".equals(key)){            	
				            	JSONObject innerJson = com.alibaba.fastjson.JSON.parseObject(value, JSONObject.class);
				            	Iterator innerIt = innerJson.keys();
				            	while(innerIt.hasNext()){           		
				            		String innerKey = (String) innerIt.next();   		
				            		if("content".equals(innerKey)){         			
				            			JSONArray array = innerJson.getJSONArray(innerKey);
				            			for(int i=0;i<array.size();i++){     
				            				Map<String,String> contentMap = new HashMap<String,String>();
				            				 JSONObject arrayObj = array.getJSONObject(i);
				            				 Iterator leafIt = arrayObj.keys();
				            				 while(leafIt.hasNext()){
				            					 String leafKey = (String)leafIt.next();
				            					 String leafValue = arrayObj.getString(leafKey);
				            					 if(!"null".equals(leafValue)){
					            					 contentMap.put(leafKey, leafValue);
				            					 }
				            				 }
				            				 contentList.add(contentMap);
				            			}
				            		}
				            	}
				            	dataMessage.setContentList(contentList);
				            }else if("result".equals(key)){
				            	dataMessage.setResult(value);
				            } else if("msg".equals(key)){
				            	dataMessage.setMsg(value);
				            }
		            }
		        }
			    getRestPage(jsonString,dataMessage);
			}else{
				dataMessage.setMsg("数据返回错误");
				dataMessage.setResult(ManageConfig.DATA_MESSAGE_MSG_FAILURE);
			}
		}catch(Exception e){
			log.error("数据解析异常", e);
			dataMessage.setMsg("数据返回错误");
			dataMessage.setResult(ManageConfig.DATA_MESSAGE_MSG_FAILURE);
		}
		
		return dataMessage;
	}
	
	/**
	 * 查询返回数据列表信息
	 * @author 许进
	 */
	@SuppressWarnings("rawtypes")
	public static DataPageMessage parseDateSumMoneyPageMessage(String jsonString){
		DatePageSumMoneyMessage dataMessage = new DatePageSumMoneyMessage();
		try{
			if(MStringUtil.isNotEmpty(jsonString)){
				JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(jsonString, JSONObject.class);
				List<Map<String,String>> contentList= new ArrayList<Map<String,String>>();
				Iterator it = jsonObject.keys();
			    while (it.hasNext()) {  
		            String key = (String) it.next();  
		            String value = jsonObject.getString(key);
		            if(!value.equals("null")){
		            	 if("data".equals(key)){
		            		 	BigDecimal sumMoney=BigDecimal.ZERO;
				            	JSONObject innerJson = com.alibaba.fastjson.JSON.parseObject(value, JSONObject.class);
				            	Iterator innerIt = innerJson.keys();
				            	while(innerIt.hasNext()){           		
				            		String innerKey = (String) innerIt.next();   		
				            		if("content".equals(innerKey)){         			
				            			JSONArray array = innerJson.getJSONArray(innerKey);
				            			for(int i=0;i<array.size();i++){     
				            				Map<String,String> contentMap = new HashMap<String,String>();
				            				 JSONObject arrayObj = array.getJSONObject(i);
				            				 Iterator leafIt = arrayObj.keys();
				            				 while(leafIt.hasNext()){
				            					 String leafKey = (String)leafIt.next();
				            					 String leafValue = arrayObj.getString(leafKey);
				            					 if(!"null".equals(leafValue)){
					            					 contentMap.put(leafKey, leafValue);
				            					 }
				            					 if("money".equals(leafKey)){
				            						 sumMoney=sumMoney.add(new BigDecimal(leafValue));
				            					 }

				            				 }
				            				 contentList.add(contentMap);
				            			}
				            		}
				            	}
				            	dataMessage.setSumMoney(sumMoney);
				            	dataMessage.setContentList(contentList);
				            }else if("result".equals(key)){
				            	dataMessage.setResult(value);
				            } else if("msg".equals(key)){
				            	dataMessage.setMsg(value);
				            }
		            }
		        }
			    getRestPage(jsonString,dataMessage);
			}else{
				dataMessage.setMsg("数据返回错误");
				dataMessage.setResult(ManageConfig.DATA_MESSAGE_MSG_FAILURE);
			}
		}catch(Exception e){
			log.error("数据解析异常", e);
			dataMessage.setMsg("数据返回错误");
			dataMessage.setResult(ManageConfig.DATA_MESSAGE_MSG_FAILURE);
		}
		
		return dataMessage;
	}
	
	/**
	 * 查询返回数据列表信息
	 * @author 许进
	 */
	@SuppressWarnings("rawtypes")
	public static DataPageMessage parseDatePageMessageForP2b(String jsonString){
		DataPageMessage dataMessage = new DataPageMessage();
		try{
			if(MStringUtil.isNotEmpty(jsonString)){
				JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(jsonString, JSONObject.class);
				List<Map<String,String>> contentList= new ArrayList<Map<String,String>>();
				Iterator it = jsonObject.keys();
				Page page = new Page();
			    while (it.hasNext()) {  
		            String key = (String) it.next();  
		            String value = jsonObject.getString(key);
		            if(!value.equals("null")){
						if ("data".equals(key)) {
							JSONArray array = jsonObject.getJSONArray(key);
							for (int i = 0; i < array.size(); i++) {
								Map<String, String> contentMap = new HashMap<String, String>();
								JSONObject arrayObj = array.getJSONObject(i);
								Iterator leafIt = arrayObj.keys();
								while (leafIt.hasNext()) {
									String leafKey = (String) leafIt.next();
									String leafValue = arrayObj
											.getString(leafKey);
									if (!"null".equals(leafValue)) {
										contentMap.put(leafKey, leafValue);
									}
								}
								contentList.add(contentMap);
							}
							dataMessage.setContentList(contentList);
						} else if ("result".equals(key)) {
							dataMessage.setResult(value);
						} else if ("msg".equals(key)) {
							dataMessage.setMsg(value);
						} else if ("pageSize".equals(key)) {
							page.setNumPerPage(Integer.parseInt(value));
						} else if ("pageNumber".equals(key)) {
							page.setPageNum(Integer.parseInt(value));
						} else if ("totalCount".equals(key)) {
							page.setTotalCount(Integer.parseInt(value));
						}
						dataMessage.setPage(page);
		            }
		        }
			}else{
				dataMessage.setMsg("数据返回错误");
				dataMessage.setResult(ManageConfig.DATA_MESSAGE_MSG_FAILURE);
			}
		}catch(Exception e){
			log.error("数据解析异常", e);
			dataMessage.setMsg("数据返回错误");
			dataMessage.setResult(ManageConfig.DATA_MESSAGE_MSG_FAILURE);
		}
		
		return dataMessage;
	}
	
	@SuppressWarnings("rawtypes")
	public static DataPageMessage getRestPage(String returnJson,DataPageMessage dataMessage){
		Page page = new Page();
		JSONObject jsonObject = JSONObject.fromObject(returnJson);
		Iterator it = jsonObject.keys();
		while(it.hasNext()){
			String key = (String) it.next();  
			if("data".equals(key)){
			  	String value = jsonObject.getString(key); 
			  	if(MStringUtil.isEmpty(value)) {
            		page.setNumPerPage(0);
            		page.setPageNum(1);
            		page.setTotalCount(0);
			  		break;
			  	}
			  	
            	JSONObject innerJson = JSONObject.fromObject(value);
            	Iterator innerIt = innerJson.keys();
            	while(innerIt.hasNext()){ 
            		String innerKey = (String) innerIt.next(); 
            		if("size".equals(innerKey)){
            			page.setNumPerPage(innerJson.getInt(innerKey));
            		}
            		else if("number".equals(innerKey)){
            			page.setPageNum(innerJson.getInt(innerKey)+1);
            		}
            		else if("totalElements".equals(innerKey)){
            			page.setTotalCount(innerJson.getInt(innerKey));
            		}
            	}
			}
		}
		dataMessage.setPage(page);
		return dataMessage;
	}
	
	public static  DataMessage parseDataMessageOfString(String jsonString) {
		DataMessage dataMessage = new DataMessage();
		try{
			Map<String,String> dataMap = new HashMap<String,String>();
			if(StringUtils.isNotEmpty(jsonString)){
				JSONObject jsonObject = JSON.parseObject(jsonString, JSONObject.class);
				Iterator it = jsonObject.keys();
				while(it.hasNext()){
					String key = (String) it.next();
					String value = jsonObject.getString(key);
					if("result".equals(key)){
						dataMessage.setResult(value);
					}else if("msg".equals(key)){
						dataMessage.setMsg(value);
					}else if("data".equals(key) && null!=value){
						if(MStringUtil.isNotEmpty(value)){
							dataMap.put("sumMoney", value);
						}
						dataMessage.setData(dataMap);
					}
				}
			}else{
				dataMessage.setMsg("数据返回异常");
				dataMessage.setResult(ManageConfig.DATA_MESSAGE_MSG_FAILURE);
			}
		}catch(Exception e){		
			dataMessage.setMsg("数据返回异常");
			dataMessage.setResult(ManageConfig.DATA_MESSAGE_MSG_FAILURE);
		}
		return dataMessage;
	}
	
	public static  DataMessage parseDataMessage(String jsonString){
		DataMessage dataMessage = new DataMessage();
		Map<String,String> dataMap = new HashMap<String,String>();
		if(StringUtils.isNotEmpty(jsonString)){
			JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(jsonString, JSONObject.class);
			@SuppressWarnings("rawtypes")
			Iterator it = jsonObject.keys();
			while(it.hasNext()){
				String key = (String) it.next();
				String value = jsonObject.getString(key);
				if("result".equals(key)){
					dataMessage.setResult(value);
				}else if("msg".equals(key)){
					dataMessage.setMsg(value);
				}else if("data".equals(key)){
					if(MStringUtil.isNotEmpty(value)){
						JSONObject jobject = com.alibaba.fastjson.JSON.parseObject(value, JSONObject.class);
						@SuppressWarnings("rawtypes")
						Iterator innerIt = jobject.keys();
						while(innerIt.hasNext()){
							String innerKey = (String)innerIt.next();
							String innerValue = jobject.getString(innerKey);
							if(MStringUtil.isNotEmpty(innerValue)){
								dataMap.put(innerKey, innerValue);
							}					
						}
						dataMessage.setData(dataMap);
					}
				}
			}
		}else{
			dataMessage.setMsg("数据返回异常");
			dataMessage.setResult(ManageConfig.DATA_MESSAGE_MSG_FAILURE);
		}

		return dataMessage;
	}

	public static  DataMessage parseDataMessageForP2b(String jsonString){
		DataMessage dataMessage = new DataMessage();
		Map<String,String> dataMap = new HashMap<String,String>();
		if(StringUtils.isNotEmpty(jsonString)){
			JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(jsonString, JSONObject.class);
			@SuppressWarnings("rawtypes")
			Iterator it = jsonObject.keys();
			while(it.hasNext()){
				String key = (String) it.next();
				String value = jsonObject.getString(key);
				if("result".equals(key)){
					dataMessage.setResult(value);
				}else if("msg".equals(key)){
					dataMessage.setMsg(value);
				}else if("data".equals(key)){
					if(MStringUtil.isNotEmpty(value)){
						//去掉 []
						value = value.substring(1, value.length()-1);
						if(org.apache.commons.lang.StringUtils.isNotBlank(value.trim())){
							JSONObject jobject = com.alibaba.fastjson.JSON.parseObject(value, JSONObject.class);
							@SuppressWarnings("rawtypes")
							Iterator innerIt = jobject.keys();
							while(innerIt.hasNext()){
								String innerKey = (String)innerIt.next();
								String innerValue = jobject.getString(innerKey);
								if(MStringUtil.isNotEmpty(innerValue)){
									dataMap.put(innerKey, innerValue);
								}					
							}
						}
						dataMessage.setData(dataMap);
					}
				}
			}
		}else{
			dataMessage.setMsg("数据返回异常");
			dataMessage.setResult(ManageConfig.DATA_MESSAGE_MSG_FAILURE);
		}

		return dataMessage;
	}
	
	/**
	 * 查询返回数据列表信息
	 * @author 许进
	 */
	@SuppressWarnings("rawtypes")
	public static DataPageMessage parseDateListMessage(String jsonString){
		DataPageMessage datapagemessage = new DataPageMessage();
		if(MStringUtil.isNotEmpty(jsonString)){
			JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(jsonString, JSONObject.class);
			List<Map<String, String>> contentList = new ArrayList<Map<String, String>>();
			Iterator it = jsonObject.keys();
		    while (it.hasNext()) {  
	            String key = (String) it.next();  
	            String value = jsonObject.getString(key);
	            if("data".equals(key)){    
	            	if(MStringUtil.isNotEmpty(value)){
	            		JSONArray jsonArray = JSONArray.fromObject(value);
		            	for(int i=0;i<jsonArray.size();i++){
							JSONObject jsonobject = jsonArray.getJSONObject(i);
							Iterator leafIt = jsonobject.keys();
							Map<String, String> contentMap = new HashMap<String, String>();
							while (leafIt.hasNext()) {
								String leafKey = (String) leafIt.next();
								String leafValue=null;
								if(leafKey.equals("money")){
									leafValue = new BigDecimal(jsonobject.getString(leafKey)).stripTrailingZeros().toPlainString();
								}else{
									leafValue= jsonobject.getString(leafKey);
								}
								contentMap.put(leafKey, leafValue);
							}
							contentList.add(contentMap);
		            	}
	            	}
	            	datapagemessage.setContentList(contentList);
	            }else if("result".equals(key)){
	            	datapagemessage.setResult(value);
	            } else if("msg".equals(key)){
	            	datapagemessage.setMsg(value);
	            }
	        }
		}else{
			datapagemessage.setMsg("数据返回错误");
			datapagemessage.setResult(ManageConfig.DATA_MESSAGE_MSG_FAILURE);
		}
		return datapagemessage;
	}
	public static void main(String[] args) {
		JSONObject jobject2 = com.alibaba.fastjson.JSON.parseObject("{\"customerid\":713,\"email\":null,\"idcard\":\"110101201401017996\",\"isautologin\":null,\"mobile\":\"15900000015\",\"name\":\"借款人勿删\"}", JSONObject.class);
		System.out.println(jobject2.get("customerid"));
		JSONObject jobject = com.alibaba.fastjson.JSON.parseObject("[{\"customerid\":713,\"email\":null,\"idcard\":\"110101201401017996\",\"isautologin\":null,\"mobile\":\"15900000015\",\"name\":\"借款人勿删\"}]", JSONObject.class);
		System.out.println(jobject.get("customerid"));
		
	}
}

