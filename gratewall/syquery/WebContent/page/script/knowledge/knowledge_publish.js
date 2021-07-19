var orgCode="";
var toolbarData;
$(function(){
	orgCode=queryUserOrgCode();
	if(orgCode=="110000000"){ //�о��û������޸ķ�����Χ
		toolbarData=[{text: '��������',handler: cancelPublishBatchKnowledge},"-", 
	           	     {text: '�޸ķ�����Χ',handler: batchModifyScope}];
	}else{
		toolbarData=[{text: '��������',handler: cancelPublishBatchKnowledge}];
	}
	initUnpublishedDataGrid();
	initPublishedDataGrid();
	initPublishScopeWindow();
	if(window.name){
		$("#"+window.name,window.parent.document).parent().css("overflow","hidden");//���ظ����ڹ�����
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
	    title:"֪ʶ�б�",
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
   	        { field: 'title', title: '����', width:"33%", align: 'left',fixed:true},
   	        { field: 'catName', title: '�������', width: "14%", align: 'left',fixed:true},
   	        { field: 'issuer', title: '������λ', width: "15%", align: 'left',fixed:true},
   	        { field: 'option', title: '����', width: 290, align: 'left',fixed:true,formatter:optformat_published}
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
	    title:"֪ʶ�б�",
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
	        { field: 'title', title: '����', width:"47%", align: 'left',fixed:true},
	        { field: 'catName', title: '�������', width: "15%", align: 'left',fixed:true},
	        { field: 'issuer', title: '������λ', width: "17%", align: 'left',fixed:true},
	        { field: 'option', title: '����', width: 170, align: 'left',fixed:true,formatter:optformat_unpublished}
	    ]],
	    toolbar: [
	       { 
	         text: '����', 
	         handler: publishBatchKnowledge 
	       }
	    ]
	});
}
//��Ⱦ������
function optformat_published(field,rowdata,rowindex){
	var doc_id=rowdata.docId;
	var html="";
	if(orgCode=="110000000"){
		html="<a href='javascript:void(0)' onclick=modifyScope('"+doc_id+"')>�޸ķ�����Χ</a>&nbsp;&nbsp;"+
		 "<a href='javascript:void(0)' onclick=cancel_publish_knowledge('"+doc_id+"')>����</a>&nbsp;&nbsp;"+
		 "<a href='javascript:void(0)' onclick=\"edit_knowledge('"+doc_id+"','"+doc_state.published+"');return false;\">�޶�</a>&nbsp;&nbsp;"+
		 "<a href='javascript:void(0)' onclick=\"knowledge_detail('"+doc_id+"');return false;\">����</a>";
		
	}else{
		html="<a href='javascript:void(0)' onclick=cancel_publish_knowledge('"+doc_id+"')>����</a>&nbsp;&nbsp;"+
		 "<a href='javascript:void(0)' onclick=\"edit_knowledge('"+doc_id+"','"+doc_state.published+"');return false;\">�޶�</a>&nbsp;&nbsp;"+
		 "<a href='javascript:void(0)' onclick=\"knowledge_detail('"+doc_id+"');return false;\">����</a>";
	}
	
	if(rowdata.useState=="3"){
		html+="&nbsp;&nbsp;<a href='javascript:void(0)' onclick=\"knowledge_feedback('"+doc_id+"');return false;\">�鿴����</a>";
	}
	return html;
}

//��Ⱦ������
function optformat_unpublished(field,rowdata,rowindex){
	var doc_id=rowdata.docId;
	var html="<a href='javascript:void(0)' onclick=publish_knowledge('"+doc_id+"')>����</a>&nbsp;&nbsp;"+
			 "<a href='javascript:void(0)' onclick=\"edit_knowledge('"+doc_id+"','"+doc_state.checked+"');return false;\">�޶�</a>&nbsp;&nbsp;"+
			 "<a href='javascript:void(0)' onclick=\"knowledge_detail('"+doc_id+"');return false;\">����</a>";
	return html;
}

//֪ʶ����
function knowledge_detail(doc_id){
	window.location.href="knowledge_detail.html?doc_id="+doc_id;
}

//�޸�֪ʶ
function edit_knowledge(doc_id,state){
	window.location.href="knowledge_edit.html?from=pub&doc_id="+doc_id+"&state="+state;
}

//�鿴����
function knowledge_feedback(doc_id){
	window.location.href="knowledge_feedback_list.html?doc_id="+doc_id;
}

//��ʼ��������Χ����
function initPublishScopeWindow(){
	$("#window_publish_scope").window({    
	    width:300,    
	    height:460,  
	    title:"ѡ�񷢲���Χ",
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
				var rootNode={id:"0",pId:"-1",name:"������Χ"};
				data.push(rootNode);
				$.fn.zTree.init($("#tree_publish_scope"), publishscope_tree_setting, data);
				
				var tree = $.fn.zTree.getZTreeObj("tree_publish_scope");
				
				//չ���ڵ�
				tree.expandAll(true);
			}
		}
	});
}

//֪ʶ����
function publish_knowledge(doc_id){
	
	$.messager.confirm('ȷ��','ȷ��������',function(confirm){    
	    if (confirm){
	    	if(!orgCode){
				$.messager.alert("������Ϣ","δ��ȡ����ǰ�û��������أ�����ϵ����Ա��","info");
				return;
			}
	    	if(orgCode=="110000000"){ //�о��û�ѡ�񷢲���Χ
	    		$("#publish_ok").off("click").on("click",function(){
	    			publish_ok(doc_id);
	    		});
	    		open_scope_window();
	    	}else{		  //�־��û�ֱ�ӷ������û������־�
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
    				   $.messager.alert("��ʾ��Ϣ","�����ɹ���","info",function(){
    					   $("#knowledgeUnpublishedGrid").datagrid("reload");
    					   $("#knowledgePublishedGrid").datagrid("reload");
    				   });
    			   }
    		    });
	    	}
	    }    
	}); 
}

//�Ƿ����о��û�
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
//�޸�֪ʶ������Χ
function modifyScope(doc_id){
	if(orgCode=="110000000"){ //�о��û�ѡ�񷢲���Χ
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
			   $.messager.alert("��ʾ��Ϣ","�����ɹ���","info",function(){
				   $("#knowledgeUnpublishedGrid").datagrid("reload");
				   $("#knowledgePublishedGrid").datagrid("reload");
			   });
		   }
	    });
	}
	
}

//�����޸�֪ʶ������Χ
function batchModifyScope() {
	var rows = $('#knowledgePublishedGrid').datagrid("getChecked");
	if(rows.length==0){
		$.messager.alert("��ʾ��Ϣ","��ѡ��Ҫ�޸ķ�����Χ��֪ʶ��","info");
		return;
	}
	var docIds = "";
	$.each(rows, function(i, row) {
		docIds += row.docId + ",";
	});
	docIds = docIds.substring(0, docIds.length - 1);
	
	
	if(orgCode=="110000000"){ //�о��û�
		$("#publish_ok").off("click").on("click",function(){
			batch_publish_ok(docIds);
		});
		open_scope_window();
	}else{					 //�־��û�
		$.ajax({
			url : "../../knowledge/manageAction.do?method=updateBatchKnowledgeState",
			type : "post",
			data : {
				docIds : docIds,
				state  : doc_state.published,
				doc_scope:orgCode
			},
			success : function(data) {
				$.messager.alert("��ʾ��Ϣ", "�����ɹ���", "info", function() {
					$("#knowledgeUnpublishedGrid").datagrid("reload");
					$("#knowledgePublishedGrid").datagrid("reload");
				});
			}
		});
	}
}

//��ʾ"ѡ�񷢲���Χ"����
function open_scope_window(){
	reset_zTree("tree_publish_scope");
	$("#window_publish_scope").window("open");
	$("#window_publish_scope").window("center");
}
//������Ĺ�ѡ��,֧�ֶ����
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
//��ȡ������Χ
function getDocScope(){
	var treeObj = $.fn.zTree.getZTreeObj("tree_publish_scope");
	var nodes = treeObj.getCheckedNodes(true);
	var checkedNodeIds="";
	$.each(nodes,function(){
		var node=this;
		if(node.id!="0"){//ȥ��������Χ���ڵ�
			checkedNodeIds+=node.id+";";
		}
	});
	if(checkedNodeIds){
		checkedNodeIds=checkedNodeIds.substring(0,checkedNodeIds.length-1);
	}
	return checkedNodeIds;
}
//ѡ�񷢲���Χ-ȷ��
function publish_ok(doc_id){
	var doc_scope=getDocScope();
	if(!doc_scope){
		$.messager.alert("������Ϣ","��ѡ��Ҫ�����Ĺ��̷־֡�","info");
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
		   $.messager.alert("��ʾ��Ϣ","�����ɹ���","info",function(){
			   $("#knowledgeUnpublishedGrid").datagrid("reload");
			   $("#knowledgePublishedGrid").datagrid("reload");
			   publish_close();
		   });
		   
	   }
    });
}

//�ر�"ѡ�񷢲���Χ"����
function publish_close(){
	$("#window_publish_scope").window("close");
}

//��������֪ʶ
function publishBatchKnowledge() {
	var rows = $('#knowledgeUnpublishedGrid').datagrid("getChecked");
	if(rows.length==0){
		$.messager.alert("��ʾ��Ϣ","��ѡ��Ҫ������֪ʶ��","info");
		return;
	}
	$.messager.confirm('ȷ��', '��ȷ��Ҫ������', function(confirm) {
		if (confirm) {
			var docIds = "";
			$.each(rows, function(i, row) {
				docIds += row.docId + ",";
			});
			docIds = docIds.substring(0, docIds.length - 1);
			if(!orgCode){
				$.messager.alert("������Ϣ","δ��ȡ����ǰ�û��������أ�����ϵ����Ա��","info");
				return;
			}
	    	if(orgCode=="110000000"){ //�о��û�
				$("#publish_ok").off("click").on("click",function(){
					batch_publish_ok(docIds);
				});
				open_scope_window();
			}else{					 //�־��û�
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
						$.messager.alert("��ʾ��Ϣ", "�����ɹ���", "info", function() {
							$("#knowledgeUnpublishedGrid").datagrid("reload");
							$("#knowledgePublishedGrid").datagrid("reload");
						});
					}
				});
			}
		}
	});
}

//����ѡ�񷢲���Χ-ȷ��
function batch_publish_ok(docIds){
	var doc_scope=getDocScope();
	if(!doc_scope){
		$.messager.alert("������Ϣ","��ѡ��Ҫ�����Ĺ��̷־֡�","info");
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
			$.messager.alert("��ʾ��Ϣ", "�����ɹ���", "info", function() {
				$("#knowledgeUnpublishedGrid").datagrid("reload");
				$("#knowledgePublishedGrid").datagrid("reload");
				publish_close();
			});
		}
	});
}

//��������
function cancel_publish_knowledge(doc_id){
	$.messager.confirm('ȷ��','ȷ������������',function(confirm){    
	    if (confirm){    
	       $.ajax({
	    	   url:"../../knowledge/manageAction.do?method=updateKnowledgeState",
	    	   type:"post",
    		   data : {
    			   docId : doc_id,
				   state : doc_state.checked
				},
	    	   success:function(data){
	    		   $.messager.alert("��ʾ��Ϣ","�����ɹ���","info",function(){
	    			   $("#knowledgeUnpublishedGrid").datagrid("reload");
	    			   $("#knowledgePublishedGrid").datagrid("reload");
	    		   });
	    	   }
	       });
	    }    
	}); 
}

//������������
function cancelPublishBatchKnowledge() {
	var rows = $('#knowledgePublishedGrid').datagrid("getChecked");
	if(rows.length==0){
		$.messager.alert("��ʾ��Ϣ","��ѡ��Ҫ����������֪ʶ��","info");
		return;
	}
	$.messager.confirm('ȷ��', '��ȷ��Ҫ����������', function(confirm) {
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
					$.messager.alert("��ʾ��Ϣ", "�����ɹ���", "info", function() {
						$("#knowledgeUnpublishedGrid").datagrid("reload");
						$("#knowledgePublishedGrid").datagrid("reload");
					});
				}
			});
		}
	});
}

//��ѯ֪ʶ
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
//�����ѯ����
function deal_query_params(){
	var data=$("#knowledge_query_form").serializeArray();
	var jsonData={};
	$.each(data,function(){
		jsonData[this.name]=encodeURI(this.value);
	});
	return jsonData;
}

//����
function reset_search(){
	//��ձ�
	$("#knowledge_query_form").form("reset");
	//���������𡢵Ǽ�����ID�������ֵ
	$("#busiCode").val("");
	$("#categoryCode").val("");
	//�������ѡ��
	reset_zTree("zTree_categoryCode,zTree_busiCode");
}



