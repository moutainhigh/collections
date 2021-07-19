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
var personid;
function initData(res){
	personid = res.getAttr("personid");
	loadData(personid);
}
function loadData(personid){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/trsquery/querygetijinyinxxdata.do?personid='+personid);
$('div[name="formpanel"]').formpanel('option', 'readonly', true)
$('div[name="formpanel"]').formpanel('reload');
}
</script>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="personid" vtype="textfield" label="人员序号" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pripid" vtype="textfield" label="主体身份代码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="name" vtype="textfield" label="姓名" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="sex" vtype="textfield" label="性别(CB17)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="natdate" vtype="textfield" label="出生日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="litedeg" vtype="textfield" label="文化程度" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="nation" vtype="textfield" label="民族(CB32)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="dom" vtype="textfield" label="住所" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="postalcode" vtype="textfield" label="邮政编码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="tel" vtype="textfield" label="联系电话" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="polstand" vtype="textfield" label="政治面貌(CB30)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="occstbeapp" vtype="textfield" label="申请前职业状况" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="certype" vtype="textfield" label="证件类型(CB16)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cerno" vtype="textfield" label="证件号码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cerissdate" vtype="textfield" label="证件签发日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="operarea" vtype="textfield" label="经营者所在地区(CA01)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cervalper" vtype="textfield" label="证件有效期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="notorg" vtype="textfield" label="身份核证文件核(公)证机构(人)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="notdocno" vtype="textfield" label="身份核证文件编号" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
</body>
</html>s