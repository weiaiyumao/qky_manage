<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp" %>
<% request.setAttribute("CAL_TYPE", DictionaryMap.CAL_TYPE); %>
<% request.setAttribute("CASHGIFT_STATUS", DictionaryMap.CASHGIFT_STATUS); %>
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
<title>客户支出红包管理</title>
<script type="text/javascript">
$(document).ready(function(){
	//控制每页显示条数
	var num = ${dataMessage.page.numPerPage};
	$("#cashgiftOutList").val(num);
});
function navTabAjaxDone(json){
   	 DWZ.ajaxDone(json);
 	 if (json.statusCode == DWZ.statusCode.ok){
 		 //列表页面重新加载数据
          navTab.reloadFlag("cashgiftOutmgr");    
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

<form id="pagerForm" rel="pagerForm" onsubmit="return navTabSearch(this);"  method="post" action="${ctx}/cashgiftOut/getCashgiftOut.htm">
<input type="hidden" name="pageNum" value="1" />
<div class="pageHeader">

	<div class="searchBar">
		<table class="searchContent" style="text-align: right;"  >
			<tr>
				<td>
					金额区间（&gt）：<input type="text" name="mixMoney" id="mixMoney" onkeypress="keyPress()" maxlength="10" value="${mixMoney }" />
				</td>
				<td>
					金额区间（&lt）：<input type="text" name="maxMoney" id="maxMoney" onkeypress="keyPress()" maxlength="10" value="${maxMoney }" />
				</td>
				<td>
					创建时间区间（&gt）：<input type="text" name="beginDate"  class="date" dateFmt="yyyy-MM-dd HH:mm:ss"
readonly="true" value="${beginDate }"/>
				</td>
				<td>
					创建时间区间（&lt）：<input type="text" name="endDate"  class="date" dateFmt="yyyy-MM-dd HH:mm:ss"
readonly="true" value="${endDate }"/>
				</td>
			</tr>
			<tr>
				<td>
					订单号：<input type="text" name="orderId" id="orderId" value="${orderId}">
				</td>
				<td>
					使用状态：<select name="flag" style="width: 152px;">
							<option value="">请选择</option>
							<c:forEach var="flay" items="<%= DictionaryMap.CASHGIFT_STATUS%>">
								<option value="${flay.key }" <c:if test="${flay.key == flag}">selected</c:if> >${flay.value }</option>
							</c:forEach>
						</select>
				</td>
				<td>
					计算类型：<select name="type" style="width: 152px;">
							<option value="">请选择</option>
							<c:forEach var="ctype" items="<%= DictionaryMap.CAL_TYPE%>">
								<option value="${ctype.key }" <c:if test="${ctype.key == type}">selected</c:if> >${ctype.value }</option>
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


<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">      
			<li>
				<span>
					<c:if test="${dataMessage_money.data['sumMoney'] != null}">当前红包金额：<font color="red">${dataMessage_money.data['sumMoney']}</font>元&nbsp;&nbsp;</c:if>
				</span>
			</li> 
		</ul>
	</div>
	<table class="table" width="100%" layoutH="185">
		<thead>
			<tr>
			    <th align="center" width="5%" ><input type="checkbox" group="ids" class="checkboxCtrl"  ></th>
				<th width="10%">编号</th>
				<th width="20%">金额</th>
			    <th width="20%">订单号</th>
				<th width="20%">创建时间</th>
				<th width="15%">使用状态</th>
				<th width="15%">计算类型</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vMap" items="${dataMessage.contentList }">
				<tr target="id_cashgiftIn" rel="${vMap['id']}">
					<td align="center"><input type="checkbox" name="ids" value="${vMap['id']}"/></td>
					<td>${vMap['id']}</td>
					<td>${vMap['money']}</td>
					<td>${vMap['orderId']}</td>
					<td>${vMap['timeCreate']}</td>
					<td>${CASHGIFT_STATUS[vMap['status']]}</td>
					<td>${CAL_TYPE[vMap['calType']]}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select id="cashgiftOutList" class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
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
