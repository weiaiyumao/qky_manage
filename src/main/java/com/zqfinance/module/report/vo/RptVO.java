package com.zqfinance.module.report.vo;

import java.math.BigDecimal;
import java.util.List;

public class RptVO {
	
	private String title;//报表标题
	
	private String subTitle;//副标题
	
	private BigDecimal sumMoney;//总金额
	
	private BigDecimal countMoney;//当前维度统计数据
	
	private String month;//统计月份 yyyy-MM
	
	private String year;//统计年份
	
	private String json; //转换的对象转换Json格式
	
	@SuppressWarnings("rawtypes")
	private List xaixsList;//x 轴
	
	@SuppressWarnings("rawtypes")
	private List dataList;//数据
	
	@SuppressWarnings("rawtypes")
	private List numList;//数据

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(BigDecimal sumMoney) {
		this.sumMoney = sumMoney;
	}

	public BigDecimal getCountMoney() {
		return countMoney;
	}

	public void setCountMoney(BigDecimal countMoney) {
		this.countMoney = countMoney;
	}

	@SuppressWarnings("rawtypes")
	public List getXaixsList() {
		return xaixsList;
	}

	@SuppressWarnings("rawtypes")
	public void setXaixsList(List xaixsList) {
		this.xaixsList = xaixsList;
	}

	@SuppressWarnings("rawtypes")
	public List getDataList() {
		return dataList;
	}

	@SuppressWarnings("rawtypes")
	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	@SuppressWarnings("rawtypes")
	public List getNumList() {
		return numList;
	}

	@SuppressWarnings("rawtypes")
	public void setNumList(List numList) {
		this.numList = numList;
	}

	
	
	
	
	
}
