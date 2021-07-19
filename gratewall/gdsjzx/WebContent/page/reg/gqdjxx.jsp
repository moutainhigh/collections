﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>股权冻结信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sczt/sczt.js"></script>

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>
<script type="text/javascript">

function rowclick(event,data){
	
}
				 
function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var sharfroprop = data[i]["sharfroprop"];
		data[i]["sharfroprop"] = sharfroprop+'%';
		var froam = data[i]["froam"];
		data[i]["froam"] = froam+'万元';
	}
	return data;
}
</script>
				 
</head>
<body>
<div vtype="gridpanel" name="jbxxGrid" height="236" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()"
			selecttype="1"	titledisplay="true" title="股权冻结信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="100%">
		<div> 
			 <div name='investinfo' text="股东信息" textalign="center"  width="20%"></div>
			 <div name='frodocno' text="冻结文号" textalign="center"  width="20%"></div>
			 <div name='freezeitem' text="冻结事项" textalign="center"  width="20%"></div>
			 <div name='froam' text="冻结金额" textalign="center" width="10%"></div>
			 <div name='sharfroprop' text="冻结比例" textalign="center"  width="7%"></div>
			 <div name='froauth' text="冻结机关" textalign="center"  width="15%"></div>
				<div name='frofrom' text="冻结起始日期" textalign="center"></div>
				<div name='froto' text="冻结截止日期" textalign="center"></div>
			<div name='frozsign' text="冻结标志" textalign="center" width="8%"></div>
			 <div name='thawdocno' text="解冻文号" textalign="center" ></div>
			 <div name='thawgist' text="解冻依据" textalign="center" ></div>
			 <div name='thawauth' text="解冻机关" textalign="center" ></div>
			 <div name='thawdate' text="解冻日期" textalign="center" ></div>
				<div name='corentname' text="相关企业名称" textalign="center"></div>
				<div name='exestate' text="执行状态" textalign="center"></div>
				<div name='historyinfoid' text="业务历史编号" textalign="center" ></div>
	</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
	<div id="formpanel" class="formpanel_table" style="height:300px;margin-bottom:15px;">
			<div class="font_title">详细信息</div>
			<table id="datashow" class="font_table" >
				<tr class="class_hg"><th class="class_td1">股东信息:</th><th class="class_td2" id="czrow1"></th><th class="class_td4">冻结文号:</th><th id="czrow2" class="class_td5"></th></tr>
				<tr><th class="class_td1">冻结事项:</th><th class="class_td2" id="czrow3" colspan="3"></th></tr>
				<tr><th class="class_td1">冻结金额:</th><th class="class_td2" id="czrow4"></th><th class="class_td4">冻结比例:</th><th class="class_td5" id="czrow5"></th></tr>
				<tr><th class="class_td1">冻结机关:</th><th class="class_td2" id="czrow6"></th><th class="class_td4">冻结标志:</th><th class="class_td5" id="czrow9"></th></tr>
				<tr><th class="class_td1">冻结起始日期:</th><th class="class_td2" id="czrow7"></th><th class="class_td4">冻结截止日期:</th><th class="class_td5" id="czrow8"></th></tr>
				<tr><th class="class_td1">解冻文号:</th><th class="class_td2" id="czrow9"></th><th class="class_td4">解冻依据:</th><th class="class_td5" id="czrow10"></th></tr>
				<tr><th class="class_td1">解冻机关:</th><th class="class_td2" id="czrow11"></th><th class="class_td4">解冻日期:</th><th class="class_td5" id="czrow12"></th></tr>
				<tr><th class="class_td1">相关企业名称:</th><th class="class_td2" id="czrow13"></th><th class="class_td4">执行状态:</th><th class="class_td5" id="czrow14"></th></tr>
			</table>
	</div>
</body>
</html>