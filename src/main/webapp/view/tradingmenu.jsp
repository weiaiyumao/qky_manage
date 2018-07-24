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
				<li><a href="javascript:void(0)">交易管理</a>
					<ul>
						<li><mgr:menu
								href="${pageContext.request.contextPath }/product/getProductList.htm"
								rel="productmgr" value="理财产品管理" target="navTab"></mgr:menu></li>
						<li><a href="javascript:void(0)">投资管理</a>
								<ul>
									<li><mgr:menu
										href="${pageContext.request.contextPath }/productOrder/getProductOrderList.htm"
										rel="productOrdermgr" value="投资管理" target="navTab"></mgr:menu></li>
									<li><mgr:menu
										href="${pageContext.request.contextPath }/productOrder/getPublicProductList.htm"
										rel="publicProductOrdermgr" value="投资管理" target="navTab"></mgr:menu></li>	
									<li><mgr:menu
										href="${pageContext.request.contextPath }/productOrder/getCanMatchOrderList.htm"
										rel="canMatchOrdermgr" value="可匹配订单管理" target="navTab"></mgr:menu></li>
								</ul>		
						</li>
						<li><mgr:menu
								href="${pageContext.request.contextPath }/loan/getloanList.htm"
								rel="loanmgr" value="贷款管理" target="navTab"></mgr:menu></li>
						<li><mgr:menu
								href="${pageContext.request.contextPath }/experience/getExperienceMoneyList.htm"
								rel="experienceMoneyListmgr" value="用户的体验本金管理" target="navTab"></mgr:menu></li>
						<li><mgr:menu
								href="${pageContext.request.contextPath }/rewards/getRewardsList.htm"
								rel="rewardsmgr" value="活动奖励收益管理" target="navTab"></mgr:menu></li>
						<li><mgr:menu
								href="${pageContext.request.contextPath }/customerlc/getCustomerLCBankList.htm"
								rel="customerLCmgr" value="用户银行卡管理" target="navTab"></mgr:menu></li>		
						<li><mgr:menu
								href="${pageContext.request.contextPath }/outMoney/getOutMoneyList.htm"
								rel="outmoneymgr" value="提现管理" target="navTab"></mgr:menu></li>		
						<li><mgr:menu
								href="${pageContext.request.contextPath }/outMoney/getPublicOutMoneyList.htm"
								rel="publicOutmoneymgr" value="提现管理" target="navTab"></mgr:menu></li>	
						<li><a href="javascript:void(0)">红包管理</a>
								<ul>
									<li><mgr:menu
										href="${pageContext.request.contextPath }/cashgiftIn/getCashgiftIn.htm"
										rel="cashgiftInmgr" value="客户收入红包管理" target="navTab"></mgr:menu></li>
									<li><mgr:menu
										href="${pageContext.request.contextPath }/cashgiftOut/getCashgiftOut.htm"
										rel="cashgiftOutmgr" value="客户支出红包管理" target="navTab"></mgr:menu></li>	
								</ul>		
						</li>																
					</ul></li>

			</ul>
		</div>
	</div>

</body>
</html>