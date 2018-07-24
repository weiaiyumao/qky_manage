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
@RequestMapping("/company")
public class CompanyOutmoneyBankController extends DwzAjaxController {
	
	/**
	 * 获取列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCompanyOutmoneyBankList")
	public ModelAndView getCompanyOutmoneyBankList(HttpServletRequest request){	
		
		HttpSession session =  request.getSession(false);
		com.zqfinance.module.sys.domain.User user = null;
		if(null != session){
			user = (com.zqfinance.module.sys.domain.User) session.getAttribute(ManageConfig.LOGIN_USER);
			if(null != user){
				//保存成功登陆日志
				Loginfo loginfo = new Loginfo();
				loginfo.setUserId(user.getId());
				loginfo.setUserName(user.getUserName());
				loginfo.setModuleName("公司提现银行卡管理");
				loginfo.setLoginStatus(ManageConfig.loginStatus_SUCCESS);
				loginfoService.addLoginfo(loginfo);
			}
		}
		
		ModelAndView modelAndView = new ModelAndView("trading/company/companyOutmoneyBankList");
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String resultString = sendRequestService.sendRequest(ConfigManager.COMPANYOUTMONEYBANK_FINDBYPAGE,jsonObject);
		DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
		DateUtil.formatDateByDataPageMessage(dataMessage);
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
		ModelAndView modelAndView = new ModelAndView("trading/company/companyOutmoneyBankInfo");
		JSONObject jsonObject = new JSONObject();
	    jsonObject.put("id", id.trim());
	    String resultString = sendRequestService.sendRequest(ConfigManager.COMPANYOUTMONEYBANK_FINDBYID,jsonObject);
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
		ModelAndView modelAndView = new ModelAndView("trading/company/companyOutmoneyBankEdit");
		JSONObject jsonObject = new JSONObject();
	    jsonObject.put("id", id.trim());
	    String resultString = sendRequestService.sendRequest(ConfigManager.COMPANYOUTMONEYBANK_FINDBYID,jsonObject);
	    DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
	    
	    JSONObject jsonObject_province = new JSONObject();
	    String resultString_province = sendRequestService.sendRequest(ConfigManager.OUTMONEYFLOW_FINDPROVINCE,jsonObject_province);
	    DataPageMessage province_dataMessage = MessageUtil.parseDateListMessage(resultString_province);
		modelAndView.addObject("dataMessage_province", province_dataMessage);
		
	    DateUtil.formatDateByDataMessage(dataMessage);
		modelAndView.addObject("dataMessage", dataMessage);
		return modelAndView;
	}
	
	/**
	 * 跳转添加页面
	 * @return
	 */
	@RequestMapping("/forwardAdd")
	public ModelAndView forwardAdd(){
		ModelAndView modelAndView = new ModelAndView("trading/company/companyOutmoneyBankAdd");
		JSONObject jsonObject = new JSONObject();
	    String resultString = sendRequestService.sendRequest(ConfigManager.OUTMONEYFLOW_FINDPROVINCE,jsonObject);
	    DataPageMessage province_dataMessage = MessageUtil.parseDateListMessage(resultString);
		modelAndView.addObject("dataMessage", province_dataMessage);
		return modelAndView;
	}
	/**
	 * 获取城市
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCityName")
	public @ResponseBody Object getCityName(HttpServletRequest request){
		JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String resultString = sendRequestService.sendRequest(ConfigManager.OUTMONEYFLOW_FINDCITY, jsonObject);
		DataPageMessage city_dataMessage = MessageUtil.parseDateListMessage(resultString);
		return city_dataMessage.getContentList();
	}
	
	/**
	 * 添加保存
	 * @param investProductId
	 * @return
	 */
	@RequestMapping("/addCompanyOutmoneyBank")
	public @ResponseBody DwzAjaxController addCompanyOutmoneyBank(HttpServletRequest request){
		JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String provinceName = jsonObject.get("provinceName").toString();
		String[] provinceNameArray = provinceName.split(",");
		jsonObject.put("provinceName",provinceNameArray[0]);
		String resultString = sendRequestService.sendRequest(ConfigManager.COMPANYOUTMONEYBANK_SAVE, jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if (ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())) {
			return ajaxForwardSuccess("添加银行卡信息成功！", "",ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT, null);
		} else {
			return ajaxForwardError(dataMessage.getMsg(), null, null, null);
		}
	}

	/**
	 * 修改保存
	 * @param investProductId
	 * @return
	 */
	@RequestMapping("/updateCompanyOutmoneyBank")
	public @ResponseBody DwzAjaxController updateCompanyOutmoneyBank(HttpServletRequest request){
		JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String provinceName = jsonObject.get("provinceName").toString();
		String[] provinceNameArray = provinceName.split(",");
		jsonObject.put("provinceName",provinceNameArray[0]);
		String resultString = sendRequestService.sendRequest(ConfigManager.COMPANYOUTMONEYBANK_UPDATE, jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if (ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())) {
			return ajaxForwardSuccess("修改银行卡信息成功！", "",ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT, null);
		} else {
			return ajaxForwardError(dataMessage.getMsg(), null, null, null);
		}
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delCompanyOutmoneyBank")
	public @ResponseBody DwzAjaxController delCompanyOutmoneyBank(String id){		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id.trim());
		String resultString = sendRequestService.sendRequest(ConfigManager.COMPANYOUTMONEYBANK_DEL,jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			return this.ajaxForwardSuccess("删除成功！","",null,null);
		}else{
			return this.ajaxForwardError(dataMessage.getMsg(), null, null, null);
		}
	}
	
}
