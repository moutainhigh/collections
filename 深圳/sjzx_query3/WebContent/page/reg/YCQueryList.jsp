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
	

$(function() {
	var priPid = getUrlParam("priPid"); // 当前系统主键
	var entityNo = getUrl("entityNo");
	//var regno = getUrlParam("regno"); 
		$('#jbxxGrid').gridpanel(
				'option',
				'dataurl',
				rootPath + '/reg/detail.do?priPid=' + priPid +'&flag='+entityNo
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
			var htm ='<div class="jazz-grid-cell-inner">'
				+'<a href="javascript:void(0);" title="查看" onclick="viewDataSource(\''+data[i]["id"]+'\');"><img src="'+rootPath+'/static/images/other/mag.png" width="11px" height="12px" border="0"/></a>'
				+'</div>';
			/* var html ='<div class="jazz-grid-cell-inner">'
				+'<a href="javascript:void(0);" title="查看" onclick="viewDataSource(\''+data[i]["personid"]+'\');">'+data[i]["name"]+'</a>'
				+'</div>'; */
			data[i]["fix"] = htm;
			//data[i]["name"] = html;
			if(data[i]["createtime"]){
				data[i]["createtime"] = data[i]["createtime"].substring(0,10);
			}
			/* if(data[i]["cerno"]){
				var cerno = data[i]["cerno"];
				var str = cerno.substring(cerno.length - 4,cerno.length);
				data[i]["cerno"] = cerno.replace(str, "****");
			} */
		}
		if(data.length==0){
			$("div[name='grid_table']").html("<br>&nbsp;&nbsp;说明：未查询到该商事主体的异常名录信息。");
			$("div[name='grid_table']").css({"text-align": "left","color": "red","font-size":"13px"});
		}
		
		return data;
	}
	function viewDataSource(id){
		var title="详细信息";
		var frameurl=""+'/trsquery/queryyichangxxform.do?id='+id;
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
	<div vtype="gridpanel" name="jbxxGrid" height="536" width="100%"
		id='jbxxGrid' dataloadcomplete="" datarender="fixColumn()"
		style="Position: Reletive" selecttype="1" titledisplay="true"
		title="异常名录信息" defaultview="table" showborder="false"
		isshowselecthelper="false" selecttype="2">
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>

				<!--  <div name='regno' text="统一社会信用代码/注册号" textalign="center" width="23%"></div> -->
				<!-- <div name='entname' text="商事主体名称" textalign="center" width="20%"></div> -->
				<div name='createtime' text="列入日期" textalign="center" width="20%"></div>
				<div name='remark' text="列入异常名录原因" textalign="center" width="35%"></div>
				<!--  <div name='abnormaltype' text="列入异常名录原因" textalign="center" width="35%" dataurl="../comselect/abnormaltype.json"></div>-->
				<div name='type' text="异常名录状态" textalign="center" width="35%" dataurl="../comselect/btype.json"></div>
                <div name='fix' text="操作" textalign="center" width="10%"></div>

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