<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible"content="IE=8;IE=9;IE=EDGE"> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企业基本信息</title>
<%
	String rootPath = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=rootPath%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/sczt/base64.js"
	type="text/javascript"></script>
<script>
var cerno = null;
$(function(){
	cerno = decode(getUrlParam("cerno"));
	$("#gridpanel").gridpanel("option", "dataurlparams", {
		"cerno" : cerno
	});
	$("#gridpanel").gridpanel('option', 'dataurl',rootPath + "/reg/entdetail.do");
	$("#gridpanel").gridpanel('reload');
});

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return r[2];
	return null; // 返回参数值
}

function renderdata(event,obj){
	var data = obj.data;
	for (var i = 0; i < data.length; i++) {
		data[i]["entname"] = '<a href="javascript:void(0)" title="'+data[i]["entname"]+'"  onclick="regDetail(\''
				+ data[i]["id"]
				+ '\',\''
				+ data[i]["type"]
				+ '\',\''
				+ data[i]["opetype"]
				+ '\',\''
				+ data[i]["entstatus"]
				+ '\',\''
				+ data[i]["entname"]
				+ '\',\''
				+ data[i]["regno"]
				+ '\',\''
				+ data[i]["entid"]
				+ '\')">' + data[i]["entname"] + ' </a>';
	}
	return data;
}

function regDetail(id, enttype, opetype, entstatus, entname, regno, entid) {
	var economicproperty ="";
		if(enttype.substring(0,1)=="1"||enttype.substring(0,1)=="2"||enttype.substring(0,1)=="3"||enttype.substring(0,1)=="4" || enttype.substring(0,1)=="A" || enttype.substring(0,1)=="C" ){//内资企业
			economicproperty="2";
		}else if(enttype.substring(0,1)=="5"||enttype.substring(0,1)=="6"||enttype.substring(0,1)=="7" || enttype.substring(0,1)=="W" || enttype.substring(0,1)=="Y"  ){//外资企业
			economicproperty= "3";
		}else if(enttype.substring(0,2)=="95"){//个体
			economicproperty= "1";
		}else if(enttype.substring(0,1) == "8"){//集团
			economicproperty= "4";
		}else{
			economicproperty= "2"; //暂时先写成2
		}
		var urlleft="<%=request.getContextPath()%>";
	var urlright = "page/reg/regDetail.jsp";
	var url = urlleft + "/" + urlright + "?flag=" + encode("0")
			+ "&economicproperty=" + encode(economicproperty) + "&priPid="
			+ encode(id) + "&opetype=" + encode(opetype) + "&entstatus="
			+ encode(entstatus)+ "&entname="
			+ encode(entname)+ "&regno="
			+ encode(regno)+ "&entid="
			+ encode(entid)+ "&bregno="
			+ encode(regno);
	window.open(url);

}



</script>
<body style="overflow-x:hidden">
	<div name="gridpanel" id="gridpanel" vtype="gridpanel" titleDisplay="false" title="查询列表" labelStyleClass="labelstyle" lineno="true" isshowselecthelper="false"
		  showborder=true width="100%" datarender = "renderdata()">
			<div name="functionConfigGrid_column" vtype="gridcolumn">
				<div>
					<div name='regno' text="统一社会信用代码/注册号" textalign="left" width="15%"></div>
					<div name='entname' text="商事主体名称" textalign="left" width="22%"></div>
					<div name='enttype' text="经济性质" textalign="left" width="22%"></div>
					<div name='estdate' text="成立日期" datatype="date" dataformat="YYYY-MM-DD" textalign="left" width="9%"></div>
					<div name='currency' text="币种"  textalign="left" width="9%"></div>
					<div name='regcap' text="认缴资本总额（万元）" textalign="right" width="13%"></div>
					<div name='regstate' text="商事主体状态" textalign="left"  width="10%"></div>
				</div>
			</div>
			<div vtype="gridtable" name="grid_table"></div>
			<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
			<div class="nodata" style="display: none;text-align: center;" ></div>
		</div>
</body>
</html>