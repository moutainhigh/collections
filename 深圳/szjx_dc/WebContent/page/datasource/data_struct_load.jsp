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
//判断数据装载 状态 防止 二次装载
// true表示正在进行装载
var allDate =false;
var allViews=false;
var allPro=false;
var allTri=false;
/*  console.log("控制台输出----------------------"); */
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
			+'<a href="javascript:void(0);" title="装载表" onclick="LoadTable(\''+data[i]["pkDcDataSource"]+'\');">装载表</a>&nbsp;&nbsp;&nbsp;'
			+'<a href="javascript:void(0);" title="装载视图" onclick="LoadViews(\''+data[i]["pkDcDataSource"]+'\');">视图</a>&nbsp;&nbsp;&nbsp;'
			+'<a href="javascript:void(0);" title="装载存储过程" onclick="LoadProcedure(\''+data[i]["pkDcDataSource"]+'\');">存储过程</a>&nbsp;&nbsp;&nbsp;'
			+'<a href="javascript:void(0);" title="装载触发器" onclick="LoadTrigger(\''+data[i]["pkDcDataSource"]+'\');">触发器</a>'
			+'</div>';
		data[i]["fix"] = htm; 
	}
	return data;
}

//验证数据源连接状态  返回true为连接正常
function connectStatus(pkDcDataSource){
	 var t1 =false;
		$.ajax({
			url:rootPath+"/dataSource/DataSourceTestbyPKAjax.do",
			data:{
				pkId : pkDcDataSource
			},
			type:"post",
			async:false,
			success:function(res){
				if(res.back=='success'){
					t1=true;
				}else{
					
					jazz.error(res.msg);
					t1= false;
				}
			}
		});
	return t1;

}
function LoadALL(pkDcDataSource){
	//防止多次加载
	if(!allDate){
		allDate=true;
	}else{
		//jazz.info("数据正在加载，请稍候再试！");
		return;
	}
	if(connectStatus(pkDcDataSource)){//判断连接是否正常 

   		var params = {
	   			url:rootPath+"/dataSource/saveAll.do",
	   			params: {pkId:pkDcDataSource},
	   			callback : function(data,obj,res) {
	   				//加载完成 把状态设置为容许再次加载；
	   				allDate=false;
					if (res.getAttr("back") == 'success') {
						jazz.info("加载成功");
						//queryMenu();
					} else {
						jazz.info("加载失败");
					}		   				
	   			}
	   		};
	   		$.DataAdapter.submit(params);	
	}
}
function LoadProcedure(pkDcDataSource){
	//防止多次加载
	if(!allPro){
		allPro=true;
	}else{
		//jazz.info("数据正在加载，请稍候再试！");
		return;
	}
	if(connectStatus(pkDcDataSource)){//判断连接是否正常 
   		var params = {
	   			url:rootPath+"/dataSource/saveProcedure.do",
	   			params: {pkId:pkDcDataSource},
	   			callback : function(data,obj,res) {	  
	   				allPro=false;
					if (res.getAttr("back") == 'success') {
						jazz.info("加载成功");
						//queryMenu();
					} else {
						jazz.info("加载失败");
					}		   				
	   			}
	   		};
	   		$.DataAdapter.submit(params);	
	}
}
function LoadTable(pkDcDataSource){
	if(connectStatus(pkDcDataSource)){
		var title="加载表结构";
		var type="table";
		var frameurl=""+'/dataSource/getdata_struct_dual.do?type='+type+'&pkDcDataSource='+pkDcDataSource;
		createNewWindow(title,frameurl); 	
		
	}
}
//加载视图 
function LoadViews(pkDcDataSource){
	//防止多次加载
	if(!allViews){
		allViews=true;
	}else{
		//jazz.info("数据正在加载，请稍候再试！");
		return;
	}
	if(connectStatus(pkDcDataSource)){//判断连接是否正常 
   		var params = {
	   			url:rootPath+"/dataSource/saveViews.do",
	   			params: {pkId:pkDcDataSource},
	   			callback : function(data,obj,res) {
	   				allViews=false;
					if (res.getAttr("back") == 'success') {
						jazz.info("加载成功");
					} else {
						jazz.info("加载失败");
					}		   				
	   			}
	   		};
	   		$.DataAdapter.submit(params);	
	}
}

//加载触发器
function LoadTrigger(pkDcDataSource){
	if(!allTri){
		allTri=true;
	}else{
		//jazz.info("数据正在加载，请稍候再试！");
		return;
	}
	if(connectStatus(pkDcDataSource)){//判断连接是否正常 
   		var params = {
	   			url:rootPath+"/dataSource/saveTrigger.do",
	   			params: {pkId:pkDcDataSource},
	   			callback : function(data,obj,res) {
	   				allTri=false;
					if (res.getAttr("back") == 'success') {
						jazz.info("加载成功");
						//queryMenu();
					} else {
						jazz.info("加载失败");
					}		   				
	   			}
	   		};
	   		$.DataAdapter.submit(params);	
	}
}




var win;
function createNewWindow(title,frameurl){
    win = top.jazz.widget({ 
    	  
        vtype: 'window', 
        name: 'win', 
        title: title, 
        width: 750, 
        height: 530, 
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
	<div class="title_nav">当前位置：资源管理 > <span>数据结构装载</span></div>
	   	<div name="formpanel" id="formpanel" vtype="formpanel" titledisplay="false" width="100%" layout="table" showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*']}" >
			<!--  <div name='dataSourceType' id="dataSourceType" vtype="comboxfield" label="数据源类型" labelwidth="80"   labelAlign="left"
			 		width="95%"		dataurl="<%=contextpath%>/dataSource/findKeyValueDcDmDstypeBO.do" ></div> -->
			<div name='dataSourceName' id="dataSourceName" vtype="textfield" label="数据源名称" labelwidth="80"   labelAlign="right"  maxlength="50" 
			 		width="95%"		></div>			 		
			<div name='pkDcBusiObject' id='pkDcBusiObject' vtype="comboxfield" label="业务系统" labelalign="right" labelwidth="90" width="95%"  dataurl="<%=contextpath%>/dataSource/findKeyValueDcBusiObjectBO.do"></div>
			<div id="toolbar" name="toolbar" vtype="toolbar" >			
		    	<div id="btn3" name="btn3" vtype="button" align="center" defaultview="1" text="查 询"  click="querySomeMenu()"></div>
		    	<div id="btn4" name="btn4" vtype="button" align="center" defaultview="1" text="重 置"  click="reset()"></div>
		    </div>
	  	</div>
	   	<div vtype="gridpanel" name="gridpanel" id="gridpanel" width="100%" layout="fit" showborder="false" datarender="fixColumn()" 
		 		rowselectable="false" titledisplay="false"  isshowselecthelper="true" selecttype="0">
		 	<div name="toolbar" vtype="toolbar">
			</div>
			<div vtype="gridcolumn" name="grid_column" id="grid_column">
				<div> <!-- 单行表头 -->
					<div name="pkDcDataSource" key="true" visible="false"></div>
					<div name="dataSourceName" text="数据源名称" textalign="center" sort="true"  width="12%"></div>
					<!--  <div name="dataSourceType" text="数据源类型" textalign="center" sort="true"  width="12%"
					  dataurl="<%=contextpath%>/dataSource/findKeyValueDcDmDstypeBO.do"
					></div> -->
			
					<div name="pkDcBusiObject"  text="业务系统" textalign="center" sort="true" width="12%"
						datatype="comboxfield" dataurl="<%=contextpath%>/dataSource/findKeyValueDcBusiObjectBO.do" 
					></div>
					
					<div name="dataSourceIp" text="数据源IP" textalign="center" sort="true" width="12%"
					></div>					
					<div name="lastModifierName" text="最后加载人" textalign="center" sort="true" width="10%"
					></div>
					<div name="lastLoadTime" text="最后加载时间" textalign="center" sort="true" datatype="date" dataformat="YYYY-MM-DD" width="10%"></div>					
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