<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<% request.setAttribute("TYPE", DictionaryMap.TYPE); %>
<% request.setAttribute("INVESTDUMMYPRINCIPLE_STATUS", DictionaryMap.INVESTDUMMYPRINCIPLE_STATUS); %>
<%@ include file="../../include/include.jsp" %>
<div class="pageContent">

	<form name="form1" method="post" action="${ctx}/law/addLaw.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		<h2 class="contentTitle">用户的体验本金详情</h2>
			<dl class="nowrap">
				<dt>订单号：</dt>
				<dd>
					<input type="text" name="orderid" id="orderid" value="${dataMessage.data['orderid'] }" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>用户编号：</dt>
				<dd>
					<input type="text" name="customerId" id="customerId" value="${dataMessage.data['customerId'] }" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>金额：</dt>
				<dd>
					<input type="text" name="money" id="money" value="${dataMessage.data['money'] }" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>利率：</dt>
				<dd>
					<input type="text" name="apr" id="apr" value="${dataMessage.data['apr'] }" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>体验金额类型：</dt>
				<dd>
					<input type="text" name="apr" id="apr" value="${TYPE[dataMessage.data['type']]}" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>处理状态：</dt>
				<dd>
					<input type="text" name="apr" id="apr" value="${TYPE[dataMessage.data['status']]}" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>开始时间：</dt>
				<dd>
					<input type="text" name="begindate" id="begindate" value="${dataMessage.data['begindate'] }" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>结束时间：</dt>
				<dd>
					<input type="text" name="enddate" id="enddate" value="${dataMessage.data['enddate'] }" maxlength="20" disabled="disabled">
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