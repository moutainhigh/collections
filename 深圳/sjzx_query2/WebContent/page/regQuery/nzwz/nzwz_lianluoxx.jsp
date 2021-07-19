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
		'/trsquery/querynzwzlianluoxxdata.do?recid='+recid);
$('div[name="formpanel"]').formpanel('option', 'readonly', true);
$('div[name="formpanel"]').formpanel('reload');
}
</script>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="recid" vtype="textfield" label="记录ID" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pripid" vtype="textfield" label="主体身份代码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="personid" vtype="textfield" label="联络员记录ID" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="department" vtype="textfield" label="部门" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="s_ext_timestamp" vtype="textfield" label="时间戳" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="update_time" vtype="textfield" label="更新时间戳" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
</body>
</html>