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

$(function(){
	queryMenu();

	$(document).keydown(function(event) {
		if (event.keyCode == 13) {
			querySomeMenu();
		}
	});
});
function queryMenu() {
	$('div[name="gridpanel"]').gridpanel('option', 'dataurl',rootPath+'/auth/getServiceMenu.do');
	$('div[name="gridpanel"]').gridpanel('reload');
}
function queryCurrentPage(){
	
	$('div[name="gridpanel"]').gridpanel('reloadCurrentPage');
}


function querySomeMenu(){
		var systemName =$("#systemName").textfield('getValue');		
		$('div[name="gridpanel"]').gridpanel('option', 'dataurl',rootPath+'/auth/getSomeServiceMenu.do');
		$('div[name="gridpanel"]').gridpanel('query', [ 'formpanel']);	
}


function downloadData(pkid){
	
	window.location.href =  rootPath+"/auth/downloadDoc.do?pkid="+pkid;
	
}
function fixColumn(event, obj) {// 维护按钮
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var htm ='<div class="jazz-grid-cell-inner">'
			+'<a href="javascript:void(0);" title="编辑" onclick="editorServices(\''+data[i]["pkSmServices"]+'\');"><img src="'+rootPath+'/static/images/other/forma-1.png" width="11px" height="11px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
/* 			+'<a href="javascript:void(0);" title="分配权限" onclick="authMenu(\''+data[i]["pkSmServices"]+'\');"><img src="'+rootPath+'/static/images/other/fengbei.png" width="11px" height="11px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
 */			+'<a href="javascript:void(0);" title="查看" onclick="viewServices(\''+data[i]["pkSmServices"]+'\');"><img src="'+rootPath+'/static/images/other/mag.png" width="11px" height="12px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
			+'<a href="javascript:void(0);" title="删除" onclick="deleteServices(\''+data[i]["pkSmServices"]+'\');"><img src="'+rootPath+'/static/images/other/close.png" width="13px" height="13px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
			+'<a href="javascript:void(0);" title="下载接口操作文档" onclick="downloadData(\''+data[i]["pkSmServices"]+'\');">下载 </a>'
			+'</div>';
		data[i]["fix"] = htm; 
	}
	return data;
}
function deleteServices(pkSmServices){
   	jazz.confirm('是否确认删除，删除后不可恢复',
		    function(){
		   		$('#gridpanel').gridpanel('removeRowById',pkSmServices);
		   		var params = {
		   			url:rootPath+"/auth/deleteSmServices.do",
		   			params: {pkId:pkSmServices},
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
	var title="新增服务";
	var type="add";
	var frameurl=""+'/auth/getService_addHtml.do?type='+type;
	
	createNewWindow(title,frameurl);
}
function editorServices(pkSmServices){
	var title="编辑服务";
	var type="update";
	var frameurl=""+'/auth/getService_addHtml.do?type='+type+'&pkSmServices='+pkSmServices;
	var height = 600;
	createNewWindow(title,frameurl,height);
}
function viewServices(pkSmServices){
	var title="查看服务";
	var type="view";
	var frameurl=""+'/auth/getService_addHtml.do?type='+type+'&pkSmServices='+pkSmServices;
	createNewWindow(title,frameurl,580);	
}
function authMenu(pkSmServices){
	var title="分配权限";
	var frameurl=""+'/auth/getService_system_authHtml.do?pkSmServices='+pkSmServices;
	
	createNewWindow(title,frameurl);
}
var win;
function createNewWindow(title,frameurl,height){
	var hgt = 0;
	if(height==""||height==undefined){
		hgt = 560;
	}else{
		hgt = height;
	}
    win = top.jazz.widget({ 
        vtype: 'window', 
        name: 'win', 
        title: title, 
        width: 750, 
        height: hgt, 
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
function reset(){
	$("#formpanel").formpanel('reset');
}
</script>
</head>
<body>
	<div class="title_nav">当前位置：权限管理 > <span>服务管理</span></div>
	   	<div name="formpanel" id="formpanel" vtype="formpanel" titledisplay="false" width="100%" layout="table" showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*']}" >
			<div name='serviceName' id='serviceName' vtype="textfield" label="服务名称" labelalign="right" labelwidth="120" width="90%" editable="true"></div>
			<div name='pkSysIntegration' id="pkSysIntegration" vtype="comboxfield" label="所属系统" labelwidth="120"   labelAlign="right"
			 		width="90%"		dataurl="<%=contextpath%>/auth/findServicePkSysIntegration.do" ></div>
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
					<div name="pkSmServices" key="true" visible="false"></div>
					<div name="serviceName" text="服务名称" textalign="center" sort="true"  width="14%"></div>
					<div name="serviceNo" text="服务编号" textalign="center" sort="true"  width="10%"></div>
					<div name="agreementType" text="协议类型" textalign="center" sort="true"	datatype="comboxfield"				
					 	dataurl="<%=contextpath%>/auth/findAgreementType.do" width="10%"></div>					
					<div name="pkSysIntegration"  text="所属系统" textalign="center" sort="true" width="14%"
						datatype="comboxfield" dataurl="<%=contextpath%>/auth/findServicePkSysIntegration.do" 
					></div>
					
					
					<div name="pkSmFirm" text="厂商" textalign="center" sort="true" width="14%"
						datatype="comboxfield" dataurl="<%=contextpath%>/auth/findServicePkSmFirme.do"
					></div>
					<div name="pkSmLikeman" text="联系人" textalign="center" sort="true" width="12%"
						dataurl="<%=contextpath%>/auth/findServicePkSmLikeman.do" datatype="comboxfield"
					></div>
				
<%-- 					<div name="serviceType" text="服务类型" textalign="center" sort="true"	datatype="comboxfield"				
					 	dataurl="<%=contextpath%>/auth/findServiceType.do" width="9%"></div>	
					<div name="createrTime" text="创建时间" textalign="center" sort="true" datatype="date" dataformat="YYYY-MM-DD" width="10%"></div>
 --%>
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