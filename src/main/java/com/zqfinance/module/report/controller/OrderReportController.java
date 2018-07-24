package com.zqfinance.module.report.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSON;
import com.zqfinance.module.report.vo.RptVO;
import com.zqfinance.system.common.DataPageMessage;
import com.zqfinance.system.config.ConfigManager;
import com.zqfinance.system.service.SendRequestService;
import com.zqfinance.system.util.DateUtil;
import com.zqfinance.system.util.ExcelUtil;
import com.zqfinance.system.util.FromatMoneyUtil;
import com.zqfinance.system.util.MessageUtil;

/**
 * 投资报表
 * 
 * @author xuj
 *
 */
@Controller
@RequestMapping("/orderReport")
public class OrderReportController {

	/**
	 * 请求统一入口
	 */
	@Autowired
	protected SendRequestService sendRequestService;

	@RequestMapping("/orderReportDay")
	public String OrderReportDay() {
		return "report/orderRptDay";
	}

	@RequestMapping("/getOrderMoneyMonth")
	@ResponseBody
	public RptVO getOrderMoneyMonth(String month) throws ParseException, IOException {
		JSONObject jsonObject = new JSONObject();
		List<Integer> xaxisList = new ArrayList<Integer>(); 
		List<Integer> numList = new ArrayList<Integer>(); 
		BigDecimal countMoney = BigDecimal.ZERO;
		List<BigDecimal> dataList = new ArrayList<BigDecimal>();
		jsonObject.put("month", month);
		String resultString = sendRequestService.sendRequest(
				ConfigManager.ORDERRPT_GETORDERRPTMONTH, jsonObject);
		DataPageMessage dataMessage = MessageUtil
				.parseDateListMessage(resultString);
		if (null != dataMessage
				&& CollectionUtils.isNotEmpty(dataMessage.getContentList())) {
			Map<String, String> numMap = new HashMap<String, String>();
			Map<String, BigDecimal> returnMap = new HashMap<String, BigDecimal>();
			for (Map<String, String> map : dataMessage.getContentList()) {
				returnMap.put(map.get("tradeDate"), BigDecimal.valueOf(Double.valueOf(map.get("money"))));
				numMap.put(map.get("tradeDate"), map.get("num"));
			}
			
			
			//判断传递的月份是否是当前的月份，如果是当前的月份则只展示到当前日期截止的数据
			String nowDate = DateUtil.COMMON.getFormat().format(Calendar.getInstance().getTime());
			String nowMonth = nowDate.substring(0, 7);
			String dayOfMonth = "";
			if(month.equals(nowMonth)){
				//获取当前天数
				dayOfMonth = nowDate.substring(8);
			}
			else{
				dayOfMonth = String.valueOf(DateUtil.getDayintForMonth(month));
			}
			
			for(int i=1;i<=Integer.parseInt(dayOfMonth);i++){
				String days = String.valueOf(i);
				if(i<=9){
					days = "0"+days;
				}
				xaxisList.add(Integer.parseInt(days));
				numList.add(Integer.parseInt(numMap.get(month+"-"+days) == null ?"0":numMap.get(month+"-"+days)));
				if(!DateUtil.keyContainsDay(returnMap.keySet(),days)){
					dataList.add(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_DOWN));
				}else{
					dataList.add( returnMap.get(month+"-"+days).setScale(2,BigDecimal.ROUND_DOWN));
					countMoney = countMoney.add(returnMap.get(month+"-"+days));
				}
			}
			
		}
		RptVO rptVO = new RptVO();
		rptVO.setXaixsList(xaxisList);
		rptVO.setDataList(dataList);
		rptVO.setNumList(numList);
		rptVO.setTitle(month +"投资数据统计");
		rptVO.setSubTitle("本月投资金额:"+ FromatMoneyUtil.fromatMoney(Double.parseDouble(countMoney.toString())));
		rptVO.setCountMoney(countMoney.setScale(2, BigDecimal.ROUND_DOWN));
		rptVO.setMonth(month);
		rptVO.setJson(JSON.json(rptVO));
		return rptVO;
		
	}
	
	@RequestMapping("/delMonthExcel")
	public void delMonthExcel(HttpServletRequest request, HttpServletResponse response) throws com.alibaba.dubbo.common.json.ParseException {
		String json = request.getParameter("rptJson");
		RptVO rptVO =JSON.parse(json, RptVO.class);
		ExcelUtil.modelOne(rptVO, response, "（"+rptVO.getMonth()+"月）理财产品日订单资金总额对照表", "日期");
	}
	
	@RequestMapping("/orderReportMonth")
	public String OrderReportMonth() {
		return "report/orderRptMonth";
	}

	@RequestMapping("/getOrderMoneyYear")
	@ResponseBody
	public RptVO getOrderMoneyYear(String year) throws ParseException, IOException {
		JSONObject jsonObject = new JSONObject();
		List<Integer> xaxisList = new ArrayList<Integer>(); 
		List<Integer> numList = new ArrayList<Integer>(); 
		BigDecimal countMoney = BigDecimal.ZERO;
		List<BigDecimal> dataList = new ArrayList<BigDecimal>();
		jsonObject.put("age", year);
		String resultString = sendRequestService.sendRequest(
				ConfigManager.ORDERRPT_GETORDERRPTAGE, jsonObject);
		DataPageMessage dataMessage = MessageUtil
				.parseDateListMessage(resultString);
		if (null != dataMessage
				&& CollectionUtils.isNotEmpty(dataMessage.getContentList())) {
			Map<String, BigDecimal> returnMap = new HashMap<String, BigDecimal>();
			Map<String, String> numMap = new HashMap<String, String>();
			for (Map<String, String> map : dataMessage.getContentList()) {
				returnMap.put(map.get("tradeDate"), BigDecimal.valueOf(Double
						.valueOf(map.get("money"))));
				numMap.put(map.get("tradeDate"), map.get("num"));
			}
			
//			//判断传递的年是否是当前的年，如果是当前的年则只展示到当前月份的数据2014-12-12
			String nowDate = DateUtil.COMMON.getFormat().format(Calendar.getInstance().getTime());
			String nowYear = nowDate.substring(0, 4);
			int yearOfMonth = 0;
			if(year.equals(nowYear)){
				//获取当前月
				yearOfMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
			}
			else{
				yearOfMonth = 12;
			}
//			
			for(int i=1;i<=yearOfMonth;i++){
				String month = String.valueOf(i);
				if(i<=9){
					month = "0"+month;
				}
				numList.add(Integer.parseInt(numMap.get(year+"-"+month) == null ?"0":numMap.get(year+"-"+month)));
				xaxisList.add(Integer.parseInt(month));
				if(!DateUtil.keyContainsMonth(returnMap.keySet(),month)){
					dataList.add(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_DOWN));
				}else{
					dataList.add( returnMap.get(year+"-"+month).setScale(2,BigDecimal.ROUND_DOWN));
					countMoney = countMoney.add(returnMap.get(year+"-"+month));
				}
			}
			
		}
		RptVO rptVO = new RptVO();
		rptVO.setXaixsList(xaxisList);
		rptVO.setDataList(dataList);
		rptVO.setNumList(numList);
		rptVO.setTitle(year +"投资数据统计");
		rptVO.setSubTitle("本年投资金额:"+ FromatMoneyUtil.fromatMoney(Double.parseDouble(countMoney.toString())));
		rptVO.setCountMoney(countMoney.setScale(2, BigDecimal.ROUND_DOWN));
		rptVO.setYear(year);
		rptVO.setJson(JSON.json(rptVO));
		return rptVO;
		
	}
	
	@RequestMapping("/delYearExcel")
	public void delYearExcel(HttpServletRequest request, HttpServletResponse response) throws com.alibaba.dubbo.common.json.ParseException {
		String json = request.getParameter("rptJsonYear");
		RptVO rptVO =JSON.parse(json, RptVO.class);
		ExcelUtil.modelOne(rptVO, response, "（"+rptVO.getYear()+"年）理财产品月订单资金总额对照表", "月份");
	}
}
