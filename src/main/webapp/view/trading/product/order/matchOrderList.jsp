<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../../include/include.jsp" %>
<% request.setAttribute("PRODUCTORDER_FLAG", DictionaryMap.PRODUCTORDER_FLAG); %>
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:if test="${dataMessage.result != 0}">
<script type="text/javascript">
	$(document).ready(function(){
		alertMsg.info("${dataMessage.msg}");
	});
</script>
</c:if>
<title>理财投资管理</title>
<script type="text/javascript">
 function navTabAjaxDone(json){
   	 DWZ.ajaxDone(json);
 	 if (json.statusCode == DWZ.statusCode.ok){
 		 //列表页面重新加载数据
          navTab.reloadFlag("canMatchOrdermgr");    
        if ("closeCurrent" == json.callbackType) {
              setTimeout(function(){navTab.closeCurrentTab();}, 100);
        } else if ("forward" == json.callbackType) {
              navTab.reload(json.forwardUrl);
        }
  }
}

 $(document).ready(function(){
		//控制每页显示条数	
		var num = ${dataMessage.page.numPerPage};
		$("#canMatchOrderList").val(num);
	});
</script>
</head>
<body>

<form method="post"  id="pagerForm" onsubmit="return navTabSearch(this);" action="${ctx}/productOrder/getCanMatchOrderList.htm">
<input type="hidden" name="pageNum" value="1" />
<div class="pageHeader">

	<div class="searchBar">
		<table class="searchContent" style="text-align: right;">
			<tr>
				<td>
					投资金额区间（&gt）：<input type="text" name="minMoney" id="minMoney" onkeypress="keyPress()" maxlength="10" value="${minMoney }" />
				</td>
				<td>
					投资金额区间（&lt）：<input type="text" name="maxMoney" id="maxMoney" onkeypress="keyPress()" maxlength="10" value="${maxMoney }" />
				</td>
				<td>
					投资时间区间（&gt）：<input type="text" name="beginDate"  class="date" dateFmt="yyyy-MM-dd"
readonly="true" value="${beginDate }"/>
				</td>
				<td>
					投资时间区间（&lt）：<input type="text" name="endDate"  class="date" dateFmt="yyyy-MM-dd"
readonly="true" value="${endDate }"/>
				</td>
			</tr>
			<tr>
				<td>
					订单号：<input type="text" name="orderId" id="orderId" value="${orderId }" />
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


<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">      
			<li><mgr:button href="${ctx}/productOrder/forwardInfo.htm?id={id_productOrder}" target="navTab" value="详情"  cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/productOrder/forwardMarryCreidt.htm?id={id_productOrder}" target="navTab" value="匹配"  cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/productOrder/autoMatchLoan.htm" title="确认所选都是需要自动匹配标的吗" target="selectedTodo" postType="string" value="自动匹配" cssClass="edit"></mgr:button></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="185">
		<thead>
			<tr>
			    <th align="center" width="5%"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="10%">订单编号</th>
			    <th width="10%">投资金额(RMB)</th>
			    <th width="10%">剩余金额(RMB)</th>
			    <th width="10%">未匹配金额(RMB)</th>
				<th width="10%">投资时间</th>
				<th width="10%">所属理财产品</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vMap" items="${dataMessage.contentList }">
				<tr target="id_productOrder" rel="${vMap['id']} ">
					<td align="center"><input type="checkbox" name="ids" value="${vMap['id']}" /></td>
					<td>${vMap['orderId']}</td>
					<td>${vMap['money']}</td>
					<td>${vMap['residualMoney']}</td>
					<td>${vMap['matchMoney']}</td>
					<td>${vMap['timeCreate']}</td>	
					<td>华融道·月利宝</td>	
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select id="canMatchOrderList" class="combox" name="numPerPage"  onchange="navTabPageBreak({numPerPage:this.value})">
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
