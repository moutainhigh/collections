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
	src="<%=request.getContextPath()%>/chart/Highcharts-4.2.3/js/highcharts.js"
	type="text/javascript"></script>
	
<script
	src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/static/js/analysis/picker.js"
	type="text/javascript"></script>
<script 
	src="<%=request.getContextPath()%>/static/script/jazz.js" 
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/analysis/commonUtil.js"
	type="text/javascript"></script>
<style type="text/css">
	h4{
	margin-top:60%
	}
</style>
<title>企业设立登记 - 按二级组织形式分析</title>
<script type="text/javascript">
var t = "${param.at}";
changeTitle(t);
_data_type_table = "${param.bt}";

//init measure data && set titile
/* if("1" == t){//为市场主体概况，否则为设立登记
	document.title = '市场主体概况' + document.title.substr(6, document.title.length);
} */
  //定义一个Highcharts的变量，初始值为null
  var oChart = null;
  var draw = null;
  
  function drawHighCharts(rederTo,chartType,title,xName,yName,series){
   draw = {
		 chart: { 
		        renderTo: rederTo, 
		   	    type: chartType,
		   	 /*    events: {
                drilldown: function (e) {
               		 var chart=this;
					drilldownOne(e,chart);
		                }
		            } */
		   	    /* backgroundColor: {
                linearGradient: [0, 0, 0, 300],
                stops: [
                    [0, '#FFFFFF'],
                    [1, '#E0E0E0']
                ]
            } */
		 }, 
		    title: {
		    	text: document.title,
	            useHTML:true
	        },
	        /* subtitle: {
	            text: ''
	        }, */
	        //待验证
	         tooltip: {
	            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	            pointFormat: '<tr><td style="  };padding:0 ">{series.name}: </td>' +
	                '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
	            footerFormat: '</table>',
	            shared: true,
	            useHTML: true
        	},
	        xAxis: {
	        	 categories: xName,//x轴
	             crosshair: true,
	        	 labels: {
	        	 		step:1,
                        align: 'right',
                        rotation: -45, //字体倾斜
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
                 point: {
                    events: {
                        click: function () {
                           window.location.href=this.options.url;
                        }
                    }
                }
            }
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
	            layout: 'vertical',//horizontal
	            align: 'right',
	            verticalAlign: 'top',
	            floating: true,
	            borderWidth: 0,
	            floating: true,
	            borderWidth: 1,
	            backgroundColor: '#FFFFFF',
	        //    title:{text:'业务类型分类:'}
	        },
		    series:series,
	 };
		oChart = new Highcharts.Chart(draw);
}
		//
		var title="<h3>广东省业务量业务类型分析</h3>";
		var rederTo="container";//指定ID
		var yName="度量值";
		var chartType="column";
$(function () {
		var drillType = getUrlParam("drillType");
		var url = '<%=request.getContextPath()%>/analysis/iniZzxsAndIndustrAnalysts.do?drillType='+drillType;
		if(_data_type_table)
			url += '&isBG=1';
				$.ajax({
  			 url: url,
				//	async : false,
					dataType : 'json',
					type : "POST", //请求方式
					success : function(data) {
					for(var i=0;i<data[0].length;i++){
					var html = "<option value="+data[0][i].measurecde+">"+data[0][i].measure+"</option>";
					$("#busType").append(html);
						}
					for(var k=0;k<data[1].length;k++){
					var html = "<option value="+data[1][k].c+">"+data[1][k].v+"</option>";
					$("#industrType").append(html);
						}
					}
				});
		
				var toDate = new Date();
				var strDate=toDate.getFullYear()+"-"+(toDate.getMonth()+1)+"-"+toDate.getDate(); //获取完整的年份(4位,1970-????)
				$("#showstarttime").val(strDate);
				$("#showendtime").val(strDate);
				var data={selType:"1",busType:"001",industrType:drillType};
				loadChars(title,rederTo,chartType,yName,data);
				$("#selData").bind("click",selData);
		});
	
	function selData(){
			//var selType=$("#selType").val();
			var selType=$('input[name="selType"]:checked ').val();
			var industrType=$("#industrType").val();
			var showstarttime=$("#showstarttime").val();
			var showendtime=$("#showendtime").val();
			var busType=$("#busType").val();
			var industr;
			if(industrType!=null){
			industr=industrType.join(",");
			}
			
			var data={industrType:industr,selType:selType,startdt:showstarttime,enddt:showendtime,busType:busType};
			loadChars(title,rederTo,chartType,yName,data);
	}
	
//获取数据
function loadChars(title,rederTo,chartType,yName,data){
	if(_data_type_table)
		data.isBG = 1;
	$.ajax({
  			 url: '<%=request.getContextPath()%>/analysis/zzxsAndIndustrycoSum.do',
				//	async : false,
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
				//	alert(xName.length);
					var regorgrStr="";
					for(var k=0;k<data.length;k++){
						var value=data[k].c;
						if(regorgrStr.indexOf("/"+value+"/")==-1){
						regorgrStr=regorgrStr+"/"+value;
						}
					}
					var regArray=regorgrStr.split("/");
					for(var i=1;i<regArray.length;i++){
					if(regArray[i]!=regArray[i-1]){
					//按什么来分类 --- 图例部分
					series.push(groupSeries(data,regArray[i],xName));
					}
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
		//var selType=$("#selType").val();
		var selType=$('input[name="selType"]:checked ').val();
		var showstarttime=$("#showstarttime").val();
		var showendtime=$("#showendtime").val();
		for(var k=0;k<xName.length;k++){
			//alert(data.length);
			for(var i=0;i<data.length;i++){
			if(name==data[i].c && data[i].t==xName[k]){
					var  seriesData; 
					var date=data[i].s;
					seriesData={dataLabels: {enabled: true,align:'left'},y:date};
					transdt.push(seriesData);
					break;
					}else{
					if(i==data.length-1){
					var date=0;	
					var  seriesData; 
					seriesData={dataLabels: {enabled: true,align:'left'},y:date};
					transdt.push(seriesData);
						}
					}
				}
			}
		//alert(transdt);
		var seriesJson={"data":transdt,"name":name};
		return seriesJson;
	}
	//regorgsCode  regorgsValue
	function  groupxName(data){
		var transdt=new Array();
		var transtr="";
		for(var i=0;i<data.length;i++){
				var date=data[i].t;
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
 			 日期类型：<input type="radio" name="selType" value="1" checked="checked" />日 
			<input type="radio" name="selType" value="3" />月
            
            <input id="showstarttime" type="text" readonly="readonly" class="Wdate" onclick="createWdatePickerStart();" />
        		TO
            <input id="showendtime" type="text" readonly="readonly" class="Wdate" onclick="createWdatePickerEnd();" />        
    	<!-- 按范围划分
    		<select id="regorg" name="dept" style="width: 150px;" onChange="scopeChange()">
    			<option value="city">按各级地市划分</option>
    			<option value="area">按区域划分</option>
    		</select>  -->
	    	 度量维度
		<select id="busType" name="dept" style="width: 150px;"></select>
    		
    	  组织形式
    		<select id="industrType" multiple="multiple" name="dept" style="width: 150px;">
    		</select>
	 <input id="selData" type="button" value="查询"/>    
	<span id="container" style="width:70%"></span>
	
	
	
</body>
</html>