<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link href="../statics/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="../statics/inner/uploader.css" rel="stylesheet" type="text/css" />
<script src="../statics/js/jquery-V30.min.js" type="text/javascript"></script>
<script src="../statics/js/tzcommon.js" type="text/javascript"></script>
<link href="../statics/webuploader/webuploader.css" rel="stylesheet" type="text/css" />
<script src="../statics/webuploader/webuploader.js" type="text/javascript"></script>
<!-- https://www.cnblogs.com/52lnamp/p/9232919.html -->
<script type="text/javascript" charset="utf-8" src="../statics/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="../statics/ueditor/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="../statics/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" charset="utf-8" src="../statics/js/tzcommon.js"></script>
<style>
.wu-example {
    position: relative;
    padding: 45px 15px 15px;
    margin: 15px 0;
    background-color: #fafafa;
    box-shadow: inset 0 3px 6px rgba(0, 0, 0, .05);
    border-color: #e5e5e5 #eee #eee;
    border-style: solid;
    border-width: 1px 0;
}
.wu-example:after {
    content:"";
    position: absolute;
    top: 15px;
    left: 15px;
    font-size: 12px;
    font-weight: bold;
    color: #bbb;
    text-transform: uppercase;
    letter-spacing: 1px;
}
</style>
</head>
<body>

	<div style="width: 95%; margin-top: 15px;" class="form-horizontal">
		<div class="form-group">
			<label for="inputEmail3" class="col-sm-1 control-label">标题：</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="title" />
			</div>
		</div>
		<div class="form-group">
			<label for="inputEmail3" class="col-sm-1 control-label">内容简要：</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="contentMeta" />
			</div>
		</div>
		
		<!-- <div class="form-group">
			<label for="inputPassword3" class="col-sm-1 control-label">内容简介</label>
			<div class="col-sm-10">
				<input type="test" class="form-control" id="contentmeta" />
			</div>
		</div> -->
		<div class="form-group">
			<div class="col-sm-offset-1 col-sm-10">
				<div class="checkbox">
					<label>
						<input type="checkbox" id="isHasThumb" />
						图
					</label>
					<div id="isDisplay" style="visibility: hidden">
						<div id="uploader-demo">
							<!--用来存放item-->
							<div id="fileList" class="uploader-list"></div>
							<div id="filePicker">选择图片</div>
						</div>
					</div>
				</div>

			</div>
		</div>

		<!-- <div class="form-group">
			<label for="inputPassword3" class="col-sm-1 control-label">视频或音频文件附件</label>
			<div class="col-sm-10">
				<div id="uploader" class="wu-example">
					用来存放文件信息
					<div id="thelist" class="uploader-list"></div>
					<div class="btns">
						<div id="picker">选择文件</div>
						<button id="ctlBtn" class="btn btn-default">开始上传</button>
					</div>
				</div>
			</div>
		</div> -->


		<div class="form-group">
			<label for="inputPassword3" class="col-sm-1 control-label">文章正文</label>
			<div class="col-sm-10">
				<script id="editor" type="text/plain" name="gdesc" style="width: 100%; height: 350px; padding-top: 10px"></script>
			</div>
		</div>
	</div>







	<div class="submit-footerbar">
		<label class="control-label"></label>
		<div class="controls">
			<input type="hidden" id="Remark" name="Remark" value="PageAdmin网站管理系统">
			<button type="button" class="btn btn-primary ui-pagePost" onclick="publish(1)">发布</button>
			<button type="button" class="btn btn-primary ui-pagePost" onclick="publish(0)">保存</button>
		</div>
	</div>
	<script>
		$(function() {
			$("#isHasThumb").change(function() {
				var _this = $(this);
				var flag = _this.prop("checked");
				if (flag) {
					$("#isDisplay").css("visibility", "visible")
				} else {
					$("#isDisplay").css("visibility", "hidden")
				}
			})
		})

		function loadDocument() {
			var docid = getQueryString('docid');
			//if (docid != null) {
				$.ajax({
					url : "../getDetail.do",
					type : "post",
					dataType : 'json',
					data : {
						docid : docid
					},
					beforeSend : function() {

					},
					success : function(data) {
						var dataStr = data.ret;
						$("#title").val(dataStr.title);
						var ue = UE.getEditor('editor');
						ue.addListener("ready", function() {
							//赋值
							//  ue.setContent("要传入的值");
							//var str = dataStr.contents.replace(/\n/g,"<br/>")
							//var str = dataStr.contentToEd;
							var str = dataStr.contentsPlainText;
							ue.setContent(str);
							//取值
							//var content = ue.getContent();
						})
					}
				})
			//}
		}

		loadDocument(); 

		//https://www.jianshu.com/p/06616c5527f6
		//https://www.jb51.net/article/43175.htm
		//实例化编辑器
		var editor = UE.getEditor('editor', {});
		function publish(isPubOrSave) {
			var docid = getQueryString('docid');
			var cid = getQueryString('type');
			var contents = editor.getContent();
			var contentsPlainText = editor.getPlainTxt();
			var isCheck = $("#isHasThumb").prop("checked");
			var fileList = $("#fileList div[class='info']").html();
			var contentMeta = $("#contentMeta").val();
			var isHaveThumb = 0;
			if (isCheck) {
				isHaveThumb = 1;
			}

			var title = $("#title").val();
			if (title.length == 0) {
				alert("请输入标题");
			} else {
				$.ajax({
					url : "../save.do",
					type : "post",
					dataType : 'json',
					data : {
						channelid : cid,
						docid : docid,
						contents : contents,
						contentsPlainText : contentsPlainText,
						title : title,
						isPubOrSave : isPubOrSave,
						isHaveThumb:isHaveThumb,
						contentmeta:contentMeta
					},
					beforeSend : function() {

					},
					success : function(data) {
						var msg = data.msg;
						alert("保存成功");
					}
				})
			}

		}
	</script>






	<script type="text/javascript">
		var BASE_URL = '/webuploader';
		// 文件上传https://www.jianshu.com/p/68366f924c78

		jQuery(function() {
			var $ = jQuery, $list = $('#thelist'), $btn = $('#ctlBtn'), state = 'pending', uploader;

			uploader = WebUploader.create({

				// 不压缩image
				resize : false,

				// swf文件路径
				swf : BASE_URL + '/js/Uploader.swf',

				// 文件接收服务端。
				server : '../springUpload.do',

				// 选择文件的按钮。可选。
				// 内部根据当前运行是创建，可能是input元素，也可能是flash.
				pick : '#picker'
			});

			// 当有文件添加进来的时候
			uploader.on('fileQueued', function(file) {
				$list.append('<div id="' + file.id + '" class="item">' + '<h4 class="info">' + file.name + '</h4>' + '<p class="state">等待上传...</p>' + '</div>');
			});

			// 文件上传过程中创建进度条实时显示。
			uploader.on('uploadProgress', function(file, percentage) {
				var $li = $('#' + file.id), $percent = $li.find('.progress .progress-bar');

				// 避免重复创建
				if (!$percent.length) {
					$percent = $('<div class="progress progress-striped active">' + '<div class="progress-bar" role="progressbar" style="width: 0%">' + '</div>' + '</div>').appendTo($li).find('.progress-bar');
				}

				$li.find('p.state').text('上传中');

				$percent.css('width', percentage * 100 + '%');
			});

			uploader.on('uploadSuccess', function(file) {
				$('#' + file.id).find('p.state').text('已上传');
			});

			uploader.on('uploadError', function(file) {
				$('#' + file.id).find('p.state').text('上传出错');
			});

			uploader.on('uploadComplete', function(file) {
				$('#' + file.id).find('.progress').fadeOut();
			});

			uploader.on('all', function(type) {
				if (type === 'startUpload') {
					state = 'uploading';
				} else if (type === 'stopUpload') {
					state = 'paused';
				} else if (type === 'uploadFinished') {
					state = 'done';
				}

				if (state === 'uploading') {
					$btn.text('暂停上传');
				} else {
					$btn.text('开始上传');
				}
			});

			$btn.on('click', function() {
				if (state === 'uploading') {
					uploader.stop();
				} else {
					uploader.upload();
				}
			});
		});

		// 图片上传demo
		jQuery(function() {
			var $ = jQuery, $list = $('#fileList'),
			// 优化retina, 在retina下这个值是2
			ratio = window.devicePixelRatio || 1,

			// 缩略图大小
			thumbnailWidth = 100 * ratio, thumbnailHeight = 100 * ratio,

			// Web Uploader实例
			uploader;

			// 初始化Web Uploader
			uploader = WebUploader.create({

				// 自动上传。
				auto : true,

				// swf文件路径
				swf : BASE_URL + '/js/Uploader.swf',

				// 文件接收服务端。
				server : '../springUpload.do',

				// 选择文件的按钮。可选。
				// 内部根据当前运行是创建，可能是input元素，也可能是flash.
				pick : '#filePicker',

				// 只允许选择文件，可选。
				accept : {
					title : 'Images',
					extensions : 'gif,jpg,jpeg,bmp,png',
					mimeTypes : 'image/*'
				}
			});

			// 当有文件添加进来的时候
			uploader.on('fileQueued', function(file) {
				var $li = $('<div id="' + file.id + '" class="file-item thumbnail">' + '<img>' + '<div class="info">' + file.name + '</div>' + '</div>'), $img = $li.find('img');

				$list.append($li);

				// 创建缩略图
				uploader.makeThumb(file, function(error, src) {
					if (error) {
						$img.replaceWith('<span>不能预览</span>');
						return;
					}

					$img.attr('src', src);
				}, thumbnailWidth, thumbnailHeight);
			});

			// 文件上传过程中创建进度条实时显示。
			uploader.on('uploadProgress', function(file, percentage) {
				var $li = $('#' + file.id), $percent = $li.find('.progress span');

				// 避免重复创建
				if (!$percent.length) {
					$percent = $('<p class="progress"><span></span></p>').appendTo($li).find('span');
				}

				$percent.css('width', percentage * 100 + '%');
			});

			// 文件上传成功，给item添加成功class, 用样式标记上传成功。
			uploader.on('uploadSuccess', function(file) {
				$('#' + file.id).addClass('upload-state-done');
				var url = location.href;
				var docid=  getQueryString("docid");
				var fileName = file.name;
				$.ajax({
					url:"../updateThumb.do",
					type:"post",
					dataType:'json',
					data:{
						docId:docid,
						fileName:fileName
					},
					beforeSend:function(){
						
					},
					success:function(data){
						console.log(data)
					}
				});
			});

			// 文件上传失败，现实上传出错。
			uploader.on('uploadError', function(file) {
				var $li = $('#' + file.id), $error = $li.find('div.error');

				// 避免重复创建
				if (!$error.length) {
					$error = $('<div class="error"></div>').appendTo($li);
				}

				$error.text('上传失败');
			});

			// 完成上传完了，成功或者失败，先删除进度条。
			uploader.on('uploadComplete', function(file) {
				$('#' + file.id).find('.progress').remove();
			});
		});
	</script>
</body>
</html>