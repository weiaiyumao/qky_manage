<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp" %>
<% request.setAttribute("LOAN_LOANSTATUS", DictionaryMap.LOAN_LOANSTATUS); %>
<% request.setAttribute("TERM_TYPE", DictionaryMap.TERM_TYPE); %>
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${ctx }/resources/js/trading/loan/loan.js" type="text/javascript"></script>
<c:if test="${dataMessage.result != 0}">
<script type="text/javascript">
	$(document).ready(function(){
		alertMsg.info("${dataMessage.msg}");
	});
</script>
</c:if>
<script type="text/javascript">
$(document).ready(function(){
	//控制每页显示条数
	var num = ${dataMessage.page.numPerPage};
	$("#loanList").val(num);
	
});
</script>
<title>贷款管理</title>
</head>
<body>
<form id="pagerForm" method="post" action="${ctx}/loan/getloanList.htm">
	<input type="hidden" name="pageNum" value="1" />
	<input id="hiddenNumPerPage" type="hidden" name="numPerPage" value="${dataMessage.page.numPerPage}" />
</form>

<form rel="pagerForm" onsubmit="return navTabSearch(this);" >
<div class="pageHeader">

	<div class="searchBar">
		<table class="searchContent" style="text-align: right;">
			<tr>
				<td>
					贷款金额区间（&gt）：<input type="text" name="minMoney" id="minMoney" onkeypress="keyPress()" maxlength="10" value="${minMoney }" />
				</td>
				<td>
					贷款金额区间（&lt）：<input type="text" name="maxMoney" id="maxMoney" onkeypress="keyPress()" maxlength="10" value="${maxMoney }" />
				</td>
				<td>
					贷款时间区间（&gt）：<input type="text" name="beginDate"  class="date" dateFmt="yyyy-MM-dd HH:mm:ss"
readonly="true" value="${beginDate }"/>
				</td>
				<td>
					贷款时间区间（&lt）：<input type="text" name="endDate"  class="date" dateFmt="yyyy-MM-dd HH:mm:ss"
readonly="true" value="${endDate }"/>
				</td>
			</tr>
			<tr>
				<td>
					贷款标编号：<input type="text" name="id" id="id" value="${id }" />
				</td>
				<td>
					贷款标名称：<input type="text" name="title" id="title" value="${title }" />
				</td>
				<td>
					贷款状态：<select name="loanStatus" style="width: 152px;">
								<option value="">请选择</option>
							<c:forEach var="flag" items="<%= DictionaryMap.LOAN_LOANSTATUS%>">
								<option value="${flag.key }" <c:if test="${flag.key ==loanStatus}">selected</c:if> >${flag.value }</option>
							</c:forEach>
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
			<li><mgr:button href="${ctx}/loan/forwordLoan.htm"   target="navTab" value="发标"  cssClass="add"></mgr:button></li>
			<li><mgr:button href="${ctx}/loan/forwordLoan.htm?1=1&loanId={id_loan}" target="navTab" value="修改标" warn="请选择需要修改的标" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/loan/showLoan.htm?1=1&loanId={id_loan}"  target="navTab" value="标详情" title="标详情" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/loan/updateByFlag.htm" title="确认所选都是需要审核的吗" target="selectedTodo" postType="string" value="审核" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/loan/deleteLoan.htm?1=1&loanbillId={id_loan}"  target="ajaxTodo" value="删除" title="确定要删除吗?" cssClass="delete"></mgr:button></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="185">
		<thead>
			<tr>
			    <th width="5%" align="center"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
			    <th width="10%">贷款编号</th>
			    <th width="10%">贷款标题</th>
				<th width="10%">贷款金额(RMB)</th>
				<th width="10%">年利率(%)</th>
				<th width="10%">贷款期限</th>
				<th width="10%">申请时间</th>
				<th width="10%">发标时间</th>
				<th width="10%">贷款状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vMap" items="${dataMessage.contentList }">
				<tr target="id_loan" rel="${vMap['id']} ">
					<td align="center"><input type="checkbox" name="ids" value="${vMap['id']}" /></td>
					<td>${vMap['id']}</td>
					<td>${vMap['title']}</td>
					<td><fmt:formatNumber value="${vMap['money']}" pattern="#.00"/></td>
					<td>${vMap['apr']}</td>	
					<td>${vMap['terms']}<c:if test="${vMap['termType'] =='0'}">天</c:if><c:if test="${vMap['termType'] =='1'}">个月</c:if></td>
					<td>${vMap['timeCreate']}</td>
					<td>${vMap['sendTime']}</td>								
					<td>${LOAN_LOANSTATUS[vMap['loanStatus']] }</td>				
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select id="loanList" class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
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
