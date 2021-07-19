<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script>
var recid;
function initData(res){
	recid = res.getAttr("recid");
	loadData(recid);
}
function loadData(recid){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/trsquery/querynzwzqingsuanchengyuanxxdata.do?recid='+recid);
$('div[name="formpanel"]').formpanel('option', 'readonly', true);
$('div[name="formpanel"]').formpanel('reload');
}
</script>
<style>
/*文字超出不换行*/
table td{word-break: keep-all;white-space:nowrap;}
.jazz-form-text-label{
	color: #000;
	font-weight: normal;
}
</style>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="persname" vtype="textfield" label="姓名" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="sex" vtype="textfield" label="性别" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="certype" vtype="textfield" label="证件类型" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cerno" vtype="textfield" label="证件号码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="licflag" vtype="textfield" label="清算组负责人标识" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="forname" vtype="textfield" label="英文名" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="mobel" vtype="textfield" label="移动电话" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="tel" vtype="textfield" label="电话" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="postalcode" vtype="textfield" label="邮政编码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="email" vtype="textfield" label="电子邮件" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="natdate" vtype="textfield" label="出生日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="litedeg" vtype="textfield" label="文化程度" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="countrycode" vtype="textfield" label="国别(地区)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="addr" vtype="textfield" label="地址" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cervalper" vtype="textfield" label="证件有效期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
    <script type="text/javascript">
    $(function(){
    	 $("tr:even").css({background:"#EFF6FA"});
		 $("tr:odd").css({background:"#FBFDFD"});
    })
    
    </script>
</body>
</html>
