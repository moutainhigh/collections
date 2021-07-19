var PageContext ={};
Object.extend(PageContext,{
	init : function(_params){
		var docId = getParameter("DocId")||getParameter("DocumentId")||0;
		var channelId = getParameter("ChannelId")||0;
		var siteId = getParameter("SiteId")||0;
		var flowDocId = getParameter("FlowDocId") || 0;		
		this.params = {ObjectId:docId,ChannelId:channelId,SiteId:siteId,FlowDocId:flowDocId};
		Element.show("bodyDiv");
	},
	loadProps : function(){
		$("FlowDocId").value = PageContext.params.FlowDocId;
		resizeImgIfNeed($("photoitem").src);
		ValidationHelper.addValidListener(function(){
			FloatPanel.disableCommand('Ok', false);
		}, "form_photoprops");
		ValidationHelper.addInvalidListener(function(){
			FloatPanel.disableCommand('Ok', true);
		}, "form_photoprops");
		ValidationHelper.initValidation();
//		PageContext.loadExtendedProps();
//
		var picUploader = new PicReuploader();			
		Event.observe($("photouploader"),'click',picUploader.upload);
	},
	loadExtendedProps : function(){
		BasicDataHelper.call("wcm6_photo","getExtendedProps",this.params,true,function(_transport,_json){			
			var sValue = TempEvaler.evaluateTemplater('template_imageextprops', _json);			
			Element.update($("attr_extend"),sValue);
		});
	},
	save : function(){
		BasicDataHelper.call("wcm6_document","save",'form_photoprops',true,function(_transport,_json){
			notifyFPCallback(_transport);
			FloatPanel.close();	
		});
	}
});

PageContext.init();

function Ok(){
	if(ValidationHelper.doValid("form_photoprops")){
		PageContext.save();	
	}
	
	return false;
}

function resizeImgIfNeed(_fn){
	var fn = "../images/photo/pic_notfound.gif";
	if(_fn == null || _fn.trim().length == 0){
		return fn;
	}
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
		
		$("photoitem").width = w;
		$("photoitem").height = h;
		imageLoader.onload = null;
	}
	imageLoader.src = _fn;		
}

PicReuploader = Class.create("PicReuploader");
PicReuploader.prototype = {
	initialize : function(){
	},
	upload : function(){
		//Event.stop(window.event);
		if(!LockerUtil.lock(PageContext.params.ObjectId,605)) return false;
		Object.extend(PageContext.params,{LibId:PageContext.params.SiteId});
		var link = WCMConstants.WCM6_PATH + 'photo/photo_reupload.jsp';
		var cb = wcm.CrashBoard.get({
			title :wcm.LANG.PHOTO_CONFIRM_55 || '重新上传图片',
			url : link,
			width: '500px',
			height: '200px',
			params : PageContext.params,
			callback : function(){
				refreshImg();
			}
		});
		cb.show();
		return false;
	}
}
function $openMaxWin(_sUrl, _sName, _bResizable){
	var nWidth	= window.screen.width - 12;//document.body.clientWidth;
	var nHeight = window.screen.height - 60;//document.body.clientHeight;
	var nLeft	= 0;//(window.screen.availWidth - nWidth) / 2;
	var nTop	= 0;//(window.screen.availHeight - nHeight) / 2;
	var sName	= _sName || "";

	var oWin = window.open(_sUrl, sName, "resizable=" + (_bResizable == true ? "yes" : "no") + ",top=" + nTop + ",left=" + nLeft + ",menubar =no,toolbar =no,width=" + nWidth + ",height=" + nHeight + ",scrollbars=yes,location =no,titlebar=no");
	if(oWin)oWin.focus();
}

function editPhoto(_id,_siteid){
	var winUrl = WCMConstants.WCM6_PATH + "photo/photo_origin_edit.jsp?PhotoId="+_id+"&SiteId="+_siteid;
	if(!LockerUtil.lock(_id,605)) return;
	$openMaxWin(winUrl);
}

function refreshImg(){	
	window.location.reload();
//	var imgsrc = $("photoitem").src;
//	if(imgsrc.indexOf("?") == -1){
//		imgsrc += "?r"+Math.random();
//	}else{
//		imgsrc += "&r="+Math.random();
//	}
//
//	$("photoitem").src = imgsrc;
}

LockerUtil.register2(PageContext.params.ObjectId,605,true,"Ok");

Event.observe(window,'load',function(){	
	PageContext.loadProps();
});
Event.observe(window, 'load', function(){
	if(!$('DocKeywords'))return;
	var sg1 = new wcm.Suggestion();
	var sInputValue = "";
	sg1.init({
		el : 'DocKeywords',
		autoComplete : false,
		requestOnFocus : false,
		execute : function(){
			//TODO override the method.
			if(sInputValue != "" && sInputValue.charAt(sInputValue.length-1)!=";"){
				sInputValue += ";";
			}
			var sNewInputValue = this.getListValue();
			var inputValueArr = [];
			var oldInputValueArr = sInputValue.split(/[\s　,,;; . ,]/g );
			var newInputValueArr = sNewInputValue.split(/[\s　,,;; . ,]/g );
			for(var z=0;z<newInputValueArr.length;z++){
				if(!oldInputValueArr.include(newInputValueArr[z]))inputValueArr.push(newInputValueArr[z]);
			}
			sNewInputValue = inputValueArr.join(";")
			sInputValue += sNewInputValue;
			this.setInputValue(sInputValue);
			//alert(this.getInputValue());
		},
		request : function(sValue){			
			var items = [];
			var nLen = sValue.length;
			var arr = ["," , " " , "," , ";" , ";", ",", "."];
			if(sValue=="")sInputValue="";
			var arr1 = sValue.split(/[\s　,,;; . ,]/g );
			var arr2 = sInputValue.split(";");
			var arr3 = [];
			for(var m=0;m<arr2.length;m++){
				if(arr1.include(arr2[m]))arr3.push(arr2[m]);
			}
			if(arr.include(sValue.charAt(nLen-1))&&(!arr2.include(arr1[arr1.length-1])))arr3.push(arr1[arr1.length-1]);
			sInputValue = arr3.join(";");
			if(arr.include(sValue.charAt(nLen-1)))return;
			for (var i = 0; i < nLen; i++){
				var sChar = sValue.charAt(nLen-i-1);
				if(arr.include(sChar)){
					sValue = sValue.slice(nLen-i,nLen);
					break;
				}
				continue;
			}
			var oPostData = {
				siteType : nSiteType,
				siteId : nSiteId,
				kname : sValue
			}
			BasicDataHelper.JspRequest('../keyword/keyword_create.jsp',oPostData,false,function(_trans,_json){
				//debugger
				var json = com.trs.util.JSON.eval(_trans.responseText.trim());
				for(var i=0;i<json.length;i++){
					items.push(json[i]);
				}
				sg1.setItems(items);
			}.bind(this));
		}
	});
});