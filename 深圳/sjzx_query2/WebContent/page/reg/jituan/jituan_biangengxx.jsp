<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<%
	String contextpath = request.getContextPath();
%>
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
<script src="<%=request.getContextPath()%>/static/js/sczt/jituanSczt.js"
	type="text/javascript"></script>

<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=request.getContextPath()%>/static/css/trs/regDetail.css" />

<script type="text/javascript">
function fixColumn(event, obj) {// 维护按钮
	var data = obj.data;
	var priPid = getUrlParam("priPid");
	var entityNo = getUrlParam("entityNo");
	var economicproperty = getUrlParam("economicproperty");
	for(var i=0;i<data.length;i++){
		var htmCompare ='<div class="jazz-grid-cell-inner">'
			+'<a href="javascript:void(0);" title="查看" onclick="viewDataSource(\''+priPid+'\',\''+entityNo+'\',\''+economicproperty+'\',\''+data[i]["alttime"]+'\',\''+data[i]["altdate"]+'\',\''+data[i]["entname"]+'\');"><img src="'+rootPath+'/static/images/other/mag.png" width="11px" height="12px" border="0"/></a>'
			+'</div>';
		data[i]["fix"] = htmCompare;
	}
	return data;
}

function viewDataSource(priPid,entityNo,economicproperty,alttime,altdate,entname){
	var title="详细信息";
	var frameurl = ""+'/page/reg/nzwz/nzwz_biangengxx_two.jsp?priPid='+priPid+'&entityNo='+entityNo+'&economicproperty='+economicproperty+'&alttime='+alttime+'&altdate='+altdate+'&entname='+entname;
	//createNewWindow1(title,frameurl);	
	createNewWindow(frameurl);	
}

function createNewWindow(frameurl){
	window.open(rootPath+frameurl);
}

/* function fixColumn(event, obj) {// 维护按钮
	var data = obj.data;
	//console.info(data);
	for(var i=0;i<data.length;i++){
		if(data[i]["describe"] == '2'){
			var htmBefore ='<div class="jazz-grid-cell-inner">'
				+'<a href="javascript:void(0);" title="查看" onclick="viewDataSource(\''+data[i]["recid"]+'\',\''+ data[i]["altitemcode"]+'\',\''+ data[i]["pripid"]+'\',\''+'1'+'\',\''+data[i]["regino"]+'\');"><img src="'+rootPath+'/static/images/other/xiangqing.png" width="25px" height="12px" border="0"/></a>'
				+'</div>';
			data[i]["altbe"] = htmBefore;
			
			var htmAfter ='<div class="jazz-grid-cell-inner">'
				+'<a href="javascript:void(0);" title="查看" onclick="viewDataSource(\''+data[i]["recid"]+'\',\''+ data[i]["altitemcode"]+'\',\''+ data[i]["pripid"]+'\',\''+'2'+'\',\''+data[i]["regino"]+'\');"><img src="'+rootPath+'/static/images/other/xiangqing.png" width="25px" height="12px" border="0"/></a>'
				+'</div>';
			data[i]["altaf"] = htmAfter;
			
			var htmCompare ='<div class="jazz-grid-cell-inner">'
				+'<a href="javascript:void(0);" title="查看" onclick="viewDataSource(\''+data[i]["recid"]+'\',\''+ data[i]["altitemcode"]+'\',\''+ data[i]["pripid"]+'\',\''+'3'+'\',\''+data[i]["regino"]+'\');"><img src="'+rootPath+'/static/images/other/mag.png" width="11px" height="12px" border="0"/></a>'
				+'</div>';
			data[i]["fix"] = htmCompare;
			
			//console.info(data[i]["regino"]);
		}
		data[i]['altdate'] = data[i]['altdate'].substring(0,10);
	}
	return data;
} */

<%-- 
function viewDataSource(recid, altitemcode, pripid,beforeAfter,regino){
	var title="详细信息";
	//var type="view";
	var frameurl = ""+'/trsquery/queryjituanbiangengxxform.do?pripid='+pripid+'&beforeAfter='+beforeAfter+'&altitemcode='+altitemcode+'&regino='+regino;
	createNewWindow(title,frameurl);	
}
var win;
function createNewWindow(title,frameurl){
    win = top.jazz.widget({ 
    	  
        vtype: 'window', 
        name: 'win', 
        title: title, 
        width: 750, 
        height: 500, 
        modal:true, 
        visible: true ,
		showborder : true, //true显示窗体边框    false不显示窗体边
		closestate: false,
		minimizable : true, //是否显示最小化按钮
		titleicon : "<%=contextpath%>/static/images/other/notepad-.png",
		frameurl: rootPath+frameurl
    });
} --%>
</script>

</head>
<body>
	<div vtype="gridpanel" name="jbxxGrid" height="236" width="100%"
		id='jbxxGrid' dataloadcomplete="" datarender="fixColumn()"
		selecttype="1" titledisplay="true" title="集团变更信息" showborder="false"
		isshowselecthelper="false" selecttype="2">
		<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
						<!-- <div name='recid' text="记录ID" textalign="center" width="0%"></div>
						<div name='openo' text="业务编号" textalign="center" width="0%"></div>
						<div name='pripid' text="主体身份代码" textalign="center" width="0%"></div>
						<div name='altdate' text="变更日期" textalign="center" width="15%"></div>
						<div name='altitem' text="变更事项代码" textalign="center" width="25%"></div>
						<div name='altbe' text="变更前内容" textalign="center" width="25%"></div>
						<div name='altaf' text="变更后内容" textalign="center" width="25%"></div>
						<div name='fix' text="操作" textalign="center" width="10%"></div> -->
						
						<div name='altdate' text="变更日期" textalign="center" width="15%"></div>
						<div name='altitems' text="变更事项" textalign="center" width="75%"></div>
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