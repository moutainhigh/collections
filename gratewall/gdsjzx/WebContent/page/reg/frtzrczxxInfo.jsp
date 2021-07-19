<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>投资人出资信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sczt/sczt.js"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>

<script type="text/javascript">
function rowclick(event,data){
}
		
function renderdata(event, obj){
	var regtype=getUrlParam("regtype");
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var conam = data[i]["conam"];
		var subconam = data[i]["subconam"];
		var acconam = data[i]["acconam"];
		var conprop = data[i]["conprop"];
		var invtype = data[i]["invtype"];
	//	var commitment = data[0]["commitment"];
	//	console.info($('#hiddenSum',window.parent.document));
		// $(window.parent.frames["main"].document).find('#hiddEnttype').val();
		var commitment=parent.getSum();
	
		//	alert(commitment);
		if(commitment==undefined){
			commitment=0;
		}else{
		commitment=commitment.replace("(万元[人民币])","");
		};
		if(regtype=='gt'){
			if(conam!=undefined){
				data[i]["conam"] = conam+'万元';
			}
			if(subconam!=undefined){
				data[i]["subconam"] = subconam+'万元';
			}
			if(acconam!=undefined){
				data[i]["acconam"] = acconam+'万元';
			}
			if(acconam==null){
				data[i]["conprop"] = '0%';
			}else{
				data[i]["conprop"] = '100%';
			}
			if(invtype==1){
				data[i]["tzrype"] = '法人';
			}else if(invtype==20){
				data[i]["tzrtype"] = '自然人';
			}
		} else {	
			if(conam!=undefined){
				data[i]["conam"] = conam+'万元';
			}
			if(subconam!=undefined){
				data[i]["subconam"] = subconam+'万元';
			}
			if(acconam!=undefined){
				data[i]["acconam"] = acconam+'万元';
			}
			
			if(acconam==undefined){
				acconam = 0;
			}
			//alert(acconam/(parseInt(commitment)/100));
			if(acconam%(parseInt(commitment)/100)==0){
				conprop=(acconam/(parseInt(commitment)/100));
			}else{
				//未测试
		 		var reg = new RegExp("^[1-9]+(.[1-9]{1,3})?$");  
	    		if(reg.test(conprop)){ 
        			conprop=(acconam/(parseInt(commitment)/100)).toFixed(2);
        		}else{
        			conprop=(acconam/(parseInt(commitment)/100)).toFixed(2);
        		}
			}
			
			data[i]["conprop"] = conprop+'%';
			if(invtype==1){
				data[i]["tzrtype"] = '法人';
			}else if(invtype==20){
				data[i]["tzrtype"] = '自然人';
			}
		} 
	}
	return data;
}
</script>
			 
				 
</head>
<body >
<div vtype="gridpanel" name="jbxxGrid" height="236" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()"
			selecttype="1"	titledisplay="true" title="投资人出资信息" showborder="false" isshowselecthelper="false"  selecttype="2">
	<!-- 表头 -->
	<div vtype="gridcolumn" name="grid_column" width="100%">
		<div> 
		<!--法人投资人信息 自然人没有证照编号和证照类型 法人-->
			<div name='regno' text="注册号" textalign="center" ></div>
			<!-- <div name='exclusiveid' text="股东编号唯一标志" textalign="center"></div> -->
			
			<div name='inv' text="投资人" textalign="center"  width="22%"></div>
			<div name='tzrtype' text="投资人类型" textalign="center"  width="13%"></div>
			
			<div name='cerno' text="证件号码" textalign="center"></div>
			<div name='certype' text="证件类型" textalign="center"></div>
			
			<div name='blicno' text="证照编号" textalign="center"></div>
			<div name='blictype' text="证照类型" textalign="center" ></div>
			
		<div name='subconam' text="认缴出资额" textalign="center"  width="15%"></div>
		<div name='acconam' text="实缴出资额" textalign="center"  width="15%"></div>
		<!-- <div name='conam' text="出资额" textalign="center" width="10%"></div> -->
			<div name='baldelper' text="余额缴付期限" textalign="center" ></div>
		<div name='conprop' text="出资比例" visible="false" textalign="center"  width="10%"></div>
		<div name='conform' text="出资方式" textalign="center"  width="10%"></div>
			<div name='subcondata' text="认缴出资日期" textalign="center"></div>
		<div name='acconbegdate' text="实缴出资日期" textalign="center"  width="15%"></div>
			<div name='lerep' text="法定代表人" textalign="center"></div>
			<div name='excutor' text="执行人" textalign="center"></div>
			<div name='regtype' text="登记类型" textalign="center"></div>
			<div name='grpmemtype' text="集团成员类型" textalign="center"></div>
			<div name='takedutytype' text="承担责任方式" textalign="center"></div>
			<div name='verifiorg' text="验资机构" textalign="center" ></div>
		<!--法人投资人其他信息 -->
		<div name='currency' text="币种" textalign="center"  width="10%"></div>
			<div name='exchangerate' text="汇率" textalign="center"></div>
			<div name='country' text="国别(地区)" textalign="center"></div>
			<div name='dom' text="住所" textalign="center"></div>
			<div name='cooperatecondition' text="合作条件" textalign="center"></div>
			<div name='isfarmer' text="成员情况" textalign="center"></div>
			<div name='fronum' text="冻结数目" textalign="center"></div>
			<div name='beforefronum' text="冻结前数目" textalign="center"></div>
			<div name='stocknum' text="持股份数" textalign="center"></div>
			
			<div name='isofficiateaffair' text="是否执行合伙事务字段" textalign="center"></div>
			<div name='isoriginateperson' text="是否发起人" textalign="center"></div>
			<div name='isexecutor' text="是否执行者" textalign="center"></div>
			<div name='isselectsettlemonth' text="是否选择了余额缴清按月计算" textalign="center"></div>
			<div name='isouter' text="是否外地企业" textalign="center"></div>
		
	<!-- <div name='invid' text="投资人身份标识" textalign="center"></div>
	<div name='tzrtype' text="投资人类别" textalign="center" ></div>
	<div name='historyinfoid' text="历史编号" textalign="center"></div>
	<div name='biz_historyinfoid' text="父企业或者子企业的历史编号" textalign="center"></div>
 -->	</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
  	<div id="formpanel" class="formpanel_table">
		<div class="font_title">详细信息</div>
			<table id="datashow" class="font_table">
				<tr class="class_hg"><th class="class_td1">注册号:</th><th class="class_td2" id="row1" colspan="3"></th></tr>
				<tr><th class="class_td1">投资人:</th><th class="class_td2" id="row2"/><th class="class_td4">投资人类型:</th><th class="class_td5" id="row3"></th></tr>
				<!-- <tr><th class="class_td1">证照编号:</th><th class="class_td2" id="row6"/><th class="class_td4">证照类型:</th><th class="class_td5" id="row7"></th></tr> -->
				<tr><th class="class_td1">认缴出资额:</th><th class="class_td2" id="row8"/><th class="class_td4">实缴出资额:</th><th class="class_td5" id="row9"></th></tr>
				<tr><th class="class_td1">余额缴付期限:</th><th class="class_td2" id="row10" colspan="3"/></tr>
				<tr><th class="class_td1">出资方式:</th><th class="class_td2" id="row12"/><th class="class_td4">认缴出资日期:</th><th class="class_td5" id="row13"></th></tr>
				<tr><th class="class_td1">实缴出资日期:</th><th class="class_td2" id="row14"/><th class="class_td4">法定代表人:</th><th class="class_td5" id="row15"></th></tr>
				<tr><th class="class_td1">执行人:</th><th class="class_td2" id="row16"/><th class="class_td4">登记类型:</th><th class="class_td5" id="row17"></th></tr>
				<tr><th class="class_td1">集团成员类型:</th><th class="class_td2" id="row18"/><th class="class_td4">承担责任方式:</th><th class="class_td5" id="row19"></th></tr>
				<tr><th class="class_td1">验资机构:</th><th class="class_td2" id="row20"/><th class="class_td4">币种:</th><th class="class_td5" id="row21"></th></tr>
				<tr><th class="class_td1">汇率:</th><th class="class_td2" id="row22"/><th class="class_td4">国别(地区):</th><th class="class_td5" id="row23"/></tr>
				<tr><th class="class_td1">住所:</th><th class="class_td2" id="row24"/><th class="class_td4">合作条件:</th><th class="class_td5" id="row25"></th></tr>
				<tr><th class="class_td1">成员情况:</th><th class="class_td2" id="row26"/><th class="class_td4">冻结数目:</th><th class="class_td5" id="row27"></th></tr>
				<tr><th class="class_td1">冻结前数目:</th><th class="class_td2" id="row28"/><th class="class_td4">持股份数:</th><th class="class_td5" id="row29"></th></tr>
				<tr><th class="class_td1">是否执行合伙事务字段:</th><th class="class_td2" id="row30"/><th class="class_td4">是否发起人:</th><th class="class_td5" id="row31"></th></tr>
				<tr><th class="class_td1">是否执行者:</th><th class="class_td2" id="row32"/><th class="class_td4">是否选择了余额缴清按月计算:</th><th class="class_td5" id="row33"></th></tr>
				<tr><th class="class_td1">是否外地企业:</th><th class="class_td2" id="row34"/><th class="class_td4"></th><th class="class_td5" id="row351"></th></tr>
			</table>
	</div>
</body>
</html>