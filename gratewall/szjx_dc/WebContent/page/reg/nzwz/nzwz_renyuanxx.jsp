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
				+'<a href="javascript:void(0);" title="查看" onclick="viewDataSource(\''+data[i]["personid"]+'\');"><img src="'+rootPath+'/static/images/other/mag.png" width="11px" height="12px" border="0"/></a>'
				+'</div>';
			data[i]["fix"] = htm; 
		}
		return data;
	}
	function viewDataSource(personid){
		var title="详细信息";
		var frameurl=""+'/trsquery/querynzwzrenyuanxxform.do?personid='+personid;
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
		title="人员信息" defaultview="table" showborder="false"
		isshowselecthelper="false" selecttype="2">
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>

				<div name='personid' text="人员ID" textalign="center" width="0%"></div>
				<div name='pripid' text="主体身份代码" textalign="center" width="0%"></div>
				<div name='name' text="姓名" textalign="center" width="20%"></div>
				<div name='sex' text="性别" textalign="center" width="10%"></div>
				<div name='natdate' text="出生日期" textalign="center" width="18%"></div>
				<div name='certype' text="证件类型" textalign="center" width="20%"></div>
				<div name='cerno' text="证件号码/代表证编号" textalign="center" width="0%"></div>
				<div name='position' text="职务" textalign="center" width="10%"></div>
				<div name='posbrform' text="职务产生方式" textalign="center" width="15%"></div>
				<div name='occstbeapp' text="申请前职业状况" textalign="center" width="0%"></div>
				<div name='lerepsign' text="法定代表人标志/首席代表标志/负责人标识" textalign="center"
					width="0%"></div>
				<div name='appounit' text="任命单位/委派方" textalign="center" width="0%"></div>
				<div name='tel' text="联系电话" textalign="center" width="0%"></div>
				<div name='country' text="国别（地区）" textalign="center" width="0%"></div>
				<div name='telnumber' text="固定电话" textalign="center" width="0%"></div>
				<div name='mobtel' text="移动电话" textalign="center" width="0%"></div>
				<div name='email' text="电子邮箱" textalign="center" width="0%"></div>
				<div name='houseadd' text="住址" textalign="center" width="0%"></div>
				<div name='arrchdate' text="入境时间" textalign="center" width="0%"></div>
				<div name='repcarfrom' text="代表证期限自/任职起始日期" textalign="center"
					width="0%"></div>
				<div name='repcarto' text="代表证期限至/任职截止日期" textalign="center"
					width="0%"></div>
				<div name='postalcode' text="邮政编码" textalign="center" width="0%"></div>
                <div name='fix' text="操作" textalign="center" width="10%"></div>

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