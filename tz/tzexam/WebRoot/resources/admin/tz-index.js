/*****************************************************文件上传的代码***************************************/
$(function(){
	$.tzUpload({
		url:basePath+"/upload/file",
		targetId:"uploadBtn",
		size:"10 MB",
		type:"*.rar;*.zip",
		single:true,
		callback:function(data){
			var json = eval("("+data+")");
			var opid = $("#subjectdir").val();
			var fid = $("#subjectbox >.on").find("li.on").data("fid");
			var params = {
				name:json.oldname,	
				ext:json.ext,	
				size:json.size,
				description:json.name,	
				subjectId:opid,	
				folderId:fid,
				url:json.url
			};
			
			$.ajax({
				type:"post",
				url:basePath+"/admin/exam/save",
				data:params,
				success:function(data){
					$('#examlist').append("<li data-opid='"+data+"'>"+
					"	<span class='icon'><i class='fa fa-file-archive-o'></i></span>"+
					"	<span class='op'>"+
					"	<a href='"+basePath+json.url+"' target='_blank' class='download'><i class='fa fa-download'></i></a>"+
					"	<a href='javascript:void(0);' class='looksee'><i class='fa fa-street-view'></i></a></span>"+
					"	<div class='clearfix' style='padding-top:3px;'><span class='cnt'>"+json.oldname+"</span>"+
					"	<span class='cnt' style='font-size:12px;'>柯柯&nbsp;1秒前</span></div>"+
					"</li>");
				}
			});
		}
	});
});	

/*****************************************************文件上传的代码 end***************************************/

/*****************************************************根据学科id查询作业信息***************************************/
function tzLoadingExam(subjectId,fid,obj){
	if($(obj).hasClass("on")){
		return;
	}
	$(obj).addClass("on").siblings().removeClass("on");
	$.ajax({
		type:"post",
		url:basePath+"/admin/exam/list",
		data:{"subjectId":subjectId,"folderId":fid},
		success:function(data){
			$("#examlist").html(data);
		}
	});
};

$(function(){
	var $li = $("#subjectbox").children().eq(0).find(".cul").find("li").eq(0);
	var sid = $li.data("sid");
	var fid = $li.data("fid");
	tzLoadingExam(sid,fid);
});
/*****************************************************根据学科id查询作业信息 end***************************************/



/*****************************************************新建文件和文件的处理，拉动的处理***************************************/
window.onload = function(){
	resizeMain();
	this.onresize = function(){
		resizeMain();
	};

	function resizeMain(){
		var w = window.innerWidth;
		var h = window.innerHeight;
		var cw = w *0.3;
		var ccw = w *0.7;
		$("#percentbar").width (w-320);
		$(".center").width(cw).height(h-130);
		$(".contentbox").height(h-130).width(ccw).css("marginLeft",cw+1);
	}
};

$(function(){
	$(".expand").on("click",function(){
		$(this).find("i").toggleClass("fa-square-o fa-square");
		var left = $(".silder").offset().left;
		$(".silder").animate({left:left==0 ?-300:0});
		$(".rightbox").animate({marginLeft:left==0 ?0:301});
		$(".setting").animate({marginRight:left==0 ?10:310});
	});

	$(".center,.contentbox").mCustomScrollbar({
		theme:"minimal",
		callbacks:{
			onScrollStart:function(){},
			onScroll:function(){},
			onTotalScroll:function(){
				
			},
			onTotalScrollBack:function(){},
			onTotalScrollOffset:0,
			whileScrolling:false,
			whileScrollingInterval:1
		}
	});
});

//js检索学科消息
var uhtml = $("#subjectbox").html();
function tzsearch(obj){
	var value = obj.value;
	if(isNotEmpty(value)){
		var $lis =  $("#subjectbox").find("li").filter(function(){
			var text = $(this).text().toLowerCase();
			if(text.indexOf(value.toLowerCase())!=-1){
				return $(this);
			}
		});
		$("#subjectbox").prepend($lis.css("color","red"));
	}else{
		$("#subjectbox").html(uhtml);
	}
};

//查询学科信息
function tz_sersubject(obj){
	var key = $("#key").val();
	if(isEmpty(key)){
		loading("请输入关键字...",3);
		$("#key").focus();
		return;
	}
	$.ajax({
		type:"post",
		url:basePath+"/admin/subject/loadData",
		data:{name:key},
		success:function(data){
			if(data!="empty"){
				var jsonArr = eval("("+data+")");
				var len = jsonArr.length,i=0;
				var html = "";
				for(;i<len;i++){
					html+="<li class='"+(i==0?'on':'')+"'><i class='fa fa-home'></i>"+jsonArr[i].name+"</li>";
				}
				$("#subjectbox").html(html);
			}
		}
	});	
};

$(function(){
	//切换
	$("#subjectbox").find(".link").on("click",function(e){
		setDirPath( $(this).parent())
		$(this).next().slideToggle().end().parent().addClass("on").siblings().removeClass("on").find(".cul").slideUp();
	});
	
	setDirPath($("#subjectbox").find(".on"));
	
	//添加目录
	$("#subjectbox").find(".addFolder").on("click",function(e){
		//获取当前对象
		var $this = $(this);
		var $ul = $this.parent().next();
		var open = $ul.is(":visible");
		if(!open){
			$this.parent().trigger("click");		
		}
		
		var len =  $ul.find("input").length;
		if(len==0){
			var sid = $this.data("sid");
			$this.parent().next().prepend("<li><input type='text' data-sid='"+sid+"'  onblur='tz_saveFolder(this)' autofocus='autofocus'/><a href='javascript:void(0);' class='fr delicon' onclick='tz_deleteFolder(this)'><i class='fa fa-remove'></i></a></li>");
		}
		stopBubble(e);
	});
});

function setDirPath($obj,opid){
	var opid = $obj.data("opid");
	$("#subjectdir").val(opid);
}


//保存文件夹
function tz_saveFolder(obj){
	var $this = $(obj);
	var sid = $this.data("sid");
	var value = $this.val().trim();
	if(isNotEmpty(value)){
		$.ajax({
			type:"post",
			url:basePath+"/admin/folder/save",
			data:{sid:sid,name:value},
			beforeSend:function(){loading("请稍后，数据保存中...");},
			//timeout:4000,
			error:function(){
				loading("保存失败...",4);
			},
			success:function(data){
				loading("创建【"+data.name+"】成功...",4);
				$this.parent().data({sid:sid,fid:data.id}).html("<span>"+data.name+"</span> "+
					"<a href='javascript:void(0);' data-fid='"+data.id+"' class='fr delicon'  onclick='tz_deleteFolder(this)'><i class='fa fa-remove'></i></a>"+
					"<a href='javascript:void(0);' data-fid="+data.id+" class='fr delicon'  onclick='tz_editFolder(this)'><i class='fa fa-edit'></i></a>");
			}
		});
	}
};

//删除文件夹
function tz_deleteFolder(obj){
	var $this = $(obj);
	var fid = $this.data("fid");
	if(fid){
		$.ajax({
			type:"post",
			url:basePath+"/admin/folder/delete/"+fid,
			beforeSend:function(){loading("请稍后，数据操作中...");},
			error:function(){
				loading("删除失败...",4);
			},
			success:function(data){
				loading("删除成功...",4);
				$this.parent().slideUp(function(){
					$(this).remove();
				});
			}
		});
	}else{
		$this.parent().slideUp(function(){
			$(this).remove();
		});
	}
};

/*编辑文件夹*/
function tz_editFolder(obj){
	var $this = $(obj);
	var fid = $this.data("fid");
	var $span = $this.prev().prev();
	$span.html("<input type='text' data-fid='"+fid+"' value='"+$span.text()+"'   onblur='tz_updateFolder(this)' autofocus='autofocus'/>");
	$span.find("input").select();
};

/*更新文件夹*/
function tz_updateFolder(obj){
	var $this = $(obj);
	var fid = $this.data("fid");
	var value = $this.val();
	if(isNotEmpty(value)){
		$.ajax({
			type:"post",
			url:basePath+"/admin/folder/update/"+fid,
			data:{name:value},
			beforeSend:function(){loading("请稍后，数据更新中...");},
			error:function(){
				loading("更新失败...",4);
			},
			success:function(data){
				loading("更新成功...",4);
				$this.parent().text(value);
			}
		});
	}
};
/*****************************************************新建文件和文件的处理，拉动的处理 end***************************************/