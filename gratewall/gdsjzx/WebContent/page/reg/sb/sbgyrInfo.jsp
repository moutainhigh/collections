<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>商标共有人</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="98%" id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="商标共有人信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
			<div name='regno' text="注册号"  textalign="center"  width="15%"></div>
			<div name='coowercnname' text="共有人中文名称"  textalign="center"  width="22%"></div>
			<div name='coownercnaddr' text="共有人中文地址"  textalign="center"  width="20%"></div>
			<div name='coownerenname' text="共有人外文名称"  textalign="center"  width="22%"></div>
			<div name='coownerenaddr' text="共有人外文地址"  textalign="center"  width="20%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
</body>
</html>