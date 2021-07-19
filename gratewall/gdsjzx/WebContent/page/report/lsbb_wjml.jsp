<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>文件目录</title>
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
	$("#zzjgGrid").gridpanel("hideColumn", "approvedate");
		$("#zzjgGrid").gridpanel("showColumn", "modfydate");
	$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+
			'/query/list.do');
	$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
}

function rowclick(event,data){
	window.location.href="<%=request.getContextPath()%>/page/general/query-panel-right.jsp?priPid="+data.pripid;
}


function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var pripid = data[i]["pripid"];
		data[i]["cz"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="update(\''+pripid+'\')">修改  </a><a href="javascript:void(0);" onclick="update(\''+pripid+'\')">删除  </a></div>';
	}
	return data;
}

function detail(pripid){
	winEdit = jazz.widget({
 	     vtype: 'window',
	   	     frameurl: './columnDetail.jsp?priPid='+pripid+'&update=true',
	  			name: 'win',
	  	    	title: '详细',
	  	    	titlealign: 'center',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 550,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});
}

	function add(){
		window.open("<%=request.getContextPath()%>/page/report/lsbb_wjml_addml.jsp");
	}
</script>
</head>
<body>
	<div>位置：报表管理>文件管理>文件目录</div>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
		showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="300" title="查询条件">

		<div name='zjhm' vtype="textfield" label="表样代码" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='fbsjq' vtype="textfield" label="表样名称" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='ggmc' vtype="comboxfield" label="年度" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='ggmc1' vtype="comboxfield" label="报告期别" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='ggmc2' vtype="textfield" label="主题" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div name="query_button" vtype="button" text="查询" 
				icon="../query/queryssuo.png" onclick="queryUrl();"></div>
			<div name="reset_button" vtype="button" text="重填"
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
				
				<div name='name' text="表样代码" textalign="center"  width="10%"></div>
				<div name='sex' text="目录名称" textalign="center"  width="15%"></div>
				<div name='sex1' text="年度" textalign="center"  width="10%"></div>
				<div name='bbmc' text="报告期别" textalign="center"  width="15%"></div>
				<div name='sex2' text="主题" textalign="center"  width="10%"></div>
				<div name='regorg' text="创建人" textalign="center"  width="10%"></div>
				<div name='regorg1' text="创建时间" textalign="center"  width="10%"></div>
				<div name='regno' text="使用状态" textalign="center"  width="10%"></div>
				<div name='cz' text="操作" textalign="center"  width="10%"></div>
			</div>
		</div>
		<!-- 表格 -->
		<!-- ../../grid/reg3.json -->
		<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
	</div>
</body>
</html>