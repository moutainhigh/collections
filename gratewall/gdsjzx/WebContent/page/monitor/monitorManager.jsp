<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>监控管理</title>
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
		{ name:"登记系统", open:true,
			children: [
				{ name:"结果库增量更新",isParent:true},
				{ name:"历史库增量更新",isParent:true}
			]},
		{ name:"案件",
			children: [
				{ name:"案件增量更新", open:true}
			]}
	];
$(document).ready(function(){
	$("#treeDemo").tree({setting: setting, data: zNodes});
	$("#treeDemo1").tree({setting: setting, data: zNodes});
	$("#treeDemo2").tree({setting: setting, data: zNodes});
	queryUrl();
});
function queryUrl() {
	$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+'/query/list.do');
	$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
}
function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var pripid = data[i]["pripid"];
		data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="update(\''+pripid+'\')">修改</a></div>';
	}
	return data;
}
function update(pripid){
	winEdit = jazz.widget({
 	    vtype: 'window',
   	    frameurl: './monitorEdit.jsp?dmbId='+pripid+'&update=true',
		name: 'win',
    	title: '修改指标和阀值',
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
<div>位置：运行监控>监控管理</div>

<div id="column_id" width="100%" height="98%" vtype="panel" name="panel" layout="column" layoutconfig="{width: ['20%','*']}">
        <div>
    		<div name="w1" vtype="panel" title="电影栏目" titledisplay="false" height="100%">
	    		<div name="tab_name" vtype="tabpanel" overflowtype="2" tabtitlewidth="80"  width="100%" height="100%" orientation="top" id="tab_name">    
				    <ul>    
				        <li><a href="#tab1">采集服务</a></li>    
				        <li><a href="#tab2">共享服务</a></li>  
				        <li><a href="#tab3">服务器资源</a></li>   
				    </ul>    
				    <div>    
				        <div id="tab1">    
							<div id="treeDemo" class="ztree"></div>				
				        </div>    
				        <div id="tab2">    
							<div id="treeDemo1" class="ztree"></div>
				        </div>  
				        <div id="tab3">    
							<div id="treeDemo2" class="ztree"></div>
				        </div>   
				    </div>    
				</div>
    		</div>
    	</div>
    	<div>
    		<div name="c1" vtype="panel" title="体育栏目" titledisplay="false" height="100%">
    			<div vtype="gridpanel" name="zzjgGrid" height="200" width="100%"  id='zzjgGrid' datarender="renderdata()"
				titledisplay="true" title="指标和阀值"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
						<div name='pripid' ></div>
						<div name='regno' text="指标" textalign="center"  width="20%"></div>
						<div name='enttype' text="阀值" textalign="center"  width="20%"></div>
						<div name='regorg' text="监控间隔" textalign="center"  width="20%"></div>
						<div name='servicestate' text="是否警告" textalign="center"  width="20%"></div>
						<div name='custom' text="操作" textalign="center"  width="20%"></div>
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