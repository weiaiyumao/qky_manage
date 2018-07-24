<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>投资列表（天）</title>
		<script type="text/javascript" src="${ctx}/resources/js/hchart/highcharts.src.js"></script>
		<script type="text/javascript"  src="${ctx}/resources/js/hchart/exporting.js"></script>
		<script type="text/javascript">
		$(function () {
			var month  = getNowMonth();
			createDayRptReq(month);
			$("#outMoneyPreMonth").bind("click",function(){
				var outMoneyRptMonth = $("#outMoneyRptMonth").val();
				var monthStr = outMoneyRptMonth.substring(5,7);
				var monthPreNum = Number(monthStr) - 1;
				
				var month = "";
				if(monthPreNum > 0){
					if(monthPreNum >9){
						month = outMoneyRptMonth.substring(0,4)+"-" + monthPreNum;
					}
					else{
						month = outMoneyRptMonth.substring(0,4) + "-0"+monthPreNum
					}
				}else{
					var yearPreNum = Number(outMoneyRptMonth.substring(0,4)) - 1
					month =  yearPreNum + "-12";
				}
				if(month.substring(0,4) < "2015"){
					alertMsg.info("仅2015年之后存在数据");
					return;
				}else{
					createDayRptReq(month);
				}
			});
			
			$("#outMoneyNextMonth").bind("click",function(){
				var outMoneyRptMonth = $("#outMoneyRptMonth").val();
				var monthStr = outMoneyRptMonth.substring(5,7);
				var monthPreNum = Number(monthStr) + 1;
				
				var month = "";
				if(monthPreNum < 13){
					if(monthPreNum >9){
						month = outMoneyRptMonth.substring(0,4)+"-" + monthPreNum;
					}
					else{
						month = outMoneyRptMonth.substring(0,4) + "-0"+monthPreNum
					}
				}else{
					var yearPreNum = Number(outMoneyRptMonth.substring(0,4)) + 1
					month =  yearPreNum + "-01";
				}
				if(month > getNowMonth()){
					alertMsg.info("数据不存在");
					return ;
				}
				else{
					createDayRptReq(month);
				}
			})
   		 });
		
		$("#outMoneyDelExcelMonth").bind("click",function(){
			$("#outMoneyDelExcelfromDay").submit();
		});
		
		function createDayRptReq(month){
			var data = {
				"month":month
			}
			$.ajax({
				url:ctx + "/outMoneyOrderReport/getOutMoneyOrderMoneyMonth.htm",
				data:data,
				type:"POST",
				dataType:"json",
				success:function(resp){
					$("#outMoneyRptMonth").val(resp.month);
					$("#outMoneyRptJsonDay").val(resp.json);
					createOutMoneyDayRpt(resp);
				}
			})
		}
    	function createOutMoneyDayRpt(resp){   
        	$('#outMoneyMyrpt').highcharts({
           	 title: {
                text: resp.title,
                x: -20 //center
            },
            subtitle: {
                text: resp.subTitle,
                x: -20
            },
            xAxis: {
                categories: resp.xaixsList,
                title: {
                    text: '日期（天）'
                },
            },
            yAxis: {
                title: {
                    text: '提现金额'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },

            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },
            series: [{
                name: '提现金额',
                data: resp.dataList
            },{
                name: '提现笔数',
                data: resp.numList
            }],
            exporting: {
                enabled: false
            }
        });
    	}
   
    	
    	function getNowMonth(){
    		var date = new Date();
    		var year = date.getFullYear();
    		var monthL = date.getMonth();
    		var montht = monthL + 1;
    		if(montht <= 9){
    			return year + "-0"+montht; 
    		}else{
    			return year + "-" + montht;
    		}
    	}

		</script>
	</head>
	<body>
	<input type="hidden"  id="outMoneyRptMonth" />
	
	<form method="post" action="${ctx}/outMoneyOrderReport/delMonthExcel.htm" id="outMoneyDelExcelfromDay">
		<input type="hidden" id="outMoneyRptJsonDay" name="rptJson"/>
	</form>
	
<div class="pageHeader">
	<div class="searchBar" style="padding:5px 0;">
		<a id="outMoneyPreMonth" href="javascript:void(0)">上一月</a>&nbsp;&nbsp;<a id="outMoneyNextMonth" href="javascript:void(0)">下一月</a>&nbsp;&nbsp;<a id="outMoneyDelExcelMonth" href="javascript:void(0)" >导出excel</a>
	</div>
</div>
<div class="pageContent">
		<div id="outMoneyMyrpt" style="min-width: 310px; height: 400px;"></div>
</div>
	</body>
</html>