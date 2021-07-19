<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<!-- Mobile viewport optimized -->
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<!-- Title -->
		<title>index</title>
		<!-- Stylesheet -->
		<link rel="stylesheet"
			href="/zwt/wp-content/themes/conversation_theme/style.css"
			type="text/css" />
		<!-- WP Head -->
		<meta name="generator" content="WordPress 3.5.2" />
		<meta name="template" content="Oxygen 0.2.2" />
		<script src="js/html5.js"></script>
		<script type="text/javascript" src="Charts/jquery.min.js"></script>
		<script language="JavaScript" src="Charts/FusionCharts.js"></script>
		<script type="text/javascript" src="js/MLPie3.js"></script>

		<!-- Style settings -->
		<style type="text/css" media="all">
html {
	font-size: 16px;
}
body,div,p{padding:0;}
h1,h2,h3,h4,h5,h6,dl dt,blockquote,blockquote blockquote blockquote,#site-title,#menu-primary li a
	{
	font-family: Abel, sans-serif;
}

a,a:visited,.page-template-front .hfeed-more .hentry .entry-title a:hover,.entry-title a,.entry-title a:visited
	{
	color: #0da4d3;
}

a:hover,.comment-meta a,.comment-meta a:visited,.page-template-front .hentry .entry-title a:hover,.archive .hentry .entry-title a:hover,.search .hentry .entry-title a:hover
	{
	border-color: #0da4d3;
}

.read-more,.read-more:visited,.pagination a:hover,.comment-navigation a:hover,#respond #submit,.button,a.button,#subscribe #subbutton,.wpcf7-submit,#loginform .button-primary
	{
	background-color: #0da4d3;
}

a:hover,a:focus {
	color: #000;
}

.pic_tilte{
	color: white;
	padding-top: 90px;
	text-align: center;
	font-size: 12px;
}

.read-more:hover,#respond #submit :hover,.button:hover,a.button:hover,#subscribe #subbutton
	:hover,.wpcf7-submit:hover,#loginform .button-primary:hover {
	background-color: #111;
}
#screen span { 
        position:absolute; 
        overflow:hidden; 
        border:#FFF solid 1px; 
        background:#FFF; 
    } 
    #screen img{ 
        position:absolute; 
        left:-32px; 
        top:-32px; 
        cursor: pointer; 
    } 
    #caption, #title{ 
        color: #FFF; 
        font-family: georgia, 'times new roman', times, veronica, serif; 
        font-size: 1em; 
        text-align: center; 
    } 
    #caption b { 
        font-size: 2em; 
    } 
</style>
		<script type="text/javascript">
	  function toShow(){
	 	document.getElementById('charts').style.display="block";
		document.getElementById('moreCharts').style.display="none";
		document.getElementById('hideCharts').style.display="block";
	 }
	
	function toHide(){
	 	document.getElementById('charts').style.display="none";
		document.getElementById('moreCharts').style.display="block";
		document.getElementById('hideCharts').style.display="none";
	 }
	
	function toShowChart(){
	  document.getElementById('graph').style.display="none";
	  document.getElementById('chartdiv').style.display="none";
	} 
	
	function toFenbu(){
	  $('.selected').each(function(){
	  	var imgSrc = $(this).css('background-image');
	  	imgSrc = imgSrc.replace('2', '');
	  	$(this).css('background-image', imgSrc);
	  }).removeClass('selected');
	  $('#a1').css("background" , "url(/zwt/images/front/icon_gxfw2.png) center 0px no-repeat");
	  $('#a1').addClass("selected");
	  document.getElementById('div_fenbu').style.display="block";
	  document.getElementById('div_qingyu').style.display="none";
	  document.getElementById('div_hot').style.display="none";
	  document.getElementById('div_tree').style.display="none";
	  toShowChart();
	  $("#chartdiv").slideDown(1000);
	}
	function toHome(){
	  $('.selected').each(function(){
	  	var imgSrc = $(this).css('background-image');
	  	imgSrc = imgSrc.replace('2', '');
	  	$(this).css('background-image', imgSrc);
	  }).removeClass('selected');
	  
	  $('#a0').css("background" , "url(/zwt/images/front/icon_sjjhtx2.png) center 10px no-repeat");
	  $('#a0').addClass("selected");
	  document.getElementById('graph').style.display="block";
	  document.getElementById('chartdiv').style.display="none";
	}
	function toTree(){
	  $('.selected').each(function(){
	  	var imgSrc = $(this).css('background-image');
	  	imgSrc = imgSrc.replace('2', '');
	  	$(this).css('background-image', imgSrc);
	  }).removeClass('selected');
	  $('#a2').css("background" , "url(/zwt/images/front/icon_gxxyll2.png) center -5px no-repeat");
	  $('#a2').addClass("selected");
	  document.getElementById('div_fenbu').style.display="none";
	  document.getElementById('div_qingyu').style.display="none";
	  document.getElementById('div_hot').style.display="none";
	  document.getElementById('div_tree').style.display="block";
	  toShowChart();
	  $("#chartdiv").slideDown(1000);
	}
	function toQingyu(){
	  $('.selected').each(function(){
	  	var imgSrc = $(this).css('background-image');
	  	imgSrc = imgSrc.replace('2', '');
	  	$(this).css('background-image', imgSrc);
	  }).removeClass('selected');
	  $('#a3').css("background" , "url(/zwt/images/front/icon_fwdxrd2.png) center 0px no-repeat");
	  $('#a3').addClass("selected");
	  document.getElementById('div_fenbu').style.display="none";
	  document.getElementById('div_qingyu').style.display="block";
	  document.getElementById('div_hot').style.display="none";
	  document.getElementById('div_tree').style.display="none";
	  toShowChart();
	  $("#chartdiv").slideDown(1000);
	}
	function toRl(){
	  $('.selected').each(function(){
	  	var imgSrc = $(this).css('background-image');
	  	imgSrc = imgSrc.replace('2', '');
	  	$(this).css('background-image', imgSrc);
	  }).removeClass('selected');
	  $('#a4').css("background" , "url(/zwt/images/front/icon_gxfwfx2.png) center -5px no-repeat");
	  $('#a4').addClass("selected");
	  document.getElementById('div_fenbu').style.display="none";
	  document.getElementById('div_qingyu').style.display="none";
	  document.getElementById('div_hot').style.display="block";
	  document.getElementById('div_tree').style.display="none";
	  toShowChart();
	  $("#chartdiv").slideDown(1000);
	}

	function setIframe(){
	  $('.selected').removeClass("selected");
	  $('#a0').css("background" , "url(/zwt/images/front/icon_sjjhtx2.png) center 10px no-repeat");
	  $('#a0').addClass("selected");
	  document.getElementById('frame_fenbu').src="MLPie3.jsp";
	  document.getElementById('frame_qingyu').src="crossfilter.jsp";
	  document.getElementById('frame_hot').src="hatmap.jsp";
	  document.getElementById('frame_tree').src="tree.jsp";
	}

	function menu_show(){
		if($('#navbar').hasClass('menushow')){
			$('#navbar').hide();
			$('#navbar').removeClass('menushow');
			$('#title_menu').text('打开导航栏');
		}else{
			$('#navbar').addClass('menushow');
			$('#navbar').show(500);
			$('#title_menu').text('关闭导航栏');
		}
	}
	function which2show(which){
//		alert(which);
	}

</script>
	</head>

	<body onload="setIframe()" style="overflow-x:hidden;">
	<!--<div align="center" style="position:absolute; left:50px; top:350px;color: #3182BD;font-size: 14px;font-weight: bold;">
	  业务系统<br/>(数据来源方)
	</div>
	<div align="center" style="position:absolute; left:1050px; top:350px;color: #3182BD;font-size: 14px;font-weight: bold;">
	  服务对象<br/>(数据获取方)
	</div>
		--><div id="container">
			<div class="wrap">
				<div class='header'>
					<table cellpadding="0" cellspacing="0" style="width: 100%;">
						<tr>
							<td style="">
								<table style="width:100%;" cellpadding="0" cellspacing="0">
									<tr>
										<td style="height:45px;width:500px;background:url(<%=request.getContextPath() %>/images/head_top.png) left 50% no-repeat;"></td>
										<td style="width:250px;background:url(<%=request.getContextPath() %>/images/head_bg.png) left 50% repeat-x;">&nbsp;</td>
										<td style="width:250px;background:url(<%=request.getContextPath() %>/images/head_top.png) right 50% no-repeat;">
											<div style="color:white;float:right; margin-right:100px;font-size:10pt;cursor:pointer;" id="title_menu" onclick="menu_show();">打开导航栏</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr id="navbar" style="display:none;background:url(<%=request.getContextPath() %>/images/nav_bg.png) repeat-x;">
							<td style=" height: 60px;background:url(<%=request.getContextPath() %>/images/menu.png) top left no-repeat;">&nbsp;</td>
					    </tr>
					</table>
				</div>
				<!-- #header -->
				<div id="main">
					<table style="width:100%;margin-top: 5px;" cellspacing="0" cellpadding="0" >
						<tr>
							<td style="text-align:center; vertical-align: top;bakcground-color:#ebf0f5;">
								<div id="graph" style="width:100%;text-align:center;float:left;overflow:hidden;background: #EBF0F5;"></div>
								<div id="chartdiv" style="width:100%;float: left; display: none;">
					    <div id="div_fenbu">
							<iframe id="frame_fenbu" src="" width="100%" height="650px" frameborder="0">
							</iframe>
						</div>
						<div id="div_qingyu">
							<iframe id="frame_qingyu" src="" width="100%" height="650px" frameborder="0">
							</iframe>
						</div>
						<div id="div_hot">
							<iframe id="frame_hot" src="" width="100%" height="650px" frameborder="0">
							</iframe>
						</div>
						<div id="div_tree">
							<iframe id="frame_tree" src="" width="100%" height="650px" frameborder="0">
							</iframe>
						</div>
					</div>
							</td>
							<td style="vertical-align:top;width:160px;background:url(<%=request.getContextPath() %>/images/front/bg.png) center 0px no-repeat;">
						<div id="charts" style="display: ;width:100%;text-align:right;">
							<div id="a0" onclick="toHome();" style="cursor:pointer;background:url(<%=request.getContextPath() %>/images/front/icon_sjjhtx.png) center 0px no-repeat;width:115px;height:115px;margin:0 auto;">
								<div class="pic_tilte">交换平台关系</div>
							</div>
							<div id="a1" onclick="toFenbu();" style="cursor:pointer;background:url(<%=request.getContextPath() %>/images/front/icon_gxfw.png) center -10px no-repeat;width:115px;height:115px;margin:0 auto;">
								<div style="padding-top:75px;" class="pic_tilte">共享服务分布统计</div>
							</div>
							<div id="a3" onclick="toQingyu();" style="cursor:pointer;background:url(<%=request.getContextPath() %>/images/front/icon_fwdxrd.png) center 0px no-repeat;width:115px;height:115px;margin:0 auto;">
								<div style="padding-top:80px;" class="pic_tilte">共享服务分析</div>
							</div>
							<div id="a4" onclick="toRl();" style="cursor:pointer;background:url(<%=request.getContextPath() %>/images/front/icon_gxfwfx.png) center -5px no-repeat;width:110px;height:105px;margin:0 auto;">
								<div style="padding-top:75px;" class="pic_tilte">服务对象热力</div>
							</div>
							<div id="a2" onclick="toTree();" style="cursor:pointer;background:url(<%=request.getContextPath() %>/images/front/icon_gxxyll.png) center -10px no-repeat;width:115px;height:105px;margin:0 auto;">
								<div style="padding-top:70px;" class="pic_tilte">共享资源浏览</div>
							</div>
						</div>
						<div style="width:160px;background:url(<%=request.getContextPath() %>/images/front/jrxt_bg.png) center 0px no-repeat;height:60px;margin:5px auto;">
							<div style="font-size:15px;line-height:60px;color:white;padding-left:60px;width:160px;background:url(<%=request.getContextPath() %>/images/front/jrxt.png) 10px 50% no-repeat;width:100%;height:60px;">
								<a href="content.jsp" style="color: #fff;text-decoration:none;">进入系统</a>
							</div>
						</div>
							</td>
						</tr>
					</table>
					<script
						src="/zwt/wp-content/themes/conversation_theme/d3.v3.min.js"></script>
					<script
						src="/zwt/wp-content/themes/conversation_theme/conversation.min.js"></script>
				</div>
				<!-- .content-wrap -->
			</div>
			<!-- #main -->
			<!-- #footer -->
		</div>
		<!-- .wrap -->
		<!-- </div> -->
		<!-- #container -->

		<script type='text/javascript'>
		/* <![CDATA[ */
		var slider_settings = {
			"timeout" : "6000"
		};
		/* ]]> */
	</script>


	</body>