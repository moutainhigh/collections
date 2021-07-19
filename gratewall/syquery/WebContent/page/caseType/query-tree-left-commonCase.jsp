<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>案件分类信息</title>

<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">

<script
	src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=request.getContextPath()%>/static/css/jbxx/menu-left.css" />
<style type="text/css">
</style>
<script type="text/javascript">
	//year 从初始化的时候，和选择的时候赋值
  		 var year=null;
  		 var caseType=null;
  		 var economicproperty=null;
  		 var ndurl="annualReport/annual-nz.json";	
	$(document).ready(function() {
		$.ajaxSetup({ cache: false }); //清除缓存
		var url = "";
		if(parent.caseType=='0'){//判断是否简易案件
			$("#enttypeName").text("普通案件"),
			url ="left-commonCase.json";
  		}else if(parent.caseType=='1'){//个体
  			$("#enttypeName").text("简易案件"),
  			url ="left-simpleCase.json";
  		}else{
  			jazz.error("案件类型信息出错！！！"+parent.caseType);
  		}
  	
  	
  		$.ajaxSettings.async = false;  
		$.getJSON(url,function(data){
			for(var i=0 ;i<data.length;i=i+2){
				var url=data[i].url;
				if((i+1)<data.length){
					//	$('<tr height="30px"><th width="50%"><a target="main" href="'+url+parent.priPid+'&sourceflag='+parent.sourceflag+'">'+data[i].content+'</a></th><th>|</th><th width="50%"><a target="main" href="'+data[i+1].url+parent.priPid+'&sourceflag='+parent.sourceflag+'">'+data[i+1].content+'</a></th></tr>').appendTo($('#container'));
					$('<tr height="30px"><th width="50%"><a  href="#" onclick=rightPageSet("'+url+parent.priPid+'&caseType='+parent.caseType+'")>'+data[i].content+'</a></th><th>|</th><th width="50%"><a  href="#" onclick=rightPageSet("'+data[i+1].url+parent.priPid+'&caseType='+parent.caseType+'")>'+data[i+1].content+'</a></th></tr>').appendTo($('#container'));

				}else{
						$('<tr height="30px"><th width="50%"><a  href="#"  onclick=rightPageSet("'+url+parent.priPid+'&caseType='+parent.caseType+'")>'+data[i].content+'</a></th></tr>').appendTo($('#container'));
		     	}
			}
		/* 	if(data.length>8){
				$('<tr height="30px"><th align="right" colspan="3" id="cs"><a onclick="javascript:showtable()" href="#">展示年度报告>></a></t></tr>').appendTo($('#container'));
			} */
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
								$(htmlSel).appendTo($('#sel'));
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
</body>
</html>
