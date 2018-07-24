<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<% request.setAttribute("ID_TYPE", DictionaryMap.ID_TYPE); %>
<% request.setAttribute("CARD_TYPE", DictionaryMap.CARD_TYPE); %>
<%@ include file="../../include/include.jsp" %>
<script>
function updateOrder(){
	var data={
			"money":$("#money").val(),
			"orderId":$("#outmoneyOrderId").val(),
			"acctName":$("#acctName").val(),
			"bankCode":$("#bankCode").val(),
			"bankName":$("#bankName").val(),
			"brabankCode":$("#brabankCode").val(),
			"brabankName":$("#brabankName").val(),
			"cardFlag":$("#cardFlag").val(),
			"cardNo":$("#cardNo").val(),
			"cardType":$("#cardType").val(),
			"cityCode":$("#cityCode").val(),
			"cityName":$("#cityName").val(),
			"idNo":$("#idNo").val(),
			"idType":$("#idType").val(),
			"provinceCode":$("#provinceCode").val(),
			"provinceName":$("#provinceName").val()
	}
	$.ajax({
		type:"POST",
		dataType:"json",
		url: ctx +"/companyOutMoney/addCompanyOutMoney.htm",
		data:data,
		success:function(response){debugger;
				 if (response.statusCode == DWZ.statusCode.ok){
					 alertMsg.correct(response.message);
					 navTab.reload(response.forwardUrl); 
				 }else{
					 alertMsg.error(response.message);
				 }
		}
	});
}

function aduitOrder(){
	var data={
			"money":$("#money").val(),
			"orderId":$("#outmoneyOrderId").val(),
			"acctName":$("#acctName").val(),
			"bankCode":$("#bankCode").val(),
			"bankName":$("#bankName").val(),
			"brabankCode":$("#brabankCode").val(),
			"brabankName":$("#brabankName").val(),
			"cardFlag":$("#cardFlag").val(),
			"cardNo":$("#cardNo").val(),
			"cardType":$("#cardType").val(),
			"cityCode":$("#cityCode").val(),
			"cityName":$("#cityName").val(),
			"idNo":$("#idNo").val(),
			"idType":$("#idType").val(),
			"provinceCode":$("#provinceCode").val(),
			"provinceName":$("#provinceName").val()
	}
	$.ajax({
		type:"POST",
		dataType:"json",
		url: ctx +"/companyOutMoney/aduitCompanyOutMoney.htm",
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

	<form name="form1" method="post" action="${ctx}/companyOutMoney/getCompanyOutMoneyList.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		<h2 class="contentTitle">编辑用户银行卡信息详情</h2>
			<p>	
				<label>订单号：</label>
				<input type="text" name="outmoneyOrderId" id="outmoneyOrderId" value="${companyOutMoneyDataMessage.data['orderId'] }" readonly="readonly">
			</p>
			<p>	
				<label>名称：</label>
				<input type="text" name="acctName" id="acctName" value="${bankDataMessage.data['acctName'] }" maxlength="20" disabled="disabled">
			</p>
			<p>	
				<label>证件卡号：</label>
				<input type="text" name="idNo" id="idNo" value="${bankDataMessage.data['idNo'] }" maxlength="20" disabled="disabled">
			</p>
			<p>	
				<label>身份证类型：</label>
				<select name="idType" id="idType" style="width: 152px;">
							<option value="${bankDataMessage.data['idType'] }" >${ID_TYPE[bankDataMessage.data['idType']]}</option>
				</select>
			</p>
			<p>	
				<label>银行卡号：</label>
				<input type="text" name="cardNo" id="cardNo" value="${bankDataMessage.data['cardNo']}" maxlength="20" disabled="disabled">
			</p>
			<p>	
				<label>银行卡类型：</label>
				<select name="cardType" id="cardType" style="width: 152px;">
							<option value="${bankDataMessage.data['cardType'] }" >${CARD_TYPE[bankDataMessage.data['cardType']]}</option>
				</select>
			</p>
			<p>	
				<label>对公对私类型：</label>
				<input type="text" name="cardFlag" id="cardFlag" value="${bankDataMessage.data['cardFlag'] }" maxlength="20" disabled="disabled">
			</p>
			<p>	
				<label>所属银行编号：</label>
				<input type="text" name="bankCode" id="bankCode" value="${bankDataMessage.data['bankCode'] }" maxlength="20" disabled="disabled">
			</p>
			<p>	
				<label>所属银行名称：</label>
				<input type="text" name="bankName" id="bankName" value="${bankDataMessage.data['bankName'] }" maxlength="20" disabled="disabled">
			</p>
			<div class="divider"></div>
			<p>	
				<label>开户行编号：</label>
				<input type="text" name="brabankCode" id="brabankCode" value="${bankDataMessage.data['brabankCode'] }" maxlength="20" >				
			</p>
			<p>	
				<label>开户支行名称：</label>
				<input type="text" class="required" name="brabankName" id="brabankName" value="${bankDataMessage.data['brabankName'] }" maxlength="30" >
			</p>
			<p>	
				<label>开户行所在省编码：</label>
				<input type="text" class="required" name="provinceCode" id="provinceCode" value="${bankDataMessage.data['provinceCode'] }" maxlength="20" >
			</p>
			<p>	
				<label>开户行所在省名称：</label>
				<input type="text" class="required" name="provinceName" id="provinceName" value="${bankDataMessage.data['provinceName'] }" maxlength="30" >				
			</p>
			<p>	
				<label>开户行所在市编码：</label>
				<input type="text" class="required" name="cityCode" id="cityCode" value="${bankDataMessage.data['cityCode'] }" maxlength="20">				
			</p>
			<p>	
				<label>开户行所在市名称：</label>
				<input type="text" class="required" name="cityName" id="cityName" value="${bankDataMessage.data['cityName'] }" maxlength="30" >	
			</p>
			<p>
				<label>取现金额:</label>
				<input class=" number textInput" name="money" id="money"
						type="text" size="30" value="${companyOutMoneyDataMessage.data['money'] }"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="updateOrder()">确认修改</button></div></div>
				</li>
				<li>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="aduitOrder()">审核通过</button></div></div>
				</li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>