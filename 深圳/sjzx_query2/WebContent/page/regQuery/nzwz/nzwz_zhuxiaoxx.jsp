<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
</head>
<script>
var recid;
function initData(res){
	recid = res.getAttr("recid");
	loadData(recid);
}
function loadData(recid){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/trsquery/querynzwzzhuxiaoxxdata.do?recid='+recid);
$('div[name="formpanel"]').formpanel('option', 'readonly', true);
$('div[name="formpanel"]').formpanel('reload');
}
</script>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="recid" vtype="textfield" label="记录id" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="canrea" vtype="textfield" label="注销原因" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="extclebrsign" vtype="textfield" label="对外投资清理完毕标志" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cancelbrsign" vtype="textfield" label="分公司注销登记情况" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="declebrsign" vtype="textfield" label="债权债务清理完结情况" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="affwritno" vtype="textfield" label="清算组成员备案确认文书编号" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pubnewsname" vtype="textfield" label="公告报纸名称" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pubdate" vtype="textfield" label="公告日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="sanauth" vtype="textfield" label="批准机关" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="sandocno" vtype="textfield" label="批准文号" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="sandate" vtype="textfield" label="批准日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cleantax" vtype="textfield" label="清稅情况(CA93)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cancellationcertificate" vtype="textfield" label="批准证书缴销情况" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="customsettlement" vtype="textfield" label="海关手续清缴情况(CA94)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cleanbondunit" vtype="textfield" label="清理债权债务单位" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pripid" vtype="textfield" label="主体身份代码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="candate" vtype="textfield" label="注销日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
</body>
</html>
