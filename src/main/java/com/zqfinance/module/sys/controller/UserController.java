/**
 * 用户管理
 */

package com.zqfinance.module.sys.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zqfinance.module.sys.domain.Role;
import com.zqfinance.module.sys.domain.User;
import com.zqfinance.module.sys.domain.UserRoleMap;
import com.zqfinance.module.sys.service.RoleService;
import com.zqfinance.module.sys.service.UserService;
import com.zqfinance.system.config.ManageConfig;
import com.zqfinance.system.controller.DwzAjaxController;
import com.zqfinance.system.util.DateUtil;
import com.zqfinance.system.util.MStringUtil;

@Controller
@RequestMapping("/user")
public class UserController extends DwzAjaxController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/getUserList")
	public ModelAndView getUserList(User user){ 
		ModelAndView modelAndView = new ModelAndView("sysmgr/userList");
		int totalCount = userService.getUserCount(user);
		user.setTotalCount(totalCount);
		List<User> userList = userService.getUserList(user);
		modelAndView.addObject("userList", userList);
		//分页相关内容
		modelAndView.addObject("user", user);
		return modelAndView;
	}
	/**
	 * 新增或者修改页面条状
	 * @author 许进
	 */
	@RequestMapping("/forwordUserMerge")
	public ModelAndView forwordUserMerge(String userId){
		//修改页面
		//查询出所有的角色
		List<Role> roleList = roleService.getRoleList(new Role());

		ModelAndView modelAndView = null;
		if(MStringUtil.isNotEmpty(userId)){		
			User user = userService.getUserById(Long.valueOf(userId.trim()));
			List<UserRoleMap> userRoleList = userService.getRoleListByUserId(Long.valueOf(userId.trim()));
			List<Long> roleIdList = new ArrayList<Long>();
			StringBuffer su = new StringBuffer();
			for(UserRoleMap userRoleMap : userRoleList){
				roleIdList.add(userRoleMap.getRoleId());
				su.append(userRoleMap.getRoleId());
				su.append(",");
			}
			user.setRoleIds(su.toString());
			for(Role role : roleList){
				if(roleIdList.contains(role.getId())){
					role.setCheckFlag("checked");
				}
			}
			modelAndView = new ModelAndView("sysmgr/userUpdate");
			modelAndView.addObject("user", user);
		}else{
		    modelAndView = new ModelAndView("sysmgr/userAdd");
		}
		modelAndView.addObject("roleList", roleList);
		return modelAndView;
	}
	
	/**
	 * 修改密码（管理员）
	 * @author 许进
	 */
	@RequestMapping("/forwordUserPass")
	public ModelAndView forwordUserPass(String userId){
		//修改页面
		ModelAndView modelAndView = new ModelAndView("sysmgr/userUpdatePass");
		User user = userService.getUserById(Long.valueOf(userId.trim()));
		modelAndView.addObject("user", user);
		return modelAndView;
	}
	
	/**
	 * 修改密码(个人)
	 * @author 许进
	 */
	@RequestMapping("/forwordUserPwd")
	public ModelAndView forwordUserPwd(String userId){
		//修改页面
		ModelAndView modelAndView = new ModelAndView("common/userUpdatePass");
		User user = userService.getUserById(Long.valueOf(userId));
		modelAndView.addObject("user", user);
		return modelAndView;
	}
	
	@RequestMapping("/mergeUser")
	public @ResponseBody DwzAjaxController mergeUser(@ModelAttribute("user")User user){
		//修改
		Md5PasswordEncoder mpe = new Md5PasswordEncoder();
		if(null!=user && user.getId() > 0){
			userService.updateUser(user);
			return ajaxForwardSuccess("修改用户成功","",ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT,null);
		}
		//新增
		else{
			
			if(!MStringUtil.isNotEmpty(user.getRoleIds())){
				return ajaxForwardError("请选择用户角色","",null,null);
			}
			user.setLastLoginTime(DateUtil.fmt14Date(new Date()));
			//默认密码
			user.setUserPass(mpe.encodePassword("123456",user.getUserName()));
			userService.addUser(user);
			return ajaxForwardSuccess("新增用户成功","",ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT,null);
		}
	
	}
	
	/**
	 * 个人修改
	 * @param user
	 * @return
	 */
	@RequestMapping("/mergeUserPwd")
	public @ResponseBody DwzAjaxController mergeUserPwd(@ModelAttribute("user")User user){
		
		Md5PasswordEncoder mpe = new Md5PasswordEncoder();
		User users = userService.getUserById(Long.valueOf(user.getId()));
		
		if(!users.getUserPass().equals(mpe.encodePassword(user.getUserPassword(),users.getUserName()))){
			return ajaxForwardError("原始密码输入不正确，修改失败！", null, null, null);
		}
		
		if(mpe.isPasswordValid(mpe.encodePassword(user.getUserPass(),users.getUserName()), user.getUserPwd(), users.getUserName())){
			//加密
			users.setUserPass(mpe.encodePassword(user.getUserPass(),users.getUserName()));
			userService.updateUserPass(users);
			return ajaxForwardSuccess("修改密码成功",null,ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT,null);
		}else{
			return ajaxForwardError("修改密码失败，2次输入的密码不一致！", null, null, null);
		}
	}
	
	
	/**
	 * 超级管理员修改
	 * @param user
	 * @return
	 */
	@RequestMapping("/mergeUserPass")
	public @ResponseBody DwzAjaxController mergeUserPass(@ModelAttribute("user")User user){
		
		Md5PasswordEncoder mpe = new Md5PasswordEncoder();
		User users = userService.getUserById(Long.valueOf(user.getId()));
		if(mpe.isPasswordValid(mpe.encodePassword(user.getUserPass(),users.getUserName()), user.getUserPwd(), users.getUserName())){
			//加密
			users.setUserPass(mpe.encodePassword(user.getUserPass(),users.getUserName()));
			userService.updateUserPass(users);
			return ajaxForwardSuccess("修改密码成功",null,ManageConfig.DWZ_CALLBACK_TYPE_CLOSECURRENT,null);
		}else{
			return ajaxForwardError("修改密码失败，2次输入的密码不一致！", null, null, null);
		}
	}
	
	/**
	 * 删除用户
	 * @author 许进
	 */
	@RequestMapping("/delUser")
	public @ResponseBody DwzAjaxController delUser(String userId){
		userService.delUser(Long.valueOf(userId));
		return ajaxForwardSuccess("删除用户成功","",null,null);
	}
	
	/**
	 * 判断用户工号或者登陆账号是否重复
	 * @param cardNo
	 * @return
	 * @author 宋宇
	 */
	@RequestMapping("/hasUserCardnoOrName")
	public @ResponseBody String hasUserCardnoOrName(String cardNo,String userName,String id){
		User user = new User();
		if(null!=id){
			user.setId(Long.parseLong(id));
			if(null!=cardNo){
				user.setCardNo(cardNo);
				User userTwo = userService.getUserByNameOrCardNoOrId(user);
				if(null==userTwo){
					//检查是否有重名
					User userParm = new User();
					userParm.setCardNo(cardNo);
					User userThree = userService.getUserByNameOrCardNoOrId(userParm);
					if(null==userThree){
						return "true";
					}else{
						return "false";
					}
				}else{
					return "true";
				}
			}
			if(null!=userName){
				user.setUserName(userName);
				User userTwo = userService.getUserByNameOrCardNoOrId(user);
				if(null==userTwo){
					//检查是否有重名
					User userParm = new User();
					userParm.setUserName(userName);
					User userThree = userService.getUserByNameOrCardNoOrId(userParm);
					if(null==userThree){
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
			if(null!=cardNo){
				user.setCardNo(cardNo);
				User users = userService.getUserByNameOrCardNoOrId(user);
				if(null==users){
					return "true";
				}else{
					return "false";
				}
			}
			if(null!=userName){
				user.setUserName(userName);
				User users = userService.getUserByNameOrCardNoOrId(user);
				if(null==users){
					return "true";
				}else{
					return "false";
				}
			}
		}
		return "false";
	}
}

