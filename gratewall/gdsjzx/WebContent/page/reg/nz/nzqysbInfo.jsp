<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>农资企业商品信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="230" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="农资企业商品信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='merchandiseid' text="商品标识" visible="false" textalign="center"  width="10%"></div>
				<div name='pripid' text="主体身份代码" visible="false" textalign="center"  width="10%"></div>
				<div name='code' text="商品序号" visible="false" textalign="center"  width="10%"></div>
			<div name='categoryid' text="经营商品类别" textalign="center"  width="10%"></div>
			<div name='mdsename' text="商品名称" textalign="center"  width="20%"></div>
			<div name='model' text="型号（款式）" textalign="center"  width="15%"></div>
			<div name='manent' text="生产企业" textalign="center"  width="20%"></div>
				<div name='prono' text="产品编号" visible="false" textalign="center"  width="10%"></div>
			<div name='farspeartregno' text="营业执照注册号" textalign="center"  width="15%"></div>
			<div name='tmname' text="商标名称" textalign="center"  width="19%"></div>
			<div name='entcode' text="企业代码" visible="false" textalign="center"  width="10%"></div>
			<div name='busst' text="经营状态" visible="false" textalign="center"  width="10%"></div>
			<div name='entname' text="企业(机构)名称" visible="false" textalign="center"  width="10%"></div>
			<div name='regno' text="注册号" visible="false" textalign="center"  width="10%"></div>
			<div name='prospec' text="产品规格" visible="false" textalign="center"  width="10%"></div>
			<div name='isbusiness' text="是否经营" visible="false" textalign="center"  width="10%"></div>
			<div name='units1' text="规格1" visible="false" textalign="center"  width="10%"></div>
			<div name='units2' text="规格2" visible="false" textalign="center"  width="10%"></div>
			<div name='units3' text="规格3" visible="false" textalign="center"  width="10%"></div>
			<div name='flag' text="入库标记" visible="false" textalign="center"  width="10%"></div>
			<div name='isedited' text="已修改标记" visible="false" textalign="center"  width="10%"></div>
			<div name='productlicenseno' text="生产许可证号" visible="false" textalign="center"  width="10%"></div>
			<div name='issync' text="备用" visible="false" textalign="center"  width="10%"></div>
			<div name='lastupdatetime' text="最后一次修改时间" visible="false" textalign="center"  width="10%"></div>
			<div name='isdeleted' text="删除标记" visible="false" textalign="center"  width="10%"></div>
				<div name='basemerchandiseid' text="商品标识" visible="false" textalign="center"  width="10%"></div>
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
		<tr><th class="class_td1">经营商品类别:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">商品名称:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">型号（款式）:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">生产企业:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">产品编号:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">营业执照注册号:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">商标名称:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">企业代码:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">经营状态:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">企业(机构)名称:</th><th id="row13" class="class_td5"></th></tr>
		<tr><th class="class_td1">注册号:</th><th id="row14" class="class_td2"></th>
			<th class="class_td4">产品规格:</th><th id="row15" class="class_td5"></th></tr>
		<tr><th class="class_td1">是否经营:</th><th id="row16" class="class_td2"></th>
			<th class="class_td4">规格1:</th><th id="row17" class="class_td5"></th></tr>
		<tr><th class="class_td1">规格2:</th><th id="row18" class="class_td2"></th>
			<th class="class_td4">规格3:</th><th id="row19" class="class_td5"></th></tr>
		<tr><th class="class_td1">入库标记:</th><th id="row20" class="class_td2"></th>
			<th class="class_td4">已修改标记:</th><th id="row21" class="class_td5"></th></tr>
		<tr><th class="class_td1">生产许可证号:</th><th id="row22" class="class_td2"></th>
			<th class="class_td4">备用:</th><th id="row23" class="class_td5"></th></tr>
		<tr><th class="class_td1">最后一次修改时间:</th><th id="row24" class="class_td2"></th>
			<th class="class_td4">删除标记:</th><th id="row25" class="class_td5"></th></tr>
	</table>
</div>
</body>
</html>