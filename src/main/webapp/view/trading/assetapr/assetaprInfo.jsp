<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp" %>
<div class="pageContent">

	<form name="form1" method="post" action="${ctx}/law/addLaw.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		<h2 class="contentTitle">账户资产收益率规则详情</h2>
			<dl class="nowrap">
				<dt>编号：</dt>
				<dd>
					<input type="text" name="id" id="id" value="${dataMessage.data['id'] }" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>最小金额：</dt>
				<dd>
					<input type="text" name="minmoney" id="minmoney" value="${dataMessage.data['minmoney'] }" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>最大金额：</dt>
				<dd>
					<input type="text" name="maxmoney" id="maxmoney" value="${dataMessage.data['maxmoney'] }" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>利率：</dt>
				<dd>
					<input type="text" name="apr" id="apr" value="${dataMessage.data['apr'] }" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>称谓：</dt>
				<dd>
					<input type="text" class="required" size="30" name="customertitle" id="customertitle" disabled="disabled" value="${dataMessage.data['customertitle']}">
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