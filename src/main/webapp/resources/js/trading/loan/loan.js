$(document).ready(function(){
	//控制每页显示条数
	$("#numPerPage").val($("#hiddenNumPerPage").val());
	$("#numPerPage2").val($("#hiddenNumPerPage2").val());
	
	//列表页面控制标状态
	$("#loanStatus").val($("#hiddenStatus").val());
	$("#loanStatus").bind("change",function(){
		$("#hiddenStatus").val($("#loanStatus").val());
	});
});

function navTabAjaxDone(json){
       	 DWZ.ajaxDone(json);
     	 if (json.statusCode == DWZ.statusCode.ok){
     		 //列表页面重新加载数据
             navTab.reloadFlag("loanmgr");         
            if ("closeCurrent" == json.callbackType) {
                  setTimeout(function(){navTab.closeCurrentTab();}, 100);
            } else if ("forward" == json.callbackType) {
                  navTab.reload(json.forwardUrl);
            }
      }
}
 
 
 function checkAll(){
     var arrSon = document.getElementsByName("chkSon");
     var allchk = document.getElementById("chkall");
     var tempState=allchk.checked;
     for(var i=0;i<arrSon.length;i++){
         if(arrSon[i].checked!=tempState){
             arrSon[i].click();
              
         }
     }
     doaudite();
 }
 
 function doaudite(){
     var chklist = document.getElementsByName("chkSon");
     var arr = new Array();
     for(var i=0;i<chklist.length;i++){
         if(chklist[i].checked == true){
             arr.push(chklist[i].value);
         }
     }
 }
 
 function ChkSonClick(){
     var arrSon = document.getElementsByName("chkSon");
     var allchk = document.getElementById("chkall");
     for(var i=0;i<arrSon.length;i++){
         if(!arrSon[i].checked) {
          allchk.checked = false;
          return;
         }
     }
     allchk.checked = true;
     doaudite();
 }
 
 function chageCustomerNumPerPage(numPerPage){
	 $("#hiddenNumPerPage").val(numPerPage);
	 dwzPageBreak({targetType:'dialog', numPerPage:numPerPage});
 }
 
 /**
  * 检查身份证合法性
  * @param id
  * @returns {Boolean}
  */
 function checkIdcard(id){
	 var num = $("#"+id).val();
	    num = num.toUpperCase();
	    //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
	    if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num)))
	    {
	        alertMsg.info("输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。");
			$("#"+id).val("");
			return false;
	    }
	    //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
	    //下面分别分析出生日期和校验位
	    var len, re;
	    len = num.length;
	    if (len == 15)
	    {
	        re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
	        var arrSplit = num.match(re);
	 
	        //检查生日日期是否正确
	        var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
	        var bGoodDay;
	        bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
	        if (!bGoodDay)
	        {
	        	alertMsg.info("输入的身份证号里出生日期不对！");
	 			$("#"+id).val("");
	 			return false;
	        }
	        else
	        {
	                //将15位身份证转成18位
	                //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
	                var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
	                var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
	                var nTemp = 0, i;
	                num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
	                for(i = 0; i < 17; i ++)
	                {
	                    nTemp += num.substr(i, 1) * arrInt[i];
	                }
	                num += arrCh[nTemp % 11];
	                return true;
	        }
	    }
	    if (len == 18)
	    {
	        re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
	        var arrSplit = num.match(re);
	 
	        //检查生日日期是否正确
	        var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]);
	        var bGoodDay;
	        bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
	        if (!bGoodDay)
	        {
	        	alertMsg.info("输入的身份证号里出生日期不对！");
	 			$("#"+id).val("");
	 			return false;
	        }
	    else
	    {
	        //检验18位身份证的校验码是否正确。
	        //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
	        var valnum;
	        var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
	        var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
	        var nTemp = 0, i;
	        for(i = 0; i < 17; i ++)
	        {
	            nTemp += num.substr(i, 1) * arrInt[i];
	        }
	        valnum = arrCh[nTemp % 11];
	        if (valnum != num.substr(17, 1))
	        {
	            alertMsg.info("18位身份证的校验码不正确！最后一位数应该为：" + valnum);
	 			$("#"+id).val("");
	        }
	        return true;
	    }
	    }
	    return false;
	}
/**
 * 手机号码验证，验证13系列和150-159(154除外)、180-189(184除外)几种号码，长度11位
 * @param value
 * @returns {Boolean}
 */
 function isMobel(id){
	var value = $("#"+id).val();
	if (/^13\d{9}$/g.test(value) || (/^15[0-35-9]\d{8}$/g.test(value)) || (/^18[0-35-9]\d{8}$/g.test(value))){
		return true;
	}else{
		alertMsg.info("输入的手机号码格式不正确！");
		$("#"+id).val("");
		return false;
	}	
}
 /**
  * 根据类别判断字段是否必填
  */
 function onLoantype(id){
	 var value = $("#"+id).val();
	 if(value != "1"){
		 $("#rentalagency").removeClass("required");
		 $("#stateOperation").removeClass("required");
		 $("#companyInfo").removeClass("required");
		 $("#controlAudit").removeClass("required");
		 $("#certificatesNumber").removeClass("required");
		 $("#rentalagency").removeClass("required");
		 document.getElementById("showprojectfiledJpgpath").style.display="none";
		 document.getElementById("showrentalcontentJpgpath").style.display="none";
		 $("#projectfiledJpgpath").removeClass("required");
		 $("#rentalcontentJpgpath").removeClass("required");
	 }else{
		 //项目对接标这些信息必填
		 $("#rentalagency").addClass("required");
		 $("#stateOperation").addClass("required");
		 $("#companyInfo").addClass("required");
		 $("#controlAudit").addClass("required");
		 $("#certificatesNumber").addClass("required");
		 $("#rentalagency").addClass("required");
		 document.getElementById("showprojectfiledJpgpath").style.display="block";
		 document.getElementById("showrentalcontentJpgpath").style.display="block";
		 $("#projectfiledJpgpath").addClass("required");
		 $("#rentalcontentJpgpath").addClass("required");
	 }
 }
