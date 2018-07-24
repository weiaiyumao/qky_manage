package com.zqfinance.module.trading.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
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
@RequestMapping("/companyOrder")
public class CompanyOrderController extends DwzAjaxController {
	
	/**
	 * 公司充值列表页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCompanyOrderList")
	public ModelAndView getCompanyOrderList(HttpServletRequest request){
		
		HttpSession session =  request.getSession(false);
		com.zqfinance.module.sys.domain.User user = null;
		if(null != session){
			user = (com.zqfinance.module.sys.domain.User) session.getAttribute(ManageConfig.LOGIN_USER);
			if(null != user){
				//保存成功登陆日志
				Loginfo loginfo = new Loginfo();
				loginfo.setUserId(user.getId());
				loginfo.setUserName(user.getUserName());
				loginfo.setModuleName("公司订单管理");
				loginfo.setLoginStatus(ManageConfig.loginStatus_SUCCESS);
				loginfoService.addLoginfo(loginfo);
			}
		}
		
		ModelAndView modelAndView = new ModelAndView("trading/product/companyOrder/companyOrderList");
		JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String resultString = sendRequestService.sendRequest(ConfigManager.COMPANYORDER_GETCOMPANYORDERPAGEVIEW,jsonObject);
		if(StringUtils.isNotEmpty(resultString)){
			DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
			DateUtil.formatDateByDataPageMessage(dataMessage);
			modelAndView.addObject("dataMessage", dataMessage);
		}
		modelAndView.addObject("minMoney", jsonObject.get("minMoney"));
		modelAndView.addObject("maxMoney", jsonObject.get("maxMoney"));
		modelAndView.addObject("orderId", jsonObject.get("orderId"));
		modelAndView.addObject("status", jsonObject.get("status"));
		return modelAndView;
	}
	
	@RequestMapping("/forwordCreateOrderPage")
	public ModelAndView forwordCreateOrderPage(HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView("trading/product/companyOrder/companyOrderAdd");
		//取得所有商品的信息
		String resultString = sendRequestService.sendRequest(ConfigManager.INVESTPRODUCT_GETALL,new JSONObject());
		if(StringUtils.isNotEmpty(resultString)){
			DataPageMessage dataMessage = MessageUtil.parseDateListMessage(resultString);
			List<Map<String,String>> productList = dataMessage.getContentList();	
			modelAndView.addObject("productList", productList);
		}
	
		return modelAndView;
	}
	
	/**
	 * 保存公司充值订单信息
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@ResponseBody	
	@RequestMapping("/saveCompanyOrder")
	public DwzAjaxController saveCompnayOrder(HttpServletRequest request) throws ParseException{
		String money = request.getParameter("money");		
		User user = (User)request.getSession().getAttribute(ManageConfig.LOGIN_USER);
		JSONObject jsonObejct = new JSONObject();
		jsonObejct.put("money", money);
		jsonObejct.put("customerId", user.getId());
		String resultString = sendRequestService.sendRequest(ConfigManager.COMPANYORDER_CREATE,jsonObejct);
		if(StringUtils.isNotEmpty(resultString)){
			DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
			if(null != dataMessage && ManageConfig.DATE_MESSAGE_SUCCESS.equals(dataMessage.getResult())){
				Map<String,String> orderMap = dataMessage.getData();
				String orederId = orderMap.get("orderId");
				String orderDate = orderMap.get("timeCreate");
				Calendar calendar= Calendar.getInstance();
				calendar.setTimeInMillis(Long.valueOf(orderDate));
				String dtOrder = DateUtil.COMPAT_FULL.getDateText(calendar.getTime());
				List<Object> idList = new ArrayList<Object>();			
				idList.add(orederId);
				idList.add(dtOrder);
				return ajaxForwardSuccess(null, "",ManageConfig.DWZ_CALLBACK_TYPE_FORWARD, idList);
			}
		}
		return ajaxForwardError("生成订单失败", "/companyOrder/sendPay", ManageConfig.DWZ_CALLBACK_TYPE_FORWARD, null);
	}
	
	@RequestMapping("/sendPay")
	public void sendPay(HttpServletRequest request,HttpServletResponse response){
		String orderId = request.getParameter("orderId");
		String money = request.getParameter("money");
		String acctName = request.getParameter("acctName");
		String idNo = request.getParameter("idNo");
		String productName = request.getParameter("productName");
		String orderDate = request.getParameter("orderDate");
		String bankCode = request.getParameter("bankCode");
		User user = (User)request.getSession().getAttribute(ManageConfig.LOGIN_USER);
		if(null != user ){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("user_id", user.getId());
			jsonObject.put("no_order", orderId);
			jsonObject.put("name_goods", productName);
			jsonObject.put("money_order", money);
			jsonObject.put("rege_time", DateUtil.COMPAT_FULL.getDateText(user.getCreateTime()));
			jsonObject.put("acctName", acctName);
			jsonObject.put("idNo", idNo);
			jsonObject.put("order_date", orderDate);
			jsonObject.put("bank_code", bankCode);
			sendRequestService.redirectRequest(response, ConfigManager.BANKGATE_PAY, jsonObject);
		}
	}
	
	/**
	 * 订单对账（只对订单记录修复，不会向对账表添加数据）
	 * @param noOrder
	 * @return
	 */
	@RequestMapping("/queryOrderPayStatus")
	public @ResponseBody DwzAjaxController queryOrderPayStatus(String id){
		// order对象
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id.trim());
		String order_resultString = sendRequestService.sendRequest(ConfigManager.COMPANYORDER_FINDBYID, jsonObject);
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
				String orderResult = sendRequestService.sendRequest(ConfigManager.COMPANYORDER_UPDATEORDERSTAUTS, updateStatus);
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
}
