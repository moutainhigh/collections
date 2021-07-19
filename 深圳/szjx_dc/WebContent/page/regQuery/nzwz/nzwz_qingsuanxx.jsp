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
var liid;
function initData(res){
	liid = res.getAttr("liid");
	loadData(liid);
}
function loadData(liid){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/trsquery/querynzwzqingsuanxxdata.do?liid='+liid);
$('div[name="formpanel"]').formpanel('option', 'readonly', true);
$('div[name="formpanel"]').formpanel('reload');
}
</script>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="liid" vtype="textfield" label="清算信息编号" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pripid" vtype="textfield" label="主体身份代码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="ligst" vtype="textfield" label="清算完结情况(CD14)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="ligenddate" vtype="textfield" label="清算完结日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="debttranee" vtype="textfield" label="债务承接人" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="claimtranee" vtype="textfield" label="债权承接人" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="limeid" vtype="textfield" label="清算成员编号" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pripid" vtype="textfield" label="主体身份代码(CA14)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="liqmem" vtype="textfield" label="清算组成员" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="certype" vtype="textfield" label="证件类型(CB16)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cerno" vtype="textfield" label="证件号码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="addr" vtype="textfield" label="地址" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="tel" vtype="textfield" label="联系电话" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="ligprisign" vtype="textfield" label="清算负责人标志" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
</body>
</html>
