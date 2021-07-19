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
var brpripid;
function initData(res){
	brpripid = res.getAttr("brpripid");
	loadData(brpripid);
}
function loadData(brpripid){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/trsquery/querynzwzlishuxxdata.do?brpripid='+brpripid);
$('div[name="formpanel"]').formpanel('option', 'readonly', true);
$('div[name="formpanel"]').formpanel('reload');
}
</script>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <!--  <div name="brpripid" vtype="textfield" label="主体身份代码(CA14)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div> -->
        <!--  <div name="pripid" vtype="textfield" label="隶属主体身份代码(CA14)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div> -->
        <div name="entname" vtype="textfield" label="企业(机构)名称" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="regno" vtype="textfield" label="注册号(CA15)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="uniscid" vtype="textfield" label="统一社会信用代码(CA91)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
</body>
</html>