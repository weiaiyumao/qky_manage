<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript">
 function navTabAjaxDone(json){
       	 //DWZ.ajaxDone(json);
     	 if (json.statusCode == DWZ.statusCode.ok){
     		 //列表页面重新加载数据
             navTab.reloadFlag("sysmenu");         
            if ("closeCurrent" == json.callbackType) {
                  setTimeout(function(){navTab.closeCurrentTab();}, 100);
            } else if ("forward" == json.callbackType) {
                  navTab.reload(json.forwardUrl);
            }
      }
}
 
 function dialogAjaxDone(json){
	    DWZ.ajaxDone(json);
	    //取得用户新增的按钮，动态展示在页面上
	    if (json.statusCode == DWZ.statusCode.ok){
	          if (json.navTabId){
	                navTab.reload(json.forwardUrl, {}, json.navTabId);
	          }
	          $.pdialog.close("buttonAdd"); 
	          if(json.objList.length > 0){
	        	var str = "<tr>"+
	        				"<td>"+json.objList[0].buttonName+"</td>"+
	        				"<td>"+json.objList[0].buttonCode+"</td>"+
	        				"<td><a href='javascript:void(0)' onclick=delButton(this)><font color='#1046ad'>删除</font></a></td>"+
	        				"</tr>";
	        	$("#showButtonAdd .gridTbody").find("table").find("tbody").append(str);
	        	//将新增的按钮数据存放到页面的隐藏域中
	         	var buttonArray = new Array();
	        	//alert($(".gridTbody").find("table").find("tbody").find("tr").length);
	        	$("#showButtonAdd .gridTbody").find("table").find("tbody").find("tr").each(function(){
	        		var buttonObj = {
	        			"buttonName":	$(this).find("td").first().html(),
	        			"buttonCode":	$(this).find("td").eq(1).html()
	        		};
	        		buttonArray.push(buttonObj);
	        	});
	        	$("#menuButtons").val(JSON.stringify(buttonArray)); 
	          }
	    }

	}
 function delButton(obj){
		$(obj).parent().parent().remove();
 }
 $(document).ready(function(){	
	//但选择菜单级别为一级菜单的时候，父级菜单填写项，一级按钮列表应该隐藏
	$("#menuLevel").bind("change",function(){
		//如果是一级菜单
		if($(this).val() == 1){
			$("#parentIdP").hide();
			$("#menuUrlP").hide();
			$("#showButtonAdd").hide();
		}
		else{
			$("#parentIdP").show();
			$("#showButtonAdd").show();
			$("#menuUrlP").show();
		}
	});
	
	
 })
</script>
<div class="pageContent">
	<form method="post" action="${pageContext.request.contextPath }/sysMenu/mergeMenu.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>菜单名称：</label>
				<input id="menuName" class="required" name="menuName" type="text" size="30" value="" alt="请输入菜单名称"/>
			</p>
			<p>
				<label>菜单编码：</label>
				<input id="menuCode" name="menuCode" class="required" type="text" size="30" value="" alt="请输入菜单编码"/>
			</p>
			<p>
				<label>菜单级别：</label>
				<select id="menuLevel" name="menuLevel" class="required combox" >
					<option value="">请选择</option>
					<c:forEach items="${menuLevelMap }" var="menuLevel">
						<option value="${menuLevel.key }">${menuLevel.value }</option>
					</c:forEach>
				</select>	
			</p>
			<p id="parentIdP">
				<label>父级菜单：</label>
				<select id="parentId" name="parentId" class="combox">
					<option value="0">请选择</option>
					<c:forEach items="${firstSysMenuList}" var="varMenu">
						<option value="${varMenu.id }">${varMenu.menuName }</option>
					</c:forEach>
				</select>	
			</p>
			<p>
				<label>菜单排序：</label>
				<input id="menuSort" name="menuSort" class="required" type="text" size="30" value="" alt="请输入菜单排序"/>
			</p>
			<p id="menuUrlP">
				<label>菜单URL：</label>
					<input id="menuUrl" name="menuUrl"  type="text" size="30" value="" alt="请输入菜单URL"/>
			</p>
			<input type="hidden" id="menuButtons" name="menuButtons" />
			<div class="divider"></div>
			<div id="showButtonAdd" class="pageContent">
			<div class="panelBar">
				<ul class="toolBar">
					<li><a  href="${pageContext.request.contextPath }/view/sysmgr/buttonAdd.jsp" target="dialog"  rel="buttonAdd" max="false" title="新增按钮" width="400" height="180"><span>新增按钮</span></a></li>
				</ul>
			</div>
				<table id="buttonTable" class="table" width="50%" layoutH="138">
					<thead>
						<tr>
							<th width="25%">按钮名称</th>
							<th width="25%">按钮编码</th>
							<th width="25%">操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
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