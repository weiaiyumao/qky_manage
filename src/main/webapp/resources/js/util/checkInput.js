/**
 *  Input onkeypress事件 只能输入数字 0-9 
 */
function keyPress() {     
	    var keyCode = event.keyCode;  
	    event.returnValue = ((keyCode >= 48 && keyCode <= 57));     
}  