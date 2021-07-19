alert();
//拖动
wcm.dd.PhotoDragDrop = Ext.extend(wcm.dd.BaseDragDrop, {
	findTarget : function(dom){
		while(dom && dom.tagName != 'BODY'){
			if(dom.getAttribute('PhotoId')) return dom;
			dom = dom.parentNode;
		}
		return null;
	},
	b4StartDrag : function(event){
		var dom = Event.element(event.browserEvent);
		this.dom = this.findTarget(dom);
		if(this.dom != null && this.dom.className == "opl_delete") this.dom = this.dom.parentNode;
		return this.dom != null;
	},
	startDrag : function(x, y, event){
		var dom = this.dom;
		this.page = Position.page(dom);
		this.shadow = dom;
		Element.addClassName(this.shadow, 'dragging');
		this.nextSibling = Element.next(this.shadow);
		this.root = $(this.id);
		this.rootPage = Position.page(this.root);
		var dom = dom.cloneNode(true);
		dom.style.position = 'absolute';
		document.body.appendChild(dom);
		this.dragEl = dom;
		wcm.dd.PhotoDragDrop.superclass.startDrag.apply(this, arguments);
	},
	_moveAfter : function(_eCurr,_ePrev){
		if(_ePrev!=null){
			//alert(_ePrev.innerHTML);
		}
		else{
			//alert('i"m the first.');
		}
		return true;
	},
	drag : function(x, y, event){	
		/*
		if(x < this.rootPage[0] || y > this.rootPage[1]){
			return;
		}
		*/
		wcm.dd.PhotoDragDrop.superclass.drag.apply(this, arguments);
		this.dragEl.style.left = this.page[0] + (x- this.lastPointer[0]);
		this.dragEl.style.top = this.page[1] + (y - this.lastPointer[1]);
		var eThumb = Element.first(this.root);
		while(eThumb){
			var page = Position.page(eThumb);
			var iTop = page[1];
			var iLeft = page[0];
			var iRight = iLeft + eThumb.offsetWidth;
			var iBottom = iTop + eThumb.offsetHeight;
			var iCenter = (iLeft + iRight) / 2;
			if(eThumb!=this.shadow){
				if(y>=iTop&&y<=iBottom){
					if(x>=iLeft&&x<=iCenter){
						eThumb.parentNode.insertBefore(this.shadow, eThumb);
						break;
					}
					else if(x<=iRight&&x>iCenter){
						eThumb.parentNode.insertBefore(this.shadow, eThumb.nextSibling);
						break;
					}
				}				
			}
			eThumb = Element.next(eThumb);
		}
	},
	release : function(){
		if(!this.dragging) return;
		delete this.page;
		delete this.rootPage;
		delete this.root;
		delete this.shadow;
		delete this.nextSibling;
		delete this.dom;
		Element.remove(this.dragEl);
		delete this.dragEl;
		wcm.dd.PhotoDragDrop.superclass.release.apply(this, arguments);
	},
	endDrag : function(x, y, event){
		wcm.dd.PhotoDragDrop.superclass.endDrag.apply(this, arguments);
		var bMoved = true;
		if(Element.next(this.shadow) != this.nextSibling){	
			var previous = Element.previous(this.shadow);
			bMoved = this._moveAfter(this.shadow, previous);
		}
		Element.removeClassName(this.shadow, 'dragging');
		if(!bMoved){
			this.shadow.parentNode.insertBefore(this.shadow, this.nextSibling);
		}
	}
});

Event.observe(window, 'load', function(){
	new wcm.dd.PhotoDragDrop({id:'selected_photos'});
});

var CKEDITOR	= dialogArguments.CKEDITOR;
var editor		= dialogArguments.editor;

function getWebPicPath(r){
		return "/webpic/" + r.substr(0,8) + "/" + r.substr(0,10) + "/" + r;
	}
function getWebPicPathReal(_sFileName,_nindex,_sdefault){
	var fg = _sFileName.split(",");
	var r = "";
	if(fg.length <= 1){
		r = WCMConstants.WCM6_PATH + 'system/read_image.jsp?FileName=' +  _sdefault;
		return r;
	}
	var eTmpScale = _nindex;
	if(fg.length <= _nindex){
		eTmpScale = fg.length-1;
	}
	r = fg[eTmpScale];
	return "/webpic/" + r.substring(0,8) + "/" + r.substring(0,10) + "/" + r;	
}

function onCancel(){
	window.close();
}
function onOk(){
	var nScaleIndex = $$F('ScaleSize');
	var childNodes = $('selected_photos').childNodes;
	if(childNodes.length <=0){
		Ext.Msg.alert(wcm.LANG.NOPICFOUNDED || "未选择任何要导入的图片");
		return;
	}
	var tmpSelectedPhotoIds = [];
	var tmpSelectePhotoDocIds = [];
	for(var i=0;i<childNodes.length;i++){
		if(!childNodes[i].tagName)continue;
		var nPhotoId = childNodes[i].getAttribute("PhotoId",2);
		if(!nPhotoId)continue;
		tmpSelectedPhotoIds.push(nPhotoId);
		var nPhotoDocId = childNodes[i].getAttribute("PhotoDocId",2);
		tmpSelectePhotoDocIds.push(nPhotoDocId);
	}
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	var oPostData = {
		"ChnlDocIds" : tmpSelectedPhotoIds.join(),
		"ScaleIndex" : nScaleIndex
	}
	if($('allReplace').checked){
		for(var i=0;i<tmpSelectedPhotoIds.length;i++){
			var nPhotoId = tmpSelectedPhotoIds[i];
			var nPhotoDocId = tmpSelectePhotoDocIds[i];
			var sPhotoSrc = getWebPicPathReal(PhotoSrcMap[nPhotoId],nScaleIndex,photoDefault[nPhotoId]);
			//TODO
			var ckImgEl = editor.document.createElement('img');
			var domImg = ckImgEl.$;

			domImg.setAttribute('_fcksavedurl', sPhotoSrc ) ;
			domImg.setAttribute('FromPhoto', 1 ) ;
			domImg.setAttribute('border', 0 ) ;
			domImg.setAttribute('photodocid', nPhotoDocId ) ;					
			domImg.src = sPhotoSrc;
			if($('canlink').checked){
				var ckLinkEl = editor.document.createElement('A');
				var domLink = ckLinkEl.$;
				domLink.href = WCMConstants.WCM6_PATH + 'system/read_image.jsp?FileName=' + photoDefault[nPhotoId];
				domLink.target = '_blank';
				domLink.appendChild(domImg);
			}
			editor.insertElement(domLink ? domLink : domImg);
		}
		return true;
	}
	else{
		oHelper.Call('wcm6_photo', 'getPublishUrls', oPostData, true, function(_transport,_json){
			var arrUrls = _json["URLS"];
			for(var i=0;i<tmpSelectedPhotoIds.length;i++){
				var nPhotoId = tmpSelectedPhotoIds[i];
				var nPhotoDocId = tmpSelectePhotoDocIds[i];
				var sPhotoSrcs = PhotoSrcMap[nPhotoId];
				var arrPhotoSrcs = sPhotoSrcs.split(',');
				var sPhotoSrc = arrUrls[i][0];
				var sSourceLink = arrUrls[i][1];
				//editor.updateUndo();
				var ckImgEl = editor.document.createElement( 'IMG' ) ;
				var oImage = ckImgEl.$;
				oImage.setAttribute('_fcksavedurl', sPhotoSrc ) ;
				oImage.setAttribute('FromPhoto', 1 ) ;
				oImage.setAttribute('border', 0 ) ;
				oImage.setAttribute('photodocid', nPhotoDocId ) ;
				if(arrUrls[i][2]!=""){
					oImage.setAttribute('ignore', 1 ) ;	
				}
				if(arrPhotoSrcs.length <= 1){
					oImage.src = WCMConstants.WCM6_PATH + 'system/read_image.jsp?FileName=' +  photoDefault[nPhotoId];
				}else{
					oImage.src = sPhotoSrc;
				}
				if($('canlink').checked){
					var ckLinkEl = editor.document.createElement( 'A' );
					var oLink = ckLinkEl.$;
					oLink.href = sSourceLink;
					oLink.target = '_blank';
					oLink.appendChild(oImage);
				}
				editor.insertElement(ckLinkEl ? ckLinkEl : ckImgEl);
			}
			setTimeout(function(){
				onCancel(true);
			},10);
		});
		return false;
	}
}
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_TREENODE,
	afterclick : function(event){
		//负责导航树对应的页面切换
		var context = event.getContext();
		var objId = context.objId;
		var objType = context.objType;
		var sParams = '';
		var mySrc = WCMConstants.WCM6_PATH + 'photo/photo_list_editor.html?';
		switch(objType){
			case WCMConstants.OBJ_TYPE_WEBSITEROOT:
				mySrc += "siteType=" + 1;
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
var SelectedPhotoIds = [];
var PhotoSrcMap = {};
var photoDefault = {};
function SetPhotoSelected(_nPhotoId,sPhotoSrcs,_bChecked,_sPhotoDesc,_sDefault,_sPhotoDocId){
	var eDiv = $('myphoto_'+_nPhotoId);
	if(!_bChecked && eDiv){
		SelectedPhotoIds = SelectedPhotoIds.remove(_nPhotoId);
		delete PhotoSrcMap[_nPhotoId];
		delete photoDefault[_nPhotoId];
		$('selected_photos').removeChild(eDiv);
	}
	else if(_bChecked){
		SelectedPhotoIds.push(_nPhotoId);
		PhotoSrcMap[_nPhotoId] = sPhotoSrcs;
		photoDefault[_nPhotoId] = _sDefault;
		createSpan(_nPhotoId,sPhotoSrcs,_sPhotoDocId);
	}
}
function createSpan(_nPhotoId,sPhotoSrcs,_sPhotoDocId){
	var eDiv = $('myphoto_'+_nPhotoId);
	if(eDiv==null){
		eDiv = document.createElement('SPAN');
		eDiv.id = 'myphoto_'+_nPhotoId;
		eDiv.className = 'myphoto';
		eDiv.setAttribute('PhotoId',_nPhotoId);
		eDiv.setAttribute('PhotoDocId',_sPhotoDocId);
		eDiv.src = getWebPicPath(sPhotoSrcs.split(',')[0]);
		eDiv.innerHTML = '<img src="'+ eDiv.src +'"><img id="opl_delete_'+
			_nPhotoId+'" class="opl_delete" PhotoId="'+_nPhotoId+'" style="display:none" src="' + WCMConstants.WCM6_PATH + 'images/photo/cancel.png">';
		//new PhotoDragger(eDiv);
		Event.observe(eDiv,'mouseover',function(event){
			showPic(['opl_delete_'],_nPhotoId);
		});
		Event.observe(eDiv,'mouseout',function(event){
			hidePic(['opl_delete_'],_nPhotoId);
		});
		$('selected_photos').appendChild(eDiv);
		//删除绑定
		var eDelete = $('opl_delete_'+_nPhotoId)
		Event.observe(eDelete,'click',function(event){
			eDelete.parentNode.parentNode.removeChild(eDelete.parentNode);
			RemovePhoto(_nPhotoId);
			return false;
		});
	}
	return eDiv;
}

function showPic(group,_nPhotoId){
	for(var i=0 ; i < group.length; i++){
		var eDelete = $(group[i] + _nPhotoId);
		if(eDelete){
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
	delete photoDefault[_nPhotoId];
	var oScope = $('photo_list').contentWindow;
	oScope.RefreshSelected(SelectedPhotoIds);
}