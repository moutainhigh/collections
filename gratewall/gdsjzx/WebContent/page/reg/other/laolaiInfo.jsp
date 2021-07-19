<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>老赖信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="235" id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="失信违法执行人信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='infoactiontype' text="infoactiontype" visible="false" textalign="center"  width="10%"></div>
				<div name='id' text="id" visible="false" textalign="center"  width="10%"></div>
				<div name='sextsequence' text="变" visible="false" textalign="center"  width="10%"></div>
			<div name='casecode' text="案件号" textalign="center"  width="15%"></div>
			<div name='iname' text="名称" textalign="center"  width="20%"></div>
				<div name='sexname' text="性别" visible="false" textalign="center"  width="10%"></div>
				<div name='age' text="年龄" visible="false" textalign="center"  width="10%"></div>
			<div name='cardnum' text="证件号" textalign="center"  width="15%"></div>
			<div name='courtname' text="审判法庭" textalign="center"  width="20%"></div>
				<div name='areaid' text="区域代码" visible="false" textalign="center"  width="10%"></div>
			<div name='areaname' text="区域" textalign="center"  width="15%"></div>
			<div name='partytypename' visible="false" text="类型名称" textalign="center"  width="15%"></div>
			<div name='gistcid' text="审判字号" visible="false" textalign="center"  width="10%"></div>
			<div name='gistunit' text="审判单位" visible="false" textalign="center"  width="10%"></div>
			<div name='performance' text="执行情况" visible="false" textalign="center"  width="10%"></div>
			<div name='publishdate' text="颁布时间" visible="false" textalign="center"  width="10%"></div>
			<div name='regdate' text="登记时间" visible="false" textalign="center"  width="10%"></div>
			<div name='performedpart' text="已执行情况" visible="false" textalign="center"  width="10%"></div>
			<div name='unperformpart' text="未执行情况" visible="false" textalign="center"  width="10%"></div>
			<div name='sextvalidflag' text="是否有效" visible="false" textalign="center"  width="10%"></div>
			<div name='tp' text="类型" textalign="center"  width="15%"></div>
				<div name='disreput_type_name' text="变" visible="false" textalign="center"  width="10%"></div>
				<div name='buesinessentity' text="buesinessentity" visible="false" textalign="center"  width="10%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">案件号:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">名称:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">性别:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">年龄:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">证件号:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">审判法庭:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">区域代码:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">区域:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">类型名称:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">审判字号:</th><th id="row13" class="class_td5"></th></tr>
		<tr><th class="class_td1">审判单位:</th><th id="row14" class="class_td2"></th>
			<th class="class_td4">执行情况:</th><th id="row15" class="class_td5"></th></tr>
		<tr><th class="class_td1">颁布时间:</th><th id="row16" class="class_td2"></th>
			<th class="class_td4">登记时间:</th><th id="row17" class="class_td5"></th></tr>
		<tr><th class="class_td1">已执行情况:</th><th id="row18" class="class_td2"></th>
			<th class="class_td4">未执行情况:</th><th id="row19" class="class_td5"></th></tr>
		<tr><th class="class_td1">是否有效:</th><th id="row20" class="class_td2" ></th>
		<th class="class_td4">类型:</th><th id="row21" class="class_td5"></th></tr>
	</table>
</div>
</body>
</html>