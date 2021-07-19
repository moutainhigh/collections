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
var memid;
function initData(res){
	memid = res.getAttr("memid");
	loadData(memid);
}
function loadData(memid){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/trsquery/queryjituanchengyuanxxdata.do?memid='+memid);
$('div[name="formpanel"]').formpanel('option', 'readonly', true)
$('div[name="formpanel"]').formpanel('reload');
}
</script>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="memid" vtype="textfield" label="成员ID" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="uniscid" vtype="textfield" label="统一社会信用代码(CA91)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pripid" vtype="textfield" label="集团主体身份代码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="grpmemtype" vtype="textfield" label="集团成员类型" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="memname" vtype="textfield" label="成员名称" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="regno" vtype="textfield" label="注册号(CA15)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="regcap" vtype="textfield" label="注册资本(金)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
</body>
</html>