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
<script src="<%=request.getContextPath()%>/static/js/analysis/picker.js"
	type="text/javascript"></script>

<script src="<%=request.getContextPath()%>/static/script/jquery.bgiframe.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.multiSelect.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/static/script/jquery.multiSelect.css" rel="stylesheet" type="text/css" media="all">
<link href="<%=request.getContextPath()%>/static/css/analysis/analysis.css" rel="stylesheet" type="text/css" media="all">

<title>企业设立登记-按经济性质分析</title>
<script type="text/javascript">

$(function () {
	//init measure data
	initMeasureData();
	$("#selData").bind("click", initCharts);
	$("#selData_2").bind("click", initSecCharts);
	$("#busType").bind("change",changeBusType);
	$("#chartChange").bind("click", showFir);
	
});

//全局变量
var secCharts = null; //缓存第二层chart
var secXs = null;  //缓存第二层X轴值
var secYs = null; //缓存第二层y轴值
var xs; //x轴
var y1,y2,y3; //y轴

//var codes = null; //对应产业类型
//第二层数据
function initSecCharts(){
	var selType=$("#selType").val();
	var showstarttime=$("#showstarttime").val();
	var showendtime=$("#showendtime").val();
    var measureCode = getSelectedMeasureCode();
    //initSecondAna(startTime,endTime,dateType, code, m_t, title1, title2, economicCode){
    initSecondAna(showstarttime,showendtime,selType, measureCode, 
    		     //$("#induType").val(), $("#induType option:selected").text(), 
    		     -1, "", 
    		     $("#busType option:selected").text(), getSelectedIndusCode());
}
//第一层
function initCharts(){
	//设置加载gif @TODO
	getData(function(data){
		//{"t":"2016-06-01","s":328443307.900251,"c":"1"}数据格式,c=1|2|3 第一二三经济，可能为空
		xs = new Array(); 
	    y1 = new Array(); y2 = new Array(); y3 = new Array();
		if(data != null && data.length > 0){//组装数据
			insertNew();  //初始化;
			xs.push(data[0].t);
			type(data[0].c)[0] = parseInt(data[0].s);
			var j = 0;
			var t_a = null;
			for(var i = 1; i < data.length; i ++){
				t_a = type(data[i].c);
				if(xs[j] == data[i].t){ //日期相同累计
					t_a[j] += parseInt(data[i].s);
				}else{
					xs.push(data[i].t);
					insertNew();
					t_a[++j] = parseInt(data[i].s);
				}
			}			
		}
		drawHighCharts(xs,y1,y2,y3,$("#busType option:selected").text(), getSelectedMeasureCode(),j);
	});
}
//获取measureCode
function getSelectedMeasureCode(){
	//return $("#busType option:selected").val();
	var t = "";
	$("#busType option:selected").each(function(index,element){ t += element.value + ','; }); 
	return t;
}

//获取industryCode ,2级
function getSelectedIndusCode(){
	var t = "";
	$(".multiSelectOptions").find("label[class='checked'] input").each(function(index,element){ t += element.value + ','; }); 
	return t;
}

//度量维度切换 @TODO
function changeBusType(){
	/* var t = null;
	$("#busType option:selected").each(function(index,element){
	}); */
	//initCharts();
	// var vas = $("#busType option:selected");
}

function insertNew(){
	y1.push(0);y2.push(0);y3.push(0);
}
//根据code判断产业类型
function type(c){
 	if(c == _undefined_name){
 		return new Array(); 
 	}
	if(c == "1")
		return y1; 
	else if(c == "2")
		return y2;
	else
		return y3;
}
	
//获取数据
function getData(exe){
	var selType=$("#selType").val();
	var showstarttime=$("#showstarttime").val();
	var showendtime=$("#showendtime").val();
    var measureCode = getSelectedMeasureCode();
    if(measureCode == null) {
    	alert("请求数据不正确！");
    	return ;
    }
	var data={dateType:selType,startTime:showstarttime,endTime:showendtime,
			 measureCode:measureCode};
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/economicPageMarketSubjectInfo.do',
	    //	async : false,
		dataType : 'json',
		type : "POST", //请求方式
		data:data,
		success : function(data) {
			exe(data);
		}
	});
}


//画图
function drawHighCharts(xs,y1,y2,y3,title, code,j){
	$('#container').highcharts({
        chart: {type: 'column'},
        title: {text: '企业设立登记'},
        subtitle: {text: '按经济性质分析'},
    	scrollbar: {enabled: true},
        xAxis: {categories: xs,
        	max: Math.ceil(j/5)-1>0?5:Math.ceil(j%5),
			min: 0,
            labels: {rotation: -45,align: 'right',style: { font: 'normal 13px 宋体'}}
        },
        yAxis: {min: 0,title: {text: (title == null ? '值' : title)}},
        legend: {
            layout: 'vertical',align: 'right',verticalAlign: 'top',
            x: -0,y: 0,floating: true,borderWidth: 1,
            backgroundColor: '#FFFFFF',title:{text:'经济性质:'}
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0,
				events: {
				    click: function(e) {
					   //alert(e.point.series.name + e.point.category);\
					   initIndusCodes(this.options.m_t);
					   //initSecondAna(startTime,endTime,dateType, code, m_t, title1, title2, economicCode){
					   initSecondAna(e.point.category, e.point.category, $("#selType").val(),
							   code, this.options.m_t, e.point.series.name, title, null);
					}
			    },
            },
        },
        series: [{ m_t: 1, name: '内资',data: y1,}, 
                 { m_t: 2, name: '私营',data: y2, }, 
                 { m_t: 3, name: '外资',data: y3, }]
    });
}

function initIndusCodes(m_t){
	//根据第一经济性质加载第二曾
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/economicCodeInfo.do',
		dataType : 'json',
		type : "POST", //请求方式
		data:{},
		success : function(data) {
			$("#indusType").html('');
			//[{"v":"内资（非私营）","c":"1","pc":"1","pv":"内资"},{"v":"国有","c":"11","pc":"1","pv":"内资"}]
			for(var i = 0; i < data.length; i ++){
				if(data[i].pc == m_t)
					$("#indusType").append("<option selected value='" + data[i].c + "'>" + data[i].v + "</option>");
			}
		}
	});
}

//加载第二层数据       时间  measure 产业类型
function initSecondAna(startTime,endTime,dateType, code, m_t, title1, title2, economicCode){
	//加载charts图表
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/economicPageMarketSubjectInfo.do',
	    //	async : false,
		dataType : 'json',
		type : "POST", //请求方式
		data:{startTime:startTime, endTime:endTime, measureCode:code, parentCode:m_t,
			 dateType:dateType,economicCode:economicCode, isSecondData:'1'},
		success : function(data_all) {
			showSec();
			if(data_all != null){
				var x = new Array();
				var series = new Array();
				var names = new Array();
				if(data_all.length > 0) {
					x.push(data_all[0].t);
					names.push(data_all[0].n);
					var i = 1;
					var j = 0;
					//日期以及名称去重复
					for( ; i < data_all.length; i ++){
						if(data_all[i].t != x[j]){//新增时间点
							x.push(data_all[i].t);
							j ++;
						}
						//名字不重复列表
						addIfAbsent(names, data_all[i].n);
					}
					//拼装series数据,series数组长度应为名称数组长度
					for(i = 0; i < names.length; i ++) {
						series.push({name:names[i],data: new Array()});
					}
					//插入数据
					var obj_t;
					for(i = 0 ; i < data_all.length; i ++){
						(series[getIndex(names, data_all[i].n)]).data.push({x:getIndex(x,data_all[i].t),y:data_all[i].s});
					}
					//组装charts数据
				}
				//画图
				$('#container2').highcharts({
			        chart: {type: 'column'},
			        title: {text: title1 + " - " + title2},
			        subtitle: {text: "[" + startTime + "," + endTime + "]"},
			        scrollbar: {enabled: true},
			        xAxis: {categories: x,
			        	max: Math.ceil(j/5)-1>0?5:Math.ceil(j%5),
						min: 0,
			            labels: {rotation: -45,align: 'right',style: { font: 'normal 13px 宋体'}}
			        },
			        yAxis: {min: 0,title: {text: ''}},
			        legend: {
			            layout: 'vertical',align: 'right',verticalAlign: 'top',
			            x: -0,y: 0,floating: true,borderWidth: 1,
			            backgroundColor: '#FFFFFF',title:{text:''}
			        },
			        tooltip: {
			            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			                '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
			            footerFormat: '</table>',
			            shared: true,
			            useHTML: true
			        },
			        series: series
			    });
				//secCharts = $('#container2').highcharts();

			}
		}
	});
}

//返回数组中位置
function getIndex(array, value){
	for(var i = 0 ; i < array.length; i ++){
		if(array[i] == value)
			return i;
	}
	return -1;
}

//数组中不存在则添加
function addIfAbsent(array, value){
	for(var i = 0; i < array.length; i ++){
		if(array[i] == value)
			return;
	}
	array.push(value);
}

//复选框函数
function indusChange(obj){
	/* var index = $(obj).val();
	var name = $(obj).attr('t');
	if(secCharts == null) 
		return;
	if(obj.checked){ //选中
		secCharts.series[0].addPoint([index, secYs[index]]);
	}else{//复选框取消则需要删除统计图中的column
		secCharts.series[0].removePoint(index);
		secCharts.xAxis[0].setExtremes(0, 100);
	} */
}

//两个div切换显示
function showFir(){
	$("#container_sec").hide();
	$("#container_fir").show();
}
function showSec(){
	$("#container_fir").hide();
	$("#container_sec").show();
}
//加载下拉框数据
function initMeasureData(){
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/measureInfo.do',
	    //	async : false,
		dataType : 'json',
		type : "POST", //请求方式
		data:{},
		success : function(data) {
			//[{mn=期末实有户数, mc=001001, mk=0}, {mn=本期登记户数, mc=001002, mk=1}] 
			if(data != null){
			    if(data.length > 0)
			    	$("#busType").append("<option selected value='" + data[0].mc + "' mk='"+(data[0].mk==null ? 0 : data[0].mk)+"'>" + data[0].mn + "</option>");
				for(var i = 1; i < data.length; i ++) {
					$("#busType").append("<option value='" + data[i].mc + "' mk='"+(data[i].mk==null ? 0 : data[i].mk)+"'>" + data[i].mn + "</option>");
				}
				initCharts();
			}
 		}
		//
	});
}


   </script>
</head>
<body>
	<select id="selType" name="dept" style="width: 150px;">
		<option value="0">按日</option>
		<option value="1">按月</option>
	</select> 选中时间段
	<input id="showstarttime" type="text" readonly="readonly" class="Wdate" value='2016-05-01'
		onclick="createWdatePickerStart();" /> 到
	<input id="showendtime" type="text" readonly="readonly" class="Wdate" value='2016-07-01'
		onclick="createWdatePickerEnd();" /> 度量维度
	<select id="busType" name="dept" style="width: 150px;">
		<!-- <option value="0">本期</option>
		<option value="1">期末</option> -->
	</select>
	<div/>
	<span id="container_fir" style="width: 70%">
		<input id="selData" type="button" value="查询" />
		<span id="container" style="width: 70%"></span>
	</span>
	
	<span id="container_sec" style="width: 70%;display:none">
	    <!-- <select id="induType" name="dept_1" style="width: 150px;">
	    	<option selected value='0'>第一产业</option>
	    	<option value='1'>第二产业</option>
	    	<option value='2'>第三产业</option>
		</select> -->
		二级经济性质：<select id="indusType" multiple name="dept_1" style="width: 150px;"></select>
		<input id="selData_2" type="button" value="查询" />
	    <input type="button" id="chartChange" value="返回"/>
	    <span id="container2" style="width: 70%"></span>
	    </br>
	    <!-- <span id="allIndus">
	    	
	    </span> -->
	</span>



</body>
</html>
