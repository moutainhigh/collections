<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<%
	String contextpath = request.getContextPath();
%>
<title>股权冻结</title>
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
		//altRows('datashow');

	};

	function fixColumn(event, obj) {// 维护按钮
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			var htm ='<div class="jazz-grid-cell-inner">'
				+'<a href="javascript:void(0);" title="查看" onclick="viewDataSource(\''+data[i]["froid"]+'\');"><img src="'+rootPath+'/static/images/other/mag.png" width="11px" height="12px" border="0"/></a>'
				+'</div>';
			data[i]["fix"] = htm; 
		}
		return data;
	}
	function viewDataSource(froid){
		var title="详细信息";
		var frameurl=""+'/trsquery/querynzwzgqdongjiexxform.do?froid='+froid;
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
		style="Position: Reletive" selecttype="1" titledisplay="true"
		title="股权冻结" defaultview="table" showborder="false"
		isshowselecthelper="false" selecttype="2">
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>

				<div name='froid' text="冻结ID" textalign="center" width="0%"></div>
				<div name='pripid' text="被冻结股权所在市场主体身份代码" textalign="center"
					width="0%"></div>
				<div name='parentId' text="股权冻结被执行人信息ID" textalign="center"
					width="0%"></div>
				<div name='executeitem' text="执行事项" textalign="center" width="15%"></div>
				<div name='froauth' text="执行法院" textalign="center" width="15%"></div>
				<div name='frodocno' text="执行裁定书文号" textalign="center" width="10%"></div>
				<div name='executeno' text="协助执行通知书文号" textalign="center"
					width="10%"></div>
				<div name='invtype' text="被执行人类型" textalign="center" width="10%"></div>
				<div name='inv' text="被执行人" textalign="center" width="10%"></div>
				<div name='certype' text="证件类型" textalign="center" width="0%"></div>
				<div name='cerno' text="证件号码" textalign="center" width="20%"></div>
				<div name='blictype' text="证照类型" textalign="center" width="0%"></div>
				<div name='blicno' text="证照号码" textalign="center" width="0%"></div>
				<div name='corentname' text="被冻结股权所在市场主体名称" textalign="center"
					width="0%"></div>
				<div name='regno' text="被冻结股权所在市场主体注册号" textalign="center"
					width="0%"></div>
				<div name='uniscid' text="被冻结股权所在市场统一社会信用代码" textalign="center"
					width="0%"></div>
				<div name='froam' text="股权数额" textalign="center" width="0%"></div>
				<div name='foramme' text="股权数额单位" textalign="center" width="0%"></div>
				<div name='regcapcur' text="币种(CA04)" textalign="center" width="0%"></div>
				<div name='frozdeadline' text="冻结期限" textalign="center" width="0%"></div>
				<div name='frofrom' text="冻结期限自" textalign="center" width="0%"></div>
				<div name='froto' text="冻结期限至" textalign="center" width="0%"></div>
				<div name='keepfrofrom' text="续行冻结期限自" textalign="center" width="0%"></div>
				<div name='keepfroto' text="续行冻结期限至" textalign="center" width="0%"></div>
				<div name='keepfrozdeadline' text="续行冻结期限" textalign="center"
					width="0%"></div>
				<div name='publicdate' text="公示日期" textalign="center" width="0%"></div>
				<div name='frozstate' text="股权冻结状态" textalign="center" width="0%"></div>
				<div name='thawdate' text="解冻日期" textalign="center" width="0%"></div>
				<div name='loseeffdate' text="失效日期" textalign="center" width="0%"></div>
				<div name='loseeffres' text="失效原因" textalign="center" width="0%"></div>
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