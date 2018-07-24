<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../../include/include.jsp" %>
<% request.setAttribute("PRODUCTORDER_FLAG", DictionaryMap.PRODUCTORDER_FLAG); %>
<% request.setAttribute("TRADE_STATUS", DictionaryMap.TRADE_STATUS); %>
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
<title>理财投资管理</title>
<script type="text/javascript">
 function navTabAjaxDone(json){
   	 DWZ.ajaxDone(json);
 	 if (json.statusCode == DWZ.statusCode.ok){
 		 //列表页面重新加载数据
          navTab.reloadFlag("productOrdermgr");    
//           if(null != json.objList && "" != json.objList){
//         	  var str = new String(json.objList);  
//         	  var arr = new Array(); 
//         	  arr = str.split(',');
//         	  for(var i=0;i<arr.length-1;i++){  
//         		  alert($(arr[i]).html);
//         	 }  
//           }
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
		$("#productOrderList").val(num);
	});
  
 //文件上传
 function upload(){
     $.ajaxFileUpload({  
         url:'${ctx}/orderFileUpload/uploadImport.htm',  
         secureuri:false,  
         fileElementId:'file',//file标签的id  
         dataType: 'json',//返回数据的类型  
         success: function (data, status) {  
             var obj = jQuery.parseJSON(data); 
             alertMsg.info(obj['msg']);
         },  
         error: function (data, status, e) { 
        	 alertMsg.error(obj['msg']);
         }  
     });  
}
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
margin-left: 18px;
margin-top: 6px;
}
.updateposition{
background-position: -110px -310px;
}
.updatepositionok{
background-position: -65px -260px;

}
</style>
</head>
<body>

<form method="post"  id="pagerForm" onsubmit="return navTabSearch(this);" action="${ctx}/productOrder/getProductOrderList.htm">
<input type="hidden" name="pageNum" value="1" />
<div class="pageHeader">

	<div class="searchBar">
		<table class="searchContent" style="text-align: right;">
			<tr>
				<td>
					投资金额区间（&gt）：<input type="text" name="minMoney" id="minMoney" onkeypress="keyPress()" maxlength="10" value="${minMoney }" />
				</td>
				<td>
					投资金额区间（&lt）：<input type="text" name="maxMoney" id="maxMoney" onkeypress="keyPress()" maxlength="10" value="${maxMoney }" />
				</td>
				<td>
					投资时间区间（&gt）：<input type="text" name="beginDate"  class="date" dateFmt="yyyy-MM-dd"
readonly="true" value="${beginDate }"/>
				</td>
				<td>
					投资时间区间（&lt）：<input type="text" name="endDate"  class="date" dateFmt="yyyy-MM-dd"
readonly="true" value="${endDate }"/>
				</td>
			</tr>
			<tr>
				<td>
					订单号：<input type="text" name="orderId" id="orderId" value="${orderId}" />
				</td>
				<td>
					银行卡号：<input type="text" name="cardNo" id="cardNo" value="${cardNo}" />
				</td>
				<td>
					投资状态：<select name="status" style="width: 152px;">
							<option value="">请选择</option>
							<c:forEach var="flg" items="<%= DictionaryMap.PRODUCTORDER_FLAG%>">
								<option value="${flg.key}" <c:if test="${flg.key == status}">selected</c:if> >${flg.value }</option>
							</c:forEach>
						</select>
				</td>
				<td>
					匹配状态：<select name="defaultMoney" style="width: 152px;">
								<option value="">请选择</option>
								<option value="1" <c:if test="${defaultMoney == '1'}">selected</c:if> >已匹配</option>
								<option value="2" <c:if test="${defaultMoney == '2'}">selected</c:if> >未匹配</option>
							</select>
				</td>
			</tr>
		</table>
		<div class="divider"></div>
		<input id="file" name="file" type="file" onchange="upload()">(仅支持.txt,csv格式)
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
			<li><mgr:button href="${ctx}/productOrder/forwardInfo.htm?id={id_productOrder}" target="navTab" value="详情"  cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/productOrder/dwlExport.htm"  target="dwzExport" targetType="navTab" pagechecked="checked" cssClass="icon" value="导出 Excel"></mgr:button></li>
			<li><mgr:button href="${ctx}/productOrder/autoReconciliationOrder.htm" title="确实要对账?" target="selectedTodo" postType="string" value="批量自动对账" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/productOrder/delProductOrder.htm"  title="确实要删除这些充值记录吗?" target="selectedTodo" postType="string" value="删除" cssClass="delete"></mgr:button></li>
			<li>
				<span style="margin-left: 50px;">
					<c:if test="${countdataMessage.data['sumMoney'] != null}">当前总投资金额：<font color="red">${countdataMessage.data['sumMoney']}</font>元&nbsp;&nbsp;</c:if>
					<c:if test="${countdataMessage.data['sumResidualMoney'] != null}">当前可取现金额：<font color="red">${countdataMessage.data['sumResidualMoney']}</font>元&nbsp;&nbsp;</c:if>
					<c:if test="${countdataMessage.data['sumMatchMoney'] != null}">当前未匹配金额：<font color="red">${countdataMessage.data['sumMatchMoney']}</font>元</c:if>
				</span>
			</li>
		</ul>
	</div>

	<table class="table" width="100%" layoutH="228">
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
				<th width="5%">订单对账</th>
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
					<td>
						<c:choose>
						   <c:when test="${vMap['tradeStatus'] == null || vMap['tradeStatus'] == ''}">  
						       <a title="确定要进行订单对账吗！" target="ajaxTodo" href="${ctx}/productOrder/queryOrderPayStatus.htm?id=${vMap['id']}" class="btnb updateposition">订单对账</a>   
						   </c:when>
						   <c:otherwise> 
						   		<a title="对账成功！" class="btnb updatepositionok">对账成功</a>   
			  				</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select id="productOrderList" class="combox" name="numPerPage"  onchange="navTabPageBreak({numPerPage:this.value})">
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
