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
		
/* 		$('div[name="serviceCode"]').autocompletecomboxfield('option','dataurl',rootPath+'/log/requestServiceCode.do');
		$('div[name="serviceCode"]').autocompletecomboxfield('reload');  */
		/* 		$('div[name="resPkSys"]').comboxfield('option','dataurl',rootPath+'/log/targetSystem.do');
		$('div[name="resPkSys"]').comboxfield('reload'); */
		
		query();
		
	});
	
	function query(){
		$('div[name="gridpanel"]').gridpanel('option','dataurl',rootPath+'/log/queryOsbLog.do');
		$('div[name="gridpanel"]').gridpanel('query', [ 'formpanel']);
	}
	
	function reset(){
		$('div[name="formpanel"]').formpanel('reset');
	}
	
	function renderdata (event, obj){

		var data = obj.data;
 		for(var i=0;i<data.length;i++){
				data[i]["requestUrl"] = '<a href="javascript:void(0)" title="查看" onclick="checkData(\''+data[i]["pkOsbLog"]+'\')">'+data[i]["requestUrl"]+' </a>';
		} 

		return data;
	}
	function checkData(pkid){
		var title = "查看OSB日志详细信息";
		var frameurl= ""+'/log/osblogDetail.do?pkOsbLog='+pkid;
		createNewWindow(title,frameurl);
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
	<div class="title_nav">当前位置：日志管理 > <span>接口调用日志</span></div>
	  
		   <div name="formpanel" id="formpanel" vtype="formpanel" titledisplay="false" width="100%" layout="table" 
			     layoutconfig="{cols:2, columnwidth: ['30%','40%','*']}" title="接口调用日志查询" showborder="false" >
				<div name='serviceCode' vtype="autocompletecomboxfield" label="服务代码" labelalign="right" labelwidth="120" width="90%" editable="true"></div>
				<div name='requestUrl' vtype="autocompletecomboxfield" label="调用地址" labelalign="right" labelwidth="120" width="90%" editable="true"></div>
				<div name='requestMeth' vtype="autocompletecomboxfield" label="调用方法" labelalign="right" labelwidth="120" width="90%" editable="true"></div>
				<div id="toolbar" name="toolbar" vtype="toolbar" >		
			    	<div id="btn3" name="btn3" vtype="button" text="查 询" align="center" defaultview="1" click="query()"></div>
			    	<div id="btn4" name="btn4" vtype="button" text="重 置" align="center" defaultview="1" click="reset()"></div>
			    </div>
		   </div>
			 		
		<div vtype="gridpanel" name="gridpanel" width="100%" showborder="false" isshowselecthelper="true" 
		selecttype="0" datarender="renderdata()" dataurl="" layout="fit" titledisplay="false" rowselectable="false">
			     <div name="toolbar" vtype="toolbar">
			
			     </div>
			 	 <div vtype="gridcolumn" name="grid_column" id="grid_column">
					<div> <!-- 单行表头 -->
						<div name="pkOsbLog" key="true" visible="false"></div>
						<div name="serviceCode"  text="服务代码" textalign="center" sort="true" width="10%"></div>
						<div name="requestUrl" text="调用地址" textalign="center" sort="true" width="10%"></div>
						<div name="requestMeth" text="调用方法" textalign="center" sort="true" width="10%"></div>
						<div name="requestIp" text="请求方ip" textalign="center" width="10%"></div>
						<div name="inboundpar" text="传入参数" textalign="center" sort="true" width="9%"></div>
						<div name="starttime" text="开始时间" textalign="center" sort="true" datatype="date" dataformat="YYYY-MM-DD HH:mm:ss" width="10%"></div>
						<div name="outboutparamms" text="返回参数" textalign="center" sort="true" width="10%"></div>
						<div name="endtime" text="结束时间" textalign="center" width="10%"></div>
						<div name="issuccess" text="状态" textalign="center" dataurl='[{"text":"成功","value":"Y"},{"text":"失败","value":"N"}]' ></div>
					</div>
				 </div>
				 <!-- 表格 -->
				 <div vtype="gridtable" name="grid_table" id="grid_table" rowrender="rowrender()"></div>
				 <!-- 分页 -->
				 <div vtype="paginator" name="grid_paginator" id="grid_paginator"></div>
		  </div>
			
	  
	

</body>
</html>