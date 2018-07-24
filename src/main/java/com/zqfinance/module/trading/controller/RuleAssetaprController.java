package com.zqfinance.module.trading.controller;

import java.util.HashMap;
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
import com.zqfinance.system.util.MessageUtil;
import com.zqfinance.system.util.RequestUtil;

@Controller
@RequestMapping("/ruleAssetapr")
public class RuleAssetaprController extends DwzAjaxController{
	/**
	 * 获取列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getRuleAssetaprList")
	public ModelAndView getLawList(HttpServletRequest request){	
		
		HttpSession session =  request.getSession(false);
		com.zqfinance.module.sys.domain.User user = null;
		if(null != session){
			user = (com.zqfinance.module.sys.domain.User) session.getAttribute(ManageConfig.LOGIN_USER);
			if(null != user){
				//保存成功登陆日志
				Loginfo loginfo = new Loginfo();
				loginfo.setUserId(user.getId());
				loginfo.setUserName(user.getUserName());
				loginfo.setModuleName("配置利率管理");
				loginfo.setLoginStatus(ManageConfig.loginStatus_SUCCESS);
				loginfoService.addLoginfo(loginfo);
			}
		}
		
		ModelAndView modelAndView = new ModelAndView("trading/assetapr/assetaprList");
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String resultString = sendRequestService.sendRequest(ConfigManager.RULEASSETAPR_FINDBYCONDITION,jsonObject);
		DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
		DateUtil.formatDateByDataPageMessage(dataMessage);
		modelAndView.addObject("dataMessage", dataMessage);
		modelAndView.addObject("beginDate",jsonObject.get("beginDate"));
		modelAndView.addObject("endDate",jsonObject.get("endDate"));
		modelAndView.addObject("apr",jsonObject.get("apr"));
		return modelAndView;
	}
	
	/**
	 * 跳转修改页面
	 * @param investProductId
	 * @return
	 */
	@RequestMapping("/forwardEdit")
	public ModelAndView forwardEdit(String id){	
		ModelAndView modelAndView = findRuleAssetaprById(id,"edit");
		modelAndView.addObject("reqBtn","edit");
		return modelAndView;
	}
	
	
	/**
	 * 跳转详情页面
	 * @param investProductId
	 * @return
	 */
	@RequestMapping("/forwardInfo")
	public ModelAndView forwardInfo(String id){	
		ModelAndView modelAndView = findRuleAssetaprById(id,"info");
		modelAndView.addObject("reqBtn","info");
		return modelAndView;
	}
	
	/**
	 * 跳转修改称谓页面
	 * @param investProductId
	 * @return
	 */
	@RequestMapping("/forwardEditCustomerTitle")
	public ModelAndView forwardEditCustomerTitle(String id){	
		ModelAndView modelAndView = findRuleAssetaprById(id,"updateTitle");
		modelAndView.addObject("reqBtn","updateTitle");
		return modelAndView;
	}
	
	/**
	 * 跳转添加页面
	 * @param investProductId
	 * @return
	 */
	@RequestMapping("/forwardAdd")
	public ModelAndView forwardAdd(){	
		ModelAndView modelAndView = new ModelAndView("trading/assetapr/assetaprAdd");
		//获取maxmoney最大的记录
	    JSONObject maxjsonObject = new JSONObject();
	    String maxresultString = sendRequestService.sendRequest(ConfigManager.RULEASSETAPR_GETBYLASTRULEASSETAPR,maxjsonObject);
	    DataMessage maxdataMessage = MessageUtil.parseDataMessage(maxresultString);
	    if(null == maxdataMessage.getData()){
	    	Map<String,String> dataMap = new HashMap<String,String>();
	    	dataMap.put("maxmoney", "0");
	    	maxdataMessage.setData(dataMap);
	    }
	    modelAndView.addObject("maxdataMessage", maxdataMessage);
		return modelAndView;
	}
	
	public ModelAndView findRuleAssetaprById(String id,String reqBtn){
		ModelAndView modelAndView = null;
		if(reqBtn.equals("info")){
			modelAndView = new ModelAndView("trading/assetapr/assetaprInfo");
		}else if(reqBtn.equals("edit")){
			modelAndView = new ModelAndView("trading/assetapr/assetaprEdit");
		}else if(reqBtn.equals("updateTitle")){
			modelAndView = new ModelAndView("trading/assetapr/assetaprEditCutomertitle");
		}
		JSONObject jsonObject = new JSONObject();
	    jsonObject.put("id", id.trim());
	    String resultString = sendRequestService.sendRequest(ConfigManager.RULEASSETAPR_GETBYID,jsonObject);
	    DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
	    DateUtil.formatDateByDataMessage(dataMessage);
		modelAndView.addObject("dataMessage", dataMessage);
		return modelAndView;
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delassetapr")
	public @ResponseBody DwzAjaxController delLaw(String ids){		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ids", ids.trim());
		String resultString = sendRequestService.sendRequest(ConfigManager.RULEASSETAPR_DEL,jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			return this.ajaxForwardSuccess("删除账户资产收益率规则成功！","",null,null);
		}else{
			return this.ajaxForwardError(dataMessage.getMsg(), null, null, null);
		}
	}
	
	/**
	 * 添加
	 * @param request
	 * @return
	 */
	@RequestMapping("/addassetapr")
	public @ResponseBody DwzAjaxController addRuleAssetapr(HttpServletRequest request){
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
        String resultString = sendRequestService.sendRequest(ConfigManager.RULEASSETAPR_ADD,jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			return ajaxForwardSuccess("增加账户资产收益率规则成功！","",ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT,null);
		}else{
			return ajaxForwardError(dataMessage.getMsg(), null, null, null);
		}	
	} 
	
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateassetapr")
	public @ResponseBody DwzAjaxController updateRuleAssetapr(HttpServletRequest request){
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
        String resultString = sendRequestService.sendRequest(ConfigManager.RULEASSETAPR_UPDATE,jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			return ajaxForwardSuccess("修改账户资产收益率规则成功！","",ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT,null);
		}else{
			return ajaxForwardError(dataMessage.getMsg(), null, null, null);
		}
	} 

	@RequestMapping("/updateassetaprTitle")
	public @ResponseBody DwzAjaxController updateassetaprTitle(HttpServletRequest request){
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
        String resultString = sendRequestService.sendRequest(ConfigManager.RULEASSETAPR_UPDATETITLE,jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			return ajaxForwardSuccess("修改称谓成功！","",ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT,null);
		}else{
			return ajaxForwardError(dataMessage.getMsg(), null, null, null);
		}
	} 
}
