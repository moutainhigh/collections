<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>详细</title>
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
var priPid=null;
$(function(){
	$("#formpanel").formpanel('option', 'readonly', true);
	priPid=getUrlParam("priPid");
	alert(priPid);
	if(priPid==null){
		alert("获取数据失败！");
		return;
	}
	queryHistory(priPid);
}); 
function queryHistory(priPid){
	$('#formpanel .jazz-panel-content').loading();
	$("#formpanel").formpanel('option', 'dataurl',rootPath+'/query/detail.do?priPid='+priPid);
	$("#formpanel").formpanel('reload', "null", function(){$('#formpanel .jazz-panel-content').loading('hide');});
}
function getUrlParam(name) {
 var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
 var r = window.location.search.substr(1).match(reg);  //匹配目标参数
 if (r != null) return unescape(r[2]); return null; //返回参数值
}
</script>
</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
		showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="300" title="查询条件">

		<div name='regno' vtype="textfield" label="批准文号" labelAlign="right" labelwidth='100px' width="410"></div>
		<div name='entname' vtype="textfield" label="提交印章人" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='industryphy' vtype="textfield" label="登记证数" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='industryco' vtype="textfield" label="收照人" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='estdate' vtype="datefield" label="注销日期" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='servicestate' vtype="datefield" label="批准日期" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='regorg' vtype="comboxfield" label="批准机关" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='enttype' vtype="comboxfield" label="注销原因" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
	</div>

	
</body>
</html>