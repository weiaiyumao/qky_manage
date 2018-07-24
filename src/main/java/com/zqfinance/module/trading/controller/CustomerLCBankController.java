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
import com.zqfinance.system.util.SpringUtil;

@Controller
@RequestMapping("/customerlc")
public class CustomerLCBankController extends DwzAjaxController {
	
	/**
	 * 获取列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCustomerLCBankList")
	public ModelAndView getCustomerLCBankList(HttpServletRequest request){	
		
		HttpSession session =  request.getSession(false);
		com.zqfinance.module.sys.domain.User user = null;
		if(null != session){
			user = (com.zqfinance.module.sys.domain.User) session.getAttribute(ManageConfig.LOGIN_USER);
			if(null != user){
				//保存成功登陆日志
				Loginfo loginfo = new Loginfo();
				loginfo.setUserId(user.getId());
				loginfo.setUserName(user.getUserName());
				loginfo.setModuleName("客户银行卡管理");
				loginfo.setLoginStatus(ManageConfig.loginStatus_SUCCESS);
				loginfoService.addLoginfo(loginfo);
			}
		}
		
		ModelAndView modelAndView = new ModelAndView("trading/customerLC/customerLCBankList");
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String resultString = sendRequestService.sendRequest(ConfigManager.CUSTOMERLC_FINDBYCONDITION,jsonObject);
		DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
		DateUtil.formatDateByDataPageMessage(dataMessage);
		SpringUtil.formatDateByDataPageMessage(dataMessage);
		modelAndView.addObject("dataMessage", dataMessage);
		modelAndView.addObject("beginDate",jsonObject.get("beginDate"));
		modelAndView.addObject("endDate",jsonObject.get("endDate"));
		modelAndView.addObject("idNo",jsonObject.get("idNo"));
		modelAndView.addObject("acctName",jsonObject.get("acctName"));
		modelAndView.addObject("bankCode",jsonObject.get("bankCode"));
		modelAndView.addObject("cardNo",jsonObject.get("cardNo"));
		modelAndView.addObject("bankName", jsonObject.get("bankName"));
		modelAndView.addObject("idType", jsonObject.get("idType"));
		modelAndView.addObject("cardType", jsonObject.get("cardType"));
		return modelAndView;
	}
	
	/**
	 * 跳转详情页面
	 * @param investProductId
	 * @return
	 */
	@RequestMapping("/forwardInfo")
	public ModelAndView forwardInfo(String id){	
		ModelAndView modelAndView = new ModelAndView("trading/customerLC/customerLCBankInfo");
		JSONObject jsonObject = new JSONObject();
	    jsonObject.put("id", id.trim());
	    String resultString = sendRequestService.sendRequest(ConfigManager.CUSTOMERCL_GETBYID,jsonObject);
	    DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
	    DateUtil.formatDateByDataMessage(dataMessage);
		modelAndView.addObject("dataMessage", dataMessage);
		return modelAndView;
	}
	
	/**
	 * 跳转编辑页面
	 * @param investProductId
	 * @return
	 */
	@RequestMapping("/forwardEdit")
	public ModelAndView forwardEdit(String id){
		ModelAndView modelAndView = new ModelAndView("trading/customerLC/customerLCBankEdit");
		JSONObject jsonObject = new JSONObject();
	    jsonObject.put("id", id.trim());
	    String resultString = sendRequestService.sendRequest(ConfigManager.CUSTOMERCL_GETBYID,jsonObject);
	    DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
	    DateUtil.formatDateByDataMessage(dataMessage);
		modelAndView.addObject("dataMessage", dataMessage);
		return modelAndView;
	}
	

	/**
	 * 修改保存
	 * @param investProductId
	 * @return
	 */
	@RequestMapping("/updateCustomerLCBank")
	public @ResponseBody DwzAjaxController updateCustomerLCBank(HttpServletRequest request){
		JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String resultString = sendRequestService.sendRequest(ConfigManager.CUSTOMERCL_UPDATE, jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if (ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())) {
			return ajaxForwardSuccess("修改银行卡信息成功！", "",ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT, null);
		} else {
			return ajaxForwardError(dataMessage.getMsg(), null, null, null);
		}
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delCustomerLCBank")
	public @ResponseBody DwzAjaxController delCustomerLCBank(String ids){		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ids", ids.trim());
		String resultString = sendRequestService.sendRequest(ConfigManager.CUSTOMERCL_DELBYIDS,jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			return this.ajaxForwardSuccess("删除用户银行卡信息成功！","",null,null);
		}else{
			return this.ajaxForwardError(dataMessage.getMsg(), null, null, null);
		}
	}
}
