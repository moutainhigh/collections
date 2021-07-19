<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>投诉举报</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<style type="text/css">
td{
	text-align: center;
}
.jazz-pagearea{
	height: 0px;
}
</style>
<script>
function reset() {
	$("#formpanel").formpanel('reset');
}
function queryUrl() {
	$("#zzjgGrid").gridpanel("hideColumn", "approvedate");
		$("#zzjgGrid").gridpanel("showColumn", "modfydate");
	$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+
			'/query/list.do');
	$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
}

function rowclick(event,data){
	window.open("<%=request.getContextPath()%>/page/general/ts_detail_tsjb.jsp?priPid="+data.pripid);
}
</script>
</head>
<body>
	<div>位置:通用查询>广告监管信息>投诉举报</div>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
		showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="300" title="查询条件">

		<div name='fbsjq' vtype="textfield" label="投诉举报方"  labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='zjhm' vtype="textfield" label="广告名称"  labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='ggmc' vtype="textfield" label="广告主名称"  labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='djdw' vtype="datefield" label="发布时间"  labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='spmc1' vtype="datefield" label="发布时间至"  labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='spmc2' vtype="comboxfield" label="广告来源" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div name="query_button" vtype="button" text="查询" 
				icon="../query/queryssuo.png" onclick="queryUrl();"></div>
			<div name="reset_button" vtype="button" text="重置"
				icon="../query/queryssuo.png" click="reset();" ></div>
		</div>
	</div>

	<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid'
		titledisplay="true" title="身份信息"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				
				咨询信息未定
				<div name='name' text="姓名" textalign="center"  width="20%"></div>
				<div name='sex' text="性别" textalign="center"  width="20%"></div>
				<div name='regorg' text="证件名称" textalign="center"  width="20%"></div>
				<div name='regno' text="证件号码" textalign="center"  width="20%"></div>
				<div name='addr' text="住所" textalign="center"  width="10%"></div>
				<div name='tel' text="联系电话" textalign="center"  width="10%"></div>
			</div>
		</div>
		<!-- 表格 -->
		<!-- ../../grid/reg3.json -->
		<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
	</div>
</body>
</html>