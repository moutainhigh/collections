<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>抵押登记信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="98%" id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="抵押登记信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='pledgeid' text="pledgeid" visible="false" textalign="center"  width="10%"></div>
				<div name='historyinfoid' text="historyInfoid" visible="false" textalign="center"  width="10%"></div>
				<div name='bizsequence' text="业务流水号" visible="false" textalign="center"  width="10%"></div>
			<div name='morregcno' text="动产抵押登记书编号" textalign="center"  width="10%"></div>
			<div name='guaname' text="抵押物名称" textalign="center"  width="15%"></div>
			<div name='mortgagor' text="抵押人" textalign="center"  width="20%"></div>
			<div name='contractname' text="抵押合同名称" textalign="center"  width="15%"></div>
			<div name='bargainno' text="主合同编号" textalign="center"  width="15%"></div>
			<div name='bargainname' text="主合同名称" textalign="center"  width="15%"></div>
			<div name='status' text="抵押业务状态" textalign="center"  width="10%"></div>
			
			<div name='approvedate' text="抵押核准日期" visible="false" textalign="center"  width="10%"></div>
			<div name='isforeignplace' text="是否异地抵押" visible="false" textalign="center"  width="10%"></div>
			<div name='reason' text="申请抵押物登记原因" visible="false" textalign="center"  width="10%"></div>
			<div name='totalvalue' text="抵押物总价值" visible="false" textalign="center"  width="10%"></div>
			<div name='owner' text="抵押物权属状况" visible="false" textalign="center"  width="10%"></div>
			<div name='guades' text="抵押物概况" visible="false" textalign="center"  width="10%"></div>
			<div name='priclaseckind' text="被担保的主债权种类" visible="false" textalign="center"  width="10%"></div>
			<div name='pefperto' text="履行期限至" visible="false" textalign="center"  width="10%"></div>
			<div name='scope' text="担保范围" visible="false" textalign="center"  width="10%"></div>
			<div name='applydate' text="抵押申请日期" visible="false" textalign="center"  width="10%"></div>
			<div name='foreignregdep' text="foreignRegDep异地工商局名称" visible="false" textalign="center"  width="10%"></div>
			<div name='supervisedeptid' text="上级部门ID" visible="false" textalign="center"  width="10%"></div>
			<div name='contractno' text="主合同编号" visible="false" textalign="center"  width="10%"></div>
			<div name='totalnum' text="抵押物数量" visible="false" textalign="center"  width="10%"></div>
			<div name='approvecontent' text="核准信息" visible="false" textalign="center"  width="10%"></div>
			<div name='issuedeptid' text="发照机关编号" visible="false" textalign="center"  width="10%"></div>
			<div name='aicid' text="登记管辖机关编号" visible="false" textalign="center"  width="10%"></div>
			<div name='suretymoney' text="合同的被担保数额" visible="false" textalign="center"  width="10%"></div>
			<div name='bargaintype' text="主合同类型" visible="false" textalign="center"  width="10%"></div>
			<div name='unit' text="抵押物数量单位字段" visible="false" textalign="center"  width="10%"></div>
			<div name='contractnotype' text="抵押合同种类字段" visible="false" textalign="center"  width="10%"></div>
			<div name='totalvaluecointype' text="抵押物总价值币种" visible="false" textalign="center"  width="10%"></div>
			<div name='totalvalueinrmb' text="换算成人民币" visible="false" textalign="center"  width="10%"></div>
			<div name='suretymoneycointype' text="担保主债权数额币种" visible="false" textalign="center"  width="10%"></div>
			<div name='suretymoneyinrmb' text="换算成人民币" visible="false" textalign="center"  width="10%"></div>
			<div name='islimited' text="是否海关监管" visible="false" textalign="center"  width="10%"></div>
			<div name='expireshowmode' text="债务人履行债务的期限的表示方式" visible="false" textalign="center"  width="10%"></div>
			<div name='pefperform' text="履行期限自" visible="false" textalign="center"  width="10%"></div>
			<div name='otherbargaintypename' text="其他合同类型名称" visible="false" textalign="center"  width="10%"></div>
			<div name='pmoney' text="被担保债权金额" visible="false" textalign="center"  width="10%"></div>
			<div name='creditbargaintype' text="合同类别" visible="false" textalign="center"  width="10%"></div>
			<div name='remark' text="备注" visible="false" textalign="center"  width="10%"></div>
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
		<tr><th class="class_td1">动产抵押登记书编号:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">抵押物名称:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押人:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">抵押合同名称:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">主合同编号:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">主合同名称:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押核准日期:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">抵押业务状态:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">是否异地抵押:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">申请抵押物登记原因:</th><th id="row13" class="class_td5"></th></tr>
			
		<tr><th class="class_td1">抵押物总价值:</th><th id="row14" class="class_td2"></th>
			<th class="class_td4">抵押物权属状况:</th><th id="row15" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押物概况:</th><th id="row16" class="class_td2"></th>
			<th class="class_td4">被担保的主债权种类:</th><th id="row17" class="class_td5"></th></tr>
		<tr><th class="class_td1">履行期限至:</th><th id="row18" class="class_td2"></th>
			<th class="class_td4">担保范围:</th><th id="row19" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押申请日期:</th><th id="row20" class="class_td2"></th>
			<th class="class_td4">异地工商局名称:</th><th id="row21" class="class_td5"></th></tr>
		<tr><th class="class_td1">上级部门ID:</th><th id="row22" class="class_td2"></th>
			<th class="class_td4">主合同编号:</th><th id="row23" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押物数量:</th><th id="row24" class="class_td2"></th>
			<th class="class_td4">核准信息:</th><th id="row25" class="class_td5"></th></tr>	
		<tr><th class="class_td1">发照机关编号:</th><th id="row26" class="class_td2"></th>
			<th class="class_td4">登记管辖机关编号:</th><th id="row27" class="class_td5"></th></tr>
		<tr><th class="class_td1">合同的被担保数额:</th><th id="row28" class="class_td2"></th>
			<th class="class_td4">主合同类型:</th><th id="row29" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押物数量单位字段:</th><th id="row30" class="class_td2"></th>
			<th class="class_td4">抵押合同种类字段:</th><th id="row31" class="class_td5"></th></tr>
		<tr><th class="class_td1">抵押物总价值"币种":</th><th id="row32" class="class_td2"></th>
			<th class="class_td4">换算成人民币:</th><th id="row33" class="class_td5"></th></tr>
		<tr><th class="class_td1">担保主债权数额"币种":</th><th id="row34" class="class_td2"></th>
			<th class="class_td4">换算成人民币:</th><th id="row35" class="class_td5"></th></tr>
		<tr><th class="class_td1">是否海关监管:</th><th id="row36" class="class_td2"></th>
			<th class="class_td4">债务人履行债务的期限的表示方式:</th><th id="row37" class="class_td5"></th></tr>
		<tr><th class="class_td1">履行期限自:</th><th id="row38" class="class_td2"></th>
			<th class="class_td4">其他合同类型名称:</th><th id="row39" class="class_td5"></th></tr>
		<tr><th class="class_td1">被担保债权金额:</th><th id="row40" class="class_td2"></th>
			<th class="class_td4">合同类别:</th><th id="row41" class="class_td5"></th></tr>	
		<tr><th class="class_td1">备注:</th><th id="row42" class="class_td2" colspan="3"></th>
			</tr>
	</table>
</div>
</body>
</html>