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
	src="<%=request.getContextPath()%>/chart/Highstock-4.2.3/js/modules/no-data-to-display.src.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/analysis/picker.js"
	type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/static/js/analysis/commonUtil.js"
	type="text/javascript"></script>


<script src="<%=request.getContextPath()%>/static/script/jquery.bgiframe.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.multiSelect.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/static/script/jquery.multiSelect.css" rel="stylesheet" type="text/css" media="all">
<link href="<%=request.getContextPath()%>/static/css/analysis/analysis.css" rel="stylesheet" type="text/css" media="all">

<title>全省企业及经济分布情况</title>

<script type="text/javascript">

$(function () {
	var t = "${param.at}";
	document.title = initMCode(t) + ' - ' + document.title;
	_data_type_table = "${param.bt}";
	/* var tableName;
	//init measure data && set titile
	if("1" == t){//为市场主体概况，否则为设立登记
		document.title = '市场主体概况' + document.title.substr(6, document.title.length);
		//$("#busType").append("<option selected value='001001'>期末实有户数</option>");
		//$("#busType").append("<option value='002001'>期末实有注册资本(金)(万元)</option>");
		tableName="t_analysis_regorg";
	}else{
		tableName="t_analysis_regorg";
		//$("#busType").append("<option selected value='001002'>本期登记户数</option>");
		//$("#busType").append("<option value='002005'>本期登记注册资本(金)(万元)</option>");
	} */
	$("#chartChange").hide();
	initCharts();//加载图形
	//init measure data
	//initMeasureData(tableName);
	$("#selData").bind("click", initCharts);
	$("#selData_2").bind("click", initSecCharts);
	$("#busType").bind("change",changeBusType);
	$("#chartChange").bind("click", showFir);
	
	
	var selType=$('#Header input[name="gender"]:checked ').val();
	$("#areaScopeLabe").hide();
	$("#areaScope").hide();
	$("#selData_2").hide();

});

//全局变量
var secCharts = null; //缓存第二层chart
var secXs = null;  //缓存第二层X轴值
var secYs = null; //缓存第二层y轴值
var xs; //x轴
var y1,y2,y3,y4,y5,y6,y7,y8,y9,y10; //y轴

//var codes = null; //对应产业类型
//第二层数据
function initSecCharts(){
	var selType=$('#Header input[name="gender"]:checked ').val();
	var showstarttime=$("#showstarttime").val();
	var showendtime=$("#showendtime").val();
    var measureCode = getSelectedMeasureCode();
    initSecondAna(showstarttime,showendtime,selType, measureCode, 
    		     //$("#induType").val(), $("#induType option:selected").text(), 
    		     -1, "", 
    		     $("#busType option:selected").text(), getSelectedIndusCode());
}
//第一层
function initCharts(){
	//设置加载gif @TODO
	getData(function(data){
		xs = new Array(); 
	    y1 = new Array(); y2 = new Array(); y3 = new Array(); y4 = new Array(); y5 = new Array();
		y6 = new Array(); y7 = new Array(); y8 = new Array(); y9 = new Array(); y10 = new Array();
		if(data[0] != null && data[0].length > 0){//组装数据
			insertNew();  //初始化;
			xs.push(data[0][0].t);
			type(data[0][0].c)[0] = parseInt(data[0][0].s);
			var j = 0;
			var t_a = null;
			for(var i = 1; i < data[0].length; i ++){
				t_a = type(data[0][i].c);
				if(xs[j] == data[0][i].t){ //日期相同累计
					t_a[j] += parseInt(data[0][i].s);
				}else{
					xs.push(data[0][i].t);
					insertNew();
					t_a[++j] = parseInt(data[0][i].s);
				}
			}			
		}
		
		if(data[1] != null && data[1].length > 0){//组装数据
			insertNew1();  //初始化;
			//xs.push(data[1][0].t);
			type1(data[1][0].c)[0] = parseInt(data[1][0].s);
			var j = 0;
			var t_a = null;
			for(var i = 1; i < data[1].length; i ++){
				t_a = type1(data[1][i].c);
				if(xs[j] == data[1][i].t){ //日期相同累计
					t_a[j] += parseInt(data[1][i].s);
				}else{
					xs.push(data[1][i].t);
					insertNew1();
					t_a[++j] = parseInt(data[1][i].s);
				}
			}			
		}
		drawHighCharts(xs,y1,y2,y3,y4,y5,y6,y7,y8,y9,y10,$("#busType option:selected").text(), getSelectedMeasureCode(),j);
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

//分析指标切换 @TODO
function changeBusType(){
	/* var t = null;
	$("#busType option:selected").each(function(index,element){
	}); */
	//initCharts();
	// var vas = $("#busType option:selected");
}

function insertNew(){
	y1.push(0);y2.push(0);y3.push(0);y4.push(0);y5.push(0);
}

function insertNew1(){
	y6.push(0);y7.push(0);y8.push(0);y9.push(0);y10.push(0);
}

//根据code判断产业类型
function type(code2){
 	if(code2 == _undefined_name){
 		return new Array(); 
 	}
	if(code2 == "广东省" || code2 == '省局')
		return y1; 
	else if(code2 == "珠三角")
		return y2;
	 else if(code2 == "粤东")
		return y3;
	else if(code2 == "粤西")
		return y4;
	else
		return y5;
}

function type1(code2){
	if(code2 == "广东省")
		return y6; 
	else if(code2 == "珠三角")
		return y7;
	 else if(code2 == "粤东")
		return y8;
	else if(code2 == "粤西")
		return y9;
	else
		return y10;
}
//获取数据
function getData(exe){
	var selType=$('#Header input[name="gender"]:checked ').val();
	var showstarttime=$("#showstarttime").val();
	var showendtime=$("#showendtime").val();
    var measureCode = getSelectedMeasureCode();
    if(measureCode == null) {
    	alert("请求数据不正确！");
    	return ;
    }
	var data={dateType:selType,startTime:showstarttime,endTime:showendtime,measureCode:measureCode};
	if(_data_type_table)
		data.isBG = 1;
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/areaInfo.do',
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
function drawHighCharts(xs,y1,y2,y3,y4,y5,y6,y7,y8,y9,y10,title, code,j){
	$('#container').highcharts({
        chart: {type: 'column'},
        title: {text: '全省企业及经济分布情况'},
        subtitle: {text: ''},
        xAxis: {categories: xs,
        	max: Math.ceil(j/8)-1>0?8:Math.ceil(j%8),
			min: 0,
            labels: {rotation: -45,align: 'right',style: { font: 'normal 13px 宋体'}}
        },
         scrollbar : {
		            enabled:true
		        },
        yAxis: [{min: 0,title: {text: (title == null ? '产量值' : title)}}],
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'top',
            x: -0,y: 0,
            //floating: true,
            borderWidth: 1
            //backgroundColor: '#FFFFFF',title:{text:''}
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
				       //window.open("industryAnaByMType.jsp?time=" + e.point.category
				       //	       +"&measureCode=" + code + "&type=" + this.options.m_t ,"_blank"); 
					   initIndusCodes(this.options.m_t,e.point.series.name);
					   /*  initSecondAna(e.point.category, e.point.category,$('#Header input[name="gender"]:checked ').val(),
							   code, this.options.m_t, e.point.series.name, title, null);  */
					   initSecondAna($("#showstarttime").val(), $("#showendtime").val(),$('#Header input[name="gender"]:checked ').val(),
								   code, this.options.m_t, e.point.series.name, title, null)
					}
			    },
            },
        },
        series: [
                 { m_t: 0, name: '广东省',data: y1,}, 
                 { m_t: 1, name: '珠三角',data: y2,}, 
                 { m_t: 2, name: '粤东',data: y3, },
                 { m_t: 3, name: '粤西',data: y4, },
                 { m_t: 4, name: '粤北',data: y5, }]
    });
}

function initIndusCodes(m_t,seriesName){
	//根据区域加载市级列表
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/sencondAreaInfo.do',
		dataType : 'json',
		type : "POST", //请求方式
		data:{type:m_t},
		success : function(data) {
			$("#areaScope").html('');
			for(var i = 0; i < data.length; i ++){
				if(seriesName==data[i].v1){
				$("#areaScope").append("<option selected value='" + data[i].c1 + "'>" + data[i].v1 + "</option>");
				}else{
				$("#areaScope").append("<option value='" + data[i].c1 + "'>" + data[i].v1 + "</option>");
				}
			}
			
			$("#areaScope").multiSelect({   
			    selectAll: false,  
			    oneOrMoreSelected: '*',  
			    selectAllText: '全选',  
			    noneSelected: '请选择'  
			}, function(){ 
				  //回调函数  
			    if($("[name='roleIds']:checked").length > 0)  
			    {  
			        $("#errRed").empty();  
			    }  
			    else  
			    {  
			        $("#errRed").text("请选择");  
			    }  
			});
		}
	});
}

//加载第二层数据       时间  measure 产业类型
function initSecondAna(startTime,endTime,dateType, code, m_t, title1, title2, areaScope){
	var data = {startTime:startTime, endTime:endTime, measureCode:code, type:m_t,dateType:dateType,areaScope:areaScope};
	if(_data_type_table)
		data.isBG = 1;
	//加载charts图表
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/provinceByArea.do',
	    //	async : false,
		dataType : 'json',
		type : "POST", //请求方式
		data:data,
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
			          //  step: 1,staggerLines: 1,
			            labels: {step: 1,rotation: -45,align: 'right',style: { font: 'normal 13px 宋体'}}
			        },
			        yAxis: {min: 0,title: {text: ''}},
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'top',
			            x: -0,y: 0,
			            //floating: true,
			            borderWidth: 1,
			            //backgroundColor: '#FFFFFF',title:{text:''}
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
	$("#areaScopeLabe").hide();
	$("#areaScope").hide();
	$("#selData_2").hide();
	$("#selData").show();
	$("#chartChange").hide();
}
function showSec(){
	$("#container_fir").hide();
	$("#container_sec").show();
	$("#selData").hide();
	$("#selData_2").show();
	$("#chartChange").show();
}
//加载下拉框数据
function initMeasureData(tableName){
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/measureInfo.do',
	    async : false,
		dataType : 'json',
		type : "POST", //请求方式
		//data:{type:"qyfx_qsgs"},
		data:{tname:tableName},
		success : function(data) {
			//[{mn=期末实有户数, mc=001001, mk=0}, {mn=本期登记户数, mc=001002, mk=1}] 
			if(data != null){
			    if(data.length > 0)
			    	$("#busType").append("<option selected value='" + data[0].mc + "' mk='"+(data[0].mk==null ? 0 : data[0].mk)+"'>" + data[0].mn + "</option>");
				for(var i = 1; i < data.length; i ++) {
					$("#busType").append("<option value='" + data[i].mc + "' mk='"+(data[i].mk==null ? 0 : data[i].mk)+"'>" + data[i].mn + "</option>");
				}
				//加载第一层图形
				initCharts();
			}
 		}
		//
	});
}


   </script>
</head>
<body>

<div id="Container">
    <div class="headertop" id="Header" >
       	
      	
       	<label>时间维度:</label>
       	<input class="btu" type="radio" name="gender" id="x" value="0" checked="checked"><label for="x">日</label>
        <input class="btu" type="radio" name="gender" id="y" value="1"><label for="y">月</label>
		<label id="showstart" for="开始">从:</label>
		<input class="btu" id="showstarttime" type="text" readonly="readonly" class="Wdate"
			value='2016-05-01' onclick="createWdatePickerStart();" /> 
		<label id="showend" for="结束">至:</label><input class="btu" id="showendtime" type="text" readonly="readonly" class="Wdate"
			value='2016-07-01' onclick="createWdatePickerEnd();"/>
		<!-- <input class="btu" class="btu" id="showtime" type="text" readonly="readonly" class="Wdate"
			value='2016-07-01' onclick="createWdatePickerEnd();"/> -->
		<label id='busTypeLabel'>分析指标:</label><select class="btu_select" id="busType" name="dept"></select>
		<input class="bt" id="selData" type="button" value="提交" /> 
		<input class="bt" id="selData_2" type="button" value="提交" /> 
		<input class="bt" type="button" id="chartChange" value="返回" />
    </div>
    <div id="Content">
       	<div id="container_fir" style="width: 98%;"> 
			<div id="container"></div>
		</div>

		<div id="container_sec" style="width: 98%;display:none">
			 <div id="container2" style=""></div>
		</div>
    </div>
</div>

</body>
</html>
