
var search_keyword=getUrlParam("keyword")||"";



$(function(){
	initQueryCondition();
	initDataGrid();  
	if(window.name){
		$("#"+window.name,window.parent.document).parent().css("overflow","hidden");//���ظ����ڹ�����
	}
});


/**
 * ��ʼ����ѯ����
 */
function initQueryCondition(){
	$("#text_categoryCode").val(search_keyword);
	var categoryValue = "";
	$.ajax({
		url:"../../knowledge/manageAction.do?method=queryCategoryList",
		type:"post",
		async:false,
		success:function(data){
			if(data){
				var category = data.catCode;
				if(category!=null && category.length>0){
					for(var i=0;i<category.length;i++){
						if(search_keyword==category[i].name){
							categoryValue = category[i].id;
							break;
						}
					}
				}
			}
		}
	});
	
	$("#categoryCode").val(categoryValue);
}


function initDataGrid(){
	
	
	$('#knowledgeGrid').datagrid({
	    height: 567,
	    width:"99%",
	    url: '../../knowledge/manageAction.do?method=queryKnowledgeTypeList',
	    method: 'POST',
	    queryParams:{cat_name:encodeURI(search_keyword)},
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
   	        { field: 'title', title: '����', width:"60%", align: 'left',fixed:true},
   	        { field: 'catName', title: '�������', width: "10%", align: 'left',fixed:true},
   	        { field: 'issuer', title: '������λ', width: "13%", align: 'left',fixed:true},
   	        { field: 'state', title: '״̬', width: "8%", align: 'left',fixed:true},
	        { field: 'option', title: '����', width: "8%", align: 'center',fixed:true,formatter:optformat}
	    ]]
	});

	setPagination();
}

//��Ⱦ������
function optformat(field,rowdata,rowindex){
	var doc_id=rowdata.docId;
	var html="";
	
	html="<a href='javascript:void(0)' onclick=\"knowledge_detail('"+doc_id+"');return false;\">����</a>&nbsp;&nbsp;";
	
	return html;
}

//֪ʶ����
function knowledge_detail(doc_id){
	//window.location.href="knowledge_detail.html?doc_id="+doc_id;
	window.location.href="knowledge_search_detail.html?doc_id="+doc_id+"&keyword=";
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



