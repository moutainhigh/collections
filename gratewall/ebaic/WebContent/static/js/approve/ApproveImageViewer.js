// 页面初始化。
$(function() {
	initPicData();
	eventBinding();
	
	
});




/**
 * 事件绑定。
 */
function eventBinding() {
	// 保存图片审查信息
	$("#pass").on('click', function() {
		submitArroveMsg('3', 'pass');
	});
	// 审查未通过
	$("#nopass").on('click', function() {
		submitArroveMsg('4', 'noPass');
	});
	// 忽略该待审查图片
	$("#ignore").on('click', function() {
		submitArroveMsg('0', 'ignore');
	});
	
	
	$(".slide_left").on('click', sllideLeft);
	$(".slide_right").on('click', sllideRight);
	
	//$(".slide_left").on('click', sliderLeftSel);
	//$(".slide_right").on('click', sliderRightSel);

}

/**
 * 初始化页面数据。
 */
function initPicData() {

	// 身份认证给父弹出的页面让它自动给iframe赋值
	var height = $('body').height() + 140;
	var fram = $('#frametabcontent', window.parent.document);

	if (fram) {
		fram.css('height', height);
	}

	var categoryId = jazz.util.getParameter('categoryId');
	$.ajax({
		url : "../../../approve/pic/getListByCategory.do?"
				+ edit_primaryValues(),
		type : 'post',
		async : true,
		dataType : 'json',
		success : function(data) {
			if (data.length == 0) {
				jazz.info("当前类别没有待审核的图像材料。");
				return;
			}
			var li = '';
			for (var i = 0; i < data.length; i++) {
				var select = "";
				if (i == 0) {
					select = "selected";
				}
				li += "<li class='state" + data[i].state + " " + select + "'>"
						+ "<img class='smallfile' fileid='" + data[i].fileId
						+ "'" + " fid='" + data[i].fId + "' state='"
						+ data[i].state + "' "
						+ "src='../../../upload/showPic.do?fileId="
						+ data[i].thumbFileId + "'></li>";

			}
			$("#imgList").append(li);
			var width = 115 * (data.length);
			$("#imgList").css('width', width);
			$(".smallfile").on('click', changeFile);
			// 初始化第一张显示图片
			var file = $("#imgList").find("img:eq(0)");
			//$(".slide_left").css({"opacity" : "0.1"});// 初始化显示第一张图片，默认将前一页的按钮的灰度变低，最好是一些明显的颜色

			file.click();
		}// end of success function
	});// end of ajax
}

function edit_primaryValues() {
	return "&gid=" + jazz.util.getParameter('gid') + "&categoryId="+ jazz.util.getParameter('categoryId');
};

/**
 * 提交审批意见。
 * 
 * @params iconName pass,noPass,ignore
 */
function submitArroveMsg(state) {
	
	opener.updateImageState(); //调用从父窗口更新签字盖章图片状态的方法
	
	
	if (!state) {
		alert("状态不能为空。");
		return;
	}

	var gid = jazz.util.getParameter('gid') || '';
	if (!gid) {
		alert("批次号不能为空。");
		return;
	}
	var fId = $("#fId").val() || '';
	if (!fId) {
		alert("图片编号不能为空。");
		return;
	}
	var approveMsg = $("#suggestion").val() || '';
	if (!approveMsg && state != '0' && state != '3') {
		alert("审查意见不能为空。");
		return;
	}
	if (approveMsg.length > 1200) {
		alert("审查意见长度过长。");
		return;
	}


	
	$.ajax({
		url : "../../../approve/imageApprove/saveResult.do",
		type : "post",
		data : {
			gid : gid,
			fId : fId,
			state : state,
			approveMsg : approveMsg
		},
		dataType : "json",
		success : function(data) {
			if (data && data.data) {

				$("div[name='imgviewer']").imageviewer("option","watermarkurl","../../../static/image/approve/mask-state" + state+ ".png");
				$('.selected').attr('class', 'selected state' + state);
				if (state == '3') {
					$(".selected").next().find('img').click();
				}
				$("#suggestion").val("");
			}
		},
		error : function(data) {
			if (data.responseText) {
				var error = $.parseJSON(data.responseText);
				jazz.info(error.exceptionMes);
				return;
			} else {
				alert(data);
			}
		}// end of error
	});
};

// 点击按钮切换大小图
function changeFile() {
	var fileId = $(this).attr('fileid');
	var fId = $(this).attr('fid');
	var image = new Image();
	image.onload = function() {
		$("#bigimage").imageviewer("option", "imagesrc", image.src);
		$("#bigimage").imageviewer("reset");
	}
	image.src = "../../../upload/showPic.do?fileId=" + fileId;
	$('#fileId').val(fileId);
	$('#fId').val(fId);
	var li = $(this).parent();
	li.addClass('selected');
	li.siblings().removeClass('selected');

	// 获取对应图片信息
	$.ajax({
				url : "../../../approve/pic/getPicInfo.do",
				type : "post",
				data : {
					fId : fId
				},
				dataType : "json",
				success : function(data) {
					$(".imgPostion").css("visibility", "hidden");

					// $("#historySuggestion").html(data.hisApproveMsg[0] ||'');
					 //$("#suggestion").val(data.approveMsg|| '');//图像审查时，在审批审查未通过的图片，填写审查意见后，在申请平台再次重新上传图片后，提交到审批系统，查看该图像时，上一次的审查意见应该显示在“历史审查意见”框内

					$("#historySuggestion").html(data.approveMsg || '');
					
					$("div[name='imgviewer']").imageviewer("option","watermarkurl","../../../static/image/approve/mask-state"+ (data.state || '') + ".png");
					$('.selected').attr('class', 'selected state' + data.state);
					
					
					if (data) {
						var content = $.extend(true, {}, data.data || {},data.ent || {});
						content = $.extend(true, {}, content, data.req || {});
						if (data.mbr) {
							content.mbr = data.mbr;
						}
						
						if (data.dataType) {
							var id = data.dataType;
							$('#' + id).css('display', 'block');
							$('#' + id).siblings('.data_detail').css('display','none');
						
							var info = $("#" + id).find('.detailInfo');
							info.find('span').each(function(index, item) {
												var name = $(item).attr('name');
												var val = content[name];
												if (typeof (val) == 'string') {
													$(item).html(val).parent().css('display','block');
												} else if (typeof (val) == 'object') {
													var htm = '';
													if (name == 'mbr') {
														var first = true;
														for (var i = 0, len = val.length; i < len; i++) {
															if (first) {
																first = false;
																htm += val[i].name+ '('+ val[i].position+ ')';
															} else {
																htm += '、'+ val[i].name+ '('+ val[i].position+ ')';
															}
														}
													} else {
														var first = true;
														var text = $(item).attr('text');
														for (var i = 0, len = val.length; i < len; i++) {
															if (first) {
																first = false;
																htm += val[i][text];
															} else {
																htm += '、'+ val[i][text];
															}
														}
													}
													if (htm) {
														$(item).html(htm).parent().css('display','block');
													} else {
														$(item).parent().css('display','none');
													}

												} else {
													$(item).parent().css('display', 'none');
												}

											});
							info.find('img').each(function(index, item) {
								$(item).hide();
							});
						}
					}
				}
			});
};

function sllideRight() {
	var left = -(parseInt($('#imgList').css('left'), 10));
	var maxleft = $('#imgList').width() - $('.slide_content').width();
	maxleft = maxleft > 0 ? maxleft : 0;
	var length = 115 * 3;
	if ((left + length) > maxleft) {
		$('#imgList').css('left', -maxleft + 'px');
	} else if ((left + length) < maxleft) {
		$('#imgList').css('left', -(left + length) + 'px');
	} else {

	}
}







function sllideLeft() {
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


