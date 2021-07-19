<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>数据结构管理</title>
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
		var pripid = data[i]["pripid"];
		data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="loadAll(\''
				+pripid+'\')">全部装载</a>  <a href="javascript:void(0);" onclick="loadTable(\''
						+pripid+'\')">装载表</a>  <a href="javascript:void(0);" onclick="loadView(\''
								+pripid+'\')">装载视图</a>  <a href="javascript:void(0);" onclick="loadProc(\''
								+pripid+'\')">装载存储过程</a>  <a href="javascript:void(0);" onclick="loadFunc(\''
								+pripid+'\')">装载函数</a></div>';
	}
	return data;
}
function loadAll(dmbId){
	jazz.info("装载成功");  
}
function loadView(dmbId){
	jazz.info("装载成功");  
}
function loadProc(dmbId){
	jazz.info("装载成功");  
}
function loadFunc(dmbId){
	jazz.info("装载成功");  
}
function loadTable(dmbId){
	winEdit = jazz.widget({
  	     vtype: 'window',
	   	     frameurl: './loadTable.jsp?dmbId='+dmbId+'&update=true',
	  			name: 'win',
	  	    	title: '加载表结构',
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 600,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});
}
</script>
</head>
<body>
<div>位置：资源管理>数据结构装载</div>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
		showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="200" title="查询条件">

		<div name='regno' vtype="comboxfield" dataurl='[{"text":"数据中心","value":"1"},{"text":"知识产权","value":"2"}]' label="业务系统" labelAlign="right" labelwidth='100px' width="410"></div>
		<div name='entname' vtype="comboxfield"  label="数据源IP" labelAlign="right" labelwidth='100px' width="410"></div>
		
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div name="query_button" vtype="button" text="查询" 
				icon="../query/queryssuo.png" onclick="queryUrl();"></div>
			<div name="reset_button" vtype="button" text="重置"
				icon="../query/queryssuo.png" click="reset();" ></div>
		</div>
	</div>

	<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender="renderdata()"
		titledisplay="true" title="查询列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='pripid'  width="0%"></div>
				<div name='regno' text="数据源名称" textalign="center"  width="10%"></div>
				<div name='regorg' text="数据源类型" textalign="center"  width="10%"></div>
				<div name='enttype' text="所属对象" textalign="center"  width="10%"></div>
				<div name='servicestate' text="业务系统" textalign="center"  width="10%"></div>
				<div name='regcap' text="数据源IP" textalign="center"  width="10%"></div>
				<div name='estdate' text="最后装载人" textalign="center"  width="10%"></div>
				<div name='regcap1' text="最后装载时间" textalign="center"  width="10%"></div>
				<div name='custom' text="操作" textalign="center"  width="29%"></div>
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