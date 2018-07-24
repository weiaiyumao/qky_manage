/**
 * role controller
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

import com.zqfinance.module.sys.domain.Role;
import com.zqfinance.module.sys.domain.RoleActionMap;
import com.zqfinance.module.sys.service.ActionService;
import com.zqfinance.module.sys.service.ModuleService;
import com.zqfinance.module.sys.service.RoleService;
import com.zqfinance.system.config.ManageConfig;
import com.zqfinance.system.controller.DwzAjaxController;
import com.zqfinance.system.util.MStringUtil;
import com.zqfinance.system.util.RoleUtil;

@Controller
@RequestMapping("/role")
public class RoleController extends DwzAjaxController{
	@Autowired
	private RoleService roleService;
	@Autowired
	private ModuleService moduleServicce;
	@Autowired
	private ActionService actionService;
	
	/**
	 * 查询action列表
	 * @author 许进
	 */
	@RequestMapping("/getRoleList")
	public ModelAndView getRoleList(@ModelAttribute("role")Role role){ 
		ModelAndView modelAndView = new ModelAndView("sysmgr/roleList");
		int totalCount = roleService.getRoleCount();
		role.setTotalCount(totalCount);
		List<Role> roleList = roleService.getRoleList(role);
		modelAndView.addObject("roleList", roleList);
		return modelAndView;
	}
	/**
	 * 跳转到新增或者修改页面
	 * @author 许进
	 */
	@RequestMapping("/forwordMerge")
	public ModelAndView forwordMerge(String roleId){
		ModelAndView modelAndView = null;
		String returnJson = new String();
		//跳转到修改
		if(MStringUtil.isNotEmpty(roleId)){
			modelAndView = new ModelAndView("sysmgr/roleUpdate");
			//查询role对象
			Role role = roleService.getRoleById(Long.valueOf(roleId.trim()));
			List<RoleActionMap> roleActionList= roleService.getRoleActionMapByRoleId(Long.valueOf(roleId.trim()));
			List<Long> actionIdList = new ArrayList<Long>();
			StringBuffer roleAcitons = new StringBuffer();
			for(RoleActionMap roleActionMap : roleActionList){
				if(!actionIdList.contains(roleActionMap.getActionId())){
					actionIdList.add(roleActionMap.getActionId());
					roleAcitons.append(roleActionMap.getActionId());
					roleAcitons.append(",");
				}
			}
			returnJson = moduleServicce.getJsonForModuleAndAction(actionIdList);	
			role.setRoleActions(roleAcitons.toString());
			modelAndView.addObject("role", role);
			
			
		}
		//跳转到新增
		else{
			returnJson = moduleServicce.getJsonForModuleAndAction(null);
			modelAndView = new ModelAndView("sysmgr/roleAdd");		
		}
		modelAndView.addObject("returnJson", returnJson);
		return modelAndView;
	}
	
	/**
	 * 新增或者修改角色
	 * @author 许进
	 */
	@RequestMapping("/mergeRole")
	public @ResponseBody DwzAjaxController mergeRole(Role role){
		if(!MStringUtil.isNotEmpty(role.getRoleActions())){
			return ajaxForwardError("请选择角色的权限",null,null,null);
		}
		//修改
		if(role.getId() > 0){
			roleService.updateRole(role);
			RoleUtil.loadRole();
			return ajaxForwardSuccess("修改角色成功","",ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT,null);
		}
		//新增
		else{
			roleService.saveRole(role);
			RoleUtil.loadRole();
			return ajaxForwardSuccess("新增角色成功",null,ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT,null); 
		}
	}
	/**
	 * 删除角色
	 * @author 许进
	 */
	@RequestMapping("/delRole")
	public @ResponseBody DwzAjaxController delRole(String roleId){
		roleService.delRole(Long.valueOf(roleId.trim()));
		
		return ajaxForwardSuccess("删除角色成功",null,null,null); 
	}
	
	
	/**
	 * 检查角色编码角色名称是否重名
	 * @param roleCode
	 * @param roleName
	 * @return
	 * @author 宋宇
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/hasRoleRoleCodeOrRoleName")
	public @ResponseBody String hasRoleRoleCodeOrRoleName(String roleCode,String roleName,String id) throws UnsupportedEncodingException{
		Role role = new Role();
		if(null!=id){
			role.setId(Long.parseLong(id));
			if(null!=roleCode){
				role.setRoleCode(roleCode);
				Role roleTwo = roleService.getRoleByRoleNameOrRoleCodeOrId(role);
				if(null==roleTwo){
					//检查是否有重名
					Role roleParm = new Role();
					roleParm.setRoleCode(roleCode);
					Role roleThree = roleService.getRoleByRoleNameOrRoleCodeOrId(roleParm);
					if(null==roleThree){
						return "true";
					}else{
						return "false";
					}
				}else{
					return "true";
				}
			}
			if(null!=roleName){
				role.setRoleName(new String(roleName.getBytes("ISO8859_1"), "UTF-8"));
				Role roleTwo = roleService.getRoleByRoleNameOrRoleCodeOrId(role);
				if(null==roleTwo){
					//检查是否有重名
					Role roleParm = new Role();
					roleParm.setRoleName(new String(roleName.getBytes("ISO8859_1"), "UTF-8"));
					Role roleThree = roleService.getRoleByRoleNameOrRoleCodeOrId(roleParm);
					if(null==roleThree){
						return "true";
					}else{
						return "false";
					}
				}else{
					return "true";
				}
			}
		}else{
			//添加
			if(null!=roleName){
				role.setRoleName(new String(roleName.getBytes("ISO8859_1"), "UTF-8"));
				Role roles = roleService.getRoleByRoleNameOrRoleCodeOrId(role);
				if(null==roles){
					return "true";
				}else{
					return "false";
				}
			}
			if(null!=roleCode){
				role.setRoleCode(roleCode);
				Role roles = roleService.getRoleByRoleNameOrRoleCodeOrId(role);
				if(null==roles){
					return "true";
				}else{
					return "false";
				}
			}
		}
		return "false";
	}
}

