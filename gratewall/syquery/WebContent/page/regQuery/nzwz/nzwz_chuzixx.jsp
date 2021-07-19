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
var cerno;
function initData(res){
	invid = res.getAttr("invid");
	cerno = res.getAttr("cerno");	
	loadData(invid);
}

function loadData(invid){
	$('div[name="jbxxGrid"]').gridpanel('option','dataurl',rootPath+
			'/trsquery/querynzwzchuzifsdata.do?invid='+invid);
	$('div[name="jbxxGrid"]').gridpanel('query', [ 'formpanel' ]);
	//$('div[name="jbxxGrid"]').gridpanel('option', 'readonly', true);
	//$('div[name="jbxxGrid"]').gridpanel('reload');
	
	$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
			'/trsquery/querynzwzchuzixxdata.do?invid='+invid);
	$('div[name="formpanel"]').formpanel('option', 'readonly', true);
	$('div[name="formpanel"]').formpanel('reload');

$('div[name="jbxxGrid"]').formpanel('option','dataurl',rootPath+
		'/trsquery/querynzwzchuzijihuaxxdata.do?invid='+invid);

$('div[name="jbxxGrid"]').formpanel('reload');
}
function viewCerno(obj){
	
	//$('#cerno1').html(cerno);
	var _this = $(obj);
	if(cerno==""||cerno=="undefined"||cerno==undefined){
		_this.html("").addClass("none");
	}else{
		_this.html(cerno).addClass("none");	
	}
	$.ajax({
		url:rootPath+'/reg/showphone.do',
		data:{
			flag : "查询出资人员证照号码"
		},
		type:"post",
		dataType : 'json',
		success:function(data){

		}
	});
}

</script>

<style>
/*文字超出不换行*/
table td{word-break: keep-all;white-space:nowrap;}
.jazz-form-text-label{
	color: #000;
	font-weight: normal;
}



.none{
	text-decoration: none;
	color: #000;
	cursor: default;
}
</style>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="62%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['45%','*']}">
        <div name="inv" vtype="textfield" label="投资人名称" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="invtype" vtype="textfield" label="投资人类型" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="certype" vtype="textfield" label="证件类型" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div id="cerno1" name="cerno1" vtype="textfield" label="证件号码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"><a href="javascript:void(0)" onclick="viewCerno(this)">查看(该操作会记录日志)</a></div>
        <div name="subconam" vtype="textfield" label="认缴出资额" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="acconam" vtype="textfield" label="实缴出资额" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="conprop" vtype="textfield" label="出资比例" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="exeaffsign" vtype="textfield" label="执行合伙事务标志" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="invatt" vtype="textfield" label="投资人属性" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="dom" vtype="textfield" label="住所" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="countrycode" vtype="textfield" label="国别(地区)" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="responway" vtype="textfield" label="承担责任方式" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="tzfxz" vtype="textfield" label="投资方经济性质" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
    
    
    <div vtype="gridpanel" name="jbxxGrid" height="198" width="100%"
		id='jbxxGrid' dataloadcomplete="" datarender=""
		selecttype="1" titledisplay="true" title="出资方式" showborder="false"
		isshowselecthelper="false" selecttype="2">
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='conform' text="出资方式" textalign="center" width="50%"></div>
				<div name='conam' text="出资额(万元)" textalign="center" width="50%"></div>
			</div>
		</div>
		
		<div vtype="gridtable" name="grid_table" id="grid_table" rowrender=""></div>
		<div vtype="paginator" name="grid_paginator"></div>
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