<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title>市场主体信息展示</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/jbxx/detial.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/trs/regDetail.css" />

<style type="text/css">
td{
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
/* tr:nth-child(2n){ 
  background-color:#eff6fa ;

}
tr:nth-child(2n+1){
 background-color:#fbfdfd;
} */
</style>
<script src="<%=request.getContextPath()%>/static/js/sczt/base64.js"
	type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	$.ajax({
		url:rootPath+'/reg/fadingdaibiaorenjbxx.do',
		data:{
			flag : parent.entityNo,
			priPid : parent.priPid,
			enttype: parent.enttype,
			economicproperty : parent.economicproperty //看是否内资还是外资 js页面判断
		},
		type:"post",
		dataType : 'json',
		success:function(data){
			//var st = '<tr><td style="text-align: right;">企业(机构)名称:</td><td>佛山市高明区荷城谁见谁爱服饰店</td><td style="text-align: right;">企业(机构)名称:</td><td>佛山市高明区荷城谁见谁爱服饰店</td></tr><tr><td style="text-align: right;">企业(机构)名称:</td><td colspan="3">佛山市高明区荷城谁见谁爱服饰店</td></tr>';
			$('#tabs tbody').append(data.returnString);
			$('#hiddEnttype').val(data.enttype);
			$('#entname').append(data.entname);
			$('#regno').append(data.regno);
			$("tr:nth-child(odd)").css("background","#eff6fa");
			$("tr:nth-child(even)").css("background","#fbfdfd");
		}
	});

});

function viewDataSource(cerno){
	window.open("entlist.jsp?cerno=" + encode(cerno));
}

function viewPhone(phone){
	var persname = $('#persname').html();
	$('#phone').html(phone);
	$.ajax({
		url:rootPath+'/reg/showphone.do',
		data:{
			flag : "查询法定代表人电话号码",
			entname : parent.entname,
			persname : persname
		},
		type:"post",
		dataType : 'json',
		success:function(data){

		}
	});
}

function viewTel(tel){
	var persname = $('#persname').html();
	$('#tel').html(tel);
	$.ajax({
		url:rootPath+'/reg/showphone.do',
		data:{
			flag : "查询法定代表人电话号码",
			entname : parent.entname,
			persname : persname
		},
		type:"post",
		dataType : 'json',
		success:function(data){

		}
	});
}

function viewCerno(cerno){
	var persname = $('#persname').html();
	$('#cerno').html(cerno);
	$.ajax({
		url:rootPath+'/reg/showphone.do',
		data:{
			flag : "查询法定代表人证照号码",
			entname : parent.entname,
			persname : persname
		},
		type:"post",
		dataType : 'json',
		success:function(data){

		}
	});
}
/* /shareResource/selectShareTable.do */
</script>
</head>

<body style="background-color: #dceeef;"><!-- #dceeef; -->
<div class="jazz-panel-titlebar jazz-panel-header jazz-formpanel-titlebar">
	<div class="jazz-formpanel-titlebar-inner" style="width:100%; height:100%">
	<span class="jazz-panel-title-font jazz-formpanel-title-font">
	  <a>投资人信息</a> 
	<span class="jazz-panel-rtl-left" style=""></span><span class="jazz-panel-rtl-right" style="">
	</span></div>
</div>
<table id="tabs" cellspacing="0" cellpadding="0" style="border-collapse: collapse; table-layout: fixed; width: 100%;">
<colgroup>
<col align="right" height="40px" width="130px" style="vertical-align: middle;background-color:#DCEEEF;border:1px solid #AFB0B1;border-left: 0px solid #AFB0B1;">
<col width="255px" style="vertical-align: middle;background-color:white;border:1px solid #AFB0B1;">
<col align="right" height="40px" width="130px" style="vertical-align: middle;background-color:#DCEEEF;border:1px solid #AFB0B1;">
<col width="255px" style="vertical-align: middle;background-color:white;border:1px solid #AFB0B1;">
</colgroup>
<tbody>
</tbody></table>
<input type="hidden" id="hiddEnttype"></input>
</body>
</html>