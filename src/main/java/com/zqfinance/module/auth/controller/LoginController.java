/**
 * 登录入口
 * @author 许进
 */
package com.zqfinance.module.auth.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zqfinance.module.customer.domain.Customer;
import com.zqfinance.system.common.DataMessage;
import com.zqfinance.system.common.DataPageMessage;
import com.zqfinance.system.config.ConfigManager;
import com.zqfinance.system.config.ManageConfig;
import com.zqfinance.system.controller.DwzAjaxController;
import com.zqfinance.system.util.DateUtil;
import com.zqfinance.system.util.MessageUtil;
import com.zqfinance.system.util.RequestUtil;
import com.zqfinance.system.util.SpringUtil;

@Controller
public class LoginController extends DwzAjaxController {
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login")
    public String login() {
        logger.debug("this is loginAction login");
        return "login";
    }

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request,HttpServletResponse response) {    	

        return "index";
    }
    
    @RequestMapping(value = "/getIndexmessage")
    public String getIndexMessage(HttpServletRequest request,HttpServletResponse response) {
    	
        return "index";
    }

    /**
     * 指定无访问额权限页面
     * 
     * @return
     */
    @RequestMapping(value = "/denied", method = RequestMethod.GET)
    public String getDeniedPage() {
        logger.debug("Received request to show denied page");
        return "403";

    }
    
    @RequestMapping(value = "/forwordCustomerSessionPage")
    public ModelAndView forwordCustomerSessionPage(HttpServletRequest request){
    	ModelAndView modelAndView = null;
    	if(request.getSession().getAttribute(ManageConfig.SESSION_CUSTOMER) != null){
    		modelAndView = new ModelAndView("common/customerInSession");
    	}else{
    		modelAndView = new ModelAndView("common/customerNoSession");
    	}
    	
    	return modelAndView;
    }
    
    @RequestMapping(value = "/saveCustomerInSession")
    public @ResponseBody DwzAjaxController saveCustomerInSession(HttpServletRequest request,String id){    	
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put("customerIds", id.trim());
		String resultString = sendRequestService.sendRequest(ConfigManager.LOAN_FINDBYCUSTOMERID,jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessageForP2b(resultString);
		Customer customer = new Customer();
		customer.setCustomerId(dataMessage.getData().get("customerid"));
		customer.setEmail(dataMessage.getData().get("email"));
		if(StringUtils.isNotBlank(dataMessage.getData().get("isautologin"))){
			customer.setIsAutoLogin(Integer.parseInt(dataMessage.getData().get("isautologin")));
		}
		customer.setMobile(SpringUtil.formatNum(dataMessage.getData().get("mobile"), 3));
		customer.setUsername(SpringUtil.formatName(dataMessage.getData().get("name")));
		customer.setIdcard(SpringUtil.formatNum(dataMessage.getData().get("idcard"), 4));
		request.getSession().setAttribute(ManageConfig.SESSION_CUSTOMER, customer);
    	request.getSession().setAttribute(ManageConfig.SESSION_CUSTOMER_ID, customer.getCustomerId());
    	List<Object> cuList = new ArrayList<Object>();
    	cuList.add(customer);
		return ajaxForwardSuccess("客户信息已选择",ManageConfig.DWZ_CALLBACK_TYPE_FORWARD , ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT, cuList);
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
		//DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(returnString);
		SpringUtil.formatDateByDataPageMessage(dataMessage);
//		DateUtil.formatDateByDataPageMessage(dataMessage);
		ModelAndView modelAndView = new ModelAndView("common/customerList");
		modelAndView.addObject("dataMessage", dataMessage);
		modelAndView.addObject("jsonobject",jsonobject);
		return modelAndView;
	}
	
	@RequestMapping("/findPublicCustomerList")
	public ModelAndView findPublicCustomerList(HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView("common/publicCustomerList");
		
		JSONObject jsonobject = RequestUtil.fromRequestToJson(request);
		if(jsonobject.get("id") == null && jsonobject.get("name") == null && jsonobject.get("mobile") == null && jsonobject.get("idcard") == null){
			return modelAndView;
		}
		if(jsonobject.get("id").toString().equals("") && jsonobject.get("name").toString().equals("") && jsonobject.get("mobile").toString().equals("") && jsonobject.get("idcard").toString().equals("")){
			return modelAndView;
		}
		
		if(jsonobject.get("numPerPage")!=null && StringUtils.isNotBlank(jsonobject.get("numPerPage").toString())){
			jsonobject.put("p2bPageSize",jsonobject.get("numPerPage"));
		} else {
			jsonobject.put("p2bPageSize","20");
		}
		if(request.getParameter("pageNum")!=null && StringUtils.isNotBlank(request.getParameter("pageNum").toString())){
			jsonobject.put("p2bPageNum", jsonobject.get("pageNum"));
		}else{
			jsonobject.put("p2bPageNum", "1");
		}
		String returnString = sendRequestService.sendRequest(ConfigManager.LOAN_SINGLECUSTOMER_LIST,jsonobject);
		DataPageMessage dataMessage = MessageUtil.parseDatePageMessageForP2b(returnString);
		//DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(returnString);
		SpringUtil.formatDateByDataPageMessage(dataMessage);
		DateUtil.formatDateByDataPageMessage(dataMessage);
		modelAndView.addObject("dataMessage", dataMessage);
		modelAndView.addObject("jsonobject",jsonobject);
		return modelAndView;
	}
    
    @RequestMapping(value = "/cancelCustomerInSession")
    public @ResponseBody DwzAjaxController cancelCustomerInSession(HttpServletRequest request,Customer customer){    	
    	//实际应该通过查询条件充 usercenter取得customer的数据存放到session中 	
    	request.getSession().removeAttribute(ManageConfig.SESSION_CUSTOMER);
    	request.getSession().removeAttribute(ManageConfig.SESSION_CUSTOMER_ID);
    	//request.getSession().removeAttribute(ManageConfig.LOGIN_USER);
    	return ajaxForwardSuccess("客户信息已注销",null , ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT, null);
    }
}

