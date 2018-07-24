<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp" %>
<div class="pageContent">

	<form name="form1" method="post" action="${ctx}/law/updateLaw.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		<h2 class="contentTitle">编辑合同</h2>
			<dl class="nowrap">
				<dt>合同标题：</dt>
				<dd>
					<input type="text" class="required" name="title" id="title" value="${dataMessage.data['title'] }" maxlength="20">
				</dd>
			</dl>
			<dl class="nowrap">
				<input type="hidden" name="id" value="${dataMessage.data['id'] }">
				<dt>合同类型：</dt>
				<dd>
					<select id="lawtype" class="combox required" name="lawtype" >
						<option value="">请选择合同类型</option>
						<c:forEach var="law" items="<%=DictionaryMap.LAWTYPE %>">
							<option value="${law.key }" <c:if test="${law.key == dataMessage.data['lawtype']}">selected</c:if> >${law.value}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>合同内容：</dt>
				<textarea class="editor required" maxlength="20000" name="content" id="content" rows="30" cols="100" 
								upLinkUrl="upload.php" upLinkExt="zip,rar,txt" 
								upImgUrl="upload.php" upImgExt="jpg,jpeg,gif,png" 
								upFlashUrl="upload.php" upFlashExt="swf"
								upMediaUrl="upload.php" upMediaExt:"avi">${dataMessage.data['content'] }</textarea>
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