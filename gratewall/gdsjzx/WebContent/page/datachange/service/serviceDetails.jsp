<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script
	src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=request.getContextPath()%>/static/css/jbxx/shareDetial.css" />
<style>
tr {
	height: 0px;
}

td {
	height: 0px;
}
.
{
}
</style>
<script>
	var serviceid;
	$(function() {
		serviceid = jazz.util.getParameter("serviceid");
		

		$("#formpanel").formpanel('option', 'readonly', true);
		$('#formpanel .jazz-panel-content').loading();
		$("#formpanel").formpanel('option', 'dataurl',
				rootPath + '/dataservice/serviceDetail.do?serviceid=' + serviceid);
		$("#formpanel").formpanel('reload', "null", function() {
			$('#formpanel .jazz-panel-content').loading('hide');
			$('div .jazz-field-comp-in2').removeClass("jazz-field-comp-in2");
		});
		
		
		$('#zzjgGrid').gridpanel('option', 'dataurl',
				rootPath + '/dataservice/selectListenerInfo.do?serviceId='+serviceid);
		$('#zzjgGrid').gridpanel('query', [ 'formpanel' ]);
	});
	function renderdata(event, obj) {
		var data = obj.data;
		return data;
	}
	function back() {
		parent.winEdit.window("close");
	}

	//saveLicenseTable
</script>
</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="0" name="formpanel"
		titledisplay="false" width="98%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*'], border: true}"
		height="auto">

		<div name='serviceid' vtype="hiddenfield" label="ID"
			labelAlign="right" labelwidth='120px' width="380" height='40px'></div>
		<div name='servicename' vtype="textfield" label="?????????"
			labelAlign="right" labelwidth='120px' width="380" height='40px'></div>


		<div name='servicestate' vtype="comboxfield"  dataurl="[{value: '0',text: '??????'},{value: '1',text: '??????'}]" 
		label="????????????" labelAlign="right" labelwidth='120px' width="380" height='40px'></div>
		<div name='createtime' vtype="textfield" label="????????????"
			labelAlign="right" labelwidth='120px' width="380" height='40px'></div>

		<div name='createperson' vtype="textfield" label="?????????"
			labelAlign="right" labelwidth='120px' width="380" height='40px'></div>
		<div name='lastmodifytime' vtype="textfield" label="??????????????????"
			labelAlign="right" labelwidth='120px' width="380" height='40px'></div>
		<div name='lastmodifyperson' vtype="textfield" label="???????????????"
			labelAlign="right" labelwidth='120px' width="380" height='40px'></div>

		<div name='executetype' vtype="comboxfield" label="????????????"  dataurl="[{checked: true,value: '0',text: '??????'},{value: '1',text: '?????????'}]"
			labelAlign="right" labelwidth='120px' width="380" height='40px'></div>
		<div name='serviceurl' vtype="textareafield" label="??????url" colspan="2"
			labelAlign="right" labelwidth='120px' width="850" height='30'></div>
		<div name='serviceconentshow' vtype="textareafield" colspan="2"
			label="????????????" labelAlign="right" labelwidth='120px' width="850"
			height='30'></div>
		<div name='description' vtype="textareafield" colspan="2" label="??????"
			labelAlign="right" labelwidth='120px' width="850" height='30'></div>

	</div>


	<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"
		id='zzjgGrid' datarender="renderdata()" titledisplay="false"
		 title="????????????????????????" showborder="false"
		isshowselecthelper="true" selecttype="2">
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='serviceobjectname' text="???????????????" textalign="center" width="15%"></div>
				<div name='subscriptionobject' text="?????????" textalign="center" width="15%"></div>
				<div name='startsubscribetime' text="????????????" textalign="center" width="15%"></div>
				<div name='frequency' text="??????" textalign="center" width="15%"></div>
				<div name='acceptway' text="????????????" textalign="center" width="20%"></div>
				<div name='path' text="??????" textalign="center" width="20%"></div>
			</div>
		</div>

		<div vtype="gridtable" name="grid_table"></div>
		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
	</div>
	<input id="hiddenId" type="hidden" />
</body>
</html>