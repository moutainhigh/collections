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
	
<script
	src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/static/js/analysis/picker.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/analysis/commonUtil.js"
	type="text/javascript"></script>	
<script src="<%=request.getContextPath()%>/static/script/jquery.bgiframe.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.multiSelect.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/static/script/jquery.multiSelect.css" rel="stylesheet" type="text/css" media="all">
<link href="<%=request.getContextPath()%>/static/css/analysis/analysis.css" rel="stylesheet" type="text/css" media="all">
	
<style type="text/css">
	h4{
	margin-top:60%
	}
</style>
<title>组织形式-产业分析</title>
<script type="text/javascript">
var t = "${param.at}";
_data_type_table = "${param.bt}";
changeTitle(t);
//init measure data && set titile
/* if("1" == t){//为市场主体概况，否则为设立登记
	document.title = '市场主体概况' + document.title.substr(6, document.title.length);
} */
  //定义一个Highcharts的变量，初始值为null
  var oChart = null;
  var draw = null;
  
  function drawHighCharts(rederTo,chartType,title,xName,yName,series,j){
   draw = {
		 chart: { 
		        renderTo: rederTo, 
		   	    type: chartType,
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
	       /*  subtitle: {
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
	             max: Math.ceil(j/8)-1>0?8:Math.ceil(j%8),
			 	 min: 0,
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
            column: {
            	pointWidth: 10,
                cursor: 'pointer',
                 point: {
                    events: {
                        click: function (e) {
                         //location.href = this.options.url+"&startdt="+e.point.category;
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
	        },/* tooltip: {
	         
	        }, */
	        credits: {
	        	text:"",
	        	href:''
	        },
	        legend: {
	            layout: 'vertical',//horizontal
	            align: 'right',
	            verticalAlign: 'top',
	            borderWidth: 0,
	            floating: true,
	            borderWidth: 1,
	            backgroundColor: '#FFFFFF',
	        //    title:{text:'业务类型分类:'}
	        },
		    series:series,
		    drilldown: {series: []}
	 };
		oChart = new Highcharts.Chart(draw);
}
		//
		var title="<h3>企业设立登记组织形式分和产业析</h3>";
		var rederTo="container";//指定ID
		var yName="度量值";
		var chartType="column";
$(function () {
			var t = "${param.at}";
			//init measure data && set titile
			if("0" == t){//为市场主体概况，否则为设立登记
				document.title = '市场主体概况' + document.title.substr(6, document.title.length);
				$("#businessCount").append("<option selected value='001001'>期末实有户数</option>");
				$("#businessCount").append("<option value='002001'>期末实有注册资本(金)(万元)</option>");
			}else if("1" == t){
				$("#businessCountLabel").hide();
				$("#businessCount").hide();
				$("#businessCount").append("<option selected value='001002'>本期登记户数</option>");
			//	$("#businessCount").append("<option value='002005'>本期登记注册资本(金)(万元)</option>");
			}else if("2" == t){
				$("#businessCount").append("<option selected value='001001'>期末实有户数</option>");
				$("#businessCount").append("<option value='002001'>期末实有注册资本(金)(万元)</option>");
			}else{
				$("#businessCount").append("<option selected value='001005'>本期注吊销户数</option>");
				$("#businessCount").append("<option value='002009'>本期注销注册资本(金)(万元)</option>");
			}

				var toDate = new Date();
				var strDate=toDate.getFullYear()+"-"+(toDate.getMonth()+1)+"-"+(toDate.getDate()-7); //获取完整的年份(4位,1970-????)
				var entDate=toDate.getFullYear()+"-"+(toDate.getMonth()+1)+"-"+toDate.getDate(); //获取完整的年份(4位,1970-????)
				$("#showstarttime").val(strDate);
				$("#showendtime").val(entDate);
				var data={selType:"0",level:"1",startdt:strDate,enddt:entDate,businessCount:"001001,001003,001004"};
				loadChars(title,rederTo,chartType,yName,data);
				$("#selData").bind("click",selData);
		});
	function selData(){
		var selType = $('input[name="gender"]:checked ').val();
			var showstarttime=$("#showstarttime").val();
			var showendtime=$("#showendtime").val();
			var businessCount=$("#businessCount").val();
			var data={level:"1",selType:selType,startdt:showstarttime,enddt:showendtime,businessCount:businessCount};
			loadChars(title,rederTo,chartType,yName,data);
	}
	
//获取数据
function loadChars(title,rederTo,chartType,yName,data){
	if(_data_type_table)
		data.isBG = 1;
	$.ajax({
  			 url: '<%=request.getContextPath()%>/analysis/zzxsAndIndustrycoAnalystsType.do',
				//	async : false,
					dataType : 'json',
					type : "POST", //请求方式
					data:data,
					success : function(data) {
					if(data[0]==undefined){
					drawHighCharts(rederTo,chartType,title,null,yName,null,data.length);
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
						var regorgName=data[k].c;
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
					drawHighCharts(rederTo,chartType,title,xName,yName,series,data.length-1);
						} 
					}
				});
	}

	//根据时间作为X轴。类别作为具体项的情况下
	function  groupSeries(data,name,xName){
		var transdt=[];
		//var selType=$("#selType").val();
		var selType=$('input[name="gender"]:checked ').val();
		var showstarttime=$("#showstarttime").val();
		var showendtime=$("#showendtime").val();
		for(var k=0;k<xName.length;k++){
			//alert(data.length);
			for(var i=0;i<data.length;i++){
			var url="<%=request.getContextPath()%>/page/analysis/marketSubject/qysldj/qysldj_zzxsfx_drillzzhcyfx.jsp";
			if(name==data[i].c && data[i].t==xName[k]){
					var  seriesData; 
					var date=data[i].s;
					seriesData={/*dataLabels: {enabled: true,align:'left'},*/y:date,url:url+"?drillType="+data[i].d+"&showstarttime="+showstarttime+"&showendtime="+showendtime};
					transdt.push(seriesData);
					break;
					}else{
					if(i==data.length-1){
					var date=0;	
					var  seriesData; 
					seriesData={/*dataLabels: {enabled: true,align:'left'},*/y:date,url:url+"?organizationmode="+data[i].organizationmode+"&showstarttime="+showstarttime+"&showendtime="+showendtime};
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
	<div id="Container">
    <div class="headertop" id="Header">
				时间维度: <input style="display:none" class="btu" type="radio" name="gender" id="x" value="0" checked="checked"><label style="display:none" for="x">日</label>
        <input style="display:none" class="btu" type="radio" name="gender" id="y" value="1"><label style="display:none" for="y">月</label>
		<label style="display:none" id="showstart" for="开始">开始:</label><input class="btu" id="showstarttime" type="text" readonly="readonly" class="Wdate" 
			value='2016-05-01' onclick="createWdatePickerStart();" /> 
		<label style="display:none" id="showend" for="结束">结束:</label><input style="display:none" class="btu" id="showendtime" type="text" readonly="readonly" class="Wdate"
			value='2016-07-01' onclick="createWdatePickerEnd();"/>
		<label id="businessCountLabel" for="分析指标">分析指标:</label><select class="btu_select" id="businessCount" name="dept" style="width: 150px;"></select>
		<label style="display:none" id="organizationTypeLabel" for="组织形式">组织形式:</label><select class="btu_select" id="organizationType" name="dept_1" style="width: 150px;display:none"></select>
		<input class="bt" id="selData" type="button" value="提交" /> 
    	</div>
	     <div id="Content">
				<div id="container_fir" style="width: 98%;"> 
					<div id="container"></div>
				</div>
	    </div>
    </div>
	

	
</body>
</html>