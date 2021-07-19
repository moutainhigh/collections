<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<style type="text/css">
.btn{
width:40px;
height:20px;
}
</style>
<script>
	function save() {
		parent.winEdit.window("close");
	}
	
	var update;
	$(function(){
		var priPid = jazz.util.getParameter("dmbId");
		update = jazz.util.getParameter("update");
		if(priPid != null){
			//$('#formpanel_edit .jazz-panel-content').loading();
			$("#formpanel_edit").formpanel('option', 'dataurl',rootPath+'/query/detail.do?priPid='+priPid);
			$("#formpanel_edit").formpanel('reload', "null", function(){$('#formpanel_edit .jazz-panel-content').loading('hide');});
		}
	});
	
	function back() {
		parent.winEdit.window("close");
	}
	
</script>
</head>
<body>
	<div id="formpanel_edit" name="formpanel_edit" vtype="formpanel"
		tableStyleClass="tablecss" titledisplay="false" showborder="false" width="100%"
		layout="table" layoutconfig="{cols:2, columnwidth: ['50%','*']}"
		height="100" dataurl="" readonly="true">
		<div name='pripid' vtype="hiddenfield" label="ID" labelAlign="right"
			rule="must" width="400" labelwidth="130"></div>
		<div name='regno' vtype="textfield" label="接口名称" labelAlign="right" 
			rule="must" width="400" labelwidth="130"></div>
		<div name='entname' vtype="textareafield" label="接口说明" colspan="2" height="20"
			labelAlign="right" width="800" labelwidth="130"></div>
	</div>
	
	<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender=""
		titledisplay="true" title="服务对象列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="85%">
			<div>
				<div name='pripid'></div>
				<div name='regno' text="服务对象名称" textalign="center"  width="33%"></div>
				<div name='entname' text="服务对象类型" textalign="center"  width="33%"></div>
				<div name='regorg' text="服务对象说明" textalign="center"  width="34%"></div>
			</div>
		</div>
		<!-- 表格 -->
		<!-- ../../grid/reg3.json -->
		<div vtype="gridtable" name="grid_table" ></div>
		<!-- 分页
		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div> -->
	</div>
					
	<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
		<div id="btn2" name="btn2" vtype="button" text="返回"
			icon="../../../style/default/images/fh.png" click="back()"></div>
	</div>
</body>
</html>