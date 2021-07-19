<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>广告审查员管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script type="text/javascript">
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
<div>位置:通用查询>广告监管信息>专项管理>广告审查员管理</div>
<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
	titledisplay="true" width="98%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*'], border: false}" height="auto">
<div style="border:0px solid red;width:100%;font-weight:bold;color:#327BB9;margin:1%;margin-left:2%;">基本信息</div>
<div></div>
<hr style="color:#FCFCFC;"><hr  style="color:#FCFCFC;margin-left:-2px;">
	<div id="regno" name='regno' vtype="hiddenfield" label="注册号" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div id="entname" name='entname' vtype="hiddenfield" label="企业(机构)名称" labelAlign="right" labelwidth='102px'  width="410"></div>
	
	<div name='oldregno' vtype="textfield" label="姓名" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='enttype' vtype="textfield" label="身份证号" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='servicestate' vtype="textfield" label="培训证号" labelAlign="right" labelwidth='100px'  width="410"></div>
	<div name='regcap' vtype="datefield" label="发证日期" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='industryphy' vtype="textfield" label="培训单位名称" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='industryco' vtype="comboxfield" label="工作单位" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='estdate' vtype="textfield" label="工作媒体" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='email' vtype="textfield" label="登记部门名称" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco' vtype="textfield" label="登记人" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco' vtype="datefield" label="登记日期" labelAlign="right" labelwidth='102px'  width="410"></div>
</div>




</body>
</html>
