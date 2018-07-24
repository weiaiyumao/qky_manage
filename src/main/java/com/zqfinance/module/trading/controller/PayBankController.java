package com.zqfinance.module.trading.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

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
import com.zqfinance.system.util.DateUtil;
import com.zqfinance.system.util.MessageUtil;
import com.zqfinance.system.util.RequestUtil;

@Controller
@RequestMapping("/PayBank")
public class PayBankController extends DwzAjaxController{
	
	
	/**
	 * 获取列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPayBankList")
	public ModelAndView getPayBankList(HttpServletRequest request){	
		
		HttpSession session =  request.getSession(false);
		com.zqfinance.module.sys.domain.User user = null;
		if(null != session){
			user = (com.zqfinance.module.sys.domain.User) session.getAttribute(ManageConfig.LOGIN_USER);
			if(null != user){
				//保存成功登陆日志
				Loginfo loginfo = new Loginfo();
				loginfo.setUserId(user.getId());
				loginfo.setUserName(user.getUserName());
				loginfo.setModuleName("连连支持银行卡管理");
				loginfo.setLoginStatus(ManageConfig.loginStatus_SUCCESS);
				loginfoService.addLoginfo(loginfo);
			}
		}
		
		ModelAndView modelAndView = new ModelAndView("trading/payBank/payBankList");
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String resultString = sendRequestService.sendRequest(ConfigManager.PAYBANK_FINDPAGE,jsonObject);
		DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
		DateUtil.formatDateByDataPageMessage(dataMessage);
		modelAndView.addObject("dataMessage", dataMessage);
		modelAndView.addObject("jsonObject",jsonObject);
		return modelAndView;
	}
	
	/**
	 * 跳转添加页面
	 * @return
	 */
	@RequestMapping("/forwardAdd")
	public ModelAndView forwardAdd(){
		return new ModelAndView("trading/payBank/payBankAdd");
	}
	
	/**
	 * 添加
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPayBank")
	public @ResponseBody DwzAjaxController addPayBank(HttpServletRequest request){		
		JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String resultString = sendRequestService.sendRequest(ConfigManager.PAYBANK_SAVE,jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			return this.ajaxForwardSuccess("添加支持银行成功！","",ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT,null);
		}else{
			return this.ajaxForwardError(dataMessage.getMsg(), null, null, null);
		}
	}
	
	/**
	 * 跳转编辑页面
	 * @param investProductId
	 * @return
	 */
	@RequestMapping("/forwardEdit")
	public ModelAndView forwardEdit(String id){	
		ModelAndView modelAndView = new ModelAndView("trading/payBank/payBankEdit");
		JSONObject jsonObject = new JSONObject();
	    jsonObject.put("id", id.trim());
	    String resultString = sendRequestService.sendRequest(ConfigManager.PAYBANK_FINDBYID,jsonObject);
	    DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
	    DateUtil.formatDateByDataMessage(dataMessage);
		modelAndView.addObject("dataMessage", dataMessage);
		return modelAndView;
	}
	
	/**
	 * 跳转详情页面
	 * @param investProductId
	 * @return
	 */
	@RequestMapping("/forwardInfo")
	public ModelAndView forwardInfo(String id){	
		ModelAndView modelAndView = new ModelAndView("trading/payBank/payBankInfo");
		JSONObject jsonObject = new JSONObject();
	    jsonObject.put("id", id.trim());
	    String resultString = sendRequestService.sendRequest(ConfigManager.PAYBANK_FINDBYID,jsonObject);
	    DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
	    DateUtil.formatDateByDataMessage(dataMessage);
		modelAndView.addObject("dataMessage", dataMessage);
		return modelAndView;
	}
	
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatePayBank")
	public @ResponseBody DwzAjaxController updatePayBank(HttpServletRequest request){		
		JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String resultString = sendRequestService.sendRequest(ConfigManager.PAYBANK_UPDATE,jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			return this.ajaxForwardSuccess("修改支持银行成功！","",ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT,null);
		}else{
			return this.ajaxForwardError(dataMessage.getMsg(), null, null, null);
		}
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delPayBank")
	public @ResponseBody DwzAjaxController delPayBank(String id){		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id.trim());
		String resultString = sendRequestService.sendRequest(ConfigManager.PAYBANK_DELBYID,jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			return this.ajaxForwardSuccess("删除连连支持银行成功！","",null,null);
		}else{
			return this.ajaxForwardError(dataMessage.getMsg(), null, null, null);
		}
	}
	
	/**
	 * 更新限额
	 * @param ids
	 * @return
	 */
	@RequestMapping("/updatePayBankMoney")
	public @ResponseBody DwzAjaxController updatePayBankMoney(){		
		JSONObject jsonObject = new JSONObject();
		String resultString = sendRequestService.sendRequest(ConfigManager.PAYBANK_UPATEPAYBANSTATUS,jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			return this.ajaxForwardSuccess("更新银行限额成功！","",null,null);
		}else{
			return this.ajaxForwardError(dataMessage.getMsg(), null, null, null);
		}
	}
	
	
}
