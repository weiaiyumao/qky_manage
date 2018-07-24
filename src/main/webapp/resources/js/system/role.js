 function navTabAjaxDone(json){
       	 DWZ.ajaxDone(json);
     	 if (json.statusCode == DWZ.statusCode.ok){
     		 //列表页面重新加载数据
             navTab.reloadFlag("rolemgr");         
            if ("closeCurrent" == json.callbackType) {
                  setTimeout(function(){navTab.closeCurrentTab();}, 100);
            } 
      }
}
 
 

 function zTreeOnclick(event, treeId, treeNode) {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getCheckedNodes(true);
		var actionIds = "";
		for ( var node in nodes) {
			if (!nodes[node].isParent) {
				actionIds += nodes[node].id + ",";
			}
		}
		$("#roleActions").val(actionIds);
 } 


 
