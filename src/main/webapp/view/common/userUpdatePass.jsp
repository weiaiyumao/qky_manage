<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="pageContent">
	<form method="post" action="${pageContext.request.contextPath }/user/mergeUserPwd.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDonePass);">
		<div class="pageFormContent nowrap" layoutH="56">
			<input type="hidden" name="id" value="${user.id }" />
			<dl>
				<dt>原始密码：</dt>
				<dd>
					<input name="userPassword" type="password"  class="required" size="30" alt="请输入登录密码">
				</dd>
			</dl>
			<dl>
				<dt>新密码：</dt>
				<dd>
					<input name="userPass" type="password"  class="required" size="30" alt="请输入登录密码">
				</dd>
			</dl>
			<dl>
				<dt>确认新密码：</dt>
				<dd>
					<input name="userPwd" type="password"  class="required" size="30" alt="请再次确认密码">
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>