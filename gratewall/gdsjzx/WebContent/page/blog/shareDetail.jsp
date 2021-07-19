<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	var update;
	$(function(){
		var fwrzjbid = jazz.util.getParameter("fwrzjbid");
		update = jazz.util.getParameter("update");
		if(fwrzjbid != null){
			$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+'/blog/findShareLogDetailList.do?fwrzjbid='+fwrzjbid);
			$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
		}
	});
	function back() {
		parent.winEdit.window("close");
	}
	function renderdata(event, obj){
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			var executecontent = data[i]["executecontent"];
			data[i]["executecontent"] =  executecontent.replace('/>', '').replace('<', '');
			var code = data[i]["code"];
			if(code=='0'){
				data[i]["code"]="成功";
			}else{
				data[i]["code"]="失败";
			}
		}
		return data; 
	}
</script>
</head>
<body>

<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender="renderdata()"
	titledisplay="false" title="查询列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="100%">
		<div>
			<div name='fwrzxxid' key="true" visible="false" width="0%"></div>
			<div name='obj' text="日志对象名称" textalign="left"  width="20%"></div>
			<div name='detail' text="参数" textalign="left"  width="20%"></div>
			<div name='code' text="结果状态" textalign="left"  width="10%"></div>
			<div name='executecontent' text="结果信息" textalign="left"  width="20%"></div>
			<div name='starttime' text="开始时间" textalign="left"  width="10%"></div>
			<div name='endtime' text="结束时间" textalign="left"  width="10%"></div>
			<div name='time' text="时间差" textalign="left"  width="10%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" ></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
		<div id="btn2" name="btn2" vtype="button" text="关闭" icon="../../../style/default/images/fh.png" click="back()"></div>
</div>
</body>
</html>