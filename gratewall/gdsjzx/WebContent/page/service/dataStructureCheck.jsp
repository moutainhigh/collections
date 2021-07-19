<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>数据结构一致性检查结果</title>
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
$(document).ready(function(){
	queryUrl();
});

function queryUrl() {
	$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+
			'/query/list.do');
	$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
}
function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var pripid = data[i]["pripid"];
		var a = data[i]["regno"];
		data[i]["regno"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="detail(\''+pripid+'\')">'+a+'</a></div>';
		data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="update(\''+pripid+'\')">修改</a>  <a href="javascript:void(0);" onclick="del(\''+pripid+'\')">删除</a>  <a href="javascript:void(0);" onclick="run(\''+pripid+'\')">执行</a></div>';
	}
	return data;
}
function detail(dmbId){
	winEdit = jazz.widget({
	     vtype: 'window',
	   	     frameurl: './dataStructureCheckDetail.jsp?dmbId='+dmbId+'&update=true',
	  			name: 'win',
	  	    	title: '执行',
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 550,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});
}
function del(dmbId){
	jazz.confirm("是否删除？", function(){
		jazz.info("删除成功");
		$.DataAdapter.submit(params);
	}, function(){});
}
function run(dmbId){
	winEdit = jazz.widget({
 	     vtype: 'window',
	   	     frameurl: './dataStructureExecutionProcess.jsp?dmbId='+dmbId+'&update=true',
	  			name: 'win',
	  	    	title: '检查数据结构一致性检查过程',
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 550,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});
}
function update(dmbId){
	winEdit = jazz.widget({
  	     vtype: 'window',
	   	     frameurl: './dataStructureCheckEdit.jsp?dmbId='+dmbId+'&update=true',
	  			name: 'win',
	  	    	title: '修改常规检查',
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 550,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});
}

function add(){
	winEdit = jazz.widget({
	     vtype: 'window',
	     frameurl: './dataStructureCheckEdit.jsp',
	  			name: 'win',
	  	    	title: '新增常规检查',
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 550,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		}); 
}
</script>
</head>
<body>
<div>位置：数据服务>数据治理>数据结构一致性检查</div>
	<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender="renderdata()"
		titledisplay="true" title="查询数据结构检查活动列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
		<div name="toolbar" vtype="toolbar">
			<div name="btn1" align="right" vtype="button" text="新增数据结构一致活动" click="add()"></div>
    	</div>
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='pripid' width="0%"></div>
				<div name='regno' text="活动名称" textalign="center"  width="14%"></div>
				<div name='regorg' text="活动状态" textalign="center"  width="14%"></div>
				<div name='regorg' text="创建日期" textalign="center"  width="14%"></div>
				<div name='entname' text="基准数据源" textalign="center"  width="15%"></div>
				<div name='entname' text="比对数据源" textalign="center"  width="15%"></div>
				<div name='custom1' text="检查表集合" textalign="center"  width="15%"></div>
				<div name='custom' text="操作" textalign="center"  width="10%"></div>
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