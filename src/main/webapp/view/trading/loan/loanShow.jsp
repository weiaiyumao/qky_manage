<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="${pageContext.request.contextPath}/view/include/context.jsp" />
<script src="${ctx}/resources/js/trading/loan/loan.js" type="text/javascript"></script>
<div class="pageContent">
<%-- 	<form name="form1" method="post" action="${pageContext.request.contextPath }/loan/updateLoan.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);"> --%>
		<input type="hidden" id="id" name="id" value="${dataMessage['id']}"/>
		<div class="pageFormContent nowrap" layoutH="56">
			<p>
				<label>关联客户ID：</label>
				<input id="customerId" class="required" name="customerId" type="text" size="30" value="${dataMessage['customerId']}" />
			</p>
			<p>	
				<label>标题：</label>
				<input id="title" class="required" name="title" type="text" size="30" value="${dataMessage['title']}"/>
			</p>
			<p>
				<label>还款方式：</label>
				<select id="methodtype" class="required" name="methodtype" value="${dataMessage['methodtype']}">
					<option value="1">等额本息</option>
					<option value="2">先息后本</option>
					<option value="3">到期本息</option>
				</select>
			</p>
			<p>	
				<label>还款周期：</label>
				<select id="cycle" class="required" name="cycle" value="${dataMessage['cycle']}">
					<option value="1">月</option>
					<option value="2">季度</option>
					<option value="3">年</option>
					<option value="4">到期还本息</option>
				</select>
			</p>
			<p>
				<label>是否担保：</label>
				<select id="assure" class="required" name="assure" value="${dataMessage['assure']}">
					<option value="1">是</option>
					<option value="2">否</option>
				</select>
			</p>
			<p>	
				<label>借款保障类型：</label>
				<select id="safeType" class="required" name="safeType" value="${dataMessage['safeType']}">
					<option value="1">保本</option>
					<option value="2">保本息</option>
					<option value="3">保本保收益</option>
				</select>
			</p>
			<p>
				<label>信用值：</label>
				<input id="creditValue"  name="creditValue" type="text" size="30" value="${dataMessage['creditValue']=='null'?'':dataMessage['creditValue']}" />
			</p>
			<p>	
				<label>是否投标限制：</label>
				<select id="isRestrict" class="required" name="isRestrict" value="${dataMessage['isRestrict']}" >
					<option value="1">是</option>
					<option value="2">否</option>
				</select>
			</p>
			<p>
				<label>是否投保：</label>
				<select id="insure" class="required" name="insure" value="${dataMessage['insure']}" >
					<option value="1">是</option>
					<option value="2">否</option>
				</select>
			</p>
			<p>	
				<label>发标时间：</label>
				<input id="sendTime" class="date textInput readonly valid" datefmt="yyyy-MM-dd HH:mm:ss" 
					name="sendTime" size="30" type="text" readonly="true" value="${dataMessage['sendTime']}" />
				<a href="javascript:;" class="inputDateButton">选择</a>
			</p>
			<p>
				<label>截止时间：</label>
				<input id="lasttime" class="date textInput readonly valid" datefmt="yyyy-MM-dd HH:mm:ss" name="lasttime" type="text" size="30" readonly="true" value="${dataMessage['lasttime']}" />
				<a href="javascript:;" class="inputDateButton">选择</a>	
			</p>
<!-- 			<p>	 -->
<!-- 				<label>发放贷款日：</label> -->
<%-- 				<input id="sendMoneyDate" class="date textInput readonly valid" datefmt="yyyy-MM-dd HH:mm:ss" name="sendMoneyDate" type="text" size="30" readonly="true" value="${dataMessage['sendMoneyDate']}" /> --%>
<!-- 				<a href="javascript:;" class="inputDateButton">选择</a> -->
<!-- 			</p> -->
			<p>
				<label>借款期限：</label>
				<input id="month" class="required" name="month" type="text" size="30" value="${dataMessage['month']}" />
			</p>
			<p>	
				<label>金额：</label>
				<input id="money" class="required" name="money" type="text" size="30" value="<fmt:formatNumber value="${dataMessage['money']}" pattern="#.00"/>"/>
			</p>
			<p>
				<label>年利率：</label>
				<input id="apr" class="required" name="apr" type="text" size="30" value="${dataMessage['apr']}" />
			</p>
			<p>	
				<label>投标下限：</label>
				<input id="lower" class="required" name="lower" type="text" size="30"  value="${dataMessage['lower']}" />
			</p>
			<p>
				<label>投标上限：</label>
				<input id="ceiling" class="required" name="ceiling" type="text" size="30"  value="${dataMessage['ceiling']}" />
			</p>
			<p>	
				<label>服务费率：</label>z
				<input id="serviceRate" name="serviceRate" type="text" size="30" value="${dataMessage['serviceRate']=='null'?'':dataMessage['serviceRate']}" />
			</p>
			<p>
				<label>管理费率：</label>
				
				<input id="manageRate" name="manageRate" type="text" size="30" value="${dataMessage['manageRate']=='null'?'':dataMessage['manageRate']}" />
			</p>
		</div>
<!-- 	</form> -->
</div>