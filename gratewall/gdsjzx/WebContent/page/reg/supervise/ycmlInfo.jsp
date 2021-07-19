<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>异常名录信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="230" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="异常名录信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='abnormalqiyeid' text="主键ID" visible="false" textalign="center"  width="10%"></div>
				<div name='pripid' text="Market主键" visible="false" textalign="center"  width="10%"></div>
				<div name='abnormalid' text="异常名录ID" visible="false" textalign="center"  width="10%"></div>
			<div name='regno' text="注册号"  textalign="center"  width="15%"></div>
			<div name='uniscid' text="统一社会信用代码"  textalign="center"  width="15%"></div>
			<div name='entname' text="企业名称" textalign="center"  width="20%"></div>
			<div name='lerep' text="企业法人" textalign="center"  width="15%"></div>
			<div name='specause' text="列入经营异常名录原因" textalign="center"  width="17%"></div>
			<div name='abntime' text="列入日期" visible="false" textalign="center"  width="10%"></div>
			<div name='remexcpres' text="移出经营异常名录原因" textalign="center"  width="17%"></div>
			<div name='remdate' text="移出日期" visible="false" textalign="center"  width="10%"></div>
			<div name='decorg' text="作出决定机关" visible="false" textalign="center"  width="10%"></div>
			<div name='auditingfileno' text="决定书文号" visible="false" textalign="center"  width="10%"></div>
			<div name='reconsiderationorg' text="复议机关-工商部门" visible="false" textalign="center"  width="10%"></div>
			<div name='litigationorg' text="复议机关-人民政府" visible="false" textalign="center"  width="10%"></div>
			<div name='governmentorg' text="诉讼机关-人民法院" visible="false" textalign="center"  width="10%"></div>
			<div name='area_code' text="区域" visible="false" textalign="center"  width="10%"></div>
			<div name='biaoji' text="区分企业.个体.农专" visible="false" textalign="center"  width="10%"></div>
				<div name='sourceflag' text="变" visible="false" textalign="center"  width="10%"></div>
				<div name='timestamp' text="变" visible="false" textalign="center"  width="10%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">注册号:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">统一社会信用代码:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">企业名称:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">企业法人:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">列入经营异常名录原因:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">列入日期:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">移出经营异常名录原因:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">移出日期:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">作出决定机关:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">决定书文号:</th><th id="row13" class="class_td5"></th></tr>
		<tr><th class="class_td1">复议机关-工商部门:</th><th id="row14" class="class_td2"></th>
			<th class="class_td4">复议机关-人民政府:</th><th id="row15" class="class_td5"></th></tr>
		<tr><th class="class_td1">诉讼机关-人民法院:</th><th id="row16" class="class_td2"></th>
			<th class="class_td4">区域:</th><th id="row17" class="class_td5"></th></tr>
		<tr><th class="class_td1">区分企业.个体.农专:</th><th id="row18" class="class_td2" colspan="3"></th>
			</tr>
	</table>
</div>
</body>
</html>