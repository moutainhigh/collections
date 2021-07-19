<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="header">
	<div class="logo">
		<span class="i_logo"><a href="/" title="潭州学院音乐网站"><img src="${basePath}/images/logo.png"></a></span>
		<div class="btn-wrapper-proxy">
			<a href="javascript:;" id="prev" class="jp-previous prev" title="上一曲">
				<span class="icon-prev-lg icon"></span>
			</a>
			<a href="javascript:;" id="play" class="play" title="播放">
				<span class="icon-play-lg icon"></span>
			</a>
			<a href="javascript:;" id="pause" class="pause" title="暂停" style="display: none;">
				<span class="icon-pause-lg icon"></span>
			</a>
			<a href="javascript:;" id="next" class="jp-next next margin-right-0" title="下一曲">
				<span class="icon-next-lg icon"></span>
			</a>
		</div>
		<div id='tz_usermessagebox'>
			<c:if test="${empty user}">
				<span class="sgl_items"><a href="${basePath}/reg">注册</a>&nbsp;<a href="${basePath}/login">登陆</a></span>
			</c:if>
			<c:if test="${not empty user}">
				<span class="sgl_items">
				<img src="${user.headerPic}" data-address="${address}"  id="userinfo" title="${user.username}">
				<a href="javascript:void(0);" target="_blank" title="${user.username}">【${user.username}】</a><a href="javascript:void(0);" onclick="tm_logout(this)">【退出】</a>
				</span>
			</c:if>
		</div>
	</div>
	
</div>