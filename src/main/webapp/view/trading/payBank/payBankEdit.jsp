<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp" %>
<div class="pageContent">

	<form name="Myform" method="post" action="${ctx}/PayBank/updatePayBank.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		<h2 class="contentTitle">新增连连支持银行</h2>
			<input type="hidden" class="required" size="30" name="id" value="${dataMessage.data['id']}" maxlength="20" >
			<p>	
				<label>银行编码：</label>
				<input type="text" class="required" size="30" name="bankcode" id="bankcode" value="${dataMessage.data['bankcode']}" maxlength="20" >
			</p>
			<p>	
				<label>银行名称：</label>
				<input type="text" class="required" size="30" name="bankname" id="bankname" value="${dataMessage.data['bankname']}" maxlength="20">
			</p>
			<p>	
				<label>银行简码：</label>
				<input type="text" class="required" size="30" name="banknick" id="banknick" value="${dataMessage.data['banknick']}" maxlength="20">
			</p>
			<p>	
				<label>每日限额：</label>
				<input type="text"  size="30" name="dayquota" id="dayquota" value="${dataMessage.data['dayquota']}" maxlength="20">
			</p>
			<p>	
				<label>单笔限额：</label>
				<input type="text" class="required" size="30" name="onequota" id="onequota" value="${dataMessage.data['onequota']}" maxlength="20">
			</p>
			<dl class="nowrap">
				<dt>描述：</dt>
				<dd><textarea name="remark" class="textInput" cols="95" rows="4" maxlength="200">${dataMessage.data['remark']}</textarea></dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>