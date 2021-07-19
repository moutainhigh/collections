<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>案件基本信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>

<script src="<%=request.getContextPath()%>/static/js/sczt/sczt.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>

<script type="text/javascript">
function rowclick(event,data){
}
</script>
				 
</head>
<body >
<div vtype="gridpanel" name="jbxxGrid" height="230" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="案件基本信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
					<div name='caseid' text="案件记录ID" visible="false" textalign="center"  width="10%"></div>
					<div name='casesrcid' text="案源ID" visible="false" textalign="center"  width="10%"></div>
					<div name='timestamp' text="变" visible="false" textalign="center"  width="10%"></div>
				<div name='caseno' text="案件编号" textalign="center"  width="15%"></div>
				<div name='casetype' text="案件类型" textalign="center"  width="15%"></div>
				<div name='casename' text="案件名称" textalign="center"  width="20%"></div>
				<div name='casescedistrict' text="案发地行政区划" textalign="center"  width="20%"></div>
				<div name='casespot' text="案发地" textalign="center"  width="15%"></div>
				<div name='casetime' text="案发时间" textalign="center"  width="14%"></div>
				<div name='casereason' text="案由" visible="false" textalign="center"  width="10%"></div>
				<div name='caseval' text="案值" visible="false" textalign="center"  width="10%"></div>
				<div name='appprocedure' text="适用程序" visible="false" textalign="center"  width="10%"></div>
				<div name='caseinternetsign' text="是否利用网络" visible="false" textalign="center"  width="10%"></div>
				<div name='caseforsign' text="是否涉外案件" visible="false" textalign="center"  width="10%"></div>
				<div name='casestate' text="案件状态" visible="false" textalign="center"  width="10%"></div>
				<div name='casefiauth' text="立案机关" visible="false" textalign="center"  width="10%"></div>
				<div name='casefidate' text="立案日期" visible="false" textalign="center"  width="10%"></div>
				<div name='exedate' text="执行日期" visible="false" textalign="center"  width="10%"></div>
				<div name='exesort' text="执行类别" visible="false" textalign="center"  width="10%"></div>
				<div name='unexereasort' text="未执行原因类别" visible="false" textalign="center"  width="10%"></div>
				<div name='caseresult' text="案件结果" visible="false" textalign="center"  width="10%"></div>
				<div name='casedep' text="办案机构" visible="false" textalign="center"  width="10%"></div>
				<div name='clocaserea' text="销案理由" visible="false" textalign="center"  width="10%"></div>
				<div name='clocasedate' text="销案日期" visible="false" textalign="center"  width="10%"></div>
					<div name='sourceflag' text="变" visible="false" textalign="center"  width="10%"></div>

	</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">案件编号:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">案件类型:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">案件名称:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">案发地行政区划:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">案发地:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">案发时间:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">案由:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">案值:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">适用程序:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">是否利用网络:</th><th id="row13" class="class_td5"></th></tr>
		<tr><th class="class_td1">是否涉外案件:</th><th id="row14" class="class_td2"></th>
			<th class="class_td4">案件状态:</th><th id="row15" class="class_td5"></th></tr>
		<tr><th class="class_td1">立案机关:</th><th id="row16" class="class_td2"></th>
			<th class="class_td4">立案日期:</th><th id="row17" class="class_td5"></th></tr>
		<tr><th class="class_td1">执行日期:</th><th id="row18" class="class_td2"></th>
			<th class="class_td4">执行类别:</th><th id="row19" class="class_td5"></th></tr>
		<tr><th class="class_td1">未执行原因类别:</th><th id="row20" class="class_td2"></th>
			<th class="class_td4">案件结果:</th><th id="row21" class="class_td5"></th></tr>
		<tr><th class="class_td1">办案机构:</th><th id="row22" class="class_td2"></th>
			<th class="class_td4">销案理由:</th><th id="row23" class="class_td5"></th></tr>
		<tr><th class="class_td1">销案日期:</th><th id="row24" class="class_td2" colspan="3"></th>
			</tr>	
			
	</table>
</div>
</body>
</html>