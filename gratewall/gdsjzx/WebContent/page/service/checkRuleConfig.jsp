<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>检查规则配置</title>
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
var setting = {
		data: {
			simpleData: {
				enable: true
			}
		}
	};

	var zNodes =[
		{ name:"预处理库", open:true,
			children: [
				{ name:"市场主题登记系统",
					children: [
						{ name:"企业（机构）"},
						{ name:"个体工商户"},
						{ name:"人员职务"}
					]},
				{ name:"知识产权系统",isParent:true},
				{ name:"执法办案系统",isParent:true}
			]}
	];
$(document).ready(function(){
	$("#treeDemo").tree({setting: setting, data: zNodes});
});
function queryUrl() {
	$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+'/query/list.do');
	$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
}
function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var pripid = data[i]["pripid"];
		var a = data[i]["regno"];
		data[i]["regno"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="detail(\''+pripid+'\')">'+a+'</a></div>';
		data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="del(\''+pripid+'\')">删除</a>  <a href="javascript:void(0);" onclick="guidang(\''+pripid+'\')">导出</a></div>';
	}
	return data;
}
function del(dmbId){
	jazz.confirm("是否删除？", function(){
		jazz.info("删除成功");
		$.DataAdapter.submit(params);
	}, function(){});
}
function guidang(dmbId){
	jazz.info("导出成功");
}
function addOne(){
	
}
function addTwo(){
	
}
function detail(){
	
}
</script>
</head>
<body>
<div>位置：数据服务>数据治理>检查规则配置</div>

<div id="column_id" width="100%" height="98%" vtype="panel" name="panel" layout="column" layoutconfig="{width: ['20%','*']}">
        <div>
    		<div name="w1" vtype="panel" title="电影栏目" titledisplay="false" height="100%">
	    		<div id="treeDemo" class="ztree"></div>
    		</div>
    	</div>
    	<div>
    		<div name="c1" vtype="panel" title="体育栏目" titledisplay="false" height="100%">
    			<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
					showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="250" title="查询条件">
					<div name='regno' vtype="textfield" label="规则名称" labelAlign="right" labelwidth='100px' width="410"></div>
					<div name='entname' vtype="textfield" label="规则有效性" labelAlign="right" labelwidth='100px' width="410"></div>
					<div name='estdate' vtype="comboxfield" label="规则类型" dataurl='[{"text":"数据完整性","value":"1"},{"text":"有效性","value":"2"}]' labelAlign="right" labelwidth='100px' width="410"></div>
					<div name='a' vtype="textfield" label="规则藐视" labelAlign="right" labelwidth='100px' width="410"></div>
					
					<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
						<div name="query_button" vtype="button" text="查询" 
							icon="../query/queryssuo.png" onclick="queryUrl();"></div>
						<div name="reset_button" vtype="button" text="重置"
							icon="../query/queryssuo.png" click="reset();" ></div>
					</div>
				</div>
    			
    			<div vtype="gridpanel" name="zzjgGrid" height="200" width="100%"  id='zzjgGrid' datarender="renderdata()"
				titledisplay="true" title="查询列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
				
				<div name="toolbar" vtype="toolbar">
					<div name="btn1" align="right" vtype="button" text="增加单表规则" click="addOne();"></div>
					<div name="btn2" align="right" vtype="button" text="增加关联表规则" click="addTwo();"></div>
    			</div>
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
						<div name='pripid' width="0%"></div>
						<div name='regno' text="规则名称" textalign="center"  width="25%"></div>
						<div name='enttype' text="规则有效性" textalign="center"  width="25%"></div>
						<div name='regorg' text="规则类型" textalign="center"  width="23%"></div>
						<div name='custom' text="操作" textalign="center"  width="25%"></div>
					</div>
				</div>
				<!-- 表格 -->
				<!-- ../../grid/reg3.json -->
				<div vtype="gridtable" name="grid_table" ></div>
				<!-- 分页 -->
				<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
			</div>
    		</div>
    	</div>
    </div>
</body>
</html>