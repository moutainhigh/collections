$(function(){
	initUncheckedDataGrid();
	initCheckedDataGrid();
	if(window.name){
		$("#"+window.name,window.parent.document).parent().css("overflow","hidden");//���ظ����ڹ�����
	}
});

function initCheckedDataGrid(){
	$('#knowledgeCheckedGrid').datagrid({
	    height: 547,
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
	   	        { field: 'title', title: '����', width:"45%", align: 'left',fixed:true},
	   	        { field: 'catName', title: '�������', width: "16%", align: 'left',fixed:true},
	   	        { field: 'issuer', title: '������λ', width: "18%", align: 'left',fixed:true},
	   	        { field: 'option', title: '����', width: 120, align: 'center',fixed:true,formatter:optformat_checked}
	   	    ]]
	});
}
function initUncheckedDataGrid(){
	$('#knowledgeUncheckedGrid').datagrid({
	    height: 547,
	    width:"100%",
	    url: '../../knowledge/manageAction.do?method=queryKnowledgeList&state=1',
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
	        { field: 'title', title: '����', width:"45%", align: 'left',fixed:true},
	        { field: 'catName', title: '�������', width: "16%", align: 'left',fixed:true},
	        { field: 'issuer', title: '������λ', width: "16%", align: 'left',fixed:true},
	        { field: 'option', title: '����', width: 210, align: 'center',fixed:true,formatter:optformat_unchecked}
	    ]],
	    toolbar: [
	      { 
	        text: '�˻��޸�', 
	        //iconCls: 'icon-add', 
	        handler: backmodifyBatchKnowledge 
	      },"-",
	      { 
	          text: '���ͨ��', 
	          //iconCls: 'icon-edit', 
	          handler: checkBatchKnowledge
	       }
	    ]
	});
}
//��Ⱦ������
function optformat_unchecked(field,rowdata,rowindex){
	var doc_id=rowdata.docId;
	var html="<a href='javascript:void(0)' onclick=check_knowledge('"+doc_id+"')>���ͨ��</a>&nbsp;&nbsp;"+
	 		 "<a href='javascript:void(0)' onclick=\"edit_knowledge('"+doc_id+"','"+doc_state.unchecked+"');return false;\">�޶�</a>&nbsp;&nbsp;"+
			 "<a href='javascript:void(0)' onclick=backmodify_knowledge('"+doc_id+"')>�˻��޸�</a>&nbsp;&nbsp;"+
			 "<a href='javascript:void(0)' onclick=\"knowledge_detail('"+doc_id+"');return false;\">����</a>";
	return html;
}

//��Ⱦ������
function optformat_checked(field,rowdata,rowindex){
	var doc_id=rowdata.docId;
	var html="<a href='javascript:void(0)' onclick=\"edit_knowledge('"+doc_id+"','"+doc_state.checked+"');return false;\">�޶�</a>&nbsp;&nbsp;"+
	 	     "<a href='javascript:void(0)' onclick=\"knowledge_detail('"+doc_id+"');return false;\">����</a>";
	 		 
	return html;
}

//֪ʶ����
function knowledge_detail(doc_id){
	window.location.href="knowledge_detail.html?doc_id="+doc_id;
}

//�޸�֪ʶ
function edit_knowledge(doc_id,state){
	window.location.href="knowledge_edit.html?from=chk&doc_id="+doc_id+"&state="+state;
}


//֪ʶ���ͨ��
function check_knowledge(doc_id){
	$.messager.confirm('ȷ��','ȷ�����ͨ����',function(confirm){    
	    if (confirm){    
	       $.ajax({
	    	   url:"../../knowledge/manageAction.do?method=updateKnowledgeState",
	    	   type:"post",
	    	   data : {
	    		   docId : doc_id,
				   state  : doc_state.checked
				},
	    	   success:function(data){
	    		   $.messager.alert("��ʾ��Ϣ","�����ɹ���","info",function(){
	    			   $("#knowledgeUncheckedGrid").datagrid("reload");
	    			   $("#knowledgeCheckedGrid").datagrid("reload");
	    		   });
	    	   }
	       });
	    }    
	}); 
}

//�������֪ʶ
function checkBatchKnowledge() {
	var rows = $('#knowledgeUncheckedGrid').datagrid("getChecked");
	if(rows.length==0){
		$.messager.alert("��ʾ��Ϣ","��ѡ��Ҫ��˵����ݡ�","info");
		return;
	}
	$.messager.confirm('ȷ��', '��ȷ��Ҫ �����Щ������', function(confirm) {
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
						$("#knowledgeUncheckedGrid").datagrid("reload");
						$("#knowledgeCheckedGrid").datagrid("reload");
					});
				}
			});
		}
	});
}


//֪ʶ�˻��޸�
function backmodify_knowledge(doc_id){
	$.messager.confirm('ȷ��','ȷ���˻��޸���',function(confirm){    
	    if (confirm){    
	       $.ajax({
	    	   url:"../../knowledge/manageAction.do?method=updateKnowledgeState",
	    	   type:"post",
    		   data : {
    			   docId : doc_id,
				   state : doc_state.backmodify
				},
	    	   success:function(data){
	    		   $.messager.alert("��ʾ��Ϣ","�����ɹ���","info",function(){
	    			   $("#knowledgeUncheckedGrid").datagrid("reload");
	    			   $("#knowledgeCheckedGrid").datagrid("reload");
	    		   });
	    	   }
	       });
	    }    
	}); 
}

//�����˻��޸�
function backmodifyBatchKnowledge() {
	var rows = $('#knowledgeUncheckedGrid').datagrid("getChecked");
	if(rows.length==0){
		$.messager.alert("��ʾ��Ϣ","��ѡ��Ҫ�˻��޸ĵ����ݡ�","info");
		return;
	}
	$.messager.confirm('ȷ��', '��ȷ��Ҫ �˻���Щ������', function(confirm) {
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
					state  : doc_state.backmodify
				},
				success : function(data) {
					$.messager.alert("��ʾ��Ϣ", "�����ɹ���", "info", function() {
						$("#knowledgeUncheckedGrid").datagrid("reload");
						$("#knowledgeCheckedGrid").datagrid("reload");
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
		$('#knowledgeUncheckedGrid').datagrid("reload",params);
	} else {
		$('#knowledgeCheckedGrid').datagrid("reload",params);
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



