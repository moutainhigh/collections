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
var casetranid;
function initData(res){
	casetranid = res.getAttr("casetranid");
	loadData(casetranid);
}
function loadData(casetranid){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/casequery/queryanjianyisongxxdata.do?casetranid='+casetranid);
$('div[name="formpanel"]').formpanel('option', 'readonly', true)
$('div[name="formpanel"]').formpanel('reload');
}
</script>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="casetranid" vtype="textfield" label="案件移送ID" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="caseid" vtype="textfield" label="案件基本信息ID" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="accptranfauthtype" vtype="textfield" label="受移送机关类别(CE45)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="accptranfauth" vtype="textfield" label="受移送机关" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="tranfreatype" vtype="textfield" label="移送原因类别(CE47)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="tranfrea" vtype="textfield" label="移送原因" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="tranfdate" vtype="textfield" label="移送日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="tranfnum" vtype="textfield" label="移送人数" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
</body>
</html>
