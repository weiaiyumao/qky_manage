<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp" %>
<% request.setAttribute("OUTMONEY_FLAG", DictionaryMap.OUTMONEY_FLAG); %>
<% request.setAttribute("OUTMONEY_FEEPROVIDER", DictionaryMap.OUTMONEY_FEEPROVIDER);%>
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 引用ajaxfileupload.js -->  
<script src="${ctx}/resources/js/ajaxfileupload.js"></script> 

<title>提现管理</title>

<script type="text/javascript">
 function navTabAjaxDone(json){
   	 DWZ.ajaxDone(json);
 	 if (json.statusCode == DWZ.statusCode.ok){
 		 //列表页面重新加载数据
          navTab.reloadFlag("publicOutmoneymgr");    
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
			$("#publicOutMoneyList").val(num);
		}
	});
	
	
</script>

</head>
<body>

<form id="pagerForm" rel="pagerForm" onsubmit="return navTabSearch(this);"  method="post" action="${ctx}/outMoney/getPublicOutMoneyList.htm">
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
			    <th align="center" width="5%" ><input type="checkbox" group="ids" class="checkboxCtrl"  ></th>
				<th width="10%">订单号</th>
				<th width="12%">取现金额(RMB)</th>
				<th width="8%">所属银行</th>
				<th width="12%">银行卡号</th>
				<th width="5%">提现状态</th>
				<th width="10%">订单生成时间</th>
				<th width="10%">交易成功时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vMap" items="${dataMessage.contentList }">
				<tr target="id_outMoney" rel="${vMap['id']}">
					<td align="center"><input type="checkbox" name="ids" value="${vMap['id']}"/></td>
					<td>${vMap['orderNo']}</td>
					<td>${vMap['money']}</td>
					<td>${bankMap[vMap['bank']]}</td>
					<td>${vMap['cardid']}</td> 
					<td>${OUTMONEY_FLAG[vMap['status']]}</td>					
					<td>${vMap['timeCreate'] }</td>
					<td>${vMap['timeTrading']}</td>		
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select id="publicOutMoneyList" class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
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
