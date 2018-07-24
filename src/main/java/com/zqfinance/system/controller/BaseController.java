package com.zqfinance.system.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.zqfinance.module.auth.service.InitIndexService;
import com.zqfinance.module.sys.service.LoginfoService;
import com.zqfinance.system.service.SendRequestService;


public abstract class BaseController {

	private static final Log log = LogFactory.getLog(BaseController.class);
	
	/**
	 * 请求统一入口
	 */
	@Autowired
	protected SendRequestService sendRequestService;
	
	/**
	 * 首页默认待办任务列表
	 */
	@Autowired
	protected InitIndexService initIndexService;
	
	
	@Autowired
	protected LoginfoService loginfoService;
	
	/**
	 * 全局异常处理
	 * @param e
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ExceptionHandler
	public ModelAndView handle(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 错误消息
		log.error("程序异常", e);
		ModelAndView modelAndView = new ModelAndView("exception");
		return modelAndView;
	}


}
