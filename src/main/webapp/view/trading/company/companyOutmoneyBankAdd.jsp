<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<% request.setAttribute("ID_TYPE", DictionaryMap.ID_TYPE); %>
<% request.setAttribute("CARD_TYPE", DictionaryMap.CARD_TYPE); %>
<% request.setAttribute("CARD_FLAG", DictionaryMap.CARD_FLAG); %>
<%@ include file="../../include/include.jsp" %>
<script type="text/javascript" src="../webapp/resources/js/util/checkInput.js"></script>
<script type="text/javascript">
	function getCityName(){
		var strs= new Array(); //定义一数组 
		var strs=$("#provinceName").val().split(","); //字符分割 
		var number = strs[1];
		//清空下拉
		$("#cityName").html("<option value='' class='toSelected'>请选择</option>");
		if(number != null){
			//ajax 请求取值
			$.ajax({
			   type: "POST",
			   url: "${ctx}/company/getCityName.htm",
			   data: "provinceId="+number,
			   success: function(list){
			       $.each(list,function(idx,item){ //循环对象取值
			    	   if(item.city!=''){
			    		   $("#cityName").append("<option  value='"+item.city+"' class='delcity'>"+item.city+"</option>");
			    	   }
			       });
			   }
			});
		}
	}
</script>
<div class="pageContent">

	<form name="form1" method="post" action="${ctx}/company/addCompanyOutmoneyBank.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		<h2 class="contentTitle">新增公司银行卡信息详情</h2>
			<p>	
				<label>名称：</label>
				<input type="text" class="required" name="acctName" id="acctName" value="" maxlength="20">
			</p>
			<p>	
				<label>证件卡号：</label>
				<input type="text" class="required" name="idNo" id="idNo" value="" maxlength="18" >
			</p>    
			<p>	
				<label>身份证类型：</label>
				<select class="required" id="idType"  name="idType" >
					<option value="">请选择身份证类型</option>
					<c:forEach var="idType" items="<%=DictionaryMap.ID_TYPE %>">
						<option value="${idType.key }" <c:if test="${idType.key == '0'}">selected</c:if> >${idType.value }</option>
					</c:forEach>
				</select>
			</p>
			<p>	
				<label>银行卡号：</label>
				<input type="text" class="required" name="cardNo" id="cardNo" onkeypress="keyPress()" value="" maxlength="20" >
			</p>
			<p>	
				<label>银行卡类型：</label>
				<select class="required" id="cardType"  name="cardType" >
					<option value="">请选择身份证类型</option>
					<c:forEach var="cardType" items="<%=DictionaryMap.CARD_TYPE %>">
						<option value="${cardType.key }" <c:if test="${cardType.key == '2'}">selected</c:if> >${cardType.value }</option>
					</c:forEach>
				</select>
			</p>
			<p>	
				<label>对公对私类型：</label>
				<select class="required" id="cardFlag"  name="cardFlag" >
					<option value="">请选择身份证类型</option>
					<c:forEach var="cardFlag" items="<%=DictionaryMap.CARD_FLAG %>">
						<option value="${cardFlag.key }" <c:if test="${cardFlag.key == '0'}">selected</c:if> >${cardFlag.value }</option>
					</c:forEach>
				</select>
			</p>
			<p>	
				<label>所属银行编号：</label>
				<input type="text" class="required" name="bankCode" id="bankCode" value="" maxlength="8" >
			</p>
			<p>	
				<label>所属银行名称：</label>
				<input type="text" class="required" name="bankName" id="bankName" value="" maxlength="20" >
			</p>
			<p>	
				<label>开户行编号：</label>
				<input type="text" class="required" name="brabankCode" id="brabankCode" value="" maxlength="20" >				
			</p>
			<p>	
				<label>开户支行名称：</label>
				<input type="text" class="required" name="brabankName" id="brabankName" value="" maxlength="30" >
			</p>
			<p>	
				<label>开户行所在省编码：</label>
				<input type="text" class="required" name="provinceCode" id="provinceCode" value="" maxlength="20" >
			</p>
			<p>	
				<label>开户行所在省名称：</label>
				<select class="required" id="provinceName"  name="provinceName" onchange="getCityName();">
					<option value="null">请选择省份</option>
					<c:forEach var="province" items="${dataMessage.contentList }">
					
					
						<option value="${province.provinceName},${province.serialNumber}">${province.provinceName }</option>
					</c:forEach>
				</select>	
			</p>
			<p>	
				<label>开户行所在市编码：</label>
				<input type="text" class="required" name="cityCode" id="cityCode" value="" maxlength="20">				
			</p>
			<p>	
				<label>开户行所在市名称：</label>
				<select id="cityName" class="required"  name="cityName" >
					<option value="" class="toSelected">请选择</option>
				</select>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div>
				</li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>