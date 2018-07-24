<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp" %>
<script src="${ctx}/resources/js/trading/loan/loan.js" type="text/javascript"></script>
<!-- 引用ajaxfileupload.js -->  
<script src="${ctx}/resources/js/ajaxfileupload.js"></script> 
<script type="text/javascript">
function projectupload(){
    $.ajaxFileUpload({  
        url:'${ctx}/JPGUploadServlet/uploadImport.htm',  
        secureuri:false,  
        fileElementId:'fileprojectfiledJpgpath',//file标签的id  
        dataType: 'json',//返回数据的类型  
        success: function (data, status) {  
            var obj = jQuery.parseJSON(data); 
            $("#projectfiledJpgpath").val(obj['path']);
            alertMsg.info(obj['msg']);
        },  
        error: function (data, status, e) { 
       	 alertMsg.error(obj['msg']);
        }  
    });  
}
function rentalupload(){
    $.ajaxFileUpload({  
        url:'${ctx}/JPGUploadServlet/uploadImport.htm',  
        secureuri:false,  
        fileElementId:'filerentalcontentJpgpath',//file标签的id  
        dataType: 'json',//返回数据的类型  
        success: function (data, status) {  
            var obj = jQuery.parseJSON(data); 
            $("#rentalcontentJpgpath").val(obj['path']);
            alertMsg.info(obj['msg']);
        },  
        error: function (data, status, e) { 
       	 alertMsg.error(obj['msg']);
        }  
    });  
}
</script>
<div class="pageContent">
	<form name="form1" method="post" action="${ctx}/loan/addLoan.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		<h2 class="contentTitle">借款标基本信息</h2>
			<p>	
				<label>借款人名称：</label>
				<input  class="required" name="customerName" type="text" size="30" maxlength="20" alt="请输入借款人名称"/>
			</p>
			<p>	
				<label>身份证：</label>
				<input  class="required" id="customerIdcard" name="customerIdcard" type="text" size="30" maxlength="18" alt="请输入身份证" onblur="checkIdcard('customerIdcard')"/>
			</p>
			<p>	
				<label>手机号码：</label>
				<input  class="required" id="customerMobile" name="customerMobile" type="text" size="30" onkeypress="keyPress()" maxlength="11" alt="请输入手机号码" onblur="isMobel('customerMobile')" />
			</p>
			<p>	
				<label>标题：</label>
				<input  class="required" name="title" type="text" size="30" maxlength="30" alt="请输入借款标题"/>
			</p>
			<p>
				<label>借款金额：</label>
				<input id="money" class="required number" name="money" type="text" size="30"  alt="请输入金额" maxlength="19" onblur="checkMoney('money')"/>
			</p>
			<p>
				<label>年利率：</label>
				<input id="apr" class="required number" name="apr" type="text" size="30" maxlength="6"  alt="请输入年利率"/>
			</p>
			<p>		
				<label>发标时间：</label>
				<input id="sendTime" class="date textInput readonly valid required" datefmt="yyyy-MM-dd HH:mm:ss" name="sendTime" size="30" onclick="checkTime('sendTime','lasttime')" type="text" />
				<a href="javascript:;" class="inputDateButton">选择</a>
			</p>
			<p> 
				<label>借款期限：</label>
				<input id="terms" class="required digits" name="terms" type="text" size="30" maxlength="10" alt="请输入借款期限"/>
			</p>
			<p>
				<label>贷款期限类型：</label>
				<select class="combox required" id="termType"  name="termType" >
					<option value="">请选择贷款期限</option>
					<c:forEach var="termsType" items="<%=DictionaryMap.TERM_TYPE %>">
						<option value="${termsType.key }" <c:if test="${termsType.key==1}">selected="selected"</c:if> >${termsType.value }</option>
					</c:forEach>
				</select>			
			</p>
			<p>
				<label>还款方式：</label>
				<select id="methodtype" class="combox required" name="methodtype" >
					<option value="">请选择还款方式</option>
					<c:forEach var="loanMethod" items="<%=DictionaryMap.LOAN_METHOD %>">
						<option value="${loanMethod.key }" <c:if test="${loanMethod.key==2}">selected="selected"</c:if> >${loanMethod.value }</option>
					</c:forEach>
				</select>
			</p>
			<p>
				<label>是否担保：</label>
				<select id="assure" class="combox required" name="assure" >
					<option value="">选择是否担保</option>
					<c:forEach var="loanAssure" items="<%=DictionaryMap.YESORNO %>">
						<option value="${loanAssure.key }" <c:if test="${loanAssure.key==1}">selected="selected"</c:if> >${loanAssure.value }</option>
					</c:forEach>
				</select>
			</p>
			<p>	
				<label>借款保障类型：</label>
				<select id="safeType" class="combox" name="safeType" >
					<option value="">选择借款保障类型</option>
					<c:forEach var="safeType" items="<%=DictionaryMap.LOAN_SAFETYPE %>">
						<option value="${safeType.key}" <c:if test="${safeType.key==2}">selected="selected"</c:if> >${safeType.value}</option>
					</c:forEach>
				</select>
			</p>
			<p>
				<label>是否投保：</label>
				<select id="insure" class="combox" name="insure" >
					<option value="">选择是否投保</option>
					<c:forEach var="yesOrNo" items="<%=DictionaryMap.YESORNO %>">
						<option value="${yesOrNo.key }" <c:if test="${yesOrNo.key==1}">selected="selected"</c:if> >${yesOrNo.value}</option>
					</c:forEach>
				</select>
			</p>
			
			<p>
				<label>合同：</label>
				<select class="combox required" id="lawId"  name="lawId" >
					<option value="">请选择合同</option>
					<c:forEach var="law" items="${law_dataMessage.contentList }">
						<option value="${law.id }" >${law.title }</option>
					</c:forEach>
				</select>	
			</p>
			<p>
				<label>贷款标类型：</label>
				<select id="loantype" class="combox" name="loantype" onchange="onLoantype('loantype')">
					<option value="">选择贷款标类型</option>
					<c:forEach var="loantype" items="<%=DictionaryMap.LOAN_TYPE %>">
						<option value="${loantype.key }" <c:if test="${loantype.key==0}">selected="selected"</c:if> >${loantype.value}</option>
					</c:forEach>
				</select>
			</p>
			<p>	
				<label>租凭机构：</label>
				<input  class="" id="rentalagency" name="rentalagency" type="text" size="30" maxlength="30" alt="请输入租凭机构"/>
			</p>
			<p>	
				<label>央行登记证明编号：</label>
				<input  class="" id="certificatesNumber" name="certificatesNumber" type="text" size="30" maxlength="30" alt="请输入央行登记证明编号"/>
			</p>
			<dl class="nowrap">
				<dt>风控审核信息：</dt>
				<dd>
					<textarea name="controlAudit" id="controlAudit" class="editor" maxlength="20000" rows="15" cols="100" 
								upLinkUrl="upload.php" upLinkExt="zip,rar,txt" 
								upImgUrl="upload.php" upImgExt="jpg,jpeg,gif,png" 
								upFlashUrl="upload.php" upFlashExt="swf"
								upMediaUrl="upload.php" upMediaExt:"avi"></textarea>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>企业信息：</dt>
				<dd>
					<textarea name="companyInfo" id="companyInfo" class="editor" maxlength="20000" rows="15" cols="100" 
								upLinkUrl="upload.php" upLinkExt="zip,rar,txt" 
								upImgUrl="upload.php" upImgExt="jpg,jpeg,gif,png" 
								upFlashUrl="upload.php" upFlashExt="swf"
								upMediaUrl="upload.php" upMediaExt:"avi"></textarea>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>经营状况：</dt>
				<dd>
					<textarea name="stateOperation" id="stateOperation" class="editor" maxlength="20000" rows="15" cols="100" 
								upLinkUrl="upload.php" upLinkExt="zip,rar,txt" 
								upImgUrl="upload.php" upImgExt="jpg,jpeg,gif,png" 
								upFlashUrl="upload.php" upFlashExt="swf"
								upMediaUrl="upload.php" upMediaExt:"avi"></textarea>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>描述：</dt>
				<dd><textarea name="note" id="note" class="textInput required" cols="95" rows="4" maxlength="2000"></textarea></dd>
			</dl>
			<dl class="nowrap" style="display: none;" id="showprojectfiledJpgpath">
				<dt style="width: 240px;">项目备案确认登记表图片地址<span style="color: red;">(必选)</span>：</dt>
				<dd><input  class="" id="fileprojectfiledJpgpath" name="projectfiledJpgpath" type="file" size="40" onchange="projectupload()" /></dd>
				<dd><input  class="" id="projectfiledJpgpath" name="projectfiledJpgpath" type="text" size="80" readonly="readonly"/></dd>
			</dl>
			<dl class="nowrap" style="display: none;" id="showrentalcontentJpgpath">
				<dt style="width: 240px;">租凭物图片地址<span style="color: red;">(必选)</span>：</dt>
				<dd><input  class="" id="filerentalcontentJpgpath" name="rentalcontentJpgpath" type="file" size="40" onchange="rentalupload()"/></dd>
				<dd><input  class="" id="rentalcontentJpgpath" name="rentalcontentJpgpath" type="text" size="80" readonly="readonly"/></dd>
			</dl>
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