<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>案件信息</title>
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
	window.location.href="<%=request.getContextPath()%>/page/general/detail.jsp?priPid="+data.pripid;
}
</script>
</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
		showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="300" title="查询条件">

		<div name='regno' vtype="textfield" label="批准文号" labelAlign="right" labelwidth='100px' width="410"></div>
		<div name='entname' vtype="textfield" label="提交印章人" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='industryphy' vtype="textfield" label="登记证数" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='industryco' vtype="textfield" label="收照人" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='estdate' vtype="datefield" label="注销日期" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='servicestate' vtype="datefield" label="批准日期" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='regorg' vtype="textfield" label="批准机关"  labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='enttype' vtype="textfield" label="注销原因"  labelAlign="right"  labelwidth='100px' width="410"></div>
		
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div name="query_button" vtype="button" text="查询" 
				icon="../query/queryssuo.png" onclick="queryUrl();"></div>
			<div name="reset_button" vtype="button" text="重置"
				icon="../query/queryssuo.png" click="reset();" ></div>
		</div>
	</div>

	<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid'
		titledisplay="true" title="案件信息"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
			<div name='pripid' text='pripid' textalign="center" width="0"></div>
				<div name='regno' text="批准文号" textalign="center"  width="10%"></div>
				<div name='enttype' text="注销原因" textalign="center"  width="10%"></div>
				<div name='regorg' text="批准机关" textalign="center"  width="10%"></div>
				<div name='servicestate' text="批准日期" textalign="center"  width="10%"></div>
				<div name='estdate' text="注销日期" textalign="center"  width="10%"></div>
				<div name='regcap' text="债务量" textalign="center"  width="10%"></div>
				<div name='entname' text="提交印章人" textalign="center"  width="10%"></div>
				<div name='industryco' text="收照人" textalign="center"  width="10%"></div>
				<div name='industryphy' text="登记证数" textalign="center"  width="10%"></div>
				<div name='tel' text="代表证数" textalign="center"  width="10%"></div>
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