<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9">
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
		'/trsquery/querynzwzgqchuzhixxdata.do?recid='+recid);
$('div[name="formpanel"]').formpanel('option', 'readonly', true);
$('div[name="formpanel"]').formpanel('reload');
}
</script>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="recid" vtype="textfield" label="股权出质信息ID" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pripid" vtype="textfield" label="股权所在公司主体身份代码(CA14)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="entname" vtype="textfield" label="股权所在公司名称" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="regno" vtype="textfield" label="股权所在公司注册号(CA15)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="uniscid" vtype="textfield" label="股权所在公司统一社会信用代码(CA91)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="equityno" vtype="textfield" label="股权登记编号" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pledgor" vtype="textfield" label="出质人" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pledblictype" vtype="textfield" label="出质人证照类型(CA50)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pledblictype_cn" vtype="textfield" label="出质人证照类型（中文名称）" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pledblicno" vtype="textfield" label="出质人证照号码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pledamunit" vtype="textfield" label="出质股权数额单位" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="impam" vtype="textfield" label="出质股权数额" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="regcapcur" vtype="textfield" label="出质股权数额币种(CA04)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="regcapcur_cn" vtype="textfield" label="出质股权数额币种（中文名称）" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="imporg" vtype="textfield" label="质权人" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="imporgblictype" vtype="textfield" label="质权人证照类型(CA50)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="imporgblictype_cn" vtype="textfield" label="质权人证照类型（中文名称）" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="imporgblicno" vtype="textfield" label="质权人证照号码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="equpledate" vtype="textfield" label="股权出质登记日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="type" vtype="textfield" label="状态" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="candate" vtype="textfield" label="注销日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="equplecanrea" vtype="textfield" label="注销原因" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="canceldate" vtype="textfield" label="撤销日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cancelrea" vtype="textfield" label="撤销原因" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="publicdate" vtype="textfield" label="公示日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
</body>
</html>
