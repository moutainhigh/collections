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
<script src="<%=request.getContextPath()%>/static/js/sczt/base64.js"
	type="text/javascript"></script>
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/trs/regDetail.css" />

<script type="text/javascript">
var pripid = null;
var type = null;

$(function() {
	 pripid = getUrlParam("pripid"); // 当前系统主键
	 type = getUrl("type"); 
	//var regno = getUrlParam("regno"); 
		$('#jbxxGrid').gridpanel(
				'option',
				'dataurl',
				rootPath + '/NBselect/queryNBDetailReg.do?pripid=' + pripid +'&type='+type
		);
		$('#jbxxGrid').gridpanel('query', [ 'gridpanel' ]);
});

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return decode(unescape(r[2]));
	return null; // 返回参数值
}

function getUrl(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
}

	function fixColumn(event, obj) {// 维护按钮
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			data[i]["opt"] = '<a href="javascript:void(0);" onclick="NBDetail(\''
				+data[i].pripid
				+ '\',\''
				+ data[i].ancheid
				+ '\',\''
				+ data[i].ancheyear
				+ '\',\''
				+ data[i].anchedate
				+'\')">' + "年报详情" + '</a>'
		}
		if(data.length==0){
			$("div[name='grid_table']").html("<br>&nbsp;&nbsp;说明：未查询到该商事主体的年报信息。");
			$("div[name='grid_table']").css({"text-align": "left","color": "red","font-size":"13px"});
		}
		return data;
	}
	function NBDetail(pripid, ancheid, ancheyear, anchedate){
		if(type==0){
			var url = "<%=request.getContextPath()%>"+"/page/comselect/nbEntDetail.jsp?id="+encode(ancheid)+"&pripid="+encode(pripid)+"&ancheyear="+encode(ancheyear)+"&anchedate="+encode(anchedate)+"&isGtOrQe="+0+"&type=gt";
	    }
	    if(type==1){
	    	var url = "<%=request.getContextPath()%>"+"/page/comselect/nbEntDetail.jsp?id="+encode(ancheid)+"&pripid="+encode(pripid)+"&ancheyear="+encode(ancheyear)+"&anchedate="+encode(anchedate)+"&isGtOrQe="+1+"&type=qy";
	    }
    		window.open(url);
    	}
	
	
	
</script>


</head>
<body>
	<div vtype="gridpanel" name="jbxxGrid" height="536" width="100%"
		id='jbxxGrid' dataloadcomplete="" datarender="fixColumn()"
		style="Position: Reletive" selecttype="1" titledisplay="true"
		title="年报信息" defaultview="table" showborder="false"
		isshowselecthelper="false" selecttype="2">
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='ancheyear' text="报送年度" textalign="center" width="40%"></div>
				<div name='anchedate' text="年报日期" textalign="center" datatype="date" dataformat="YYYY-MM-DD"  width="40%" ></div>
				<div name='opt' text="操作" textalign="center" width='20%'></div>
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