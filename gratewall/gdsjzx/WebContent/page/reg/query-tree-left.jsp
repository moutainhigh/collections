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
	<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/menu/lanrenzhijia.css" type="text/css">
	<script>document.documentElement.className = "js";</script>
	<script src="<%=request.getContextPath()%>/static/script/menu/json2.js"></script>
	<script src="<%=request.getContextPath()%>/static/script/menu/jquery.collapse.js"></script>
	<script src="<%=request.getContextPath()%>/static/script/menu/jquery.collapse_storage.js"></script>
	<script src="<%=request.getContextPath()%>/static/script/menu/jquery.collapse_cookie_storage.js"></script>
	<style type="text/css">
	h3{
		height:30px;
		font-family: "微软雅黑,宋体";
		font-size: 14px;
		font-weight: bold;
		
	}
	h3 a{
		margin :0 auto;
		padding:5px;
		height: 30px;
		text-align: center;
	}
	li a{
		font-family: "微软雅黑,宋体";
		font-size: 14px;
		text-align: center;
	}
	
	
	ul{
		margin: 0 auto;
	}
	</style>
	<script type="text/javascript">
		//year 从初始化的时候，和选择的时候赋值
		var year=null;
		var enttype=null;
		var economicproperty=null;
		var ndurl="annualReport/annual-nz.json";
		$(document).ready(function() {
			if(parent.open=="sczt"){
				$('#custom-show-hide-example > h3:eq(0) > a').click();
			}else if(parent.open=="ndbg"){
				year=parent.year;
				//$("#sel").val(year);
				judgeType(parent.enttype,year);
				//	$("#sel option[value='"+year+"']").attr("select","selected");
				$('#custom-show-hide-example > h3:eq(1) > a').click();
			}else if(parent.open=="scjg"){
				$('#custom-show-hide-example > h3:eq(2) > a').click();
			}
			$.ajaxSetup({ cache: false }); //清除缓存
			var url = "";
			if(parent.economicproperty=='3'){//判断是否为外资
				url ="left-wz.json";
			}else{//内资和个体
				if(parent.type=='0'){//个体
					url ="left-gt.json";
				}else{
					url ="left.json";
				}
			}

			$.ajaxSettings.async = true;
			$.getJSON(url,function(data){
				for(var i=0 ;i<data.length;i++){
					var url=data[i].url;
					$('<li class="detial"><a target="main" href="'+url+parent.priPid+'&sourceflag='+parent.sourceflag+'">'+data[i].content+'</a>      </li> ' ).appendTo($('.sczt'));
				}
			});
			//市场监管
			var scjgurl="supervise/left.json";
			$.getJSON(scjgurl,function(data){
				for(var i=0 ;i<data.length;i++){
					var url=data[i].url;
					$('<li class="detial"><a target="main" onclick="openWin(\''+url+parent.priPid+'&regno='+parent.regno+'&sourceflag='+parent.sourceflag+'\')" href="javascript:void(0);">'+data[i].content+'</a></li>').appendTo($('.scjg'));
				}
			});
			//商标
			scjgurl="sb/left.json";
			$.getJSON(scjgurl,function(data){
				for(var i=0 ;i<data.length;i++){
					var url=data[i].url;
					$('<li class="detial"><a target="main" onclick="openWin(\''+url+parent.priPid+'&regno='+parent.regno+'&sourceflag='+parent.sourceflag+'\')" href="javascript:void(0);">'+data[i].content+'</a></li>').appendTo($('.sb'));
				}
			});
			
			//12315
			/* scjgurl="12315/left.json";
			$.getJSON(scjgurl,function(data){
				for(var i=0 ;i<data.length;i++){
					var url=data[i].url;
					$('<li class="detial"><a target="main" onclick="openWin(\''+url+parent.priPid+'&regno='+parent.regno+'&sourceflag='+parent.sourceflag+'\')" href="javascript:void(0);">'+data[i].content+'</a></li>').appendTo($('.12315'));
				}
			}); */
			
			//农资
			scjgurl="nz/left.json";
			$.getJSON(scjgurl,function(data){
				for(var i=0 ;i<data.length;i++){
					var url=data[i].url;
					$('<li class="detial"><a target="main" onclick="openWin(\''+url+parent.priPid+'&regno='+parent.regno+'&sourceflag='+parent.sourceflag+'\')" href="javascript:void(0);">'+data[i].content+'</a></li>').appendTo($('.nz'));
				}
			});
			//广告
			scjgurl="gg/left.json";
			$.getJSON(scjgurl,function(data){
				for(var i=0 ;i<data.length;i++){
					var url=data[i].url;
					$('<li class="detial"><a target="main" onclick="openWin(\''+url+parent.priPid+'&regno='+parent.regno+'&sourceflag='+parent.sourceflag+'\')" href="javascript:void(0);">'+data[i].content+'</a></li>').appendTo($('.gg'));
				}
			});
			//案件
			scjgurl="caseinfo/left.json";
			$.getJSON(scjgurl,function(data){
				for(var i=0 ;i<data.length;i++){
					var url=data[i].url;
					$('<li class="detial"><a target="main" onclick="openWin(\''+url+parent.priPid+'&regno='+parent.regno+'&sourceflag='+parent.sourceflag+'\')" href="javascript:void(0);">'+data[i].content+'</a></li>').appendTo($('.aj'));
				}
			});
		});
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
			refresh();//alert($('#annual0').attr('uri'));
			$('#annual0').click();
		}

		function deleTr(tabTitle){
			$('#'+tabTitle+' li:gt(0)').remove();
		}
		//刷新展示列表
		function refresh(){
			deleTr('ndreport');
			$.ajaxSettings.async = false;
			$.getJSON(ndurl,function(nddata){
				for(var j=0 ;j<nddata.length;j++){
					var url=nddata[j].url;
					$('<li class="detial"><a id="annual'+j+'" target="main" href="javascript:void(0);" uri="\''+url+parent.priPid+'&sourceflag='+parent.sourceflag+'&year='+year+'\'" onclick="openWin(\''+url+parent.priPid+'&sourceflag='+parent.sourceflag+'&year='+year+'\');">'+nddata[j].content+'</a>    </li> ' ).appendTo($('.nb'));
				}
			});
		}

		function openWin(url){
			parent.main.location.href=url; 
		}

		function submitAjax(title) {//获取年份
			//alert($("#changeYear").html());
			if(title=="年度报告"){
				yearFlag++;
				if($("#changeYear").html()!=undefined){
					if(yearFlag%2==1){
						if(cuYear==$("#sel option:first").val()){
							$('#annual0').click();
						}
					}
					return
				}
				$.ajax({
					url : "/gdsjzx/report/year.do",
					type : "POST",
					data : {
						priPid : parent.priPid,
						sourceflag : parent.sourceflag
					},
					success : function(msg) {
						if (msg != null && msg != "") {
							var htmlvar = '<li  height="20px"><h4><div id="changeYear" class="ndtitle title">选择年度:<select id="sel" onchange="showSel()"> </select></div></h4></li>';
							$(htmlvar).prependTo($('#ndreport'));
							for ( var i = 0; i < msg.length; i++) {
								var htmlSel = '<option value='+msg[i].ancheyear+'>' + msg[i].ancheyear + '</option>';
								$(htmlSel).appendTo($('#sel'));
							}
							if(year==null){//年份
								year = msg[0].ancheyear;
							}else{
								$("#sel option[value='"+year+"']").attr("selected","selected");
							}
						} else {
							var htmlvar = '<li id="ndname"><a>该企业暂无年度报告</a></li>';
							if($('#ndname').length==0 || $('#ndname')==undefined){
								$(htmlvar).appendTo($('.nb'));//$(divvar).appendTo($('#divboder'));
							}
						}
						enttype = $(window.parent.frames["main"].document).find('#hiddEnttype').val();
						judgeType(enttype,year);
						//9999--个体   9100，9200-农合//enttype=msg[0].enttype;
						//3--外资   2--私营 --非公司法人    1--内资// economicproperty=msg[0].economicproperty;
					}
				});
			}else if(title=="其他部门行政处罚"){
				parent.main.location.href="/gdsjzx/page/reg/other/xzcfInfo.jsp?type=0&entityNo=32&priPid="+parent.priPid+"&regno="+parent.regno+"&sourceflag="+parent.sourceflag; 
			}else if(title=="其他部门行政许可"){
				parent.main.location.href="/gdsjzx/page/reg/other/xzxkInfo.jsp?type=0&entityNo=33&priPid="+parent.priPid+"&regno="+parent.regno+"&sourceflag="+parent.sourceflag;
			}else if(title=="失信违法被执行人"){
				parent.main.location.href="/gdsjzx/page/reg/other/laolaiInfo.jsp?type=0&entityNo=39&priPid="+parent.priPid+"&regno="+parent.regno+"&sourceflag="+parent.sourceflag;
			}
		}
		function judgeType(enttype,year){//年报判断类型
			if (year != null && year != undefined) {
				if (enttype == "9100" || enttype == "9200") {
					ndurl = "annualReport/annual-nh.json";
				} else {
					if (enttype == "9999") {
						ndurl = "annualReport/annual-gt.json";
					} else {
						ndurl = "annualReport/annual-nz.json";
						/* if(economicproperty=="3"){
						 ndurl="annualReport/annual-fzjg.json";
						 }else{
						 if(economicproperty=="2"){
						 ndurl="annualReport/annual-hf.json";
						 }else{ */
						/* }}*/
					}
				}
				refresh();
				$('#annual0').click();
			}
		}
		var cuYear = new Date().getFullYear()-1;
		var yearFlag = 0;
</script>


</head>

<body  ><!-- #F8F9FB; -->
<input id="hiddenSum" type="hidden"  value="0"/>
<!-- 这里开始 -->
<div id="divboder"  class="col c2 divboder">
  <div id="custom-show-hide-example">
    <h3>市场主体基本信息</h3>
    <div style="width:100%;">
     	<ul class="sczt">
		</ul>
    </div>
    <h3 onclick="submitAjax('年度报告');">年度报告</h3>
    <div style="width:100%">
    		<ul id="ndreport" class="nb" >
			</ul>
	</div>
	<h3>市场监管</h3><!-- onclick="submitAjax('市场监管');" -->
    <div style="width:100%">
   		<ul id="scjg" class="scjg">
		</ul>
	</div>
	<h3 onclick="submitAjax('其他部门行政许可');">其他部门行政许可</h3>
    <div style="width:100%">
	</div>
	<h3 onclick="submitAjax('其他部门行政处罚');">其他部门行政处罚</h3>
    <div style="width:100%">
	</div>
	<h3 >商标</h3><!-- onclick="submitAjax('商标');" -->
    <div style="width:100%">
    	<ul id="sb" class="sb">
		</ul>
	</div>
	<!-- <h3>12315</h3>
    <div style="width:88%">
   		<ul id="12315" class="12315">
		</ul>
	</div> -->
	<h3>农资</h3>
    <div style="width:100%">
   		<ul id="nz" class="nz">
		</ul>
	</div>
	<h3>广告</h3>
    <div style="width:100%">
   		<ul id="gg" class="gg">
		</ul>
	</div>
	<h3>案件</h3>
    <div style="width:100%">
   		<ul id="aj" class="aj">
		</ul>
	</div>
	<h3 onclick="submitAjax('失信违法被执行人');">失信违法被执行人</h3>
    <div style="width:100%">
	</div>
  </div>
  
  <script>
        new jQueryCollapse($("#custom-show-hide-example"), {
          open: function() {
            this.slideDown(150);
          },
          close: function() {
            this.slideUp(150);
          }
        });
      </script>
</div>

</body>
</html>
