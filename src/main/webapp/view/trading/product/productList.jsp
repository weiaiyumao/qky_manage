<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp" %>
<% request.setAttribute("PRODUCT_STATUS", DictionaryMap.PRODUCT_STATUS); %>
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
<title>理财管理</title>
<script type="text/javascript">
 function navTabAjaxDone(json){
   	 DWZ.ajaxDone(json);
 	 if (json.statusCode == DWZ.statusCode.ok){
 		 //列表页面重新加载数据
          navTab.reloadFlag("productmgr");    
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
		$("#productList").val(num);
	});
</script>
</head>
<body>

<form id="pagerForm" rel="pagerForm" onsubmit="return navTabSearch(this);"  method="post" action="${ctx}/product/getProductList.htm">
<input type="hidden" name="pageNum" value="1" />
<div class="pageHeader">

	<div class="searchBar">
		<table class="searchContent" style="text-align: right;">
			<tr>
				<td>
					可投金额区间（&gt）：<input type="text" name="mixMoney" id="mixMoney" onkeypress="keyPress()" maxlength="10" value="${mixMoney }" />
				</td>
				<td>
					可投金额区间（&lt）：<input type="text" name="maxMoney" id="maxMoney" onkeypress="keyPress()" maxlength="10" value="${maxMoney }" />
				</td>
				<td>
					产品时间区间（&gt）：<input type="text" name="beginDate"  class="date" dateFmt="yyyy-MM-dd HH:mm:ss"
readonly="true" value="${beginDate }"/>
				</td>
				<td>
					产品时间区间（&lt）：<input type="text" name="endDate"  class="date" dateFmt="yyyy-MM-dd HH:mm:ss"
readonly="true" value="${endDate }"/>
				</td>
			</tr>
			<tr>
				<td>
					理财产品名称：<input type="text" name="title" id="title" value="${title }" />
				</td>
				<td>
					理财产品状态：<select name="flag" style="width: 152px;">
							<option value="">请选择</option>
							<c:forEach var="status" items="<%= DictionaryMap.PRODUCT_STATUS%>">
								<option value="${status.key }" <c:if test="${status.key == flag}">selected</c:if> >${status.value }</option>
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
			<li><mgr:button href="${ctx}/product/forwardAdd.htm" target="navTab" value="新增产品"  cssClass="add"></mgr:button></li>
			<li><mgr:button href="${ctx}/product/forwardEdit.htm?id={id_product}" target="navTab" value="修改产品" warn="请选择需要修改的产品" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/product/forwardInfo.htm?id={id_product}"  target="navTab" value="产品详情" title="标详情" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/product/auditProduct.htm" title="确实要审核这些理财产品吗?" target="selectedTodo" postType="string" value="审核" cssClass="edit"></mgr:button>
			<li><mgr:button href="${ctx}/product/delproduct.htm?id={id_product}"  target="ajaxTodo" value="删除" title="确定要删除吗?" cssClass="delete"></mgr:button></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="185">
		<thead>
			<tr>
			    <th align="center" width="5%" ><input type="checkbox" group="ids" class="checkboxCtrl"  ></th>
				<th width="10%">理财产品标题</th>
			    <th width="15%">累计预融资金额(RMB)</th>
				<th width="15%">推出时间</th>
				<th width="15%">理财产品状态</th>
				<th width="10%">投标上限</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vMap" items="${dataMessage.contentList }">
				<tr target="id_product" rel="${vMap['id']}">
					<td align="center"><input type="checkbox" name="ids" value="${vMap['id']}"/></td>
					<td>${vMap['title']}</td>
					<td><fmt:formatNumber type="number" groupingUsed="false" value="${vMap['money']}" /></td>
					<td>${vMap['sendtime']}</td>
					<td>${PRODUCT_STATUS[vMap['status']]}</td>					
					<td><fmt:formatNumber value="${vMap['ceiling'] }" pattern="00"/></td>	
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select id="productList" class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
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
