<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>党建信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/report/yreport.js" type="text/javascript" ></script>
	
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>
<script type="text/javascript">
	function handler(){
						
	}
</script>		

				 
</head>
<body >
<div vtype="gridpanel" name="jbxxGrid" height="98%" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()"
			selecttype="1"	titledisplay="true" title="党建信息"   showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="100%">
		<div> 
		<!-- 党建信息 -->
		<div name='partyid' text="主键ID" textalign="center"  width="0"></div>
		<div  name='numparm' text="党员（预备党员）人数" textalign="center"  width="25%"></div>
		<div name='numparmisshow' text="是否公示" textalign="center"></div>
		<div name='parins' text="党组织建制" textalign="center" width="25%"></div>
		<div name='parinsisshow' text="是否公示" textalign="center" ></div>
		<div name='resparmsign' text="经营者是否党员" textalign="center"  width="25%"></div>
		<div name='parinsisshow' text="是否公示" textalign="center" ></div>
		<div name='resparsecsign' text="经营者是否党组织书记" textalign="center" width="25%"></div>
		<div name='resparsecsignisshow' text="是否公示" textalign="center"></div>
	</div>
	</div>
	<!-- 表格 -->
	<div vtype="gridtable" name="grid_table"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
</body>
</html>