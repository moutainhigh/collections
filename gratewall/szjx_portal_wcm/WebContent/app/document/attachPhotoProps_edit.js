var PageContext = {};
Object.extend(PageContext,{
	init : function(_params){
		var docIds = getParameter("DocIds")||"0";
		var channelId = getParameter("ChannelId")||0;
		var siteId = getParameter("SiteId")||0;
		this.params = {ChannelId:channelId,SiteId:siteId,DocIds:docIds};		
		this.pDocIds = docIds.split(",");		
		Element.show("bodyDiv");
	},
	loadProps : function(_id){
		Object.extend(this.params,{ObjectId:_id});
		BasicDataHelper.call("wcm6_document","findById",this.params,true,function(_transport,_json){
			$("ObjectId").value = trim($v(_json, "DOCUMENT.DOCID"));
			$("DocTitle").value = trim($v(_json, "DOCUMENT.DOCTITLE"));
			$("DocRelTime").value = $v(_json, "DOCUMENT.DOCRELTIME");
			$("DocContent").value = outShow($v(_json, "DOCUMENT.DOCCONTENT"));
			$("DocAuthor").value = outShow($v(_json, "DOCUMENT.DOCAUTHOR"));
			$("DocPeople").value = outShow($v(_json, "DOCUMENT.DOCPEOPLE"));
			$("DocPlace").value = outShow($v(_json, "DOCUMENT.DOCPLACE"));
			$("DocKeywords").value = outShow($v(_json, "DOCUMENT.DOCKEYWORDS"));
			Element.show("imgId");
			$("imgId").src = mapFileName(_json.DOCUMENT.DOCRELWORDS,1,_json.DOCUMENT.ATTRIBUTE.SRCFILE);
			$("imgId").setAttribute("sourceSrc",$("imgId").src);
			window.sOriginalImg = _json.DOCUMENT.ATTRIBUTE.SRCFILE;
			window.oOriginalImg = new Image();
			oOriginalImg.src = "../../file/read_image.jsp?FileName="+sOriginalImg;
			resizeImgIfNeed($("imgId").src);
		});
	},
	save : function(_finished){
		BasicDataHelper.call("wcm6_document","save",'form_photoprops',true,function(_transport,_json){
			if(_finished == true){
				changeStatus($F(ChnlDocs).split(","));
//              第二种刷新方式
				CMSObj.createFrom({
					objId : $v(_json, 'result'),
					objType : 'photo'//WCMConstants.OBJ_TYPE_PHOTO
				}).afterdelete();
				FloatPanel.close();	
				Ext.Msg.$alert(wcm.LANG.DOCUMENT_PROCESS_237 || "提取成功！");
			}
		});
	}
});
function changeStatus(sDocIds){
	if(sDocIds.length > 0){
		for(var i = 0 ; i < sDocIds.length ; i++){
			if(sDocIds[i] == "") continue;
			var oPostData = {
				'ObjectIds': sDocIds[i],
				'StatusId': 1
			};
			var sServiceId = 'wcm6_viewdocument';
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			oHelper.Call(sServiceId, 'changeStatus', oPostData, true,
				function(_transport, _json){
				}
			);
		}
	}
}
function trim(_sParam){
	if(_sParam==null){
			return "";
		}
	return _sParam;
}
PageContext.init();
var m_nCurrIndex = 0;
var m_nAll = PageContext.pDocIds.length;
function onPrevious(){
	if(m_nCurrIndex == 0){
		Ext.Msg.alert( wcm.LANG.PHOTO_CONFIRM_57 || "已经是第一幅图了!");
		return false;
	}
	
	if(!ValidationHelper.doValid("form_photoprops")){
		return false;
	}

	saveCropImage(function(){
		PageContext.save();	
		Element.update($("currpic"),m_nCurrIndex+"");
		m_nCurrIndex--;
		disableCommand();
		
		PageContext.loadProps(PageContext.pDocIds[m_nCurrIndex],m_nCurrIndex);
	});
	return false;
}

function disableCommand(){
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
		$("imgId").width = w;
		$("imgId").height = h;
		imageLoader.onload = null;
	}
	imageLoader.src = _fn;		
}

function onNext(){
	if(m_nCurrIndex == m_nAll-1){
		Ext.Msg.alert(wcm.LANG.PHOTO_CONFIRM_58 || "已经是最后一幅图了!");
		return false;
	}

	if(!ValidationHelper.doValid("form_photoprops")){
		return false;
	}
	saveCropImage(function(){
		PageContext.save();	
		m_nCurrIndex++;
		disableCommand();
		Element.update($("currpic"),m_nCurrIndex+1+"");	
		PageContext.loadProps(PageContext.pDocIds[m_nCurrIndex],m_nCurrIndex);	
	});
	return false;
}

function ok(){
	if(ValidationHelper.doValid("form_photoprops")){
		if($("imgId").getAttribute("sourceSrc") != $("imgId").src){
			saveCropImage(function(){
				PageContext.save(true);
			});
		}else{
			PageContext.save(true);
		}
	}
	
	return false;
}

function mapFileName(_fn,_ix,_default){
	var fn = "../images/photo/pic_notfound.gif";
	if(_fn == null || _fn.trim().length == 0){
		return fn;
	}

	var fs = _fn.split(",");
	
	if(fs.length < _ix){
		fn = fs[0];
	}else{
		fn = fs[_ix];
	}
	if(!fn){
		fn = fs[0];
	}
	return "/webpic/"+fn.substr(0,8)+"/"+fn.substr(0,10)+"/"+fn;
}

function outShow(param){
	if(param == null || param == "[object Object]")
		return "";
	return param;
}

Event.observe(window,'load',function(){
	PageContext.loadProps(PageContext.pDocIds[m_nCurrIndex]);
	ValidationHelper.initValidation();
});

var imgInfoOfCrop;
var bound;
function crop(){
	var oImg = window.oOriginalImg;
	//var nWidth = parseInt(oImg.width, 10);
	//var nHeight = parseInt(oImg.height, 10);
	//nWidth = Math.min(window.screen.width - 12, nWidth);
	var nWidth	= window.screen.width - 12;
	var nHeight = window.screen.height - 60;
	var parameters = "photo="+encodeURIComponent(oImg.src);
	//var sUrl = WCMConstants.WCM6_PATH + "photo/photo_crop.html?"+parameters;
	var sUrl = WCMConstants.WCM6_PATH + "photo/photo_compress.jsp?"+parameters;
	var dialogArguments = window;
	var sFeatures = "center:yes;dialogWidth:" + nWidth + "px;dialogHeight:" + nHeight +"px;status:no;resizable:no;";
	bound = window.showModalDialog(sUrl, dialogArguments, sFeatures);
	if(bound == null){
		return;
	}
	setNewImg(bound.FN);
	showCropInfo(bound);
}
function setNewImg(FN){
	$('imgId').src="../../file/read_image.jsp?FileName="+FN;
	$('imgId').setAttribute("orignsrc", window.oOriginalImg.src);
	window.oOriginalImg.src = $('imgId').src;
}
// 编辑图片时已经进行处理，不在这处理
/*function doCropWithServer(fCallback){
	BasicDataHelper.call(
		"wcm61_photo", 
		"cropImage", 
		{
			Filename : window.sOriginalImg,
			x:bound.left,
			y:bound.top,
			width:bound.width,
			height:bound.height
		}, 
		true, function(_transport, _json){
			imgInfoOfCrop = _json || {FN:""};
			if(fCallback) fCallback();
		}
	);
}
*/

function saveCropImage(fCallBack){
	/*if(!imgInfoOfCrop || !imgInfoOfCrop.FN){
		if(bound){
			saveCropImage(fCallBack);
			return;
		}
		if(fCallBack) fCallBack();
		return;
	}
	*/
	var src=$('imgId').src;
	if(src.indexOf("FileName")>=0)
		imgInfoOfCrop={FN:src.substring(src.indexOf("FileName=")+"FileName=".length)};
	else
		imgInfoOfCrop={FN:window.sOriginalImg};
	BasicDataHelper.call(
		"wcm6_photo",
		"save",
		{
			PHOTODOCID : PageContext.pDocIds[m_nCurrIndex],
			PHOTOFILE : imgInfoOfCrop.FN,
			WATERMARKFILE : ''
		},
		true,
		function(_transport,_json){
			hideCropInfo();	
			if(fCallBack) fCallBack();
		}
	);		
}

function cancelCrop(){
	$('imgId').src=$('imgId').getAttribute("orignsrc");
	window.oOriginalImg.src = $('imgId').src;
	hideCropInfo();
}

function showCropInfo(bound){
	//var sHtml = ['左[',bound.left,'],上[',bound.top,'],宽[',bound.width,'],高[',bound.height,']'].join("");
	//Element.update('scopeTip', sHtml);
	Element.show('cancelCropHandler');	
}

function hideCropInfo(){
	imgInfoOfCrop = null;
	bound = null;
	//Element.update('scopeTip', "");
	Element.hide('cancelCropHandler');	
}
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
				siteId : nSiteId4KeyWord,
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