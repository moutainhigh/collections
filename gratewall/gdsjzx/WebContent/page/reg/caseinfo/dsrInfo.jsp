<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>案件基本信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="230" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="当事人信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='casepartyid' text="案件当事人ID" visible="false" textalign="center"  width="10%"></div>
				<div name='caseid' text="案件记录ID" visible="false" textalign="center"  width="10%"></div>
				<div name='sourceflag' text="变" visible="false" textalign="center"  width="10%"></div>
			<div name='name' text="当事人名称"  textalign="center"  width="20%"></div>
			<div name='regno' text="注册号" textalign="center"  width="15%"></div>
			<div name='partytype' text="当事人类型" textalign="center"  width="15%"></div>
			<div name='lerep' text="法定代表人" textalign="center"  width="15%"></div>
			<div name='uniscid' text="统一社会信用代码" textalign="center"  width="15%"></div>
			<div name='unitname' text="单位名称" textalign="center"  width="19%"></div>
			<div name='lereppos' text="法定代表人职务" visible="false" textalign="center"  width="10%"></div>
			<div name='sex' text="性别" visible="false" textalign="center"  width="10%"></div>
			<div name='age' text="年龄" visible="false" textalign="center"  width="10%"></div>
			<div name='occupation' text="职业" visible="false" textalign="center"  width="10%"></div>
			<div name='dom' text="住所" visible="false" textalign="center"  width="10%"></div>
			<div name='tel' text="联系电话" visible="false" textalign="center"  width="10%"></div>
			<div name='postalcode' text="邮政编码" visible="false" textalign="center"  width="10%"></div>
			<div name='workunit' text="工作单位" visible="false" textalign="center"  width="10%"></div>
			<div name='litedeg' text="文化程度" visible="false" textalign="center"  width="10%"></div>
			<div name='certype' text="证件类型" visible="false" textalign="center"  width="10%"></div>
			<div name='cerno' text="证件号码" visible="false" textalign="center"  width="10%"></div>
			<div name='nation' text="民族" visible="false" textalign="center"  width="10%"></div>
			<div name='enqtime' text="询问时间" visible="false" textalign="center"  width="10%"></div>
			<div name='enttype' text="市场主体类型" visible="false" textalign="center"  width="10%"></div>
				<div name='partytab' text="变" visible="false" textalign="center"  width="10%"></div>
				<div name='timestamp' text="变" visible="false" textalign="center"  width="10%"></div>
				<div name='pripid' text="主体身份代码" visible="false" textalign="center"  width="10%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">当事人名称:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">注册号:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">当事人类型:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">法定代表人:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">统一社会信用代码:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">单位名称:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">法定代表人职务:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">性别:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">年龄:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">职业:</th><th id="row13" class="class_td5"></th></tr>
		<tr><th class="class_td1">住所:</th><th id="row14" class="class_td2"></th>
			<th class="class_td4">联系电话:</th><th id="row15" class="class_td5"></th></tr>
		<tr><th class="class_td1">邮政编码:</th><th id="row16" class="class_td2"></th>
			<th class="class_td4">工作单位:</th><th id="row17" class="class_td5"></th></tr>
		<tr><th class="class_td1">文化程度:</th><th id="row18" class="class_td2"></th>
			<th class="class_td4">证件类型:</th><th id="row19" class="class_td5"></th></tr>
		<tr><th class="class_td1">证件号码:</th><th id="row20" class="class_td2"></th>
			<th class="class_td4">民族:</th><th id="row21" class="class_td5"></th></tr>
		<tr><th class="class_td1">询问时间:</th><th id="row22" class="class_td2"></th>
			<th class="class_td4">市场主体类型:</th><th id="row23" class="class_td5"></th></tr>
			
	</table>
</div>
</body>
</html>