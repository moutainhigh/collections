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

<script>
	$(function() {
	var fwdxjbid = jazz.util.getParameter("fwdxjbid");
		$('#zzjgGrid').gridpanel(
				'option',
				'dataurl',
				rootPath + '/dataservice/serviceObjectDetailsfw.do?fwdxjbid=' + fwdxjbid
						);
		$('#zzjgGrid').gridpanel('query', [ 'formpanel' ],function(){
    		$('div[name="grid_table"] tbody tr:eq(1) td:first input').attr("checked","checked");
    	});
		/* var a = {"data":{"page":1,"pagerows":30,"rows":[],"totalrows":92},"name":"gridpanel","vtype":"gridpanel"};
		$('div[name="zzjgGrid"]').gridpanel('reload',a,function(){
    		alert("111");
    	}); */
	});
	function renderdata(event, obj) {
		var data = obj.data;
		for ( var i = 0; i < data.length; i++) {
		var servicestate = data[i]["servicestate"];
		if (servicestate == "0") {
				data[i]["servicestate"] = "启用";
			} else {
				data[i]["servicestate"] = "停用";
			}
			var lineNo = data[i]["lineNo"];	
			if(lineNo!=null){
				$('#hiddenId').val(data[i]["lineNo"]);
			}
		}
		$('div[name="gridpanel"]').gridpanel('selectRow',1);
		/* $.each(data,function(i,row){
  			var index = $(row).attr("lineNo");
  			if(parseInt(index)%3==0){
		    	$(row).css({
		    		"background-color": "#ededed",
		    		"color": "#666"
		    	});
  			}
  		});
  		 */
  		
		return data; 
	/* 
		var data = obj.data;
		for ( var i = 0; i < data.length; i++) {
			var fwnrid = data[i]["fwdxjbid"];
			var state = data[i]["state"];
			var controlobjectstate = data[i]["controlobjectstate"];
			var serviceobjectname = data[i]["serviceobjectname"];

			if (state == "0") {
				data[i]["state"] = "启用";
			} else {
				data[i]["state"] = "停用";
			}

			if (controlobjectstate == "1") {
				data[i]["controlobjectstate"] = "不可控";
			} else {
				data[i]["controlobjectstate"] = "可控";
			}
			data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="update(\''
					+ fwnrid
					+ '\',\''
					+ state
					+ '\')">修改</a> <a href="javascript:void(0);" onclick="license(\''
					+ fwnrid
					+ '\')">授权</a> <a href="javascript:void(0);" onclick="run(\''
					+ fwnrid
					+ '\',\''
					+ state
					+ '\')">启用</a> <a href="javascript:void(0);" onclick="notrun(\''
					+ fwnrid + '\',\'' + state + '\')">停用</a></div>';
			data[i]["serviceobjectname"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="detailsfw(\''
					+ fwnrid + '\')">' + data[i]["serviceobjectname"] + '</a>';
		}
		return data; */
	}
	function dataload(){
		alert("....");
	}
	function back() {
		parent.winEdit.window("close");
	}
</script>
</head>
<body>
	<div vtype="gridpanel" name="zzjgGrid" width="100%"  
		id='zzjgGrid' datarender="renderdata()" titledisplay="false"
		dataloadcomplete="dataload()" title="对象提供服务信息"  layout="fit" showborder="false"
		isshowselecthelper="true" selecttype="2">
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='serviceid' width="0%"></div>
				<div name='createtime' text="服务创建时间" textalign="center"
					width="22%"></div>
				<div name='servicename' text="服务名称" textalign="center"
					width="40%"></div>
				<div name='createperson' text="创建人" textalign="center"
					width="20%"></div>
				<div name='servicestate' text="状态" textalign="center"
					width="18%"></div>
			</div>
		</div>
		<div vtype="gridtable" name="grid_table"></div>
		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
	</div>
	<input id="hiddenId" type="hidden"/>
</body>
</html>