<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp" %>
<% request.setAttribute("LAWTYPE", DictionaryMap.LAWTYPE); %>
<div class="pageContent">

	<form name="form1" method="post" action="${ctx}/law/addLaw.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		<h2 class="contentTitle">合同详情</h2>
			<dl class="nowrap">
				<dt>合同标题：</dt>
				<dd>
					<input type="text" name="title" id="title" value="${dataMessage.data['title'] }" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>合同类型：</dt>
				<dd>
					<input type="text" name="title" id="title" value="${LAWTYPE[dataMessage.data['lawtype']]}" maxlength="20" disabled="disabled">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>合同内容：</dt>
				<textarea class="editor readonly" name="content" id="content" rows="30" cols="100" disabled="disabled" readonly="readonly" >${dataMessage.data['content'] }</textarea>
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