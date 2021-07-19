<%-- <%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<script
	src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js"
	type="text/javascript"></script>

<script
	src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js"
	type="text/javascript"></script>
<style type="text/css">
td {
	text-align: center;
}
#downloadbutton{
	height:50px;;	
	wigth:50px;	
	padding-top:50px;
	background-color: white;
}
#xiao{
	width:50px;
	height:25px;
	text-align: center;
	font-size:x-large;
}
#buffer{
	width:68px;
	height:5px;
	margin-bottom: 10px;
	
}
#bufferimg{
}

.jazz-pagearea {
	height: 0px;
}
</style>

<script type="text/javascript" charset="UTF-8">

function reset() {
	$("#formpanel").formpanel('reset');
}
function downExcel(){
	var aa=$("#formpanel").formpanel('getValue');
	var begintime=aa.data.begintime;
	var endtime=aa.data.endtime;
	var node = $("#buffer");
    node.css("display", "");
    
	if (begintime.length!=0 && endtime.length!=0) {
		if (begintime>endtime) {
			alert("无数据");
			node.css("display", "none");
			return;
		}
		if (begintime==endtime) {
			node.css("display", "none");
			alert("无数据");
			return;
		}
	}
	window.setTimeout(function() {
		 node.css("display", "none");
	}, 5000);
	 var url= rootPath+"/quert12315Controller/downExcel.do?begintime="+begintime+"&endtime="+endtime;
	 location=url;
	//$.ajax({
    //   type: "post",
    //   url: rootPath + "/quert12315Controller/downExcel.do",
    //   data:"begintime="+begintime+"&endtime="+endtime,
    //    dataType: "text",
    //    success: function(data){
    //    	if(data)
    //    		node.css("display", "none");
    //    	}});
}



function queryUrl() {
	//var aa = JSON.stringify($("#formpanel").formpanel('getValue'));
	var aa=$("#formpanel").formpanel('getValue');
	var begintime=aa.data.begintime;
	var endtime=aa.data.endtime;
	var node = $("#buffer");

	if (begintime.length!=0 && endtime.length!=0) {
		if (begintime>endtime) {
			alert("初始日期大于截止日期");
			node.css("display", "none");
			return;
		}
		if (begintime==endtime) {
			alert("初始日期等于截止日期");
			node.css("display", "none");
			return;
		}
	}
	
	
	 document.getElementById('chengbanshu').innerHTML ="";
     document.getElementById('shoulishu').innerHTML ="";
     document.getElementById('tjcgs').innerHTML ="";
     document.getElementById('tjcgl').innerHTML ="";
     document.getElementById('whjjss').innerHTML ="";
     document.getElementById('sza').innerHTML ="";
     document.getElementById('fkje').innerHTML ="";
     document.getElementById('fwz').innerHTML ="";
     document.getElementById('chengbanshu2').innerHTML ="";
     document.getElementById('shoulishuz').innerHTML ="";
     document.getElementById('tjcgsz').innerHTML ="";
     document.getElementById('tjcglz').innerHTML ="";
     document.getElementById('whjjssz').innerHTML ="";
     document.getElementById('szaz').innerHTML ="";
     document.getElementById('fkjez').innerHTML ="";
     document.getElementById('fwzz').innerHTML ="";
     node.css("display", "");
	
	$.ajax({
        type: "post",
        url: rootPath + "/quert12315Controller/queryDate.do",
        data:"begintime="+begintime+"&endtime="+endtime,
        dataType: "text",
        success: function(data){
        			node.css("display", "none");
                    $('#resText').empty();   //清空resText里面的所有内容
                   var obj = new Function("return" + data)();
                    var tjcgl=obj.tjcgs/(obj.shoulishu==0?1:obj.shoulishu)*100;
                    var tjcgl2=obj.tjcgs2/obj.shoulishu2*100;
                    document.getElementById('chengbanshu').innerHTML =obj.chengban;
                    document.getElementById('shoulishu').innerHTML =obj.shoulishu;
                    document.getElementById('tjcgs').innerHTML =obj.tjcgs;
                    document.getElementById('tjcgl').innerHTML =tjcgl.toString().substring(0,5);
                    document.getElementById('whjjss').innerHTML =obj.whjjss;
                    document.getElementById('sza').innerHTML =obj.sza;
                    document.getElementById('fkje').innerHTML =obj.fkje;
                    document.getElementById('fwz').innerHTML =obj.fwz;
                    document.getElementById('chengbanshu2').innerHTML =obj.chengban2;
                    document.getElementById('shoulishuz').innerHTML =obj.shoulishu2;
                    document.getElementById('tjcgsz').innerHTML =obj.tjcgs2;
                    document.getElementById('tjcglz').innerHTML =tjcgl2.toString().substring(0,5);
                    document.getElementById('whjjssz').innerHTML =obj.whjjss2;
                    document.getElementById('szaz').innerHTML =obj.sza2;
                    document.getElementById('fkjez').innerHTML =obj.fkje2;
                    document.getElementById('fwzz').innerHTML =obj.fwz2;
                    
                 }
    });
	
	
	//$('#reportListGrid').gridpanel('option', 'dataurl',
	//		rootPath + '/quert12315Controller/queryDate.do?begintime='+begintime+'&endtime='+endtime);
	//$('#reportListGrid').gridpanel('query', [ 'formpanel' ]);
}


</script>
<style id="消费者投诉处理情况表_24798_Styles"><!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
.font524798
	{color:#333399;
	font-size:10.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font624798
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font724798
	{color:red;
	font-size:16.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:华文中宋;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.xl1524798
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:general;
	vertical-align:bottom;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl6324798
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:#333399;
	font-size:10.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	background:white;
	mso-pattern:black none;
	white-space:normal;}
.xl6424798
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:#333399;
	font-size:10.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"Times New Roman", serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	background:white;
	mso-pattern:black none;
	white-space:normal;}
.xl6524798
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:#333399;
	font-size:10.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:黑体, monospace;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:1.0pt solid windowtext;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl6624798
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:#333399;
	font-size:10.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:黑体, monospace;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	background:white;
	mso-pattern:black none;
	white-space:nowrap;}
.xl6724798
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:10.5pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	background:white;
	mso-pattern:black none;
	white-space:normal;}
.xl6824798
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:10.5pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	background:white;
	mso-pattern:black none;
	white-space:nowrap;}
.xl6924798
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:#333399;
	font-size:10.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:黑体, monospace;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:1.0pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:none;
	border-left:1.0pt solid windowtext;
	background:white;
	mso-pattern:black none;
	white-space:nowrap;}
.xl7024798
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:#333399;
	font-size:10.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:黑体, monospace;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:1.0pt solid windowtext;
	background:white;
	mso-pattern:black none;
	white-space:nowrap;}
.xl7124798
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:#333399;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:1.0pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:none;
	border-left:1.0pt solid windowtext;
	background:white;
	mso-pattern:black none;
	white-space:nowrap;}
.xl7224798
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:#333399;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:1.0pt solid windowtext;
	background:yellow;
	mso-pattern:black none;
	white-space:nowrap;}
.xl7324798
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:#333399;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:1.0pt solid windowtext;
	background:white;
	mso-pattern:black none;
	white-space:normal;}
.xl7424798
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:#333399;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:1.0pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	background:white;
	mso-pattern:black none;
	white-space:normal;}
.xl7524798
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:16.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:华文中宋;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl7624798
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:general;
	vertical-align:bottom;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl7724798
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:#333399;
	font-size:10.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:1.0pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:none;
	border-left:1.0pt solid windowtext;
	background:white;
	mso-pattern:black none;
	white-space:normal;}
.xl7824798
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:#333399;
	font-size:10.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:1.0pt solid windowtext;
	background:white;
	mso-pattern:black none;
	white-space:normal;}
ruby
	{ruby-align:left;}
rt
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-char-type:none;}
--></style>
</head>
<body>


	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="150"
		title="统计条件">
		<div id="begintime" name='begintime' name='endtime' vtype="datefield" label="分析开始日期"
			labelAlign="right" labelwidth='100px' width="310"
			></div>
	
		<div id="endtime" name='endtime' 
				vtype="datefield" label="分析截止日期" labelAlign="right"
				labelwidth='100px' width="310"  ></div>
		
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom"
			align="center">
			<div name="query_button" vtype="button" text="查询"
				icon="../query/queryssuo.png" onclick="queryUrl();"></div>
			<div name="reset_button" vtype="button" text="重置"
				icon="../query/queryssuo.png" click="reset();"></div>
		</div>
	</div>



	<div vtype="gridpanel" name="reportListGrid" id="reportListGrid"
		height="400" width="100%" datarender="renderdata" titledisplay="true"
		title="查询结果" dataurl="" layout="fit" showborder="false">
	</div>
		<!-- 表头 -->
		<div id="消费者投诉处理情况表_24798" align=center x:publishsource="Excel">
		
<table border=0 cellpadding=0 cellspacing=0 width=966 style='border-collapse:
 collapse;table-layout:fixed;width:726pt'>
 <col width=165 style='mso-width-source:userset;mso-width-alt:5280;width:124pt'>
 <col width=80 style='mso-width-source:userset;mso-width-alt:2560;width:60pt'>
 <col width=117 span=2 style='mso-width-source:userset;mso-width-alt:3744;
 width:88pt'>
 <col width=85 span=2 style='mso-width-source:userset;mso-width-alt:2720;
 width:64pt'>
 <col width=158 style='mso-width-source:userset;mso-width-alt:5056;width:119pt'>
 <col width=159 style='mso-width-source:userset;mso-width-alt:5088;width:119pt'>
 <tr height=30 style='height:22.5pt'>
  <td colspan=8 height=30 class=xl7524798 width=966 style='height:22.5pt;
  width:726pt'>　　消费者投诉处理情况表<font class="font724798"></font></td>
 </tr>
 <tr height=20 style='height:15.0pt'>
  <td rowspan=2 height=60 class=xl6924798 style='border-bottom:1.0pt solid black;
  height:45.0pt;border-top:none'>消费者投诉处理情况</td>
  <td rowspan=2 class=xl7124798 style='border-bottom:1.0pt solid black;
  border-top:none'>单位</td>
  <td colspan=2 class=xl7324798 width=234 style='border-right:1.0pt solid black;
  border-left:none;width:176pt'>本年情况</td>
  <td colspan=2 class=xl7324798 width=170 style='border-right:1.0pt solid black;
  border-left:none;width:128pt'>上年情况</td>
  <td rowspan=2 class=xl7724798 width=158 style='border-bottom:1.0pt solid black;
  border-top:none;width:119pt'>本年累计比上年同期增减</td>
  <td rowspan=2 class=xl7724798 width=159 style='border-bottom:1.0pt solid black;
  border-top:none;width:119pt'>本年累计比上年同期增减%</td>
 </tr>
 <tr height=40 style='mso-height-source:userset;height:30.0pt'>
  <td height=40 class=xl6324798 width=117 style='height:30.0pt;width:88pt'>本月</td>
  <td class=xl6424798 width=117 style='width:88pt'>1-<font class="font524798">本月累计</font></td>
  <td class=xl6324798 width=85 style='width:64pt'>本月</td>
  <td class=xl6424798 width=85 style='width:64pt'>1-<font class="font524798">本月累计</font></td>
 </tr>
 <tr height=20 style='height:15.0pt'>
  <td height=20 class=xl6524798 style='height:15.0pt'>承办数</td>
  <td class=xl6624798>件</td>
  <td id="chengbanshu" class=xl6724798 align=right width=117 style='width:88pt'></td>
  <td id="chengbanshu2" class=xl6724798 align=right width=117 style='width:88pt'></td>
  <td class=xl6724798 width=85 style='width:64pt'>　</td>
  <td class=xl6724798 width=85 style='width:64pt'>　</td>
  <td class=xl6724798 width=158 style='width:119pt'>　</td>
  <td class=xl6724798 width=159 style='width:119pt'>　</td>
 </tr>
 <tr height=20 style='height:15.0pt'>
  <td height=20 class=xl6524798 style='height:15.0pt'>受理数</td>
  <td class=xl6624798>件</td>
  <td  id="shoulishu" class=xl6724798 align=right width=117 style='width:88pt72'></td>
  <td id="shoulishuz" class=xl6824798 align=right></td>
  <td class=xl6724798 width=85 style='width:64pt'>　</td>
  <td class=xl6724798 width=85 style='width:64pt'>　</td>
  <td class=xl6724798 width=158 style='width:119pt'>　</td>
  <td class=xl6724798 width=159 style='width:119pt'>　</td>
 </tr>
 <tr height=20 style='height:15.0pt'>
  <td height=20 class=xl6524798 style='height:15.0pt'>调解成功数</td>
  <td class=xl6624798>件</td>
  <td id="tjcgs" class=xl6724798 align=right width=117 style='width:88pt'></td>
  <td id="tjcgsz" class=xl6724798 align=right width=117 style='width:88pt'></td>
  <td class=xl6724798 width=85 style='width:64pt'>　</td>
  <td class=xl6724798 width=85 style='width:64pt'>　</td>
  <td class=xl6724798 width=158 style='width:119pt'>　</td>
  <td class=xl6724798 width=159 style='width:119pt'>　</td>
 </tr>
 <tr height=20 style='height:15.0pt'>
  <td height=20 class=xl6524798 style='height:15.0pt'>调解成功率</td>
  <td class=xl6624798>%</td>
  <td id="tjcgl" class=xl6724798 align=right width=117 style='width:88pt'></td>
  <td id="tjcglz" class=xl6724798 align=right width=117 style='width:88pt'></td>
  <td class=xl6724798 width=85 style='width:64pt'>　</td>
  <td class=xl6724798 width=85 style='width:64pt'>　</td>
  <td class=xl6724798 width=158 style='width:119pt'>　</td>
  <td class=xl6724798 width=159 style='width:119pt'>　</td>
 </tr>
 <tr height=20 style='height:15.0pt'>
  <td height=20 class=xl6524798 style='height:15.0pt'>挽回损失</td>
  <td class=xl6624798>万元</td>
  <td id="whjjss" class=xl6724798 align=right width=117 style='width:88pt'></td>
  <td id="whjjssz" class=xl6724798 align=right width=117 style='width:88pt'></td>
  <td class=xl6724798 width=85 style='width:64pt'>　</td>
  <td class=xl6724798 width=85 style='width:64pt'>　</td>
  <td class=xl6724798 width=158 style='width:119pt'>　</td>
  <td class=xl6724798 width=159 style='width:119pt'>　</td>
 </tr>
 <tr height=20 style='height:15.0pt'>
  <td height=20 class=xl6524798 style='height:15.0pt'>诉转案立案数</td>
  <td class=xl6624798>件</td>
  <td id="sza" class=xl6724798 align=right width=117 style='width:88pt'></td>
  <td id="szaz"class=xl6724798 align=right width=117 style='width:88pt'></td>
  <td class=xl6724798 width=85 style='width:64pt'>　</td>
  <td class=xl6724798 width=85 style='width:64pt'>　</td>
  <td class=xl6724798 width=158 style='width:119pt'>　</td>
  <td class=xl6724798 width=159 style='width:119pt'>　</td>
 </tr>
 <tr height=20 style='height:15.0pt'>
  <td height=20 class=xl6524798 style='height:15.0pt'>诉转案罚款金额</td>
  <td class=xl6624798>万元</td>
  <td id="fkje" class=xl6724798 align=right width=117 style='width:88pt'></td>
  <td id="fkjez" class=xl6724798 align=right width=117 style='width:88pt'></td>
  <td class=xl6724798 width=85 style='width:64pt'>　</td>
  <td class=xl6724798 width=85 style='width:64pt'>　</td>
  <td class=xl6724798 width=158 style='width:119pt'>　</td>
  <td class=xl6724798 width=159 style='width:119pt'>　</td>
 </tr>
 <tr height=20 style='height:15.0pt'>
  <td height=20 class=xl6524798 style='height:15.0pt'>转服务站处理数</td>
  <td class=xl6624798>件</td>
  <td id="fwz" class=xl6724798 align=right width=117 style='width:88pt'></td>
  <td id="fwzz" class=xl6724798 align=right width=117 style='width:88pt'></td>
  <td class=xl6724798 width=85 style='width:64pt'>　</td>
  <td class=xl6724798 width=85 style='width:64pt'>　</td>
  <td class=xl6724798 width=158 style='width:119pt'>　</td>
  <td class=xl6724798 width=159 style='width:119pt'>　</td>
 </tr>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=165 style='width:124pt'></td>
  <td width=80 style='width:60pt'></td>
  <td width=117 style='width:88pt'></td>
  <td width=117 style='width:88pt'></td>
  <td width=85 style='width:64pt'></td>
  <td width=85 style='width:64pt'></td>
  <td width=158 style='width:119pt'></td>
  <td width=159 style='width:119pt'></td>
 </tr>
 <![endif]>
</table>
		<div id ="downloadbutton">
			<div style="display:none;" id="buffer" >
				<img hid="bufferimg" src="images/buffer.gif">
			</div>
			<div href="#" name="reset_button" vtype="button" text="导  出"
			id ="xiao" icon="../query/queryssuo.png" click="downExcel()"></div>
		</div>
</div>
		<!-- 表格 -->
		<!-- ../../grid/reg3.json -->
		<div vtype="gridtable" name="grid_table"></div>
		
</body>
</html> --%>



