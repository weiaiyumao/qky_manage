<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/themes/css/zTreeStyle.css" type="text/css">
<script src="${pageContext.request.contextPath}/resources/js/jquery.ztree.core-3.5.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.ztree.excheck-3.5.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/system/action.js" type="text/javascript"></script>
<script type="text/javascript">

var zNodes = ${moduleJson};
var setting = {
		check: {
			enable: true,
			chkStyle: "radio",
			radioType: "all"
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onCheck : zTreeOnclick
		}
	};
$(document).ready(function(){	
	 $.fn.zTree.init($("#treeDemo"), setting, zNodes);
});
</script>
<div class="pageContent">
	<form name="form1" method="post" action="${pageContext.request.contextPath }/action/mergeAction.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="56">
			<dl>
				<dt>功能名称：</dt>
				<dd>
					<input id="actionName" class="required" name="actionName" type="text" size="30" remote="${pageContext.request.contextPath }/action/hasActionName.htm" alt="请输入功能名称"/>
				</dd>
			</dl>
			<dl>
				<dt>功能URL：</dt>
				<dd>
					<input id="actionUrl" class="required" name="actionUrl" type="text" size="30" value="" alt="请输入功能URL"/>
				</dd>
			</dl>
			<dl>
				<dt>所属模块：</dt>
				<dd>
					<div style=" float:left; display:block;  overflow:auto; width:250px; height:700px; overflow:auto; border:solid 1px #CCC; line-height:21px; background:#FFF;">
						<ul id="treeDemo" class="ztree"></ul>
					</div>
				</dd>			
			</dl>
		</div>
			<input type="hidden" id="moduleId" name="moduleId" value="0"  />
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