<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>消息列表页</title>
<%
	String contextpath = request.getContextPath();
%>
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
</head>
<body>
   <div vtype="gridpanel" name="gridpanel" id="gridpanel" height="100%" width="100%" layout="fit"  dataurl="<%=contextpath%>/home/getMessage.do?type=1"
	 	titledisplay="false"  isshowselecthelper="true" selecttype="0">
		<div vtype="gridcolumn" name="grid_column" id="grid_column">
			<div> <!-- 单行表头 -->
				<div name="pkNotice" key="true" visible="false"></div>
				<div name="title"  text="标题" textalign="center"  width="20%"></div>
				<div name="content" text="内容" textalign="center" ></div>
				<div name="createrId" text="创建人" textalign="center" sort="true" width="13%"></div>
				<div name="createrTime" text="创建时间" textalign="center" sort="true" datatype="date" dataformat="YYYY-MM-DD" width="13%"></div>
				<div name="effectiveTime" text="有效时间" textalign="center" sort="true" datatype="date" dataformat="YYYY-MM-DD" width="13%"></div>
			</div>
		</div>
		<!-- 表格 -->
		<div vtype="gridtable" name="grid_table" id="grid_table"></div>
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" id="grid_paginator"></div>
	</div>	
</body>
</html>