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
var personid;
function initData(res){
	personid = res.getAttr("personid");
	loadData(personid);
}
function loadData(personid){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/trsquery/querynzwzrenyuanxxdata.do?personid='+personid);
$('div[name="formpanel"]').formpanel('option', 'readonly', true);
$('div[name="formpanel"]').formpanel('reload');
}
</script>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="personid" vtype="textfield" label="人员ID" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pripid" vtype="textfield" label="主体身份代码(CA14)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="name" vtype="textfield" label="姓名" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="sex" vtype="textfield" label="性别(CB17)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="natdate" vtype="textfield" label="出生日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="certype" vtype="textfield" label="证件类型(CB16)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cerno" vtype="textfield" label="证件号码/代表证编号（DB320）" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="position" vtype="textfield" label="职务(CB18)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="position_cn" vtype="textfield" label="职务（中文名称）" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="posbrform" vtype="textfield" label="职务产生方式(CB19)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="occstbeapp" vtype="textfield" label="申请前职业状况" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="lerepsign" vtype="textfield" label="法定代表人标志/首席代表标志/负责人标识" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="appounit" vtype="textfield" label="任命单位/委派方" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="tel" vtype="textfield" label="联系电话" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="country" vtype="textfield" label="国别（地区）(CA02)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="telnumber" vtype="textfield" label="固定电话" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="mobtel" vtype="textfield" label="移动电话" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="email" vtype="textfield" label="电子邮箱" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="houseadd" vtype="textfield" label="住址" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="arrchdate" vtype="textfield" label="入境时间" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="repcarfrom" vtype="textfield" label="代表证期限自/任职起始日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="repcarto" vtype="textfield" label="代表证期限至/任职截止日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="postalcode" vtype="textfield" label="邮政编码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
</body>
</html>
