<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ taglib prefix="mgr" uri="http://rrdai.com/mgr-tags"%> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统管理菜单</title>
</head>
<body>
	<div  class="accordion" fillSpace="sidebar">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>功能列表
			</h2>
		</div>
		<div id="sysmenuleft" class="accordionContent">
			<ul class="tree treeFolder">
				<li><a href="javascript:void(0)">系统管理</a>
					<ul>
						<li><mgr:menu rel="usermgr"
								href="${pageContext.request.contextPath }/user/getUserList.htm"
								value="用户管理" target="navTab"></mgr:menu></li>
						<li><mgr:menu rel="rolemgr"
								href="${pageContext.request.contextPath }/role/getRoleList.htm"
								value="角色管理" target="navTab"></mgr:menu></li>
						<li><mgr:menu rel="actionmgr"
								href="${pageContext.request.contextPath }/action/getActionList.htm"
								value="功能管理" target="navTab"></mgr:menu></li>
						<li><mgr:menu rel="modulemgr"
								href="${pageContext.request.contextPath }/module/getModuleList.htm"
								value="模块管理" target="navTab"></mgr:menu></li>
						<li><mgr:menu rel="loginfomgr"
								href="${pageContext.request.contextPath }/loginfo/getLoginfoList.htm"
								value="登陆日志管理" target="navTab"></mgr:menu></li>		
					</ul></li>

			</ul>
		</div>
	</div>

</body>
</html>