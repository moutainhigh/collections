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
var casesrcid;
function initData(res){
	casesrcid = res.getAttr("casesrcid");
	loadData(casesrcid);
}
function loadData(casesrcid){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/casequery/queryanyuanxxdata.do?casesrcid='+casesrcid);
$('div[name="formpanel"]').formpanel('option', 'readonly', true)
$('div[name="formpanel"]').formpanel('reload');
}
</script>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="casesrcid" vtype="textfield" label="案源ID" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="caseid" vtype="textfield" label="案件基本信息ID" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="casesou" vtype="textfield" label="案件来源(CE03)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cluecon" vtype="textfield" label="线索(举报)内容" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="regtime" vtype="textfield" label="登记时间" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
</body>
</html>
