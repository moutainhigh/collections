<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<%
	String contextpath = request.getContextPath();
%>
<title>药品企业详细查询</title>
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
<style type="text/css">
.jazz-field-comp-input {
	line-height: 22px;
	_line-height: 22px;
}

.btnExport {
	position: absolute;
	top: 220px;
	right: 3px;
	z-index: 9999;
	/* display:none; */
}

font {
	display: none;
}

#btnExport {
	display: none;
}

font {
	display: none;
}
</style>
</head>
<body>

	<script>
		
	</script>
<body style="overflow-x: hidden">
	<!-- 数据展现 -->
	<!-- <div style="display:none;color:red;background:#f8f8f8" id="tips" >说明：共<span id="totals"></span>条记录（自2017年2月1日起）。以下为查询结果的前100条，如有更多需求，请与信息中心联系，电话：83070056。</div> -->

	<div name="SpQyQueryListGrid" id="SpQyQueryListGrid" datarender="fixColumn()"	 vtype="gridpanel" titleDisplay="false" title="查询列表" labelStyleClass="labelstyle" lineno="true" isshowselecthelper="false" showborder=true width="100%">
		<!-- <div name="add_function_button" id="add_function_button" vtype='button' text='新增功能信息' ></div> -->



		<div name="SpQyQueryListGridColumn" id="SpQyQuery" vtype="gridcolumn">
			<div>
				<div name='certno' text="证照号码" textalign="left" width="35%"></div>
				<div name='validperiodend' text="有效期" datatype="date" dataformat="YYYY-MM-DD" textalign="left" width="20%"></div>
				<div name='typename' text="证照类型" textalign="left" width="35%"></div>
				<div name='opt' text="详情" textalign="center" ></div>
			</div>
		</div>
		<div vtype="gridtable" name="grid_table"></div>
		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
		<div class="nodata" style="display: none;"></div>
	</div>

	<!-- 构建页面==结束 -->

	
	
	<script type="text/javascript">
	

$(function() {
	var entname = parent.entname; 
	var pripid = parent.priPid;
	var regno = parent.regno;
	$("#SpQyQueryListGrid").gridpanel("option", "dataurlparams", {
		 "entname":entname,
		 "pripid": pripid
	});
	$("#SpQyQueryListGrid").gridpanel('option', 'dataurl',rootPath + "/yaoxieZZ/yaoXieQyQueryDetail.do");
	$("#SpQyQueryListGrid").gridpanel('reload');
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
			data[i]["opt"] = '<a href="javascript:void(0)" title="'+data[i]["casename"]+'"  onclick="caseDetail(\''
				+ data[i]["entid"]
				+ '\',\''
				+ data[i]["pripid"]
				+ '\',\''
				+ data[i]["regno"]
				+ '\',\''
				+ data[i]["entname"]
				+ '\',\''
				+ data[i]["id"]
				+ '\',\''
				+ data[i]["certno"]
			+ '\')">详情 </a>';
		}
		if(data.length==0){
			$("div[name='grid_table']").html("<br>&nbsp;&nbsp;说明：在系统中未查询到该商事主体的相关证照信息。");
			$("div[name='grid_table']").css({"text-align": "left","color": "red","font-size":"13px"});
		}
		return data;
	}
	function caseDetail(entid, pripid,regno,entname,id,certno){
		var url = "<%=request.getContextPath()%>/page/yaopin/yjm.jsp";
		var param ="?entid="+entid+"&pripid="+pripid+"&regno="+regno+"&entname="+encodeURIComponent(entname)+"&id="+id+"&certno="+encodeURIComponent(certno);
		url = url +param;
		window.open(url);
	}
	
	
</script>
	
</body>
</html>