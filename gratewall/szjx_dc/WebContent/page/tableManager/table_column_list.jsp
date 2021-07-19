<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据中心-表管理-列表</title>
<%
	String contextpath = request.getContextPath();
	String pkDcDataSource = request.getParameter("pkDcDataSource");
	String pkDcTable = request.getParameter("pkDcTable");
%>
<script type="text/javascript">
	var pkDcDataSource = '<%=pkDcDataSource%>';
	var pkDcTable = '<%=pkDcTable%>';
</script>
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=contextpath%>/static/script/home/table_column_list.js" type="text/javascript"></script>

</head>
<body>
	<div class="title_nav">当前位置：资源管理  > 数据资源管理  > 表管理 ><span> 字段管理</span></div>
   	<div name="formpanel" id="formpanel" vtype="formpanel" titledisplay="false" width="100%" layout="table" showborder="false" layoutconfig="{cols:3, columnwidth: ['50%','50%']}" >
		<div name='columnNameEn' id='columnNameEn' vtype="textfield" label="字段名" labelalign="right" labelwidth="120" width="80%" editable="true"></div>
		<div name='columnNameCn' id='columnNameCn' vtype="textfield" label="字段中文名" labelalign="right" labelwidth="120" width="80%" editable="true"></div>
		<!--  <div name='columnType' id='columnType' vtype="textfield" label="字段类型" labelalign="right" labelwidth="120" width="80%" editable="true"></div> -->
		<div id="toolbar" name="toolbar" vtype="toolbar" >		
	    	<div id="btn3" name="btn3" vtype="button" align="center" defaultview="1"  text="查 询"  click="query()"></div>
	    	<div id="btn4" name="btn4" vtype="button" align="center" defaultview="1"  text="重 置"  click="reset()"></div>
	    	<div id="btn5" name="btn5" vtype="button" align="center" defaultview="1"  text="返 回"  click="goBack()"></div>
	    </div>
  	</div>
   	<div vtype="gridpanel" name="gridpanel" id="gridpanel" width="100%" layout="fit" showborder="false" datarender="renderdata()" dataurlparams="{'pkDcDataSource':'<%=pkDcDataSource%>','pkDcTable':'<%=pkDcTable%>'}" dataurl="<%=contextpath%>/tableManager/findColumnByList.do"
	 	titledisplay="false"  isshowselecthelper="true" selecttype="0">
		<div vtype="gridcolumn" name="grid_column" id="grid_column">
			<div> <!-- 单行表头 -->
				<div name="pkDcColumn" key="true" visible="false"></div>
				<div name="columnNameEn"  text="字段名称" textalign="center" sort="true" width="15%"></div>
				<div name="columnNameCn" text="字段中文名" textalign="center" sort="true" width="20%"></div>
				<div name="columnType" text="字段类型" textalign="center" sort="true" width="15%" ></div>
				<div name="columnLength" text="字段长度" textalign="center" sort="true" width="15%"></div>
				<div name="isNull" text="是否为空" textalign="center" sort="true" width="10%" dataurl='[{"text": "是","value": "Y"},{"text": "否", "value": "N"}]'></div>
				<div name="isStandard" text="合标性" textalign="center" sort="true" width="10%"dataurl='[{"text": "合标","value": "1"},{"text": "参照", "value": "2"},{"text": "非合标", "value": "3"},{"text": "无标可依", "value": "4"}]'></div>
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