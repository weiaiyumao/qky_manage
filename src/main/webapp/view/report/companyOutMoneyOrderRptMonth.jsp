<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>公司充值列表（月）</title>
		<script type="text/javascript" src="${ctx}/resources/js/hchart/highcharts.src.js"></script>
		<script type="text/javascript"  src="${ctx}/resources/js/hchart/exporting.js"></script>
		<script type="text/javascript">
		$(function () {
			var year  = getNowYear();
			createOutMoneyRptReq(year);
			
			$("#companyPreYear").bind("click",function(){
				var rptYear = $("#rptYear").val();
				var yearPreNum = Number(rptYear) - Number(1);
				if(yearPreNum<2015){
					alertMsg.info("数据不存在");
					return ;
				}
				createOutMoneyRptReq(yearPreNum);
			});
			
			$("#nextYear").bind("click",function(){
				var rptYear = $("#rptYear").val();
				var yearNextNum = Number(rptYear) + Number(1);
				if(yearNextNum > getNowYear()){
					alertMsg.info("数据不存在");
					return ;
				}
				else{
					createOutMoneyRptReq(yearNextNum);
				}
			});
			
			$("#delExcel").bind("click",function(){
				$("#delExcelfrom").submit();
			});
			
   		 });
		function createOutMoneyRptReq(year){
			var data = {
				"year":year
			}
			$.ajax({
				url:ctx + "/companyOutMoneyOrderReport/getOutMoneyOrderMoneyYear.htm",
				data:data,
				type:"POST",
				dataType:"json",
				success:function(resp){
					$("#rptYear").val(resp.year);
					$("#rptJsonYear").val(resp.json);
					createOutMoneyRpt(resp)
				}
			})
		}
    	function createOutMoneyRpt(resp){    		
        	$('#companyOutMoneyRptYear').highcharts({
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
                    text: '公司提现金额'
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
                name: '公司提现金额',
                data: resp.dataList
            },{
                name: '公司提现笔数',
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
	<input type="hidden"  id="rptYear" />
	<form method="post" action="${ctx}/companyOutMoneyOrderReport/delYearExcel.htm" id="delExcelfrom">
		<input type="hidden" id="rptJsonYear" name="rptJsonYear"/>
	</form>
	
<div class="pageHeader">
	<div class="searchBar" style="padding:5px 0;">
		<a id="companyPreYear" href="javascript:void(0)">上一年</a>&nbsp;&nbsp;<a id="nextYear" href="javascript:void(0)">下一年</a>&nbsp;&nbsp;<a id="delExcel" href="javascript:void(0)" >导出excel</a>
	</div>
</div>
<div class="pageContent">
		<div id="companyOutMoneyRptYear" style="min-width: 310px; height: 400px;"></div>
</div>
	</body>
</html>