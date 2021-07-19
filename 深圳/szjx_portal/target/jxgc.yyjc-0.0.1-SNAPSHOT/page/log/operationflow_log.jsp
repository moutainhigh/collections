<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>业务流程日志</title>
<%
	String contextpath = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=contextpath %>/static/script/jazz.js" type="text/javascript"></script>
<script>
	
	$(function(){
		query();
	});
	
	function query(){
		$('div[name="gridpanel"]').gridpanel('option','dataurl',rootPath+'/log/queryFlow.do');
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
	<div class="title_nav">当前位置：日志管理 > <span>业务流程日志</span></div>
	  
		   <div name="formpanel" id="formpanel" vtype="formpanel" titledisplay="false" width="100%" layout="table" showborder="false" 
			     layoutconfig="{cols:2, columnwidth: ['50%','*']}"  title="业务流程日志查询" >
				<div name='participantName' vtype="textfield" label="参与者姓名" labelalign="right" labelwidth="120" width="90%" editable="true"></div>
				<div name='businessName' vtype="textfield" label="工作项名称" labelalign="right" labelwidth="120" width="90%" editable="true"></div>
				<div id="toolbar" name="toolbar" vtype="toolbar" >		
			    	<div id="btn3" name="btn3" vtype="button" text="查 询" align="center" defaultview="1" click="query()"></div>
			    	<div id="btn4" name="btn4" vtype="button" text="重 置" align="center" defaultview="1" click="reset()"></div>
			    </div>
		   </div>
		   <div vtype="gridpanel" name="gridpanel" id="gridpanel" width="100%" height="100%" layout="fit" showborder="false"
			 		titledisplay="false"  isshowselecthelper="true" selecttype="0" rowselectable="false">
			 	 <div id="toolbar" name="toolbar" vtype="toolbar" >		
			    	
			    </div>
			 	 <div vtype="gridcolumn" name="grid_column" id="grid_column">
					<div> <!-- 单行表头 -->
						<div name="pkFlowLog" key="true" visible="false"></div>
						<div name="businessName"  text="工作项名称" textalign="center" sort="true" width="10%"></div>
						<div name="participantName" text="参与者" textalign="center" sort="true" width="10%"></div>
						<div name="processName" text="流程名称" textalign="center" sort="true" width="14%"></div>
						<div name="activityName" text="活动名称" textalign="center" sort="true" width="10%"></div>
						<div name="startTime" text="开始时间" textalign="center" sort="true" datatype="date" dataformat="YYYY-MM-DD HH:mm:ss" width="15%"></div>
						<div name="endTime" text="完成时间" textalign="center" sort="true" datatype="date" dataformat="YYYY-MM-DD HH:mm:ss" width="15%"></div>
						<div name="timeLimit" text="时限(天)" textalign="center" width="10%"></div>
						<div name="activityState" text="状态" textalign="center" dataurl='[{"text":"成功","value":"0"},{"text":"失败","value":"1"}]' ></div>
						<div name="systemCode" text="业务子系统" textalign="center" dataurl="<%=contextpath%>/log/queryLogCodeValue.do?flag=systemCode" width="10%"></div>
					</div>
				 </div>
				 <!-- 表格 -->
				 <div vtype="gridtable" name="grid_table" id="grid_table" rowrender="rowrender()"></div>
				 <!-- 分页 -->
				 <div vtype="paginator" name="grid_paginator" id="grid_paginator"></div>
		 </div>
			
	  
	
	

</body>
</html>