<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="../view/include/resource.jsp" />
<%@ include file="../view/include/include.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rrdai.com/mgr-tags" prefix="mgr"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<% Long random = new Date().getTime(); %>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<head>
<style type="text/css">
	#header{height:85px}
	#leftside, #container, #splitBar, #splitBarProxy{top:90px}
	.fontClass{ color: green;}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>区块云管理后台</title>


<script  type="text/javascript">
function dialogAjaxDone(json){
    DWZ.ajaxDone(json);
    if (json.statusCode == DWZ.statusCode.ok){
    	  $("#customerId").show();
    	  $("#customerId").html(json.objList[0].username);
    	  $("#customerList").hide();
	      $.pdialog.closeCurrent();
          navTab.closeAllTab();
    }
}

function dialogAjaxDone2(json){
    DWZ.ajaxDone(json);
    if (json.statusCode == DWZ.statusCode.ok){
    	  $("#customerList").show();
    	  $("#customerId").hide();
          $.pdialog.closeCurrent();
          navTab.closeAllTab();
    }
}

function navTabAjaxDonePass(json){
	 DWZ.ajaxDone(json);
	 if (json.statusCode == DWZ.statusCode.ok){
		 $.pdialog.closeCurrent();
   }
}

</script>

</head>

<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="http://www.hrd800.com">标志</a>

				<ul class="nav">
					<li id="switchEnvBox"><a href="javascript:">欢迎您：${sessionScope.user.userName}</a>
					</li>
<%-- 					<li><a id="customerId" href="${pageContext.request.contextPath }/forwordCustomerSessionPage.htm" target="dialog">
					<c:choose>
						<c:when test="${sessionScope.customer != null }">
								${sessionScope.customer.customerId }
						</c:when>
						<c:otherwise>
							客户信息
						</c:otherwise>
					</c:choose>			
					</a></li> --%>
					<li>
						<c:choose>
						<c:when test="${sessionScope.customer != null }">
						<c:if test="${sessionScope.customer.username==null||sessionScope.customer.username==''}"><a id="customerId" href="${ctx}/forwordCustomerSessionPage.htm" target="dialog">未知名用户</a></c:if>
							<a id="customerId" href="${pageContext.request.contextPath }/forwordCustomerSessionPage.htm" target="dialog">${sessionScope.customer.username }</a>
						</c:when>
						<c:otherwise>
							<mgr:button id="customerList" href="${ctx}/findCustomerList.htm?t=<%=random %>" target="dialog" value="客户列表" width="800" height="400"  cssClass=""></mgr:button>
							<mgr:button id="customerList" href="${ctx}/findPublicCustomerList.htm?t=<%=random %>" target="dialog" value="客户列表" width="800" height="400"  cssClass=""></mgr:button>
							<a id="customerId" href="${ctx}/forwordCustomerSessionPage.htm" target="dialog" style="display: none"></a>
						</c:otherwise>
					</c:choose>
                   </li>
					<li><a href="${ctx}/user/forwordUserPwd.htm?1=1&userId=${user.id}" target="dialog" width="800" height="400">修改密码</a></li>
					<li><a href="${ctx}/j_spring_security_logout">退出</a></li>
				</ul>
				<ul class="themeList" id="themeList">
					<li theme="default"><div>蓝色</div></li>
					<li theme="green"><div>绿色</div></li>
					<li theme="purple"><div>紫色</div></li>
					<li theme="silver"><div>银色</div></li>
					<li theme="azure"><div class="selected">天蓝</div></li>
				</ul>
			</div>

			<div id="navMenu">
				<ul>
					<li ><a href="${pageContext.request.contextPath}/view/tradingmenu.jsp"><span>交易管理</span></a></li>
					<li><a href="${pageContext.request.contextPath}/view/financemenu.jsp"><span>财务管理</span></a></li>
					<li><a href="${pageContext.request.contextPath}/view/rptMenu.jsp"><span>报表管理</span></a></li>
					<li><a href="${pageContext.request.contextPath}/view/config.jsp"><span>配置管理</span></a></li>
					<li class="selected"><a href="${pageContext.request.contextPath}/view/sysmgrmenu.jsp"><span>系统管理</span></a></li>
				</ul>
			</div>
	
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>
				<%@ include file="tradingmenu.jsp" %>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
				<div id="innercontainer" class="navTab-panel tabsPageContent layoutBox" style="overflow: visible;">
					<div class="page unitBox">

					</div>
				</div>
			</div>
		</div>

	</div>

	<div id="footer">版权所有  区块云（上海）金融信息服务有限公司 Copyright &copy; 2007-2014 <a href="view/systemResearchPersonnelInfo.html" target="dialog"> 区块云技术团队</a> 沪ICP备13024152号-4</div>

</body>
</html>
