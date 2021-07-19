<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>隶属信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/sczt/sczt.js" type="text/javascript" ></script>
	
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>

<script type="text/javascript">

function rowclick(event,data){
}
</script>
				 
</head>
<body >
<div vtype="gridpanel" name="jbxxGrid" height="236" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()"
			selecttype="1"	titledisplay="true" title="隶属信息"   showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="100%">
		<div> 
		<!-- 市场主体隶属信息 -->
		<div name='regno' text="注册号" textalign="center"  width="13%"></div>
		<div  name='entname' text="企业名称" textalign="center"  width="32%"></div>
			<div name='foreignname' text="外文名称" textalign="center"></div>
			<div name='enterprisetype' text="企业类型" textalign="center"></div>
		
		<div name='entitycharacter' text="隶属企业性质" textalign="center"  width="12%"></div>
		<div name='subordinaterelation' text="隶属关系" textalign="center"  width="8%"></div>
		<div name='domdistrict' text="隶属企业住所行政区划" textalign="center"  width="12%"></div>
		<div name='country' text="隶属企业国别" textalign="center"  width="8%"></div>
		
			<div name='regorg' text="登记机关" textalign="center"></div>
			<div name='opscoandform' text="经营范围及方式" textalign="center"></div>
			
			<div name='isbranch' text="是否分支机构" textalign="center"></div>
			<div name='isforeign' text="是否外来的企业" textalign="center"></div>
		<div name='prilname' text="负责人" textalign="center"  width="8%"></div>
			<div name='estdate' text="成立日期" textalign="center" ></div>
			<div name='operbegindate' text="隶属企业营业起始时间" textalign="center"></div>
			<div name='operenddate' text="隶属企业营业终止时间" textalign="center" ></div>
			
			<div name='addr' text="住所" textalign="center"></div>
			<div name='tel' text="联系电话" textalign="center"></div>
			
	<!-- 市场主体隶属信息补充信息 -->
		<div name='regcap' text="注册资本" textalign="center"  width="8%"></div>
			<div name='regcapcur' text="注册资本币种" textalign="center"></div>
			<div name='exchangerate' text="汇率" textalign="center" ></div>
			<div name='reccap' text="实收资本" textalign="center"></div>
			<div name='regcapusd' text="注册资本(金)折万美元" textalign="center" ></div>
			<div name='regcaprmb' text="注册资本(金)折人民币" textalign="center" ></div>
		
	</div>
	</div>
	<!-- 表格 -->
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
  	<div id="formpanel" class="formpanel_table" style="height:250px;">
			<div class="font_title">详细信息</div>
			<table id="datashow" class="font_table" >
				<tr class="class_hg"><th class="class_td1">注册号:</th><th class="class_td2" id="row1"></th><th class="class_td4">企业名称:</th><th id="row2" class="class_td5"></th></tr>
				<tr><th class="class_td1">外文名称:</th><th class="class_td2" id="row3"></th><th class="class_td4">企业类型:</th><th class="class_td5" id="row4"></th></tr>
				<tr><th class="class_td1">隶属企业性质:</th><th class="class_td2" id="row5"></th><th class="class_td4">隶属关系:</th><th class="class_td5" id="row6"></th></tr>
				<tr><th class="class_td1">隶属企业住所行政区划:</th><th class="class_td2" id="row7"></th><th class="class_td4">隶属企业国别:</th><th class="class_td5" id="row8"></th></tr>
				<tr><th class="class_td1">登记机关:</th><th class="class_td2" id="row9"></th><th class="class_td4">经营范围及方式:</th><th class="class_td5" id="row10"></th></tr>
				<tr><th class="class_td1">是否分支机构:</th><th class="class_td2" id="row11"></th><th class="class_td4">是否外来的企业:</th><th class="class_td5" id="row12"></th></tr>
				<tr><th class="class_td1">负责人:</th><th class="class_td2" id="row13"></th><th class="class_td4">成立日期:</th><th class="class_td5" id="row14"></th></tr>
				<tr><th class="class_td1">隶属企业营业起始时间:</th><th class="class_td2" id="row15"></th><th class="class_td4">隶属企业营业终止时间:</th><th class="class_td5" id="row16"></th></tr>
				<tr><th class="class_td1">住所:</th><th class="class_td2" id="row17"></th><th class="class_td4">联系电话:</th><th class="class_td5" id="row18"></th></tr>
				<tr><th class="class_td1">注册资本:</th><th class="class_td2" id="row19"></th><th class="class_td4">注册资本币种:</th><th class="class_td5" id="row20"></th></tr>
				<tr><th class="class_td1">汇率:</th><th class="class_td2" id="row21"></th><th class="class_td4">实收资本:</th><th class="class_td5" id="row22"></th></tr>
				<tr><th class="class_td1">注册资本(金)折万美元:</th><th class="class_td2" id="row23"></th><th class="class_td4">注册资本(金)折人民币:</th><th class="class_td5" id="row24"></th></tr>
			</table>
	</div>
</body>
</html>