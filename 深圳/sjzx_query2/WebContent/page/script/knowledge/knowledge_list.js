$(function(){
	initDataGrid();  
	if(window.name){
		$("#"+window.name,window.parent.document).parent().css("overflow","hidden");//���ظ����ڹ�����
	}
});

function initDataGrid(){
	$('#knowledgeGrid').datagrid({
	    height: 567,
	    width:"99%",
	    url: '../../knowledge/manageAction.do?method=queryKnowledgeList',
	    method: 'POST',
	    idField: 'docId',
	    striped: true,
	    title:"��ѯ�б�",
	    fitColumns: true,
	    singleSelect: false,
	    selectOnCheck:true,
	    checkOnSelect:false,
	    rownumbers: true,
	    pagination: true,
	    nowrap: true,
	    showFooter: true,
	    columns: [[
	        { field: 'ck',checkbox:true},
   	        { field: 'docId', title: 'id', hidden:true},
   	        { field: 'title', title: '����', width:"38%", align: 'left',fixed:true},
   	        { field: 'catName', title: '�������', width: "15%", align: 'left',fixed:true},
   	        { field: 'issuer', title: '������λ', width: "15%", align: 'left',fixed:true},
   	        { field: 'state', title: '״̬', width: "11%", align: 'left',fixed:true},
	        { field: 'option', title: '����', width: 170, align: 'center',fixed:true,formatter:optformat}
	    ]],
	    toolbar: [
	      { 
	        text: '���', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	            window.location.href="knowledge_input.html";
	            return false;
	        } 
	      },"-",
//	      { 
//	          text: '�޸�', 
//	          iconCls: 'icon-edit', 
//	          handler: function() { 
//	              var rows=$('#knowledgeGrid').datagrid("getChecked");
//	              if(rows.length==0){
//	            	  $.messager.alert("��ʾ��Ϣ","��ѡ��Ҫ�޸ĵ����ݡ�","info");
//	              }else if(rows.length>1){
//	            	  $.messager.alert("��ʾ��Ϣ","һ��ֻ���޸�һ�����ݡ�","info");
//	              }else{
//	            	  var docId=rows[0].docId;
//	            	  edit_knowledge(docId);
//	              }
//	          } 
//	       },
	       "-",
		      { 
		          text: 'ɾ��', 
		          iconCls: 'icon-remove', 
		          handler: delBatchKnowledge
		       }
	    ]
	});
	//$(".datagrid").css("width",802);

	setPagination();
}

//��Ⱦ������
function optformat(field,rowdata,rowindex){
	var doc_id=rowdata.docId;
	var html="";
	if(rowdata.state=="δ�ύ"||rowdata.state=="�˻��޸�"){
		html="<a href='javascript:void(0)' onclick=knowledge_submit('"+doc_id+"')>�ύ</a>&nbsp;&nbsp;"+
			 "<a href='javascript:void(0)' onclick=\"knowledge_detail('"+doc_id+"');return false;\">����</a>&nbsp;&nbsp;"+
			 "<a href='javascript:void(0)' onclick=\"javascript:edit_knowledge('"+doc_id+"');return false;\">�޸�</a>&nbsp;&nbsp;"+
			 "<a href='javascript:void(0)' onclick=delete_knowledge('"+doc_id+"')>ɾ��</a>&nbsp;&nbsp;";
	}else{
		html="<a href='javascript:void(0)' onclick=\"knowledge_detail('"+doc_id+"');return false;\">����</a>&nbsp;&nbsp;";
	}
	return html;
}

//֪ʶ����
function knowledge_detail(doc_id){
	window.location.href="knowledge_detail.html?doc_id="+doc_id;
}


function batchSubmitKnowledge(){
	var rows = $('#knowledgeGrid').datagrid("getChecked");
	if(rows.length==0){
		$.messager.alert("��ʾ��Ϣ","��ѡ��Ҫ�ύ�����ݡ�","info");
		return;
	}
	$.messager.confirm('ȷ��', '�ύ�󲻿��޸ģ�ȷ��Ҫ �ύ��Щ������', function(confirm) {
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
					state  : doc_state.unchecked
				},
				success : function(data) {
					$.messager.alert("��ʾ��Ϣ", "�����ɹ���", "info", function() {
						$("#knowledgeGrid").datagrid("reload");
					});
				}
			});
		}
	});
}
//֪ʶ�ύ
function knowledge_submit(doc_id){
	$.messager.confirm('ȷ��','�ύ�󲻿��޸ģ�ȷ���ύ��',function(confirm){    
	    if (confirm){    
	       $.ajax({
	    	   url:"../../knowledge/manageAction.do?method=updateKnowledgeState",
	    	   type:"post",
	    	   data : {
	    		   docId : doc_id,
				   state  : doc_state.unchecked
				},
	    	   success:function(data){
	    		   $.messager.alert("��ʾ��Ϣ","�����ɹ���","info",function(){
	    			   $("#knowledgeGrid").datagrid("reload");
	    		   });
	    	   }
	       });
	    }    
	}); 
}

//�޸�֪ʶ
function edit_knowledge(doc_id){
	window.location.href="knowledge_edit.html?doc_id="+doc_id;
}


//ɾ��֪ʶ
function delete_knowledge(doc_id){
	$.messager.confirm('ȷ��','��ȷ����Ҫɾ����¼��',function(confirm){    
	    if (confirm){    
	       $.ajax({
	    	   url:"../../knowledge/manageAction.do?method=delKnowledge",
	    	   type:"post",
	    	   data:{docId:doc_id},
	    	   success:function(data){
	    		   $.messager.alert("��ʾ��Ϣ","ɾ���ɹ���","info",function(){
	    			   $("#knowledgeGrid").datagrid("reload");
	    		   });
	    	   }
	       });
	    }    
	}); 
}

//����ɾ��֪ʶ
function delBatchKnowledge() {
	var rows = $('#knowledgeGrid').datagrid("getChecked");
	if(rows.length==0){
		$.messager.alert("��ʾ��Ϣ","��ѡ��Ҫɾ�������ݡ�","info");
		return;
	}
	var isCandel=true;
	var docIds = "";
	$.each(rows, function(i, row) {
		docIds += row.docId + ",";
		if(rows.state!="δ�ύ"){
			isCandel=false;
		}
	});
	if(!isCandel){
		$.messager.alert("��ʾ��Ϣ","��ѡ��¼�а����Ѿ��ύ�ļ�¼��ֻ��ɾ��δ�ύ�ļ�¼��","info");
		return;
	}
	docIds = docIds.substring(0, docIds.length - 1);
	$.messager.confirm('ȷ��', '��ȷ����Ҫɾ����¼��', function(confirm) {
		if (confirm) {
			$.ajax({
				url : "../../knowledge/manageAction.do?method=delBatchKnowledge",
				type : "post",
				data : {
					docIds : docIds
				},
				success : function(data) {
					$.messager.alert("��ʾ��Ϣ", "ɾ���ɹ���", "info", function() {
						$("#knowledgeGrid").datagrid("reload");
					});
				}
			});
		}
	});
}

//���÷�ҳ��Ϣ
function setPagination() {
	var p = $('#knowledgeGrid').datagrid('getPager'); 
    $(p).pagination({ 
        pageSize: 10,//ÿҳ��ʾ�ļ�¼������Ĭ��Ϊ10 
        pageList: [10,15,20],//��������ÿҳ��¼�������б� 
        beforePageText: '��',//ҳ���ı���ǰ��ʾ�ĺ��� 
        afterPageText: 'ҳ    �� {pages} ҳ', 
        displayMsg: '��ǰ��ʾ {from} - {to} ����¼   �� {total} ����¼'
    });
}

//��ѯ֪ʶ
function search_knowledge(){
	var params=deal_query_params();
	//console.log($("#knowledge_query_form").serialize());
	
	$('#knowledgeGrid').datagrid("reload",params);
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



