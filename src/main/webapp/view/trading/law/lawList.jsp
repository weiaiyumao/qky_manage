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
<title>合同管理</title>
<script type="text/javascript">
$(document).ready(function(){
	//控制每页显示条数
	var num = ${dataMessage.page.numPerPage};
	$("#law").val(num);
});
function navTabAjaxDone(json){
   	 DWZ.ajaxDone(json);
 	 if (json.statusCode == DWZ.statusCode.ok){
 		 //列表页面重新加载数据
          navTab.reloadFlag("lawmgr");    
        if ("closeCurrent" == json.callbackType) {
              setTimeout(function(){navTab.closeCurrentTab();}, 100);
        } else if ("forward" == json.callbackType) {
              navTab.reload(json.forwardUrl);
        }
  }
}
function limit(){
	var objString = jQuery.trim($(this).text());  
    var objLength = objString.length; 
    if(objLength > num){  
	$(this).attr("title",objString);  
        objString = $(this).text(objString.substring(0,num) + "...");  
    }  
}
</script>
 
</head>
<body>

<form id="pagerForm" rel="pagerForm" onsubmit="return navTabSearch(this);"  method="post" action="${ctx}/law/getLawList.htm">
<input type="hidden" name="pageNum" value="1" />
<div class="pageHeader">

	<div class="searchBar">
		<table class="searchContent" style="text-align: right;"  >
			<tr>
				<td>
					合同时间区间（&gt）：<input type="text" name="beginDate"  class="date" dateFmt="yyyy-MM-dd HH:mm:ss"
readonly="true" value="${beginDate }"/>
				</td>
				<td>
					合同时间区间（&lt）：<input type="text" name="endDate"  class="date" dateFmt="yyyy-MM-dd HH:mm:ss"
readonly="true" value="${endDate }"/>
				</td>
				<td>
					合同编号：<input type="text" name="id" id="id" value="${id}">
				</td>
				<td>
					合同类别：<select name="lawtype" style="width: 152px;">
							<option value="">请选择</option>
							<c:forEach var="law" items="<%= DictionaryMap.LAWTYPE%>">
								<option value="${law.key }" <c:if test="${law.key == lawtype}">selected</c:if> >${law.value }</option>
							</c:forEach>
						</select>
				</td>
			</tr>
			<tr>
				<td>
					合同标题：<input type="text" name="title" id="title" value="${title}">
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
			<li><mgr:button href="${ctx}/law/forwardAdd.htm" target="navTab" value="新增合同"  cssClass="add"></mgr:button></li>
			<li><mgr:button href="${ctx}/law/forwardEdit.htm?id={id_law}" target="navTab" value="修改合同" warn="请选择需要修改的合同" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/law/forwardInfo.htm?id={id_law}"  target="navTab" value="合同详情" title="合同详情" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/law/delLaw.htm" title="确实要删除这些合同记录吗?" target="selectedTodo" postType="string" value="删除" cssClass="delete"></mgr:button>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="185">
		<thead>
			<tr>
			    <th align="center" width="5%" ><input type="checkbox" group="ids" class="checkboxCtrl"  ></th>
				<th width="5%">合同编号</th>
				<th width="30%">合同标题</th>
			    <th width="30%">合同类型</th>
				<th width="10%">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vMap" items="${dataMessage.contentList }">
				<tr target="id_law" rel="${vMap['id']}">
					<td align="center"><input type="checkbox" name="ids" value="${vMap['id']}"/></td>
					<td>${vMap['id']}</td>
					<td>${vMap['title']}</td>
					<td>${LAWTYPE[vMap['lawtype']]}</td>
					<td>${vMap['timeCreate']}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select id="law" class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
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
