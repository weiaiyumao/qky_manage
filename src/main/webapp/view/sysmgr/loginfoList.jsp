<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/include.jsp" %>
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆日志管理</title>
<script type="text/javascript">
function navTabAjaxDone(json){
  	 DWZ.ajaxDone(json);
	 if (json.statusCode == DWZ.statusCode.ok){
		 //列表页面重新加载数据
         navTab.reloadFlag("loginfomgr");    
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
<form id="pagerForm" method="post" action="${ctx}/loginfo/getLoginfoList.htm">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${loginfo.numPerPage}" />
</form>

<form id="pagerForm" onsubmit="return navTabSearch(this);" >
<div class="pageHeader">

	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					登陆用户名称：<input type="text" name="userName" value="${loginfo.userName}" />
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
		</ul>
	</div>
	<table class="table" width="100%" layoutH="156">
		<thead>
			<tr>
				<th width="10%">用户编号</th>
				<th width="20%">登陆用户</th>
				<th width="15%">登陆IP</th>
				<th width="15%">查看模块</th>
				<th width="20%">登陆状态</th>
				<th width="20%">登陆时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="loginfo" items="${loginfoList }">
				<tr target="id_loginfo" rel="${loginfo.id} ">
					<td>${loginfo.id}</td>
					<td>${loginfo.userName}</td>
					<td>${loginfo.requestIp}</td>
					<td>${loginfo.moduleName}</td>
					<td>${loginfo.loginStatus=='0'?'登陆成功':'登陆失败'}</td>
					<td><fmt:formatDate value="${loginfo.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
			<span>条，共${loginfo.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${loginfo.totalCount }" numPerPage="${loginfo.numPerPage }" pageNumShown="10" currentPage="${loginfo.pageNum }"></div>

	</div>
</div>

</body>
</html>