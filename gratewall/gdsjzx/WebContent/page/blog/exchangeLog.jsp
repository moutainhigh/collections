<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>查询交换日志</title>
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
body{
overflow-x: hidden
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
	$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+'/blog/findShareLogList.do');
	$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
}
function rowclick(event,data){
	window.location.href="<%=request.getContextPath()%>/page/general/detail.jsp?priPid="+data.fwrzjbid;
}
function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var fwrzjbid = data[i]["fwrzjbid"];
		data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="detail(\''+fwrzjbid+'\')">查看详细</a></div>';
	}
	return data;
}

function detail(fwrzjbid){
	winEdit = jazz.widget({
  	     vtype: 'window',
	   	     frameurl: './shareDetail.jsp?fwrzjbid='+fwrzjbid+'&update=true',
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
<div>位置：日志管理>服务日志</div>
<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
	showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="200" title="查询条件">

<div name='callername' vtype="textfield" label="调用者名称" labelAlign="right" labelwidth='100px' width="410"></div>
<div name='callerip' vtype="textfield" label="调用方IP" labelAlign="right" labelwidth='100px' width="410"></div>
<div name='callertime' vtype="datefield" label="调用时间" labelAlign="right" labelwidth='100px' width="410"></div>
<div name='executetime' vtype="datefield" label="执行时间" labelAlign="right" labelwidth='100px' width="410"></div>
<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
	<div name="query_button" vtype="button" text="查询" 
		icon="../query/queryssuo.png" onclick="queryUrl();"></div>
		<div name="reset_button" vtype="button" text="重置"
			icon="../query/queryssuo.png" click="reset();" ></div>
	</div>
</div>
<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender="renderdata()"
	titledisplay="true" title="查询列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
	<div  vtype="gridcolumn" name="grid_column" width="100%">
		<div>
			<div name='fwrzjbid' key="true" visible="false" width="14%"></div>
			<div name='fwmc' text="服务名称" textalign="left"  width="20%"></div>
			<div name='callername' text="调用者名称" textalign="left"  width="20%"></div>
			<div name='calleer' text="提供者名称" textalign="left"  width="20%"></div>
			<div name='callertime' text="服务开始时间" textalign="left"  width="10%"></div>
			<div name='callerenttime' text="服务结束时间" textalign="left"  width="10%"></div>
			<div name='executetime' text="服务处理时间" textalign="left"  width="10%"></div>
			<div name='executeresult' text="执行结果" textalign="left"  width="5%"></div>
			<div name='custom' text="操作" textalign="center"  width="8%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" ></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
</body>
</html>