<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>代码集管理</title>
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
		data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="manager(\''+pripid+'\')">管理</a></div>';
		data[i]["regno"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="query()">'+a+'</a></div>';
	}
	return data;
}
function manager(dmbId){
	winEdit = jazz.widget({
  	     vtype: 'window',
	   	     frameurl: './daiMaJiEdit.jsp?dmbId='+dmbId+'&update=true',
	  			name: 'win',
	  	    	title: '修改代码集',
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 400,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});
}
function query(){
	//window.location.href="<%=request.getContextPath()%>/page/resources/daiMaManager.jsp";
	window.open("<%=request.getContextPath()%>/page/resources/daiMaManager.jsp");     
}
</script>
</head>
<body>
<div>位置：资源管理>数据资源管理>代码集管理</div>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
		showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="300" title="查询条件">

		<div name='regno' vtype="textfield" label="代码" labelAlign="right" labelwidth='100px' width="410"></div>
		<div name='entname' vtype="textfield" label="代码名称"  labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='industryphy' vtype="comboxfield" label="引用标准" dataurl="[{checked: true,value: '1',text: '业务'},{value: '2',text: '系统'},{value: '3',text: '历史'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='industryco' vtype="textfield" label="分项名称" labelAlign="right"  labelwidth='100px' width="410"></div>
		
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div name="query_button" vtype="button" text="查询" 
				icon="../query/queryssuo.png" onclick="queryUrl();"></div>
			<div name="reset_button" vtype="button" text="重置"
				icon="../query/queryssuo.png" click="reset();" ></div>
		</div>
	</div>

	<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender="renderdata()"
		titledisplay="true" title="表信息"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='pripid'  width="1%"></div>
				<div name='enttype' text="业务系统" textalign="center"  width="15%"></div>
				<div name='regno' text="代码" textalign="center"  width="15%"></div>
				<div name='regorg' text="代码名称" textalign="center"  width="18%"></div>
				<div name='servicestate' text="引用标准" textalign="center"  width="15%"></div>
				<div name='estdate' text="代码集标准标识符" textalign="center"  width="15%"></div>
				<div name='custom' text="操作" textalign="center"  width="18%"></div>
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