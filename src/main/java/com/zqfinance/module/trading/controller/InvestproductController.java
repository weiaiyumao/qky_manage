package com.zqfinance.module.trading.controller;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
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
@RequestMapping("/product")
public class InvestproductController extends DwzAjaxController{
	
	/**
	 * 获得理财产品列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getProductList")
	public ModelAndView getProductList(HttpServletRequest request){
		
		HttpSession session =  request.getSession(false);
		com.zqfinance.module.sys.domain.User user = null;
		if(null != session){
			user = (com.zqfinance.module.sys.domain.User) session.getAttribute(ManageConfig.LOGIN_USER);
			if(null != user){
				//保存成功登陆日志
				Loginfo loginfo = new Loginfo();
				loginfo.setUserId(user.getId());
				loginfo.setUserName(user.getUserName());
				loginfo.setModuleName("理财管理");
				loginfo.setLoginStatus(ManageConfig.loginStatus_SUCCESS);
				loginfoService.addLoginfo(loginfo);
			}
		}
		
		ModelAndView modelAndView = new ModelAndView("trading/product/productList");
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String resultString = sendRequestService.sendRequest(ConfigManager.INVESTPRODUCT_GETPAGE,jsonObject);
		DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
		DateUtil.formatDateByDataPageMessage(dataMessage);
		modelAndView.addObject("dataMessage", dataMessage);
		modelAndView.addObject("mixMoney",jsonObject.get("mixMoney"));
		modelAndView.addObject("maxMoney",jsonObject.get("maxMoney"));
		modelAndView.addObject("beginDate",jsonObject.get("beginDate"));
		modelAndView.addObject("endDate",jsonObject.get("endDate"));
		modelAndView.addObject("id",jsonObject.get("id"));
		modelAndView.addObject("title",jsonObject.get("title"));
		modelAndView.addObject("flag",jsonObject.get("flag"));
		return modelAndView;
	}
	
	/**
	 * 跳转添加页面
	 * @param investProductId
	 * @return
	 */
	@RequestMapping("/forwardAdd")
	public ModelAndView forwardAdd(HttpServletRequest request){	
		ModelAndView modelAndView = new ModelAndView("trading/product/productAdd");
		//获取合同列表
		JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		jsonObject.put("lawtype", "0");
		String resultString = sendRequestService.sendRequest(ConfigManager.LAW_FINDBYALL,jsonObject);
		DataPageMessage dataMessage = MessageUtil.parseDateListMessage(resultString);
		modelAndView.addObject("reqBtn","add");
		modelAndView.addObject("dataMessage", dataMessage);
		return modelAndView;
	}
	
	/**
	 * 跳转修改页面
	 * @param investProductId
	 * @return
	 */
	@RequestMapping("/forwardEdit")
	public ModelAndView forwardEdit(String id){	
		ModelAndView modelAndView = findProductById(id,"edit");
		return modelAndView;
	}
	
	/**
	 * 跳转详情页面
	 * @param investProductId
	 * @return
	 */
	@RequestMapping("/forwardInfo")
	public ModelAndView forwardInfo(String id){	
		ModelAndView modelAndView = findProductById(id,"info");
		return modelAndView;
	}
	
	/**
	 * 獲取 investProduct 對象
	 * @param investProductId
	 * @return
	 */
	public ModelAndView findProductById(String id,String reqBtn){
		ModelAndView modelAndView = null;
		if(reqBtn.equals("info")){
			modelAndView = new ModelAndView("trading/product/productInfo");
		}else if(reqBtn.equals("edit")){
			modelAndView = new ModelAndView("trading/product/productEdit");
		}
		JSONObject jsonObject = new JSONObject();
	    jsonObject.put("id", id.trim());
	    String resultString = sendRequestService.sendRequest(ConfigManager.INVESTPRODUCT_GETBYID,jsonObject);
	    DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
	    if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
	    	DateUtil.formatDateByDataMessage(dataMessage);
			modelAndView.addObject("dataMessage", dataMessage);
			//获取该理财的规则
			JSONObject json = new JSONObject();
			json.put("investProductId", id.trim());
			String resultString_apr =  sendRequestService.sendRequest(ConfigManager.INVESTPRODUCTAPR_LIST,json);
			DataPageMessage dataMessage_apr = MessageUtil.parseDateListMessage(resultString_apr);
			modelAndView.addObject("dataMessage_apr", dataMessage_apr);
			//获取合同列表
			JSONObject lawjson = new JSONObject();
			lawjson.put("lawtype", "0");
			String lawString = sendRequestService.sendRequest(ConfigManager.LAW_FINDBYALL,lawjson);
			DataPageMessage law_dataMessage = MessageUtil.parseDateListMessage(lawString);
			modelAndView.addObject("law_dataMessage", law_dataMessage);
			//获取理财产品的已投金额
			JSONObject jsonObject_money = new JSONObject();
			jsonObject_money.put("investProductId", id.trim());
		    String resultString_money = sendRequestService.sendRequest(ConfigManager.PRODUCTORDER_GETSUMMONEYBYINVESTPRODUCTID,jsonObject_money);
		    DataMessage dataMessage_money = MessageUtil.parseDataMessage(resultString_money);
		    modelAndView.addObject("dataMessage_money", dataMessage_money);
	    }
		return modelAndView;
	}
	
	/**
	 * 新增理财产品
	 * */
	@RequestMapping("/addproduct")
	public @ResponseBody DwzAjaxController addproduct(HttpServletRequest request){
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
        
        //利率规则列表
        JSONArray array = new JSONArray();
		Iterator<?> keys = jsonObject.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String value = jsonObject.get(key).toString();
			if(key.contains("apr")){
				if(MStringUtil.isNotEmpty(value.trim())){
					JSONObject jo = new JSONObject();
					jo.put("apr", value);
					jo.put("month", key.substring(3));
	    			array.add(jo);
				}
    			
			}
		}
		JSONObject json =  JSONObject.fromObject("{aprList:"+array.toString()+"}");
		json.put("title", jsonObject.get("title"));
		json.put("money", jsonObject.get("money"));
		json.put("note", jsonObject.get("note"));
		json.put("lower", jsonObject.get("lower"));
		json.put("ceiling", jsonObject.get("ceiling"));
		json.put("lawId", jsonObject.get("lawId"));
		String resultString = sendRequestService.sendRequest(ConfigManager.INVESTPRODUCT_SAVE,json);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		//保存理财产品
		
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			return ajaxForwardSuccess("增加理财产品成功！","",ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT,null);
		}else
			return ajaxForwardError(dataMessage.getMsg(), null, null, null);
	} 
	
	/**
	 * 修改理财产品
	 * */
	@RequestMapping("/updateProduct")
	public @ResponseBody DwzAjaxController updateProduct(HttpServletRequest request,HttpServletResponse response){
		JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
        
        //利率规则列表
        JSONArray array = new JSONArray();
		Iterator<?> keys = jsonObject.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String value = jsonObject.get(key).toString();
			if(key.contains("apr")){
				if(MStringUtil.isNotEmpty(value.trim())){
					JSONObject jo = new JSONObject();
					jo.put("apr", value);
					jo.put("month", key.substring(3));
	    			array.add(jo);
				}
    			
			}
		}
		JSONObject json =  JSONObject.fromObject("{aprList:"+array.toString()+"}");
		json.put("id", jsonObject.get("id"));
		json.put("title", jsonObject.get("title"));
		json.put("money", jsonObject.get("money"));
		json.put("note", jsonObject.get("note"));
		json.put("lower", jsonObject.get("lower"));
		json.put("ceiling", jsonObject.get("ceiling"));
		json.put("lawId", jsonObject.get("lawId"));
		String resultString = sendRequestService.sendRequest(ConfigManager.INVESTPRODUCT_UPDATE,json);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		
		//刷新首页
		initIndexService.setSessionValue(request, "/product/forwardEdit");
		
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			return ajaxForwardSuccess("修改理财产品成功！","",ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT,null);
		}else
			return ajaxForwardError(dataMessage.getMsg(), null, null, null);
	} 
	
	/**
	 * 批量审核
	 */
	@RequestMapping("/auditProduct")
	public @ResponseBody DwzAjaxController auditProduct(String ids){
		JSONObject json = new JSONObject();
		json.put("ids", ids.trim());
		String resultString = sendRequestService.sendRequest(ConfigManager.INVESTPRODUCT_AUDIT,json);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			return ajaxForwardSuccess("审核理财产品成功","",null,null);
		}
		return ajaxForwardError(dataMessage.getMsg(),"",null,null);
	}
	
	/**
	 * 删除理财产品
	 * **/
	@RequestMapping("/delproduct")
	public @ResponseBody DwzAjaxController delproduct(String id){		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id.trim());
		String resultString = sendRequestService.sendRequest(ConfigManager.INVESTPRODUCT_DEL,jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			return this.ajaxForwardSuccess("删除理财成功！","",null,null);
		}else{
			return this.ajaxForwardError(dataMessage.getMsg(), null, null, null);
		}
	}
	
}
