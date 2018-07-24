package com.zqfinance.module.auth.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.zqfinance.module.auth.service.InitIndexService;
import com.zqfinance.module.sys.service.UserService;
import com.zqfinance.system.common.DataMessage;
import com.zqfinance.system.common.DataPageMessage;
import com.zqfinance.system.config.ConfigManager;
import com.zqfinance.system.config.ManageConfig;
import com.zqfinance.system.service.SendRequestService;
import com.zqfinance.system.util.DateUtil;
import com.zqfinance.system.util.FromatMoneyUtil;
import com.zqfinance.system.util.MessageUtil;
import com.zqfinance.system.util.RequestUtil;

@Service
public class InitIndexServiceImpl implements InitIndexService{

	@Autowired
	private UserService userService;
	
	/**
	 * 请求统一入口
	 */
	@Autowired
	private  SendRequestService sendRequestService;
	
	/**
	 * 登陆首页加载
	 */
	public void initIndex(HttpServletRequest request,
			HttpServletResponse response, List<String> list) {
		setSessionValue(request,list);
	}
	
	/**
	 * set待办任务列表
	 * @param request
	 * @param object
	 */
	public void setSessionValue(HttpServletRequest request,Object...object){
		HttpSession session =  request.getSession(false);
		boolean fag = false; //确认首页是否显示（<h3 style="line-height: 30px;padding-left: 30px; margin-top:10px;">待处理事项：</h3>）
//		for (Object url : object) {
//			if(null != session){
//				//提现待审核5条
//				if(url.toString().contains("/outMoney/getOutMoneyAudit")){
//					fag = true;
//					JSONObject json_OutMoneyToAudit = new JSONObject();
//					json_OutMoneyToAudit.put("flag", "0");
//					json_OutMoneyToAudit.put("numPerPage", "5");
//					String resultString_OutMoneyToAudit= sendRequestService.sendRequest(ConfigManager.OUTMONEY_LIST,json_OutMoneyToAudit);
//					DataPageMessage dataMessage_OutMoneyToAudit = MessageUtil.parseDatePageMessage(resultString_OutMoneyToAudit);
//					if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage_OutMoneyToAudit.getResult())){
//						DateUtil.formatDateByDataPageMessage(dataMessage_OutMoneyToAudit);
//						session.setAttribute("showOutMoneyToAuditListTopfive", "yes");
//						session.setAttribute("outMoneyToAuditListTopfive", dataMessage_OutMoneyToAudit);
//					}
//				}
//
//				//提现待放款5条
//				if(url.toString().contains("/outMoney/payOutMoney")){
//					fag = true;
//					JSONObject json_OutMoneyToMakeLoans = new JSONObject();
//					json_OutMoneyToMakeLoans.put("flag", "3");
//					json_OutMoneyToMakeLoans.put("numPerPage", "5");
//					String resultString_OutMoneyToMakeLoans = sendRequestService.sendRequest(ConfigManager.OUTMONEY_LIST,json_OutMoneyToMakeLoans);
//					DataPageMessage dataMessage_OutMoneyToMakeLoans = MessageUtil.parseDatePageMessage(resultString_OutMoneyToMakeLoans);
//					if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage_OutMoneyToMakeLoans.getResult())){
//						DateUtil.formatDateByDataPageMessage(dataMessage_OutMoneyToMakeLoans);
//						session.setAttribute("showOutMoneyToMakeLoansListTopfive", "yes");
//						session.setAttribute("outMoneyToMakeLoansListTopfive", dataMessage_OutMoneyToMakeLoans);
//					}
//
//				}
//
//				//显示月利宝的已投金额
//				if(url.toString().contains("/product/forwardEdit")){
//					fag = true;
//					//当前理财产品剩余可投金额提醒
//					JSONObject jsonObject = new JSONObject();
//					String resultString = sendRequestService.sendRequest(ConfigManager.INVESTPRODUCT_GETPAGE,jsonObject);
//					DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
//					if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
//						Map<String,String> map = dataMessage.getContentList().get(0);
//						//获取理财产品月利宝的已投金额
//						JSONObject jsonObject_money = new JSONObject();
//						jsonObject_money.put("investProductId", map.get("id").trim());
//					    String resultString_money = sendRequestService.sendRequest(ConfigManager.PRODUCTORDER_GETSUMMONEYBYINVESTPRODUCTID,jsonObject_money);
//					    DataMessage dataMessage_money = MessageUtil.parseDataMessage(resultString_money);
//					    if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage_money.getResult())){
//					    	BigDecimal symoney = new BigDecimal(dataMessage.getContentList().get(0).get("money")).subtract(new BigDecimal(dataMessage_money.getData().get("money")));
//						    session.setAttribute("showSymoney", "yes");
//							session.setAttribute("symoney", symoney);
//					    }
//					}
//
//				}
//
//				//当前贷款可匹配金额
//				if(url.toString().contains("/loan/forwordLoan")){
//					fag = true;
//				    JSONObject json_loan = RequestUtil.fromRequestToJson(request);
//				    json_loan.put("loanStatus", "2");//投标中
//					String resultString_loan = sendRequestService.sendRequest(ConfigManager.LOAN_FINDBYVIEWLIST,json_loan);
//					DataPageMessage dataMessage_loan = MessageUtil.parseDatePageMessage(resultString_loan);
//					if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage_loan.getResult())){
//						BigDecimal loanSyMoney = BigDecimal.ZERO;
//						for (int i = 0; i < dataMessage_loan.getContentList().size(); i++) {
//							loanSyMoney  = loanSyMoney.add(new BigDecimal(dataMessage_loan.getContentList().get(i).get("symoney")));
//						}
//						session.setAttribute("showLoanSyMoney", "yes");
//						session.setAttribute("loanSyMoney", loanSyMoney);
//					}
//				}
//
//				//今天提现客户金额 (目前显示权限：公司充值，公司取现放款)
//				if(url.toString().contains("/companyOrder/forwordCreateOrderPage") || url.toString().contains("/companyOutMoney/payCompanyOutMoney")){
//					fag = true;
//					JSONObject json = RequestUtil.fromRequestToJson(request);
//					String resultString = sendRequestService.sendRequest(ConfigManager.FUNDUTILIZATIONRATE_GETORDERRESIDUALMONEYBYTODAY,json);
//					DataMessage dataMessage = MessageUtil.parseDataMessageOfString(resultString);
//					if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
//						session.setAttribute("showOutMoney", "yes");
//						session.setAttribute("outMoney", FromatMoneyUtil.fromatMoney(Double.parseDouble(dataMessage.getData().get("sumMoney")!=null?dataMessage.getData().get("sumMoney"):"0")));
//					}
//				}
//
//				//预计今天可公司提现金额
//				if(url.toString().contains("/companyOutMoney/addCompanyOutMoneyPage")){
//					fag = true;
//					JSONObject json = RequestUtil.fromRequestToJson(request);
//					json.put("day", DateUtil.getSyDate(new Date(), 0, 0, -1));
//					String resultString = sendRequestService.sendRequest(ConfigManager.FUNDUTILIZATIONRATE_GETORDERRESIDUALMONEYBYDAY,json);
//					DataMessage dataMessage = MessageUtil.parseDataMessageOfString(resultString);
//					if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
//						session.setAttribute("showCompanyOutMoney", "yes");
//						session.setAttribute("companyOutMoney", FromatMoneyUtil.fromatMoney(Double.parseDouble(dataMessage.getData().get("sumMoney")!=null?dataMessage.getData().get("sumMoney"):"0")));
//					}
//				}
//
//				if(fag){
//					session.setAttribute("todo", "yes");
//				}
//			}
//		}
	}
}
