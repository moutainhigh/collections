<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>证照信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/sczt/sczt.js" type="text/javascript"></script>

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>

<script type="text/javascript">
	function handler(){
	}
	function renderdata(event, obj){
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			var valto = data[i]["valto"];
			if(valto==undefined){
				data[i]["valto"] = "无限期";
			}
		}
	}
</script>				 
</head>
<body>
<div vtype="gridpanel" name="jbxxGrid" height="98%" layout="fit" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()"
		selecttype="1"titledisplay="true" title="证照信息"   showborder="false" isshowselecthelper="false"  selecttype="2">
	<!-- 表头 -->
	<div vtype="gridcolumn" name="grid_column" width="100%">
		<div> 
		<!-- 证照信息 -->
			<div name='blicno' text="证照编号" textalign="center"  width="17%"></div>
			<div name='blictype' text="证照类型" textalign="center"  width="17%"></div>
			<div name='blicstate' text="证照状态" textalign="center"  width="16%"></div>
			<div name='oricopsign' text="正副本标志" textalign="center"  width="16%"></div>
			<div name='valfrom' text="有效期自" textalign="center"  width="17%"></div>
			<div name='valto' text="有效期至" textalign="center"  width="17%"></div>
		</div>
	</div>
	<!-- 表格 -->
	<div vtype="gridtable" name="grid_table"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
</body>
</html>