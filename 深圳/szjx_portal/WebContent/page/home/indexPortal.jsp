<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.gwssi.optimus.plugin.auth.model.User"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>深圳市市场和质量监督管理委员会</title>
<%
	String contextpath = request.getContextPath();
%>
<%
	User user = (User) request.getSession().getAttribute("user");
	String username = user.getUserName();
%>
<link rel="stylesheet" href="<%=contextpath%>/static/portal/index.css" />
<link rel="stylesheet" href="<%=contextpath%>/static/portal/enter.css" />
<script
	src="<%=contextpath%>/static/script/JAZZ-UI/external/jquery-1.8.3.js"
	type="text/javascript"></script>
<script>
	var rootPath="<%=contextpath%>";
</script>

<!--[if IE 7]>
<style>
body{font-family:"微软雅黑"}
.hotNews{padding-bottom:4px !important;}
.wrap .hot_links a img{margin-top:0px !important;}
img{border:0;}

.submenu_content a{height:20px !important;line-height:20px !important;}

div.m-t-104{margin-top:115px !important;}

div.show2{left:62px;}

.clearfix{clear:both; !important;}
div.content{padding: 12px 12px 10px 4px !important;}

.newsAnous{margin-top:15px !important;}
div.wrap{margin-top:15px !important;}

div.icon_submenu {
	background: url(../../static/portal/images/20.png) no-repeat;
	background-position-y:6px;
	width: 20px;
	height: 20px;
}

div.submenu_content:hover .icon_submenu {
	background: url(../../static/portal/images/21.png) no-repeat;
	background-position-y: 6px;
}


div.submenu_content a {
    color: #fff;
    margin-left: 4px;
    font: 12px/1em Microsoft YaHei;
    width: 165px;
/*     width: 100px; */
    display: inline-block;
    vertical-align: top;
    margin-top: 1px;
}

div.m-t-103{margin-top:115px !important;}

div.enter{width: 630px !important;overflow-x:hidden;}
div.waiter{height:330px !important;}
</style>
<![endif]-->

<style type="text/css">
div.show2{left:64px;}
.fl {
	float: left;
}

img{border:0 !important;}
.fr {
	float: right;
}

.m-t-12 {
	margin-top: 12px;
}

.m-l-19 {
	margin-left: 19px;
}

.ml23 {
	margin-left: 23px;;
}

.w104 {
	width: 104px;
}

.w125 {
	width: 125px;
}

.w136 {
	width: 136px;
}

.w260 {
	width: 260px;
}

.w236 {
	width: 236px;
}

.w245 {
	width: 245px;
}

.w314 {
	width: 314px;
}

.w317 {
	width: 317px;
}

.w321 {
	width: 321px;
}

.p-b-24 {
	padding-bottom: 24px;
}

.p-b-15 {
	padding-bottom: 15px;;
}

.p-b-5 {
	padding-bottom: 5px;
}

.hot_news li a {
	font-family: "宋体";
}

.hot_news li {
	padding-top: 4px;;
}

.submenu {
	height: 70px;
}

.m-t-12 {
	margin-top: 12px;
}

.m-t-13 {
	margin-top: 13px;
}

div.m-t-104{
	margin-top: 104px;
}

.p-b-8 {
	padding-bottom: 8px;
}

.p-b-18 {
	padding-bottom: 18px;
}

.p-l-29 {
	padding-left: 29px;
}

/*通用样式*/
li:hover a {
	color: #fff;
}

li:hover  span {
	color: #fff;
}

/*轮播图*/
.banner {
	border: 1px solid transparent;
	border-left: none;
}

/*banner图右边的内容*/
.carousel {
	width: 368px;
	height: 246px;
}

/*热点新闻*/
div.hotNews{
	width: 599px;
	margin-left: 10px;
	padding-bottom: 20px;
    padding-right: 15px;
    border: 1px solid #C4D9EA;
}

/*系统入口*/
.submenu,.submenu1 {
	height: 260px;
}

.right {
	position: relative;
}
/*代办事项*/
.wait_business {
	background: #0085BD
}

.content .left {
	width: 630px;
}

.content .right {
	width: 360px;
	margin-top: 19px;
}

.w300 {
	width: 300px;
}

.w290 {
	width: 290px;
}
/*常用链接*/
.hot_links li {
	padding: 0;
	width: 80px;
	float: left;
	height: 80px;
}

.hot_links li a {
	width: 100%;
}

.hot_links img {
	display: block;
	margin: -2px 23px;
	padding-top: 4px;
	padding-bottom: 4px;;
}

.hot_links a {
	display: block;
	color: #fff;
	height: 65px;
	/* line-height: 60px; */
	text-align: center;
	font-family: "微软雅黑"
}

.link1 {
	background: #78c446
}

.link1:hover {
	background: #66b92f;
}

.link2 {
	background: #FF9B45
}

.link2:hover {
	background: #ed842a;
}

.link3 {
	background: #E3AA42
}

.link3:hover {
	background: #d89a28;
}

.link4 {
	background: #6fb9e3;
}

.link4:hover {
	background: #44a6de
}

.link5 {
	background: #dc94e9
}

.link5:hover {
	background: #ce6ce0
}

.link6 {
	background: #38E0D3
}

.link6:hover {
	background: #1ed2c3;
}

.link7 {
	background: #EEC217
}

.link7:hover {
	background: #e5b909
}

.link8 {
	background: #FF6666;
}

.link8:hover {
	background: #FF0033;
}

/*学习园地等*/
.southSider .gray_content {
	float: left;
}

.hot_study li {
	margin-top: 3px;;
}

.hot_study li:hover {
	background-color: #b0d0dd;
	color: #fff;
}

.hot_work li {
	margin-top: 3px;;
}

.hot_work li:hover {
	background-color: #b0d0dd;
	color: #fff;
}

.d6 {
	border-bottom: 1px solid #d6d6d6;
}

.hot_reading {
	margin-top: 5px;;
}

.hot_reading li {
	margin-top: 3px;
}

.hot_reading li:hover {
	background-color: #b0d0dd;
	color: #fff;
}
/*通知公告*/
.hot_publish li {
	list-style: none;
	margin-top: 3px;;
}

.hot_publish li:hover {
	background-color: #b0d0dd;
	color: #fff;
}

.hot_publish li span {
	display: block;
}

.hot_publish .publishTitle {
	width: 480px;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}
/*常用表格*/
.hot_table {
	margin-top: 5px;
}

.hot_table li {
	margin-top: 3px;
}

.hot_table li:hover {
	background-color: #b0d0dd;
	color: #fff;
}

div.newsAnous {
	width: 608px;
	margin-top: 6px;
}

/*细节修正*/
.mt15 {
	margin-top: 15px;
}

.w233 {
	width: 233px;
}

.w290 {
	width: 290px;
}

.w310 {
	width: 310px;
}

.w320 {
	width: 320px;
}

.m-t-6 {
	margin-top: 6px;
}

.m-t-7 {
	margin-top: 7px;
}

.m-t-10 {
	margin-top: 10px;
}

.m-t-17 {
	margin-top: 70px;
}

.m-l-0 {
	margin-left: 0;
}

.m-l-10 {
	margin-left: 10px;
}

.m-l-15 {
	margin-left: 15px;
}

.m-l-20 {
	margin-left: 20px;
}

.m-l_-8 {
	margin-left: -8px;
}

.m-l-12 {
	margin-left: 12px;
}
.m-l-13{
	margin-left:13px;
}
.m-l-8 {
	margin-left: 8px;
}

.m-b-12 {
	margin-top: 90px;
}
.m-t-12{
	margin-top: ;12px
}
.p-b-2{
	padding-bottom: 2px;
}
.p-b-6{
	padding-bottom: 6px;
}

.p-b-3{
	padding-bottom: 3px;
}
.wait_business  li:hover span {
	color: #fff;
}

/*OA系统*/
.workSystem {
	width: 357px;
	height: 80px;
	    margin-top: 6px;
    margin-bottom: 6px;
}
/*.workSystem ul li{float: left;height: 80px;padding: 0;}
.workSystem ul li img{width: 100%;}*/


.menu:hover .icoPic {display:block}

.waiter{height: 330px;background: #f9f9f9;width: 355px;overflow-x:hidden; }

.b4{
	color: #b4b4b4;
}

.lagefont span{display:block;
	padding-left: 6px;
}
.abs{
	position: absolute;
	top: 18px;
}

.shiChangfaXian{width: 360px;height: 43px;margin-top: 11px;}

.left,.right{padding-top:6px}

.clearfix{clear:both; !important;}

div.wrap{margin-top: 11px;}

div.content{padding: 12px 12px 15px 4px;}
div.m-t-103{margin-top: 103px;}
div.enter{width: 630px !important;overflow-x:hidden;*width: 630px !important;width:630px\9;}
div.waiter{height:330px !important;*width: 330px; width:330px\9;}
.f12{font-size: 12px;}
</style>
</head>
<body>
	<div class="header">
		<jsp:include page="HeaderPortal.jsp" />
	</div>
	<div class="m-t-12 content ">
		<div class="banner m-l-10">
		<div class="fl">
			<div class="carousel">
				<div class="carousel_content">
					<img class="selected" src="../../static/portal/images/29.png">
					<div class="shade"></div>
					<a href="javascript:void(0)" class="white lagefont">深圳市市场和质量监管委召开两学质量监学一做学习教广...</a>
				</div>
				<div class="carousel_content hide">
					<img src="../../static/portal/images/29.png">
					<div class="shade"></div>
					<a href="javascript:void(0)" class="white lagefont" class="white">深圳市市场和质量监管委召开两学质量监学一做学习教广...</a>
				</div>
				<div class="carousel_content hide">
					<div class="shade"></div>
					<img src="../../static/portal/images/29.png"> <a
						href="javascript:void(0)" class="white lagefont" class="white">深圳市市场和质量监管委召开两学质量监学一做学习教广...</a>
				</div>
				<div class="carousel_switch">
					<div id="_switchleft" class="switch left_content">
						<span class="icon_left  inline_block"></span>
					</div>
					<div id="_switchright" class="switch left_content">
						<span class="icon_right  inline_block"></span>
					</div>
					<div class="clear"></div>
				</div>
				<!-- <div class="icon_carousel"></div> -->
			</div>
			
			<div class="shiChangfaXian">
				<img src="../../static/portal/images/38.png">
			</div>
			</div>
			
			<div class="gray_content fl hotNews">
				<h3 class="fb">新闻热点<a class="fr f12 fb">更多</a></h3>
				<div class="gray_line"></div>
				<ul class="hot_publish ">
					<li><a href="javascript:void(0)"><span
							class="fl publishTitle">深圳市场监督管理局关到公布...</span><span class="fr b4">08-02</span>
					</a></li>
					<li><a href="javascript:void(0)"><span
							class="fl publishTitle">深圳市市场和质量监督管施和</span><span class="fr b4">08-02</span>
					</a></li>
					<li><a href="javascript:void(0)"><span
							class="fl publishTitle">深圳市标准技术情况的公告...</span><span class="fr b4">08-02</span>
					</a></li>
					<li><a href="javascript:void(0)"><span
							class="fl publishTitle">深圳市食品药品监督管理局关于开...</span><span class="fr b4">08-02</span>
					</a></li>
					<li><a href="javascript:void(0)"><span
							class="fl publishTitle">关于举办2016深圳国际BT领袖峰会和...</span><span
							class="fr b4">07-26</span>
					</a>
					</li>
					<li><a href="javascript:void(0)"><span
							class="fl publishTitle">市场和质量监管委关于限期办</span><span class="fr b4">07-28</span>
					</a></li>
					<li><a href="javascript:void(0)"><span
							class="fl publishTitle">市场和质量监管委关于限期办</span><span class="fr b4">07-28</span>
					</a></li>

				</ul>
			</div>
			
			<div class="clear"></div>
		</div>


		<div class="left">
			<div class="enter">
			<iframe id="iframe" name="iframepage" src="enter.jsp" width="630px"
				height="330px" noResize frameborder="no" scrolling="no"></iframe>
			</div>

			<div class="gray_content newsAnous">
				<h3 class="fb">通知公告<a class="fr f12 fb">更多</a></h3>
				<div class="gray_line"></div>
				<ul class="hot_publish p-b-8">
					<li><a href="javascript:void(0)"><span
							class="fl publishTitle">深圳市场监督管理局关到公布...</span><span class="fr b4">08-02</span>
					</a></li>
					<li><a href="javascript:void(0)"><span
							class="fl publishTitle">深圳市市场和质量监督管施和</span><span class="fr b4">08-02</span>
					</a></li>
					<li><a href="javascript:void(0)"><span
							class="fl publishTitle">深圳市标准技术情况的公告...</span><span class="fr b4">08-02</span>
					</a></li>
					<li><a href="javascript:void(0)"><span
							class="fl publishTitle">深圳市食品药品监督管理局关于开...</span><span class="fr b4">08-02</span>
					</a></li>
					<li><a href="javascript:void(0)"><span
							class="fl publishTitle">关于举办2016深圳国际BT领袖峰会和...</span><span
							class="fr b4">07-26</span>
					</a>
					</li>
					<li><a href="javascript:void(0)"><span
							class="fl publishTitle">市场和质量监管委关于限期办</span><span class="fr b4">07-28</span>
					</a></li>

				</ul>
			</div>

			<div class="southSider m-t-6">
				<div class="gray_content m-t-10 w290">
					<h3 class="fb">工作简报<a class="fr f12 fb">更多</a></h3>
					<div class="gray_line"></div>
					<ul class="hot_work p-b-5">
						<li><a href="javascript:void(0)">市场监管要情专报</a>
						</li>
						<li><a href="javascript:void(0)">深圳市打击传销规范直销工作</a>
						</li>
						<li><a href="javascript:void(0)">市场食品准入监管系统</a>
						</li>
						<li><a href="javascript:void(0)">省两建办简报</a>
						</li>
						<li><a href="javascript:void(0)">情况通报</a>
						</li>
						<li><a href="javascript:void(0)">情况通报</a>
						</li>
					</ul>
				</div>
				<div class="gray_content m-t-10 w290 m-l-12">
					<h3 class="fb">学习园地<a class="fr f12 fb">更多</a></h3>
					<div class="gray_line"></div>
					<ul class="hot_study p-b-5">
						<li><a href="javascript:void(0)">学习资料</a>
						</li>
						<li><a href="javascript:void(0)">民主评议政风行风专栏</a>
						</li>
						<li><a href="javascript:void(0)">消费热点专题分析</a>
						</li>
						<li><a href="javascript:void(0)">信息安全</a>
						</li>
						<li><a href="javascript:void(0)">金信工作</a>
						</li>
						<li><a href="javascript:void(0)">民主评议风行风专栏</a>
						</li>

					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!--southSider end-->

		</div>
		<div class="right">
			<div class="waiter">
			<!-- 代办事项 -->
			<iframe id="iframeWaiter" name="iframeWaiter" src="waitPortal.jsp"	width="360" noResize frameborder="no" border="0"	marginwidth="0" marginheight="0" scrolling="auto" 
				allowtransparency="yes" style="overflow: hidden;"></iframe>
			
			</div>
			<!-- <div class="small_interval2"></div> -->
			<div class="wrap">
				 <ul class="hot_links" style="height: 160px;">
					<li class="m-l-0"><a href="fa.htm" class="link1" target="_blank"><img src="../../static/portal/images/23.png" width="32" height="32"/>
							<span>常用链接 </span></a>
					</li>
					<li class="m-l-13"><a href="javascript:void(0)" class="link2">
							<img src="../../static/portal/images/24.png" width="32" height="32"/> <span>通讯录</span> </a>
					</li>
					<li class="m-l-13"><a href="javascript:void(0)" class="link3">
							<img src="../../static/portal/images/25.png" width="32" height="32"/> <span>法律法规</span> </a>
					</li>


					<li class="m-l-13"><a href="javascript:void(0)" class="link4">
							<img src="../../static/portal/images/26.png" width="32" height="32"/> <span>短信 </span></a>
					</li>

					<li class="m-l-0"><a href="javascript:void(0)" class="link5">
							<img src="../../static/portal/images/32.png" width="32" height="32"/><span> 图片库</span> </a>
					</li>

					<li class="m-l-13"><a href="javascript:void(0)" class="link6">
							<img src="../../static/portal/images/33.png"
							onclick="windowsopen('http://email/owa/')" width="32" height="32"/><span> 邮件</span> </a>
					</li>


					<li class="m-l-13"><a href="javascript:void(0)" class="link7">
							<img src="../../static/portal/images/31.png"
							onclick="windowsopen('http://email/owa/')" width="32" height="32"/><span> 新闻投稿 </span></a>
					</li>
					
					<li class="m-l-13"><a href="javascript:void(0)" class="link8">
							<img src="../../static/portal/images/icon_zscd.png"
							onclick="windowsopen('http://email/owa/')" width="32" height="32"/><span> 政务之声 </span></a>
					</li>
				</ul>
				<div class="clear"></div> 
			</div>


			<div class="gray_content m-t-10"
				style="background: #F1F1F1; display: none;">
				<h3 class="fb">阅文区</h3>
				<div class="gray_line d6"></div>
				<ul class="hot_reading">
					<li><a href="javascript:void(0)">通知</a>
					</li>
					<li><a href="javascript:void(0)">讲话</a>
					</li>
					<li><a href="javascript:void(0)">人文</a>
					</li>
					<li><a href="javascript:void(0)">历史</a>
					</li>
				</ul>
			</div>

			<div class="gray_content m-t-103">
				<h3 class="fb">常用表格<a class="fr f12 fb">更多</a></h3>
				<div class="gray_line"></div>
				<ul class="hot_table p-b-3">
					<li><a href="javascript:void(0)">自行采购需求方案申报书模板</a>
					</li>
					<li><a href="javascript:void(0)">合同审签表</a>
					</li>
					<li><a href="javascript:void(0)">附件1 市管干部因私出国</a>
					</li>
					<li><a href="javascript:void(0)">附件2 副处级以上机关因私出国</a>
					</li>
					<li><a href="javascript:void(0)">附件1 市管干部因私出国</a>
					</li>
					<li><a href="javascript:void(0)">附件2 副处级以上机关因私出国</a>
					</li>
				</ul>
			</div>
		</div>
		
		<div class="clear"></div>
	</div>
	<!-- content end -->

	<div class="footer">版权所有：深圳市市场和质量监督管理委员会&nbsp;&nbsp;技术支持：长城计算机软件与系统有限公司</div>

	<script type="text/javascript">
		$(function() {

			var carousels = $('.carousel_content');
			var length = carousels.length;
			var index = 0;

			function showPics(index) {
				carousels.eq(index).fadeIn(200);
				carousels.eq(index).siblings('.carousel_content').fadeOut(200);
			}

			setInterval(function() {
				index++;
				showPics((index) % length);

			}, 4000);

			$('#_switchleft').on('click', function() {
				index--;
				showPics((index + length) % length);
			});
			$('#_switchright').on('click', function() {
				index++;
				showPics((index) % length);
			})
		});
		function windowsopen(url) {
			window.open(url);
		}
		
		
		function getTitleWrap(){
			var that =  $(".lagefont");
			that.each(function(){
				var _this = $(this);
				var html = _this.html();
				var length = _this.html().length;
				if(html>=12){
					//_html.substr(10,length,);
				}
			})
		}

		$(function(){
			getTitleWrap();
		})
		
		
		
		
	</script>
</body>
</html>