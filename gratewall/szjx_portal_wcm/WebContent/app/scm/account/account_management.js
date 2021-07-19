$(function(){
	$("html").click(function(){
		$('#moreAccountGroups',parent.document).hide();//隐藏
	});
	setInterval("setAccountMgrHeight()",100);
});
$(function(){ 
	//下拉框绑定事件
	$('.account_to_group').click(function(e) {
		$(this).parent().find(".account_to_group_list").show();
	});

	$(document).click(function(e){
		var e=e?e:window.event;
		var tar = e.srcElement||e.target;
		if($(".account_to_group_list").has(tar).html()==null && $(tar).attr("id")!="addaccounttogroup"){
			$(".account_to_group_list").hide();
		}
		if($(tar).attr("id")=="addaccounttogroup"){
			$(".account_to_group_list").hide();
			$(tar).parent().find(".account_to_group_list").show();
		}
	});
});

//解除绑定
function relieveBind(_SCMGroupId,_AccountId){
	if(confirm("确定要解除绑定吗？")){
	$.ajax({
			type:"post",
			data:{SCMGroupId:_SCMGroupId,AccountId:_AccountId},
			dataType:"text",
			url:"account_bind_dowith.jsp",
			success:function(data){
				if($.trim(data)==1){					
					//alert("解除绑定成功！");	
					window.location.reload();					
				}else{
					alert("解除绑定失败！");
				}
			},
			error:function(){						
				alert("解除绑定失败！");
			}
	});
	}
}

function addorremoveaccount(_elCheckbox){
	if(_elCheckbox.checked){
		$.ajax({
			type:"post",
			data:{SCMGroupId:_elCheckbox.value,AccountId:_elCheckbox.name,flag:1},
			dataType:"text",
			url:"add_account_to_group_dowith.jsp",
			success:function(data){
				if($.trim(data)==1){							
					//alert("创建成功！");
					//window.location.reload();
				}else{
					alert("添加失败！");
				}
			},
			error:function(){						
				alert("添加失败！");
			}
		});
	}else{
		$.ajax({
			type:"post",
			data:{SCMGroupId:_elCheckbox.value,AccountId:_elCheckbox.name,flag:0},
			dataType:"text",
			url:"add_account_to_group_dowith.jsp",
			success:function(data){
				if($.trim(data)==1){							
					//alert("创建成功！");
					//window.location.reload();
				}else{
					alert("移除失败！");
				}
			},
			error:function(){						
				alert("移除失败！");
			}
		});
	}
}