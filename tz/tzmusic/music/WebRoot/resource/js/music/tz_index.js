/*************************音乐播放代码***********************************/
$(function(){
	/*置顶*/
	$(window).scroll(function() {
	    if ($(this).scrollTop() >= 100){
	    	$("#dynamic-to-top,.btn-wrapper-proxy").fadeIn(300);
	    }else{
	    	$("#dynamic-to-top,.btn-wrapper-proxy").stop(true, true).fadeOut(300);
	    }
	});
	/*点击置顶*/
	$("#dynamic-to-top").on("click",function() {
	    $("html, body").animate({
	        scrollTop: 0
	    },"slow");
	    $("#dynamic-to-top,.btn-wrapper-proxy").stop(true, true).fadeOut(300);
	    return false;
	});
	
	/*点击body关闭登录弹出框*/
	$("body").click(function(e){
		$("#tm_login_dialog").fadeOut("slow",function(){
			$(this).remove();
		});
		stopBubble(e);
	});
	//播放
	$(".play").on("click",function(){
		if(audio.audioDom.paused){//判断当前是暂停的情况下，才播放
			audio.play();
		}
		$(".play").hide();
		$(".pause").show();
		$(".cycle").addClass("r");
	});
	
	
	//暂停
	$(".pause").on("click",function(){
		if(audio.audioDom.played){//判断当前是播放的情况下，才暂停
			audio.pause();
		}
		$(".pause").hide();
		$(".play").show();
		$(".cycle").removeClass("r");
	});
	
	
	var index = 0;
	var length = $("#playlist").find(".track-item").length;
	//下一首
	var nextTimeout = null;
	var prevTimeout = null;
	$(".next").on("click",function(){
		clearTimeout(nextTimeout);
		nextTimeout = setTimeout(function(){
			index++;
			if(index >=length)index = 0;
			$("#playlist").find(".track-item").eq(index).trigger("click");
		}, 200);
	});
	
	//下一首
	$(".prev").on("click",function(){
		clearTimeout(prevTimeout);
		prevTimeout = setTimeout(function(){
			index--;
			if(index <=0)index = length-1;
			$("#playlist").find(".track-item").eq(index).trigger("click");
		}, 200);
	});
	
	//播放第一首歌曲
	$("#playlist").find(".track-item").on("click",function(){
		var $link =  $(this).find(".playitems");
		var src =$link.data("src");
		var img =$link.data("img");
		var article =$link.data("article");
		var singer =$link.data("singer");
		var title =$link.attr("title");
		audio.playLink(src);
		$(this).addClass("onplayed").siblings().removeClass("onplayed");
		$(".play").hide();
		$(".pause").show();
		index = $(this).index();
		//修改音乐的相关信息和封面
		$(".imagetarget").attr("src","/"+img+"?date="+new Date().getTime());
		$(".m_title").text(title);
		$(".m_article").text(article);
		$(".m_singer").text(singer);
		$(".cycle").addClass("r");
	}).eq(0).trigger("click");
	
	
	//静音
	$(".staticvolume").click(function(){
		var $this = $(this);
		audio.staticVolume(function(p){
			if(p==0){
				$this.toggleClass("staticvolume staticvolume_none");
			}else{
				$this.toggleClass("staticvolume staticvolume_none");
			}
			$(".m_volume_percent").width((p*100)+"%");
		});
	});
	
	//单曲循环
	$(".m_loop").click(function(){
		if(audio.loop){
			$(this).addClass("m_loop_one");
			audio.loops(false);
		}else{
			$(this).removeClass("m_loop_one");
				audio.loops(true);
			}
	});
});

/**********************************评论的添加和控制******************************************/
function tm_loadComments(pageNo,pageSize){
	var periodId = $("#submitcomment").data("pid");
	$("#comment-listbox").load("/ncomment/template/"+periodId+"/"+pageNo+"/"+pageSize,function(){
		sessionStorage.setItem("commentDatas", $("#comment-listbox").html());
	});
};

$(function(){
	var pageNo = 0;
	var pageSize = 10;
	tm_loadComments(pageNo,pageSize);//加载评论
});

//保存评论
function tm_saveComment(obj,e){
	stopBubble(e);
	var user = getUserInfo();
	if(isEmpty(user.img)){
		tm_loginDialog(obj);
		return;
	}
	var periodId = $(obj).data("pid");
	var content = $("#commentEditor").val();
	if(isEmpty(periodId)){
		loading("没有找到对应的期刊!!!",1);
		return false;
	}
	if(isEmpty(content)){
		loading("请输入评论...",3);
		$("#commentEditor").select();
		return false;
	}
	if(isNotEmpty(content) && content.length>400){
		loading("评论的长度不能超过400个字符...",2);
		$("#commentEditor").focus();
		return;
	}
	var message = content;
	$("#commentEditor").val("");
	//控制用户重复提交
	$(obj).text("提交中...").removeAttr("onclick");
	$.ajax({
		type:"post",
		data:{"content":message},
		beforeSend:function(){loading("请稍后，数据提交中...");},
		error:function(){loading("remove",1);$(obj).text("提交").attr("onclick","tm_saveComment(this)");},
		url:"/comment/save/"+periodId,
		success:function(data){
			if(data=="success"){
				$(obj).text("提交").attr("onclick","tm_saveComment(this)");
				loading("添加成功",1);
				$("#commentEditor").val("");
				var user = getUserInfo();
				$("#comment-listbox").prepend("<div class='item animated rollIn'>"+
				"		<a href='javascript:void(0);' class='avatar-wrapper' >"+
				"			<img src='"+user.img+"' alt='BLACK8' class='avatar rounded'>"+
				"		</a>"+
				"		<div class='item-wrapper'>"+
				"			<p class='helper'>"+
				"				<a href='javascript:void(0);' class='username' target='_blank'>"+user.username+"</a>"+
				"			</p>"+
				"			<div class='comment-ct'>"+
				"				<p class='the-comment'>"+content+"</p>"+
				"			</div>"+
				"			<div class='helper clearfix'>"+
				"				1秒前"+
				"				&nbsp;&nbsp;来自："+user.address+
				"				&nbsp;&nbsp;"+
				"				<a href='javascript:;' class='btn-reply btn-action-reply'>回复</a>&nbsp;&nbsp;"+
				"				<a class='btn-vote btn-action-vote' href='javascript:;'><span>赞</span></a>"+
				"			</div>"+
				"		</div>"+
				"	</div>");
			}else if(data=="fail"){
				alert("保存失败!!!");
			}else if(data=="logout"){
				//在这里一个弹出登陆div层
				tm_loginDialog(obj);
			}
		}
	});
};

/*弹出登陆框*/
function tm_loginDialog(obj){
	var left = $(obj).offset().left ;
	var top = $(obj).offset().top + $(obj).height()+20;
	$("#tm_login_dialog").remove();
	$("body").append("<div class='login_dialog' style='left:"+left+"px;top:"+top+"px' id='tm_login_dialog'>"+
	"	<span class='corn'></span>"+
	"	<div class='msg_header'>"+
	"		<h1>潭州学院登录</h1>"+
	"	</div>"+
	"	<div class='msg_box'>"+
	"		<p><input type='text' maxLength='80' autoFocus='autoFocus' id='d_account' class='inp ua' placeholder='请输入账号...'></p>"+
	"		<p><input type='password' maxLength='20' id='d_password' class='inp' placeholder='请输入密码...'></p>"+
	"		<p style='padding-top: 20px;'><a href='javascript:void(0);' onclick='tm_dialog_login(this)' class='btnlogin'>登录</a></p>"+
	"		<p class='erromessage hide' id='d_message'>请输入账号和密码!!!</p>"+
	"	</div>	"+
	"</div>");
	
	$("#tm_login_dialog").click(function(e){
		stopBubble(e);
	});
};

/*弹出登陆业务逻辑*/
function tm_dialog_login(obj){
	var account = $("#d_account").val();
	var password = $("#d_password").val();
	if(isEmpty(account)){
		 $("#d_account").focus();
		 showmessage("请输入账号!");
		 return false;
	}
	
	if(isEmpty(password)){
		 $("#d_password").focus();
		 showmessage("请输入密码!");
		 return false;
	}
	
	$.ajax({
		type:"post",
		beforeSend:function(){$(obj).off("click").css("padding","8px 101px").text("登陆中...");},
		error:function(){$(obj).on("click",tm_dialog_login).css("padding","8px 100px").text("登陆");},
		url:"/logined/"+account+"/"+password,
		success:function(data){
			if(data){
				//关闭登录框，提示登录成功
				$("#tm_login_dialog").fadeOut("slow",function(){
					$(this).remove();
				});
				tm_loginsuccess(data);//登录成功返回头像信息
			}else{
				showmessage("请正确输入账号和密码 ！");
				$("#d_account").select();
				$("#d_password").val("");
				$(obj).on("click",tm_dialog_login).css("padding","8px 100px").text("登陆");
			}
		}
	});
};

//登录成功
function tm_loginsuccess(user){
	$("#tz_usermessagebox").html("<span class='sgl_items'>"+
	"		<img src='"+user.headerPic+"' data-address='"+user.address+"'  id='userinfo' title='"+user.username+"'>"+
	"		<a href='/center'>【"+user.username+"】</a><a href='javascript:void(0);' onclick='tm_logout(this)'>【退出】</a>"+
	"</span>");
};

//注销的方法
function tm_logoutsuccess(){
	$("#tz_usermessagebox").html("<span class='sgl_items'><a href='/reg'>注册</a>&nbsp;<a href='/login'>登陆</a></span>");
}

/*注销方法*/
function tm_logout(){
	$.tzAlert({title:"退出登录提示",content:"您确定退出吗?",callback:function(ok){
		if(ok){
			$.ajax({
				type:"post",
				url:"/logout",
				success:function(data){
					tm_logoutsuccess();
				}
			});
		}
	}});
}

//显示错误信息
function showmessage(message){
	$("#d_message").show().removeClass("hide").fadeOut(2000).text(message);
}

//用户信息获取--封装
function getUserInfo(){
	var $user = $("#userinfo");
	var img = $user.attr("src");
	var account = $user.attr("account");
	var username = $user.attr("title");
	var address = $user.data("address");
	var data = {
		img:img,
		username:username,
		address:address,
		account:account
	};
	return data;
};

//得到焦点 
function editorFocus(id) {
	CKEDITOR.instances[id].focus();
}

//获取带有格式的内容--html
function getEditorHtml(editorId) {
	var editor = CKEDITOR.instances[editorId];
    return editor.getData();
};

//获取编辑器内容,纯文本 text
function getEditorText(editor) {
	var stemTxt = CKEDITOR.instances[editor].document.getBody().getText();
	return stemTxt;
};

//插入编辑器内容--覆盖
function setEditorContent(editorId,message) {
	var editor = CKEDITOR.instances[editorId];
	editor.setData(message);
};

//追加编辑器内容--追加
function  appendEditorContent(editorId,message){
	var editor = CKEDITOR.instances[editorId];
	editor.insertHtml(message);
};