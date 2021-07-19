<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>服务管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<style type="text/css">
td{
	text-align: center;
}
.jazz-pagearea{
	height: 0px;
}
#west {
	border-top: 2px solid #3498db;
}
.jazz-column-border{
	border-top: 2px solid #3498db;
}
</style>
<script>

var updaserviceid;
var setting = {
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pid"
			}
		}, 
		callback: {
			onClick: treeOnClick
		}
};
var gnId, //当前选中的功能ID
gnMc, //当前选中的功能名称
sjgnId, //当前选中功能的上级功能ID
sjgnMc; //当前选中功能的上级功能名称
function treeOnClick(event, treeId, treeNode){

	gnId = treeNode.id;
	/* var gnIdtemp = treeNode.id;
	alert(gnIdtemp);
	if(gnId == gnIdtemp){
		//$(this).removeClass("curSelectedNode");
		gnId=null;
	}else{
		gnId = gnIdtemp;
		alert(gnId);
	} */
	/* clearForm();
	gnId = treeNode.id;
	gnMc = gnId=="0"? "" : treeNode.name ;
	var pnode = treeNode.getParentNode();
	sjgnId = pnode.id;
	sjgnMc = sjgnId=="0"? "无" : pnode.name;
	
	$('#formpanel').formpanel('reset');
	$('#formpanel').formpanel('option', 'dataurl', rootPath+'/auth/funcDetail.do?gnId='+gnId);
	$('#formpanel').formpanel('reload', null, function(){
		$("#sjgnMc").textfield("setValue", sjgnMc);			
	});
	
	$('#urlGrid').gridpanel('option', 'dataurl', rootPath+'/auth/queryFuncUrl.do?gnId='+gnId);
	$('#urlGrid').gridpanel('reload');
	
	$('#zjGrid').gridpanel('option', 'dataurl', rootPath+'/auth/queryFuncZj.do?gnId='+gnId);
	$('#zjGrid').gridpanel('reload'); */
	queryUrl();
}
var tree;
$(document).ready(function(){
	refreshFuncTree();
	queryUrl();
	//$("#border_id").panel("layoutSwitch", "west", 0);
	/* $("#testover").mouseleave(function(){
		//$("#funcTree_div").show(300);
	}).mouseenter(function(){
		//$("#funcTree_div").hide(300);
		if($("#funcTree_div").is(":hidden")){
			$("#funcTree_div").show(300);
		}else{
			$("#funcTree_div").hide(300);
		}
	}); */
});
function refreshFuncTree(){
	gnId='', gnMc='',sjgnId='',sjgnMc='';
	var params = {
		url : rootPath+'/org/queryOrgTree.do',
		callback : function(data, r, res) { 
			var data = data["data"];
			tree = $.fn.zTree.init($("#funcTree"), setting, data);
			tree.expandAll(true);
		}
	}
	$.DataAdapter.submit(params);
}
function reset() {
	$("#formpanel").formpanel('reset');
}
function queryUrl() {

		/* var selectedData = tree.getCheckedNodes(true);
			var funcIds = new Array();
			$.each(selectedData, function(i, n){
				funcIds.push(n.id);
			});
			var funcIdsStr = funcIds.join(",");  */
		/* 	
			var params = {
				url : rootPath+'/dataservice/saveServiceObject.do?update='+update,
				components : [ 'formpanel_edit' ],
				params: {
						serviceName:serviceobjectname,
						backstageData:backstageData,
						funcIdsStr : funcIdsStr,
						funcNamesStr : funcNamesStr,
						},
				callback : function(data, r, res) { 
					if (res.getAttr("back") == 'success'){
						parent.queryUrl();
						parent.winEdit.window("close");
						jazz.info("保存成功！");
					}else{
						jazz.error("添加服务对象失败！");
					}
				}
			};
			$.DataAdapter.submit(params);
			 */
			
			
	$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+'/dataservice/serviceList.do?gnId='+gnId);
	
	$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
}
function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var serviceid = data[i]["serviceid"];
		var state = data[i]["servicestate"];
		var type=data[i]["createtype"];
		var servicetype=data[i]["servicetype"];
		var executetype=data[i]["executetype"];
		var servicerunstate=data[i]["servicerunstate"];
		var serviceobjectid=data[i]["serviceobjectid"];
		
		if(state=="0"){
			data[i]["servicestate"] = '<a href="javascript:void(0);" onclick="notrun(\''+serviceid+'\',\'1\')">启用</a>';
		}else{
			data[i]["servicestate"] = '<a href="javascript:void(0);" onclick="run(\''+serviceid+'\',\'0\')">停用</a>';
		}
		
		if(servicetype==0){
			data[i]["servicetype"]="WebService";
		}else{
			if(servicetype==1){
			data[i]["servicetype"]="数据库";
			}else{
				if(servicetype==2){
			data[i]["servicetype"]="FTP";	
				}
			}
		}
		if(executetype==0){
			data[i]["executetype"]="正常";
		}else{
			if(executetype==1){
			data[i]["executetype"]="降级";
			}
		}
		if(servicerunstate==0){
			data[i]["servicerunstate"]="运行中";
		}else{
			data[i]["servicerunstate"]="停止";
		}
		
		data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="update(\''+serviceid+'\',\''+state+'\',\''+type+'\',\''+serviceobjectid+'\')">修改</a>';
		if(type == 'sjzx') {
			 data[i]["custom"] += ' <a href="javascript:void(0);" onclick="listen(\''+serviceid+'\',\'' +data[i]["servicename"]+'\',\'' + state+'\')">订阅</a>';
		}
		data[i]["custom"] += "</div>";
	}
	
	
	return data;
}
function add(){
	winEdit = jazz.widget({
	     vtype: 'window',
	     frameurl: './serviceEdit.jsp?update=false',
	  			name: 'win',
	  	    	title: '新增服务',
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 650,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		}); 
}

function listen(serviceId, serviceName, state){
	if(state=="1"){
		jazz.info("服务已停用，无法订阅!");
		return;
	}
	winEdit = jazz.widget({
 	     vtype: 'window',
	   	     frameurl: './serviceListen.jsp?serviceId='+serviceId,
	  			name: 'win',
	  	    	title: '服务订阅 - ' + serviceName,
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 650,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});
}

function update(serviceid,state,type,serviceobjectid){
	if(state=="0"){
		jazz.info("该记录已经处于启用状态，不能修改!");
		return;
	}
	winEdit = jazz.widget({
  	     vtype: 'window',
	   	     frameurl: './serviceEdit.jsp?serviceid='+serviceid+'&update=true&type='+type+"&serviceobjectid=" + serviceobjectid,
	  			name: 'win',
	  	    	title: '修改服务',
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 650,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});
}
function notrun(serviceid,state){
	jazz.confirm("是否停用？", function(){
		var params = {
				url : rootPath+'/dataservice/runService.do?serviceid='+serviceid+'&state=1',
				components: ['zzjgGrid'],
				callback : function(data, r, res) { 
					if (res.getAttr("back") == 'success'){
						queryUrl();
						jazz.info("停用成功!");
					}else if(res.getAttr("back") == 'exist'){
						jazz.info("该服务有被服务对象引用，无法停用，请去除引用再停用！");
					}else{
						jazz.info("停用失败！");
					}
				}
		}
		$.DataAdapter.submit(params);
	}, function(){});
}
function run(serviceid,state){
	jazz.confirm("是否启用？", function(){
		var params = {
				url : rootPath+'/dataservice/runService.do?serviceid='+serviceid+'&state=0',
				components: ['zzjgGrid'],
				callback : function(data, r, res) { 
					if (res.getAttr("back") == 'success'){
						queryUrl();
						jazz.info("启用成功!");
					}else{
						jazz.info("启用失败!");
					}
				}
		}
		$.DataAdapter.submit(params);
	}, function(){});
}
</script>
</head>
<body>
<div>位置：数据服务>服务发布管理</div>
<div id="border_id" height="100%" width="100%" vtype="panel" name="panel" title="border布局" titledisplay="false" 
	style="margin:0 auto;" layout="border" westdragstart="_dragstart();" westdragstop="_dragstop();">
	<div region="west" id="west" width="25%" height="100%">
		<div id="funcTree_div" style="overflow-x:hidden ">
			<div id="funcTree" name="funcTree" class="ztree"></div>
		</div>
	</div>
	<div region="center" height="100%" style="overflow-y:hidden;">	
		<div style="height: 98%;">
			<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
				showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="200" title="查询条件">
				<div name='servicename' vtype="textfield" label="服务名称" labelAlign="right"  labelwidth='100px' width="410"></div>
				<div name='servicetype' vtype="comboxfield" label="服务类型" dataurl="[{checked: true,value: '0',text: 'WebServices'},{value: '1',text: '数据库'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
				<div name='servicestate' vtype="comboxfield" label="服务状态" dataurl="[{checked: true,value: '0',text: '启用'},{value: '1',text: '停用'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
				<div name='executetype' vtype="radiofield" label="执行类型" dataurl="[{checked: true,value: '0',text: '正常'},{value: '1',text: '降级'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
				<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
					<div name="query_button" vtype="button" text="查询" 	icon="../query/queryssuo.png" onclick="queryUrl();"></div>
					<div name="reset_button" vtype="button" text="重置" icon="../query/queryssuo.png" click="reset();" ></div>
				</div>
			</div>
			<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender="renderdata()"
				titledisplay="true" title="查询列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
				<div name="toolbar" vtype="toolbar">
					<div name="btn1" align="right" vtype="button" text="新增" click="add()"></div>
		    	</div>
				<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
						<div name='serviceid' key="true" visible="false" width="0%"></div>
						<div name='servicename' text="服务名称" textalign="left"  width="26%"></div>
						<!-- <div name='servicecode' text="服务代码" textalign="center"  width="10%"></div> -->
						<div name='serviceurl' text="服务URI" textalign="left"  width="10%"></div>
						<div name='executetype' text="执行类型" textalign="center"  width="8%"></div>
						<div name='servicestate' text="服务状态" textalign="center"  width="8%"></div>
						<div name='servicerunstate' text="服务运行状态" textalign="center"  width="11%"></div>
						<div name='servicetype' text="服务类型" textalign="center"  width="8%"></div>
						<div name='createtype' text="提供方类型" textalign="center"  width="0%"></div>
						<div name='serviceconentshow' text="服务内容" textalign="center"  width="15%"></div>
						<div name='custom' text="操作" textalign="center"  width="12%"></div>
					</div>
				</div>
				<div vtype="gridtable" name="grid_table" ></div>
				<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
			</div>
		</div>
	</div>
</div>
</body>
</html>