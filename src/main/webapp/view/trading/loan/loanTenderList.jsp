<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/include.jsp" %>  
<%@ page import="com.zqfinance.system.util.DictionaryMap" %>
<% request.setAttribute("TENDER_CHINAPNRFLAG", DictionaryMap.TENDER_CHINAPNRFLAG); %>
<% request.setAttribute("TENDER_OPERATETYPE", DictionaryMap.TENDER_OPERATETYPE); %>
<% request.setAttribute("TENDER_FLAG", DictionaryMap.TENDER_FLAG); %>
<% request.setAttribute("TENDER_ISLOAN", DictionaryMap.TENDER_ISLOAN); %>
<script src="${pageContext.request.contextPath}/resources/js/trading/loan/loan.js" type="text/javascript"></script>
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>放款管理</title>
<script type="text/javascript">
function navTabAjaxDone(json){
	DWZ.ajaxDone(json);
	if (json[DWZ.keys.statusCode] == DWZ.statusCode.ok){
		if ("forward" == json.callbackType) {
			navTab.reload("${ctx}"+json.forwardUrl);
		} 
	}
}
</script>
</head>
<body>
<form id="pagerForm" method="post" action="${pageContext.request.contextPath }/action/getActionList.htm">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${dataMessage.page.numPerPage}" />
</form>
<form rel="pagerForm" onsubmit="return navTabSearch(this);" >

</form>
<form rel="pagerForm" onsubmit="return navTabSearch(this);" >
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">      
			<li><mgr:button href="${ctx}/loan/tenderLoan.htm?loanId=${loanId}" target="ajaxTodo" value="全部放款" cssClass="edit" callback="navTabAjaxDone"></mgr:button></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
			    <th width="10%">投标金额</th>
				<th width="10%">投标时间</th>
				<th width="10%">投标客户编号</th>
				<th width="10%">投标类型</th>
				<th width="10%">汇付处理状态</th>
				<th width="10%">投标状态</th>
				<th width="10%">放款状态</th>
				<th width="10%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vMap" items="${dataMessage.contentList }">
				<tr target="id_loan" rel="${vMap['loanID']}">
					<td><fmt:formatNumber value="${vMap['money']}" pattern="#.00"/></td>
					<td>${vMap['timeCreateValue']}</td>
					<td>${vMap['customerId']}</td>
					<td>${TENDER_OPERATETYPE[vMap['operateType']]}</td>
					<td>${TENDER_CHINAPNRFLAG[vMap['chinaPNRFlag']]}</td>			
					<td>${TENDER_FLAG[vMap['flag']]}</td>	
					<td>${TENDER_ISLOAN[vMap['isLoan']]}</td>	
					<td><c:if test="${vMap['isLoan']==0||vMap['isLoan']==2}"><a href="#" style="color: green; text-decoration: none;">放款</a></c:if></td>	
				</tr>
			</c:forEach>
		</tbody>
	</table>

<div class="panelBar" style="margin-bottom: 0px;">
		<div class="pages">
			<span>显示</span>
			<select id="numPerPage" class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20">20</option>
				<option value="50">50</option>
			</select>
			<span>条，共${dataMessage.page.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${dataMessage.page.totalCount }" numPerPage="${dataMessage.page.numPerPage }" pageNumShown="10" currentPage="${dataMessage.page.pageNum }"></div>

	</div>
</div>
</form>
</body>
</html>