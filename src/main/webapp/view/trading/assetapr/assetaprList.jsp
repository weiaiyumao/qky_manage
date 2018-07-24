<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp" %>
<% request.setAttribute("LAWTYPE", DictionaryMap.LAWTYPE); %>
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
<title>账户资产收益率规则管理</title>
<script type="text/javascript">
$(document).ready(function(){
	//控制每页显示条数
	var num = ${dataMessage.page.numPerPage};
	$("#assetaprList").val(num);
});
function navTabAjaxDone(json){
   	 DWZ.ajaxDone(json);
 	 if (json.statusCode == DWZ.statusCode.ok){
 		 //列表页面重新加载数据
          navTab.reloadFlag("assetaprmgr");    
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

<form id="pagerForm" rel="pagerForm" onsubmit="return navTabSearch(this);"  method="post" action="${ctx}/ruleAssetapr/getRuleAssetaprList.htm.htm">
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
					利率：<input type="text" name="apr" id="apr" value="${apr}" onkeypress="keyPress();">
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
			<li><mgr:button href="${ctx}/ruleAssetapr/forwardAdd.htm" target="navTab" value="新增"  cssClass="add"></mgr:button></li>
			<li><mgr:button href="${ctx}/ruleAssetapr/forwardEdit.htm?id={id_ruleAssetapr}" target="navTab" value="修改" warn="请选择需要修改的资产收益率规则" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/ruleAssetapr/forwardInfo.htm?id={id_ruleAssetapr}"  target="navTab" value="详情" title="资产收益率规则详情" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/ruleAssetapr/forwardEditCustomerTitle.htm?id={id_ruleAssetapr}" target="navTab" value="修改称谓" warn="请选择需要修改的资产收益率规则" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/ruleAssetapr/delassetapr.htm" title="确实要删除这些附加利率记录吗?" target="selectedTodo" postType="string" value="删除" cssClass="delete"></mgr:button>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="158">
		<thead>
			<tr>
			    <th align="center" width="5%" ><input type="checkbox" group="ids" class="checkboxCtrl"  ></th>
				<th width="5%">附加利率编号</th>
				<th width="15%">最小金额(RMB)</th>
			    <th width="15%">最大金额(RMB)</th>
			    <th width="10%">利率(%)</th>
			    <th width="15%">称谓</th>
				<th width="15%">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vMap" items="${dataMessage.contentList }">
				<tr target="id_ruleAssetapr" rel="${vMap['id']}">
					<td align="center"><input type="checkbox" name="ids" value="${vMap['id']}"/></td>
					<td>${vMap['id']}</td>
					<td>${vMap['minmoney']}</td>
					<td>${vMap['maxmoney']}</td>
					<td>${vMap['apr']}</td>
					<td>${vMap['customertitle'] }</td>
					<td>${vMap['timeCreate']}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select id="assetaprList" class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
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
