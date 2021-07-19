<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>变更信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="230" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="农资企业信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='sourceflag' text="SOURCEFLAG" visible="false" textalign="center"  width="10%"></div>
				<div name='timestamp' text="TIMESTAMP" visible="false" textalign="center"  width="10%"></div>
				<div name='pripid' text="主体身份代码" visible="false" textalign="center"  width="10%"></div>
			<div name='entname' text="企业(机构)名称" textalign="center"  width="20%"></div>
			<div name='regno' text="注册号" textalign="center"  width="15%"></div>
			<div name='assureorg' text="发证机关" textalign="center"  width="20%"></div>
				<div name='opscope' text="经营范围" visible="false" textalign="center"  width="15%"></div>
			<div name='enttype' text="企业类型" textalign="center"  width="15%"></div>
			<div name='country' text="国别(地区)" textalign="center"  width="10%"></div>
				<div name='aicid' text="经营者所在地区" visible="false" textalign="center"  width="10%"></div>
				<div name='locprov' text="所在省" visible="false" textalign="center"  width="10%"></div>
				<div name='quality_city' text="省、地市" visible="false" textalign="center"  width="10%"></div>
				<div name='county' text="所在县（区）" visible="false" textalign="center"  width="10%"></div>
			<div name='localadm' text="属地监管工商所" textalign="center"  width="19%"></div>
			<div name='addr' text="地址" visible="false" textalign="center"  width="10%"></div>
			<div name='postalcode' text="邮政编码" visible="false" textalign="center"  width="10%"></div>
			<div name='email' text="电子邮箱" visible="false" textalign="center"  width="10%"></div>
			<div name='linkman' text="联系人" visible="false" textalign="center"  width="10%"></div>
			<div name='tel' text="联系电话" visible="false" textalign="center"  width="10%"></div>
			<div name='fax' text="传真" visible="false" textalign="center"  width="10%"></div>
			<div name='mobtel' text="移动电话" visible="false" textalign="center"  width="10%"></div>
			<div name='agrcate' text="所经营农资类别" visible="false" textalign="center"  width="10%"></div>
			<div name='categoryid' text="经营商品类别" visible="false" textalign="center"  width="10%"></div>
			<div name='estdate' text="成立日期" visible="false" textalign="center"  width="10%"></div>
			<div name='opfrom' text="经营期限自" visible="false" textalign="center"  width="10%"></div>
			<div name='opto' text="经营期限至" visible="false" textalign="center"  width="10%"></div>
			<div name='busst' text="经营状态" visible="false" textalign="center"  width="10%"></div>
			<div name='flag' text="外省企业标志" visible="false" textalign="center"  width="10%"></div>
			<div name='areaname' text="地区名称" visible="false" textalign="center"  width="10%"></div>
			<div name='chatype' text="连锁经营类型" visible="false" textalign="center"  width="10%"></div>
				<div name='businessmode' text="备用" visible="false" textalign="center"  width="10%"></div>
				<div name='isfrombiz' text="备用" visible="false" textalign="center"  width="10%"></div>
				<div name='leveltype' text="备用" visible="false" textalign="center"  width="10%"></div>
				<div name='isformbiz' text="备用" visible="false" textalign="center"  width="10%"></div>
				<div name='isdeleted' text="备用" visible="false" textalign="center"  width="10%"></div>
				<div name='area_code' text="备用" visible="false" textalign="center"  width="10%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">企业(机构)名称:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">注册号:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">发证机关:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">经营范围:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">企业类型:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">国别(地区):</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">经营者所在地区:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">所在省:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">省、地市:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">所在县（区）:</th><th id="row13" class="class_td5"></th></tr>
		<tr><th class="class_td1">属地监管工商所:</th><th id="row14" class="class_td2"></th>
			<th class="class_td4">地址:</th><th id="row15" class="class_td5"></th></tr>
		<tr><th class="class_td1">邮政编码:</th><th id="row16" class="class_td2"></th>
			<th class="class_td4">电子邮箱:</th><th id="row17" class="class_td5"></th></tr>
		<tr><th class="class_td1">联系人:</th><th id="row18" class="class_td2"></th>
			<th class="class_td4">联系电话:</th><th id="row19" class="class_td5"></th></tr>
		<tr><th class="class_td1">传真:</th><th id="row20" class="class_td2"></th>
			<th class="class_td4">移动电话:</th><th id="row21" class="class_td5"></th></tr>
		<tr><th class="class_td1">所经营农资类别:</th><th id="row22" class="class_td2"></th>
			<th class="class_td4">经营商品类别:</th><th id="row23" class="class_td5"></th></tr>
		<tr><th class="class_td1">成立日期:</th><th id="row24" class="class_td2"></th>
			<th class="class_td4">经营期限自:</th><th id="row25" class="class_td5"></th></tr>
		<tr><th class="class_td1">经营期限至:</th><th id="row26" class="class_td2"></th>
			<th class="class_td4">经营状态:</th><th id="row27" class="class_td5"></th></tr>
		<tr><th class="class_td1">外省企业标志:</th><th id="row28" class="class_td2"></th>
			<th class="class_td4">地区名称:</th><th id="row29" class="class_td5"></th></tr>
		<tr><th class="class_td1">连锁经营类型:</th><th id="row30" class="class_td2" colspan="3"></th>
			</tr>
	</table>
</div>
</body>
</html>