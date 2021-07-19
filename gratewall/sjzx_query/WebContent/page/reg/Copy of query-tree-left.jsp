<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>市场主体分类信息</title>

<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">

<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/jbxx/menu-left.css" />
<style type="text/css">


</style>
<script type="text/javascript">
	//year 从初始化的时候，和选择的时候赋值
  		var year=null;
	$(document).ready(function() {
		//$("#enttypeName").html(parent.enttypeName);
		$.ajaxSetup({ cache: false }); //清除缓存
		var url = "";
		if(parent.economicproperty=='3'){//判断是否为外资
			url ="left-wz.json";
  		}else{//内资和个体
  			if(parent.type=='0'){//个体
  				url ="left-gt.json";
  			}else{
  				url ="left.json";
  				ndurl="year-nz.json";
  			}
  		}
  	
  		$.ajaxSettings.async = false;  
		$.getJSON(url,function(data){
			for(var i=0 ;i<data.length;i=i+2){
				var url=data[i].url;
				if((i+1)<data.length){
					if(i>=8){
						$('<tr style="display: none;" height="30px"><th width="50%"><a target="main" href="'+url+parent.priPid+'&sourceflag='+parent.sourceflag+'">'+data[i].content+'</a></th><th>|</th><th width="50%"><a target="main" href="'+data[i+1].url+parent.priPid+'&sourceflag='+parent.sourceflag+'">'+data[i+1].content+'</a></th></tr>').appendTo($('.container'));
					}else{
						$('<tr height="30px"><th width="50%"><a target="main" href="'+url+parent.priPid+'&sourceflag='+parent.sourceflag+'">'+data[i].content+'</a></th><th>|</th><th width="50%"><a target="main" href="'+data[i+1].url+parent.priPid+'&sourceflag='+parent.sourceflag+'">'+data[i+1].content+'</a></th></tr>').appendTo($('.container'));
					}
		     	}else{
		     		if(i>=8){
		     			$('<tr style="display: none;" height="30px"><th width="50%"><a target="main" href="'+url+parent.priPid+'&sourceflag='+parent.sourceflag+'">'+data[i].content+'</a></th></tr>').appendTo($('.container'));
					}else{
						$('<tr height="30px"><th width="50%"><a target="main" href="'+url+parent.priPid+'&sourceflag='+parent.sourceflag+'">'+data[i].content+'</a></th></tr>').appendTo($('.container'));
					}
		     	}
			}
			if(data.length>8){
				$('<tr height="30px"><th align="right" colspan="3" id="cs"><a onclick="javascript:showtable()" href="#">更多>></a></t></tr>').appendTo($('.container'));
			}
		});
	});
	

	function showtable(){
		var rows =$("#container tr").length;//获取行数
		//将隐藏的显示
		$("#cs").html('<a onclick="javascript:hidetable()" href="#"><<隐藏</a>');
		for(var i=0 ;i<rows-1;i++){
			if(i>=4){
				$("#container tr")[i].style.display = "";
			}
		}
	}
	function hidetable(){
		var rows =$("#container tr").length;//获取行数
		//将显示的隐藏
		$("#cs").html('<a onclick="javascript:showtable()" href="#">更多>></a>');
		for(var i=0 ;i<rows-1;i++){
			if(i>=4){
				$("#container tr")[i].style.display = "none";
			}
		}
	}
</script>
</head>

<body style="margin: 0px;padding: 0px;">
	<div style="border-bottom:1px solid #AFB0B1;padding-top:0px;margin:0px;margin-top:0px;">
		<div id="enttypeName" style="border:1px solid #EDF5FD;height:auto;padding:4%;padding-top:6%;background:#F8F9FB;font-weight: bold;font-size:20px;">市场主体信息</div>
		<table class="container" style="margin:0px;" id="container"></table> 
	</div>
	<div style="padding-top:0px;margin:0px;margin-top:0px;">
		<div id="enttypeName" style="border:1px solid #EDF5FD;height:auto;padding:4%;padding-top:6%;background:#F8F9FB;font-weight: bold;font-size:20px;">年报信息</div>
		<table class="container" style="margin:0px;" id="container"></table> 
	</div>
</body>
</html>
				       		