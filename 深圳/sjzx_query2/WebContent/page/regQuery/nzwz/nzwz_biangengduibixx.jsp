<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script>
	var pripid;
	function initData(res){
		pripid = res.getAttr("pripid");
		loadData(pripid);
	}
	function loadData(pripid){
		$('#jbxxGridBefore').gridpanel('option','dataurl',rootPath+
			'/trsquery/querynzwzduibixxdata.do?pripid='+pripid+'&type=before');
		$('#jbxxGridBefore').gridpanel('reload');
		
		$('#jbxxGridAfter').gridpanel('option','dataurl',rootPath+
			'/trsquery/querynzwzduibixxdata.do?pripid='+pripid+'&type=after');
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
				title="变更前信息" defaultview="table" showborder="false"
				isshowselecthelper="false" selecttype="2" layoutconfig="{border: true}">
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
						<div name='recid' text="记录ID" textalign="center" width="0%"></div>
						<div name='persname' text="姓名" textalign="center" width="100%"></div>
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
  
        </div> 
  
        <div> 
  
            <div vtype="gridpanel" name="jbxxGrid2" height="90%" width="100%"
				id='jbxxGridAfter' dataloadcomplete="" datarender=""
				style="Position: Reletive" selecttype="1" titledisplay="true"
				title="变更后信息" defaultview="table" showborder="false"
				isshowselecthelper="false" selecttype="2" layoutconfig="{border: true}">
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
						<div name='recid' text="记录ID" textalign="center" width="0%"></div>
						<div name='persname' text="姓名" textalign="center" width="100%"></div>
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
  
        </div> 
         
  
    </div> 
</body>
</html>
