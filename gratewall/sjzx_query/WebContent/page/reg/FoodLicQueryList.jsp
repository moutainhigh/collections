<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<%
	String contextpath = request.getContextPath();
%>
<title>食品信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

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
	var priPid = getUrlParam("pripid"); // 当前系统主键
	var entname = getUrlParam("entname");
	var regno = getUrlParam("regno");

	$("#jbxxGrid").gridpanel("option", "dataurlparams", {
		 "priPid":priPid,
		 "entname":entname,
		 "regno":regno
	});
	$("#jbxxGrid").gridpanel('option', 'dataurl',rootPath + "/reg/fooddetail.do");
	$("#jbxxGrid").gridpanel('reload');
});

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return decode(unescape(r[2]));
	return null; // 返回参数值
}

	function fixColumn(event, obj) {// 维护按钮
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			data[i]["detail"] = '<a href="javascript:void(0)" title="'+data[i]["detail"]+'"  onclick="FoodDetail(\''
				+ data[i]["id"]
				+ '\',\''
				+ data[i]["lictype"]
				+ '\',\''
				+ data[i]["licid"]
			+ '\')">' + '详情' + ' </a>';
		}
		if(data.length==0){
			$("div[name='grid_table']").html("<br>&nbsp;&nbsp;说明：在食品系统中未查询到该商事主体的食品信息。");
			$("div[name='grid_table']").css({"text-align": "left","color": "red","font-size":"13px"});
		}
		return data;
	}
	function FoodDetail(id, lictype, licid){
		var url = "<%=request.getContextPath()%>"+"/page/food/foodDetail.jsp?flag=" + encode("0")
		  + "&id=" + encode(id) + "&lictype=" + encode(lictype)
		  + "&licid=" + encode(licid);
		window.open(url);
	}
	
	
</script>


</head>
<body>
	<div vtype="gridpanel" name="jbxxGrid" height="90%" width="100%"
		id='jbxxGrid' dataloadcomplete="" datarender="fixColumn()"
		style="Position: Reletive" selecttype="1" titledisplay="true"
		title="食品信息" defaultview="table" showborder="false"
		isshowselecthelper="false" selecttype="2">
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='licno' text="许可证号" textalign="center" width="35%" ></div>
				<div name='validDate' text="有效期限"  textalign="center" width="20%"></div>
				<div name='lictypeCn' text="许可证类型"  textalign="center" width="35%"></div>
				<div name='detail' text="详情"  textalign="center" width="10%"></div>
                
			</div>
		</div>
		<!-- 表格 -->
		<div vtype="gridtable" name="grid_table" id="grid_table" rowrender=""></div> 
		<!-- 卡片 -->
		<!-- <div vtype="gridcard" name="grid_card" width="205px" height="90px"></div> -->
		<!-- 分页 -->
		<!--   <div vtype="paginator" name="grid_paginator" ></div>  -->

	</div>
		<div class="display" style="height:15px"> </div>
		
</body>
</html>