<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资源管理-业务主题管理-市场主体管理</title>
<%
	String contextpath = request.getContextPath();
%>
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=contextpath%>/static/script/home/table_list.js" type="text/javascript"></script>
<script>
var pkDcTopic;
function initData(res){
	pkDcTopic = res.getAttr("pkDcTopic");
	$('div[name="gridpanel"]').gridpanel('option','dataurl',rootPath+
			'/topicManager/findTableByTopic.do?pkDcTopic='+pkDcTopic);
	$('div[name="gridpanel"]').gridpanel('reload');
}
</script>
</head>
<body>
	<div class="title_nav">当前位置：资源管理  > 业务主题管理  ><span> 主题管理</span></div>
	
   	<!-- <div vtype="gridpanel" name="gridpanel" id="gridpanel" width="100%" layout="fit" showborder="false" datarender="renderdata()"
	 	titledisplay="false"  isshowselecthelper="true" selecttype="0"> -->
	<div vtype="gridpanel" name="gridpanel" id="gridpanel" width="100%" layout="fit" showborder="false" datarender="renderdata()"
	 	titledisplay="false"  isshowselecthelper="true" selecttype="0">
		<div vtype="gridcolumn" name="grid_column" id="grid_column">
			<div> <!-- 单行表头 -->
				<div name="pkDcTopic" key="true" visible="false"></div>
				<div name="pkDcTable" key="true" visible="false"></div>
				<!--  <div name="pkDcDataSource"  text="数据源名称" textalign="center" sort="true" width="20%"></div> -->
				<div name="tableNameEn" text="物理表" textalign="center" sort="true" width="20%"></div>
				<div name="tableNameCn" text="物理表中文名" textalign="center" sort="true" width="20%"></div>
				<div name="tableType" text="表类型" textalign="center" sort="true" width="20%" dataurl='[{"text": "业务表","value": "0"},{"text": "字典表", "value": "1"},{"text": "系统表","value": "2"},{"text": "历史表", "value": "3"}]'></div>
				<div name="createrId" text="装载人" textalign="center" sort="true" width="20%"></div>
				<div name="createrTime" text="装载时间" textalign="center" sort="true" width="20%"></div>
				<!-- <div name="custom" text="操作" textalign="center" width="*"></div> -->
			</div>
		</div>
		<!-- 表格 -->
		<div vtype="gridtable" name="grid_table" id="grid_table" rowrender="rowrender()"></div>
		<!-- 分页 -->
		<!--  <div vtype="paginator" name="grid_paginator" id="grid_paginator"></div> -->
    </div>		
</body>
</html>