<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title>市场主体信息展示</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/jbxx/detial.css" />

<style type="text/css">
td{
	color: #666;
    font-size: 12px;
    font-family: "瀹嬩綋";
    /* padding: 7px 0px 0px; */
    font-weight:bold;
    word-wrap: break-word;
}
tr{
border:1px solid #AFB0B1;
}
</style>
<script type="text/javascript">
$(function() {
	$.ajax({
		url:rootPath+'/reg/scztjbxx.do',
		data:{
			flag : parent.entityNo,
			priPid : parent.priPid,
			sourceflag : parent.sourceflag,
			economicproperty : parent.economicproperty,
			type : parent.type
		},
		type:"post",
		dataType : 'json',
		success:function(data){
			//var st = '<tr><td style="text-align: right;">企业(机构)名称:</td><td>佛山市高明区荷城谁见谁爱服饰店</td><td style="text-align: right;">企业(机构)名称:</td><td>佛山市高明区荷城谁见谁爱服饰店</td></tr><tr><td style="text-align: right;">企业(机构)名称:</td><td colspan="3">佛山市高明区荷城谁见谁爱服饰店</td></tr>';
			$('#tabs tbody').append(data.returnString);
			$('#hiddEnttype').val(data.enttype);
			$('#entname').append(data.entname);
			$('#regno').append(data.regno);
		}
	});
});
/* /shareResource/selectShareTable.do */
</script>
</head>

<body>
<div class="jazz-panel-titlebar jazz-panel-header jazz-formpanel-titlebar">
	<div class="jazz-formpanel-titlebar-inner" style="width:100%; height:100%">
	<span class="jazz-panel-title-font jazz-formpanel-title-font">
	<a id="entname" style="font-size:19px;">主体名称：</a>
	<a id="regno" style="float:right;font-size:19px;">注册号:</a></span>
	<span class="jazz-panel-rtl-left" style=""></span><span class="jazz-panel-rtl-right" style="">
	</span></div>
</div>
<table id="tabs" cellspacing="0" cellpadding="0" style="border-collapse: collapse; table-layout: fixed; width: 100%;">
<colgroup>
<col align="right" height="40px" width="119px" style="vertical-align: middle;background-color:#DCEEEF;border:1px solid #AFB0B1;">
<col width="255px" style="vertical-align: middle;background-color:white;border:1px solid #AFB0B1;">
<col align="right" height="40px" width="119px" style="vertical-align: middle;background-color:#DCEEEF;border:1px solid #AFB0B1;">
<col width="255px" style="vertical-align: middle;background-color:white;border:1px solid #AFB0B1;">
</colgroup>
<tbody>
</tbody></table>
<input type="hidden" id="hiddEnttype"></input>
</body>
</html>
