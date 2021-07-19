<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>主题管理</title>
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
</style>
<script>
$(function(){
	queryUrl();
});
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

}

function reset() {
	$("#formpanel").formpanel('reset');
}
function queryUrl() {
	$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+'/datatheme/themeList.do');
	$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
}
function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var themeid = data[i]["ztid"];
		var isstart = data[i]["isstart"];
		if(isstart=="0"){
			data[i]["isstart"] = '<a href="javascript:void(0);" onclick="run(\''+themeid+'\',\''+1+'\')">启用</a>';
		}else{
			data[i]["isstart"] = '<a href="javascript:void(0);" onclick="run(\''+themeid+'\',\''+0+'\')">停用</a>';
		}
		data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="update(\''+themeid+'\',\''+isstart+'\')">修改</a></div>';
	}
	return data;
}
function add(){
	winEdit = jazz.widget({
	     vtype: 'window',
	     frameurl: './themeEdit.jsp?update=false',
	  			name: 'win',
	  	    	title: '新增主题',
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 600,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		}); 
}
function update(themeid,isstart){
	//if(isstart == '1'){
		winEdit = jazz.widget({
  	  	   vtype: 'window',
	   	     frameurl: './themeEdit.jsp?themeid='+themeid+'&update=true',
	  			name: 'win',
	  	    	title: '修改主题',
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 600,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});
	   //	}else{
	   //	jazz.info("请先停用再修改!");	
	   //		}
}
function notrun(themeid,state){
	if(state=='1'){
		jazz.info("该记录已经处于停用状态!");
		return;
	}
	jazz.confirm("是否停用？", function(){
		var params = {
				url : rootPath+'/datatheme/runTheme.do?themeid='+themeid+'&state=1',
				components: ['zzjgGrid'],
				callback : function(data, r, res) { 
					if (res.getAttr("back") == 'success'){
						queryUrl();
						jazz.info("停用成功!");
					}else if(res.getAttr("back") == 'exist'){
						jazz.info("该主题有被服务对象引用，无法停用，请去除引用再停用！");
					}else{
						jazz.info("停用失败！");
					}
				}
		}
		$.DataAdapter.submit(params);
	}, function(){});
}
function run(themeid,state){
	var s=state=='1'?"停用":"启用";
	jazz.confirm("是否"+s+"？",  function(){
		var params = {
				url : rootPath+'/datatheme/runTheme.do?themeid='+themeid+'&state=0',
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
<div>位置：数据服务>服务框架主题管理</div>
<div id="table" vtype="panel" layout="column" showborder="false" width="100%" height="97%"  layoutconfig="{columnwidth: ['*']}">
<div>	
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
		showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="200" title="查询条件">
		<div name='themename' vtype="textfield" label="主题名称" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='createperson' vtype="textfield" label="主题创建人"  labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='isstart' vtype="comboxfield" label="是否启用" dataurl="[{checked: true,value: '0',text: '启用'},{value: '1',text: '停用'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
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
				<div name='themeid' key="true" visible="false" width="0%"></div>
				<div name='themename' text="主题名称" textalign="center"  width="20%"></div>
				<div name='createperson' text="主题创建人" textalign="center"  width="20%"></div>
				<div name='isstart' text="状态" textalign="center"  width="20%"></div>
				<div name='lastuupdatetime' text="主题说明" textalign="center"  width="20%"></div>
				<div name='custom' text="操作" textalign="center"  width="20%"></div>
				
				<!-- <div name='createtype' text="提供方类型" textalign="center"  width="0%"></div>
				<div name='servicestate' text="服务状态" textalign="center"  width="10%"></div>
				<div name='custom' text="操作" textalign="center"  width="20%"></div> -->
			</div>
		</div>
		<div vtype="gridtable" name="grid_table" ></div>
		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
	</div>
</div>
</div>
</body>
</html>