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
$(function(){
	var text = $("div[name='acconam']").textfield("getValue");
	 $("div[name='acconam']").css("display","none");
	
	 
	 
	 
	 $("tr:even").css({background:"#EFF6FA"});
	 $("tr:odd").css({background:"#FBFDFD"});
})

</script>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="persname" vtype="textfield" label="姓名" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="sex" vtype="textfield" label="性别" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="certype" vtype="textfield" label="证件类型" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cerno" vtype="textfield" label="证件号码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="forname" vtype="textfield" label="英文名" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="mobel" vtype="textfield" label="移动电话" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="tel" vtype="textfield" label="电话" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="postalcode" vtype="textfield" label="邮政编码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="email" vtype="textfield" label="电子邮件" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="natdate" vtype="datefield" dataformat="YYYY-MM-DD" label="出生日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="litedeg" vtype="textfield" label="文化程度" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="countrycode" vtype="textfield" label="国别(地区)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="addr" vtype="textfield" label="地址" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cervalper" vtype="textfield" label="证件有效期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
</body>
</html>