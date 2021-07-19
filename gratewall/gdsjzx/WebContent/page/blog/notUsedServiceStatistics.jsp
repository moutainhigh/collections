<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>未使用服务情况统计</title>
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
		data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="detail(\''+pripid+'\')">查看详细</a></div>';
	}
	return data;
}

function detail(dmbId){
	winEdit = jazz.widget({
  	     vtype: 'window',
	   	     frameurl: './sysOperationDetail.jsp?dmbId='+dmbId+'&update=true',
	  			name: 'win',
	  	    	title: '查看详细',
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
<div>位置：日志管理>日志统计>未使用服务情况统计</div>

<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
	showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="300" title="查询条件">
	<div dataurl="[{checked: true,value: '1',text: '内部系统'},{value: '2',text: '外部系统'}]" label="服务对象" name='entname' vtype="comboxfield" labelAlign="right" labelwidth='100px' width="410"></div>
	<div dataurl="[{checked: true,value: '1',text: '一周'},{value: '2',text: '二周'}]" name='industryphy' vtype="comboxfield" label="统计周期" labelAlign="right" labelwidth='100px' width="410"></div>
	<div dataurl="[{checked: true,value: '1',text: '折线'},{value: '2',text: '曲线图'}]" name='industryco1' vtype="comboxfield" label="展现方式" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='estdate' vtype="checkboxfield" label="指标" dataurl="[{checked: true,value: '1',text: '采集数据量'},{value: '2',text: '共享数据量'},{value: '3',text: '平均响应时间'},{value: '4',text: '调用次数'}]" labelAlign="right" labelwidth='100px' width="410"></div>
	
	<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
		<div name="query_button" vtype="button" text="查询" 
			icon="../query/queryssuo.png" onclick="queryUrl();"></div>
		<div name="reset_button" vtype="button" text="重置"
			icon="../query/queryssuo.png" click="reset();" ></div>
	</div>
</div>
<div align="center">
<img alt="" src="<%=request.getContextPath()%>/static/images/a.png" align="middle"></div>
</body>
</html>