<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript">
 function navTabAjaxDone(json){
       	 DWZ.ajaxDone(json);
     	 if (json.statusCode == DWZ.statusCode.ok){
     		 //列表页面重新加载数据
             navTab.reloadFlag("usermgr");         
            if ("closeCurrent" == json.callbackType) {
                  setTimeout(function(){navTab.closeCurrentTab();}, 100);
            } else if ("forward" == json.callbackType) {
                  navTab.reload(json.forwardUrl);
            }
      }
}
 
 function getCheckBox(){
	 var roleIds = "";
	 $("input[type='checkbox']:checked").each(function(i,a){
		 roleIds += a.value + ",";
	 });
	 $("#roleIds").val(roleIds);
 }
</script>
<div class="pageContent">
	<form method="post" action="${pageContext.request.contextPath }/user/mergeUserPass.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="56">
			<input type="hidden" name="id" value="${user.id }" />
			<dl>
				<dt>登录密码：</dt>
				<dd>
					<input name="userPass" type="password"  class="required" size="30" alt="请输入登录密码">
				</dd>
			</dl>
			<dl>
				<dt>确认登录密码：</dt>
				<dd>
					<input name="userPwd" type="password"  class="required" size="30" alt="请再次确认密码">
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>