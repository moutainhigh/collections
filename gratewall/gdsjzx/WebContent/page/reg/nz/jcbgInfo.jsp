<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>检测报告信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="230" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="检测报告信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='checkreportid' text="检验报告标识" visible="false" textalign="center"  width="10%"></div>
				<div name='sampleid' text="抽样标识" visible="false" textalign="center"  width="10%"></div>
				<div name='fsprojectid' text="方案标识" visible="false" textalign="center"  width="10%"></div>
			<div name='checktype' text="检验类型" textalign="center"  width="15%"></div>
			<div name='inspection_time' text="抽检时间" textalign="center"  width="15%"></div>
			<div name='inspection_agency' text="检验机构" textalign="center"  width="20%"></div>
				<div name='checkrepcode' text="检验报告编号" visible="false" textalign="center"  width="10%"></div>
			<div name='inspection_conclusion' text="检查结论" textalign="center"  width="20%"></div>
			<div name='disqualified_item_summary' text="不合格项目"  textalign="center"  width="14%"></div>
			<div name='picfile' text="检验报告" textalign="center"  width="15%"></div>
			<div name='sendingdate' text="报告寄出时间" visible="false" textalign="center"  width="10%"></div>
			<div name='signingdate' text="报告送达时间" visible="false" textalign="center"  width="10%"></div>
			<div name='lastnoticeday' text="截止确认期限" visible="false" textalign="center"  width="10%"></div>
			<div name='receiver' text="签收人" visible="false" textalign="center"  width="10%"></div>
			<div name='tel' text="联系电话" visible="false" textalign="center"  width="10%"></div>
			<div name='confirmresult' text="检验结论确认" visible="false" textalign="center"  width="10%"></div>
			<div name='applyrecheck' text="申请复查情况" visible="false" textalign="center"  width="10%"></div>
			<div name='remark' text="备注" visible="false" textalign="center"  width="10%"></div>
			<div name='busst' text="经营状态" visible="false" textalign="center"  width="10%"></div>
			<div name='operpriname' text="经营者名称" visible="false" textalign="center"  width="10%"></div>
			<div name='fcflag' text="初检，复检" visible="false" textalign="center"  width="10%"></div>
			<div name='completedate' text="检验完成日期" visible="false" textalign="center"  width="10%"></div>
			<div name='standardjudge' text="明示标准判定" visible="false" textalign="center"  width="10%"></div>
			<div name='accforstan' text="符合强制标准" visible="false" textalign="center"  width="10%"></div>
			<div name='quaprojudge' text="质量承诺判定" visible="false" textalign="center"  width="10%"></div>
			<div name='determine' text="综合判定" visible="false" textalign="center"  width="10%"></div>
			<div name='procomaffirm' text="生产企业监测结果确认" visible="false" textalign="center"  width="10%"></div>
			<div name='checomaffirm' text="被监测企业监测结果确认" visible="false" textalign="center"  width="10%"></div>
			<div name='sampleno' text="抽样编号" visible="false" textalign="center"  width="10%"></div>
			<div name='sertime' text="送达时间" visible="false" textalign="center"  width="10%"></div>
			<div name='checomapprecheck' text="被监测企业申请复检情况" visible="false" textalign="center"  width="10%"></div>
			<div name='range' text="不合格商品发布范围" visible="false" textalign="center"  width="10%"></div>
			<div name='checkstandard' text="检验标准" visible="false" textalign="center"  width="10%"></div>
			<div name='amsreport' text="是否保存变更记录" visible="false" textalign="center"  width="10%"></div>
				<div name='mssreport' text="mssreport" visible="false" textalign="center"  width="10%"></div>
				<div name='organ' text="组织" visible="false" textalign="center"  width="10%"></div>
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
		<tr><th class="class_td1">检验类型:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">抽检时间:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">检验机构:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">检验报告编号:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">检查结论:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">不合格项目:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">检验报告:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">报告寄出时间:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">报告送达时间:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">截止确认期限:</th><th id="row13" class="class_td5"></th></tr>
		<tr><th class="class_td1">签收人:</th><th id="row14" class="class_td2"></th>
			<th class="class_td4">联系电话:</th><th id="row15" class="class_td5"></th></tr>
		<tr><th class="class_td1">检验结论确认:</th><th id="row16" class="class_td2"></th>
			<th class="class_td4">申请复查情况:</th><th id="row17" class="class_td5"></th></tr>
		<tr><th class="class_td1">备注:</th><th id="row18" class="class_td2"></th>
			<th class="class_td4">经营状态:</th><th id="row19" class="class_td5"></th></tr>
		<tr><th class="class_td1">经营者名称:</th><th id="row20" class="class_td2"></th>
			<th class="class_td4">初检，复检:</th><th id="row21" class="class_td5"></th></tr>
		<tr><th class="class_td1">检验完成日期:</th><th id="row22" class="class_td2"></th>
			<th class="class_td4">明示标准判定:</th><th id="row23" class="class_td5"></th></tr>
		<tr><th class="class_td1">符合强制标准:</th><th id="row24" class="class_td2"></th>
			<th class="class_td4">质量承诺判定:</th><th id="row25" class="class_td5"></th></tr>
		<tr><th class="class_td1">综合判定:</th><th id="row26" class="class_td2"></th>
			<th class="class_td4">生产企业监测结果确认:</th><th id="row27" class="class_td5"></th></tr>
		<tr><th class="class_td1">被监测企业监测结果确认:</th><th id="row28" class="class_td2"></th>
			<th class="class_td4">抽样编号:</th><th id="row29" class="class_td5"></th></tr>
		<tr><th class="class_td1">送达时间:</th><th id="row30" class="class_td2"></th>
			<th class="class_td4">被监测企业申请复检情况:</th><th id="row31" class="class_td5"></th></tr>
		<tr><th class="class_td1">不合格商品发布范围:</th><th id="row32" class="class_td2"></th>
			<th class="class_td4">检验标准:</th><th id="row33" class="class_td5"></th></tr>
		<tr><th class="class_td1">是否保存变更记录:</th><th id="row34" class="class_td2" colspan="3"></th>
			</tr>
	</table>
</div>
</body>
</html>