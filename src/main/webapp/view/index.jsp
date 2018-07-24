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
<% request.setAttribute("OUTMONEY_FLAG", DictionaryMap.OUTMONEY_FLAG); %>
<% request.setAttribute("OUTMONEY_FEEPROVIDER", DictionaryMap.OUTMONEY_FEEPROVIDER);%>
<% request.setAttribute("BANK_MAP", DictionaryMap.BANK_MAP);%>
<head>
<style type="text/css">
	#header{height:85px}
	#leftside, #container, #splitBar, #splitBarProxy{top:90px}
	.fontClass{ color: green;}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>华融道管理后台</title>


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
					<li class="selected"><a href="${pageContext.request.contextPath}/view/tradingmenu.jsp"><span>交易管理</span></a></li>								
					<li><a href="${pageContext.request.contextPath}/view/financemenu.jsp"><span>财务管理</span></a></li>
					<li><a href="${pageContext.request.contextPath}/view/rptMenu.jsp"><span>报表管理</span></a></li>
					<li><a href="${pageContext.request.contextPath}/view/config.jsp"><span>配置管理</span></a></li>
					<li><a href="${pageContext.request.contextPath}/view/sysmgrmenu.jsp"><span>系统管理</span></a></li>
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
					<c:if test="${todo == 'yes'}">
						<h3 style="line-height: 30px;padding-left: 30px; margin-top:10px;">待处理事项：</h3>
					</c:if>
					<c:if test="${showSymoney == 'yes'}">
						<p style="line-height: 30px;padding-left: 30px;">当前理财产品《月利宝》剩余可投资金额为：<span <c:if test="${symoney <= 300000}">style='color: red;'</c:if> >${symoney/10000}万</span><c:if test="${symoney <= 300000}">，请添加可投资金额！</c:if></p>
					</c:if>
					<c:if test="${showLoanSyMoney == 'yes' }">
						<p style="line-height: 30px;padding-left: 30px;">当前可供理财产品匹配的贷款标剩余可匹配金额为：<span <c:if test="${loanSyMoney <= 300000}">style='color: red;'</c:if> >${loanSyMoney/10000}万</span><c:if test="${symoney <= 300000}"><span style="margin-left: 20px;"><a href="${ctx}/loan/forwordLoan.htm" target="navTab" title="发标" style="color: blue;">立即处理</a></span></c:if></p>
					</c:if>
					<c:if test="${showOutMoney == 'yes' }">
						<p style="line-height: 30px;padding-left: 30px;">预计今天公司需要充值金额为：<span style="color: red;">${outMoney}</span>元，不包含手续费！（规则：统计昨日和前天客户所有的提现申请金额）</p>
					</c:if>
					<c:if test="${showCompanyOutMoney == 'yes' }">
						<p style="line-height: 30px;padding-left: 30px;">预计今天公司可以提现金额为：<span style="color: red;">${companyOutMoney}</span>元（规则：统计客户昨日成功提现后剩余未取现金额+本月已经取现客户昨日购买订单金额）<span style="color: red;">具体可以提现金额多少，需根据公司账户余额来决定！此数据仅供参考！</span></p>
					</c:if>
					<c:if test="${showOutMoneyToAuditListTopfive =='yes'}">
						<p style="line-height: 30px;padding-left: 30px;color: red;">客户提现待处理，共${outMoneyToAuditListTopfive.page.totalCount}条<span style="margin-left: 20px;"><a href="${pageContext.request.contextPath }/outMoney/getOutMoneyList.htm?flag=0" rel="outmoneymgr" target="navTab" target="navTab" title="提现管理" style="color: blue;">立即处理</a></span></p>
						<table class="table" width="100%">
							<thead>
								<tr>
								    <th width="10%" style="border-top: 1px #d0d0d0 solid;">订单号</th>
									<th width="12%" style="border-top: 1px #d0d0d0 solid;">取现金额(RMB)</th>
									<th width="8%" style="border-top: 1px #d0d0d0 solid;">所属银行</th>
									<th width="12%" style="border-top: 1px #d0d0d0 solid;">银行卡号</th>
									<th width="5%" style="border-top: 1px #d0d0d0 solid;">提现状态</th>
									<th width="10%" style="border-top: 1px #d0d0d0 solid;">订单生成时间</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="vMap" items="${outMoneyToAuditListTopfive.contentList }">
									<tr target="id_outMoney" rel="${vMap['id']}">
										<td>${vMap['orderNo']}</td>
										<td>${vMap['money']}</td>
										<td>${BANK_MAP[vMap['bank']]}</td>
										<td>${vMap['cardid']}</td> 
										<td>${OUTMONEY_FLAG[vMap['status']]}</td>					
										<td>${vMap['timeCreate'] }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
					<c:if test="${showOutMoneyToMakeLoansListTopfive == 'yes'}">
						<p style="line-height: 30px;padding-left: 30px;color: red;">客户提现待放款，共${outMoneyToMakeLoansListTopfive.page.totalCount}条<span style="margin-left: 20px;"><a href="${pageContext.request.contextPath }/outMoney/getOutMoneyList.htm?flag=3" rel="outmoneymgr" target="navTab" title="提现管理" style="color: blue;">立即处理</a></span></p>
						<table class="table" width="100%">
							<thead>
								<tr>
								    <th width="10%" style="border-top: 1px #d0d0d0 solid;">订单号</th>
									<th width="12%" style="border-top: 1px #d0d0d0 solid;">取现金额(RMB)</th>
									<th width="8%" style="border-top: 1px #d0d0d0 solid;">所属银行</th>
									<th width="12%" style="border-top: 1px #d0d0d0 solid;">银行卡号</th>
									<th width="5%" style="border-top: 1px #d0d0d0 solid;">提现状态</th>
									<th width="10%" style="border-top: 1px #d0d0d0 solid;">订单生成时间</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="vMap" items="${outMoneyToMakeLoansListTopfive.contentList }">
									<tr target="id_outMoney" rel="${vMap['id']}">
										<td>${vMap['orderNo']}</td>
										<td>${vMap['money']}</td>
										<td>${BANK_MAP[vMap['bank']]}</td>
										<td>${vMap['cardid']}</td> 
										<td>${OUTMONEY_FLAG[vMap['status']]}</td>					
										<td>${vMap['timeCreate'] }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>	
					</c:if>
					</div>
				</div>
			</div>
		</div>

	</div>

	<div id="footer">版权所有 中秋（上海）金融信息服务有限公司 Copyright &copy; 2007-2014 <a href="view/systemResearchPersonnelInfo.html" target="dialog"> 华融道技术团队</a> 沪ICP备13024115号-4</div>

</body>
</html>
