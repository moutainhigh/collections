<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>案件基本信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="230" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="违法行为及处罚信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='illegactid' text="案件处罚ID" visible="false" textalign="center"  width="10%"></div>
				<div name='caseid' text="案件基本信息ID" visible="false" textalign="center"  width="10%"></div>
				<div name='timestamp' text="变" visible="false" textalign="center"  width="10%"></div>
			<div name='illegacttype' text="违法行为种类"  textalign="center"  width="15%"></div>
			<div name='illegact' text="违法行为"  textalign="center"  width="20%"></div>
			<div name='illegincome' text="违法所得"  textalign="center"  width="15%"></div>
			<div name='quabasis' text="定性依据" textalign="center"  width="15%"></div>
			<div name='penbasis' text="处罚依据"  textalign="center"  width="15%"></div>
			<div name='pentype' text="处罚种类" visible="false" textalign="center"  width="10%"></div>
			<div name='penresult' text="处罚结果"  textalign="center"  width="19%"></div>
			<div name='penam' text="罚款金额" visible="false" textalign="center"  width="10%"></div>
			<div name='forfam' text="没收金额" visible="false" textalign="center"  width="10%"></div>
			<div name='apprcuram' text="变价金额" visible="false" textalign="center"  width="10%"></div>
			<div name='pendecissdate' text="处罚决定书签发日期" visible="false" textalign="center"  width="10%"></div>
			<div name='penexest' text="处罚执行情况" visible="false" textalign="center"  width="10%"></div>
			<div name='caseigdegree' text="违法程度" visible="false" textalign="center"  width="10%"></div>
			<div name='lenity' text="是否宽大处理" visible="false" textalign="center"  width="10%"></div>
			<div name='sertime' text="处罚决定书送达时间" visible="false" textalign="center"  width="10%"></div>
			<div name='penauth' text="处罚机关" visible="false" textalign="center"  width="10%"></div>
			<div name='penauth_cn' text="处罚机关名称" visible="false" textalign="center"  width="10%"></div>
			<div name='pendecno' text="处罚决定书文号" visible="false" textalign="center"  width="10%"></div>
			<div name='illegfact' text="主要违法事实" visible="false" textalign="center"  width="10%"></div>
			<div name='sourceflag' text="变" visible="false" textalign="center"  width="10%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">违法行为种类:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">违法行为:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">违法所得:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">定性依据:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">处罚依据:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">处罚种类:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">处罚结果:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">罚款金额:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">没收金额:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">变价金额:</th><th id="row13" class="class_td5"></th></tr>
		<tr><th class="class_td1">处罚决定书签发日期:</th><th id="row14" class="class_td2"></th>
			<th class="class_td4">处罚执行情况:</th><th id="row15" class="class_td5"></th></tr>
		<tr><th class="class_td1">违法程度:</th><th id="row16" class="class_td2"></th>
			<th class="class_td4">是否宽大处理:</th><th id="row17" class="class_td5"></th></tr>
		<tr><th class="class_td1">处罚决定书送达时间:</th><th id="row18" class="class_td2"></th>
			<th class="class_td4">处罚机关:</th><th id="row19" class="class_td5"></th></tr>
		<tr><th class="class_td1">处罚机关名称:</th><th id="row20" class="class_td2"></th>
			<th class="class_td4">处罚决定书文号:</th><th id="row21" class="class_td5"></th></tr>
		<tr><th class="class_td1">主要违法事实:</th><th id="row22" class="class_td2" colspan="3"></th>
			</tr>
	</table>
</div>
</body>
</html>