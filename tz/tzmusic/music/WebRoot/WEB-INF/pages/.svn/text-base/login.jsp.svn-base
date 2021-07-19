<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>潭州学院-音乐网站--登陆页面</title>
<meta name="keywords" content="keyword1,keyword2,keyword3">
<meta name="description" content="this is my page">
<meta name="content-type" content="text/html; charset=UTF-8">
<%@include  file="/WEB-INF/common/common.jsp"%>  
<style type="text/css">
	.container{left:50%;margin-left:-400px;position:absolute;top:23%;width:800px;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box;}
	.theader{margin-bottom:26px;}
	.size_large{float:none;width:232px;height:70px;margin:0 auto;text-align:center;line-height:62px;font-size:56px;}
	.size_large .logo_anchor{color:#999}
	.size_large .logo_anchor:hover{color:#529ECC;transition:all 1s ease;text-shadow:1px 1px 1px #4684b2,2px 2px 2px #111;font-size:60px;}
	.form_row{width:320px;margin:0 auto;}
	.form_row input{padding:11px 10px 11px 13px;display:block;margin:0;width:100%;border:0;background:#fff;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box;height:60px;outline:none;font-family:"微软雅黑";font-size:14px;}
	.form_row input:focus{background:#FAEEBE;transition:all 0.5s linear;}
	.buttons{margin-top:50px;text-align:center;}
	.buttons .loginbtn{background:#529ECC;padding:10px 120px;font-size:20px;color:#f9f9f9;border-radius:4px;}
	.buttons .loginbtn:hover{background:#1D3C4F;transition:all 0.5s ease;padding:12px 120px;}
	.form_row_email{border-bottom:1px solid #FAEBEB}
	.errormsg{margin:0 auto;background:#FAEEBE;width:305px;margin-top:20px;border:none;padding:8px;}
	.efont{position:relative;color:#588411;}
	/*背景图片*/
	.lay_background{position:fixed;left:0;top:0;z-index:-1;overflow:hidden}
	#suggestbox .items{height:15px;border-bottom:1px solid #fff;padding:8px 0;text-indent: 1em;}
	#suggestbox .items:hover{background:#fca;}
	.on{background:#DFCB5C;color:#fff;}
</style>
</head>

<body>
	<div class="container">
		<div class="theader">
			<h1 class="logo size_large">
				<a class="logo_anchor" href="javascript:void(0);">静.....</a>
			</h1>
		</div>
		<div class="signup_view account login" style="height: 160px;">
			<div class="form_row form_row_email">
				<input type="text" autocomplete="off" autofocus="autofocus" maxlength="80" name="account" placeholder="请输入邮箱..."
					id="account"/>
			</div>
			<div class="form_row form_row_password">
				<input type="password" name="password" autocomplete="off"  maxlength="20" placeholder="请输入密码..."
					id="password" value="123456"/>
			</div>
			<div class="errormsg hide">
				<span class="efont"></span>
			</div>
		</div>
		<div class="buttons">
			<a href="javascript:void(0);" id="login" class="loginbtn">登陆</a>
		</div>
	</div>
	<div class="lay_background" id="lay_background"
		style="width: 100%; height: 100%;">
		<div id="lay_background_img"
			class="lay_background_img lay_background_img_fade_out">
			<img src="http://www.zhihuishu.com/web/images/index/loginBg_5.jpg"
				id="bgimg">
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$(document).keydown(function(e){
				if(e.keyCode==13){
					$("#login").trigger("click");
				}
			});
			
			$("#login").on("click",tm_login);
			$("body").click(function(e){
				$("#suggestbox").remove();
				return false;
			});
			var index = 0;
			$("#account").keyup(function(e){
				var $this = $(this);
				var value = $this.val();
				
				if(!$.trim(value)){
					return;
				}
				//一定输入一个@符号才出现自动补全
				if(value.indexOf("@")!=-1){
					var marr = value.split("@");
					//获取当前文本框的高度和宽和坐标x ,y
					var width = $this.width();
					var height = $this.height();
					var left = $this.offset().left;
					var top = $this.offset().top;
					//创建一个div
					$("#suggestbox").remove();
					var $div = $("<div id='suggestbox'></div>");
					$div.css({
						position:"absolute",
						top : (height+top+25),
						left:left,
						width:(width+22),
						height:240,
						border:"1px solid #ccc",
						background:"#e5e5e5"
					});
					
					$("body").append($div);
					var arr = tz_suggest(marr[0]+"@");
					var html = "";
					var length = arr.length;
					for(var i=0;i<length;i++){
						html +="<div class='items'>"+arr[i]+"</div>";
					}
					$div.append(html);
					
					//点击选中
					$("#suggestbox").find(".items").click(function(){
						$this.val($(this).text());
						$("#suggestbox").remove();
						index = 0;
					});
					
					//键盘向下选中
					var kcode = e.keyCode;
					if(kcode==40 || kcode == 13){
						if(index == length)index = 0;
						var $on = $("#suggestbox").find(".items").eq(index);
						$on.addClass("on").siblings().removeClass("on");
						$this.val($on.text());
						index++;
						if(kcode==13){
							index = 0;
							$("#suggestbox").remove();
						}
					}
					
					//esc退出
					if(kcode==27){
						index = 0;
						$this.select();
						$("#suggestbox").remove();
					}
				}else{
					index = 0;
					$("#suggestbox").remove();
				}
			});
			
			
			function tz_suggest(key){
				var arr = [];
				arr.push(key+"qq.com");
				arr.push(key+"sina.com");
				arr.push(key+"126.com");
				arr.push(key+"163.com");
				arr.push(key+"sina.com.cn");
				return arr;
			}
			
			
		});
		
		/*登陆方法*/
		function tm_login(){
			var account = $("#account").val();
			var password = $("#password").val();
			
			if(isEmpty(account)){
				 $("#account").focus();
				 showmessage("请输入账号!");
				 return false;
			}
			
			if(isEmpty(password)){
				 $("#password").focus();
				 showmessage("请输入密码!");
				 return false;
			}
			
			$.ajax({
				type:"post",
				beforeSend:function(){$("#login").off("click").css("padding","10px 110px").text("登陆中...");},
				error:function(){$("#login").on("click",tm_login).text("登陆");},
				url:"${basePath}/logined/"+account+"/"+password,
				success:function(data){
					if(data){
						//调整到首页去
						var rurl = document.referrer;
						if(!rurl){
							window.location.href = "${basePath}/music/1";
						}else{
							window.location.href = rurl;
						}
					}else{
						showmessage("请正确输入账号和密码 ！");
						$("#account").select();
						$("#password").val("");
						$("#login").on("click",tm_login).css("padding","10px 120px").text("登陆");
					}
				}
			});
		};
		
		//显示错误信息
		function showmessage(message){
			$(".errormsg").show().removeClass("hide").fadeOut(2000).find("span").text(message);
		}
	
	</script>
</body>
</html>
