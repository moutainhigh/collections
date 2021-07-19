<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>应用集成-管理</title>
<%
	String contextpath = request.getContextPath();
%>
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=contextpath%>/static/script/home/xt_list.js" type="text/javascript"></script>

</head>
<body>
	<div class="title_nav">当前位置：应用集成 > <span>应用集成管理</span></div>
   	<div name="formpanel" id="formpanel" vtype="formpanel" titledisplay="false" width="100%" layout="table" showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*']}" >
		<div name='systemName' id='systemName' vtype="textfield" label="子系统名称" labelalign="right" labelwidth="120" width="90%" editable="true"></div>
		<div name='systemCode' id='systemCode' vtype="textfield" label="子系统编码" labelalign="right" labelwidth="120" width="90%" editable="true"></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" >		
	    	<div id="btn3" name="btn3" vtype="button" align="center" defaultview="1"  text="查 询"  click="query()"></div>
	    	<div id="btn4" name="btn4" vtype="button" align="center" defaultview="1"  text="重 置"  click="reset()"></div>
	    </div>
  	</div>
   	<div vtype="gridpanel" name="gridpanel" id="gridpanel" width="100%" layout="fit" showborder="false" datarender="renderdata()" dataurl="<%=contextpath%>/integration/findByListIntegration.do"
	 	titledisplay="false"  isshowselecthelper="true" selecttype="0">
	 	<div name="toolbar" vtype="toolbar">
			<div id="add_button" name="add_button" vtype="button" align="right" iconurl="<%=contextpath%>/static/images/other/gridadd3.png" text="增加" click="add()"></div>
    	</div>
		<div vtype="gridcolumn" name="grid_column" id="grid_column">
			<div> <!-- 单行表头 -->
				<div name="pkSysIntegration" key="true" visible="false"></div>
				<div name="systemName"  text="子系统名称" textalign="center" sort="true" width="8%"></div>
				<div name="systemCode" text="子系统编码" textalign="center" sort="true" width="4%"></div>
				<div name="systemState" text="子系统运行状态" textalign="center" sort="true" dataurl='[{"text": "启用","value": "0"},{"text": "停止", "value": "1"}]' width="8%"></div>
				<div name="systemType" datatype="comboxfield" text="是否业务域"textalign="center" sort="true" dataurl='[{"text": "否","value": "SYS"},{"text": "是", "value": "NOSYS"}]' width="6%"></div>
				<div name="integratedUrl" text="集成URL" textalign="center" ></div>
	
				<div name="createrTime" text="创建时间" textalign="center" sort="true" datatype="date" dataformat="YYYY-MM-DD" width="8%"></div>
				<div name="firmname" text="系统厂商" textalign="center" sort="true" width="8%"></div>
				<div name="linkman" text="厂商联系人" textalign="center" sort="true" width="8%"></div>
				<div name="custom" text="操作" textalign="center" width="14%"></div>
			</div>
		</div>
		<!-- 表格 -->
		<div vtype="gridtable" name="grid_table" id="grid_table" rowrender="rowrender()"></div>
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" id="grid_paginator"></div>
    </div>		
</body>
</html>