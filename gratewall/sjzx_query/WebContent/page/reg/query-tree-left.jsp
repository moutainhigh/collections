<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible"content="IE=8;IE=9;IE=EDGE"> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<title>市场主体分类信息</title>
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
</style>
<script type="text/javascript">	
	$(document).ready(function() {
		$.ajaxSetup({ cache: false }); //清除缓存
		var url = "";   //商事主体信息
		
		if(parent.economicproperty=='3' || parent.economicproperty=='2'){//判断是否为外资
			$("#enttypeName").text("商事主体信息");
			if(parent.opetype == 'FGS' || parent.opetype == 'GRDZFZ' || parent.opetype == 'HHFZ' || parent.opetype == 'HZSFZ'|| parent.opetype == 'NZYY'|| parent.opetype == 'WGDB' || parent.opetype == 'WGJY' || parent.opetype == 'WZFZ' || parent.opetype == 'WZHHFZ'){ //隶属信息
				if(parent.entstatus == 4){ //注销
					if(parent.opetype == 'FGS' || parent.opetype == 'HHFZ' || parent.opetype == 'GRDZFZ'){
						url = "leftjson/left-lishu-zhuxiao-fuzeren.json";
					}else if(parent.opetype == 'GRDZ'){
						url = "leftjson/left-lishu-zhuxiao-touziren.json";
					}else if(parent.opetype == 'HHQY' || parent.opetype == 'WZHH'){
						url = "leftjson/left-lishu-zhuxiao-hehuoren.json";
					}else if(parent.opetype == 'WGDB'){
						url = "leftjson/left-lishu-zhuxiao-shouxidaibiao.json";
					}else{
						url = "leftjson/left-lishu-zhuxiao.json";
					}
				}
				else if(parent.entstatus == 6){ //迁出
					if(parent.opetype == 'FGS' || parent.opetype == 'HHFZ' || parent.opetype == 'GRDZFZ'){
						url = "leftjson/left-qianchu-lishu-fuzeren.json";
					}else if(parent.opetype == 'GRDZ'){
						url = "leftjson/left-qianchu-lishu-touziren.json";
					}else if(parent.opetype == 'HHQY' || parent.opetype == 'WZHH'){
						url = "leftjson/left-qianchu-lishu-hehuoren.json";
					}else if(parent.opetype == 'WGDB'){
						url = "leftjson/left-qianchu-lishu-shouxidaibiao.json";
					}else{
						url = "leftjson/left-qianchu-lishu.json";
					}
				}
				else{
					if(parent.opetype == 'FGS' || parent.opetype == 'HHFZ' || parent.opetype == 'GRDZFZ'){
						url = "leftjson/left-lishu-fuzeren.json";
					}else if(parent.opetype == 'GRDZ'){
						url = "leftjson/left-lishu-touziren.json";
					}else if(parent.opetype == 'HHQY' || parent.opetype == 'WZHH'){
						url = "leftjson/left-lishu-hehuoren.json";
					}else if(parent.opetype == 'WGDB'){
						url = "leftjson/left-lishu-shouxidaibiao.json";
					}else{
						url = "leftjson/left-lishu.json";
					}
				}
			}
			else if(parent.opetype == 'GS' || parent.opetype == 'NZFR' || parent.opetype == 'HHQY' || parent.opetype == 'GRDZ' || parent.opetype == 'WZGS' || parent.opetype == 'WZHH' || parent.opetype == 'HZS' || parent.opetype == 'SLYB'){//出资信息，出资计划
				if(parent.entstatus == 4){ //注销
					if(parent.opetype == 'FGS' || parent.opetype == 'HHFZ' || parent.opetype == 'GRDZFZ'){
						url = "leftjson/left-chuzi-zhuxiao-fuzeren.json";
					}else if(parent.opetype == 'GRDZ'){
						url = "leftjson/left-chuzi-zhuxiao-touziren.json";
					}else if(parent.opetype == 'HHQY' || parent.opetype == 'WZHH'){
						url = "leftjson/left-chuzi-zhuxiao-hehuoren.json";
					}else if(parent.opetype == 'WGDB'){
						url = "leftjson/left-chuzi-zhuxiao-shouxidaibiao.json";
					}else{
						url = "leftjson/left-chuzi-zhuxiao.json";
					}
				}
				else if(parent.entstatus == 6){ //迁出
					if(parent.opetype == 'FGS' || parent.opetype == 'HHFZ' || parent.opetype == 'GRDZFZ'){
						url = "leftjson/left-chuzi-qianchu-fuzeren.json";
					}else if(parent.opetype == 'GRDZ'){
						url = "leftjson/left-chuzi-qianchu-touziren.json";
					}else if(parent.opetype == 'HHQY' || parent.opetype == 'WZHH'){
						url = "leftjson/left-chuzi-qianchu-hehuoren.json";
					}else if(parent.opetype == 'WGDB'){
						url = "leftjson/left-chuzi-qianchu-shouxidaibiao.json";
					}else{
						url = "leftjson/left-chuzi-qianchu.json";
					}
				}
				else{
					if(parent.opetype == 'FGS' || parent.opetype == 'HHFZ' || parent.opetype == 'GRDZFZ'){
						url = "leftjson/left-chuzi-fuzeren.json";
					}else if(parent.opetype == 'GRDZ'){
						url = "leftjson/left-chuzi-touziren.json";
					}else if(parent.opetype == 'HHQY' || parent.opetype == 'WZHH'){
						url = "leftjson/left-chuzi-hehuoren.json";
					}else if(parent.opetype == 'WGDB'){
						url = "leftjson/left-chuzi-shouxidaibiao.json";
					}else{
						url = "leftjson/left-chuzi.json";
					}
				}
			}
			else{
				if(parent.entstatus == 4){
					if(parent.opetype == 'FGS' || parent.opetype == 'HHFZ' || parent.opetype == 'GRDZFZ'){
						url = "leftjson/left-zhuxiao-fuzeren.json";
					}else if(parent.opetype == 'GRDZ'){
						url = "leftjson/left-zhuxiao-touziren.json";
					}else if(parent.opetype == 'HHQY' || parent.opetype == 'WZHH'){
						url = "leftjson/left-zhuxiao-hehuoren.json";
					}else if(parent.opetype == 'WGDB'){
						url = "leftjson/left-zhuxiao-shouxidaibiao.json";
					}else{
						url = "leftjson/left-zhuxiao.json";
					}
				}else if(parent.entstatus == 6){ //迁出
					if(parent.opetype == 'FGS' || parent.opetype == 'HHFZ' || parent.opetype == 'GRDZFZ'){
						url = "leftjson/left-qianchu-fuzeren.json";
					}else if(parent.opetype == 'GRDZ'){
						url = "leftjson/left-qianchu-touziren.json";
					}else if(parent.opetype == 'HHQY' || parent.opetype == 'WZHH'){
						url = "leftjson/left-qianchu-hehuoren.json";
					}else if(parent.opetype == 'WGDB'){
						url = "leftjson/left-qianchu-shouxidaibiao.json";
					}else{
						url = "left-qianchu.json";
					}
				}else{
					if(parent.opetype == 'FGS' || parent.opetype == 'HHFZ' || parent.opetype == 'GRDZFZ'){
						url = "leftjson/left-fuzeren.json";
					}else if(parent.opetype == 'GRDZ'){
						url = "leftjson/left-touziren.json";
					}else if(parent.opetype == 'HHQY' || parent.opetype == 'WZHH'){
						url = "leftjson/left-hehuoren.json";
					}else if(parent.opetype == 'WGDB'){
						url = "leftjson/left-shouxidaibiao.json";
					}else{
						url = "leftjson/left.json";
					}
				}
			}
		
  		}else if(parent.economicproperty=='1'){//个体
  			$("#enttypeName").text("商事主体信息");
  			$("#caseName").text("案件信息");
  			if(parent.entstatus == 4){
  				url ="left-gt-zhuxiao.json";
  			}else{
  				url ="left-gt.json";
  			}

  		}else if(parent.economicproperty=='4'){
  			$("#enttypeName").text("集团信息");
  			if(parent.entstatus == 4){
  				url ="left-jituan-zhuxiao.json";
  			}else{
  				url ="left-jituan.json";//集团
  			}
  		}else{
  			jazz.error("企业类型信息出错！！！"+parent.economicproperty);
  		}
		
  		$.ajaxSettings.async = false;  
		$.getJSON(url,function(data){
			for(var i=0 ;i<data.length;i=i+2){
				var url=data[i].url;
				if((i+1)<data.length){
					//	$('<tr height="30px"><th width="50%"><a target="main" href="'+url+parent.priPid+'&sourceflag='+parent.sourceflag+'">'+data[i].content+'</a></th><th>|</th><th width="50%"><a target="main" href="'+data[i+1].url+parent.priPid+'&sourceflag='+parent.sourceflag+'">'+data[i+1].content+'</a></th></tr>').appendTo($('#container'));
					$('<tr height="30px"><th width="50%"><a  href="#" onclick=rightPageSet("'+url+parent.priPid+'&economicproperty='+parent.economicproperty+'")>'+data[i].content+'</a></th><th>|</th><th width="50%"><a  href="#" onclick=rightPageSet("'+data[i+1].url+parent.priPid+'&economicproperty='+parent.economicproperty+'")>'+data[i+1].content+'</a></th></tr>').appendTo($('#container'));
				}else{
					$('<tr height="30px"><th width="50%"><a  href="#"  onclick=rightPageSet("'+url+parent.priPid+'&economicproperty='+parent.economicproperty+'")>'+data[i].content+'</a></th></tr>').appendTo($('#container'));
		     	}
			}
		});
		
		var jgurl = ""; //监管信息
		$("#jgName").text("监管信息");
		if(parent.economicproperty=='1'){
			jgurl = "jianguan-gt.json";
		}
		else{
			jgurl = "jianguan-qy.json";
		}

		$.getJSON(jgurl,function(data){
			for(var i=0 ;i<data.length;i=i+2){
				var url=data[i].url;
				if((i+1)<data.length){
					//	$('<tr height="30px"><th width="50%"><a target="main" href="'+url+parent.priPid+'&sourceflag='+parent.sourceflag+'">'+data[i].content+'</a></th><th>|</th><th width="50%"><a target="main" href="'+data[i+1].url+parent.priPid+'&sourceflag='+parent.sourceflag+'">'+data[i+1].content+'</a></th></tr>').appendTo($('#container'));
					$('<tr height="30px"><th width="50%"><a  href="#" onclick=rightPageSet("'+url+encode(parent.entid)+'&economicproperty='+parent.economicproperty+'")>'+data[i].content+'</a></th><th>|</th><th width="50%"><a  href="#" onclick=rightPageSet("'+data[i+1].url+encode(parent.entid)+'&economicproperty='+parent.economicproperty+'")>'+data[i+1].content+'</a></th></tr>').appendTo($('#container2'));
				}else{
					$('<tr height="30px"><th width="50%"><a  href="#"  onclick=rightPageSet("'+url+encode(parent.entid)+'&economicproperty='+parent.economicproperty+'")>'+data[i].content+'</a></th></tr>').appendTo($('#container2'));
		     	}
			}
		});
		
		var caseurl = ""; //案件信息
		$("#caseName").text("案件消保信息");
		caseurl = "case-cpr.json";
		$.getJSON(caseurl,function(data){
			for(var i=0 ;i<data.length;i=i+2){
				var url=data[i].url;
				if((i+1)<data.length){
					//	$('<tr height="30px"><th width="50%"><a target="main" href="'+url+parent.priPid+'&sourceflag='+parent.sourceflag+'">'+data[i].content+'</a></th><th>|</th><th width="50%"><a target="main" href="'+data[i+1].url+parent.priPid+'&sourceflag='+parent.sourceflag+'">'+data[i+1].content+'</a></th></tr>').appendTo($('#container'));
					$('<tr height="30px"><th width="50%"><a  href="#" onclick=rightPageSet("'+url+encode(parent.entid)+'&entname='+encode(parent.entname)+'&regno='+encode(parent.regno)+'")>'+data[i].content+'</a></th><th>|</th><th width="50%"><a  href="#" onclick=rightPageSet("'+data[i+1].url+encode(parent.entid)+'&entname='+encode(parent.entname)+'&regno='+encode(parent.regno)+'")>'+data[i+1].content+'</a></th></tr>').appendTo($('#container3'));
				}else{
					$('<tr height="30px"><th width="50%"><a  href="#" onclick=rightPageSet("'+url+encode(parent.entid)+'&entname='+encode(parent.entname)+'&regno='+encode(parent.regno)+'")>'+data[i].content+'</a></th></tr>').appendTo($('#container3'));
		     	}
			}
		});
		
		var foodurl = ""; //食品信息
		$("#foodName").text("食品信息");
		foodurl = "food.json";
		$.getJSON(foodurl,function(data){
			for(var i=0 ;i<data.length;i=i+2){
				var url=data[i].url;
				if((i+1)<data.length){
					//	$('<tr height="30px"><th width="50%"><a target="main" href="'+url+parent.priPid+'&sourceflag='+parent.sourceflag+'">'+data[i].content+'</a></th><th>|</th><th width="50%"><a target="main" href="'+data[i+1].url+parent.priPid+'&sourceflag='+parent.sourceflag+'">'+data[i+1].content+'</a></th></tr>').appendTo($('#container'));
					$('<tr height="30px"><th width="50%"><a  href="#" onclick=rightPageSet("'+url+encode(parent.entid)+'&entname='+encode(parent.entname)+'&regno='+encode(parent.regno)+'")>'+data[i].content+'</a></th><th>|</th><th width="50%"><a  href="#" onclick=rightPageSet("'+data[i+1].url+encode(parent.entid)+'&entname='+encode(parent.entname)+'&regno='+encode(parent.regno)+'")>'+data[i+1].content+'</a></th></tr>').appendTo($('#container4'));
				}else{
					$('<tr height="30px"><th width="50%"><a  href="#" onclick=rightPageSet("'+url+encode(parent.entid)+'&entname='+encode(parent.entname)+'&regno='+encode(parent.regno)+'")>'+data[i].content+'</a></th></tr>').appendTo($('#container4'));
		     	}
			}
		});
		
	});
	
	function showtable(){
				 var rows =$("#ndreport tr").length;//获取行数
					 $("#ndreport").show();
					 $("#cs").html('<a onclick="javascript:hidetable()" href="#"><<隐藏</a>');
					// alert($('#sel').val());
				 if($('#sel').val()==undefined){
				 	//alert("sel"+$('#sel').val());
				 	//alert(rows);
				 	submitAjax();
				}
			}
	
	function hidetable(){
		var rows =$("#ndreport tr").length;//获取行数
		$("#ndreport").hide();
		$("#cs").html('<a onclick="javascript:showtable()" href="#">展示年度报告>></a>');
	}
	
	
	//截取年度数
   function getUrlParam(name) {
         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
         var r = window.location.search.substr(1).match(reg);  //匹配目标参数
         if (r != null) return unescape(r[2]); return null; //返回参数值
     }
		    		//选择年份
   function showSel(){
			var annual = $("#sel").find("option:selected").text();
			year=annual;
			refresh();
		}

	function deleTr(){
		$('#ndreport tr:gt(0)').remove();
	}	
	//刷新展示列表
	function refresh(){
				deleTr();
				$.getJSON(ndurl,function(nddata){
					for(var j=0 ;j<nddata.length;j=j+2){
						var url=nddata[j].url;
						if((j+1)<nddata.length){
								$('<tr  height="30px"><th width="50%"><a id="annual'+j+'"  href="#" onclick=rightPageSet("'+url+parent.priPid+'&sourceflag='+parent.sourceflag+'&year='+year+'")>'+nddata[j].content+'</a></th><th>|</th><th width="50%"><a  href="#" onclick=rightPageSet("'+nddata[j+1].url+parent.priPid+'&sourceflag='+parent.sourceflag+'&year='+year+'")>'+nddata[j+1].content+'</a></th></tr>').appendTo($('#ndreport'));
				     	}else{
								$('<tr  height="30px"><th width="50%"><a  href="#" onclick=rightPageSet("'+url+parent.priPid+'&sourceflag='+parent.sourceflag+'&year='+year+'")>'+nddata[j].content+'</a></th></tr>').appendTo($('#ndreport'));
				     				}
								}
							});
		}
		

	function rightPageSet(url){
		window.parent.rightPageSet(url);
	}
	//获取年份
	function submitAjax() {
		$.ajax({
					url : "/gdsjzx/report/year.do",
					type : "POST",
					data : {
						priPid : parent.priPid,
						sourceflag : parent.sourceflag
					},
					success : function(msg) {
						if (msg != null && msg != "") {
							var htmlvar = '<tr  height="30px"><td width="100%" colspan="3"><div id="enttypeName" class="ndtitle title">年度报告<select id="sel" onchange="showSel()"> </select></div></td></tr>';
							$(htmlvar).appendTo($('#ndreport'));
							for ( var i = 0; i < msg.length; i++) {
								var htmlSel = '<option value='+msg[i].ancheyear+'>'
										+ msg[i].ancheyear + '</option>';
								$(htmlSel).appendTo($(
										'#sel'));
							}
							//年份
							year = msg[0].ancheyear;
						} else {
							var htmlvar = '<tr  height="30px"><td width="100%" colspan="3"><div  id="enttypeName" class="ndtitle title">该企业暂无年度报告</div></td></tr>';
							if($('#ndreport').html().trim()==""){
								$(htmlvar).appendTo($('#ndreport'));
							}
						}
						//9999--个体   9100，9200-农合
						//enttype=msg[0].enttype;
						//3--外资   2--私营 --非公司法人    1--内资
						// economicproperty=msg[0].economicproperty;
					}
				});

		enttype = $(window.parent.frames["main"].document).find('#hiddEnttype').val();
		if (year != null && year != undefined) {
			if (enttype == "9100" || enttype == "9200") {
				ndurl = "annualReport/annual-nh.json";
			} else {
				if (enttype == "9999") {
					ndurl = "annualReport/annual-gt.json";
				} else {
					/* if(economicproperty=="3"){
					ndurl="annualReport/annual-fzjg.json";
					}else{
						if(economicproperty=="2"){
						ndurl="annualReport/annual-hf.json";
						}else{ */
					ndurl = "annualReport/annual-nz.json";
					/* } 				
					}*/
				}
			}
			refresh();
		}

	}
</script>
</head>

<body style="background-color: #f2f9f9;"><!-- #F8F9FB; -->
	<div id="div1" height="100%">
		<div class="divboder">
			<div id="enttypeName" class="title"></div>
			<table class="container" style="margin:0px;" id="container"></table>
			<table style="display:none" class="container" style="margin:0px;border-collapse: collapse;"
				id="ndreport"></table>
		</div>
	</div>
	<div id="div2" height="100%">
		<div class="divboder">
			<div id="jgName" class="title"></div>
			<table class="container" style="margin:0px;" id="container2"></table>
			<table style="display:none" class="container" style="margin:0px;border-collapse: collapse;"
				id="ndreport2"></table>
		</div>
	</div>
	<div id="div3" height="100%">
		<div class="divboder">
			<div id="caseName" class="title"></div>
			<table class="container" style="margin:0px;" id="container3"></table>
			<table style="display:none" class="container" style="margin:0px;border-collapse: collapse;"
				id="ndreport2"></table>
		</div>
	</div>
	<div id="div4" height="100%">
		<div class="divboder">
			<div id="foodName" class="title"></div>
			<table class="container" style="margin:0px;" id="container4"></table>
			<table style="display:none" class="container" style="margin:0px;border-collapse: collapse;"
				id="ndreport2"></table>
		</div>
	</div>
</body>
</html>
