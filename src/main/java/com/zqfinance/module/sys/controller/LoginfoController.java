package com.zqfinance.module.sys.controller;

import java.util.List;

import com.zqfinance.system.common.DataMessage;
import com.zqfinance.system.util.MessageUtil;
import com.zqfinance.system.util.RequestUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zqfinance.module.sys.service.LoginfoService;
import com.zqfinance.module.sys.domain.Loginfo;
import com.zqfinance.system.controller.DwzAjaxController;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/loginfo")
public class LoginfoController extends DwzAjaxController{
	
	@Autowired
	private LoginfoService LoginfoService;
	/**
	 * 查询Loginfo列表
	 * @author 宋宇
	 */
	@RequestMapping("/getLoginfoList")
	public ModelAndView getModuleList(@ModelAttribute("loginfo")Loginfo loginfo,HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView("sysmgr/loginfoList");
		int totalCount = LoginfoService.getLoginfoCount(loginfo);
		loginfo.setTotalCount(totalCount);
		List<Loginfo> loginfoList = LoginfoService.getLoginfoList(loginfo);
		modelAndView.addObject("loginfoList", loginfoList);

		JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		jsonObject.put("test","123456");
		String resultString = sendRequestService.sendRequest("http://127.0.0.1:8088/test/demo2",jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);


		return modelAndView;
	}
	
}
