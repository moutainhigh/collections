<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人员变更</title>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script>
	var pripid;
	function initData(res){
		pripid = res.getAttr("pripid");
		//beforeAfter = res.getAttr("beforeAfter");
		altitemcode = res.getAttr("altitemcode");
		regino = res.getAttr("regino");
		loadData(pripid,altitemcode,regino);
	}
	function loadData(pripid,altitemcode,regino){
		$('#jbxxGridBefore').gridpanel('option','dataurl',rootPath+
			'/trsquery/querynzwzduibixxdata.do?pripid='+pripid+'&beforeAfter='+'1'+'&altitemcode='+altitemcode+'&regino='+regino);
		$('#jbxxGridBefore').gridpanel('reload');
		
		$('#jbxxGridAfter').gridpanel('option','dataurl',rootPath+
				'/trsquery/querynzwzduibixxdata.do?pripid='+pripid+'&beforeAfter='+'2'+'&altitemcode='+altitemcode+'&regino='+regino);
		$('#jbxxGridAfter').gridpanel('reload');
	}
</script>
</head>
<body>
     <div id="column_id" width="100%" height="100%" vtype="panel" name="panel" layout="column" layoutconfig="{border: true, width:['*','50%','200']}"> 
        <div> 
            <div vtype="gridpanel" name="jbxxGrid1" height="90%" width="100%"
				id='jbxxGridBefore' dataloadcomplete="" 
				style="Position: Reletive" selecttype="1" titledisplay="true"
				title="变更前详细信息" defaultview="table" showborder="false"
				isshowselecthelper="false" selecttype="2" layoutconfig="{border: true}">
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
						<div name='persname' text="姓名" textalign="center" width="25%"></div>
						<div name='sex' text="性别" textalign="center" width="15%"></div>
						<div name='certype' text="证件类型" textalign="center" width="35%"></div>
						<div name='cerno' text="证件号码" textalign="center" width="25%"></div>
						<!-- <div name='APPOINTUNIT' text="委派单位" textalign="center" width="20%"></div> -->
					</div>
				</div>
				<!-- 表格 -->
				<div vtype="gridtable" name="grid_table" id="grid_table"
					rowrender=""></div>
				<!-- 分页 -->
				<div vtype="paginator" name="grid_paginator" id="grid_paginator1"></div>
			</div> 
        </div> 
    
        <div> 
            <div vtype="gridpanel" name="jbxxGrid2" height="90%" width="100%"
				id='jbxxGridAfter' dataloadcomplete="" 
				style="Position: Reletive" selecttype="1" titledisplay="true"
				title="变更后详细信息" defaultview="table" showborder="false"
				isshowselecthelper="false" selecttype="2" layoutconfig="{border: true}">
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
						<div name='persname' text="姓名" textalign="center" width="25%"></div>
						<div name='sex' text="性别" textalign="center" width="15%"></div>
						<div name='certype' text="证件类型" textalign="center" width="35%"></div>
						<div name='cerno' text="证件号码" textalign="center" width="25%"></div>
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
