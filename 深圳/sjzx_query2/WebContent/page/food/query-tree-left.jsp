<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible"content="IE=8;IE=9;IE=EDGE"> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<title>食品许可证分类信息</title>
<script
	src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/sczt/base64.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=request.getContextPath()%>/static/css/jbxx/menu-left.css" />
<style type="text/css">
div.title{white-space:nowrap}
</style>
<script type="text/javascript">	
	$(document).ready(function() {
		$.ajaxSetup({ cache: false }); //清除缓存
		var url = "";   
		
		if(parent.lictype=='1'){
			$("#foodName").text("食品经营许可证");
			url='jyxk.json';
		}else if(parent.lictype=='3'){
  			$("#foodName").text("食品生产许可证");
  			url='scxk.json';
  		}else if(parent.lictype=='4'){
  			$("#foodName").text("食品生产许可证");
  			url='scxk-old.json';
  		}else if(parent.lictype=='5'){
  			$("#foodName").text("食品流通许可证");
  			url='splt.json';
  		}else if(parent.lictype=='6'){
  			$("#foodName").text("餐饮服务许可证");
  			url='cyfw.json';
  		}else{
  			jazz.error("许可证信息出错！！！"+parent.lictype);
  		}
		
  		$.ajaxSettings.async = false;  
		$.getJSON(url,function(data){
			for(var i=0 ;i<data.length;i=i+2){
				var url=data[i].url;
				if((i+1)<data.length){
					$('<tr height="30px"><th width="50%"><a  href="#" onclick=rightPageSet("'+url+parent.id+'&lictype='+parent.lictype+'&licno='+parent.licno+'")>'+data[i].content+'</a></th><th>|</th><th width="50%"><a  href="#" onclick=rightPageSet("'+data[i+1].url+parent.id+'&lictype='+parent.lictype+'&licno='+parent.licno+'")>'+data[i+1].content+'</a></th></tr>').appendTo($('#container'));
				}else{
					$('<tr height="30px"><th width="50%"><a  href="#"  onclick=rightPageSet("'+url+parent.id+'&lictype='+parent.lictype+'&licno='+parent.licno+'")>'+data[i].content+'</a></th></tr>').appendTo($('#container'));
		     	}
			}
		});
		
	});
	
	function rightPageSet(url){
		window.parent.rightPageSet(url);
	}

</script>
</head>

<body style="background-color: #f2f9f9;"><!-- #F8F9FB; -->
	<div id="div1" height="100%">
		<div class="divboder">
			<div id="foodName" class="title"></div>
			<table class="container" style="margin:0px;" id="container"></table>
		</div>
	</div>
</body>
</html>
