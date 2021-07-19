<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<div id='flashcontent'>您的浏览器和Flash环境异常, 导致该内容无法显示!</div>
<noscript>您使用的浏览器不支持或没有启用javascript, 请启用javascript后再访问!</noscript>
<script type='text/javascript' src='/wcm/WCMV6/video/js/opensource/swfobject.js'></script>
<script type='text/javascript'>
var flashvars = {videoSource:'http://localhost:8134/fmsApp/1237e4db896.flv',autoPlay:'true',FunMode:'0',isAutoBandWidthDetection:'false',logoAlpha:'0'};
var params = {allowFullScreen:'true',quality:'high',allowScriptAccess:'always'};
var attributes = {};attributes.id = 'SimplePlayer';
swfobject.embedSWF('/wcm/WCMV6/video/TRSVideoPlayer.swf','flashcontent',320,240,'9.0.124',false,flashvars,params,attributes);
</script>