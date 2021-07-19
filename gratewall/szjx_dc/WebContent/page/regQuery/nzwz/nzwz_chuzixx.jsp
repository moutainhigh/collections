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
var invid;
function initData(res){
	invid = res.getAttr("invid");
	loadData(invid);
}
function loadData(invid){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/trsquery/querynzwzchuzixxdata.do?invid='+invid);
$('div[name="formpanel"]').formpanel('option', 'readonly', true);
$('div[name="formpanel"]').formpanel('reload');
}
</script>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="invtype_cn" vtype="textfield" label="投资人类型/主管部门类型（中文名称）" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="blictype" vtype="textfield" label="证照类型(CA50)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="blictype_cn" vtype="textfield" label="证照类型（中文名称）" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="blicno" vtype="textfield" label="证照编号" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="subconam" vtype="textfield" label="认缴出资额" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="subconamusd" vtype="textfield" label="认缴出资额折万美元" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="subconform" vtype="textfield" label="认缴出资方式(CA22)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="subconprop" vtype="textfield" label="认缴出资比例" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="condate" vtype="textfield" label="认缴出资时间" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="acconam" vtype="textfield" label="实缴出资额" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="acconamusd" vtype="textfield" label="实缴出资额折万美元" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="dom" vtype="textfield" label="住所" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="currency" vtype="textfield" label="币种(CA04)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="currency_cn" vtype="textfield" label="币种（中文名称）" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="country" vtype="textfield" label="国别(地区)(CA02)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="country_cn" vtype="textfield" label="国别(地区)（中文名称）" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="exeaffsign" vtype="textfield" label="执行合伙事务标志" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="respform" vtype="textfield" label="承担责任方式/责任形式(CA29)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="respform_cn" vtype="textfield" label="承担责任方式/责任形式（中文名称）" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="invid" vtype="textfield" label="投资人身份标识" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pripid" vtype="textfield" label="主体身份代码/合作社成员身份标识(CA14)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="inv" vtype="textfield" label="投资人/主管部门名称" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="invtype" vtype="textfield" label="投资人类型/主管部门类型(CA24)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
</body>
</html>