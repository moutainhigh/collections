<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>农资商品标准信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="230" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="农资商品标准信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='merchandiseid' text="商品标识" visible="false" textalign="center"  width="10%"></div>
				<div name='categoryid' text="经营商品类别" visible="false" textalign="center"  width="10%"></div>
				<div name='sourseid' text="信息来源Id" visible="false" textalign="center"  width="10%"></div>
			<div name='mdsename' text="商品名称" textalign="center"  width="22%"></div>
			<div name='model' text="型号（款式）" textalign="center"  width="15%"></div>
			<div name='manent' text="生产企业" textalign="center"  width="22%"></div>
				<div name='prono' text="产品编号" visible="false" textalign="center"  width="10%"></div>
			<div name='farspeartregno' text="营业执照注册号" textalign="center"  width="20%"></div>
			<div name='tmname' text="商标名称" textalign="center"  width="20%"></div>
			<div name='busst' text="经营状态" visible="false" textalign="center"  width="10%"></div>
			<div name='infoori' text="信息来源" visible="false" textalign="center"  width="10%"></div>
			<div name='productlicenseno' text="生产许可证号" visible="false" textalign="center"  width="10%"></div>
			<div name='isdeleted' text="删除标记" visible="false" textalign="center"  width="10%"></div>
				<div name='iskey' text="isKey" visible="false" textalign="center"  width="10%"></div>
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
		<tr><th class="class_td1">商品名称:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">型号（款式）:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">生产企业:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">产品编号:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">营业执照注册号:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">商标名称:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">经营状态:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">信息来源:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">生产许可证号:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">删除标记:</th><th id="row13" class="class_td5"></th></tr>
	</table>
</div>
</body>
</html>