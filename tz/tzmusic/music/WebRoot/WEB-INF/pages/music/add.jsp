<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="/WEB-INF/tlds/tz.tld" prefix="tz" %>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加音乐</title>
<%@include  file="/WEB-INF/common/common.jsp"%>
<link href="${basePath}/js/upload/upload.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${basePath}/js/upload/tz_upload.js"></script>  
<script type="text/javascript" src="${basePath}/js/music/tz_index.js"></script>
<script type="text/javascript" src="${basePath}/js/ckeditor/ckeditor.js"></script>  
<style type="text/css">
body{background: #C3D2D5}
.ml_icon{ background-image:url(${basePath}/images/ml_icon.png); background-repeat:no-repeat;}
a{ text-decoration:none;}
a:hover{ text-decoration:none;}
.wth652{ width:652px; padding:15px; background:#eaebec;}
.wth622{ width:622px; padding:0px 15px 15px 45px; background:#eaebec;}
.borderbot {
border-bottom: 1px solid #eaebec;
padding-bottom: 26px;
padding-top: 14px;
}

#p_label{height: 30px;}
#add_content{ width:1040px; margin:0 auto; font-size:12px;padding-top: 50px;}
#add_content .con_bt{ width:100%; padding-top:10px; padding-bottom:20px;}
#add_content .con_bt a{ display:block; width:100px; height:40px; margin-left:12px; background:#3b4047; color:#ffffff;}
#add_content .con_bt a:hover{ background:#28b779;}
#add_content .con_bt span{ display:block; width:14px; height:14px; background-position:-75px -27px; margin-left:10px; margin-top:13px; float:left;}
#add_content .con_bt p{ float:left; line-height:38px; padding-left:6px;}
#add_content .con_left{ width:220px; margin-left:12px; float:left;}
#add_content .conl_nr{ width:212px; height:230px; border:4px dotted #eaebec; -moz-border-radius:8px;-webkit-border-radius:8px;border-radius: 8px; position:relative;}
#add_content .conl_nr .pic_area{ width:100%; height:230px; background:url(${basePath}/images/pic_bg.png) no-repeat 33px 33px;}
#add_content .conl_nr .up_btn{  height:30px; position:absolute; bottom:20px; left:50%; margin-left:-45px; z-index:9;}
#add_content .conl_nr .up_btn a{ display:block; width:90px; height:30px; background:#28b779; color:#ffffff;}
#add_content .conl_nr .up_btn a:hover{ background:#1f989b;}
#add_content .conl_nr .up_btn span{ display:block; width:14px; height:14px; background-position:-58px -26px; margin-left:10px; margin-top:7px; float:left;}
#add_content .conl_nr .up_btn p{ line-height:30px; padding-left:6px; float:left;}

#add_content .con_right{ width:760px; margin-left:30px; float:left;}
#add_content .conr_nr{ width:100%; height:auto; margin-bottom:26px; color:#565656;}
#add_content .conr_nr .text{ background:#f7f7f7; border:1px solid #eaebec; text-indent:12px; float:left;}
#add_content .conr_nr .text1{ background:#ffffff; border:1px solid #dddddd; text-indent:12px; float:left;}
#add_content .conr_nr .kc_name{ width:300px; height:36px; *line-height:36px;}
#add_content .conr_nr .kc_infor{ width:680px; height:168px;}
#add_content .conr_nr .kc_infor1{ width:652px; height:130px;}
#add_content .conr_nr .kc_infor2{ width:622px; height:130px;}
#add_content .conr_nr span{ display:block; float:left;}
#add_content .conr_nr .star{ padding-left:10px; padding-top:9px; color:#ff3000;}
#add_content .conr_nr .choose{ width:80px; height:30px; line-height:30px; background:#1f989b; color:#ffffff; text-align:center; float:left;}
#add_content .conr_nr .star_all{ padding-left:12px; padding-top:8px; float:left;}
#add_content .conr_nr .star_all a{ display:block; width:15px; height:14px; background-position:-131px -27px; margin-left:5px; float:left;}
#add_content .conr_nr .star_all a.select{ background-position:-178px 0px;}
#add_content .conr_nr .add_bar{ display:block;height:30px; background:#3b4047; color:#ffffff; float:right;}
#add_content .conr_nr .add_bar:hover{ background:#28b779;}
#add_content .conr_nr .add_bar span{ display:block; width:14px; height:14px; background-position:-75px -27px; margin-left:10px; margin-top:8px; float:left;}
#add_content .conr_nr .add_bar p{ float:left; line-height:30px; padding-left:6px;}
#add_content .conr_nr .zj_name{ width:100px; height:36px; *line-height:36px;}
#add_content .conr_nr .zj_smname{ width:480px; height:36px; *line-height:36px;}
#add_content .conr_nr .zj_smsmname{ width:450px; height:36px; *line-height:36px;}
#add_content .conr_nr .add_icon,#add_content .conr_nr .delet,#add_content .conr_nr .add_nr{ display:block; width:36px; height:36px; background:#c0c0c0; margin-left:4px; float:right;}
#add_content .conr_nr .add_icon:hover{ background:#28b779;}
#add_content .conr_nr .add_icon span{ display:block; width:14px; height:14px; background-position:-75px -27px; margin-left:12px; margin-top:11px;}
#add_content .conr_nr .delet:hover{ background:#ff3000;}
#add_content .conr_nr .delet span{ display:block; width:16px; height:17px; background-position:-92px -23px; margin-left:11px; margin-top:9px;}
#add_content .conr_nr .add_nr:hover{ background:#1f989b;}
#add_content .conr_nr .add_nr span{ display:block; width:17px; height:17px; background-position:-111px -23px; margin-left:10px; margin-top:9px;}
#add_content .conr_nr .submit{ width:80px; height:30px; border:0px; background:#28b779; color:#ffffff; margin-right:12px; float:left; cursor:pointer;}
#add_content .conr_nr .submit:hover,#add_content .conr_nr .cancle:hover{ background:#3b4047;}
#add_content .conr_nr .cancle{ width:80px; height:30px; border:0px; background:#f08c0a; color:#ffffff; float:left; cursor:pointer;}
.pic_area img{width:100%;height: 72%;}
.btn_ufile {
background: #1F989B;
padding: 9px 14px;
color: #fff;
text-decoration: none;
position: relative;
top: 8px;
left: 8px;
}
.wth682{margin-top: 15px;}
#progressBar{width: 480px;height: 30px;}
#upload_box{position: fixed;top:40%;left: 40%;}
</style>
</head>

<body>

<c:if test="${user.id!=1}">
   	<script type="text/javascript">
   		window.location.href = "/error.jsp";
   	</script>
</c:if> 
 <c:if test="${user.id==1}"> 
<!-- 头部 -->
<%@include  file="/WEB-INF/common/header.jsp"%>  
<!-- <div id="upload_box"> -->
<!-- 	<progress id="progressBar" value="0" max="100"></progress> <span id="percentage"></span> -->
<!-- </div> -->
<div id="add_content">
	<form id="period_form" method="post">
	<input type="hidden" id="period_id"   value="${period.id}" name="id" />	
     <div class="con_bt" style="visibility: hidden;"><a href="#"><span class="ml_icon"></span><p>添加音乐</p></a></div>
     <div class="con_left">
          <div class="conl_nr">
               <div class="pic_area" data-img="${period.img}" id="simg"><c:if test="${not empty period.img}"><img alt="" src="${basePath}/${period.img}"></c:if></div>
               <input type="hidden" id="simginp" value="${period.img}" name="img"/>
               <div class="up_btn"><span id="tmupload"></span></div>
               <div style="text-align: center;margin-top: 8px;font-size: 14px;color: #fff">建议大小：640 x 440</div>
          </div>
          
          <div class="conl_nr" style="margin-top: 50px;">
               <div class="pic_area" data-img="${period.background}" id="s_background"><c:if test="${not empty period.background}"><img alt="" src="${basePath}/${period.background}"></c:if></div>
               <input type="hidden" id="sbackground" value="${period.background}" name="background"/>
               <div class="up_btn"><span id="tmupload23"></span></div>
               <div style="text-align: center;margin-top: 8px;font-size: 14px;color: #fff">建议大小：1920 x 1080</div>
          </div>
     </div>
    
     <div class="con_right">
          <div class="conr_nr">
               <input type="text" class="text kc_name"   name="period" id="p_period" placeholder="请输入期刊数..." value="${period.period}" /><span class="star">*</span>
               <div class="clearfix"></div>
          </div>
          <div class="conr_nr">
               <input type="text" class="text kc_name" name="title" id="p_title" value="${period.title}" placeholder="请输入期刊名称..." /><span class="star">*</span>
            		  分类：<select style="padding: 6px;" name="musicType.id" id="p_type">
	               	<option value="1" ${period.musicType.id==1?"selected='selected'":""}>摇滚</option>
	               	<option value="2" ${period.musicType.id==2?"selected='selected'":""}>流行</option>
	               	<option value="3" ${period.musicType.id==3?"selected='selected'":""}>重金属</option>
               </select>
               <div class="clearfix"></div>
          </div>
          <div class="conr_nrs">
               <textarea id="p_desc"  class="text kc_infor" name="description" placeholder="请输入期刊描述..." >${period.description}</textarea><span class="star">*</span>
               <div class="clearfix"></div>
          </div>
          
          <div class="conr_nr">
               <div class="wth682 borderbot">
               <div class="choose">标签：</div>
               <div class="star_all" style="position: relative;top: -8px;"><input type="text" name="label" value="${period.label}" placeholder="请输入期刊标签..."  id="p_label"></div>
               <div class="clearfix"></div>
               </div>
          </div>
          
          <div class="conr_nr">
               <div class="wth682">
	               <input name="" type="button" value="完成" data-opid="${peroid.id}" id="submitperiod" onclick="tz_savePeroid(this)" class="submit" />
	               <c:if test="${not empty id}"><input name="" type="button" onclick="tz_showview(this)" value="预览" class="cancle" /></c:if>
	               <input name="" type="button" value="取消" style="margin-left: 10px"  class="cancle" />
               </div>
          </div>  
          
          
          <c:if test="${not empty id}">
	          <div class="conr_nr">
	               <div class="wth682 borderbot">
	               <a href="#" class="add_bar"><span id="tmupload2"></span></a>
	               <div class="clearfix"></div>
	               </div>
	          </div>
	          
	          <div class="conr_nr" id="tz_mp3box">
	          	<c:forEach items="${period.musics}" var="music">
					<div class="wth682 m_items" data-opid="${music.id}" data-sizestring="" data-size="${music.timelength}" data-url="${music.path}">
						<input name="m_name" onblur='tm_updatemusic(this)' value="${music.title}" type="text" class="text zj_name" style="width: 202px" placeholder="音乐名称"><span class="star">*</span> 
						<input name="m_article"  onblur='tm_updatemusic(this)' value="${music.article}" type="text" class="text zj_name" placeholder="专辑"><span class="star">*</span>
						<input name="m_singer" onblur='tm_updatemusic(this)' value="${music.singer}" type="text" class="text zj_name" placeholder="演唱者" value="未知"><span class="star">*</span>
						<input name="m_time" onblur='tm_updatemusic(this)' value="${music.timer}" type="text" class="text zj_name" placeholder="时间"><span class="star">*</span>
						<a href="javascript:void(0);" class="btn_ufile" onclick="openBrowse(this)">上传</a> 
						<a href="javascript:void(0);" class="delet" data-opid="${music.id}" onclick="tz_deletemusic(this)" title="删除"><span class="ml_icon"></span></a> 
						<a href="javascript:void(0);" class="add_nr" data-opid="${music.id}" onclick="tz_parsemusic(this)" title="解析mp3"><span class="ml_icon"></span></a>
						<img src="${basePath}/${music.img}" width="40" height="36" data-img="${music.img}" class="fl m_img">
						<div class="clearfix"></div>
					</div>
				</c:forEach>
	          </div> 
		     </c:if>
	     </div>
     <div class="clearfix"></div>
     <input type="file" id="file" name="file" onchange="uploadFile(this)" style="display:none"/> 
    </form>
</div>

<div style="height: 200px;width: 100%;background:transparent;">
</div>
<script type="text/javascript">
	
	
	
	var $fileObj = null;
	function openBrowse(obj){ 
		$fileObj = $(obj);
		var ie=navigator.appName=="Microsoft Internet Explorer" ? true : false; 
		if(ie){ 
			document.getElementById("file").click(); 
			document.getElementById("filename").value=document.getElementById("file").value;
		}else{
			var a=document.createEvent("MouseEvents");//FF的处理 
			a.initEvent("click", true, true);  
			document.getElementById("file").dispatchEvent(a); 
		} 
	} 
	
	
	// 	Attributes	设置或返回文件或文件夹的属性
	// 	DateCreated	返回指定文件或文件夹的创建时间
	// 	DateLastAccessed	返回最近访问文件或文件夹的创建时间
	// 	DateLastModified	返回最后修改指定文件和文件夹的日期和日期
	// 	Drive	返回指定文件或文件夹所在的驱动器的驱动器号
	// 	Name	设置或返回文件或文件夹的名称
	// 	ParentFolder	返回指定文件或文件夹的父文件夹对象
	// 	Path	返回指定文件或文件夹或驱动器的路径
	// 	ShortName	返回短名称
	// 	ShortPath	返回短路径
	// 	Size	对于文件,以字节为单位返回指定文件的大小.
	// 	对于文件夹,以字节为单位返回文件夹中包含的所有子文件夹中的所有文件和子文件夹的大小
	// 	Type	返回文件或文件夹的信息.
	function uploadFile() {
	    var fileObj = document.getElementById("file").files[0]; // js 获取文件对象
	    var type = fileObj.type;
// 	    if(type!="image/jpeg"){
// 	    	loading("请选择图片文件!",2);
// 	    	return;
// 	    }
	    // FormData 对象
	    var form = new FormData();
	    form.append("doc", fileObj);                           // 文件对象
	    // XMLHttpRequest 对象
	    var xhr = new XMLHttpRequest();
	    var dirPath = "mp3/"+$("#p_period").val();
	    var img = $fileObj.parents(".m_items").find(".m_img").data("img");
	    img = img.replace(dirPath+"/","");
	    xhr.open("post", "http://localhost/upload?dir="+dirPath+"&oldName="+img, true);
	    xhr.onreadystatechange = function(){
			if(xhr.readyState==4 && xhr.status==200){//状态4和200代表和服务器端交互成功
				var data = eval("("+xhr.responseText+")");
				$fileObj.parents(".m_items").find(".m_img").data("img",data.url).attr("src","/"+data.url+"?"+new Date().getTime());
				$fileObj.parents(".m_items").find("input[name='m_name']").trigger("blur");
			}
		};

// 	    xhr.onload = function (data) {
// 	    	var text = data.srcElement.responseText;
// 	    	alert(text);
// 	    };
	    xhr.upload.addEventListener("progress", progressFunction, false);
	    xhr.send(form);
	};
	
	
	
    function progressFunction(evt) {
        var progressBar = document.getElementById("progressBar");
        var percentageDiv = document.getElementById("percentage");
        if (evt.lengthComputable) {
            progressBar.max = evt.total;
            progressBar.value = evt.loaded;
            percentageDiv.innerHTML = Math.round(evt.loaded / evt.total * 100) + "%";
        }
    } ; 

    
    
    
	//添加和修改，他们之间区别只在一个id之别
	$(function(){
		//编辑器的初始化
		CKEDITOR.replace("p_desc");	
		/*上传组件调用方法*/
		var dirPath = "mp3/"+$("#p_period").val();
		//上传期刊封面
		$.tmUpload({"params":{"dir":dirPath,"oldName":$("#simg").data("img").replace(dirPath+"/","")},"fileTypes":"*.jpg",callback:function(data,file,tid){
			if(tid=="tmupload"){
				var jdata = eval("("+data+")");
				$("#simg").data("img",jdata.url).html("<img src='/"+jdata.url+"?"+new Date().getTime()+"'>");
				$("#simginp").val(jdata.url);
				$("#submitperiod").trigger("click");
			}
		}});
		//背景图	
		$.tmUpload({btnId:"tmupload23","params":{"dir":"mp3/"+$("#p_period").val(),"oldName":$("#s_background").data("img").replace(dirPath+"/","")},"fileTypes":"*.jpg",callback:function(data,file,tid){
			if(tid=="tmupload23"){
				var jdata = eval("("+data+")");
				$("#s_background").data("img",jdata.url).html("<img src='/"+jdata.url+"?"+new Date().getTime()+"'>");
				$("#sbackground").val(jdata.url);
				$("#submitperiod").trigger("click");
			}
		}});
		
		$.tmUpload({"params":{"dir":"mp3/"+$("#p_period").val()},btnId:"tmupload2","fileTypes":"*.mp3",callback:function(data,file,tid){
			if(tid!="tmupload2")return;
			var jdata = eval("("+data+")");
			var params = {
				"title":jdata.name,
				"article":jdata.name,
				"singer":"未知",
				"timelength":jdata.size,
				"period.id":$("#period_id").val(),
				"path":jdata.url
			};
			
			$.ajax({
				type:"post",
				url:"/tzmusic/save",
				data:params,
				success:function(data){
					//就应该去保存到数据库中了......
					var cdata = eval("("+data+")");
		            $("#tz_mp3box").append("<div class='wth682 m_items' data-opid='"+cdata.id+"' data-sizestring='"+jdata.sizeString+"'  data-size='"+jdata.size+"' data-url='"+jdata.url+"'>"+
			         "      <input name='m_name' onblur='tm_updatemusic(this)' value='"+jdata.name+"' type='text' class='text zj_name' style='width: 202px' placeholder='音乐名称' /><span class='star'>*</span>"+
			         "      <input name='m_article' onblur='tm_updatemusic(this)' type='text' class='text zj_name' placeholder='专辑' /><span class='star'>*</span>"+
			         "      <input name='m_singer' onblur='tm_updatemusic(this)' type='text' class='text zj_name' placeholder='演唱者' value='未知'/><span class='star'>*</span>"+
			         "      <input name='m_time' onblur='tm_updatemusic(this)' type='text' class='text zj_name' placeholder='时间' /><span class='star'>*</span>"+
			         "		<a href='javascript:void(0);' class='btn_ufile' onclick='openBrowse(this)'>上传</a>  "+
			         "		<a href='javascript:void(0);' class='delet' data-opid="+cdata.id+" onclick='tz_deletemusic(this)' title='删除'><span class='ml_icon'></span></a> "+
					 "		<a href='javascript:void(0);' class='add_nr' data-opid="+cdata.id+" onclick='tz_parsemusic(this)' title='解析mp3'><span class='ml_icon'></span></a>"+
					 "		<img src='' width='40' height='36' data-img='' class='fl m_img'>"+
			         "      <div class='clearfix'></div>"+
		             "</div>");
				}
			});//ajax
			
         }});
	});
	

    
 
    
    
	
	/*保存修改期刊*/
	function tz_savePeroid(obj){
		$("#p_desc").val(getEditorHtml("p_desc"));
		var params = $("#period_form").serializeArray();
		loading("数据提交中....");
		var url = "/period/save";
		var pid = $("#period_id").val();
		if(isNotEmpty(pid)){
			url = "/period/update";
		}
		$.ajax({
			type:"post",
			url:url,
			data:params,
			success:function(data){
				if(data && data!="logout"){
					loading("操作成功...",2);
					if(isEmpty(pid)){
						window.location.href = "/period/editmusic/"+data.id;
					}
				}else{
					if(data=="logout"){
						window.location.href = "/login";
					}else{
						loading("操作失败...",2);
					}
				}
			}
		});
	}
	
	/*删除音乐*/
	function tz_deletemusic(obj){
		var opid = $(obj).data("opid");
		$.tzAlert({content:"您确定要删除吗?",callback:function(ok){
			if(ok){
				$.ajax({
					type:"post",
					url:"/tzmusic/delete/"+opid,
					success:function(data){
						if(data=="success"){
							$(obj).parents(".m_items").fadeOut("slow",function(){
								$(this).remove();
							});
						}else if(data=="fail"){
							loading("删除失败...",2);
						}else if(data=="logout"){
							window.location.href = "/login";
						}
					}
				});
			}
		}});
	};
	
	var timer = null;
	function tm_updatemusic(obj){
		var $parent = $(obj).parents(".m_items");
		var opid = $parent.data("opid");
		clearTimeout(timer);
		var title = $parent.find("input[name='m_name']").val(); 
		var article = $parent.find("input[name='m_article']").val(); 
		var singer = $parent.find("input[name='m_singer']").val(); 
		var timer = $parent.find("input[name='m_time']").val(); 
		var img = $parent.find(".m_img").data("img");
		var params = {
			"id":opid,
			"title":title,
			"article":article,
			"singer":singer,
			"img":img,
			"timer":timer
		};
		timer = setTimeout(function(){
			$.ajax({
				type:"post",
				data:params,
				url:"/tzmusic/update",
				success:function(data){
					if(data=="success"){
						loading("更新成功!",2);
					}else if(data=="fail"){
						loading("更新失败...",2);
					}else if(data=="logout"){
						window.location.href = "/login";
					}
				}
			});
		}, 200);
	};
	
	var ptimer = null;
	function tz_parsemusic(obj){
		var $parent = $(obj).parents(".m_items");
		var path =  $parent.data("url");
		clearTimeout(ptimer);
		ptimer = setTimeout(function(){
			loading("解析中...",true);
			$.ajax({
				type:"post",
				data:{"path":path},
				url:"/parse/mp3",
				success:function(data){
					if(data && data!="logout"){
						loading("解析成功...",1);
						$parent.find("input[name='m_name']").val(data.songTitle); 
						$parent.find("input[name='m_article']").val(data.albumTitle); 
						$parent.find("input[name='m_singer']").val(data.artist); 
						$parent.find("input[name='m_time']").val(data.timeLine); 
						$parent.find("input[name='m_name']").trigger("blur");
					}else{
						if(data=="logout"){
							window.location.href = "/login";
						}else{
							loading("操作失败...",2);
						}
					}
				}
			});
		}, 200);
		
	};
	
	function tz_showview(obj){
		var pid = $("#period_id").val();
		window.open("/music/"+pid);
	}
</script>
</c:if>
</body>
</html>