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
		'/blackent/blackentrenyuanxxdata.do?id='+personid);
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
        <div name="certype" vtype="textfield" label="证件类型" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cerno" vtype="textfield" label="证件号码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="sex" vtype="textfield" label="性别" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="natdate" vtype="textfield" label="出生日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="dom" vtype="textfield" label="住址" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="postalcode" vtype="textfield" label="邮政编码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="tel" vtype="textfield" label="电话" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="litdeg" vtype="textfield" label="文化程度" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="nation" vtype="textfield" label="民族" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="polstand" vtype="textfield" label="政治面貌" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="occst" vtype="textfield" label="职业状况" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="offsign" vtype="textfield" label="公务员标志" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="accdside" vtype="textfield" label="委派方" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="lerepsign" vtype="textfield" label="法定代表人标志" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="character" vtype="textfield" label="经营者主体特征" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="country" vtype="textfield" label="国别（地区）" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="arrchdate" vtype="textfield" label="来华日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cerlssdate" vtype="textfield" label="签证签发日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cervalper" vtype="textfield" label="签证有效期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="chiofthedelsign" vtype="textfield" label="首席代表标志" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="notorg" vtype="textfield" label="身份核证文件核(公)证机构(人)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="notdocno" vtype="textfield" label="身份核证文件编号" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
       <!--  <div name="sExtValidflag" vtype="textfield" label="标识位" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div> -->
        <div name="sExtNodenum" vtype="textfield" label="数据来源" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
    
     <script type="text/javascript">
    $(function(){
   	 	$("tr:even").css({background:"#EFF6FA"});
	 	$("tr:odd").css({background:"#FBFDFD"});
	});
    </script>
</body>
</html>
