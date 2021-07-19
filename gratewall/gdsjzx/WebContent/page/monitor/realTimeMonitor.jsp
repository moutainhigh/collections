<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>实时监控</title>
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

function queryUrl() {
	$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+'/query/list.do');
	$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
}

function queryUrl1() {
	$('#zzjgGrid1').gridpanel('option', 'dataurl',rootPath+'/query/list.do');
	$('#zzjgGrid1').gridpanel('query', [ 'formpanel']);
}
$(function(){
	queryUrl();
	queryUrl1();
});


</script>
</head>
<body>
<div>位置：运行监控>实时监控</div>
<div name="row_id" height="100%" vtype="panel" layout="row" layoutconfig="{rowheight:['50%','*']}">
		<div>
			<div vtype="gridpanel" name="zzjgGrid" height="200" width="100%"  id='zzjgGrid' datarender=""
				titledisplay="true" title="共享服务"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
						<div name='pripid' width="0%"></div>
						<div name='regno' text="接口名称" textalign="center"  width="15%"></div>
						<div name='enttype' text="主题" textalign="center"  width="15%"></div>
						<div name='regorg' text="业务系统" textalign="center"  width="19%"></div>
						<div name='servicestate' text="请求次数" textalign="center"  width="19%"></div>
						<div name='estdate' text="返回数据" textalign="center"  width="15%"></div>
						<div name='industryco' text="平均耗时" textalign="center"  width="15%"></div>
					</div>
				</div>
				<!-- 表格 -->
				<!-- ../../grid/reg3.json -->
				<div vtype="gridtable" name="grid_table" ></div>
				<!-- 分页 -->
				<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
			</div>
		</div>
		<div>
			<div vtype="gridpanel" name="zzjgGrid1" height="400" width="100%"  id='zzjgGrid1' datarender=""
				titledisplay="true" title="采集服务"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
						<div name='pripid' width="0%"></div>
						<div name='regno' text="服务名称" textalign="center"  width="15%"></div>
						<div name='enttype' text="开始时间" textalign="center"  width="10%"></div>
						<div name='regorg' text="结束时间" textalign="center"  width="10%"></div>
						<div name='servicestate' text="数据量" textalign="center"  width="10%"></div>
						<div name='estdate' text="错误数据量" textalign="center"  width="14%"></div>
						<div name='entname' text="服务状态" textalign="center"  width="20%"></div>
						<div name='custom' text="下次开始时间" textalign="center"  width="19%"></div>
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
</body>
</html>