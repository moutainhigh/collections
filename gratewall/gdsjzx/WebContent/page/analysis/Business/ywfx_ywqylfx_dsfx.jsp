<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script
	src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/chart/Highstock-4.2.3/js/highstock.js"
	type="text/javascript"></script>

<%-- <script src="<%=request.getContextPath()%>/chart/Highcharts-4.2.3/js/modules/drilldown.js"></script>
	 --%>
<script
	src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/static/js/analysis/picker.js"
	type="text/javascript"></script>
<style type="text/css">
	h4{
	margin-top:60%
	}
</style>
<title>业务分析-按业务量业务类型分析</title>
<script type="text/javascript">

  //定义一个Highcharts的变量，初始值为null
  var oChart = null;
  var draw = null;
  
  function drawHighCharts(rederTo,chartType,title,xName,yName,series){
   draw = {
		 chart: { 
		        renderTo: rederTo, 
		   	    type: chartType,
		    }, 
		    title: {
	            text: title,
	            useHTML:true
	        },
	        xAxis: {
	        	 categories: xName,//x轴
	             crosshair: true,
	        	 labels: {
	        	 		step:1,
                        align: 'center',
                        style: { font: 'normal 13px 宋体'}
				}
	        },
	        scrollbar: {
          		  enabled:true
	        },
	         plotOptions: {
	            series: {
	            	pointWidth: 10,
	                cursor: 'pointer',
	            }
	        },
	         tooltip: {
	            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	            pointFormat: '<tr><td style="  };padding:0 ">{series.name}: </td>' +
	                '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
	            footerFormat: '</table>',
	            shared: true,
	            useHTML: true
        	},
	        yAxis: {
	        	allowDecimals:false,
	        	endOnTick:true,
	            title: {
	                text: yName//y轴标题
	            },
	         labels: {
					    formatter:function(){
					      return this.value;
					    }
					  }
	        },tooltip: {
	         
	        },
	        credits: {
	        	text:"",
	        	href:''
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'top',
	            borderWidth: 0,
	           // useHTML:true
	        },
		    series:series,
		    drilldown: {series: []}
	 };
		oChart = new Highcharts.Chart(draw);
}
//
var title="地区业务数分析";
var rederTo="container";//指定ID
var yName="度量值";
var chartType="column";
//var regorg;
var drillType="01";
var regorgsCode=[];
var regorgsValue=[];
$(function () {
	var showstarttime = getUrlParam("showstarttime");
	var showendtime = getUrlParam("showendtime");
	var selType = getUrlParam("selType");
	drillType = getUrlParam("drillType");
	//provincesCity
	//regorg = getUrlParam("regorg");
	
	
	
		var toDate = new Date();
		var strDate=toDate.getFullYear()+"-"+(toDate.getMonth()+1)+"-"+toDate.getDate(); //获取完整的年份(4位,1970-????)			
		$('#selType').val("1");
		$("#showstarttime").val(strDate);
		$("#showendtime").val(strDate);
		$("#provincesCity").val(drillType);
		var data={selType:"0",showstarttime:strDate,showendtime:strDate,drillType:drillType,businessCount:"1"};
		title="地市业务量分析";
		loadChars(title,rederTo,chartType,yName,data);
		$("#selData").bind("click",selData);
		});
	
		function selData(){
			var selType=$('#Header input[name="gender"]:checked ').val();
			var showstarttime=$("#showstarttime").val();
			var showendtime=$("#showendtime").val();
			var provincesCity=$("#provincesCity").val();
			var businessCount=$("#businessCount").val();
		//a.transdt  and a.transdt  and b.parentcode  and a.MEASURE  selType   regType	
			var data={businessCount:businessCount,selType:selType,showstarttime:showstarttime,showendtime:showendtime,drillType:drillType};
			loadChars(title,rederTo,chartType,yName,data);
	}
	
//获取数据
function loadChars(title,rederTo,chartType,yName,data){
	
	$.ajax({
  			 url: '<%=request.getContextPath()%>/analysis/businessAnalystsSum.do',
					dataType : 'json',
					type : "POST", //请求方式
					data:data,	
					success : function(data) {
					if(data[0]==undefined){
					drawHighCharts(rederTo,chartType,title,null,yName,null);
					oChart.setTitle({ text: '<h4>返回报表为空</h4>'});
					oChart.setTitle(
           				 { style: { color: 'red' } }
        				);
					}else{
					var xName=groupxName(data);
					var series=new Array();
					var regorgrStr="";
					for(var k=0;k<data.length;k++){
						var regorgName=data[k].qyvalue;
						if(regorgrStr.indexOf(regorgName)==-1){
						regorgrStr=regorgrStr+"/"+regorgName;
						}
					}
					var regArray=regorgrStr.split("/");
					for(var i=1;i<regArray.length;i++){
					//按什么来分类 --- 图例部分
					series.push(groupSeries(data,regArray[i],xName));
					}
					// 按区域分析 ---- 标题
					drawHighCharts(rederTo,chartType,title,xName,yName,series);
					}
				}
			});
	}

	//根据时间作为X轴。类别作为具体项的情况下
	function  groupSeries(data,name,xName){
		var transdt=[];
		for(var k=0;k<xName.length;k++){
			for(var i=0;i<data.length;i++){
			var url="<%=request.getContextPath()%>/page/analysis/Business/ywfx_ywlxlfx_dsfx.jsp";
			if(name==data[i].qyvalue && data[i].transdt==xName[k]){
					var  seriesData; 
					var date=data[i].measurevalue;
					seriesData={dataLabels: {enabled: true,align:'left'},y:date};
					transdt.push(seriesData);
					break;
					}else{
					if(i==data.length-1){
					var date=0;	
					var  seriesData; 
					seriesData={dataLabels: {enabled: true,align:'left'},y:date,url:url+"?organizationmode="+data[i].organizationmode+"&showstarttime="+showstarttime+"&showendtime="+showendtime};
					transdt.push(seriesData);
						}
					}
				}
			}
		var seriesJson={"data":transdt,"name":name};
		return seriesJson;
	}

	function  groupxName(data){
		var transdt=new Array();
		var transtr="";
		for(var i=0;i<data.length;i++){
				var date=data[i].transdt;
				if(transtr.indexOf(date)==-1){
					transtr=transtr+"/"+date;
			}
		}
		var strList=transtr.split("/");
		for(var j=1;j<strList.length;j++){
			transdt.push(strList[j]);
		}
		return transdt;
	}
	
	function getUrlParam(name) {
         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
         var r = window.location.search.substr(1).match(reg);  //匹配目标参数
         if (r != null) return unescape(r[2]); return null; //返回参数值
     }
    </script>
</head>
<body>
			
    		

<div id="Container">
    <div class="headertop" id="Header" >
       	时间维度: <input class="btu" type="radio" name="gender" id="x" value="0" checked="checked"><label for="x">日</label>
        <input class="btu" type="radio" name="gender" id="y" value="1"><label for="y">月</label>
		<label id="showstart" for="开始">开始</label><input class="btu Wdate" id="showstarttime" type="text" readonly="readonly" 
			value='2016-05-01' onclick="createWdatePickerStart();" /> 
		<label id="showend" for="结束">结束</label><input class="btu Wdate" id="showendtime" type="text" readonly="readonly" 
			value='2016-07-01' onclick="createWdatePickerEnd();"/>
		<!-- <label >各级地市:</label>
    		<select id="provincesCity" name="dept" style="width: 150px;" >
    		</select> -->
		<label >固定指标:</label><select id="businessCount" name="dept" class="btu_select" style="width: 150px;">
    			<option value="1">本期业务量</option>
    			<option value="2">期末业务量</option>
    		</select> 
		<input class="bt" id="selData" type="button" value="查询" /> 
		<input class="bt" type="button"onclick="javascript:history.go(-1);tt(this.style.display='none')"value="返回">
    </div>
    <div id="Content">
       	<div id="container" style="width: 98%;"> 
		</div>
    </div>
</div>


	
</body>
</html>