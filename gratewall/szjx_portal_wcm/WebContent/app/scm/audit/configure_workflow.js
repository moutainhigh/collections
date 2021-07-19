//内容收缩，并切换图片
function slideUpDown(that,upImgPath,downImgPath,id){
	if($(that).attr('src')!=downImgPath){
		$("#"+id).slideToggle();
		$(that).attr('src',downImgPath);
	}else{
		$(that).attr('src',upImgPath);
		$("#"+id).slideToggle();
	}
}

function changeDefaultText(that){
	$(that).parent().next("div").html($(that).html());
}

//设置工作流
//type=1：设置默认工作流
//type=2: 设置分组工作流
function setWorkFlow(type,workflowId,groupId,pageindex,pagesize){
	pageindex = new Number(pageindex);
	pagesize = new Number(pagesize);
	//type ==1 && workflowId == 0 ：取消默认工作流
	if(type ==1 && workflowId == 0){
		var isConfirmed = confirm("您将取消默认工作流设置，即系统默认的审核流程。取消后，除定制工作流的分组外，系统将不再对发布的微博进行审核。您确认取消系统默认工作流吗?");
		if(!isConfirmed){
			window.location.href = "configure_workflow.jsp?PageIndex="+pageindex+"&PageSize="+pagesize;
			return;
		}
	}

	//type == 2：修改分组工作流配置
	if(type == 2){
		if(workflowId == -1){
			var isConfirmed = confirm("您将分组的工作流配置定制为不走工作流，此设置将忽略系统默认审核设置！您确认要进行此操作吗?");
			if(!isConfirmed){
				window.location.href = "configure_workflow.jsp?PageIndex="+pageindex+"&PageSize="+pagesize;
				return;
			}
		}else{
			var isConfirmed = confirm("您将修改分组的工作流设置，该分组已流转的微博，将继续使用以前的设置完成审核。您确认要继续修改吗?");
			if(!isConfirmed){
				window.location.href = "configure_workflow.jsp?PageIndex="+pageindex+"&PageSize="+pagesize;
				return;
			}
		}
	}
	$.ajax({
		type:"post",
		data:{Type:type,WorkFlowId:workflowId,SCMGroupId:groupId},
		dataType:"text",
		url:"setWorkFlow.jsp",
		success:function(){
			alert("设置审核工作流成功！");
			window.location.href = "configure_workflow.jsp?PageIndex="+pageindex+"&PageSize="+pagesize;
		},
		error:function(){
			alert("设置审核工作流失败！");
			window.location.href = "configure_workflow.jsp?PageIndex="+pageindex+"&PageSize="+pagesize;
		}
	});
}

// 解决工作流列表中，修改工作流时有闪现效果
function setWorkFlow1(type,that,groupId,pageindex,pagesize,originWorkflowId){
	pageindex = new Number(pageindex);
	pagesize = new Number(pagesize);
	workflowId = $(that).val();
	if(type == 2){
		if(workflowId == -1){
			var isConfirmed = confirm("您将分组的工作流配置定制为不走工作流，此设置将忽略系统默认审核设置！您确认要进行此操作吗?");
			if(!isConfirmed){
				$(that).val(originWorkflowId);
				return;
			}
		}else{
			var isConfirmed = confirm("您将修改分组的工作流设置，该分组已流转的微博，将继续使用以前的设置完成审核。您确认要继续修改吗?");
			if(!isConfirmed){
				$(that).val(originWorkflowId);
				return;
			}
		}
	}
	$.ajax({
		type:"post",
		data:{Type:type,WorkFlowId:workflowId,SCMGroupId:groupId},
		dataType:"text",
		url:"setWorkFlow.jsp",
		success:function(){
			alert("设置审核工作流成功！");
			window.location.href = "configure_workflow.jsp?PageIndex="+pageindex+"&PageSize="+pagesize;
		},
		error:function(){
			alert("设置审核工作流失败！");
			window.location.href = "configure_workflow.jsp?PageIndex="+pageindex+"&PageSize="+pagesize;
		}
	});
}

