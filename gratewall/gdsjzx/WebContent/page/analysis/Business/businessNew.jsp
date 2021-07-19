<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/chart/Highstock-4.2.3/js/highstock.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/chart/Highstock-4.2.3/js/modules/no-data-to-display.src.js" type="text/javascript"></script>

<script src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/analysis/picker.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/analysis/commonUtil.js" type="text/javascript"></script>


<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/bootstrap.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/multiple-select.css" />
<script src="<%=request.getContextPath()%>/static/script/multiple-select.js"></script>


<title>全省各市业务量情况</title>

<style type="text/css">
table {
	width: 100%;
	border: solid #e5e5e5;
	border-width: 1px 0px 0px 1px;
}

.td1 {
	text-align: right;
	border: 1px solid;
	width: 10%;
	border: solid #e5e5e5;
	border-width: 0px 1px 1px 0px;
}

.td2 {
	border: 1px solid;
	width: 15%;
	text-align: left;
	border: solid #e5e5e5;
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

.bt_second{background:url(/gdsjzx/page/sjbd/img/36.png) no-repeat;width:73px;height: 26px;}
/*edit ended*/

</style>
</style>

<script type="text/javascript">

$(function () {
	initMultSelect("regorg1");
	initMultSelect("accepttype");
	$("#chartChange").hide();
	
	var endDate = new Date();
	var startDate = new Date(endDate.getTime() - 86400000);
	$("#showstarttime").val(startDate.getFullYear() + '-' + ((startDate.getMonth() + 1) < 10 ? "0" + (startDate.getMonth() + 1) : (startDate.getMonth() + 1)) + '-' + (startDate.getDate() < 10 ? "0" + startDate.getDate() : startDate.getDate()));
	
	$("#selData").bind("click", initCharts);
	$("#selData_2").bind("click", initSecCharts);
//	$("#regorg").bind("change",changeRegorg);
	$("#chartChange").bind("click", showFir);
	
	
	$("#selData_2").hide();
	var h = $(window).height() - document.getElementById('header_table').clientHeight;
	$("#container").css('height',h);
	$("#container2").css('height',h);
	initCharts();//加载图形
	
});
/* 
function initMultSelect(name,callback) {
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
			//addCss();
	})
} */






//全局变量
var secCharts = null; //缓存第二层chart
var secXs = null;  //缓存第二层X轴值
var secYs = null; //缓存第二层y轴值
var xs; //x轴
var y1; //y轴
var regorgCN;
//第二层数据
function initSecCharts(){
	//var measure=$('#Header input[name="index"]:checked ').val();
	var transdt=$("#showstarttime").val();
	$('#gdzb').hide();
	$('#Header input[name="index"] ').hide();
	$('#benqilabel').hide();
	$('#qimolabel').hide();
	/* $('#regorg1').hide();
	$('#regorgLabel').hide();
	 $('#regorg1').next().hide(); */
	var regorg=returnSelect("regorg1");
	var accepttype=returnSelect("accepttype");
    var data = {transdt:transdt,regorg:regorg,regorgCN:regorgCN,accepttype:accepttype};
    var title2=$('#regorg1').multipleSelect('getSelects', 'text')+"";
    replaceStr="工商行政";
    if(title2.length>1 ){
    if(title2.indexOf('省工商行政')!=-1){
    title2=title2.split(replaceStr).join('');
   	title2="省工商局"+title2.substring(1,title2.length);
    }else{
    title2=title2.split(replaceStr).join('');
    }
    }
    initSecondAna(data, 
    		    null,title2);
}
//第一层
function initCharts(){
	//设置加载gif @TODO
	getData(function(data){
		xs = new Array(); 
	    y1 = new Array();
	    var provinceSum=0;
		if(data!= null && data.length > 0){//组装数据
			//insertNew();  //初始化;
			xs.push(data[0].x);
			y1.push(data[0].s);
			provinceSum=parseInt(data[0].s);
			var j = 0;
			var t_a = null;
			for(var i = 1; i < data.length; i ++){
				t_a=y1;
				if(xs[j] == data[i].x){ //日期相同累计
					t_a[j] += parseInt(data[i].s);
				}else{
					xs.push(data[i].x);
					y1.push(0);
					t_a[++j] = parseInt(data[i].s);
				}
				provinceSum=provinceSum+parseInt(data[i].s);
			}			
		}
		var titleType;
		var measure=$('#Header input[name="index"]:checked ').val();
		if(measure==1){
			titleType="本期业务量:";
		}else{
			titleType="期末业务量:";
		}
		drawHighCharts(xs,y1,"全省各市业务量情况", returnSelect("regorg1"),j,titleType,provinceSum);
	});
}
/* //获取measureCode
function getSelectedRegorgCode(){
	var t = "";
	$("#regorg").next().find("label[class='checked'] input").each(function(index,element){ 
		t += element.value + ','; 
	});
	return t;
} */


//获取数据
function getData(exe){
	var measure=$('#Header input[name="index"]:checked ').val();
	var showstarttime=$("#showstarttime").val();
	//var showendtime=$("#showendtime").val();
    var regorg = returnSelect("regorg1");
    var accepttype =returnSelect("accepttype");
	var data={measure:measure,transdt:showstarttime,regorg:regorg,accepttype:accepttype};
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/businessSum.do',
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
function drawHighCharts(xs,y1,title, code,j,titleType,provinceSum){
	$('#container').highcharts({
        chart: {type: 'column'},
        title: {text: '全省各市业务量情况'},
         subtitle: {
        	 			text: "广东省"+ titleType + provinceSum+"件"
			        },
        xAxis: {categories: xs,
        	/* max: Math.ceil(j/8)-1>0?8:Math.ceil(j%8),
			min: 0, */
            labels: {rotation: -45,align: 'right',style: { font: 'normal 13px 宋体'}}
        },
         scrollbar : {
		            enabled:true
		        },
        yAxis: [{min: 0,title: {text: (title == null ? '产量值' : title)}}],
         credits: {
	         enabled:false
	        },
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
					 var measure=$('#Header input[name="index"]:checked ').val();
					 var transdt=$("#showstarttime").val();
					 $('#td2').show();
					 $('#t1').hide();
					 $('#t2').hide();
					 $('#gdzb').hide();
					 $('#Header input[name="index"]').hide();
					 $('#benqilabel').hide();
					 $('#qimolabel').hide();
					 /* $('#regorg1').hide();
					 $('#regorgLabel').hide();
					  $('#regorg1').next().hide(); */
				    // var measureCode = getSelectedMeasureCode();
				   	 regorgCN= e.point.category;
				     var data = {transdt:transdt,measure:measure,regorgCN:regorgCN,regorg:'-1'};
				     initSecondAna(data, e.point.series.name, regorgCN);
					}
			    },
            },
            series: {
                borderWidth: 2,
                borderColor: 'black',
                pointWidth: 30
            }
        },
        series: [
                 { m_t: 0, name: '业务总量',data: y1}]
    });
}

//加载第二层数据       时间  measure 产业类型
function initSecondAna(data,title1, title2){
	//加载charts图表
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/businessSecondSum.do',
					//	async : false,
					dataType : 'json',
					type : "POST", //请求方式
					data : data,
					success : function(data_all) {
						showSec();
						if (data_all != null) {
							for ( var i = 0; i < data_all.length; i++) {
								var series = [];
								var p0 = [];
								for ( var j = 0; j < data_all[i].length; j++) {
									var p1 = [ data_all[i][j].x,
											data_all[i][j].s ];
									p0.push(p1);
								}
								var name;
								if (i == 0) {
									name = "本期业务量";
								} else {
									name = "期末业务量";
								}
								var pieData = {
									type : 'pie',
									name : name,
									data : p0
								};
								series.push(pieData);
								drawPie("container" + (i + 2), series, name,
										"业务类型", title2);
							}

						}
					}
				});
	}

	function drawPie(id, series, name, title, title2) {
		$('#' + id)
				.highcharts(
						{
							chart : {
								plotBackgroundColor : null,
								plotBorderWidth : null,
								plotShadow : false
							},
							colors : [ 'green', 'blue', '#1aadce', '#492970',
									'#f28f43', '#77a1e5', '#c42525', '#a6c96a' ],
							title : {
								text : "[ " + name + " - " + title + " ]",
								useHTML : true
							},
							subtitle : {
								text : "[ " + title2 + " ]",
								floating : true
							},
							credits : {
								enabled : false,
								text : '业务类型分析',
								href : ''
							},
							tooltip : {
								pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
							},
							credits : {
								enabled : false
							},
							plotOptions : {
								pie : {
									allowPointSelect : true,
									cursor : 'pointer',
									dataLabels : {
										enabled : true,
										format : '<b>{point.name}</b><b>{point.percentage:.1f}%</b><br/><b>{point.name}数:</b><b>{point.y}件</b>',
									},
									showInLegend : true
								}
							},
							series : series
						});

	}
	//返回数组中位置
	function getIndex(array, value) {
		for ( var i = 0; i < array.length; i++) {
			if (array[i] == value)
				return i;
		}
		return -1;
	}

	//数组中不存在则添加
	function addIfAbsent(array, value) {
		for ( var i = 0; i < array.length; i++) {
			if (array[i] == value)
				return;
		}
		array.push(value);
	}

	//两个div切换显示
	function showFir() {
		$("#td2").hide();
		$('#t1').show();
		$('#t2').show();
		$("#container_sec").hide();
		$("#container_sec1").hide();
		$("#container_fir").show();
		$('#gdzb').show();
		$('#Header input[name="index"]').show();
		$('#benqilabel').show();
		$('#qimolabel').show();
		$("#selData_2").hide();
		$("#selData").show();
		$("#chartChange").hide();

	}
	function showSec() {
		$("#container_fir").hide();
		$("#container_sec").show();
		$("#container_sec1").show();
		$("#selData").hide();
		$("#selData_2").show();
		$("#chartChange").show();
	}

	//初始化selected框
	function initMultSelect(name, callback) {
		//尝试清除如果已经存在的
		//alert(name + ":" + $("#" + name).next().attr('class'));
		var yv = '';
		$('#' + name).multipleSelect({
			minimumCountSelected : 10,//多少个以后采用占位符
			isOpen : false,//类型: boolean是否打开下拉列表。默认值为 false。
			//width: '20%',//类型: integer定义下拉列表的宽度。默认值为 undefined。 默认与 select 的宽度相同。
			placeholder : "请选择",//类型: string 显示默认的提示信息。默认值为 ''。
			selectAll : true,//类型: boolean 是否显示全选复选框。默认值为 true。
			selectAllText : '全部(包含未注明类型)',//类型: string 全选复选框的显示内容。默认值为 Select all。
			filter : true,//类型: boolean是否开启过滤功能。默认值为 false。
			multiple : false,//类型: boolean 是否在一行中显示多个选项。默认值为 false。
			//multipleWidth://类型: integer 一行中每个选项的宽度。默认值为 80。
			single : false,//类型: boolean是否只允许你选择一行。默认值为 false。
			position : 'bottom',//类型: string定义下拉列表的位置，只能是 bottom 或者 top。默认值为 bottom.
			maxHeight : 250,//类型: integer下拉列表的最大高度。默认值为 250。
			countSelected : '总数 % 中的 # 个',//选择几个总数几个
			allSelected : '全部',
			noMatchesFound : '没有匹配的选项',
			onOpen : function() {

			},
			onClose : function() {

			},
			onCheckAll : function() {
				var v = $('#' + name).multipleSelect('getSelects');
				var text = $('#' + name).multipleSelect('getSelects', 'text');
				var arra = new Array();
				reconstructionData(v, arra, name);
			},
			onUncheckAll : function() {
				if (name == 'indus_p1') {//是一级产业
					$("#indus_p2").empty();
					$("#indus_p2").multipleSelect("refresh");
					$("#indus").empty();
					$("#indus").multipleSelect("refresh");
				} else if (name == 'indus_p2') {//是二级产业
					$("#indus").empty();
					$("#indus").append(arra).multipleSelect("refresh");
				} else if (name == 'econ_p') {//是经济性质
					$("#econ").empty();
					$("#econ").append(arra).multipleSelect("refresh");
				} else if (name == 'dom_p') {//是资金规模
					$("#dom").empty();
					$("#dom").append(arra).multipleSelect("refresh");
				} else if (name == 'organ_p') {//是组织形式
					$("#organ").empty();
					$("#organ").append(arra).multipleSelect("refresh");
				} else if (name == 'regorg_p') {//是区域及单位
					$("#regorg").empty();
					$("#regorg").append(arra).multipleSelect("refresh");
				} else {

				}
			},
			onOptgroupClick : function(view) {

			},
			onClick : function() {
				var v = $('#' + name).multipleSelect('getSelects');
				var arra = new Array();
				reconstructionData(v, arra, name);
			}
		});
	}
</script>
</head>
<body>

	<div id="Container">
		<div class="headertop" id="Header">
			<table id="header_table">
				<tr class="box_bgc_fb">
					<td id="t1" class="td1"><label id="gdzb">固定指标:</label></td>
					<td id="t2" class="td2"><input class="btu" type="radio" name="index" id="x" value="1" checked="checked"> <label id="benqilabel" for="x">本期</label> <input class="btu" type="radio" name="index" id="y" value="2"> <label id="qimolabel" for="y">期末</label></td>
					<td class="td1"><label>时间维度:</label></td>
					<td class="td2"><input class="btu" id="showstarttime" type="text" readonly="readonly" class="Wdate" style="background: #fbfdfd" value='2016-08-21' onclick="createWdatePickerStart();" /></td>
					<td class="td1"><label id='regorgLabel'>行政区划:</label></td>
					<td class="td2"><select class="btu_select" id="regorg1" multiple name="dept">
							<option value="440000" p="001">直属局</option>
							<option value="440100" p="002">广州市</option>
							<option value="440200" p="005">韶关市</option>
							<option value="440300" p="002">深圳市</option>
							<option value="440400" p="002">珠海市</option>
							<option value="440500" p="003">汕头市</option>
							<option value="440600" p="002">佛山市</option>
							<option value="440700" p="002">江门市</option>
							<option value="440800" p="004">湛江市</option>
							<option value="440900" p="004">茂名市</option>
							<option value="441200" p="002">肇庆市</option>
							<option value="441300" p="002">惠州市</option>
							<option value="441400" p="003">梅州市</option>
							<option value="441500" p="003">汕尾市</option>
							<option value="441600" p="003">河源市</option>
							<option value="441700" p="004">阳江市</option>
							<option value="441800" p="005">清远市</option>
							<option value="441900" p="002">东莞市</option>
							<option value="442000" p="002">中山市</option>
							<option value="445100" p="003">潮州市</option>
							<option value="445200" p="003">揭阳市</option>
							<option value="445300" p="004">云浮市</option>
							<option value="440606" p="002">顺德区</option>
							<option value="440003" p="002">横琴新区</option>
						</select></td>
					<td class="td1"><label id='accepttypeLabel'>业务类型:</label></td>
					<td class="td2"><select class="btu_select" style="width: 150px" id="accepttype" multiple name="dept">
							<option value="1">登记业务</option>
							<option value="2">监管业务</option>
							<option value="3">收费业务</option>
							<option value="4">案件业务</option>
							<option value="5">行政复议</option>
							<option value="6">执法监察</option>
							<option value="7">市场合同</option>
						</select></td>
					<td id="td2" class="td1" style="display: none" colspan="2"></td>
				</tr>
				<tr>
					<td class="td1" colspan="8" style='text-align: center;padding-top:4px;background: #f8fbfd;border: 1px solid #E4E4E4;border-left:none;border-right:none;'>
					<input id="selData" class="btn_submit box-btn"  type="button" value="" onclick="initCharts();" /> 
					<input id="selData_2" class="btn_submit box-btn"  type="button" value="" onclick="initCharts();" /> 
					<!-- <input id="selData" class="bt _first" style="background-color: #41A6E0; color: white;" type="button" value="提交" /> 
					<input id="selData_2" style="background-color: #41A6E0; color: white;" class="bt _second" type="button" value="提交" /> --> 
					<!-- <input style="background-color: #41A6E0; color: white;" id="chartChange" class="bt _second" type="button" value="返回" /> -->
					<input  id="chartChange" class="bt_second" type="button" value="" /> 
				</tr>

			</table>

			<input style="display: none" class="btu" type="radio" name="gender" id="x" value="0" checked="checked">
			<label style="display: none" for="x">日</label>
			<input style="display: none" class="btu" type="radio" name="gender" id="y" value="1">
			<label style="display: none" for="y">月</label>
			<label style="display: none" id="showstart" for="开始">从:</label>
			<label style="display: none" id="showend" for="结束">至:</label>
			<input style="display: none" class="btu" id="showendtime" type="text" readonly="readonly" class="Wdate" value='2016-07-01' onclick="createWdatePickerEnd();" />
			<!-- <input class="btu" class="btu" id="showtime" type="text" readonly="readonly" class="Wdate"
			value='2016-07-01' onclick="createWdatePickerEnd();"/> -->
			<!-- <label id='regorgLabel'>分析指标:</label><select class="btu_select" id="regorg" name="dept"></select> -->
			<!-- <label id='accepttypeLabel'>业务类型:</label><select class="btu_select" id="accepttype" name="dept"></select> -->


		</div>
		<div id="Content">
			<div id="container_fir" style="width: 98%;">
				<div id="container"></div>
			</div>

			<div id="container_sec1" style="float: left; width: 48%; display: none">
				<div id="container2"></div>
			</div>
			<div id="container_sec" style="float: right; width: 48%; display: none">
				<div id="container3"></div>
			</div>
		</div>
	</div>



<script>
/*修正里面不能直接在样式里面 由jazz生成的表单内容产生的样式的问题*/
$(function(){
	$("#regorg1").siblings().find("button").css("background","#fbfdfd");
	$("#accepttype").siblings().find("button").css("background","#fbfdfd");
});
</script>
</body>
</html>
