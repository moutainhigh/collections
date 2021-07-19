function changeComment(comment,url){
	if(comment=="#showComment"){
		$("#showComment").removeClass("notSelected");
		$("#showComment").addClass("currentSelected");
		$("#sentComment").removeClass("currentSelected");
		$("#sentComment").addClass("notSelected");
		window.open(url,"_self");
	}else{
		$("#showComment").removeClass("currentSelected");
		$("#showComment").addClass("notSelected");
		$("#sentComment").removeClass("notSelected");
		$("#sentComment").addClass("currentSelected");
		window.open(url,"_self");
	}
}

function deleteComment(accountId,commentId){
	if(confirm("确认要删除吗？")){
		$.ajax({
			type:"post",
			data:{AccountId:accountId,CommentId:commentId},
			dataType:"text",
			url:"comment_delete_dowith.jsp",
			success:function(data){
				if($.trim(data)==1){
					//$("#"+elementId).slideUp();
					setTimeout(function(){window.location.reload();},400);
				}else{
					alert("删除失败！");
				}
			},
			error:function(){
				alert("失败！");
			}
		});
	}
}