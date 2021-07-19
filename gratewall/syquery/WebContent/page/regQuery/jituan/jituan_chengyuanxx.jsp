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
var grpmemid;
function initData(res){
	grpmemid = res.getAttr("grpmemid");
	loadData(grpmemid);
}
function loadData(grpmemid){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/trsquery/queryjituanchengyuanxxdata.do?grpmemid='+grpmemid);
$('div[name="formpanel"]').formpanel('option', 'readonly', true)
$('div[name="formpanel"]').formpanel('reload');
}
</script>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="inv" vtype="textfield" label="投资人名称" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="invtype" vtype="textfield" label="投资人类型" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="subconam" vtype="textfield" label="认缴出资额" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="acconam" vtype="textfield" label="实缴出资额" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="conprop" vtype="textfield" label="出资比例" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <!--  <div name="exeaffsign" vtype="textfield" label="执行合伙事务标志" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div> -->
        <div name="invatt" vtype="textfield" label="投资人属性" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="dom" vtype="textfield" label="住所" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="countrycode" vtype="textfield" label="国别(地区)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <!--  <div name="responway" vtype="textfield" label="承担责任方式" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div> -->
        <!--  <div name="tzfxz" vtype="textfield" label="投资方经济性质" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div> -->
    </div>
    <script type="text/javascript">
    	$(function(){
    		 $("tr:even").css({background:"#EFF6FA"});
    		 $("tr:odd").css({background:"#FBFDFD"});
    	})
    
    </script> 
</body>
</html>