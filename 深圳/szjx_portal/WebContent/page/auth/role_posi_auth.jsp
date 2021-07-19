<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	String contextpath = request.getContextPath();
%>

<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<script>
var roleCode=null;
var data = null;
	function initData(res){
		debugger;
		roleCode=res.getAttr("roleCode");
		data=res.getAttr("gridpanel");
		
	}
	
	$(function(){
		debugger;
		$('div[name="gridpanel"]').gridpanel('option', 'dataurl',rootPath+'/auth/getShowRoleList.do?roleCode='+roleCode);
		$('div[name="gridpanel"]').gridpanel('reload');
	});

	function rowrender(event,rows){
		 var data = rows.data;
		 var rowElements = rows.rowEl;
		 $.each(rowElements,function(i,trobj){
			$.each($(trobj).children(),function(j,tdobj){
				
				$(tdobj).css("line-height","26px");
					
			});
		}); 
	}
	
	
</script>
</head>
<body>
	<div vtype="gridpanel" name="gridpanel" id="gridpanel" width="100%"
		layout="fit" showborder="false" rowselectable="false"
		titledisplay="false" isshowselecthelper="true" selecttype="0">
		<div name="toolbar" vtype="toolbar"></div>
		<div vtype="gridcolumn" name="grid_column" id="grid_column">
			<div>
				<!-- 单行表头 -->
				<div name="postName" text="岗位名称" textalign="center" sort="true"
					width="20%"></div>
			</div>
		</div>
		<div vtype="gridtable" name="grid_table" id="grid_table" rowrender="rowrender()"></div>
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" id="grid_paginator"></div>
	</div>
	
</body>

</html>