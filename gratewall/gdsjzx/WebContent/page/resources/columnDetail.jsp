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

function back() {
	parent.winEdit.window("close");
}
</script>
</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" tableStyleClass="tablecss" titledisplay="false" showborder="false" width="100%"
		layout="table" layoutconfig="{cols:2, columnwidth: ['50%','*']}"
		height="495" dataurl="">

		<div name='regno' vtype="textfield" label="字段名" labelAlign="right" labelwidth='130' width="400"></div>
		<div name='entname' vtype="textfield" label="字段中文名" labelAlign="right"  labelwidth='130' width="400"></div>
		<div name='estdate' vtype="textfield" label="字段类型" labelAlign="right"  labelwidth='130' width="400"></div>
		<div name='industryphy' vtype="textfield" label="字段长度" labelAlign="right"  labelwidth='130' width="400"></div>
		<div name='regorg' vtype="datefield" label="是否为空" labelAlign="right"  labelwidth='130' width="400"></div>
		<div name='servicestate' vtype="datefield" label="缺省值" labelAlign="right"  labelwidth='130' width="400"></div>
		<div name='industryco' vtype="comboxfield" label="标准标识符" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='130' width="400"></div>
		<div name='enttype' vtype="comboxfield" label="说明" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='130' width="400"></div>
	
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div id="btn2" name="btn2" vtype="button" text="关闭"
				icon="../../../style/default/images/fh.png" click="back()"></div>
		</div>
	</div>

	
</body>
</html>