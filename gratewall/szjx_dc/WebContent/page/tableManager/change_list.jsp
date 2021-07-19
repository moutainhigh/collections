<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据中心-表管理-列表</title>
<%
	String contextpath = request.getContextPath();
%>
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=contextpath%>/static/script/home/change_list.js" type="text/javascript"></script>

</head>
<body>
	<div class="title_nav">当前位置：资源管理  > 数据资源管理  ><span> 表变更管理</span></div>
   	<div name="formpanel" id="formpanel" vtype="formpanel" titledisplay="false" width="100%" layout="table" showborder="false" layoutconfig="{cols:2, columnwidth: ['33%','33%','*']}" >
		<div name='tableNameEn' id='tableNameEn' vtype="textfield" label="物理表" labelalign="left" labelwidth="55" width="95%" editable="true"></div>
		<div name='columnNameEn' id='columnNameEn' vtype="textfield" label="字段名" labelalign="left" labelwidth="55" width="95%" editable="true"></div>
	    <div name="changeItem" id='changeItem' vtype="comboxfield" label="变更类型" labelalign="left" labelwidth="70" width="98%" dataurl="<%=contextpath%>/changeManager/getChangeType.do"></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" >		
	    	<div id="btn3" name="btn3" vtype="button" align="center" defaultview="1"  text="查 询"  click="query()"></div>
	    	<div id="btn4" name="btn4" vtype="button" align="center" defaultview="1"  text="重 置"  click="reset()"></div>
	    </div>
  	</div>
   	<div vtype="gridpanel" name="gridpanel" id="gridpanel" width="100%" layout="fit" showborder="false" datarender="renderdata()" dataurl="<%=contextpath%>/changeManager/findChangeByList.do"
	 	titledisplay="false"  isshowselecthelper="true" selecttype="0">
		<div vtype="gridcolumn" name="grid_column" id="grid_column">
			<div> <!-- 单行表头 -->
				<div name="pkDcChange" key="true" visible="false"></div>
				<div name="dataSourceName"  text="数据源名称" textalign="center" sort="true" width="15%"></div>
				<div name="busiObjectName" text="业务系统" textalign="center" sort="true" width="15%"></div>
				<div name="tableNameEn" text="物理表" textalign="center" sort="true" width="15%"></div>
				<div name="columnNameEn" text="字段名" textalign="center" sort="true" width="*"></div>
				<div name="changeItemName" text="变更类型" textalign="center" sort="true" width="10%"></div>
				<div name="changeBefore" text="变更前内容" textalign="center" sort="true" width="10%"></div>
				<div name="changeAfter" text="变更后内容" textalign="center" sort="true" width="10%"></div>
				<div name="createrTime" text="变更时间" textalign="center" sort="true" datatype="date" dataformat="YYYY-MM-DD" width="10%"></div>
			</div>
		</div>
		<!-- 表格 -->
		<div vtype="gridtable" name="grid_table" id="grid_table"></div>
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" id="grid_paginator"></div>
    </div>		
</body>
</html>