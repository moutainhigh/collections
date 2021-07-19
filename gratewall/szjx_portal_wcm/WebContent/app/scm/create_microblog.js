function userPicList(flag){
	setInterval("forbidLength()",100);
	var page = 1;
	var i = 8; //每版放8个图片
	var rightPic = "";
	var leftPic = "";
	var grayRightPic = "";
	var grayLeftPic = "";
	if(flag==1){
		rightPic = "images/create_right_btn.png";
		leftPic = "images/create_left_btn.png";
		grayRightPic = "images/gray_right.png";
		grayLeftPic = "images/gray_left.png";
	}else{
		rightPic = "../images/create_right_btn.png";
		leftPic = "../images/create_left_btn.png";
		grayRightPic = "../images/gray_right.png";
		grayLeftPic = "../images/gray_left.png";
	}
	var page_count = Math.ceil($("#headUlStyle").find("li").length/8);
	$("span.createPrev").find("img").attr("src",grayLeftPic);
	if(page_count == 1 || page_count == 0){
		$("span.createNext").find("img").attr("src",grayRightPic);
		return false;
	}
	//向后 按钮
	$("span.createNext").click(function(){ //绑定click事件
		var $parent = $(this).parents("div.v_show");//根据当前点击元素获取到父元素
		var $v_show = $parent.find("div.v_content_list"); //寻找到“图片内容展示区域”
		var $v_content = $parent.find("div.v_content"); //寻找到“图片内容展示区域”外围的DIV元素
		var v_width = $v_content.width();
		if( !$v_show.is(":animated") ){ //判断“图片内容展示区域”是否正在处于动画
			if( page != page_count ){
				$v_show.animate({ left : '-='+v_width }, "slow"); //通过改变left值，达到每次换一个版面
					page++;
			}
		}
		if(page_count>1 && page == page_count ){
			$("span.createNext").find("img").attr("src",grayRightPic);
		}else{
			$("span.createNext").find("img").attr("src",rightPic);
		}
		if(page_count > 1 && page == 1 ){
			$("span.createPrev").find("img").attr("src",grayLeftPic);
		}else{
			$("span.createPrev").find("img").attr("src",leftPic);
		}
	});
	//往前 按钮
	$("span.createPrev").click(function(){
		var $parent = $(this).parents("div.v_show");//根据当前点击元素获取到父元素
		var $v_show = $parent.find("div.v_content_list"); //寻找到“图片内容展示区域”
		var $v_content = $parent.find("div.v_content"); //寻找到“图片内容展示区域”外围的DIV元素
		var v_width = $v_content.width();
		if( !$v_show.is(":animated") ){ //判断“图片内容展示区域”是否正在处于动画
			if( page != 1 ){
				$v_show.animate({ left : '+='+v_width }, "slow");
				page--;
			}
		}
		if(page_count>1 && page == 1 ){
			$("span.createPrev").find("img").attr("src",grayLeftPic);
		}else{
			$("span.createPrev").find("img").attr("src",leftPic);
		}
		if(page_count>1 && page == page_count ){
			$("span.createNext").find("img").attr("src",grayRightPic);
		}else{
			$("span.createNext").find("img").attr("src",rightPic);
		}
	});
}
//用户图片灰度切换
function createUserChangeState(num){
	var headerNum = $("#headUlStyle").find("li").length;
	for(var i=1;i<=headerNum;i++){
		if(num==i){
			if($("#user_h"+i).hasClass("grayPic")){
				$("#user_h"+i).removeClass('grayPic');
				$("#user_l"+i).removeClass('grayPic');
				$("#user_t"+i).removeClass('hidden');
			}else{
				$("#user_h"+i).addClass('grayPic');
				$("#user_l"+i).addClass('grayPic');
				$("#user_t"+i).addClass('hidden');
			}
		}
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
//输入字符个数判断
function forbidLength(){
	var content=$("#myExpress").val();
	if(isEnterSpace(content)){
		content="";
	}
	if(content == '' || content == null || typeof(content) == "undefined"){
		$("#showNum").html(140);
		docsource = "SCM";
		return false;
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
		$("#showNum").parent().html('已超出<em id="showNum" class="wcmSelectFont"><font color=\"red\">'+ info + '</font></em>字');
		return len;
	}
	$("#showNum").parent().html('还可以输入<em id="showNum" class="wcmSelectFont">'+ info + '</em>字');
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
function showTip(){
	$("#checkMessage").show();
}
function hideCheck(){
	$("#ejectDiv").hide();
	$("#cover").removeClass("showCheck");
}

// 修改图片大小
function resizeIfNeed(_loadImg){
	if(!_loadImg) return;
	var maxHeight = 112;
	var maxWidth = 172;
	var loadImg = new Image();
	loadImg.src = _loadImg.src;
	var height = loadImg.height, width = loadImg.width;
	if(height > maxHeight || width > maxWidth){	
		if((height > width) || (height == width)){
			width = maxHeight * width/height;
			height = maxHeight;
		}else {
			height = maxWidth * height/width;
			width = maxWidth;
		}
	}
	if(height == 0 || width == 0){
		height= maxHeight;
		width = maxWidth;
	}
	_loadImg.width = width;
	_loadImg.height = height;
}

function deletePicture(){
	$("#picture").val("");
	$(window.document).find("#imgContainer").css("display","none");
	$(window.document).find(".deletePicture").css("display","none");
	$(window.document).find("#img_ViewThumb").css("src","");
	top.window.CrashBoard.get("createMicroblog").onResize("574px","290px");
}
//为wcm页面的删除图片方法
function deletePicture1(){
	$("#picture").val("");
	$("#img_ViewThumb").attr("src","images/no_pic.png");
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