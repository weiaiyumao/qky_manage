package com.zqfinance.module.trading.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zqfinance.module.sys.domain.Loginfo;
import com.zqfinance.system.common.DataMessage;
import com.zqfinance.system.common.DataPageMessage;
import com.zqfinance.system.config.ConfigManager;
import com.zqfinance.system.config.ManageConfig;
import com.zqfinance.system.controller.BaseController;
import com.zqfinance.system.util.DateUtil;
import com.zqfinance.system.util.FromatMoneyUtil;
import com.zqfinance.system.util.MessageUtil;
import com.zqfinance.system.util.RequestUtil;

@Controller
@RequestMapping("/cashgiftIn")
public class CashgiftInController extends BaseController{
	/**
	 * 获取红包列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCashgiftIn")
	public ModelAndView getCashgiftIn(HttpServletRequest request){
		
		HttpSession session =  request.getSession(false);
		com.zqfinance.module.sys.domain.User user = null;
		if(null != session){
			user = (com.zqfinance.module.sys.domain.User) session.getAttribute(ManageConfig.LOGIN_USER);
			if(null != user){
				//保存成功登陆日志
				Loginfo loginfo = new Loginfo();
				loginfo.setUserId(user.getId());
				loginfo.setUserName(user.getUserName());
				loginfo.setModuleName("收入红包管理");
				loginfo.setLoginStatus(ManageConfig.loginStatus_SUCCESS);
				loginfoService.addLoginfo(loginfo);
			}
		}
		
        ModelAndView modelAndView = new ModelAndView("trading/cashgift/cashgiftIn");
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String resultString = sendRequestService.sendRequest(ConfigManager.CASH_FINDBYCONDITION,jsonObject);
		DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
		DateUtil.formatDateByDataPageMessage(dataMessage);
		
		//统计金额
		JSONObject jsonObject_money = RequestUtil.fromRequestToJson(request);
		String resultString_money = sendRequestService.sendRequest(ConfigManager.CASH_GETSUMMONEY,jsonObject_money);
		DataMessage dataMessage_money = MessageUtil.parseDataMessageOfString(resultString_money);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage_money.getResult())){
			if(null != dataMessage_money.getData().get("sumMoney")){
				dataMessage_money.getData().put("sumMoney", FromatMoneyUtil.fromatMoney(Double.parseDouble(dataMessage_money.getData().get("sumMoney"))));
			}
			modelAndView.addObject("dataMessage_money", dataMessage_money);
		}
		
		modelAndView.addObject("dataMessage", dataMessage);
		modelAndView.addObject("mixMoney",jsonObject.get("mixMoney"));
		modelAndView.addObject("maxMoney",jsonObject.get("maxMoney"));
		modelAndView.addObject("beginDate",jsonObject.get("beginDate"));
		modelAndView.addObject("endDate",jsonObject.get("endDate"));
		modelAndView.addObject("moneyType",jsonObject.get("moneyType"));
		modelAndView.addObject("orderId",jsonObject.get("orderId"));
		return modelAndView;
	}
}
