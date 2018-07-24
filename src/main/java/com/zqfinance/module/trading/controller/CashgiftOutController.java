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
@RequestMapping("/cashgiftOut")
public class CashgiftOutController extends BaseController{
	/**
	 * 获取红包列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCashgiftOut")
	public ModelAndView getCashgiftOut(HttpServletRequest request){	
		
		HttpSession session =  request.getSession(false);
		com.zqfinance.module.sys.domain.User user = null;
		if(null != session){
			user = (com.zqfinance.module.sys.domain.User) session.getAttribute(ManageConfig.LOGIN_USER);
			if(null != user){
				//保存成功登陆日志
				Loginfo loginfo = new Loginfo();
				loginfo.setUserId(user.getId());
				loginfo.setUserName(user.getUserName());
				loginfo.setModuleName("支出红包管理");
				loginfo.setLoginStatus(ManageConfig.loginStatus_SUCCESS);
				loginfoService.addLoginfo(loginfo);
			}
		}
		
        ModelAndView modelAndView = new ModelAndView("trading/cashgift/cashgiftOut");
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String resultString = sendRequestService.sendRequest(ConfigManager.CASHGIFTOUT_FINDBYCONDITION,jsonObject);
		DataPageMessage dataPageMessage = MessageUtil.parseDatePageMessage(resultString);
		DateUtil.formatDateByDataPageMessage(dataPageMessage);
		
		//统计金额
		JSONObject jsonObject_money = RequestUtil.fromRequestToJson(request);
		String resultString_money = sendRequestService.sendRequest(ConfigManager.CASHGIFTOUT_GETSUMMONEY,jsonObject_money);
		DataMessage dataMessage_money = MessageUtil.parseDataMessageOfString(resultString_money);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage_money.getResult())){
			if(null != dataMessage_money.getData().get("sumMoney")){
				dataMessage_money.getData().put("sumMoney", FromatMoneyUtil.fromatMoney(Double.parseDouble(dataMessage_money.getData().get("sumMoney"))));
			}
			modelAndView.addObject("dataMessage_money", dataMessage_money);
		}
		
		modelAndView.addObject("dataMessage", dataPageMessage);
		modelAndView.addObject("mixMoney",jsonObject.get("mixMoney"));
		modelAndView.addObject("maxMoney",jsonObject.get("maxMoney"));
		modelAndView.addObject("beginDate",jsonObject.get("beginDate"));
		modelAndView.addObject("endDate",jsonObject.get("endDate"));
		modelAndView.addObject("flag",jsonObject.get("flag"));
		modelAndView.addObject("type",jsonObject.get("type"));
		modelAndView.addObject("orderId",jsonObject.get("orderId"));
		return modelAndView;
	}
}
