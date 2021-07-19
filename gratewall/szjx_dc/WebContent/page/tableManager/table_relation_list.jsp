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
<script src="<%=contextpath%>/static/script/home/table_relation_list.js" type="text/javascript"></script>
<style type="text/css">
.title_nav {
	border-bottom:none;
}
</style>
</head>
<body>
	<div class="title_nav">当前位置：资源管理  > 数据资源管理  > 表管理 ><span> 关系维护管理</span></div>
   	<div vtype="gridpanel" name="gridpanel" id="gridpanel" width="100%" layout="fit" showborder="false" datarender="renderdata()" dataurlparams="{'pkDcDataSource':'<%=pkDcDataSource%>','pkDcTable':'<%=pkDcTable%>'}" dataurl="<%=contextpath%>/relationManager/findRelationByList.do"
	 	titledisplay="false"  isshowselecthelper="true" selecttype="0">
	    <div name="toolbar" vtype="toolbar">
			<div id="add_button" name="add_button" vtype="button" align="right" iconurl="<%=contextpath%>/static/images/other/gridadd3.png" text="增 加" click="add()"></div>
    		<div id="add_button2" name="add_button2" vtype="button" align="right" iconurl="<%=contextpath%>/static/images/other/gridadd3.png" text="返 回" click="goBack();"></div>
    	</div>
		<div vtype="gridcolumn" name="grid_column" id="grid_column">
			<div> <!-- 单行表头 -->
				<div name="pkDcRelation" key="true" visible="false"></div>
				<!--  <div name="dataSourceName"  text="主数据源" textalign="center" sort="true" width="10%"></div> -->
				<div name="tableNameEn" text="主表" textalign="center" sort="true" width="10%"></div>
				<div name="tableNameCn" text="主表中文名" textalign="center" sort="true" width="15%"></div>
				<div name="columnNameEn" text="主字段" textalign="center" sort="true" width="10%"></div>
				<div name="columnNameCn" text="字段中文名" textalign="center" sort="true" width="8%"></div>
				<!--  <div name="dataSourceNameRelation"  text="关联数据源" textalign="center" sort="true" width="10%"></div> -->
				<div name="tableNameEnRelation" text="关联表" textalign="center" sort="true" width="10%"></div>
				<div name="tableNameCnRelation" text="关联表中文名" textalign="center" sort="true" width="15%"></div>
				<div name="columnNameEnRelation" text="关联字段" textalign="center" sort="true" width="10%"></div>
				<div name="columnNameCnRelation" text="字段中文名" textalign="center" sort="true" width="8%"></div>
				<div name="custom" text="操作" textalign="center" width="*"></div>
			</div>
		</div>
		<!-- 表格 -->
		<div vtype="gridtable" name="grid_table" id="grid_table"></div>
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" id="grid_paginator"></div>
    </div>		
</body>
</html>