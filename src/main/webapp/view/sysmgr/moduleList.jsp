<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="mgr" uri="http://rrdai.com/mgr-tags"%> 
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>功能模块管理</title>
<script type="text/javascript">
function navTabAjaxDone(json){
  	 DWZ.ajaxDone(json);
	 if (json.statusCode == DWZ.statusCode.ok){
		 //列表页面重新加载数据
         navTab.reloadFlag("modulemgr");    
       if ("closeCurrent" == json.callbackType) {
             setTimeout(function(){navTab.closeCurrentTab();}, 100);
       } else if ("forward" == json.callbackType) {
             navTab.reload(json.forwardUrl);
       }
 }
}
</script>
</head>
<body>
<form id="pagerForm" method="post" action="${pageContext.request.contextPath }/module/getModuleList.htm">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${module.numPerPage}" />
</form>

<form rel="pagerForm" onsubmit="return navTabSearch(this);" >
<div class="pageHeader">

	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					模块名称：<input type="text" name="moduleName" value="${module.moduleName }" />
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
			<li><mgr:button href="${pageContext.request.contextPath }/module/forwordMerge.htm" target="navTab" value="新增模块" cssClass="add"></mgr:button></li>
			<li><mgr:button href="${pageContext.request.contextPath }/module/forwordMerge.htm?1=1&moduleId={id_module}" target="navTab" value="修改模块" warn="请选择需要修改的模块" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${pageContext.request.contextPath }/module/delModule.htm?1=1&moduleId={id_module}" target="ajaxTodo" value="删除模块" title="确定要删除吗?" cssClass="delete"></mgr:button></li>
		</ul>
	</div>
	<table class="table" width="50%" layoutH="156">
		<thead>
			<tr>
				<th width="25%">ID</th>
				<th width="25%">模块名称</th>
				<th width="25%">父级模块</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="module" items="${moduleList }">
				<tr target="id_module" rel="${module.id} ">
					<td>${module.id}</td>
					<td>${module.moduleName}</td>
					<td>${module.parentName }</td>
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
			</select>
			<span>条，共${module.totalCount}条</span>
		</div>
		
		<div class="pagination" targetType="navTab" totalCount="${module.totalCount }" numPerPage="${module.numPerPage }" pageNumShown="10" currentPage="${module.pageNum }"></div>

	</div>
</div>

</body>
</html>