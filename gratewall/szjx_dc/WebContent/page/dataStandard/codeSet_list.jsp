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

	function reset(){
		$('div[name="formpanel"]').formpanel('reset');
	}
	
	function query(){
		
		$('div[name="gridpanel"]').gridpanel('option','dataurl',rootPath+'/codeList/query.do');
		$('div[name="gridpanel"]').gridpanel('query', [ 'formpanel']);
	}
	
	function renderdata(event, obj){
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			data[i]["custom"] = '<div class="jazz-grid-cell-inner">'
								+'<a href="javascript:void(0);" title="编辑" onclick="updateData(\''+data[i]["standardCodeindex"]+'\');"><img src="'+rootPath+'/static/images/other/forma-1.png" width="11px" height="11px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
								+'<a href="javascript:void(0);" title="标准代码集" onclick="details(\''+data[i]["standardCodeindex"]+'\');"><img src="'+rootPath+'/static/images/other/mag.png" width="11px" height="12px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
								+'<a href="javascript:void(0);" title="删除" onclick="deleteData(\''+data[i]["standardCodeindex"]+'\');"><img src="'+rootPath+'/static/images/other/close.png" width="13px" height="13px" border="0"/></a>'
								+'</div>';
			data[i]["standardCodeindex"] = '<a href="javascript:void(0)" title="查看" onclick="checkData(\''+data[i]["standardCodeindex"]+'\')">'+data[i]["standardCodeindex"]+' </a>';
		}
		return data;
	}
	
	
	
	function deleteData(standardCodeindex){
		
		jazz.confirm("是否确认删除，删除后不可恢复", function(){
			var params = {
					url : rootPath+'/codeList/delete.do',
					params: {standardCodeindex:standardCodeindex},
					callback : function(data, r, res) { 
						if (res.getAttr("back") == 'success'){
							query();
							jazz.info("删除成功");
						}
					}
			}
			$.DataAdapter.submit(params);
		})
	
	}
	
	function details(standardCodeindex){
		$("iframe", parent.document).attr("src", rootPath+'/codeDataList/getCodeDataPage.do?standardCodeindex='+standardCodeindex);
	}

	
	var win;
	function createNewWindow(title,frameurl,winWidth,winHeight){
		var width = 0;
		var height = 0;
		if(winWidth==""||winWidth==undefined){
			width = 680;
		}else{
			width = winWidth;
		}
		
		if(winHeight==""||winHeight==undefined){
			height = 380;
		}else{
			height = winHeight;
		}
		
		
	    win = top.jazz.widget({ 
	    	  
	        vtype: 'window', 
	        name: 'win', 
	        title: title, 
	        width: width, 
	        height: height, 
	        modal:true, 
	        visible: true ,
			showborder : true, //true显示窗体边框    false不显示窗体边
			closestate: false,
			minimizable : true, //是否显示最小化按钮
			titleicon : "<%=contextpath%>/static/images/other/notepad-.png",
			frameurl: rootPath+frameurl
	    }); 
	}
	function addData(){
		var title = "新增标准代码集";
		var type = "add";
		var frameurl= ""+'/codeList/addCodeSet.do?type='+type;
		createNewWindow(title,frameurl,680,450);
	}
	
	function updateData(standardCodeindex){
		var title="编辑标准代码集";
		var type="update";
		var frameurl= ""+'/codeList/addCodeSet.do?type='+type+'&standardCodeindex='+standardCodeindex;
		createNewWindow(title,frameurl,680,450);
	}
	
	function checkData(standardCodeindex){
		var title = "查看标准代码集";
		var type = "check";
		var frameurl= ""+'/codeList/addCodeSet.do?type='+type+'&standardCodeindex='+standardCodeindex;
		createNewWindow(title,frameurl,680,450);
	}
	
	
	function leave(){
		win.window('close'); 
		 
	}
	
</script>
<style type="text/css">
	.container{width: 100%;background: #F8F9FB;}
	.cwrap{width: 980px;margin: 0 auto;}
</style>
</head>
<body>
 <div class="title_nav">当前位置：资源管理> 数据标准管理> <span>标准代码集管理</span></div>
 
 
     <div class="container">
    <div class="cwrap">
 	<div vtype="formpanel" id="formpanel" name="formpanel" titledisplay="false" width="100%" layout="table"  showborder="false" 
				layoutconfig="{cols:2, columnwidth: ['50%','*']}" title="标准代码集查询" >

		<div name='standardCodeindex' vtype="textfield" label="标识符" labelalign="right"  width="94%" labelwidth="70"></div>
		<div name='codeindexName' vtype="textfield" label="代码集名称" labelalign="right" width="96%" labelwidth="80" ></div>
		
		<div id="toolbar" name="toolbar" vtype="toolbar" >		
		    	<div id="btn3" name="btn3" vtype="button" align="center" defaultview="1" text="查 询"  click="query()"></div>
		    	<div id="btn4" name="btn4" vtype="button" align="center" defaultview="1" text="重 置"  click="reset()"></div>
	   </div>
	</div>
	</div>
	</div>
	
	<div vtype="gridpanel" name="gridpanel" width="100%" showborder="false" isshowselecthelper="true" 
		selecttype="0" datarender="renderdata()" dataurl="" layout="fit" titledisplay="false" rowselectable="false">
		<div name="toolbar" vtype="toolbar">
			<div id="add_button" name="add_button" vtype="button" align="right" text="增加" 
				iconurl="<%=contextpath %>/static/images/other/gridadd3.png" click="addData()"></div>
			
			
		</div>
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<!-- 单行表头 -->
				<div name='standardCodeindex' text="标识符"  textalign="center" sort="true" width='15%'></div>
				<div name='codeindexName' text="代码集名称" textalign="center" sort="true" width='17%'></div>
				<div name='standardType' text="代码集标准" textalign="center" sort="true" width='17%'></div>
				<div name='representation' text="表示" textalign="center" sort="true" width='15%'></div> 
				<div name='version' text="版本" textalign="center" sort="true" width='17%'></div>
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
