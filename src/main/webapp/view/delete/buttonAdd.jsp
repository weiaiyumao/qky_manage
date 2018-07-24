<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">


 </script>
<div class="pageContent">
	<form method="post" action="${pageContext.request.contextPath }/sysMenu/mergeButton.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>按钮名称：</label>
				<input class="required" name="buttonName" type="text" size="30" value="" alt="请输入按钮名称"/>
			</p>
			<p>
				<label>按钮编码：</label>
				<input name="buttonCode" class="required" type="text" size="30" value="" alt="请输入按钮编码"/>
			</p>			
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive"><div class="buttonContent"><button  type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>