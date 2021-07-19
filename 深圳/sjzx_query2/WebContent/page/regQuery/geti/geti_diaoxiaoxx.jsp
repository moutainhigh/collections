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
var revid;
function initData(res){
	revid = res.getAttr("revid");
	loadData(revid);
}
function loadData(revid){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/trsquery/querygetidiaoxiaoxxdata.do?revid='+revid);
$('div[name="formpanel"]').formpanel('option', 'readonly', true)
$('div[name="formpanel"]').formpanel('reload');
}
</script>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="revid" vtype="textfield" label="吊销信息id" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pripid" vtype="textfield" label="主体身份代码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="revrea" vtype="textfield" label="吊销登记原因" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="revdate" vtype="datefield" dataformat="YYYY-MM-DD" label="吊销日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
</body>
</html>
