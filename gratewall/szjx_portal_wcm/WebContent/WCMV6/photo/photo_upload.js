var m_hValidateFiles = {
	jpg:1,gif:2,bmp:3,jpeg:4,
	toStr : function(){
		var str = [];
		for(var p in this){
			if(p != "toStr"){
				str.push(p);
			}
		}
		return str.join(",");
	}
};

Object.extend(PageContext,{
	init : function(){
		this.mainKindId = getParameter("ChannelId")||0;
		this.LibId = getParameter("SiteId") || 0;
		BasicDataHelper.call("wcm6_photo","getSupportedFormat",null,false,function(_transport,_json){			
			var sValue = _transport.responseText;
			if(sValue.trim().length > 0){
				eval("var j = "+sValue);
				Object.extend(m_hValidateFiles,j);						
			}
		});		
	},
	loadDefaultBmpConverType : function(){
		BasicDataHelper.call("wcm6_photo","getDefaultBmpConverType",null,false,function(_transport,_json){			
			$("BmpConverTypeSelect").value = _transport.responseText;
			$("BmpConverType").value =  _transport.responseText;
		});
	},
	loadMainKind : function(_id){
		var id = _id || this.mainKindId;
		BasicDataHelper.call("wcm6_channel","findById",{ObjectId:id},false,function(_transport,_json){
			var sValue = TempEvaler.evaluateTemplater('template_mainkind', _json);			
			Element.update($("mainkind"),sValue);
			if($("_mainkind").innerText.trim().length == 0){
				Element.update($("_mainkind"),"<span style='color:red;font-size:16px;'>选择</span>");
			}
			PageContext.loadWatermarks(PageContext.LibId||$("_mainkind")._siteId);
			$("MainKindId").value = id;
			PageContext.mainKindId = id;
		});
	},
	loadOtherKinds : function(_ids){
		var ids = _ids || "";				
		BasicDataHelper.call("wcm6_channel","findByIds",{ObjectIds:ids},true,function(_transport,_json){
			var sValue = TempEvaler.evaluateTemplater('template_otherkinds', _json);			
			Element.update($("otherkinds_holder"),sValue);
			Event.observe($("otherkinds"),"keydown",PageContext.removeOtherKind);
			$("OtherKindIds").value = ids;
		});
	},
	removeOtherKind : function(_evt){
		var evt = window.event || _evt;
		if(evt.keyCode == 46){//删除
			var r = [];
			var ops = $("otherkinds").options;	
			var op = null;
			for(var i=ops.length-1;i>=0;i--){
				op = ops[i];
				if(op.selected){
					ops.remove(i);
				}else{
					r.push(op.value);
				}
			}
			$("OtherKindIds").value = r.join(",");
		}		
	},
	loadWatermarks : function(_libId){
		BasicDataHelper.call("wcm6_watermark","query",{LibId:_libId},false,function(_transport,_json){
			var sValue = TempEvaler.evaluateTemplater('template_watermarks', _json);			
			Element.update($("watermarks"),sValue);
			Event.observe($("selwatermark"),"change",function(){
				var el = $("selwatermark");
				var op = el.options[el.selectedIndex];
				if(op.value == -1){
					Element.hide($("watermarkpic"));
					Element.hide($("div_watermarkpos"));
					$("WatermarkFile").value = "";
				}else{
					var imgLoaded = new Image();
					imgLoaded.onload = function(){
						resizeIfNeed(imgLoaded.height,imgLoaded.width);
						imgLoaded.onload = null;
						$("watermarkpic").src = op._picsrc;
						Element.show($("watermarkpic"));
					}
					imgLoaded.src = op._picsrc + "?r="+Math.random();
										
					Element.show($("div_watermarkpos"));					
					$("WatermarkFile").value = op._picfile;
				}
			});
		});
	},
	save : function(){	
		ProcessBar.addState('正在保存图片.');
		ProcessBar.start();
		var wmpos = [];
		var wmposcheckbox = $("LT","CM","RB");
		for(var i=0;i<3;i++){
			if(wmposcheckbox[i].checked){
				wmpos.push(wmposcheckbox[i].value);
			}
		}
		$("WatermarkPos").value = wmpos.join(",");
		
		//过渡页显示,并把按钮置为不可用
		var waitingBar = $("process_wating");
		var pageSize = getPageSize();		
		waitingBar.style.width = pageSize[0];
		waitingBar.style.height = pageSize[1];
		waitingBar.style.display = "inline";
		FloatPanel.disableCommand("btnOnOK",true);
		FloatPanel.disableCloseCommand();
		BasicDataHelper.call("wcm6_photo","saveImageInfo","form_imageInfo",true,function(_transport,_json){			
			//var p = {DocIds:,ChannelId:PageContext.mainKindId};		
			var queryStr = "DocIds="+_transport.responseText + "&ChannelId="+PageContext.mainKindId;
			
			FloatPanel.open("./photo/photoprops_edit.html?"+queryStr,"标注图片属性",680,350);
			ProcessBar.exit();
		},function(_transport,_json){
			//if fail,reset the panel.
			window.DefaultAjax500CallBack(_transport,_json,this);			
			waitingBar.style.display = "none";
			FloatPanel.disableCommand("btnOnOK",false);
			FloatPanel.enableCloseCommand();
		});
	}
});

PageContext.init();

function getPageSize(){
	var de = document.documentElement;
	var w = window.innerWidth || self.innerWidth || (de&&de.clientWidth) || document.body.clientWidth;
	var h = window.innerHeight || self.innerHeight || (de&&de.clientHeight) || document.body.clientHeight;
	arrayPageSize = [w,h];
	return arrayPageSize;
}

function onSelectedFile(_el){
	var fn = _el.value;
	if(fn.trim().length == 0){
		return false;
	}
	var fext = fn.substr(fn.lastIndexOf(".")+1).toLowerCase();	
	if($("batchupload").checked){
		if("zip" != fext){
			$alert("您所选的不是一个zip格式的文件!");
			return false;
		}

		return true;
	}else{
		return validatePicFile(fext);
	}
}

function validatePicFile(_fext){
	if(m_hValidateFiles[_fext] == void(0)){
		$alert("只支持["+m_hValidateFiles.toStr()+"]格式的图片！");
		return false;
	}

	return true;
}

function switchUploadMode(){
	var checkboxEl = $("batchupload");
	var picnum = $("picnum");
	var picnumbtn = $("picnumbtn");
	if(checkboxEl.checked){
		picnum.disabled = true;
		picnumbtn.disabled = true;
		picnum.value = 1;
		changeUploadBlocks();
		$("BatchMode").value = "1";
	}else{
		picnum.disabled = false;
		picnumbtn.disabled = false;
		$("BatchMode").value = "0";
	}
	
	checkboxEl = null;
	picnum = null;
	picnumbtn = null;
}

var m_nTotalPicNum = 1;
function changeUploadBlocks(){
	var num = $F("picnum");
	if(num>10 || num<1 || isNaN(parseInt(num)) || num.length != 1){
		alert("请输入[1-9]的数字！");
		return;
	}
	var diff = num - m_nTotalPicNum;	
	var picForm = $("form_pic");
	var formContainer = picForm.parentNode;
	var tempChild = null;
	if(diff > 0){
		for(var i=0;i<diff;i++){
			tempChild = picForm.cloneNode(true);
			tempChild.id = "form_pic" + m_nTotalPicNum;
			formContainer.appendChild(tempChild);
			m_nTotalPicNum++;
		}
	}else if(diff < 0){
		diff = -diff;
		var elid = null;			
		for(var i=0;i<diff;i++){
			elid = "form_pic" + (--m_nTotalPicNum);	
			tempChild = $(elid);
			formContainer.removeChild(tempChild);				
		}
	}		
		
	picForm = null;
	formContainer = null;
}

var CurrType = 1;
var UploadFileName = null;
var sDefaultNoneImg = '';
var isSSL = location.href.indexOf("https://")!=-1;
function uploadFile(_formId){
	var formId = _formId || "form_pic";		
	var fn = $(formId).PicUpload.value;	
	
	ProcessBar.init('执行进度，请稍候...');
	ProcessBar.addState('正在上传文件:' + fn);
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
				}, '上传文件失败，与服务器交互时出错啦！');
				ProcessBar.close();
			}
			else{						
				saveImageInfo(fn,sResponseText);
				ProcessBar.next();
				setTimeout(ProcessBar.next.bind(ProcessBar),10);
			}
		}
	}
	try{
		YUIConnect.setForm(formId,true,isSSL);
		YUIConnect.asyncRequest('POST','../system/file_upload_dowith.jsp?FileParamName=PicUpload',callBack1);
	}catch(err){
		ProcessBar.exit();
		$alert(err.message);
	}
}

var m_aUploadedFiles = [];
var m_aSrcFiles = [];
var m_nUploaded = 0;
function saveImageInfo(_fn,_uploaded){
	ProcessBar.exit();
	if($("batchupload").checked){		
		$("UploadedFiles").value = _uploaded;		
		PageContext.save();	
		return false;
	}	
	
	m_aUploadedFiles.push(_uploaded);
	m_aSrcFiles.push(_fn);
	m_nUploaded++;

	if(m_nUploaded < m_nTotalPicNum){
		uploadFile("form_pic"+m_nUploaded);
	}else{
		$("SourceFiles").value = m_aSrcFiles.join(",");
		$("UploadedFiles").value = m_aUploadedFiles.join(",");		
		PageContext.save();	
	}
}

function Ok(){
	//validate first.

	//主分类
	if($F("MainKindId") == 0){
		alert("请选择一个主分类！");
		return false;
	}

	//文件格式
	if($F("BatchMode") == 0){
		var uploadPics = document.getElementsByClassName("input_file");
		var uploadPic = null;	
		var fn = null;
		var fext = null;		
		var zNotValid = false;
		for(var i=0,len=uploadPics.length;i<len;i++){			
			uploadPic = uploadPics[i];
			fn = uploadPic.value;
			
			if(fn){
				fext = fn.substr(fn.lastIndexOf(".")+1);
			}else{
				fext = "";				
			}

			if(m_hValidateFiles[fext.toLowerCase()] == void(0)){				
				uploadPic.className = "input_file invalid_file";				
				if(fn){				
					alert("["+fn+"]不是支持的图片格式!\n仅支持["+m_hValidateFiles.toStr()+"]类型");
				}else{				
					alert("没有选择图片!");
				}				
				zNotValid = zNotValid || true;
			}			
		}
		if(zNotValid){			
			return false;
		}
	}else{
		var fzip = document.getElementById("PicUpload").value;		
		if(!fzip || "zip" != fzip.substr(fzip.lastIndexOf(".")+1)){
			alert("请选择一个有效的zip文件!");
			return false;
		}
	}	
	
	uploadFile();

	//FloatPanel.open("./photo/photoprops_edit.html?DocIds=796,795,794&ChannelId=53","标注图片属性",680,350);for test.
	return false;
}

function convertBmp(_select){
	$("BmpConverType").value = _select.value;	
}

//全选或反全选水印位置
function selectAllPos(){
	var poses = $("LT","CM","RB");
	var unchecked = false;
	for(var i=0;i<poses.length;i++){
		if(!poses[i].checked){
			unchecked = true;
			poses[i].checked = true;
		}
	}

	if(!unchecked){
		for(var i=0;i<poses.length;i++){				
			poses[i].checked = false;				
		}
	}
	
	poses = null;
}

var DIALOG_IMAGEKIND_SELECTOR = "Dialog_ImageKind_Selector";
ChannelSelector = Class.create("ChannelSelector");

ChannelSelector.prototype = {
	//m_eSelector : null,	
	initialize : function(){
		var mytop = top || top.actualTop;
		mytop.m_eSelector = TRSDialogContainer.register(DIALOG_IMAGEKIND_SELECTOR, '选择图片分类', './photo/channel_select.html', '250px', '300px', false);		
		mytop.m_eSelector.onFinished = function(_args){				
			if(!_args.ids || _args.ids.length == 0){
				alert("没有选择分类");
				return;
			}
			if(_args.mode == "radio"){
				PageContext.loadMainKind(_args.ids);
			}else{				
				PageContext.loadOtherKinds(_args.ids);
			}
		}
		
		TRSCrashBoard.setMaskable(true);
	},
	selectMainKind : function(){
		var mainkind = $("_mainkind");		
		var pContext = {mode:"radio",SelectedIds:mainkind._mainkindId,CurrId:mainkind._mainkindId,SiteId:mainkind._siteId};
		var dialog = TRSDialogContainer.DialogsMap[DIALOG_IMAGEKIND_SELECTOR];
		dialog.refreshTitle("选择主分类");
		TRSDialogContainer.display(DIALOG_IMAGEKIND_SELECTOR, pContext);		
	},
	selectOtherKinds : function(){
		var dialog = TRSDialogContainer.DialogsMap[DIALOG_IMAGEKIND_SELECTOR];		
		dialog.refreshTitle("选择其它分类");
		TRSDialogContainer.display(DIALOG_IMAGEKIND_SELECTOR, {mode:"multi",SelectedIds:$F("OtherKindIds"),CurrId:$F("MainKindId")});
	}	
};

function resizeIfNeed(height,width){
		var h = height,w = width;
		if(height > 124 || width > 97){	
			if(height > width){				
				h = 124;	
				w = 124 * width/height;
			}else{				
				w = 97;
				h = 97 * height/width;
			}			
		}

		$("watermarkpic").width = w;
		$("watermarkpic").height = h;			
}
function truncateIfNeed(_srcstr){
	if(_srcstr.length > 12){
		_srcstr = _srcstr.substr(0,12) + "...";
	}

	return _srcstr;
}
Event.observe(window,'load',function(){
	PageContext.loadMainKind();
	PageContext.loadDefaultBmpConverType();
	channelSelector = new ChannelSelector();
	Event.observe($("batchupload"),"click",switchUploadMode);
	Event.observe($("picnumbtn"),"click",changeUploadBlocks);
	Event.observe($("mainkind"),"click",channelSelector.selectMainKind);
	Event.observe($("selOtherKinds"),"click",channelSelector.selectOtherKinds);	
});

