<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="shortcut icon" href="statics/images/favicon.ico" type="image/x-icon"/> 
<title></title>
<link rel="stylesheet" type="text/css" href="statics/css/nav.css">
<link rel="stylesheet" type="text/css" href="statics/css/detail.css">
<script type="text/javascript" src="statics/js/jquery-V30.min.js"></script>
<script type="text/javascript" src="statics/js/focus.js"></script>
<!-- <script type="text/javascript"	src="http://www.sznews.com/2013/js/article_common2.js"></script> -->
<!--script type="text/javascript" src="http://www.sznews.com/js/sznewsjs2013.js"></script>-->
<!--<script type="text/javascript" src="http://www.sznews.com/2016gb/js/article_search.js"></script>-->
<!--<script type="text/javascript" src="http://www.sznews.com/2016gb/js/onebtnget.js"></script>-->
<!--语音朗读-->
<!--<script type="text/javascript" src=" http://www.sznews.com/2016mi/js/pcvoice.js"></script>-->
<script src="statics/js/tzcommon.js" type="text/javascript"></script>

<style type="text/css">
body{
	color: rgb(68, 68, 68);
    background-color: rgb(241, 241, 241);
    font: 14px/1.5em 微软雅黑;
}

/*语音朗读*/
.yyld-btn {
	height: 35px;
	line-height: 35px;
	margin-bottom: 20px;
	overflow: hidden;
	zoom: 1;
}

.yyld-btn b {
	font-size: 20px;
	font-weight: bold;
	display: block;
	float: left;
}

.yyld-btn img {
	width: 50px;
	height: 35px;
	float: left;
	margin-right: 10px;
	cursor: pointer;
}
</style>
<style type="text/css">
.header {
	position: relative;
}

.content {
	padding-top: 0;
}
/*点赞css*/
.like-btn {
	float: inline-block;
	background: url(http://www.sznews.com/2016gb/zan-l1.png) no-repeat 0px 1px;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
	height: 38px;
	line-height: 38px;
	padding-left: 35px;
	width: 50px;
	font-size: 26px;
	margin-left: 10px;
	cursor: pointer;
	color: #F03;
}

span.like-btn.like-btn-page-bottom {
	height: 24px;
	line-height: 32px;
	width: auto;
	font-size: 18px;
	margin-right: 10px;
	padding-left: 25px;
	float: right;
	vertical-align: text-bottom;
	background: url(http://www.sznews.com/2016gb/zan-l2.png) no-repeat 0px 4px;
}

.remark-write {
	margin-left: 1px;
}

.count-display {
	display: none;
}
</style>

<!-- 延伸阅读 -->
<style type="text/css">
<!--
.box {
	border-top: none;
	padding: 10px;
}

#hl-RelationDiv {
	margin: 0;
	list-style-type: none;
	line-height: 24px;
	border: none;
}

#hl-RelationDiv li {
	border-bottom: #F2F2F2 solid 1px;
	margin-bottom: 20px;
	padding-bottom: 15px;
}

#hl-RelationDiv li a {
	line-height: 50px;
	font-size: 20px;
	color: #333;
	font-family: "微软雅黑";
}

#hl-RelationDiv li span {
	font-size: 12px;
	margin-left: 30px;
}

#hl-RelationDiv li p {
	line-height: 28px;
	font-size: 14px;
	font-family: "微软雅黑";
}
-->
</style>

<!--分页样式-->
<style type="text/css">
.pages {
	float: right;
	font-size: 13px;
	padding: 20px 0 20px;
	text-align: center;
	margin: 20px 0;
}

.pages li {
	float: left;
	margin-right: 6px;
}

.page-previous a {
	background-color: #fff;
	border: 1px solid #e4e4e4;
	border-radius: 2px;
	padding: 7px 14px;
	width: 69px;
}

.page-next a {
	background-color: #fff;
	border: 1px solid #e4e4e4;
	border-radius: 2px;
	padding: 7px 14px;
	width: 69px;
}

.page a {
	background-color: #f5f5f5;
	border: 1px solid #d7d7d7;
	border-radius: 2px;
	color: #333;
	cursor: pointer;
	padding: 7px 14px;
	text-decoration: none;
}

.page a:hover, .page-active a {
	background-color: #ff0000;
	border: 1px solid #ccdbe4;
	color: #FFFFFF;
}


.footer{
    width: 100%;
    background: url(statics/images/banner1.jpg);
    margin: 30px auto 0;
    text-align: center;
    font-size: 18px;
    padding: 15px 0;
    color: #fff;
    }
</style>
</head>

<!-- http://www.xinhuanet.com/politics/xxjxs/2019-07/01/c_1124695938.htm -->
<!-- http://ai.baidu.com/tech/speech/tts?track=cp:ainsem|pf:pc|pp:chanpin-yuyin|pu:yuyin-yuyinhecheng-1|ci:|kw:10003524 -->

<body>
	<div style="height: 65px; overflow: hidden; width: 100%; background: url('statics/images/banner1.jpg');margin-bottom: 7px;text-align: center;">
		<div class="szsclogo"><a href="http://amr.sz.gov.cn/" target="_blank"><img src="statics/images/banner.jpg"></a></div>
	</div>
	<!-- End:header -->

	<!-- Start:content -->
	<div class="content">
		<!-- Start:crumbs -->
		<div class="crumbs yahei">
			<span class="crumbs-ico"></span>
			当前位置： <a href="http://mqs" target="_blank" class="">首页</a>><a><a href="javascript:;" target="_blank" class=""><span id="newsTitleBread">滚动新闻</span></a>
		</div>
		<!-- End:crumbs -->


		<h1 class="h1-news titles" style='text-align: center;'></h1>

		<!-- Start:share -->
		<div class="share yahei cf" style="text-align: center;">
				<div class="fs18 share-date">
					发布日期：<span id="date"></span> 
				</div>
			
		</div>

		<div class="page-info2">
			<h3 id="smallBodyTitle">
				<Article class='titles'></Article>
			</h3>
		</div>


		<!-- Start:main -->
		<div class="main cf">
			<!-- Start:left -->
			<div class="left">
				<!-- Start:article -->
				<div class="article yahei">
					<!-- <h2 class="article-title" id="fzy">10日，深圳市生态环境局副局长周岸标带队赴龙岗和坪山区对“散乱污”企业（场所）综合整治工作进行调研，同时开展突击检查执法行动，现场对5家环境违法企业进行了查处。</h2> -->
					<div class="article-content cf new_txt" id="contents">
						<!-- <p style='text-indent: 2em; margin-bottom: 15px;' ></p> -->
					</div>
					<div class="emit cf">
						<!-- <span class="r">[责任编辑：陈苏雅]</span> -->
					</div>
				</div>
				<!-- End:article -->


				<!-- Start:remark -->
				<!-- End:remark -->

			</div>
			<!-- End:left -->

		</div>
		<!-- End:main -->
	</div>
	<!-- End:content -->

	<!-- Start:footer -->
	<div class="footer">
	深圳市市场监督管理局不忘初心牢记使命主题教育领导小组办公室
	</div>
	<!-- End:footer -->

	<script>
		function loadDocument() {
			var docid = getQueryString('docid');
			if (docid != null) {
				$.ajax({
					url : "getDetail.do",
					type : "post",
					dataType : 'json',
					data : {
						docid : docid
					},
					beforeSend : function() {
						
					},
					success : function(data) {
						var dataStr = data.ret;
						$(".titles").html(dataStr.title);
						document.title = dataStr.title;
						var date = dataStr.pubdate;
						var cid = dataStr.channelid;
						var contents = dataStr.tranformeContents;
						var nav = "滚动新闻";
						if(cid==1){
							 nav = "滚动新闻";
						}
						else if(cid==2){
							 nav = "媒体报道";
						}else if(cid==3){
							nav = "理论学习";
						}else if(cid==4){
							nav = "方案计划";
						}else if(cid==5){
							nav = "影像资料";
						}
						$("#newsTitleBread").html(nav)
						
						if(dataStr.thumburl!=null){
							if(cid!=5){
								contents ="<img border='0' width='100%' height='100%' src="+dataStr.thumburl+"/>"+contents;
							}
							
						}
						$("#date").html(date.substr(0,date.length-2));
						$("#contents").html(contents);
					}
				})
			}
		}

		$(window).load(function(){
			loadDocument();
		})
		
	</script>
</body>
</html>