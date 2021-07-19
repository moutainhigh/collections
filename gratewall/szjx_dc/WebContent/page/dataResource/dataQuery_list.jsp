<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<title></title>
<%
	String contextpath = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
 
<script>
	
	$(function(){
		$(document).keydown(function(event) {
			if (event.keyCode == 13) {
				query();
			}
		}); 
		query();
	});
	
	function query(){
		$('div[name="gridpanel"]').gridpanel('option','dataurl',rootPath+'/dataQuery/query.do');
		$('div[name="gridpanel"]').gridpanel('query',['formpanel']);
	}
	
	function reset(){
		$('div[name="formpanel"]').formpanel('reset');
	}
	
	function leave(){
		win.window('close'); 
		 
	}
	
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
 <div class="title_nav">当前位置：资源管理> 数据资源管理> <span>元数据查询</span></div>
 
 
    
 	<div vtype="formpanel" id="formpanel" name="formpanel" titledisplay="false" width="100%" layout="table"  showborder="false" 
				layoutconfig="{cols:2, columnwidth: ['50%','*']}" title="元数据查询" >
		<div name='tableNameEn' vtype="textfield" label="表名" labelalign="right" width="70%" labelwidth="150"></div>
		<div name='tableNameCn' vtype="textfield" label="表中文名" labelalign="right" width="70%" labelwidth="150" ></div>
		<div name='columnNameEn' vtype="textfield" label="字段名" labelalign="right" width="70%" labelwidth="150"></div>
		<div name='columnNameCn' vtype="textfield" label="字段中文名" labelalign="right" width="70%" labelwidth="150" ></div>
		
		<div id="toolbar" name="toolbar" vtype="toolbar" >		
		    	<div id="btn3" name="btn3" vtype="button" align="center" defaultview="1" text="查 询"  click="query()"></div>
		    	<div id="btn4" name="btn4" vtype="button" align="center" defaultview="1" text="重 置"  click="reset()"></div>
	   </div>
	</div>
	
	<div vtype="gridpanel" name="gridpanel" width="100%" showborder="false" isshowselecthelper="true" 
		selecttype="0" dataurl="" layout="fit" titledisplay="false" rowselectable="false">
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<!-- 单行表头 -->
				<!--  <div name='pkDcColumn' key="true" visible="false" ></div> -->
				<div name='dcTopic' text="业务主题" textalign="center" sort="true" width='20%' dataurl="<%=contextpath%>/page/tableManager/DcTopic.json"></div> 
				<div name='tableNameEn' text="表名"  textalign="center" sort="true" width='20%'></div>
				<div name='tableNameCn' text="表中文名"  textalign="center" sort="true" width='20%'></div>
				<div name='columnNameEn' text="字段名" textalign="center" sort="true" width='20%'></div>
				<div name='columnNameCn' text="字段中文名" textalign="center" sort="true" width='20%'></div>				
			</div>
		</div>
		<!-- 表格 -->
		<div vtype="gridtable" name="grid_table" rowrender="rowrender()"></div>
		<!-- 分页 -->

		<div vtype="paginator" name="grid_paginator" id="grid_paginator"></div>
	</div>

 
</body>
</html>
