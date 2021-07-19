<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>图片预览技术</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="js/util.js"></script>
	<link href="css/magic.css"  rel="stylesheet"> 
	<style type="text/css">
		#tmimgbox{
			position: absolute;left: 0px;top: 0px;padding:10px;border:2px solid #e5e5e5;background:#f9f9f9;
			box-shadow:1px 1px 1em #333;
			border-radius:4px;
			z-index:100
		}
		.tmui-overlay{width:100%;height:100%;background-color:#2D373C;position:absolute;top:0;left:0;z-index:99;filter:alpha(opacity=78);-moz-opacity:0.78;-khtml-opacity:0.78;opacity:0.78;}
		#tmimgbox .icon{background:url('images/icon.png')}
		#tmimgbox .close{background-position:0px 0px;display:inline-block;width:29px;height:26px;position:absolute;right:0px;top:-14px;right:-15px;}
	</style>
  </head>
  
  <body>
  	
  	<img data-bigsrc="images/2.jpg" src="images/2.jpg" class="tmui_imgbox" width="120" height="120">
  	<img data-bigsrc="images/3.jpg" src="images/3.jpg" class="tmui_imgbox" width="120" height="120">
  	<img data-bigsrc="images/4.jpg" src="images/4.jpg" class="tmui_imgbox" width="120" height="120">
  	<img data-bigsrc="images/5.jpg" src="images/5.jpg" class="tmui_imgbox" width="120" height="120">
  	<img data-bigsrc="images/6.jpg" src="images/6.jpg" class="tmui_imgbox" width="120" height="120">
  	<img data-bigsrc="images/folder.png" src="images/folder.png" class="tmui_imgbox" width="120" height="120">
  	
  	<script type="text/javascript" src="js/tz_imgbox.js"></script>
  	<script type="text/javascript">
  		$(function(){
  			//为什么要等比例压缩，是为了防止图片失真
  			//如果你在一个网站上访问的时候，图片是需要从服务器下载的，下载的过程是需要时间，如果如图还没下载完毕，通过javascript获取还正在下载
  			//或者还没有下载完毕的图片的时候，返回的img.width/img.height=0
  			$(".tmui_imgbox").tzImgbox({events:"click",position:true,width:960,height:560});
  		});
  	</script>
  </body>
</html>