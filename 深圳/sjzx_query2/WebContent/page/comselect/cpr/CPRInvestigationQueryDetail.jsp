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
var investigationid;
function initData(res){
	investigationid = res.getAttr("investigationid");
	loadData(investigationid);
}
function loadData(investigationid){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/CPRShow/CPRInvestigationDetail.do?investigationid='+investigationid);
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
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" layout="table" style="text-align: left;" layoutconfig="{cols:2, columnwidth: ['50%','50%']}"  height="100%" width="100%">
        
        <div name="invusename" vtype="textfield" label="调查人姓名" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
        <div name="invdepname" vtype="textfield" label="调查部门" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
        <div name="investigatetime" vtype="textfield" label="调查时间" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
        <div name="surman" vtype="textfield" label="被调查人" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
        <div name="invedsex" vtype="textfield" label="被调查人性别" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
        <div name="invedage" vtype="textfield" label="被调查人年龄" maxlength="50" labelwidth="125" labelAlign="right" width="90%"></div>
       <div name="invplace" vtype="textfield" label="调查地点" maxlength="50" labelwidth="125" labelAlign="right" width="90%"  colspan="2" rowspan="1"></div>
        <div name="invedaddress" vtype="textfield" label="单位或地址" maxlength="50" labelwidth="125" labelAlign="right" width="90%" colspan="2" rowspan="1"></div>
        <div name="content" vtype="textfield" label="调查内容" maxlength="50" labelwidth="125" labelAlign="right" width="90%" colspan="2" rowspan="3"></div>
    </div>
    
    
  <script type="text/javascript">
    $(function(){
   	 	$("tr:even").css({background:"#EFF6FA"});
	 	$("tr:odd").css({background:"#FBFDFD"});
	});
    </script>
</body>
</html>
