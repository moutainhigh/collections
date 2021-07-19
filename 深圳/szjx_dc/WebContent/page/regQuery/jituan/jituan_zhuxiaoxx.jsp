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
var pripid;
function initData(res){
	pripid = res.getAttr("pripid");
	loadData(pripid);
}
function loadData(pripid){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/trsquery/queryjituanzhuxiaoxxdata.do?pripid='+pripid);
$('div[name="formpanel"]').formpanel('option', 'readonly', true)
$('div[name="formpanel"]').formpanel('reload');
}
</script>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="pripid" vtype="textfield" label="集团主体身份代码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="canrea" vtype="textfield" label="注销原因(CD16)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="candate" vtype="textfield" label="注销日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
</body>
</html>
