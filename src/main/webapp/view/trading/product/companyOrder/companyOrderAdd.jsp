<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.zqfinance.system.config.DataConfig" %>
<c:set var="bankmap" value="<%=DataConfig.BANK_MAP %>"  />
<div class="pageContent">
	<form method="post" action="${pageContext.request.contextPath }/companyOrder/saveCompanyOrder.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="56">
			<dl>
				<dt>充值金额：</dt>
				<dd>
					<input class="required number" id="money" name="money" type="text" size="30" value="" alt="请输入充值金额" />
				</dd>
			</dl>
			<dl>
				<dt>充值产品：</dt>
				<dd>
					<select id="bankCode" name="bankCode" class="required combox" style="width: 210px;">
						<c:forEach var="bank" items="${bankmap }">
							<option value="${bank.key}">${bank.value}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>银行卡所有人：</dt>
				<dd>
					<input class="required" id="acctName" name="acctName" type="text" size="30" value="" alt="银行卡所有人" />
				</dd>
			</dl>
			
			<dl>
				<dt>开卡身份证：</dt>
				<dd>
					<input class="required" id="idNo" name="idNo" type="text" size="30" value="" alt="开卡身份证" />
				</dd>
			</dl>
			
			
			<dl>
				<dt>充值产品：</dt>
				<dd>
					<select id="productId" name="productId" class="required combox" style="width: 210px;">
						<c:forEach var="product" items="${productList }">
							<option value="${product['id'] }">${product['title'] }</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
				
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>