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
	if(id != '1'){
		if($("#apr"+id).val()==null || $("#apr"+id).val()==''){
			$("#apr"+(Number(id) - Number(1))).removeAttr("readonly");
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
			$("#apr"+(Number(id) - Number(1))).attr({ onblur: "removeP('"+(Number(id) - Number(1))+"')"});
			$("#apr"+id).parent().remove();
		}
	}
	
}
</script>
<div class="pageContent">
	<form name="form1" method="post" action="${ctx}/product/updateProduct.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		<h2 class="contentTitle">编辑理财产品基本信息</h2>
			<p>	
				<label>标题：</label>
				<input name="id" type="hidden" size="30" value="${dataMessage.data['id'] }"/>
				<input  class="required textInput" name="title" type="text" size="30" maxlength="30" value="${dataMessage.data['title'] }"/>
			</p>
			<p>
				<label>累计预融资金额：</label>
				<input id="money" class="required number textInput" name="money" type="text" size="30" maxlength="19" value="<fmt:formatNumber value="${dataMessage.data['money']}" pattern="#.00"/>"  onblur="checkMoney('money')"/>
			</p>
			<p>
				<label>理财已投金额：</label>
				<input id="money" class="number textInput" name="money" type="text" size="30" maxlength="19" value="<fmt:formatNumber value="${dataMessage_money.data['money']}" pattern="#.00"/>" disabled="disabled" onblur="checkMoney('money')" style="color: red;"/>
			</p>
			<p>
				<label>投标下限：</label>
				<input id="lower"  name="lower" type="text" size="30" class="number" maxlength="8" value="<fmt:formatNumber value="${dataMessage.data['lower'] }" pattern="00" />" onkeypress="keyPress()"/>
			</p>
			<p>
				<label>投标上限：</label>
				<input id="ceiling"  name="ceiling" type="text" size="30" class="number textInput" maxlength="8" value="<fmt:formatNumber value="${dataMessage.data['ceiling'] }" pattern="00" />" onkeypress="keyPress()" onblur="checkLower('lower','ceiling')"/>
			</p>
			<p>
				<label>合同：</label>
				<select class="combox required" id="lawId"  name="lawId" >
					<option value="">请选择合同</option>
					<c:forEach var="law" items="${law_dataMessage.contentList }">
						<option value="${law.id }" <c:if test="${law.id == dataMessage.data['lawid'] }">selected</c:if> >${law.title }</option>
					</c:forEach>
				</select>	
			</p>
			<dl class="nowrap">
				<dt>描述：</dt>
				<dd><textarea name="note" class="textInput" cols="95" rows="4" maxlength="500" >${dataMessage.data['note'] }</textarea></dd>
			</dl>
			<dl class="nowrap">
				<c:if test="${dataMessage.data['status']==1}">
					<dd style="width: 790px;"><a  href="javascript:appendToP();" style="color: blue;" class="btnb addposition"></a>设置利率(利率设置规则：客户购买产品每月的年利率(%))&nbsp;&nbsp;<span id="reminder" style="color: red;">当前可以修改第${dataMessage_apr.contentList.size()==1?'一':(dataMessage_apr.contentList.size()==2?'二':(dataMessage_apr.contentList.size()==3?'三':(dataMessage_apr.contentList.size()==4?'四':(dataMessage_apr.contentList.size()==5?'五':(dataMessage_apr.contentList.size()==6?'六':(dataMessage_apr.contentList.size()==7?'七':(dataMessage_apr.contentList.size()==8?'八':(dataMessage_apr.contentList.size()==9?'九':(dataMessage_apr.contentList.size()==10?'十':(dataMessage_apr.contentList.size()==11?'十一':(dataMessage_apr.contentList.size()==12?'十二':dataMessage_apr.contentList.size())))))))))))}月</span></dd>
				</c:if>
				<c:if test="${dataMessage.data['status']!=1}">
					<dd><lable>&nbsp;&nbsp;年利率规则：(%)</lable></dd>
				</c:if>
				</dl>
			<c:forEach var = "vMap" items="${dataMessage_apr.contentList}" varStatus="number">
				<p>
					<label>第${vMap['month']==1?'一':(vMap['month']==2?'二':(vMap['month']==3?'三':(vMap['month']==4?'四':(vMap['month']==5?'五':(vMap['month']==6?'六':(vMap['month']==7?'七':(vMap['month']==8?'八':(vMap['month']==9?'九':(vMap['month']==10?'十':(vMap['month']==11?'十一':(vMap['month']==12?'十二':vMap['month'])))))))))))}月：</label>
					<c:if test="${dataMessage.data['status']==1}">
						<c:if test="${dataMessage_apr.contentList.size()==vMap['month']}">
							<input id="apr${number.count}"  name="apr${number.count}" type="text" class = "${vMap['month']==1 ? 'number required' : 'number'}" maxlength="6" size="30" class="number" seleted ="seleted" value="${vMap['apr']}" onblur="removeP('${number.count}');" />
						</c:if>
						<c:if test="${dataMessage_apr.contentList.size()!=vMap['month']}"> 
							<input id="apr${number.count}"  name="apr${number.count}" type="text" class = "${vMap['month']==1 ? 'number required' : 'number'}" maxlength="6" size="30" readonly="readonly" seleted ="seleted" class="number" value="${vMap['apr']}"/>
						</c:if>
					</c:if>
					<c:if test="${dataMessage.data['status']!=1}">
						<input id="apr${number.count}"  name="apr${number.count}" type="text" class = "${vMap['month']==1 ? 'number required' : 'number'}" maxlength="6" size="30" class="number" seleted ="seleted" readonly="readonly" value="${vMap['apr']}"/>
					</c:if>
				</p>
			</c:forEach>
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