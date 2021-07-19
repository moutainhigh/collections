<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>清算信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="236" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()"
			selecttype="1"	titledisplay="true" title="清算信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<!-- 表头 -->
	<div vtype="gridcolumn" name="grid_column"  >
		<div> 
		<!-- 清算信息 -->
			<div name='ligprincipal' text="清算负责人" textalign="center"  width="14.3%"></div>
			<div name='liqmem' text="清算组成员" textalign="center"  width="14.3%"></div>
			<div name='debttranee' text="债务承接人" textalign="center"  width="14.3%"></div>
			<div name='claimtranee' text="债权承接人" textalign="center"  width="14.5%"></div>
			<div name='ligst' text="清算完结情况" textalign="center"  width="14.3%"></div>
			<div name='ligenddate' text="清算完结日期" textalign="center"  width="14.3%"></div>
			<div name='addr' text="地址" textalign="center"></div>
			<div name='tel' text="联系电话" textalign="center"  width="14.5%"></div>
	</div>
	</div>
	<!-- 表格 -->
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>

  	<div id="formpanel"  class="formpanel_table1" style="height:210px">
		<div class="font_title">详细信息</div>
			<table id="datashow" class="font_table">
				<tr class="class_hg"><th class="class_td1">清算负责人:</th><th class="class_td2" id="row1"></th><th class="class_td4">清算组成员:</th><th id="row2" class="class_td5"></th></tr>
				<tr><th class="class_td1">债务承接人:</th><th class="class_td2" id="row3"/><th class="class_td4">债权承接人:</th><th class="class_td5" id="row4"></th></tr>
				<tr><th class="class_td1">清算完结情况:</th><th class="class_td2" id="row5"/><th class="class_td4">清算完结日期:</th><th class="class_td5" id="row6"></th></tr>
				<tr><th class="class_td1">地址:</th><th class="class_td2" id="row7"/><th class="class_td4">联系电话:</th><th class="class_td5" id="row8"></th></tr>
			</table>
	</div>
</body>
</html>