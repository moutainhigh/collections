<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单管理-菜单维护</title>
<%
	String contextpath = request.getContextPath();
%>

<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>

<script>

var pkSysIntegration;
var systemName;
	function initData(res){
		 pkSysIntegration = res.getAttr("pkSysIntegration");
	}
	$(function() {
		$("#btn1").hide();
		queryFuncName(pkSysIntegration);
		var params = {
				url : rootPath+'/auth/getSystemName.do?pkSysIntegration='+pkSysIntegration,
				callback : function(data, r, res) {
					if (res.getAttr("back") == 'success') {
						systemName=res.getAttr("systemName");
						$("#formpanel").formpanel('option','title',"维护：<"+systemName+"> 系统");
						$("#gridpanel").gridpanel('option','title',"<"+systemName+">"+"子功能菜单");
						queryFuncMenu();
					} else {
					
					}
				}
			};
			$.DataAdapter.submit(params);		

			$(document).keydown(function(event) {
				if (event.keyCode == 13) {
					queryOneFunc();
				}
			});			
	});
	function queryFuncName(pkSysIntegration){// 填入FORM中
		var params = {
				url : rootPath+'/auth/queryFuncName.do?pkSysIntegration='+pkSysIntegration,
				callback : function(data, r, res) {
				$('#functionName').autocompletecomboxfield('option', 'dataurl',res.getAttr("functionName"));
				}
			};
			$.DataAdapter.submit(params);		
/* 		$('#formpanel').formpanel('option', 'dataurl',rootPath+
				'/auth/queryFuncName.do?pkSysIntegration='+pkSysIntegration);
		$('#formpanel').formpanel('reload'); */			
		
	}
	function queryFuncMenu() {
		$('div[name="gridpanel"]').gridpanel('option', 'dataurl',rootPath+'/auth/getFuncMenu.do?pkSysIntegration='+pkSysIntegration);
		$('div[name="gridpanel"]').gridpanel('reload');
	}
	var win;
	function addfunction(){
		//alert("功能新增菜单");
		win = jazz.widget({
	   	     vtype: 'window',
	   	     frameurl: rootPath+'/auth/getMenu_crudHtml.do?pkSysIntegration='+pkSysIntegration,
	  			name: 'win',
	  	    	title: '功能新增菜单',
	  	    	titlealign: 'center',
	  			maximize : true, //默认最大化展开
	  			closestate : true, //true关闭   false隐藏 
	  	    	titledisplay: true,
	  	        width: 700,
	  	        height: 500,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});
	}
	var alterwin;
	function alterwinfunction(pkFunction){
		alterwin = jazz.widget({
	   	     vtype: 'window',
	   	     frameurl: rootPath+'/auth/getMenu_crudHtml.do?pkSysIntegration='+pkSysIntegration+'&pkFunction='+pkFunction,
	  			name: 'win',
	  	    	title: '修改功能菜单',
	  			maximize : true, //默认最大化展开
	  			closestate : true, //true关闭   false隐藏 
	  	    	titlealign: 'center',
	  	    	titledisplay: true,
	  	        width: 700,
	  	        height: 500,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});		
	}
	var querywin;
	function querywinfunction(pkFunction,queryone){
		//alert(pkFunction);
		querywin = jazz.widget({
	   	     vtype: 'window',
	   	     frameurl: rootPath+'/auth/getMenu_crudHtml.do?pkSysIntegration='+pkSysIntegration+'&pkFunction='+pkFunction+'&queryone='+queryone,
	  			name: 'win',
	  	    	title: '查询单个功能',
	  			maximize : true, //默认最大化展开
	  			closestate : true, //true关闭   false隐藏 
	  	    	titlealign: 'center',
	  	    	titledisplay: true,
	  	        width: 700,
	  	        height: 450,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});		
	}	
	function alterfunction(){
		var selected = $('div[name="gridpanel"]').gridpanel('getSelection');
		//var date=selected.data;		
		if (selected == null || selected.length>1 ){
			jazz.info("请选中一个目标");
		}else if(selected.length=1){
			var pkFunction= selected[0]["pkFunction"];
			alterwinfunction(pkFunction)
		}		
	}	
	function deletefunction(){
		var selected = $('div[name="gridpanel"]').gridpanel('getSelection');
		if (selected == null || selected.length<1 ){
			jazz.info("请选中至少一个目标");
		}else if(selected.length>=1){
			jazz.confirm("是否删除选中项？", function(){
				var params = {
						url : rootPath+'/auth/deleteSomeFunc.do',
						components: ['gridpanel'],
						callback : function(data, r, res) { 
							if (res.getAttr("back") == 'success'){
								queryFuncMenu();
								jazz.info("删除成功");
							}
						}
				}
				$.DataAdapter.submit(params);
			}, function(){})
		}
	}	
	function queryOnefunction(){//查看一个功能
		var selected = $('div[name="gridpanel"]').gridpanel('getSelection');
		//var date=selected.data;		
		if (selected == null || selected.length>1 ){
			jazz.info("请选中一个目标，且只能选中一个目标");
		}else if(selected.length=1){
			var pkFunction= selected[0]["pkFunction"];
			var queryone='yes';
			querywinfunction(pkFunction,queryone)
		}	
	}
	function queryOneFunc(){//通过PK主键来选择
		var functionName =$("#functionName").autocompletecomboxfield('getValue');
		$('div[name="gridpanel"]').gridpanel('option', 'dataurl',rootPath+'/auth/getSomeFuncMenu.do?pkSysIntegration='+pkSysIntegration+'&funcName='+functionName);
		$('div[name="gridpanel"]').gridpanel('query', [ 'formpanel']);	
					
	}
	function querySomeFunc(){
		var funcName =$("#funcName").textfield('getValue');
		//alert(funcName);
		$('div[name="gridpanel"]').gridpanel('option', 'dataurl',rootPath+'/auth/getFuncMenu.do?pkSysIntegration='+pkSysIntegration+'&funcName='+funcName);
		$('div[name="gridpanel"]').gridpanel('reload');	
	}	
	function closeWin(){
		parent.win.window("close");
	}
	function reset() {
		$("#formpanel").formpanel('reset');
	}
	function fixColumn(event, obj) {
		var data = obj.data;
		for(var i=0;i<data.length;i++){//新增
			var htm = '<a href="javascript:void(0);" onclick="addOnefunction(\''+data[i]["pkFunction"]+'\',\''+data[i]["functionUrl"]+'\')"><img src="'+rootPath+'/static/images/other/forma-1.png" width="11px" height="11px" border="0"/>'
				+ "" + '</a>';
			data[i]["fix"] = htm; 
	/* 		var zyjs = data[i]["zyjs"];
			if(zyjs==null || zyjs=='')
				data[i]["zyjs"]= '普通用户'; */
		}
		return data;
	}
	function addOnefunction(pkFunction){
		addOnefunction(pkFunction)
	}
	function addOnefunction(pkFunction,url){
		//alert(pkFunction);
		if(url!='#'){
			jazz.info("请选择一个包");		
		}else{
			win = jazz.widget({
		   	     vtype: 'window',
		   	     frameurl: rootPath+'/auth/getMenu_crudHtml.do?pkSysIntegration='+pkSysIntegration+'&superFunction='+pkFunction,
		  			name: 'win',
		  			maximize : true, //默认最大化展开
		  			closestate : true, //true关闭   false隐藏 
		  	    	title: '功能新增菜单',
		  	    	titlealign: 'center',
		  	    	titledisplay: true,
		  	        width: 700,
		  	        height: 500,
		  	        modal:true,
		  	        visible: true,
		  	      	resizable: true
		   		}); 		
		}

	}
	function goback(){//返回           /auth/getMenu_manageHtml.do
		$("iframe", parent.document).attr("src", rootPath+'/auth/getMenu_manageHtml.do');
		//$("#column_id1").panel("option", "frameurl",rootPath+'/page/auth/menu_manage.jsp');
		
	}
</script>
</head>
<body>
<div id="column_id1" width="100%" height="100%" vtype="panel" name="panel" layout="row" layoutconfig="{height:['120', '*']}">
		<div>
			<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
				titledisplay="true" width="100%" layout="table"  showborder="false"
				height="100%" layoutconfig="{cols:2, columnwidth: ['50%','*']}" title="菜单查询">

       			<div name='functionName' id='functionName' vtype="autocompletecomboxfield" label="菜单名称" labelalign="right" 
       				 width="300"  dataurl="" minquerylength="0">
       			</div> 
				<div id="toolbar1" name="toolbar1" vtype="toolbar" location="bottom" align="center">
<!-- 					<div name="query_button" vtype="button" text="查询"
						icon="../query/queryssuo.png" onclick="querySomeFunc();"></div> -->
					<div name="query_button" vtype="button" text="查询"
						align="center" icon="" defaultview="1" onclick="queryOneFunc();"></div>						
					<div name="reset_button" vtype="button" text="返回"
						align="center" icon="" defaultview="1" click="goback();"></div>
				</div>
			</div>
		</div>
	<div>		
 	<div vtype="gridpanel" name="gridpanel" id="gridpanel" height="100%" width="100%" datarender="fixColumn" linenowidth="50" showborder="false"
		titledisplay="false"  title="菜单管理" dataurl="" layout="fit">
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="left">
			<div name="add_new" vtype="button" text="新增"
				align="center"  icon=""  defaultview="1" onclick="addfunction();"></div>
			<div name="alter_button" vtype="button" text="修改"
				align="center" icon="" defaultview="1" onclick="alterfunction();"></div>				
			<div name="query_button" vtype="button" text="删除"
				align="center" icon="" defaultview="1" onclick="deletefunction();"></div>
			
			<div name="query_button" vtype="button" text="查看"
				align="center" icon="" defaultview="1" onclick="queryOnefunction();"></div>			
			<div name="query_button" id="btn1" vtype="button" text="."
				align="" icon="" ></div>			
		</div>
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column">
			<div>
				<!-- 单行表头 -->
				<div name='functionName' text="菜单名称" textalign="center" width='15%'></div>
				<div name='pkFunction' text="菜单代码" textalign="center" width='10%'></div>				
				<div name='functionCode' text="功能代码" textalign="center" width='15%'></div>				
				<div name='pkSysIntegration' text=系统集成表主键 textalign="center" width='0%'></div>	
				<div name='levelCode' text="层次码" textalign="center" width='10%'></div>								
				<div name='functionUrl' text="功能URL" textalign="center" width='15%'></div>
				<div name='superFuncCode' text="上级功能代码" textalign="center" width='10%'
				></div>				
				<div name='orderNo' text="序号" textalign="center" width='10%'
				></div>
				
				<div name='fix' text="操作" textalign="center" width='13%'
				></div>

			</div>
		</div>
		<!-- 表格 -->
		<!-- ../../grid/reg3.json -->
		<div vtype="gridtable" name="grid_table"></div>
		<!-- 分页 -->

		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
	</div>
	</div>
</div>
</body>
</html>