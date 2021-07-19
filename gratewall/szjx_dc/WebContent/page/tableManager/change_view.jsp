<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据中心-表管理-更新</title>
<%
	String contextpath = request.getContextPath();
%>
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=contextpath%>/static/script/home/change_view.js" type="text/javascript"></script>
</head>
<body>
   <div id="formpanel" name="formpanel" vtype="formpanel" titledisplay="false" showborder="false" width="100%" layout="table" 
	     layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%">
   		<div name='pkDcChange' id='pkDcChange' vtype="hiddenfield" ></div>
   		<div name='dataSourceName' id='dataSourceName' vtype="textfield" label="数据源名称" labelalign="right" labelwidth="100" width="80%" disabled="true"></div>
		<div name='busiObjectName' id='busiObjectName' vtype="textfield" label="所属业务系统" labelalign="right" labelwidth="100" width="80%" disabled="true"></div>
   		<div name='tableNameEn' id='tableNameEn' vtype="textfield" label="物理表" labelalign="right" labelwidth="100" width="80%" disabled="true"></div>
		<div name='tableNameCn' id='tableNameCn' vtype="textfield" label="表中文名" labelalign="right" labelwidth="100" width="80%" disabled="true"></div>
   		<div name='columnNameEn' id='columnNameEn' vtype="textfield" label="字段名" labelalign="right" labelwidth="100" width="80%" disabled="true"></div>
		<div name='columnNameCn' id='columnNameCn' vtype="textfield" label="字段中文名" labelalign="right" labelwidth="100" width="80%" disabled="true"></div>
		<div name='changeItem' id='changeItem' vtype="comboxfield"  colspan="2" rowspan="1" label="变更类型" labelalign="right" dataurl="<%=contextpath%>/changeManager/getChangeType.do" labelwidth="100" width="80%"></div>
   		<div name='changeBefore' id='changeBefore' vtype="textfield" label="变更前内容" labelalign="right" labelwidth="100" width="80%" disabled="true"></div>
		<div name='changeAfter' id='changeAfter' vtype="textfield" label="变更后内容" labelalign="right" labelwidth="100" width="80%" disabled="true"></div>
   		<div name='createrName' id='columnNameEn' vtype="textfield" label="变更人" labelalign="right" labelwidth="100" width="80%" disabled="true"></div>
		<div name='createrTime' id='columnNameCn' vtype="textfield" label="变更时间" labelalign="right" labelwidth="100" width="80%" disabled="true"></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" >		
	    	<div id="btn4" name="btn4" vtype="button" text="返  回" defaultview="1" align="center" click="leave()"></div>
	    </div>
   </div>
</body>
</html>