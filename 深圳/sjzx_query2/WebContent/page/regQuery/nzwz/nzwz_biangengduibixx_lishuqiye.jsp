<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>隶属企业变更</title>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script>
	var pripid;
	function initData(res){
		pripid = res.getAttr("pripid");
		beforeAfter = res.getAttr("beforeAfter");
		altitemcode = res.getAttr("altitemcode");
		regino = res.getAttr("regino");
		loadData(pripid,beforeAfter,altitemcode,regino);
	}
	function loadData(pripid,beforeAfter,altitemcode,regino){
		$('#jbxxGridBefore').gridpanel('option','dataurl',rootPath+
			'/trsquery/querynzwzduibixxdata.do?pripid='+pripid+'&beforeAfter='+beforeAfter+'&altitemcode='+altitemcode+'&regino='+regino);
		$('#jbxxGridBefore').gridpanel('reload');
	}
</script>
</head>
<body>
     <div id="column_id" width="100%" height="100%" vtype="panel" name="panel" layout="column" layoutconfig="{border: true, width:['*','50%','200']}"> 
        <div> 
            <div vtype="gridpanel" name="jbxxGrid1" height="90%" width="100%"
				id='jbxxGridBefore' dataloadcomplete="" 
				style="Position: Reletive" selecttype="1" titledisplay="true"
				title="详细信息" defaultview="table" showborder="false"
				isshowselecthelper="false" selecttype="2" layoutconfig="{border: true}">
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
						<div name='entname' text="企业名称" textalign="center" width="25%"></div>
						<div name='unifsocicrediden' text="统一社会信息用代码" textalign="center" width="25%"></div>
						<div name='regno' text="注册号" textalign="center" width="25%"></div>
						<div name='lerep' text="法定代表人" textalign="center" width="25%"></div>
						<!-- <div name='APPOINTUNIT' text="委派单位" textalign="center" width="20%"></div> -->
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
