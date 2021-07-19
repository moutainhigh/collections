<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>异常日志查看</title>
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
		$('div[name="gridpanel"]').gridpanel('option','dataurl',rootPath+'/log/queryException.do');
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
	<div class="title_nav">当前位置：日志管理 > <span>操作日志</span></div>
	  
		   <div name="formpanel" id="formpanel" vtype="formpanel" titledisplay="false" width="100%" layout="table" showborder="false" 
			     layoutconfig="{cols:2, columnwidth: ['50%','*']}"  title="操作日志查询" >
				<div name='createrName' id='createrName' vtype="textfield" label="操作人姓名" labelalign="right" labelwidth="120" width="90%" editable="true"></div>
				<div name='operationTime' id='operationTime' vtype="datefield" label="操作时间" labelalign="right" labelwidth="120" width="90%" editable="true"></div>
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
						<div name="pkOperationLog" key="true" visible="false"></div>
						<div name="createrName"  text="操作人" textalign="center" sort="true" width="13%"></div>
						<div name="organName" text="机构名称" textalign="center" sort="true" width="13%"></div>
						<div name="systemCode" text="业务子系统" textalign="center" dataurl="<%=contextpath%>/log/queryLogCodeValue.do?flag=systemCode" width="10%"></div>
						<div name="functionCode" text="操作功能" textalign="center" dataurl="<%=contextpath%>/log/queryLogCodeValue.do?flag=functionCode"  width="10%"></div>
						<div name="operationType" text="操作类型" textalign="center" sort="true" width="13%"></div>
						<div name="operationTime" text="操作时间" textalign="center" sort="true" datatype="date" dataformat="YYYY-MM-DD HH:mm:ss" width="10%"></div>
						<div name="operationState" text="状态" textalign="center" dataurl='[{"text":"成功","value":"0"},{"text":"失败","value":"1"}]' ></div>
						<div name="exceptionCode" text="异常码" textalign="center"  width="10%"></div>
						<div name="exceptionDesc" text="异常描述"  textalign="center" width="9%"></div>
					
					</div>
				</div>
				<!-- 表格 -->
				<div vtype="gridtable" name="grid_table" id="grid_table" rowrender="rowrender()"></div>
				<!-- 分页 -->
				<div vtype="paginator" name="grid_paginator" id="grid_paginator"></div>
		</div>
			
	   

</body>
</html>