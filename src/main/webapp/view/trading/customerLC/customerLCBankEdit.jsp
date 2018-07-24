<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<% request.setAttribute("ID_TYPE", DictionaryMap.ID_TYPE); %>
<% request.setAttribute("CARD_TYPE", DictionaryMap.CARD_TYPE); %>
<% request.setAttribute("CARD_FLAG", DictionaryMap.CARD_FLAG); %>
<%@ include file="../../include/include.jsp" %>
<div class="pageContent">

	<form name="form1" method="post" action="${ctx}/customerlc/updateCustomerLCBank.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		<h2 class="contentTitle">编辑用户银行卡信息详情</h2>
			<p>	
				<label>用户编号：</label>
				<input type="text" name="customerId" id="customerId" value="${dataMessage.data['customerId'] }" maxlength="20" disabled="disabled">
				<input type="hidden" name="id" id="id" value="${dataMessage.data['id'] }" maxlength="20">
			</p>
			<p>	
				<label>名称：</label>
				<input type="text" name="acctName" id="acctName" value="${dataMessage.data['acctName'] }" maxlength="20" disabled="disabled">
			</p>
			<p>	
				<label>证件卡号：</label>
				<input type="text" name="idNo" id="idNo" value="${dataMessage.data['idNo'] }" maxlength="20" disabled="disabled">
			</p>
			<p>	
				<label>身份证类型：</label>
				<input type="text" name="idType" id="idType" value="${ID_TYPE[dataMessage.data['idType']]}" maxlength="20" disabled="disabled">
			</p>
			<p>	
				<label>银行卡号：</label>
				<input type="text" name="cardNo" id="cardNo" value="${dataMessage.data['cardNo']}" maxlength="20" disabled="disabled">
			</p>
			<p>	
				<label>银行卡类型：</label>
				<input type="text" name="cardType" id="cardType" value="${CARD_TYPE[dataMessage.data['cardType']]}" maxlength="20" disabled="disabled">
			</p>
			<p>	
				<label>对公对私类型：</label>
				<input type="text" name="cardFlag" id="cardFlag" value="${CARD_FLAG[dataMessage.data['cardFlag']]}" maxlength="20" disabled="disabled">
			</p>
			<p>	
				<label>所属银行编号：</label>
				<input type="text" name="bankCode" id="bankCode" value="${dataMessage.data['bankCode'] }" maxlength="20" disabled="disabled">
			</p>
			<p>	
				<label>所属银行名称：</label>
				<input type="text" name="bankName" id="bankName" value="${dataMessage.data['bankName'] }" maxlength="20" disabled="disabled">
			</p>
			<p>	
				<label>开户行编号：</label>
				<input type="text" name="brabankCode" id="brabankCode" value="${dataMessage.data['brabankCode'] }" maxlength="20" >				
			</p>
			<p>	
				<label>开户支行名称：</label>
				<input type="text" class="required" name="brabankName" id="brabankName" value="${dataMessage.data['brabankName'] }" maxlength="30" >
			</p>
			<p>	
				<label>开户行所在省编码：</label>
				<input type="text" class="required" name="provinceCode" id="provinceCode" value="${dataMessage.data['provinceCode'] }" maxlength="20" >
			</p>
			<p>	
				<label>开户行所在省名称：</label>
				<input type="text" class="required" name="provinceName" id="provinceName" value="${dataMessage.data['provinceName'] }" maxlength="30" >				
			</p>
			<p>	
				<label>开户行所在市编码：</label>
				<input type="text" class="required" name="cityCode" id="cityCode" value="${dataMessage.data['cityCode'] }" maxlength="20">				
			</p>
			<p>	
				<label>开户行所在市名称：</label>
				<input type="text" class="required" name="cityName" id="cityName" value="${dataMessage.data['cityName'] }" maxlength="30" >	
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div>
				</li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>