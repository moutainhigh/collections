<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据中心-表管理-更新</title>
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
<script src="<%=contextpath%>/static/script/home/table_editor.js" type="text/javascript"></script>
</head>
<body>
   <div id="formpanel" name="formpanel" vtype="formpanel" titledisplay="false" showborder="false" width="100%" layout="table" 
	     layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%">
   		<div name='pkDcTable' id='pkDcTable' vtype="hiddenfield" ></div>
   		<div name='isCheck' id='isCheck' vtype="hiddenfield" ></div>
   		<div name='tableNameEn' id='tableNameEn' vtype="textfield" label="物理表" labelalign="right" labelwidth="100" width="80%" disabled="true"></div>
		<div name='busiObjectName' id='busiObjectName' vtype="textfield" label="业务系统" labelalign="right" labelwidth="100" width="80%" disabled="true"></div>
   		<div name='firstRecordCount' id='firstRecordCount' vtype="textfield" label="数据量" labelalign="right" labelwidth="100" width="80%"  disabled="true"></div>
   		<div name='primaryKeyName' id='primaryKeyName' vtype="textfield" label="表主键" labelalign="right" labelwidth="100" width="80%"  disabled="true"></div>
		<div name='tableNameCn' id='tableNameCn' vtype="textfield" label="表中文名" labelalign="right" labelwidth="100" width="80%" disabled="true"></div>
		<div name='dcTopic' id='dcTopic' vtype="comboxfield" label="业务主题" labelalign="right" labelwidth="100" width="80%"  dataurl="<%=contextpath%>/page/tableManager/DcTopic.json"></div>
		<div name='tableType' id='tableType' vtype="radiofield" label="表类型" colspan="2" rowspan="1" labelalign="right" labelwidth="100" width="80%" itemwidth="100" dataurl='[{"text": "业务表","value": "0"},{"text": "字典表", "value": "1"},{"text": "系统表","value": "2"},{"text": "历史表", "value": "3"}]'></div>
		<div name='tableUseDesc' id='tableUseDesc' vtype="textareafield"  colspan="2" rowspan="1" label="用途说明" labelalign="right" labelwidth="100" width="90%" height="80" editable="true" maxlength="1500"></div>
		<div name='remarks' id='remarks' vtype="textareafield"  colspan="2" rowspan="1" label="备注" labelalign="right" labelwidth="100" width="90%" height="80" editable="true" maxlength="1500"></div>
		
		
		<div id="toolbar" name="toolbar" vtype="toolbar" class="toolLine">		
	    	<div id="btn3" name="btn3" vtype="button" text="保  存" defaultview="1" align="center" click="save()"></div>
	    	<div id="btn4" name="btn4" vtype="button" text="返  回" defaultview="1" align="center" click="leave()"></div>
	    </div>
   </div>
</body>
</html>