﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>股权出质信息</title>
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
				titledisplay="true" title="股权出质信息" showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="100%">
		<div> 
			 <div name='regno' text="注册号" textalign="center" width="12%"></div>
			 <div name='entname' text="股权所在公司名称" textalign="center" width="14%"></div>
			 <div name='stockregisterno' text="股权出质登记号" textalign="center"  width="12%"></div>
			 <div name='pledgor' text="出质人" textalign="center"  width="20%"></div>
			 	<div name='blicno' text="出质人证照号码" textalign="center"></div>
			 	<div name='blictype' text="出质人证照类型" textalign="center" ></div>
			 	<div name='cerno' text="出质人证件号码" textalign="center"></div>
				<div name='certype' text="出质人证件类型" textalign="center" ></div>
			
			<div name='imporg' text="质权人" textalign="center"  width="20%"></div>
			<div name='imporgtel' text="质权人联系方式" textalign="center" ></div>
			<div name='zqrblicno' text="质权人证照号码" textalign="center"  ></div>
			<div name='zqrblictype' text="质权人证照类型" textalign="center" ></div>
			<div name='zqrcerno' text="质权人证件号码" textalign="center"  ></div>
			<div name='zqrcertype' text="质权人证件类型" textalign="center" ></div>
			
			<div name='pledamunit' text="出质股权数" textalign="center" ></div>
			<div name='impam' text="出质股权数额单位" textalign="center"></div>
			<div name='applydate' text="受理日期" textalign="center" ></div>
			<div name='approvedate' text="核准日期" textalign="center" ></div>
			<div name='regstatus' text="登记状态" textalign="center"  width="8%"></div>
			<div name='regorg' text="登记机关" textalign="center"  width="14%"></div>
			<div name='rescindopinion' text="解除原因" textalign="center" ></div>
			
			<div name='historyinfoid' text="业务历史编号" textalign="center"></div>
			<div name='stockpledgehostexclusiveid' text="出质人的唯一码" textalign="center" ></div>
	</div>
	</div>
	<div vtype="gridtable" name="grid_table"rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
  <div id="formpanel" class="formpanel_table" style="height:250px;">
			<div class="font_title">详细信息</div>
			<table id="datashow" class="font_table" >
				<tr class="class_hg"><th class="class_td1">注册号:</th><th class="class_td2" id="djrow1"></th><th class="class_td4">股权所在公司名:</th><th id="djrow2" class="class_td5"></th></tr>
				<tr><th class="class_td1">股权出质登记号:</th><th class="class_td2" id="djrow3"/><th class="class_td4">出质人:</th><th class="class_td5" id="djrow4"></th></tr>
				<tr><th class="class_td1">出质人证照号码:</th><th class="class_td2" id="djrow5"/><th class="class_td4">出质人证照类型:</th><th class="class_td5" id="djrow6"></th></tr>
				<tr><th class="class_td1">出质人证件号码:</th><th class="class_td2" id="djrow7"/><th class="class_td4">出质人证件类型:</th><th class="class_td5" id="djrow8"></th></tr>
				
				<tr><th class="class_td1">质权人:</th><th class="class_td2" id="djrow9"/><th class="class_td4">质权人联系方式:</th><th class="class_td5" id="djrow10"></th></tr>
				<tr><th class="class_td1">质权人证照号码:</th><th class="class_td2" id="djrow11"/><th class="class_td4">质权人证照类型:</th><th class="class_td5" id="djrow12"></th></tr>
				<tr><th class="class_td1">质权人证件号码:</th><th class="class_td2" id="djrow13"/><th class="class_td4">质权人证件类型:</th><th class="class_td5" id="djrow14"></th></tr>
				
				<tr><th class="class_td1">出质股权数:</th><th class="class_td2" id="djrow15"/><th class="class_td4">出质股权数单位:</th><th class="class_td5" id="djrow16"></th></tr>
				<tr><th class="class_td1">受理日期:</th><th class="class_td2" id="djrow17"/><th class="class_td4">核准日期:</th><th class="class_td5" id="djrow18"></th></tr>
				<tr><th class="class_td1">登记状态:</th><th class="class_td2" id="djrow19"/><th class="class_td4">登记机关:</th><th class="class_td5" id="djrow20"></th></tr>
				<tr><th class="class_td4">解除原因:</th><th class="class_td5" id="djrow21" colspan="3"></th></tr>
			</table>
	</div>
</body>
</html>