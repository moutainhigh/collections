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
var casepartyid;
function initData(res){
	casepartyid = res.getAttr("casepartyid");
	loadData(casepartyid);
}
function loadData(casepartyid){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/casequery/querydangshirenxxdata.do?casepartyid='+casepartyid);
$('div[name="formpanel"]').formpanel('option', 'readonly', true)
$('div[name="formpanel"]').formpanel('reload');
}
</script>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="casepartyid" vtype="textfield" label="案件当事人ID" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="caseid" vtype="textfield" label="案件记录ID" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="partytype" vtype="textfield" label="当事人类型" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="unitname" vtype="textfield" label="当事人名称" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pripid" vtype="textfield" label="主体身份代码(CA14)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="regno" vtype="textfield" label="注册号(CA15)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="enttype" vtype="textfield" label="市场主体类型(CA16)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="uniscid" vtype="textfield" label="统一社会信用代码(CA91)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="lerep" vtype="textfield" label="法定代表人" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="sex" vtype="textfield" label="性别(CB17)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="age" vtype="textfield" label="年龄" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="occupation" vtype="textfield" label="职业" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="dom" vtype="textfield" label="住所" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="tel" vtype="textfield" label="联系电话" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="postalcode" vtype="textfield" label="邮政编码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="workunit" vtype="textfield" label="工作单位" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="certype" vtype="textfield" label="证件类型(CB16)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cerno" vtype="textfield" label="证件号码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
</body>
</html>
