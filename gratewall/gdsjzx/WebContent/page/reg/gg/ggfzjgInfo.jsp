<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>变更信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="98%" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="广告分支机构信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
			<div name='regno' text="注册号"  textalign="center"  width="20%"></div>
			<div name='brname' text="分支机构名称"  textalign="center"  width="20%"></div>
			<div name='regorg' text="登记机关"  textalign="center"  width="20%"></div>
			<div name='estdate' text="成立日期"  textalign="center"  width="19%"></div>
			<div name='prilname' text="负责人姓名"  textalign="center"  width="20%"></div>
				<div name='sourceflag' text="变" visible="false" textalign="center"  width="10%"></div>
				<div name='timestamp' text="变" visible="false" textalign="center"  width="10%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
</body>
</html>