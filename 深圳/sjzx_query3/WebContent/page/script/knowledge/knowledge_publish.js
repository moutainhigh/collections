var orgCode="";
var toolbarData;
$(function(){
	orgCode=queryUserOrgCode();
	if(orgCode=="110000000"){ //市局用户可以修改发布范围
		toolbarData=[{text: '撤销发布',handler: cancelPublishBatchKnowledge},"-", 
	           	     {text: '修改发布范围',handler: batchModifyScope}];
	}else{
		toolbarData=[{text: '撤销发布',handler: cancelPublishBatchKnowledge}];
	}
	initUnpublishedDataGrid();
	initPublishedDataGrid();
	initPublishScopeWindow();
	if(window.name){
		$("#"+window.name,window.parent.document).parent().css("overflow","hidden");//隐藏父窗口滚动条
	}
});

var publishscope_tree_setting={
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

function initPublishedDataGrid(){
	$('#knowledgePublishedGrid').datagrid({
	    height: 567,
	    width:"100%",
	    url: '../../knowledge/manageAction.do?method=queryKnowledgeList&state=3',
	    method: 'POST',
	    idField: 'docId',
	    striped: true,
	    title:"知识列表",
	    fitColumns: true,
	    singleSelect: false,
	    selectOnCheck:true,
	    checkOnSelect:false,
	    rownumbers: true,
	    pagination: true,
	    pageSize: 10,
        pageList: [10,15,20],
	    nowrap: true,
	    showFooter: true,
	    columns: [[
   	        { field: 'ck',checkbox:true},
   	        { field: 'docId', title: 'id', hidden:true},
   	        { field: 'title', title: '标题', width:"33%", align: 'left',fixed:true},
   	        { field: 'catName', title: '属性类别', width: "14%", align: 'left',fixed:true},
   	        { field: 'issuer', title: '发布单位', width: "15%", align: 'left',fixed:true},
   	        { field: 'option', title: '操作', width: 290, align: 'left',fixed:true,formatter:optformat_published}
   	    ]],
	    toolbar: toolbarData
	});
}
function initUnpublishedDataGrid(){
	$('#knowledgeUnpublishedGrid').datagrid({
	    height: 567,
	    width:"100%",
	    url: '../../knowledge/manageAction.do?method=queryKnowledgeList&state=2',
	    method: 'POST',
	    idField: 'docId',
	    striped: true,
	    title:"知识列表",
	    fitColumns: true,
	    singleSelect: false,
	    selectOnCheck:true,
	    checkOnSelect:false,
	    rownumbers: true,
	    pagination: true,
	    pageSize: 10,
        pageList: [10,15,20],
	    nowrap: true,
	    showFooter: true,
	    columns: [[
	        { field: 'ck',checkbox:true},
	        { field: 'docId', title: 'id', hidden:true},
	        { field: 'title', title: '标题', width:"47%", align: 'left',fixed:true},
	        { field: 'catName', title: '属性类别', width: "15%", align: 'left',fixed:true},
	        { field: 'issuer', title: '发布单位', width: "17%", align: 'left',fixed:true},
	        { field: 'option', title: '操作', width: 170, align: 'left',fixed:true,formatter:optformat_unpublished}
	    ]],
	    toolbar: [
	       { 
	         text: '发布', 
	         handler: publishBatchKnowledge 
	       }
	    ]
	});
}
//渲染操作列
function optformat_published(field,rowdata,rowindex){
	var doc_id=rowdata.docId;
	var html="";
	if(orgCode=="110000000"){
		html="<a href='javascript:void(0)' onclick=modifyScope('"+doc_id+"')>修改发布范围</a>&nbsp;&nbsp;"+
		 "<a href='javascript:void(0)' onclick=cancel_publish_knowledge('"+doc_id+"')>撤销</a>&nbsp;&nbsp;"+
		 "<a href='javascript:void(0)' onclick=\"edit_knowledge('"+doc_id+"','"+doc_state.published+"');return false;\">修订</a>&nbsp;&nbsp;"+
		 "<a href='javascript:void(0)' onclick=\"knowledge_detail('"+doc_id+"');return false;\">详情</a>";
		
	}else{
		html="<a href='javascript:void(0)' onclick=cancel_publish_knowledge('"+doc_id+"')>撤销</a>&nbsp;&nbsp;"+
		 "<a href='javascript:void(0)' onclick=\"edit_knowledge('"+doc_id+"','"+doc_state.published+"');return false;\">修订</a>&nbsp;&nbsp;"+
		 "<a href='javascript:void(0)' onclick=\"knowledge_detail('"+doc_id+"');return false;\">详情</a>";
	}
	
	if(rowdata.useState=="3"){
		html+="&nbsp;&nbsp;<a href='javascript:void(0)' onclick=\"knowledge_feedback('"+doc_id+"');return false;\">查看反馈</a>";
	}
	return html;
}

//渲染操作列
function optformat_unpublished(field,rowdata,rowindex){
	var doc_id=rowdata.docId;
	var html="<a href='javascript:void(0)' onclick=publish_knowledge('"+doc_id+"')>发布</a>&nbsp;&nbsp;"+
			 "<a href='javascript:void(0)' onclick=\"edit_knowledge('"+doc_id+"','"+doc_state.checked+"');return false;\">修订</a>&nbsp;&nbsp;"+
			 "<a href='javascript:void(0)' onclick=\"knowledge_detail('"+doc_id+"');return false;\">详情</a>";
	return html;
}

//知识详情
function knowledge_detail(doc_id){
	window.location.href="knowledge_detail.html?doc_id="+doc_id;
}

//修改知识
function edit_knowledge(doc_id,state){
	window.location.href="knowledge_edit.html?from=pub&doc_id="+doc_id+"&state="+state;
}

//查看反馈
function knowledge_feedback(doc_id){
	window.location.href="knowledge_feedback_list.html?doc_id="+doc_id;
}

//初始化发布范围窗口
function initPublishScopeWindow(){
	$("#window_publish_scope").window({    
	    width:300,    
	    height:460,  
	    title:"选择发布范围",
	    collapsible:false,
	    minimizable:false,
	    maximizable:false,
	    inline:false,
	    modal:true   
	});
	$("#window_publish_scope").window("close");
	
	$.ajax({
		url:"../../knowledge/manageAction.do?method=queryPublishScopeTree",
		type:"post",
		success:function(data){
			if(data){
				var rootNode={id:"0",pId:"-1",name:"发布范围"};
				data.push(rootNode);
				$.fn.zTree.init($("#tree_publish_scope"), publishscope_tree_setting, data);
				
				var tree = $.fn.zTree.getZTreeObj("tree_publish_scope");
				
				//展开节点
				tree.expandAll(true);
			}
		}
	});
}

//知识发布
function publish_knowledge(doc_id){
	
	$.messager.confirm('确定','确定发布吗？',function(confirm){    
	    if (confirm){
	    	if(!orgCode){
				$.messager.alert("错误信息","未获取到当前用户所属机关，请联系管理员。","info");
				return;
			}
	    	if(orgCode=="110000000"){ //市局用户选择发布范围
	    		$("#publish_ok").off("click").on("click",function(){
	    			publish_ok(doc_id);
	    		});
	    		open_scope_window();
	    	}else{		  //分局用户直接发布到用户所属分局
	    		orgCode=orgCode.substring(0,6);
	    		$.ajax({
    			   url:"../../knowledge/manageAction.do?method=updateKnowledgeState",
    			   type:"post",
    			   data : {
    				   docId : doc_id,
    				   state  : doc_state.published,
    				   doc_scope:orgCode
    				},
    			   success:function(data){
    				   $.messager.alert("提示信息","操作成功。","info",function(){
    					   $("#knowledgeUnpublishedGrid").datagrid("reload");
    					   $("#knowledgePublishedGrid").datagrid("reload");
    				   });
    			   }
    		    });
	    	}
	    }    
	}); 
}

//是否是市局用户
function queryUserOrgCode(){
	var orgCode="";
	$.ajax({
	   url:"../../knowledge/manageAction.do?method=queryUserOrgCode",
	   type:"post",
	   async:false,
	   success:function(data){
		   orgCode=data;
	   }
    });
	return orgCode;
}
//修改知识发布范围
function modifyScope(doc_id){
	if(orgCode=="110000000"){ //市局用户选择发布范围
		$("#publish_ok").off("click").on("click",function(){
			publish_ok(doc_id);
		});
		open_scope_window();
	}else{
		$.ajax({
		   url:"../../knowledge/manageAction.do?method=updateKnowledgeState",
		   type:"post",
		   data : {
			   docId : doc_id,
			   state  : doc_state.published,
			   doc_scope:orgCode
			},
		   success:function(data){
			   $.messager.alert("提示信息","操作成功。","info",function(){
				   $("#knowledgeUnpublishedGrid").datagrid("reload");
				   $("#knowledgePublishedGrid").datagrid("reload");
			   });
		   }
	    });
	}
	
}

//批量修改知识发布范围
function batchModifyScope() {
	var rows = $('#knowledgePublishedGrid').datagrid("getChecked");
	if(rows.length==0){
		$.messager.alert("提示信息","请选择要修改发布范围的知识。","info");
		return;
	}
	var docIds = "";
	$.each(rows, function(i, row) {
		docIds += row.docId + ",";
	});
	docIds = docIds.substring(0, docIds.length - 1);
	
	
	if(orgCode=="110000000"){ //市局用户
		$("#publish_ok").off("click").on("click",function(){
			batch_publish_ok(docIds);
		});
		open_scope_window();
	}else{					 //分局用户
		$.ajax({
			url : "../../knowledge/manageAction.do?method=updateBatchKnowledgeState",
			type : "post",
			data : {
				docIds : docIds,
				state  : doc_state.published,
				doc_scope:orgCode
			},
			success : function(data) {
				$.messager.alert("提示信息", "操作成功。", "info", function() {
					$("#knowledgeUnpublishedGrid").datagrid("reload");
					$("#knowledgePublishedGrid").datagrid("reload");
				});
			}
		});
	}
}

//显示"选择发布范围"窗口
function open_scope_window(){
	reset_zTree("tree_publish_scope");
	$("#window_publish_scope").window("open");
	$("#window_publish_scope").window("center");
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
			}
		});
	}
}
//获取发布范围
function getDocScope(){
	var treeObj = $.fn.zTree.getZTreeObj("tree_publish_scope");
	var nodes = treeObj.getCheckedNodes(true);
	var checkedNodeIds="";
	$.each(nodes,function(){
		var node=this;
		if(node.id!="0"){//去掉发布范围根节点
			checkedNodeIds+=node.id+";";
		}
	});
	if(checkedNodeIds){
		checkedNodeIds=checkedNodeIds.substring(0,checkedNodeIds.length-1);
	}
	return checkedNodeIds;
}
//选择发布范围-确定
function publish_ok(doc_id){
	var doc_scope=getDocScope();
	if(!doc_scope){
		$.messager.alert("错误信息","请选择要发布的工商分局。","info");
		return ;
	}
    $.ajax({
	   url:"../../knowledge/manageAction.do?method=updateKnowledgeState",
	   type:"post",
	   data : {
		   docId : doc_id,
		   state  : doc_state.published,
		   doc_scope:doc_scope
		},
	   success:function(data){
		   $.messager.alert("提示信息","操作成功。","info",function(){
			   $("#knowledgeUnpublishedGrid").datagrid("reload");
			   $("#knowledgePublishedGrid").datagrid("reload");
			   publish_close();
		   });
		   
	   }
    });
}

//关闭"选择发布范围"窗口
function publish_close(){
	$("#window_publish_scope").window("close");
}

//批量发布知识
function publishBatchKnowledge() {
	var rows = $('#knowledgeUnpublishedGrid').datagrid("getChecked");
	if(rows.length==0){
		$.messager.alert("提示信息","请选择要发布的知识。","info");
		return;
	}
	$.messager.confirm('确定', '您确定要发布吗？', function(confirm) {
		if (confirm) {
			var docIds = "";
			$.each(rows, function(i, row) {
				docIds += row.docId + ",";
			});
			docIds = docIds.substring(0, docIds.length - 1);
			if(!orgCode){
				$.messager.alert("错误信息","未获取到当前用户所属机关，请联系管理员。","info");
				return;
			}
	    	if(orgCode=="110000000"){ //市局用户
				$("#publish_ok").off("click").on("click",function(){
					batch_publish_ok(docIds);
				});
				open_scope_window();
			}else{					 //分局用户
				orgCode=orgCode.substring(0,6);
				$.ajax({
					url : "../../knowledge/manageAction.do?method=updateBatchKnowledgeState",
					type : "post",
					data : {
						docIds : docIds,
						state  : doc_state.published,
						doc_scope:orgCode
					},
					success : function(data) {
						$.messager.alert("提示信息", "操作成功。", "info", function() {
							$("#knowledgeUnpublishedGrid").datagrid("reload");
							$("#knowledgePublishedGrid").datagrid("reload");
						});
					}
				});
			}
		}
	});
}

//批量选择发布范围-确定
function batch_publish_ok(docIds){
	var doc_scope=getDocScope();
	if(!doc_scope){
		$.messager.alert("错误信息","请选择要发布的工商分局。","info");
		return ;
	}
	$.ajax({
		url : "../../knowledge/manageAction.do?method=updateBatchKnowledgeState",
		type : "post",
		data : {
			docIds : docIds,
			state  : doc_state.published,
			doc_scope:doc_scope
		},
		success : function(data) {
			$.messager.alert("提示信息", "操作成功。", "info", function() {
				$("#knowledgeUnpublishedGrid").datagrid("reload");
				$("#knowledgePublishedGrid").datagrid("reload");
				publish_close();
			});
		}
	});
}

//撤销发布
function cancel_publish_knowledge(doc_id){
	$.messager.confirm('确定','确定撤销发布吗？',function(confirm){    
	    if (confirm){    
	       $.ajax({
	    	   url:"../../knowledge/manageAction.do?method=updateKnowledgeState",
	    	   type:"post",
    		   data : {
    			   docId : doc_id,
				   state : doc_state.checked
				},
	    	   success:function(data){
	    		   $.messager.alert("提示信息","操作成功。","info",function(){
	    			   $("#knowledgeUnpublishedGrid").datagrid("reload");
	    			   $("#knowledgePublishedGrid").datagrid("reload");
	    		   });
	    	   }
	       });
	    }    
	}); 
}

//批量撤销发布
function cancelPublishBatchKnowledge() {
	var rows = $('#knowledgePublishedGrid').datagrid("getChecked");
	if(rows.length==0){
		$.messager.alert("提示信息","请选择要撤销发布的知识。","info");
		return;
	}
	$.messager.confirm('确定', '您确定要撤销发布吗？', function(confirm) {
		if (confirm) {
			var docIds = "";
			$.each(rows, function(i, row) {
				docIds += row.docId + ",";
			});
			docIds = docIds.substring(0, docIds.length - 1);
			$.ajax({
				url : "../../knowledge/manageAction.do?method=updateBatchKnowledgeState",
				type : "post",
				data : {
					docIds : docIds,
					state  : doc_state.checked
				},
				success : function(data) {
					$.messager.alert("提示信息", "操作成功。", "info", function() {
						$("#knowledgeUnpublishedGrid").datagrid("reload");
						$("#knowledgePublishedGrid").datagrid("reload");
					});
				}
			});
		}
	});
}

//查询知识
function search_knowledge(){
	var tab = $('#knowledge_tab').tabs('getSelected');
	var index = $('#knowledge_tab').tabs('getTabIndex',tab);
	
	var params=deal_query_params();
	if (index == 0) {
		$('#knowledgeUnpublishedGrid').datagrid("reload",params);
	} else {
		$('#knowledgePublishedGrid').datagrid("reload",params);
	}
}
//处理查询参数
function deal_query_params(){
	var data=$("#knowledge_query_form").serializeArray();
	var jsonData={};
	$.each(data,function(){
		jsonData[this.name]=encodeURI(this.value);
	});
	return jsonData;
}

//重置
function reset_search(){
	//清空表单
	$("#knowledge_query_form").form("reset");
	//清空属性类别、登记事项ID隐藏域的值
	$("#busiCode").val("");
	$("#categoryCode").val("");
	//清空树勾选项
	reset_zTree("zTree_categoryCode,zTree_busiCode");
}



