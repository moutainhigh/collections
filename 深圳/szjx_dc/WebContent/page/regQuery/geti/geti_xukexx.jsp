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
var licid;
function initData(res){
	licid = res.getAttr("licid");
	loadData(licid);
}
function loadData(licid){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/trsquery/querygetixukexxdata.do?licid='+licid);
$('div[name="formpanel"]').formpanel('option', 'readonly', true)
$('div[name="formpanel"]').formpanel('reload');
}
</script>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="licid" vtype="textfield" label="许可文件ID" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pripid" vtype="textfield" label="主体身份代码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="licno" vtype="textfield" label="许可文件编号" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="licname" vtype="textfield" label="许可文件名称(CA25)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="valfrom" vtype="textfield" label="有效期自" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="valto" vtype="textfield" label="有效期至" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="licanth" vtype="textfield" label="许可机关" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
</body>
</html>