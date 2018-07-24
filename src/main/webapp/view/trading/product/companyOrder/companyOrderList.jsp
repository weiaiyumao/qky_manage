<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../../include/include.jsp" %>
<% request.setAttribute("PRODUCTORDER_FLAG", DictionaryMap.PRODUCTORDER_FLAG); %>
<% request.setAttribute("TRADE_STATUS", DictionaryMap.TRADE_STATUS); %>
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公司充值管理</title>
<script type="text/javascript">
function navTabAjaxDone(json){
  	 DWZ.ajaxDone(json);
	 if (json.statusCode == DWZ.statusCode.ok){
		 //列表页面重新加载数据
         navTab.reloadFlag("companyOrdermgr");    
       if ("closeCurrent" == json.callbackType) {
             setTimeout(function(){navTab.closeCurrentTab();}, 100);
       } else if ("forward" == json.callbackType) {
    	   var orderId = json.objList[0];
    	   var orderDate = json.objList[1];
    	   var money = $("#money").val();
    	   var acctName = encodeURI($("#acctName").val());
    	   var idNo = $("#idNo").val();
    	   var productId = $("#productId").val();
    	   var productName = $("#productId")[0].options[$("#productId")[0].selectedIndex].text;
    	   var bankCode = $("#bankCode").val();
    	   productName = encodeURI(productName);
    	   var url = "${ctx}/companyOrder/sendPay.htm?";
    	   url += "orderId="+orderId;
    	   url += "&money="+money;
    	   url += "&acctName="+acctName;
    	   url += "&idNo="+idNo;
    	   url += "&productName="+productName;
    	   url += "&orderDate="+orderDate;
    	   url += "&bankCode="+bankCode;
    	   window.open(url);
    	   setTimeout(function(){navTab.closeCurrentTab();}, 100);
       }
 }
}
 $(document).ready(function(){
		//控制每页显示条数	
		var num = ${dataMessage.page.numPerPage};
		$("#companyOrderList").val(num);
	});
</script>

<style type="text/css">
.btnb{
background:url(${ctx}/resources/images/btn.png) no-repeat; 
display:block; 
width:22px; 
height:20px; 
text-indent:-1000px; 
overflow:hidden; 
float:left; 
margin-left: 72px;
margin-top: 5px;
}
.updateposition{
background-position: -110px -310px;
}
</style>
</head>
<body>

<form method="post"  id="pagerForm" onsubmit="return navTabSearch(this);" action="${ctx}/companyOrder/getCompanyOrderList.htm">
<input type="hidden" name="pageNum" value="1" />
<div class="pageHeader">

	<div class="searchBar">
		<table class="searchContent" style="text-align: right;">
			<tr>
				<td>
					充值金额区间（&gt）：<input type="text" name="minMoney" id="minMoney" onkeypress="keyPress()" maxlength="10" value="${minMoney }" />
				</td>
				<td>
					充值金额区间（&lt）：<input type="text" name="maxMoney" id="maxMoney" onkeypress="keyPress()" maxlength="10" value="${maxMoney }" />
				</td>
				<td>
					订单号：<input type="text" name="orderId" id="orderId" value="${orderId }" />
				</td>
				<td>
					投资状态：<select name="status" style="width: 152px;">
							<option value="">请选择</option>
							<c:forEach var="flg" items="<%= DictionaryMap.PRODUCTORDER_FLAG%>">
								<option value="${flg.key}" <c:if test="${flg.key == status}">selected</c:if> >${flg.value }</option>
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
			<li><mgr:button href="${ctx}/companyOrder/forwordCreateOrderPage.htm" target="navTab" value="充值"  cssClass="edit"></mgr:button></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="185">
		<thead>
			<tr>
			    <th align="center" width="5%"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="14%">订单编号</th>
			    <th width="10%">投资金额(RMB)</th>
				<th width="10%">投资时间</th>
				<th width="10%">投资状态</th>
				<th width="10%">连连处理状态</th>
				<th width="10%">订单对账</th>
			</tr>
		</thead>
		<tbody>
				<c:forEach var="vMap" items="${dataMessage.contentList }">
					<tr target="id_productOrder" rel="${vMap['id']}">
						<td align="center"><input type="checkbox" name="ids" value="${vMap['id']}" /></td>
						<td>${vMap['orderId']}</td>
						<td><fmt:formatNumber type="number" groupingUsed="false" value="${vMap['money']}" maxFractionDigits="2" /></td>
						<td>${vMap['timeCreate']}</td>
						<td>${PRODUCTORDER_FLAG[vMap['status']]}</td>
						<td>${TRADE_STATUS[vMap['tradeStatus']]}</td>
						<td><c:if test="${vMap['tradeStatus'] == null || vMap['tradeStatus'] == ''}"><a title="确定要进行订单对账吗！" target="ajaxTodo" href="${ctx}/companyOrder/queryOrderPayStatus.htm?id=${vMap['id']}" class="btnb updateposition">订单对账</a></c:if></td>
					</tr>
				</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select id="companyOrderList" class="combox" name="numPerPage"  onchange="navTabPageBreak({numPerPage:this.value})">
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
