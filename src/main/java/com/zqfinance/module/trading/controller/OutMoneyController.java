package com.zqfinance.module.trading.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.zqfinance.module.sys.domain.Loginfo;
import com.zqfinance.module.sys.domain.User;
import com.zqfinance.system.common.DataMessage;
import com.zqfinance.system.common.DataPageMessage;
import com.zqfinance.system.config.ConfigManager;
import com.zqfinance.system.config.ManageConfig;
import com.zqfinance.system.controller.DwzAjaxController;
import com.zqfinance.system.service.CommonService;
import com.zqfinance.system.util.DateHelper;
import com.zqfinance.system.util.DateUtil;
import com.zqfinance.system.util.DictionaryMap;
import com.zqfinance.system.util.FromatMoneyUtil;
import com.zqfinance.system.util.MStringUtil;
import com.zqfinance.system.util.MessageUtil;
import com.zqfinance.system.util.RequestUtil;

@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/outMoney")
public class OutMoneyController extends DwzAjaxController {
	public static final String STATUS_PROCESS = "0"; //待处理
	public static final String STATUS_SUCCESS = "1"; //放款成功
	public static final String STATUS_FAIL = "2"; //放款失败
	public static final String STATUS_AUDIT_SUCCESS = "3"; //审核通过
	public static final String STATUS_LOAN = "4"; //连连放款
	
	
	@Autowired
	private CommonService commonService;
	/**
	 * 获得转账提现列表(管理员的)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getOutMoneyList")
	public ModelAndView getOutMoneyList(HttpServletRequest request) {
		
		HttpSession session =  request.getSession(false);
		com.zqfinance.module.sys.domain.User user = null;
		if(null != session){
			user = (com.zqfinance.module.sys.domain.User) session.getAttribute(ManageConfig.LOGIN_USER);
			if(null != user){
				//保存成功登陆日志
				Loginfo loginfo = new Loginfo();
				loginfo.setUserId(user.getId());
				loginfo.setUserName(user.getUserName());
				loginfo.setModuleName("提现管理（管理员）");
				loginfo.setLoginStatus(ManageConfig.loginStatus_SUCCESS);
				loginfoService.addLoginfo(loginfo);
			}
		}
		
		ModelAndView modelAndView = new ModelAndView(
				"trading/outMoney/outMoneyList");
		JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String resultString = sendRequestService.sendRequest(
				ConfigManager.OUTMONEY_LIST, jsonObject);
		JSONObject sumMoneyObject = RequestUtil.fromRequestToJson(request);
		sumMoneyObject.remove("numPerPage");
		sumMoneyObject.remove("pageNum");
		String sumMoneyString = sendRequestService.sendRequest(
				ConfigManager.OUTMONEY_ADVANCEDSEARCHSUMMONEY, sumMoneyObject);
		DataPageMessage dataMessage = MessageUtil
				.parseDateSumMoneyPageMessage(resultString);
		DataMessage sumMoneyMessage=MessageUtil.parseDataMessageOfString(sumMoneyString);
		DateUtil.formatDateByDataPageMessage(dataMessage);
		Map<String,String> bankMap=commonService.getPaybankMap();
		modelAndView.addObject("bankMap", bankMap);
		modelAndView.addObject("dataMessage", dataMessage);
		modelAndView.addObject("customerName", jsonObject.get("customerName"));
		modelAndView.addObject("mobile", jsonObject.get("mobile"));
		modelAndView.addObject("brank", jsonObject.get("brank"));
		modelAndView.addObject("cardNo", jsonObject.get("cardNo"));
		modelAndView.addObject("mixMoney", jsonObject.get("mixMoney"));
		modelAndView.addObject("maxMoney", jsonObject.get("maxMoney"));
		modelAndView.addObject("beginDate", jsonObject.get("beginDate"));
		modelAndView.addObject("endDate", jsonObject.get("endDate"));
		modelAndView.addObject("flag", jsonObject.get("flag"));
		modelAndView.addObject("orderNo", jsonObject.get("orderNo"));
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(sumMoneyMessage.getResult())){
			//FromatMoneyUtil.fromatMoney(Double.parseDouble(sumMoneyMessage.getData().get("sumMoney")))
			modelAndView.addObject("sumMoney",FromatMoneyUtil.fromatMoney(Double.parseDouble(sumMoneyMessage.getData().get("sumMoney"))));
			//modelAndView.addObject("sumMoney", sumMoneyMessage.getData().get("sumMoney"));
		}
		return modelAndView;
	}
	
	
	/**
	 * 获得转账提现列表（公开的）
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPublicOutMoneyList")
	public ModelAndView getPublicOutMoneyList(HttpServletRequest request) {
		
		HttpSession session =  request.getSession(false);
		com.zqfinance.module.sys.domain.User user = null;
		if(null != session){
			user = (com.zqfinance.module.sys.domain.User) session.getAttribute(ManageConfig.LOGIN_USER);
			if(null != user){
				//保存成功登陆日志
				Loginfo loginfo = new Loginfo();
				loginfo.setUserId(user.getId());
				loginfo.setUserName(user.getUserName());
				loginfo.setModuleName("提现管理");
				loginfo.setLoginStatus(ManageConfig.loginStatus_SUCCESS);
				loginfoService.addLoginfo(loginfo);
			}
		}
		
		ModelAndView modelAndView = new ModelAndView("trading/outMoney/publicOutMoneyList");
		JSONObject jsonObject = new JSONObject();
		//添加客户信息id查询
      	String customerId =  session.getAttribute(ManageConfig.SESSION_CUSTOMER_ID)!=null?session.getAttribute(ManageConfig.SESSION_CUSTOMER_ID).toString():null;
      	if(MStringUtil.isNotEmpty(customerId)){
      		jsonObject.put("customerId", customerId);
      		String resultString = sendRequestService.sendRequest(ConfigManager.OUTMONEY_LIST, jsonObject);
    		DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
    		DateUtil.formatDateByDataPageMessage(dataMessage);
    		modelAndView.addObject("dataMessage", dataMessage);
    		modelAndView.addObject("data", true);
      	}else{
      		modelAndView.addObject("data", false);
      	}
		return modelAndView;
	}

	/**
	 * 获得转账提现详情
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getOutMoneyDetail")
	@ResponseBody
	public ModelAndView getOutMoneyDetails(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView(
				"trading/outMoney/outMoneyDetail");
		JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		JSONObject jsonObjectForList = RequestUtil.fromRequestToJson(request);
		JSONObject jsonObjectForFlow = RequestUtil.fromRequestToJson(request);
		String outMoneyString = sendRequestService.sendRequest(
				ConfigManager.OUTMONEYBYID, jsonObject);
		String outMoneyList = sendRequestService.sendRequest(
				ConfigManager.OUTMONEYINVERSTPRODUCTBYID, jsonObjectForList);
		DataMessage dataMessage = MessageUtil.parseDataMessage(outMoneyString);
		if (ManageConfig.DATE_MESSAGE_SUCCESS.equals(dataMessage.getResult())) {
			Map<String, String> outMoneyMap = dataMessage.getData();
			String customerId = outMoneyMap.get("customerId");
			JSONObject jsonCustomerInfo = new JSONObject();
			jsonCustomerInfo.put("customerIds", customerId);
			String customerInfoString = sendRequestService.sendRequest(
					ConfigManager.LOAN_FINDBYCUSTOMERID, jsonCustomerInfo);
			DataMessage jobject = MessageUtil
					.parseDataMessageForP2b(customerInfoString);// com.alibaba.fastjson.JSON.parseObject(customerInfoString,
																// JSONObject.class);
			modelAndView.addObject("customerInfo", jobject);
		}
		String operationFlowString = sendRequestService.sendRequest(
				ConfigManager.OUTMONEYOPERATIONFLOWBYOUTMONEYID,
				jsonObjectForFlow);
		DataPageMessage operationFlowDataMessage = MessageUtil
				.parseDateListMessage(operationFlowString);
		DataPageMessage dataList = MessageUtil
				.parseDateListMessage(outMoneyList);
		DateUtil.formatDateByDataMessage(dataMessage);
		DateUtil.formatDateByDataPageMessage(dataList);
		DateUtil.formatDateByDataPageMessage(operationFlowDataMessage);
		Map<String,String> bankMap=commonService.getPaybankMap();
		modelAndView.addObject("bankMap", bankMap);
		modelAndView.addObject("dataMessage", dataMessage);
		modelAndView.addObject("dataList", dataList);
		modelAndView.addObject("operationFlowDataMessage",
				operationFlowDataMessage);
		return modelAndView;
	}
	
	
	/**
	 * 获得转账提现审核界面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getOutMoneyAudit")
	@ResponseBody
	public ModelAndView getOutMoneyAudit(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView(
				"trading/outMoney/outMoneyAudit");
		JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String outMoneyString = sendRequestService.sendRequest(
				ConfigManager.OUTMONEYBYID, jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(outMoneyString);
		if (ManageConfig.DATE_MESSAGE_SUCCESS.equals(dataMessage.getResult())) {
			Map<String, String> outMoneyMap = dataMessage.getData();
			String customerId = outMoneyMap.get("customerId");
			//String outmoney=outMoneyMap.get("money");
			JSONObject jsonCustomerInfo = new JSONObject();
			jsonCustomerInfo.put("customerIds", customerId);
			String customerInfoString = sendRequestService.sendRequest(
					ConfigManager.LOAN_FINDBYCUSTOMERID, jsonCustomerInfo);
			DataMessage jobject = MessageUtil
					.parseDataMessageForP2b(customerInfoString);
			// com.alibaba.fastjson.JSON.parseObject(customerInfoString,JSONObject.class);
			JSONObject jsonSumMoney = new JSONObject();
			jsonSumMoney.put("customerId", customerId);
			jsonSumMoney.put("outMoneyId", outMoneyMap.get("id").toString());
			String sumMoneyString = sendRequestService.sendRequest(
					ConfigManager.OUTMONEY_USABLESUMMONEYBYMANAGE, jsonSumMoney);
			DataMessage sumMoneyMessage = MessageUtil.parseDataMessageOfString(sumMoneyString);
			Map<String, String> sumMoneyMap=sumMoneyMessage.getData();
			String sumMoney=sumMoneyMap.get("sumMoney");
			sumMoneyMap.put("sumMoney", sumMoney.toString());
			JSONObject jsonOutMoneyList = new JSONObject();
			jsonOutMoneyList.put("customerId", customerId);
			String OutMoneyLisString = sendRequestService.sendRequest(
					ConfigManager.OUTMONEY_GETOUTMONEYBYCUSTOMERID, jsonOutMoneyList);
			DataPageMessage dataPageMessage=MessageUtil.parseDateListMessage(OutMoneyLisString);
			List<Map<String, String>> list=dataPageMessage.getContentList();
			BigDecimal money=BigDecimal.ZERO;//取现成功和取现流程中的金额
			BigDecimal usedMoney = BigDecimal.ZERO;//取现成功的金额
			if(!list.isEmpty()){
				for(int i=0;i<list.size();i++){
					Map<String, String> map=list.get(i);
					String status=map.get("status");
					if("2".equals(status)){
						continue;
					}
					money=money.add(new BigDecimal(map.get("money")));
					if("1".equals(status) || "4".equals(status)){
						usedMoney = usedMoney.add(new BigDecimal(map.get("money")));
					}
				}
			}
			JSONObject buyJson = new JSONObject();
			buyJson.put("customerId", customerId);
			String buyJsonString = sendRequestService.sendRequest(
					ConfigManager.PRODUCTORDER_GETSUMMONEYBYCUSTOMER, buyJson);
			DataMessage buyMessage=MessageUtil.parseDataMessageOfString(buyJsonString);

			boolean flag=triggerWarning(money, new BigDecimal(buyMessage.getData().get("sumMoney")));
			modelAndView.addObject("isWarning", flag);
			modelAndView.addObject("buySumMoney", buyMessage.getData().get("sumMoney"));
			modelAndView.addObject("usedMoney", usedMoney);
			modelAndView.addObject("sumMoney", sumMoneyMessage);
			modelAndView.addObject("customerInfo", jobject);
		}
		DateUtil.formatDateByDataMessage(dataMessage);
		modelAndView.addObject("dataMessage", dataMessage);
		
		return modelAndView;
	}
	

	/**
	 * 审核转账提现
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/auditOutMoney")
	public @ResponseBody DwzAjaxController auditOutMoney(
			HttpServletRequest request, String id,String value) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user==null){
			return ajaxForwardError("session失效，请重新登陆!", "", null, null);
		}
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("value", value);
		json.put("userId", user.getId());
		json.put("userName", user.getUserName());
		String resultString = sendRequestService.sendRequest(
				ConfigManager.OUTMONEYAUDIT, json);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if (ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage
				.getResult())) {
			//刷新首页
			initIndexService.setSessionValue(request, "/outMoney/getOutMoneyAudit");
			return ajaxForwardSuccess("操作成功", "", null, null);
		}
		
		return ajaxForwardError(dataMessage.getMsg(), "", null, null);
	}

	/**
	 * 自动批量审核转账提现
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/batchAuditOutMoney")
	public @ResponseBody DwzAjaxController batchAuditOutMoney(
			HttpServletRequest request, String ids) {
		String[] idStr = ids.split(",");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		List<Object> list=new ArrayList<Object>();
		if(user==null){
			return ajaxForwardError("session失效，请重新登陆!", "", null, null);
		}
		for (String id : idStr) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("id", id);
			String outMoneyString = sendRequestService.sendRequest(
					ConfigManager.OUTMONEYBYID, jsonObject);
			DataMessage dataMessage = MessageUtil.parseDataMessage(outMoneyString);
			if (ManageConfig.DATE_MESSAGE_FAIL.equals(dataMessage.getResult())) {
				list.add(id);
				continue;
			}
			Map<String, String> outMoneyMap = dataMessage.getData();
			String outmoney=outMoneyMap.get("money");
			if(new BigDecimal(outmoney).compareTo(new BigDecimal(ConfigManager.OUTMONEY_AUDIT)) >= 0){
				list.add(id);
				continue;
			}
			JSONObject json = new JSONObject();
			json.put("id", id);
			json.put("value", "3");
			json.put("userId", user.getId());
			json.put("userName", user.getUserName());
			String resultString = sendRequestService.sendRequest(
					ConfigManager.OUTMONEYAUDIT, json);
			DataMessage auditdataMessage = MessageUtil.parseDataMessage(resultString);
			if (ManageConfig.DATE_MESSAGE_FAIL.equalsIgnoreCase(auditdataMessage
					.getResult())) {
				list.add(id);
				continue;
			}
			//刷新首页
			initIndexService.setSessionValue(request, "/outMoney/getOutMoneyAudit");
		}
		if(list!=null&&list.size()>0){
			return ajaxForwardError("有"+list.size()+"笔审核失败", "", null, list);
		}
		return ajaxForwardSuccess("操作成功", "", null, null);
	}
	
	
	/**
	 * 转账提现自动放款
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/payOutMoney")
	public @ResponseBody DwzAjaxController payOutMoney(
			HttpServletRequest request, String ids) {
		String[] idStr = ids.split(",");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		List<Object> list=new ArrayList<Object>();
		if(user==null){
			return ajaxForwardError("session失效，请重新登陆!", "", null, null);
		}
		for (String id : idStr) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", id);
			String outMoneyString = sendRequestService.sendRequest(
					ConfigManager.OUTMONEYBYID, jsonObject);
			DataMessage outMoneyDataMessage = MessageUtil
					.parseDataMessage(outMoneyString);
			if (OutMoneyController.STATUS_LOAN.equals(outMoneyDataMessage
					.getData().get("status"))) {
//				return ajaxForwardError("已存在放款的订单，不能重复放款", "", null, null);
				list.add(id);
				continue;
			}
			if (OutMoneyController.STATUS_SUCCESS.equals(outMoneyDataMessage
					.getData().get("status"))) {
//				return ajaxForwardError("已放款成功，不能重复放款", "", null, null);
				list.add(id);
				continue;
			}
			if (!(OutMoneyController.STATUS_AUDIT_SUCCESS.equals(outMoneyDataMessage
					.getData().get("status")))) {
//				return ajaxForwardError("必须是审核通过状态才能放款", "", null, null);
				list.add(id);
				continue;
			}
			//本次放款超过1笔
			if(idStr.length > 1){
				// 新增放款限额限制
				if(ConfigManager.OUTMONEY_QUOTA != ""){
					BigDecimal outMoney = new BigDecimal(outMoneyDataMessage.getData().get("money")!="" ? outMoneyDataMessage.getData().get("money") : "0");
					BigDecimal quota = new BigDecimal(ConfigManager.OUTMONEY_QUOTA);
					if(outMoney.compareTo(quota)!=-1){
//						return ajaxForwardError("超过系统"+ConfigManager.OUTMONEY_QUOTA+"元金额限制不能放款", "", null, null);
						list.add(id);
						continue;
					}
				}
			}
			JSONObject validateMoneyObject = new JSONObject();
			validateMoneyObject.put("customerId", outMoneyDataMessage.getData().get("customerId"));
			validateMoneyObject.put("cardId", outMoneyDataMessage.getData().get("cardid"));
			validateMoneyObject.put("money", outMoneyDataMessage.getData().get("money"));
			validateMoneyObject.put("outMoneyId", id);
			String validateMoneyString = sendRequestService.sendRequest(
					ConfigManager.VALIDATE_CARDIDANDMONEY, validateMoneyObject);
			DataMessage validateMoneyMessage = MessageUtil
					.parseDataMessage(validateMoneyString);
			if (ManageConfig.DATE_MESSAGE_FAIL.equals(validateMoneyMessage
					.getResult())) {
//				return ajaxForwardError(validateMoneyMessage.getMsg(), "", null,
//						null);
				list.add(id);
				continue;
			}
			JSONObject validateObject = new JSONObject();
			validateObject.put("outMoneyId", id);
			validateObject.put("userId", user.getId());
			validateObject.put("userName", user.getUserName());
			String validateString = sendRequestService.sendRequest(
					ConfigManager.VALIDATE_LASTCOUNTAPR, validateObject);
			DataMessage validateDataMessage = MessageUtil
					.parseDataMessage(validateString);
			if (ManageConfig.DATE_MESSAGE_FAIL.equals(validateDataMessage
					.getResult())) {
//				return ajaxForwardError(validateDataMessage.getMsg(), "", null,
//						null);
				list.add(id);
				continue;
			}
			JSONObject json = new JSONObject();
			json.put("id", id);
			String resultString = sendRequestService.sendRequest(
					ConfigManager.OUTMONEY_GETCUSTOMERBANKANDOUTMONEY, json);
			DataMessage dataMessage = MessageUtil
					.parseDataMessage(resultString);
			Map<String, String> map = dataMessage.getData();
			map.put("info_order", "客户提现转账代付");
			map.put("flag_card", ConfigManager.CARD_FLAG_PRI);
			map.put("userId", String.valueOf(user.getId()));
			map.put("userName",user.getUserName());
			JSONObject requestJson = JSONObject.fromObject(map);
			String payResultString = sendRequestService.sendRequest(
					ConfigManager.OUTMONEY_LOANPAY, requestJson);
			if (StringUtils.isNotEmpty(payResultString)) {
				DataMessage payDataMessage = MessageUtil
						.parseDataMessage(payResultString);
				if (ManageConfig.DATE_MESSAGE_FAIL.equals(payDataMessage
						.getResult())) {
					 
//					return ajaxForwardError(payDataMessage.getMsg(), "", null,
//							null);
					list.add(id);
					continue;
				}
			}

		}
		//刷新首页显示数据
		initIndexService.setSessionValue(request, "/outMoney/payOutMoney");
		if(list!=null&&list.size()>0){
			return ajaxForwardSuccess("有"+list.size()+"笔申请未通过", "", null, list);
		}
		return ajaxForwardSuccess("所有连连代付订单申请成功", "", null, null);
	}
	
	
	public static void main(String[] args) {
		String[] idStr = "1".split(",");
		System.out.println(idStr.length);
	}
	/**
	 * 转账提现自动对账
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/autoReconciliationOutMoney")
	public @ResponseBody DwzAjaxController autoReconciliationOutMoney(HttpServletRequest request, String ids){
		String[] idStr = ids.split(",");
		List<Object> list=new ArrayList<Object>();
		for (String id : idStr) {
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", id);
			String outMoneyString = sendRequestService.sendRequest(
					ConfigManager.OUTMONEYBYID, jsonObject);
			DataMessage outMoneyDataMessage = MessageUtil
					.parseDataMessage(outMoneyString);
			if (OutMoneyController.STATUS_PROCESS.equals(outMoneyDataMessage.getData().get("status"))
					||OutMoneyController.STATUS_AUDIT_SUCCESS.equals(outMoneyDataMessage.getData().get("status"))) {
//				return ajaxForwardError("取现订单状态不符合对账要求", "", null, null);
				list.add(id);
				continue;
			}
			JSONObject json2 = new JSONObject();
			json2.put("id", id);
			String resultString = sendRequestService.sendRequest(
					ConfigManager.OUTMONEY_GETCUSTOMERBANKANDOUTMONEY, json2);
			DataMessage dataMessage = MessageUtil
					.parseDataMessage(resultString);
			if(ManageConfig.DATE_MESSAGE_FAIL.equals(dataMessage
					.getResult())){
//				return ajaxForwardError(dataMessage.getMsg(), "", null,
//						null);
				list.add(id);
				continue;
			}
			Map<String, String> map = dataMessage.getData();
			JSONObject requestJson = JSONObject.fromObject(map);
			String payResultString = sendRequestService.sendRequest(
					ConfigManager.OUTMONEY_AUTORECONCILIATION, requestJson);
			DataMessage payDataMessage = MessageUtil
					.parseDataMessage(payResultString);
			if (ManageConfig.DATE_MESSAGE_FAIL.equals(payDataMessage
					.getResult())) {
//				return ajaxForwardError(payDataMessage.getMsg(), "", null,
//						null);
				list.add(id);
				continue;
			}
			Map<String, String> resultMap=payDataMessage.getData();
			String result_pay=resultMap.get("result_pay");
			String no_order=resultMap.get("no_order");
//			String oid_partner=resultMap.get("oid_partner");
//			String dt_order=resultMap.get("dt_order");
//			String oid_paybill=resultMap.get("oid_paybill");
			String money_order=resultMap.get("money_order");
//			String settle_date=resultMap.get("settle_date");
//			String info_order=resultMap.get("info_order");
//			String pay_type=resultMap.get("pay_type");
//			String bank_code=resultMap.get("bank_code");
//			String bank_name=resultMap.get("bank_name");
//			String memo=resultMap.get("memo");
			//订单金额对比
			if(StringUtils.isEmpty(money_order)){
				money_order="0";
			}
//			if(new BigDecimal(money_order).compareTo(new BigDecimal(outMoneyDataMessage.getData().get("money"))) != 0){
//				list.add(id);
//				continue;
//			}
			
			JSONObject updateJson = new JSONObject();
			updateJson.put("orderNo", no_order.trim());
			updateJson.put("result_pay",result_pay);
			String paySuccessString = sendRequestService.sendRequest(
					ConfigManager.OUTMONEY_UPDATEPAYSUCCESS, updateJson);
			DataMessage updateJsonMessage = JSON.parseObject(paySuccessString,
					DataMessage.class);
			if (ManageConfig.DATE_MESSAGE_FAIL.equalsIgnoreCase(updateJsonMessage
					.getResult())) {
//				return ajaxForwardError(updateJsonMessage.getMsg(), "", null, null);
				list.add(id);
				continue;
			}
			JSONObject updateIsReconciliationJson = new JSONObject();
			updateIsReconciliationJson.put("outMoneyId", id);
			String updateIsReconciliationString = sendRequestService.sendRequest(
					ConfigManager.UPDATERECONCILIATIONBYID, updateIsReconciliationJson);
			DataMessage updateIsReconciliationMessage = JSON.parseObject(updateIsReconciliationString,
					DataMessage.class);
			if (ManageConfig.DATE_MESSAGE_FAIL.equalsIgnoreCase(updateIsReconciliationMessage
					.getResult())) {
//				return ajaxForwardError(updateIsReconciliationMessage.getMsg(), "", null, null);
				list.add(id);
				continue;
			}
		}
		if(list!=null&&list.size()>0){
			return ajaxForwardSuccess("有"+list.size()+"笔对账未通过", "", null, list);
		}
		return ajaxForwardSuccess("自动对账修复成功", "", null, null);
	}

	/**
	 * 删除转账提现
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteOutMoney")
	public @ResponseBody DwzAjaxController deleteOutMoney(
			HttpServletRequest request, String ids) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user==null){
			return ajaxForwardError("session失效，请重新登陆!", "", null, null);
		}
		String[] idStr = ids.split(",");
		for (String id : idStr) {
			JSONObject json = new JSONObject();
			json.put("id", id.trim());
			json.put("userName", user.getId());
			String resultString = sendRequestService.sendRequest(
					ConfigManager.DELETEOUTMONEY, json);
			DataMessage dataMessage = MessageUtil
					.parseDataMessage(resultString);
			if (ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage
					.getResult())) {
				continue;
			} else {
				return ajaxForwardError(dataMessage.getMsg(), "", null, null);
			}
		}
		return ajaxForwardError("删除成功", "", null, null);
	}

	/**
	 * 转账提现对账界面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/reconciliationOutMoney")
	public ModelAndView reconciliationOutMoney(HttpServletRequest reques,
			String orderNo) {
		ModelAndView mav = new ModelAndView(
				"trading/outMoney/outMoneyReconciliation");
		JSONObject json = new JSONObject();
		json.put("orderNo", orderNo.trim());
		String resultString = sendRequestService.sendRequest(
				ConfigManager.OUTMONEYFLOW_RECONCILIATIONSEARCH, json);
		DataMessage dataMessage = JSON.parseObject(resultString,
				DataMessage.class);
		DateUtil.formatDateByDataMessage(dataMessage);
		mav.addObject("dataMessage", dataMessage);
		return mav;
	}


	/**
	 * 对账修复
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateOutMoneyStatus")
	public @ResponseBody DwzAjaxController updateOutMoneyStatus(
			HttpServletRequest reques, String orderNo, String status,
			String date,String id) {
		JSONObject json = new JSONObject();
		json.put("orderNo", orderNo.trim());
		json.put("result_pay",
				status.equals(OutMoneyController.STATUS_SUCCESS) ? "SUCCESS"
						: "FAILURE");
		String paySuccessString = sendRequestService.sendRequest(
				ConfigManager.OUTMONEY_UPDATEPAYSUCCESS, json);
		DataMessage dataMessage = JSON.parseObject(paySuccessString,
				DataMessage.class);
		if (ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage
				.getResult())) {
			JSONObject updateIsReconciliationJson = new JSONObject();
			updateIsReconciliationJson.put("outMoneyId", id);
			String updateIsReconciliationString = sendRequestService.sendRequest(
					ConfigManager.UPDATERECONCILIATIONBYID, updateIsReconciliationJson);
			DataMessage updateIsReconciliationMessage = JSON.parseObject(updateIsReconciliationString,
					DataMessage.class);
			if (ManageConfig.DATE_MESSAGE_FAIL.equalsIgnoreCase(updateIsReconciliationMessage
					.getResult())) {
				return ajaxForwardError(updateIsReconciliationMessage.getMsg(), "", null, null);
			}
			return ajaxForwardSuccess("修复成功", "", null, null);
		} else {
			return ajaxForwardError(dataMessage.getMsg(), "", null, null);
		}
	}

	/**
	 * 导出excel文件
	 * 
	 * @param ids
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/dwlExport")
	public void downLoadExcport(HttpServletRequest request,
			HttpServletResponse response, String ids)
			throws UnsupportedEncodingException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("信息");
		// sheet.autoSizeColumn(0); // 调整第一列宽度
		// sheet.autoSizeColumn(1); // 调整第二列宽度
		// sheet.autoSizeColumn(2); // 调整第三列宽度
		// sheet.autoSizeColumn(3); // 调整第四列宽度
		HSSFRow row = sheet.createRow(0);
		// HSSFDataFormat format = wb.createDataFormat();
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// style.setDataFormat(format.getFormat("0.00"));
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("*日期");
		cell.setCellStyle(style);
		cell = row.createCell(1);
		cell.setCellValue("*总金额");
		cell.setCellStyle(style);
		cell = row.createCell(2);
		cell.setCellValue("*总笔数");
		cell.setCellStyle(style);
		// 得到数据
		JSONObject json = new JSONObject();
		json.put("ids", ids);
		String resultString = sendRequestService.sendRequest(
				ConfigManager.OUTMONEYBYIDS, json);
		DataPageMessage dataMessage = MessageUtil
				.parseDateListMessage(resultString);
		List<Map<String, String>> resultList = dataMessage.getContentList();
		List<Map<String, String>> allResultList = new ArrayList<Map<String, String>>();
		BigDecimal sumMoney = BigDecimal.ZERO;
		for (int k = 0; k < resultList.size(); k++) {
			Map<String, String> resutltMap = resultList.get(k);
			String money = resutltMap.get("money");
			if (StringUtils.isEmpty(resutltMap.get("feeProvider"))) {
				sumMoney = sumMoney.add(new BigDecimal(resutltMap.get("fee")));
			}
			sumMoney = sumMoney.add(new BigDecimal(money));
			JSONObject json3 = new JSONObject();
			json3.put("id", resutltMap.get("id"));
			json3.put("customerId", resutltMap.get("customerId"));
			json3.put("cardNo", resutltMap.get("cardid"));
			String customerInfoString = sendRequestService.sendRequest(
					ConfigManager.CUSTOMERLC_FINDBYCUSTOMERIDANDCARDNO, json3);
			DataMessage customerInfoMessage = MessageUtil
					.parseDataMessage(customerInfoString);
			Map<String, String> customerCardInfoMap = customerInfoMessage
					.getData();
			Map<String, String> allResutltMap = new HashMap<String, String>();
			allResutltMap.put("orderNo", resutltMap.get("orderNo"));
			allResutltMap.put("money", resutltMap.get("money"));
			allResutltMap.put("cardFlag", customerCardInfoMap.get("cardFlag"));
			allResutltMap.put("acctName", customerCardInfoMap.get("acctName"));
			allResutltMap.put("cardNo", customerCardInfoMap.get("cardNo"));
			allResutltMap.put("bankCode", customerCardInfoMap.get("bankCode"));
			allResutltMap.put("provinceName",
					customerCardInfoMap.get("provinceName"));
			allResutltMap.put("cityName", customerCardInfoMap.get("cityName"));
			allResutltMap.put("brabankName",
					customerCardInfoMap.get("brabankName"));
			allResutltMap.put("brabankCode",
					customerCardInfoMap.get("brabankCode"));
			allResultList.add(allResutltMap);
		}
		row = sheet.createRow((int) 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		row.createCell(0).setCellValue(sdf.format(new Date()));
		row.createCell(1).setCellValue(FromatMoneyUtil.fromatMoney(sumMoney.doubleValue()));
		row.createCell(2).setCellValue(allResultList.size());

		HSSFRow row2 = sheet.createRow(2);
		String[] strList = new String[] { "*商户流水号", "*1-对公/0-对私", "*收款方开户名",
				"*收款银行账号", "*金额(精确到分)", "银行编号", "收款银行所在省份", "收款银行所在市",
				"收款支行名称", "联行号(大额行号)", "备注" };
		for (int i = 0; i < strList.length - 1; i++) {
			HSSFCell cell2 = row2.createCell(i);
			cell2.setCellValue(strList[i]);
			cell2.setCellStyle(style);
			sheet.setColumnWidth(i, 5300);
		}
		for (int y = 0; y < allResultList.size(); y++) {
			Map<String, String> resutltMap = allResultList.get(y);
			String investProductOrderId = resutltMap.get("orderNo");// 商户流水号
			String cardFlag = resutltMap.get("cardFlag");// 1-对公/0-对私
			String acctName = resutltMap.get("acctName");// 收款方开户名
			String cardNo = resutltMap.get("cardNo");// 收款银行账号
			String outMoney = resutltMap.get("money");// 金额(精确到分)
			String bankCode = resutltMap.get("bankCode");// 银行编号
			String provinceName = resutltMap.get("provinceName");// 收款银行所在省份
			String cityName = resutltMap.get("cityName");// 收款银行所在
			String brabankName = resutltMap.get("brabankName");// 收款支行名称
			String brabankCode = resutltMap.get("brabankCode");// 联行号(大额行号)
			String remark = resutltMap.get("");// 备注

			Double money = 0.0D;
			if (null != outMoney) {
				money = Double.parseDouble(outMoney);
			}

			row2 = sheet.createRow(y + 3);
			sheet.setColumnWidth(y, 5300);
			row2.createCell(0).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			row2.createCell(0).setCellValue(investProductOrderId);
			row2.createCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
			row2.createCell(1).setCellValue("1".equals(cardFlag) ? "对公" : "对私");
			row2.createCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
			row2.createCell(2).setCellValue(acctName);
			row2.createCell(3).setCellType(HSSFCell.CELL_TYPE_STRING);
			row2.createCell(3).setCellValue(cardNo);
			row2.createCell(4).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			row2.createCell(4).setCellValue(money);
			row2.createCell(5).setCellType(HSSFCell.CELL_TYPE_STRING);
			row2.createCell(5).setCellValue(bankCode);
			row2.createCell(6).setCellType(HSSFCell.CELL_TYPE_STRING);
			row2.createCell(6).setCellValue(provinceName);
			row2.createCell(7).setCellType(HSSFCell.CELL_TYPE_STRING);
			row2.createCell(7).setCellValue(cityName);
			row2.createCell(8).setCellType(HSSFCell.CELL_TYPE_STRING);
			row2.createCell(8).setCellValue(brabankName);
			row2.createCell(9).setCellType(HSSFCell.CELL_TYPE_STRING);
			row2.createCell(9).setCellValue(brabankCode);
			row2.createCell(10).setCellType(HSSFCell.CELL_TYPE_STRING);
			row2.createCell(10).setCellValue(remark);
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="
				+ new String("取现订单记录".getBytes("gb2312"), "iso8859-1") + ".xls");
		OutputStream ouputStream;
		try {
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	@RequestMapping("/dwlFinanceExport")
	public void downLoadFinanceExcport(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String resultString = sendRequestService.sendRequest(ConfigManager.OUTMONEY_LIST, jsonObject);
		DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
		DateUtil.formatDateByDataPageMessage(dataMessage);

		for (int i = 2; i < dataMessage.getPage().getTotalCount() / 20 + 2; i++) {
			JSONObject jsonObjects = RequestUtil.fromRequestToJson(request);
			jsonObjects.put("pageNum", i);
			String resultStrings = sendRequestService.sendRequest(ConfigManager.OUTMONEY_LIST, jsonObjects);
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
			cell0.setCellValue(new HSSFRichTextString("提现订单对照表"));
			cell0.setCellStyle(sheetStyle);
			CellRangeAddress range = new CellRangeAddress(0, 0, 0, 7);
			sheet.addMergedRegion(range);
			// 创建第二行
			HSSFRow row2 = sheet.createRow(2);
			String[] strList = new String[] { "订单号", "金额（RMB）", "交易时间", "银行缩写", "银行卡号",
					"提现费用", "费用提供","状态" };
			for (int i = 0; i < strList.length; i++) {
				HSSFCell cell2 = row2.createCell(i);
				cell2.setCellValue(strList[i]);
				cell2.setCellStyle(sheetStyle);
				sheet.setColumnWidth(i, 5800);
			}
			// 创建n行
			BigDecimal money = BigDecimal.ZERO;
			BigDecimal fee = BigDecimal.ZERO;
			for (int i = 0; i < dataMessage.getContentList().size(); i++) {
				Map<String, String> resutltMap = dataMessage.getContentList()
						.get(i);
				HSSFRow row = sheet.createRow(i + 3);
				sheet.setColumnWidth(i, 5800);
				row.createCell(0).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				row.createCell(0).setCellValue(resutltMap.get("orderNo"));
				row.createCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
				money = money
						.add(BigDecimal.valueOf(Double.parseDouble(resutltMap
								.get("money") != null ? resutltMap.get("money")
								: new String("0.0"))));
				row.createCell(1)
						.setCellValue(
								Double.parseDouble(resutltMap.get("money") != null ? resutltMap
										.get("money") : new String("0.0")));
				row.createCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
				row.createCell(2).setCellValue(resutltMap.get("timeTrading"));
				row.createCell(3).setCellType(HSSFCell.CELL_TYPE_STRING);
				row.createCell(3).setCellValue(
						DictionaryMap.BANK_MAP.get(resutltMap.get("bank")));
				row.createCell(4).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				row.createCell(4).setCellValue(resutltMap.get("cardid"));
				row.createCell(5).setCellType(HSSFCell.CELL_TYPE_STRING);
				row.createCell(5).setCellValue(resutltMap.get("fee"));
				if (DictionaryMap.OUTMONEY_FEEPROVIDER.get(
						resutltMap.get("feeProvider")).equals("平台")) {
					fee = fee
							.add(BigDecimal.valueOf(Double.parseDouble(resutltMap
									.get("fee") != null ? resutltMap.get("fee")
									: new String("0.0"))));
				}
				row.createCell(6).setCellType(HSSFCell.CELL_TYPE_STRING);
				row.createCell(6).setCellValue(
						DictionaryMap.OUTMONEY_FEEPROVIDER.get(resutltMap
								.get("feeProvider")));
				row.createCell(7).setCellType(HSSFCell.CELL_TYPE_STRING);
				row.createCell(7).setCellValue(
						DictionaryMap.OUTMONEY_FLAG.get(resutltMap
								.get("status")));
			}
			// 列尾
			int footRownumber = 0;
			HSSFRow footRow = sheet.createRow(footRownumber + 1);
			footRow.setHeight((short) 450);
			HSSFCell footRowcell = footRow.createCell(0);
			footRowcell.setCellValue(new HSSFRichTextString("导出总条数"
					+ dataMessage.getPage().getTotalCount() + "条    总交易金额："
					+ FromatMoneyUtil.fromatMoney(money.doubleValue()) + "元     平台提现费用：" + fee + "元     导出时间为："
					+ DateHelper.timeToString(new Date())));
			footRowcell.setCellStyle(sheetStyle);
			range = new CellRangeAddress(footRownumber + 1, footRownumber + 1,
					0, 7);
			sheet.addMergedRegion(range);

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ new String("提现订单记录".getBytes("gb2312"), "iso8859-1")
					+ ".xls");
			OutputStream ouputStream = response.getOutputStream();
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
			workbook.close();
		} catch (Exception e) {

		}
	}
	
	private boolean triggerWarning(BigDecimal money,BigDecimal buySumMoney){
		if(money.compareTo(buySumMoney.multiply(new BigDecimal("1.15")))==1){
			return true;
		}
		return false;
	}
}
