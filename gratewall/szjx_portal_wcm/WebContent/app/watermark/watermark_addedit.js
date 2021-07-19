var ObjectTypeConst_WATERMARK = 1377689707;//to be a global var?
var FILE_TYPES = {gif:0,jpg:1,jpeg:2,bmp:3,png:4};
var PageContext = {};
Object.extend(PageContext,{
	serviceId : 'wcm6_watermark',
	init : function(_params){			
		this.params = _params || {};
		this.params.ObjectId = getParameter("ObjectId");
		this.params.LibId = getParameter("siteid");		
	},
	loadObj : function(_params){
		PageContext.resizeImgIfNeed($("wmpic").src);
		Event.observe($("wmname"),"keydown",function(_evt){
			var evt = window.event || _evt;
			if(evt.keyCode == 13){
				saveWaterMark();
				return false;
			}
		});

		if(Ext.isIE){//FF下input file只能取到文件名.暂不显示
			Event.observe($("wmpicture_in"),'change',function(){					
				var fn = $("wmpicture_in").value;						
				var fext = fn.substr(fn.lastIndexOf(".")+1);	
				if(FILE_TYPES[fext.toLowerCase()] == void(0)){
					setTimeout(function(){
						Ext.Msg.alert( String.format("不是有效的水印图片![{0}]</br> 只支持.jpg,.gif,.jpeg,.png,.bmp格式图片",fn));
						$("wmpicture_in").focus();
					},100);
					var clazzName = $("wmpicture_in").className;
					$("wmpicture_in").className = " invalid_file";
					return true;
				}

				$("wmpicture_in").className = "input_file";
				PageContext.resizeImgIfNeed(fn);
				
				$("wmpic").src = fn;
				$("wmpic").title = wcm.LANG.WATERMARK_PROCESS_2 || "点击查看原图\n\r" + fn;	
				Element.show($("pic_showarea"));
				fn = null;
				fext = null;
			});
		}
		Event.observe($("wmpic"),'click',function(){
				var sUrl = $("wmpic").src;						
				if(sUrl.indexOf("/webpic/W0") == -1){						
					window.open("localpic_view.html?LocalFile="+sUrl);
				}else{
					window.open(sUrl);
				}
		});
		
		ValidationHelper.addValidListener(function(){
			FloatPanel.disableCommand('saveWaterMark', false);
		}, "addEditForm");
		ValidationHelper.addInvalidListener(function(){
			FloatPanel.disableCommand('saveWaterMark', true);
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
			//默认不选中新建水印
			notifyFPCallback();
			FloatPanel.close();			
		});
	},
	uploadAndSave : function(_fn){
		ProcessBar.init(wcm.LANG.WATERMARK_PROCESS_3 || '执行进度，请稍候...');
		try{
			FileUploadHelper.validFileExt(_fn, ".jpg,.gif,.jpeg,.png,.bmp");
		}catch(err){
			Ext.Msg.alert(err.message, function(){
			});
			return;
		}
		ProcessBar.addState((wcm.LANG.WATERMARK_PROCESS_4 || '正在上传文件:')+_fn);
		ProcessBar.addState(wcm.LANG.WATERMARK_PROCESS_5 || '成功上传文件.');
		ProcessBar.start();
		var callBack1 = {
			"upload":function(_transport){
				var sResponseText = _transport.responseText;			
				if(sResponseText.match(/<!--ERROR-->/img)){
					var texts = sResponseText.split('<!--##########-->');
					Ext.Msg.fault({
						message : texts[1],
						detail : texts[2]
					},wcm.LANG.WATERMARK_PROCESS_6 || '与服务器交互时出错啦！');
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
			Ext.Msg.alert(err.message);
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

LockerUtil.register2(PageContext.params.ObjectId,ObjectTypeConst_WATERMARK,true,"saveWaterMark");
Event.observe(window,'load',function(){		
		PageContext.loadObj();		
});