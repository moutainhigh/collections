<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>农资供销企业信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="230" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="农资供销企业信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='customerid' text="供货/经销商标识" visible="false" textalign="center"  width="10%"></div>
				<div name='pripid' text="主体身份代码" visible="false" textalign="center"  width="10%"></div>
				<div name='sourceflag' text="SOURCEFLAG" visible="false" textalign="center"  width="10%"></div>
			<div name='entname' text="企业名称" textalign="center"  width="20%"></div>
				<div name='customerno' text="供货/经销商自编号" visible="false" textalign="center"  width="10%"></div>
			<div name='corpname' text="供货/经销商名称" textalign="center"  width="20%"></div>
			<div name='farspeartregno' text="营业执照注册号" textalign="center"  width="20%"></div>
			<div name='buyorsell' text="供/销" textalign="center"  width="10%"></div>
			<div name='shoadd' text="店铺地址" textalign="center"  width="29%"></div>
			<div name='postalcode' text="邮政编码" visible="false" textalign="center"  width="10%"></div>
			<div name='email' text="电子邮箱" visible="false" textalign="center"  width="10%"></div>
			<div name='linkman' text="联系人" visible="false" textalign="center"  width="10%"></div>
			<div name='tel' text="联系电话" visible="false" textalign="center"  width="10%"></div>
			<div name='mobtel' text="移动电话" visible="false" textalign="center"  width="10%"></div>
			<div name='busst' text="经营状态" visible="false" textalign="center"  width="10%"></div>
			<div name='issync' text="备用" visible="false" textalign="center"  width="10%"></div>
			<div name='lastupdatetime' text="最后修改时间" visible="false" textalign="center"  width="10%"></div>
			<div name='isdeleted' text="删除标记" visible="false" textalign="center"  width="10%"></div>
				<div name='timestamp' text="TIMESTAMP" visible="false" textalign="center"  width="10%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">企业名称:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">供货/经销商自编号:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">供货/经销商名称:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">营业执照注册号:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">供/销:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">店铺地址:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">邮政编码:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">电子邮箱:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">联系人:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">联系电话:</th><th id="row13" class="class_td5"></th></tr>
		<tr><th class="class_td1">移动电话:</th><th id="row14" class="class_td2"></th>
			<th class="class_td4">经营状态:</th><th id="row15" class="class_td5"></th></tr>
		<tr><th class="class_td1">备用:</th><th id="row16" class="class_td2"></th>
			<th class="class_td4">最后修改时间:</th><th id="row17" class="class_td5"></th></tr>
		<tr><th class="class_td1">删除标记:</th><th id="row18" class="class_td2" colspan="3"></th>
			</tr>
	</table>
</div>
</body>
</html>