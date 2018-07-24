<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp"%>
<script>
function submitOrder(){
	var money=$("#money").val();
	var bankId=$("#bankId").val();
	if(null==money||money==''||parseFloat(money)=='0'){
		alertMsg.error("请输取现入金额");
		return false;
	}
	if(null==bankId||bankId==''){
		alertMsg.error("请选择银行卡");
		return false;
	}
	var data={
			"money":money,
			"bankId":bankId
	}
	$.ajax({
		type:"POST",
		dataType:"json",
		url: ctx +"/companyOutMoney/addCompanyOutMoney.htm",
		data:data,
		success:function(response){debugger;
				 if (response.statusCode == DWZ.statusCode.ok){
					 alertMsg.correct(response.message);
			         navTab.reloadFlag("companyOutmoneymgr"); 
				 }else{
					 alertMsg.error(response.message);
				 }
		}
	});
}
</script>
<div class="pageContent">
	<form  method="post"
		action=""
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<h2 class="contentTitle">提现详情信息</h2>
			<dl class="nowrap">
				<dt>取现金额：</dt>
				<dd>
					<input id="money" class=" number textInput" id="money"
						type="text" size="30" maxlength="9"/>
				</dd>
				<dt>提取到银行卡：</dt>
				<dd>
					<select id="bankId" style="width: 152px;">
							<option value="">请选择</option>
							<c:forEach var="bank" items="${bankMessage.contentList }">
								<option value="${bank.id }" <c:if test="${bank.id == dataMessage.data['bankId']}">selected</c:if> >${bank.cardNo }</option>
							</c:forEach>
					</select>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li>
						<div class="button">
							<div class="buttonContent" style="margin-right: 20px;">
								<button type="button" class="close"  onclick="submitOrder()">提交订单</button>
							</div>
						</div>
						<div class="button">
							<div class="buttonContent" style="margin-right: 20px;">
								<button type="button" class="close">返回</button>
							</div>
						</div>
				</li>
			</ul>
		</div>
	</form>
</div>