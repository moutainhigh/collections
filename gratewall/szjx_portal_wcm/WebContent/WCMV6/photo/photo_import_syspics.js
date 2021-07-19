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
		var id = _id || this.mainKindId;
		BasicDataHelper.call("wcm6_channel","findById",{ObjectId:id},false,function(_transport,_json){
			var sValue = TempEvaler.evaluateTemplater('template_mainkind', _json);			
			Element.update($("mainkind"),sValue);
			if($("_mainkind").innerText.trim().length == 0){
				Element.update($("_mainkind"),"<span style='color:red;font-size:16px;'>选择</span>");
			}
			if(PageContext.LibId <= 0){
				PageContext.LibId = $("_mainkind")._siteId;
			}
			PageContext.loadWatermarks(PageContext.LibId);
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
		BasicDataHelper.call("wcm6_photo","saveImageInfo","form_imageInfo",true,function(_transport,_json){			
			var params = {DocIds:_transport.responseText,ChannelId:PageContext.mainKindId}
			ProcessBar.exit();
			FloatPanel.open("./photo/photoprops_edit.html?"+$toQueryStr(params),"标注图片属性",680,350);
			
		});
	}	
});

PageContext.init();

Event.observe(window,"load",function(){
	PageContext.loadMainKind();
	PageContext.loadDefaultConverType();
});

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


var DIALOG_IMAGEKIND_SELECTOR = "Dialog_ImageKind_Selector";
ChannelSelector = Class.create("ChannelSelector");

ChannelSelector.prototype = {
	//m_eSelector : null,	
	initialize : function(){
		var mytop = top || top.actualTop;
		mytop.m_eSelector = TRSDialogContainer.register(DIALOG_IMAGEKIND_SELECTOR, '选择图片分类', './channel_select.html', '250px', '300px', false);		
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
		var positions = Position.getAbsolutePositionInTop(Event.element(Event.findEvent()));	
		TRSDialogContainer.display(DIALOG_IMAGEKIND_SELECTOR, pContext,positions[0]+50,positions[1]-150);		
	},
	selectOtherKinds : function(){
		var dialog = TRSDialogContainer.DialogsMap[DIALOG_IMAGEKIND_SELECTOR];		
		dialog.refreshTitle("选择其它分类");
		var positions = Position.getAbsolutePositionInTop(Event.element(Event.findEvent()));	
		TRSDialogContainer.display(DIALOG_IMAGEKIND_SELECTOR, {mode:"multi",SelectedIds:$F("OtherKindIds"),CurrId:$F("MainKindId")},positions[0]+50,positions[1]-200);
	}	
};

var SelectedPhotoIds = [];
var PhotoSrcMap = {
};
var PhotoDragger = Class.create("PhotoDragger");
Object.extend(PhotoDragger.prototype,com.trs.wcm.SimpleDragger.prototype);
Object.extend(PhotoDragger.prototype,{
	delegate : function(_eRoot){
		this.shadow = _eRoot;
		this.thumbs = $('selected_photos').childNodes;
		var eHandler = this._getHint(_eRoot);
		this._handler = eHandler;
//		this.shadow.style.display = 'none';
//		this.shadow.style.height = '5px';
		this.oldBorder = this.shadow.style.border;
		this.lastNextSibling = _eRoot.nextSibling;
		this.shadow.style.border = '1px solid red';
		return eHandler;
	},
	_getHint : function(_eRoot){
		var eDiv = document.createElement('IMG');
		eDiv.className = 'myphoto';
		eDiv.src = _eRoot.src;
		eDiv.style.zIndex = 999;
//				eDiv.style.backgroundImage = _eRoot.style.backgroundImage;
		$('selected_photos').appendChild(eDiv);
		return eDiv;
	},
	_moveAfter : function(_eCurr,_ePrev){
		if(_ePrev!=null){
		}
		else{
		}
		return true;
	},
	onDragStart : function(nx,ny,_pXY,_event){
		Position.clone(this.root,this._handler);
	},
	onDrag : function(nx,ny,_pXY,_event){
		for(var i=0;i<this.thumbs.length;i++){
			var eThumb = this.thumbs[i];
			var offset = Position.cumulativeOffset(eThumb);
			var rOffset = Position.realOffset(eThumb);
			var iTop = offset[1]-rOffset[1]+Position.deltaY;
			var iBottom = iTop+eThumb.offsetHeight;
			var iCenter = (iBottom+iTop)/2;
			if(eThumb!=this.root&&eThumb!=this._handler){
				if(_pXY[1]>=iTop&&_pXY[1]<=iCenter){
					eThumb.parentNode.insertBefore(this.shadow,eThumb);
					this.moved = true;
					break;
				}
				else if(_pXY[1]>iCenter&&_pXY[1]<=iBottom){
					eThumb.parentNode.insertBefore(this.shadow,eThumb.nextSibling);
					this.moved = true;
					break;
				}
			}
		}
	},
	onDragEnd : function(nx,ny,_pXY,_event){
		var bNeedMove = false;
		if(this.moved){
			this.moved = false;
			if(this.shadow.nextSibling!=this.lastNextSibling&&
				this._moveAfter(this.shadow,this.shadow.previousSibling)){
				bNeedMove = true;
			}
		}
		this.shadow.style.border = this.oldBorder;
		if(!bNeedMove){
			this.root.parentNode.insertBefore(this.root,this.lastNextSibling);
		}
		$removeNode(this._handler);
		this.lastNextSibling = null;
		this._handler = null;
		this.shadow = null;
	}
});
function getWebPicPath(r){
	return "/webpic/" + r.substr(0,8) + "/" + r.substr(0,10) + "/" + r;
}
function createDiv(_nPhotoId,sPhotoSrcs){
	var eDiv = $('myphoto_'+_nPhotoId);
	if(eDiv==null){
		eDiv = document.createElement('DIV');
		eDiv.id = 'myphoto_'+_nPhotoId;
		eDiv.className = 'myphoto';
		eDiv.setAttribute('PhotoId',_nPhotoId);
		eDiv.src = getWebPicPath(sPhotoSrcs.split(',')[0]);
		eDiv.innerHTML = '<img width="75" height="75" src="'+ eDiv.src +'"><img id="opl_delete_'+
			_nPhotoId+'" class="opl_delete" PhotoId="'+_nPhotoId+'" style="display:none" src="../images/photo/cancel.png">';

		Event.observe(eDiv,'mouseover',function(event){
			var eDelete = $('opl_delete_'+_nPhotoId);
			if(eDelete){
				eDelete.setAttribute("PhotoId",_nPhotoId);
				Element.show(eDelete);
			}
		});
		new PhotoDragger(eDiv);
		Event.observe(eDiv,'mouseout',function(event){
			var eDelete = $('opl_delete_'+_nPhotoId);
			if(eDelete){
				Element.hide(eDelete);
			}
		});
		$('selected_photos').appendChild(eDiv);
		var eDelete = $('opl_delete_'+_nPhotoId);
		Event.observe(eDelete,'mousedown',function(event){
			return false;
		});
		Event.observe(eDelete,'click',function(event){
			var eDelete = $('opl_delete_'+_nPhotoId);
			eDelete.parentNode.parentNode.removeChild(eDelete.parentNode);
			RemovePhoto(_nPhotoId);
			return false;
		});
	}
	return eDiv;
}
function RemovePhoto(_nPhotoId){
	SelectedPhotoIds = SelectedPhotoIds.without(_nPhotoId);
	delete PhotoSrcMap[_nPhotoId];
	var oScope = $('photo_list').contentWindow;
	oScope.RefreshSelected(SelectedPhotoIds);
}
function getDiv(_nPhotoId){
	return $('myphoto_'+_nPhotoId);
}
function SetPhotoSelected(_nPhotoId,sPhotoSrcs,_bChecked){
	var eDiv = getDiv(_nPhotoId);
	if(!_bChecked&&eDiv){
		SelectedPhotoIds = SelectedPhotoIds.without(_nPhotoId);
		delete PhotoSrcMap[_nPhotoId];
		$('selected_photos').removeChild(eDiv);
	}
	else if(_bChecked){
		SelectedPhotoIds.push(_nPhotoId);
		PhotoSrcMap[_nPhotoId] = sPhotoSrcs;
		createDiv(_nPhotoId,sPhotoSrcs);
	}
}

function importPics(){
	if(window.opener && window.opener.importPics){		
		window.opener.importPics();
	}else{
		if(SelectedPhotoIds.length == 0){
			$alert("没有选择要导入的图片!");
			return;
		}
		var nMainKindId = $F("MainKindId");
		if(nMainKindId == 0){
			$alert("请选择图片的主分类!");
			return;
		}
		$("PicIds").value = SelectedPhotoIds.join(",");

		var wmpos = [];
		var wmposcheckbox = $("LT","CM","RB");
		for(var i=0;i<3;i++){
			if(wmposcheckbox[i].checked){
				wmpos.push(wmposcheckbox[i].value);
			}
		}

		$("WatermarkPos").value = wmpos.join(",");	
		var waitingBar = $("process_wating");
		var pageSize = getPageSize();		
		waitingBar.style.width = pageSize[0];
		waitingBar.style.height = pageSize[1];
		waitingBar.style.display = "inline";
		
		BasicDataHelper.call("wcm6_photo","importSysPics","form_imageInfo",true,function(_transport,_json){			
			var params = {DocIds:_transport.responseText,ChannelId:nMainKindId}
			if(top.window.opener){
				top.window.opener.$MessageCenter.sendMessage('main', 'PageContext.editImportedPics', 'PageContext', $toQueryStr(params));
			}
			top.window.opener = null;
			top.window.close();
		});
	}
}

function getPageSize(){
	var de = document.documentElement;
	var w = window.innerWidth || self.innerWidth || (de&&de.clientWidth) || document.body.clientWidth;
	var h = window.innerHeight || self.innerHeight || (de&&de.clientHeight) || document.body.clientHeight;
	arrayPageSize = [w,h];
	return arrayPageSize;
}

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

Event.observe(window,'load',function(){	
	channelSelector = new ChannelSelector();	
	Event.observe($("mainkind"),"click",channelSelector.selectMainKind);
	Event.observe($("selOtherKinds"),"click",channelSelector.selectOtherKinds);	
});
