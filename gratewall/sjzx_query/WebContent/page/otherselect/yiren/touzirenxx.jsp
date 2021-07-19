<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<%
	String contextpath = request.getContextPath();
%>
<title>人员信息</title>
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
	src="<%=request.getContextPath()%>/static/js/other/blackent.js"></script>

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
				+'<a href="javascript:void(0);" title="查看" onclick="viewDataSource(\''+data[i]["id"]+'\');"><img src="'+rootPath+'/static/images/other/mag.png" width="11px" height="12px" border="0"/></a>'
				+'</div>';
			data[i]["fix"] = htm;
			if(data[i]["invtypecode"] != "20" &&  data[i]["invtypecode"] != "21" && data[i]["invtypecode"] != "35" && data[i]["invtypecode"] != "36" ){
				data[i]["certype"] = data[i]["blictype"];
				data[i]["cerno"] = data[i]["blicno"];	
			}
			
		}
		return data;
	}
	function viewDataSource(personid){
		var title="详细信息";
		var frameurl=""+'/blackent/yrtouzirenxxform.do?id='+personid;
		createNewWindow(title,frameurl);	
	}
	var win;
	function createNewWindow(title,frameurl){
	    win = top.jazz.widget({ 
	    	  
	        vtype: 'window', 
	        name: 'win', 
	        title: title, 
	        width: 850, 
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
	<div vtype="gridpanel" name="jbxxGrid" height="450" width="100%"
		id='jbxxGrid' dataloadcomplete="" datarender="fixColumn()"
		  titledisplay="true"
		title="投资人息" defaultview="table" showborder="false"
		isshowselecthelper="false" selecttype="2">
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='inv' text="投资人" textalign="center" width="10%"></div>
				<div name='invtype' text="投资人类型" textalign="center" width="15%"></div>
				<div name='certype' text="证件(证照)类型" textalign="center" width="25%"></div>
				<div name='cerno' text="证件(证照)号码" textalign="center" width="20%"></div>
				<div name='subconam' text="认缴出资额" textalign="center" width="12%"></div>
				<div name='conprop' text="出资比例" textalign="center" width="10%"></div>
				<!-- <div name='sExtValidflag' text="标识位" textalign="center" width="10%" dataurl="[{'value':'1','text':'有效'},{'value':'0','text':'无效'}]"></div> -->
                <div name='fix' text="操作" textalign="center" width="8%"></div>
			</div>
		</div>
		<!-- 表格 -->
		<div vtype="gridtable" name="grid_table" id="grid_table" rowrender=""></div>
		<!-- 卡片 -->
		<!-- <div vtype="gridcard" name="grid_card" width="205px" height="90px"></div> -->
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" ></div>

	</div>
		
</body>
</html>