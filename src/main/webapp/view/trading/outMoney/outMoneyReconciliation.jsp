<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% request.setAttribute("OUTMONEY_FLAG", DictionaryMap.OUTMONEY_FLAG); %>
<script>
function navTabAjaxDone(json){
  	 DWZ.ajaxDone(json);
	 if (json.statusCode == DWZ.statusCode.ok){
		 //列表页面重新加载数据
         navTab.reload(json.forwardUrl);  
       if ("closeCurrent" == json.callbackType) {
             setTimeout(function(){navTab.closeCurrentTab();}, 100);
       } else if ("forward" == json.callbackType) {
             navTab.reload(json.forwardUrl);
       }
 	}
} 
</script>
<div class="pageContent">
	<form name="form1" method="post"
		action="${ctx}/outMoney/getOutMoneyList.htm"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<h2 class="contentTitle">提现对账信息</h2>
			<input name="id" type="hidden" size="30"
				value="${dataMessage.data['id']}" />
			<dt style="background-color: #b8d0d6;">转账提现对账操作</dt>
			<table class="table" width="100%">
				<thead>
					<tr>
						<th width="5%">订单编号</th>
						<th width="10%">订单状态</th>
						
						<th width="10%">银通订单号</th>
						<th width="8%">订单金额</th>
						<th width="8%">连连交易状态</th>
						<th width="10%">商户业务编号</th>
						<th width="10%">操作</th>
					</tr>
				</thead>
				<tbody>
						<tr>
							<td>${dataMessage.data['orderNo']}</td>
							<td>${dataMessage.data['status']==1?'成功':dataMessage.data['status']==2?'失败':'其他'}</td>
							<td>${dataMessage.data['jetcoOrderid']}</td>
							<td>${dataMessage.data['orderMoney']}</td>
							<td>${dataMessage.data['tradingStatus']==0?'交易成功':'交易失败'}</td>
							<td>${dataMessage.data['merchantBusinessNo']}</td>
							<td>
								<c:if test="${dataMessage.data['tradingStatus']==0 && dataMessage.data['status']!=1}">
									<a title="确定修复吗?" target="ajaxTodo" class="button" href="${ctx}/outMoney/updateOutMoneyStatus.htm?orderNo=${dataMessage.data['orderNo']}&status=1&id=${dataMessage.data['id']}">
									<span>修复</span>
									</a>
								</c:if>
								<c:if test="${dataMessage.data['tradingStatus']==1 && dataMessage.data['status']!=2}">
									<a title="确定修复吗?" target="ajaxTodo" class="button" href="${ctx}/outMoney/updateOutMoneyStatus.htm?orderNo=${dataMessage.data['orderNo']}&status=2&id=${dataMessage.data['id']}">
									<span>修复</span>
									</a>
								</c:if>
								<c:if test="${dataMessage.data['tradingStatus']==0 && dataMessage.data['status']==1}">
								不需要修复
								</c:if>
								<c:if test="${dataMessage.data['tradingStatus']==1 && dataMessage.data['status']==2}">
								不需要修复
								</c:if>
							</td>
						</tr>
				</tbody>
			</table>
			<input id="isToAddP" type="hidden" value="求你别动">
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">返回</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>