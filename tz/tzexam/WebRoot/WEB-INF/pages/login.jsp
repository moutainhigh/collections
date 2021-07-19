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
    <title>Document</title>
	<%@include file="common/public.jsp" %>
</head>
<body>
	
	<style>
		#loadingbox{ position:fixed;
			top:46%;
			left:0;
			z-index:3;
			width:100%;
			height:100%;
			text-align:center;
		  }

		  .loadingover{position:fixed;
			top:0;
			left:0;
			z-index:2;
			background:#f9f9f9;
			opacity:0.6;
			width:100%;
			height:100%;}
		.sk-fading-circle {
		  width: 40px;
		  height: 40px;
		  display:inline-block;
		  position:relative;
		 }
		  .sk-fading-circle .sk-circle {
			width: 100%;
			height: 100%;
			position: absolute;
			left: 0;
			top: 0; }
		  .sk-fading-circle .sk-circle:before {
			content: '';
			display: block;
			margin: 0 auto;
			width: 15%;
			height: 15%;
			background-color: #333;
			border-radius: 100%;
			-webkit-animation: sk-circleFadeDelay 1.2s infinite ease-in-out both;
					animation: sk-circleFadeDelay 1.2s infinite ease-in-out both; }
		  .sk-fading-circle .sk-circle2 {
			-webkit-transform: rotate(30deg);
				-ms-transform: rotate(30deg);
					transform: rotate(30deg); }
		  .sk-fading-circle .sk-circle3 {
			-webkit-transform: rotate(60deg);
				-ms-transform: rotate(60deg);
					transform: rotate(60deg); }
		  .sk-fading-circle .sk-circle4 {
			-webkit-transform: rotate(90deg);
				-ms-transform: rotate(90deg);
					transform: rotate(90deg); }
		  .sk-fading-circle .sk-circle5 {
			-webkit-transform: rotate(120deg);
				-ms-transform: rotate(120deg);
					transform: rotate(120deg); }
		  .sk-fading-circle .sk-circle6 {
			-webkit-transform: rotate(150deg);
				-ms-transform: rotate(150deg);
					transform: rotate(150deg); }
		  .sk-fading-circle .sk-circle7 {
			-webkit-transform: rotate(180deg);
				-ms-transform: rotate(180deg);
					transform: rotate(180deg); }
		  .sk-fading-circle .sk-circle8 {
			-webkit-transform: rotate(210deg);
				-ms-transform: rotate(210deg);
					transform: rotate(210deg); }
		  .sk-fading-circle .sk-circle9 {
			-webkit-transform: rotate(240deg);
				-ms-transform: rotate(240deg);
					transform: rotate(240deg); }
		  .sk-fading-circle .sk-circle10 {
			-webkit-transform: rotate(270deg);
				-ms-transform: rotate(270deg);
					transform: rotate(270deg); }
		  .sk-fading-circle .sk-circle11 {
			-webkit-transform: rotate(300deg);
				-ms-transform: rotate(300deg);
					transform: rotate(300deg); }
		  .sk-fading-circle .sk-circle12 {
			-webkit-transform: rotate(330deg);
				-ms-transform: rotate(330deg);
					transform: rotate(330deg); }
		  .sk-fading-circle .sk-circle2:before {
			-webkit-animation-delay: -1.1s;
					animation-delay: -1.1s; }
		  .sk-fading-circle .sk-circle3:before {
			-webkit-animation-delay: -1s;
					animation-delay: -1s; }
		  .sk-fading-circle .sk-circle4:before {
			-webkit-animation-delay: -0.9s;
					animation-delay: -0.9s; }
		  .sk-fading-circle .sk-circle5:before {
			-webkit-animation-delay: -0.8s;
					animation-delay: -0.8s; }
		  .sk-fading-circle .sk-circle6:before {
			-webkit-animation-delay: -0.7s;
					animation-delay: -0.7s; }
		  .sk-fading-circle .sk-circle7:before {
			-webkit-animation-delay: -0.6s;
					animation-delay: -0.6s; }
		  .sk-fading-circle .sk-circle8:before {
			-webkit-animation-delay: -0.5s;
					animation-delay: -0.5s; }
		  .sk-fading-circle .sk-circle9:before {
			-webkit-animation-delay: -0.4s;
					animation-delay: -0.4s; }
		  .sk-fading-circle .sk-circle10:before {
			-webkit-animation-delay: -0.3s;
					animation-delay: -0.3s; }
		  .sk-fading-circle .sk-circle11:before {
			-webkit-animation-delay: -0.2s;
					animation-delay: -0.2s; }
		  .sk-fading-circle .sk-circle12:before {
			-webkit-animation-delay: -0.1s;
					animation-delay: -0.1s; }

		@-webkit-keyframes sk-circleFadeDelay {
		  0%, 39%, 100% {
			opacity: 0; }
		  40% {
			opacity: 1; } }

		@keyframes sk-circleFadeDelay {
		  0%, 39%, 100% {
			opacity: 0; }
		  40% {
			opacity: 1; } }	
		.fa{color:#999;padding-right:6px;}
		.container{width:100%;height:100%;position:fixed;top:0;left:0;background:rgba(255, 255, 250,0.6);text-align:center;}
		.container .loginbox{width:780px;margin:200px auto;text-align:center;position:relative;height:100%;}
		.container h1{font-size:42px;margin-bottom:42px;color:#999;text-shadow:2px 2px 1px #111;box-shadow:0 0 20em #999;font-weight:700 }
		.container .loginbox  .inpb{margin-top:12px;box-shadow:0 0 200em #ccc;}
		.container .loginbox input{width:320px;height:42px;border:1px solid #e5e5e5;font-family:"微软雅黑";text-indent:1em;box-shadow:2px 2px 1px #111;outline:none;}
		.container .loginbox input[type='button']{width:320px;height:42px;border:1px solid #e5e5e5;color:#333;font-size:14px;font-family:"微软雅黑";box-shadow:2px 2px 1px #111}
		.container .btn{outline:none;}
		.container .btn:hover{background:#e5e5e5;color:#fff;}
		.container .info{margin-top:40px;color:#fff;font-size:13px;}
	</style>
	
	<div class="container">
		<div class="loginbox">
			<h1>潭州学院软件集群VIP作业管理系统</h1>
			<div class="inpb"><input type="text" id="account" value="keke" laceholder="请输入学号/邮箱/手机"/></div>
			<div class="inpb"><input type="password" id="password" value="123456"  placeholder="请输入密码"/></div>
			<div class="inpb"><input type="button" onclick="tzLogin(this)" class="btn" value="登&nbsp;&nbsp;陆"/></div>
			<select id="locale">
				<option value="zh">中国</option>
				<option value="en">英国</option>
			</select>
			<div class="info">power by keke</div>
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
	<script type="text/javascript">
	
		document.onkeydown = function(e){
			if(e.keyCode==13){
				tzLogin();
			}
		};
	
		function tzLogin(){
			var account = $("#account").val();
			var password = $("#password").val();
			var locale = $("#locale").val();
			if(account==""){
				alert("请输入账号...");
				$("#account").focus();
				return;
			}
			if(password==""){
				alert("请输入密码...");
				$("#password").focus();
				return;
			}
			$.ajax({
				type:"post",
				url:basePath+"/logined",
				data:{account:account,password:password,locale:locale},
				success:function(data){
					if(data=="success"){
						window.location.href = basePath+"/admin/index";
					}else if(data=="null"){
						alert("请输入账号和密码...");
						$("#account").focus();
					}else if(data=="error"){
						alert("请正在输入账号和密码...");
						$("#password").val("").focus();
					}						
				}
			});
		};
	</script>
 </body>
</html>