<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>商标代理人</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>

<script src="<%=request.getContextPath()%>/static/js/sczt/sczt.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>

<script type="text/javascript">
function rowclick(event,data){
}
</script>
				 
</head>
<body >
<div vtype="gridpanel" name="jbxxGrid" height="245" id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="商标代理人信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
			<div name='code' text="代理人代码" textalign="center"  width="15%"></div>
			<div name='name' text="代理人中文名称" textalign="center"  width="20%"></div>
			<div name='enname' text="英文名称" textalign="center"  width="15%"></div>
			<div name='agentaddr' text="代理人中文地址" visible="false" textalign="center"  width="10%"></div>
			<div name='agentenaddr' text="英文地址" visible="false" textalign="center"  width="10%"></div>
			<div name='agentcontentname' text="代理人联系人" visible="false" textalign="center"  width="10%"></div>
			<div name='agentcontentaddr' text="代理人联系地址" visible="false" textalign="center"  width="10%"></div>
			<div name='agentcontentzip' text="代理人联系邮编" visible="false" textalign="center"  width="10%"></div>
			<div name='agentcontenttel' text="代理人联系电话" visible="false" textalign="center"  width="10%"></div>
			<div name='agentmobilenum' text="代理人手机号码" visible="false" textalign="center"  width="10%"></div>
			<div name='agentcontentfax' text="代理人联系传真" visible="false" textalign="center"  width="10%"></div>
			<div name='agentcontenteml' text="代理人联系E_Mail" visible="false" textalign="center"  width="10%"></div>
			<div name='agenteconomytype' text="代理人经济性质" textalign="center"  width="15%"></div>
			<div name='agenttype' text="代理人类型" textalign="center"  width="15%"></div>
			<div name='agentcername' text="代理人证件名称" visible="false" textalign="center"  width="10%"></div>
			<div name='agentcerid' text="代理人证件编码" visible="false" textalign="center"  width="10%"></div>
			<div name='agentdistrict' text="代理人行政区划" visible="false" textalign="center"  width="10%"></div>
			<div name='agentstate' text="代理人状态" textalign="center"  width="10%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">代理人代码:</th><th id="row1" class="class_td2"></th>
			<th class="class_td4">代理人中文名称:</th><th id="row2" class="class_td5"></th></tr>
		<tr><th class="class_td1">英文名称:</th><th id="row3" class="class_td2"></th>
			<th class="class_td4">代理人中文地址:</th><th id="row4" class="class_td5"></th></tr>
		<tr><th class="class_td1">英文地址:</th><th id="row5" class="class_td2"></th>
			<th class="class_td4">代理人联系人:</th><th id="row6" class="class_td5"></th></tr>
		<tr><th class="class_td1">代理人联系地址:</th><th id="row7" class="class_td2"></th>
			<th class="class_td4">代理人联系邮编:</th><th id="row8" class="class_td5"></th></tr>
		<tr><th class="class_td1">代理人联系电话:</th><th id="row9" class="class_td2"></th>
			<th class="class_td4">代理人手机号码:</th><th id="row10" class="class_td5"></th></tr>
		<tr><th class="class_td1">代理人联系传真:</th><th id="row11" class="class_td2"></th>
			<th class="class_td4">代理人联系E_Mail:</th><th id="row12" class="class_td5"></th></tr>
		<tr><th class="class_td1">代理人经济性质:</th><th id="row13" class="class_td2"></th>
			<th class="class_td4">代理人类型:</th><th id="row14" class="class_td5"></th></tr>
		<tr><th class="class_td1">代理人证件名称:</th><th id="row15" class="class_td2"></th>
			<th class="class_td4">代理人证件编码:</th><th id="row16" class="class_td5"></th></tr>
		<tr><th class="class_td1">代理人行政区划编码:</th><th id="row17" class="class_td2"></th>
			<th class="class_td4">代理人状态:</th><th id="row18" class="class_td5"></th></tr>
	</table>
</div>
</body>
</html>