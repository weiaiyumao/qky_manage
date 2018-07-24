package com.zqfinance.module.auth.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface InitIndexService {
	public void initIndex(HttpServletRequest request, HttpServletResponse response,List<String> list);
	public void setSessionValue(HttpServletRequest request,Object...object);
}
