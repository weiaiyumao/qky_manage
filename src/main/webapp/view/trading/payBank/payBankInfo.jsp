<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp" %>
<div class="pageContent">

	<form name="Myform" method="post" action="${ctx}/PayBank/addPayBank.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		<h2 class="contentTitle">新增连连支持银行</h2>
			<dl class="nowrap">
				<dt>银行编码：</dt>
				<dd><input type="text" class="required" size="30" name="bankcode" id="bankcode" value="${dataMessage.data['bankcode']}" maxlength="20" ></dd>
			</dl>
			<dl class="nowrap">
				<dt>银行名称：</dt>
				<dd><input type="text" class="required" size="30" name="bankname" id="bankname" value="${dataMessage.data['bankname']}" maxlength="20"></dd>
			</dl>
			<dl class="nowrap">
				<dt>银行简称</dt>
				<dd><input type="text" class="required" size="30" name="banknick" id="banknick" value="${dataMessage.data['banknick']}" maxlength="20"></dd>
			</dl>
			<dl class="nowrap">
				<dt>每日限额：</dt>
				<dd><input type="text" class="required" size="30" name="dayquota" id="dayquota" value="${dataMessage.data['dayquota']}" maxlength="20"></dd>
			</dl>
			<dl class="nowrap">
				<dt>单笔限额：</dt>
				<dd><input type="text" class="required" size="30" name="onequota" id="onequota" value="${dataMessage.data['onequota']}" maxlength="20"></dd>
			</dl>
			<dl class="nowrap">
				<dt>描述：</dt>
				<dd><textarea name="remark" class="textInput" cols="95" rows="4" maxlength="200">${dataMessage.data['remark']}</textarea></dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>