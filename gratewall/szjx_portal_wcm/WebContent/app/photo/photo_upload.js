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

var PageContext = {};
Object.extend(PageContext,{
	mainKindId : 0,
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
		BasicDataHelper.call("wcm6_channel","findById",{ObjectId:parseInt(_id)},false,function(_transport,_json){
			$("mainkind").innerHTML = _json.CHANNEL.CHNLDESC.NODEVALUE;
			$("mainkind").className = "mainkindfound";
		});
		$("MainKindId").value = _id;
		PageContext.mainKindId = _id;
		channelId = _id;
	},
	loadOtherKinds : function(_ids,_brefresh){
		var brefresh = _brefresh || false;
		var ids = _ids || "";	
		if(ids == ""){
			$("otherkinds").innerHTML = "";
			$("OtherKindIds").value = ids;
			return ;
		}
		BasicDataHelper.call("wcm6_channel","findByIds",{ObjectIds:ids},true,function(_transport,_json){
			var num = _json.CHANNELS.NUM;
			if(num>10 || num<1 || isNaN(parseInt(num)) || num.length != 1){
				alert( wcm.LANG.PHOTO_CONFIRM_126 || "请输入[1-9]个分类!");
				return;
			}	
			var formContainer = $("otherkinds");
			var length = formContainer.childNodes.length;
			if(length > 0){
				for(var i=0;i< length ;i++){
					formContainer.removeChild(formContainer.childNodes[0]);
				}
			}
			var picForm = document.createElement("OPTION");
			picForm.id = "othersId";
			formContainer.appendChild(picForm);
			var tempChild = null;
			if(num>1){
				picForm.innerHTML =  _json.CHANNELS.CHANNEL[0].CHNLDESC.NODEVALUE;
				picForm.value = _json.CHANNELS.CHANNEL[0].CHANNELID.NODEVALUE;
				for(var i=1;i<num;i++){
					tempChild = picForm.cloneNode(true);
					tempChild.id = "othersId" + i;
					tempChild.innerHTML = _json.CHANNELS.CHANNEL[i].CHNLDESC.NODEVALUE;
					tempChild.value = _json.CHANNELS.CHANNEL[i].CHANNELID.NODEVALUE;
					formContainer.appendChild(tempChild);
				}
			}else{
				picForm.innerHTML = $v(_json,"CHANNELS.CHANNEL.CHNLDESC");
				picForm.value = $v(_json,"CHANNELS.CHANNEL.CHANNELID");
			}
			$("OtherKindIds").value = ids;
			Event.observe($("otherkinds"),"keydown",PageContext.removeOtherKind);
		});
	},
	removeOtherKind : function(_evt){
		var evt = (_evt) ? _evt : ((window.event) ? window.event : "")
		var keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which :evt.charCode);

		if(keyCode == 46){//删除
			var r = [];
			var ops = $("otherkinds");	//fix FF
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
	save : function(){	
		var wmpos = [];
		var wmposcheckbox = [$("LT"),$("CM"),$("RB")];
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
		FloatPanel.disableCommand("Ok",true);
		//FloatPanel.disableCommand("cancel",true);
		BasicDataHelper.call("wcm6_photo","saveImageInfo","form_imageInfo",true,function(_transport,_json){			
			//var p = {DocIds:,ChannelId:PageContext.mainKindId};
			if(!$("batchupload").checked){
				var params = {
					ObjectIds:_transport.responseText,
					SrcFiles:m_aUploadedFiles.reverse().join(",")
				}
				BasicDataHelper.call("wcm61_photo","saveExtif",params,true,function(_transport,_json){
				});
			}
			var queryStr = "DocIds="+_transport.responseText + "&ChannelId="+ $F("MainKindId") +"&SiteId4KeyWord=" + nSiteId;
			notifyFPCallback({
				src : WCMConstants.WCM6_PATH +
					'photo/photoprops_edit.jsp?'+queryStr+"&WaterMarkFile="+$("WatermarkFile").value+"&WatermarkPos="+$("WatermarkPos").value,
				title : wcm.LANG.PHOTO_CONFIRM_45 || "标注图片属性"
			});
		},function(_transport,_json){
			//if fail,reset the panel.
			window.DefaultAjax500CallBack(_transport,_json,this);			
			waitingBar.style.display = "none";
			FloatPanel.disableCommand("Ok",false);
			//FloatPanel.disableCommand("cancel",false);
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
		Ext.Msg.alert(wcm.LANG.PHOTO_CONFIRM_44 || "请输入[1-9]的数字！");
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

	var callBack1 = {
		"upload":function(_transport){
			var sResponseText = _transport.responseText;			
			if(sResponseText.match(/<!--ERROR-->/img)){
				var texts = sResponseText.split('<!--##########-->');
				Ext.Msg.fault({
					message : texts[1],
					detail : texts[2]
				},wcm.LANG.PHOTO_CONFIRM_34 || '上传文件失败，与服务器交互时出错啦！');
				ProcessBar.close();
			}
			else{						
				saveImageInfo(fn,sResponseText);
			}
		}
	}
	try{
		YUIConnect.setForm(formId,true,isSSL);
		YUIConnect.asyncRequest('POST','../system/file_upload_dowith.jsp?FileParamName=PicUpload',callBack1);
	}catch(err){
		ProcessBar.close();
		Ext.Msg.alert(err.message);
	}
}

var m_aUploadedFiles = [];
var m_aSrcFiles = [];
var m_nUploaded = 0;
function saveImageInfo(_fn,_uploaded){
	if($("batchupload").checked){		
		$("UploadedFiles").value = _uploaded;
		ProcessBar.close();
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
		ProcessBar.close();
		PageContext.save();	
	}
}

function cancel(){
	return true;
}
function Ok(){
	//validate first.

	//主分类
	if($F("MainKindId") == 0){
		Ext.Msg.alert(wcm.LANG.PHOTO_CONFIRM_48 || "请选择一个主分类！");
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
					Ext.Msg.alert(String.format(wcm.LANG.PHOTO_CONFIRM_49 || ("[{0}]不是支持的图片格式!\n仅支持[{1}]类型"),fn,m_hValidateFiles.toStr()));
				}else{				
					Ext.Msg.alert(wcm.LANG.PHOTO_CONFIRM_50 || "没有选择图片!");
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
			Ext.Msg.alert(wcm.LANG.PHOTO_CONFIRM_51 || "请选择一个有效的zip文件!");
			return false;
		}
	}	
	ProcessBar.start(wcm.LANG.PHOTO_CONFIRM_61 || '上传图片');
	uploadFile();

	//FloatPanel.open("./photo/photoprops_edit.html?DocIds=796,795,794&ChannelId=53","标注图片属性",680,350);for test.
	return false;
}

function convertBmp(_select){
	$("BmpConverType").value = _select.value;	
}

//全选或反全选水印位置
function selectAllPos(){
	var poses = [$("LT"),$("CM"),$("RB")];
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
var channelId = getParameter("channelid") || 0;
var siteid = getParameter("siteid") || 0;
var sType = channelId != 0? "channel":"site";
var nCurrId = channelId != 0? channelId:siteid;
ChannelSelector.prototype = {
	//m_eSelector : null,	
	initialize :function(){
	},
	selectMainKind : function(){
		var pContext = {mode:"radio",SelectedIds:$F("MainKindId") == 0 ? "" : $F("MainKindId") +"",CurrId:nCurrId,Type:sType};
		display(wcm.LANG.PHOTO_CONFIRM_52 || "选择主分类",pContext);		
	},
	selectOtherKinds : function(){
		sType = channelId != 0? "channel":"site";
		var pContext = {mode:"multi",SelectedIds:$F("OtherKindIds"),CurrId:$F("MainKindId") +"",Type:sType};
		display(wcm.LANG.PHOTO_CONFIRM_53 || "选择其它分类",pContext);
	}	
};
function display (_title,_param){
	wcm.CrashBoarder.get(DIALOG_IMAGEKIND_SELECTOR).show({
		title : _title,
		src :  WCMConstants.WCM6_PATH + 'photo/channel_select.html',
		width: '250px',
		height: '300px',
		reloadable : false,
		params : _param,
		maskable : true,
		callback : function(_args){
			if(_args.mode == "radio"){
				PageContext.loadMainKind(_args.ids.join(","));
			}else{				
				PageContext.loadOtherKinds(_args.ids.join(","));
			}
		}
	});
}
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
function addWaterMark(_select){
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
			$("watermarkpic").src = op.getAttribute("_picsrc");
			Element.show($("watermarkpic"));
		}
		imgLoaded.src = op.getAttribute("_picsrc") + "?r="+Math.random();
							
		Element.show($("div_watermarkpos"));					
		$("WatermarkFile").value = op.getAttribute("_picfile");
	}
}
Event.observe(window,'load',function(){
	PageContext.loadDefaultBmpConverType();
	channelSelector = new ChannelSelector();
	Event.observe($("batchupload"),"click",switchUploadMode);
	Event.observe($("picnumbtn"),"click",changeUploadBlocks);
	Event.observe($("mainkind"),"click",channelSelector.selectMainKind);
	Event.observe($("selOtherKinds"),"click",channelSelector.selectOtherKinds);	
});

