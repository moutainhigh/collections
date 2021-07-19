<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>消息管理</title>
<%
	String contextpath = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=contextpath %>/static/script/jazz.js" type="text/javascript"></script>
<script>
    
	$(function(){
		
		query();
		
		
	});
	
	function date2str(x,y) { 
		var z = {M:x.getMonth()+1,d:x.getDate(),h:x.getHours(),m:x.getMinutes(),s:x.getSeconds()}; 
		y = y.replace(/(M+|d+|h+|m+|s+)/g,function(v) {
				return ((v.length>1?"0":"")+eval('z.'+v.slice(-1))).slice(-2)
			}); 
		return y.replace(/(y+)/g,function(v) {
				return x.getFullYear().toString().slice(-v.length)
			}); 
	} 
	currentTime = date2str(new Date(),"yyyy-MM-dd");
	//alert(currentTime); 
		
	
	
	
	
	function query(){
		$('div[name="gridpanel"]').gridpanel('option','dataurl',rootPath+'/message/query.do');
		$('div[name="gridpanel"]').gridpanel('query', [ 'formpanel']);
	}
	
	
	function reset(){
		$('div[name="formpanel"]').formpanel('reset');
	}
	
	var winEdit;
	function addData(){
		winEdit = top.jazz.widget({
	   	     vtype: 'window',
	   	     frameurl: rootPath+'/message/add.do',
	  			name: 'win',
	  	    	title: '新增消息',
	  	    	titledisplay: true,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true,
	  	        minimizable : true, //是否显示最小化按钮
	  		    showborder : false, //true显示窗体边框    false不显示窗体边
	  		    //maximize : true, //默认最大化展开
	  		    closestate : true, //true关闭   false隐藏 
	  		 	titleicon : "<%=contextpath%>/static/images/other/notepad-.png",
	  		  	width: 750, 
	          	height: 500, 
	   		});
		
	}
	
	function renderdata(event, obj){
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			if(data[i]["effectiveTime"]>currentTime||data[i]["effectiveTime"] == currentTime){
				/* alert(data[i]["effectiveTime"]); */
				data[i]["custom"] = '<div class="jazz-grid-cell-inner">'
								+'<a href="javascript:void(0);" title="编辑" onclick="updateData(\''+data[i]["pkNotice"]+'\');"><img src="'+rootPath+'/static/images/other/forma-1.png" width="11px" height="11px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
								+'</div>';
			}
			else{
				data[i]["custom"] = '<div class="jazz-grid-cell-inner">'
					+'<a href="javascript:void(0);" onclick="informData(\''+data[i]+'\');"><img src="'+rootPath+'/static/images/other/forma-1.png" width="11px" height="11px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
					+'</div>';
			}
		}
		return data;
	}
	
	
	function updateData(pkNotice){
		winEdit = top.jazz.widget({
	   	     vtype: 'window',
	   	     frameurl: rootPath+'/message/update.do?pkNotice='+pkNotice,
	  			name: 'win',
	  	    	title: '维护消息',
	  	    	titledisplay: true,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true,
	  	        minimizable : true, //是否显示最小化按钮
	  		    showborder : false, //true显示窗体边框    false不显示窗体边
	  		   // maximize : true, //默认最大化展开
	  		    closestate : true, //true关闭   false隐藏 
	  		    titleicon : "<%=contextpath%>/static/images/other/notepad-.png",
	  		  	width: 750, 
	          	height: 500, 
	   		});
		
	}
	
	function informData(){
		 jazz.warn('已过期!');
		
	}
	
	function leave(){
		winEdit.window('close'); 
		 
	}

	
	
	
	
</script>
</head>
<body>
<div class="title_nav">当前位置：消息管理 </div>
   
 	<div vtype="formpanel" id="formpanel" name="formpanel" titledisplay="false" width="100%" layout="table"  showborder="false" 
				layoutconfig="{cols:2, columnwidth: ['50%','*']}" title="消息查询">

		<div name='pkNotice' vtype="hiddenfield" label="主键" labelalign="right" width="80%" labelwidth="200"></div>
		<div name='title' vtype="textfield" label="标题" labelalign="right" width="80%" labelwidth="200"></div>
		<div name='createrTime' vtype="datefield" label="创建时间" labelalign="right" width="80%" labelwidth="200"></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" >		
			    <div id="btn3" name="btn3" vtype="button" text="查 询" align="center" defaultview="1" click="query()"></div>
			    <div id="btn4" name="btn4" vtype="button" text="重 置" align="center" defaultview="1" click="reset()"></div>
	    </div>
	</div>
	
	<div vtype="gridpanel" name="gridpanel" width="100%" showborder="false" isshowselecthelper="true" 
		selecttype="0"  datarender="renderdata()" dataurl="" layout="fit" rowselectable="false">
		<div name="toolbar" vtype="toolbar">
			<div id="add_button" name="add_button" vtype="button" align="right" text="增加" 
				iconurl="<%=contextpath %>/static/images/other/gridadd3.png" click="addData()"></div>
			
			
		</div>
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<!-- 单行表头 -->
				<div name='pkNotice' key="true" visible="false" ></div>
				<div name='title' text="标题" textalign="center" sort="true" width='25%'></div>
				<div name='content' text="内容" textalign="center" sort="true" width='25%'></div>
				<!-- <div name='sendTo' text="发送对象" textalign="center" sort="true" width='12%'></div> -->
				<div name='effectiveTime' text="截止日期" textalign="center" sort="true" width='19%' datatype="date" dataformat="YYYY-MM-DD"></div>
				<!-- <div name='createrId' text="创建人" textalign="center" sort="true" width='9%'></div>	 -->
				<div name='createrTime' text="创建时间" textalign="center" sort="true" width='19%' datatype="date" dataformat="YYYY-MM-DD"></div>
				<!-- <div name='modifierId' text="最后修改人" textalign="center" sort="true" width='9%'></div>
				<div name='modifierTime' text="最后修改时间" textalign="center" sort="true" width='14%'></div> -->
				<div name="custom" text="操作" textalign="center" ></div>
			</div>
		</div>
		<!-- 表格 -->
		<div vtype="gridtable" name="grid_table"></div>
		<!-- 分页 -->

		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
	</div>
	
 
</body>
</html>