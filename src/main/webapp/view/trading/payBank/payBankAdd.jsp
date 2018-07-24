<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp" %>
<div class="pageContent">

	<form name="Myform" method="post" action="${ctx}/PayBank/addPayBank.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		<h2 class="contentTitle">新增连连支持银行</h2>
			<p>	
				<label>银行编码：</label>
				<input type="text" class="required" size="30" name="bankcode" id="bankcode" value="" maxlength="20" >
			</p>
			<p>	
				<label>银行名称：</label>
				<input type="text" class="required" size="30" name="bankname" id="bankname" value="" maxlength="20">
			</p>
			<p>	
				<label>银行简码：</label>
				<input type="text" class="required" size="30" name="banknick" id="banknick" value="" maxlength="20">
			</p>
			<p>	
				<label>每日限额：</label>
				<input type="text"  size="30" name="dayquota" id="dayquota" maxlength="20">
			</p>
			<p>	
				<label>单笔限额：</label>
				<input type="text" class="required" size="30" name="onequota" id="onequota" maxlength="20">
			</p>
			<dl class="nowrap">
				<dt>描述：</dt>
				<dd><textarea name="remark" class="textInput" cols="95" rows="4" maxlength="200"></textarea></dd>
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