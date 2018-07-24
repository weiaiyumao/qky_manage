<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript">
 function navTabAjaxDone(json){
       	 DWZ.ajaxDone(json);
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
	        	var str = "<tr><td>"+json.objList[0].buttonName+
	        				"</td><td>"+json.objList[0].buttonCode+"</td>"+
	        				"<td><a href='javascript:void(0)' onclick=delButton(this)><font color='#1046ad'>删除</font></a></td>"+
	        				"</tr>";
	        	$("#showButtonAdd .gridTbody").find("table").find("tbody").append(str);
	        	//将新增的按钮数据存放到页面的隐藏域中
	         	var buttonArray = new Array();
	        	//alert($(".gridTbody").find("table").find("tbody").find("tr").length);
	        	$("#showButtonAdd .gridTbody").find("table").find("tbody").find("tr").each(function(){
	        		var idval = $(this).find("td").first().attr("id");
	        		if(idval == null || idval == ""){
	        			idval = 0;
	        		}
	        		var buttonObj   = {
	        			"id":idval,
	        			"buttonName":	$(this).find("td").first().html(),
	        			"buttonCode":	$(this).find("td").eq(1).html()
	        		};
	        		buttonArray.push(buttonObj);
	        	});
	        	$("#menuButtons").val(JSON.stringify(buttonArray)); 
	          }
	    }

	}
 function delButtonOri(obj){
		$(obj).parent().parent().parent().remove();
	}
 function delButton(obj){
		$(obj).parent().parent().remove();
	}
 //改写validateCallback
 function checkValue(){
	 if($("#showButtonAdd .gridTbody").find("table").find("tbody").find("tr").length > 0){
		 	$("#showButtonAdd .gridTbody").find("table").find("tbody").find("tr").each(function(){
				var idval = $(this).find("td").first().attr("id");
				if(idval == null || idval == ""){
					idval = 0;
				}
				var buttonObj   = {
					"id":idval,
					"buttonName":	$(this).find("td").first().html(),
					"buttonCode":	$(this).find("td").eq(1).html()
				};
				buttonArray.push(buttonObj);
			});
			$("#menuButtons").val(JSON.stringify(buttonArray)); 
	 }
 }
 function validateCallbackMenu(form, callback, confirmMsg) {
	// checkValue();
		var $form = $(form);
		if($(form).find("#showButtonAdd").find(".gridTbody").find("table").find("tbody").find("tr").length > 0){
			var buttonArray = new Array();
			$(form).find("#showButtonAdd").find(".gridTbody").find("table").find("tbody").find("tr").each(function(){
        		var idval = $(this).find("td").first().attr("id");
        		if(idval == null || idval == ""){
        			idval = 0;
        		}
        		var buttonName = $(this).find("td").first().html();
        		if($(this).find("td").first().find("div").length>0){
        			buttonName = $(this).find("td").first().find("div").html();
        		}
        		var buttonCode = $(this).find("td").eq(1).html();
        		if($(this).find("td").eq(1).find("div").length >0){
        			buttonCode = $(this).find("td").eq(1).find("div").html();
        		}
        		var buttonObj   = {
        			"id":idval,
        			"buttonName":	buttonName,
        			"buttonCode":	buttonCode
        		};
        		buttonArray.push(buttonObj);
        	});
        	$("#menuButtons").val(JSON.stringify(buttonArray));
		}

		if (!$form.valid()) {
			return false;
		}
		
		var _submitFn = function(){
			$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: callback || DWZ.ajaxDone,
				error: DWZ.ajaxError
			});
		}
		
		if (confirmMsg) {
			alertMsg.confirm(confirmMsg, {okCall: _submitFn});
		} else {
			_submitFn();
		}
		
		return false;
	}
 $(document).ready(function(){	
 })
</script>
<div class="pageContent">
	<form method="post" action="${pageContext.request.contextPath }/sysMenu/mergeMenu.htm" class="pageForm required-validate" onsubmit="return validateCallbackMenu(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>菜单名称：</label>
				<input type="hidden" name="id" value="${sysMenu.id }" />
				<input id="menuName" class="required" name="menuName" value="${sysMenu.menuName }" type="text" size="30" value="" alt="请输入菜单名称"/>
			</p>
			<p>
				<label>菜单编码：</label>
				<input id="menuCode" name="menuCode" class="required" value="${sysMenu.menuCode }" type="text" size="30" value="" alt="请输入菜单编码"/>
			</p>
			<p>
				<label>菜单级别：</label>
					<input type="hidden" name="menuLevel" value="${sysMenu.menuLevel }"/>
					<input  class="readonly" value="${menuLevelMap[sysMenu.menuLevel] }" type="text" size="30" value="" />	
			</p>
			<c:if test="${sysMenu.parentId > 0 }">
				<p>
					<label>父级菜单：</label>
					<select id="parentId" name="parentId" class="combox readonly">
						<option value="0">请选择</option>
						<c:forEach items="${firstSysMenuList}" var="varMenu">
							<option value="${varMenu.id }" <c:if test="${varMenu.id == sysMenu.parentId}">selected</c:if> >${varMenu.menuName }</option>
						</c:forEach>
					</select>	
				</p>
			</c:if>
			<p>
				<label>菜单排序：</label>
				<input id="menuSort" name="menuSort" class="required" type="text" value="${sysMenu.menuSort }" size="30" value="" alt="请输入菜单排序"/>
			</p>
			<c:if test="${sysMenu.parentId > 0 }">
				<p>
					<label>菜单URL：</label>
						<input id="menuUrl" name="menuUrl"  type="text" size="30"  alt="请输入菜单URL" value="${sysMenu.menuUrl }"/>
				</p>
			</c:if>
			<input type="hidden" id="menuButtons" name="menuButtons" />
			<div class="divider"></div>
			<c:if test="${sysMenu.parentId > 0 }">
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
							<c:forEach items="${sysButtonList }" var="sysButton">
								<tr>
									<td id="${ sysButton.id}">${sysButton.buttonName }</td>
									<td>${sysButton.buttonCode }</td>
									<td><a href='javascript:void(0)' onclick="delButtonOri(this)"><font color='#1046ad'>删除</font></a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
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