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

$(function(){
	queryMenu();
	$(document).keydown(function(event) {
		if (event.keyCode == 13) {
			querySomeMenu();
		}
	});
	//$('div[name="gridpanel"]').gridpanel('hideColumn', 'pkSysIntegration');
});
function queryMenu() {
	$('div[name="gridpanel"]').gridpanel('option', 'dataurl',rootPath+'/auth/getMenu.do');
	$('div[name="gridpanel"]').gridpanel('reload');
}
function querySomeMenu(){
	var systemName =$("#systemName").textfield('getValue');
	$('div[name="gridpanel"]').gridpanel('option', 'dataurl',rootPath+'/auth/getSomeMenu.do');
	$('div[name="gridpanel"]').gridpanel('query', [ 'formpanel']);	
}



function fixColumn(event, obj) {//维护
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var htm = '<div class="jazz-grid-cell-inner">'
				+'<a href="javascript:void(0);" title="维护" onclick="fixRole(\''+data[i]["systemCode"]+'\',\''+data[i]["pkSysIntegration"]+'\');"><img src="'+rootPath+'/static/images/other/forma-1.png" width="11px" height="11px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
				+'</div>';
		data[i]["fix"] = htm; 
	}
	
	for(var i=0;i<data.length;i++){
		data[i]["integratedUrl"] = '<div class="jazz-grid-cell-inner">'+data[i]["integratedUrl"]+'<pre></pre>';
		data[i]["integratedUrl"] += '</div>';
	}
	return data;
}
var fixwin;
function fixRole(systemCode,pkSysIntegration) {
	$("iframe", parent.document).attr("src", rootPath+'/auth/getRole_fixHtml.do?systemCode='+systemCode+"&pkSysIntegration="+pkSysIntegration);
}
function rowrender(event,rows){
	 var data = rows.data;
	 var rowElements = rows.rowEl;
	 $.each(rowElements,function(i,trobj){
		$.each($(trobj).children(),function(j,tdobj){
			//$(tdobj).css("line-height","26px");
			if(j==6){
				var txt = $(tdobj).text();
				if(txt=='停止'){
					$(tdobj).css("color","red");
				}
			}
		});
	}); 
}
function reset(){
	$("#formpanel").formpanel('reset');
}



</script>
</head>
<body>
	<div class="title_nav">当前位置：权限管理 > <span>角色管理</span></div>
	   	<div name="formpanel" id="formpanel" vtype="formpanel" titledisplay="false" width="100%" layout="table" showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*']}" >
			<div name='systemName' id='systemName' vtype="textfield" label="子系统名称" labelalign="right" labelwidth="120" width="90%" editable="true"></div>
			<div name='systemCode' id='systemCode' vtype="textfield" label="子系统编码" labelalign="right" labelwidth="120" width="90%" editable="true"></div>
			<div id="toolbar" name="toolbar" vtype="toolbar" >		
		    	<div id="btn3" name="btn3" vtype="button" align="center" defaultview="1"  text="查 询"  click="querySomeMenu()"></div>
		    	<div id="btn4" name="btn4" vtype="button" align="center" defaultview="1"  text="重 置"  click="reset()"></div>
		    </div>
	  	</div>
	   	<div vtype="gridpanel" name="gridpanel" id="gridpanel" width="100%" layout="fit" showborder="false" datarender="fixColumn()" 
		 		rowselectable="false" titledisplay="false"  isshowselecthelper="true" selecttype="0">
		 	<div name="toolbar" vtype="toolbar">
			</div>		 	
			<div vtype="gridcolumn" name="grid_column" id="grid_column">
				<div> <!-- 单行表头 -->
					<div name="pkSysIntegration" key="true" visible="false"></div>
					<div name="systemName"  text="子系统名称" textalign="center" sort="true" width="20%"></div>
					<div name="systemCode" text="子系统编码" textalign="center" sort="true" width="10%"></div>
					<div name="integratedUrl" text="集成URL" textalign="center" sort="true" width="30%"></div>
					<div name="systemState" text="子系统运行状态" textalign="center" sort="true" dataurl='[{"text": "启用","value": "0"},{"text": "停止", "value": "1"}]' width="10%"></div>
					<div name="createrTime" text="创建时间" textalign="center" sort="true" datatype="date" dataformat="YYYY-MM-DD" width="19%"></div>
					<div name="fix" text="操作" textalign="center"  ></div>
				</div>
			</div>
			<!-- 表格 -->
			<div vtype="gridtable" name="grid_table" id="grid_table" rowrender="rowrender()"></div>
			<!-- 分页 -->
			<div vtype="paginator" name="grid_paginator" id="grid_paginator"></div>
	    </div>		
  
</body>

</html>