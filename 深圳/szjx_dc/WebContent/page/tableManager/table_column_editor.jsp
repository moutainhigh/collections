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
	String pkDcColumn = request.getParameter("pkDcColumn");
	String type = request.getParameter("type");
%>
<script type="text/javascript">
	var pkDcDataSource = '<%=pkDcDataSource%>';
	var pkDcTable = '<%=pkDcTable%>';
	var pkDcColumn = '<%=pkDcColumn%>';
	var type = '<%=type%>';
</script>
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=contextpath%>/static/script/home/table_column_editor.js" type="text/javascript"></script>
</head>
<body>
   <div id="formpanel" name="formpanel" vtype="formpanel" titledisplay="false" showborder="false" width="100%" layout="table" 
	     layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%">
   		<div name='pkDcColumn' id='pkDcColumn' vtype="hiddenfield" ></div>
   		<div name='isCheck' id='isCheck' vtype="hiddenfield" ></div>
   		<div name='columnNameEn' id='columnNameEn' vtype="textfield" label="字段名" labelalign="right" labelwidth="100" width="90%" disabled="true"></div>
	
		<div name='columnType' id='columnType' vtype="textfield" label="字段类型" labelalign="right" labelwidth="100" width="90%"  disabled="true"></div>
   		<div name='columnLength' id='columnLength' vtype="textfield" label="字段长度" labelalign="right" labelwidth="100" width="90%"  disabled="true"></div>
   		<div name='isNull' id='isNull' vtype="radiofield" label="是否为空" labelalign="right" labelwidth="100" width="90%"  itemwidth="50" disabled="true" dataurl='[{"text": "是","value": "Y"},{"text": "否", "value": "N"}]'></div>
<!-- 		<div name='effectiveMarker' id='effectiveMarker' vtype="radiofield" label="是否有效" labelwidth="100" labelalign="right" width="90%" disabled="true" itemwidth="50" dataurl='[{"text": "是","value": "Y"},{"text": "否", "value": "N"}]'></div>
 -->
<!-- 		<div name='defaultValue' id='defaultValue' vtype="textfield" label="缺省值" labelalign="right" labelwidth="100" width="90%" ></div>
		<div name='standardValue' id='standardValue' vtype="textfield" label="标准标识符" labelalign="right" labelwidth="100" width="90%" ></div> -->
<!-- 		<div name='isCodedata' id='isCodedata' vtype="radiofield" label="是否代码集" labelalign="right" labelwidth="100" width="90%"  itemwidth="50" dataurl='[{"text": "是","value": "Y"},{"text": "否", "value": "N"}]'></div>
 -->		
 		<div name='isStandard' id='isStandard'vtype="radiofield" colspan="2" label="合标性"  rule="must" labelalign="right" labelwidth="100" width="90%"  itemwidth="110" dataurl='[{"text": "合标","value": "1"},{"text": "参照", "value": "2"},{"text": "非合标", "value": "3"},{"text": "无标可依", "value": "4"}]'></div>
		<div name='columnNameCn' id='columnNameCn' vtype="textfield" label="字段中文名" labelalign="right" labelwidth="100" width="90%" ></div>
		<div name='dcDmId' id='dcDmId' vtype="comboxfield" label="系统代码集"  labelalign="right" labelwidth="100" width="90%"  dataurl="<%=contextpath%>/tableManager/getSysCodeSet.do?pkDcDataSource=<%=pkDcDataSource%>"></div>

		<div name='pkDcStandardDataelement' id='pkDcStandardDataelement' vtype="comboxfield" label="基础数据元"  labelalign="right" labelwidth="100" width="90%"  dataurl="<%=contextpath%>/tableManager/getStand.do"></div>
	
	
		<div name='pkDcStandarCodeindex' id='pkDcStandarCodeindex' vtype="comboxfield" label="基础代码集" labelalign="right" labelwidth="100" width="90%"  dataurl="<%=contextpath%>/tableManager/getCode.do"></div>
		
		
		
		<div name='remarks' id='remarks' vtype="textareafield"  colspan="2" rowspan="1" label="说明" labelalign="right" labelwidth="100" width="95.2%" height="80" editable="true" maxlength="1500"></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" >		
	    	<div id="btn3" name="btn3" vtype="button" text="保  存" defaultview="1" align="center" click="save()"></div>
	    	<div id="btn4" name="btn4" vtype="button" text="返  回" defaultview="1" align="center" click="leave()"></div>
	    </div>
   </div>
</body>
</html>