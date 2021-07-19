<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<%
	String contextpath = request.getContextPath();
%>
<title>投资人出资信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script
	src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js"
	type="text/javascript"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/static/js/sczt/sczt.js"></script>


<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/trs/regDetail.css" />

<script type="text/javascript">
	

	window.onload = function() {
		//altRows('datashow1');
		altRows('datashow2');
	};

	
	function fixColumn(event, obj) {// 维护按钮
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			var htm ='<div class="jazz-grid-cell-inner">'
				+'<a href="javascript:void(0);" title="查看" onclick="viewDataSource(\''+data[i]["invid"]+'\');"><img src="'+rootPath+'/static/images/other/mag.png" width="11px" height="12px" border="0"/></a>'
				+'</div>';
			data[i]["fix"] = htm; 
		}
		return data;
	}
	function viewDataSource(invid){
		var title="详细信息";
		var frameurl=""+'/trsquery/querynzwzchuzixxform.do?invid='+invid;
		createNewWindow(title,frameurl);	
	}
	var win;
	function createNewWindow(title,frameurl){
	    win = top.jazz.widget({ 
	    	  
	        vtype: 'window', 
	        name: 'win', 
	        title: title, 
	        width: 750, 
	        height: 530, 
	        modal:true, 
	        visible: true ,
			showborder : true, //true显示窗体边框    false不显示窗体边
			closestate: false,
			minimizable : true, //是否显示最小化按钮
			titleicon : "<%=contextpath%>/static/images/other/notepad-.png",
			frameurl: rootPath+frameurl
	    }); 
	}
			
	
</script>


</head>
<body>
	<div vtype="gridpanel" name="jbxxGrid" height="236" width="100%"
		id='jbxxGrid' dataloadcomplete="" datarender="fixColumn()"
		selecttype="1" titledisplay="true" title="投资人出资信息" showborder="false"
		isshowselecthelper="false" selecttype="2">
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='invid' text="投资人身份标识" textalign="center" width="0%"></div>
				<div name='pripid' text="主体身份代码" textalign="center" width="0%"></div>
				<div name='inv' text="投资人/主管部门名称" textalign="center" width="15%"></div>
				<div name='certype' text="证件类型" textalign="center" width="10%"></div>
				<div name='invtype' text="投资人类型/主管部门类型" textalign="center"
					width="0%"></div>
				<div name='cerno' text="证件号码" textalign="center" width="0%"></div>
				<div name='lisubconam' text="认缴出资额" textalign="center" width="10%"></div>
				<div name='subconamusd' text="认缴出资额折万美元" textalign="center"
					width="0%"></div>
				<div name='subconform' text="认缴出资方式" textalign="center" width="10%"></div>
				<div name='subconprop' text="认缴出资比例" textalign="center" width="10%"></div>
				<div name='condate' text="认缴出资期限" textalign="center" width="0%"></div>
				<div name='liacconam' text="实缴出资额" textalign="center" width="0%"></div>
				<div name='acconamusd' text="实缴出资额折万美元" textalign="center"
					width="0%"></div>
				<div name='dom' text="住址" textalign="center" width="0%"></div>
				<div name='currency' text="币种" textalign="center" width="10%"></div>
				<div name='acconam' text="实缴出资额" textalign="center" width="10%"></div>
				<div name='country' text="国别(地区)" textalign="center" width="10%"></div>
				<div name='exeaffsign' text="执行合伙事务标志" textalign="center" width="0%"></div>
				<div name='respform' text="承担责任方式/责任形式" textalign="center"
					width="0%"></div>
				<div name='sconform' text="出资方式（个独）" textalign="center" width="0%"></div>
				<div name='sex' text="性别" textalign="center" width="0%"></div>
				<div name='nation' text="民族" textalign="center" width="0%"></div>
				<div name='natdate' text="出生日期" textalign="center" width="0%"></div>
				<div name='litdeg' text="文化程度" textalign="center" width="0%"></div>
				<div name='polstand' text="政治面貌" textalign="center" width="0%"></div>
				<div name='occst' text="职业状况" textalign="center" width="0%"></div>
				<div name='postalcode' text="邮政编码" textalign="center" width="0%"></div>
				<div name='tel' text="电话" textalign="center" width="8%"></div>
				<!-- 隐藏数字而已 -->
				<div name='commitment' text="总金额" textalign="center"></div>
				<div name='fix' text="操作" textalign="center" width="10%"></div>

			</div>
		</div>
		
		<!-- 表格 -->
		<div vtype="gridtable" name="grid_table" id="grid_table" rowrender=""></div>
		<!-- 卡片 -->
		<!-- <div vtype="gridcard" name="grid_card" width="205px" height="90px"></div> -->
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator"></div>
	</div>

		
</body>
</html>