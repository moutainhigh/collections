<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@include file="../../include/public_server.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>SWFUpload Demos - Simple Demo</title>
<link href="../css/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="swfupload.js"></script>
<script type="text/javascript" src="swfupload.queue.js"></script>
<script type="text/javascript" src="handler/fileprogress.js"></script>
<script type="text/javascript" src="handler/handlers.js"></script>
<script type="text/javascript">
		var swfu;
		
		var chnlid = 1//getParameter("DocChannelId");
		function renderUploadFlash(cfg){
			var settings = {
				flash_url : "swfupload.swf",
				upload_url: "../../system/import_appendix.jsp?method=upload&ResponseType=2&channelId="+cfg.channelId+"&Type="+cfg.type,
				file_types : cfg.allowExt || "*.*",
				file_types_description : "文件",
				file_upload_limit : 100,
				file_queue_limit : 0,
				custom_settings : {
					progressTarget : "fsUploadProgress",
					cancelButtonId : "btnCancel"
				},
				debug: true,

				// Button settings
				button_image_url: "images/TestImageNoText_65x29.png",
				button_width: "65",
				button_height: "22",
				button_placeholder_id: "spanButtonPlaceHolder",
				button_text: '<span class="theFont">'+cfg.desc+'</span>',
				button_text_style: ".theFont { font-size: 12; }",
				button_text_left_padding: 0,
				button_text_top_padding: 0,
				
				// The event handler functions are defined in handlers.js
				swfupload_preload_handler : preLoad,
				swfupload_load_failed_handler : loadFailed,
				file_queued_handler : fileQueued,
				file_queue_error_handler : fileQueueError,
				file_dialog_complete_handler : fileDialogComplete,
				upload_start_handler : uploadStart,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
				upload_success_handler : uploadSuccess,
				upload_complete_handler : uploadComplete,
				queue_complete_handler : queueComplete	// Queue plugin event
			};
			swfu = new SWFUpload(settings);
		}
		window.onload = function() {
			renderUploadFlash({
				channelId:254,
				desc:"图片文件",
				flag : 20,
				frm : 'fm_pic',
				type:"DOC_APPENDIX_IMAGE_SIZE_LIMIT",
				allowExt : '*.jpg;*.gif;*.png;*.bmp'
			});
		};
	</script>
</head>
<body>


<div id="content">
	<form id="form1" action="index.html" method="post" enctype="multipart/form-data">
			<div class="fieldset flash" id="fsUploadProgress">
			<span class="legend">Upload Queue</span>
			</div>
		<div id="divStatus">0 Files Uploaded</div>
			<div>
				<span id="spanButtonPlaceHolder"></span>
				<input id="btnCancel" type="button" value="Cancel All Uploads" onclick="swfu.cancelQueue();" disabled="disabled" style="margin-left: 2px; font-size: 8pt; height: 29px;" />
			</div>

	</form>
</div>
</body>
</html>