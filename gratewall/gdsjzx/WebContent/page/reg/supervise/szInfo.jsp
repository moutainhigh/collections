<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>守重信息</title>
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
<div vtype="gridpanel" name="jbxxGrid" height="230" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="守重信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='curcompactcreditid' text="守合同重信息用正式表id" visible="false" textalign="center"  width="10%"></div>
				<div name='bizsequence' text="业务id" visible="false" textalign="center"  width="10%"></div>
				<div name='bizcompactcreditid' text="守合同重信息用业务表id" visible="false" textalign="center"  width="10%"></div>
			<div name='corpname' text="企业名称"  textalign="left"  width="25%"></div>
			<div name='registerno' text="注册号"  textalign="left"  width="20%"></div>
			<div name='comcrelevelid' text="认定级别"  textalign="center"  width="10%"></div>
			<div name='comcreyear' text="认定年度"  textalign="center"  width="10%"></div>
			<div name='entitytypeid' text="企业类型" visible="false" textalign="center"  width="10%"></div>
			<div name='principal' text="负责人姓名"  textalign="center"  width="15%"></div>
			<div name='address' text="地址" visible="false" textalign="center"  width="10%"></div>
			<div name='postcode' text="邮政编码" visible="false" textalign="center"  width="10%"></div>
			<div name='phone' text="电话" visible="false" textalign="center"  width="10%"></div>
			<div name='mobilephone' text="手机" visible="false" textalign="center"  width="10%"></div>
			<div name='chargedep' text="管理部门" visible="false" textalign="center"  width="10%"></div>
			<div name='economicproperty' text="企业经济性质" visible="false" textalign="center"  width="10%"></div>
			<div name='regcapital' text="注册资本" visible="false" textalign="center"  width="10%"></div>
			<div name='regcapitalcoin' text="注册币种" visible="false" textalign="center"  width="10%"></div>
			<div name='employeenum' text="从业人数" visible="false" textalign="center"  width="10%"></div>
			<div name='approvetypeid' text="核准方式" visible="false" textalign="center"  width="10%"></div>
			<div name='entityurl' text="企业网址" visible="false" textalign="center"  width="10%"></div>
			<div name='corporgcode' text="组织代码证号码" visible="false" textalign="center"  width="10%"></div>
			<div name='countrytaxno' text="国税登记证号码" visible="false" textalign="center"  width="10%"></div>
			<div name='regiontaxno' text="地税登记证号码" visible="false" textalign="center"  width="10%"></div>
			<div name='businessscope' text="业务范围" visible="false" textalign="center"  width="10%"></div>
			<div name='issuedeptid' text="发照机关" visible="false" textalign="center"  width="10%"></div>
			<div name='aicid' text="登记管辖机关" visible="false" textalign="center"  width="10%"></div>
			<div name='firstcomcreyear' text="初次获得守合同重信用企业称号的年份" visible="false" textalign="center"  width="10%"></div>
			<div name='continuecomcrenum' text="连续获得守合同重信用企业称号的年数" visible="false" textalign="center"  width="10%"></div>
			<div name='continuecomcreyear' text="连续获得守合同重信用企业称号的年份" visible="false" textalign="center"  width="10%"></div>
			<div name='approvedeptid' text="认定机关"  textalign="center"  width="10%"></div>
			<div name='acceptdeptid' text="认定受理机关"  textalign="center"  width="10%"></div>
			<div name='entityno' text="企业唯一号" visible="false" textalign="center"  width="10%"></div>
			<div name='statusid' text="守重状态" visible="false" textalign="center"  width="10%"></div>
			<div name='islocation' text="是否本地库企业" visible="false" textalign="center"  width="10%"></div>
			<div name='approvedate' text="核准日期" visible="false" textalign="center"  width="10%"></div>
			<div name='applydate' text="受理日期" visible="false" textalign="center"  width="10%"></div>
			<div name='othercredit' text="其他信用资料" visible="false" textalign="center"  width="10%"></div>
			<div name='recommendunit' text="推荐单位" visible="false" textalign="center"  width="10%"></div>
			<div name='submitname' text="申报人名字" visible="false" textalign="center"  width="10%"></div>
			<div name='submitdate' text="申报日期" visible="false" textalign="center"  width="10%"></div>
			<div name='iswebsubmit' text="是否网上申报" visible="false" textalign="center"  width="10%"></div>
			<div name='isdomedal' text="是否制作本年公示铜牌" visible="false" textalign="center"  width="10%"></div>
			<div name='isrundomedal' text="是否制作连续公示铜牌" visible="false" textalign="center"  width="10%"></div>
			<div name='sourceflag' text="变" visible="false" textalign="center"  width="10%"></div>
			<div name='timestamp' text="变" visible="false" textalign="center"  width="10%"></div>
	</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">企业名称:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">注册号:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">认定级别:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">认定年度:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">企业类型:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">负责人姓名:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">地址:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">邮政编码:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">电话:</th><th id="row12" class="class_td2"></th>
			<th class="class_td4">手机:</th><th id="row13" class="class_td5"></th></tr>
		<tr><th class="class_td1">管理部门:</th><th id="row14" class="class_td2"></th>
			<th class="class_td4">企业经济性质:</th><th id="row15" class="class_td5"></th></tr>
		<tr><th class="class_td1">注册资本:</th><th id="row16" class="class_td2"></th>
			<th class="class_td4">注册币种:</th><th id="row17" class="class_td5"></th></tr>
			
		<tr><th class="class_td1">从业人数:</th><th id="row18" class="class_td2"></th>
			<th class="class_td4">核准方式:</th><th id="row19" class="class_td5"></th></tr>
		<tr><th class="class_td1">企业网址:</th><th id="row20" class="class_td2"></th>
			<th class="class_td4">组织代码证号码:</th><th id="row21" class="class_td5"></th></tr>
		<tr><th class="class_td1">国税登记证号码:</th><th id="row22" class="class_td2"></th>
			<th class="class_td4">地税登记证号码:</th><th id="row23" class="class_td5"></th></tr>
		<tr><th class="class_td1">业务范围:</th><th id="row24" class="class_td2"></th>
			<th class="class_td4">发照机关:</th><th id="row25" class="class_td5"></th></tr>	
			
		<tr><th class="class_td1">登记管辖机关:</th><th id="row26" class="class_td2"></th>
			<th class="class_td4">初次获得守合同重信用企业称号的年份:</th><th id="row27" class="class_td5"></th></tr>
		<tr><th class="class_td1">连续获得守合同重信用企业称号的年数:</th><th id="row28" class="class_td2"></th>
			<th class="class_td4">连续获得守合同重信用企业称号的年份:</th><th id="row29" class="class_td5"></th></tr>
		<tr><th class="class_td1">认定机关:</th><th id="row30" class="class_td2"></th>
			<th class="class_td4">认定受理机关:</th><th id="row31" class="class_td5"></th></tr>
		<tr><th class="class_td1">企业唯一号:</th><th id="row32" class="class_td2"></th>
			<th class="class_td4">守重状态:</th><th id="row33" class="class_td5"></th></tr>
			
		<tr><th class="class_td1">是否本地库企业:</th><th id="row34" class="class_td2"></th>
			<th class="class_td4">核准日期:</th><th id="row35" class="class_td5"></th></tr>
		<tr><th class="class_td1">受理日期:</th><th id="row36" class="class_td2"></th>
			<th class="class_td4">其他信用资料:</th><th id="row37" class="class_td5"></th></tr>
		<tr><th class="class_td1">推荐单位:</th><th id="row38" class="class_td2"></th>
			<th class="class_td4">申报人名字:</th><th id="row39" class="class_td5"></th></tr>
		<tr><th class="class_td1">申报日期:</th><th id="row40" class="class_td2"></th>
			<th class="class_td4">是否网上申报:</th><th id="row41" class="class_td5"></th></tr>	
		<tr><th class="class_td1">是否制作本年公示铜牌:</th><th id="row42" class="class_td2"></th>
			<th class="class_td4">是否制作连续公示铜牌:</th><th id="row43" class="class_td5"></th></tr>	
	</table>
</div>
</body>
</html>