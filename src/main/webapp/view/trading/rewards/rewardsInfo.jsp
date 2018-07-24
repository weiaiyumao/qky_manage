<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<% request.setAttribute("REWARDS_TYPE", DictionaryMap.REWARDS_TYPE); %>
<% request.setAttribute("REWARDS_STATUS", DictionaryMap.REWARDS_STATUS); %>
<%@ include file="../../include/include.jsp" %>
<div class="pageContent">

	<form name="form1" method="post" action="${ctx}/law/addLaw.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		<h2 class="contentTitle">活动奖励收益详情</h2>
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
				<dt>收益类型：</dt>
				<dd>
					<input type="text" name="type" id="type" value="${REWARDS_TYPE[dataMessage.data['type']]}" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>处理状态：</dt>
				<dd>
					<input type="text" name="status" id="status" value="${REWARDS_STATUS[dataMessage.data['status']]}" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>交易时间：</dt>
				<dd>
					<input type="text" name="timeTrading" id="timeTrading" value="${dataMessage.data['timeTrading'] }" maxlength="20" disabled="disabled">
				</dd>
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