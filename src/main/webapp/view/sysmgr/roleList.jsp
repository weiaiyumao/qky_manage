<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="mgr" uri="http://rrdai.com/mgr-tags"%> 
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
</head>
<body>
<form id="pagerForm" method="post" action="${pageContext.request.contextPath }/role/getRoleList.htm">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${role.numPerPage}" />
</form>

<form rel="pagerForm" onsubmit="return navTabSearch(this);" >
<div class="pageHeader">

	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					角色名称：<input type="text" name="roleName" value="${role.roleName }" />
				</td>
	
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
</div>
</form>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><mgr:button href="${pageContext.request.contextPath }/role/forwordMerge.htm" target="navTab" value="新增角色" cssClass="add"></mgr:button></li>
			<li><mgr:button href="${pageContext.request.contextPath }/role/forwordMerge.htm?1=1&roleId={id_role}" target="navTab" value="修改角色" warn="请选择需要修改的角色" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${pageContext.request.contextPath }/role/delRole.htm?1=1&roleId={id_role}" target="ajaxTodo" value="删除角色" title="确定要删除吗?" cssClass="delete"></mgr:button></li>
			
			</ul>
	</div>
	<table class="table" width="50%" layoutH="156">
		<thead>
			<tr>
				<th width="25%">角色名称</th>
				<th width="25%">角色编码</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="role" items="${roleList }">
				<tr target="id_role" rel="${role.id} ">
					<td>${role.roleName}</td>
					<td>${role.roleCode}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<span>条，共${role.totalCount}条</span>
		</div>
		
		<div class="pagination" targetType="navTab" totalCount="${role.totalCount }" numPerPage="${role.numPerPage }" pageNumShown="10" currentPage="${role.pageNum }"></div>

	</div>
</div>

</body>
</html>