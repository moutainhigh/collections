<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据中心-表管理-表关系更新</title>
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
<script src="<%=contextpath%>/static/script/home/table_relation_editor.js" type="text/javascript"></script>
</head>
<body>
   <div id="formpanel" name="formpanel" vtype="formpanel" titledisplay="false" showborder="false" width="100%" layout="table" 
	     layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%">
   		<div name='pkDcRelation' id='pkDcRelation' vtype="hiddenfield" ></div>
   		<div name='pkDcDataSource' id='pkDcDataSource' vtype="hiddenfield" ></div>
   		<div name='pkDcTable' id='pkDcTable' vtype="hiddenfield" ></div>
   		<div class="jazz-field-comp" colspan="2" rowspan="1">主表信息</div>
   		<!--  <div name='dataSourceName' id='dataSourceName' vtype="textfield" label="数据源" labelalign="right" labelwidth="100" width="80%" readonly="true"></div>
		<div name='busiObjectName' id='busiObjectName' vtype="textfield" label="业务系统" labelalign="right" labelwidth="100" width="80%" readonly="true"></div> -->
		<div name='dcTopic' id='dcTopic' vtype="comboxfield" label="业务主题" labelalign="right" labelwidth="100" width="80%" readonly="true" colspan="2" rowspan="1" dataurl="<%=contextpath%>/page/tableManager/DcTopic.json"></div> 
		<div name='tableNameEn' id='tableNameEn' vtype="textfield" label="物理表" labelalign="right" labelwidth="100" width="80%" readonly="true"></div>
		<div name='pkDcColumn' id='pkDcColumn' vtype="comboxfield" label="选择字段" labelalign="right" labelwidth="100" width="80%"  dataurl="<%=contextpath%>/relationManager/findRelationColumn.do?pkDcDataSource=<%=pkDcDataSource%>&pkDcTable=<%=pkDcTable%>" rule="must"></div>
		<div class="jazz-field-comp" colspan="2" rowspan="1">请选择关联表信息</div>
		<!-- <div name='pkDcDataSourceRelation' id='pkDcDataSourceRelation' vtype="comboxfield" label="关联数据源" labelalign="right" labelwidth="100" width="80%" dataurl="<%=contextpath%>/relationManager/findRelationDataSource.do" change="changeBusiTalbe" rule="must"></div>
		<div name='busiObjectNameRelation' id='busiObjectNameRelation' vtype="textfield" label="关联业务系统" labelalign="right" labelwidth="100" width="80%" readonly="true"></div>  -->
		<div name='dcTopicRelation' id='dcTopicRelation' vtype="comboxfield" label="业务主题" labelalign="right" labelwidth="100" width="40%" colspan="2" rowspan="1" change="changeBusiTalbe" dataurl="<%=contextpath%>/page/tableManager/DcTopic.json"></div>	
		<div name='pkDcTableRelation' id='pkDcTableRelation' vtype="comboxfield" label="关联物理表" labelalign="right" labelwidth="100" width="80%" change=changeColumn rule="must"></div>
		<div name='pkDcColumnRelation' id='pkDcColumnRelation' vtype="comboxfield" label="关联选择字段" labelalign="right" labelwidth="100" width="80%" rule="must"></div>
		 <div id="toolbar" name="toolbar" vtype="toolbar" >		
	    	<div id="btn3" name="btn3" vtype="button" text="保  存" defaultview="1" align="center" click="save()"></div>
	    	<div id="btn4" name="btn4" vtype="button" text="返  回" defaultview="1" align="center" click="leave()"></div>
	</div>
   </div>
</body>
</html>