<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>多证合一信息</title>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script>
 $(function(res){  
	var pripid = getUrlParam("priPid");
	loadData(pripid);
});  
function loadData(pripid){
 	$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
			'/trsquery/querynzwzduozhengheyi.do?pripid='+pripid);
	$('div[name="formpanel"]').formpanel('option', 'readonly', true);
	$('div[name="formpanel"]').formpanel('reload'); 
}

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg); 
	if (r != null)
		return unescape(r[2]);
	return null; 
}

/* function fixColumn(event,obj){
	var data = obj.data;
	alert(1);
	
	for(var i=0;i<data.length;i++){
		
		var issmalente = data[i]["issmalente"];
		alert(issmalente);
		
		if(issmalente == '1'){
			issmalente = '是';
		}else if(issmalente == '0'){
			issmalente = '否';
		}else{
			issmalente = "";
		}
		
		alert(issmalente);
		var htm ='<div class="jazz-grid-cell-inner">'
			
			+'</div>';
		data[i]["fix"] = htm; 
	}
	return data;
} */
</script>
<style>
 table td{word-break: keep-all;white-space:nowrap;}
.jazz-form-text-label{
	color: #000;
	font-weight: normal;
} 
li{
	list-style:none;
	color: red;
}
.biaoti{
	color: #0371a7;
	font-size:14px;
	line-height:32px; 
	font-family: "Microsoft YaHei";
	text-transform: none;
    font-weight: normal;
}
td{
	height:27px;
	color: #333333;
    font-size: 12px;
    font-family: "宋体";
    /* padding: 7px 0px 0px; */
    word-wrap: break-word;
    BORDER-RIGHT: #f6f6f6 0px solid;/*  //显示右边框为1px，如果不想显示就为0px */
	BORDER-TOP: #f9f9f9 1px solid; /* //显示上边框为1px，如果不想显示就为0px */
	BORDER-LEFT: #f9f9f9 1px solid;/* //显示左边框为1px，如果不想显示就为0px */
	BORDER-BOTTOM: #f5f5f5 0px solid;/* //显下右边框为1px，如果不想显示就为0px  */
}
tr{
border:1px solid #AFB0B1;
border-left: 0px solid #AFB0B1;
}

.jazz-formpanel-content{
	overflow-x: hidden;
}
</style>
</head>
<body >
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" >
      
       		<div class ="biaoti">&nbsp;&nbsp;税务信息:</div><br>
       		  <div name="hypotaxis" vtype="textfield" label="隶属关系" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
       		  <div name="accosystcode" vtype="textfield" label="适用会计制度" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
       		  <div name="prodbusiaddr" vtype="textfield" label="生产经营地" maxlength="50" labelwidth="100" labelAlign="right" width="200%"></div><br>
       		  <div name="orgabusiincotaxbeloagen" vtype="textfield" label="总机构企业所得税所属机关" maxlength="50" labelwidth="170" labelAlign="left" width="200%"></div><br>
       		  <div name="regipostcode" vtype="textfield" label="注册地邮政编码" maxlength="50" labelwidth="100" labelAlign="right" width="*0%"></div>
       		  <div name="regicontphonnumb" vtype="textfield" label="注册地联系电话" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
       		  <div name="calcmethcode" vtype="textfield" label="核算方式" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
       		  <div name="wzwz" vtype="textfield" label="网站网址" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
       		  <div name="empnum" vtype="textfield" label="从业人数" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
       		  <div name="forempnum" vtype="textfield" label="外籍人数" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
       		
       		<div class = "biaoti">&nbsp;&nbsp;公安信息:</div><br>
       		  <div name="sealtype" vtype="textfield" label="刻章申请" maxlength="50" labelwidth="100" labelAlign="right" width="200%"></div><br>
       		  <div name="sfsqdzyz" vtype="textfield" label="是否申请电子刻章" maxlength="50" labelwidth="120" labelAlign="right" width="200%"></div><br>
       		 
       		 
       		<div class = "biaoti">&nbsp;&nbsp;社保信息:</div><br>
       		    <div name="sociseculink" vtype="textfield" label="社会联保人" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
          		<div name="socisecucontphonnumb" vtype="textfield" label="社会联保人电话" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
       		
       			
       		<div class = "biaoti">&nbsp;&nbsp;小微企业声明:</div><br>
          		<div name="issmalente" vtype="textfield" label="本企业是否属小型企业或微型企业" maxlength="50" labelwidth="205" labelAlign="left" width="188%"></div><br>
          
       		<div class = "biaoti">&nbsp;&nbsp;网络经营者电子标识:</div><br>
          		<div name="entewebs" vtype="textfield" label="网站地址" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
          		<div name="entewebsname" vtype="textfield" label="网站名称" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
          		<div name="webtype" vtype="textfield" label="网站类型" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
          		<div name="webpname" vtype="textfield" label="网站负责人名称" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
          		<div name="webpphone" vtype="textfield" label="网站负责人手机" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
          		<div name="webpmail" vtype="textfield" label="网站负责人邮箱" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
    </div>
    
    <script type="text/javascript">
    	$(function(){
    		 $("tr:even").css({background:"#EFF6FA"});
    		 $("tr:odd").css({background:"#FBFDFD"});
    	})
    
    </script> 
</body>
</html>
