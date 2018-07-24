<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/include.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择借款用户</title>
<script src="${ctx }/resources/js/trading/loan/loan.js" type="text/javascript"></script>
</head>
<body>

<form id="pagerForm" method="post" action="${ctx}/findCustomerList.htm">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="name" value="${jsonobject.name}" />
	<input type="hidden" name="mobile" value="${jsonobject.mobile}" />
	<input type="hidden" name="idcard" value="${jsonobject.idcard}" />
	<input id="hiddenNumPerPage2" type="hidden" name="numPerPage" value="${dataMessage.page.numPerPage}" />
</form>

<div class="pageHeader" >
	<form  onsubmit="return dwzSearch(this, 'dialog');" action="${ctx}/findCustomerList.htm">
	<input type="hidden" name="pageNum" value="1" />	
	<input type="hidden" name="numPerPage" value="${dataMessage.page.numPerPage}" />
	<div class="searchBar">
		<table class="searchContent"  style="text-align: right;">
			<tr>
				<td>
					用户编号：<input type="text" name="id" value="${jsonobject.id}" />
				</td>
				<td>
					用户名称：<input type="text" name="name" value="${jsonobject.name}" />
				</td>
				<td>
					用户手机：<input type="text" name="mobile" value="${jsonobject.mobile}"/>
				</td>
			</tr>
			<tr>
				<td>
					身份证：<input type="text" name="idcard" value="${jsonobject.idcard}"/>
				</td>
				
				<td>
					
				</td>
				<td>
					
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">

	<table class="table" layoutH="154" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th>用户编号</th>
				<th>用户名称</th>
				<th>用户电话</th>
				<th>身份证号码</th>
				<th width="80">选择带回</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vMap" items="${dataMessage.contentList }">
				<tr>
					<td>${vMap['id']}</td>
					<td>${vMap['name']}</td>
					<td>${vMap['mobile']}</td>
					<td>${vMap['idcard']}</td>
					<td>
					<form method="post" action="${ctx}/saveCustomerInSession.htm" onsubmit="return validateCallback(this, dialogAjaxDone);">
						<input type="hidden" value="${vMap['id']}" name="id">
						<div class="buttonContent" style="margin-top: 5px;"><button type="submit">选择</button></div>
					</form>
					</td>			
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>每页</span>

			<select class="combox" id="numPerPage2" name="numPerPage" onchange="chageCustomerNumPerPage(this.value)">
				<option value="20">20</option>
			</select>
			<span>条，共${dataMessage.page.totalCount}条</span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${dataMessage.page.totalCount }" numPerPage="${dataMessage.page.numPerPage }" pageNumShown="10" currentPage="${dataMessage.page.pageNum }"></div>
	</div>
</div>
</body>
</html>