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
<div vtype="gridpanel" name="jbxxGrid" height="230" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="广告经营基本信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='adid' text="主键" visible="false" textalign="center"  width="10%"></div>
				<div name='pripid' text="市场主体主键" visible="false" textalign="center"  width="10%"></div>
				<div name='sourceflag' text="变" visible="false" textalign="center"  width="10%"></div>
			<div name='adproprietor' text="广告经营者" textalign="center"  width="20%"></div>
			<div name='adbuenttype' text="广告经营单位类型" textalign="center"  width="15%"></div>
			<div name='adbuentchar' text="广告经营单位性质" textalign="center"  width="15%"></div>
			<div name='adope' text="广告经营额" textalign="center"  width="15%"></div>
			<div name='tax' text="纳税额" textalign="center"  width="15%"></div>
			<div name='opetype' text="广告业务类型" textalign="center"  width="15%"></div>
			<div name='adbuent' text="广告经营单位" visible="false" textalign="center"  width="10%"></div>
			<div name='adopeorg' text="广告经营机构" visible="false" textalign="center"  width="10%"></div>
			<div name='oploc' text="经营场所" visible="false" textalign="center"  width="10%"></div>
			<div name='postalcode' text="邮政编码" visible="false" textalign="center"  width="10%"></div>
			<div name='adopfrom' text="广告经营期限自" visible="false" textalign="center"  width="10%"></div>
			<div name='adopto' text="广告经营期限至" visible="false" textalign="center"  width="10%"></div>
			<div name='adlicbu' text="广告许可经营范围" visible="false" textalign="center"  width="10%"></div>
			<div name='regcap' text="注册资本(金)" visible="false" textalign="center"  width="10%"></div>
			<div name='maglicno' text="媒介单位许可证号" visible="false" textalign="center"  width="10%"></div>
			<div name='remark' text="备注" visible="false" textalign="center"  width="10%"></div>
			<div name='adopeinc' text="广告营业收入" visible="false" textalign="center"  width="10%"></div>
			<div name='forbusiadinc' text="外商来华广告收入" visible="false" textalign="center"  width="10%"></div>
			<div name='netinc' text="净利润" visible="false" textalign="center"  width="10%"></div>
			<div name='deficit' text="亏损额" visible="false" textalign="center"  width="10%"></div>
				<div name='timestamp' text="变" visible="false" textalign="center"  width="10%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">广告经营者:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">广告经营单位类型:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">广告经营单位性质:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">广告经营额:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">纳税额:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">广告业务类型:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">广告经营单位:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">广告经营机构:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">经营场所:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">邮政编码:</th><th id="row13" class="class_td5"></th></tr>
		<tr><th class="class_td1">广告经营期限自:</th><th id="row14" class="class_td2"></th>
			<th class="class_td4">广告经营期限至:</th><th id="row15" class="class_td5"></th></tr>
		<tr><th class="class_td1">广告许可经营范围:</th><th id="row16" class="class_td2"></th>
			<th class="class_td4">注册资本(金):</th><th id="row17" class="class_td5"></th></tr>
		<tr><th class="class_td1">媒介单位许可证号:</th><th id="row18" class="class_td2"></th>
			<th class="class_td4">备注:</th><th id="row19" class="class_td5"></th></tr>
		<tr><th class="class_td1">广告营业收入:</th><th id="row20" class="class_td2"></th>
			<th class="class_td4">外商来华广告收入:</th><th id="row21" class="class_td5"></th></tr>
		<tr><th class="class_td1">净利润:</th><th id="row22" class="class_td2"></th>
			<th class="class_td4">亏损额:</th><th id="row23" class="class_td5"></th></tr>
	</table>
</div>
</body>
</html>