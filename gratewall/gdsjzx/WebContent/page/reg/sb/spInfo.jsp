<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>商品信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="245" id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="商品信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
			<div name='regno' text="注册号" textalign="center"  width="20%"></div>
			<div name='intcls' text="国际分类" textalign="center"  width="19%"></div>
			<div name='goodscode' text="商品编码" textalign="center"  width="20%"></div>
			<div name='similarcode' text="类似群编码" textalign="center"  width="20%"></div>
			<div name='goodscnname' text="中文名称" textalign="center"  width="20%"></div>
			<div name='doodsseqnum' text="商品序号" visible="false" textalign="center"  width="10%"></div>
			<div name='delsign' text="删除标志" visible="false" textalign="center"  width="10%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">注册号:</th><th id="row1" class="class_td2"></th>
			<th class="class_td4">国际分类:</th><th id="row2" class="class_td5"></th></tr>
		<tr><th class="class_td1">商品编码:</th><th id="row3" class="class_td2"></th>
			<th class="class_td4">类似群编码:</th><th id="row4" class="class_td5"></th></tr>
		<tr><th class="class_td1">中文名称:</th><th id="row5" class="class_td2"></th>
			<th class="class_td4">商品序号:</th><th id="row6" class="class_td5"></th></tr>
		<tr><th class="class_td1">删除标志:</th><th id="row7" class="class_td2" colspan="3"></th>
			</tr>
	</table>
</div>
</body>
</html>