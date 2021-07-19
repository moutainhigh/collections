$(function(){
	//初始化。
	//定义组件绘制的top窗口对象
	window.__actualTop=window;
	for(var i=1;;i++){
		if($(".btn-slide"+i).html()==null){
			break;
		}

		//计算有多少个li标签，通过里标签的个数计算包含这些li标签的div的高度
		var liNumAll = $("#panel"+i).find("li").length;
		var liNumNone = $("#panel"+i).find("li[style]").length;
		$("#panel"+i).height((liNumAll-liNumNone)*32+4);
		
		//循环绑定click事件
		$("#prefix" + i).bind("click", {i: i}, clickHandler);//循环添加监听事件
		$("#icon" + i).bind("click", {i: i}, clickHandler);//循环添加监听事件
		$(".btn-slide" + i).bind("click", {i: i}, clickHandler);//循环添加监听事件
	}
	setInterval("clearOneAjax()",60*1000);//自动更改全局变量m_oneAjax的值
});
//树结构的收缩事件
function clickHandler(event) {
	var i = event.data.i;
	$("#panel"+i).slideToggle();//当前节点下的子节点收缩显示
	otherClearBg("prefix"+i);//清除节点的背景色
	otherPutAway(i);//其他收起
	if($("#prefix"+i).attr("src")!="images/iconb.png"){//改变图标方向
		$("#prefix"+i).attr("src","images/iconb.png");
	}else{
		$("#prefix"+i).attr("src","images/icon.png");
	}
	$(".btn-slide"+i).parent().addClass("liBackground");//设置背景颜色
	$(".btn-slide"+i).css("color","#fff");//设置背景颜色
	return false;
}
//清除父节点背景颜色
function otherClearBg(prefix){
	for(var i=1;;i++){
		if($(".btn-slide"+i).parent().html()==null){
			break;
		}
		$(".btn-slide"+i).parent().removeClass("liBackground");
		$(".btn-slide"+i).css("color","#0E70B1");
		if(prefix!="prefix"+i){
			$("#prefix"+i).attr("src","images/icon.png");
		}
	}
}
//收起
function otherPutAway(idName){
	for(var i=1;;i++){
		if($("#panel"+i).html()==null){
			break;
		}
		if(idName!=i && $("#panel"+i).css("display")!="none"){
			$("#panel"+i).slideUp();
		}
	}
}
//清除背景颜色
function clearLi(){
	for(var i=1;;i++){
		if($("#li"+i).html()==null){
			break;
		}
		$("#li"+i).css("background","");
	}
}
//改变当前li标签的背景状态
function changeState(id){
	clearLi();
	$(id).attr("style","background:#fff");
}
function hiddenShortMessage(){
	$("#shortMessage").hide();
	$("#headerRight .showShortMessage").removeClass("overMessage");
}
function showOnlyShortMessage(){
	showCenterLeft();
	$("#shortMessage").show();
	$("#autoMessage").hide();
	$("#headerRight .showShortMessage").addClass("overMessage");
}
//显示提示信息
var m_oneAjax = 2;//页面不刷新只提交一次AJAX请求
function clearOneAjax(){
	m_oneAjax=1;
}
//var m_one = 1;//记录鼠标是否为第一次滑动到消息上。
/*var showMassageCount = 1;*/
var messageContent = "";
function showCenterLeft(){
	var patt = new RegExp('没有微博未读消息');//要查找的字符串
	if(patt.test($("#shortMessage").html())){//字符串存在返回true否则返回false
		$("#shortMessage").css("text-align","center");
	}else{
		$("#shortMessage").css("text-align","left");
	}
}
function showShortMessage(){
	showCenterLeft();
	$("#shortMessage").show();
	$("#autoMessage").hide();
	$("#headerRight .showShortMessage").addClass("overMessage");
	if(m_oneAjax == 1 || messageContent == ""){
		$("#shortMessage").html("请稍等，正在为您加载消息.....");
		m_oneAjax = 2;
		$.ajax({
			type:"post",
			data:{MessageType:2},
			dataType:"html",
			url:"get_message.jsp",
			success:function(data){
				var patt = new RegExp('没有微博未读消息！');//要查找的字符串
				var pattError =  new RegExp("加载未读消息失败");
				if(patt.test(data)){//字符串存在返回true否则返回false
					$("#shortMessage").html("<div style='margin:0 auto;text-align:center;'>您暂时没有微博未读消息！</div>")
				}else{
					$("#shortMessage").html(data);
					messageContent = data;
				}
			},
			error:function(){}
		});
	}else{
		messageContent=messageContent.replace("showAutoMessage","showRepeatMessage");
		$("#shortMessage").html(messageContent);
	}
}
function showAutoMessage(){
	$("#autoMessage").html('<span class="closeAutoMessage" onclick="hiddenMessage()">×</span>');
	$("#autoMessage").append("<div id='waiting' style='text-align:center'>请稍等，正在为您加载消息.....</div>");
	$("#autoMessage").show();
	$.ajax({
		type:"post",
		data:{MessageType:1},
		dataType:"html",
		url:"get_message.jsp",
		success:function(data){
			var patt = new RegExp('没有微博未读消息');//要查找的字符串
			var pattError =  new RegExp("加载未读消息失败");
			if(patt.test(data)){//字符串存在返回true否则返回false
				$("#waiting").html("您暂时没有微博未读消息！");
				messageContent = "您暂时没有微博未读消息！";
				setTimeout(function(){
					$("#autoMessage").hide();
				},5000);
			}else{
				$("#autoMessage").html('<span class="closeAutoMessage" onclick="hiddenMessage()">×</span>');
				$("#autoMessage").show();
				messageContent = data;
				$("#autoMessage").append(data);
			}
		},
		error:function(){}
	});
}
function showRepeatMessage(){
	$.ajax({
		type:"post",
		data:{MessageType:2},
		dataType:"html",
		url:"get_message.jsp",
		success:function(data){
			var patt = new RegExp('没有微博未读消息');//要查找的字符串
			var pattError =  new RegExp("加载未读消息失败");
			if(patt.test(data)){//字符串存在返回true否则返回false
				messageContent="<div style='margin:0 auto;text-align:center;'>您暂时没有微博未读消息！</div>";
				$("#shortMessage").html(messageContent);

			}else{
				$("#shortMessage").html(data);
				messageContent = data;
			}
		},
		error:function(){}
	});
}
var showFlag = 1;
function showShortMessageShow(){
	$("#shortMessage").show();
	$("#autoMessage").hide();
	$("#headerRight .showShortMessage").addClass("overMessage");
	if(showFlag==1){
		showShortMessage();
	}
}
//点击推出信息的链接时执行的操作
function toCommentPage(num,groupId,accountId){
	if(num==1){
		if($("#prefix6").attr("src")!="images/iconb.png"){
			$("#prefix6").click();
		}
		changeState('#li20');
		window.open("audit/microblog_checking_list.jsp","frame_content");
	}else if(num==2){
		if($("#prefix1").attr("src")!="images/iconb.png"){
			$("#prefix1").click();
		}
		changeState('#li3');
		window.open("microcontent/show_comment_list.jsp?SCMGroupId="+groupId+"&AccountId="+accountId,"frame_content");
	}else if(num==3){
		if($("#prefix1").attr("src")!="images/iconb.png"){
			$("#prefix1").click();
		}
		changeState('#li5');
		window.open("microcontent/show_at_list.jsp?SCMGroupId="+groupId+"&AccountId="+accountId,"frame_content");
	}
	
	return false;
}
//隐藏消息
function hiddenMessage(){
	$("#autoMessage").hide();
}
function closeBrowser(){ 
	window.opener=null;
	window.open("","_self");
	window.close();
}