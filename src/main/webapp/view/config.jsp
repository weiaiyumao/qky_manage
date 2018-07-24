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
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<li><a href="javascript:void(0)">配置管理</a>
					<ul>
						<li><mgr:menu
								href="${pageContext.request.contextPath }/PayBank/getPayBankList.htm"
								rel="payBankmgr" value="连连支持银行管理" target="navTab"></mgr:menu></li>
						<li><mgr:menu
								href="${pageContext.request.contextPath }/ruleAssetapr/getRuleAssetaprList.htm"
								rel="assetaprmgr" value="资产收益率规则管理" target="navTab"></mgr:menu></li>	
						<li><mgr:menu
								href="${pageContext.request.contextPath }/law/getLawList.htm"
								rel="lawmgr" value="合同管理" target="navTab"></mgr:menu></li>					
			</ul>
		</div>
	</div>

</body>
</html>