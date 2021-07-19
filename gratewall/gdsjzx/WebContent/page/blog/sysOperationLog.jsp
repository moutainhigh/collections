<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>查询系统操作日志</title>
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
$(function(){
	queryUrl();
});
function reset() {
	$("#formpanel").formpanel('reset');
}
function queryUrl() {
	$("#zzjgGrid").gridpanel("hideColumn", "approvedate");
		$("#zzjgGrid").gridpanel("showColumn", "modfydate");
	$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+
			'/blog/querySysLogList.do');
	$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
}

function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var pripid = data[i]["logid"];
		data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="detail(\''+pripid+'\')">查看详细</a></div>';
	}
	return data;
}

function detail(dmbId){
	winEdit = jazz.widget({
  	     vtype: 'window',
	   	     frameurl: './sysOperationDetail.jsp?dmbId='+dmbId+'&update=true',
	  			name: 'win',
	  	    	title: '查看详细',
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 400,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});
}


</script>
</head>
<body>
<div>位置：日志管理>系统日志</div>

<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
	showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="150" title="查询条件">
	<div name='username' vtype="textfield" label="操作人" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='operatetime' vtype="datefield" label="操作时间" labelAlign="right" labelwidth='100px' width="410"></div>
	
	<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
		<div name="query_button" vtype="button" text="查询" 
			icon="../query/queryssuo.png" onclick="queryUrl();"></div>
		<div name="reset_button" vtype="button" text="重置"
			icon="../query/queryssuo.png" click="reset();" ></div>
	</div>
</div>

<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender="renderdata()"
	titledisplay="true" title="查询列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="100%">
		<div>
			<div name='logid' key="true" visible="false"></div>
			<div name='username' text="操作人" textalign="center"  width="10%"></div>
			<div name='ip' text="IP" textalign="center"  width="10%"></div>
			<div name='req' text="参数" textalign="center"  width="35%"></div>
			<div name='url' text="访问地址" textalign="center"  width="20%"></div>
			<div name='operatetime' text="操作时间" textalign="center"  width="15%"></div>
			<div name='custom' text="操作" textalign="center"  width="8%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" ></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
</body>
</html>