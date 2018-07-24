package com.zqfinance.module.report.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSON;
import com.zqfinance.module.report.vo.RptVO;
import com.zqfinance.system.common.DataPageMessage;
import com.zqfinance.system.config.ConfigManager;
import com.zqfinance.system.controller.DwzAjaxController;
import com.zqfinance.system.util.DateUtil;
import com.zqfinance.system.util.ExcelUtil;
import com.zqfinance.system.util.FromatMoneyUtil;
import com.zqfinance.system.util.MessageUtil;

/**
 * 公司提现报表
 */
@Controller
@RequestMapping("/companyOutMoneyOrderReport")
public class CompanyOutMoneyOrderReportController extends DwzAjaxController{
	
	@RequestMapping("/outMoneyOrderReportMonth")
	public String outMoneyOrderReportMonth() {
		return "report/companyOutMoneyOrderRptMonth";
	}

	@RequestMapping("/getOutMoneyOrderMoneyYear")
	@ResponseBody
	public RptVO getOutMoneyOrderMoneyYear(String year) throws ParseException, IOException {
		JSONObject jsonObject = new JSONObject();
		List<Integer> xaxisList = new ArrayList<Integer>(); 
		List<Integer> numList = new ArrayList<Integer>(); 
		BigDecimal countMoney = BigDecimal.ZERO;
		List<BigDecimal> dataList = new ArrayList<BigDecimal>();
		jsonObject.put("year", year);
		String resultString = sendRequestService.sendRequest(
				ConfigManager.COMPANYORDERRPT_GETOUTMONEYORDERRPTAGE, jsonObject);
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
				xaxisList.add(Integer.parseInt(month));
				numList.add(Integer.parseInt(numMap.get(year+"-"+month) == null ?"0":numMap.get(year+"-"+month)));
				if(!keyContainsMonth(returnMap.keySet(),month)){
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
		rptVO.setTitle(year +"提现数据统计");
		rptVO.setSubTitle("本年提现金额:"+ FromatMoneyUtil.fromatMoney(Double.parseDouble(countMoney.toString())));
		rptVO.setCountMoney(countMoney.setScale(2, BigDecimal.ROUND_DOWN));
		rptVO.setYear(year);
		rptVO.setJson(JSON.json(rptVO));
		return rptVO;
		
	}
	
	@RequestMapping("/delYearExcel")
	public void delYearExcel(HttpServletRequest request, HttpServletResponse response) throws com.alibaba.dubbo.common.json.ParseException {
		String json = request.getParameter("rptJsonYear");
		RptVO rptVO =JSON.parse(json, RptVO.class);
		ExcelUtil.modelOne(rptVO, response, "（"+rptVO.getYear()+"年   公司提现月订单资金总额对照表", "月份");
	}

	private boolean keyContainsMonth(Set<String> keyset,String MonthStr){
		Iterator<String> iterator =  keyset.iterator();
		while(iterator.hasNext()){
			String keys = iterator.next();
			if(keys.substring(5,7).equals(MonthStr)){
				return true;
			}
		}
		return false;
	}

}
