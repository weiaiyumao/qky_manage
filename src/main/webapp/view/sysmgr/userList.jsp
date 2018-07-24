<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="mgr" uri="http://rrdai.com/mgr-tags"%> 
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<script type="text/javascript">
 function navTabAjaxDone(json){
       	 DWZ.ajaxDone(json);
     	 if (json.statusCode == DWZ.statusCode.ok){
     		 //列表页面重新加载数据
             navTab.reloadFlag("usermgr");         
      }
}
 

 
</script>
</head>
<body>
<form id="pagerForm" method="post" action="${pageContext.request.contextPath }/user/getUserList.htm">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${user.numPerPage}" />
</form>

<form onsubmit="return navTabSearch(this);" action="${pageContext.request.contextPath }/user/getUserList.htm" method="post">
<div class="pageHeader">

	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					工号：<input type="text" name="cardNo" value="${user.cardNo }" />
				</td>
				<td>
					中文名：<input type="text" name="userNameCn" value="${user.userNameCn }" />
				</td>
				<td>
					英文名：<input type="text" name="userNameEn" value="${user.userNameEn }" />
				</td>

			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
</div>
</form>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><mgr:button href="${pageContext.request.contextPath }/user/forwordUserMerge.htm" target="navTab" value="新增用户" cssClass="add"></mgr:button></li>
			<li><mgr:button href="${pageContext.request.contextPath }/user/forwordUserMerge.htm?1=1&userId={id_user}" target="navTab" value="修改用户" warn="请选择需要修改的用户" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${pageContext.request.contextPath }/user/forwordUserPass.htm?1=1&userId={id_user}" target="navTab" value="修改密码" warn="请选择需要修改的用户" cssClass="edit"></mgr:button></li>
			<li><mgr:button href="${pageContext.request.contextPath }/user/delUser.htm?1=1&userId={id_user}" target="ajaxTodo" value="删除用户" title="确定要删除吗?" cssClass="delete"></mgr:button></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="156">
		<thead>
			<tr>
				<th>工号</th>
				<th>中文名</th>
				<th>英文名</th>
				<th>系统账号</th>
				<th align="center">用户状态</th>
				<th>最后登录时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="user" items="${userList }">
				<tr target="id_user" rel="${user.id}">
					<td>${user.cardNo }</td>
					<td>${user.userNameCn }</td>
					<td>${user.userNameEn }</td>
					<td>${user.userName }</td>
					<td>${user.userStatus }</td>
					<td>${user.lastLoginTime }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="1">1</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<span>条，共${user.totalCount}条</span>
		</div>
		
		<div class="pagination" targetType="navTab" totalCount="${user.totalCount }" numPerPage="${user.numPerPage }" pageNumShown="10" currentPage="${user.pageNum }"></div>

	</div>
</div>

</body>
</html>