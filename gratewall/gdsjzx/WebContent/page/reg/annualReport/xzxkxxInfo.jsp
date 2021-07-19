<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>行政许可信息</title>
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
			selecttype="1"	titledisplay="true" title="行政许可信息"   showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="100%">
		<div> 
		<!-- 行政许可信息 -->
		<div name='approveid' text="主键ID" textalign="center" ></div>
		<div  name='reportid' text="个体户或农民专业合作社关联ID" textalign="center"  width="20%"></div>
		<div name='licname' text="许可文件名称" textalign="center" width="12%" ></div>
		<div name='licnO' text="许可文件文号" textalign="center" width="12%"></div>
		<div name='licanth' text="审批机关" textalign="center"  width="12%"></div>
		<div name='valfrom' text="审批日期" textalign="center" width="12%"></div>
		<div name='valto' text="有效截止日期" textalign="center"  width="12%"></div>
		<div name='licitem' text="许可内容" textalign="center"  width="20%"></div>
	</div>
	</div>
	<div vtype="gridtable" name="grid_table"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
</body>
</html>