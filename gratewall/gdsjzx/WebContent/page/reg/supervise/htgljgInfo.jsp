<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>合同管理机构信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="98%" id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="合同管理机构信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='curcommanageorgid' text="合同管理机构id" visible="false" textalign="center"  width="10%"></div>
				<div name='curcompactcreditid' text="守合同重信息用正式表id2" visible="false" textalign="center"  width="10%"></div>
				<div name='sourceflag' text="SOURCEFLAG" visible="false" textalign="center"  width="10%"></div>
			<div name='orgname' text="机构名称"  textalign="center"  width="20%"></div>
			<div name='principal' text="负责人"  textalign="center"  width="20%"></div>
			<div name='phone' text="电话号码" textalign="center"  width="15%"></div>
			<div name='mobilephone' text="手机" textalign="center"  width="15%"></div>
			<div name='managercount' text="管理人员合计" textalign="center"  width="10%"></div>
			<div name='fulltimecount' text="专职人数"  textalign="center"  width="10%"></div>
			<div name='sidelinecount' text="兼职人数" textalign="center"  width="10%"></div>
				<div name='timestamp' text="TIMESTAMP" visible="false" textalign="center"  width="10%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
</body>
</html>