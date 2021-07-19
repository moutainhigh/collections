$(function() {
	// 身份认证给父弹出的页面让它自动给iframe赋值
	var height = $('body').height() + 140;
	var fram = $('#frametabcontent', window.parent.document);

	if (fram) {
		fram.css('height', height);
	}
	var name = decodeURI(jazz.util.getParameter("name"))||'';
	var cerType = jazz.util.getParameter("cerType")||'';
	var cerNo = jazz.util.getParameter("cerNo")||'';
	var personSign = jazz.util.getParameter("personSign")||'';
	if(personSign=='0'){
		$("#fzrr").show();
		$("#zrr").hide();
	}else{
		$("#zrr").show();
		$("#fzrr").hide();
	}
	// 获取要进行身份核查人的相关信息
	var url = "../../../approve/identity/censor/loadLiveUserPic.do";
	$.ajax({
			url : url,
			type : "post",
			dataType : "json",
			data:{
				"userName":name,
				"cerType":cerType,
				"cerNo":cerNo
			},
			success : function(data) {
				if (data&&data.length==0) {
					$("#pass").hide();
					$("#nopass").hide();
					alert("没有待审核的图像。");
					return;
				}else{
					$("#pass").show();
					$("#nopass").show();
				}
				var li = ''
				$("#imgList").children("li").remove();
				for (var i = 0; i < data.length; i++) {
					// 状态：-1-未提交；0-待审批；1-正常；2-审批不通过
					//li += "<li><img class='smallfile' src='../../../upload/showPic.do?fileId="
					li += "<li><img class='smallfile' src='http://160.99.11.27:9090/upload/showPic.do?fileId="
							+ data[i].thumbFileId + "' " + "fileid='"
							+ data[i].fileId + "' bizid='" + data[i].bizId
							+ "'></li>";
				}
				$("#imgList").append(li);
				var width = 115 * (data.length);
				$("#imgList").css('width', width);
				$(".smallfile").on('click', changeFile);
				// 初始化第一张显示图片
				var file = $("#imgList").find("img:eq(0)");
				file.click();
				if(personSign=='0'){
					var contactName = data[0].contactName||'';
					var contactCerNo = data[0].contactCerno||'';
					var mobile = data[0].mobile||'';
					$("#entName").text(name);
					$("#uniScid").text(cerNo);
					$("#contactName").text(contactName);
					$("#contactCerNo").text(contactCerNo);
					$("#mobile").text(mobile);
				}else{
					$("#name").text(name);
					$("#codeId").text(cerNo);
				}
			}
	});
	// 保存图片审查信息
	$("#pass").on('click', function() {
		submitArroveMsg('1', 'pass');
	});
	// 审查图片未通过
	$("#nopass").on('click', function() {
		submitArroveMsg('2', 'noPass');
	});
	// 关闭按钮
	$("#close_button").on('click', function() {
		window.location.href="../../../page/approve/sysidentify/identityList.html";
	});
	//绑定按钮事件slideLeft,slideRight
	//$(".slide_left").on('click', slideLeft);
	//$(".slide_right").on('click', slideRight);
	$("div[name='close_button']").on('click', closeWindow);
});
//关闭窗口，返回列表
function closeWindow() {
	window.location.href="../../../page/approve/sysidentify/identityList.html";
}
// 点击按钮切换大小图片
function changeFile() {
	var bizId = $(this).attr("bizid");
	var fileId = $(this).attr('fileid');
	var image = new Image();
	image.onload = function() {
		$("#bigimage").imageviewer("option", "imagesrc", image.src);
		$("#bigimage").imageviewer("reset");
	}
	//image.src = "../../../upload/showPic.do?fileId=" + fileId;
	image.src = "http://160.99.11.27:9090/upload/showPic.do?fileId=" + fileId;
	$('#fileId').val(fileId);
	$('#bizId').val(bizId);
	var li = $(this).parent();
	li.addClass('selected');
	li.siblings().removeClass('selected');
	// 获取对应图片对应的申请人信息（初始化显示对应的图片审查）
	$.ajax({
		url : "../../../approve/identity/censor/item.do?type=0&bizId=" + bizId,//type=0代表现场身份认证
		dataType : "json",
		type : "post",
		success : function(receive) {
			var data = receive.data;
			var name = data.name || '';
			var cerNo = data.cerNo || '';
			$("#name").text(name);
			$("#codeId").text(cerNo);
			$("#suggestion").text(receive.hisMsg);
			if(data.flag=='1'){
				$("div[name='imgviewer']").imageviewer("option","watermarkurl","../../../static/image/approve/mask-state3.png");
			}else if(data.flag=='2'){
				$("div[name='imgviewer']").imageviewer("option","watermarkurl","../../../static/image/approve/mask-state4.png");
			}else{
				$("div[name='imgviewer']").imageviewer("option","watermarkurl"," ");
			}
		}
	});
}

// 提交审批意见
function submitArroveMsg(state) {
	if (!state) {
		alert("状态不能为空。");
		return;
	}
	var bizId = $("#bizId").val();
	if (!bizId) {
		alert('图片编号不能为空。');
		return;
	}
	var approveMsg = $("#suggestion").val() || '';
	if (!approveMsg && state != '1') {
		alert('审批意见不为空。');
		return;
	}
	if (approveMsg.length > 1200) {
		alert("审查意见长度过长。");
		return;
	}
	//保存图片
	$.ajax({
		url : "../../../approve/identity/censor/save.do",
		type : "post",
		data : {
			bizId : bizId,
			state : state,
			msg : approveMsg
		},
		dataType : "json",
		success : function(response) {
			if (!response) {
				alert('保存失败。');
				return;
			}
			var msg = response.msg || '';
			if (response.state == 'success') {// 保存成功
				$('.selected').attr('class', 'selected');
				if(state==1){
					$("div[name='imgviewer']").imageviewer("option","watermarkurl","../../../static/image/approve/mask-state3.png");
				}
				if(state==2){
					$("div[name='imgviewer']").imageviewer("option","watermarkurl","../../../static/image/approve/mask-state4.png");
				}
			} 
			alert(response.msg);
		},
		error : function(data) {
			if (data.responseText) {
				var error = $.parseJSON(data.responseText);
				alert(error.exceptionMes);
			}
		}
	});
};
// 向右滑动
function slideRight() {
	var left = -(parseInt($("#imgList").css("left")));
	var maxleft = $("#imgList").width() - $(".slide_content").width();
	maxleft = maxleft > 0 ? maxleft : 0;
	var length = 115 * 3;
	if ((left + length) > maxleft) {
		$("#imgList").css('left', -maxleft + 'px');
	} else if ((left + length) < maxleft) {
		$("#imgList").css('left', -(left + length) + 'px');
	} else {

	}
}
//向左滑动
function slideLeft() {
	var left = -(parseInt($('#imgList').css('left'), 10));
	var minleft = 0;
	var length = 115 * 3;
	if (left == 0) {

	} else if ((left - length) < minleft) {
		$('#imgList').css('left', minleft + 'px');
	} else if ((left - length) > minleft) {
		$('#imgList').css('left', -(left - length) + 'px');
	}
}




