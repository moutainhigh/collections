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
		$('div[name="pkDcBusiObject"]').comboxfield('option','dataurl',rootPath+'/proceList/queryBusiObjectName.do');
		$('div[name="pkDcBusiObject"]').comboxfield('reload');
	});
	
	function query(){
		$('div[name="gridpanel"]').gridpanel('option','dataurl',rootPath+'/proceList/query.do');
		$('div[name="gridpanel"]').gridpanel('query',['formpanel']);
	}
	
	
	function renderdata(event, obj){
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			data[i]["custom"] = '<div class="jazz-grid-cell-inner">'
								+'<a href="javascript:void(0);" title="管理" onclick="updateData(\''+data[i]["pkDcProcedure"]+'\');"><img src="'+rootPath+'/static/images/other/forma-1.png" width="11px" height="11px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
								+'</div>';
			data[i]["procName"] = '<a href="javascript:void(0)" title="查看" onclick="checkData(\''+data[i]["pkDcProcedure"]+'\')">'+data[i]["procName"]+' </a>';
		}
		return data;
	}
	
	var win;
	function createNewWindow(title,frameurl){
	    win = top.jazz.widget({ 
	    	  
	        vtype: 'window', 
	        name: 'win', 
	        title: title, 
	        width: 700, 
	        height: 450, 
	        modal:true, 
	        visible: true ,
			showborder : true, //true显示窗体边框    false不显示窗体边
			closestate: false,
			minimizable : true, //是否显示最小化按钮
			titleicon : "<%=contextpath%>/static/images/other/notepad-.png",
			frameurl: rootPath+frameurl
	    }); 
	}
	
	function updateData(pkid){
		var title = "修改存储过程信息";
		var type = "update";
		var frameurl= ""+'/proceList/updateProce.do?pkDcProcedure='+pkid+'&type='+type;
		createNewWindow(title,frameurl);
		
		
	}
	
	function checkData(pkid){
		var title = "查看存储过程信息";
		var type = "check";
		var frameurl= ""+'/proceList/checkProce.do?pkDcProcedure='+pkid+'&type='+type;
		createNewWindow(title,frameurl);
		
	}
	
	function reset(){
		$('div[name="formpanel"]').formpanel('reset');
	}
	
	function leave(){
		win.window('close'); 
		 
	}
	
	
	
	
</script>
</head>
<body>
 <div class="title_nav">当前位置：资源管理> 数据资源管理> <span>存储过程管理</span></div>
 
 
    
 	<div vtype="formpanel" id="formpanel" name="formpanel" titledisplay="false" width="100%" layout="table"  showborder="false" 
				layoutconfig="{cols:2, columnwidth: ['50%','*']}" title="存储过程查询" >

		<!--  <div name='dataSourceName' vtype="textfield" label="数据源" labelalign="right" width="90%" labelwidth="120"></div> -->
		<div name='procName' vtype="textfield" label="存储过程名称" labelalign="right" width="90%" labelwidth="120" ></div>
		<div name='pkDcBusiObject' vtype="comboxfield" label="业务系统" labelalign="right" width="90%" labelwidth="120"></div>
		
		<div id="toolbar" name="toolbar" vtype="toolbar" >		
		    	<div id="btn3" name="btn3" vtype="button" align="center" defaultview="1" text="查 询"  click="query()"></div>
		    	<div id="btn4" name="btn4" vtype="button" align="center" defaultview="1" text="重 置"  click="reset()"></div>
	   </div>
	</div>
	
	<div vtype="gridpanel" name="gridpanel" width="100%" showborder="false" isshowselecthelper="true" 
		selecttype="0" datarender="renderdata()" dataurl="" layout="fit" titledisplay="false" rowselectable="false">
		<div name="toolbar" vtype="toolbar">
			
		</div>
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<!-- 单行表头 -->
				<div name='pkDcProcedure' key="true" visible="false" ></div>
				<!-- <div name='dataSourceName' text="数据源"  textalign="center" sort="true" width='15%'></div> -->
				<div name='busiObjectName' text="业务系统" textalign="center" sort="true" width='15%'></div>
				<div name='procName' text="存储过程名称" textalign="center" sort="true" width='20%'></div>
				<div name='procUseDesc' text="存储过程用途" textalign="center" sort="true" width='55%'></div> 
				<!-- <div name='isCheck' text="是否管理" textalign="center" sort="true" width='10%' dataurl='[{"text": "是","value": "Y"},{"text": "否", "value": "N"}]'></div> -->
				<div name="custom" text="操作" textalign="center" ></div>
			</div>
		</div>
		<!-- 表格 -->
		<div vtype="gridtable" name="grid_table"></div>
		<!-- 分页 -->

		<div vtype="paginator" name="grid_paginator" id="grid_paginator"></div>
	</div>

 
</body>
</html>
