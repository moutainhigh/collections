<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>年度报表展示</title>
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
border-left: 0px solid #AFB0B1;
}
</style>
<script type="text/javascript">
/* $(function() {
	$("#formpanel").formpanel('option', 'readonly', true);
		//行间间距缩小
		//$('.jazz-textfield-comp').css("height","8px");
  		//获取传递过来的参数，进行初始化请求
  		if(parent.entityNo!=null){
  			var year = getUrlParam("year");
 			queryHistory(parent.entityNo,parent.priPid,parent.sourceflag,year);
  		} 
 	});
	function queryHistory(entityNo,priPid,sourceflag,year){
		$('#formpanel .jazz-panel-content').loading();
		$("#formpanel").formpanel('option', 'dataurl',rootPath+
		'/report/detail.do?flag='+entityNo+'&priPid='+priPid+'&sourceflag='+sourceflag+'&year='+year);
		
		
		
		$("#formpanel").formpanel('reload', "null", function(){
	        $('#formpanel .jazz-panel-content').loading('hide');
	        //选取面板上的字号名称：$("").hiddenfield("getValue"));
	         var ancheyear = $('div[name="ancheyear"]').textfield("getValue");
	        $('#formpanel div:first > div span:first').html("<a style='font-size:19px;'>"+ancheyear+"年年度报告&nbsp&nbsp&nbsp</a><a style='float:right;font-size:19px;'>主体名称："+$('#entname').textfield("getValue")+"&nbsp&nbsp&nbsp</a>");
		});
	}

	 function getUrlParam(name) {
         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
         var r = window.location.search.substr(1).match(reg);  //匹配目标参数
         if (r != null) return unescape(r[2]); return null; //返回参数值
     }
      */
   $(function() {
   var year = getUrlParam("year");
	$.ajax({
		url:rootPath+'/report/reportjbxx.do',
		data:{
			flag : parent.entityNo,
			priPid : parent.priPid,
			year : year,
			type : parent.type
		},
		type:"post",
		dataType : 'json',
		success:function(data){
			//var st = '<tr><td style="text-align: right;">企业(机构)名称:</td><td>佛山市高明区荷城谁见谁爱服饰店</td><td style="text-align: right;">企业(机构)名称:</td><td>佛山市高明区荷城谁见谁爱服饰店</td></tr><tr><td style="text-align: right;">企业(机构)名称:</td><td colspan="3">佛山市高明区荷城谁见谁爱服饰店</td></tr>';
			$('#tabs tbody').append(data.returnString);
			$('#hiddEnttype').val(data.enttype);
			$('#ancheyear').append(data.ancheyear);
			$('#regno').append(data.regno);
		}
	});
});
 
 
 function getUrlParam(name) {
         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
         var r = window.location.search.substr(1).match(reg);  //匹配目标参数
         if (r != null) return unescape(r[2]); return null; //返回参数值
     }
</script>
</head>

<body style="background-color: #F8F9FB;">
<div class="jazz-panel-titlebar jazz-panel-header jazz-formpanel-titlebar">
	<div class="jazz-formpanel-titlebar-inner" style="width:100%; height:100%">
	<span class="jazz-panel-title-font jazz-formpanel-title-font">
	<a id="ancheyear" style="font-size:19px;">年度报告</a>
	<a id="regno" style="float:right;font-size:19px;">注册号:</a></span>
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
<input type="hidden" id="hiddEnttype"></input>
</body>
</html>
