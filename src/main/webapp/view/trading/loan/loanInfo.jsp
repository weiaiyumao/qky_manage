<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp" %>
<script src="${ctx}/resources/js/trading/loan/loan.js" type="text/javascript"></script>
<div class="pageContent">
	<form name="form1" method="post" action="${ctx}/loan/updateLoan.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		<h2 class="contentTitle">借款标基本信息</h2>
			<dl class="nowrap">
				<dt>借款人名称：</dt>
				<dd><input  class="required" name="customerName" type="text" size="30" maxlength="20" disabled="disabled" value="${resultmap['customerName'] }" /></dd>
			</dl>
			<dl class="nowrap">
				<dt>手机号码：</dt>
				<dd><input  class="required" name="customerMobile" type="text" size="30" maxlength="11" disabled="disabled" value="${resultmap['customerMobile'] }"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>身份证：</dt>
				<dd><input  class="required" name="customerIdcard" type="text" size="30" maxlength="18" disabled="disabled"  value="${resultmap['customerIdcard'] }" /></dd>
			</dl>
			<dl class="nowrap">
				<dt>标题：</dt>
				<dd><input id="title" name="title" type="text" size="30"  alt="请输入借款标题" value="${resultmap['title'] }" disabled="disabled"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>借款金额：</dt>
				<dd><input id="money" name="money" type="text" size="30"  alt="请输入金额" onblur="checkMoney('money')" disabled="disabled" value="<fmt:formatNumber value="${resultmap['money'] }" pattern="#.00"/>"  /></dd>
			</dl>
			<dl class="nawrap">
				<dt>剩余可匹配金额</dt>
				<dd>
				<c:choose>
				   <c:when test="${matchMoney<=0}">  
				   	  <input id="matchMoney" name="matchMoney" type="text" size="30" disabled="disabled" value="<fmt:formatNumber value="${matchMoney}" pattern="#" type="number"/>.00" style="color: red;"/>
				   </c:when>
				   <c:otherwise> 
				   	  <input id="matchMoney" name="matchMoney" type="text" size="30" disabled="disabled" value="<fmt:formatNumber value="${matchMoney}" pattern="#.00"/>" style="color: red;"/>
				   </c:otherwise>
				</c:choose>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>贷款期限类型：</dt>
				<dd>
					<select class="combox" id="termType" name="termType">
						<option value="">请选择期限类型</option>
						<c:forEach var="termsType" items="<%=DictionaryMap.TERM_TYPE%>">
							<option value="${termsType.key }"
								<c:if test="${termsType.key ==resultmap['termType']  }">selected</c:if>>${termsType.value }</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>借款期限：</dt>
				<dd><input id="terms" class=" digits" name="terms" type="text" size="30" value="${resultmap['terms']}" disabled="disabled" alt="请输入借款期限"/></dd>
			</dl>
			<dl class="nowrap">
					<dt>年利率：</dt>
					<dd><input id="apr" class=" number" name="apr" type="text" size="30"  alt="请输入年利率" maxlength="6" disabled="disabled" value="${resultmap['apr']}"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>还款方式：</dt>
				<dd>
					<select id="methodtype" class="combox " name="methodtype">
						<option value="">请选择还款方式</option>
						<c:forEach var="loanMethod"
							items="<%=DictionaryMap.LOAN_METHOD%>">
							<option value="${loanMethod.key }"
								<c:if test="${loanMethod.key ==resultmap['method']  }">selected</c:if>>${loanMethod.value }</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>是否担保：</dt>
				<dd>
					<select id="assure" class="combox" name="assure">
						<option value="">选择是否担保</option>
						<c:forEach var="loanAssure" items="<%=DictionaryMap.YESORNO%>">
							<option value="${loanAssure.key }"
								<c:if test="${loanAssure.key ==resultmap['assure']  }">selected</c:if>>${loanAssure.value }</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>借款保障类型：</dt>
				<dd>
					<select id="safeType" class="combox" name="safeType">
						<option value="">选择借款保障类型</option>
						<c:forEach var="safeType"
							items="<%=DictionaryMap.LOAN_SAFETYPE%>">
							<option value="${safeType.key}"
								<c:if test="${safeType.key ==resultmap['safeType']  }">selected</c:if>>${safeType.value}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl class="nowrap">
					<dt>借款人信用值：</dt>
					<dd><input id="creditValue"  name="creditValue" type="text" size="30" disabled="disabled" value="${resultmap['creditValue']}" /></dd>
			</dl>	
			<dl class="nowrap">
				<dt>是否投保：</dt>
				<dd>
					<select id="insure" class="combox" name="insure">
						<option value="">选择是否投保</option>
						<c:forEach var="yesOrNo" items="<%=DictionaryMap.YESORNO%>">
							<option value="${yesOrNo.key }"
								<c:if test="${yesOrNo.key ==resultmap['insure']  }">selected</c:if>>${yesOrNo.value}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>发标时间：</dt>
				<dd>
					<input id="sendTime" class="date textInput readonly valid"
						value="${resultmap['sendTime']}" dateFmt="yyyy-MM-dd HH:mm:ss"
						name="sendTime" size="30" type="text" readonly="true" /> <a
						href="javascript:;" class="inputDateButton">选择</a>
				</dd>
			</dl>
<!-- 			<dl class="nowrap"> -->
<!-- 				<dt>截止时间：</dt> -->
<!-- 				<dd> -->
<!-- 					<input id="lasttime" class="date textInput readonly valid" -->
<%-- 						value="${resultmap['lasttime']}" dateFmt="yyyy-MM-dd HH:mm:ss" --%>
<!-- 						name="lasttime" type="text" size="30" readonly="true" /> <a -->
<!-- 						href="javascript:;" class="inputDateButton">选择</a> -->
<!-- 				</dd> -->
<!-- 			</dl> -->
			<dl class="nowrap">
				<dt>是否投标限制：</dt>
				<dd>
					<select id="isRestrict" class="combox" name="isRestrict">
						<option value="">选择是否限制投标</option>
						<c:forEach var="yesOrNo" items="<%=DictionaryMap.YESORNO%>">
							<option value="${yesOrNo.key }"
								<c:if test="${yesOrNo.key ==resultmap['isRestrict']  }">selected</c:if>>${yesOrNo.value}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>投标下限：</dt>
				<dd><input id="lower"  name="lower" type="text" size="30" disabled="disabled" value="${resultmap['lower']}" class="number" /></dd>
			</dl>
			<dl class="nowrap">
				<dt>投标上限：</dt>
				<dd><input id="ceiling"  name="ceiling" type="text" size="30" disabled="disabled" value="${resultmap['ceiling']}" class="number" /></dd>
			</dl>
			<dl class="nowrap">
				<dt>行登记证明编号：</dt>
				<dd><input id="certificatesNumber" name="certificatesNumber" type="text" size="30" disabled="disabled" value="${resultmap['certificatesNumber']}" maxlength="30"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>租凭机构：</dt>
				<dd><input  class="" id="rentalagency" name="rentalagency" type="text" size="30" maxlength="30" disabled="disabled" value="${resultmap['rentalagency']}"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>合同：</dt>
				<dd>
					<select class="combox " id="lawId"  name="lawId" >
						<option value="">请选择合同</option>
						<c:forEach var="law" items="${law_dataMessage.contentList }">
							<option value="${law.id }" <c:if test="${law.id == resultmap['lawId'] }">selected</c:if> >${law.title }</option>
						</c:forEach>
					</select>	
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>贷款标类型：</dt>
				<dd>
					<select id="loantype" class="combox" name="loantype" onchange="onLoantype('loantype')">
					<option value="">选择贷款标类型</option>
					<c:forEach var="loantype" items="<%=DictionaryMap.LOAN_TYPE %>">
						<option value="${loantype.key }" <c:if test="${loantype.key==resultmap['loantype']}">selected="selected"</c:if> >${loantype.value}</option>
					</c:forEach>
				</select>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>风控审核信息：</dt>
				<dd><textarea name="controlAudit" disabled="disabled" class="textInput " cols="95" rows="4" maxlength="2000">${resultmap['controlAudit']}</textarea></dd>
			</dl>
			<dl class="nowrap">
				<dt>企业信息：</dt>
				<dd><textarea name="companyInfo" disabled="disabled" class="textInput " cols="95" rows="4" maxlength="2000">${resultmap['companyInfo']}</textarea></dd>
			</dl>
			<dl class="nowrap">
				<dt>经营状况：</dt>
				<dd><textarea name="stateOperation"  disabled="disabled" class="textInput " cols="95" rows="4" maxlength="2000">${resultmap['stateOperation']}</textarea></dd>
			</dl>
			<dl class="nowrap">
				<dt>描述：</dt>
				<dd><textarea name="note" class="textInput" disabled="disabled" cols="95" rows="4">${resultmap['note']}</textarea></dd>
			</dl>
			<dl class="nowrap">
				<dt style="width: 240px;">项目备案确认登记表图片地址：</dt>
				<dd><input value="${resultmap['projectfiledJpgpath']}" type="text" size="80" disabled="disabled"/></dd>
			</dl>
			<dl class="nowrap">
				<dt style="width: 240px;">租凭物图片地址：</dt>
				<dd><input name="rentalcontentJpgpath" value="${resultmap['rentalcontentJpgpath']}" type="text" size="80" disabled="disabled"/></dd>
			</dl>
		</div>
		<div class="formBar">
	
				<ul>
				<c:if test="${isDetail == null ||  !isDetail == true}">			
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				</c:if>	
					<li>
						<div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div>
					</li>
				</ul>
		</div>
	</form>
</div>