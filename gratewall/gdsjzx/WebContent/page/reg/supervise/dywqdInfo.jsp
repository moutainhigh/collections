<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>抵押物清单信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="98%" id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="抵押物清单信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='pawnitemid' text="编号" visible="false" textalign="center"  width="10%"></div>
				<div name='pledgeid' text="pledgeid" visible="false" textalign="center"  width="10%"></div>
				<div name='sourceflag' text="SOURCEFLAG" visible="false" textalign="center"  width="10%"></div>
			<div name='pawnname' text="名称" textalign="center"  width="20%"></div>
			<div name='brand' text="品牌" textalign="center"  width="15%"></div>
			<div name='modelspec' text="型号规格" textalign="center"  width="15%"></div>
			<div name='type' text="类别" textalign="center"  width="15%"></div>
			<div name='leavefactorydate' text="出厂日期" textalign="center"  width="15%"></div>
			<div name='workableyear' text="使用年限" textalign="center"  width="10%"></div>
			    <div name='amount' text="数量" visible="false" textalign="center"  width="10%"></div>
			<div name='pawnvalue' text="价值" textalign="center"  width="10%"></div>
			<div name='place' text="存放地" visible="false" textalign="center"  width="10%"></div>
			<div name='evaluativevalue' text="估价" visible="false" textalign="center"  width="10%"></div>
			<div name='titledeedid' text="所有权登记证号" textalign="center"  width="10%"></div>
			<div name='ownexpiredate' text="权属期限" visible="false" textalign="center"  width="10%"></div>
			<div name='isforeignplace' text="是否属于异地存放" visible="false" textalign="center"  width="10%"></div>
			<div name='foreignregdept' text="异地工商局名称" visible="false" textalign="center"  width="10%"></div>
			<div name='foreignregdeptid' text="异地工商局代码" visible="false" textalign="center"  width="10%"></div>
			<div name='managelimit' text="抵押物处分权限制" visible="false" textalign="center"  width="10%"></div>
			<div name='unit' text="抵押物单位" visible="false" textalign="center"  width="10%"></div>
			<div name='remark' text="备注" visible="false" textalign="center"  width="10%"></div>
				<div name='timestamp' text="TIMESTAMP" visible="false" textalign="center"  width="10%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">名称:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">品牌:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">型号规格:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">类别:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">出厂日期:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">使用年限:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">数量:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">价值:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">存放地:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">估价:</th><th id="row13" class="class_td5"></th></tr>
		<tr><th class="class_td1">所有权登记证号:</th><th id="row14" class="class_td2"></th>
			<th class="class_td4">权属期限:</th><th id="row15" class="class_td5"></th></tr>
		<tr><th class="class_td1">是否属于异地存放:</th><th id="row16" class="class_td2"></th>
			<th class="class_td4">异地工商局名称:</th><th id="row17" class="class_td5"></th></tr>
		<tr><th class="class_td1">异地工商局代码:</th><th id="row18" class="class_td2"></th>
			<th class="class_td4">抵押物处分权限制:</th><th id="row19" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押物单位:</th><th id="row20" class="class_td2"></th>
			<th class="class_td4">备注:</th><th id="row21" class="class_td5"></th></tr>
	</table>
</div>
</body>
</html>