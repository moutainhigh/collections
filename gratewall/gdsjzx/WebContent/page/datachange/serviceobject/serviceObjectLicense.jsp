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
var fwdxjbid;
$(function() {
	fwdxjbid = jazz.util.getParameter("fwdxjbid");
	$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath + '/dataservice/serviceAll.do');
		$('#zzjgGrid').gridpanel('query',[ 'formpanel' ],function() {
			$.ajax({
				url : rootPath + '/dataservice/serviceCheckedAll.do?fwdxjbid=' + fwdxjbid,
				async : false,
				type : "post",
				dataType : 'json',
				success : function(data) {
				for (var t = 0; t < data.length; t++) {
					for ( var h = 0; h < $('div[name="grid_table"] tbody tr').length; h++) {
						if ($('div[name="grid_table"] tbody tr:eq('+ h + ') td:eq(2)').text() == data[t].sid) {
							$('div[name="grid_table"] tbody tr:eq('+ h + ') td:first input').attr("checked","checked");
						}
					}
				}
			}
		});
	});
});
function renderdata(event, obj) {
	var data = obj.data;
	return data;
}
function save() {
	var fwjbid="";
	for ( var h = 0; h < $('div[name="grid_table"] tbody tr').length; h++) {
		if($('div[name="grid_table"] tbody tr:eq(' + h + ') td:first input').attr("checked")=="checked"){
			fwjbid=fwjbid+","+$('div[name="grid_table"] tbody tr:eq(' + h + ') td:eq(2)').text();		
		};
	}
	fwjbid=fwjbid.substring(1,fwjbid.length);
	$.ajax({
		url : rootPath + '/dataservice/saveLicenseTable.do?fwdxjbid=' + fwdxjbid,
		async : false,
		type : "post",
		data : {
			fwjbid : fwjbid
		},
		dataType : 'json',
		success : function(data) {
			jazz.info("保存成功！");
			back();
		}
	});
}
function back() {
	parent.winEdit.window("close");
}

	//saveLicenseTable
</script>
</head>
<body>
	<div vtype="gridpanel" name="zzjgGrid" height="470" width="100%"
		id='zzjgGrid' datarender="renderdata()" titledisplay="false"
		dataloadcomplete="dataload()" title="对象提供服务信息" showborder="false"
		isshowselecthelper="true" selecttype="2" isshowpaginator=false>
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='serviceid' width="0%"></div>
				<div name='serviceobjectname' text="服务来源" textalign="left" width="40%"></div>
				<div name='servicename' text="服务名称" textalign="left" width="50%"></div>
				<div name='description' text="服务内容" textalign="left" width="10%"></div>
			</div>
		</div>

		<div vtype="gridtable" name="grid_table"></div>
		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom"
			align="center">
			<div id="btn1" name="btn1" vtype="button" text="保存"
				icon="../../../style/default/images/save.png" click="save()"></div>
			<div id="btn2" name="btn2" vtype="button" text="返回"
				icon="../../../style/default/images/fh.png" click="back()"></div>
		</div>
	</div>
	<input id="hiddenId" type="hidden" />
</body>
</html>