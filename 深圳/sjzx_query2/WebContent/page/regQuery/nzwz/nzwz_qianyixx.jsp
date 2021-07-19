<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script>
var id;
function initData(res){
	id = res.getAttr("id");
	loadData(id);
}
function loadData(id){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/trsquery/querynzwzqianyixxdata.do?id='+id);
$('div[name="formpanel"]').formpanel('option', 'readonly', true);
$('div[name="formpanel"]').formpanel('reload');
}
</script>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="id" vtype="textfield" label="迁入/迁出id" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pripid" vtype="textfield" label="主体身份代码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="minletnum" vtype="textfield" label="函号" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="moutarea" vtype="textfield" label="迁入/迁出地区" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="minrea" vtype="textfield" label="原因" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="moutareregorg" vtype="textfield" label="登记机关(CA11)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="mindate" vtype="textfield" label="日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="qrqctype" vtype="textfield" label="迁移类型" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
</body>
</html>