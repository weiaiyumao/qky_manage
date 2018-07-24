<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp" %>
<% request.setAttribute("COMPANYOUTMONEY_FLAG", DictionaryMap.COMPANYOUTMONEY_FLAG); %>
<% request.setAttribute("OUTMONEY_FEEPROVIDER", DictionaryMap.OUTMONEY_FEEPROVIDER);%>
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 引用ajaxfileupload.js -->  
<script src="${ctx}/resources/js/ajaxfileupload.js"></script> 
<c:if test="${dataMessage.result != 0}">
<script type="text/javascript">
	$(document).ready(function(){
		alertMsg.info("${dataMessage.msg}");
	});
</script>
</c:if>
<title>公司提现列表</title>
<script type="text/javascript">  
$(document).ready(function(){
	//控制每页显示条数
	var num = ${dataMessage.page.numPerPage};
	$("#companyOutMoneyList").val(num);
});
</script>  
<script type="text/javascript">
 function navTabAjaxDone(json){
   	 DWZ.ajaxDone(json);
 	 if (json.statusCode == DWZ.statusCode.ok){
 		 //列表页面重新加载数据
          navTab.reloadFlag("companyOutmoneymgr");    
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

<form id="pagerForm" rel="pagerForm" onsubmit="return navTabSearch(this);"  method="post" action="${ctx}/companyOutMoney/getCompanyOutMoneyList.htm">
<input type="hidden" name="pageNum" value="1" />
<div class="pageHeader">

	<div class="searchBar">
		<table class="searchContent" style="text-align: right;">
			<tr>
			 	<td>
					提现金额区间（&gt）：<input type="text" name="mixMoney" id="mixMoney" onkeypress="keyPress()" maxlength="10" value="${minMoney }" />
				</td>
				<td>
					提现金额区间（&lt）：<input type="text" name="maxMoney" id="maxMoney" onkeypress="keyPress()" maxlength="10" value="${maxMoney }" />
				</td>
				<td>
					提现交易时间区间（&gt）：<input type="text" name="beginDate"  class="date" dateFmt="yyyy-MM-dd" readonly="true" value="${beginDate }"/>
				</td>
				<td>
					提现交易时间区间（&lt）：<input type="text" name="endDate"  class="date" dateFmt="yyyy-MM-dd" readonly="true" value="${endDate }"/>
				</td>
			</tr>
			<tr>
				<td>
				           订单号 ：<input type="text" name="orderId" id="orderId"  value="${orderId}" />
				</td>
				<td>
					提现状态：<select name="flag" style="width: 152px;">
							<option value="">请选择</option>
							<c:forEach var="_status" items="<%= DictionaryMap.COMPANYOUTMONEY_FLAG%>">
								<option value="${_status.key }" <c:if test="${_status.key == flag}">selected</c:if> >${_status.value }</option>
							</c:forEach>
						</select>
				</td>
			</tr>
		</table>
		<div class="divider"></div>
<!-- 		<input id="file" name="file" type="file" onchange="upload()">(仅支持.txt,.xls格式) -->
		<div class="divider"></div>
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
			<li><mgr:button href="${ctx}/companyOutMoney/addCompanyOutMoneyPage.htm" target="navTab" value="添加" title="添加提现记录" cssClass="add"></mgr:button></li>
			<li><mgr:button href="${ctx}/companyOutMoney/aduitCompanyOutMoneyPage.htm?id={id_companyOutMoney}" title="审核提现" target="navTab" warn="请选择需要审核的选项" value="审核提现" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/companyOutMoney/payCompanyOutMoney.htm" title="确实要放款吗?" target="selectedTodo" postType="string" value="自动放款" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/companyOutMoney/autoReconciliationCompanyOutMoney.htm" title="确实要对账?" target="selectedTodo" postType="string" value="自动对账" cssClass="edit"></mgr:button></li>
<%-- 			<li><mgr:button href="${ctx}/outMoney/dwlExport.htm"  target="dwzExport" targetType="navTab" pagechecked="nochecked" cssClass="icon" value="导出 Excel"></mgr:button></li> --%>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="228">
		<thead>
			<tr>
			    <th align="center" width="5%" ><input type="checkbox" group="ids" class="checkboxCtrl"  ></th>
				<th width="10%">订单号</th>
				<th width="12%">取现金额(RMB)</th>
				<th width="5%">提现状态</th>
				<th width="8%">手续费提供</th>
				<th width="5%">手续费</th>
				<th width="10%">订单生成时间</th>
				<th width="10%">备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vMap" items="${dataMessage.contentList }">
				<tr target="id_companyOutMoney" rel="${vMap['id']}">
					<td align="center"><input type="checkbox" name="ids" value="${vMap['id']}"/></td>
					<td>${vMap['orderId']}</td>
					<td>${vMap['money']}</td>
					<td>${COMPANYOUTMONEY_FLAG[vMap['status']]}</td>
					<td>${OUTMONEY_FEEPROVIDER[vMap['feeProvider']]}</td>
					<td>${vMap['fee'] }</td>					
					<td>${vMap['timeCreate'] }</td>
					<td>${vMap['remark'] }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select id="companyOutMoneyList" class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
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
