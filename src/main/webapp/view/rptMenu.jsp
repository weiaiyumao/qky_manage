<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ taglib prefix="mgr" uri="http://rrdai.com/mgr-tags"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表管理菜单</title>
</head>
<body>
	<div class="accordion" fillSpace="sidebar">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>功能列表
			</h2>
		</div>
		<div  class="accordionContent">
			<ul class="tree treeFolder">
				<li><a href="javascript:void(0)">报表管理</a>
					<ul>
						<li><a href="javascript:void(0)">投资报表</a>
								<ul>
									<li><mgr:menu
										href="${pageContext.request.contextPath }/orderReport/orderReportDay.htm"
										rel="orderReportDayMgr" value="投资报表-天" target="navTab"></mgr:menu></li>
									<li><mgr:menu
										href="${pageContext.request.contextPath }/orderReport/orderReportMonth.htm"
										rel="orderReportMonthMgr" value="投资报表-月" target="navTab"></mgr:menu></li>	
									<li><mgr:menu
										href="${pageContext.request.contextPath }/companyOrderReport/orderReportMonth.htm"
										rel="companyOrderReportMonthMgr" value="公司充值报表-月" target="navTab"></mgr:menu></li>		
								</ul>		
						</li>
							<li><a href="javascript:void(0)">提现报表</a>
								<ul>
									<li><mgr:menu
										href="${pageContext.request.contextPath }/outMoneyOrderReport/outMoneyOrderReportDay.htm"
										rel="outMoneyOrderReportDayMgr" value="提现报表-天" target="navTab"></mgr:menu></li>
									<li><mgr:menu
										href="${pageContext.request.contextPath }/outMoneyOrderReport/outMoneyOrderReportMonth.htm"
										rel="outMoneyOrderReportMonthMgr" value="提现报表-月" target="navTab"></mgr:menu></li>	
									<li><mgr:menu
										href="${pageContext.request.contextPath }/companyOutMoneyOrderReport/outMoneyOrderReportMonth.htm"
										rel="companyOutMoneyOrderReportMonthMgr" value="公司提现报表-月" target="navTab"></mgr:menu></li>
								</ul>		
						</li>
			</ul>
		</div>
	</div>

</body>
</html>