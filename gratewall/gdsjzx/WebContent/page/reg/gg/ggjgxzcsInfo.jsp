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
<div vtype="gridpanel" name="jbxxGrid" height="230" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="广告监管行政措施信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='jgxzid' text="主键" visible="false" textalign="center"  width="10%"></div>
				<div name='sourceflag' text="变" visible="false" textalign="center"  width="10%"></div>
				<div name='timestamp' text="变" visible="false" textalign="center"  width="10%"></div>
			<div name='adhost' text="广告主" textalign="center"  width="20%"></div>
			<div name='adproprietor' text="广告经营者" textalign="center"  width="20%"></div>
			<div name='adissent' text="广告发布单位" textalign="center"  width="15%"></div>
			<div name='adname' text="广告名称" textalign="center"  width="20%"></div>
			<div name='admedia' text="广告媒介" textalign="center"  width="12%"></div>
			<div name='medianame' text="媒体名称" textalign="center"  width="12%"></div>
			<div name='gostype' text="广告内容类别" visible="false" textalign="center"  width="10%"></div>
			<div name='adisstime' text="广告发布时间" visible="false" textalign="center"  width="10%"></div>
			<div name='adisstimelen' text="广告发布时长" visible="false" textalign="center"  width="10%"></div>
			<div name='adissarea' text="广告发布区域" visible="false" textalign="center"  width="10%"></div>
			<div name='layout' text="版面" visible="false" textalign="center"  width="10%"></div>
			<div name='spf' text="规格" visible="false" textalign="center"  width="10%"></div>
			<div name='admmea' text="广告监管行政措施" visible="false" textalign="center"  width="10%"></div>
			<div name='admonauth' text="广告监管机关" visible="false" textalign="center"  width="10%"></div>
			<div name='setdate' text="处理日期" visible="false" textalign="center"  width="10%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">广告主:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">广告经营者:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">广告发布单位:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">广告名称:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">广告媒介:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">媒体名称:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">广告内容类别:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">广告发布时间:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">广告发布时长:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">广告发布区域:</th><th id="row13" class="class_td5"></th></tr>
		<tr><th class="class_td1">版面:</th><th id="row14" class="class_td2"></th>
			<th class="class_td4">规格:</th><th id="row15" class="class_td5"></th></tr>
		<tr><th class="class_td1">广告监管行政措施:</th><th id="row16" class="class_td2"></th>
			<th class="class_td4">广告监管机关:</th><th id="row17" class="class_td5"></th></tr>
		<tr><th class="class_td1">处理日期:</th><th id="row18" class="class_td2" colspan="3"></th>
			</tr>
	</table>
</div>
</body>
</html>