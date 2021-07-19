<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企业历史人员变更</title>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script>
$(function(){
	var id = getUrlParam("id");
	var cerno = getUrlParam("cerno");
	var flag = getUrlParam("flag");
	
	$('#jbxxGrid').gridpanel('option','dataurl','<%=request.getContextPath()%>/readTxt/queryBGDetail.do?id='+id+'&bgtype='+'1'+'&cerno='+cerno+'&flag='+flag);
	$('#jbxxGrid').gridpanel('reload');
});
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}
function fixColumn(event, obj) {// 维护按钮
	var data = obj.data;
	var priPid = getUrlParam("id");
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
</script>
</head>
<body>
     <div vtype="gridpanel" name="jbxxGrid" height="90%" width="100%"
				id='jbxxGrid' dataloadcomplete="" datarender="fixColumn()"
				style="Position: Reletive" selecttype="1" titledisplay="true"
				title="变更信息" defaultview="table" showborder="false"
				isshowselecthelper="false" selecttype="2">
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
						<div name='altdate' text="变更日期" textalign="center" width="15%"></div>
						<div name='altitems' text="变更事项" textalign="center" width="75%"></div>
						<div name='fix' text="操作" textalign="center" width="10%"></div>
					</div>
				</div>
				<!-- 表格 -->
				<div vtype="gridtable" name="grid_table" id="grid_table"
					rowrender=""></div>
				<!-- 卡片 -->
				<!-- <div vtype="gridcard" name="grid_card" width="205px" height="90px"></div> -->
				<!-- 分页 -->
				<div vtype="paginator" name="grid_paginator" id="grid_paginator1"></div>

			</div>
</body>
</html>
