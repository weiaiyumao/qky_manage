/**
 *  Input onkeypress事件 只能输入数字 0-9 
 */
function keyPress() {     
	    var keyCode = event.keyCode;  
	    event.returnValue = ((keyCode >= 48 && keyCode <= 57));     
}  
/**
 * 检查金额
 * 格式为可以纯16位数字和2位小数点
 * @param apr
 */
function checkapr(money){
	var reg = new RegExp("^[1-9]|[1-9][0-9]+(.[0-9]{2})?$");
	var m = $("#"+money).val();
	if(!reg.test(m)){
		alertMsg.info("必须是纯1-16位纯数字或者带2个小数的正整数，不能以0开头！");
		$("#"+money).val("");
	}else if(m.length >= 17 && m.length<=19){
		var d = m.substring(Number(m.length) - Number(3), Number(m.length) - Number(2));
		if(d != '.'){
			alertMsg.info("必须是纯1-16位纯数字或者带2个小数的正整数，不能以0开头！");
			$("#"+money).val("");
		}
	}else if(m.length>19){
		alertMsg.info("必须是纯1-16位纯数字或者带2个小数的正整数，不能以0开头！");
		$("#"+money).val("");
	}
}

/**
 * 检查金额
 * 格式为可以纯16位数字和2位小数点
 * @param money
 */
function checkMoneyToEight(money){
	var reg = new RegExp("^[1-9]|[1-9][0-9]+(.[0-9]{2})?$");
	var m = $("#"+money).val();
	if(!reg.test(m)){
		alertMsg.info("必须是纯1-8位纯数字或者带2个小数的正整数，不能以0开头！");
		$("#"+money).val("");
	}else if(m.length >= 9 && m.length<=12){
		var d = m.substring(Number(m.length) - Number(3), Number(m.length) - Number(2));
		if(d != '.'){
			alertMsg.info("必须是纯1-8位纯数字或者带2个小数的正整数，不能以0开头！");
			$("#"+money).val("");
		}
	}else if(m.length>12){
		alertMsg.info("必须是纯1-8位纯数字或者带2个小数的正整数，不能以0开头！");
		$("#"+money).val("");
	}
}

/**
 * 控制投标上限必须小于下限
 * @param lower
 * @param ceiling
 */
function checkLower(lower,ceiling){
	
	var l = $("#"+lower).val();
	var c = $("#"+ceiling).val();
	
	if(l !="" && c != ""){
		if(c<l){
			alertMsg.info("上限必须大于下限，请重新设置！");
			$("#"+lower).val("");
			$("#"+ceiling).val("");
		}
	}
}

function checkTime(startTime,endTime){
	var s = $("#"+startTime).val();
	s.replace("-","/");
	var e = $("#"+endTime).val();
	e.replace("-","/");
	if(s != "" && e != ""){
		var d1 = new Date(Date.parse(s));   
		var d2=new Date(Date.parse(e));   
		if(d1>d2){    
			alertMsg.info("发标时间不能大于投标截止时间！");
			$("#"+startTime).val("");
			$("#"+endTime).val("");
		} 
	}
}

function checkTenderMoney(money){
	var reg = new RegExp("^[1-9]|[1-9][0-9]+(.[0-9]{2})?$");
	var m = $("#"+money).val();
	var i = money.substring(Number(7),Number(money.length));
	var r = $("#surplusMoney"+i).val();
	if(!reg.test(m)){
		alertMsg.info("必须是纯1-16位纯数字或者带2个小数的正整数，不能以0开头！");
		$("#"+money).val("");
	}else if(m.length >= 17 && m.length<=19){
		var d = m.substring(Number(m.length) - Number(3), Number(m.length) - Number(2));
		if(d != '.'){
			alertMsg.info("必须是纯1-16位纯数字或者带2个小数的正整数，不能以0开头！");
			$("#"+money).val("");
		}
	}else if(m.length>19){
		alertMsg.info("必须是纯1-16位纯数字或者带2个小数的正整数，不能以0开头！");
		$("#"+money).val("");
	} else if(Number(r)<Number(m)){
		alertMsg.info("投入金额必须小于剩余可投金额！");
		$("#"+money).val("");
	}
}



