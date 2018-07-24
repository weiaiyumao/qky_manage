package com.zqfinance.module.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zqfinance.module.sys.service.LoginfoService;
import com.zqfinance.module.sys.domain.Loginfo;
import com.zqfinance.system.controller.DwzAjaxController;

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
	public ModelAndView getModuleList(@ModelAttribute("loginfo")Loginfo loginfo){ 
		ModelAndView modelAndView = new ModelAndView("sysmgr/loginfoList");
		int totalCount = LoginfoService.getLoginfoCount(loginfo);
		loginfo.setTotalCount(totalCount);
		List<Loginfo> loginfoList = LoginfoService.getLoginfoList(loginfo);
		modelAndView.addObject("loginfoList", loginfoList);
		return modelAndView;
	}
	
}
