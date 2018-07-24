<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../include/include.jsp" %>  
<%@ page import="com.zqfinance.system.util.DictionaryMap" %>
<% request.setAttribute("LOAN_INVESTPRODUCT_ORDER_STATUS", DictionaryMap.LOAN_INVESTPRODUCT_ORDER_STATUS); %>
<div class="pageContent">
	<form name="creditForm" method="post" action="${ctx}/productOrder/marryCreidt.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56" id="PageList">
			<h2 class="contentTitle">订单号（${dataMessage.data['orderId'] }）基本信息</h2>
			<dl class="nowrap">
				<dt>理财产品标题</dt>
				<dd><input name="title" disabled="disabled"  type="text" size="30" value="${dataMessage_product.data['title'] }"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>订单号：</dt>
				<dd><input id="orderId" disabled="disabled"  name="money" type="text" size="30" value="${dataMessage.data['orderId'] }"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>订单金额：</dt>
				<dd><input id="money" disabled="disabled"  name="money" type="text" size="30" value="<fmt:formatNumber groupingUsed="false" value="${dataMessage.data['money'] }" type="number" />"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>订单来源：</dt>
				<dd><input name="paytype" disabled="disabled" type="text" size="30" value="${dataMessage.data['paytype'] }"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>剩余可提现金额：</dt>
				<dd><input name="residualMoney" disabled="disabled" type="text" size="30" value="<fmt:formatNumber type="number" groupingUsed="false" value="${dataMessage.data['residualMoney']}" />"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>未匹配金额：</dt>
				<dd><input name="matchMoney" disabled="disabled" type="text" size="30" value="${dataMessage.data['matchMoney']}"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>交易时间：</dt>
				<dd><input name="orderdate" disabled="disabled" type="text" size="30" value="${dataMessage.data['orderdate'] }"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>交易银行卡号：</dt>
				<dd><input name="cardNo" disabled="disabled" type="text" size="30" value="${dataMessage.data['cardNo'] }"/></dd>
			</dl>
			<h2 class="contentTitle">客户基本信息</h2>
			<dl class="nowrap">
				<dt>名称：</dt>
				<dd>
					<input class=" textInput" name="title" type="text" value="${customerInfo.data['name']}" disabled="disabled" />
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>手机号码：</dt>
				<dd>
					<input class=" textInput" name="mobile" type="text" value="${customerInfo.data['mobile']}" disabled="disabled" />
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>身份证号码：</dt>
				<dd>
					<input class=" textInput" name="idcard" type="text" value="${customerInfo.data['idcard']}" disabled="disabled" />
				</dd>
			</dl>
				<dl class="nowrap">
				<dt>年龄：</dt>
				<dd>
					<input class=" textInput" name="age" type="text" value="${customerInfo.data['age']}" disabled="disabled" />
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>性别：</dt>
				<dd>
					<input class=" textInput" name="gender" type="text" value="${customerInfo.data['gender']=='M'?'男':(customerInfo.data['gender']=='F'?'女':'未知')}" disabled="disabled" />
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>客户等级：</dt>
				<dd>
					<input class=" textInput" name="level" type="text" value="${customerInfo.data['level']=='0'?'普通客户':(customerInfo.data['level']=='1'?'VIP客户':(customerInfo.data['level']=='2'?'代理人':'未知'))}" disabled="disabled" />
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>城市：</dt>
				<dd>
					<input class=" textInput" name="region" type="text" value="${customerInfo.data['region']}" disabled="disabled" />
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>注册时间：</dt>
				<dd>
					<input class=" textInput" name="createdatetime" type="text" value="${customerInfo.data['createdatetime']}" disabled="disabled" />
				</dd>
			</dl>
			<h2 style = "margin-bottom: 10px;line-height: 30px;font-size: 14px;">匹配详情列表</h2>
			<table class="table" width="100%">
				<thead>
					<tr>
					    <th width="10%" style="border-top: 1px #d0d0d0 solid;">匹配标编号</th>
					    <th width="10%" style="border-top: 1px #d0d0d0 solid;">匹配标标题</th>
						<th width="10%" style="border-top: 1px #d0d0d0 solid;">匹配标年利率</th>
						<th width="10%" style="border-top: 1px #d0d0d0 solid;">匹配标期限</th>
						<th width="10%" style="border-top: 1px #d0d0d0 solid;">订单匹配金额</th>
						<th width="10%" style="border-top: 1px #d0d0d0 solid;">匹配时间</th>
						<th width="10%" style="border-top: 1px #d0d0d0 solid;">状态</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${datePageMessage.contentList.size()==0 }">
						<tr>
							<td colspan="6" align="center">尚未给订单匹配任何标</td>
						</tr>
					</c:if>
					<c:forEach var="vMap" items="${datePageMessage.contentList }">
						<tr>
							<td>${vMap['id']}</td>
							<td>${vMap['title']}</td>
							<td>${vMap['apr']}</td>	
							<td>${vMap['terms']}</td>
							<td>${vMap['matchMoney']}</td>
							<td>${vMap['matchTime']}</td>
							<td>${LOAN_INVESTPRODUCT_ORDER_STATUS[vMap['status']]}</td>										
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
			</ul>
		</div>
	</form>
</div>