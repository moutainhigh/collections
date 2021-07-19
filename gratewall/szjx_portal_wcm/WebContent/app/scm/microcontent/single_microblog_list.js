//回复框的显示和隐藏
function operateCommentOn(num){
  $("#commentOn"+num).slideToggle();
}
//流转情况显示和隐藏
function showTable(num){
	for(var i=1;i<6;i++){
		if(num==i){
			$("#divTable"+i).slideToggle();
		}
	}
}
//全部
function getAllAccountMicroContext(num,pageSize,pageIndex){
	window.open("all_microblog_list.jsp?PageSize="+pageSize+"&PageIndex="+pageIndex+"&SCMGroupId="+num,"_self");
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
function deleteOperate(that,elementId,accountId,microContent,pageIndex,pageSize){
	if(confirm("确认要删除吗？")){
		var top = getTop(that);
		var left = getLeft(that);
		ProcessBar.init(0,top-60,left-250);
		ProcessBar.start();
		$.ajax({
			type:"post",
			data:{AccountId:accountId,MicroContentId:microContent},
			dataType:"text",
			url:"microblog_delete_dowith.jsp",
			success:function(data){
				ProcessBar.exit();
				if($.trim(data)==1){
					$("#"+elementId).slideUp();
				}else{
					alert($.trim(data));
				}
				setTimeout(function(){
					window.location.href = "single_microblog_list.jsp?PageIndex="+pageIndex+"&PageSize="+pageSize;
				},400);
			},
			error:function(){
				ProcessBar.exit();
				alert("请求删除失败！");
				window.location.reload();
			}
		});
	}
}