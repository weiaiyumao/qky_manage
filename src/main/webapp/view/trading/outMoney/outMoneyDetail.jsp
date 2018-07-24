<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp"%>
<% request.setAttribute("OUTMONEY_FLAG", DictionaryMap.OUTMONEY_FLAG); %>
<div class="pageContent">
	<form name="form1" method="post"
		action="${ctx}/outMoney/getOutMoneyList.htm"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<h2 class="contentTitle">提现详情信息</h2>
			<input name="id" type="hidden" size="30"
				value="${dataMessage.data['id']}" />
			<dl class="nowrap">
				<dt>客户名称：</dt>
				<dd>
					<input class=" textInput" name="title" type="text"
						value="${customerInfo.data['name']}" disabled="disabled" />
				</dd>
				</dl>
				<dl class="nowrap">
				<dt>取现金额：</dt>
				<dd>
					<input id="money" class=" number textInput" name="money"
						type="text" size="30" value="${dataMessage.data['money'] }"
						disabled="disabled" />
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>提现手续费：</dt>
				<dd>
					<input id="money" class=" number textInput" name="money"
						type="text" size="30" value="${dataMessage.data['fee'] }"
						disabled="disabled" />
				</dd>
				</dl>
				<dl class="nowrap">
				<dt>手续费用支付方：</dt>
				<dd>
					<input id="money" class=" number textInput" name="money"
						type="text" size="30" value="${dataMessage.data['feeProvider']==0?'客户':'华融道' }"
						disabled="disabled" />
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>处理状态：</dt>
					<dd>
						<lable>${OUTMONEY_FLAG[dataMessage.data['status']] }</lable>
				</dd>
			</dl>
			<div class="divider"></div>
			<dl class="nowrap">
				<dt>手机号码：</dt>
				<dd>
					<input class=" textInput" name="title" type="text"
						value="${customerInfo.data['mobile']}" disabled="disabled" />
				</dd>
				</dl>
				<dl class="nowrap">
				<dt>身份证 ：</dt>
				<dd>
					<input class=" textInput" name="title" type="text"
						value="${customerInfo.data['idcard']}" disabled="disabled" />
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>银行：</dt>
				<dd>
					<input id="lower" name="lower" type="text" size="30" class="number"
						value="${bankMap[dataMessage.data['bank']] }" disabled="disabled" />
				</dd>
				</dl>
				<dl class="nowrap">
				<dt>银行卡号：</dt>
				<dd>
					<input id="ceiling" name="ceiling" type="text" size="30"
						class="number textInput" value="${dataMessage.data['cardid'] }"
						disabled="disabled" />
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>描述：</dt>
				<dd>
					<textarea name="note" class="textInput" cols="95" rows="4"
						disabled="disabled">${dataMessage.data['note'] }</textarea>
				</dd>
			</dl>
			<div class="divider"></div>
			<dt style="background-color: #b8d0d6;">转账提现订单</dt>
			<table class="table" width="100%">
				<thead>
					<tr>
						<th width="5%" style="border-top: 1px #d0d0d0 solid;">编号</th>
						<th width="10%" style="border-top: 1px #d0d0d0 solid;">转出提现金额</th>
						<th width="12%" style="border-top: 1px #d0d0d0 solid;">类型</th>
						<th width="10%" style="border-top: 1px #d0d0d0 solid;">状态</th>
						<th width="10%" style="border-top: 1px #d0d0d0 solid;">订单生成时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="vMap" items="${dataList.contentList}">
						<tr>
							<td>${vMap['id']}</td>
							<td>${vMap['money']}</td>
							<td>
							<c:if test="${vMap['type']==0}">其他</c:if>
							<c:if test="${vMap['type']==1}"><a href="${ctx}/productOrder/forwardInfo.htm?id=${vMap['investProductOrderId']}" target='navTab' postType='string' style='color: red;'>本金(${vMap['orderId']})</a></c:if>
							<c:if test="${vMap['type']==2}">利息</c:if>
							<c:if test="${vMap['type']==3}">体验本金利息</c:if>
							<c:if test="${vMap['type']==4}">现金奖励</c:if>
							</td>
							<td>${vMap['status']==0?'待处理':vMap['status']==1?'处理成功':vMap['status']==2?'处理失败':'处理中'}</td>
							<td>${vMap['timeCreate']}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="divider"></div>
			<dt style="background-color: #b8d0d6;">转账提现操作流程</dt>
			<table class="table" width="100%">
				<thead>
					<tr>
						<th width="10%" style="border-top: 1px #d0d0d0 solid;">操作用户</th>
						<th width="10%" style="border-top: 1px #d0d0d0 solid;">操作用户名称</th>
						<th width="10%" style="border-top: 1px #d0d0d0 solid;">操作用户类型</th>
						<th width="8%" style="border-top: 1px #d0d0d0 solid;">操作内容</th>
						<th width="10%" style="border-top: 1px #d0d0d0 solid;">操作时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="flowMap" items="${operationFlowDataMessage.contentList}">
						<tr>
							<td>${flowMap['customerId']=='null'?"":flowMap['customerId']}</td>
							<td>${flowMap['customerName']=='null'?"":flowMap['customerName']}</td>
							<td>${flowMap['customerType']==1?'客户':flowMap['customerType']==2?'管理员':'系统(自动)'}</td>
							<td>${flowMap['remark']}</td>
							<td>${flowMap['requestTime']}</td>
						</tr>
					</c:forEach>
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