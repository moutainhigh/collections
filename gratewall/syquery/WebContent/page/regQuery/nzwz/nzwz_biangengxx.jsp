<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>变更信息</title>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script>
	var recid;
	function initData(res){
		recid = res.getAttr("recid");
		loadData(recid);
	}
	function loadData(recid){
		$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
			'/trsquery/querynzwzbiangengxxdata.do?recid='+recid);
		$('div[name="formpanel"]').formpanel('option', 'readonly', true);
		$('div[name="formpanel"]').formpanel('reload');
	}
</script>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="openo" vtype="textfield" label="业务编号" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="altitem" vtype="textfield" label="变更事项" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="altbe" vtype="textfield" label="变更前内容" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="altaf" vtype="textfield" label="变更后内容" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="altdate" vtype="textfield" label="变更日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="alttime" vtype="textfield" label="变更次数" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
</body>
</html>
