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
var froid;
function initData(res){
	froid = res.getAttr("froid");
	loadData(froid);
}
function loadData(froid){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/trsquery/querynzwzgqdongjiexxdata.do?froid='+froid);
$('div[name="formpanel"]').formpanel('option', 'readonly', true);
$('div[name="formpanel"]').formpanel('reload');
}
</script>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="froid" vtype="textfield" label="冻结ID" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pripid" vtype="textfield" label="被冻结股权所在市场主体身份代码(CA14)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="parent_id" vtype="textfield" label="股权冻结被执行人信息ID" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="executeitem" vtype="textfield" label="执行事项(CZ12)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="executeitem_cn" vtype="textfield" label="执行事项（中文名称）" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="froauth" vtype="textfield" label="执行法院" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="frodocno" vtype="textfield" label="执行裁定书文号" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="executeno" vtype="textfield" label="协助执行通知书文号" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="invtype" vtype="textfield" label="被执行人类型(CA24)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="invtype_cn" vtype="textfield" label="被执行人类型（中文名称）" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="inv" vtype="textfield" label="被执行人" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="certype" vtype="textfield" label="证件类型(CB16)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="certype_cn" vtype="textfield" label="证件类型（中文名称）" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cerno" vtype="textfield" label="证件号码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="blictype" vtype="textfield" label="证照类型(CA50)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="blictype_cn" vtype="textfield" label="证照类型（中文名称）" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="blicno" vtype="textfield" label="证照号码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="corentname" vtype="textfield" label="被冻结股权所在市场主体名称" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="regno" vtype="textfield" label="被冻结股权所在市场主体注册号(CA15)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="uniscid" vtype="textfield" label="被冻结股权所在市场统一社会信用代码(CA91)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="froam" vtype="textfield" label="股权数额" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="foramme" vtype="textfield" label="股权数额单位" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="regcapcur" vtype="textfield" label="币种(CA04)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="regcapcur_cn" vtype="textfield" label="币种（中文名称）" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="frozdeadline" vtype="textfield" label="冻结期限" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="frofrom" vtype="textfield" label="冻结期限自" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="froto" vtype="textfield" label="冻结期限至" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="keepfrofrom" vtype="textfield" label="续行冻结期限自" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="keepfroto" vtype="textfield" label="续行冻结期限至" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="keepfrozdeadline" vtype="textfield" label="续行冻结期限" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="publicdate" vtype="textfield" label="公示日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="frozstate" vtype="textfield" label="股权冻结状态(CZ13)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="frozstate_cn" vtype="textfield" label="股权冻结状态（中文名称）" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="thawdate" vtype="textfield" label="解冻日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="loseeffdate" vtype="textfield" label="失效日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="loseeffres" vtype="textfield" label="失效原因(CZ14)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="loseeffres_cn" vtype="textfield" label="失效原因（中文名称）" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
</body>
</html>
