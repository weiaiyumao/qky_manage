<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp" %>
<div class="pageContent">
	<form name="form1" method="post" action="${ctx}/product/updateProduct.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		<h2 class="contentTitle">理财产品基本详情信息</h2>
			<input name="id" type="hidden" size="30" value="${dataMessage.data['id'] }"/>
			<dl class="nowrap">
				<dt>标题：</dt>
				<dd>${dataMessage.data['title'] }</dd>
			</dl>
			<dl class="nowrap">
				<dt>累计预融资金额：</dt>
				<dd><input id="money" class=" number textInput" name="money" type="text" size="30" value="<fmt:formatNumber value="${dataMessage.data['money'] }" pattern="#.00"/>" disabled="disabled"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>理财已投金额：</dt>
				<dd><input id="money" class=" number textInput" name="money" type="text" size="30" value="<fmt:formatNumber value="${dataMessage_money.data['money']}" pattern="#.00"/>" disabled="disabled"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>投标下限：</dt>
				<dd><input id="lower"  name="lower" type="text" size="30" class="number" value="<fmt:formatNumber value="${dataMessage.data['lower'] }" pattern="#.00"/>" disabled="disabled"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>投标上限：</dt>
				<dd><input id="ceiling"  name="ceiling" type="text" size="30" class="number textInput" value="<fmt:formatNumber value="${dataMessage.data['ceiling'] }" pattern="#.00"/>" disabled="disabled"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>合同：</dt>
				<dd>
					<select class="combox required" id="lawId"  name="lawId" disabled="disabled">
						<option value="">请选择合同</option>
						<c:forEach var="law" items="${law_dataMessage.contentList }">
							<option value="${law.id }" <c:if test="${law.id == dataMessage.data['lawid'] }">selected</c:if> >${law.title }</option>
						</c:forEach>
					</select>	
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>描述：</dt>
				<dd><textarea name="note" class="textInput" cols="95" rows="4" disabled="disabled">${dataMessage.data['note'] }</textarea></dd>
			</dl>
			<dl class="nowrap">
				<c:if test="${dataMessage_apr.contentList.size()<=0}">
					<dd><lable>&nbsp;&nbsp;未设定利率规则</lable></dd>
				</c:if>
				<c:if test="${dataMessage_apr.contentList.size()>0}">
					<dd><lable>&nbsp;&nbsp;年利率规则：(%)</lable></dd>
				</c:if>
			</dl>
			<c:forEach var = "vMap" items="${dataMessage_apr.contentList}" varStatus="number">
				<p>
					<label>第${vMap['month']==1?'一':(vMap['month']==2?'二':(vMap['month']==3?'三':(vMap['month']==4?'四':(vMap['month']==5?'五':(vMap['month']==6?'六':(vMap['month']==7?'七':(vMap['month']==8?'八':(vMap['month']==9?'九':(vMap['month']==10?'十':(vMap['month']==11?'十一':(vMap['month']==12?'十二':vMap['month'])))))))))))}月：</label>
					<input id="apr${number.count}"  name="apr${number.count}" type="text" size="30" class="number" readonly="readonly" value="${vMap['apr']}"/>
				</p>
			</c:forEach>
			<input id="isToAddP" type="hidden" value="求你别动">
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>