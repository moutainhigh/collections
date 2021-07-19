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
	personid = res.getAttr("id");
	loadData(personid);
}
function loadData(personid){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/blackent/yrtouzirenxxdata.do?id='+personid);
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
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" layout="table"  style="text-align: left;" layoutconfig="{cols:2, columnwidth: ['50%','50%']}"  height="100%" width="100%">
        <div name="inv" vtype="textfield" label="投资人" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
        <div name="invtype" vtype="textfield" label="投资人类型" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
        <div name="certype" vtype="textfield" label="证件类型" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
        <div name="cerno" vtype="textfield" label="证件号码" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
        <div name="blictype" vtype="textfield" label="证照类型" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
        <div name="blicno" vtype="textfield" label="证照号码" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
        <div name="country" vtype="textfield" label="国家（地区）" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
        <div name="currency" vtype="textfield" label="币种" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
        <div name="subconam" vtype="textfield" label="认缴出资额" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
        <div name="acconam" vtype="textfield" label="实缴出资额" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
        <div name="subconamusd" vtype="textfield" label="认缴出资额折万美元" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
        <div name="acconamusd" vtype="textfield" label="实缴出资额折万美元" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
        <div name="conprop" vtype="textfield" label="出资比例" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
        <div name="conform" vtype="textfield" label="出资方式" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
        <div name="condate" vtype="textfield" label="出资日期" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
        <div name="baldelper" vtype="textfield" label="余额缴付期限" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
        <div name="conam" vtype="textfield" label="出资额" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
        <div name="exeaffsign" vtype="textfield" label="执行合伙事务标志" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
      <!--   <div name="sExtValidflag" vtype="textfield" label="标识位" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div> -->
        <div name="sExtNodenum" vtype="textfield" label="数据来源" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
    </div>
    
      <script type="text/javascript">
    $(function(){
   	 	$("tr:even").css({background:"#EFF6FA"});
	 	$("tr:odd").css({background:"#FBFDFD"});
	});
    </script>
</body>
</html>
