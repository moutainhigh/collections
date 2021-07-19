<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>抵押人信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="230" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="抵押人信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='mortgagorid' text="抵押人主键" visible="false" textalign="center"  width="10%"></div>
				<div name='pledgeid' text="抵押信息主键" visible="false" textalign="center"  width="10%"></div>
				<div name='pripid' text="主体身份代码" visible="false" textalign="center"  width="10%"></div>
			<div name='mortgagor' text="抵押人" textalign="center"  width="20%"></div>
			<div name='regno' text="注册号" textalign="center"  width="20%"></div>
			<div name='mortadd' text="抵押人地址" visible="false" textalign="center"  width="10%"></div>
			<div name='mortregdept' text="抵押人注册机关"  textalign="center"  width="15%"></div>
			<div name='mortlegrep' text="抵押人法定代表人"  textalign="center"  width="15%"></div>
			<div name='mortlegrepsex' text="抵押人法定代表人性别"  textalign="center"  width="15%"></div>
			<div name='mortlegrepcertid' text="抵押人法定代表人身份证"  textalign="center"  width="15%"></div>
			<div name='mortlegrepcon' text="抵押人法定代表人国籍" visible="false" textalign="center"  width="10%"></div>
			<div name='mortlegrepadd' text="抵押人法定代表人住所" visible="false" textalign="center"  width="10%"></div>
			<div name='mortagent' text="抵押人代理人" visible="false" textalign="center"  width="10%"></div>
			<div name='mortagentsex' text="抵押人代理人性别" visible="false" textalign="center"  width="10%"></div>
			<div name='mortagentcertid' text="抵押人代理人身份证" visible="false" textalign="center"  width="10%"></div>
			<div name='mortagentphone' text="抵押人代理人电话" visible="false" textalign="center"  width="10%"></div>
			<div name='mortagentadd' text="抵押人代理人住所" visible="false" textalign="center"  width="10%"></div>
			<div name='mortagentcon' text="抵押人代理人国籍" visible="false" textalign="center"  width="10%"></div>
			<div name='mortlegrepcerttype' text="抵押人法定代表人证件类型" visible="false" textalign="center"  width="10%"></div>
			<div name='mortagentcerttype' text="抵押人代理证件类型" visible="false" textalign="center"  width="10%"></div>
			<div name='mortentitytype' text="当时人经济实体类型" visible="false" textalign="center"  width="10%"></div>
			<div name='mortregdeptid' text="抵押人的登记机关代码" visible="false" textalign="center"  width="10%"></div>
			<div name='isforeign' text="是否异地登记企业" visible="false" textalign="center"  width="10%"></div>
			<div name='mortproperty' text="抵押权人性质" visible="false" textalign="center"  width="10%"></div>
			<div name='applicantcername' text="代理人资格" visible="false" textalign="center"  width="10%"></div>
			<div name='mortcardidtype' text="当事人证件类型" visible="false" textalign="center"  width="10%"></div>
			<div name='mortcardid' text="当事人证件号" visible="false" textalign="center"  width="10%"></div>
				<div name='sourceflag' text="SOURCEFLAG" visible="false" textalign="center"  width="10%"></div>
				<div name='timestamp' text="TIMESTAMP" visible="false" textalign="center"  width="10%"></div>
				<div name='mortcodeid' text="抵押人唯一码" visible="false" textalign="center"  width="10%"></div>									
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">抵押人:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">注册号:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押人地址:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">抵押人注册机关:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押人法定代表人:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">抵押人法定代表人性别:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押人法定代表人身份证:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">抵押人法定代表人国籍:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押人法定代表人住所:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">抵押人代理人:</th><th id="row13" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押人代理人性别:</th><th id="row14" class="class_td2"></th>
			<th class="class_td4">抵押人代理人身份证:</th><th id="row15" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押人代理人电话:</th><th id="row16" class="class_td2"></th>
			<th class="class_td4">抵押人代理人住所:</th><th id="row17" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押人代理人国籍:</th><th id="row18" class="class_td2"></th>
			<th class="class_td4">抵押人法定代表人证件类型:</th><th id="row19" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押人代理证件类型:</th><th id="row20" class="class_td2"></th>
			<th class="class_td4">当时人经济实体类型:</th><th id="row21" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押人的登记机关代码:</th><th id="row22" class="class_td2"></th>
			<th class="class_td4">是否异地登记企业:</th><th id="row23" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押权人性质:</th><th id="row24" class="class_td2"></th>
			<th class="class_td4">代理人资格:</th><th id="row25" class="class_td5"></th></tr>	
		<tr><th class="class_td1">当事人证件类型:</th><th id="row26" class="class_td2"></th>
			<th class="class_td4">当事人证件号:</th><th id="row27" class="class_td5"></th></tr>
	</table>
</div>
</body>
</html>