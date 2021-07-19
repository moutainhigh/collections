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
var illegactid;
function initData(res){
	illegactid = res.getAttr("illegactid");
	loadData(illegactid);
}
function loadData(illegactid){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/casequery/queryweifaxxdata.do?illegactid='+illegactid);
$('div[name="formpanel"]').formpanel('option', 'readonly', true)
$('div[name="formpanel"]').formpanel('reload');
}
</script>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="illegfact" vtype="textfield" label="主要违法事实" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="illegactid" vtype="textfield" label="案件处罚ID" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="caseid" vtype="textfield" label="案件基本信息ID" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="illegacttype" vtype="textfield" label="违法行为种类(CE02)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="illegacttype_cn" vtype="textfield" label="违法行为种类_中文名称" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="illegact" vtype="textfield" label="违法行为" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="illegincome" vtype="textfield" label="违法所得" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="quabasis" vtype="textfield" label="定性依据(CE10)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="penbasis" vtype="textfield" label="处罚依据(CE10)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pentype" vtype="textfield" label="处罚种类(CE16)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="penresult" vtype="textfield" label="处罚结果(CE22)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="penam" vtype="textfield" label="罚款金额" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="forfam" vtype="textfield" label="没收金额" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="apprcuram" vtype="textfield" label="变价金额" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pendecissdate" vtype="textfield" label="处罚决定书签发日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="penexest" vtype="textfield" label="处罚执行情况" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="caseigdegree" vtype="textfield" label="违法程度(CE44)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="lenity" vtype="textfield" label="是否从轻减轻或者宽大处理" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="penbeisadgui" vtype="textfield" label="处罚前是否进行行政指导" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="sertime" vtype="textfield" label="处罚决定书送达时间" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="penauth" vtype="textfield" label="处罚机关(CA11)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="penauth_cn" vtype="textfield" label="处罚机关名称" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pendecno" vtype="textfield" label="处罚决定书文号" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
</body>
</html>
