<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<%
	String contextpath = request.getContextPath();
%>
<title>股权出质</title>
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
		altRows('datashow');

	};
   
	
	function fixColumn(event, obj) {// 维护按钮
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			var htm ='<div class="jazz-grid-cell-inner">'
				+'<a href="javascript:void(0);" title="查看" onclick="viewDataSource(\''+data[i]["imporgid"]+'\');"><img src="'+rootPath+'/static/images/other/mag.png" width="11px" height="12px" border="0"/></a>'
				+'</div>';
			data[i]["fix"] = htm; 
		}
		return data;
	}
	function viewDataSource(imporgid){
		var title="详细信息";
		var frameurl=""+'/trsquery/querynzwzgqchuzhixxform.do?imporgid' + imporgid;
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
		title="股权出质" defaultview="table" showborder="false"
		isshowselecthelper="false" selecttype="2">
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>

				<div name='imporgid' text="股权出质信息ID" textalign="center" width="0%"></div>
				<div name='pripid' text="股权所在公司主体身份代码" textalign="center" width="0%"></div>
				<div name='entname' text="股权所在公司名称" textalign="center" width="15%"></div>
				<div name='regno' text="股权所在公司注册号" textalign="center" width="10%"></div>
				<div name='uniscid' text="股权所在公司统一社会信用代码" textalign="center"
					width="0%"></div>
				<div name='equityno' text="股权登记编号" textalign="center" width="0%"></div>
				<div name='pledgor' text="出质人" textalign="center" width="10%"></div>
				<div name='pledblictype' text="出质人证照类型" textalign="center"
					width="10%"></div>
				<div name='pledblicno' text="出质人证照号码" textalign="center" width="10%"></div>
				<div name='pledamunit' text="出质股权数额单位" textalign="center"
					width="10%"></div>
				<div name='impam' text="出质股权数额" textalign="center" width="10%"></div>
				<div name='regcapcur' text="出质股权数额币种" textalign="center" width="0%"></div>
				<div name='imporg' text="质权人" textalign="center" width="0%"></div>
				<div name='imporgblictype' text="质权人证照类型" textalign="center"
					width="0%"></div>
				<div name='imporgblicno' text="质权人证照号码" textalign="center"
					width="15%"></div>
				<div name='equpledate' text="股权出质登记日期" textalign="center" width="0%"></div>
				<div name='type' text="状态" textalign="center" width="0%"></div>
				<div name='candate' text="注销日期" textalign="center" width="0%"></div>
				<div name='equplecanrea' text="注销原因" textalign="center" width="0%"></div>
				<div name='canceldate' text="撤销日期" textalign="center" width="0%"></div>
				<div name='cancelrea' text="撤销原因" textalign="center" width="0%"></div>
				<div name='publicdate' text="公示日期" textalign="center" width="0%"></div>
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