<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>投资列表（月）</title>
		<script type="text/javascript" src="${ctx}/resources/js/hchart/highcharts.src.js"></script>
		<script type="text/javascript"  src="${ctx}/resources/js/hchart/exporting.js"></script>
		<script type="text/javascript">
		$(function () {
			var year  = getNowYear();
			createMonthRptReq(year);
			
			$("#outMoneyPreYear").bind("click",function(){
				var outMoneyRptYear = $("#outMoneyRptYear").val();
				var yearPreNum = Number(outMoneyRptYear) - Number(1);
				if(yearPreNum<2015){
					alertMsg.info("数据不存在");
					return ;
				}
				createMonthRptReq(yearPreNum);
			});
			
			$("#outMoneyNextYear").bind("click",function(){
				var outMoneyRptYear = $("#outMoneyRptYear").val();
				var yearNextNum = Number(outMoneyRptYear) + Number(1);
				if(yearNextNum > getNowYear()){
					alertMsg.info("数据不存在");
					return ;
				}
				else{
					createMonthRptReq(yearNextNum);
				}
			});
			
			$("#outMoneyDelExcelYear").bind("click",function(){
				$("#outMoneyDelExcelfromMonth").submit();
			});
			
   		 });
		function createMonthRptReq(year){
			var data = {
				"year":year
			}
			$.ajax({
				url:ctx + "/outMoneyOrderReport/getOutMoneyOrderMoneyYear.htm",
				data:data,
				type:"POST",
				dataType:"json",
				success:function(resp){
					$("#outMoneyRptYear").val(resp.year);
					$("#outMoneyRptJsonYear").val(resp.json);
					createOutMoneyMonthRpt(resp)
				}
			})
		}
    	function createOutMoneyMonthRpt(resp){ 
        	$('#outMoneyMyrptYear').highcharts({
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
                    text: '月份（月）'
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
   
    	
    	function getNowYear(){
    		var date = new Date();
    		var year = date.getFullYear();
    		return year;
    	}

		</script>
	</head>
	<body>
	<input type="hidden"  id="outMoneyRptYear" />
	<form method="post" action="${ctx}/outMoneyOrderReport/delYearExcel.htm" id="outMoneyDelExcelfromMonth">
		<input type="hidden" id="outMoneyRptJsonYear" name="rptJsonYear"/>
	</form>
	
<div class="pageHeader">
	<div class="searchBar" style="padding:5px 0;">
		<a id="outMoneyPreYear" href="javascript:void(0)">上一年</a>&nbsp;&nbsp;<a id="outMoneyNextYear" href="javascript:void(0)">下一年</a>&nbsp;&nbsp;<a id="outMoneyDelExcelYear" href="javascript:void(0)" >导出excel</a>
	</div>
</div>
<div class="pageContent">
		<div id="outMoneyMyrptYear" style="min-width: 310px; height: 400px;"></div>
</div>
	</body>
</html>