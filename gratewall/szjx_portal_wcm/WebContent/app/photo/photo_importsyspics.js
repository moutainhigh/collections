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
var PageContext ={};
Object.extend(PageContext,{
	mianKindId : 0,
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
		/**
		BasicDataHelper.call("wcm6_photo","getDefaultBmpConverType",null,false,function(_transport,_json){			
			$("BmpConverTypeSelect").value = _transport.responseText;
			$("BmpConverType").value =  _transport.responseText;
		});*/	
	},
	loadDefaultConverType : function(){
		BasicDataHelper.call("wcm6_photo","getDefaultBmpConverType",null,false,function(_transport,_json){			
			$("BmpConverTypeSelect").value = _transport.responseText;
			$("BmpConverType").value =  _transport.responseText;
		});	
	},
	loadMainKind : function(_id){
		BasicDataHelper.call("wcm6_channel","findById",{ObjectId:parseInt(_id)},false,function(_transport,_json){
			$("mainkind").innerHTML = _json.CHANNEL.CHNLDESC.NODEVALUE;
		});
		$("MainKindId").value = _id;
		PageContext.mainKindId = _id;
		channelId = _id;
	},
	firstLoadMainKind: function(_id){
		var id = _id || this.mainKindId;
		$("MainKindId").value = id;
		PageContext.mainKindId = id;
	},
	loadOtherKinds : function(_ids){
		var ids = _ids || "";	
		if(ids == ""){
			$("otherkinds").innerHTML = "";
			$("OtherKindIds").value = ids;
			return ;
		}
		BasicDataHelper.call("wcm6_channel","findByIds",{ObjectIds:ids},true,function(_transport,_json){
			var num = _json.CHANNELS.NUM;
			if(num>10 || num<1 || isNaN(parseInt(num)) || num.length != 1){
				alert(wcm.LANG.PHOTO_CONFIRM_44 || "请输入[1-9]的数字！");
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
				picForm.innerHTML = _json.CHANNELS.CHANNEL[0].CHNLDESC.NODEVALUE;
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
			Event.observe($("otherkinds"),"keydown",PageContext.removeOtherKind);
			$("OtherKindIds").value = ids;
		});
	},
	removeOtherKind : function(_evt){
		var evt = (_evt) ? _evt : ((window.event) ? window.event : "")
		var keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which :evt.charCode);
		if(keyCode == 46){//删除
			var r = [];
			var ops = $("otherkinds");	
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
	getRadioValue : function(_sName){
		var elms = document.getElementsByName(_sName);
		if(elms == null || elms.length <= 0) {
			return null;
		}
		//else
		for (var i = 0; i < elms.length; i++){
			var elm = elms[i];
			if(elm.checked) {
				return elm.value;
			}
		}

		return null;
	},
	__bindRadionEvent : function(_sElmClsName){
		var rds = document.getElementsByName(_sElmClsName + 'Interval');
		for (var i = 0; i < rds.length; i++){
			var rd = rds[i];
			Event.observe(rd, 'click', function(){
				if(this.value == -1) {
					enableTimeSelect(_sElmClsName, true);
				}else{
					enableTimeSelect(_sElmClsName, false);
				}
			}.bind(rd), false);//*/

			delete rd;
		}		
	}	
});

PageContext.init();

Event.observe(window,"load",function(){
	PageContext.firstLoadMainKind();
	PageContext.loadDefaultConverType();
});

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
function convertBmp(_select){
	$("BmpConverType").value = _select.value;	
}

var mySrc = '';
//全选或反全选水印位置
function selectAllPos(){
	var poses =[$("LT"),$("CM"),$("RB")];
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

function display (_title,_param){
	var mytop = top || top.actualTop;
	mytop.m_eSelector = wcm.CrashBoarder.get(DIALOG_IMAGEKIND_SELECTOR).show({
		title : _title,
		src : './photo/channel_select.html',
		width: '250px',
		height: '300px',
		top : '220px',
		left : '490px',
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
function showController(_triggle){
	$("NavTree").style.display = "none";
	$("Classic").style.display = "none";
	switch(_triggle){
		case 1:
			$("NavTree").style.display = "";
			break;
		case 2:
			$("Classic").style.display = "";
			break;
		default :
			alert(wcm.LANG.PHOTO_CONFIRM_93 || "非法输入!");
	}
};

var DocSourceHelper = {
	NotifyInput : function(){
		$('txtDocSource').value = $('selDocSource').value;
	},
	NotifySelect : function(){
		$('selDocSource').value = $('txtDocSource').value;
	}
};

var DIALOG_IMAGEKIND_SELECTOR = "Dialog_ImageKind_Selector";
ChannelSelector = Class.create("ChannelSelector");
var channelId = getParameter("channelid") || 0;
var siteid = getParameter("siteid") || 0;
var sType = channelId != 0? "channel":"site";
var nCurrId = channelId != 0? channelId:siteid;
ChannelSelector.prototype = {
	initialize : function(){
	},
	selectMainKind : function(){		
		var pContext = {mode:"radio",SelectedIds:$F("MainKindId") == 0 ? "" : $F("MainKindId") +"",CurrId:nCurrId,Type:sType};
		display(wcm.LANG.PHOTO_CONFIRM_52 || "选择主分类",pContext);	
		//var positions = Position.getAbsolutePositionInTop(Event.element(Event.findEvent()));	
		//TRSDialogContainer.display(DIALOG_IMAGEKIND_SELECTOR, pContext,positions[0]+50,positions[1]-150);		
	},
	selectOtherKinds : function(){
		sType = channelId != 0? "channel":"site";
		var pContext = {mode:"multi",SelectedIds:$F("OtherKindIds"),CurrId:$F("MainKindId") +"",Type:sType};
		display(wcm.LANG.PHOTO_CONFIRM_53 || "选择其它分类",pContext);
		//var positions = Position.getAbsolutePositionInTop(Event.element(Event.findEvent()));	
		//TRSDialogContainer.display(DIALOG_IMAGEKIND_SELECTOR, {mode:"multi",SelectedIds:$F("OtherKindIds"),CurrId:$F("MainKindId")},positions[0]+50,positions[1]-200);
	}	
};

function cancelImport(){
	if(window.opener && window.opener.cancelImport){
		window.opener.cancelImport();
	}else{
		window.close();
	}
}
function truncateStrIfNeed(_srcstr){
	if(_srcstr.length > 10){
		_srcstr = _srcstr.substr(0,10)+"...";
	}

	return _srcstr;
}
function Tobeimport(){
	var object = $("field");
	var frame =$("photo_list");	
	var Currwindow = $("photo_list").contentWindow;
	if(object.style.display == "none"){
		if(!window.m_bIsSmallScreen) {
			Currwindow.$("grid_navigator").style.top = "411px";
			Currwindow.$("selDiv").style.top = "420px";
		}
		Element.show(object);
	}else{
		if(!window.m_bIsSmallScreen) {
			Currwindow.$("grid_navigator").style.top = "531px";
			Currwindow.$("selDiv").style.top = "510px";
		}
		Element.hide(object);
	}
	return false;
}

Event.observe(window,'load',function(){	
	channelSelector = new ChannelSelector();	
	Event.observe($("mainkind"),"click",channelSelector.selectMainKind);
	Event.observe($("selOtherKinds"),"click",channelSelector.selectOtherKinds);	
	PageContext.__bindRadionEvent('CrTime');
	PageContext.__bindRadionEvent('PubTime');
});
//保存导入图片
var SelectedPhotoIds = [];
var PhotoSrcMap = {
};
function SetPhotoSelected(_nPhotoId,sPhotoSrcs,_bChecked){
	var eDiv = $('myphoto_'+_nPhotoId);
	if(!_bChecked && eDiv){
		SelectedPhotoIds = SelectedPhotoIds.remove(_nPhotoId);
		delete PhotoSrcMap[_nPhotoId];
		$('selected_photos').removeChild(eDiv);
	}
	else if(_bChecked){
		SelectedPhotoIds.push(_nPhotoId);
		PhotoSrcMap[_nPhotoId] = sPhotoSrcs;
		createSpan(_nPhotoId,sPhotoSrcs);
	}
}

function createSpan(_nPhotoId,sPhotoSrcs){
	var eDiv = $('myphoto_'+_nPhotoId);
	if(eDiv==null){
		eDiv = document.createElement('SPAN');
		eDiv.id = 'myphoto_'+_nPhotoId;
		eDiv.className = 'myphoto';
		eDiv.setAttribute('PHOTOID',_nPhotoId);
		eDiv.src = getWebPicPath(sPhotoSrcs.split(',')[0]);
		eDiv.innerHTML = '<img width="100px" height="92px" style="overflow:hidden;" src="'+ eDiv.src +'"/><img id="opl_delete_'+
			_nPhotoId+'" class="opl_delete" PHOTOID="'+_nPhotoId+'" style="display:none" src="../images/photo/cancel.png"/><img id="opl_moveright_'+
			_nPhotoId+'" class="opl_moveright" PHOTOID="'+_nPhotoId+'" style="display:none" src="../images/photo/right.png"/><img id="opl_moveleft_'+
			_nPhotoId+'" class="opl_moveleft" PHOTOID="'+_nPhotoId+'" style="display:none" src="../images/photo/left.png"/><span style="width:3px;">&nbsp;</span>';

		Event.observe(eDiv,'mouseover',function(event){
			showPic(['opl_delete_','opl_moveleft_','opl_moveright_'],_nPhotoId);
		});
		Event.observe(eDiv,'mouseout',function(event){
			hidePic(['opl_delete_','opl_moveleft_','opl_moveright_'],_nPhotoId);
		});
		$('selected_photos').appendChild(eDiv);
		//删除绑定
		var eDelete = $('opl_delete_'+_nPhotoId)
		Event.observe(eDelete,'click',function(event){
			eDelete.parentNode.parentNode.removeChild(eDelete.parentNode);
			RemovePhoto(_nPhotoId);
			return false;
		});
		//左右移绑定
		var eDelete = $('opl_moveleft_'+_nPhotoId);
		Event.observe(eDelete,'click',function(event){
			var nparentNode = eDelete.parentNode.parentNode;
			var dom = eDelete.parentNode;
			if(Element.previous(dom)){
				nparentNode.insertBefore(dom,Element.previous(dom));
				hidePic(['opl_delete_','opl_moveleft_','opl_moveright_'],eDelete.getAttribute("PHOTOID"));
				var oScope = $('photo_list').contentWindow;
				oScope.RefreshSelected(SelectedPhotoIds);
			}
			return false;
		});
		var eDelete = $('opl_moveright_'+_nPhotoId);
		Event.observe(eDelete,'click',function(event){
			var nparentNode = eDelete.parentNode.parentNode;
			var dom = eDelete.parentNode;
			if(Element.next(dom)){
				nparentNode.insertBefore(Element.next(dom),dom);
				hidePic(['opl_delete_','opl_moveleft_','opl_moveright_'],eDelete.getAttribute("PHOTOID"));
				var oScope = $('photo_list').contentWindow;
				oScope.RefreshSelected(SelectedPhotoIds);
			}
			return false;
		});
	}
	return eDiv;
}
function showPic(group,_nPhotoId){
	for(var i=0 ; i < group.length; i++){
		var eDelete = $(group[i] + _nPhotoId);
		var dom = eDelete.parentNode;
		if(eDelete){
			if(!Element.previous(dom) && group[i] == "opl_moveleft_") continue;
			if(!Element.next(dom) && group[i] == "opl_moveright_") continue;
			if(!Element.next(dom) && group[i] == "opl_moveleft_"){
				eDelete.style.right = "26px";
			}else if(group[i] == "opl_moveleft_"){
				eDelete.style.right = "46px";
			}	
			Element.show(eDelete);
		}
	}
}
function hidePic(group,_nPhotoId){
	for(var i=0 ; i < group.length; i++){
		var eDelete = $(group[i] + _nPhotoId);
		if(eDelete){
				Element.hide(eDelete);
			}
	}
}
function RemovePhoto(_nPhotoId){
	SelectedPhotoIds = SelectedPhotoIds.remove(_nPhotoId);
	delete PhotoSrcMap[_nPhotoId];
	var oScope = $('photo_list').contentWindow;
	oScope.RefreshSelected(SelectedPhotoIds);
}
function getWebPicPath(r){
	return "/webpic/" + r.substr(0,8) + "/" + r.substr(0,10) + "/" + r;
}
function Ok(){
	if(SelectedPhotoIds.length == 0){
		Ext.Msg.alert(wcm.LANG.PHOTO_CONFIRM_50 || "没有选择图片!");
		return false;
	}
	var nMainKindId = $F("MainKindId");
	if(nMainKindId == 0){
		Ext.Msg.alert(wcm.LANG.PHOTO_CONFIRM_52 || "选择主分类!");
		return false;
	}
	$("PicIds").value = SelectedPhotoIds.join(",");
	
	ProcessBar.start(wcm.LANG.PHOTO_CONFIRM_30 || '上传图片');
	var wmpos = [];
	var wmposcheckbox = [$("LT"),$("CM"),$("RB")];
	for(var i=0;i<3;i++){
		if(wmposcheckbox[i].checked){
			wmpos.push(wmposcheckbox[i].value);
		}
	}

	$("WatermarkPos").value = wmpos.join(",");
	BasicDataHelper.call("wcm6_photo","importSysPics","form_imageInfo",true,function(_transport,_json){	
	var queryStr = "DocIds="+_transport.responseText + "&ChannelId="+$F("MainKindId") +"&SiteId4KeyWord=" + nSiteId;
	ProcessBar.close();
	notifyFPCallback({
		src : WCMConstants.WCM6_PATH +
			'photo/photoprops_edit.jsp?'+queryStr+"&WaterMarkFile="+$("WatermarkFile").value+"&WatermarkPos="+$("WatermarkPos").value,
		title : wcm.LANG.PHOTO_CONFIRM_45 || "标注图片属性"
	});},function(_transport,_json){
		//if fail,reset the panel.
		window.DefaultAjax500CallBack(_transport,_json,this);			
		FloatPanel.disableCommand("Ok",false);
	});
	return false;
}
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_TREENODE,
	afterclick : function(event){
		//负责导航树对应的页面切换
		var context = event.getContext();
		var objId = context.objId;
		var objType = context.objType;
		mySrc = 'photo_syspics_list.html?';
		var sParams = '';
		switch(objType){
			case WCMConstants.OBJ_TYPE_WEBSITEROOT:
				break;
			case WCMConstants.OBJ_TYPE_WEBSITE:
				mySrc += "siteid=" + objId;
				break;
			case WCMConstants.OBJ_TYPE_CHANNEL:
				mySrc += "channelid=" + objId ;
				break;
		}
		$('photo_list').src = mySrc;
		return false;
	}
});