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
<script src="${ctx}/resources/js/util/jsonUtil.js"></script> 
<c:if test="${dataMessage.result != 0}">
<script type="text/javascript">
	$(document).ready(function(){
		alertMsg.info("${dataMessage.msg}");
	});
</script>
</c:if>
<title>理财管理</title>
<script type="text/javascript">  
function upload(){
        $.ajaxFileUpload({  
            url:'${ctx}/upload/uploadImport.htm',  
            secureuri:false,  
            fileElementId:'file',//file标签的id  
            dataType: 'json',//返回数据的类型  
            success: function (data, status) {  
                var obj = jQuery.parseJSON(data); 
                alertMsg.correct(obj['msg'])
            },  
            error: function (data, status, e) { 
            	alertMsg.error("错误");  
            }  
        });  
}
</script>  
<script type="text/javascript">
 function navTabAjaxDone(json){
   	 DWZ.ajaxDone(json);
 	 if (json.statusCode == DWZ.statusCode.ok){
 		 //列表页面重新加载数据
         // navTab.reloadFlag("outmoneymgr");    
        if ("closeCurrent" == json.callbackType) {
              setTimeout(function(){navTab.closeCurrentTab();}, 100);
        } else if ("forward" == json.callbackType) {
              navTab.reload(json.forwardUrl);
        }
  	}
 	 navTab.reloadFlag("outmoneymgr");    
 } 
	$(document).ready(function(){
		Ilength=0;
		setValue=0;
		//控制每页显示条数
		var num = ${dataMessage.page.numPerPage};
		$("#outMoneyList").val(num);
	});
	
	function checkAll(money){
		if($("input[name='boxOfAll']:checked").length>0){
			$("#fontId").html(money.formatMoney());
			Ilength=${dataMessage.page.numPerPage};
			setValue=money;
		}else{
			$("#fontId").html(0);
			Ilength=0;
			setValue=0;
		}
	}
	
	function check(money){
		 var sumVALUE;
		 var length=$("input[name='ids']:checked").length;
		 if(length>Ilength){
			 sumVALUE=setValue+money;
		 }else{
			 sumVALUE=setValue-money;
		 }
		 Ilength=length;
		 $("#fontId").html((Math.round(sumVALUE*100)/100).formatMoney());
		 setValue=sumVALUE;
		 if(setValue<0){
			 setValue=0;
			 $("#fontId").html(0);
		 }
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

<form id="pagerForm" rel="pagerForm" onsubmit="return navTabSearch(this);"  method="post" action="${ctx}/outMoney/getOutMoneyList.htm">
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
					银行  ：<input type="text" name="brank" id="brank"  maxlength="20" value="${brank }" />
				</td>
				<td>
					银行卡号 ：<input type="text" name="cardNo" id="cardNo"  maxlength="20" value="${cardNo }" />
				</td>	
				<td>
				           订单号 ：<input type="text" name="orderNo" id="orderNo"  maxlength="20" value="${orderNo}" />
				</td>
				<td>
					提现状态：<select name="flag" style="width: 152px;">
							<option value="">请选择</option>
							<c:forEach var="_status" items="<%= DictionaryMap.OUTMONEY_FLAG%>">
								<option value="${_status.key }" <c:if test="${_status.key == flag}">selected</c:if> >${_status.value }</option>
							</c:forEach>
						</select>
				</td>
			</tr>
		</table>
		<div class="divider"></div>
		<input id="file" name="file" type="file" onchange="upload()">(仅支持.txt,.xls格式)
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
			<li><mgr:button href="${ctx}/outMoney/getOutMoneyDetail.htm?id={id_outMoney}" target="navTab" value="提现详情" title="查看详情" warn="请选择需要查询的选项" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/outMoney/getOutMoneyAudit.htm?id={id_outMoney}" title="审核提现" target="navTab" warn="请选择需要审核的选项" value="审核提现" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/outMoney/batchAuditOutMoney.htm" title="确认自动审核吗？" target="selectedTodo" postType="string" value="自动审核" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/outMoney/payOutMoney.htm" title="确实要放款吗?" target="selectedTodo" postType="string" value="自动放款" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/outMoney/autoReconciliationOutMoney.htm" title="确实要对账?" target="selectedTodo" postType="string" value="自动对账" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${ctx}/outMoney/dwlExport.htm"  target="dwzExport" targetType="navTab" pagechecked="nochecked" cssClass="icon" value="导出 Excel"></mgr:button></li>
			<li><mgr:button href="${ctx}/outMoney/dwlFinanceExport.htm"  target="dwzExport" targetType="navTab" pagechecked="checked" cssClass="icon" value="财务导出 Excel"></mgr:button></li>
			<li><mgr:button href="${ctx}/outMoney/deleteOutMoney.htm?id={id_outMoney}"  target="ajaxTodo" value="删除" title="确定要删除吗?" cssClass="delete"></mgr:button></li>
			<li>
				<span style="margin-left: 50px;"><c:if test="${sumMoney != '0.00'}">当前统计总金额：<font color="red">${sumMoney}</font>元</c:if></span>
				<span style="margin-left: 50px;">选中统计总金额：<font color="red" id="fontId">0</font>元</span>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="228">
		<thead>
			<tr>
			    <th align="center" width="5%" ><input type="checkbox" group="ids" name="boxOfAll" class="checkboxCtrl" onchange="checkAll(${dataMessage.sumMoney });" ></th>
				<th width="10%">订单号</th>
				<th width="12%">取现金额(RMB)</th>
				<th width="8%">所属银行</th>
				<th width="12%">银行卡号</th>
				<th width="5%">提现状态</th>
				<th width="10%">订单生成时间</th>
				<th width="10%">交易成功时间</th>
				<th width="5%">平账操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vMap" items="${dataMessage.contentList }" varStatus="status">
				<tr target="id_outMoney" rel="${vMap['id']}">
					<td align="center">
					<input type="checkbox" name="ids" value="${vMap['id']}" onchange="check(${vMap['money']});"/>
					</td>
					<td>${vMap['orderNo']}</td>
					<td>${vMap['money']}</td>
					<td>${bankMap[vMap['bank']]}</td>
					<td>${vMap['cardid']}</td> 
					<td>${OUTMONEY_FLAG[vMap['status']]}</td>					
					<td>${vMap['timeCreate'] }</td>
					<td>${vMap['timeTrading']}</td>		
					<td>
						<c:if test="${vMap['countReconciliation']!=null}">
							<a title="提现对账" target="navTab"
								href="${ctx}/outMoney/reconciliationOutMoney.htm?orderNo=${vMap['orderNo']}" 
								class="btnEdit" style="margin-left: 15px;margin-top: 5px;">对账</a>
						</c:if>
						<c:if test="${vMap['isReconciliation']=='1'}">
							<a title="对账成功！" class="btnb updatepositionok"></a> 
						</c:if>
					</td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select id="outMoneyList" class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
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
