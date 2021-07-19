<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html> 
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<!-- Mobile viewport optimized -->
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<!-- Title -->
		<title>深圳市市场和质量监督管理委员会数据交换平台关系图</title>
		<!-- Stylesheet -->
		<link rel="stylesheet" href="/page/zwt/wp-content/themes/conversation_theme/style.css" type="text/css" />
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
	#allZwt a {
		text-decoration:none;
		color:#fafafa;
	}    
</style>
	</head>
     
	<body>
	<div style="padding:5px;font-size:12px;line-height:1.3; color:#fafafa;background-color:#8AB0D7;position:absolute; left:20px; top:20px;text-align:left; ">
	 左侧：业务系统(数据来源方)<br/>
	 中间：共享服务的基础接口<br/>
	 右侧：服务对象(数据获取方)<br/>
	</div>
	<!--
	<div id="allZwt" style="padding:5px;font-size:12px;line-height:1.3; color:#fafafa;background-color:#8AB0D7;position:absolute; left:20px; top:90px;text-align:left;">
		 <a href="#" onclick="_winOpen('zwtDetail.jsp')">查看全部</a><br/>
		
		 <a href="#" onclick="_winOpen('systablein.jsp')">系统-表-接口</a><br/>
		 <a href="#" onclick="_winOpen('tableinobject.jsp')">表-接口-服务对象</a><br/>
	</div>	
	--> 
	<div id="container">
			<div class="wrap">
				<div class='header'>
				</div>
				<!-- #header -->
				<div id="main">
					
				 <div id="graph" style="width:100%;text-align:center;float:left;overflow:hidden;background: #EBF0F5;"></div>
								
					<script
						src="/page/zwt/js/d3.v3.min.js"></script>
					<script
						src="/page/zwt/js/conversation.min.js"></script>
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
		function _winOpen(jspfile){
			window.open(jspfile,'_blank');
		}
	</script>
<a href="" target="_blank" style="display: none;" id="turnDetail">click</a>

	</body>