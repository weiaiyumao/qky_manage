<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp" %>
<% request.setAttribute("ID_TYPE", DictionaryMap.ID_TYPE); %>
<% request.setAttribute("CARD_TYPE", DictionaryMap.CARD_TYPE); %>
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
<title>公司账户信息管理</title>
<script type="text/javascript">
$(document).ready(function(){
	//控制每页显示条数
	var num = ${dataMessage.page.numPerPage};
	$("#companyOutmoneyBankList").val(num);
});
function navTabAjaxDone(json){
   	 DWZ.ajaxDone(json);
 	 if (json.statusCode == DWZ.statusCode.ok){
 		 //列表页面重新加载数据
          navTab.reloadFlag("companyOutmoneyBankmgr");    
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

<form id="pagerForm" rel="pagerForm" onsubmit="return navTabSearch(this);"  method="post" action="${ctx}/company/getCompanyOutmoneyBankList.htm">
<input type="hidden" name="pageNum" value="1" />
<div class="pageHeader">

	<div class="searchBar">
		<table class="searchContent" style="text-align: right;"  >
			<tr>
				<td>
					时间区间（&gt）：<input type="text" name="beginDate"  class="date" dateFmt="yyyy-MM-dd HH:mm:ss"
readonly="true" value="${beginDate }"/>
				</td>
				<td>
					时间区间（&lt）：<input type="text" name="endDate"  class="date" dateFmt="yyyy-MM-dd HH:mm:ss"
readonly="true" value="${endDate }"/>
				</td>
				<td>
					证件卡号：<input type="text" name="idNo" id="idNo" maxlength="20" value="${idNo }" />
				</td>
				<td>
					银行名称：<input type="text" name="bankName" id="bankName" value="${bankName}">
				</td>
				<td>
					银行号：<input type="text" name="bankCode" id="bankCode" maxlength="20" value="${bankCode }" />
				</td>
			</tr>
			<tr>
				<td>
					银行卡号：<input type="text" name="cardNo" id="cardNo" value="${cardNo}">
				</td>
				<td>
					证件类型：<select name="idType" style="width: 152px;">
							<option value="">请选择</option>
							<c:forEach var="idt" items="${ID_TYPE}">
								<option value="${idt.key }" <c:if test="${idt.key == idType}">selected</c:if> >${idt.value }</option>
							</c:forEach>
						</select>
				</td>
				<td>
					卡类型：<select name="cardType" style="width: 152px;">
							<option value="">请选择</option>
							<c:forEach var="card" items="${CARD_TYPE}">
								<option value="${card.key}" <c:if test="${card.key == cardType}">selected</c:if> >${card.value }</option>
							</c:forEach>
						</select>
				</td>
				<td>&nbsp;</td>
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
			<li><mgr:button href="${ctx}/company/forwardAdd.htm" target="navTab" value="新增" cssClass="add"></mgr:button></li>
			<li><mgr:button href="${ctx}/company/forwardInfo.htm?id={id_customerLC}"  target="navTab" value="详情" title="详情" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/company/forwardEdit.htm?id={id_customerLC}" target="navTab" value="修改" warn="请选择需要修改的银行卡信息" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/company/delCompanyOutmoneyBank.htm?id={id_customerLC}"  target="ajaxTodo" value="删除" title="确定要删除吗?" cssClass="delete"></mgr:button></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="185">
		<thead>
			<tr>
			    <th align="center" width="3%" ><input type="checkbox" group="ids" class="checkboxCtrl"  ></th>
				<th width="5%">用户名称</th>
				<th width="10%">证件号码</th>
			    <th width="5%">证件类型</th>
				<th width="10%">银行卡号</th>
				<th width="5%">银行卡类型</th>
				<th width="5%">银行号</th>
				<th width="10%">所属银行名称</th>
				<th width="10%">开户行编号</th>
				<th width="10%">开户支行名称</th>
				<th width="10%">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vMap" items="${dataMessage.contentList }">
				<tr target="id_customerLC" rel="${vMap['id']}">
					<td align="center"><input type="checkbox" name="ids" value="${vMap['id']}"/></td>
					<td>${vMap['acctName']}</td>
					<td>${vMap['idNo']}</td>
					<td>${ID_TYPE[vMap['idType']]}</td>
					<td>${vMap['cardNo']}</td>
					<td>${CARD_TYPE[vMap['cardType']]}</td>
					<td>${vMap['bankCode']}</td>
					<td>${vMap['bankName']}</td>
					<td>${vMap['brabankCode']}</td>
					<td>${vMap['brabankName']}</td>
					<td>${vMap['timeCreate']}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select id="companyOutmoneyBankList" class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
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
