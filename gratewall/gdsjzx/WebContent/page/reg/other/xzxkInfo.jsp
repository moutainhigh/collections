<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>行政許可信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="235" id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="行政许可信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='appperinformationid' text="主键" visible="false" textalign="center"  width="10%"></div>
				<div name='taskid' text="变" visible="false" textalign="center"  width="10%"></div>
				<div name='entno' text="企业主键" visible="false" textalign="center"  width="10%"></div>
			<div name='regno' text="企业注册号"  textalign="center"  width="20%"></div>
			<div name='entname' text="企业名称"  textalign="center"  width="25%"></div>
				<div name='orgcode' text="变" visible="false" textalign="center"  width="10%"></div>
			<div name='apppercardid' text="许可文件编号" visible="false" textalign="center"  width="10%"></div>
			<div name='apppername' text="许可文件名称"  textalign="center"  width="20%"></div>
			<div name='apppercontent' text="许可内容" visible="false" textalign="center"  width="10%"></div>
				<div name='principal' text="变" visible="false" textalign="center"  width="10%"></div>
			<div name='appperstarttime' text="有效期自"  textalign="center"  width="15%"></div>
			<div name='appperendtime' text="有效期至"  textalign="center"  width="15%"></div>
			<div name='releaseauthorykey' text="变" visible="false" textalign="center"  width="10%"></div>
			<div name='releaseauthoryvalue' text="许可机关名称" visible="false" textalign="center"  width="10%"></div>
			<!-- <div name='entrypeople' text="变" visible="false" textalign="center"  width="10%"></div>
			<div name='entrytime' text="变" visible="false" textalign="center"  width="10%"></div>
			<div name='accessory' text="变" visible="false" textalign="center"  width="10%"></div>
			<div name='status' text="变" visible="false" textalign="center"  width="10%"></div> -->
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">企业注册号:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">企业名称:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">许可文件编号:</th><th id="row7" class="class_td2"></th>
			<th class="class_td4">许可文件名称:</th><th id="row8" class="class_td5"></th></tr>
		<tr><th class="class_td1">许可内容:</th><th id="row9" class="class_td2"></th>
			<th class="class_td4">有效期自:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">有效期至:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">许可机关名称:</th><th id="row14" class="class_td5"></th></tr>
	</table>
</div>
</body>
</html>