<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zqfinance.system.util.DictionaryMap"%>
<%@ include file="../../include/include.jsp" %>
<style type="text/css">
.btnb{
background:url(${ctx}/resources/images/btn.png) no-repeat; 
display:block; 
width:22px; 
height:20px; 
text-indent:0px; 
overflow:hidden; 
float:left; 
margin-right: 3px;
}
.addposition{
background-position: -110px -258px;
}
.delposition{
background-position: -158px -255px;
}
</style>
<script type="text/javascript">
function appendToP(){
	var i = $("input[seleted='seleted']").size();
	var apr = $("#apr"+i).val();
	if(null != apr && "" != apr){
		i = Number(i) + Number(1);
		if(i==1){
			createP(i,"一");
		}
		if(i==2){
			createP(i,"二");
		}
		if(i==3){
			createP(i,"三");
		}
		if(i==4){
			createP(i,"四");
		}
		if(i==5){
			createP(i,"五");
		}
		if(i==6){
			createP(i,"六");
		}
		if(i==7){
			createP(i,"七");
		}
		if(i==8){
			createP(i,"八");
		}
		if(i==9){
			createP(i,"九");
		}
		if(i==10){
			createP(i,"十");
		}
		if(i==11){
			createP(i,"十一");
		}
		if(i==12){
			createP(i,"十二");
		}
		if(i>=13){
			alert("最多设置12个月");
		}
	}else{
		alertMsg.info("新增利率规则，必须先填写好当前月！");
	}
	
}
function createP(i,month){
	i = Number(i) - Number(1);
	var apr = $("#apr"+i).val();
	if(null != apr && "" != apr){
		$("#apr"+i).attr({ readonly: "readonly"});
		i = Number(i) + Number(1);
		$("#isToAddP").before("<p><label>第"+month+"月：</label><input id='apr"+i+"' name='apr"+i+"' type='text' size='30' maxlength='6' class='number textInput valid' seleted ='seleted' onblur='removeP("+i+");' /></p>");
		if(i==2){
			$("#reminder").html("当前可修改第二月");
		}
		if(i==3){
			$("#reminder").html("当前可修改第三月");
		}
		if(i==4){
			$("#reminder").html("当前可修改第四月");
		}
		if(i==5){
			$("#reminder").html("当前可修改第五月");
		}
		if(i==6){
			$("#reminder").html("当前可修改第六月");
		}
		if(i==7){
			$("#reminder").html("当前可修改第七月");
		}
		if(i==8){
			$("#reminder").html("当前可修改第八月");
		}
		if(i==9){
			$("#reminder").html("当前可修改第九月");
		}
		if(i==10){
			$("#reminder").html("当前可修改第十月");
		}
		if(i==11){
			$("#reminder").html("当前可修改第十一月");
		}
		if(i==12){
			$("#reminder").html("当前可修改第十二月");
		}
	}else{
		alertMsg.info("新增利率规则，必须先填写好当前月！");
	}
}
function removeP(id){
	if($("#apr"+id).val()==null || $("#apr"+id).val()==''){
		$("#apr"+(Number(id) - Number(1))).removeAttr("readonly");
		$("#apr"+id).attr({ onblur: "removeP('"+id+"')"});
		if(Number(id) - Number(1)==1){
			$("#reminder").html("当前可修改第一月");
		}
		if(Number(id) - Number(1)==2){
			$("#reminder").html("当前可修改第二月");
		}
		if(Number(id) - Number(1)==3){
			$("#reminder").html("当前可修改第三月");
		}
		if(Number(id) - Number(1)==4){
			$("#reminder").html("当前可修改第四月");
		}
		if(Number(id) - Number(1)==5){
			$("#reminder").html("当前可修改第五月");
		}
		if(Number(id) - Number(1)==6){
			$("#reminder").html("当前可修改第六月");
		}
		if(Number(id) - Number(1)==7){
			$("#reminder").html("当前可修改第七月");
		}
		if(Number(id) - Number(1)==8){
			$("#reminder").html("当前可修改第八月");
		}
		if(Number(id) - Number(1)==9){
			$("#reminder").html("当前可修改第九月");
		}
		if(Number(id) - Number(1)==10){
			$("#reminder").html("当前可修改第十月");
		}
		if(Number(id) - Number(1)==11){
			$("#reminder").html("当前可修改第十一月");
		}
		$("#apr"+id).parent().remove();
	}
}
</script>
<div class="pageContent">

	<form name="form1" method="post" action="${ctx}/product/addproduct.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		<h2 class="contentTitle">新增理财产品</h2>
			<p>	
				<label>标题：</label>
				<input  class="required" name="title" type="text" size="30" maxlength="30" value=""/>
			</p>
			<p>
				<label>累计预融资金额：</label>
				<input id="money" class="required number" name="money" maxlength="19" type="text" size="30" value="" onblur="checkMoney('money')"/>
			</p>
			<p>
				<label>投标下限：</label>
				<input id="lower"  name="lower" type="text" size="30" maxlength="8" class="number" value="" onkeypress="keyPress()" onblur="checkLower('lower','ceiling')"/>
			</p>
			<p>
				<label>投标上限：</label>
				<input id="ceiling"  name="ceiling" type="text" size="30" maxlength="8" class="number" value="" onblur="checkLower('lower','ceiling')" onkeypress="keyPress()" />
			</p>
			<p>
				<label>合同：</label>
				<select class="combox required" id="lawId"  name="lawId" >
					<option value="">请选择合同</option>
					<c:forEach var="law" items="${dataMessage.contentList }">
						<option value="${law.id }">${law.title }</option>
					</c:forEach>
				</select>	
			</p>
			<dl class="nowrap">
				<dt>描述：</dt>
				<dd><textarea name="note" class="textInput" cols="95" rows="4" maxlength="500"></textarea></dd>
			</dl>
			<dl class="nowrap">
				<dd style="width: 790px;"><a  href="javascript:appendToP();" style="color: blue;" class="btnb addposition"></a>设置利率（利率设置规则：客户购买产品每月的年利率(%)）&nbsp;&nbsp;<span id="reminder" style="color: red;">当前可以修改第一月</span></dd>
			</dl>
				<p>
					<label>第一月：</label>
					<input id="apr1"  name="apr1" type="text" size="30" class="number required" maxlength="6" value="8" seleted = "seleted"/>
				</p>
			<input id="isToAddP" type="hidden" value="求你别动">
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div>
				</li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>