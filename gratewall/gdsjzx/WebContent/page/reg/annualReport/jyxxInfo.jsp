<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>企业经济状况信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/report/yreportTable.js"></script>

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>
<style>
.font_table {
    font-family:'微软雅黑', 'SimSun', 'helvetica', 'arial', 'sans-serif';
    height: 60%;
    width: 100%;
}
</style>
<script type="text/javascript">
	function rowclick(event,data){
	}
	var unit="万元";
	function renderdata(event, obj){
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			var assgro = data[i]["assgro"].toFixed(2);
			var assetsisshow = data[i]["assetsisshow"].toFixed(2);
			var liagro = data[i]["liagro"].toFixed(2);
			var vendinc = data[i]["vendinc"].toFixed(2);
			var progro = data[i]["progro"].toFixed(2);
			var netinc = data[i]["netinc"].toFixed(2);
			var ratgro = data[i]["ratgro"].toFixed(2);
			var taxisshow = data[i]["taxisshow"].toFixed(2);
			var totequ = data[i]["totequ"].toFixed(2);
			var maibusinc = data[i]["maibusinc"].toFixed(2);
			var entname = data[i]["entname"].toFixed(2);
			var department = data[i]["department"].toFixed(2);
			var inverstamount = data[i]["inverstamount"].toFixed(2);
			var regno = data[i]["regno"].toFixed(2);
			
			
			if(taxisshow!=undefined){
			data[i]["taxisshow"]=taxisshow+unit;
			}
			if(ratgro!=undefined){
			data[i]["ratgro"]=ratgro+unit;
			}
			if(netinc!=undefined){
			data[i]["netinc"]=netinc+unit;
			}
			if(progro!=undefined){
			data[i]["progro"]=progro+unit;
			}
			if(vendinc!=undefined){
			data[i]["vendinc"]=vendinc+unit; 
			}
			if(liagro!=undefined){
			data[i]["liagro"]=liagro+unit; 
			}
			if(assetsisshow!=undefined){
			data[i]["assetsisshow"]=assetsisshow+unit;
			}
			if(assgro!=undefined){
			data[i]["assgro"]=assgro+unit;
			}
			}
		return data;
	}
</script>
			 
				 
</head>
<body >
<div vtype="gridpanel" name="jbxxGrid" height="236" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()"
			selecttype="1"	titledisplay="true" title="经营信息" showborder="false" isshowselecthelper="false"  selecttype="2">
	<!-- 表头 -->
	<div vtype="gridcolumn" name="grid_column" width="100%">
		<div> 
		
			<!-- 企业资产状况表 -->
			<div name='assgro' text="资产总额" textalign="center"  width="8%"></div>
			<div name='assetsisshow' text="资产总额是否公示" textalign="center"></div>
			<div name='liagro' text="负债总额" textalign="center"   width="8%"></div>
			<div name='liabilitiesisshow' text="负债总额是否公示" textalign="center" ></div>
			<div name='vendinc' text="销售总额" textalign="center"  width="8%"></div>
			<div name='salesisshow' text="销售总额是否公示" textalign="center"></div>
			<div name='progro' text="利润总额" textalign="center"  width="8%"></div>
			<div name='profitsisshow' text="利润总额是否公示" textalign="center"></div>
			<div name='netinc' text="净利润" textalign="center" width="8%"></div>
			<div name='netprofitisshow' text="净利润是否公示" textalign="center"></div>
			<div name='ratgro' text="纳税总额" textalign="center" width="8%"></div>
			<div name='taxisshow' text="纳税总额是否公示" textalign="center"></div>
			<div name='totequ' text="所有者权益合计" textalign="center"  width="8%"></div>
			<div name='equityisshow' text="所有者权益合计是否公示" textalign="center" ></div>
			<div name='maibusinc' text="营业收入" textalign="center"  width="8%"></div>
			<div name='businessisshow' text="营业收入是否公示" textalign="center" ></div>
		
			
			<!--对外投资信息表 -->
			<div name='entname' text="企业名称" textalign="center"  width="8%"></div>
			<div name='department' text="登记机关" textalign="center" width="10%"></div>
			<div name='inverstamount' text="投入资金" textalign="center" width="8%"></div>
			<div name='regno' text="注册号" textalign="center"  width="10%"></div>
			</div>
		
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>

  	<div id="formpanel" class="formpanel_table">
		<div class="font_title">详细信息</div>
			<table id="datashow" class="font_table">
			</table>
	</div>
	
	
</body>
</html>