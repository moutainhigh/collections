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

<title>经济性质和产业分析</title>
<script type="text/javascript">

$(function () {
	var t = "${param.at}";
	document.title = initMCode(t) + ' - ' + document.title;
	_data_type_table = "${param.bt}";
	initCharts();
	var tableName;
	//init measure data && set titile
	/* if("1" == t){//为市场主体概况，否则为设立登记
		document.title = '市场主体概况' + document.title.substr(6, document.title.length);
		tableName="T_REG_ENTRY_EXIT";
		//$("#busType").append("<option selected value='001001'>期末实有户数</option>");
		//$("#busType").append("<option value='002001'>期末实有注册资本(金)(万元)</option>");
	}else{
		tableName="T_REG_ENTRY_EXIT";
		//$("#busType").append("<option selected value='001002'>本期登记户数</option>");
		//$("#busType").append("<option value='002005'>本期登记注册资本(金)(万元)</option>");
	} */
	//initCharts();//加载图形
	
	//init measure data
	//initMeasureData(tableName);
	//加载企业类型
	initEnterpriseType();
	$("#selData").bind("click", initCharts);
	$("#selData_2").bind("click", initSecCharts);
	$("#busType").bind("change",changeBusType);
	$("#chartChange").bind("click", showFir);
	$("#selData_2").hide();
	$("#indusType").hide();
	$("#chartChange").hide();
	$("#indusTypeLabe").hide();
	$("#enterpriseTypeLabe").hide();
	$("#enterpriseType").hide();
});

//全局变量
var secCharts = null; //缓存第二层chart
var secXs = null;  //缓存第二层X轴值
var secYs = null; //缓存第二层y轴值
var xs; //x轴
var y1,y2,y3; //y轴
var tempParentCode = 0;//缓存第一层产业类型

//var codes = null; //对应产业类型
//第二层数据
function initSecCharts(){
	//var selType=$("#selType").val();
	//var showstarttime=$("#showstarttime").val();
	//var showendtime=$("#showendtime").val();
    //var measureCode = getSelectedMeasureCode();
    //startTime,dateType, code, m_t, title1, title2, indusCode,economicCode
    initSecondAna(null,null,null,
    		    //$("#selType").val(), 
    		     //$("#induType").val(), $("#induType option:selected").text(), 
    		     tempParentCode, null,null,getIndusCode() ,getSelectedIndusCode());
}
//第一层
function initCharts(){
	//设置加载gif @TODO
	getData(function(data_all){
		//{"t":"2016-06-01","s":328443307.900251,"c":"A"}数据格式,c产业类型，可能为空
		//{"s":1186.000000,"c":"内资（非私营）","i":"A"},{"s":97.000000,"c":"国有","i":"A"},{"s":908.000000,"c":"集体","i":"A"}
		if(data_all != null){
			var x = new Array();
			var series = new Array();
			var names = new Array();
			if(data_all.length > 0) {
				/////////
				x.push(data_all[0].i);
				names.push(data_all[0].c);
				var i = 1;
				var j = 0;
				//日期以及名称去重复
				for( ; i < data_all.length; i ++){
					if(data_all[i].i != x[j]){//新增时间点
						x.push(data_all[i].i);
						j ++;
					}
					//名字不重复列表
					addIfAbsent(names, data_all[i].c);
				}
				//拼装series数据,series数组长度应为名称数组长度
				for(i = 0; i < names.length; i ++) {
					series.push({name:names[i],data: new Array()});
				}
				//插入数据
				var obj_t;
				for(i = 0 ; i < data_all.length; i ++){
					(series[getIndex(names, data_all[i].c)]).data.push({x:getIndex(x,data_all[i].i),y:data_all[i].s});
				}
				//组装charts数据
			}
			//画图     step ...图例的长度        max是x轴坐标
			$('#container').highcharts({
		        chart: {type: 'column'},
		        title: {text: document.title},
		        //subtitle: {text: ''},
		        scrollbar: {enabled: true},
		        xAxis: {categories: x,
		        //	max: Math.ceil(j/5)-1>0?5:Math.ceil(j%5),
		        //	min: 0,
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
		            layout: 'vertical',
		            align: 'right',
		            verticalAlign: 'top',
		            x: -0,y: 0,
		            //floating: true,
		            borderWidth: 1,
		            //backgroundColor: '#FFFFFF',title:{text:''}
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
							   var t = (this.options.name == "第一产业" ? 0: (this.options.name == "第二产业" ? 1 : 2));
							   tempParentCode = t;
							   initIndusCodes(t,e.point.series.name);
							   initSecondAna($("#showstarttime").val(), $("#selType").val(),
									   null, t, e.point.series.name, '', null);
							}
					    },
		            },
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
	});
}
//获取measureCode
function getIndusCode(){
	//return $("#busType option:selected").val();
	var t = "";
	//$("#indusType option:selected").each(function(index,element){ t += element.value + ','; }); 
	$("#indusType").next().find("label[class='checked'] input").each(function(index,element){ t += element.value + ','; }); 
	return t;
}

//获取industryCode ,2级
function getSelectedIndusCode(){
	var t = "";
	//$(".multiSelectOptions").find("label[class='checked'] input").each(function(index,element){ t += element.value + ','; }); 
	$("#enterpriseType").next().find("label[class='checked'] input").each(function(index,element){ t += element.value + ','; }); 
	
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
	y1.push(0);y2.push(0);y3.push(0);
}
//根据code判断产业类型
function type(code2){
 	if(code2 == _undefined_name){
 		return new Array(); 
 	}
	if(code2 == "A")
		return y1; 
	else if(code2 <= "E")
		return y2;
	else
		return y3;
}
	
//获取数据
function getData(exe){
    var measureCode = $("#busType option:selected").val();
    var enterpriseCode = getSelectedIndusCode(); //二级经济性质代码
    var showstarttime=$("#showstarttime").val();
	//var showendtime=$("#showendtime").val();
    if(measureCode == null) {
    	alert("请求数据不正确！");
    	return ;
    }
	var data={startTime:showstarttime,
			  measureCode:measureCode,economicCode:enterpriseCode};
	if(_data_type_table)
		data.isBG = 1;
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/economicAndIndustycoPageInfo.do',
	    //	async : false,
		dataType : 'json',
		type : "POST", //请求方式
		data:data,
		success : function(data) {
			exe(data);
		}
	});
}


function initIndusCodes(m_t,seriesName){
	//alert(m_t);
	//根据第一产业加载第二产业列表
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/sencondIndusInfo.do',
		dataType : 'json',
		type : "POST", //请求方式
		data:{type:m_t},
		success : function(data) {
			$("#indusType").html('');
			for(var i = 0; i < data.length; i ++){
				if(seriesName==data[i].v1){
				$("#indusType").append("<option selected value='" + data[i].c1 + "'>" + data[i].v1 + "</option>");
				}else{
				$("#indusType").append("<option value='" + data[i].c1 + "'>" + data[i].v1 + "</option>");
				}
			}
			
			$("#indusType").multiSelect({   
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
			
			$("#enterpriseType").multiSelect({   
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
function initSecondAna(startTime,dateType, code, m_t, title1, title2, indusCode,economicCode){
	//加载charts图表
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/economicAndIndustycoPageInfo.do',
	    //	async : false,
		dataType : 'json',
		type : "POST", //请求方式
		data:{startTime:$("#showstarttime").val(), 
			  measureCode:$("#busType option:selected").val(), 
			  economicCode:economicCode,parentIndustryCode:m_t,industryCode:indusCode,isSecond:'0'},
		success : function(data_all) {
			showSec();
			if(data_all != null){
				var x = new Array();
				var series = new Array();
				var names = new Array();
				if(data_all.length > 0) {
					/////////
					x.push(data_all[0].i);
					names.push(data_all[0].c);
					var i = 1;
					var j = 0;
					//日期以及名称去重复
					for( ; i < data_all.length; i ++){
						if(data_all[i].i != x[j]){//新增时间点
							x.push(data_all[i].i);
							j ++;
						}
						//名字不重复列表
						addIfAbsent(names, data_all[i].c);
					}
					//拼装series数据,series数组长度应为名称数组长度
					for(i = 0; i < names.length; i ++) {
						series.push({name:names[i],data: new Array()});
					}
					//插入数据
					var obj_t;
					for(i = 0 ; i < data_all.length; i ++){
						(series[getIndex(names, data_all[i].c)]).data.push({x:getIndex(x,data_all[i].i),y:data_all[i].s});
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
			        //	max: Math.ceil(j/5)-1>0?5:Math.ceil(j%5),
			        //	min: 0,
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
	$("#enterpriseTypeLabe").hide();
	$("#enterpriseType").hide();
	$("#selData_2").hide();
	$("#selData").show();
	$("#chartChange").hide();
	$("#indusType").hide();
	
}
function showSec(){
	$("#container_fir").hide();
	$("#container_sec").show();
	$("#selData").hide();
	$("#selData_2").show();
	$("#enterpriseTypeLabe").show();
	$("#enterpriseType").show();
	$("#chartChange").show();
	$("#indusType").show();
	
}
//加载下拉框数据
function initMeasureData(tableName){
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/measureInfo.do',
	    async : false,
		dataType : 'json',
		type : "POST", //请求方式
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

function initEnterpriseType(){
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/economicCodeInfo.do',
	    //	async : false,
		dataType : 'json',
		type : "POST", //请求方式
		data:{},
		success : function(data) {
			//[{mn=期末实有户数, mc=001001, mk=0}, {mn=本期登记户数, mc=001002, mk=1}] 
			if(data != null){
				$("#enterpriseType").html('');
				//[{"v":"内资（非私营）","c":"1","pc":"1","pv":"内资"},{"v":"国有","c":"11","pc":"1","pv":"内资"}]
				for(var i = 0; i < data.length; i ++){
					$("#enterpriseType").append("<option selected value='" + data[i].c + "'>" + data[i].v + "</option>");
				}
			}
 		}
	});
}


   </script>
</head>
<body>

<div id="Container">
    <div class="headertop" id="Header">
       	时间维度: <input style="display:none" class="btu" type="radio" name="gender" id="x" value="0" checked="checked">
        <input style="display:none" class="btu" type="radio" name="gender" id="y" value="1">
		<label style="display:none" id="showstart" for="开始">开始:</label><input class="btu" id="showstarttime" type="text" readonly="readonly" class="Wdate"
			value='2016-05-01' onclick="createWdatePickerStart();" /> 
		<label style="display:none" id="showend" for="结束">结束:</label><input style="display:none" class="btu" id="showendtime" type="text" readonly="readonly" class="Wdate"
			value='2016-07-01' onclick="createWdatePickerEnd();"/>
		<!-- <input class="btu" class="btu" id="showtime" type="text" readonly="readonly" class="Wdate"
			value='2016-07-01' onclick="createWdatePickerEnd();"/> -->
		<label id='busTypeLabel'>分析指标:</label><select class="btu_select" id="busType" name="dept" style="width: 150px;"></select>
		<!-- 产业类型：<select id="indusType" multiple name="dept_1" style="width: 150px;"></select>
		 -->
		<!--  二级经济性质：<select id="enterpriseType" multiple name="dept_1" style="width: 150px;"></select> -->
		<label id="indusTypeLabe" for="产业类型">产业类型:</label><select class="btu_select" id="indusType" name="dept_1" style="width: 150px;"></select>
		<label id="enterpriseTypeLabe" for="二级经济性质">二级经济性质:</label><select class="btu_select" id="enterpriseType" name="dept_1" style="width: 150px;"></select>
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
