<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>警情管理</title>
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

function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var pripid = data[i]["pripid"];
		if(i%2==0){
			data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="deal(\''+pripid+'\')">处理</a></div>';
		}else{
			data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="detail(\''+pripid+'\')">查看</a></div>';
		}
	}
	return data;
}

function deal(dmbId){
	winEdit = jazz.widget({
  	     vtype: 'window',
	   	     frameurl: './alarmDeal.jsp?dmbId='+dmbId+'&update=true',
	  			name: 'win',
	  	    	title: '处理警情',
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 550,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});
}
function detail(dmbId){
	winEdit = jazz.widget({
  	     vtype: 'window',
	   	     frameurl: './alarmDetail.jsp?dmbId='+dmbId+'&update=true',
	  			name: 'win',
	  	    	title: '查看警情',
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
<div>位置：运行监控>警情管理</div>

<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
	showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="300" title="查询条件">
	<div name='regno' vtype="textfield" label="监控对象" labelAlign="right" labelwidth='110px' width="410"></div>
	<div dataurl="[{checked: true,value: '1',text: '连通性'},{value: '2',text: '可用性'}]" label="监控指标" name='entname' vtype="comboxfield" labelAlign="right" labelwidth='110px' width="410"></div>
	<div name='estdate' vtype="datefield" label="警情发生开始时间" labelAlign="right" labelwidth='110px' width="410"></div>
	<div name='servicestate' vtype="datefield" label="警情发生结束时间" labelAlign="right" labelwidth='110px' width="410"></div>
	<div dataurl="[{checked: true,value: '1',text: '未处理'},{value: '2',text: '已处理'}]" name='industryphy' vtype="comboxfield" label="警情状态" labelAlign="right" labelwidth='110px' width="410"></div>	
	
	<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
		<div name="query_button" vtype="button" text="查询" 
			icon="../query/queryssuo.png" onclick="queryUrl();"></div>
		<div name="reset_button" vtype="button" text="重置"
			icon="../query/queryssuo.png" click="reset();" ></div>
	</div>
</div>

<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender="renderdata()"
	titledisplay="true" title="查询列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
	<!-- 表头 -->
	<div vtype="gridcolumn" name="grid_column" width="100%">
		<div>
			<div name='pripid' ></div>
			<div name='regno' text="操作人" textalign="center"  width="15%"></div>
			<div name='enttype' text="操作对象" textalign="center"  width="15%"></div>
			<div name='regorg' text="操作类型" textalign="center"  width="15%"></div>
			<div name='servicestate' text="操作时间" textalign="center"  width="15%"></div>
			<div name='industryco' text="用户状态" textalign="center"  width="20%"></div>
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