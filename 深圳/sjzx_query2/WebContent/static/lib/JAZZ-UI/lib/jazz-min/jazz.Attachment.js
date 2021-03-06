/**SWFUpload */
var SWFUpload;var swfobject;if(SWFUpload==undefined){SWFUpload=function(settings){this.initSWFUpload(settings)}}SWFUpload.prototype.initSWFUpload=function(userSettings){try{this.customSettings={};this.settings={};this.eventQueue=[];this.movieName="SWFUpload_"+SWFUpload.movieCount++;this.movieElement=null;SWFUpload.instances[this.movieName]=this;this.initSettings(userSettings);this.loadSupport();if(this.swfuploadPreload()){this.loadFlash()}this.displayDebugInfo()}catch(ex){delete SWFUpload.instances[this.movieName];throw ex;}};SWFUpload.instances={};SWFUpload.movieCount=0;SWFUpload.version="2.5.0 2010-01-15 Beta 2";SWFUpload.QUEUE_ERROR={QUEUE_LIMIT_EXCEEDED:-100,FILE_EXCEEDS_SIZE_LIMIT:-110,ZERO_BYTE_FILE:-120,INVALID_FILETYPE:-130};SWFUpload.UPLOAD_ERROR={HTTP_ERROR:-200,MISSING_UPLOAD_URL:-210,IO_ERROR:-220,SECURITY_ERROR:-230,UPLOAD_LIMIT_EXCEEDED:-240,UPLOAD_FAILED:-250,SPECIFIED_FILE_ID_NOT_FOUND:-260,FILE_VALIDATION_FAILED:-270,FILE_CANCELLED:-280,UPLOAD_STOPPED:-290,RESIZE:-300,USER_DEFINED:-500};SWFUpload.FILE_STATUS={QUEUED:-1,IN_PROGRESS:-2,ERROR:-3,COMPLETE:-4,CANCELLED:-5};SWFUpload.UPLOAD_TYPE={NORMAL:-1,RESIZED:-2};SWFUpload.BUTTON_ACTION={SELECT_FILE:-100,SELECT_FILES:-110,START_UPLOAD:-120,JAVASCRIPT:-130,NONE:-130};SWFUpload.CURSOR={ARROW:-1,HAND:-2};SWFUpload.WINDOW_MODE={WINDOW:"window",TRANSPARENT:"transparent",OPAQUE:"opaque"};SWFUpload.RESIZE_ENCODING={JPEG:-1,PNG:-2};SWFUpload.completeURL=function(url){try{var path="",indexSlash=-1;if(typeof(url)!=="string"||url.match(/^https?:\/\//i)||url.match(/^\//)||url===""){return url}indexSlash=window.location.pathname.lastIndexOf("/");if(indexSlash<=0){path="/"}else{path=window.location.pathname.substr(0,indexSlash)+"/"}return path+url}catch(ex){return url}};SWFUpload.onload=function(){};SWFUpload.prototype.initSettings=function(userSettings){this.ensureDefault=function(settingName,defaultValue){var setting=userSettings[settingName];if(setting!=undefined){this.settings[settingName]=setting}else{this.settings[settingName]=defaultValue}};this.ensureDefault("upload_url","");this.ensureDefault("preserve_relative_urls",false);this.ensureDefault("file_post_name","Filedata");this.ensureDefault("post_params",{});this.ensureDefault("use_query_string",false);this.ensureDefault("requeue_on_error",false);this.ensureDefault("http_success",[]);this.ensureDefault("assume_success_timeout",0);this.ensureDefault("file_types","*.*");this.ensureDefault("file_types_description","All Files");this.ensureDefault("file_size_limit",0);this.ensureDefault("file_upload_limit",0);this.ensureDefault("file_queue_limit",0);this.ensureDefault("flash_url","swfupload.swf");this.ensureDefault("flash9_url","swfupload_fp9.swf");this.ensureDefault("prevent_swf_caching",true);this.ensureDefault("button_image_url","");this.ensureDefault("button_width",1);this.ensureDefault("button_height",1);this.ensureDefault("button_text","");this.ensureDefault("button_text_style","color: #000000;font-size: 16pt;");this.ensureDefault("button_text_top_padding",0);this.ensureDefault("button_text_left_padding",0);this.ensureDefault("button_action",SWFUpload.BUTTON_ACTION.SELECT_FILES);this.ensureDefault("button_disabled",false);this.ensureDefault("button_placeholder_id","");this.ensureDefault("button_placeholder",null);this.ensureDefault("button_cursor",SWFUpload.CURSOR.ARROW);this.ensureDefault("button_window_mode",SWFUpload.WINDOW_MODE.WINDOW);this.ensureDefault("debug",false);this.settings.debug_enabled=this.settings.debug;this.settings.return_upload_start_handler=this.returnUploadStart;this.ensureDefault("swfupload_preload_handler",null);this.ensureDefault("swfupload_load_failed_handler",null);this.ensureDefault("swfupload_loaded_handler",null);this.ensureDefault("file_dialog_start_handler",null);this.ensureDefault("file_queued_handler",null);this.ensureDefault("file_queue_error_handler",null);this.ensureDefault("file_dialog_complete_handler",null);this.ensureDefault("upload_resize_start_handler",null);this.ensureDefault("upload_start_handler",null);this.ensureDefault("upload_progress_handler",null);this.ensureDefault("upload_error_handler",null);this.ensureDefault("upload_success_handler",null);this.ensureDefault("upload_complete_handler",null);this.ensureDefault("mouse_click_handler",null);this.ensureDefault("mouse_out_handler",null);this.ensureDefault("mouse_over_handler",null);this.ensureDefault("debug_handler",this.debugMessage);this.ensureDefault("custom_settings",{});this.customSettings=this.settings.custom_settings;if(!!this.settings.prevent_swf_caching){this.settings.flash_url=this.settings.flash_url+(this.settings.flash_url.indexOf("?")<0?"?":"&")+"preventswfcaching="+new Date().getTime();this.settings.flash9_url=this.settings.flash9_url+(this.settings.flash9_url.indexOf("?")<0?"?":"&")+"preventswfcaching="+new Date().getTime()}if(!this.settings.preserve_relative_urls){this.settings.upload_url=SWFUpload.completeURL(this.settings.upload_url);this.settings.button_image_url=SWFUpload.completeURL(this.settings.button_image_url)}delete this.ensureDefault};SWFUpload.prototype.loadSupport=function(){this.support={loading:swfobject.hasFlashPlayerVersion("9.0.28"),imageResize:swfobject.hasFlashPlayerVersion("10.0.0")}};SWFUpload.prototype.loadFlash=function(){var targetElement,tempParent,wrapperType,flashHTML,els;if(!this.support.loading){this.queueEvent("swfupload_load_failed_handler",["Flash Player doesn't support SWFUpload"]);return}if(document.getElementById(this.movieName)!==null){this.support.loading=false;this.queueEvent("swfupload_load_failed_handler",["Element ID already in use"]);return}targetElement=document.getElementById(this.settings.button_placeholder_id)||this.settings.button_placeholder;if(targetElement==undefined){this.support.loading=false;this.queueEvent("swfupload_load_failed_handler",["button place holder not found"]);return}wrapperType=(targetElement.currentStyle&&targetElement.currentStyle["display"]||window.getComputedStyle)!=="block"?"span":"div";tempParent=document.createElement(wrapperType);flashHTML=this.getFlashHTML();try{tempParent.innerHTML=flashHTML}catch(ex){this.support.loading=false;this.queueEvent("swfupload_load_failed_handler",["Exception loading Flash HTML into placeholder"]);return}els=tempParent.getElementsByTagName("object");if(!els||els.length>1||els.length===0){this.support.loading=false;this.queueEvent("swfupload_load_failed_handler",["Unable to find movie after adding to DOM"]);return}else if(els.length===1){this.movieElement=els[0]}targetElement.parentNode.replaceChild(tempParent.firstChild,targetElement);if(window[this.movieName]==undefined){window[this.movieName]=this.getMovieElement()}};SWFUpload.prototype.getFlashHTML=function(flashVersion){return['<object id="',this.movieName,'" type="application/x-shockwave-flash" data="',(this.support.imageResize?this.settings.flash_url:this.settings.flash9_url),'" width="',this.settings.button_width,'" height="',this.settings.button_height,'" class="swfupload">','<param name="wmode" value="',this.settings.button_window_mode,'" />','<param name="movie" value="',(this.support.imageResize?this.settings.flash_url:this.settings.flash9_url),'" />','<param name="quality" value="high" />','<param name="allowScriptAccess" value="always" />','<param name="flashvars" value="'+this.getFlashVars()+'" />','</object>'].join("")};SWFUpload.prototype.getFlashVars=function(){var httpSuccessString,paramString;paramString=this.buildParamString();httpSuccessString=this.settings.http_success.join(",");return["movieName=",encodeURIComponent(this.movieName),"&amp;uploadURL=",encodeURIComponent(this.settings.upload_url),"&amp;useQueryString=",encodeURIComponent(this.settings.use_query_string),"&amp;requeueOnError=",encodeURIComponent(this.settings.requeue_on_error),"&amp;httpSuccess=",encodeURIComponent(httpSuccessString),"&amp;assumeSuccessTimeout=",encodeURIComponent(this.settings.assume_success_timeout),"&amp;params=",encodeURIComponent(paramString),"&amp;filePostName=",encodeURIComponent(this.settings.file_post_name),"&amp;fileTypes=",encodeURIComponent(this.settings.file_types),"&amp;fileTypesDescription=",encodeURIComponent(this.settings.file_types_description),"&amp;fileSizeLimit=",encodeURIComponent(this.settings.file_size_limit),"&amp;fileUploadLimit=",encodeURIComponent(this.settings.file_upload_limit),"&amp;fileQueueLimit=",encodeURIComponent(this.settings.file_queue_limit),"&amp;debugEnabled=",encodeURIComponent(this.settings.debug_enabled),"&amp;buttonImageURL=",encodeURIComponent(this.settings.button_image_url),"&amp;buttonWidth=",encodeURIComponent(this.settings.button_width),"&amp;buttonHeight=",encodeURIComponent(this.settings.button_height),"&amp;buttonText=",encodeURIComponent(this.settings.button_text),"&amp;buttonTextTopPadding=",encodeURIComponent(this.settings.button_text_top_padding),"&amp;buttonTextLeftPadding=",encodeURIComponent(this.settings.button_text_left_padding),"&amp;buttonTextStyle=",encodeURIComponent(this.settings.button_text_style),"&amp;buttonAction=",encodeURIComponent(this.settings.button_action),"&amp;buttonDisabled=",encodeURIComponent(this.settings.button_disabled),"&amp;buttonCursor=",encodeURIComponent(this.settings.button_cursor)].join("")};SWFUpload.prototype.getMovieElement=function(){if(this.movieElement==undefined){this.movieElement=document.getElementById(this.movieName)}if(this.movieElement===null){throw"Could not find Flash element";}return this.movieElement};SWFUpload.prototype.buildParamString=function(){var postParams,paramStringPairs=[];postParams=this.settings.post_params;if(typeof(postParams)==="object"){for(var name in postParams){if(postParams.hasOwnProperty(name)){paramStringPairs.push(encodeURIComponent(name.toString())+"="+encodeURIComponent(postParams[name].toString()))}}}return paramStringPairs.join("&amp;")};SWFUpload.prototype.destroy=function(){var movieElement;try{this.cancelUpload(null,false);movieElement=this.cleanUp();if(movieElement){try{movieElement.parentNode.removeChild(movieElement)}catch(ex){}}window[this.movieName]=null;SWFUpload.instances[this.movieName]=null;delete SWFUpload.instances[this.movieName];this.movieElement=null;this.settings=null;this.customSettings=null;this.eventQueue=null;this.movieName=null;return true}catch(ex2){return false}};SWFUpload.prototype.displayDebugInfo=function(){this.debug(["---SWFUpload Instance Info---\n","Version: ",SWFUpload.version,"\n","Movie Name: ",this.movieName,"\n","Settings:\n","\t","upload_url:               ",this.settings.upload_url,"\n","\t","flash_url:                ",this.settings.flash_url,"\n","\t","flash9_url:                ",this.settings.flash9_url,"\n","\t","use_query_string:         ",this.settings.use_query_string.toString(),"\n","\t","requeue_on_error:         ",this.settings.requeue_on_error.toString(),"\n","\t","http_success:             ",this.settings.http_success.join(", "),"\n","\t","assume_success_timeout:   ",this.settings.assume_success_timeout,"\n","\t","file_post_name:           ",this.settings.file_post_name,"\n","\t","post_params:              ",this.settings.post_params.toString(),"\n","\t","file_types:               ",this.settings.file_types,"\n","\t","file_types_description:   ",this.settings.file_types_description,"\n","\t","file_size_limit:          ",this.settings.file_size_limit,"\n","\t","file_upload_limit:        ",this.settings.file_upload_limit,"\n","\t","file_queue_limit:         ",this.settings.file_queue_limit,"\n","\t","debug:                    ",this.settings.debug.toString(),"\n","\t","prevent_swf_caching:      ",this.settings.prevent_swf_caching.toString(),"\n","\t","button_placeholder_id:    ",this.settings.button_placeholder_id.toString(),"\n","\t","button_placeholder:       ",(this.settings.button_placeholder?"Set":"Not Set"),"\n","\t","button_image_url:         ",this.settings.button_image_url.toString(),"\n","\t","button_width:             ",this.settings.button_width.toString(),"\n","\t","button_height:            ",this.settings.button_height.toString(),"\n","\t","button_text:              ",this.settings.button_text.toString(),"\n","\t","button_text_style:        ",this.settings.button_text_style.toString(),"\n","\t","button_text_top_padding:  ",this.settings.button_text_top_padding.toString(),"\n","\t","button_text_left_padding: ",this.settings.button_text_left_padding.toString(),"\n","\t","button_action:            ",this.settings.button_action.toString(),"\n","\t","button_cursor:            ",this.settings.button_cursor.toString(),"\n","\t","button_disabled:          ",this.settings.button_disabled.toString(),"\n","\t","custom_settings:          ",this.settings.custom_settings.toString(),"\n","Event Handlers:\n","\t","swfupload_preload_handler assigned:  ",(typeof this.settings.swfupload_preload_handler==="function").toString(),"\n","\t","swfupload_load_failed_handler assigned:  ",(typeof this.settings.swfupload_load_failed_handler==="function").toString(),"\n","\t","swfupload_loaded_handler assigned:  ",(typeof this.settings.swfupload_loaded_handler==="function").toString(),"\n","\t","mouse_click_handler assigned:       ",(typeof this.settings.mouse_click_handler==="function").toString(),"\n","\t","mouse_over_handler assigned:        ",(typeof this.settings.mouse_over_handler==="function").toString(),"\n","\t","mouse_out_handler assigned:         ",(typeof this.settings.mouse_out_handler==="function").toString(),"\n","\t","file_dialog_start_handler assigned: ",(typeof this.settings.file_dialog_start_handler==="function").toString(),"\n","\t","file_queued_handler assigned:       ",(typeof this.settings.file_queued_handler==="function").toString(),"\n","\t","file_queue_error_handler assigned:  ",(typeof this.settings.file_queue_error_handler==="function").toString(),"\n","\t","upload_resize_start_handler assigned:      ",(typeof this.settings.upload_resize_start_handler==="function").toString(),"\n","\t","upload_start_handler assigned:      ",(typeof this.settings.upload_start_handler==="function").toString(),"\n","\t","upload_progress_handler assigned:   ",(typeof this.settings.upload_progress_handler==="function").toString(),"\n","\t","upload_error_handler assigned:      ",(typeof this.settings.upload_error_handler==="function").toString(),"\n","\t","upload_success_handler assigned:    ",(typeof this.settings.upload_success_handler==="function").toString(),"\n","\t","upload_complete_handler assigned:   ",(typeof this.settings.upload_complete_handler==="function").toString(),"\n","\t","debug_handler assigned:             ",(typeof this.settings.debug_handler==="function").toString(),"\n","Support:\n","\t","Load:                     ",(this.support.loading?"Yes":"No"),"\n","\t","Image Resize:             ",(this.support.imageResize?"Yes":"No"),"\n"].join(""))};SWFUpload.prototype.addSetting=function(name,value,default_value){if(value==undefined){return(this.settings[name]=default_value)}else{return(this.settings[name]=value)}};SWFUpload.prototype.getSetting=function(name){if(this.settings[name]!=undefined){return this.settings[name]}return""};SWFUpload.prototype.callFlash=function(functionName,argumentArray){var movieElement,returnValue,returnString;argumentArray=argumentArray||[];movieElement=this.getMovieElement();try{if(movieElement!=undefined){returnString=movieElement.CallFunction('<invoke name="'+functionName+'" returntype="javascript">'+__flash__argumentsToXML(argumentArray,0)+'</invoke>');returnValue=eval(returnString)}else{this.debug("Can't call flash because the movie wasn't found.")}}catch(ex){this.debug("Exception calling flash function '"+functionName+"': "+ex.message)}if(returnValue!=undefined&&typeof returnValue.post==="object"){returnValue=this.unescapeFilePostParams(returnValue)}return returnValue};SWFUpload.prototype.selectFile=function(){this.callFlash("SelectFile")};SWFUpload.prototype.selectFiles=function(){this.callFlash("SelectFiles")};SWFUpload.prototype.startUpload=function(fileID){this.callFlash("StartUpload",[fileID])};SWFUpload.prototype.startResizedUpload=function(fileID,width,height,encoding,quality,allowEnlarging){this.callFlash("StartUpload",[fileID,{"width":width,"height":height,"encoding":encoding,"quality":quality,"allowEnlarging":allowEnlarging}])};SWFUpload.prototype.cancelUpload=function(fileID,triggerErrorEvent){if(triggerErrorEvent!==false){triggerErrorEvent=true}this.callFlash("CancelUpload",[fileID,triggerErrorEvent])};SWFUpload.prototype.stopUpload=function(){this.callFlash("StopUpload")};SWFUpload.prototype.requeueUpload=function(indexOrFileID){return this.callFlash("RequeueUpload",[indexOrFileID])};SWFUpload.prototype.getStats=function(){return this.callFlash("GetStats")};SWFUpload.prototype.setStats=function(statsObject){this.callFlash("SetStats",[statsObject])};SWFUpload.prototype.getFile=function(fileID){if(typeof(fileID)==="number"){return this.callFlash("GetFileByIndex",[fileID])}else{return this.callFlash("GetFile",[fileID])}};SWFUpload.prototype.getQueueFile=function(fileID){if(typeof(fileID)==="number"){return this.callFlash("GetFileByQueueIndex",[fileID])}else{return this.callFlash("GetFile",[fileID])}};SWFUpload.prototype.addFileParam=function(fileID,name,value){return this.callFlash("AddFileParam",[fileID,name,value])};SWFUpload.prototype.removeFileParam=function(fileID,name){this.callFlash("RemoveFileParam",[fileID,name])};SWFUpload.prototype.setUploadURL=function(url){this.settings.upload_url=url.toString();this.callFlash("SetUploadURL",[url])};SWFUpload.prototype.setPostParams=function(paramsObject){this.settings.post_params=paramsObject;this.callFlash("SetPostParams",[paramsObject])};SWFUpload.prototype.addPostParam=function(name,value){this.settings.post_params[name]=value;this.callFlash("SetPostParams",[this.settings.post_params])};SWFUpload.prototype.removePostParam=function(name){delete this.settings.post_params[name];this.callFlash("SetPostParams",[this.settings.post_params])};SWFUpload.prototype.setFileTypes=function(types,description){this.settings.file_types=types;this.settings.file_types_description=description;this.callFlash("SetFileTypes",[types,description])};SWFUpload.prototype.setFileSizeLimit=function(filesizelimit){this.settings.file_size_limit=filesizelimit;this.callFlash("SetFileSizeLimit",[filesizelimit])};SWFUpload.prototype.setFileUploadLimit=function(fileuploadlimit){this.settings.file_upload_limit=fileuploadlimit;this.callFlash("SetFileUploadLimit",[fileuploadlimit])};SWFUpload.prototype.setFileQueueLimit=function(fileQueueLimit){this.settings.file_queue_limit=fileQueueLimit;this.callFlash("SetFileQueueLimit",[fileQueueLimit])};SWFUpload.prototype.setFilePostName=function(filePostName){this.settings.file_post_name=filePostName;this.callFlash("SetFilePostName",[filePostName])};SWFUpload.prototype.setUseQueryString=function(useQueryString){this.settings.use_query_string=useQueryString;this.callFlash("SetUseQueryString",[useQueryString])};SWFUpload.prototype.setRequeueOnError=function(requeueOnError){this.settings.requeue_on_error=requeueOnError;this.callFlash("SetRequeueOnError",[requeueOnError])};SWFUpload.prototype.setHTTPSuccess=function(http_status_codes){if(typeof http_status_codes==="string"){http_status_codes=http_status_codes.replace(" ","").split(",")}this.settings.http_success=http_status_codes;this.callFlash("SetHTTPSuccess",[http_status_codes])};SWFUpload.prototype.setAssumeSuccessTimeout=function(timeout_seconds){this.settings.assume_success_timeout=timeout_seconds;this.callFlash("SetAssumeSuccessTimeout",[timeout_seconds])};SWFUpload.prototype.setDebugEnabled=function(debugEnabled){this.settings.debug_enabled=debugEnabled;this.callFlash("SetDebugEnabled",[debugEnabled])};SWFUpload.prototype.setButtonImageURL=function(buttonImageURL){if(buttonImageURL==undefined){buttonImageURL=""}this.settings.button_image_url=buttonImageURL;this.callFlash("SetButtonImageURL",[buttonImageURL])};SWFUpload.prototype.setButtonDimensions=function(width,height){this.settings.button_width=width;this.settings.button_height=height;var movie=this.getMovieElement();if(movie!=undefined){movie.style.width=width+"px";movie.style.height=height+"px"}this.callFlash("SetButtonDimensions",[width,height])};SWFUpload.prototype.setButtonText=function(html){this.settings.button_text=html;this.callFlash("SetButtonText",[html])};SWFUpload.prototype.setButtonTextPadding=function(left,top){this.settings.button_text_top_padding=top;this.settings.button_text_left_padding=left;this.callFlash("SetButtonTextPadding",[left,top])};SWFUpload.prototype.setButtonTextStyle=function(css){this.settings.button_text_style=css;this.callFlash("SetButtonTextStyle",[css])};SWFUpload.prototype.setButtonDisabled=function(isDisabled){this.settings.button_disabled=isDisabled;this.callFlash("SetButtonDisabled",[isDisabled])};SWFUpload.prototype.setButtonAction=function(buttonAction){this.settings.button_action=buttonAction;this.callFlash("SetButtonAction",[buttonAction])};SWFUpload.prototype.setButtonCursor=function(cursor){this.settings.button_cursor=cursor;this.callFlash("SetButtonCursor",[cursor])};SWFUpload.prototype.queueEvent=function(handlerName,argumentArray){var self=this;if(argumentArray==undefined){argumentArray=[]}else if(!(argumentArray instanceof Array)){argumentArray=[argumentArray]}if(typeof this.settings[handlerName]==="function"){this.eventQueue.push(function(){this.settings[handlerName].apply(this,argumentArray)});setTimeout(function(){self.executeNextEvent()},0)}else if(this.settings[handlerName]!==null){throw"Event handler "+handlerName+" is unknown or is not a function";}};SWFUpload.prototype.executeNextEvent=function(){var f=this.eventQueue?this.eventQueue.shift():null;if(typeof(f)==="function"){f.apply(this)}};SWFUpload.prototype.unescapeFilePostParams=function(file){var reg=/[$]([0-9a-f]{4})/i,unescapedPost={},uk,match;if(file!=undefined){for(var k in file.post){if(file.post.hasOwnProperty(k)){uk=k;while((match=reg.exec(uk))!==null){uk=uk.replace(match[0],String.fromCharCode(parseInt("0x"+match[1],16)))}unescapedPost[uk]=file.post[k]}}file.post=unescapedPost}return file};SWFUpload.prototype.swfuploadPreload=function(){var returnValue;if(typeof this.settings.swfupload_preload_handler==="function"){returnValue=this.settings.swfupload_preload_handler.call(this)}else if(this.settings.swfupload_preload_handler!=undefined){throw"upload_start_handler must be a function";}if(returnValue===undefined){returnValue=true}return!!returnValue};SWFUpload.prototype.flashReady=function(){var movieElement=this.cleanUp();if(!movieElement){this.debug("Flash called back ready but the flash movie can't be found.");return}this.queueEvent("swfupload_loaded_handler")};SWFUpload.prototype.cleanUp=function(){var movieElement=this.getMovieElement();try{if(movieElement&&typeof(movieElement.CallFunction)==="unknown"){this.debug("Removing Flash functions hooks (this should only run in IE and should prevent memory leaks)");for(var key in movieElement){try{if(typeof(movieElement[key])==="function"){movieElement[key]=null}}catch(ex){}}}}catch(ex1){}window["__flash__removeCallback"]=function(instance,name){try{if(instance){instance[name]=null}}catch(flashEx){}};return movieElement};SWFUpload.prototype.mouseClick=function(){this.queueEvent("mouse_click_handler")};SWFUpload.prototype.mouseOver=function(){this.queueEvent("mouse_over_handler")};SWFUpload.prototype.mouseOut=function(){this.queueEvent("mouse_out_handler")};SWFUpload.prototype.fileDialogStart=function(){this.queueEvent("file_dialog_start_handler")};SWFUpload.prototype.fileQueued=function(file){file=this.unescapeFilePostParams(file);this.queueEvent("file_queued_handler",file)};SWFUpload.prototype.fileQueueError=function(file,errorCode,message){file=this.unescapeFilePostParams(file);this.queueEvent("file_queue_error_handler",[file,errorCode,message])};SWFUpload.prototype.fileDialogComplete=function(numFilesSelected,numFilesQueued,numFilesInQueue){this.queueEvent("file_dialog_complete_handler",[numFilesSelected,numFilesQueued,numFilesInQueue])};SWFUpload.prototype.uploadResizeStart=function(file,resizeSettings){file=this.unescapeFilePostParams(file);this.queueEvent("upload_resize_start_handler",[file,resizeSettings.width,resizeSettings.height,resizeSettings.encoding,resizeSettings.quality])};SWFUpload.prototype.uploadStart=function(file){file=this.unescapeFilePostParams(file);this.queueEvent("return_upload_start_handler",file)};SWFUpload.prototype.returnUploadStart=function(file){var returnValue;if(typeof this.settings.upload_start_handler==="function"){file=this.unescapeFilePostParams(file);returnValue=this.settings.upload_start_handler.call(this,file)}else if(this.settings.upload_start_handler!=undefined){throw"upload_start_handler must be a function";}if(returnValue===undefined){returnValue=true}returnValue=!!returnValue;this.callFlash("ReturnUploadStart",[returnValue])};SWFUpload.prototype.uploadProgress=function(file,bytesComplete,bytesTotal){file=this.unescapeFilePostParams(file);this.queueEvent("upload_progress_handler",[file,bytesComplete,bytesTotal])};SWFUpload.prototype.uploadError=function(file,errorCode,message){file=this.unescapeFilePostParams(file);this.queueEvent("upload_error_handler",[file,errorCode,message])};SWFUpload.prototype.uploadSuccess=function(file,serverData,responseReceived){file=this.unescapeFilePostParams(file);this.queueEvent("upload_success_handler",[file,serverData,responseReceived])};SWFUpload.prototype.uploadComplete=function(file){file=this.unescapeFilePostParams(file);this.queueEvent("upload_complete_handler",file)};SWFUpload.prototype.debug=function(message){this.queueEvent("debug_handler",message)};SWFUpload.prototype.debugMessage=function(message){var exceptionMessage,exceptionValues;if(this.settings.debug){exceptionValues=[];if(typeof message==="object"&&typeof message.name==="string"&&typeof message.message==="string"){for(var key in message){if(message.hasOwnProperty(key)){exceptionValues.push(key+": "+message[key])}}exceptionMessage=exceptionValues.join("\n")||"";exceptionValues=exceptionMessage.split("\n");exceptionMessage="EXCEPTION: "+exceptionValues.join("\nEXCEPTION: ");SWFUpload.Console.writeLine(exceptionMessage)}else{SWFUpload.Console.writeLine(message)}}};SWFUpload.Console={};SWFUpload.Console.writeLine=function(message){var console,documentForm;try{console=document.getElementById("SWFUpload_Console");if(!console){documentForm=document.createElement("form");document.getElementsByTagName("body")[0].appendChild(documentForm);console=document.createElement("textarea");console.id="SWFUpload_Console";console.style.fontFamily="monospace";console.setAttribute("wrap","off");console.wrap="off";console.style.overflow="auto";console.style.width="700px";console.style.height="350px";console.style.margin="5px";documentForm.appendChild(console)}console.value+=message+"\n";console.scrollTop=console.scrollHeight-console.clientHeight}catch(ex){alert("Exception: "+ex.name+" Message: "+ex.message)}};swfobject=function(){var D="undefined",r="object",S="Shockwave Flash",W="ShockwaveFlash.ShockwaveFlash",q="application/x-shockwave-flash",R="SWFObjectExprInst",x="onreadystatechange",O=window,j=document,t=navigator,T=false,U=[h],o=[],N=[],I=[],l,Q,E,B,J=false,a=false,n,G,m=true,M=function(){var aa=typeof j.getElementById!=D&&typeof j.getElementsByTagName!=D&&typeof j.createElement!=D,ah=t.userAgent.toLowerCase(),Y=t.platform.toLowerCase(),ae=Y?/win/.test(Y):/win/.test(ah),ac=Y?/mac/.test(Y):/mac/.test(ah),af=/webkit/.test(ah)?parseFloat(ah.replace(/^.*webkit\/(\d+(\.\d+)?).*$/,"$1")):false,X=!+"\v1",ag=[0,0,0],ab=null;if(typeof t.plugins!=D&&typeof t.plugins[S]==r){ab=t.plugins[S].description;if(ab&&!(typeof t.mimeTypes!=D&&t.mimeTypes[q]&&!t.mimeTypes[q].enabledPlugin)){T=true;X=false;ab=ab.replace(/^.*\s+(\S+\s+\S+$)/,"$1");ag[0]=parseInt(ab.replace(/^(.*)\..*$/,"$1"),10);ag[1]=parseInt(ab.replace(/^.*\.(.*)\s.*$/,"$1"),10);ag[2]=/[a-zA-Z]/.test(ab)?parseInt(ab.replace(/^.*[a-zA-Z]+(.*)$/,"$1"),10):0}}else{if(typeof O.ActiveXObject!=D){try{var ad=new ActiveXObject(W);if(ad){ab=ad.GetVariable("$version");if(ab){X=true;ab=ab.split(" ")[1].split(",");ag=[parseInt(ab[0],10),parseInt(ab[1],10),parseInt(ab[2],10)]}}}catch(Z){}}}return{w3:aa,pv:ag,wk:af,ie:X,win:ae,mac:ac}}(),k=function(){if(!M.w3){return}if((typeof j.readyState!=D&&j.readyState=="complete")||(typeof j.readyState==D&&(j.getElementsByTagName("body")[0]||j.body))){f()}if(!J){if(typeof j.addEventListener!=D){j.addEventListener("DOMContentLoaded",f,false)}if(M.ie&&M.win){j.attachEvent(x,function(){if(j.readyState=="complete"){j.detachEvent(x,arguments.callee);f()}});if(O==top){(function(){if(J){return}try{j.documentElement.doScroll("left")}catch(X){setTimeout(arguments.callee,0);return}f()})()}}if(M.wk){(function(){if(J){return}if(!/loaded|complete/.test(j.readyState)){setTimeout(arguments.callee,0);return}f()})()}s(f)}}();function f(){if(J){return}try{var Z=j.getElementsByTagName("body")[0].appendChild(C("span"));Z.parentNode.removeChild(Z)}catch(aa){return}J=true;var X=U.length;for(var Y=0;Y<X;Y++){U[Y]()}}function K(X){if(J){X()}else{U[U.length]=X}}function s(Y){if(typeof O.addEventListener!=D){O.addEventListener("load",Y,false)}else{if(typeof j.addEventListener!=D){j.addEventListener("load",Y,false)}else{if(typeof O.attachEvent!=D){i(O,"onload",Y)}else{if(typeof O.onload=="function"){var X=O.onload;O.onload=function(){X();Y()}}else{O.onload=Y}}}}}function h(){if(T){V()}else{H()}}function V(){var X=j.getElementsByTagName("body")[0];var aa=C(r);aa.setAttribute("type",q);var Z=X.appendChild(aa);if(Z){var Y=0;(function(){if(typeof Z.GetVariable!=D){var ab=Z.GetVariable("$version");if(ab){ab=ab.split(" ")[1].split(",");M.pv=[parseInt(ab[0],10),parseInt(ab[1],10),parseInt(ab[2],10)]}}else{if(Y<10){Y++;setTimeout(arguments.callee,10);return}}X.removeChild(aa);Z=null;H()})()}else{H()}}function H(){var ag=o.length;if(ag>0){for(var af=0;af<ag;af++){var Y=o[af].id;var ab=o[af].callbackFn;var aa={success:false,id:Y};if(M.pv[0]>0){var ae=c(Y);if(ae){if(F(o[af].swfVersion)&&!(M.wk&&M.wk<312)){w(Y,true);if(ab){aa.success=true;aa.ref=z(Y);ab(aa)}}else{if(o[af].expressInstall&&A()){var ai={};ai.data=o[af].expressInstall;ai.width=ae.getAttribute("width")||"0";ai.height=ae.getAttribute("height")||"0";if(ae.getAttribute("class")){ai.styleclass=ae.getAttribute("class");}if(ae.getAttribute("align")){ai.align=ae.getAttribute("align")}var ah={};var X=ae.getElementsByTagName("param");var ac=X.length;for(var ad=0;ad<ac;ad++){if(X[ad].getAttribute("name").toLowerCase()!="movie"){ah[X[ad].getAttribute("name")]=X[ad].getAttribute("value")}}P(ai,ah,Y,ab)}else{p(ae);if(ab){ab(aa)}}}}}else{w(Y,true);if(ab){var Z=z(Y);if(Z&&typeof Z.SetVariable!=D){aa.success=true;aa.ref=Z}ab(aa)}}}}}function z(aa){var X=null;var Y=c(aa);if(Y&&Y.nodeName=="OBJECT"){if(typeof Y.SetVariable!=D){X=Y}else{var Z=Y.getElementsByTagName(r)[0];if(Z){X=Z}}}return X}function A(){return!a&&F("6.0.65")&&(M.win||M.mac)&&!(M.wk&&M.wk<312)}function P(aa,ab,X,Z){a=true;E=Z||null;B={success:false,id:X};var ae=c(X);if(ae){if(ae.nodeName=="OBJECT"){l=g(ae);Q=null}else{l=ae;Q=X}aa.id=R;if(typeof aa.width==D||(!/%$/.test(aa.width)&&parseInt(aa.width,10)<310)){aa.width="310"}if(typeof aa.height==D||(!/%$/.test(aa.height)&&parseInt(aa.height,10)<137)){aa.height="137"}j.title=j.title.slice(0,47)+" - Flash Player Installation";var ad=M.ie&&M.win?"ActiveX":"PlugIn",ac="MMredirectURL="+O.location.toString().replace(/&/g,"%26")+"&MMplayerType="+ad+"&MMdoctitle="+j.title;if(typeof ab.flashvars!=D){ab.flashvars+="&"+ac}else{ab.flashvars=ac}if(M.ie&&M.win&&ae.readyState!=4){var Y=C("div");X+="SWFObjectNew";Y.setAttribute("id",X);ae.parentNode.insertBefore(Y,ae);ae.style.display="none";(function(){if(ae.readyState==4){ae.parentNode.removeChild(ae)}else{setTimeout(arguments.callee,10)}})()}u(aa,ab,X)}}function p(Y){if(M.ie&&M.win&&Y.readyState!=4){var X=C("div");Y.parentNode.insertBefore(X,Y);X.parentNode.replaceChild(g(Y),X);Y.style.display="none";(function(){if(Y.readyState==4){Y.parentNode.removeChild(Y)}else{setTimeout(arguments.callee,10)}})()}else{Y.parentNode.replaceChild(g(Y),Y)}}function g(ab){var aa=C("div");if(M.win&&M.ie){aa.innerHTML=ab.innerHTML}else{var Y=ab.getElementsByTagName(r)[0];if(Y){var ad=Y.childNodes;if(ad){var X=ad.length;for(var Z=0;Z<X;Z++){if(!(ad[Z].nodeType==1&&ad[Z].nodeName=="PARAM")&&!(ad[Z].nodeType==8)){aa.appendChild(ad[Z].cloneNode(true))}}}}}return aa}function u(ai,ag,Y){var X,aa=c(Y);if(M.wk&&M.wk<312){return X}if(aa){if(typeof ai.id==D){ai.id=Y}if(M.ie&&M.win){var ah="";for(var ae in ai){if(ai[ae]!=Object.prototype[ae]){if(ae.toLowerCase()=="data"){ag.movie=ai[ae]}else{if(ae.toLowerCase()=="styleclass"){ah+=' class="'+ai[ae]+'"'}else{if(ae.toLowerCase()!="classid"){ah+=" "+ae+'="'+ai[ae]+'"'}}}}}var af="";for(var ad in ag){if(ag[ad]!=Object.prototype[ad]){af+='<param name="'+ad+'" value="'+ag[ad]+'" />'}}aa.outerHTML='<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"'+ah+">"+af+"</object>";N[N.length]=ai.id;X=c(ai.id)}else{var Z=C(r);Z.setAttribute("type",q);for(var ac in ai){if(ai[ac]!=Object.prototype[ac]){if(ac.toLowerCase()=="styleclass"){Z.setAttribute("class",ai[ac])}else{if(ac.toLowerCase()!="classid"){Z.setAttribute(ac,ai[ac])}}}}for(var ab in ag){if(ag[ab]!=Object.prototype[ab]&&ab.toLowerCase()!="movie"){e(Z,ab,ag[ab])}}aa.parentNode.replaceChild(Z,aa);X=Z}}return X}function e(Z,X,Y){var aa=C("param");aa.setAttribute("name",X);aa.setAttribute("value",Y);Z.appendChild(aa)}function y(Y){var X=c(Y);if(X&&X.nodeName=="OBJECT"){if(M.ie&&M.win){X.style.display="none";(function(){if(X.readyState==4){b(Y)}else{setTimeout(arguments.callee,10)}})()}else{X.parentNode.removeChild(X)}}}function b(Z){var Y=c(Z);if(Y){for(var X in Y){if(typeof Y[X]=="function"){Y[X]=null}}Y.parentNode.removeChild(Y)}}function c(Z){var X=null;try{X=j.getElementById(Z)}catch(Y){}return X;}function C(X){return j.createElement(X)}function i(Z,X,Y){Z.attachEvent(X,Y);I[I.length]=[Z,X,Y]}function F(Z){var Y=M.pv,X=Z.split(".");X[0]=parseInt(X[0],10);X[1]=parseInt(X[1],10)||0;X[2]=parseInt(X[2],10)||0;return(Y[0]>X[0]||(Y[0]==X[0]&&Y[1]>X[1])||(Y[0]==X[0]&&Y[1]==X[1]&&Y[2]>=X[2]))?true:false}function v(ac,Y,ad,ab){if(M.ie&&M.mac){return}var aa=j.getElementsByTagName("head")[0];if(!aa){return}var X=(ad&&typeof ad=="string")?ad:"screen";if(ab){n=null;G=null}if(!n||G!=X){var Z=C("style");Z.setAttribute("type","text/css");Z.setAttribute("media",X);n=aa.appendChild(Z);if(M.ie&&M.win&&typeof j.styleSheets!=D&&j.styleSheets.length>0){n=j.styleSheets[j.styleSheets.length-1]}G=X}if(M.ie&&M.win){if(n&&typeof n.addRule==r){n.addRule(ac,Y)}}else{if(n&&typeof j.createTextNode!=D){n.appendChild(j.createTextNode(ac+" {"+Y+"}"))}}}function w(Z,X){if(!m){return}var Y=X?"visible":"hidden";if(J&&c(Z)){c(Z).style.visibility=Y}else{v("#"+Z,"visibility:"+Y)}}function L(Y){var Z=/[\\\"<>\.;]/;var X=Z.exec(Y)!=null;return X&&typeof encodeURIComponent!=D?encodeURIComponent(Y):Y}var d=function(){if(M.ie&&M.win){window.attachEvent("onunload",function(){var ac=I.length;for(var ab=0;ab<ac;ab++){I[ab][0].detachEvent(I[ab][1],I[ab][2])}var Z=N.length;for(var aa=0;aa<Z;aa++){y(N[aa])}for(var Y in M){M[Y]=null}M=null;for(var X in swfobject){swfobject[X]=null}swfobject=null})}}();return{registerObject:function(ab,X,aa,Z){if(M.w3&&ab&&X){var Y={};Y.id=ab;Y.swfVersion=X;Y.expressInstall=aa;Y.callbackFn=Z;o[o.length]=Y;w(ab,false)}else{if(Z){Z({success:false,id:ab})}}},getObjectById:function(X){if(M.w3){return z(X)}},embedSWF:function(ab,ah,ae,ag,Y,aa,Z,ad,af,ac){var X={success:false,id:ah};if(M.w3&&!(M.wk&&M.wk<312)&&ab&&ah&&ae&&ag&&Y){w(ah,false);K(function(){ae+="";ag+="";var aj={};if(af&&typeof af===r){for(var al in af){aj[al]=af[al]}}aj.data=ab;aj.width=ae;aj.height=ag;var am={};if(ad&&typeof ad===r){for(var ak in ad){am[ak]=ad[ak]}}if(Z&&typeof Z===r){for(var ai in Z){if(typeof am.flashvars!=D){am.flashvars+="&"+ai+"="+Z[ai]}else{am.flashvars=ai+"="+Z[ai]}}}if(F(Y)){var an=u(aj,am,ah);if(aj.id==ah){w(ah,true)}X.success=true;X.ref=an}else{if(aa&&A()){aj.data=aa;P(aj,am,ah,ac);return}else{w(ah,true)}}if(ac){ac(X)}})}else{if(ac){ac(X)}}},switchOffAutoHideShow:function(){m=false},ua:M,getFlashPlayerVersion:function(){return{major:M.pv[0],minor:M.pv[1],release:M.pv[2]}},hasFlashPlayerVersion:F,createSWF:function(Z,Y,X){if(M.w3){return u(Z,Y,X)}else{return undefined}},showExpressInstall:function(Z,aa,X,Y){if(M.w3&&A()){P(Z,aa,X,Y)}},removeSWF:function(X){if(M.w3){y(X)}},createCSS:function(aa,Z,Y,X){if(M.w3){v(aa,Z,Y,X)}},addDomLoadEvent:K,addLoadEvent:s,getQueryParamValue:function(aa){var Z=j.location.search||j.location.hash;if(Z){if(/\?/.test(Z)){Z=Z.split("?")[1]}if(aa==null){return L(Z)}var Y=Z.split("&");for(var X=0;X<Y.length;X++){if(Y[X].substring(0,Y[X].indexOf("="))==aa){return L(Y[X].substring((Y[X].indexOf("=")+1)))}}}return""},expressInstallCallback:function(){if(a){var X=c(R);if(X&&l){X.parentNode.replaceChild(l,X);if(Q){w(Q,true);if(M.ie&&M.win){l.style.display="block"}}if(E){E(B)}}a=false}}}}();swfobject.addDomLoadEvent(function(){if(typeof(SWFUpload.onload)==="function"){SWFUpload.onload.call(window)}});var SWFUpload;if(typeof(SWFUpload)==="function"){SWFUpload.queue={};SWFUpload.prototype.initSettings=(function(oldInitSettings){return function(userSettings){if(typeof(oldInitSettings)==="function"){oldInitSettings.call(this,userSettings)}this.queueSettings={};this.queueSettings.queue_cancelled_flag=false;this.queueSettings.queue_upload_count=0;this.queueSettings.user_upload_complete_handler=this.settings.upload_complete_handler;this.queueSettings.user_upload_start_handler=this.settings.upload_start_handler;this.settings.upload_complete_handler=SWFUpload.queue.uploadCompleteHandler;this.settings.upload_start_handler=SWFUpload.queue.uploadStartHandler;this.settings.queue_complete_handler=userSettings.queue_complete_handler||null}})(SWFUpload.prototype.initSettings);SWFUpload.prototype.startUpload=function(fileID){this.queueSettings.queue_cancelled_flag=false;this.callFlash("StartUpload",[fileID])};SWFUpload.prototype.cancelQueue=function(){this.queueSettings.queue_cancelled_flag=true;this.stopUpload();var stats=this.getStats();while(stats.files_queued>0){this.cancelUpload();stats=this.getStats()}};SWFUpload.queue.uploadStartHandler=function(file){var returnValue;if(typeof(this.queueSettings.user_upload_start_handler)==="function"){returnValue=this.queueSettings.user_upload_start_handler.call(this,file)}returnValue=(returnValue===false)?false:true;this.queueSettings.queue_cancelled_flag=!returnValue;return returnValue};SWFUpload.queue.uploadCompleteHandler=function(file){var user_upload_complete_handler=this.queueSettings.user_upload_complete_handler;var continueUpload;if(file.filestatus===SWFUpload.FILE_STATUS.COMPLETE){this.queueSettings.queue_upload_count++}if(typeof(user_upload_complete_handler)==="function"){continueUpload=(user_upload_complete_handler.call(this,file)===false)?false:true}else if(file.filestatus===SWFUpload.FILE_STATUS.QUEUED){continueUpload=false}else{continueUpload=true}if(continueUpload){var stats=this.getStats();if(stats.files_queued>0&&this.queueSettings.queue_cancelled_flag===false){this.startUpload()}else if(this.queueSettings.queue_cancelled_flag===false){this.queueEvent("queue_complete_handler",[this.queueSettings.queue_upload_count]);this.queueSettings.queue_upload_count=0}else{this.queueSettings.queue_cancelled_flag=false;this.queueSettings.queue_upload_count=0}}}};

(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery', 'form/jazz.form.Field'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){

/**
 * @version 0.5
 * @name jazz.Attachment
 * @description ???????????????
 * @constructor
 * @extends jazz.boxComponent
 */
$.widget('jazz.attachment', $.jazz.field, {
	
    options: /** @lends jazz.Attachment# */ {
    	
    	vtype: "attachment", 

		/**
		 *@type String
		 *@desc ??????????????????????????? "*.*" ????????????  "*.jpg;*.png" ???????????????jpg???png?????????
		 *@default '??????'
		 */		    	
    	allowfiletypes: '*.*',
		
    	/**
		 *@type String
		 *@desc ????????????????????????????????????
		 *@default '????????????'
		 */
    	allowfiletypesdesc: '????????????', 
    	
    	/**
    	 * @type String
    	 * @desc ?????????????????????
    	 * @default ''
    	 */
    	description: '',
    	
    	/**
    	 * @type String
    	 * @desc ???????????????url??????
    	 * @default ''
    	 */    	
    	downloadurl: '',
		
		/**
		 *@type String
		 *@desc ??????????????????????????????
		 *@default '50 MB'
		 */
		filesizelimit: '50 MB',
		
		/**
		 *@type Number
		 *@desc ????????????????????????
		 *@default 20
		 */	    	
		fileuploadlimit: 20,
		
		/**
		 *@type Number
		 *@desc ????????????????????????????????????????????????
		 *@default 0
		 */		
		filesizeminlimit: 0,
		
		/**
		 *@type Boolean
		 *@desc ?????????????????????????????????  true?????? false?????????
		 *@default false
		 */
		isrepeatupload: false,
    	
		/**
		 *@type String
		 *@desc ??????????????????
		 *@default ''
		 */
		previewurl: '', 
		
		/**
		 *@type String
		 *@desc ????????????????????????????????????
		 *@default '????????????'
		 */  		
		title: '????????????',
		
		/**
		 *@type Boolean
		 *@desc ??????????????????????????????, true ??????????????????????????? false ????????????????????????
		 *@default true
		 */    	
    	single: false,
    	
		/**
		 *@type Number
		 *@desc ???????????????????????????
		 *@default 0
		 */    	
    	theme: 0,
    	
		/**
		 *@type String
		 *@desc ?????????????????????
		 *@default ''
		 */    	
    	uploadurl: '',
    	
    	//event
		/**
		 *@desc ??????????????????????????????
		 *@param {event} ??????
		 *@param {data} ???????????? jazz.jsonToString(data) ??????????????????????????????;
		 *@event
		 *@example
		 *<br/>$("XXX").attachment("option", "click", function(event, ui){  <br/>} <br/>});
		 *???:
		 *<br/>$("XXX").on("attachmentclick",function(event, ui){  <br/>} <br/>});
		 *??????
		 *function XXX(){??????}
		 *<div?????? click="XXX()"></div> ??? <div?????? click="XXX"></div>
		 **/      	
    	click: null,    	
    	
		/**
		 *@desc ???????????????????????????
		 *@param {event} ??????
		 *@param {data} ???????????? jazz.jsonToString(data) ??????????????????????????????;
		 *@event
		 *@example
		 *<br/>$("XXX").attachment("option", "close", function(event, ui){  <br/>} <br/>});
		 *???:
		 *<br/>$("XXX").on("attachmentclose",function(event, ui){  <br/>} <br/>});
		 *??????
		 *function XXX(){??????}
		 *<div?????? close="XXX()"></div> ??? <div?????? close="XXX"></div>
		 **/      	
    	close: null,
    	
		/**
		 *@desc ??????????????????????????????
		 *@param {event} ??????
		 *@param {data} ???????????? jazz.jsonToString(data) ??????????????????????????????;
		 *@event
		 *@example
		 *<br/>$("XXX").attachment("option", "enter", function(event, ui){  <br/>} <br/>});
		 *???:
		 *<br/>$("XXX").on("attachmententer",function(event, ui){  <br/>} <br/>});
		 *??????
		 *function XXX(){??????}
		 *<div?????? enter="XXX()"></div> ??? <div?????? enter="XXX"></div>
		 **/      	
    	enter: null,    	
    	
		/**
		 *@desc ??????????????????????????????
		 *@param {event} ??????
		 *@param {data} ???????????? jazz.jsonToString(data) ??????????????????????????????;
		 *@event
		 *@example
		 *<br/>$("XXX").attachment("option", "leave", function(event, ui){  <br/>} <br/>});
		 *???:
		 *<br/>$("XXX").on("attachmentleave",function(event, ui){  <br/>} <br/>});
		 *??????
		 *function XXX(){??????}
		 *<div?????? leave="XXX()"></div> ??? <div?????? leave="XXX"></div>
		 **/      	
    	leave: null,

		/**
		 *@desc ????????????????????????????????????
		 *@param {event} ??????
		 *@param {data} ???????????? jazz.jsonToString(data) ??????????????????????????????; 
		 *@event
		 *@example
		 *<br/>$("XXX").attachment("option", "uploadsuccess", function(event, ui){  <br/>} <br/>});
		 *???:
		 *<br/>$("XXX").on("attachmentuploadsuccess",function(event, ui){  <br/>} <br/>});
		 *??????
		 *function XXX(){??????}
		 *<div?????? uploadsuccess="XXX()"></div> ??? <div?????? uploadsuccess="XXX"></div>
		 **/   	
    	uploadsuccess: null

	},
	
	_height: function(){},
	_disabled: function(){},
	_editable: function(){},
	
	/** @lends jazz.Attachment */
	_create: function(){
		
		this._super();
		
		this.id = this.options.name;
		if(!this.id){
			return false;
		}

		var str = '<div class="jazz-att-panel" id="'+this.id+'Panel">'
		  	    + '<div class="jazz-att-add">'
			    + '<span class="iconTrans14 jazz-att-icon"></span><a href="#" tabindex="-1">'+this.options.title+'</a>'
			    + '</div>';
		   
		    str += '<div class="jazz-att-theme" id="'+this.id+'ProgressContainer"></div>';
		   
			if(this.options.description){
			   str += '<div class="jazz-att-shuoming">'+this.options.description+'</div>';
			}
			
			str += '<div class="swfContainer" style="width:80px; height: 20px;left: 5px; top: 5px; position: absolute;">' 
			   		+'<input type="hidden" name="uploadSession" value="'+jazz.getRandom()+jazz.getRandom()+'" />'
			   		+'<input type="hidden" name="filesizelimit" value="'+this.options.filesizelimit+'" />'
			   		+'<input type="hidden" name="filesizeminlimit" value="'+this.options.filesizeminlimit+'" />'
			   		+'<input type="hidden" name="fileuploadlimit" value="'+this.options.fileuploadlimit+'" />'
			   		+'<input type="hidden" name="allowfiletypes" value="'+this.options.allowfiletypes+'" />'
			   		+'<input type="hidden" name="allowfiletypesdesc" value="'+this.options.allowfiletypesdesc+'" />'
			   		+'<input type="hidden" name="uploadedfilescount" value="0"/>'
			   		+'<input type="hidden" name="disableButtonId" value="btnSave" />'					   		
			   		+'<span id="'+this.id+'SwfHolder"></span>'
			   +'</div>'
  		       //+'<span class="tips">??????????????????????????????this.fileCount????????????</span>'
	   +'</div>';

	   this.parent.addClass("jazz-att-no-border");
	   this.inputFrame.addClass('jazz-att-panel');
	   this.inputFrame.append(str);
	  
	   this._loadSwfupload();
	},
	
	/**
	 * @desc ?????????
	 * @private
	 */
	_init: function(){
		this._super();
		
		//??????????????????
		this.datadisplay = [];
	},
	
	/**
	 * @desc ????????????????????????
	 * @private
	 */	
	_getConfig: function(){
		var id = this.id;
		if (null == id || "" == id){
			throw "???????????????????????????????????????id?????????????????????";
		}
		var config = {id: id};
		var elements = this.inputFrame.find('input[type=hidden]');
		$.each(elements, function(i, item){
			config[item.name] = item.value;  
		});
		//????????????id
		config.masterId = config.masterId || '';
		//???????????????????????????????????????50M
		config.filesizelimit = config.filesizelimit || '50 MB';
		//?????????????????????????????????
		config.filesizeminlimit = config.filesizeminlimit || 0;		
		//???????????????????????????
		config.fileuploadlimit = parseInt(config.fileuploadlimit || 20);
		//???????????????????????????
		config.allowfiletypes = config.allowfiletypes || '*.*;';
		//???????????????????????????
		config.allowfiletypesdesc = config.allowfiletypesdesc || '????????????;';
		//??????????????????????????????
		config.uploadedfilescount = parseInt(config.uploadedfilescount || 0);
		return config;
	},
		
	/**
	 * @desc ??????swfupload??????
	 * @private
	 */
	_loadSwfupload: function(){
		SWFUpload.onload = this._swf_upload();
	},

	/**
	 * @desc ???????????????????????????????????????????????????
	 * @private
	 */
	_readonly: function(){
		if(this.options.readonly == true){
			this.inputFrame.find(".jazz-att-add").css({"display": "none"});
			this.inputFrame.find(".swfContainer").css({"display": "none"}); 
			if(this.options.theme == 1){
				this.inputFrame.find(".jazz-att-theme1-close").css({"display": "none"});				
			}
		}else{
			this.inputFrame.find(".jazz-att-add").css({"display": "block"});
			this.inputFrame.find(".swfContainer").css({"display": "block"});
			if(this.options.theme == 1){
				this.inputFrame.find(".jazz-att-theme1-close").css({"display": "block"});
			}
		}
	},
	
	_swf_upload: function(){
		var config = this._getConfig();
		this._swfInit(config);
	},

	/**
	 * @desc ???????????????
	 * @param {config} ??????????????????
	 * @private
	 */	
     _swfInit: function(config){
    	var $this = this;
  		var DEFAULT_UPLOAD_URL = jazz.config.contextpath + ($this.options.uploadurl || jazz.config.default_upload_url),
  			DEFAULT_FLASH_URL = jazz.config.contextpath + jazz.config.default_flash_url,
  			DEFAULT_FLASH9_URL = jazz.config.contextpath + jazz.config.default_flash9_url;

	  		var single = this.options.single, uploadSuccessHandler = uploadSuccess;
	  		
	  		var isrepeatupload = this.options.isrepeatupload;

			this.swfInstance = new SWFUpload({
					upload_url: $.trim(DEFAULT_UPLOAD_URL),
					post_params: {
						type: 'upload',
						uploadSession: config.uploadSession
					},
					file_size_limit: config.filesizelimit,
					file_types : config.allowfiletypes,
					file_types_description : config.allowfiletypesdesc,
					file_upload_limit : config.fileuploadlimit,
					
					button_action: single?SWFUpload.BUTTON_ACTION.SELECT_FILE:SWFUpload.BUTTON_ACTION.SELECT_FILES,
					
					//Event handlers
					swfupload_loaded_handler: loaded,
					swfupload_preload_handler: preLoad,
					swfupload_load_failed_handler: loadFailed,
					file_queued_handler: fileQueued,
					file_queue_error_handler : fileQueueError,
					file_dialog_complete_handler : fileDialogComplete,
					upload_start_handler : uploadStart,
					upload_progress_handler : uploadProgress,
					upload_error_handler : uploadError,
					upload_success_handler : uploadSuccessHandler,
					upload_complete_handler : uploadComplete,
					//??????flash??????
					button_placeholder_id : $this.id + "SwfHolder",
					button_width: 70,
					button_height: 20,
					button_text_top_padding: 0,
					button_text_left_padding: 0,
					button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
					button_cursor: SWFUpload.CURSOR.HAND,
					flash_url :DEFAULT_FLASH_URL,
					flash9_url:DEFAULT_FLASH9_URL,
					custom_settings:{
						progressTarget: $this.id+"ProgressContainer",
						uploadedfilescount: config.uploadedfilescount,
						disableButtonId: config.disableButtonId,
						type: config.type,
						isrepeatupload: isrepeatupload,
						alreadyupload: [],
						customuploadstate: false  //????????????
	  				},
					debug: false
			});
			this._onLoad(this.swfInstance);
			
			function preLoad() {
				if (!this.support.loading) {
					jazz.info("??????????????????????????????,?????????FLASH9 ????????????????????????");
					return false;
				}
			}

			function loaded() {
				var fileUploadLimt = this.settings.file_upload_limit;
			    var uploadedfilescount = this.customSettings.uploadedfilescount;
				if (fileUploadLimt > 0) {   // && uploadedFilesCount > 0
					var stats = this.getStats();
						stats.successful_uploads = uploadedfilescount; 
						this.setStats(stats);
				}
			}

			function loadFailed() {
				jazz.info("??????????????????????????????,?????????FLASH9 ????????????????????????");
			}

			function fileQueued(file) {  
				try {
					var f = true;
					var size = file["size"];
					var n = parseFloat(size/1024, 0.1);
					if(!isNaN(n)){
						n = Math.round(n*100)/100;	
					}
					
					var filesize = $this.options.filesizeminlimit;
					filesize = filesize + "";
					if(filesize !== 0 && filesize.length > 2){
						filesize = $this.countFileSize(filesize);
					}
					
					var filesize = $this.countFileDisplaySize(filesize || 0);
					if(filesize[1] !== 0){
						if(parseFloat(filesize[1]) >= n){
							f = false;
						}
					}
					
					if((this.customSettings["isrepeatupload"] || $.inArray(file["name"], this.customSettings["alreadyupload"]) < 0 ) && f){
						this.customuploadstate = true;
						this.customSettings.alreadyupload.push(file["name"]);
						var progress = new FileProgress(file, this.customSettings.progressTarget, this.customSettings, "fileQueued");
						//????????????????????????
						progress.toggleCancel(true, this);
					}else{
						this.customuploadstate = false;
						if(!f){
							jazz.info('??????????????????????????????'+filesize[0]+'????????????????????????????????????');							
						}else{
							jazz.info("???????????????????????????");
						}
					}
				} catch (ex) {
					this.debug(ex);
				}
			}

			function fileQueueError(file, errorCode, message) {
				try {
					if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
						jazz.info('?????????????????????'+this.settings.file_upload_limit+'????????????');
						return;
					}
					switch (errorCode) {
					case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
						jazz.info('??????????????????????????????'+this.settings.file_size_limit+'????????????????????????????????????');
						break;
					case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
						//alert('?????????????????????0??????????????????'+file.name);
						break;
					case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
						jazz.info('?????????????????????????????????????????????????????????????????????');
						break;
					default:
						if (file !== null) {
							jazz.info('?????????????????????????????????????????????????????????????????????');
						}
						break;
					}
				} catch (ex) {
			        this.debug(ex);
			    }
			}

			function fileDialogComplete(numFilesSelected, numFilesQueued) {
				if(this.customuploadstate){
					try {
						if (numFilesSelected == 0) {
							return false;
						}
						this.startUpload();
						
						//this.startResizedUpload(this.getFile(0).id, 100, 100);
					} catch (ex)  {
				        this.debug(ex);
					} 
				}
			}

			function uploadStart(file) {
				if(this.customuploadstate){
					try {
						var progress = new FileProgress(file, this.customSettings.progressTarget, this.customSettings, "uploadStart");
						progress.toggleCancel(true, this);
					}
					catch (ex) {
						this.debug(ex);
					}
					return true;
				}
			}

			function uploadProgress(file, bytesLoaded, bytesTotal) {
				if(this.customuploadstate){
					try {
						//???????????????????????????????????????????????????
						var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
						var progress = new FileProgress(file, this.customSettings.progressTarget, this.customSettings, "uploadProgress");
						progress.setProgress(percent);
						
					} catch (ex) {
						this.debug(ex);
					}
				}
			}

			function uploadSuccess(file, serverData) {
				if(this.customuploadstate){
					try {
						var progress = new FileProgress(file, this.customSettings.progressTarget, this.customSettings, "uploadSuccess");
						var jsonData = jazz.stringToJson(serverData);   
	
						if (jsonData.success===true){
							progress.setComplete(jsonData);
							progress.toggleCancel(false, this);
							
							$this._showListEvent(file, serverData, this.customSettings);
							
							$this._event("uploadsuccess", null, serverData);						
						}else{
							this.uploadError(file, SWFUpload.UPLOAD_ERROR.USER_DEFINED, jsonData.msg);
						}
					} catch (ex) {
						this.debug(ex);
					}
				}
			}

			function uploadError(file, errorCode, message) {
				if(this.customuploadstate){
					try {
						var progress = new FileProgress(file, this.customSettings.progressTarget, this.customSettings);
						progress.setError();
						progress.toggleCancel(false, this);
				    
						switch (errorCode) {
						case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
							progress.setStatus("????????????: " + message);
							break;
						case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
							progress.setStatus("????????????.");
							break;
						case SWFUpload.UPLOAD_ERROR.IO_ERROR:
							progress.setStatus("????????????????????????????????????.");
							break;
						case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
							progress.setStatus("????????????.");
							break;
						case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
							progress.setStatus("??????????????????.");
							break;
						case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
							progress.setStatus("????????????.");
							break;
						case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
							progress.setStatus("?????????.");
							progress.setCancelled(this);
							break;
						case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
							progress.setStatus("?????????.");
							break;
						case SWFUpload.UPLOAD_ERROR.USER_DEFINED:
							progress.setStatus(message);
							break;
						default:
							progress.setStatus("??????????????????: " + errorCode);
							break;
						}
					} catch (ex) {
				        this.debug(ex);
				    }
				}
			}

			function uploadComplete(file) {
				if(this.customuploadstate){
					if (this.getStats().files_queued === 0) {
						var progress = new FileProgress(file, this.customSettings.progressTarget, this.customSettings, "uploadComplete");
						progress.toggleCancel(false, this);
					}
				}
			}

			function FileProgress(file, contentTargetId, customSettings, methodname) {
				var cache = FileProgress.instances[file.id];

				if (cache){
					return cache;
				}
				this.file = file;

				var contentTargetEl = $('#'+contentTargetId);

				if (contentTargetEl && methodname == "fileQueued"){
					$this._showList(contentTargetEl, file);
				}
				FileProgress.instances[file.id] = this;
			};

			FileProgress.instances = {};

			/**
			 *????????????
			 */
			FileProgress.prototype.setProgress = function (percentage) {
					var fileEl = $('#'+this.file.id);
					if (fileEl){
						fileEl.find('#att_prog_'+this.file.id).width(percentage + "%");
					}
			};

			FileProgress.prototype.setComplete = function (result) {
					var el = $('#'+this.file.id);
					if (el){
						   setTimeout(function(){
							   el.find(".jazz-att-progress").hide();						   
						   }, 800);
						   //????????????????????????fileid, ????????????progress??????
						   el.find("input[id=hiddenFileId]").val(result.id);
						   el.attr("fileid", result.id);
					}
			};
			FileProgress.prototype.setError = function () {
					var el = $('#'+this.file.id);
					if (el){
						el.find(".jazz-att-progress-i").css({background: "red", width: "100%"});
						
					}
			};
			FileProgress.prototype.setCancelled = function (swfuploadInstance) {
			};
			FileProgress.prototype.setStatus = function (status) {
					var el = $('#'+this.file.id);
					if (el){
						el.find(".status").get(0).innerHTML=status;
					}
			};

			FileProgress.prototype.toggleCancel = function (show, swfuploadInstance) {
					var buttonId = swfuploadInstance.customSettings.disableButtonId;
					var fileEl = $('#'+this.file.id);
					if (fileEl){
						if (! buttonId){
							var btnEl = $('#'+buttonId).get(0);
							if (!btnEl){
								btnEl.disabled = show;
							}
						}
					}else{
						if (!buttonId){
							var btnEl = $('#'+buttonId).get(0);
							if (btnEl){
								btnEl.disabled = false;
							}
						}
					}
			};			
			
	  },
	  
	/**
	 * @desc ??????
	 * @param {swfInstance} ????????????
	 * @private
	 */
	  _onLoad: function(swfInstance){
		  	var $this = this;
		  	if (swfInstance){
		  		$this._swfEvent();
		  		return;
		  	}
		  	throw '??????flash???????????????';
	  },
	  
	/**
	 * @desc ??????swf??????
	 * @private
	 */		  
	  _swfEvent: function() {
		  	var $this = this;
		  	//??????????????????
			this.inputFrame.on('mousedown.attachment', function(event) {
				  var target = event.target, $target = $(target);
				  
				  if($target.is('a')){
					  var action = $target.attr("_act");
					  var fileObj = $target.parent().parent();
					  if ("att_remove_swf" === action){
						    var file = $this.getFile(fileObj);
							$this.deleteFile(file);
							return;
					  
					  }else if ("download_file" === action){
					  	    var file = $this.getFile(fileObj);
							if (!!file){
								var _url = jazz.config.contextpath + ($this.options.downloadurl || jazz.config.default_download_url) + '?name='+encodeURI(file.fileName)+'&fileId='+file.hiddenFileId; 
								
								//??????form????????????
								var form=$("<form>"); //????????????form??????
								form.attr("style", "display:none");
								form.attr("target", "");
								form.attr("method", "post");
								form.attr("action", _url);

								$("body").append(form); //??????????????????web???

								form.submit(); //????????????						
							}
							return;
					  }
				  }
			});
	  },

	/**
	 * @desc ????????????????????????
	 * @private
	 */		  
	  destroy: function(){
		  var swf = this.swfInstance;
		  swf.destroy();
	  },
	  
	/**
	 * @desc ????????????
	 * @private
	 */		  
	  deleteFile: function(file){
		    var $this = this;
			setTimeout(function(){
				 var _id = file.deleteId || file.id;
				 $("#"+_id).remove();
           }, 200); 
	  		var swf = this.swfInstance,
	  			fileIndex = file.index, fileId = file.hiddenFileId || file.id;
	  		if (!swf){
	  			return;
			} 

	  		if (!!fileIndex){
				var cacheFile = swf.getFile(parseInt(fileIndex));
				if (cacheFile){//??????SWF????????????
					if (SWFUpload.FILE_STATUS.QUEUED===cacheFile.filestatus || SWFUpload.FILE_STATUS.IN_PROGRESS===cacheFile.filestatus ){
						swf.cancelUpload(cacheFile.id,false);
						var progress = new FileProgress(cacheFile, swf.customSettings.progressTarget);
						progress.toggleCancel(false, swf);
						return ;
					}else if (SWFUpload.FILE_STATUS.ERROR===cacheFile.filestatus || SWFUpload.FILE_STATUS.CANCELLED===cacheFile.filestatus){
						//return ;
					}
				}
			}
	  	
	  		var up = swf.customSettings.alreadyupload;
			for(var i=0, len=up.length; i<len; i++){
				if(up[i] == file["fileName"]){
					swf.customSettings.alreadyupload[i] = "XXX_XXX"
				}
			}
			
	  		if(this.options.theme == 0){
	  			this._event('close', null, file);
	  		}
			
//			//???????????????????????????
//			if (!!fileId) {
//	            var params = {
//	        		url: jazz.config.contextpath + jazz.config.default_upload_url,
//	        		params: {
//						type: 'delete',
//						id: fileId
//						//callback: this._callback  //????????????
//						//_callback: function (data, sourceThis){
//					},
//	        		async: true
//	            };
//	        	$.DataAdapter.submit(params, this);			
//			}
			
			//??????SWF??????
			var stats = swf.getStats();
			stats.successful_uploads--;
			swf.setStats(stats);
	  },
	  
	  /**
		*????????????????????????
		*@param value ?????????
		*@return ????????????????????? 
		*/
	  countFileDisplaySize: function(size) {
			if (typeof size == "string"){
				return size;
			}
			var n = 0;
			var displaySize = [];
			var ONE_KB = 1024, ONE_MB = ONE_KB * ONE_KB, ONE_GB = ONE_KB * ONE_MB;
			if (Math.floor(size / ONE_GB) > 0) {
				n = this.format(Math.floor(size * 100.0 / ONE_GB) / 100,2);
				displaySize.push(n + "GB");
			} else if (Math.floor(size / ONE_MB) > 0) {
				n = this.format(Math.floor(size * 100.0 / ONE_MB) / 100,2);
				displaySize.push(n + "MB");
			} else if (Math.floor(size / ONE_KB) > 0) {
				n = this.format(Math.floor(size * 100.0 / ONE_KB) / 100,2);
				displaySize.push(n + "KB");
			} else {
				n = size;
				displaySize.push(n + "Bytes");
			}
			displaySize.push(n);
			return displaySize;
	  },
	  
	  countFileSize: function(size){
		  var n = 0;
		  if(typeof size != "string"){
			  return n;
		  }
		  var ONE_KB = 1024, ONE_MB = ONE_KB * ONE_KB, ONE_GB = ONE_KB * ONE_MB;
		  n = parseFloat(size.substring(0, size.length-2)); size = size.substring(size.length-2, size.length);
		  if(size == "KB"){
			  n = parseFloat(n) * ONE_KB;
		  }else if(size == "MB"){
			  n = parseFloat(n) * ONE_MB;
		  }else if(size == "GB"){
			  n = parseFloat(n) * ONE_GB;
		  }
		  return n;
	  },
		
	/**
	 *????????????????????????
	 *@param value ?????????
	 *@param decimalLength ????????????
	 *@return ???????????????????????? 
	 */
	 format: function(value,decimalLength) {
			value = Math.round(value * Math.pow(10, decimalLength));
			value = value / Math.pow(10, decimalLength);
			var v = value.toString().split(".");
			if (v[0]==""){
				v[0]="0";
			}
			if (v.length > 1) {
				var len = v[1].length;
				if (len > decimalLength) {
					v[1] = v[1].substring(0, decimalLength);
				}
				return v[0] + "." + v[1];
			}	
			return v[0];
	 },
	 
	/**
	 * @desc ???????????????????????????jquery??????
	 * @private
	 */		  
	  getControl:function(){
		  return $('div[name="'+this.id+'"]');
	  },

	/**
	 * @desc ????????????
	 * @private
	 */
	  getFile: function(fileEl){
		  var hiddenFileId = fileEl.find('input[id="hiddenFileId"]').val();
		  var fileName = fileEl.find('a[_act="download_file"]').text();
		  return {
	 		  id: fileEl.attr('id'),
			  index: fileEl.attr("_fileIndex"),
			  hiddenFileId: hiddenFileId,
			  fileName: fileName
		  };
	  },
	  
	/**
	 * @desc ??????????????????
	 * @private
	 */		  
	  getFiles: function(){
		  var $this = this;
	  	  var fileContainer = this.getControl(), files=[],
  		  fileEls = fileContainer.find('div.jazz-att-list-p');
	  	  $.each(fileEls, function(index, item){
	  		  var file = $this.getFile($(item)), fileId = file.hiddenFileId;
	  		  if (!!fileId){
	  			 files.push(file);
	  		  };
	  	  });
		  return files;
	  },
	  
	/**
	 * @desc ?????????????????????????????????id
	 * @private
	 */		  
	  getFileIds: function(){
		var $this = this;
	  	var fileContainer = this.getControl(), fileIds=[],
	  		fileEls = fileContainer.find('div.jazz-att-list-p');
		  	$.each(fileEls, function(index, item){
		  		var file = $this.getFile($(item)), fileId = file.hiddenFileId;
		  		if (!!fileId){
		  			fileIds.push(fileId);
		  		};
		  	});
	  	return fileIds.join("##");
	  },
	  
	  /**
	   * @desc ??????????????????????????????
	   * @return [{name: '', fieldId: ''},{}]
	   */
	  getValue: function(){
		  var files = this.getFiles();
		  var attach = [];
		  if(files){
			  for(var i=0, len=files.length; i<len; i++){
				  var obj = {}, f = files[i];
				  if($.inArray(f.hiddenFileId, this.datadisplay) == -1){
					  obj['name'] = f.fileName;
					  var _fileid = f.hiddenFileId;
					  if(_fileid=="undefined"){
						  _fileid = f["id"];
					  }
					  obj['fileId'] = _fileid;
					  attach.push(obj);
				  }
			  }
		  }
		  return jazz.jsonToString(attach);
	  },
	  
	  /**
	   * @desc ??????????????????????????????
	   * @return {}
	   */
	  getText: function(){
		  var files = this.getFiles();
		  var attach = [];
		  if(files){
			  for(var i=0, len=files.length; i<len; i++){
				  var obj = {}, f = files[i];
				  attach.push(f.fileName);
			  }
		  }
		  return attach.join(",");
	  },
	  
	  /**
	   * @desc ??????????????????????????????
	   * @example $('XXX').attachment('reset');
	  */
	  reset: function() {},	  
	  
	  /**
	   * @desc ?????????????????????
	   * @param {data} ??????????????????????????????
	   */
	  setValue: function(data){
		    if(!data) return false; 
		    
	 		var $this = this, swf = this.swfInstance;
	 		if (!swf){
				return;
			}
			var contentTargetEl = $('#'+this.id + 'ProgressContainer');
			
			if(typeof(data) != 'object'){
				data = jazz.stringToJson(data);
			}
			
			for(var i=0, len=data.length; i<len; i++){
				swf.settings.custom_settings.alreadyupload.push(data[i]["name"]);
				this._showList(contentTargetEl, data[i], true, i, data);
			}
			
			//$this.masterId = masterId;
			swf.setPostParams({
				//masterId: $this.masterId,
				uploadSession: $this.uploadSession
	 		});
	  },

	  /**
	   * @desc ??????????????????   flag=true ???????????????????????????  index ????????????  dataDisplay ???????????????
	   * @private  
	   */		  
	  _showList: function(contentTargetEl, file, flag, index, dataDisplay){
		  var _none = '', _theme = this.options.theme; 
		  if(_theme == 1){
			  _none = 'style="display: none;"';
		  }
		  
		  var fieldId = file.id || file.fileId;
		  var hiddenFileId = file.hiddenFileId;
		  var size = file.size;
		  var index = file.index || index;
		  
		  if(flag == true){ //????????????
			  hiddenFileId = file.id;
			  this.datadisplay.push(hiddenFileId);
		  }

		  size = this.countFileDisplaySize(size);
		  var str = '<div class="jazz-att-list-p" id="'+fieldId+'" _fileindex="'+index+'">'
				+'<span class="jazz-att-normal" '+_none+'>'
				+'<cite><a tabindex="-1"  href="#" _act="download_file">'+file.name+'</a><em style="margin-left:5px;">('+size[0]+')</em></cite>'
				+'<input type="hidden" id="hiddenFileId" value="'+hiddenFileId+'"/>';
				if(_theme != 1){
					if(!flag){
						str += '<b class="jazz-att-progress"><i id="att_prog_'+fieldId+'"></i></b>';
					}
				}
				str += '<a tabindex="-1" href="#" _act="att_remove_swf">??????</a>'
				    +'<em class="status" style="color: red; margin-left: 6px;display: none;">????????????.</em>'
			    +'</span>';
				if(_theme == 1){
					str+='<div id="'+fieldId+'_img" class="jazz-att-theme1-imgbg jazz-att-theme1-size">'
					if(!flag){
						str+='<b class="jazz-att-progress jazz-att-progress-t1"><i id="att_prog_'+fieldId+'" class="jazz-att-progress-i"></i></b>'
					}
					str+='</div>';
					}						    
				 str+='</div>';
	
			  contentTargetEl.append(str); 
			  
			  if(flag == true){
				  this._showListEvent(file, dataDisplay, this.swfInstance.settings.custom_settings);
			  }
	  },
	  
	  /**
	   * @private
	   * @param {file}
	   * @param {data}
	   * @param {custom_settings}
	   */
	  _showListEvent: function(file, data, custom_settings){
			//???options.theme==1??????????????????????????????????????????
			if(this.options.theme == 1){
				data = jazz.stringToJson(data);
				var _fileId = file["id"] || file["fileId"];
				var _attachId = data["id"] || _fileId;
				if(_attachId){
					var _url = (this.options.previewurl || jazz.config.default_preview_url) + "?fileId="+_attachId;
					//????????????
					var obj = $("#"+this.id+"ProgressContainer").find("#"+_fileId+"_img");
					obj.children().remove();
					var imgType = file["type"] || file["name"];
					if(imgType == ".doc" || imgType == ".docx" || /.*(\.doc|\.docx)$/i.test(imgType)){
						obj.addClass("jazz-att-theme1-size").addClass("jazz-att-word");
					}else if(imgType == ".pdf"  || /.*(\.pdf)$/i.test(imgType)){
						obj.addClass("jazz-att-theme1-size").addClass("jazz-att-pdf");
					}else{
						obj.append('<img src="'+_url+'" class="jazz-att-theme1-size" />');									
					}
					//??????????????????
					var $close = $('<div class="jazz-att-theme1-close"></div>').appendTo(obj);
					var data = $.extend(file, data, {previewurl: _url}, {deleteId: _fileId});
					var $this = this;
					$close.css("display", "block");
					obj.on("mouseenter", function(e){
						//$close.css("display", "block");
						$this._event("enter", e, data);
					}).on("mouseleave", function(e){
						//$close.css("display", "none");
						$this._event("leave", e, data);
					}).on("mousedown", function(e){
						  var target = e.target, $target = $(target);
						  if($target.is('.jazz-att-theme1-close')){
							  if(!$this.options.isrepeatupload){
								  var arr = [];
								  var alreadyupload = custom_settings.alreadyupload;
								  for(var i = 0, len = alreadyupload.length; i < len; i++){
									  if(alreadyupload[i] == file["name"]){
									  }else{
										  arr.push(alreadyupload[i]);
									  }
								  }
								  $this.swfInstance.settings.custom_settings.alreadyupload = arr;
							  }
							  $this._event("close", e, data);
						  }else{
							  $this._event("click", e, data);								  
						  }
					});
				}
			}		  
	  },
	  
	  /**
	   * @desc ??????????????????????????????
	   * @example $('XXX').attachment('toggleLabel');
	  */
	  toggleLabel: function() {}

});

});

