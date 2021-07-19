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
			data[i]["regno"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="inspectionResults()">'+a+'</a></div>';
			data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="generate(\''+pripid+'\')">生成报告</a></div>';
		}
		return data;
	}
	
	function generate(){
	window.location.href="<%=request.getContextPath()%>/page/service/img_scbg.jsp";
	}
	function inspectionResults(){
	window.location.href="<%=request.getContextPath()%>/page/service/inspectionResults.jsp";
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
		window.location.href="<%=request.getContextPath()%>/page/service/checkExecutionProcess.jsp";
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
		<div name='regno' vtype="textfield" label="活动名称" labelAlign="right"
			rule="must" width="400" labelwidth="130"></div>
		<div name='entname' vtype="textfield" label="创建人"
			labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		
		<div name='n1' vtype="textfield" label="创建日期" labelAlign="right"
			width="400" labelwidth="130" ></div>
		<div name='n' vtype="textfield" label="规则包名称" labelAlign="right"
			width="400" labelwidth="130" ></div>
			
		<div name='n2' vtype="textfield" label="数据期限开始" labelAlign="right"
			width="400" labelwidth="130" ></div>
		<div name='n3' vtype="textfield" label="数据期限结束" labelAlign="right"
			width="400" labelwidth="130" ></div>
		
		<div name='n4' vtype="textfield" label="检查结束日期" labelAlign="right"
			width="400" labelwidth="130" ></div>
			
		<div name='regorg1' vtype="comboxfield" label="活动状态"
			labelAlign="right" width="400" labelwidth="130" ></div>
			
		<div name='regorg' vtype="textareafield" label="备注" colspan="2" height="60"
			labelAlign="right" width="850" labelwidth="130" ></div>
		<!-- <div name="tip" vtype="textfield" label="提示" labelAlign="right"
			width="850" labelwidth="130" defaultvalue="SQL语句中代码值的标识为“value”，文本的标识为“text”。
			示例：select t.wb as text,dm as value from T_PT_DMSJB t" readonly="true"></div> -->

		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div id="btn2" name="btn2" vtype="button" text="启动"
				icon="../../../style/default/images/fh.png" click="start()"></div>
			<div id="btn3" name="btn3" vtype="button" text="返回"
				icon="../../../style/default/images/fh.png" click="back()"></div>				
		</div>
	</div>
	
	<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender="renderdata()"
		titledisplay="true" title="查询列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
		<div name="toolbar" vtype="toolbar">
			<div name="btn1" align="left" vtype="button" text="对比执行记录" click="add()"></div>
    	</div>
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='pripid' ></div>
				<div name='regno' text="执行开始时间" textalign="center"  width="10%"></div>
				<div name='entname' text="执行结束时间" textalign="center"  width="10%"></div>
				<div name='regorg' text="是否保存" textalign="center"  width="10%"></div>
				<div name='entname' text="执行人" textalign="center"  width="10%"></div>
				<div name='regorg' text="问题点数量" textalign="center"  width="10%"></div>
				<div name='n' text="检查点数量" textalign="center"  width="15%"></div>
				<div name='b' text="检查记录条数" textalign="center"  width="15%"></div>
				<div name='custom' text="查看报告" textalign="center"  width="20%"></div>
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