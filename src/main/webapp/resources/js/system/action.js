$(document).ready(function(){
	//控制每页显示条数
	$("#numPerPage").val($("#hiddenNumPerPage").val());
});

function navTabAjaxDone(json){
       	 DWZ.ajaxDone(json);
     	 if (json.statusCode == DWZ.statusCode.ok){
     		 //列表页面重新加载数据
             navTab.reloadFlag("actionmgr");         
            if ("closeCurrent" == json.callbackType) {
                  setTimeout(function(){navTab.closeCurrentTab();}, 100);
            } else if ("forward" == json.callbackType) {
                  navTab.reload(json.forwardUrl);
            }
      }
}
 
 

 function zTreeOnclick(event, treeId, treeNode) {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getCheckedNodes(true);
		if(nodes.length > 0){
			$("#moduleId").val(nodes[0].id);
		}
		
 } 


 
