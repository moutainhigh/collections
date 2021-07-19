<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>公益广告查询</title>
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
<div>位置:通用查询>广告监管信息>专项管理>公益广告查询</div>
<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
	titledisplay="true" width="98%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*'], border: false}" height="auto">
<div style="border:0px solid red;width:100%;font-weight:bold;color:#327BB9;margin:1%;margin-left:2%;">基本信息</div>
<div></div>
<hr style="color:#FCFCFC;"><hr  style="color:#FCFCFC;margin-left:-2px;">
	<div id="regno" name='regno' vtype="hiddenfield" label="注册号" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div id="entname" name='entname' vtype="hiddenfield" label="企业(机构)名称" labelAlign="right" labelwidth='102px'  width="410"></div>
	
	<div name='oldregno' vtype="textfield" label="活动编号" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='enttype' vtype="textfield" label="活动主题" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='servicestate' vtype="textfield" label="活动名称" labelAlign="right" labelwidth='100px'  width="410"></div>
	<div name='regcap' vtype="textfield" label="参与广告发布者数量" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='industryphy' vtype="textfield" label="参与广告的经营者数量" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='industryco' vtype="textfield" label="参与广告主数量" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='estdate' vtype="comboxfield" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" label="登记单位" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='email' vtype="datefield" label="发布起始日期" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco6' vtype="datefield" label="发布截止日期" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='regorg1' vtype="textfield" label="电视台发布次数" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='postalcode1' vtype="textfield" label="电视设计制作数" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='tel1' vtype="textfield" label="电视投入金额" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='cbuitem1' vtype="textfield" label="电台发布次数" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='opscope1' vtype="textfield" label="电台设计制作数" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='localadm1' vtype="textfield" label="电台投入金额" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='opfrom1' vtype="textfield" label="报纸发布次数" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='opto1' vtype="textfield" label="报纸设计制作数" labelAlign="right" labelwidth='102px'  width="410"></div>
	
	<div name='abuitemco' vtype="textfield" label="报纸投入金额" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='regorg' vtype="textfield" label="杂志发布次数" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='postalcode' vtype="textfield" label="杂志设计制作数" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='tel' vtype="textfield" label="杂志投入金额" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='cbuitem' vtype="textfield" label="户外发布次数" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='opscope' vtype="textfield" label="户外设计制作数" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='localadm' vtype="textfield" label="户外投入金额" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='opfrom' vtype="textfield" label="网络发布次数" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='opto' vtype="textfield" label="网络设计制作数" labelAlign="right" labelwidth='102px'  width="410"></div>
	
	
	<div name='tel5' vtype="textfield" label="网络投入金额" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='opscope3' vtype="textfield" label="网络设计制作数" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='localadm3' vtype="textfield" label="网络投入金额" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='opto3' vtype="textfield" label="短信设计制作数" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='cbuitem3' vtype="textfield" label="短信投入金额" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='opfrom3' vtype="textfield" label="短信发布次数" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='opfrom34' vtype="textfield" label="备注" labelAlign="right" labelwidth='102px'  width="410"></div>
</div>




</body>
</html>
