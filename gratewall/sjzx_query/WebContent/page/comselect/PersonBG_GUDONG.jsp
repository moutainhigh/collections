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
	
	$('#jbxxGridBefore').gridpanel('option','dataurl','<%=request.getContextPath()%>/readTxt/queryBGDetail.do?id='+id+'&bgtype='+'1'+'&cerno='+cerno+'&flag='+flag);
		$('#jbxxGridBefore').gridpanel('reload');
	$('#jbxxGridAfter').gridpanel('option','dataurl','<%=request.getContextPath()%>/readTxt/queryBGDetail.do?id='+id+'&bgtype='+'2'+'&cerno='+cerno+'&flag='+flag);
		$('#jbxxGridAfter').gridpanel('reload');
});
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}
</script>
</head>
<body>
     <div id="column_id" width="100%" height="100%" vtype="panel" name="panel" layout="column" layoutconfig="{border: true, width:['*','50%','200']}"> 
        <div> 
            <div vtype="gridpanel" name="jbxxGrid1" height="90%" width="100%"
				id='jbxxGridBefore' dataloadcomplete="" 
				style="Position: Reletive" selecttype="1" titledisplay="true"
				title="企业历史人员变更前信息" defaultview="table" showborder="false"
				isshowselecthelper="false" selecttype="2" layoutconfig="{border: true}">
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
						<div name='inv' text="姓名" textalign="center" width="15%"></div>
						<div name='certype' text="证件类型" textalign="center" width="45%"></div>
						<div name='cerno' text="证件号码" textalign="center" width="40%"></div>
						<!-- <div name='post' text="职务" textalign="center" width="20%"></div> -->
					</div>
				</div>
				<!-- 表格 -->
				<div vtype="gridtable" name="grid_table" id="grid_table"
					rowrender=""></div>
				<!-- 分页 -->
				<!-- <div vtype="paginator" name="grid_paginator" id="grid_paginator1"></div> -->
			</div> 
        </div> 
        
        <div> 
            <div vtype="gridpanel" name="jbxxGrid2" height="90%" width="100%"
				id='jbxxGridAfter' dataloadcomplete="" 
				style="Position: Reletive" selecttype="1" titledisplay="true"
				title="企业历史人员变更后信息" defaultview="table" showborder="false"
				isshowselecthelper="false" selecttype="2" layoutconfig="{border: true}">
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
						<div name='inv' text="姓名" textalign="center" width="15%"></div>
						<div name='certype' text="证件类型" textalign="center" width="45%"></div>
						<div name='cerno' text="证件号码" textalign="center" width="40%"></div>
						<!--<div name='post' text="职务" textalign="center" width="20%"></div> -->
					</div>
				</div>
				<!-- 表格 -->
				<div vtype="gridtable" name="grid_table" id="grid_table"
					rowrender=""></div>
				<!-- 分页 -->
				<!-- <div vtype="paginator" name="grid_paginator" id="grid_paginator1"></div> -->
			</div> 
        </div> 
    </div> 
</body>
</html>
