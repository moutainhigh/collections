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
var altid;
function initData(res){
	altid = res.getAttr("altid");
	loadData(altid);
}
function loadData(altid){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/trsquery/querygetibiangengxxdata.do?altid='+altid);
$('div[name="formpanel"]').formpanel('option', 'readonly', true)
$('div[name="formpanel"]').formpanel('reload');
}
</script>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="altid" vtype="textfield" label="变更ID" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pripid" vtype="textfield" label="主体身份代码(CA14)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="altitem" vtype="textfield" label="变更事项(新增六张广告的表)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="altitem_cn" vtype="textfield" label="变更事项（中文名称）" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="altbe" vtype="textfield" label="变更前内容" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="altaf" vtype="textfield" label="变更后内容" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="altdate" vtype="textfield" label="变更日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
</body>
</html>
