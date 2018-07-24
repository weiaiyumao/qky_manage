package com.zqfinance.module.trading.controller;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zqfinance.module.sys.domain.Loginfo;
import com.zqfinance.system.common.DataMessage;
import com.zqfinance.system.common.DataPageMessage;
import com.zqfinance.system.config.ConfigManager;
import com.zqfinance.system.config.ManageConfig;
import com.zqfinance.system.controller.DwzAjaxController;
import com.zqfinance.system.util.DateHelper;
import com.zqfinance.system.util.DateUtil;
import com.zqfinance.system.util.DictionaryMap;
import com.zqfinance.system.util.FromatMoneyUtil;
import com.zqfinance.system.util.MStringUtil;
import com.zqfinance.system.util.MessageUtil;
import com.zqfinance.system.util.RequestUtil;
import com.zqfinance.system.util.SpringUtil;
@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/productOrder")
public class InvestproductOrderController extends DwzAjaxController{
	
	/**
	 * 获得理财产品列表（管理员的）
	 * @param request
	 * @return
	 */
	@RequestMapping("/getProductOrderList")
	public ModelAndView getProductList(HttpServletRequest request){	
		
		HttpSession session =  request.getSession(false);
		com.zqfinance.module.sys.domain.User user = null;
		if(null != session){
			user = (com.zqfinance.module.sys.domain.User) session.getAttribute(ManageConfig.LOGIN_USER);
			if(null != user){
				//保存成功登陆日志
				Loginfo loginfo = new Loginfo();
				loginfo.setUserId(user.getId());
				loginfo.setUserName(user.getUserName());
				loginfo.setModuleName("投资管理（管理员）");
				loginfo.setLoginStatus(ManageConfig.loginStatus_SUCCESS);
				loginfoService.addLoginfo(loginfo);
			}
		}
		
		ModelAndView modelAndView = new ModelAndView("trading/product/order/productOrderList");
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String resultString = sendRequestService.sendRequest(ConfigManager.PRODUCTORDER_LIST,jsonObject);
		DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
		DateUtil.formatDateByDataPageMessage(dataMessage);
		SpringUtil.formatDateByDataPageMessage(dataMessage);
		// 获取统计信息
		JSONObject countjsonObject = RequestUtil.fromRequestToJson(request);
		countjsonObject.remove("numPerPage");
		countjsonObject.remove("pageNum");
		String countresultString = sendRequestService.sendRequest(ConfigManager.PRODUCTORDER_GETORDERSUMMONEYINFO,countjsonObject);
		DataMessage countdataMessage = MessageUtil.parseDataMessage(countresultString);
		
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(countdataMessage.getResult())){
			if(null != countdataMessage.getData().get("sumMoney")){
				countdataMessage.getData().put("sumMoney", FromatMoneyUtil.fromatMoney(Double.parseDouble(countdataMessage.getData().get("sumMoney"))));
			}
			if(null != countdataMessage.getData().get("sumResidualMoney")){
				countdataMessage.getData().put("sumResidualMoney", FromatMoneyUtil.fromatMoney(Double.parseDouble(countdataMessage.getData().get("sumResidualMoney"))));
			}
			if(null != countdataMessage.getData().get("sumMatchMoney")){
				countdataMessage.getData().put("sumMatchMoney", FromatMoneyUtil.fromatMoney(Double.parseDouble(countdataMessage.getData().get("sumMatchMoney"))));
			}
			modelAndView.addObject("countdataMessage", countdataMessage);
		}
		modelAndView.addObject("dataMessage", dataMessage);
		modelAndView.addObject("minMoney",jsonObject.get("minMoney"));
		modelAndView.addObject("maxMoney",jsonObject.get("maxMoney"));
		modelAndView.addObject("beginDate",jsonObject.get("beginDate"));
		modelAndView.addObject("endDate",jsonObject.get("endDate"));
		modelAndView.addObject("title",jsonObject.get("title"));
		modelAndView.addObject("status",jsonObject.get("status"));
		modelAndView.addObject("defaultMoney",jsonObject.get("defaultMoney"));
		modelAndView.addObject("orderId",jsonObject.get("orderId"));
		modelAndView.addObject("cardNo",jsonObject.get("cardNo"));
		return modelAndView;
	}
	
	/**
	 * 获得理财产品列表(公开的)
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPublicProductList")
	public ModelAndView getPublicProductList(HttpServletRequest request){	
		
		HttpSession session =  request.getSession(false);
		com.zqfinance.module.sys.domain.User user = null;
		if(null != session){
			user = (com.zqfinance.module.sys.domain.User) session.getAttribute(ManageConfig.LOGIN_USER);
			if(null != user){
				//保存成功登陆日志
				Loginfo loginfo = new Loginfo();
				loginfo.setUserId(user.getId());
				loginfo.setUserName(user.getUserName());
				loginfo.setModuleName("投资管理");
				loginfo.setLoginStatus(ManageConfig.loginStatus_SUCCESS);
				loginfoService.addLoginfo(loginfo);
			}
		}
		
		ModelAndView modelAndView = new ModelAndView("trading/product/order/publicProductOrderList");
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
        //添加客户信息id查询
      	String customerId =  session.getAttribute(ManageConfig.SESSION_CUSTOMER_ID)!=null?session.getAttribute(ManageConfig.SESSION_CUSTOMER_ID).toString():null;
      	if(MStringUtil.isNotEmpty(customerId)){
      		jsonObject.put("customerId", customerId);
      		String resultString = sendRequestService.sendRequest(ConfigManager.PRODUCTORDER_LIST,jsonObject);
    		DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
    		DateUtil.formatDateByDataPageMessage(dataMessage);
    		SpringUtil.formatDateByDataPageMessage(dataMessage);
    		modelAndView.addObject("dataMessage", dataMessage);
    		modelAndView.addObject("data", true);
      	}else{
      		modelAndView.addObject("data", false);
      	}
		return modelAndView;
	}
	
	
	
	/**
	 * 获得可以 匹配的理财订单
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCanMatchOrderList")
	public ModelAndView getCanMatchOrderList(HttpServletRequest request){	
		ModelAndView modelAndView = new ModelAndView("trading/product/order/matchOrderList");
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
        //设置默认 购买审核通过 连连通过 的条件 订单未匹配金额不为0；
        jsonObject.put("defaultMoney", "2"); // 设置默认值
		String resultString = sendRequestService.sendRequest(ConfigManager.PRODUCTORDER_LIST,jsonObject);
		DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
		DateUtil.formatDateByDataPageMessage(dataMessage);
		// 获取统计信息

		modelAndView.addObject("dataMessage", dataMessage);
		modelAndView.addObject("minMoney",jsonObject.get("minMoney"));
		modelAndView.addObject("maxMoney",jsonObject.get("maxMoney"));
		modelAndView.addObject("beginDate",jsonObject.get("beginDate"));
		modelAndView.addObject("endDate",jsonObject.get("endDate"));
		modelAndView.addObject("title",jsonObject.get("title"));
		modelAndView.addObject("orderId",jsonObject.get("orderId"));
		modelAndView.addObject("cardNo",jsonObject.get("cardNo"));
		return modelAndView;
	}
	
	/**
	 * 获取 investProductOrder 对象
	 * @param investProductId
	 * @return
	 */
	public ModelAndView findProductOrderById(String investproductorderId,String reqBtn){
		ModelAndView modelAndView = null;
		if(reqBtn.equals("info")){
			modelAndView = new ModelAndView("trading/product/order/productOrderInfo");
			JSONObject json = new JSONObject();
			json.put("orderId", investproductorderId.trim());
			String resultString = sendRequestService.sendRequest(ConfigManager.LOAN_FINDBYORDERID, json);
			DataPageMessage datePageMessage = MessageUtil.parseDatePageMessage(resultString);
			DateUtil.formatDateByDataPageMessage(datePageMessage);
			modelAndView.addObject("datePageMessage", datePageMessage);
		}else if(reqBtn.equals("edit")){
			modelAndView = new ModelAndView("trading/product/order/productOrderEdit");
		}
		//order对象
		JSONObject jsonObject = new JSONObject();
	    jsonObject.put("id", investproductorderId.trim());
	    String resultString = sendRequestService.sendRequest(ConfigManager.PRODUCTORDER_GETBYID,jsonObject);
	    DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		Map<String,String> dateMap = dataMessage.getData();
		if(MStringUtil.isNotEmpty(dateMap.get("orderdate"))){
			dateMap.put("orderdate",DateHelper.ChangDateFormat2(dateMap.get("orderdate")));
		}
		
		DateUtil.formatDateByDataMessage(dataMessage);
		modelAndView.addObject("dataMessage", dataMessage);
		//product对象
		JSONObject json_product = new JSONObject();
		json_product.put("id", dataMessage.getData().get("investproductId"));
		String result_product = sendRequestService.sendRequest(ConfigManager.INVESTPRODUCT_GETBYID, json_product);
		DataMessage dataMessage_product = MessageUtil.parseDataMessage(result_product);
		DateUtil.formatDateByDataMessage(dataMessage_product);
		modelAndView.addObject("dataMessage_product", dataMessage_product);
		//customer对象
		JSONObject jsonCustomerInfo = new JSONObject();
		jsonCustomerInfo.put("customerIds", dataMessage.getData().get("customerId"));
		String customerInfoString = sendRequestService.sendRequest(
				ConfigManager.LOAN_FINDBYCUSTOMERID, jsonCustomerInfo);
		DataMessage jobject = MessageUtil
				.parseDataMessageForP2b(customerInfoString);// com.alibaba.fastjson.JSON.parseObject(customerInfoString,
															// JSONObject.class);
		jobject.getData().put("createdatetime", jobject.getData().get("createdatetime").replace("T", " "));
		modelAndView.addObject("customerInfo", jobject);
		modelAndView.addObject("reqBtn", reqBtn);
		return modelAndView;
	}
	
	/**
	 * 删除
	 * @author 宋宇
	 */
	@RequestMapping("/delProductOrder")
	public @ResponseBody DwzAjaxController delProductOrder(String ids){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ids", ids);
		String resultString = sendRequestService.sendRequest(ConfigManager.PRODUCTORDERDEL_BYIDS,jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			return ajaxForwardSuccess("删除理财产品投标成功","",null,null);
		}
		return ajaxForwardError(dataMessage.getMsg(),"",null,null);
	}
	/**
	 * 匹配
	 * @author 宋宇
	 */
	@RequestMapping("/forwardMarryCreidt")
	public ModelAndView forwardMarryCreidt(String id){
		return this.findProductOrderById(id,"edit");
	}
	
	/**
	 * 详情
	 * @param id
	 * @return
	 */
	@RequestMapping("forwardInfo")
	public ModelAndView forwardInfo(String id){
		return this.findProductOrderById(id, "info");
	}
	
	/**
	 * 订单选择匹配loan
	 * @author rain
	 */
	@RequestMapping("/marryLoan")
	public @ResponseBody DwzAjaxController marryLoan(HttpServletRequest request){
		JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		Iterator<?> keys = jsonObject.keys();
		JSONArray array = new JSONArray();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String value = jsonObject.get(key).toString();
			if(key.contains("moneyid")){
				String no = key.substring(key.length()-1,key.length());
				String loanId =  jsonObject.getString("id"+no);
				if(MStringUtil.isNotEmpty(value.trim()) && MStringUtil.isNotEmpty(loanId)){
					JSONObject jo = new JSONObject();
					jo.put("money", value);
					jo.put("loanId", loanId);
	    			array.add(jo);
				}
    			
			}
		}
		if(array.size()<=0){
			return ajaxForwardError("请选择贷款标进行匹配！", "", null, null);
		}
		JSONObject json =  JSONObject.fromObject("{orderId:"+jsonObject.get("investProductOrderId")+",loanList:"+array.toString()+"}");
		String resultString = sendRequestService.sendRequest(
				ConfigManager.LOANINVESTPRODUCTORDER_MATCHLOAN, json);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if (ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage
				.getResult())) {
			return ajaxForwardSuccess("批量匹配债权成功", "", ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT, null);
		}
		return ajaxForwardError(dataMessage.getMsg(), "", null, null);
	}
	
	/**
	 * 批量 自动匹配 Loan  
	 * @param ids
	 * @return
	 */
	@RequestMapping("/autoMatchLoan")
	public @ResponseBody DwzAjaxController autoMatchLoan(String ids){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("orderIds", ids.trim());
		String resultString = sendRequestService.sendRequest(ConfigManager.LOANINVESTPRODUCTORDER_AUTOMATCHLOAN, jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if (ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())) {
			return ajaxForwardSuccess("自动匹配成功！", "", null,null);
		}else{
			return ajaxForwardError(dataMessage.getMsg(), "", null, null);
		}
	}
	/**
	 * 订单修复
	 * @param noOrder
	 * @return
	 */
	@RequestMapping("/queryOrderPayStatus")
	public @ResponseBody DwzAjaxController queryOrderPayStatus(String id){
		// order对象
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id.trim());
		String order_resultString = sendRequestService.sendRequest(ConfigManager.PRODUCTORDER_GETBYID, jsonObject);
		DataMessage order_dataMessage = MessageUtil.parseDataMessage(order_resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(order_dataMessage.getResult())){
			//获取order成功 --
			JSONObject json = new JSONObject();
			json.put("noOrder", order_dataMessage.getData().get("orderId"));
			json.put("money", order_dataMessage.getData().get("money"));
			String result = sendRequestService.sendRequest(ConfigManager.PRODUCIORDER_QUERYORDERPAYSTATUS, json);
			DataMessage dataMessage = MessageUtil.parseDataMessage(result);
			if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
				//SUCCESS 成功 WAITING 等待支付 PROCESSING 银行支付处理中 REFUND 退款 FAILURE 失败 (连连返回代码)
				//status 0、待处理；1、处理成功；2、处理失败 (平台订单 状态)
				String result_pay = dataMessage.getData().get("result_pay");
				String status = order_dataMessage.getData().get("status");
				
				JSONObject updateStatus = new JSONObject();
				updateStatus.put("id", order_dataMessage.getData().get("id"));
				
				BigDecimal money = BigDecimal.ZERO;
				//获取红包金额 
				JSONObject json_cash = new JSONObject();
				json_cash.put("orderId", id);
				String result_cash = sendRequestService.sendRequest(ConfigManager.CASHGIFTOUT_GETUSEDCASHGIFTOUTBYORDERID, json_cash);
				DataMessage dataMessage_cash = MessageUtil.parseDataMessage(result_cash);
				if(dataMessage_cash.getResult().equalsIgnoreCase(ManageConfig.DATE_MESSAGE_SUCCESS)){
					//使用了红包
					if(null != dataMessage_cash.getData()){
						money = money.add(new BigDecimal(dataMessage_cash.getData().get("money")));
					}
				}else{
					return ajaxForwardError(dataMessage_cash.getMsg(), "", null,null);
				}
				
				//订单金额对比
				if(new BigDecimal(dataMessage.getData().get("money_order")).add(money).compareTo(new BigDecimal(order_dataMessage.getData().get("money"))) != 0){
					return ajaxForwardError("金额异常！连连金额：" + dataMessage.getData().get("money_order") + "订单金额：" + order_dataMessage.getData().get("money"),"", null,null);
				}
				
				if(result_pay.equalsIgnoreCase("SUCCESS")){
					if(status.equals("0") || status.equals("2")){
						// 修复平台数据 修复成 1 处理成功
						updateStatus.put("status", "1");
					} else {
						return ajaxForwardError("交易正常，不需要修复！", "", null,null);
					}
				} else if(result_pay.equalsIgnoreCase("WAITING") || result_pay.equalsIgnoreCase("FAILURE")){
					if(status.equals("1")){
						// 修复平台数据 修复成 2 处理失败
						updateStatus.put("status", "2");
					} else {
						return ajaxForwardError("交易正常，不需要修复！", "", null,null);
					}
				} 
				String orderResult = sendRequestService.sendRequest(ConfigManager.REPAIRPRODUCTORDERSTATUS, updateStatus);
				DataMessage orderDataMessage = MessageUtil.parseDataMessage(orderResult);
				if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(orderDataMessage.getResult())){
					return ajaxForwardSuccess("修复成功！", "", null,null);
				} else {
					return ajaxForwardError(orderDataMessage.getMsg(), "", null,null);
				}
				
			}else{
				// 向连连 获取 失败
				return ajaxForwardError("连连返回：错误号："+dataMessage.getData().get("ret_code")+"，错误信息："+dataMessage.getData().get("ret_msg")+"！", "", null, null);
			}
		}else{
			// 获取 order对象失败
			return ajaxForwardError(order_dataMessage.getMsg(), "", null, null);
		}
	}
	
	/**
	 * 批量订单修复（可以待优化）
	 * @param ids
	 * @return
	 */
	@RequestMapping("/autoReconciliationOrder")
	public @ResponseBody DwzAjaxController autoReconciliationOrder(String ids){
		String[] strIds = ids.split(",");
//		StringBuilder sb = new StringBuilder();
		for (String string : strIds) {
			// order对象
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", string.trim());
			String order_resultString = sendRequestService.sendRequest(ConfigManager.PRODUCTORDER_GETBYID, jsonObject);
			DataMessage order_dataMessage = MessageUtil.parseDataMessage(order_resultString);
			if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(order_dataMessage.getResult())){
				//获取order成功 --
				JSONObject json = new JSONObject();
				json.put("noOrder", order_dataMessage.getData().get("orderId"));
				String result = sendRequestService.sendRequest(ConfigManager.PRODUCIORDER_QUERYORDERPAYSTATUS, json);
				DataMessage dataMessage = MessageUtil.parseDataMessage(result);
				if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
					//SUCCESS 成功 WAITING 等待支付 PROCESSING 银行支付处理中 REFUND 退款 FAILURE 失败 (连连返回代码)
					//status 0、待处理；1、处理成功；2、处理失败 (平台订单 状态)
					String result_pay = dataMessage.getData().get("result_pay");
					String status = order_dataMessage.getData().get("status");
					
					JSONObject updateStatus = new JSONObject();
					updateStatus.put("id", order_dataMessage.getData().get("id"));
					
					BigDecimal money = BigDecimal.ZERO;
					//获取红包金额 
					JSONObject json_cash = new JSONObject();
					json_cash.put("orderId", order_dataMessage.getData().get("id"));
					String result_cash = sendRequestService.sendRequest(ConfigManager.CASHGIFTOUT_GETUSEDCASHGIFTOUTBYORDERID, json_cash);
					DataMessage dataMessage_cash = MessageUtil.parseDataMessage(result_cash);
					if(dataMessage_cash.getResult().equalsIgnoreCase(ManageConfig.DATE_MESSAGE_SUCCESS)){
						//使用了红包
						if(null != dataMessage_cash.getData()){
							money = money.add(new BigDecimal(dataMessage_cash.getData().get("money")));
						}
					}else{
						return ajaxForwardError(dataMessage_cash.getMsg(), "", null,null);
					}
					
					//订单金额对比
					if(new BigDecimal(dataMessage.getData().get("money_order")).add(money).compareTo(new BigDecimal(order_dataMessage.getData().get("money"))) != 0){
						return ajaxForwardError("订单号："+order_dataMessage.getData().get("orderId")+"，金额异常！连连金额：" + dataMessage.getData().get("money_order") + "订单金额：" + order_dataMessage.getData().get("money"),"", null,null);
					}
					
					if(result_pay.equalsIgnoreCase("SUCCESS")){
						if(status.equals("0") || status.equals("2")){
							// 修复平台数据 修复成 1 处理成功
							updateStatus.put("status", "1");
						}
					} else if(result_pay.equalsIgnoreCase("WAITING") || result_pay.equalsIgnoreCase("FAILURE")){
						if(status.equals("1")){
							// 修复平台数据 修复成 2 处理失败
							updateStatus.put("status", "2");
						}
					} 
					String orderResult = sendRequestService.sendRequest(ConfigManager.REPAIRPRODUCTORDERSTATUS, updateStatus);
					DataMessage orderDataMessage = MessageUtil.parseDataMessage(orderResult);
					if(!ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(orderDataMessage.getResult())){
						return ajaxForwardError("订单号："+order_dataMessage.getData().get("orderId")+"修复状态失败，请联系系统管理员！", "", null,null);
					}
				}else{
					//continue;
					// 向连连返回错误信息 不需要修复！
					return ajaxForwardError("连连返回：订单号："+order_dataMessage.getData().get("orderId")+"，错误信息："+dataMessage.getData().get("ret_msg")+"！", "", null, null);
			}
			}else{
//				sb.append(string+",");数据库不存在该记录
				continue;
			}
		}
//		List<Object> listObject = new ArrayList<Object>();
//		listObject.add(sb);
		return ajaxForwardSuccess("批量投标对账修复成功!","",null,null);
	}
	
	@RequestMapping("/dwlExport")
	public void downLoadExcport(HttpServletRequest request,
			HttpServletResponse response){
		JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String resultString = sendRequestService.sendRequest(ConfigManager.PRODUCTORDER_LIST, jsonObject);
		DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
		DateUtil.formatDateByDataPageMessage(dataMessage);
		
		for (int i = 2; i < dataMessage.getPage().getTotalCount()/20+2; i++) {
			JSONObject jsonObjects = RequestUtil.fromRequestToJson(request);
			jsonObjects.put("pageNum", i);
			String resultStrings = sendRequestService.sendRequest(ConfigManager.PRODUCTORDER_LIST, jsonObjects);
			DataPageMessage dataMessages = MessageUtil.parseDatePageMessage(resultStrings);
			DateUtil.formatDateByDataPageMessage(dataMessages);
			dataMessage.getContentList().addAll(dataMessages.getContentList());
		}
		
		HSSFWorkbook workbook = new HSSFWorkbook();// 创建一个Excel文件
		HSSFSheet sheet = workbook.createSheet();// 创建一个Excel的Sheet
		// Sheet样式
		HSSFCellStyle sheetStyle = workbook.createCellStyle();
		sheetStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		sheetStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		sheetStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		// 设置列的样式
		for (int i = 0; i <= 14; i++) {
			sheet.setDefaultColumnStyle((short) i, sheetStyle);
		}
		// 设置字体
		HSSFFont headfont = workbook.createFont();
		headfont.setFontName("黑体");
		headfont.setFontHeightInPoints((short) 22);// 字体大小
		headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗

		try {
			// 创建第一行
			HSSFRow row0 = sheet.createRow(0);
			row0.setHeight((short) 450);
			// 创建第一列
			HSSFCell cell0 = row0.createCell(0);
			cell0.setCellValue(new HSSFRichTextString("理财产品（连锁酒店经营贷）订单对照表"));
			cell0.setCellStyle(sheetStyle);
			CellRangeAddress range = new CellRangeAddress(0, 0, 0, 5);
			sheet.addMergedRegion(range);
			// 创建第三行
			HSSFRow row2 = sheet.createRow(2);
			String[] strList = new String[] { "理财产品名称", "订单号", "金额（RMB）","处理状态", "付款银行卡号", "交易时间" };
			for (int i = 0; i < strList.length; i++) {
				HSSFCell cell2 = row2.createCell(i);
				cell2.setCellValue(strList[i]);
				cell2.setCellStyle(sheetStyle);
				sheet.setColumnWidth(i, 5800);
			}
			// 创建n行
			BigDecimal money = BigDecimal.ZERO;
			for (int i = 0; i < dataMessage.getContentList().size(); i++) {
				Map<String, String> resutltMap = dataMessage.getContentList().get(i);
				HSSFRow row = sheet.createRow(i + 3);
				sheet.setColumnWidth(i, 5800);
				row.createCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
				row.createCell(0).setCellValue(resutltMap.get("title"));
				row.createCell(1).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				row.createCell(1).setCellValue(resutltMap.get("orderId"));
				row.createCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
				money = money.add(BigDecimal.valueOf(Double.parseDouble(resutltMap.get("money") != null ? resutltMap.get("money"): new String("0.0"))));
				row.createCell(2).setCellValue(FromatMoneyUtil.fromatMoney(Double.parseDouble(resutltMap.get("money") != null ? resutltMap.get("money") : new String("0.0"))));
				row.createCell(3).setCellType(HSSFCell.CELL_TYPE_STRING);
				String status = DictionaryMap.PRODUCTORDER_FLAG.get(resutltMap.get("status"));
				row.createCell(3).setCellValue(status);
				row.createCell(4).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				row.createCell(4).setCellValue(resutltMap.get("cardNo"));
				row.createCell(5).setCellType(HSSFCell.CELL_TYPE_STRING);
				row.createCell(5).setCellValue(resutltMap.get("timeTrading"));
			}
			// 创建第二行
			int footRownumber = sheet.getFirstRowNum();
			HSSFRow footRow = sheet.createRow(footRownumber + 1);
			footRow.setHeight((short) 450);
			HSSFCell footRowcell = footRow.createCell(0);
			footRowcell.setCellValue(new HSSFRichTextString("导出总条数"+ dataMessage.getPage().getTotalCount() + "条    总交易金额："+ FromatMoneyUtil.fromatMoney(money.doubleValue()) + "元     导出时间为："+ DateHelper.timeToString(new Date())));
			footRowcell.setCellStyle(sheetStyle);
			range = new CellRangeAddress(footRownumber + 1, footRownumber + 1,0, 5);
			sheet.addMergedRegion(range);

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition",
					"attachment;filename="+ new String("理财产品订单记录".getBytes("gb2312"), "iso8859-1")+".xls");
			OutputStream ouputStream = response.getOutputStream();
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
			workbook.close();
		} catch (Exception e) {

		}
	}
	
	/**
	 * 查询 投标中 所有的债权转让标 （暂时作废）
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCreditListByStatusAndApr")
	public ModelAndView getCreditListByStatusAndApr(HttpServletRequest request){	
		JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		//order
		JSONObject json_order = new JSONObject();
		json_order.put("id", jsonObject.get("id").toString().trim());
		String resultString_order = sendRequestService.sendRequest(ConfigManager.PRODUCTORDER_GETBYID,json_order);
		DataMessage dataMessage_order = MessageUtil.parseDataMessage(resultString_order);
		//理财产品购买人
		String customerId = dataMessage_order.getData().get("customerId");
		//product
		JSONObject json_product = new JSONObject();
		json_product.put("id", dataMessage_order.getData().get("investproductId"));
		String resultString_product = sendRequestService.sendRequest(ConfigManager.INVESTPRODUCT_GETBYID,json_product);
		DataMessage dataMessage_product = MessageUtil.parseDataMessage(resultString_product);
		//理财产品年利润
		String apr = dataMessage_product.getData().get("apr");
		//查询对应的债权列表
		ModelAndView modelAndView = new ModelAndView("trading/product/order/creditList");
        jsonObject.put("status", "1");
        jsonObject.put("apr", apr);
        jsonObject.put("customerId", customerId);
		String resultString = sendRequestService.sendRequest(ConfigManager.CREDIT_GETCREDITLIST,jsonObject);
		DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
		DateUtil.formatDateByDataPageMessage(dataMessage);
		modelAndView.addObject("dataMessage", dataMessage);
		modelAndView.addObject("investProductOrderid",jsonObject.get("id").toString().trim());
		return modelAndView;
	}
	
}
