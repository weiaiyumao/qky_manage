<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ taglib prefix="mgr" uri="http://rrdai.com/mgr-tags"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>交易管理菜单</title>
</head>
<body>
	<div class="accordion" fillSpace="sidebar">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>功能列表
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<li><a href="javascript:void(0)">财务管理</a>
					<ul>
						<li><mgr:menu
								href="${pageContext.request.contextPath }/productOrder/getProductOrderList.htm"
								rel="productOrdermgr" value="理财投资管理" target="navTab"></mgr:menu></li>
						<li><mgr:menu
								href="${pageContext.request.contextPath }/outMoney/getOutMoneyList.htm"
								rel="outmoneymgr" value="提现管理" target="navTab"></mgr:menu></li>
						<li><mgr:menu
								href="${pageContext.request.contextPath }/rewards/getRewardsList.htm"
								rel="rewardsmgr" value="活动奖励收益管理" target="navTab"></mgr:menu></li>	
						<li><mgr:menu
								href="${pageContext.request.contextPath }/companyOrder/getCompanyOrderList.htm"
								rel="companyOrdermgr" value="公司充值管理" target="navTab"></mgr:menu></li>																	
						<li><mgr:menu
								href="${pageContext.request.contextPath }/company/getCompanyOutmoneyBankList.htm"
								rel="companyOutmoneyBankmgr" value="公司取现银行卡管理" target="navTab"></mgr:menu></li>		
						<li><mgr:menu
								href="${pageContext.request.contextPath }/companyOutMoney/getCompanyOutMoneyList.htm"
								rel="companyOutmoneymgr" value="公司取现管理" target="navTab"></mgr:menu></li>																		
					</ul></li>

			</ul>
		</div>
	</div>

</body>
</html>