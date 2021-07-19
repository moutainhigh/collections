<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>变更信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="230" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="从业人员信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='cyryid' text="变" visible="false" textalign="center"  width="10%"></div>
				<div name='sourceflag' text="变" visible="false" textalign="center"  width="10%"></div>
				<div name='timestamp' text="变" visible="false" textalign="center"  width="10%"></div>
			<div name='name' text="姓名" textalign="center"  width="20%"></div>
			<div name='sex' text="性别" textalign="center"  width="10%"></div>
			<div name='adpratype' text="广告从业人员类型" textalign="center"  width="15%"></div>
			<div name='natdate' text="出生日期" textalign="center"  width="15%"></div>
			<div name='edubac' text="学历"  textalign="center"  width="12%"></div>
			<div name='specialty' text="专业"  textalign="center"  width="12%"></div>
			<div name='cername' text="证件名称" textalign="center"  width="15%"></div>
			<div name='certype' text="证件类型" visible="false" textalign="center"  width="10%"></div>
			<div name='cerno' text="证件号码" visible="false" textalign="center"  width="10%"></div>
			<div name='graduateno' text="毕业证号" visible="false" textalign="center"  width="10%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">姓名:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">性别:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">广告从业人员类型:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">出生日期:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">学历:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">专业:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">证件名称:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">证件类型:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">证件号码:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">毕业证号:</th><th id="row13" class="class_td5"></th></tr>
	</table>
</div>
</body>
</html>