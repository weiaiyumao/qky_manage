<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp"%>
<% request.setAttribute("OUTMONEY_FLAG", DictionaryMap.OUTMONEY_FLAG); %>
<script>
function auditDo(){
	var data={
			"id":$("#id").val(),
			"value":"3"
	}
	$.ajax({
		type:"POST",
		dataType:"json",
		url: ctx +"/outMoney/auditOutMoney.htm",
		data:data,
		success:function(response){debugger;
		 alertMsg.correct(response.message);
				navTab.reload(response.forwardUrl); 
		}
	});
}
function auditNotDo(){
	var data={
			"id":$("#id").val(),
			"value":"2"
	}
	$.ajax({
		type:"POST",
		dataType:"json",
		url: ctx +"/outMoney/auditOutMoney.htm",
		data:data,
		success:function(response){
			alertMsg.correct(response.message);
				navTab.reload(response.forwardUrl); 
		}
	});
}
</script>
<div class="pageContent">
	<form name="form1" method="post"
		action="${ctx}/outMoney/getOutMoneyList.htm"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<h2 class="contentTitle">提现详情信息</h2>
			<input name="id" type="hidden" id="id"
				value="${dataMessage.data['id']}" />
			<dl class="nowrap">
				<dt>客户名称：</dt>
				<dd>
					<input class=" textInput" name="title" type="text"
						value="${customerInfo.data['name']}" disabled="disabled" />
				</dd>
				<dt>已购买总金额：</dt>
				<dd>
					<input class=" textInput" name="title" type="text"
						value="${buySumMoney}" disabled="disabled" />
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>取现金额：</dt>
				<dd>
					<input id="money" class=" number textInput" name="money"
						type="text" size="30" value="${dataMessage.data['money'] }"
						disabled="disabled" />
				</dd>
				
				<dt>可提现总金额：</dt>
				<dd>
					<input class=" textInput" name="title" type="text"
						value="${sumMoney.data['sumMoney']}" disabled="disabled" />
				</dd>
				
			</dl>
			<dl class="nowrap">
				<dt>提现手续费：</dt>
				<dd>
					<input id="money" class=" number textInput" name="money"
						type="text" size="30" value="${dataMessage.data['fee'] }"
						disabled="disabled" />
				</dd>
				
				<dt>已取现总金额：</dt>
				<dd style="width: 500px">
					<input id="money" class=" number textInput" name="money"
						type="text" size="30" value="${usedMoney }"
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
				<dt>
			</dl>
					<c:if test="${isWarning}">
						<span style=" padding-left: 40%;"><img alt="" src="${ctx}/resources/images/warning.png" style="width:30px"></span></br>
						<span><font style="color: red;font-size: 25px;padding-left: 30%;">取现金额异常，请速与技术人员联系，谢谢！</font></span>
					</c:if>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent" style="margin-right: 20px;">
							<button type="button" class="close" onclick="auditDo()">审核通过</button>
						</div>
						<div class="button">
							<div class="buttonContent" style="margin-right: 20px;">
								<button type="button" class="close"  onclick="auditNotDo()">审核不通过</button>
							</div>
						</div>
						<div class="button">
							<div class="buttonContent" style="margin-right: 20px;">
								<button type="button" class="close">返回</button>
							</div>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>