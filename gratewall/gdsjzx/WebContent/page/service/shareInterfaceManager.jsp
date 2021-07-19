<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>共享接口管理</title>
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
function reset() {
	$("#formpanel").formpanel('reset');
}
function queryUrl() {
	$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+
			'/query/list.do');
	$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
}
function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var pripid = data[i]["pripid"];
		var a = data[i]["regno"];
		data[i]["regno"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="detailQuery(\''+pripid+'\')">'+a+'</a></div>';
		if(i%2==0){
			data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="update(\''+pripid+'\')">修改</a>  <a href="javascript:void(0);" onclick="guidang(\''+pripid+'\')">删除</a>  <a href="javascript:void(0);" onclick="notrun(\''+pripid+'\')">启用/停用</a></div>';
		}else{
			data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="update(\''+pripid+'\')">修改</a>  <a href="javascript:void(0);" onclick="guidang(\''+pripid+'\')">删除</a>  <a href="javascript:void(0);" onclick="run(\''+pripid+'\')">启用/停用</a></div>';
		}
	}
	return data;
}
function detailQuery(dmbId){
	winEdit = jazz.widget({
 	     vtype: 'window',
	   	     frameurl: './shareInterfaceDetail.jsp?dmbId='+dmbId+'&update=true',
	  			name: 'win',
	  	    	title: '查看共享接口',
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 550,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});
}
function update(dmbId){
	winEdit = jazz.widget({
  	     vtype: 'window',
	   	     frameurl: './shareInterfaceEdit.jsp?dmbId='+dmbId+'&update=true',
	  			name: 'win',
	  	    	title: '修改共享接口',
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 550,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});
}
function guidang(dmbId){
	jazz.confirm("是否删除？", function(){
		/* var params = {
				url : rootPath+'/dictionaryList/delete.do',
				components: ['gridpanel'],
				callback : function(data, r, res) { 
					if (res.getAttr("back") == 'success'){
						queryDef();
						jazz.info("删除成功");
					}
				}
		} */
		jazz.info("删除成功");
		$.DataAdapter.submit(params);
	}, function(){});
}
function notrun(dmbId){
	jazz.confirm("是否停用？", function(){
		/* var params = {
				url : rootPath+'/dictionaryList/delete.do',
				components: ['gridpanel'],
				callback : function(data, r, res) { 
					if (res.getAttr("back") == 'success'){
						queryDef();
						jazz.info("删除成功");
					}
				}
		} */
		jazz.info("停用成功");
		$.DataAdapter.submit(params);
	}, function(){});
}
function run(dmbId){
	jazz.confirm("是否启用？", function(){
		/* var params = {
				url : rootPath+'/dictionaryList/delete.do',
				components: ['gridpanel'],
				callback : function(data, r, res) { 
					if (res.getAttr("back") == 'success'){
						queryDef();
						jazz.info("删除成功");
					}
				}
		} */
		jazz.info("启用成功");
		$.DataAdapter.submit(params);
	}, function(){});
}
function add(){
	winEdit = jazz.widget({
	     vtype: 'window',
	     frameurl: './shareInterfaceEdit.jsp?update=true',
	  			name: 'win',
	  	    	title: '新增共享接口',
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 550,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		}); 
}
</script>
</head>
<body>
<div>位置：数据服务>数据共享>共享接口管理</div>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
		showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="150" title="查询条件">
		
		<div name='entname' vtype="textfield" label="接口名称" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='industryco' vtype="comboxfield" label="接口状态" dataurl="[{checked: true,value: '1',text: '启用'},{value: '2',text: '停用'},{value: '3',text: '归档'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div name="query_button" vtype="button" text="查询" 
				icon="../query/queryssuo.png" onclick="queryUrl();"></div>
			<div name="reset_button" vtype="button" text="重置"
				icon="../query/queryssuo.png" click="reset();" ></div>
		</div>
	</div>

	<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender="renderdata()"
		titledisplay="true" title="查询列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
		<div name="toolbar" vtype="toolbar">
			<div name="btn1" align="right" vtype="button" text="新增" click="add()"></div>
    	</div>
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='pripid' width="0%"></div>
				<div name='regno' text="接口名称" textalign="center"  width="20%"></div>
				<div name='entname' text="接口状态" textalign="center"  width="19%"></div>
				<div name='regorg' text="接口说明" textalign="center"  width="20%"></div>
				<div name='enttype' text="创建时间" textalign="center"  width="20%"></div>
				<div name='custom' text="操作" textalign="center"  width="20%"></div>
			</div>
		</div>
		<!-- 表格 -->
		<!-- ../../grid/reg3.json -->
		<div vtype="gridtable" name="grid_table" ></div>
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
	</div>
</body>
</html>