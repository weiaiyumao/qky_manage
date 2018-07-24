<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../include/include.jsp" %>  
<%@ page import="com.zqfinance.system.util.DictionaryMap" %>
<link rel="stylesheet" href="${ctx}/resources/themes/css/zTreeStyle.css" type="text/css">
<script src="${ctx}/resources/js/jquery.ztree.core-3.5.min.js"></script>
<script src="${ctx}/resources/js/jquery.ztree.excheck-3.5.min.js"></script>
<script src="${ctx}/resources/js/trading/credit/credit.js" type="text/javascript"></script>
<script src="${ctx}/view/trading/product/order/dwz.database.js" type="text/javascript"></script>
<script type="text/javascript">
	function removelistRow(id){
		alertMsg.confirm("您确定要删除吗，请选择保存或取消！", {
			okCall: function(){
				for(i=0;i<30;i++){
					 $("#list"+id).remove();
				}
			}
		});
	}
	function navTabAjaxDone(json){
	   	 DWZ.ajaxDone(json);
	 	 if (json.statusCode == DWZ.statusCode.ok){
	 		 //列表页面重新加载数据
	         navTab.reloadFlag("productOrdermgr");         
	        if ("closeCurrent" == json.callbackType) {
	              setTimeout(function(){navTab.closeCurrentTab();}, 100);
	        } else if ("forward" == json.callbackType) {
	              navTab.reload(json.forwardUrl);
	        }
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
margin-right: 3px;
}
.addposition{
background-position: -110px -255px;
}
.delposition{
background-position: -158px -255px;
}
</style>
<div class="pageContent">
	<form name="loanForm" method="post" action="${ctx}/productOrder/marryLoan.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56" id="PageList">
			<h2 class="contentTitle">订单号（${dataMessage.data['orderId'] }）基本信息</h2>
			<p>
				<label>理财产品标题：</label>
				<input name="title" disabled="disabled"  type="text" size="30" value="${dataMessage_product.data['title'] }"/>
			</p>
			<p>
				<label>订单金额：</label>
				<input id="money" disabled="disabled"  name="money" type="text" size="30" value="${dataMessage.data['money'] }"/>
				<input id="investProductOrderId" type="hidden" name="investProductOrderId" value="${dataMessage.data['id']}">
			</p>
			<p>
				<label>订单来源：</label>
				<input name="paytype" disabled="disabled" type="text" size="30" value="${dataMessage.data['paytype'] }"/>
			</p>
			<p>
				<label>剩余可提现金额：</label>
				<input name="residualMoney" disabled="disabled" type="text" size="30" value="${dataMessage.data['residualMoney'] }"/>
			</p>
			<p>
				<label>未匹配金额：</label>
				<input name="matchMoney" disabled="disabled" type="text" size="30" value="${dataMessage.data['matchMoney'] }"/>
			</p>
			<p>
				<label>交易时间：</label>
				<input name="orderdate" disabled="disabled" type="text" size="30" value="${dataMessage.data['orderdate'] }"/>
			</p>
			<p>
				<label>交易银行卡号：</label>
				<input name="cardNo" disabled="disabled" type="text" size="30" value="${dataMessage.data['cardNo'] }"/>
			</p>
			
			<h2 class="contentTitle" style="clear: both;">选择贷款标列表信息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="btnb addposition" href="${ctx}/loan/getloanViewList.htm" lookupgroup="" style="clear: both;">可匹配贷款标</a></h2>
			<input name="appendList" id="appendList" type="hidden" value="不能动的input --- 谁动傻逼：供js检索用">
		</div>
		<div class="formBar">
			<ul>
				<c:if test="${reqBtn=='edit'}">
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				</c:if>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>