<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="${pageContext.request.contextPath}/resources/js/trading/loan/loan.js" type="text/javascript"></script>
<div class="pageContent">
	<form name="form1" method="post" action="${pageContext.request.contextPath }/loan/updateByStatus.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<input type="hidden" id="id" name="id" value="${dataMessage['id']}"/>
		<div class="pageFormContent nowrap" layoutH="56">
			<p>
				<label>关联客户ID：</label>
				<input id="customerId" disabled="disabled" name="customerId" type="text" size="30" value="${dataMessage['customerId']}" />
			</p>
			<p>	
				<label>标题：</label>
				<input id="title" disabled="disabled" name="title" type="text" size="30" value="${dataMessage['title']}"/>
			</p>
			<p>
				<label>还款方式：</label>
				<select id="methodtype" disabled="disabled" name="methodtype" value="${dataMessage['method']}">
					<option value="1">等额本息</option>
					<option value="2">先息后本</option>
					<option value="3">到期本息</option>
					
				</select>
			</p>
			<p>	
				<label>还款周期：</label>
				<select id="cycle" disabled="disabled" name="cycle" value="${dataMessage['cycle']}">
					<option value="1">月</option>
					<option value="2">季度</option>
					<option value="3">年</option>
					<option value="4">到期还本息</option>
				</select>
			</p>
			<p>		
				<label>发标时间：</label>
				<input id="sendTime" disabled="disabled"  class="date textInput readonly valid" datefmt="yyyy-MM-dd HH:mm:ss" name="sendTime" size="30" type="text" value="${dataMessage['sendTime']}"/>
				<a href="javascript:;" class="inputDateButton">选择</a>
			</p>
			<p>
				<label>截止时间：</label>
				<input id="lasttime" disabled="disabled" class="date textInput readonly valid" datefmt="yyyy-MM-dd HH:mm:ss" name="lasttime" type="text" size="30" value="${dataMessage['lasttime']}"/>
				<a href="javascript:;" class="inputDateButton">选择</a>	
				
			</p>
			<p>
				<label>是否担保：</label>
				<select id="assure" disabled="disabled" name="assure" value="${dataMessage['assure']}">
					<option value="1">是</option>
					<option value="2">否</option>
				</select>
			</p>
			<p>	
				<label>借款保障类型：</label>
				<select id="safeType" disabled="disabled" name="safeType" value="${dataMessage['safeType']}">
					<option value="1">保本</option>
					<option value="2">保本息</option>
					<option value="3">保本保收益</option>
				</select>
			</p>
			<p>
				<label>借款期限：</label>
				<input id="month" disabled="disabled" name="month" type="text" size="30" value="${dataMessage['month']}" />
			</p>
			<p>	
				<label>金额：</label>
				<input id="money" disabled="disabled" name="money" type="text" size="30" value="<fmt:formatNumber value="${dataMessage['money']}" pattern="#.00"/>"/>
			</p>
			<p>
				<label>年利率：</label>
				<input id="apr" disabled="disabled" name="apr" type="text" size="30"  value="${dataMessage['apr']}"/>
			</p>
			<p>				
				<label>投标下限：</label>
				<input id="lower" disabled="disabled" name="lower" type="text" size="30"  value="${dataMessage['lower']}"/>
			</p>
			<p>
				<label>投标上限：</label>
				<input id="ceiling" disabled="disabled" name="ceiling" type="text" size="30"  value="${dataMessage['ceiling']}"/>
			</p>
			<p>
				<label>借款状态：</label>
				<select id="loanStatus"  name="loanStatus" value="${dataMessage['loanStatus']}" >
					<option value="1">审核</option>
					<option value="2">投标中</option>
					<option value="3">满标</option>
					<option value="4">还款中</option>
					<option value="5">已还完</option>
					<option value="6">网站垫付否</option>
					<option value="7">坏账</option>
					<option value="8">流标</option>
				</select>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>