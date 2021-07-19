<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title>族谱详细信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/jbxx/detial.css" />
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>

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
	border-left: 0px solid #AFB0B1;
}
</style>
<script type="text/javascript">
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg);  //匹配目标参数
	if (r != null) return unescape(decodeURI(r[2])); return null; //返回参数值 
}
$(function() {
	var pripid=getUrlParam("pripid");
	var name=getUrlParam("name");
	var category=getUrlParam("category");
	if(typeof(name) == "undefined" || name=='' || name==null){
	}else{
		$(document).attr("title",name);
	}
	$.ajax({
		url:rootPath+'/datatrs/zpDetail.do',
		data:{
			pripid : pripid,
			name : name,
			category : category
		},
		type:"post",
		dataType : 'json',
		success:function(data){
			$('#tabs tbody').append(data.returnString);
			$('#entname').append(data.entname);
			$('#regno').append(data.regno);
		}
	});
});
</script>
</head>

<body style="background-color: #dceeef;height: 100%;"><!-- #F8F9FB; -->
<div name="row_id" height="100%" vtype="panel" layout="row" layoutconfig="{rowheight:['50px','*','30px']}">
	<div id="Header" style="background: #1c97ca">
        <div class="headertop"  style="height:50px; width:100%;">
			 <img alt="" style="position:absolute;z-index:2;margin-left: 10px;" height="37" src="<%=request.getContextPath()%>/page/sjbd/img/12.png">
			<div class='header' style="position:absolute;z-index:10;">
				<ul id="nav" >
				</ul>
			</div>
		</div>
    </div>
	<div>
		<div class="jazz-panel-titlebar jazz-panel-header jazz-formpanel-titlebar">
			<div class="jazz-formpanel-titlebar-inner" style="width:100%; height:100%">
			<span class="jazz-panel-title-font jazz-formpanel-title-font">
			<a id="entname" style="font-size:19px;"></a>
			<a id="regno" style="float:right;font-size:19px;"></a></span>
			<span class="jazz-panel-rtl-left" style=""></span><span class="jazz-panel-rtl-right" style="">
			</span></div>
		</div>
		<table id="tabs" cellspacing="0" cellpadding="0" style="border-collapse: collapse; table-layout: fixed; width: 100%;">
		<colgroup>
		<col align="right" height="40px" width="119px" style="vertical-align: middle;background-color:#DCEEEF;border:1px solid #AFB0B1;border-left: 0px solid #AFB0B1;">
		<col width="255px" style="vertical-align: middle;background-color:white;border:1px solid #AFB0B1;">
		<col align="right" height="40px" width="119px" style="vertical-align: middle;background-color:#DCEEEF;border:1px solid #AFB0B1;">
		<col width="255px" style="vertical-align: middle;background-color:white;border:1px solid #AFB0B1;">
		</colgroup>
		<tbody>
		</tbody></table>
	</div>
	<div style="margin-top: 10px;"><p style="width:100%;height:30px;line-height:30px; text-align:center;vertical-align: middle;font-family: '微软雅黑,宋体';color: white-space: border;font-size: 14px;color: white;background-color: #26363b;position: fixed;bottom: 0px;z-index:100;">版权所有：广东省工商行政管理局  地址：广州市天河区体育西路57号红盾大厦  邮编：510620  技术支持：长城计算机软件与系统有限公司   ICP备案号：粤ICP备05028****号*1   网站备案编码：44010****1622</p></div>
</div>
</body>
</html>