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
<script src="<%=contextpath%>/static/script/home/relation_list.js" type="text/javascript"></script>
<script>
function initData(res){
	$('div[name="gridpanel"]').gridpanel('option','dataurl',rootPath+
			'/relationManager/initTable.do');
	$('div[name="gridpanel"]').gridpanel('reload');
}
</script>
</head>
<body>
	<div class="title_nav">当前位置：资源管理  > 数据资源管理  ><span> 表关系管理</span></div>
   	<div name="formpanel" id="formpanel" vtype="formpanel" titledisplay="false" width="100%" layout="table" showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','50%']}" >
		<!--  <div name='dataSourceName' id='dataSourceName' vtype="textfield" label="数据源名称" labelalign="left" labelwidth="80" width="95%" editable="true"></div> -->
		<div name='dcTopic' id='dcTopic' vtype="comboxfield" label="业务主题" labelalign="right" labelwidth="150" width="70%"  dataurl="<%=contextpath%>/page/tableManager/DcTopic.json"></div>
		<div name="tableType" id='tableType' vtype="comboxfield" label="表类型" labelalign="right" labelwidth="150" width="70%" dataurl='[{"text": "业务表","value": "0"},{"text": "字典表", "value": "1"},{"text": "系统表","value": "2"},{"text": "历史表", "value": "3"}]'></div>
		<div name='tableNameEn' id='tableNameEn' vtype="textfield" label="物理表" labelalign="right" labelwidth="150" width="70%" editable="true"></div>
		<div name='tableNameCn' id='tableNameCn' vtype="textfield" label="物理表中文名" labelalign="right" labelwidth="150" width="70%" editable="true"></div>
		
		
		<div id="toolbar" name="toolbar" vtype="toolbar" >		
	    	<div id="btn3" name="btn3" vtype="button" align="center" defaultview="1"  text="查 询"  click="query()"></div>
	    	<div id="btn4" name="btn4" vtype="button" align="center" defaultview="1"  text="重 置"  click="reset()"></div>
	    </div>
  	</div>
   	<div vtype="gridpanel" name="gridpanel" id="gridpanel" width="100%" layout="fit" showborder="false" datarender="renderdata()" dataurl="<%=contextpath%>/tableManager/findTableByList.do"
	 	titledisplay="false"  isshowselecthelper="true" selecttype="0">
		<div vtype="gridcolumn" name="grid_column" id="grid_column">
			<div> <!-- 单行表头 -->
				<div name="pkDcTable" key="true" visible="false"></div>
				<!--  <div name="dataSourceName"  text="数据源名称" textalign="center" sort="true" width="15%"></div>
				<div name="busiObjectName" text="业务系统" textalign="center" sort="true" width="15%"></div> -->
				<div name="dcTopic" text="业务主题" textalign="center" sort="true" width="10%" dataurl="<%=contextpath%>/page/tableManager/DcTopic.json"></div>
				<div name="tableNameEn" text="物理表" textalign="center" sort="true" width="20%"></div>
				<div name="tableNameCn" text="物理表中文名" textalign="center" sort="true" width="25%"></div>
				<div name="tableType" text="表类型" textalign="center" sort="true" width="10%" dataurl='[{"text": "业务表","value": "0"},{"text": "字典表", "value": "1"},{"text": "系统表","value": "2"},{"text": "历史表", "value": "3"}]'></div>
				<div name="createrName" text="管理人" textalign="center" sort="true" width="10%" ></div>
				<div name="createrTime" text="管理日期" textalign="center" sort="true" datatype="date" dataformat="YYYY-MM-DD" width="10%" ></div>
				<div name="custom" text="操作" textalign="center" width="*"></div>
			</div>
		</div>
		<!-- 表格 -->
		<div vtype="gridtable" name="grid_table" id="grid_table" rowrender="rowrender()"></div>
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" id="grid_paginator"></div>
    </div>		
</body>
</html>