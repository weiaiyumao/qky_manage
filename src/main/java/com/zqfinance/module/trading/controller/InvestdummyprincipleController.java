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
@RequestMapping("/experience")
public class InvestdummyprincipleController extends DwzAjaxController {
	
	/**
	 * 获取列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getExperienceMoneyList")
	public ModelAndView getExperienceMoneyList(HttpServletRequest request){	
		
		HttpSession session =  request.getSession(false);
		com.zqfinance.module.sys.domain.User user = null;
		if(null != session){
			user = (com.zqfinance.module.sys.domain.User) session.getAttribute(ManageConfig.LOGIN_USER);
			if(null != user){
				//保存成功登陆日志
				Loginfo loginfo = new Loginfo();
				loginfo.setUserId(user.getId());
				loginfo.setUserName(user.getUserName());
				loginfo.setModuleName("用户体验本金管理管理");
				loginfo.setLoginStatus(ManageConfig.loginStatus_SUCCESS);
				loginfoService.addLoginfo(loginfo);
			}
		}
		
		ModelAndView modelAndView = new ModelAndView("trading/experienceMoney/experienceMoneyList");
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String resultString = sendRequestService.sendRequest(ConfigManager.INVESTDUMMYPRINCIPLE_FINDBYCONDITION,jsonObject);
		DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
		DateUtil.formatDateByDataPageMessage(dataMessage);
		modelAndView.addObject("dataMessage", dataMessage);
		modelAndView.addObject("beginDate",jsonObject.get("beginDate"));
		modelAndView.addObject("endDate",jsonObject.get("endDate"));
		modelAndView.addObject("orderid",jsonObject.get("orderid"));
		modelAndView.addObject("apr",jsonObject.get("apr"));
		modelAndView.addObject("mixMoney",jsonObject.get("mixMoney"));
		modelAndView.addObject("maxMoney",jsonObject.get("maxMoney"));
		modelAndView.addObject("type", jsonObject.get("type"));
		modelAndView.addObject("customerId", jsonObject.get("customerId"));
		return modelAndView;
	}
	
	/**
	 * 跳转详情页面
	 * @param investProductId
	 * @return
	 */
	@RequestMapping("/forwardInfo")
	public ModelAndView forwardInfo(String id){	
		ModelAndView modelAndView = new ModelAndView("trading/experienceMoney/experienceMoneyInfo");
		JSONObject jsonObject = new JSONObject();
	    jsonObject.put("id", id.trim());
	    String resultString = sendRequestService.sendRequest(ConfigManager.INVESTDUMMYPRINCIPLE_FINDBYID,jsonObject);
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
	@RequestMapping("/delExperienceMoney")
	public @ResponseBody DwzAjaxController delExperienceMoney(String ids){		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ids", ids.trim());
		String resultString = sendRequestService.sendRequest(ConfigManager.INVESTDUMMYPRINCIPLE_DELBYIDS,jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			return this.ajaxForwardSuccess("删除用户的体验本金成功！","",null,null);
		}else{
			return this.ajaxForwardError(dataMessage.getMsg(), null, null, null);
		}
	}
}
