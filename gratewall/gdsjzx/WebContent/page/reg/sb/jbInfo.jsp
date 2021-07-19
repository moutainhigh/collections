<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>基本信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="245" id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="商标信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='agentid' text="代理人代码" visible="false" textalign="center"  width="10%"></div>
			<div name='regno' text="注册号" textalign="center"  width="15%"></div>
			<div name='intcls' text="国际分类" textalign="center"  width="15%"></div>
				<div name='appdate' text="申请日期" visible="false" textalign="center"  width="10%"></div>
			<div name='tmname' text="商标名称" textalign="center"  width="20%"></div>
			<div name='tmnametranslate' text="商标名称意译" textalign="center"  width="15%"></div>
			<div name='tmtype' text="商标类型" textalign="center"  width="15%"></div>
			<div name='firstanncissue' text="初审公告期号" visible="false" textalign="center"  width="10%"></div>
			<div name='firstanncdate' text="初审公告日期" visible="false" textalign="center"  width="10%"></div>
			<div name='reganncissue' text="注册公告期号" visible="false" textalign="center"  width="10%"></div>
			<div name='rrganncdate' text="注册公告日期" visible="false" textalign="center"  width="10%"></div>
			<div name='propertystrdate' text="专用期开始日期" visible="false" textalign="center"  width="10%"></div>
			<div name='propertyenddate' text="专用期结束日期" visible="false" textalign="center"  width="10%"></div>
			<div name='tmcolourdesc' text="商标颜色说明" visible="false" textalign="center"  width="10%"></div>
			<div name='abandonpropertydesc' text="放弃专用权说明" visible="false" textalign="center"  width="10%"></div>
			<div name='issolidtm' text="是否立体商标"  textalign="center"  width="10%"></div>
			<div name='issharetm' text="是否共有商标" textalign="center"  width="10%"></div>
			<div name='tmformtype' text="商标形式类型" visible="false" textalign="center"  width="10%"></div>
			<div name='landmarkinfo' text="地理标志信息" visible="false" textalign="center"  width="10%"></div>
			<div name='coloursign' text="颜色标志信息" visible="false" textalign="center"  width="10%"></div>
			<div name='iswellknowntm' text="是否驰名" visible="false" textalign="center"  width="10%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">注册号:</th><th id="row2" class="class_td2"></th>
			<th class="class_td4">国际分类:</th><th id="row3" class="class_td5"></th></tr>
		<tr><th class="class_td1">申请日期:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">商标名称:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">商标名称意译:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">商标类型:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">初审公告期号:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">初审公告日期:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">注册公告期号:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">注册公告日期:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">专用期开始日期:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">专用期结束日期:</th><th id="row13" class="class_td5"></th></tr>
		<tr><th class="class_td1">商标颜色说明:</th><th id="row14" class="class_td2"></th>
			<th class="class_td4">放弃专用权说明:</th><th id="row15" class="class_td5"></th></tr>
		<tr><th class="class_td1">是否立体商标:</th><th id="row16" class="class_td2"></th>
			<th class="class_td4">是否共有商标:</th><th id="row17" class="class_td5"></th></tr>
		<tr><th class="class_td1">商标形式类型:</th><th id="row18" class="class_td2"></th>
			<th class="class_td4">地理标志信息:</th><th id="row19" class="class_td5"></th></tr>
		<tr><th class="class_td1">颜色标志信息:</th><th id="row20" class="class_td2"></th>
			<th class="class_td4">是否驰名:</th><th id="row21" class="class_td5"></th></tr>
			</tr>
	</table>
</div>
</body>
</html>