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
var invid;
function initData(res){
	invid = res.getAttr("invid");
	loadData(invid);
}
function loadData(invid){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/trsquery/querynzwzchuzijihuaxxdata.do?invid='+invid);
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
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['45%','*']}">
        <div name="contermno" vtype="textfield" label="出资期次" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="confrom" vtype="textfield" label="出资期限自" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="conto" vtype="textfield" label="出资期限至" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="cursubconam" vtype="textfield" label="本期认缴出资额" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="curactconam" vtype="textfield" label="实际出资" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="acconddte" vtype="textfield" label="实际出资日期" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="regcapsqusign" vtype="textfield" label="是否到位" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="ncashcontranto" vtype="textfield" label="非货币出资应办理过户手续截止日期" maxlength="200" labelwidth="150" labelAlign="right" width="90%"></div>
        <div name="remark" vtype="textfield" label="备注" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
    
    <script type="text/javascript">
    	$(function(){
    		var text = $("div[name='acconam']").textfield("getValue");
    		 $("div[name='acconam']").css("display","none");
    		
    		 
    		 
    		 
    		 $("tr:even").css({background:"#EFF6FA"});
    		 $("tr:odd").css({background:"#FBFDFD"});
    	})
    
    </script>
</body>
</html>