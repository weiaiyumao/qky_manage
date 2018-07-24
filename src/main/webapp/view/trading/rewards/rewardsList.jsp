<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp" %>
<% request.setAttribute("REWARDS_TYPE", DictionaryMap.REWARDS_TYPE); %>
<% request.setAttribute("REWARDS_STATUS", DictionaryMap.REWARDS_STATUS); %>
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
<title>活动奖励收益管理</title>
<script type="text/javascript">
$(document).ready(function(){
	//控制每页显示条数
	var num = ${dataMessage.page.numPerPage};
	$("#rewardsList").val(num);
});
function navTabAjaxDone(json){
   	 DWZ.ajaxDone(json);
 	 if (json.statusCode == DWZ.statusCode.ok){
 		 //列表页面重新加载数据
          navTab.reloadFlag("rewardsmgr");    
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

<form id="pagerForm" rel="pagerForm" onsubmit="return navTabSearch(this);"  method="post" action="${ctx}/rewards/getRewardsList.htm">
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
					交易时间区间（&gt）：<input type="text" name="beginDate"  class="date" dateFmt="yyyy-MM-dd HH:mm:ss"
readonly="true" value="${beginDate }"/>
				</td>
				<td>
					交易时间区间（&lt）：<input type="text" name="endDate"  class="date" dateFmt="yyyy-MM-dd HH:mm:ss"
readonly="true" value="${endDate }"/>
				</td>
			</tr>
			<tr>
				<td>
					订单号：<input type="text" name="orderid" id="orderid" value="${orderid}">
				</td>
				<td>
					收益类型：<select name="type" style="width: 152px;">
							<option value="">请选择</option>
							<c:forEach var="fag" items="<%= DictionaryMap.REWARDS_TYPE%>">
								<option value="${fag.key }" <c:if test="${fag.key == type}">selected</c:if> >${fag.value }</option>
							</c:forEach>
						</select>
				</td>
				<td>
					处理状态：<select name="flag" style="width: 152px;">
							<option value="">请选择</option>
							<c:forEach var="status" items="<%= DictionaryMap.REWARDS_STATUS%>">
								<option value="${status.key}" <c:if test="${status.key == flag}">selected</c:if> >${status.value }</option>
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
			<li><mgr:button href="${ctx}/rewards/forwardInfo.htm?id={id_rewards}"  target="navTab" value="详情" title="详情" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/rewards/dwlExport.htm"  target="dwzExport" targetType="navTab" pagechecked="checked" cssClass="icon" value="导出 Excel"></mgr:button></li>
			<li><mgr:button href="${ctx}/rewards/delRewards.htm" title="确实要删除这些合同记录吗?" target="selectedTodo" postType="string" value="删除" cssClass="delete"></mgr:button>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="185">
		<thead>
			<tr>
			    <th align="center" width="5%" ><input type="checkbox" group="ids" class="checkboxCtrl"  ></th>
				<th width="10%">订单号</th>
				<th width="10%">客户编号</th>
			    <th width="10%">收益类型</th>
				<th width="10%">金额(RMB)</th>
				<th width="10%">交易时间</th>
				<th width="10%">处理状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vMap" items="${dataMessage.contentList }">
				<tr target="id_rewards" rel="${vMap['id']}">
					<td align="center"><input type="checkbox" name="ids" value="${vMap['id']}"/></td>
					<td>${vMap['orderid']}</td>
					<td>${vMap['customerId']}</td>
					<td>${REWARDS_TYPE[vMap['type']]}</td>
					<td>${vMap['money']}</td>
					<td>${vMap['timeTrading']}</td>
					<td>${REWARDS_STATUS[vMap['status']]}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select id="rewardsList" class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
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
