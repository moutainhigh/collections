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
<script src="<%=contextpath%>/static/script/home/table_list.js" type="text/javascript"></script>
<script>
var pkDcBusiObject;
function initData(res){
	pkDcBusiObject = res.getAttr("pkDcBusiObject");
	$('div[name="gridpanel"]').gridpanel('option','dataurl',rootPath+
			'/tableManager/findTableByList.do?pkDcBusiObject='+pkDcBusiObject);
	$('div[name="gridpanel"]').gridpanel('reload');
}


</script>
</head>
<body>
	<div class="title_nav">当前位置：资源管理  > 数据资源管理  ><span> 表管理</span></div>
   	<div name="formpanel" id="formpanel" vtype="formpanel" titledisplay="false" width="100%" layout="table" showborder="false" layoutconfig="{cols:2, columnwidth: ['40%','40%']}" >
		<!--  <div name='dataSourceName' id='dataSourceName' vtype="textfield" label="业务系统" labelalign="left" labelwidth="80" width="95%" editable="true" ></div>  -->
		<div name='pkDcBusiObject' id='pkDcBusiObject' vtype="comboxfield" label="业务系统" labelalign="right" labelwidth="150" width="70%"  dataurl="<%=contextpath%>/dataSource/findKeyValueDcBusiObjectBO.do"></div>
		<div name="tableType" id='tableType' vtype="comboxfield" label="表类型" labelalign="right" labelwidth="150" width="70%" dataurl='[{"text": "业务表","value": "0"},{"text": "字典表", "value": "1"},{"text": "系统表","value": "2"},{"text": "历史表", "value": "3"}]'></div>
		<div name='tableNameEn' id='tableNameEn' vtype="textfield" label="物理表" labelalign="right" labelwidth="150" width="70%" editable="true"></div>
		<div name='tableNameCn' id='tableNameCn' vtype="textfield" label="物理表中文名" labelalign="right" labelwidth="150" width="70%" editable="true"></div>
		<!--  <div name='columnNameEn' id='columnNameEn' vtype="textfield" label="字段名" labelalign="left" labelwidth="55" width="98%" editable="true"></div>
		<div name='columnNameCn' id='columnNameCn' vtype="textfield" label="字段中文名" labelalign="left" labelwidth="80" width="95%" editable="true"></div> -->
	
		<div id="toolbar" name="toolbar" vtype="toolbar" >		
	    	<div id="btn3" name="btn3" vtype="button" align="center" defaultview="1"  text="查 询"  click="query()"></div>
	    	<div id="btn4" name="btn4" vtype="button" align="center" defaultview="1"  text="重 置"  click="reset()"></div>
	    </div>
  	</div>
   	<div vtype="gridpanel" name="gridpanel" id="gridpanel" width="100%" layout="fit" showborder="false" datarender="renderdata()"
	 	titledisplay="false"  isshowselecthelper="true" selecttype="0">
		<div vtype="gridcolumn" name="grid_column" id="grid_column">
			<div> <!-- 单行表头 -->
				<div name="pkDcTable" key="true" visible="false"></div>
				<!--  <div name="dataSourceName"  text="数据源名称" textalign="center" sort="true" width="10%"></div> -->
				<div name="busiObjectName" text="业务系统" textalign="center" sort="true" width="10%"></div>
				<div name="tableNameEn" text="物理表" textalign="center" sort="true" width="20%"></div>
				<div name="tableNameCn" text="物理表中文名" textalign="center" sort="true" width="20%"></div>
				<div name="tableType" text="表类型" textalign="center" sort="true" width="8%" dataurl='[{"text": "业务表","value": "0"},{"text": "字典表", "value": "1"},{"text": "系统表","value": "2"},{"text": "历史表", "value": "3"}]'></div>
				<div name="isCheck" text="已认领" textalign="center" sort="true" width="8%" dataurl='[{"text": "是","value": "Y"},{"text": "否", "value": "N"}]'></div>
				<div name="isShare" text="可共享" textalign="center" sort="true" width="8%" dataurl='[{"text": "是","value": "Y"},{"text": "否", "value": "N"}]'></div>
				<div name="isQuery" text="可查询" textalign="center" sort="true" width="8%" dataurl='[{"text": "是","value": "Y"},{"text": "否", "value": "N"}]'></div>
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