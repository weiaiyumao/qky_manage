<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="pageContent">
	<form name="form1" method="post" action="${pageContext.request.contextPath }/cancelCustomerInSession.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone2);">
		<div class="pageFormContent nowrap" layoutH="56">
			<dl>
				<dt>用户ID：</dt>
				<dd>
					${sessionScope.customer.customerId}
				</dd>
			</dl>
			<dl>
				<dt>用户名称：</dt>
				<dd>
					${sessionScope.customer.username}
				</dd>			
			</dl>
			<dl>
				<dt>电话号码：</dt>
				<dd>
					${sessionScope.customer.mobile}
				</dd>			
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">注销</button></div></div></li>
			</ul>
		</div>
	</form>
</div>