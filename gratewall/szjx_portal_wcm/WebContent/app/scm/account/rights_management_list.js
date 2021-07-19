$(function(){
	$("html").click(function(){
		$('#moreGroups',parent.document).hide();//隐藏
	});
	setInterval("setAccountMgrHeight()",100);
});
function addUserToGroup(_SCMGroupId,_UserIds){
	var CrashBoarderInfo = {
		id : 'addUsertoGroup', //id
		title : '添加用户',//弹出窗口名称
		maskable : true, //是否绘制遮布
		draggable : true,//是否可以拖动
		url : 'account/user_add_dialog.jsp',//弹出框显示的内容的页面地址
		width : '920px',//宽度
		height : '450px',//高度
		params : {SCMGroupId:_SCMGroupId,UserIds:_UserIds},//传给弹出框页面的参数
		callback : function(params){//回调函数		
			$.ajax({
					type:"post",
					data:{SCMGroupId:params.GroupId,UserIds:params.UserIds},
					dataType:"text",
					url:"user_add_dowith.jsp",
					success:function(data){
						if($.trim(data)==1){
							alert("添加用户成功！");
							window.location.reload();
						}else{
							alert("添加用户失败！");
						}
					},
					error:function(){
						alert("添加用户失败！");
					}
			});
		}
	};
	getActualTop().CrashBoard.show(CrashBoarderInfo);//弹出crashBoard框
}

function removeUser(userId,SCMGroupId,userAmount){
	// 如果用户删除自己，则给出提示信息
	if(userId == nLoginUserId){
		if(!confirm("删除自己后，将不能再维护分组中的微博帐号，确定要删除自己吗？")){
			return;
		}
	}else{
		if(!confirm("确定要删除当前维护人员吗？")){
			return;
		}
	}

	$.ajax({
		type:"post",
		data:{UserId:userId,SCMGroupId:SCMGroupId},
		dataType:"text",
		url:"user_delete_dowith.jsp",
		success:function(data){
			if($.trim(data)==1){
				window.location.reload();
			}else{
				alert("删除失败！");
			}
		},
		error:function(){
			alert("删除失败！");
		}
	});
}

//删除分组
function deleteThisGroup(_sSCMGroupIds){
	
	if(confirm("确定要删除分组吗？")){
		$.ajax({
			type:"post",
			data:{SCMGroupIds:_sSCMGroupIds},
			dataType:"text",
			url:"query_groupaccount_bygroups.jsp",
			success:function(data){
				var backMessage = $.trim(data);
				if(backMessage == 1){
					alert("未找到ID为【"+_sSCMGroupIds+"】的分组");
					return;
				}else if(backMessage != "" && backMessage != 0){
					alert(backMessage);
					return false;
				}else{
					$.ajax({
						type:"post",
						data:{ObjectIds:_sSCMGroupIds},
						dataType:"text",
						url:"delete_group_dowith.jsp",
						success:function(data){
							
							if($.trim(data)==1){							
								alert("删除成功！");
								window.parent.location.href = "rights_management.jsp";
							}else{
								alert("删除失败！");
							}
						},
						error:function(){						
							alert("删除失败！");
						}
					});
				}
			},
			error:function(){
				alert("请求失败！");
			}
		});	
		
	}
}
function showCreate(){
	var CrashBoarderInfo = {
		id : 'createGroup', //id
		title : '创建分组',//弹出窗口名称
		maskable : true, //是否绘制遮布
		draggable : true,//是否可以拖动
		url : 'account/create_group_dialog.jsp',//弹出框显示的内容的页面地址
		width : '315px',//宽度
		height : '74px',//高度
		params : {SCMGroupId:0},//传给弹出框页面的参数
		callback : function(params){//回调函数
		
			$.ajax({
				type:"post",
				data:{ObjectId:params.GroupId,GroupName:params.GroupName},
				dataType:"text",
				url:"create_group_dowith.jsp",
				success:function(data){
					var objectId = $.trim(data);
					if(objectId > 0){
						alert("创建分组成功！");
						window.location.href = "rights_management.jsp?SCMGroupId="+objectId;
					}else{
						alert("创建分组失败！");
					}
				},
				error:function(){
					alert("创建分组失败！");
				}
			});
				
		}
	};
	getActualTop().CrashBoard.show(CrashBoarderInfo);//弹出crashBoard框
}
function showModify(_nSCMGroupId,_sGroupName){	
	var CrashBoarderInfo = {
		id : 'modifyGroup', //id
		title : '修改分组',//弹出窗口名称
		maskable : true, //是否绘制遮布
		draggable : true,//是否可以拖动
		url : 'account/create_group_dialog.jsp',//弹出框显示的内容的页面地址
		width : '315px',//宽度
		height : '74px',//高度
		params : {SCMGroupId:_nSCMGroupId,SCMGroupName:_sGroupName},//传给弹出框页面的参数
		callback : function(params){//回调函数
			$.ajax({
				type:"post",
				data:{ObjectId:params.GroupId,GroupName:params.GroupName},
				dataType:"text",
				url:"create_group_dowith.jsp",
				success:function(data){
					var objectId = $.trim(data);
					if(objectId > 1){
						window.parent.location.reload();
					}else{
						alert("分组名修改失败！\n错误信息："+objectId);
						return false;
					}
				},
				error:function(){
					alert("分组名修改失败！ajax请求异常");
				}
			});
		}
	};
	getActualTop().CrashBoard.show(CrashBoarderInfo);//弹出crashBoard框
}