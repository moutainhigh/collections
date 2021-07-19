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
		'/trsquery/querynzwzxukexxdata.do?recid='+recid);
$('div[name="formpanel"]').formpanel('option', 'readonly', true);
$('div[name="formpanel"]').formpanel('reload');
}
</script>
<style type="text/css">
.jazz-form-text-label{
	color: #000;
	font-weight: normal;
}
</style>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="licname" vtype="textfield" label="审批文件名称" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="licno" vtype="textfield" label="审批文件号" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="valfrom" vtype="datefield" dataformat="YYYY-MM-DD" label="有效期自" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="valto" vtype="datefield" dataformat="YYYY-MM-DD" label="有效期至" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="licstate" vtype="textfield" label="审批文件状态" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="licauth" vtype="textfield" label="发证机关" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="abuitemco" vtype="textfield" label="审批项目" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="licflag" vtype="textfield" label="前置后置标志" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="jgrlflag" vtype="textfield" label="监管认领情况" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
    
    <script type="text/javascript">
    $(function(){
    	 $("tr:even").css({background:"#EFF6FA"});
		 $("tr:odd").css({background:"#FBFDFD"});
    })
    
    </script>
</body>
</html>
