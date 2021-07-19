$(function(){
	initDataGrid();  
	if(window.name){
		$("#"+window.name,window.parent.document).parent().css("overflow","hidden");//���ظ����ڹ�����
	}
});

function initDataGrid(){
	$('#myknowledgeGrid').datagrid({
	    height: 567,
	    width:"99%",
	    url: '../../knowledge/manageAction.do?method=queryMyknowledgeList',
	    method: 'POST',
	    idField: 'id',
	    striped: true,
	    title:"�ҵ�֪ʶ�б�",
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
	        { field: 'id', title: '����id', hidden:true},
   	        { field: 'docId', title: '֪ʶid', hidden:true},
   	        { field: 'title', title: '����', width:"35%", align: 'left',fixed:true},
   	        { field: 'catName', title: '�������', width: "15%", align: 'left',fixed:true},
   	        { field: 'issuer', title: '������λ', width: "15%", align: 'left',fixed:true},
   	        { field: 'source', title: '��Դ', width: "8%", align: 'left',fixed:true,formatter:sourceformat},
   	        { field: 'recPerson', title: '�Ƽ���', width: "10%", align: 'left',fixed:true},
	        { field: 'option', title: '����', width: 130, align: 'center',fixed:true,formatter:optformat}
	    ]],
	    toolbar: [{ 
      		          text: 'ɾ��', 
      		          iconCls: 'icon-remove', 
      		          handler: delBatchKnowledge
      		      }]
	    
	});
	//$(".datagrid").css("width",802);

	setPagination();
}




//��Ⱦ������
function optformat(field,rowdata,rowindex){
	var doc_id=rowdata.docId;
	var id=rowdata.id;
	var source=rowdata.source;
	var html="<a href='javascript:void(0)' onclick=\"knowledge_detail('"+doc_id+"');return false;\">����</a>&nbsp;&nbsp;"+
			 "<a href='javascript:void(0)' onclick=delete_knowledge('"+id+"','"+source+"')>ɾ��</a>";
	return html;
}

//��Ⱦ������
function sourceformat(field,rowdata,rowindex){
	var source=rowdata.source;
	if(source=="fav"){
		return "֪ʶ�ղ�";
	}else if(source=="rec"){
		return "֪ʶ�Ƽ�";
	}
}

//ɾ��֪ʶ
function delete_knowledge(id,type){
	$.messager.confirm('ȷ��','��ȷ����Ҫɾ����¼��',function(confirm){    
	    if (confirm){    
	       $.ajax({
	    	   url:"../../knowledge/manageAction.do?method=delMyknowledge",
	    	   type:"post",
	    	   data:{id:id,type:type},
	    	   success:function(data){
	    		   $.messager.alert("��ʾ��Ϣ","ɾ���ɹ���","info",function(){
	    			   $("#myknowledgeGrid").datagrid("reload");
	    		   });
	    	   }
	       });
	    }    
	}); 
}

//����ɾ��֪ʶ
function delBatchKnowledge() {
	var rows = $('#myknowledgeGrid').datagrid("getChecked");
	if(rows.length==0){
		$.messager.alert("��ʾ��Ϣ","��ѡ��Ҫɾ�������ݡ�","info");
		return;
	}
	$.messager.confirm('ȷ��', '��ȷ����Ҫɾ����¼��', function(confirm) {
		if (confirm) {
			var favIds = "";
			var recIds = "";
			$.each(rows, function(i, row) {
				if(row.source=="fav"){ 			//��ԴΪ֪ʶ�ղ�
					favIds += row.id + ",";
				}else if(row.source=="rec"){	//��ԴΪ֪ʶ�Ƽ�
					recIds += row.id + ",";
				}
				
			});
			favIds = favIds.substring(0, favIds.length - 1);
			recIds = recIds.substring(0, recIds.length - 1);
			$.ajax({
				url : "../../knowledge/manageAction.do?method=delBatchMyknowledge",
				type : "post",
				data : {
					favIds : favIds,
					recIds : recIds
				},
				success : function(data) {
					$.messager.alert("��ʾ��Ϣ", "ɾ���ɹ���", "info", function() {
						$("#myknowledgeGrid").datagrid("reload");
					});
				}
			});
		}
	});
}
//֪ʶ����
function knowledge_detail(doc_id){
	window.location.href="knowledge_detail.html?doc_id="+doc_id;
}


//���÷�ҳ��Ϣ
function setPagination() {
	var p = $('#myknowledgeGrid').datagrid('getPager'); 
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
	
	$('#myknowledgeGrid').datagrid("reload",params);
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


