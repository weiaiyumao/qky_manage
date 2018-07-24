package com.zqfinance.module.trading.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

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
import com.zqfinance.system.util.DateUtil;
import com.zqfinance.system.util.MessageUtil;
import com.zqfinance.system.util.RequestUtil;


@Controller
@RequestMapping("/companyOutMoney")
public class CompanyOutMoneyController extends DwzAjaxController{
	
	public static final String STATUS_PROCESS = "0"; //待处理
	public static final String STATUS_SUCCESS = "1"; //放款成功
	public static final String STATUS_FAIL = "2"; //放款失败
	public static final String STATUS_LOAN = "3"; //放款中
	public static final String STATUS_ADUIT = "4"; //审核通过

	
	/**
	 * 添加公司提现记录Page
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addCompanyOutMoneyPage")
	public ModelAndView addCompanyOutMoneyPage(HttpServletRequest request) {
		
		HttpSession session =  request.getSession(false);
		com.zqfinance.module.sys.domain.User user = null;
		if(null != session){
			user = (com.zqfinance.module.sys.domain.User) session.getAttribute(ManageConfig.LOGIN_USER);
			if(null != user){
				//保存成功登陆日志
				Loginfo loginfo = new Loginfo();
				loginfo.setUserId(user.getId());
				loginfo.setUserName(user.getUserName());
				loginfo.setModuleName("公司提现管理");
				loginfo.setLoginStatus(ManageConfig.loginStatus_SUCCESS);
				loginfoService.addLoginfo(loginfo);
			}
		}
		
		ModelAndView modelAndView = new ModelAndView(
				"trading/companyOutMoney/companyOutMoneyAdd");
		 JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		 String resultString = sendRequestService.sendRequest(ConfigManager.COMPANYOUTMONEYBANK_FINDBYPAGE,jsonObject);
		 DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
		 DateUtil.formatDateByDataPageMessage(dataMessage);
		 modelAndView.addObject("bankMessage", dataMessage);
		return modelAndView;
	}
	
	/**
	 * 审核公司提现记录Page
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/aduitCompanyOutMoneyPage")
	public ModelAndView aduitCompanyOutMoneyPage(HttpServletRequest request,String id) {
		ModelAndView modelAndView = new ModelAndView(
				"trading/companyOutMoney/companyOutMoneyAduit");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		String outMoneyString = sendRequestService.sendRequest(
				ConfigManager.COMPANYOUTMONEYBYID, jsonObject);
		DataMessage outMoneyDataMessage = MessageUtil
				.parseDataMessage(outMoneyString);
		if (ManageConfig.DATE_MESSAGE_SUCCESS.equals(outMoneyDataMessage
				.getResult())) {
			modelAndView.addObject("companyOutMoneyDataMessage", outMoneyDataMessage);
			 Map<String, String> companyOutMoneyMap=outMoneyDataMessage.getData();
			String bankId=companyOutMoneyMap.get("bankId");
			JSONObject bankJsonObject = new JSONObject();
			bankJsonObject.put("id", bankId);
			String bankString = sendRequestService.sendRequest(
					ConfigManager.COMPANYOUTMONEYBANK_FINDBYID, bankJsonObject);
			DataMessage bankDataMessage = MessageUtil
					.parseDataMessage(bankString);
			if (ManageConfig.DATE_MESSAGE_SUCCESS.equals(bankDataMessage
					.getResult())) {
				modelAndView.addObject("bankDataMessage", bankDataMessage);
			}
		}
		return modelAndView;
	}
	
	
	/**
	 * 审核公司提现记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/aduitCompanyOutMoney")
	public @ResponseBody DwzAjaxController aduitCompanyOutMoneyList(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Long customerId=user.getId();
		String orderId=request.getParameter("orderId");
		JSONObject aduitJson = new JSONObject();
		aduitJson.put("customerId", customerId);
		aduitJson.put("orderId", orderId);
		String aduitString = sendRequestService.sendRequest(
				ConfigManager.COMPANYOUTMONEYBYID_AUDIT, aduitJson);
		DataPageMessage aduitMessage = MessageUtil
				.parseDatePageMessage(aduitString);
		if(ManageConfig.DATE_MESSAGE_FAIL.equals(aduitMessage
						.getResult())){
			return ajaxForwardError(aduitMessage.getMsg(), "", null,
					null);
		}
		return ajaxForwardSuccess("审核成功", "", null, null);
	}
	
	
	/**
	 * 添加公司提现记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addCompanyOutMoney")
	public @ResponseBody DwzAjaxController addCompanyOutMoneyList(HttpServletRequest request) {
		 JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Long customerId=user.getId();
		JSONObject addJson = new JSONObject();
		addJson.putAll(jsonObject);
		addJson.put("customerId", customerId);
		String addString = sendRequestService.sendRequest(
				ConfigManager.COMPANYOUTMONEY_SAVE, addJson);
		DataPageMessage addMessage = MessageUtil
				.parseDatePageMessage(addString);
		if(ManageConfig.DATE_MESSAGE_FAIL.equals(addMessage
						.getResult())){
			return ajaxForwardError(addMessage.getMsg(), "", null,
					null);
		}
		return ajaxForwardSuccess("操作成功", "", null, null);
	}
	
	
	/**
	 * 获得转账提现列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCompanyOutMoneyList")
	public ModelAndView getCompanyOutMoneyList(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView(
				"trading/companyOutMoney/companyOutMoneyList");
		JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String resultString = sendRequestService.sendRequest(
				ConfigManager.COMPANYOUTMONEY_LIST, jsonObject);
		DataPageMessage dataMessage = MessageUtil
				.parseDatePageMessage(resultString);
		DateUtil.formatDateByDataPageMessage(dataMessage);
		modelAndView.addObject("dataMessage", dataMessage);
		modelAndView.addObject("minMoney", jsonObject.get("minMoney"));
		modelAndView.addObject("maxMoney", jsonObject.get("maxMoney"));
		modelAndView.addObject("beginDate", jsonObject.get("beginDate"));
		modelAndView.addObject("endDate", jsonObject.get("endDate"));
		modelAndView.addObject("flag", jsonObject.get("flag"));
		modelAndView.addObject("orderId", jsonObject.get("orderId"));
		return modelAndView;
	}
	
	/**
	 * 转账提现自动放款
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/payCompanyOutMoney")
	public @ResponseBody DwzAjaxController payCompanyOutMoney(
			HttpServletRequest request, String ids) {
		String[] idStr = ids.split(",");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		for (String id : idStr) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", id);
			String outMoneyString = sendRequestService.sendRequest(
					ConfigManager.COMPANYOUTMONEYBYID, jsonObject);
			DataMessage outMoneyDataMessage = MessageUtil
					.parseDataMessage(outMoneyString);
			if (CompanyOutMoneyController.STATUS_SUCCESS.equals(outMoneyDataMessage
					.getData().get("status"))) {
				return ajaxForwardError("已放款成功，不能重复放款", "", null, null);
			}
			if (CompanyOutMoneyController.STATUS_FAIL.equals(outMoneyDataMessage
					.getData().get("status"))) {
				return ajaxForwardError("已放款失败，不能做此操作", "", null, null);
			}
			if (CompanyOutMoneyController.STATUS_LOAN.equals(outMoneyDataMessage
					.getData().get("status"))) {
				return ajaxForwardError("正在放款中，不能做此操作", "", null, null);
			}
			if (CompanyOutMoneyController.STATUS_PROCESS.equals(outMoneyDataMessage
					.getData().get("status"))) {
				return ajaxForwardError("需要审核通过才能放款，不能做此操作", "", null, null);
			}
			JSONObject json = new JSONObject();
			json.put("id", id);
			String resultString = sendRequestService.sendRequest(
					ConfigManager.COMPANYOUTMONEY_GETCOMPANYBANKANDCOMPANYOUTMONEY, json);
			DataMessage dataMessage = MessageUtil
					.parseDataMessage(resultString);
			Map<String, String> map = dataMessage.getData();
			map.put("info_order", ConfigManager.OUTMONEY_LOAN_INFOORDER);
			map.put("flag_card", ConfigManager.CARD_FLAG_PRI);
			map.put("userId", String.valueOf(user.getId()));
			JSONObject requestJson = JSONObject.fromObject(map);
			String payResultString = sendRequestService.sendRequest(
					ConfigManager.COMPANYOUTMONEY_LOANPAY, requestJson);
			if (StringUtils.isNotEmpty(payResultString)) {
				DataMessage payDataMessage = MessageUtil
						.parseDataMessage(payResultString);
				if (ManageConfig.DATE_MESSAGE_FAIL.equals(payDataMessage
						.getResult())) {
					 
					return ajaxForwardError(payDataMessage.getMsg(), "", null,
							null);
				}
			}

		}
		return ajaxForwardSuccess("连连代付订单申请成功", "", null, null);
	}
	
	
	/**
	 * 公司转账提现自动对账
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/autoReconciliationCompanyOutMoney")
	public @ResponseBody DwzAjaxController autoReconciliationCompanyOutMoney(HttpServletRequest request, String ids){
		String[] idStr = ids.split(",");
		for (String id : idStr) {
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", id);
			String outMoneyString = sendRequestService.sendRequest(
					ConfigManager.COMPANYOUTMONEYBYID, jsonObject);
			DataMessage outMoneyDataMessage = MessageUtil
					.parseDataMessage(outMoneyString);
			if (CompanyOutMoneyController.STATUS_PROCESS.equals(outMoneyDataMessage.getData().get("status"))
					||CompanyOutMoneyController.STATUS_ADUIT.equals(outMoneyDataMessage.getData().get("status"))) {
				return ajaxForwardError("取现订单状态不符合对账要求", "", null, null);
			}
			JSONObject json2 = new JSONObject();
			json2.put("id", id);
			String resultString = sendRequestService.sendRequest(
					ConfigManager.COMPANYOUTMONEY_GETCOMPANYBANKANDCOMPANYOUTMONEY, json2);
			DataMessage dataMessage = MessageUtil
					.parseDataMessage(resultString);
			if(ManageConfig.DATE_MESSAGE_FAIL.equals(dataMessage
					.getResult())){
				return ajaxForwardError(dataMessage.getMsg(), "", null,
						null);
			}
			Map<String, String> map = dataMessage.getData();
			JSONObject requestJson = JSONObject.fromObject(map);
			String payResultString = sendRequestService.sendRequest(
					ConfigManager.OUTMONEY_AUTORECONCILIATION, requestJson);
			DataMessage payDataMessage = MessageUtil
					.parseDataMessage(payResultString);
			if (ManageConfig.DATE_MESSAGE_FAIL.equals(payDataMessage
					.getResult())) {
				return ajaxForwardError(payDataMessage.getMsg(), "", null,
						null);
			}
			Map<String, String> resultMap=payDataMessage.getData();
			String result_pay=resultMap.get("result_pay");
			String no_order=resultMap.get("no_order");
			String info_order=resultMap.get("info_order");
//			String oid_partner=resultMap.get("oid_partner");
//			String dt_order=resultMap.get("dt_order");
//			String oid_paybill=resultMap.get("oid_paybill");
//			String money_order=resultMap.get("money_order");
//			String settle_date=resultMap.get("settle_date");
//			String pay_type=resultMap.get("pay_type");
//			String bank_code=resultMap.get("bank_code");
//			String bank_name=resultMap.get("bank_name");
//			String memo=resultMap.get("memo");
			
			JSONObject updateJson = new JSONObject();
			updateJson.put("orderId", no_order.trim());
			updateJson.put("result_pay",result_pay);
			updateJson.put("info_order",info_order);
			String paySuccessString = sendRequestService.sendRequest(
					ConfigManager.COMPANYOUTMONEY_UPDATEAFTERPAYSUCCESS, updateJson);
			DataMessage updateJsonMessage = JSON.parseObject(paySuccessString,
					DataMessage.class);
			if (ManageConfig.DATE_MESSAGE_FAIL.equalsIgnoreCase(updateJsonMessage
					.getResult())) {
				return ajaxForwardError(updateJsonMessage.getMsg(), "", null, null);
			}
		}
		return ajaxForwardSuccess("自动对账修复成功", "", null, null);
	}
	
}
