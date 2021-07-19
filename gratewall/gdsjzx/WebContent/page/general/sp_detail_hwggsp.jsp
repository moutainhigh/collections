<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>户外广告审批</title>
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
<div>位置:通用查询>广告监管信息>户外广告审批</div>
<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
	titledisplay="true" width="98%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*'], border: false}" height="auto">
<div style="border:0px solid red;width:100%;font-weight:bold;color:#327BB9;margin:1%;margin-left:2%;">基本信息</div>
<div></div>
<hr style="color:#FCFCFC;"><hr  style="color:#FCFCFC;margin-left:-2px;">
	<div id="regno" name='regno' vtype="hiddenfield" label="注册号" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div id="entname" name='entname' vtype="hiddenfield" label="企业(机构)名称" labelAlign="right" labelwidth='102px'  width="410"></div>
	
	<div name='oldregno' vtype="textfield" label="户外广告许可证" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='enttype' vtype="textfield" label="企业注册号" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='servicestate' vtype="textfield" label="发布单位" labelAlign="right" labelwidth='100px'  width="410"></div>
	<div name='regcap' vtype="textfield" label="注册地址" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='estdate' vtype="textfield" label="申请日期" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco1' vtype="textfield" label="联系人" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco2' vtype="textfield" label="联系电话" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='email' vtype="textfield" label="广告形式" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco58' vtype="textfield" label="数量" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco22' vtype="textfield" label="规格" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco11' vtype="textfield" label="类别" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco37' vtype="textfield" label="发布地点及具体位置" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco33' vtype="textfield" label="广告主" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='industryco' vtype="textfield" label="广告名称" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco64' vtype="textfield" label="审批情况" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco65' vtype="textfield" label="审批时间" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco77' vtype="textfield" label="有效期起" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco98' vtype="textfield" label="有效期至" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco423' vtype="textfield" label="受理人" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco456' vtype="textfield" label="受理时间" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco56' vtype="textfield" label="审批人" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco74' vtype="textfield" label="审批单位" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco87' vtype="textfield" label="所属分局" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco84' vtype="textfield" label="是否审批通过" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco55' vtype="textfield" label="备注" labelAlign="right" labelwidth='102px'  width="410"></div>
</div>




</body>
</html>
