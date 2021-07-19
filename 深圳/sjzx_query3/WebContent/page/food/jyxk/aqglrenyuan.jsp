<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<%
	String contextpath = request.getContextPath();
%>
<title>安全管理人员信息</title>
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
<script src="<%=request.getContextPath()%>/static/js/sczt/base64.js"
	type="text/javascript"></script>
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/trs/regDetail.css" />

<script type="text/javascript">
	

$(function() {
	var priPid = parent.id; // 企业主键
	var lictype = '12'; 
	
	$("#jbxxGrid").gridpanel("option", "dataurlparams", {
		 "priPid" : priPid,
		 "lictype" : lictype
	});
	$("#jbxxGrid").gridpanel('option', 'dataurl',rootPath + "/food/detail.do");
	$("#jbxxGrid").gridpanel('reload');
});

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
}

	function fixColumn(event, obj) {// 维护按钮
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			data[i]["opt"] ='<div class="jazz-grid-cell-inner">'
				+'<a href="javascript:void(0);" title="查看" onclick="viewDataSource(\''
						+ data[i]["id"]
						+ '\',\''
						+ data[i]["cerno"]
						+'\');"><img src="'+rootPath+'/static/images/other/mag.png" width="11px" height="12px" border="0"/></a>'
				+'</div>';
		}
//		if(data.length==0){
//			$("div[name='grid_table']").html("<br>&nbsp;&nbsp;说明：在新案件系统中未查询到该商事主体的已办结案件。");
//			$("div[name='grid_table']").css({"text-align": "left","color": "red","font-size":"13px"});
//		}
//		return data;
	}
//	function caseDetail(id, litiganttype,casesourceno){
//		var url = "<%=request.getContextPath()%>"+"/page/comselect/caseEntDetail.jsp?id="+id+"&litiganttype="+litiganttype+"&casesourceno="+casesourceno+"&type=case";
//		window.open(url);
//	}
	function viewDataSource(id){
		var title="详细信息";
		var frameurl="<%=request.getContextPath()%>"+"/page/food/jyxk/aqglrenyuanDetail.jsp?id="+id;
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
			frameurl: frameurl
	    }); 
	}	
	
</script>


</head>
<body>
	<div vtype="gridpanel" name="jbxxGrid" height="536" width="100%"
		id='jbxxGrid' dataloadcomplete="" datarender="fixColumn()"
		style="Position: Reletive" selecttype="1" titledisplay="true"
		title="安全管理人员信息" defaultview="table" showborder="false"
		isshowselecthelper="false" selecttype="2">
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='name' text="姓名" textalign="center" width="20%"></div>
				<div name='staffType' text="人员类型" textalign="center" width="20%"></div>
				<div name='position' text="职务" textalign="center" width="30%"></div>
				<div name='sex' text="性别" textalign="center" width="20%"></div>
				<div name='opt' text="详情" textalign="center" width="10%"></div>
			</div>
		</div>
		<!-- 表格 -->
		<div vtype="gridtable" name="grid_table" id="grid_table" rowrender=""></div>
		<!-- 卡片 -->
		<!-- <div vtype="gridcard" name="grid_card" width="205px" height="90px"></div> -->
		<!-- 分页 -->
		<!-- <div vtype="paginator" name="grid_paginator" ></div> -->

	</div>
		
</body>
</html>