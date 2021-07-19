<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title>集团变更信息</title>
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
<script src="<%=request.getContextPath()%>/static/js/sczt/sczt.js"
	type="text/javascript"></script>

<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=request.getContextPath()%>/static/css/trs/regDetail.css" />

<script type="text/javascript">
function fixColumn(event, obj) {// 维护按钮
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var htm ='<div class="jazz-grid-cell-inner">'
			+'<a href="javascript:void(0);" title="查看" onclick="viewDataSource(\''+data[i]["altid"]+'\');"><img src="'+rootPath+'/static/images/other/mag.png" width="11px" height="12px" border="0"/></a>'
			+'</div>';
		data[i]["fix"] = htm; 
	}
	return data;
}
function viewDataSource(altid){
	var title="详细信息";
	var type="view";
	var frameurl=""+'/trsquery/queryjituanbiangengxxform.do?altid='+altid;
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
		titleicon : "<%=request.getContextPath()%>/static/images/other/notepad-.png",
		frameurl: rootPath+frameurl
    }); 
}
</script>

</head>
<body>
	<div vtype="gridpanel" name="jbxxGrid" height="236" width="100%"
		id='jbxxGrid' dataloadcomplete="" datarender="fixColumn()"
		selecttype="1" titledisplay="true" title="注销信息" showborder="false"
		isshowselecthelper="false" selecttype="2">
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='altid' text="变更项ID" textalign="center" width="0%"></div>
				<div name='pripid' text="集团主体身份代码" textalign="center" width="0%"></div>
				<div name='grpaltitem' text="变更事项" textalign="center" width="20%"></div>
				<div name='altbe' text="变更前内容" textalign="center" width="25%"></div>
				<div name='altaf' text="变更后内容" textalign="center" width="25%"></div>
				<div name='altdate' text="变更日期" textalign="center" width="20%"></div>
				<div name='sExtFromnode' text="数据汇总单位" textalign="center" width="0%"></div>
				<div name='sExtDatatime' text="数据汇总时间" textalign="center" width="0%"></div>
				<div name='fix' text="操作" textalign="center" width="10%"></div>
			</div>
		</div>
		<!-- 表格 -->
		<div vtype="gridtable" name="grid_table"></div>
		<!-- 卡片 -->
		<!-- <div vtype="gridcard" name="grid_card" width="205px" height="90px"></div> -->
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div> 
	</div>
</body>
</html>