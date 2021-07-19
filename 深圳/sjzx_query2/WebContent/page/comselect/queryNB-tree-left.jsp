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
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=request.getContextPath()%>/static/css/jbxx/menu-left.css" />
	<script src="<%=request.getContextPath()%>/static/js/sczt/base64.js"
	type="text/javascript"></script>
<style type="text/css">
</style>
<script type="text/javascript">
	//year 从初始化的时候，和选择的时候赋值
  		 var year=null;
  		 var enttype=null;
  		 var economicproperty=null;
  		 var ndurl="annualReport/annual-nz.json";	
	$(document).ready(function() {
		$.ajaxSetup({ cache: false }); //清除缓存
		var gridUrl=null;
		if(parent.type=='gt'){
			$("#enttypeName").text("个体年报信息");
			gridUrl="../../NBselect/queryNBDetailList.do?type=0";
			var tohtml = 'RightNBGTInfo.html';
			submitAjax(gridUrl,tohtml);
		//	url = "left-gtInfo.json";
		}else if(parent.type == "qy"){
			$("#enttypeName").text("企业年报信息");
			gridUrl="../../NBselect/queryNBDetailList.do?type=1";
		//	url = "left-qyInfo.json";
		var tohtml = 'RightNBQYInfo.html';
			submitAjax(gridUrl,tohtml);
		}
		else{
  			jazz.error("页面配置生成有误 ！！！"+parent.infowareid);
  		}
		
  	
  	
  		/* $.ajaxSettings.async = false;  
		$.getJSON(url,function(data){
			for(var i=0 ;i<data.length;i=i+2){
				var url=data[i].url;
				if((i+1)<data.length){
					//	$('<tr height="30px"><th width="50%"><a target="main" href="'+url+parent.priPid+'&sourceflag='+parent.sourceflag+'">'+data[i].content+'</a></th><th>|</th><th width="50%"><a target="main" href="'+data[i+1].url+parent.priPid+'&sourceflag='+parent.sourceflag+'">'+data[i+1].content+'</a></th></tr>').appendTo($('#container'));
					$('<tr height="30px"><th width="50%"><a  href="#" onclick=rightPageSet("'+url+parent.id+'&headTitle='+encode(data[i].content)+'&toBackUrl='+data[i].toBackUrl+'")>'+data[i].content+'</a></th><th>|</th><th width="50%"><a  href="#" onclick=rightPageSet("'+data[i+1].url+parent.id+'&headTitle='+encode(data[i+1].content)+'&toBackUrl='+data[i+1].toBackUrl+'")>'+data[i+1].content+'</a></th></tr>').appendTo($('#container'));

				}else{
						$('<tr height="30px"><th width="50%"><a  href="#"  onclick=rightPageSet("'+url+parent.id+'&headTitle='+encode(data[i].content)+'&toBackUrl='+data[i].toBackUrl+'")>'+data[i].content+'</a></th></tr>').appendTo($('#container'));
		     	}
			}
		}); */
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
	function submitAjax(gridUrl,tohtml) {
		var tohtml = tohtml;
		//alert( parent.pripid)
			$.ajax({
					url : gridUrl,
					type : "POST",
					data : {
						pripid : parent.pripid
					},
					dataType : "json",
					success : function(msg) {
						var dataStr = msg.data[0];
					//	console.log(dataStr.data.length);
						if (msg != null && msg != "") {
							for(var i=0;i<=dataStr.data.length;i=i+2){
								if((i+1)<dataStr.data.length){//RightNBInfo.html
									$('#ndreport').append("<tr height='30px'><th width='50%'><a onclick=rightPageSet('"+tohtml+"?ancheid="+dataStr.data[i].ancheid+"&ancheyear="+dataStr.data[i].ancheyear+"&anchedate="+dataStr.data[i].anchedate+"') href='#'>"+dataStr.data[i].ancheyear+"年度报告</a></th><th>|</th><th width='50%'><a onclick=rightPageSet('"+tohtml+"?ancheid="+dataStr.data[i+1].ancheid+"&ancheyear="+dataStr.data[i+1].ancheyear+"&anchedate="+dataStr.data[i+1].anchedate+"') href='#'>"+dataStr.data[i+1].ancheyear+"年度报告</a></th></tr>");
								}else{
								    $('#ndreport').append("<tr height='30px'><th width='50%'><a onclick=rightPageSet('"+tohtml+"?ancheid="+dataStr.data[i].ancheid+"&ancheyear="+dataStr.data[i].ancheyear+"&anchedate="+dataStr.data[i].anchedate+"') href='#'>"+dataStr.data[i].ancheyear+"年度报告</a></th></tr>");
								}
								
							}
							
						}
					}
				});
 
		/* enttype = $(window.parent.frames["main"].document).find('#hiddEnttype').val();
		if (year != null && year != undefined) {
			if (enttype == "9100" || enttype == "9200") {
				ndurl = "annualReport/annual-nh.json";
			} else {
				if (enttype == "9999") {
					ndurl = "annualReport/annual-gt.json";
				} else {
					 if(economicproperty=="3"){
					ndurl="annualReport/annual-fzjg.json";
					}else{
						if(economicproperty=="2"){
						ndurl="annualReport/annual-hf.json";
						}else{
					ndurl = "annualReport/annual-nz.json";
					} 				
					}
				}
			}
			refresh();
		}  */

	}
</script>
</head>

<body style="background-color: #f2f9f9;"><!-- #F8F9FB; -->
	<div id="div1" height="100%">
		<div class="divboder">
			<div id="enttypeName" class="title"></div>
		<!-- 	<table class="container" style="margin:0px;" id="container"></table> -->
			<table class="container" style="margin:0px;border-collapse: collapse;"
				id="ndreport"></table>
		</div>
	</div>
</body>
</html>
