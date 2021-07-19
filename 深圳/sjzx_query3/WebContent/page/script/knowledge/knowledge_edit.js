var docId=getUrlParam("doc_id");
var from=getUrlParam("from");
var _state=getUrlParam("state"); //发布或审核页面传过来的状态
$(function(){
	loadTree();
	bindTreeEvent();
	loadKnowledge();
	if(from){
		$("#bt_submit_doc").remove();
	}
	
});

//加载知识
function loadKnowledge(){
	$.ajax({
		url:"../../knowledge/manageAction.do?method=queryKnowledge",
		type:"post",
		data:{docId:docId},
		success:function(data){
			$("#kl_edit_form").form("load",data);
			//根据后台数据勾选属性类别下拉树  登记事项下拉树
			check_node("zTree_categoryCode",data.categoryCode);
			check_node("zTree_busiCode",data.busiCode);
		}
	});
}

//保存知识
function kl_input_save(state){
	$('.validatebox-text').validatebox('enableValidation').validatebox('validate');//使表单验证生效
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