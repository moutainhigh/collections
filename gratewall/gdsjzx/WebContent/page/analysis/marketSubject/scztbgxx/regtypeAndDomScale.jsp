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

<title>按注册类型及注册资金规模分析</title>
<script type="text/javascript">

$(function () {
	var t = "${param.at}";
	document.title = initMCode(t) + ' - ' + document.title;
	_data_type_table = "${param.bt}";
	//init measure data
	//initMeasureData();
	//加载企业类型
	//initEnterpriseType();
	initIndusCodes();
	//initIndusCodes();
	$("#selData").bind("click", initCharts);
	initCharts();
	//$("#selData_2").bind("click", initSecCharts);
	//$("#busType").bind("change",changeBusType);
	//$("#chartChange").bind("click", showFir);
	
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
	//var selType=$("#selType").val();
	//var showstarttime=$("#showstarttime").val();
	//var showendtime=$("#showendtime").val();
    var measureCode = getSelectedMeasureCode();
    initSecondAna(null,null,null, measureCode, 
    		     //$("#induType").val(), $("#induType option:selected").text(), 
    		     -1, "", 
    		     $("#busType option:selected").text(), getSelectedIndusCode());
}
//第一层
function initCharts(){
	//设置加载gif @TODO
	getData(function(data_all){
		//{"t":"2016-06-01","s":328443307.900251,"c":"A"}数据格式,c产业类型，可能为空
		//{"s":"23","i":"海水捕捞","c":"个体"}
		if(data_all != null){
			var x = new Array();
			var series = new Array();
			var names = new Array();
			if(data_all.length > 0) {
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
		        title: {text: ''},
		        subtitle: {text: ''},
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

function getDomScaleCode(){
	var t = "";
	//$("#indusType option:selected").each(function(index,element){ t += element.value + ','; }); 
	$("#indusType").next().find("label[class='checked'] input").each(function(index,element){ t += element.value + ','; }); 
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
    var enterpriseCode = getSelectedIndusCode();
    var economicCode = getDomScaleCode();
    var showstarttime=$("#showstarttime").val();
	//var showendtime=$("#showendtime").val();
   /*  var economicCode = $("#indusType option:selected").val(); */
    if(measureCode == null) {
    	alert("请求数据不正确！");
    	return ;
    }
	var data={startTime:showstarttime,economicCode:economicCode,measureCode:measureCode,enterpriseCode:enterpriseCode};
	if(_data_type_table)
		data.isBG='1';
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/regtypeAndDomScalePageInfo.do',
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
function drawHighCharts(xs,y1,y2,y3,title, code){
	$('#container').highcharts({
        chart: {type: 'column'},
        title: {text: '企业设立登记'},
        subtitle: {text: '按产业分析'},
        xAxis: {categories: xs,
       		//max: 10,
			//min: 0,
       		//minorTickInterval: auto,
            labels: { rotation: -45,align: 'right',style: { font: 'normal 13px 宋体'}}
        },
          scrollbar : {
            enabled:true
        },
        yAxis: {min: 0,title: {text: (title == null ? '产量值' : title)}},
        legend: {
            layout: 'vertical',align: 'right',verticalAlign: 'top',
            x: -0,y: 0,floating: true,borderWidth: 1,
            backgroundColor: '#FFFFFF',title:{text:'一级行业及产业分类:'}
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
					   initIndusCodes(this.options.m_t);
					   initSecondAna(e.point.category, $("#selType").val(),
							   code, this.options.m_t, e.point.series.name, title, null);
					}
			    },
            },
        },
        series: [{ m_t: 0, name: '第一产业',data: y1,}, 
                 { m_t: 1, name: '第二产业',data: y2, }, 
                 { m_t: 2, name: '第三产业',data: y3, }]
    });
}



//加载第二层数据       时间  measure 产业类型
function initSecondAna(startTime,dateType, code, m_t, title1, title2, indusCode){
	var data = {startTime:startTime,  measureCode:code, type:m_t,dateType:dateType,indusCode:indusCode}
	if(_data_type_table)
		data.isBG = '1';
	//加载charts图表
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/industryPageInfoByType.do',
	    //	async : false,
		dataType : 'json',
		type : "POST", //请求方式
		data:data,
		success : function(data_all) {
			//showSec();
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
				
				
				//画图     step ...图例的长度        max是x轴坐标
				$('#container2').highcharts({
			        chart: {type: 'column'},
			        title: {text: title1 + " - " + title2},
			        subtitle: {text: "[" + startTime + "," + endTime + "]"},
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
				//加载第一层图形
				initCharts();
			}
 		}
		//
	});
}
//quanshenggeshi
//
function initEnterpriseType(){
	<%-- $.ajax({
		url: '<%=request.getContextPath()%>/analysis/allAreaInfo.do',
	    //	async : false,
		dataType : 'json',
		type : "POST", //请求方式
		data:{},
		success : function(data) {
			//[{"xc":"440000","qv":"广东省","xv":"广东省工商行政管理局","qc":"001"}] 
			if(data != null){
				$("#enterpriseType").html('');
				for(var i = 0; i < data.length; i ++){
					$("#enterpriseType").append("<option selected value='" + data[i].xc + "'>" + data[i].xv + "</option>");
				}
			}
 		}
	}); --%>
}

//注册资金规模
function initIndusCodes(){
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/domScaleCodeInfo.do',
		dataType : 'json',
		type : "POST", //请求方式
		data:{},
		success : function(data) {
			$("#indusType").html('');
			//{"v":"内资","c":"001   "},{"v":"期末实有企业注册资本（金）100万以下","c":"001001"}
			for(var i = 0; i < data.length; i ++){
				if(data[i].c.charAt(3) != ' ')
					//$("#enterpriseType").append("<option selected value='" + data[i].c + "'>" + data[i].v + "</option>");
				//else
					$("#indusType").append("<option selected value='" + data[i].c + "'>" + data[i].v + "</option>");
			}
			$("#indusType").multiSelect({
				selectAll: false,
				oneOrMoreSelected: '*',
				selectAllText: '全选',
				noneSelected: '请选择'
			}, function(data){
				if($("#indusType").length > 0){
					$("#errRed").empty();}
				else{$("#errRed").text('请选择')};
			})
		}
	});
}


   </script>
</head>
<body>
<div id="Container">
    <div class="headertop" id="Header">
	<!-- <select id="selType" name="dept" style="width: 150px;">
		<option value="0">按日</option>
		<option value="1">按月</option>
	</select> -->时间维度：
	<input style="display:none" class="btu" type="radio" name="gender" id="x" value="0" checked="checked"><label  style="display:none"  for="x">按日</label>
    <input style="display:none" class="btu" type="radio" name="gender" id="y" value="1"><label style="display:none"  for="y">按月</label>
	<input  id="showstarttime" type="text" readonly="readonly" class="Wdate" value='2016-05-01'
		onclick="createWdatePickerStart();" /> 
	<input style="display:none" id="showendtime" type="text" readonly="readonly" class="Wdate" value='2016-07-01'
		onclick="createWdatePickerEnd();" />  分析指标
	<select id="busType" class="btu_select" name="dept" style="width: 150px;">
		<!-- <option value="0">本期</option>
		<option value="1">期末</option> -->
	</select>
	<!-- 全省各市：<select id="enterpriseType" multiple name="dept_1" style="width: 150px;"></select> -->
	注册资金规模：<select id="indusType" class="btu_select" multiple name="dept_1" style="width: 150px;"></select>
	<span id="container_fir" style="width: 100%">
		<input class="bt" id="selData" type="button" value="提交" />
		<span id="container" style="width: 100%"></span>
	</span>
	</div>
	<span id="container_sec" style="width: 100%;display:none">
	    <!-- <select id="induType" name="dept_1" style="width: 150px;">
	    	<option selected value='0'>第一产业</option>
	    	<option value='1'>第二产业</option>
	    	<option value='2'>第三产业</option>
		</select> -->
		<!-- 产业类型：<select id="indusType" multiple name="dept_1" style="width: 150px;"></select>
		<input id="selData_2" type="button" value="查询" />
	    <input type="button" id="chartChange" value="返回"/>
	    <span id="container2" style="width: 70%"></span>
	    </br> -->
	    <!-- <span id="allIndus">
	    	
	    </span> -->
	</span>

</div>

</body>
</html>
