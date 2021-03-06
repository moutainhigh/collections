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
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/jbxx/shareDetial.css" />
<style>
tr{
	height:0px;
}
td{
	height:0px;
}
</style>
<script>
	

	function save() {
		parent.winEdit.window("close");
	}
	
	
	var update;
	$(function(){
		$("#formpanel").formpanel('option', 'readonly', true);
		var rsid = jazz.util.getParameter("rsid");
		update = jazz.util.getParameter("update");
		if(rsid != null){
			//$('div[name="formpanel"]').formpanel('option', 'dataurl',rootPath+'/dictionaryList/editinit.do?dmbId='+dmbId);
			//$('div[name="formpanel"]').formpanel('reload');
			$('#formpanel .jazz-panel-content').loading();
			$("#formpanel").formpanel('option', 'dataurl',rootPath+'/shareResource/queryfrom.do?rsid='+rsid);
			$("#formpanel").formpanel('reload', "null", function(){$('#formpanel .jazz-panel-content').loading('hide');});
			queryUrl(rsid);
		}
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
	function queryUrl(rsid) {
		$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+
				'/shareResource/queryde.do?rsid='+rsid);
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
<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" 
	titledisplay="false" width="98%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*'], border: true}" height="auto">

		<div name='gxzyid' vtype="hiddenfield" label="ID" labelAlign="right" labelwidth='120px'  width="380"  height='40px'></div>
		<div name='tablecode' vtype="textfield" label="?????????" labelAlign="right" labelwidth='120px'  width="380"  height='40px'></div>
		<div name='subject' vtype="textfield" label="????????????" labelAlign="right" labelwidth='120px'  width="380"  height='40px'></div>
		
		<div name='tabletype' vtype="hiddenfield" label="?????????" labelAlign="right" labelwidth='120px'  width="380"  height='40px'></div>
		<div name='tablename' vtype="textfield" label="?????????" labelAlign="right" labelwidth='120px'  width="380"  height='40px'></div>
		
		<div name='createperson' vtype="textfield" label="?????????" labelAlign="right" labelwidth='120px'  width="380"  height='40px'></div>
		<div name='createtime' vtype="textfield" label="????????????" labelAlign="right" labelwidth='120px'  width="380"  height='40px'></div>
		<div name='lastmodifyperson' vtype="textfield" label="???????????????" labelAlign="right" labelwidth='120px'  width="380"  height='40px'></div>
		<div name='lastuupdatetime' vtype="textfield" label="??????????????????" labelAlign="right" labelwidth='120px'  width="380"  height='40px'></div>
		
		
		<div name='dataquantity' vtype="textfield" label="?????????" labelAlign="right" labelwidth='120px'  width="380"  height='40px'></div>
		<div name='describe' vtype="textfield" label="??????" labelAlign="right" colspan="2" labelwidth='120px'  width="750"  height='40px'></div>
		
		<!-- <div name='description' vtype="hiddenfield" label="??????" labelAlign="right" labelwidth='120px'  colspan="2"  width="380"  height='40px'></div>
		 -->
		<!-- <div name="tip" vtype="textfield" label="??????" labelAlign="right"
			width="850" labelwidth="130" defaultvalue="SQL?????????????????????????????????value???????????????????????????text??????
			?????????select t.wb as text,dm as value from T_PT_DMSJB t" readonly="true"></div> -->

		
	</div>
	
	<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender="renderdata()"
		titledisplay="true" title="??????????????????????????????"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="0">
		<!-- ?????? -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='gxzylid' visible="false" width="0%"></div>
				<div name='tablepkid' visible="false" width="0%"></div>
				<div name='columncode' text="?????????" textalign="center"  width="30%" height="10px"></div>
				<div name='columnname' text="???????????????" textalign="center"  width="30%" height="10px"></div>
				<div name='fieldtype' text="????????????" textalign="center"  width="15%" height="10px"></div>
				<div name='fieldlength' text="????????????(??????)" textalign="center"  width="15%" height="10px"></div>
				<!-- <div name='isprimarykey' text="????????????" textalign="center"  width="12%" height="10px"></div>
				<div name='isnotnull' text="????????????" textalign="center"  width="12%" height="10px"></div> -->
				<!-- <div name='description' text="??????" textalign="center"  width="38%" height="10px"></div> -->
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