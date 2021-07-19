<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>对外担保</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/report/yreportTable.js" type="text/javascript" ></script>
	
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>
<script type="text/javascript">
function rowclick(event,data){
}
</script>		

</head>
<body >
<div vtype="gridpanel" name="jbxxGrid" height="236" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()"
			selecttype="1"	titledisplay="true" title="对外担保信息"   showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="100%">
		<div> 
		<!--对外担保信息表 -->
		<div name='mortgagor' text="债务人" textalign="center"  width="10%"></div>
		<div name='nameisshow' text="是否公示" textalign="center"></div>
		<div name='gatype' text="保证的方式" textalign="center"  width="10%"></div>
		<div name='gatypeisshow' text="是否公示" textalign="center"></div>
		<div name='rage' text="保证范围" textalign="center"  width="10%"></div>
		<div name='rageisshow' text="是否公示" textalign="center"></div>
		<div name='pefperfrom' text="保证起始日期" textalign="center"  width="10%"></div>
		<div name='pefperto' text="保证截止日期" textalign="center"  width="10%"></div>
		<div name='pefperisshow' text="是否公示" textalign="center"></div>
		<div name='more' text="债权人" textalign="center"  width="10%"></div>
		<div name='priclaseckind' text="主债权种类" textalign="center"  width="10%"></div>
		<div name='priclasecam' text="主债权数额" textalign="center"  width="10%"></div>
		<div name='guaranperiod' text="保证的期间" textalign="center"  width="10%"></div>
		<div name='isshowstatus' text="公示状态" textalign="center"  width="10%"></div>
	</div>
	</div>
	<!-- 表格 -->
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
	<div id="formpanel" class="formpanel_table" style="height:250px;">
			<div class="font_title">详细信息</div>
			<table id="datashow" class="font_table" >
					<tr><th class="class_td1">投资人:</th><th class="class_td2" id="row2"/><th class="class_td4">投资人类型:</th><th class="class_td5" id="row3"></th></tr>
				<tr><th class="class_td1">证件号码:</th><th class="class_td2" id="row4"/><th class="class_td4">证件类型:</th><th class="class_td5" id="row5"></th></tr>
				<tr><th class="class_td1">认缴出资额:</th><th class="class_td2" id="row8"/><th class="class_td4">实缴出资额:</th><th class="class_td5" id="row9"></th></tr>
				<tr><th class="class_td1">余额缴付期限:</th><th class="class_td2" id="row10"/><th class="class_td4">出资比例:</th><th class="class_td5" id="row11"></th></tr>
				<tr><th class="class_td1">出资方式:</th><th class="class_td2" id="row12"/><th class="class_td4">认缴出资日期:</th><th class="class_td5" id="row13"></th></tr>
				<tr><th class="class_td1">实缴出资日期:</th><th class="class_td2" id="row14"/><th class="class_td4">法定代表人:</th><th class="class_td5" id="row15"></th></tr>
				<tr><th class="class_td1">执行人:</th><th class="class_td2" id="row16"/><th class="class_td4">登记类型:</th><th class="class_td5" id="row17"></th></tr>
				<tr><th class="class_td1">集团成员类型:</th><th class="class_td2" id="row18"/><th class="class_td4">承担责任方式:</th><th class="class_td5" id="row19"></th></tr>
				<tr><th class="class_td1">验资机构:</th><th class="class_td2" id="row20"/><th class="class_td4">币种:</th><th class="class_td5" id="row21"></th></tr>
				<tr><th class="class_td1">汇率:</th><th class="class_td2" id="row22"/><th class="class_td4">国别(地区):</th><th class="class_td5" id="row23"/></tr>
				<tr><th class="class_td1">住所:</th><th class="class_td2" id="row24"/><th class="class_td4">合作条件:</th><th class="class_td5" id="row25"></th></tr>
			</table>
	</div>
</body>
</html>