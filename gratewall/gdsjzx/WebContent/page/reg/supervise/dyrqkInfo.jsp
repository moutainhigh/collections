<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>抵押人情况信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="98%" id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="抵押人情况信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='pledgorid' text="主键" visible="false" textalign="center"  width="10%"></div>
				<div name='pledgeid' text="对应抵押登记业务Pledge的主键" visible="false" textalign="center"  width="10%"></div>
				<div name='pripid' text="主体身份代码" visible="false" textalign="center"  width="10%"></div>
			<div name='regno' text="注册号"  textalign="center"  width="15%"></div>
			<div name='pledgorcodeid' text="抵押人号码"  textalign="center"  width="20%"></div>
			<div name='mortgagor' text="抵押人"  textalign="center"  width="20%"></div>
			<div name='addr' text="抵押人地址" visible="false" textalign="center"  width="10%"></div>
			<div name='regdep' text="抵押人登记部门" textalign="center"  width="15%"></div>
			<div name='legrep' text="抵押人法定代表人" textalign="center"  width="15%"></div>
			<div name='legrepsex' text="抵押人法定代表人性别" visible="false" textalign="center"  width="10%"></div>
			<div name='legrepcerid' text="抵押人法定代表人证照号码"  textalign="center"  width="15%"></div>
			<div name='legrepcon' text="抵押人法定代表人国籍" visible="false" textalign="center"  width="10%"></div>
			<div name='repadd' text="抵押人法定代表人住址" visible="false" textalign="center"  width="10%"></div>
			<div name='agent' text="抵押人代理" visible="false" textalign="center"  width="10%"></div>
			<div name='sex' text="抵押人代理人性别" visible="false" textalign="center"  width="10%"></div>
			<div name='agentcertid' text="抵押人代理人证件号码" visible="false" textalign="center"  width="10%"></div>
			<div name='agentphone' text="抵押人代理人电话号码" visible="false" textalign="center"  width="10%"></div>
			<div name='agentadd' text="抵押人代理人地址" visible="false" textalign="center"  width="10%"></div>
			<div name='agentcon' text="抵押人代理人国籍" visible="false" textalign="center"  width="10%"></div>
			<div name='agentcerttype' text="抵押人代理人证件类型" visible="false" textalign="center"  width="10%"></div>
			<div name='legrepcerttype' text="抵押人法定代表人证件类型" visible="false" textalign="center"  width="10%"></div>
				<div name='sourceflag' text="SOURCEFLAG" visible="false" textalign="center"  width="10%"></div>
				<div name='timestamp' text="TIMESTAMP" visible="false" textalign="center"  width="10%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">注册号:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">抵押人号码:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押人:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">抵押人地址:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押人登记部门:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">抵押人法定代表人:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押人法定代表人性别:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">抵押人法定代表人证照号码:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押人法定代表人国籍:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">抵押人法定代表人住址:</th><th id="row13" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押人代理:</th><th id="row14" class="class_td2"></th>
			<th class="class_td4">抵押人代理人性别:</th><th id="row15" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押人代理人证件号码:</th><th id="row16" class="class_td2"></th>
			<th class="class_td4">抵押人代理人电话号码:</th><th id="row17" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押人代理人地址:</th><th id="row18" class="class_td2"></th>
			<th class="class_td4">抵押人代理人国籍:</th><th id="row19" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押人代理人证件类型:</th><th id="row20" class="class_td2"></th>
			<th class="class_td4">抵押人法定代表人证件类型:</th><th id="row21" class="class_td5"></th></tr>
	</table>
</div>
</body>
</html>