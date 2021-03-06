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
<script>
	

	function save() {
		parent.winEdit.window("close");
	}
	
	
	var update;
	$(function(){
		var priPid = jazz.util.getParameter("dmbId");
		update = jazz.util.getParameter("update");
		if(priPid != null){
			//$('div[name="formpanel_edit"]').formpanel('option', 'dataurl',rootPath+'/dictionaryList/editinit.do?dmbId='+dmbId);
			//$('div[name="formpanel_edit"]').formpanel('reload');
			$('#formpanel_edit .jazz-panel-content').loading();
			$("#formpanel_edit").formpanel('option', 'dataurl',rootPath+'/query/detail.do?priPid='+priPid);
			$("#formpanel_edit").formpanel('reload', "null", function(){$('#formpanel_edit .jazz-panel-content').loading('hide');});
		}
		queryUrl();
	});	
	
	function back() {
		parent.winEdit.window("close");
	}
	function renderdata(event, obj){
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			var pripid = data[i]["pripid"];
			var a = data[i]["regno"];
			data[i]["regno"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="results()">'+a+'</a></div>';
		}
		return data;
	}
	
	function generate(){
	window.location.href="<%=request.getContextPath()%>/page/service/img_scbg.jsp";
	}
	function results(){
	window.location.href="<%=request.getContextPath()%>/page/service/dataStructureCheckResults.jsp";
	}
	
	function queryUrl() {
		$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+
				'/query/list.do');
		$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
	}
	function add(){
		window.location.href="<%=request.getContextPath()%>/page/service/cgjc_zxjgdb.jsp";
	}
	function start(pripid){
		window.location.href="<%=request.getContextPath()%>/page/service/dataStructureExecutionProcess2.jsp";
	}
	
</script>
</head>
<body>
	<div id="formpanel_edit" name="formpanel_edit" vtype="formpanel"
		tableStyleClass="tablecss" titledisplay="false" showborder="false" width="100%"
		layout="table" layoutconfig="{cols:2, columnwidth: ['50%','*']}"
		height="200" dataurl="" readonly="true">

		<div name='pripid' vtype="hiddenfield" label="ID" labelAlign="right"
			rule="must" width="400" labelwidth="130"></div>
		<div name='regno' vtype="textfield" label="????????????" labelAlign="right"
			rule="must" width="400" labelwidth="130"></div>
		<div name='entname' vtype="textfield" label="?????????"
			labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		
		<div name='n1' vtype="textfield" label="????????????" labelAlign="right"
			width="400" labelwidth="130" ></div>
		<div name='n' vtype="textfield" label="????????????" labelAlign="right"
			width="400" labelwidth="130" ></div>
			
		<div name='n2' vtype="textfield" label="???????????????" labelAlign="right"
			width="400" labelwidth="130" ></div>
		<div name='n3' vtype="textfield" label="???????????????" labelAlign="right"
			width="400" labelwidth="130" ></div>
		
		<div name='n4' vtype="textfield" label="?????????" labelAlign="right"
			width="400" labelwidth="130" ></div>
			
		<!-- <div name="tip" vtype="textfield" label="??????" labelAlign="right"
			width="850" labelwidth="130" defaultvalue="SQL?????????????????????????????????value???????????????????????????text??????
			?????????select t.wb as text,dm as value from T_PT_DMSJB t" readonly="true"></div> -->

		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div id="btn2" name="btn2" vtype="button" text="??????"
				icon="../../../style/default/images/fh.png" click="start()"></div>
			<div id="btn3" name="btn3" vtype="button" text="??????"
				icon="../../../style/default/images/fh.png" click="back()"></div>				
		</div>
	</div>
	
	<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender="renderdata()"
		titledisplay="true" title="??????????????????????????????"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
		<!-- ?????? -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='pripid' ></div>
				<div name='regno' text="??????????????????" textalign="center"  width="12%"></div>
				<div name='entname' text="??????????????????" textalign="center"  width="12%"></div>
				<div name='regorg' text="????????????" textalign="center"  width="12%"></div>
				<div name='entname1' text="?????????" textalign="center"  width="12%"></div>
				<div name='regorg' text="???????????????" textalign="center"  width="12%"></div>
				<div name='n' text="??????????????????" textalign="center"  width="12%"></div>
				<div name='b' text="???????????????" textalign="center"  width="14%"></div>
				<div name='custom1' text="???????????????" textalign="center"  width="14%"></div>
			</div>
		</div>
		<!-- ?????? -->
		<!-- ../../grid/reg3.json -->
		<div vtype="gridtable" name="grid_table" ></div>
		<!-- ?????? -->
		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
	</div>
</body>
</html>