<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ include file="../../../include/include.jsp" %>
<%@ page import="com.zqfinance.system.util.DictionaryMap" %>
<% request.setAttribute("CREDIT_STATUS", DictionaryMap.CREDIT_STATUS); %>
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>可匹配贷款标管理</title>
<script type="text/javascript">

</script>
<script src="${ctx }/resources/js/util/checkInput.js" type="text/javascript"></script>
</head>
<body>
<form id="pagerForm" method="post" action="${ctx}/loan/getloanViewList.htm">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${dataMessage.page.numPerPage}" />
</form>
<div class="pageHeader">
	<form  onsubmit="return dwzSearch(this, 'dialog');" action="${ctx}/loan/getloanViewList.htm">
		<input type="hidden" name="pageNum" value="1" />	
		<input type="hidden" name="numPerPage" value="${dataMessage.page.numPerPage}" />	
		<input type="hidden" name="id" value ="${investProductOrderid}" />
	<div class="searchBar">
		<table class="searchContent" style="text-align: right;">
			<tr>
				<td>
					金额区间（&gt）：<input type="text" name="minMoney" id="minMoney" onkeypress="keyPress()" maxlength="10" class="digits" value="${minMoney }" />
				</td>
				<td>
					金额区间（&lt）：<input type="text" name="maxMoney" id="maxMoney" onkeypress="keyPress()" maxlength="10" class="digits" value="${maxMoney }" />
				</td>
				<td>
					贷款期限：<input type="text" name="month" id="month" onkeypress="keyPress()" maxlength="10" value="${month }" />
				</td>
			</tr>	
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" multlookup="orgId" warn="请选择要匹配的贷款标">确认</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="138" style="text-align: center;">
		<thead>
			<tr>
				<th align="center" width="5%"><input type="checkbox" group="orgId" class="checkboxCtrl"></th>
				<th width="10%">贷款编号</th>
			    <th width="10%">贷款标题</th>
				<th width="10%">贷款金额(RMB)</th>
				<th width="10%">年利率(%)</th>
				<th width="10%">贷款期限</th>
				<th width="10%">剩余可投金额</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vMap" items="${dataMessage.contentList}" varStatus="number">
				<tr target="id_creditId" rel="${vMap['id']}">
				 	<td align="center"><input type="checkbox" name="orgId" value="{id${number.count}:'${vMap['id']}',surplusMoney${number.count}:'<fmt:formatNumber value="${vMap['symoney']}" pattern="#.00"/>'}"/></td>
					<td>${vMap['id']}</td>
					<td>${vMap['title']}</td>
					<td><fmt:formatNumber value="${vMap['money']}" pattern="#.00"/></td>
					<td>${vMap['apr']}</td>	
					<td>${vMap['terms']}${TERM_TYPE[vMap['termType']]}个月</td>
					<td>
						<fmt:formatNumber groupingUsed="false" value="${vMap['symoney']}" type="number" />
					</td>
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
			<span>条，共${dataMessage.page.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${dataMessage.page.totalCount }" numPerPage="${dataMessage.page.numPerPage }" pageNumShown="10" currentPage="${dataMessage.page.pageNum }"></div>
	</div>
</div>

</body>
</html>
