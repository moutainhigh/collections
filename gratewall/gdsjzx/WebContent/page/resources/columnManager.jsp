<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>字段管理</title>
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
function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var a = data[i]["regno"];
		var pripid = data[i]["pripid"];
		data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="edit(\''+pripid+'\')">修改</a></div>';
		data[i]["regno"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="detail(\''+pripid+'\')">'+a+'</a></div>';
	}
	return data;
}
function edit(dmbId){
	winEdit = jazz.widget({
  	     vtype: 'window',
	   	     frameurl: './columnEdit.jsp?dmbId='+dmbId+'&update=true',
	  			name: 'win',
	  	    	title: '修改表字段',
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 550,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});
}
function detail(pripid){
	winEdit = jazz.widget({
 	     vtype: 'window',
	   	     frameurl: './columnDetail.jsp?priPid='+pripid+'&update=true',
	  			name: 'win',
	  	    	title: '查看字段详细',
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 550,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});
}
function back(){
	window.close();
}
</script>
</head>
<body>
<div>位置：资源管理>数据资源管理>表管理>表字段管理</div>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
		showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="300" title="查询条件">
		<div name='industryco' vtype="textfield" label="物理表" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='estdate' vtype="datefield" label="物理表中文名" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='servicestate' vtype="datefield" label="字段名" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='regorg' vtype="textfield" label="字段中文名"  labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='entname' vtype="comboxfield" label="系统代码集名称" dataurl="[{checked: true,value: '1',text: '登记'},{value: '2',text: '执法'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='industryphy' vtype="comboxfield" label="字段类型" dataurl="[{checked: true,value: '1',text: '业务'},{value: '2',text: '系统'},{value: '3',text: '历史'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div name="query_button" vtype="button" text="查询" 
				icon="../query/queryssuo.png" onclick="queryUrl();"></div>
			<div name="reset_button" vtype="button" text="重置"
				icon="../query/queryssuo.png" click="reset();" ></div>
		</div>
	</div>

	<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender="renderdata()"
		titledisplay="true" title="字段信息"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
		<div name="toolbar" vtype="toolbar">
			<div name="btn1" align="right" vtype="button" text="返回" click="back()"></div>
    	</div>
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='pripid'  width="0%"></div>
				<div name='regno' text="字段名" textalign="center"  width="14%"></div>
				<div name='entname' text="字段中文名" textalign="center"  width="20%"></div>
				<div name='estdate' text="字段类型" textalign="center"  width="10%"></div>
				<div name='industryphy' text="字段长度" textalign="center"  width="10%"></div>
				<div name='servicestate' text="标准标识符" textalign="center"  width="20%"></div>
				<div name='regcap' text="是否有效" textalign="center"  width="10%"></div>
				<div name='custom' text="操作" textalign="center"  width="15%"></div>
			</div>
		</div>
		<!-- 表格 -->
		<!-- ../../grid/reg3.json -->
		<div vtype="gridtable" name="grid_table" ></div>
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
	</div>
</body>
</html>