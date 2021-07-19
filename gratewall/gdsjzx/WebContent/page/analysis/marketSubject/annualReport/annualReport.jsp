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

<%-- <script src="<%=request.getContextPath()%>/static/script/jquery.bgiframe.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.multiSelect.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/static/script/jquery.multiSelectChange.css" rel="stylesheet" type="text/css" media="all"> --%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/bootstrap.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/multiple-select.css" />
<script src="<%=request.getContextPath()%>/static/script/multiple-select.js"></script>
<%--此css对于现在的多选框有冲突 <link href="<%=request.getContextPath()%>/static/css/analysis/analysisReport.css" rel="stylesheet" type="text/css" media="all">
 --%>
 <style type="text/css">
table {
	width: 100%;
	border: solid #add9c0;
	border-width: 1px 0px 0px 1px;
}

.td1 {
	text-align: right;
	border: 1px solid;
	width: 15%;
	border: solid #add9c0;
	border-width: 0px 1px 1px 0px;
}

.td2 {
	border: 1px solid;
	width: 20%;
	text-align: left;
	border: solid #add9c0;
	border-width: 0px 1px 1px 0px;
}

btu form-control2 {
	width: 100%;
}

.btu_select _ent {
	width: 80%;
}

input[type="text"] {
	display: inline;
	height: 28px;
	padding: 6px 12px;
	font-size: 14px;
	line-height: 1.428571429;
	width: 100%;
	color: #555555;
	vertical-align: middle;
	background-color: #ffffff;
	background-image: none;
	border-radius: 4px;
		border: none;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
	-webkit-transition: border-color ease-in-out 0.15s, box-shadow
		ease-in-out 0.15s;
	transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
	color: #555555;
}

.form-control2 {
	display: inline;
	height: 28px;
	padding-left: 8px;
	width: 100%;
	font-size: 14px;
	line-height: 1.428571429;
	color: #999;
	vertical-align: middle;
	background-color: #ffffff;
	background-image: none;
	border: none;
	border-radius: 4px;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
	-webkit-transition: border-color ease-in-out 0.15s, box-shadow
		ease-in-out 0.15s;
	transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
	font-size: 14px;
}




/*edit by yenairong,edit style begin*/
input{border: 0 ;border:none;outline:none;}
input:focus{border: 0 ;border:none;outline:none;}
select{
	appearance:none;
  -moz-appearance:none;
  -webkit-appearance:none;
}

.box_bgc_ef{background: #eff6fa;}
.box_bgc_fb{background:#fbfdfd;}

.ms-choice{
	background: #eff6fa;
	border-radius:0px !important;
}

.form-control2 {border-radius: 0px !important;}
.td1{border: 0 none;font-family: "微软雅黑";font-weight: bold;}
.td2{border: 0 none;border-right: 1px dashed #ccc; }

/*提交和重置按钮*/
.box-btn{
	background-position-x:3px;
	border: 0 none;
	outline: none;
	width: 73px;
	height: 26px;
}

.btn_submit{background:url(/gdsjzx/page/sjbd/img/30.png) no-repeat;}
.btn_reset{background:url(/gdsjzx/page/sjbd/img/31.png) no-repeat;}
/*edit ended*/

</style>
 <title>年度报表</title>

</head>
<body>
<div id="Container">
    <div class="headertop" id="Header">
    <table id="header_table">
		<tr class="box_bgc_ef"> 
			<td class="td1">日期:</td>
			<td class='td2'>
			<input class="btu form-control2" id="showendtime" type="text"
						readonly="readonly" style="background: #eff6fa" class="Wdate" value='2016-06-01'
						onclick="createWdatePickerEnd();" />
			</td>
   			<td class="td1">区域:</td>
   			<td class="td2"><select style="display:none" id="regorg_p" multiple name="dept">
					<option value='001'>省局</option>
					<option value='002'>珠三角</option>
					<option value='003'>粤东</option>
					<option value='004'>粤西</option>
					<option value='005'>粤北</option>
			</select></td>
			<td class="td1">单位:</td>
			<td class="td2"><select style="display:none" id="regorg" multiple name="dept">
					<option value="440000" p="001">广东省工商行政管理局</option>
					<option value="440003" p="002">横琴新区</option>
					<option value="440100" p="002">广东省广州市工商行政管理局</option>
					<option value="440200" p="005">广东省韶关市工商行政管理局</option>
					<option value="440300" p="002">广东省深圳市工商行政管理局</option>
					<option value="440400" p="002">广东省珠海市工商行政管理局</option>
					<option value="440500" p="003">广东省汕头市工商行政管理局</option>
					<option value="440600" p="002">广东省佛山市工商行政管理局</option>
					<option value="440606" p="002">广东省佛山市顺德区工商行政管理局</option>
					<option value="440700" p="002">广东省江门市工商行政管理局</option>
					<option value="440800" p="004">广东省湛江市工商行政管理局</option>
					<option value="440900" p="004">广东省茂名市工商行政管理局</option>
					<option value="441200" p="002">广东省肇庆市工商行政管理局</option>
					<option value="441300" p="002">广东省惠州市工商行政管理局</option>
					<option value="441400" p="003">广东省梅州市工商行政管理局</option>
					<option value="441500" p="003">广东省汕尾市工商行政管理局</option>
					<option value="441600" p="003">广东省河源市工商行政管理局</option>
					<option value="441700" p="004">广东省阳江市工商行政管理局</option>
					<option value="441800" p="005">广东省清远市工商行政管理局</option>
					<option value="441900" p="002">广东省东莞市工商行政管理局</option>
					<option value="442000" p="002">广东省中山市工商行政管理局</option>
					<option value="445100" p="003">广东省潮州市工商行政管理局</option>
					<option value="445200" p="003">广东省揭阳市工商行政管理局</option>
					<option value="445300" p="004">广东省云浮市工商行政管理局</option>
			</select></td>
			</tr>
			<tr>
				<td class="td1">行业:</td>
				<td class="td2"><select  id="indusCode_p" class="btu_select form-control2" name="dept"  style="width:120px" >
					<option value="" selected>全选</option>
					<option value="-1">第一产业</option>
					<option value="-2">第二产业</option>
					<option value="-3">第三产业</option>
		        </select> </td>
		        <td class="td1">企业类型:</td>
				<td class="td2"><select id="ent" multiple name="dept" style="width: 120px;height:24px;">
		        	<option value="1">个体</option>
					<option value="2">农专</option>
					<option value="3">常驻代表机构</option>
					<option value="4">其他</option>
		        </select></td>
		        <td class="td1">年报年度:</td>
				<td class="td2"><select id=ancheyear class="btu_select form-control2" name="dept" style="width: 120px;">
					<option  value="2013">2013</option>
					<option  value="2014">2014</option>
					<option selected value="2015">2015</option>
				</select></td>
			</tr>
			<tr>
				<td class="td1 _first" colspan="8" style='text-align:center;padding-top:4px;background: #f8fbfd;border: 1px solid #E4E4E4;border-left:none;border-right:none;'>
					<input id="selData" class="btn_submit box-btn"  type="button" value="" onclick="initCharts();" /> 
				<input id="selData" class="btn_reset box-btn"  type="button" value="" onclick="initCharts();" /> 
					<!-- <input id="selData" class="bt _first"  style="background-color: #41A6E0;color: white;"  type="button" value="提交" onclick="initCharts();" />  -->
				</td>
			</tr>
			</table>
   
    
	
		<!-- 分析指标:
		<select id="busType_p" class="btu_select" name="dept" style="width: 120px;">
			<option selected value="001">户数</option>
			<option  value="002">年报率和补报率</option>
			<option  value="003">补报率</option>
		</select>
		<select id="busType" name="busType" class="btu" multiple style="width: 120px;">
		<select id="busType" name="busType" class="btu_select" style="width: 120px;">
			<option value="001001" selected>应报户数</option>
			<option value="001002">已报户数</option>
			<option value="001003">补报户数</option>
			<option value="001004">个体已报书式</option>
			<option value="001005">个体已报网报户数</option>
		</select>		 -->
		<div style="display:none">
			<input style="display:none" class="btu" type="radio" name="gender" id="x" value="0" checked="checked"><label for="x">按日</label>
		    <input style="display:none" class="btu" type="radio" name="gender" id="y" value="1"><label for="y">按月</label>
		</div>
		
		
		<label id="showend" style="display:none"  for="结束">至</label>
		<input class="btu" style="display:none" id="showendtime" type="text" readonly="readonly" class="Wdate" value='2016-07-01' onclick="createWdatePickerEnd();"/>
		<!-- <input id="showstarttime" type="text" readonly="readonly" class="Wdate" value='2016-05-01'
			onclick="createWdatePickerStart();" /> 到
		<input id="showendtime" type="text" readonly="readonly" class="Wdate" value='2016-07-01'
			onclick="createWdatePickerEnd();" />   -->
		
		

    <div id="Content">
       	<div id="container_fir" class="_first" style="width: 98%;"> 
			<div id="container"></div>
		</div>

		<div id="container_sec"  class="_second"  style="width: 98%;display:none">
			 <div id="container2" style=""></div>
		</div>
    </div>

</body>
<script type="text/javascript">
var areaInfo = []; //全局登记机关
$(function () {
//	initSourceCode('001','001');  //登记机关
//	initIndusCode(0);   //第二级行业类型,默认加载第一产业
	//initMultSelect('busType');
    initMultSelect('regorg_p'); 
    initMultSelect('regorg'); 
    initMultSelect('ent');
	
	
	/* $("#busType_p").change(function(){  //measurecode 
		if(this.value != '001') {
			$("#busType").hide();
		}else{
			$("#busType").show();
		}
	}); */
	/* $("#indusCode_p").change(function(){
		initIndusCode(this.value); //一级行业下拉框，触发二级更改
	}); */
	 /* $("#regorg_p").change(function(){
		initSourceCode(this.value, $(this).find("option:selected").attr("_code")); //行政

	});  */
	$("#selData").bind("click", initCharts);
	var h = $(window).height() - document.getElementById('header_table').clientHeight;
	$("#container").css('height',h);
	initCharts();
});

/* function initMultSelect(name,callback) {
	//尝试清除如果已经存在的
	if($("#" + name).next().attr('class') == 'multiSelectOptions'){
		$("#" + name).next().remove();
	}
	$("#" + name).multiSelect({
		selectAll: false,
		oneOrMoreSelected: '*',
		selectAllText: '全选',
		noneSelected: '请选择'
	}, function(data){
		if(callback)
			callback(data);
		if($("#" + name).length > 0){
			$("#errRed").empty();
			
			}
		else{$("#errRed").text('请选择')};
			addCss();
	})
} */

function addCss(){
	$('#regorg span').css("width","148px");
}
function initCharts() {
    var showstarttime=$("#showendtime").val();
	var showendtime=$("#showendtime").val();
	var indusCode = $("#indusCode_p option:selected").val();  //获取产业/行业
	//var enterCode = getEnt(); // 企业类型
	var ancheyear = $("#ancheyear").val(); //报表年份
	
	var data={startTime:showstarttime,endTime:showendtime,
			  indusCode:indusCode,ancheyear:ancheyear,dateType:1};
	//获取登记机关
	getMultSelectedValue(data,['regorg_p','regorg'],['regorg_p','sourceCode'], 0, null);
	getMultSelectedValue(data,['ent'],['enterCode'], 0, null);//经济性质
	if(data.regorg_p){
		data.sourceParent = '0';
		data.sourceCode = data.regorg_p;
	}
	//console.log(data);
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/yannualReportPageInfo.do',
	    //	async : false,
		dataType : 'json',
		type : "POST", //请求方式
		data:data,
		success : function(data_all) {
			if(data_all != null){
				var x = new Array();//各个地级市
				var s0 = new Array(); //应报数
				var s1 = new Array(); //已报数
				var s2 = new Array(); //补报数
				var s3 = new Array(); //年报率 
				var s4 = new Array(); //补报率
				var sumS = 0;//已报数总和
				var sumS1 = 0;//补报数总和
				var sumS2 = 0; //应报数总和
				var t;
				for(var i = 0; i < data_all.length; i ++) {
					t = data_all[i];
					t.s0 = t.s0 == null ? 0 : t.s0;
					t.s1 = t.s1 == null ? 0 : t.s1;
					t.s2 = t.s2 == null ? 0 : t.s2;
					t.s3 = t.s3 == null ? 0 : t.s3;
					t.s4 = t.s4 == null ? 0 : t.s4;
					x.push(t.i);
					s0.push(t.s0);
					s1.push(t.s1); 
					s2.push(t.s2);
					s3.push(t.s3); 
					s4.push(t.s4);
					sumS += t.s1;
					sumS1 += t.s2;
					sumS2 += t.s0;
				}
				$('#container').highcharts({
			        chart: {
			            zoomType: 'xy'
			        },
			        title: {
			            text: "年报分析"
			        },
			        subtitle: {
			            text: "应报: " + sumS2 + "  已报: " + sumS + "  补报: " + sumS1
			        },
			        credits:{
			        	enabled:false,
			        	text:'年度填报情况分析',
			        	href:''
			        },
			        xAxis: [{
			            categories: x,
			            crosshair: true
			        }],
			        yAxis: [{ // 户数
			            labels: {format: '{value} '},
			            title: {text: '年报数'}
			        }, { title: {text: '年报率'},
			            labels: {format: '{value} %'},
			            opposite: true
			        }],
			        tooltip: {shared: true},
			        legend: {
			        	layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'top',
			            x: -0,y: 0,
			            borderWidth: 1,
			            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
			        },
			        series: [{
			            name: "应报数",
			            type: 'column',
			            yAxis: 0,
			            data: s0,
			            tooltip: {
			                valueSuffix: ' '
			            }

			        },{
			            name: "已报数",
			            type: 'column',
			            yAxis: 0,
			            data: s1,
			            tooltip: {
			                valueSuffix: ' '
			            }

			        }, {
			            name: "年报率",
			            type: 'spline',
			            yAxis:1,
			            data: s3,
			            tooltip: {
			                valueSuffix: ' %'
			            }
			        }]
			    });
			}
		}
	});
}

function drawNBCharts(x,nbl,bbl) {
	var j = x.length;
	$('#container').highcharts({
        chart: {type: 'column'},
        title: {text: '年报率/补报率'},
        //subtitle: {text: ''},
        scrollbar: {enabled: true},
        xAxis: {categories: x,
        	/* max: Math.ceil(j/5)-1>0?5:Math.ceil(j%5),
        	min: 0,
        	minorTickInterval: 'auto', */
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
            },
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            pointFormat: '<tr><td style="color: {series.color}">{series.name}: </td>' +
                             '<td style="text-align: right"><b>{point.y} %</b></td></tr>',
            useHTML: true
        },
        series: [{name:'年报率',data:nbl},{name:'补报率', data:bbl}]
    });
}

//获取企业类型
function getEnt(){
	t = "";
	//$("#busType option:selected").each(function(index,element){ t += element.value + ','; }); 
	$("#enterCode").next().find("label[class='checked'] input").each(function(index,element){ t += element.value + ','; }); 
	return t;
}


//获取sourceCode, callBack -- >标示是否第一级
function getSourceCode(callBack){
	var t = "";
	//优先考虑一级多选
	//$("#regorg_p option:selected").each(function(index,element){ 
	$("#regorg_p").next().find("label[class='checked'] input").each(function(index,element){ 
		t += element.value + ','; 
	});
	if(t.length > 4){//说明第一级有多个选择
		callBack.push(0);
		return t;
	}
	t = "";
	//次级
	//$("#sourceCode option:selected").each(function(index,element){ 
	$("#regorg").next().find("label[class='checked'] input").each(function(index,element){ 
		t += element.value + ','; 
	}); 
	return t;
}
//获取indusCode
function getIndusCode(){
	var t = "";
	//$("#indusCode option:selected").each(function(index,element){ 
	$("#indusCode").next().find("label[class='checked'] input").each(function(index,element){ 
		t += element.value + ','; 
	});
	if(t.length > 1){
		return t;
	}
	return $("#indusCode_p option:selected").val();
}
	
////
//加载所有地市名称与代码映射表
function initAllAreaInfo(t,c) {
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/allAreaInfo.do',
	    //	async : false,
		dataType : 'json',
		type : "POST", //请求方式
		data:{},
		success : function(data) {
			//[{"xc":"440000","qv":"广东省","xv":"广东省工商行政管理局","qc":"001"}] 
			if(data != null){
				for(var i = 0; i < data.length; i ++){
					areaInfo.push({v:data[i].xv, c:data[i].xc, pv:data[i].qv, pc:data[i].qc});
				}
			}
			initSourceCode(t,c);
		}});
}

/* //登记机关列表
function initSourceCode(t,c) {
	//根据区域加载市级列表
	if(areaInfo != null && areaInfo.length != 0) {
		addSourceCode(t);
	}else{
		initAllAreaInfo(t,c);		
	}
} */

/* function addSourceCode(t){
	$("#regorg").html('');
	$("#regorg").append('<option selected value="-1">全选</option>');
	for(var i = 0; i < areaInfo.length; i ++){
		if(areaInfo[i].pc == t)
			$("#regorg").append("<option value='" +areaInfo[i].c + "'>" + areaInfo[i].v + "</option>");
	}
	initMultSelect('regorg');
} */


/* function initIndusCode(t) {
	t = t == '-1' ? '0' :(t == '-2' ? '1' : (t == '-3' ? '2' : t));
	//根据第一产业加载第二产业列表
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/sencondIndusInfo.do',
		dataType : 'json',
		type : "POST", //请求方式
		data:{type:t},
		success : function(data) {
			$("#indusCode").html('');
			//$("#indusCode").html("<option selected value='"+ (t==0 ? -1 : (t == 1 ? -2 : -3))+"'>全部</option>");
			for(var i = 0; i < data.length; i ++){
			if(data[i].v1.length>9){
				data[i].v1=data[i].v1.substring(0,9);
			}
				$("#indusCode").append("<option value='" + data[i].c1 + "'>" + data[i].v1 + "</option>");
			}
			initMultSelect('indusCode');
		}
	});
} */

//数组中不存在则添加
function addIfAbsent(array, value){
	for(var i = 0; i < array.length; i ++){
		if(array[i] == value)
			return;
	}
	array.push(value);
}

//返回数组中位置
function getIndex(array, value){
	for(var i = 0 ; i < array.length; i ++){
		if(array[i] == value)
			return i;
	}
	return -1;
}

$(function(){
	$("#ent").siblings().find("button").css("background","#fbfdfd");
});
</script>
</html>
