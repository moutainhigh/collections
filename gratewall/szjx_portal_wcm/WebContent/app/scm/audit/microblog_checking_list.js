window.onload = function(){
	var page = 1;
	var i = 8; //每版放4个图片
	setTimeout(function(){$(window.parent.document).find("#autoMessage").hide();},600);
	//向后 按钮
	$("span.createNext").click(function(){ //绑定click事件
		var $parent = $(this).parents("div.v_show");//根据当前点击元素获取到父元素
		var $v_show = $parent.find("div.v_content_list"); //寻找到“图片内容展示区域”
		var $v_content = $parent.find("div.v_content"); //寻找到“图片内容展示区域”外围的DIV元素
		var v_width = $v_content.width();
		var len = $v_show.find("li").length;
		var page_count = Math.ceil(len / i) ; //只要不是整数，就往大的方向取最小的整数
		if( !$v_show.is(":animated") ){ //判断“图片内容展示区域”是否正在处于动画
			if( page == page_count ){ //已经到最后一个版面了,如果再向后，必须跳转到第一个版面。
				$v_show.animate({ left : '0px'}, "slow"); //通过改变left值，跳转到第一个版面
				page = 1;
			}else{
				$v_show.animate({ left : '-='+v_width }, "slow"); //通过改变left值，达到每次换一个版面
					page++;
			}
		}
	});

	//往前 按钮
	$("span.createPrev").click(function(){
		var $parent = $(this).parents("div.v_show");//根据当前点击元素获取到父元素
		var $v_show = $parent.find("div.v_content_list"); //寻找到“图片内容展示区域”
		var $v_content = $parent.find("div.v_content"); //寻找到“图片内容展示区域”外围的DIV元素
		var v_width = $v_content.width();
		var len = $v_show.find("li").length;
		var page_count = Math.ceil(len / i) ; //只要不是整数，就往大的方向取最小的整数
		if( !$v_show.is(":animated") ){ //判断“图片内容展示区域”是否正在处于动画
			if( page == 1 ){ //已经到第一个版面了,如果再向前，必须跳转到最后一个版面。
				$v_show.animate({ left : '-='+v_width*(page_count-1) }, "slow");
				page = page_count;
			}else{
				$v_show.animate({ left : '+='+v_width }, "slow");
				page--;
			}
		}
	});
	setHeight();
};
//点击通过，隐藏输入框和微博内容，设置显示区高度。
function passClick(that,num,end){
	for(var i=0;i<end;i++){
		if(i == num){
			$("#returnContainer"+i).hide();
		}else{
			$("#returnContainer"+i).slideUp();
			$("#checkingContainer"+i).slideUp();
		}
	}
	var left = getLeft(that);
	$("#checkingContainer"+num).find("div:eq(0)").css("left",left-5);
	$("#checkingContainer"+num).slideToggle();
}
 // 获取元素的横坐标
 function getLeft(e){
	 var offset = e.offsetLeft;
	 if (e.offsetParent != null)
	 offset += getLeft(e.offsetParent);
	 return offset;
 }
//返工
function returnClick(that,num,end){
	for(var i=0;i<end;i++){
		if(i == num){
			$("#checkingContainer"+i).hide();
		}else{
			$("#checkingContainer"+i).slideUp();
			$("#returnContainer"+i).slideUp();
		}
	}
	var left = getLeft(that);
	$("#returnContainer"+num).find("div:eq(0)").css("left",left-5);
	$("#returnContainer"+num).slideToggle();
}
//删除
function deleteMicroMessage(nCurrContentId){
	if(confirm("该微博内容您还没有给出审核结果，您确定要删除该微博记录与审核记录吗？")){
		$.ajax({
			type:"post",
			data:{CurrContentId:nCurrContentId},
			dataType:"text",
			url:"deleteWorkFlow.jsp",
			success:function(){
				window.location.reload(); 
				/**/
			},
			error:function(){
				alert("审核工作流流转失败！");
			}
	});
	}
}

//通过
function pass(flowDocId,DescContent,ContentId,eleId){
	$.ajax({
			type:"post",
			data:{IsPass:1,FlowDocId:flowDocId,PostDesc:DescContent,ContentId:ContentId},
			dataType:"text",
			url:"confirmWorkFlow.jsp",
			success:function(data){
				if($.trim(data)==1){
					$("#"+eleId).slideUp();
					setTimeout(function(){window.location.reload();},400);
				}else{
					alert("操作失败：" + $.trim(data));
				} 
			},
			error:function(){
				alert("审核工作流流转失败！");
			}
	});
}
//点击返工，隐藏数据显示
function rework(flowDocId,DescContent,ContentId){
	$.ajax({
			type:"post",
			data:{IsPass:0,FlowDocId:flowDocId,PostDesc:DescContent,ContentId:ContentId},
			dataType:"text",
			url:"confirmWorkFlow.jsp",
			success:function(data){
				if($.trim(data)!=1){
					alert("操作失败：" + $.trim(data));
				} 
				window.location.reload();
			},
			error:function(){
				alert("审核工作流流转失败！");
			}
	});
}

//编辑
function editClick(num){
	// 1 获取微博内容
	var checkingTtext=$("#contentMicroMessage"+num).find("div.checkingMessage").html();
	// 2 显示编辑微博的弹出框
	$("#editContainerExtend1").show();
	// 3 设置输入框中的内容
	$("#editContainerExtend1").find("textarea").val($.trim(checkingTtext));
	// 4 改变可以输入的字数
}
function closeDialog(){
	$("#editContainerExtend1").hide();
}
function createUserChangeState(num){
	for(var i=1;i<14;i++){
		if($("#user_h"+i).html==null){
			return;
		};
		if(num==i){
			if($("#user_h"+i).hasClass("grayPic")){
				$("#user_h"+i).removeClass('grayPic');
				$("#user_l"+i).removeClass('grayPic');
				$("#user_t"+i).removeClass('hidden');				
			}else{
				$("#user_h"+i).addClass('grayPic');
				$("#user_l"+i).addClass('grayPic');
				$("#user_t"+i).addClass('hidden');
			}
		}
	}
}
function getTextareaVal(){
	$("#ejectDiv1").show();
	$("#checkContent1").html($("#myExpress1").val()+"<a href='#' title='测试成功！' style='color:red;text-decoration:none;'>提示</a>");
}

function hideCheck(num){
	$("#ejectDiv1").hide();
}
//获取离顶部的距离
function getY(element){
   var y=0;
   for(var e=element;e;e=e.offsetParent){
     y+=e.offsetTop;
   }
   for(var e=element.parentNode;e&&e!=document.body;e=e.parentNode){
      if(e.scrollTop)y-=e.scrollTop;
   }
   return y; 
}
//获取坐标
function pageXY(elem){
    var rect = elem.getBoundingClientRect();
    var scrollTop = window.scrollTop || (document.documentElement && document.documentElement.scrollTop) || document.body.scrollTop || 0;
    var scrollLeft = window.scrollLeft || (document.documentElement && document.documentElement.scrollLeft) || document.body.scrollLeft || 0;
 
    var html = document.documentElement || document.getElementsByTagName('html')[0];
 
    //修复ie6 7 下的浏览器边框也被算在 boundingClientRect 内的 bug
    var deviation = html.getBoundingClientRect();
    //修复 ie8 返回 -2 的 bug
    deviation = { //FF 不允许修改返回的对象
        left:   deviation.left < 0 ? 0 : deviation.left,
        top:    deviation.top < 0 ? 0 : deviation.top
    };
	 var cc = rect.left + scrollLeft - deviation.left;
     var dd = rect.top + scrollTop - deviation.top;
	 var bodyHeight=$("body").height();
	 if(dd>180){
		$("#editContainerExtend1").css("top",dd-180);
	 }else{
		$("#editContainerExtend1").css("top",dd);
	 }
	 if(bodyHeight-dd<180){
		$("#editContainerExtend1").css("top",bodyHeight-390);
	 } 
}