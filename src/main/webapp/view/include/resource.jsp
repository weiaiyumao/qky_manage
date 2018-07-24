<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title>华融道管理后台</title>
<% String contextPath =request.getContextPath(); %>  
<link href="<%=contextPath%>/resources/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="<%=contextPath%>/resources/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="<%=contextPath%>/resources/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="<%=contextPath%>/resources/css/manage.css" rel="stylesheet" type="text/css" media="screen"/>
<script src="<%=contextPath%>/resources/js/jquery-1.7.2.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/jquery.cookie.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/jquery.validate.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.core.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.util.date.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.validate.method.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.barDrag.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.drag.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.tree.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.accordion.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.ui.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.theme.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.navTab.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.tab.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.resize.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.dialog.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.sortDrag.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.cssTable.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.stable.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.taskBar.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.ajax.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.pagination.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.database.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.datepicker.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.effects.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.panel.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.checkbox.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.history.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.combox.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.print.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/util/json2.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/js/util/validate.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/xheditor/xheditor-1.2.1.min.js" type="text/javascript"></script>
<script src="<%=contextPath%>/resources/xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>

<script type="text/javascript">
$(function(){
	DWZ.init("<%=contextPath%>/resources/xml/dwz.frag.xml", {
		loginUrl:"login_dialog.html", loginTitle:"登录",	// 弹出登录对话框
//		loginUrl:"login.html",	// 跳到登录页面
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		keys: {statusCode:"statusCode", message:"message"}, //【可选】
		ui:{hideMode:'offsets'}, //【可选】hideMode:navTab组件切换的隐藏方式，支持的值有’display’，’offsets’负数偏移位置的值，默认值为’display’
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"resources/themes"}); // themeBase 相对于index页面的主题base路径
		}
	});
});

</script>