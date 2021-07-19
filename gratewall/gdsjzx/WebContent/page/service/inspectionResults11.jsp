<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>执行之后进入检查结果</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script>
	

	function save() {
		parent.winEdit.window("close");
	}
	
	
	var update;
	$(function(){
		var priPid = jazz.util.getParameter("dmbId");
		update = jazz.util.getParameter("update");
		if(priPid != null){
			$('#formpanel_edit .jazz-panel-content').loading();
			$("#formpanel_edit").formpanel('option', 'dataurl',rootPath+'/query/detail.do?priPid='+priPid);
			$("#formpanel_edit").formpanel('reload', "null", function(){$('#formpanel_edit .jazz-panel-content').loading('hide');});
		}
	});
	
	function back() {
		parent.winEdit.window("close");
	}
	function yes(){
		alert("ooo");
	}
</script>
</head>
<body>
	<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender="renderdata()"
		titledisplay="true" title="查询列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
		
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='pripid' width="1%" ></div>
				<div name='regno' text="检查点信息" textalign="center"  width="20%"></div>
				<div name='entname' text="执行状态" textalign="center"  width="20%"></div>
				<div name='regorg' text="执行时间" textalign="center"  width="20%"></div>
				<div name='entname' text="问题点" textalign="center"  width="20%"></div>
				<div name='regorg' text="检查点" textalign="center"  width="20%"></div>
			</div>
		</div>
		<div name="toolbar" vtype="toolbar">
			<div name="btn1" align="right" vtype="button" text="确定" click="yes()"></div>
    	</div>
		<!-- 表格 -->
		<!-- ../../grid/reg3.json -->
		<div vtype="gridtable" name="grid_table" ></div>
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
	</div>
</body>
</html>