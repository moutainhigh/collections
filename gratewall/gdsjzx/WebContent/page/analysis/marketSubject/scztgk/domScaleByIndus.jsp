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

<title>市场主体概况-资金规模分析</title>
<script type="text/javascript">

$(function () {
	var t = "${param.at}";
	document.title = initMCode(t) + ' - ' + document.title;
	//init measure data
	//initMeasureData();
	initDomScaleCode();
	$("#selData").bind("click", initCharts);
	$("#selData_2").bind("click", initSecCharts);
	$("#busType").bind("change",changeBusType);
	$("#chartChange").bind("click", showFir);
	
	$("#domScaleType").hide();	
	$("#domScaleTypeLabe").hide();	
	$("#selData_2").hide();
	$("#chartChange").hide();
	initCharts();
	
});

//全局变量
var secCharts = null; //缓存第二层chart
var secXs = null;  //缓存第二层X轴值
var secYs = null; //缓存第二层y轴值
var xs; //x轴
var y1,y2,y3; //y轴
var domScaleCode = new Array();

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
		//{"t":"2016-06-01","s":328443307.900251,"c":"A"}数据格式,c产业类型，可能为空
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
//根据code判断资金规模
function type(code2){
 	if(code2 == _undefined_name){
 		return new Array(); 
 	}
	var t = code2.substr(0,3);
	if(t=='001')
		return y1; 
	else if(t == '002')
		return y2;
	else
		return y3;
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
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/domScalePageInfo.do',
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
        title: {text: document.title},
       //subtitle: {text: '按资金规模分析'},
         xAxis: {categories: xs,
        	max: Math.ceil(j/5)-1>0?5:Math.ceil(j%5),
			min: 0,
            labels: {rotation: -45,align: 'right',style: { font: 'normal 13px 宋体'}}
        },
          scrollbar : {
            enabled:true
        },
        yAxis: {min: 0,title: {text: (title == null ? '产量值' : title)}},
        legend: {
            layout: 'vertical',align: 'right',verticalAlign: 'top',
            x: -0,y: 0,floating: true,borderWidth: 1,
            backgroundColor: '#FFFFFF',title:{text:'注册类型:'}
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
                borderWidth: 0,
               	pointWidth: 30,
               	 // 0.5的含义是，如果x轴长100px,则间距是100*0.5=50px
		        pointPadding : 0.5,
		        // 如果x轴一个点有两个柱，则这个属性设置的是这两个柱的间距。
		    //    groupPadding : 0.5, 
				events: {
				    click: function(e) {
				       //window.open("industryAnaByMType.jsp?time=" + e.point.category
				       //	       +"&measureCode=" + code + "&type=" + this.options.m_t ,"_blank"); 
					   initDomScaleSelect(this.options.m_t, e.point.series.name);
					   initSecondAna(e.point.category, e.point.category,$('#Header input[name="gender"]:checked ').val(),
							   code, this.options.m_t, e.point.series.name, title, 
							   this.options.m_t + '001,' + this.options.m_t + '002,'+this.options.m_t + '003,'+
							    this.options.m_t + '004');
					}
			    },
            },
        },
        series: [{ m_t: "001", name: '内资',data: y1,}, 
                 { m_t: "002", name: '私营',data: y2, }, 
                 { m_t: "003", name: '农合',data: y3, }]
    });
}


//加载第二层数据       时间  measure 产业类型
function initSecondAna(startTime,endTime,dateType, code, m_t, title1, title2, indusCode){
	//加载charts图表
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/domScalePageInfo.do',
	    //	async : false,
		dataType : 'json',
		type : "POST", //请求方式
		data:{startTime:startTime, endTime:endTime, measureCode:code, isSecond:'1',dateType:dateType,domCode:indusCode},
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
					//var  xStep=series.length-1;
					//组装charts数据
				}
				
				
				//画图     step ...图例的长度        max是x轴坐标
				$('#container2').highcharts({
			        chart: {type: 'column'},
			        title: {text: document.title},
			        //subtitle: {text: "[" + startTime + "," + endTime + "]"},
			        scrollbar: {enabled: true},
			        xAxis: {categories: x,
			        	max: Math.ceil(j/5)-1>0?5:Math.ceil(j%5),
			        	min: 0,
			        	//minorTickInterval: auto,
			            labels: {rotation: -45,align: 'right',style: { font: 'normal 13px 宋体'}}
			        },
			        scrollbar : {
			            enabled:true
			        },
			         plotOptions: {
            			column: {
		               // borderWidth: 0,
		               	pointWidth: 30,
				        // 如果x轴一个点有两个柱，则这个属性设置的是这两个柱的间距。
				   //     groupPadding : 3, 
		              // 	tickInterval:100
		              }
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
}

//两个div切换显示
function showFir(){
	$("#container_sec").hide();
	$("#container_fir").show();
	$("#domScaleTypeLabe").hide();
	$("#domScaleType").hide();
	$("#selData_2").hide();
	$("#selData").show();
}
function showSec(){
	$("#container_fir").hide();
	$("#container_sec").show();
	$("#selData").hide();
	$("#selData_2").show();
	$("#domScaleTypeLabe").show();
	$("#domScaleType").show();
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
				//加载第一层图形
				initCharts();
			}
 		}
		//
	});
}

function initDomScaleCode(){
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/domScaleCodeInfo.do',
	    //	async : false,
		dataType : 'json',
		type : "POST", //请求方式
		data:{},
		success : function(data) {
			//[{"v":"内资","c":"001   "},{"v":"期末实有企业注册资本（金）100万以下","c":"001001"}]
			if(data != null){//直接跳过前三个一级分类
			    /* if(data.length > 3)
			    	$("#domScaleType").append("<option selected value='" + data[3].c + "'>" + data[3].v + "</option>");
				for(var i = 4; i < data.length; i ++) {
					$("#domScaleType").append("<option value='" + data[i].c + "'>" + data[i].v + "</option>");
				} */
				for(var i = 0; i < data.length; i ++) {
					domScaleCode.push(data[i]);
				} 
				
			}
 		}
	});
}

function initDomScaleSelect(f,name){
	$("#domScaleType").html('');
	for(var i = 0; i < domScaleCode.length; i ++) {
		if(domScaleCode[i].c.substr(0,3) == f && domScaleCode[i].c.charAt(3) != ' '){
			$("#domScaleType").append("<option selected value='" + domScaleCode[i].c + "'>" + domScaleCode[i].v + "</option>");
		}
	} 
	$("#domScaleType").multiSelect({   
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


   </script>
</head>
<body>

<div id="Container">
    <div class="headertop" id="Header">
       	日期格式: <input class="btu" type="radio" name="gender" id="x" value="0" checked="checked"><label for="x">按日</label>
        <input class="btu" type="radio" name="gender" id="y" value="1"><label for="y">按月</label>
		<label id="showstart" for="开始">开始:</label><input class="btu" id="showstarttime" type="text" readonly="readonly" class="Wdate"
			value='2016-05-01' onclick="createWdatePickerStart();" /> 
		<label id="showend" for="结束">结束:</label><input class="btu" id="showendtime" type="text" readonly="readonly" class="Wdate"
			value='2016-07-01' onclick="createWdatePickerEnd();"/>
		<!-- <input class="btu" class="btu" id="showtime" type="text" readonly="readonly" class="Wdate"
			value='2016-07-01' onclick="createWdatePickerEnd();"/> -->
		度量维度:<select class="btu_select" id="busType" name="dept" style="width: 150px;"></select>
		<label id="domScaleTypeLabe" for="注册资金规模">注册资金规模:</label><select class="btu_select" id="domScaleType" name="dept_1" style="width: 150px;"></select>
		<input class="bt" id="selData" type="button" value="查询" /> 
		<input class="bt" id="selData_2" type="button" value="查询" /> 
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
