<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/include.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择借款用户</title>
<script src="${ctx }/resources/js/trading/loan/loan.js" type="text/javascript"></script>

</head>
<body>

<form id="pagerForm" method="post" action="${ctx}/loan/findCustomerList.htm">
	<input type="hidden" name="pageNum" value="${dataMessage.page.numPerPage }" />
</form>

<div class="pageHeader" >
	<form  onsubmit="return dwzSearch(this, 'dialog');" action="${ctx}/loan/findCustomerList.htm">
	<input type="hidden" name="p2bPageNum" value="${dataMessage.page.totalCount}" />	
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					用户名称：<input type="text" name="name" value="${jsonobject.name}" />
				</td>
				<td>
					用户手机：<input type="text" name="mobile" value="${jsonobject.mobile}"/>
				</td>
				<td>
					用户邮箱：<input type="text" name="email" value="${jsonobject.email}"/>
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

	<table class="table" layoutH="118" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th>用户编号</th>
				<th>用户名称</th>
				<th>用户电话</th>
				<th>邮箱地址</th>
				<th width="80">查找带回</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vMap" items="${dataMessage.contentList }">
				<tr>
					<td>${vMap['id']}</td>
					<td>${vMap['name']}</td>
					<td>${vMap['mobile']}</td>
					<td>${vMap['email']}</td>	
					<td>
						<a class="btnSelect" href="javascript:$.bringBack({customerId:${vMap['id']}, username:'${vMap['name']}'})" title="查找带回">选择</a>
					</td>				
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>每页</span>

			<select class="combox" id="customerList" name="numPerPage" onchange="chageCustomerNumPerPage(this.value)">
				<option value="20" selected="selected">20</option>
				<option value="50">50</option>
			</select>
			<span>条，共${dataMessage.page.totalCount}条</span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${dataMessage.page.totalCount }" numPerPage="${dataMessage.page.numPerPage }" pageNumShown="10" currentPage="${dataMessage.page.pageNum }"></div>
	</div>
</div>
</body>
</html>