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
	regino = res.getAttr("regino");
	loadData(personid,regino);
}
function loadData(personid,regino){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/trsquery/querynzwzrenyuanxxdataBg.do?personid='+personid+'&regino='+regino);
$('div[name="formpanel"]').formpanel('option', 'readonly', true);
$('div[name="formpanel"]').formpanel('reload');
}
</script>
<style>
/*文字超出不换行*/
table td{word-break: keep-all;white-space:nowrap;}
.jazz-form-text-label{
	color: #000;
	font-weight: normal;
}
</style>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="name" vtype="textfield" label="姓名" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="sex" vtype="textfield" label="性别" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="certype" vtype="textfield" label="证件类型" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cerno" vtype="textfield" label="证件号码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <!--  <div name="post" vtype="textfield" label="职务" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div> -->
        <div name="forname" vtype="textfield" label="英文名" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="mobel" vtype="textfield" label="移动电话" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="tel" vtype="textfield" label="电话" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="postalcode" vtype="textfield" label="邮政编码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="email" vtype="textfield" label="电子邮件" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <!--  <div name="natdate" vtype="datefield" dataformat="YYYY-MM-DD" label="出生日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div> -->
        <div name="litedeg" vtype="textfield" label="文化程度" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="countrycode" vtype="textfield" label="国别(地区)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="addr" vtype="textfield" label="地址" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cervalper" vtype="textfield" label="证件有效期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <!--<div name="posbrform" vtype="textfield" label="职务产生方式" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="offhfrom" vtype="textfield" label="任职起始日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="offhto" vtype="textfield" label="任职截止日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="appointunit" vtype="textfield" label="委派单位" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div> --> 
    </div>
    
    <script type="text/javascript">
    	$(function(){
    		 $("tr:even").css({background:"#EFF6FA"});
    		 $("tr:odd").css({background:"#FBFDFD"});
    	})
    
    </script> 
</body>
</html>
