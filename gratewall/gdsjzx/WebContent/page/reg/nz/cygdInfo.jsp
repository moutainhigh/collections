<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>抽样工单信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="230" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="抽样工单信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='samplebillid' text="抽样工单id" visible="false" textalign="center"  width="10%"></div>
				<div name='execid' text="实施方案id" visible="false" textalign="center"  width="10%"></div>
				<div name='basemerchandiseid' text="被抽检商品id" visible="false" textalign="center"  width="10%"></div>
			<div name='regno' text="注册号" textalign="center"  width="15%"></div>
			<div name='product_name' text="商品名称" textalign="center"  width="20%"></div>
			<div name='brand_name' text="品牌名称" textalign="center"  width="20%"></div>
			<div name='samdate' text="抽样日期" textalign="center"  width="15%"></div>
			<div name='samplenumber' text="抽样基数" textalign="center"  width="15%"></div>
			<div name='confirmday' text="确认时间" textalign="center"  width="14%"></div>
			<div name='sampleno' text="抽样工单编号" visible="false" textalign="center"  width="10%"></div>
			<div name='samplesite' text="监测场所" visible="false" textalign="center"  width="10%"></div>
			<div name='sampleplace' text="监测地点" visible="false" textalign="center"  width="10%"></div>
			<div name='affideg' text="确认程度" visible="false" textalign="center"  width="10%"></div>
			<div name='noticeday' text="通知厂家时间" visible="false" textalign="center"  width="10%"></div>
			<div name='noticetype' text="通知方式" visible="false" textalign="center"  width="10%"></div>
			<div name='comment' text="通知情况" visible="false" textalign="center"  width="10%"></div>
			<div name='lastnoticeday' text="截止确认期限" visible="false" textalign="center"  width="10%"></div>
			<div name='linkman' text="联系人" visible="false" textalign="center"  width="10%"></div>
			<div name='tel' text="联系电话" visible="false" textalign="center"  width="10%"></div>
			<div name='country' text="国别(地区)" visible="false" textalign="center"  width="10%"></div>
			<div name='locprov' text="所在省" visible="false" textalign="center"  width="10%"></div>
			<div name='sampling_city' text="被抽检单位地市" visible="false" textalign="center"  width="10%"></div>
			<div name='procomcounty' text="生产企业所在县（区）" visible="false" textalign="center"  width="10%"></div>
			<div name='procomtown' text="生产企业所在镇" visible="false" textalign="center"  width="10%"></div>
			<div name='production_telephone' text="生产单位固定电话" visible="false" textalign="center"  width="10%"></div>
			<div name='procomfax' text="生产企业传真" visible="false" textalign="center"  width="10%"></div>
			<div name='email' text="电子邮箱" visible="false" textalign="center"  width="10%"></div>
			<div name='addr' text="企业通信地址" visible="false" textalign="center"  width="10%"></div>
			<div name='execstandard' text="执行标准" visible="false" textalign="center"  width="10%"></div>
			<div name='qualitygrade' text="质量等级" visible="false" textalign="center"  width="10%"></div>
			<div name='production_date' text="生产日期" visible="false" textalign="center"  width="10%"></div>
			<div name='productsno' text="生产批号" visible="false" textalign="center"  width="10%"></div>
			<div name='categoryid' text="经营商品类别" visible="false" textalign="center"  width="10%"></div>
			<div name='sampling_address' text="被抽检单位地址" visible="false" textalign="center"  width="10%"></div>
			<div name='remark' text="备注" visible="false" textalign="center"  width="10%"></div>
			<div name='organ' text="组织" visible="false" textalign="center"  width="10%"></div>
			<div name='production_contacts' text="生产单位联系人" visible="false" textalign="center"  width="10%"></div>
			<div name='amsreport' text="是否保存变更记录" visible="false" textalign="center"  width="10%"></div>
			<div name='productmodel' text="标明量" visible="false" textalign="center"  width="10%"></div>
			<div name='scprojectid' text="备用" visible="false" textalign="center"  width="10%"></div>
			<div name='isdeleted' text="删除标记" visible="false" textalign="center"  width="10%"></div>
				
				<div name='fikeyid' text="业务标识" visible="false" textalign="center"  width="10%"></div>
				<div name='fsreceiver' text="备用" visible="false" textalign="center"  width="10%"></div>
				<div name='istocase' text="备用" visible="false" textalign="center"  width="10%"></div>
				<div name='sourceflag' text="SOURCEFLAG" visible="false" textalign="center"  width="10%"></div>
				<div name='timestamp' text="TIMESTAMP" visible="false" textalign="center"  width="10%"></div>
				<div name='pripid' text="主体身份代码" visible="false" textalign="center"  width="10%"></div>			
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">注册号:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">商品名称:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">品牌名称:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">抽样日期:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">抽样基数:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">确认时间:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">抽样工单编号:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">监测场所:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">监测地点:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">确认程度:</th><th id="row13" class="class_td5"></th></tr>
		<tr><th class="class_td1">通知厂家时间:</th><th id="row14" class="class_td2"></th>
			<th class="class_td4">通知方式:</th><th id="row15" class="class_td5"></th></tr>
		<tr><th class="class_td1">通知情况:</th><th id="row16" class="class_td2"></th>
			<th class="class_td4">截止确认期限:</th><th id="row17" class="class_td5"></th></tr>
				
		<tr><th class="class_td1">联系人:</th><th id="row18" class="class_td2"></th>
			<th class="class_td4">联系电话:</th><th id="row19" class="class_td5"></th></tr>
		<tr><th class="class_td1">国别(地区):</th><th id="row20" class="class_td2"></th>
			<th class="class_td4">所在省:</th><th id="row21" class="class_td5"></th></tr>
		<tr><th class="class_td1">被抽检单位地市:</th><th id="row22" class="class_td2"></th>
			<th class="class_td4">生产企业所在县（区）:</th><th id="row23" class="class_td5"></th></tr>
		<tr><th class="class_td1">生产企业所在镇:</th><th id="row24" class="class_td2"></th>
			<th class="class_td4">生产单位固定电话:</th><th id="row25" class="class_td5"></th></tr>
		<tr><th class="class_td1">生产企业传真:</th><th id="row26" class="class_td2"></th>
			<th class="class_td4">电子邮箱:</th><th id="row27" class="class_td5"></th></tr>
		<tr><th class="class_td1">企业通信地址:</th><th id="row28" class="class_td2"></th>
			<th class="class_td4">执行标准:</th><th id="row29" class="class_td5"></th></tr>
		<tr><th class="class_td1">质量等级:</th><th id="row30" class="class_td2"></th>
			<th class="class_td4">生产日期:</th><th id="row31" class="class_td5"></th></tr>
		<tr><th class="class_td1">生产批号:</th><th id="row32" class="class_td2"></th>
			<th class="class_td4">经营商品类别:</th><th id="row33" class="class_td5"></th></tr>
		<tr><th class="class_td1">被抽检单位地址:</th><th id="row34" class="class_td2"></th>
			<th class="class_td4">备注:</th><th id="row35" class="class_td5"></th></tr>
		<tr><th class="class_td1">组织:</th><th id="row36" class="class_td2"></th>
			<th class="class_td4">生产单位联系人:</th><th id="row37" class="class_td5"></th></tr>
		<tr><th class="class_td1">是否保存变更记录:</th><th id="row38" class="class_td2"></th>
			<th class="class_td4">标明量:</th><th id="row39" class="class_td5"></th></tr>
		<tr><th class="class_td1">备用:</th><th id="row40" class="class_td2"></th>
			<th class="class_td4">删除标记:</th><th id="row41" class="class_td5"></th></tr>
	</table>
</div>
</body>
</html>