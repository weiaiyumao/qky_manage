

package com.zqfinance.module.sys.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zqfinance.module.sys.domain.Action;
import com.zqfinance.module.sys.domain.Module;
import com.zqfinance.module.sys.service.ActionService;
import com.zqfinance.module.sys.service.ModuleService;
import com.zqfinance.system.config.ManageConfig;
import com.zqfinance.system.controller.DwzAjaxController;
import com.zqfinance.system.util.MStringUtil;
import com.zqfinance.system.util.RoleUtil;
@Controller
@RequestMapping("/action")
public class ActionController extends DwzAjaxController {
	@Autowired
	private ActionService actionService;
	@Autowired
	private ModuleService moduleService;
	
	private final String  INVOID_ID = "0";
	/**
	 * 查询action列表
	 * @author 许进
	 */
	@RequestMapping("/getActionList")
	public ModelAndView getModuleList(@ModelAttribute("action")Action action){ 
		ModelAndView modelAndView = new ModelAndView("sysmgr/actionList");
		int totalCount = actionService.getActionCount();
		action.setTotalCount(totalCount);
		List<Action> actionList = actionService.getActionList(action);
		modelAndView.addObject("actionList", actionList);
		return modelAndView;
	}
	/**
	 * 跳转到新增或者修改页面
	 * @author 许进
	 */
	@RequestMapping("/forwordMerge")
	public ModelAndView forwordMerge(String actionId){
		ModelAndView modelAndView = null;
		//修改
		String moduleJson = moduleService.getModuleJsonForAction(null);
		if(MStringUtil.isNotEmpty(actionId)){
			modelAndView = new ModelAndView("sysmgr/actionUpdate");
			Action action = actionService.getActionById(Long.parseLong(actionId.trim()));
			modelAndView.addObject("action", action);
			moduleJson = moduleService.getModuleJsonForAction(String.valueOf(action.getModuleId()));
			
		}else{
			modelAndView = new ModelAndView("sysmgr/actionAdd");
		}
		modelAndView.addObject("moduleJson", moduleJson);
		return modelAndView;
		
	}
	/**
	 * 查询二级功能模块
	 * @author 许进
	 */
	@RequestMapping("/getSecondModule")
	public @ResponseBody JSONArray getSecondModule(String parentId){
		JSONArray jsonArray = new JSONArray();
		if(!INVOID_ID.equals(parentId)){
			List<Module> moduleList = moduleService.getSecondModule(Long.parseLong(parentId));
			String[][] arr = new String[moduleList.size()][2];
			for(int i=0;i<moduleList.size();i++){
				Module module = moduleList.get(i);
				arr[i][0] = String.valueOf(module.getId());
				arr[i][1] = module.getModuleName();
			}
			jsonArray = JSONArray.fromObject(arr);
		}else{
			String[][] arr = new String[1][2];
			arr[0][0] = INVOID_ID;
			arr[0][1] = "请选择二级模块";
		}
		
		return jsonArray;
	}
	
	/**
	 * 新增或者删除Aciton
	 * @author 许进
	 */
	@RequestMapping("/mergeAction")
	public @ResponseBody DwzAjaxController mergeAction(Action action){
		//修改
		if(action.getId() > 0){
			actionService.updateAction(action);
			RoleUtil.loadRole();
			return ajaxForwardSuccess("修改功能成功","",ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT,null);
		}
		//新增
		else{
			actionService.addAction(action);
			RoleUtil.loadRole();
			return ajaxForwardSuccess("新增功能成功","",ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT,null); 

		}
	}
	
	/**
	 * 删除action
	 * @author 许进
	 */
	@RequestMapping("/delAction")
	public @ResponseBody DwzAjaxController delAction(String actionId){
		actionService.delAction(Long.valueOf(actionId.trim()));
		return ajaxForwardSuccess("删除功能成功","",null,null); 
	}
	
	/**
	 * 判断功能名称是否重复
	 * @param actionName
	 * @return
	 * @author 宋宇
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/hasActionName")
	public @ResponseBody String hasActionName(String actionName,String id) throws UnsupportedEncodingException{
		Action action = new Action();
		action.setActionName(new String(actionName.getBytes("ISO8859_1"), "UTF-8"));
		if(null!=id){
			//修改用
			action.setId(Long.parseLong(id));
			Action actiontwo = actionService.getActionByNameOrId(action);
			if(null == actiontwo){
				//查是否有重名的
				Action actionthree = new Action();
				actionthree.setActionName(new String(actionName.getBytes("ISO8859_1"), "UTF-8"));
				Action actionfour = actionService.getActionByNameOrId(actionthree);
				if(null==actionfour){
					return "true";
				}else{
					return "false";
				}
			}else{
				return "true";
			}
		}else{
			Action actions = actionService.getActionByNameOrId(action);
			//添加用
			if(null == actions){
				return "true"; 
			}else{
				return "false";
			}
		}
	}

}

