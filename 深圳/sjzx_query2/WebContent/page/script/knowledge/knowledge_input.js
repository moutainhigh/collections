$(function(){
	initCreator();
	$('.validatebox-text').bind('blur', function(){
		$(this).validatebox('enableValidation').validatebox('validate');
	});
	$("input[name='createDate']").val(new Date().format("yyyy-MM-dd"));
});

function initCreator(){
	$.ajax({
		url:"../../knowledge/manageAction.do?method=getCurrentUser",
		type:"post",
		success:function(user){
			var userName=user.userName;
			var userId=user.id;
			$("#creatorId").val(userId);
			$("#creator").val(userName);
		}
	});
}

function addMustSign() {
	$("input[data-options]").each(function() {
		var validate = $(this).attr("data-options");
		if (validate.indexOf("required:true") != -1) {
			var labelTd = $(this).parent().siblings().eq(0);
			labelTd.html("<span class='must'>*</span>" + labelTd.html());
		}
	});
}


//保存知识
function kl_input_save(state){
	$('.validatebox-text').validatebox('enableValidation').validatebox('validate');//使表单验证生效
	$("#state").val(state);
	if($("#kl_input_form").form("validate")){
		$("#bt_save_doc").linkbutton('disable');
		$("#bt_submit_doc").linkbutton('disable');
		setTimeout($("#kl_input_form").submit(),500);
	}
}
//添加更多附件
var file_index=1;
function addMore(){
    var td = $("#more");
    var input = $("<input type='file' name='files"+file_index+"' class='input-file'>");
    var button =$("<input type='button' value='删除' style='margin-left:100px;width:97px;'>");
    file_index++;
    button.on("click",function(){
        $(this).parent().remove();
    });
    var div=$("<div></div>");
    div.append(input);
    div.append(button);
    td.append(div);
}
