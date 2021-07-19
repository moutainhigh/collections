ValidationHelper.validByValidation = function(sValidation, sValue){
	if(!sValidation) return true;
	var validation = eval('({' + sValidation + '})');

	//valid required.
	if(validation["required"] == 1 && sValue.length <= 0){
		Ext.Msg.alert((validation["desc"] || "") + "不能为空");
		return false;
	}
	//valid allowExt
	if(validation["allowExt"] && validation["allowExt"].length>0 && sValue.length>0){
		try{
			FileUploadHelper.validFileExt(sValue, validation["allowExt"]);
		}catch(err){
			Ext.Msg.$alert(err.message);
			return false;
		}
	}
	//valid max length.
	var maxLen = validation['max_len'] || 0;
	if(maxLen > 0 && sValue.length > maxLen){
		Ext.Msg.alert(String.fromat("{0}大于最大长度[{1}]",validation["desc"] || "",maxLen));
		return false;
	}	
	return true;
};

function validData(){	
	var components = com.trs.ui.ComponentMgr.getAllComponents();
	for (var i = 0; i < components.length; i++){
		var validation = components[i].getProperty('validation');
		if(!validation) continue;
		if(!ValidationHelper.validByValidation(validation, components[i].getValue())) return false;
	}
	return true;
};

function makeData(fn){
	if(!appNameAfterEdit){
		com.trs.ui.XAppendixMgr.upload(
			function(){
				//校验
				if(!validData()){
					return false;
				}
				//调用父页面的保存
				fn('data');
			},
			function(){
				Ext.Msg.alert(arguments[0]);
			}
		);
	}else{
		// 在组装数据之前需要，拷贝编辑后的文件到指定WEBPIC的目录下
		var parame = "fileName="+$(appNameAfterEdit).value+"&pathFlag=W0";
		YUIConnect.setForm(appNameAfterEdit+"-frm", true, Ext.isSecure);
		YUIConnect.asyncRequest('POST',"logo_update_after_edit.jsp?"+parame,{
			"upload":function(_transport){
				var sResponseText = _transport.responseText;
				eval("var result="+sResponseText);
				$(appNameAfterEdit).value = result["Message"];
				com.trs.ui.XAppendixMgr.upload(
					function(){
						//校验
						if(!validData()){
							return false;
						}
						//调用父页面的保存
						fn('data');
					},
					function(){
						Ext.Msg.alert(arguments[0]);
					}
				);
			}
		});
	}
}

/*AjaxCaller.onSuccess = function(transport, json){
	
	var cbr = wcm.CrashBoarder.get(window);
	cbr.notify(true);
	cbr.close();
};*/
Event.observe(window, 'load', function(){
	initValidation();
	//validData();
});
/*
*初始化校验处理
*/
function initValidation(){
	ValidationHelper.addValidListener(function(){
		//wcmXCom.get('').enable();
	},"data");
	ValidationHelper.addInvalidListener(function(){
		//wcmXCom.get('').disable();
	},"data");
	ValidationHelper.initValidation();
};

/*
* 显示flash图像
*/
function showFlash(swfPath){
	var flashvars = {
		autoPlay:"false",
		logoAlpha:0,
		isAutoBandWidthDetection:"false",
		videoSource : swfPath
	};
	swfobject.embedSWF(swfPath, "flashcontent", 
				   400, 100, "9.0.124", false, flashvars, {allowFullScreen:"true",wmode:"opaque"}, {});
	Element.addClassName(pic_elem,"hide");//.style.display="none";
	Element.removeClassName(flash_elem,"hide");
	//$("flashcontent").style.display="";
	disabledEditButton(true);
	$("pic_size").innerHTML = "";

	Element.removeClassName($("WIDTH_row"),"hide");
	Element.removeClassName($("HEIGHT_row"),"hide");
	if(!dimension)return;
	// 设置flash人宽度，高度取默认值
	$("WIDTH").value=dimension.width;
}


var dimension = null;
/*
* 编辑图片成功后设置新的图片
*/
function refesh(_sFileName,_el,_path){
	if(isFlash(_sFileName)){
		showFlash(_path);
		return;
	}
	Element.removeClassName(pic_elem,"hide");
	Element.addClassName(flash_elem,"hide");
	Element.addClassName($("WIDTH_row"),"hide");
	Element.addClassName($("HEIGHT_row"),"hide");
	pic_elem.style.display="";
	// 更换显示的图片
	if(_sFileName.indexOf("http")>=0)
		pic_elem.src = _sFileName;
	else
		pic_elem.src="../../../file/read_image.jsp?FileName="+_sFileName;
	// 更新附件名称
	if($(_el))
		$(_el).value=_sFileName;
	// 重置图片的信息
	currImg.src=pic_elem.src;
}

/*
* 判断一个文件是否是flash文件
*/
function isFlash(_sFileName){
	return _sFileName.substring(_sFileName.lastIndexOf(".")+1).toLowerCase()=="swf";
}

/*
* 设置图片的合适的size
*/
function setSuitSize(){
	if(currImg.width>400)
		pic_elem.style.width="400px";
	else
		pic_elem.style.width=currImg.width+"px";
}
/*
*	判断是否有图片之后的操作
*/
function hasPic(_boolean){
	if(!_boolean){
		Element.removeClassName(pic_elem,"hide");
		Element.addClassName(flash_elem,"hide");
		pic_elem.src="../images/wt.gif";
		// 显示无图片
	}
	currImg.src=pic_elem.src;
	disabledEditButton(!_boolean);
	Element.addClassName($("WIDTH_row"),"hide");
	Element.addClassName($("HEIGHT_row"),"hide");
}

var appNameAfterEdit = null;
/*
*	图片编辑接口
*/
function pic_edit(_el){
	if(!bEnablePicLib){
		Ext.Msg.error("缺少编辑图片的相关类，请先确认是否正确安装了图片库选件！");
		return;
	}
	appNameAfterEdit = _el;
	var oImg = currImg;
	var nWidth	= window.screen.width - 12;
	var nHeight = window.screen.height - 60;
	var parameters = "photo="+encodeURIComponent(oImg.src);
	if(suit_width>0){
		parameters+="&Width="+suit_width;
	}
	var sUrl = WCMConstants.WCM6_PATH + "photo/photo_compress.jsp?"+parameters;
	var dialogArguments = window;
	var sFeatures = "center:yes;dialogWidth:" + nWidth + "px;dialogHeight:" + nHeight +"px;status:no;resizable:no;";
	bound = window.showModalDialog(sUrl, dialogArguments, sFeatures);
	if(!bound)
		return;
	refesh(bound.FN,_el);
}

/*
*	当前图片
*/
var currImg = new Image();
var pic_elem;
var flash_elem;
Event.observe(currImg,"load",function(){
	initCurrImg();
});
Event.observe(window,"load",function(){
	init();
});

/*
*	文档载入初始化
*
*/
function init(){
	// 初始化信息显示
	pic_elem=$("pic_element");
	flash_elem = $("flashcontent_box");
	if(pic_elem.src.indexOf("=") < 0){
		hasPic(false);	
	}
	setSuitableSize();
	var fileName = pic_elem.getAttribute("filename");
	// 看是否是flash
	if(isFlash(fileName)){
		var filePath = pic_elem.getAttribute("httpPath");
		showFlash(filePath);
	}else{
		// 设置合适的图片大小值
		refesh(pic_elem.src)
	}
	// 附件组件事件绑定
	var appendixs = com.trs.ui.XAppendixMgr.getAllComponents();
	for(var i=0;i<appendixs.length;i++){
		appendixs[i].addListener({
			'change' : function(){
				var sValue = this.getValue();
				//validdata
				if(!validData()){
					hasPic(false);
					return false;
				}
				// 上传操作
				this.upload(function(sValue){
					hasPic(sValue && sValue.length>0);
					//发送请求，获取flash文件的网络地址
					refesh(sValue,null,arguments[1]);
				},function(){
					alert(arguments[0].toString());
				});
			},
			'delete' : function(){ 
					hasPic(false);
			}
		});
	}
	$("LOGO_FILE-browser-btn").title = "允许上传jpg,gif,png,bmp,swf格式的文件!";
}
var picEditBtns=null;
function disabledEditButton(_TrueOrFalse){
	if(!picEditBtns)
		picEditBtns = document.getElementsByClassName("btn-pic-edit");
	for(var i=0;i<picEditBtns.length;i++){
		if(_TrueOrFalse){
			picEditBtns[i].disabled="disabled";
			Element.addClassName(picEditBtns[i],"disabled");;
		}else{
			picEditBtns[i].disabled="";
			Element.removeClassName(picEditBtns[i],"disabled");;
		}
	}
}

/*
*	初始化当前图片
*/
function initCurrImg(){
	var pic_size_el=$("pic_size");
	if(!pic_size_el|| !pic_elem){
		setTimeout(initCurrImg,10);
		return;
	}
	pic_size_el.innerHTML=",当前图片尺寸："+currImg.width+"×"+currImg.height+" px";
	setSuitSize();
}
var suit_width = 0;

// 设置合适的图片大小信息
function setSuitableSize(){
	try{
		dimension = top.PageController.getMainWin().PageController.execute('getCurrentWidgetDimension');
		$("suitabled_size").innerHTML=dimension.width+" px"
		suit_width = dimension.width;
	}catch(error){
	}

}
