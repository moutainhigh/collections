<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<%
	String contextpath = request.getContextPath();
%>
<title>注销信息</title>
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
				+'<a href="javascript:void(0);" title="查看" onclick="viewDataSource(\''+data[i]["pripid"]+'\');"><img src="'+rootPath+'/static/images/other/mag.png" width="11px" height="12px" border="0"/></a>'
				+'</div>';
			data[i]["fix"] = htm; 
		}
		return data;
	}
	function viewDataSource(pripid){
		var title="详细信息";
		var frameurl=""+'/trsquery/querynzwzzhuxiaoxxform.do?pripid='+pripid;
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
		title="注销信息" defaultview="table" showborder="false"
		isshowselecthelper="false" selecttype="2">
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>

				<div name='pripid' text="主体身份代码" textalign="center" width="0%"></div>
				<div name='candate' text="注销日期" textalign="center" width="10%"></div>
				<div name='canrea' text="注销原因" textalign="center" width="10%"></div>
				<div name='extclebrsign' text="对外投资清理完毕标志" textalign="center"
					width="10%"></div>
				<div name='cancelbrsign' text="分公司注销登记情况" textalign="center"
					width="10%"></div>
				<div name='declebrsign' text="债权债务清理完结情况" textalign="center"
					width="10%"></div>
				<div name='affwritno' text="清算组成员备案确认文书编号" textalign="center"
					width="10%"></div>
				<div name='pubnewsname' text="公告报纸名称" textalign="center" width="0%"></div>
				<div name='pubdate' text="公告日期" textalign="center" width="0%"></div>
				<div name='sanauth' text="批准机关" textalign="center" width="0%"></div>
				<div name='sandocno' text="批准文号" textalign="center" width="0%"></div>
				<div name='sandate' text="批准日期" textalign="center" width="15%"></div>
				<div name='cleantax' text="清稅情况" textalign="center" width="18%"></div>
				<div name='cancellationcertificate' text="批准证书缴销情况"
					textalign="center" width="0%"></div>
				<div name='customsettlement' text="海关手续清缴情况" textalign="center"
					width="0%"></div>
				<div name='cleanbondunit' text="清理债权债务单位" textalign="center"
					width="0%"></div>
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