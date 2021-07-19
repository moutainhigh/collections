<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>检验项目结果信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>

<script src="<%=request.getContextPath()%>/static/js/sczt/scztTable.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>

<script type="text/javascript">
function handler(){
}
</script>				 
</head>
<body >
<div vtype="gridpanel" name="jbxxGrid" height="98%" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="检验项目结果信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='resultid' text="检测结果标识" visible="false" textalign="center"  width="10%"></div>
				<div name='projectid' text="方案标识" visible="false" textalign="center"  width="10%"></div>
				<div name='checkitemuuid' text="监测项目标识" visible="false" textalign="center"  width="10%"></div>
				<div name='checkreportid' text="初检报告Id" visible="false" textalign="center"  width="10%"></div>
			<div name='checkitemname' text="检验项名称" textalign="center"  width="15%"></div>
			<div name='standard' text="标准要求" textalign="center"  width="23%"></div>
			<div name='meaunit' text="计量单位" extalign="center"  width="15%"></div>
			<div name='result' text="检验结果" textalign="center"  width="23%"></div>
			<div name='conclusion' text="单项结论" textalign="center"  width="23%"></div>
				<div name='sourceflag' text="SOURCEFLAG" visible="false" textalign="center"  width="10%"></div>
				<div name='timestamp' text="TIMESTAMP" visible="false" textalign="center"  width="10%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
</body>
</html>