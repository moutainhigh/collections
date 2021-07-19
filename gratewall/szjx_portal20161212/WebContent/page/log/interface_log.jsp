<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>接口调用日志</title>
<%
	String contextpath = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=contextpath %>/static/script/jazz.js" type="text/javascript"></script>
<script>
	
	$(function() {
		
		$('div[name="reqPkSys"]').comboxfield('option','dataurl',rootPath+'/log/requestSystem.do');
		$('div[name="reqPkSys"]').comboxfield('reload');
		$('div[name="resPkSys"]').comboxfield('option','dataurl',rootPath+'/log/targetSystem.do');
		$('div[name="resPkSys"]').comboxfield('reload');
		
		query();
		
	});
	
	function query(){
		$('div[name="gridpanel"]').gridpanel('option','dataurl',rootPath+'/log/queryInterface.do');
		$('div[name="gridpanel"]').gridpanel('query', [ 'formpanel']);
	}
	
	function reset(){
		$('div[name="formpanel"]').formpanel('reset');
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
	<div class="title_nav">当前位置：日志管理 > <span>接口调用日志</span></div>
	  
		   <div name="formpanel" id="formpanel" vtype="formpanel" titledisplay="false" width="100%" layout="table" 
			     layoutconfig="{cols:2, columnwidth: ['50%','*']}" title="接口调用日志查询" showborder="false" >
				<div name='reqPkSys' vtype="comboxfield" label="请求系统" labelalign="right" labelwidth="120" width="90%" editable="true"></div>
				<div name='resPkSys' vtype="comboxfield" label="目标系统" labelalign="right" labelwidth="120" width="90%" editable="true"></div>
				<div id="toolbar" name="toolbar" vtype="toolbar" >		
			    	<div id="btn3" name="btn3" vtype="button" text="查 询" align="center" defaultview="1" click="query()"></div>
			    	<div id="btn4" name="btn4" vtype="button" text="重 置" align="center" defaultview="1" click="reset()"></div>
			    </div>
		   </div>
		   <div vtype="gridpanel" name="gridpanel" id="gridpanel"  width="100%" height="100%" layout="fit" showborder="false"
			 		titledisplay="false"  isshowselecthelper="true" selecttype="0" rowselectable="false">
			     <div name="toolbar" vtype="toolbar">
			
			     </div>
			 	 <div vtype="gridcolumn" name="grid_column" id="grid_column">
					<div> <!-- 单行表头 -->
						<div name="pkInterfaceLog" key="true" visible="false"></div>
						<div name="serviceName"  text="任务名称" textalign="center" sort="true" width="10%"></div>
						<div name="reqSysName" text="请求系统" textalign="center" sort="true" width="10%"></div>
						<div name="resSysName" text="目标系统" textalign="center" sort="true" width="10%"></div>
						<div name="interfaceType" text="接口类型" textalign="center" sort="true" width="9%"></div>
						<div name="operationType" text="请求开始时间" textalign="center" sort="true" datatype="date" dataformat="YYYY-MM-DD HH:mm:ss" width="10%"></div>
						<div name="operationTime" text="耗时(秒)" textalign="center" sort="true" width="10%"></div>
						<div name="operationState" text="数据量(条)" textalign="center" width="10%"></div>
						<div name="operationState" text="请求报文" textalign="center" width="10%"></div>
						<div name="operationState" text="响应报文" textalign="center" width="10%"></div>
						<div name="interfaceState" text="状态" textalign="center" dataurl='[{"text":"成功","value":"0"},{"text":"失败","value":"1"}]' ></div>
					</div>
				 </div>
				 <!-- 表格 -->
				 <div vtype="gridtable" name="grid_table" id="grid_table" rowrender="rowrender()"></div>
				 <!-- 分页 -->
				 <div vtype="paginator" name="grid_paginator" id="grid_paginator"></div>
		  </div>
			
	  
	

</body>
</html>