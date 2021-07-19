<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script>
	var recid;
	function initData(res){
		recid = res.getAttr("recid");
		loadData(recid);
	}
	function loadData(recid){
		$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
			'/trsquery/querynzwzcaiwufuzexxdata.do?recid='+recid);
		$('div[name="formpanel"]').formpanel('option', 'readonly', true);
		$('div[name="formpanel"]').formpanel('reload');
	}
</script>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="recid" vtype="textfield" label="财务负责人ID" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="name" vtype="textfield" label="姓名" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="certype" vtype="textfield" label="证件类型" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cerno" vtype="textfield" label="证件号码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="tel" vtype="textfield" label="固定电话" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="mobtel" vtype="textfield" label="移动电话" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="email" vtype="textfield" label="电子邮箱" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
</body>
</html>
