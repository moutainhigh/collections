<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>案件信息</title>
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
</script>
</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
		showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="200" title="自定义查询信息">

		<div name='regno' vtype="textfield" label="查询名称" labelAlign="right" labelwidth='100px' width="410"></div>
		<div name='entname' vtype="textfield" label="执行次数" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='estdate' vtype="datefield" label="创建日期" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='servicestate' vtype="datefield" label="最后执行日期" labelAlign="right"  labelwidth='100px' width="410"></div>
	</div>

	<div name="row_id" height="100%" vtype="panel" layout="row" layoutconfig="{rowheight:['10%','40%','*']}">
		<div >
		h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
		<div >
		h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
		<div >
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
	</div>
</body>
</html>