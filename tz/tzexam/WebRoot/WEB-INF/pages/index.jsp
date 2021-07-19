<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="common/common.jsp" %>
<!doctype html>
<html lang="en">
 <head>
    <meta charset="UTF-8">
    <meta name="Generator" content="EditPlusÂ®">
    <meta name="Author" content="">
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <title>潭州学院Javavip学生作业管理系统</title>
	<%@include file="common/public.jsp" %>
	<script type="text/javascript">var basePath = "${basePath}";</script>
	<link rel="stylesheet" href="${basePath}/resources/upload/tz-upload.css">
	<link rel="stylesheet" href="${basePath}/resources/js/jquery.mCustomScrollbar.min.css">
	<script src="${basePath}/resources/js/jquery.mCustomScrollbar.concat.min.js"></script>
	<script src="${basePath}/resources/upload/tz-upload.js"></script>
	<script src="${basePath}/resources/admin/tz-index.js"></script>
	<style type="text/css">
		html{overflow:hidden}
		#loadingbox{position:fixed;top:46%;left:0;z-index:3;width:100%;height:100%;text-align:center}
		.loadingover{position:fixed;top:0;left:0;z-index:2;background:#f9f9f9;opacity:.6;width:100%;height:100%}
		.sk-fading-circle{width:40px;height:40px;display:inline-block;position:relative}
		.sk-fading-circle .sk-circle{width:100%;height:100%;position:absolute;left:0;top:0}
		.sk-fading-circle .sk-circle:before{content:'';display:block;margin:0 auto;width:15%;height:15%;background-color:#333;border-radius:100%;-webkit-animation:sk-circleFadeDelay 1.2s infinite ease-in-out both;animation:sk-circleFadeDelay 1.2s infinite ease-in-out both}
		.sk-fading-circle .sk-circle2{-webkit-transform:rotate(30deg);-ms-transform:rotate(30deg);transform:rotate(30deg)}
		.sk-fading-circle .sk-circle3{-webkit-transform:rotate(60deg);-ms-transform:rotate(60deg);transform:rotate(60deg)}
		.sk-fading-circle .sk-circle4{-webkit-transform:rotate(90deg);-ms-transform:rotate(90deg);transform:rotate(90deg)}
		.sk-fading-circle .sk-circle5{-webkit-transform:rotate(120deg);-ms-transform:rotate(120deg);transform:rotate(120deg)}
		.sk-fading-circle .sk-circle6{-webkit-transform:rotate(150deg);-ms-transform:rotate(150deg);transform:rotate(150deg)}
		.sk-fading-circle .sk-circle7{-webkit-transform:rotate(180deg);-ms-transform:rotate(180deg);transform:rotate(180deg)}
		.sk-fading-circle .sk-circle8{-webkit-transform:rotate(210deg);-ms-transform:rotate(210deg);transform:rotate(210deg)}
		.sk-fading-circle .sk-circle9{-webkit-transform:rotate(240deg);-ms-transform:rotate(240deg);transform:rotate(240deg)}
		.sk-fading-circle .sk-circle10{-webkit-transform:rotate(270deg);-ms-transform:rotate(270deg);transform:rotate(270deg)}
		.sk-fading-circle .sk-circle11{-webkit-transform:rotate(300deg);-ms-transform:rotate(300deg);transform:rotate(300deg)}
		.sk-fading-circle .sk-circle12{-webkit-transform:rotate(330deg);-ms-transform:rotate(330deg);transform:rotate(330deg)}
		.sk-fading-circle .sk-circle2:before{-webkit-animation-delay:-1.1s;animation-delay:-1.1s}
		.sk-fading-circle .sk-circle3:before{-webkit-animation-delay:-1s;animation-delay:-1s}
		.sk-fading-circle .sk-circle4:before{-webkit-animation-delay:-.9s;animation-delay:-.9s}
		.sk-fading-circle .sk-circle5:before{-webkit-animation-delay:-.8s;animation-delay:-.8s}
		.sk-fading-circle .sk-circle6:before{-webkit-animation-delay:-.7s;animation-delay:-.7s}
		.sk-fading-circle .sk-circle7:before{-webkit-animation-delay:-.6s;animation-delay:-.6s}
		.sk-fading-circle .sk-circle8:before{-webkit-animation-delay:-.5s;animation-delay:-.5s}
		.sk-fading-circle .sk-circle9:before{-webkit-animation-delay:-.4s;animation-delay:-.4s}
		.sk-fading-circle .sk-circle10:before{-webkit-animation-delay:-.3s;animation-delay:-.3s}
		.sk-fading-circle .sk-circle11:before{-webkit-animation-delay:-.2s;animation-delay:-.2s}
		.sk-fading-circle .sk-circle12:before{-webkit-animation-delay:-.1s;animation-delay:-.1s}
		@-webkit-keyframes sk-circleFadeDelay{0%,100%,39%{opacity:0}
		40%{opacity:1}
		}
		@keyframes sk-circleFadeDelay{0%,100%,39%{opacity:0}
		40%{opacity:1}
		}
		.fa{color:#999;padding-right:6px}
		.container .silder{width:300px;background:#fff;height:100%;position:absolute;top:0;left:0;float:left;border-right:1px solid #e5e5e5}
		.silder .sbox{height:45px;text-align:center;line-height:45px}
		.silder .sbox input{font-size:12px;font-family:"微软雅黑";width:240px;height:24px;line-height:24px;text-indent:.5em;border:1px solid #e7e7e7}
		.silder .ibox ul li{line-height:34px}
		.silder .ibox ul li.on{background:#e7e7e7;color:#fff}
		.silder .ibox ul li.item{background:#333;color:#fff;border-bottom:1px solid #999}
		.silder .ibox ul li .cul li{background:#666;padding-left:20px}
		.silder .ibox ul li .fa{font-size:18px;color:#999;padding-right:10px}
		.silder .ibox ul li.on .fa{color:#fff}
		.container .rightbox{background:#fff;width:100%;height:100%;position:absolute;top:0;left:0;margin-left:301px}
		.rightbox .headerbox{height:90px;background:#333}
		.rightbox .userbox{height:45px;background:#fff;line-height:45px;font-size:14px;padding-left:10px;color:#4D89C1;position:relative}
		.rightbox .userbox .fa{color:#4D89C1}
		.rightbox .headerbox .upbtn{background:#666;color:#fff;padding:4px 24px;display:inline-block;margin-top:10px;margin-left:10px}
		.rightbox .headerbox .fa{color:#fff}
		.percentbar{padding:10px}
		.percentbar .bar{background:#111;height:2px;width:100%;position:relative}
		.percentbar .pbar{background:#4D89C1;height:2px;width:10%;position:absolute;top:0;left:0;height:2px}
		.name{padding-left:10px}
		.cul input{color:#333;height:26px;width:220px;outline:0;text-indent:.5em;font-family:"微软雅黑";line-height:26px}
		.cul .delicon{display:none}
		.cul li{cursor: pointer;}
		.silder .ibox ul li .cul  li.on{background:#ccc}
		.cul li:hover .delicon{display:block}
		.center{height:100%;float:left;border-right:1px solid #e5e5e5;}
		.contentbox{height:100%;float:left;width:100%;position:absolute;}
		.center ul li{padding-left:20px;border-bottom:1px solid #e7e7e7;padding:10px;}
		.center ul li.on{background:#e3e3e3;color:#333}
		.center ul li .fa{font-size:18px;color:#999;padding-right:10px;}
		.center ul li.on .fa{color:#b2b2b2}
		.center ul li:hover{background:#e3e3e3}
		.center ul li .op ,.center ul li .icon{display:block;float:left;}
		.center ul li span.op{float:right;line-height:40px;}
		.center ul li span p{line-height:0}
		.center ul li .icon .fa{font-size:32px;color:#b2b2b2}
		.center ul li .cnt{display:block;height:14px;line-height:14px;}
		.center ul li .op a{color:#333;font-size:12px;}	
	</style>
</head>
<body>
	<div class="container">
		<div class="silder">
			<div class="sbox">
				<span class="addSubject fl" style="padding-left:5px"  data-sid="${subject.id}"><i class="fa fa-plus"></i></span>
				<input type="text" max-length="30" onkeyup="tzsearch(this)" id="key" placeholder="请输入部门">
				<a href="javascript:void(0);" onclick="tz_sersubject(this)"><i class="fa fa-search"></i></a>
			</div>
			<!-- 部门 -->
			<div class="ibox">
				<ul id="subjectbox">
					<c:forEach var="subject" items="${subjects}" varStatus="sub">
						<li class="item  ${sub.index==0?'on':''}" data-opid="${subject.id}">
							<a href="javascript:void(0);" class="link" style="display:block;overflow: hidden;">
								<span class="name fl"><i class="fa fa-home"></i>${subject.name}</span>
								<span class="addFolder fr" data-sid="${subject.id}"><i class="fa fa-plus"></i></span>
							</a>
							<ul class="cul" style="display:${sub.index==0?'block':'none'}">
								<tz:folderList subjectId="${subject.id}">
									<li class="${folder_index==0?'on':''}" data-sid="${subject.id}" data-fid="${folder.id}" onclick="tzLoadingExam(${subject.id},${folder.id},this)">
										<span>${folder.name}</span>
										<a href='javascript:void(0);' data-fid="${folder.id}" class='fr delicon'  onclick='tz_deleteFolder(this)'><i class='fa fa-remove'></i></a>
										<a href='javascript:void(0);' data-fid="${folder.id}" class='fr delicon'  onclick='tz_editFolder(this)'><i class='fa fa-edit'></i></a>
									</li>
								</tz:folderList>
							</ul>
						</li>
					</c:forEach>
				</ul>
			</div>
			<!-- 部门 -->
		</div>
		<div class="rightbox">
			<div class="userbox">
				<div class="fl">
					<a href="javascript:void(0);" class="expand"><i class="fa fa-square-o"></i></a>
					<i class="fa fa-user"></i>${SESSION_USER_NAME}
				</div>
				<div class="fr setting" style="margin-right:310px;font-size:12px;">
				<a href="${basePath}/loginout"><i class="fa fa-cog fa-fw"></i>
					<tz:message locale="${locale}" key="logout"/>
				</a></div>
			</div>
			
			<div class="headerbox">
				<input type="hidden" id="subjectdir" />
				<a href="javascript:void(0);" class="upbtn" id="uploadBtn"><i class="fa fa-upload"></i>
				<tz:message locale="${locale}" key="name"/>	
				</a>	
				<div class="percentbar" id="percentbar">
					<div class="bar"><div class="pbar"></div></div>
				</div>
			</div>
			<div class="center">
				<!-- 存放作业的区域 -->
				<ul id="examlist"></ul>
			</div>
			<div class="contentbox">
			</div>
		</div>
	</div>
	<div id="loadingbox" style="display:none;">
		<div class="sk-fading-circle">
		  <div class="sk-circle1 sk-circle"></div>
		  <div class="sk-circle2 sk-circle"></div>
		  <div class="sk-circle3 sk-circle"></div>
		  <div class="sk-circle4 sk-circle"></div>
		  <div class="sk-circle5 sk-circle"></div>
		  <div class="sk-circle6 sk-circle"></div>
		  <div class="sk-circle7 sk-circle"></div>
		  <div class="sk-circle8 sk-circle"></div>
		  <div class="sk-circle9 sk-circle"></div>
		  <div class="sk-circle10 sk-circle"></div>
		  <div class="sk-circle11 sk-circle"></div>
		  <div class="sk-circle12 sk-circle"></div>
		</div>
	</div>
	<div class="loadingover" style="display:none;"></div>
 </body>
</html>
