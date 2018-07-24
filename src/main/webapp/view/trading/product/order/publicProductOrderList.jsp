<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../../include/include.jsp" %>
<% request.setAttribute("PRODUCTORDER_FLAG", DictionaryMap.PRODUCTORDER_FLAG); %>
<% request.setAttribute("TRADE_STATUS", DictionaryMap.TRADE_STATUS); %>
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>理财投资管理</title>
<script type="text/javascript">
 function navTabAjaxDone(json){
   	 DWZ.ajaxDone(json);
 	 if (json.statusCode == DWZ.statusCode.ok){
 		 //列表页面重新加载数据
          navTab.reloadFlag("publicProductOrdermgr");    
        if ("closeCurrent" == json.callbackType) {
              setTimeout(function(){navTab.closeCurrentTab();}, 100);
        } else if ("forward" == json.callbackType) {
              navTab.reload(json.forwardUrl);
        }
  }
}

 $(document).ready(function(){
		//控制每页显示条数	
		var data = "${data}";
		if(data == true){
			var num = "${dataMessage.page.numPerPage}";
			$("#publicProductOrderList").val(num);
		}
	});
  

</script>

</head>
<body>

<form method="post"  id="pagerForm" onsubmit="return navTabSearch(this);" action="${ctx}/productOrder/getPublicProductList.htm">
<input type="hidden" name="pageNum" value="1" />
<div class="pageHeader">

	<div class="searchBar">
		
		<div class="subBar">
			<ul>
			</ul>
		</div>
	</div>
</div>


<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">    
		</ul>
	</div>

	<table class="table" width="100%" layoutH="131">
		<thead>
			<tr>
			    <th align="center" width="5%"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="14%">订单编号</th>
			    <th width="10%">投资金额(RMB)</th>
			    <th width="10%">剩余可提现本金(RMB)</th>
			    <th width="10%">未匹配金额(RMB)</th>
				<th width="10%">投资时间</th>
				<th width="7%">投资状态</th>
				<th width="14%">银行卡号</th>
				<th width="8%">所属理财产品</th>
				<th width="5%">连连状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vMap" items="${dataMessage.contentList }">
				<tr target="id_productOrder" rel="${vMap['id']}" id = "${vMap['id']}">
					<td align="center"><input type="checkbox" name="ids" value="${vMap['id']}" /></td>
					<td>${vMap['orderId']}</td>
					<td>${vMap['money']}</td>
					<td>${vMap['residualMoney']}</td>
					<td>${vMap['matchMoney']}</td>
					<td>${vMap['timeCreate']}</td>	
					<td>${PRODUCTORDER_FLAG[vMap['status']]}</td>
					<td>${vMap['cardNo']}</td>
					<td>华融道·月利宝</td>
					<td>${TRADE_STATUS[vMap['tradeStatus']]}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select id="publicProductOrderList" class="combox" name="numPerPage"  onchange="navTabPageBreak({numPerPage:this.value})">
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
