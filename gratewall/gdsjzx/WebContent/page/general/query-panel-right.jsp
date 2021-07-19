<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>市场主体信息展示</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/jbxx/detial.css" />
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
<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
	titledisplay="true" width="98%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*'], border: true}" height="auto">

	<div id="regno" name='regno' vtype="hiddenfield" label="注册号" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div id="entname" name='entname' vtype="hiddenfield" label="企业(机构)名称" labelAlign="right" labelwidth='102px'  width="410"></div>
	
	<div name='oldregno' vtype="textfield" label="广告名称" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='enttype' vtype="textfield" label="广告编号" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='servicestate' vtype="textfield" label="发布媒体" labelAlign="right" labelwidth='100px'  width="410"></div>
	<div name='regcap' vtype="textfield" label="广告主名称" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='industryphy' vtype="textfield" label="广告经营者名称" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='industryco' vtype="textfield" label="广告主类型" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='estdate' vtype="textfield" label="广告经营者类型" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='email' vtype="textfield" label="商品(服务)名称" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco' vtype="textfield" label="商品(服务)类别" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='regorg' vtype="textfield" label="商品(服务)大类" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='postalcode' vtype="textfield" label="商品(服务)中类" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='tel' vtype="textfield" label="商品(服务)小类" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='cbuitem' vtype="textfield" label="广告相关批号" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='opscope' vtype="textfield" label="发布时间" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='localadm' vtype="textfield" label="登记时间" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='opfrom' vtype="textfield" label="管辖单位" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='opto' vtype="textfield" label="登记人姓名" labelAlign="right" labelwidth='102px'  width="410"></div>
</div>




<div vtype="formpanel" id="formpanel2" displayrows="1" name="formpanel2"
	titledisplay="true" width="98%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*'], border: true}" height="auto">


	<div name='oldregno' vtype="textfield" label="广告发布版面" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='enttype' vtype="textfield" label="违规表现" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='servicestate' vtype="textfield" label="认定依据" labelAlign="right" labelwidth='100px'  width="410"></div>
	<div name='regcap' vtype="textfield" label="处罚依据及监管措施" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='industryphy' vtype="textfield" label="处理人姓名" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='industryco' vtype="textfield" label="发布次数" labelAlign="right" labelwidth='102px'  width="410"></div>
</div>
</body>
</html>
