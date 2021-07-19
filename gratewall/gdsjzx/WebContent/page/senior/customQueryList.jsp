<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>自定义查询</title>
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
function add() {
	window.location.href="<%=request.getContextPath()%>/page/senior/customQueryAdd.jsp";
}
function run() {
	$("#formpanel").formpanel('reset');
}
function detail() {
	$("#formpanel").formpanel('reset');
}
function update() {
	$("#formpanel").formpanel('reset');
}
function del() {
	jazz.confirm("该操作将删除该条数据，是否删除？", function(){
		
		jazz.info("删除成功");
		/* var params = {
				url : rootPath+'/dictionaryList/delete.do',
				components: ['gridpanel'],
				callback : function(data, r, res) { 
					if (res.getAttr("back") == 'success'){
						queryDef();
						jazz.info("删除成功");
					}
				}
		}
		$.DataAdapter.submit(params); */
	}, function(){});
}
function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var a = data[i]["name"];
		data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="run()">执行</a>  <a href="javascript:void(0);" onclick="detail()">详细</a>  <a href="javascript:void(0);" onclick="update()">修改</a>  <a href="javascript:void(0);" onclick="del()">删除</a></div>';
	}
	return data;
}
</script>
</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
		showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="200" title="查询条件">
		<div name='regno' vtype="textfield" label="查询名称" labelAlign="right" labelwidth='100px' width="410"></div>
		<div name='entname' vtype="textfield" label="用途" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='industryphy' vtype="textfield" label="创建人" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='servicestate' vtype="datefield" label="创建日期" labelAlign="right"  labelwidth='100px' width="410"></div>
		
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div name="query_button" vtype="button" text="查询" 
				icon="../query/queryssuo.png" onclick="queryUrl();"></div>
			<div name="reset_button" vtype="button" text="重置"
				icon="../query/queryssuo.png" click="reset();" ></div>
		</div>
	</div>

	<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender="renderdata()"
		titledisplay="true" title="查询列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
		<div name="toolbar" vtype="toolbar">
			<div name="btn1" align="right" vtype="button" text="新增" click="add()"></div>
    	</div>
		
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='entname' text="查询名称" textalign="center"  width="30%"></div>
				<div name='regno' text="用途" textalign="center"  width="20%"></div>
				<div name='industryco' text="创建人" textalign="center"  width="15%"></div>
				<div name='estdate' text="创建日期" textalign="center"  width="15%"></div>
				<div name="custom" text="自定义" textalign="center" width="18%"></div>
			</div>
		</div>
		<!-- 表格 -->
		<!-- ../../grid/reg3.json -->
		<div vtype="gridtable" name="grid_table"></div>
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
	</div>
</body>
</html>