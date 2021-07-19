<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>合同签订履行信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="230" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="合同签订履行信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='curcomexecutiondesid' text="合同签订履行情况正式表id" visible="false" textalign="center"  width="10%"></div>
				<div name='curcompactcreditid' text="守合同重信息用正式表id" visible="false" textalign="center"  width="10%"></div>
				<div name='sourceflag' text="SOURCEFLAG" visible="false" textalign="center"  width="10%"></div>
			<div name='pretransfercount' text="上年结转合同份数"  textalign="center"  width="10%"></div>
			<div name='pretransfermoney' text="上年结转合同金额" textalign="center"  width="10%"></div>
			<div name='cursigncount' text="本年度签订合同份数" textalign="center"  width="10%"></div>
			<div name='cursignmoney' text="本年度签订合同金额" textalign="center"  width="10%"></div>
			<div name='alreadyexecutecount' text="已经履行合同份数" textalign="center"  width="10%"></div>
			<div name='alreadyexecutecountpercent' text="已经履行合同数百分比" textalign="center"  width="10%"></div>
			<div name='alreadyexecutemoney' text="已经履行合同金额" textalign="center"  width="10%"></div>
			<div name='alreadyexecutemoneypercent' text="已经履行合同金额百分比" textalign="center"  width="10%"></div>
			<div name='curexecutecount' text="正在履行合同份数"  textalign="center"  width="10%"></div>
			<div name='curexecutecountpercent' text="正在履行合同份数百分比"  textalign="center"  width="10%"></div>
			<div name='curexecutemoney' text="正在履行合同金额" visible="false" textalign="center"  width="10%"></div>
			<div name='curexecutemoneypercent' text="正在履行合同金额百分比" visible="false" textalign="center"  width="10%"></div>
			<div name='suddencomcount' text="不可抗力合同份数" visible="false" textalign="center"  width="10%"></div>
			<div name='suddencommoney' text="不可抗力合同金额" visible="false" textalign="center"  width="10%"></div>
			<div name='partnerbreakcomcount' text="对方违约份数" visible="false" textalign="center"  width="10%"></div>
			<div name='partnerbreakcommoney' text="对方违约金额" visible="false" textalign="center"  width="10%"></div>
			<div name='selfbreakcomcount' text="自身违约份数" visible="false" textalign="center"  width="10%"></div>
			<div name='selfbreakcommoney' text="自身违约金额" visible="false" textalign="center"  width="10%"></div>
			<div name='invalidcomcount' text="无效合同份数" visible="false" textalign="center"  width="10%"></div>
			<div name='invalidcommoney' text="无效合同金额" visible="false" textalign="center"  width="10%"></div>
			<div name='breakillegalcomcount' text="违法合同份数" visible="false" textalign="center"  width="10%"></div>
			<div name='breakillegalcommoney' text="违法合同金额" visible="false" textalign="center"  width="10%"></div>
			<div name='unchaincomcount' text="协商解除合同份数" visible="false" textalign="center"  width="10%"></div>
			<div name='unchaincommoney' text="协商解除合同金额" visible="false" textalign="center"  width="10%"></div>
			<div name='changecomcount' text="依法变更合同份数" visible="false" textalign="center"  width="10%"></div>
			<div name='changecommoney' text="依法变更合同金额" visible="false" textalign="center"  width="10%"></div>
				<div name='timestamp' text="TIMESTAMP" visible="false" textalign="center"  width="10%"></div>						
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">上年结转合同份数:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">上年结转合同金额:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">本年度签订合同份数:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">本年度签订合同金额:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">已经履行合同份数:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">已经履行合同数百分比:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">已经履行合同金额:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">已经履行合同金额百分比:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">正在履行合同份数:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">正在履行合同份数百分比:</th><th id="row13" class="class_td5"></th></tr>
		<tr><th class="class_td1">正在履行合同金额:</th><th id="row14" class="class_td2"></th>
			<th class="class_td4">正在履行合同金额百分比:</th><th id="row15" class="class_td5"></th></tr>
		<tr><th class="class_td1">不可抗力合同份数:</th><th id="row16" class="class_td2"></th>
			<th class="class_td4">不可抗力合同金额:</th><th id="row17" class="class_td5"></th></tr>
		<tr><th class="class_td1">对方违约份数:</th><th id="row18" class="class_td2"></th>
			<th class="class_td4">对方违约金额:</th><th id="row19" class="class_td5"></th></tr>
		<tr><th class="class_td1">自身违约份数:</th><th id="row20" class="class_td2"></th>
			<th class="class_td4">自身违约金额:</th><th id="row21" class="class_td5"></th></tr>
		<tr><th class="class_td1">无效合同份数:</th><th id="row22" class="class_td2"></th>
			<th class="class_td4">无效合同金额:</th><th id="row23" class="class_td5"></th></tr>
		<tr><th class="class_td1">违法合同份数:</th><th id="row24" class="class_td2"></th>
			<th class="class_td4">违法合同金额:</th><th id="row25" class="class_td5"></th></tr>	
		<tr><th class="class_td1">协商解除合同份数:</th><th id="row26" class="class_td2"></th>
			<th class="class_td4">协商解除合同金额:</th><th id="row27" class="class_td5"></th></tr>
		<tr><th class="class_td1">依法变更合同份数:</th><th id="row28" class="class_td2"></th>
			<th class="class_td4">依法变更合同金额:</th><th id="row29" class="class_td5"></th></tr>
	</table>
</div>
</body>
</html>