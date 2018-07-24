<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
</head>
<body>
<form id="pagerForm" method="post" action="${pageContext.request.contextPath }/sysMenu/getSysMenuList.htm">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${sysMenu.numberPerPage}" />
</form>

<form rel="pagerForm" onsubmit="return navTabSearch(this);" >
<div class="pageHeader">

	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					菜单名称：<input type="text" name="menuName" value="${sysMenu.menuName }" />
				</td>
				<td>
					菜单编码：<input type="text" name="menuCode" value="${sysMenu.menuCode }" />
				</td>
				<td>
					<select class="combox" name="menuLevel">
						<option value="">菜单级别</option>
						<option value="1" <c:if test="${ sysMenu.menuLevel == '1'}">selected</c:if>>一级菜单</option>
						<option value="2" <c:if test="${ sysMenu.menuLevel == '2'}">selected</c:if>>二级菜单</option>
					</select>
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
			<li><a class="add" href="${pageContext.request.contextPath }/sysMenu/forwordMerge.htm" target="navTab"><span>添加菜单</span></a></li>
			<li><a class="edit" href="${pageContext.request.contextPath }/sysMenu/forwordMerge.htm?1=1&sysMenuId={sid_user}" target="navTab" mask="true" warn="请选择菜单"><span>修改菜单</span></a></li>
			<li><a class="delete" href="${pageContext.request.contextPath }/sysMenu/delMenu.htm?1=1&sysMenuId={sid_user}" target="ajaxTodo" title="确定要删除吗?"><span>删除菜单</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th>菜单名称</th>
				<th>菜单编码</th>
				<th>菜单级别</th>
				<th>菜单状态</th>
				<th>父级菜单</th>
				<th>菜单排序号</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="menu" items="${sysMenuList }">
				<tr target="sid_user" rel="${menu.id} ">
					<td>${menu.menuName}</td>
					<td>${menu.menuCode }</td>
					<td>${menuLevelMap[menu.menuLevel] }</td>
					<td>${menu.menuStatus }</td>        
					<td>${menu.parentName }</td>
					<td>${menu.menuSort }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="1">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<span>条，共${sysMenu.totalCount}条</span>
		</div>
		
		<div class="pagination" targetType="navTab" totalCount="${sysMenu.totalCount }" numPerPage="${sysMenu.numberPerPage }" pageNumShown="10" currentPage="${sysMenu.pageNum }"></div>

	</div>
</div>

</body>
</html>