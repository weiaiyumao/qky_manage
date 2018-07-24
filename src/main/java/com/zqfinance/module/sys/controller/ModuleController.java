/**
 * 功能模块controller
 * @author 许进
 */
package com.zqfinance.module.sys.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.zqfinance.module.sys.domain.Module;
import com.zqfinance.module.sys.service.ModuleService;
import com.zqfinance.system.config.ManageConfig;
import com.zqfinance.system.controller.DwzAjaxController;
import com.zqfinance.system.util.MStringUtil;
@Controller
@RequestMapping("/module")
public class ModuleController extends DwzAjaxController {
	@Autowired
	private ModuleService moduleService;
	/**
	 * 查询module列表
	 * @author 许进
	 */
	@RequestMapping("/getModuleList")
	public ModelAndView getModuleList(@ModelAttribute("module")Module module){ 
		ModelAndView modelAndView = new ModelAndView("sysmgr/moduleList");
		int totalCount = moduleService.getModuleCount();
		module.setTotalCount(totalCount);
		List<Module> moduleList = moduleService.getModuleList(module);
		modelAndView.addObject("moduleList", moduleList);
		return modelAndView;
	}
	/**
	 * 跳转到修改或者新增页面
	 * @author 许进
	 */
	@RequestMapping("/forwordMerge")
	public ModelAndView forwordMerge(String moduleId){
 		ModelAndView modelAndView = null;
 		String moduleJson = new String();
		//跳转到修改页面
		if(MStringUtil.isNotEmpty(moduleId)){
			//查询菜单基本信息以及菜单相关按钮
			Module module = moduleService.getModuleById(Long.valueOf(moduleId.trim()));
			modelAndView = new ModelAndView("sysmgr/moduleUpdate");
			modelAndView.addObject("module", module);
		    moduleJson = moduleService.getModuleJson(moduleId.trim(),String.valueOf(module.getParentId()));
		}
		//跳转到新增页面
		else{
			//查询出所有的模块信息
			moduleJson = moduleService.getModuleJson(null,null);
			modelAndView = new ModelAndView("sysmgr/moduleAdd");
		}
		modelAndView.addObject("moduleJson", moduleJson);
		return modelAndView;
	}
	/**
	 * 保存功能模块信息
	 * @author 许进
	 */
	@RequestMapping("/mergeModule")
	public @ResponseBody DwzAjaxController mergeModule(Module module){
		//修改
		if(module.getId() > 0){
			moduleService.updateModule(module);
			return ajaxForwardSuccess("修改功能模块成功","",ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT,null);
		}
		//新增
		else{
			moduleService.saveModule(module);
			return ajaxForwardSuccess("保存功能模块成功","",ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT,null);
		}

	}
	
	/**
	 * 根据父级菜单查询字迹菜单
	 * @author 许进
	 */
	@RequestMapping("/getModuleListByParentId")
	public @ResponseBody List<Module> getModuleListByParentId(String moduleId){
		List<Module> moduleList = new ArrayList<Module>();
		if(StringUtils.isNotEmpty(moduleId)){
			moduleList = moduleService.getSecondModule(Long.valueOf(moduleId));
		}
		return moduleList;
	}
	
	@RequestMapping("/delModule")
	public @ResponseBody DwzAjaxController delModule(String moduleId){
		moduleService.delModule(Long.valueOf(moduleId.trim()));
		return ajaxForwardSuccess("删除模块成功","",null,null);
	}
	
	/**
	 * 判断功能名称是否重复
	 * @param moduleName
	 * @return
	 * @author 宋宇
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/hasModuleName")
	public @ResponseBody String hasModuleName(String moduleName,String id) throws UnsupportedEncodingException{
		Module module = new Module();
		module.setModuleName(new String(moduleName.getBytes("ISO8859_1"), "UTF-8"));
		if(null!=id){
			//修改用
			module.setId(Long.parseLong(id));
			Module moduletwo = moduleService.getModuleByNameOrId(module);
			if(null == moduletwo){
				//查是否有重名的
				Module modulethree = new Module();
				modulethree.setModuleName(new String(moduleName.getBytes("ISO8859_1"), "UTF-8"));
				Module modulefour = moduleService.getModuleByNameOrId(modulethree);
				if(null==modulefour){
					return "true";
				}else{
					return "false";
				}
			}else{
				return "true";
			}
		}else{
			Module modules = moduleService.getModuleByNameOrId(module);
			//添加用
			if(null == modules){
				return "true"; 
			}else{
				return "false";
			}
		}
	}
}

