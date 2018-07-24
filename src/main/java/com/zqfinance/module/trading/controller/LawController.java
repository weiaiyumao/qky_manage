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
@RequestMapping("/law")
public class LawController extends DwzAjaxController {
	
	/**
	 * 获取列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getLawList")
	public ModelAndView getLawList(HttpServletRequest request){	
		
		HttpSession session =  request.getSession(false);
		com.zqfinance.module.sys.domain.User user = null;
		if(null != session){
			user = (com.zqfinance.module.sys.domain.User) session.getAttribute(ManageConfig.LOGIN_USER);
			if(null != user){
				//保存成功登陆日志
				Loginfo loginfo = new Loginfo();
				loginfo.setUserId(user.getId());
				loginfo.setUserName(user.getUserName());
				loginfo.setModuleName("合同管理");
				loginfo.setLoginStatus(ManageConfig.loginStatus_SUCCESS);
				loginfoService.addLoginfo(loginfo);
			}
		}
		
		ModelAndView modelAndView = new ModelAndView("trading/law/lawList");
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String resultString = sendRequestService.sendRequest(ConfigManager.LAW_FINDLIST,jsonObject);
		DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
		DateUtil.formatDateByDataPageMessage(dataMessage);
		modelAndView.addObject("dataMessage", dataMessage);
		modelAndView.addObject("beginDate",jsonObject.get("beginDate"));
		modelAndView.addObject("endDate",jsonObject.get("endDate"));
		modelAndView.addObject("id",jsonObject.get("id"));
		modelAndView.addObject("lawtype",jsonObject.get("lawtype"));
		modelAndView.addObject("title", jsonObject.get("title"));
		return modelAndView;
	}
	
	/**
	 * 跳转修改页面
	 * @param investProductId
	 * @return
	 */
	@RequestMapping("/forwardEdit")
	public ModelAndView forwardEdit(String id){	
		ModelAndView modelAndView = findLawById(id,"edit");
		modelAndView.addObject("reqBtn","edit");
		return modelAndView;
	}
	
	/**
	 * 跳转详情页面
	 * @param investProductId
	 * @return
	 */
	@RequestMapping("/forwardInfo")
	public ModelAndView forwardInfo(String id){	
		ModelAndView modelAndView = findLawById(id,"info");
		modelAndView.addObject("reqBtn","info");
		return modelAndView;
	}
	
	/**
	 * 跳转添加页面
	 * @param investProductId
	 * @return
	 */
	@RequestMapping("/forwardAdd")
	public ModelAndView forwardAdd(){	
		ModelAndView modelAndView = new ModelAndView("trading/law/lawAdd");
		return modelAndView;
	}
	
	public ModelAndView findLawById(String id,String reqBtn){
		ModelAndView modelAndView = null;
		if(reqBtn.equals("info")){
			modelAndView = new ModelAndView("trading/law/lawInfo");
		}else if(reqBtn.equals("edit")){
			modelAndView = new ModelAndView("trading/law/lawEdit");
		}
		JSONObject jsonObject = new JSONObject();
	    jsonObject.put("id", id.trim());
	    String resultString = sendRequestService.sendRequest(ConfigManager.LAW_FINDBYID,jsonObject);
	    DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
	    DateUtil.formatDateByDataMessage(dataMessage);
		modelAndView.addObject("dataMessage", dataMessage);
		return modelAndView;
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delLaw")
	public @ResponseBody DwzAjaxController delLaw(String ids){		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ids", ids.trim());
		String resultString = sendRequestService.sendRequest(ConfigManager.LAW_DELBYIDS,jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			return this.ajaxForwardSuccess("删除合同成功！","",null,null);
		}else{
			return this.ajaxForwardError(dataMessage.getMsg(), null, null, null);
		}
	}
	
	/**
	 * 添加
	 * @param request
	 * @return
	 */
	@RequestMapping("/addLaw")
	public @ResponseBody DwzAjaxController addLaw(HttpServletRequest request){
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
        String resultString = sendRequestService.sendRequest(ConfigManager.LAW_SAVE,jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			return ajaxForwardSuccess("增加合同成功！","",ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT,null);
		}else{
			return ajaxForwardError(dataMessage.getMsg(), null, null, null);
		}	
	} 
	
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateLaw")
	public @ResponseBody DwzAjaxController updateLaw(HttpServletRequest request){
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
        String resultString = sendRequestService.sendRequest(ConfigManager.LAW_UPDATE,jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			return ajaxForwardSuccess("修改合同成功！","",ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT,null);
		}else{
			return ajaxForwardError(dataMessage.getMsg(), null, null, null);
		}
	} 
	
}
