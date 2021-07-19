function renderUploadFlash(cfg){
	var sUrl = "/wcm/app/system/import_appendix.jsp";
	if(cfg.jsessionid){
		sUrl += ";" + cfg.jsessionid.Name + "=" + cfg.jsessionid.Value;
	}
	var settings = {
		flash_url : "/wcm/app/document/swfupload/swfupload.swf",
		upload_url: sUrl+"?method=upload&ResponseType=2&channelId="+cfg.channelId+"&Type="+cfg.type,
		file_types : cfg.allowExt || "*.*",
		file_types_description : "files",
		file_upload_limit : 1,//只允许上传一张图片
		file_queue_limit : 0,
		custom_settings : {
			progressTarget : "fsUploadProgress",
			appendix_flag :cfg.flag
		},
		debug: false,

		// Button settings
		button_image_url: "/wcm/app/document/swfupload/images/ImageNoText.png",
		button_width: "85",
		button_height: "18",
		button_action: SWFUpload.BUTTON_ACTION.SELECT_FILE,//同时选择的文件数量
		button_placeholder_id: cfg.handlerId,
		button_text: '<a class="theFont" href="#">'+cfg.desc+'</a>',
		button_text_style: ".theFont { font-size: 12; background-color:#FFF9D4;}",
		button_text_left_padding: 15,
		button_text_top_padding: -2,
		button_window_mode:"transparent",		
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
	var swfu = new SWFUpload(settings);
}

function preLoad() {
	if (!this.support.loading) {
		alert("You need the Flash Player 9.028 or above to use SWFUpload.");
		return false;
	}
}
function loadFailed() {
	alert("Something went wrong while loading SWFUpload. If this were a real application we'd clean up and then give you an alternative");
}

function fileQueued(file) {
	try {
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setStatus("Pending...");
		progress.toggleCancel(true, this);
	} catch (ex) {
		this.debug(ex);
	}

}

function fileQueueError(file, errorCode, message) {
	try {
		if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
			alert("You have attempted to queue too many files.\n" + (message === 0 ? "You have reached the upload limit." : "You may select " + (message > 1 ? "up to " + message + " files." : "one file.")));
			return;
		}

		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setError();
		progress.toggleCancel(false);

		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			progress.setStatus("File is too big.");
			this.debug("Error Code: File too big, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			progress.setStatus("Cannot upload Zero Byte files.");
			this.debug("Error Code: Zero byte file, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
			progress.setStatus("Invalid File Type.");
			this.debug("Error Code: Invalid File Type, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		default:
			if (file !== null) {
				progress.setStatus("Unhandled Error");
			}
			this.debug("Error Code: " + errorCode + ", File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		}
	} catch (ex) {
        this.debug(ex);
    }
}

function fileDialogComplete(numFilesSelected, numFilesQueued) {
	try {
		/*if (numFilesSelected > 0) {
			document.getElementById(this.customSettings.cancelButtonId).disabled = false;
		}*/
		
		/* I want auto start the upload and I can do that here */
		this.startUpload();
		if(numFilesQueued>0){
			/* 显示进度条 遮布*/
			Element.removeClassName($(this.customSettings.progressTarget),"display-none");
			Element.removeClassName($("maskDiv"),"display-none");
		}
		/* 当上传的大小为0时，numFilesQueued==0，但是仍然要弹出错误信息,而且此时进度条还被隐藏*/
		if(numFilesQueued==0) {
			Element.removeClassName($(this.customSettings.progressTarget),"display-none");
		}
	} catch (ex)  {
        this.debug(ex);
	}
}

function uploadStart(file) {
	try {
		/* I don't want to do any file validation or anything,  I'll just update the UI and
		return true to indicate that the upload should start.
		It's important to update the UI here because in Linux no uploadProgress events are called. The best
		we can do is say we are uploading.
		 */
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setStatus("正在上传...");
		progress.toggleCancel(true, this);
	}
	catch (ex) {}
	
	return true;
}

function uploadProgress(file, bytesLoaded, bytesTotal) {
	try {
		var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);

		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setProgress(percent);
		progress.setStatus("正在上传...");
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadSuccess(file, serverData) {
	var arrResult = serverData.split('<!--##########-->');
	var faultInfo = {
		code		: arrResult[1],
		message		: arrResult[2],
		detail		: arrResult[3],
		suggestion  : arrResult[4]
	}

	try {
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		//faultInfo.message?progress.setError():progress.setComplete();
		progress.setStatus(faultInfo.message || "上传成功.");
		progress.toggleCancel(false);
		if(faultInfo.message)
			progress.setError();
		else{
			__uploadComplete(file,serverData,this);
			progress.setComplete();
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadError(file, errorCode, message) {
	try {
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setError();
		progress.toggleCancel(false);

		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
			progress.setStatus("Upload Error: " + message);
			this.debug("Error Code: HTTP Error, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
			progress.setStatus("Upload Failed.");
			this.debug("Error Code: Upload Failed, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.IO_ERROR:
			progress.setStatus("Server (IO) Error");
			this.debug("Error Code: IO Error, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
			progress.setStatus("Security Error");
			this.debug("Error Code: Security Error, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			progress.setStatus("Upload limit exceeded.");
			this.debug("Error Code: Upload Limit Exceeded, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
			progress.setStatus("Failed Validation.  Upload skipped.");
			this.debug("Error Code: File Validation Failed, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			// If there aren't any files left (they were all cancelled) disable the cancel button
			/*if (this.getStats().files_queued === 0) {
				document.getElementById(this.customSettings.cancelButtonId).disabled = true;
			}*/
			progress.setStatus("Cancelled");
			progress.setCancelled();
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			progress.setStatus("Stopped");
			break;
		default:
			progress.setStatus("Unhandled Error: " + errorCode);
			this.debug("Error Code: " + errorCode + ", File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		}
	} catch (ex) {
        this.debug(ex);
    }
}

function __uploadComplete(file,serverData,swf) {
	
	var arr = serverData.split("#==#");
	var tmpObj = new Object();
	tmpObj['desc'] = file.name;
	tmpObj['src'] = arr[0];
	tmpObj['url'] = '';
	tmpObj['type'] = swf.customSettings.appendix_flag;
	window.uploaded([tmpObj]);
	/*if (this.getStats().files_queued === 0) {
		document.getElementById(this.customSettings.cancelButtonId).disabled = true;
	}*/
}
function uploadComplete(file) {
	/*if (this.getStats().files_queued === 0) {
		document.getElementById(this.customSettings.cancelButtonId).disabled = true;
	}*/
}

// This event comes from the Queue Plugin
function queueComplete(numFilesUploaded) {
	Element.addClassName($("maskDiv"),"display-none");
	//$(this.customSettings.progressTarget)
	/*var status = document.getElementById("divStatus");
	status.innerHTML = numFilesUploaded + " file" + (numFilesUploaded === 1 ? "" : "s") + " uploaded.";*/
}
