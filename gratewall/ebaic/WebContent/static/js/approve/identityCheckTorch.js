$(function() {

	
	// 身份认证给父弹出的页面让它自动给iframe赋值
	var height = $('body').height() + 140;
	var fram = $('#frametabcontent', window.parent.document);

	if (fram) {
		fram.css('height', height);
	}
    
	// 获取要进行身份核查人的相关信息
	var gid = jazz.util.getParameter("gid");
	var url = "../../../approve/identity/censor/loadCheckUser.do?&gid=" + gid;
	$.ajax({
			url : url,
			type : "post",
			dataType : "json",
			success : function(data) {
				if (data&&data.checkUser.length==0) {
					alert("没有身份待审核用户。");
					return;
				}
				var checkUsers = data.checkUser;
				var li = '';
				//默认显示第一个用户
				var firstUserName = '';
				var firstUserCerNo = '';
				var firstUserType = '';
				for (var i = 0; i < checkUsers.length; i++) {
					//0:法人，1：申请人，2：自然人股东，3：非自然人股东的法定代表人
					var userName= checkUsers[i].name;
					var cerNo =checkUsers[i].cerNo;
					var userType = checkUsers[i].userType;
					var flag = checkUsers[i].flag;
					if(i==0){
						firstUserName = userName;
						firstUserCerNo = cerNo;
						firstUserType = userType;
					}
					if(checkUsers[i].userType=='0'){
						if(flag==1){
							li +="<li id='user"+cerNo+"' class='approvePass'>";
						}else if(flag==2){
							li +="<li id='user"+cerNo+"' class='approveNotPass'>";
						}else{
							li +="<li id='user"+cerNo+"'>";
						}
						li += "<span cerno="+cerNo+" usertype="+userType+" username="+userName+" id='user"+i+"'>"+userName+"(法定代表人)</span></li>";
						$("#user"+i).live('click', function() {
							fun_getUserPic($(this).attr("userName"),$(this).attr("cerno"),$(this).attr("usertype"));
						});
					}
					if(checkUsers[i].userType=='1'){
						if(flag==1){
							li +="<li id='user"+cerNo+"' class='approvePass'>";
						}else if(flag==2){
							li +="<li id='user"+cerNo+"' class='approveNotPass'>";
						}else{
							li +="<li id='user"+cerNo+"'>";
						}
						li += "<span cerno="+cerNo+" usertype="+userType+" username="+userName+" id='user"+i+"' style='cursor: pointer;'>"+checkUsers[i].name+"(申请人)</span></li>";
						$("#user"+i).live('click', function() {
							fun_getUserPic($(this).attr("userName"),$(this).attr("cerno"),$(this).attr("usertype"));
						});
					}
					if(checkUsers[i].userType=='2'){
						if(flag==1){
							li +="<li id='user"+cerNo+"' class='approvePass'>";
						}else if(flag==2){
							li +="<li id='user"+cerNo+"' class='approveNotPass'>";
						}else{
							li +="<li id='user"+cerNo+"'>";
						}
						li += "<span cerno="+cerNo+" usertype="+userType+" username="+userName+" id='user"+i+"' style='cursor: pointer;'>"+checkUsers[i].name+"(自然人股东)</span></li>";
						$("#user"+i).live('click', function() {
							fun_getUserPic($(this).attr("userName"),$(this).attr("cerno"),$(this).attr("usertype"));
						});
					}
					if(checkUsers[i].userType=='3'){
						if(flag==1){
							li +="<li id='user"+cerNo+"' class='approvePass'>";
						}else if(flag==2){
							li +="<li id='user"+cerNo+"' class='approveNotPass'>";
						}else{
							li +="<li id='user"+cerNo+"'>";
						}
						li += "<span cerno="+cerNo+" usertype="+userType+" username="+userName+" id='user"+i+"' style='cursor: pointer;'>"+checkUsers[i].name+"(法人股东)</span></li>";
						$("#user"+i).live('click', function() {
							fun_getUserPic($(this).attr("userName"),$(this).attr("cerno"),$(this).attr("usertype"));
						});
					}
				}
				$("#checkUserList").append(li);
				fun_getUserPic(firstUserName,firstUserCerNo,firstUserType);
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
	
	//绑定按钮事件slideLeft,slideRight
	$(".slide_left").on('click', slideLeft);
	$(".slide_right").on('click', slideRight);
});
function fun_getUserPic(userName,cerNo,userType){
	// 获取要进行身份核查人的相关信息
	var url = "../../../approve/identity/censor/loadSelectUserPic.do";
	$.ajax({
			url : url,
			type : "post",
			dataType : "json",
			data:{
				"userName":userName,
				"cerNo":cerNo
			},
			success : function(data) {
				if(userType=='0'){
					$("#userTitleType").text("法定代表人");
				}
				if(userType=='1'){
					$("#userTitleType").text("申请人");
				}
				if(userType=='2'){
					$("#userTitleType").text("自热人股东");
				}
				if(userType=='3'){
					$("#userTitleType").text("法人股东");
				}
				if (data&&data.length==0) {
					$("#imgList").children("li").remove();
					$("#bigimage").imageviewer("option", "imagesrc", " ");
					$("div[name='imgviewer']").imageviewer("option","watermarkurl"," ");
					$("#suggestion").val("");
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
				var contactName ="";
				var contactCerno="";
				var mobile="";
				for (var i = 0; i < data.length; i++) {
					if(!contactName){
						contactName = data[i].contactName;
					}
					if(!contactCerno){
						contactCerno = data[i].contactCerno;
					}
					if(!mobile){
						mobile = data[i].mobile;
					}
					// 状态：-1-未提交；0-待审批；1-正常；2-审批不通过
					li += "<li><img class='smallfile' src='http://160.99.11.27:9090/upload/showPic.do?fileId="
						//li += "<li><img class='smallfile' src='../../../upload/showPic.do?fileId="
							+ data[i].thumbFileId + "' " + "fileid='"
							+ data[i].fileId + "' bizid='" + data[i].bizId
							+ "'></li>";
				}
				if(userType=='3'){
					$(".entInfo").show();
					$(".personInfo").hide();
					$("#entName").text(userName);
					$("#uniScid").text(cerNo);
					$("#contactName").text(contactName);
					$("#contactCerno").text(contactCerno);
					$("#mobile").text(mobile);
				}else{
					$(".personInfo").show();
					$(".entInfo").hide();
					$("#name").text(userName);
					$("#codeId").text(cerNo);
				}
				$("#imgList").append(li);
				var width = 115 * (data.length);
				$("#imgList").css('width', width);
				$(".smallfile").on('click', changeFile);
				// 初始化第一张显示图片
				var file = $("#imgList").find("img:eq(0)");
				file.click();
			}
	});
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
		url : "../../../approve/identity/censor/item.do?type=0&bizId=" + bizId,
		dataType : "json",
		type : "post",
		success : function(receive) {
			var data = receive.data;
			if(data){
				var name = data.name || '';
				var cerNo = data.cerNo || '';
				$("#name").text(name);
				$("#codeId").text(cerNo);
				$("#suggestion").val(receive.hisMsg);
				if(data.flag=='1'){
					$("div[name='imgviewer']").imageviewer("option","watermarkurl","../../../static/image/approve/mask-state3.png");
				}else if(data.flag=='2'){
					$("div[name='imgviewer']").imageviewer("option","watermarkurl","../../../static/image/approve/mask-state4.png");
				}else{
					$("div[name='imgviewer']").imageviewer("option","watermarkurl"," ");
				}
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
	var name = $("#name").text();
	var codeId = $("#codeId").text();
	//保存图片
	$.ajax({
		url : "../../../approve/identity/censor/save.do",
		type : "post",
		async:false,
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
			if (response.state == 'success') {// 保存成功
				$('.selected').attr('class', 'selected');
				if(state==1){
					$("div[name='imgviewer']").imageviewer("option","watermarkurl","../../../static/image/approve/mask-state3.png");
				}
				if(state==2){
					$("div[name='imgviewer']").imageviewer("option","watermarkurl","../../../static/image/approve/mask-state4.png");
				}
				alert(response.msg);
				$.ajax({
					url:"../../../approve/identity/censor/loadSelectUserPic.do",
					type:"post",
					dataType:"json",
					data:{
						"userName":name,
						"cerNo":codeId
					},
					success:function(data){
						if(data&&data.length>0){
							var passFlag = true;
							for (var i = 0; i < data.length; i++) {
								if(data[i].flag!='1'){
									passFlag = false;
									break;
								}
							}
							if(passFlag){
								$("#user"+codeId).attr("class",'approvePass');
							}else{
								$("#user"+codeId).attr("class",'approveNotPass');
							}
						}
					}
				});
				return;
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




