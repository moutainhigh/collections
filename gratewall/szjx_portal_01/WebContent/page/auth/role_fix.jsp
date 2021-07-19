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

	var pkSysIntegration;
	var systemName;
	function initData(res){
		 pkSysIntegration = res.getAttr("pkSysIntegration");
	}
	$(function() {
		queryFuncName(pkSysIntegration);
		var params = {
				url : rootPath+'/auth/getSystemName.do?pkSysIntegration='+pkSysIntegration,
				callback : function(data, r, res) {
					if (res.getAttr("back") == 'success') {
						systemName=res.getAttr("systemName");
						$("#formpanel").formpanel('option','title',"<"+systemName+"> 角色管理");
						$("#gridpanel").gridpanel('option','title',"<"+systemName+">"+"角色");	
						queryRole();
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
				url : rootPath+'/auth/queryRolecName.do?pkSysIntegration='+pkSysIntegration,
				callback : function(data, r, res) {
				$('#functionName').autocompletecomboxfield('option', 'dataurl',res.getAttr("functionName"));
				}
			};
			$.DataAdapter.submit(params);		
		
	}
	function queryRole() {
		$('div[name="gridpanel"]').gridpanel('option', 'dataurl',rootPath+'/auth/getRolebyPKsyInte.do?pkSysIntegration='+pkSysIntegration);
		$('div[name="gridpanel"]').gridpanel('reload');
	}

	function deletefunction(){
		var selected = $('div[name="gridpanel"]').gridpanel('getSelection');
		if (selected == null || selected.length<1 ){
			jazz.info("请选中至少一个目标");
		}else if(selected.length>=1){
			jazz.confirm("是否删除选中项？", function(){
				var params = {
						url : rootPath+'/auth/deleteSomeRole.do',
						components: ['gridpanel'],
						callback : function(data, r, res) { 
							if (res.getAttr("back") == 'success'){
								queryRole();
								jazz.info("删除成功");
							}
						}
				}
				$.DataAdapter.submit(params);
			}, function(){})
		}
	}	
	function queryOnefunction(){//查看一个功能
	    var data = $('div[name="gridpanel"]').gridpanel('getSelection'); 
	    if(data.length==1){ 
	    	pkRole = data[0]["pkRole"]; 
			type="see";
			roleCrud();
	    }else{ 
	        alert("请选择一条要查询的数据"); 
	    } 		
	}
	var type;
	var pkRole;
	function addrole(){
		type="add";
		roleCrud();
	}
	var win1;
	function alterRole(){
		
	    var data = $('div[name="gridpanel"]').gridpanel('getSelection'); 
	    if(data.length==1){ 
	    	pkRole = data[0]["pkRole"]; 
			type="update";
			roleCrud();
	    }else{ 
	        alert("请选择一条要查询的数据"); 
	    } 		
	}		
	function roleCrud(){
		win1 = jazz.widget({
	   	     vtype: 'window',
	   	     frameurl: rootPath+'/auth/getRole_crudHtml.do?pkSysIntegration='+pkSysIntegration+'&type='+type+'&pkRole='+pkRole,
	  			name: 'win',
	  	    	title: '角色维护',
	  	    	titlealign: 'center',
	  	    	titledisplay: true,
	  			maximize : true, //默认最大化展开
	  			closestate : true, //true关闭   false隐藏 
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});		
	}

	function queryOneFunc(){//通过PK主键来选择
		var functionName =$("#functionName").autocompletecomboxfield('getValue');
		$('div[name="gridpanel"]').gridpanel('option', 'dataurl',rootPath+'/auth/getSomeRole.do?pkSysIntegration='+pkSysIntegration);
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
	function goback(){//返回
		$("iframe", parent.document).attr("src", rootPath+'/auth/getRole_manageHtml.do');
		//$("#column_id1").panel("option", "frameurl",rootPath+'/page/auth/role_manage.jsp');
		
	}
	function fixColumn(event, obj){//操作部分代码

 		var data = obj.data;
		for(var i=0;i<data.length;i++){
			data[i]["fix"] = '<div class="jazz-grid-cell-inner">'
								+'<a href="javascript:void(0);" title="删除" onclick="deleteOne(\''+data[i]["pkRole"]+'\');"><img src="'+rootPath+'/static/images/other/close.png" width="13px" height="13px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
								+'<a href="javascript:void(0);" title ="修改" onclick="editorRole(\''+data[i]["pkRole"]+'\');"><img src="'+rootPath+'/static/images/other/forma-1.png" width="11px" height="11px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
								+'<a href="javascript:void(0);" title="查看" onclick="viewRole(\''+data[i]["pkRole"]+'\');"><img src="'+rootPath+'/static/images/other/mag.png" width="11px" height="12px" border="0"/></a>'
								+'</div>';
		}
		return data; 
	}
	function editorRole(r1){//修改
    	pkRole =r1;
		type="update";
		roleCrud();
	}
	function viewRole(r1){//查看角色
    	pkRole = r1; 
		type="see";
		roleCrud();		
	}
	function deleteOne(pkid){
	   	jazz.confirm('是否确认删除，删除后不可恢复',
			    function(){
			   		$('#gridpanel').gridpanel('removeRowById',pkid);
			   		var params = {
			   			url:rootPath+"/auth/deleteOneRole.do",
			   			params: {pkId:pkid},
			   			callback : function(data,obj,res) {
			   				
			   			}
			   		};
			   		$.DataAdapter.submit(params);
			   	},
			   	function(){
			   		return;
			   	}
	);   
	}
</script>
</head>
<body>
	<div class="title_nav">当前位置：权限管理 > <span>角色管理</span></div>
	<div vtype="panel" width="100%" height="100%" layout="fit" titledisplay="false" bgcolor="white" showborder="true">
	   	<div name="formpanel" id="formpanel" vtype="formpanel" titledisplay="true" width="100%" layout="table" showborder="false" titleicon="../../static/images/other/notepad-.png"
		     layoutconfig="{cols:2, columnwidth: ['50%','*']}" title="角色查询" toggleable="true" height="147">
       			<div name='roleName' id='functionName' vtype="autocompletecomboxfield" label="菜单名称" labelalign="right" 
       				 width="300"  dataurl="" minquerylength="0">
       			</div> 
				<div id="toolbar1" name="toolbar1" vtype="toolbar" location="bottom" align="center">
					<div name="query_button" align="center" defaultview="1" vtype="button" text="查询"
						icon="../query/queryssuo.png" onclick="queryOneFunc();"></div>						
					<div name="reset_button" defaultview="1" vtype="button" text="返回"
						icon="../query/queryssuo.png" align="center" click="goback();"></div>
				</div>
			</div>
	   	<div vtype="gridpanel" name="gridpanel" id="gridpanel" width="100%" layout="fit" showborder="false" datarender="fixColumn()" dataurl="<%=contextpath%>/integration/findByListIntegration.do"
		 	titledisplay="false"  isshowselecthelper="true" >
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="left">
<!-- 			<div name="alter_button" vtype="button" text="修改" defaultview="1" align="center"
				width="55" height="23" icon="../query/queryssuo.png" onclick="alterRole();"></div>				
			<div name="query_button" vtype="button" text="删除" defaultview="1" align="center"
				width="55" height="23"icon="../query/queryssuo.png" onclick="deletefunction();"></div>			
			<div name="query_button" vtype="button" text="查看" defaultview="1" align="center"
				width="55" height="23"icon="../query/queryssuo.png" onclick="queryOnefunction();"></div> -->		
			<div id="add_button" name="add_button" vtype="button" align="right" defaultview="0" 
				width="55" height="23" iconurl="<%=contextpath%>/static/images/other/gridadd.png" click="addrole()"></div>									
			
		</div>
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column">
			<div>
				<!-- 单行表头 -->
				<div name='roleName' text="角色名称" textalign="center" width='15%'></div>
				<div name='roleCode' text="角色代码" key="true" visible="true" textalign="center" width='10%'></div>				
				<div name='pkRole' text="角色表主键" textalign="center" width='15%'></div>				
				<div name='pkSysIntegration' text=系统集成表主键 textalign="center" width='10%'></div>				
				<div name='roleType' text="角色类型" textalign="center" width='15%'
					datatype="comboxfield" 
					dataurl='[{"text":"子系统管理员","value":"1"},{"text":"一般角色","value":"0"}]'
				></div>
				<div name='roleState' text="角色状态" textalign="center" width='10%'
				></div>				
				<div name='orderNo' text="序号" textalign="center" width='10%'
				></div>
				<div name='fix' text="操作" textalign="center" width='15%'
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
</body>

</html>