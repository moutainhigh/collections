window.onload = function(){
	$("#groupSelect").sSelect();//模拟select
	//改变宽度，是分组与文字不会错位
	$(".dropselectbox").find("h4").css("width","71px");
	$(".dropselectbox").find("ul").css("width","91px");
	//绑定鼠标事件，显示删除
	$(".messageContent").bind("mouseover",function(){
		if($(this).find(".deleteStyle").length>0){
			$(this).find(".deleteStyle").show();
		}
	});
	//绑定鼠标事件，隐藏删除
	$(".messageContent").bind("mouseout",function(){
		if($(this).find(".deleteStyle").length>0){
			$(this).find(".deleteStyle").hide();
		}
	});
	//当分组改变时自动提交
	$("#groupSelect").change(function(){
		var accountId = $("#groupSelect").find("option:selected").attr("account");
		$("#accountId").val(accountId);
		$("#scmGroupForm").submit();
	});
	$(document).click(function (event){
		var e = event || window.event;
		var elem = e.srcElement||e.target;
		var parentCount = $(elem).parents('.divTable').length;
		var parentCount2 = $(elem).parents('.divTable2').length;
		if(parentCount==0 && parentCount2==0){
			if($(elem).attr("src")!="../images/duibi.png" && $(elem).attr("src")!="../images/check_state.png"){
				$('.divTable').slideUp();
				$('.divTable2').slideUp();
			}
		}
	});
};
//点击回复的操作
function operateCommentOn(num){
	$("#commentOn"+num).slideToggle();//回复框的显示或隐藏
}
function showRepostNum(num,scmcontentid,scmgroupid){
	// 改成第一次点击的时候发送请求，后面除非页面经过刷新，否则就不再发送请求了
	$(".divTable").slideUp();
	$(".divTable2").slideUp();
	if($("#divTable"+num)[0].style.display == ''){
		$.ajax({
			type:"post",
			data:{Num:num,SCMGroupId:scmgroupid,SCMMicroContentId:scmcontentid},
			dataType:"html",
			url:"get_repost_dowith.jsp",
			success:function(data){
				$("#divTable"+num).find("div:first").html(data);
				$("#divTable"+num).slideDown();
			},
			error:function(){
				alert("失败！");
			}
		});
		return false;
	}
	if($("#divTable"+num).is(":hidden")){
		$("#divTable"+num).slideDown();
	}else{
		$("#divTable"+num).slideUp();
	}
}
function showFlowContent(num,scmcontentid){
	$(".divTable").slideUp();
	$(".divTable2").slideUp();
	if($("#divTable"+num)[0].style.display == ''){
		$.ajax({
			type:"post",
			data:{SCMMicroContentId:scmcontentid},
			dataType:"html",
			url:"get_flowcontent_dowith.jsp",
			success:function(data){
				$("#divTable"+num).find("div:first").html(data);
				$("#divTable"+num).slideDown();
			},
			error:function(){
				alert("失败！");
			}
		});
		return false;
	}
	if($("#divTable"+num).is(":hidden")){
		$("#divTable"+num).slideDown();
	}else{
		$("#divTable"+num).slideUp();
	}
}
function userChangeStatus(domId,accountId,scmMicroContentId){
	$.ajax({
		type:"post",
		data:{AccountId:accountId,SCMMicroContentId:scmMicroContentId},
		dataType:"text",
		url:"repost_microcontent_dowith.jsp",
		success:function(data){
			if(data.indexOf("Reposted") >= 0){
				var obj=eval("("+data+")");//转换为json对象
				var json = obj[0];
				if($.trim(json["Reposted"])==1){
					$("#"+domId).find("img").removeClass("grayPic");
					$("#"+domId).attr("onclick","");
					$("#"+domId).find("img").attr("title","");
					$("#"+domId).parent().find("div:eq(1)").html(json["RepostCount"]);
					$("#"+domId).parent().find("div:eq(2)").html(json["CommentCount"]);
					alert("补发成功！");
				}
			}else{
				alert("补发失败:" + $.trim(data));
			}
		},
		error:function(){
			alert("失败！");
		}
	});
}
//获取元素轴坐标
function getTop(e){
	var offset = e.offsetTop;
	if (e.offsetParent != null)
	offset += getTop(e.offsetParent);
	return offset;
}
//获取元素横坐标
function getLeft(e){
	var offset = e.offsetLeft;
	if (e.offsetParent != null)
	offset += getLeft(e.offsetParent);
	return offset;
}
//删除微博
function deleteMicroBlog(that,scmBlogId,pageIndex,pageSize,isSearch,searchWords){
	$(that).parent().parent().parent().unbind("mouseout");
	$(that).show();
	var isConfirmed = confirm("您即将删除SCM及已发布微博平台上的该条微博数据。您确认要删除该条微博吗?");
	if(!isConfirmed){
		$(that).parent().parent().parent().bind("mouseout",function(){
			$(this).find(".deleteStyle").hide();
		});
		return;
	}
	var top = getTop(that);
	var left = getLeft(that);
	ProcessBar.init(0,top-50,left-366);
	ProcessBar.start();
	$.ajax({
		type:"post",
		data:{ObjectIds:scmBlogId},
		dataType:"text",
		url:"allmicroblog_delete_dowith.jsp",
		success:function(data){
			ProcessBar.exit();
			if($.trim(data)==1){
				$(that).parent().parent().parent().slideUp();
			}else{
				alert("删除失败！");
			}
			setTimeout(function(){
				if(isSearch){
					window.location.href = "all_microblog_list.jsp?PageIndex="+pageIndex+"&PageSize="+pageSize
				}else{
					window.location.href = "all_microblog_list.jsp?PageIndex="+pageIndex+"&PageSize="+pageSize+"&Content="+searchWords;
				}
			},400);
		},
		error:function(){
			ProcessBar.exit();
			alert("删除失败！");
			window.location.href = "all_microblog_list.jsp?PageIndex="+pageIndex+"&PageSize="+pageSize;
		}
	});
}
//显示搜索内容
 function searchHtml(){
	 window.location.href="all_microblog_list.jsp?Content="+$("#searchWords").val();
 }
function entersearch(){
	var event = window.event || arguments.callee.caller.arguments[0];
	if (event.keyCode == 13){
		searchHtml();
	}
} 
//删除
function deleteMicroMessage(nCurrContentId){
	if(confirm("该微博内容您还没有给出审核结果，您确定要删除该微博记录与审核记录吗？")){
		$.ajax({
			type:"post",
			data:{CurrContentId:nCurrContentId},
			dataType:"text",
			url:"../audit/deleteWorkFlow.jsp",
			success:function(){
				window.location.reload(); 
				/**/
			},
			error:function(){
				alert("审核工作流流转失败！");
			}
	});
	}
}