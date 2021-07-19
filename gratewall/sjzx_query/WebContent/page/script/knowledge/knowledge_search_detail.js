var docId=getUrlParam("doc_id");
var keyword=getUrlParam("keyword");

var recommend_tree_setting={
	data:{
		simpleData:{
			enable:true
		}
	},
	check:{
		enable:true
	},
	callback: {
	}
};

$(function(){
	initDetail();
	initFeedbackWindow();
	initRecommendWindow();
});


function initDetail(){
	$.ajax({
		url:"../../knowledge/searchAction.do?method=queryDetail",
		data:{docId:docId,keyword:encodeURI(keyword)},
		type:"post",
		success:function(data){
			if(data.docId){
				save_read_log();
			}
			$("#knowledge_detail span[name]").each(function() {
				$(this).empty();
				var attr = $(this).attr("name");
				var value = data[attr];
				$(this).html(value);
			});
			var docContent=data.docContent.replace(/\n+/ig,"<br>").replace(/ /ig,"&nbsp;").replace(/<font&nbsp;color=red>/ig,"<font color=red>");
			$("#docContent").html(docContent);
			if(data.isCollected=="1"){//已收藏
				$("#add_to_favorite").linkbutton('disable');
				$("#add_to_favorite").linkbutton({text:"已收藏"});
			}
			if(data.files){
				var html="";
				$.each(data.files,function(){
					var file=this;
					html+="<a href='javascript:void(0)' onclick=\"download_file('"+file.FILE_ID+"');return false;\">"+file.FILE_NAME+"</a></br>";
				});
				$("#klFiles").html(html);
			}
		}
	});
}

//收藏知识
function add_to_favorite(){
	$.ajax({
		url:"../../knowledge/manageAction.do?method=addToFavorite",
		type:"post",
		data:{docId:docId},
		success:function(data){
			$.messager.alert("提示信息","收藏成功。","info");
			$("#add_to_favorite").linkbutton('disable');
			$("#add_to_favorite").linkbutton({text:"已收藏"});
		}
	});
}

//知识反馈
function knowledge_feedback(){
	$("#feedback_content").val("");
	$("#feedback_window").window("open");
	$("#feedback_window").window("center");
	
}

//知识反馈
function feedback_submit(){
	var feedback_content=$("#feedback_content").val();
	
	$.ajax({
		url:"../../knowledge/searchAction.do?method=feedback",
		type:"post",
		data:{docId:docId,fbContent:encodeURI(feedback_content)},
		success:function(data){
			$.messager.alert("提示信息","提交成功。","info",function(){
				$("#feedback_window").window("close");
			});
			
		}
	});
}

//初始化知识反馈弹出窗口
function initFeedbackWindow(){
	$("#feedback_window").window({    
	    width:560,   
	    height:260,  
	    title:"知识反馈",
	    collapsible:false,
	    minimizable:false,
	    maximizable:false,
	    inline:false,
	    modal:true   
	});
	$("#feedback_window").window("close");
}


//初始化知识推荐弹出窗口
function initRecommendWindow(){
	$("#window_recommend_tree").window({    
	    width:300,    
	    height:460,  
	    title:"选择被推荐人",
	    collapsible:false,
	    minimizable:false,
	    maximizable:false,
	    inline:false,
	    modal:true   
	});
	$("#window_recommend_tree").window("close");
	
	$.ajax({
		url:"../../knowledge/searchAction.do?method=queryRecommendTree",
		type:"post",
		success:function(data){
			if(data){
				var rootObj={id:"0",pId:"-1",name:"北京工商系统"};
				data.push(rootObj);
				$.fn.zTree.init($("#tree_recommend_list"), recommend_tree_setting, data);
				
				var tree = $.fn.zTree.getZTreeObj("tree_recommend_list");
				var rootNode=tree.getNodeByParam("id", "0", null);
				//展开根节点
				tree.expandNode(rootNode,true,false,true);
			}
		}
	});
}

//显示知识推荐窗口
function open_recommend_window(){
	reset_zTree("tree_recommend_list");
	$("#window_recommend_tree").window("open");
	$("#window_recommend_tree").window("center");
}

//关闭知识推荐窗口
function close_recommend_window(){
	$("#window_recommend_tree").window("close");
}

//选择被推荐人-确定
function recommend_ok(){
	var userIds=getRecommendList();
	if(!userIds){
		$.messager.alert("错误信息","请选择被推荐人。","info");
		return ;
	}
    $.ajax({
	   url:"../../knowledge/searchAction.do?method=knowledgeRecommend",
	   type:"post",
	   data : {
		   docId : docId,
		   userIds:userIds
		},
	   success:function(data){
		   $.messager.alert("提示信息","推荐成功。","info",function(){
			   close_recommend_window();
		   });
		   
	   }
    });
}

//获取被推荐人
function getRecommendList(){
	var treeObj = $.fn.zTree.getZTreeObj("tree_recommend_list");
	var nodes = treeObj.getCheckedNodes(true);
	var checkedNodeIds="";
	$.each(nodes,function(){
		var node=this;
		if(node.id!="0"&&node.pId!='0'){//勾选值排除根结点及分局节点
			checkedNodeIds+=node.id+",";
		}
	});
	if(checkedNodeIds){
		checkedNodeIds=checkedNodeIds.substring(0,checkedNodeIds.length-1);
	}
	return checkedNodeIds;
}

function download_file(fileId){
	window.location.href="../../knowledge/manageAction.do?method=download&fileId="+fileId;
}


function goback(){
	window.history.go(-1);
	return false;
}

//清空树的勾选项,支持多个树
function reset_zTree(treeIds){
	var treeIdArray=treeIds.split(",");
	if(treeIdArray.length>0){
		$.each(treeIdArray,function(i,treeId){
			var zTreeObj=$.fn.zTree.getZTreeObj(treeId);
			if(zTreeObj){
				var tree_nodes = zTreeObj.getCheckedNodes(true);
				if (tree_nodes.length>0) { 
					for(var i=0;i<tree_nodes.length;i++){
						zTreeObj.checkNode(tree_nodes[i], false, true);
					}
				}
				zTreeObj.expandAll(false);
				var rootNode=zTreeObj.getNodeByParam("id", "0", null);
				//展开根节点
				zTreeObj.expandNode(rootNode,true,false,true);
			}
		});
	}
}

function save_read_log(){
	$.ajax({
		url:"../../knowledge/searchAction.do?method=saveReadLog",
		data:{docId:docId},
		type:"post",
		success:function(data){
			
		}
	});
}