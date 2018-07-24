<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp" %>
<div class="pageContent">

	<form name="Myform" method="post" action="${ctx}/ruleAssetapr/addassetapr.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		<h2 class="contentTitle">新增账户资产收益率规则</h2>
			<dl class="nowrap">
				<dt>最小金额：</dt>
				<dd>
					<input type="text" class="number required" size="30" name="minmoney" id="minmoney" readonly="readonly" value="${maxdataMessage.data['maxmoney']}" maxlength="10" >
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>最大金额：</dt>
				<dd>
					<input type="text" class="number required" size="30" name="maxmoney" id="maxmoney" onblur="checkMoneyToEight('maxmoney')" value="" maxlength="10">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>利率：</dt>
				<dd>
					<input type="text" class="number required" size="30" name="apr" id="apr" value="" maxlength="8">
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>称谓：</dt>
				<dd>
					<input type="text" class="required" size="30" name="customertitle" id="customertitle" maxlength="20">
				</dd>
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