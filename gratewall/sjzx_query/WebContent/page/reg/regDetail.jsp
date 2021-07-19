<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible"content="IE=8;IE=9;IE=EDGE"> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商事主体信息</title>
<%
	String rootPath = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=rootPath%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/sczt/base64.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/lib/jquery.PrintArea.js" type="text/javascript"></script>

<script type="text/javascript">
		var entityNo=null;//编号
		var priPid=null;//主键
		var sourceflag=null;//平台 无
		var enttype=null;//类型
		var type=null;//无
		var economicproperty =null;//经济性质 无
		var entname =null;//企业名
		var year=null;//无
		var opetype=null; //隶属企业标志位
		var entstatus =null; //企业状态
		var entid = null;  //档案影像
		var regno = null; // 注册号/统一社会信用代码
	 $(function(){
		 priPid=getUrlParam("priPid");//主键
  		if(priPid==null){
  			jazz.error("从主页中重新检索");
  		}
		/* entityNO代表的值
  		 隶属信息:1
  		出资信息:9
  		人员信息:10
  		股权信息:3
  		变更信息:5
  		迁移信息:2
  		证照信息:8
  		注吊销信息:7
  		清算信息:6 */
  		entityNo=getUrlParam("flag");
  		priPid=getUrlParam("priPid");
  		sourceflag=getUrlParam("sourceflag");
  		enttype=getUrlParam("enttype"); //企业类型
  		type=getUrlParam("type");
  		economicproperty=getUrlParam("economicproperty");
  		entname = getUrlParam("entname");
  		year = getUrlParam("year");
  		opetype = getUrlParam("opetype");
  		entstatus = getUrlParam("entstatus");
  		entid = getUrlParam("entid");
  		regno = getUrlParam("regno");
  		if(economicproperty=="2"){//内资企业
  			$("#main").panel("option","frameurl","<%=request.getContextPath()%>/page/reg/query-panel-right.jsp");
  			
  		}else if(economicproperty=="3"){//外资企业
  	

  			$("#main").panel("option","frameurl","<%=request.getContextPath()%>/page/reg/query-panel-right-waizi.jsp");
  		}else if(economicproperty=="1"){ //个体

  			$("#main").panel("option","frameurl","<%=request.getContextPath()%>/page/reg/query-panel-right-getti.jsp");
  		}else if(economicproperty=="4"){//集团
  			$("#main").panel("option","frameurl","<%=request.getContextPath()%>/page/reg/query-panel-right-jituan.jsp");
  		}
  		$("#left").panel("option","frameurl","<%=request.getContextPath()%>/page/reg/query-tree-left.jsp");
  	
 	}); 
 	
 	 function getUrlParam(name) {
         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
         var r = window.location.search.substr(1).match(reg);  //匹配目标参数
         if (r != null) return decode(unescape(r[2])); return null; //返回参数值
     }

 	function rightPageSet(url){
 		if(url.substring(0,4) == "http"){
 			entid = getUrlParam("entid");
 			var newUrl = "http://aaicweb03/EIMIS/frmShowEntImage.aspx?entid=" + entid;
 			window.open(newUrl);
 		}
 		else{
         var url2="";
         url2="<%=request.getContextPath()%>/page/reg/"+url;
 		 $("#main").panel("option","frameurl",url2); 
 		}
	}
   //  $('#main')
</script>
<style type="text/css">
#wrap {
	width: 760px;
	margin: 0 auto;
	border: 1px solid #333;
	background-color: #ccc;
}

.jazz-row-border {
	background-color: #efefef;
	border: 0 none;
	height: 0px;
	overflow: hidden;
	position: relative;
	width: 100%;
}

.jazz-column-btn {
	cursor: pointer;
	height: 42px;
	margin-top: -21px;
	opacity: 0.7;
	position: absolute;
	top: 50%;
	width: 0px;
}

.footer_tishi {
	height: 40px;
	background-color: black;
	position: relative;
	height: 100%;
	line-height: 30px;
	/* padding-left:15%; */
	font-size: 12px;
	color: #eff1ef;
	font-family: simsun;
}

.yincangdiv {
	overflow-x: hidden;
	overflow-y: hidden;
}
</style>
</head>
<body>
<div id="allcontent" vtype="panel" width="100%" height="1500"
	showborder="false" layout="border"
	layoutconfig="{border: false, north_show_border: true, south_show_border: false ,north_drag:false,west_drag:false}">
	<div class="yincangdiv" height="68" region="north">
		<div name="n1" vtype="panel" title="" titledisplay="false"
			height="100%" width="100%"
			frameurl="<%=request.getContextPath()%>/page/reg/regDetail_head.jsp"></div>
	</div>

<%-- 	<div region="west" width="0">
		<div name="w1" vtype="panel" title="" titledisplay="false"
			height="100%" width="100%"
			frameurl="<%=request.getContextPath()%>/page/reg/regDetail_back.jsp"></div>

	</div> --%>
	<div region="center" widht="100%" height="100%" style="margin-top: 10px;">
		<div id="column_id" width="100%" vtype="panel" name="panel"
			layout="column" height="100%" layoutconfig="{width: ['50%','200','4','790','*']}">
			<div width="100%" height="100%">
			</div>
			<div width="100%" height="100%">
				<div id="left" name="left" vtype="panel" title=""
					titledisplay="false" height="100%" width="100%" frameurl=""></div>
			</div>

			<div width="100%" height="100%">
				<div id="center" name="center" vtype="panel" title=""
					titledisplay="false" height="100%" width="100%"
					frameurl="<%=request.getContextPath()%>/page/reg/query-tree-center.jsp"></div>
			</div>

			<div width="100%" height="100%">
				<div id="main" name="main" vtype="panel" title=""
					titledisplay="false" height="100%" width="100%" frameurl=""></div>
				
			</div>
			
			<div width="100%" height="100%">
			
			</div>
		</div>

	</div>
<%-- 	<div region="east" width="0" style="background-color: #fff;">
		<div name="e1" vtype="panel" title="" titledisplay="false"
			height="100%" width="100%"
			frameurl="<%=request.getContextPath()%>/page/reg/regDetail_back.jsp"></div>

	</div> --%>
	<!-- <div region="south" height="31">
		<div name="s1" vtype="panel" title="" titledisplay="false"
			height="100%" width="100%"
			frameurl="<%=request.getContextPath()%>/page/reg/regDetail_footer.jsp"></div>

	</div> -->
</div>
<style>
.printPrew {
	width: 50%;
	min-height: 450px;
	position: absolute;
	z-index: 9999;
	top: 10%;
	left: 26%;
	background: #000;
}

.show {
	display: block;
}

.hide {
	display: none;
}

.printPrew table {
	width: 100%;
}

.printPrew table td {
	width: 50px;
	height: 16px;
}

.printBtns {
	width: 50px;
	text-align: center;
	height: 38px;
	line-height: 38px;
	display: inline-block;
	text-decoration: none;
	margin-top: 15px;
	background: #49BEEF;
	color: #fff;
	margin-bottom: 10px;
}
</style>

	<div id="printer" class="printPrew hide">
		<div style="background: #e5e5e5">
			<div style="width: 140px;margin: 0 auto">
				<a class="printBtns" href="javascript:;" onclick="pintPrev()">打印</a>
				<a  class="printBtns" onclick="closePrew()" href="javascript:;">取消</a>
			</div>
		</div>
		<div id="pintAreas">
			<table id="tableDes"></table>
		</div>
	</div>
	<script>
		function pintPrev() {
			$("#pintAreas").printArea();
		}
		
		function closePrew(){
			$("#printer").removeClass("show").addClass("hide");
		}
		
		$(window).load(function(){
			var scH = screen.height;
			if(scH<=768){
				$("#tableDes").css("border-spacing","1px");
				$(".printPrew").css("width","50%");
			}else{
				$("#tableDes").css("border-spacing","2px");
				$(".printPrew").css("width","51%");
			}
			
		});
	</script>
</body>
</html>
