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
	<form method="post" action="${pageContext.request.contextPath }/user/mergeUser.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="56">
			<dl>
				<dt>工号：</dt>
				<dd>
					<input class="required" name="cardNo" type="text" size="30" value="" remote="${pageContext.request.contextPath }/user/hasUserCardnoOrName.htm" alt="请输入用户工号" />
				</dd>
			</dl>
			
			<dl>
				<dt>姓名：</dt>
				<dd>
					<input name="userNameCn" class="required" type="text" size="30" value="" alt="请输入用户姓名"/>
				</dd>
			</dl>
			
			<dl>
				<dt>英文名：</dt>
				<dd>
					<input name="userNameEn"  type="text" size="30" value="" class="textInput"/>		
				</dd>
			</dl>
			
			<dl>
				<dt>登录账号：</dt>
				<dd>
					<input type="text"  class="required" size="30" value="" name="userName" remote="${pageContext.request.contextPath }/user/hasUserCardnoOrName.htm" alt="请输入登录账号">&nbsp;&nbsp;&nbsp;默认登录密码<font color="red">123456</font>
				</dd>
			</dl>
			
			<dl>
				<dt>用户状态：</dt>
				<dd>
					<select name="userStatus" class="required combox" style="width: 210px;">
					<option value="">请选择</option>
					<option value="1" selected >正常</option>
					<option value="2" >离职</option>
					<option value="3" >禁用</option>
					</select>
				</dd>
			</dl>
			<div class="divider"></div>
				<p>
					<label>用户角色：</label>
					<c:forEach items="${roleList}" var="role">
					<input type="checkbox" name="roleId" value="${role.id }" onclick="getCheckBox();" ${role.checkFlag } />${role.roleName }
				</c:forEach>
				<input type="hidden" name="roleIds" id="roleIds" />
				</p>
				
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