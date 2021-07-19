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
	
<script src="<%=request.getContextPath()%>/static/script/jquery.bgiframe.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.multiSelect.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/static/script/jquery.multiSelect.css" rel="stylesheet" type="text/css" media="all">
<link href="<%=request.getContextPath()%>/static/css/analysis/analysis.css" rel="stylesheet" type="text/css" media="all">

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
	            text: title,
	            useHTML:true
	        },
	        subtitle: {
	            text: ''
	        },
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
            series: {
            	pointWidth: 10,
                cursor: 'pointer',
                 point: {
                    events: {
                        click: function (e) {
                            location.href = this.options.url;
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
		    drilldown: {series: []}
	 };
		oChart = new Highcharts.Chart(draw);
}
		var title="<h3>广东省业务量业务类型分析</h3>";
		var rederTo="container";//指定ID
		var yName="度量值";
		var chartType="column";
$(function () {
	
				$.ajax({
  			 url: '<%=request.getContextPath()%>/analysis/iniBusinessAnalystsBusiness.do',
				//	async : false,
					dataType : 'json',
					type : "POST", //请求方式
					//data:{regrogCode:"city"},
					success : function(data) {
					for(var i=0;i<data[0].length;i++){
					var html="<option value="+data[0][i].code+">"+data[0][i].value+"</option>"
						$("#selArea1").append(html);
					}
					
					loadMultiSelect("selArea1");
				
					for(var k=0;k<data[1].length;k++){
					var html="<option value="+data[1][k].value+">"+data[1][k].code+"</option>"
						$("#selArea2").append(html);
					}
					
					loadMultiSelect("selArea2");
					$('#selArea1').hide();
				    
				    for(var k=0;k<data[2].length;k++){
					var html="<option value="+data[2][k].code+">"+data[2][k].value+"</option>"
						$("#accepttype").append(html);
					}
					loadMultiSelect("accepttype");
					}
				});
				
				var toDate = new Date();
				//var strDate=toDate.getFullYear()+"-"+(toDate.getMonth()-5)+"-"+toDate.getDate(); //获取完整的年份(4位,1970-????)
				var endDate=toDate.getFullYear()+"-"+(toDate.getMonth()+1)+"-"+toDate.getDate(); //获取完整的年份(4位,1970-????)
				$("#showstarttime").val(endDate);
				//$("#showendtime").val(endDate);
				$("#regType").val("area");
				$("#businessCount").val("1");
				var data={selType:"1",businessCount:"1"};
				loadChars(title,rederTo,chartType,yName,data);
				$("#selData").bind("click",selData);
		});
	
	//加载多选框
	function  loadMultiSelect(name){
					$("#"+name).multiSelect({   
					    selectAll: false,  
					    oneOrMoreSelected: '*',  
					    selectAllText: '全选',  
					    noneSelected: '请选择'  
					}, function(){ 
						  //回调函数  
					    if($("[name='"+name+"Ids']:checked").length > 0)  
					    {  
					        $("#errRed").empty();  
					    }  
					    else  
					    {  
					        $("#errRed").text("请选择");  
					    }  
				});
	}
	//获取多选框的值
/* 	function loadMultiSelectValue(name){ 
	if ($('input[name="'+name+'[]"]:checked').length < 1) { 
		}  else {  
		 var v = '';  
			 $('input[name="'+name+'[]"]:checked').each(function() { 
				 if (v != '') { 
					 v += ',';
				 } 
				 v += $(this).val();
			});  
		} 
	} */
	function getSelected(name){
		var t = "";
		$("#"+name).next().find("label[class='checked'] input").each(function(index,element){ t += element.value + ','; });;
		t=t.substring(0,t.length-1);
		return t;
	}

	function selData(){
			var selType=$('#Header input[name="gender"]:checked ').val();
			var regType=$("#regType").val();
			var showstarttime=$("#showstarttime").val();
			var showendtime=$("#showendtime").val();
			var businessCount=$("#businessCount").val();
			var accepttype=getSelected("accepttype");
			/* var selArea;
			if(regType=="selArea1"){
			selArea=getSelected();
			}else{
			selArea=$("#selArea2").val();
			};
			var selStr;
			if(selArea!=null){
			selStr=selArea.join(",");
			} */
			var data={selArea:getSelected(regType),regType:regType,startdt:showstarttime,accepttype:accepttype,businessCount:businessCount};
			loadChars(title,rederTo,chartType,yName,data);
	}
	
//获取数据
function loadChars(title,rederTo,chartType,yName,data){
	$.ajax({
  			 url: '<%=request.getContextPath()%>/analysis/businessAnalystsType.do',
				//	async : false,
					dataType : 'json',
					type : "POST", //请求方式
					data:data,
					success : function(data) {
					if(data[0]==undefined){
					drawHighCharts(rederTo,chartType,title,null,yName,null,null);
					oChart.setTitle({ text: '<h4>返回报表为空</h4>'});
					oChart.setTitle(
           				 { style: { color: 'red' } }
        				);
					}else{
					var xName=groupxName(data);
					var series=new Array();
					var regorgrStr="";
					for(var k=0;k<data.length;k++){
						var regorgName=data[k].ywvalue;
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
					drawHighCharts(rederTo,chartType,title,xName,yName,series,xName.length>0?xName.length-1:0);
						} 
					}
				});
	}

	//根据时间作为X轴。类别作为具体项的情况下
	function  groupSeries(data,name,xName){
		var transdt=[];
		var selType=$('#Header input[name="gender"]:checked ').val();
		var showstarttime=$("#showstarttime").val();
		//var showendtime=$("#showendtime").val();
		for(var k=0;k<xName.length;k++){
			for(var i=0;i<data.length;i++){
			var url="<%=request.getContextPath()%>/page/analysis/Business/ywfx_ywlxlfx_dsfx.jsp";
			if(name==data[i].ywvalue && data[i].qyvalue==xName[k]){
					var  seriesData; 
					var date=data[i].measurevalue;
					seriesData={x:xName[k],dataLabels: {enabled: true,align:'left'},y:date,url:url+"?drillType="+data[i].ywcode+"&showstarttime="+showstarttime};
					transdt.push(seriesData);
					break;
					}else{
					if(i==data.length-1){
					var date=0;	
					var  seriesData; 
					//seriesData={name:data[i].organizationmode,dataLabels: {enabled: true,align:'left'},y:date,url:url+"?organizationmode="+data[i].organizationmode+"&showstarttime="+showstarttime};
					seriesData={dataLabels: {enabled: true,align:'left'},y:date,url:url+"?drillType="+data[i].ywcode+"&showstarttime="+showstarttime};
					transdt.push(seriesData);
						}
					}
				}
			}
		var seriesJson={"data":transdt,"name":name};
		return seriesJson;
	}
	//regorgsCode  regorgsValue
	function  groupxName(data){
		var transdt=new Array();
		var transtr="";
		for(var i=0;i<data.length;i++){
				var date=data[i].qyvalue;
				if(transtr.indexOf(date)==-1){
					transtr=transtr+"/"+date;
			}
		}
		var strList=transtr.split("/");
		for(var j=1;j<strList.length;j++){
			transdt.push(strList[j]);
		}
		//问题多出个x==1
		/* for(var j=0;j<strList.length-1;j++){
			transdt.push(strList[j]);
		} */
		return transdt;
	}
	
	function getUrlParam(name) {
         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
         var r = window.location.search.substr(1).match(reg);  //匹配目标参数
         if (r != null) return unescape(r[2]); return null; //返回参数值
     }
	
	function changeArea(event){
		var reg=$(event).val();
		if(reg!=null){
		//$("#"+reg+" .multiSelectOptions").find("label[class='checked'] input").removeClass("checked");
		//$("#"+reg).find("label[class='checked'] input").removeClass("checked");
		}
		if(reg=="selArea1"){
			$("#selArea2").hide();
			$("#selArea1").show();
		}else{
			$("#selArea1").hide();
			$("#selArea2").show();
		}
	}
   </script>
</head>
<body>
	
<div id="Container">
    <div class="headertop" id="Header" >
       	时间维度: <input style="display:none" class="btu" type="radio" name="gender" id="x" value="0" checked="checked"><label style="display:none" for="x">日</label>
        <input style="display:none" class="btu" type="radio" name="gender" id="y" value="1"><label style="display:none" for="y">月</label>
		<label style="display:none" id="showstart" for="开始">开始</label><input class="btu Wdate" style="width:130px" id="showstarttime" type="text" readonly="readonly" 
			value='2016-05-01' onclick="createWdatePickerStart();" /> 
		<label style="display:none" id="showend" for="结束">结束</label><input style="display:none" class="btu Wdate" id="showendtime" type="text" readonly="readonly" 
			value='2016-07-01' onclick="createWdatePickerEnd();"/>
		<label >固定指标:</label><select id="businessCount" name="dept" class="btu_select" style="width: 120px;">
    			<option value="1">本期业务量</option>
    			<option value="2">期末业务量</option>
    		</select> 
    	<label >业务类型:</label><select id="accepttype" name="dept" class="btu_select" style="width: 120px;">
    		</select>
		<label >区域维度:</label>
    		<select id="regType"  class="btu_select"  onchange="changeArea(this)" name="dept" style="width: 130px;">
    			<option value="selArea2">按区域分析</option>
    			<option value="selArea1">按各级地市分析</option>
    		</select>
		<label >范围:</label>
		<select id="selArea2"  multiple="multiple" name="dept" style="float:left;width: 150px;">
    		</select>
		<select id="selArea1" multiple="multiple" name="dept" style="display:none;width: 150px;float:left;">
    		</select>
    		
		<input class="bt" id="selData" type="button" value="查询" /> 
    </div>
    <div id="Content">
       	<div id="container" style="width: 98%;"> 
		</div>
    </div>
</div>


	
</body>
</html>


