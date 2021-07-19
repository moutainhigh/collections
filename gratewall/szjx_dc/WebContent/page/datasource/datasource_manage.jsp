<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据源管理</title>
<%
	String contextpath = request.getContextPath();
%>

<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<script>

$(function(){
 	queryMenu();
 	$('div[name="dataSourceType"]').on("comboxfieldchange",function(event, ui){ 
 	    if(ui.newValue!='DB'){ 
 	    	$('div[name="dataSourceName"]').textfield('reset');
 	        $('div[name="dataSourceName"]').textfield("option","disabled", true); 
 	    }else{ 
 	        $('div[name="dataSourceName"]').textfield("option","disabled",false); 
 	    } 
 	}); 
	$(document).keydown(function(event) {
		if (event.keyCode == 13) {
			querySomeMenu();
		}
	}); 
});
function queryMenu() {
	$('div[name="gridpanel"]').gridpanel('option', 'dataurl',rootPath+'/dataSource/getDataSourceManagerMenu.do');
	$('div[name="gridpanel"]').gridpanel('reload');
}
function queryCurrentPage(){
	
	$('div[name="gridpanel"]').gridpanel('reloadCurrentPage');
}


function querySomeMenu(){

 		var systemName =$("#systemName").textfield('getValue');		
		$('div[name="gridpanel"]').gridpanel('option', 'dataurl',rootPath+'/dataSource/getSomeDataSourceManagerMenu.do');
		$('div[name="gridpanel"]').gridpanel('query', [ 'formpanel']);	 
}

function fixColumn(event, obj) {// 维护按钮
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var htm ='<div class="jazz-grid-cell-inner">'
			+'<a href="javascript:void(0);" title="编辑" onclick="editorDataSource(\''+data[i]["pkDcDataSource"]+'\');"><img src="'+rootPath+'/static/images/other/forma-1.png" width="11px" height="11px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
			//+'<a href="javascript:void(0);" title="测试连接" onclick="connectStatus(\''+data[i]["pkDcDataSource"]+'\');"><img src="'+rootPath+'/static/images/other/fengbei.png" width="11px" height="11px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
			+'<a href="javascript:void(0);" title="查看" onclick="viewDataSource(\''+data[i]["pkDcDataSource"]+'\');"><img src="'+rootPath+'/static/images/other/mag.png" width="11px" height="12px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
			+'<a href="javascript:void(0);" title="删除" onclick="deleteDataSource(\''+data[i]["pkDcDataSource"]+'\');"><img src="'+rootPath+'/static/images/other/close.png" width="13px" height="13px" border="0"/></a>'
			+'</div>';
		data[i]["fix"] = htm; 
	}
	return data;
}
function deleteDataSource(pkDcDataSource){
   	jazz.confirm('是否确认删除，删除后不可恢复',
		    function(){
		   		$('#gridpanel').gridpanel('removeRowById',pkDcDataSource);
		   		var params = {
		   			url:rootPath+"/dataSource/deleteDataSource.do",
		   			params: {pkId:pkDcDataSource},
		   			callback : function(data,obj,res) {
						if (res.getAttr("back") == 'success') {
							jazz.info("删除成功");
							queryMenu();
						} else {
							jazz.info("删除失败");
						}		   				
		   			}
		   		};
		   		$.DataAdapter.submit(params);
		   	},
		   	function(){
		   		return;
		   	}
	);   	
}
function add(){
 	var title="新增数据源";
	var type="add";
	var frameurl=""+'/dataSource/getDataSource_addHtml.do?type='+type;
	
	createNewWindow(title,frameurl); 
}
function editorDataSource(pkDcDataSource){
	var title="编辑数据源";
	var type="update";
	var frameurl=""+'/dataSource/getDataSource_addHtml.do?type='+type+'&pkDcDataSource='+pkDcDataSource;
	
	createNewWindow(title,frameurl);
}
function viewDataSource(pkDcDataSource){
	var title="查看数据源";
	var type="view";
	var frameurl=""+'/dataSource/getDataSource_addHtml.do?type='+type+'&pkDcDataSource='+pkDcDataSource;
	createNewWindow(title,frameurl);	
}
function connectStatus(pkDcDataSource){
		var params = {
	   			url:rootPath+"/dataSource/DataSourceTestbyPK.do",
	   			params: {pkId:pkDcDataSource},
	   			callback : function(data,obj,res) {
	   				if (res.getAttr("back") == 'success') {
	   					
	   					jazz.info("成功连接 ！！");
	   		
	   				
	   				} else {
	   					jazz.info("连接不成功，请检查用户名、密码等是否正确！！");
	   				}		   				
	   			}
	   		};
	   		$.DataAdapter.submit(params);	
}
var win;
function createNewWindow(title,frameurl){
    win = top.jazz.widget({ 
    	  
        vtype: 'window', 
        name: 'win', 
        title: title, 
        width: 750, 
        height: 430, 
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
	querySomeMenu();
}
function reset(){
	$("#formpanel").formpanel('reset');
    //$('div[name="dataSourceName"]').textfield("option","disabled", true); 
}

</script>
</head>
<body>
	<div class="title_nav">当前位置：资源管理 > <span>数据源管理</span></div>
	   	<div name="formpanel" id="formpanel" vtype="formpanel" titledisplay="false" width="100%" layout="table" showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*']}" >
			<!--  <div name='dataSourceType' id="dataSourceType" vtype="comboxfield" label="数据源类型" labelwidth="80"   labelAlign="left"
			 		width="95%"		dataurl="<%=contextpath%>/dataSource/findKeyValueDcDmDstypeBO.do" ></div> -->
			<div name='dataSourceName' id="dataSourceName" vtype="textfield" label="数据源名称" labelwidth="80"   labelAlign="left" maxlength="50" 
			 		width="95%"		></div>			 		
			<div name='pkDcBusiObject' id='pkDcBusiObject' vtype="comboxfield" label="业务系统" labelalign="left" labelwidth="90" width="97%"  dataurl="<%=contextpath%>/dataSource/findKeyValueDcBusiObjectBO.do"></div>
			<div id="toolbar" name="toolbar" vtype="toolbar" >			
		    	<div id="btn3" name="btn3" vtype="button" align="center" defaultview="1" text="查 询"  click="querySomeMenu()"></div>
		    	<div id="btn4" name="btn4" vtype="button" align="center" defaultview="1" text="重 置"  click="reset()"></div>
		    </div>
	  	</div>
	   	<div vtype="gridpanel" name="gridpanel" id="gridpanel" width="100%" layout="fit" showborder="false" datarender="fixColumn()"  
		 		rowselectable="false" titledisplay="false"  isshowselecthelper="true" selecttype="0">
		 	<div name="toolbar" vtype="toolbar">
		 		<div id="add_button" name="add_button" vtype="button" align="right" iconurl="<%=contextpath%>/static/images/other/gridadd3.png" text="增加" click="add()"></div>
			</div>
			<div vtype="gridcolumn" name="grid_column" id="grid_column">
				<div> <!-- 单行表头 -->
					<div name="pkDcDataSource" key="true" visible="false"></div>
					<div name="dataSourceName" text="数据源名称" textalign="center" sort="true"  width="14%"></div>
					<!--  <div name="dataSourceType" text="数据源类型" textalign="center" sort="true"  width="14%"
					  dataurl="<%=contextpath%>/dataSource/findKeyValueDcDmDstypeBO.do"
					></div> -->
			
					<div name="pkDcBusiObject"  text="业务系统" textalign="center" sort="true" width="14%"
						datatype="comboxfield" dataurl="<%=contextpath%>/dataSource/findKeyValueDcBusiObjectBO.do" 
					></div>
					
					<div name="dataSourceIp" text="数据源IP" textalign="center" sort="true" width="12%"
					></div>					
					<div name="createrName" text="创建人" textalign="center" sort="true" width="14%"
					></div>
					<div name="createrTime" text="创建时间" textalign="center" sort="true" datatype="date" dataformat="YYYY-MM-DD" width="10%"></div>					
					<div name="fix" text="操作" textalign="center"  ></div>
				</div>
			</div>
			<!-- 表格 -->
			<div vtype="gridtable" name="grid_table" id="grid_table" rowrender=""></div>
			<!-- 分页 -->
			<div vtype="paginator" name="grid_paginator" id="grid_paginator"></div>
	    </div>		
</body>

</html>