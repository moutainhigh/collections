var docId=getUrlParam("doc_id");
var from=getUrlParam("from");
var _state=getUrlParam("state"); //���������ҳ�洫������״̬
$(function(){
	loadTree();
	bindTreeEvent();
	loadKnowledge();
	if(from){
		$("#bt_submit_doc").remove();
	}
	
});

//����֪ʶ
function loadKnowledge(){
	$.ajax({
		url:"../../knowledge/manageAction.do?method=queryKnowledge",
		type:"post",
		data:{docId:docId},
		success:function(data){
			$("#kl_edit_form").form("load",data);
			//���ݺ�̨���ݹ�ѡ�������������  �Ǽ�����������
			check_node("zTree_categoryCode",data.categoryCode);
			check_node("zTree_busiCode",data.busiCode);
		}
	});
}

//����֪ʶ
function kl_input_save(state){
	$('.validatebox-text').validatebox('enableValidation').validatebox('validate');//ʹ����֤��Ч
	$("#state").val(state);
	if(_state){
		$("#state").val(_state);
	}
	$("#from").val(from);
	if($("#kl_edit_form").form("validate")){
		$("#bt_save_doc").attr("disabled","disabled");
		$("#kl_edit_form").submit();
	}
}