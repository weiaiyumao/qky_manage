<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% String contextPath =request.getContextPath(); %>  
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>华融道管理平台</title>
<link href="${pageContext.request.contextPath }/resources/css/login.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function hideError(){
		document.getElementById("error").style.display="none";
	}
</script>
</head>

<body>
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<img src="${pageContext.request.contextPath }/resources/images/login_logo.gif">
			</h1>
			<div class="login_headerContent">
				<h2 class="login_title"><img src="${pageContext.request.contextPath }/resources/images/login_title.png"></h2>
			</div>
		</div>
		<div id="login_content">
			<div class="loginForm">
				<form action="${pageContext.request.contextPath }/j_spring_security_check" method="post">
					<table>
						<tr style="line-height: 30px; height: 30px;">
							<td align="right">用户名：</td>
							<td><input type="text" name="j_username" size="20" class="login_input"></td>
						</tr>
						<tr style="line-height: 30px; height: 30px;">
							<td align="right">密码：</td>
							<td><input type="password" name="j_password" size="20" onclick="hideError()" class="login_input"></td>
						</tr>
						<c:if test="${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message != null}">
							<tr style="line-height: 30px; height: 30px;" id="error">
								<td align="right">&nbsp;</td>
								<td style="color: red;" align="right">用户名或者密码错误</td>
							</tr>
						</c:if>
					</table>
					<div class="login_bar" style="margin-left: 82px;margin-top: 6px;">
							<input class="sub"  id="j_submit" name="j_submit" type="submit" value="">
					</div>
				</form>
			</div>
			<div class="login_banner"><img src="${pageContext.request.contextPath }/resources/images/login_banner.jpg"></div>
			<div class="login_main">
				<ul class="helpList">
					
				</ul>
				<div class="login_inner">
				</div>
			</div>
		</div>
		<div id="login_footer">
			中秋（上海）金融信息服务有限公司 
		</div>
	</div>

</body></html>    