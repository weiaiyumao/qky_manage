/**
 * 贷款管理
 * @Autoer 宋宇
 */

package com.zqfinance.module.trading.controller;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zqfinance.module.sys.domain.Loginfo;
import com.zqfinance.system.common.DataMessage;
import com.zqfinance.system.common.DataPageMessage;
import com.zqfinance.system.config.ConfigManager;
import com.zqfinance.system.config.ManageConfig;
import com.zqfinance.system.controller.DwzAjaxController;
import com.zqfinance.system.util.DateUtil;
import com.zqfinance.system.util.MStringUtil;
import com.zqfinance.system.util.MessageUtil;
import com.zqfinance.system.util.RequestUtil;

@Controller
@RequestMapping("/loan")
public class LoanController extends DwzAjaxController {
	
	/**
	 * 获取贷款列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getloanList")
	public ModelAndView getloanList(HttpServletRequest request){	
		
		HttpSession session =  request.getSession(false);
		com.zqfinance.module.sys.domain.User user = null;
		if(null != session){
			user = (com.zqfinance.module.sys.domain.User) session.getAttribute(ManageConfig.LOGIN_USER);
			if(null != user){
				//保存成功登陆日志
				Loginfo loginfo = new Loginfo();
				loginfo.setUserId(user.getId());
				loginfo.setUserName(user.getUserName());
				loginfo.setModuleName("贷款管理");
				loginfo.setLoginStatus(ManageConfig.loginStatus_SUCCESS);
				loginfoService.addLoginfo(loginfo);
			}
		}
		
        ModelAndView modelAndView = new ModelAndView("trading/loan/loanList");
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String resultString = sendRequestService.sendRequest(ConfigManager.LOAN_FINDLIST,jsonObject);
		DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
		DateUtil.formatDateByDataPageMessage(dataMessage);
		modelAndView.addObject("dataMessage", dataMessage);
		modelAndView.addObject("minMoney",jsonObject.get("minMoney"));
		modelAndView.addObject("maxMoney",jsonObject.get("maxMoney"));
		modelAndView.addObject("beginDate",jsonObject.get("beginDate"));
		modelAndView.addObject("endDate",jsonObject.get("endDate"));
		modelAndView.addObject("title",jsonObject.get("title"));
		modelAndView.addObject("loanStatus",jsonObject.get("loanStatus"));
		modelAndView.addObject("id",jsonObject.get("id"));
		return modelAndView;
	}
	
	/**
	 * 获取贷款列表(弹出层：查找带回调用)
	 * @param request
	 * @return
	 */
	@RequestMapping("/getloanListforBack")
	public ModelAndView getloanListforBack(HttpServletRequest request){	
        ModelAndView modelAndView = new ModelAndView("trading/tender/loanList");
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String resultString = sendRequestService.sendRequest(ConfigManager.LOAN_FINDLIST,jsonObject);
		DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
		modelAndView.addObject("dataMessage", dataMessage);
		return modelAndView;
	}
	
	
	@RequestMapping("/forwordLoan")
	public ModelAndView forwordUpOrAddLoan(String loanId){
		ModelAndView modelAndView = null;
		
		//获取合同列表
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("lawtype", "1");
		String lawresultString = sendRequestService.sendRequest(ConfigManager.LAW_FINDBYALL,jsonObject);
		DataPageMessage law_dataMessage = MessageUtil.parseDateListMessage(lawresultString);
		
		//修改
		if(MStringUtil.isNotEmpty(loanId)){
			modelAndView = new ModelAndView("trading/loan/loanUpdate");
			JSONObject getLoanParams = new JSONObject();
			getLoanParams.put("id", loanId);
			String resultString = sendRequestService.sendRequest(ConfigManager.LOANBILL_GETBYID,getLoanParams);
			DataMessage dataMessage1 = MessageUtil.parseDataMessage(resultString);
			DateUtil.formatDateByDataMessage(dataMessage1);
			Map<String,String> resultmap = dataMessage1.getData();
			modelAndView.addObject("resultmap", resultmap);
			modelAndView.addObject("law_dataMessage", law_dataMessage);
			
//			//获取客户信息
//			JSONObject jsonObject_customer = new JSONObject();
//			jsonObject_customer.put("customerIds", resultmap.get("customerId"));
//			String customerInfo = sendRequestService.sendRequest(ConfigManager.LOAN_FINDBYCUSTOMERID,jsonObject_customer);
//			DataMessage dataMessage = MessageUtil.parseDataMessageForP2b(customerInfo);
//			modelAndView.addObject("dataMessage", dataMessage);
		}else{// 新增 add
			modelAndView = new ModelAndView("trading/loan/loanAdd");
			modelAndView.addObject("law_dataMessage", law_dataMessage);
		}                                  
		return modelAndView;
	}
	
	/**
	 * 新增
	 * */
	@RequestMapping("/addLoan")
	public @ResponseBody DwzAjaxController addLoan(HttpServletRequest request){
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
        jsonObject.put("method", jsonObject.get("methodtype"));
		String resultString = sendRequestService.sendRequest(ConfigManager.LOAN_Add,jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			//刷新首页
			initIndexService.setSessionValue(request, "/loan/forwordLoan");
			return ajaxForwardSuccess("增加贷款标成功","",ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT,null);
		}else{
			return ajaxForwardError(dataMessage.getMsg(), null, null, null);
		}
	} 
	
	
	/**
	 * 修改
	 * */
	@RequestMapping("/updateLoan")
	public @ResponseBody DwzAjaxController updateLoan(HttpServletRequest request){
		JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		jsonObject.put("method", jsonObject.get("methodtype"));
		String resultString = sendRequestService.sendRequest(ConfigManager.LOAN_UPDATE,jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			return ajaxForwardSuccess("修改贷款标成功","",ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT,null);
		}else{
			return ajaxForwardError(dataMessage.getMsg(), null, null, null);
		}
	} 
	
	/**
	 *贷款-先查询出需要修改状态的对象
	 ***/
	@RequestMapping("/forwordByStatus")
	public ModelAndView  forwordByStatus(String loanId){
		ModelAndView modelAndView = new ModelAndView("trading/loan/updateFlagById");
		JSONObject getLoanParams = new JSONObject();
		getLoanParams.put("id", loanId);
		String resultString = sendRequestService.sendRequest(ConfigManager.LOANBILL_GETBYID,getLoanParams);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		DateUtil.formatDateByDataMessage(dataMessage);
		Map<String,String> resultmap = dataMessage.getData();
		modelAndView.addObject("dataMessage", resultmap);
		return modelAndView;	
	}
	
	/**
	 * 详情展示
	 * **/
	@RequestMapping("/showLoan")
	public ModelAndView showLoan(String loanId){
		ModelAndView modelAndView = new ModelAndView("trading/loan/loanInfo");
		JSONObject getLoanParams = new JSONObject();
		getLoanParams.put("id", loanId);
		String resultString = sendRequestService.sendRequest(ConfigManager.LOANBILL_GETBYID,getLoanParams);
		DataMessage dataMessage1 = MessageUtil.parseDataMessage(resultString);
		DateUtil.formatDateByDataMessage(dataMessage1);
		Map<String,String> resultmap = dataMessage1.getData();
		modelAndView.addObject("resultmap", resultmap);
		modelAndView.addObject("isDetail", true);
		
//		//获取客户信息
//		JSONObject jsonObject_customer = new JSONObject();
//		jsonObject_customer.put("customerIds", resultmap.get("customerId"));
//		String customerInfo = sendRequestService.sendRequest(ConfigManager.LOAN_FINDBYCUSTOMERID,jsonObject_customer);
//		DataMessage dataMessage = MessageUtil.parseDataMessageForP2b(customerInfo);
//		modelAndView.addObject("dataMessage", dataMessage);
		
		// 获取合同列表
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("lawtype", "1");
		String lawresultString = sendRequestService.sendRequest(ConfigManager.LAW_FINDBYALL, jsonObject);
		DataPageMessage law_dataMessage = MessageUtil.parseDateListMessage(lawresultString);		
		modelAndView.addObject("law_dataMessage", law_dataMessage);
		
		// 获取贷款已经匹配金额
		JSONObject jsonObjectMatchMoney = new JSONObject();
		jsonObjectMatchMoney.put("loanId", loanId.trim());
		String matchMoneyresultString = sendRequestService.sendRequest(ConfigManager.LOANINVESTPRODUCTORDER_GETMATCHMONEY, jsonObjectMatchMoney);
		DataMessage matchMoney_dataMessage = MessageUtil.parseDataMessageOfString(matchMoneyresultString);		
		BigDecimal matchMonye = new BigDecimal(resultmap.get("money")).subtract(new BigDecimal(matchMoney_dataMessage.getData().get("sumMoney")));
		modelAndView.addObject("matchMoney", matchMonye);
		
		return modelAndView;
	}
	
	/**
	 * 贷款-批量审核
	 * **/    
	@RequestMapping("/updateByFlag")
	public @ResponseBody DwzAjaxController updateByFlag(String ids){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Ids", ids.trim());
		//传递路径+json格式参数调用trading 里面的service方法，返回json格式字符串
		String resultString = sendRequestService.sendRequest(ConfigManager.UPDATE_FLAG_BYIDS,jsonObject);
		//解析json result = 0 表示成功
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			if(dataMessage.getMsg().equals("审核失败")){
				return ajaxForwardError("审核失败，请选择处于待审核状态的贷款标进行审核！","",null,null);
			}else{
				return ajaxForwardSuccess(dataMessage.getMsg(),"",null,null);
			}
		}else{
			return ajaxForwardError(dataMessage.getMsg(), null, null, null);	
		}
	}
	
	/**
	 * 删除
	 * **/
	@RequestMapping("/deleteLoan")
	public @ResponseBody DwzAjaxController delloanbill(String loanbillId){		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", loanbillId);
		String resultString = sendRequestService.sendRequest(ConfigManager.LOANBILL_DELETE_BYIDS,jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			return this.ajaxForwardSuccess("删除贷款标成功","",null,null);
		}else{
			return this.ajaxForwardError(dataMessage.getMsg(), null, null, null);
		}
	}
	
	
	
	/**
	 * 查询用户
	 * @return
	 */
	@RequestMapping("/findCustomerList")
	public ModelAndView findCustomerList(HttpServletRequest request){
		JSONObject jsonobject = RequestUtil.fromRequestToJson(request);
		
		if(jsonobject.get("numPerPage")!=null && !jsonobject.get("numPerPage").equals("null")){
			jsonobject.put("p2bPageSize",jsonobject.get("numPerPage"));
		} else {
			jsonobject.put("p2bPageSize","20");
		}
		if(request.getParameter("pageNum")!=null && !request.getParameter("pageNum").equals("null")){
			jsonobject.put("p2bPageNum", jsonobject.get("pageNum"));
		}else{
			jsonobject.put("p2bPageNum", "1");
		}
			
		String returnString = sendRequestService.sendRequest(ConfigManager.LOAN_CUSTOMER_LIST,jsonobject);
		DataPageMessage dataMessage = MessageUtil.parseDatePageMessageForP2b(returnString);
		ModelAndView modelAndView = new ModelAndView("trading/loan/customerList");
		modelAndView.addObject("dataMessage", dataMessage);
		modelAndView.addObject("jsonobject",jsonobject);
		return modelAndView;
	}
	
	/**
	 * 获取贷款列表(匹配用)
	 * @param request
	 * @return
	 */
	@RequestMapping("/getloanViewList")
	public ModelAndView getloanViewList(HttpServletRequest request){	
        ModelAndView modelAndView = new ModelAndView("trading/product/order/LoanList");
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
        jsonObject.put("loanStatus", "2");//投标中
		String resultString = sendRequestService.sendRequest(ConfigManager.LOAN_FINDBYVIEWLIST,jsonObject);
		DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
		DateUtil.formatDateByDataPageMessage(dataMessage);
		modelAndView.addObject("dataMessage", dataMessage);
		modelAndView.addObject("minMoney", jsonObject.get("minMoney"));
		modelAndView.addObject("maxMoney", jsonObject.get("maxMoney"));
		modelAndView.addObject("month", jsonObject.get("month"));
		return modelAndView;
	}
}


