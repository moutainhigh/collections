var ObjectTypeConst_WATERMARK = 1377689707;//to be a global var?
var FILE_TYPES = {gif:0,jpg:1,jpeg:2,bmp:3,png:4};

Object.extend(PageContext,{
	serviceId : 'wcm6_watermark',
	init : function(_params){			
		this.params = _params || {};
		this.params.ObjectId = getParameter("ObjectId");
		this.params.LibId = getParameter("siteid");		
	},
	loadObj : function(_params){
		var self = this;
		if(this.params.ObjectId > 0){
			BasicDataHelper.Call(this.serviceId,"findById",this.params,true,function(_transport,_json){						
				self.onObjLoaded(_transport,_json);
			});
		}else{
			this.onObjLoaded(null,{});
		}
	},
	onObjLoaded : function(_transport,_json){
		var sValue = TempEvaler.evaluateTemplater('template_area', _json);			
			Element.update($("area_holder"),sValue);
			PageContext.resizeImgIfNeed($("wmpic").src);
			Event.observe($("wmname"),"keydown",function(_evt){
				var evt = window.event || _evt;
				if(evt.keyCode == 13){
					saveWaterMark();
					return false;
				}
			});

			Event.observe($("wmpicture_in"),'change',function(){
				var fn = $("wmpicture_in").value;
				var fext = fn.substr(fn.lastIndexOf(".")+1);	
				if(FILE_TYPES[fext.toLowerCase()] == void(0)){
					setTimeout(function(){
						$alert("不是有效的水印图片![" + fn + "]", function(){
							$dialog().hide();
							$("wmpicture_in").focus();
						});	
					},100);
					var clazzName = $("wmpicture_in").className;
					$("wmpicture_in").className = " invalid_file";
					return true;
				}

				$("wmpicture_in").className = "input_file";
				PageContext.resizeImgIfNeed(fn);
				$("wmpic").src = fn;
				$("wmpic").title = "点击查看原图\n\r" + fn;	
				Element.show($("pic_showarea"));
				fn = null;
				fext = null;
			});

			Event.observe($("wmpic"),'click',function(){
					var sUrl = $("wmpic").src;					
					if(sUrl.indexOf("/webpic/W0") == -1){						
						window.open("localpic_view.html?LocalFile="+sUrl);
					}else{
						window.open(sUrl);
					}
			});

			ValidationHelper.addValidListener(function(){
				FloatPanel.disableCommand('savebtn', false);
			}, "addEditForm");
			ValidationHelper.addInvalidListener(function(){
				FloatPanel.disableCommand('savebtn', true);
			}, "addEditForm");
			ValidationHelper.initValidation();
	},
	resizeImgIfNeed : function(_fn){
		var imageLoader = new Image();
		imageLoader.onload = function(){
			var height = imageLoader.height,width = imageLoader.width;		
			var h=height,w=width;
			if(height > 124 || width > 97){	
				if(height > width){				
					h = 124;	
					w = 124 * width/height;
				}else{				
					w = 97;
					h = 97 * height/width;
				}			
			}
			
			$("wmpic").width = w;
			$("wmpic").height = h;
			imageLoader.onload = null;
		}
		imageLoader.src = _fn;		
	},
	save : function(){
		if(this.params.ObjectId == 0){
			$("LibId").value = this.params.LibId;
		}		
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call(this.serviceId,'save','addEditForm',true,function(_transport,_json){
			$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', []);
			FloatPanel.close();			
		});
	},
	uploadAndSave : function(_fn){
		ProcessBar.init('执行进度，请稍候...');
		if(!FileUploadHelper.validFileExt(_fn, ".jpg,.gif,.jpeg,.png,.bmp")){
			return false;
		}
		
		ProcessBar.addState('正在上传文件:'+_fn);
		ProcessBar.addState('成功上传文件.');
		ProcessBar.start();
		
		var callBack1 = {
			"upload":function(_transport){
				var sResponseText = _transport.responseText;			
				if(sResponseText.match(/<!--ERROR-->/img)){
					var texts = sResponseText.split('<!--##########-->');
					FaultDialog.show({
						code		: texts[0],
						message		: texts[1],
						detail		: texts[2],
						suggestion  : ''
					}, '与服务器交互时出错啦！');
					ProcessBar.close();
				}
				else{						
					$('wmpicture').value = sResponseText;
					PageContext.save();
					ProcessBar.next();
					setTimeout(ProcessBar.next.bind(ProcessBar),10);
				}
			}
		}
		try{
			YUIConnect.setForm('addEditForm',true,false);
			YUIConnect.asyncRequest('POST','../system/file_upload_dowith.jsp?FileParamName=wmpicture_in',callBack1);
		}catch(err){
			ProcessBar.exit();
			$alert(err.message);
		}
	}
});
PageContext.init();

function saveWaterMark(){	
	if(PageContext.params.ObjectId>0 && !LockerUtil.lock(PageContext.params.ObjectId,ObjectTypeConst_WATERMARK))return false;
	if(!ValidationHelper.doValid('addEditForm')){
		return false;
	}			
	
	var sPicName = $("wmpicture_in").value.trim();		
	var sOldPicName = $F("wmpicture").trim();
	if((sOldPicName != sPicName && sPicName.length > 0)|| sOldPicName.length == 0 ){
		PageContext.uploadAndSave(sPicName);
	}else{
		PageContext.save();
	}

	return false;
}

function showPic(_fn){		
	if(_fn != null && (_fn.trim().indexOf("W0") == 0)){
		return "inline";
	}

	return "none";
}

function mapFileName(_fn){	
	return "/webpic/" + _fn.substr(0,8) + "/" + _fn.substr(0,10) + "/" + _fn;
}

function resizeIfNeed(height,width){	
	
}

LockerUtil.register2(PageContext.params.ObjectId,ObjectTypeConst_WATERMARK,true,"savebtn");
Event.observe(window,'load',function(){		
		PageContext.loadObj();		
});