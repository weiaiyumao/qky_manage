<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<% request.setAttribute("ID_TYPE", DictionaryMap.ID_TYPE); %>
<% request.setAttribute("CARD_TYPE", DictionaryMap.CARD_TYPE); %>
<% request.setAttribute("CARD_FLAG", DictionaryMap.CARD_FLAG); %>
<%@ include file="../../include/include.jsp" %>
<div class="pageContent">

	<form name="form1" method="post" action="${ctx}/law/addLaw.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		<h2 class="contentTitle">用户银行卡信息详情</h2>
			<dl class="nowrap">
				<dt>用户编号：</dt>
				<dd>
					<input type="text" name="customerId" id="customerId" value="${dataMessage.data['customerId'] }" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>名称：</dt>
				<dd>
					<input type="text" name="acctName" id="acctName" value="${dataMessage.data['acctName'] }" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>证件卡号：</dt>
				<dd>
					<input type="text" name="idNo" id="idNo" value="${dataMessage.data['idNo'] }" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>身份证类型：</dt>
				<dd>
					<input type="text" name="idType" id="idType" value="${ID_TYPE[dataMessage.data['idType']]}" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>银行卡号：</dt>
				<dd>
					<input type="text" name="cardNo" id="cardNo" value="${dataMessage.data['cardNo']}" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>银行卡类型：</dt>
				<dd>
					<input type="text" name="cardType" id="cardType" value="${CARD_TYPE[dataMessage.data['cardType']]}" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>对公对私类型：</dt>
				<dd>
					<input type="text" name="cardFlag" id="cardFlag" value="${CARD_FLAG[dataMessage.data['cardFlag']]}" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>所属银行编号：</dt>
				<dd>
					<input type="text" name="bankCode" id="bankCode" value="${dataMessage.data['bankCode'] }" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>所属银行名称：</dt>
				<dd>
					<input type="text" name="bankName" id="bankName" value="${dataMessage.data['bankName'] }" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>开户行编号：</dt>
				<dd>
					<input type="text" name="brabankCode" id="brabankCode" value="${dataMessage.data['brabankCode'] }" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>开户支行名称：</dt>
				<dd>
					<input type="text" name="brabankName" id="brabankName" value="${dataMessage.data['brabankName'] }" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>开户行所在省编码：</dt>
				<dd>
					<input type="text" name="provinceCode" id="provinceCode" value="${dataMessage.data['provinceCode'] }" maxlength="20" disabled="disabled">
				</dd>
			</dl>
				<dl class="nowrap">
				<dt>开户行所在省名称：</dt>
				<dd>
					<input type="text" name="provinceName" id="provinceName" value="${dataMessage.data['provinceName'] }" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>开户行所在市编码：</dt>
				<dd>
					<input type="text" name="cityCode" id="cityCode" value="${dataMessage.data['cityCode'] }" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>开户行所在市名称：</dt>
				<dd>
					<input type="text" name="cityName" id="cityName" value="${dataMessage.data['cityName'] }" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>创建时间：</dt>
				<dd>
					<input type="text" name="timeCreate" id="timeCreate" value="${dataMessage.data['timeCreate'] }" maxlength="20" disabled="disabled">
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>