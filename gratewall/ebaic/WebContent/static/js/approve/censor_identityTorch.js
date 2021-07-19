$(function() {

	// 身份认证给父弹出的页面让它自动给iframe赋值
	var height = $('body').height() + 140;
	var fram = $('#frametabcontent', window.parent.document);

	if (fram) {
		fram.css('height', height);
	}

	// 获取相关图片
	var gid = jazz.util.getParameter("gid");
	// IdentityApproveContrller
	var url = "../../../approve/identity/censor/load.do?&gid=" + gid;
	$.ajax({
			url : url,
			type : "post",
			dataType : "json",
			success : function(data) {
				if (data.length == 0) {
					alert("没有待审核的图像。");
					return;
				}

				var li = ''
				for (var i = 0; i < data.length; i++) {
					// 状态：-1-未提交；0-待审批；1-正常；2-审批不通过
					li += "<li >"
							+ "<img class='smallfile' src='../../../upload/showPic.do?fileId="
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
			}// end of success
		});// end of ajax

	// 保存图片审查信息
	$("#pass").on('click', function() {
		submitArroveMsg('1', 'pass');
	});
	// 审查图片未通过
	$("#nopass").on('click', function() {
		submitArroveMsg('2', 'noPass');
	});
	
	//绑定按钮事件slideLeft,slideRight
	$(".slide_left").on('click', slideLeft);
	$(".slide_right").on('click', slideRight);
});

// 点击按钮切换大小图片
function changeFile() {
	var bizId = $(this).attr("bizid");
	var fileId = $(this).attr('fileid');
	var image = new Image();
	image.onload = function() {
		$("#bigimage").imageviewer("option", "imagesrc", image.src);
		$("#bigimage").imageviewer("reset");
	}
	/* image.src = "../../../static/image/img/icon/smileFace.png"; */
	image.src = "../../../upload/showPic.do?fileId=" + fileId;
	$('#fileId').val(fileId);
	$('#bizId').val(bizId);

	// 获取对应图片对应的申请人信息（初始化显示对应的图片审查）
	$.ajax({
		// url:"/approve/identity/censor/item.do?bizId="+bizId,
		url : "../../../approve/identity/censor/item.do?bizId=" + bizId,
		dataType : "json",
		type : "post",
		success : function(receive) {
			var data = receive.data;
			var name = data.name || '';
			var cerNo = data.cerNo || '';
			// 如果姓名和证件号码不为空
			if (name) {
				if (cerNo) {
					var enname = encodeURI(name);
					//var enname = encodeURI(name);
					var picUrl = '../../../../apply/rodimus/idPic/show.do?name='+ enname + '&cerNo=' + cerNo;
					identityPicUrl = picUrl;
				} else {
					identityPicUrl = '';
				}
			} else {
				identityPicUrl = '';
			}

			$("#name").text(name);
			$("#codeId").text(cerNo);
			// $("#photo").attr("src",identityPicUrl);
		}
	});
}

// 提交审批意见
function submitArroveMsg(state) {
	if (!state) {
		alert("状态不能为空。");
		return;
	}
	var gid = jazz.util.getParameter('gid');
	if (!gid) {
		alert('批次号不能为空。');
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
				//alert(msg);
				$('.selected').attr('class', 'selected');
				$("div[name='imgviewer']").imageviewer("option","watermarkurl","../../../static/image/approve/mask-state" + state+ ".png");
				//$("div[name='imgviewer']").imageviewer("option","watermarkurl","../../../static/image/approve/mask-state3.png");
				//$(this).next().find('img').click();// 跳转到下一个图片
				
				$('.selected').attr('class', 'selected state' + state);
				if (state == '3') {
					$(".selected").next().find('img').click();
				}
				
				$("#suggestion").val("");// 清空当前输入的审查意见
				return;
			} else {
				$("div[name='imgviewer']").imageviewer("option","watermarkurl","../../../static/image/approve/mask-state" + state+ ".png");
				//$("div[name='imgviewer']").imageviewer("option","watermarkurl","../../../static/image/approve/mask-state4.png");
				//alert(msg);
			}
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

// 向左滑动
function slideLeft() {
	var left = -(parseInt($("#imgList").css("left")));
	var minleft = 0;
}




