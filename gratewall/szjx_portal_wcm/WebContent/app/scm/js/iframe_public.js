$(function (){
	setInterval("setHeight()",100);
});
//改变头的选中状态
function userChangeState(num,path){
	for (var i=1;;i++){
		if($("#user_"+i).html()==null){
			break;
		}
		if(num!=i){
			$("#user_h"+i).addClass('grayPic');
			$("#user_l"+i).addClass('grayPic');
		}else{
			if($("#user_h"+i).hasClass('grayPic')){
				$("#user_h"+i).removeClass('grayPic');
				$("#user_l"+i).removeClass('grayPic');
			}
		}
	}
	window.open(path,"_self");
}
//发送AJAX请求
function sendAjax(action){
	switch(action){
		case "#all":
			$(action).css("background","#9CAAB7");
			$("#first").css("background","#fff");
			$("#image").css("background","#fff");
			break;
		case "#first":
			$(action).css("background","#9CAAB7");
			$("#all").css("background","#fff");
			$("#image").css("background","#fff");
			break;
		case "#image":
			$(action).css("background","#9CAAB7");
			$("#first").css("background","#fff");
			$("#all").css("background","#fff");
			break;
		case "#repeatOrder":
			$(action).css("background","#9CAAB7");
			$("#timeOrder").css("background","#fff");		
			break;
		case "#timeOrder":
			$("#repeatOrder").css("background","#fff");
			$(action).css("background","#9CAAB7");	
			break;
	}
}
//设置高度
function setHeight(){
	if($("body")!=null && $("body")!="" && $("body")!="undefined"){
		var bodyHeight=$("body").height();
		var ele = $(window.parent.document).find("#content");
		var selectMod = $("#moreGroups");
		if(selectMod!=null && selectMod!="" && selectMod!="undefined"){
			if($(selectMod).height() > bodyHeight){
				bodyHeight = $(selectMod).height();
			}
		}
		if(ele!=null && ele!="" && ele!="undefined"){
			if((bodyHeight) > 600){
				$(window.parent.document).find("#content").height(bodyHeight);//设置父页面的内容区高度
				$(window.parent.document).find("#contentRight").height(bodyHeight);//设置父页面的内容右区的高度
				$(window.parent.document).find("#frame_content").height(bodyHeight);//设置iframe的高度
			}else{
				$(window.parent.document).find("#content").height(600);
				$(window.parent.document).find("#contentRight").height(600);
				$(window.parent.document).find("#frame_content").height(600);
			}
		}
	}
}
//帐号管理的设置高度
function setAccountMgrHeight(){
	if($("body")!=null && $("body")!="" && $("body")!="undefined"){
		var bodyHeight=$("body").height();
		var ele = $(window.parent.document).find("#frame_account");
		var selectMod = $(window.parent.document).find("#moreGroups");
		if(selectMod!=null && selectMod!="" && selectMod!="undefined"){
			if($(selectMod).height() > bodyHeight){
				bodyHeight = $(selectMod).height()+100;
			}
		}
		if(ele!=null && ele!="" && ele!="undefined"){
			if((bodyHeight) > 550){
				$(window.parent.document).height(bodyHeight+100);//设置父页面的内容区高度
				$(window.parent.document).find("#frame_account").height(bodyHeight+100);
				$(window.parent.document).height(bodyHeight+100);
			}else{
				$(window.parent.document).height(650);
				$(window.parent.document).find("#frame_account").height(650);
				$(window.parent.document).height(650);
			}
		}
	}
}
//图片切换
function changeImg(that,smallImgPath,bigImgPath){
	if($(that).attr('src')!=bigImgPath){
		$(that).parent().css("text-align","center");
		$(that).attr('src',bigImgPath);
		$(that).css("cursor","url(../images/small.cur), auto");
	}else{
		$(that).parent().css("text-align","left");
		$(that).attr('src',smallImgPath);
		$(that).css("cursor","url(../images/big.cur), auto");
	}
}
//评论框的显示
function showGiveComment(accountId,microContentId,backPage){
	$("#giveComment_"+microContentId).slideToggle();
	if($("#giveComment_"+microContentId).height()<10){
		$.ajax({
			type:"post",
			data:{AccountId:accountId,MicroContentId:microContentId,PageIndex:1,PageSize:5,BackPage:backPage},
			dataType:"html",
			url:"get_comments.jsp",
			success:function(data){
				$("#showComments_"+microContentId).html(data);
			},
			error:function(){
				alert("与服务器连接失败！");
			}
		});
	}else{
		$("#showComments_"+microContentId).html("")
	}
}
//评论框的显示
function showGiveRelayComment(commentId){
	$("#giveComment_"+commentId).slideToggle();
}
//内部评论框的显示
function showInnerGiveComment(accountId,parentContentId,ContentId,backPage){
	$("#giveComment_"+parentContentId+"_"+ContentId).slideToggle();
	if($("#giveComment_"+parentContentId+"_"+ContentId).height()<10){
		$.ajax({
			type:"post",
			data:{AccountId:accountId,MicroContentId:ContentId,PageIndex:1,PageSize:5,BackPage:backPage},
			dataType:"html",
			url:"get_comments.jsp",
			success:function(data){
				$("#showComments_"+parentContentId+"_"+ContentId).html(data);
			},
			error:function(){
				alert("与服务器通信失败！");
			}
		});
	}else{
		$("#showComments_"+parentContentId+"_"+ContentId).html("")
	}
}
//头像的显示
function userHeadList(pageHeadConut){
	var page = 1;
	var headPage = 1;
	var i = pageHeadConut; //每版放pageHeadConut个图片
	var contentWidth=pageHeadConut*65;
	$("div.v_content").width(contentWidth);
	$("div.userContent").width(contentWidth+100);
	$("div.v_show").width(contentWidth+100);
	$("span.next").css("left",(contentWidth+35)+"px");
	var $parent = $("div.v_show");//根据当前点击元素获取到父元素
	var $v_show = $parent.find("div.v_content_list"); //寻找到“图片内容展示区域”
	var $v_content = $parent.find("div.v_content"); //寻找到“图片内容展示区域”外围的DIV元素
	var len = $v_show.find("li").length;
	for(var k=0;k<len;k++){
		if(!$v_show.find("li:eq("+k+")").find("img:eq(0)").hasClass("grayPic")){
			if(Math.ceil((k+1)/i) > 1){
				headPage = Math.ceil((k+1)/i);
			}
		}
	}
	var page_count = Math.ceil(len / i) ; //只要不是整数，就往大的方向取最小的整数
	
	
	if(headPage > page && headPage <= page_count){
		page = headPage;
		/*使用java代码控制当前页面的显示，故注释。*/
		//var left1=$v_show.css("left");
		//var left2=parseInt(-(page-1)*contentWidth);
		//alert(contentWidth);
		//$v_show.css("left",left2);
		//$v_show.animate({ left : '-='+(page-1)*contentWidth }, 0); //通过改变left值，达到每次换一个版面
	}
	if(page==1){
		$("span.prev").find("img").attr("src","../images/grayLeftBtn.png");
	}
	if(page_count==page){
		$("span.next").find("img").attr("src","../images/grayRightBtn.png");
	}
	//向后 按钮
	$("span.next").click(function(){ //绑定click事件
		if( !$v_show.is(":animated") ){ //判断“图片内容展示区域”是否正在处于动画
			if( page != page_count ){ //已经到最后一个版面了,如果再向后，必须跳转到第一个版面。
				$v_show.animate({ left : '-='+contentWidth }, "slow"); //通过改变left值，达到每次换一个版面
				page++;
			}
		}
		if(page_count>=1 && page == page_count ){
			$("span.next").find("img").attr("src","../images/grayRightBtn.png");
		}else{
			$("span.next").find("img").attr("src","../images/rightBtn.png");
		}
		if(page_count >= 1 && page == 1 ){
			$("span.prev").find("img").attr("src","../images/grayLeftBtn.png");
		}else{
			$("span.prev").find("img").attr("src","../images/leftBtn.png");
		}
	});

	//往前 按钮
	$("span.prev").click(function(){
		if( !$v_show.is(":animated") ){ //判断“图片内容展示区域”是否正在处于动画
			if( page != 1 ){ //已经到第一个版面了,如果再向前，必须跳转到最后一个版面。
				$v_show.animate({ left : '+='+contentWidth }, "slow");
				page--;
			}
		}
		if(page_count>=1 && page == 1 ){
			$("span.prev").find("img").attr("src","../images/grayLeftBtn.png");
		}else{
			$("span.prev").find("img").attr("src","../images/leftBtn.png");
		}
		if(page_count>=1 && page == page_count ){
			$("span.next").find("img").attr("src","../images/grayRightBtn.png");
		}else{
			$("span.next").find("img").attr("src","../images/rightBtn.png");
		}
	});
}

function saveComment(accountId,microContent,commentId){
	var commentContent = $("#giveRelay"+commentId).val();
	var valLength = forbidContentLength(commentContent);
	commentContent =transEnter(commentContent);
	if(valLength==140){
		alert("评论内容不能为空哦！");
		return false;
	}else if(valLength<0){
		alert('评论最多输入140个字，已超出'+ (-valLength) + '个字！');
		return false;
	}else{
		$.ajax({
			type:"post",
			data:{AccountId:accountId,MicroContentId:microContent,Content:commentContent},
			dataType:"text",
			url:"create_comment_dowith.jsp",
			success:function(data){
				if($.trim(data)==1){
					$("#giveComment_"+microContent).slideUp();
					setTimeout(function(){window.location.reload();},400);
				}else{
					alert("评论失败:" + $.trim(data));
				}
			},
			error:function(){
				alert("与服务器通信失败！");
				return false;
			}
		});
	}
}
function saveRelayComment(accountId,microContentId,commentId){
	var commentContent = $("#giveRelay"+commentId).val();
	var valLength = forbidContentLength(commentContent);
	commentContent=transEnter(commentContent);
	if(valLength==140){
		alert("评论内容不能为空哦！");
		return false;
	}else if(valLength<0){
		alert('评论最多输入140个字，已超出'+ (-valLength) + '个字！');
		return false;
	}else{
		$.ajax({
			type:"post",
			data:{AccountId:accountId,MicroContentId:microContentId,CommentId:commentId,Content:commentContent,isReplyComment:1},
			dataType:"text",
			url:"create_comment_dowith.jsp",
			success:function(data){
				if($.trim(data)==1){
					$("#giveComment_"+commentId).slideUp();
					setTimeout(function(){window.location.reload();},400);
				}else{
					alert("评论失败:" + $.trim(data));
					return false;
				}
			},
			error:function(){
				alert("与服务器通信失败！！");
			}
		});
	}
}
function showForward(_scmGroupId,_accountId,_microContentId){
	var CrashBoarderInfo = {
		id : 'createMicroblog', //id
		title : '转发微博',//弹出窗口名称
		maskable : true, //是否绘制遮布
		draggable : true,//是否可以拖动
		url : 'microcontent/forward_crashboard.jsp',//弹出框显示的内容的页面地址
		width : '574px',//宽度
		height : '270px',//高度
		params : {SCMGroupId:_scmGroupId,AccountId:_accountId,MicroContentId:_microContentId},//传给弹出框页面的参数
		callback : function(params){//回调函数
			$.ajax({
				type:"post",
					data:{AccountIds:params._AccountIds, MicroContentId:params._MicroContentId, Content:params._Content, SCMGroupId:params._SCMGroupId},
				dataType:"text",
				url:"forward_dowith.jsp",
				success:function(data){
					if($.trim(data)==1){
						if(params._hasWorkFlow == '1'){
							alert("转发微博已提交至审核人员，请您耐心等待审核结果。");
						}else{
							alert("转发成功！");
						}
						setTimeout(function(){},400);
					}else{
						alert("转发失败：" + $.trim(data));
					}
				},
				error:function(){
					alert("与服务器连接失败！");
				}
			});
		}
	};
	getActualTop().CrashBoard.show(CrashBoarderInfo);//弹出crashBoard框
}
function collecte(that,accountId,microContentId,operateType,SourcePage){
	$.ajax({
		type:"post",
		data:{AccountId:accountId,MicroContentId:microContentId,OperateType:operateType},
		dataType:"text",
		url:"add_favorites_dowith.jsp",
		success:function(data){
			if($.trim(data)==1){
				if(operateType==1){
					$(that).html("取消收藏");
				}else{
					$(that).html("收藏");
				}
			}else if($.trim(data)==0){
				if(operateType==1){
					alert("收藏失败！");
				}else{
					alert("取消收藏失败！");
				}
			}else{
				alert($.trim(data));
			}
			setTimeout(function(){window.location.href=SourcePage;},400);
		},
		error:function(){
			alert("与服务器连接失败！");
			setTimeout(function(){window.location.href=SourcePage;},400);
		}
	});
}
//大小图切换(全部页面和审核页面)
function allChangePic(that,name){
	var srcPath = $(that).attr("src");
	if(srcPath.length > 0){
		var width = srcPath.substring(srcPath.length-3);
		if(width==120){
			$(that).attr("src","../file/read_image.jsp?FileName="+name+"&ScaleWidth=440");
			$(that).removeClass("cursorBig");
			$(that).addClass("cursorSmall");
			$(that).parent().css("text-align","center");
		}else{
			$(that).attr("src","../file/read_image.jsp?FileName="+name+"&ScaleWidth=120");
			$(that).removeClass("cursorSmall");
			$(that).addClass("cursorBig");
			$(that).parent().css("text-align","left");
		}
	}else{
		alert("图片显示失败！");
	}
}
//大小图切换(全部页面和审核页面)
function allChangePic2(that,name){
	var isBigFlag = $(that).attr("isBig");
	if(isBigFlag == 1 || isBigFlag == 2 ){
		if(isBigFlag == 1){
			$(that).attr("isBig","2")
			$(that).removeClass("cursorBig");
			$(that).addClass("cursorSmall");
			autoResizeImg2(440,440,that);
			$(that).parent().css("text-align","center");
		}else{
			$(that).attr("isBig","1")
			autoResizeImg2(120,120,that);
			$(that).removeClass("cursorSmall");
			$(that).addClass("cursorBig");
			$(that).parent().css("text-align","left");
		}
	}else{
		alert("图片显示失败！");
	}
}
//图片等比例缩放
function autoResizeImg2(maxWidth,maxHeight,objImg){
	var img = new Image();
	img.onload = function(){
		var w = this.width;
		var h = this.height;
		var hRatio;//高度的比例
		var wRatio;//宽度的比例
		var Ratio = 1;//比例
		wRatio = maxWidth / w;
		hRatio = maxHeight / h;
		if (maxWidth ==0 && maxHeight==0){
			Ratio = 1;
		}else if (maxWidth==0){//
			if (hRatio<1) Ratio = hRatio;
		}else if (maxHeight==0){
			if (wRatio<1) Ratio = wRatio;
		}else if (wRatio<1 || hRatio<1){
			Ratio = (wRatio<=hRatio?wRatio:hRatio);
		}
		if (Ratio<1){
			w = w * Ratio;
			h = h * Ratio;
		}
		if(h>0){
			objImg.height = h;
			objImg.width = w;
		}else{
			objImg.height = maxHeight;
			objImg.width = maxWidth;
		}
	}
	img.src = objImg.src;
}
//图片等比例缩放
function autoResizeImg(maxWidth,maxHeight,objImg){
	var srcPath = $(objImg).attr("src");
	if(srcPath.length > 0){
		var width = srcPath.substring(srcPath.length-3);
		if(width==maxWidth){
			var img = new Image();
			img.onload = function(){
				var w = this.width;
				var h = this.height;
				var hRatio;//高度的比例
				var wRatio;//宽度的比例
				var Ratio = 1;//比例
				wRatio = maxWidth / w;
				hRatio = maxHeight / h;
				if (maxWidth ==0 && maxHeight==0){
					Ratio = 1;
				}else if (maxWidth==0){//
					if (hRatio<1) Ratio = hRatio;
				}else if (maxHeight==0){
					if (wRatio<1) Ratio = wRatio;
				}else if (wRatio<1 || hRatio<1){
					Ratio = (wRatio<=hRatio?wRatio:hRatio);
				}
				if (Ratio<1){
					w = w * Ratio;
					h = h * Ratio;
				}
				if(h>0){
					objImg.height = h;
					objImg.width = w;
				}else{
					objImg.height = maxHeight;
					objImg.width = maxWidth;
				}
			}
			img.src = objImg.src;
		}else{
			$(objImg).removeAttr("width");
			$(objImg).removeAttr("height");
		}
	}
}
//回复的回复框的显示
function showRelayComment(commentId){
	$("#giveComment_"+commentId).slideToggle();
}
//回复的回复保存
function relayComment(accountId,commentId,microContentId){
	var commentContent = $("#giveRelay"+commentId).val();
	var valLength = forbidContentLength(commentContent);
	commentContent=transEnter(commentContent);
	if(valLength==140){
		alert("评论内容不能为空哦！");
		return false;
	}else if(valLength<0){
		alert('评论最多输入140个字，已超出'+ (-valLength) + '个字！');
		return false;
	}else{
		$.ajax({
			type:"post",
			data:{AccountId:accountId,MicroContentId:microContentId,CommentId:commentId,Content:commentContent,isReplyComment:1},
			dataType:"text",
			url:"create_comment_dowith.jsp",
			success:function(data){
				if($.trim(data)==1){
					$("#giveComment_"+commentId).slideUp();
					setTimeout(function(){window.location.reload();},400);
				}else{
					alert("评论失败:" + $.trim(data));
					return false;
				}
			},
			error:function(){
				alert("与服务器通信失败！");
			}
		});
	}
}
//判断用户输入的是否全部是回车和空格
function isEnterSpace(sContent){
	if(sContent.length > 0){
		sContent=sContent.replace(/\r\n/g,"");
		sContent=sContent.replace(/\n/g,"");
		sContent = sContent.replace(/\s{1,}/g,"");
		if(sContent.length == 0){
			return true;
		}
	}
	return false;
}
//微博输入字符个数判断
function forbidLength(){
	var content=$("#myExpress").val(); 
	if(isEnterSpace(content)){
		content="";
	}
	var len = forbidContentLength(content)
	if(len < 0){
		info = "" + (-len);
		$("#showNum").parent().html('已超出<em id="showNum" class="wcmSelectFont"><font color=\"red\"> '+ info + '</font></em>字');
		return len;
	}
	$("#showNum").parent().html('还可以输入<em id="showNum" class="wcmSelectFont">'+ info + '</em>字');
	return len;
}
//输入字符个数判断
function forbidContentLength(content){
	if(isEnterSpace(content)){
		content="";
	}
	if(content == '' || content == null || typeof(content) == "undefined"){
		return 140;
	}
	var urlMatch = "(http[s]{0,1}|ftp)://[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z0-9]{1,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?";
	var reg=new RegExp(urlMatch,"mg");//创建正则表达式样式.
	content = content.replace(reg,"oooooooooooooooooooooooooo");//网址都是用13个字代替计数.
	var iLen=0;
	for (var i = 0; i < content.length; i++) {
		iLen += /[^\x00-\xff]/g.test(content.charAt(i)) ? 1 : 0.5;
	};
	iLen=Math.ceil(iLen);
	var len = 140-iLen;//记录剩余字符串的长度
	var info = "" + len;
	if(len < 0){
		info = "" + (-len);
		return len;
	}
	return len;
}
function transEnter(content){
	var sContent = content;
	try{
		sContent=sContent.replace(/\r\n/g," ");
		sContent=sContent.replace(/\n/g," ");
		sContent = sContent.replace(/\s{2,}/g," ");//\s 匹配任意的空白符（空格，TAB，换行，中文全角空格）  /g 表明可以进行全局匹配
	}catch(e) {
		alert(e.message);
	}
	return sContent;
}
//元素获取焦点
function setFocus(id){
	var obj = $("#"+id)[0];
	if(obj.setSelectionRange){
	   setTimeout(function(){
		obj.setSelectionRange(0,0);
		obj.focus();
	},100);
	}else{
	  if(obj.createTextRange){
		var range=obj.createTextRange();
		range.collapse(true);
		range.moveEnd("character",0);
		range.moveStart("character",0);
		range.select();
	  }
	  try{obj.focus();}catch(e){}
	}
}
