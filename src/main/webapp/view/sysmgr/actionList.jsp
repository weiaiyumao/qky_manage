<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/include.jsp" %>
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/resources/js/system/action.js"></script>
<title>功能管理</title>
</head>
<body>
<form id="pagerForm" method="post" action="${ctx}/action/getActionList.htm">
	<input type="hidden" name="pageNum" value="1" />
	<input id="hiddenNumPerPage" type="hidden" name="numPerPage" value="${action.numPerPage}" />
</form>

<form id="pagerForm" onsubmit="return navTabSearch(this);" >
<div class="pageHeader">

	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					模块名称：<input type="text" name="actionName" value="${action.actionName }" />
				</td>

				<td>
					功能URL：<input type="text" name="actionUrl" value="${action.actionUrl }" />
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
			<li><mgr:button href="${ctx}/action/forwordMerge.htm" target="navTab" value="新增功能" cssClass="add"></mgr:button></li>
			<li><mgr:button href="${ctx}/action/forwordMerge.htm?1=1&actionId={id_action}" target="navTab" value="修改功能" warn="请选择需要修改的功能" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/action/delAction.htm?1=1&actionId={id_action}" target="ajaxTodo" value="删除功能" title="确定要删除吗?" cssClass="delete"></mgr:button></li>
		</ul>
	</div>
	<table class="table" width="80%" layoutH="156">
		<thead>
			<tr>
				<th width="20%">功能名称</th>
				<th width="20%">功能URL</th>
				<th width="20%">功能所属模块</th>
				<th width="20%">功能排序</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="action" items="${actionList }">
				<tr target="id_action" rel="${action.id} ">
					<td>${action.actionName}</td>
					<td>${action.actionUrl}</td>
					<td>${action.moduleName}</td>
					<td>${action.aSort}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select id="numPerPage" class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20">20</option>
				<option value="50">50</option>
			</select>
			<span>条，共${action.totalCount}条</span>
		</div>
		
		<div class="pagination" targetType="navTab" totalCount="${action.totalCount }" numPerPage="${action.numPerPage }" pageNumShown="10" currentPage="${action.pageNum }"></div>

	</div>
</div>

</body>
</html>