<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp" %>
<% request.setAttribute("PAYBANK_STATUS", DictionaryMap.PAYBANK_STATUS); %>
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
<title>连连支持银行卡管理</title>
<script type="text/javascript">
$(document).ready(function(){
	//控制每页显示条数
	var num = ${dataMessage.page.numPerPage};
	$("#payBankList").val(num);
});
function navTabAjaxDone(json){
   	 DWZ.ajaxDone(json);
 	 if (json.statusCode == DWZ.statusCode.ok){
 		 //列表页面重新加载数据
          navTab.reloadFlag("payBankmgr");    
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

<form id="pagerForm" rel="pagerForm" onsubmit="return navTabSearch(this);"  method="post" action="${ctx}/PayBank/getPayBankList.htm">
<input type="hidden" name="pageNum" value="1" />
<div class="pageHeader">

	<div class="searchBar">
		<table class="searchContent" style="text-align: right;"  >
			<tr>
				<td>
					银行编号：<input type="text" name="bankcode" id="bankcode" value="${jsonObject.bankcode }" />
				</td>
				<td>
					银行名称：<input type="text" name="bankname" id="bankname" value="${jsonObject.bankname }" />
				</td>
				<td>
					银行简码：<input type="text" name="banknick" id="banknick" value="${jsonObject.banknick }" />
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
			<li><mgr:button href="${ctx}/PayBank/forwardAdd.htm"  target="navTab" value="添加" title="添加" cssClass="add"></mgr:button></li>
			<li><mgr:button href="${ctx}/PayBank/forwardEdit.htm?id={id_payBank}"  target="navTab" value="编辑" title="编辑" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/PayBank/forwardInfo.htm?id={id_payBank}"  target="navTab" value="详情" title="详情" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/PayBank/delPayBank.htm?id={id_payBank}"  target="ajaxTodo" value="删除" title="确定要删除吗?" cssClass="delete"></mgr:button></li>
			<li><mgr:button href="${ctx}/PayBank/updatePayBankMoney.htm"  target="ajaxTodo" value="更新限额" title="确定更新限额?" cssClass="edit"></mgr:button></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="158">
		<thead>
			<tr>
				<th width="10%">银行编号</th>
				<th width="10%">银行名称</th>
			    <th width="10%">银行简码</th>
				<th width="10%">每日限额</th>
				<th width="10%">每月限额</th>
				<th width="10%">单笔限额</th>
				<th width="10%">银行状态</th>
				<th width="10%">更新时间</th>
				<th width="10%">备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vMap" items="${dataMessage.contentList }">
				<tr target="id_payBank" rel="${vMap['id']}">
					<td>${vMap['bankcode']}</td>
					<td>${vMap['bankname']}</td>
					<td>${vMap['banknick']}</td>
					<td>${vMap['dayquota']}</td>
					<td>${vMap['monthquota']}</td>
					<td>${vMap['onequota']}</td>
					<td>${PAYBANK_STATUS[vMap['supportstatus']]}</td>
					<td>${vMap['updatetime']}</td>
					<td>${vMap['remark']}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select id="payBankList" class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
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
