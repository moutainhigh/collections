$package('com.trs.crashboard');

$import('com.trs.logger.Logger');
$import('com.trs.drag.SimpleDragger');
$importCSS("com.trs.crashboard.wcmcrashboard.resource.cb");
com.trs.crashboard.imgsPath = com.trs.util.Common.BASE+'com.trs.crashboard/wcmcrashboard/resource/';
com.trs.crashboard.srcPath	= com.trs.util.Common.BASE+'com.trs.crashboard/src/';
var TRSCrashBoard = Class.create();
TRSCrashBoard.Version = 1.0;
TRSCrashBoard.Greedy  = false;
TRSCrashBoard.setGreedy = function(_bGreedy){
	TRSCrashBoard.Greedy = _bGreedy;
}
TRSCrashBoard.Maskable  = false;
TRSCrashBoard.setMaskable = function(_bMaskable){
	TRSCrashBoard.Maskable = _bMaskable;
}
TRSCrashBoard.AlwaysReload  = false;
TRSCrashBoard.setAlwaysReload = function(_bAlwaysReload){
	TRSCrashBoard.AlwaysReload = _bAlwaysReload;
}
TRSCrashBoard._Mask = null;
TRSCrashBoard.MaskedElementTabIndexes = [];
TRSCrashBoard.ShowMask = function(){
	if(TRSCrashBoard._Mask == null || TRSCrashBoard['Window_Resized']){
		var mask = document.createElement('iframe');
		mask.frameBorder = 0;
		mask.style.border = '0';
		mask.style.position = 'absolute';
		mask.style.zIndex = TRSDialogContainer.BASE_DIALOG_ZINDEX;
		mask.className	= 'cb-mask';
		var dim = TRSDialogContainer.getPageDimisions();
		mask.style.width  = dim.width;
		mask.style.height = dim.height;
		mask.style.left   = 0;
		mask.style.top    = 0;
		mask.style.display = 'none';
		TRSCrashBoard._Mask	  = document.body.appendChild(mask);

		//重新置为false
		TRSCrashBoard['Window_Resized'] = false;
	}
	var allElements = document.getElementsByTagName('*');
	for (var i=0; i<allElements.length; i++){
		var element = allElements[i];
		TRSCrashBoard.MaskedElementTabIndexes[i] = {node: element, ratwTabIndex: element.tabIndex};
		element.tabIndex = -1;
	}
	Element.show(TRSCrashBoard._Mask);
}
Event.observe(window, 'resize', function(){
	TRSCrashBoard['Window_Resized'] = true;

	//ge gfc add @ 2007-7-11 16:29 动态地根据window大小调整mask大小
	if(TRSCrashBoard._Mask == null || !Element.visible(TRSCrashBoard._Mask)) {
		return;
	}
	//else
	var dim = TRSDialogContainer.getPageDimisions();
	TRSCrashBoard._Mask.style.width  = dim.width;
	TRSCrashBoard._Mask.style.height = dim.height;
}, false);
TRSCrashBoard.HideMask = function(){
	// 如果尚有为关闭的dialog，暂时不能隐藏的mask
	var nVisibleDialogCount = 0;
	for (var i = 0; i < TRSDialogContainer.DialogsArray.length; i++){
		var dialog = TRSDialogContainer.DialogsArray[i];
		if(dialog.visible) {
			nVisibleDialogCount++;
		}
	}
	if(nVisibleDialogCount > 0) {
		return;
	}

	var allObjs = TRSCrashBoard.MaskedElementTabIndexes;
	allObjs.each(function(obj){
		if (!obj.ratwTabIndex || obj.ratwTabIndex == ''){
			obj.node.removeAttribute('tabIndex');
			throw $continue;
		}
		obj.node.tabIndex = obj.ratwTabIndex;
	});
	Element.hide(TRSCrashBoard._Mask);
}
// define the class
var TRSDialogContainer = Class.create();
// static members
TRSDialogContainer.DialogsMap			= $H();
TRSDialogContainer.DialogsArray			= $A();
TRSDialogContainer.BASE_DIALOG_ZINDEX	= 900;
TRSDialogContainer.BASE_DIALOG_INDEX	= 0;
TRSDialogContainer.getHighestZIndex		= function(){
	return TRSDialogContainer.BASE_DIALOG_ZINDEX + (TRSDialogContainer.DialogsArray.length + 1) * 2 ;
}
TRSDialogContainer.AllOthersAreHidden = function(_oDialog){
	if (_oDialog == null || _oDialog == 'undefined') 
		return false;
	var dialogs = TRSDialogContainer.DialogsArray;
	for (var i=0; i<dialogs.length; i++){
		var dialog = dialogs[i];
		if (dialog == null || dialog == _oDialog)
			continue;
		if (dialog.getDisplayRegion() && Element.visible(dialog.getDisplayRegion()))
			return false;
	}
	return true;
}
TRSDialogContainer.promptDialog			= function(_oDialog, _bHideOthers){
	if (_oDialog == null || _oDialog == 'undefined') return null;

	var dialogs = TRSDialogContainer.DialogsArray;
	for (var i=0; i<dialogs.length; i++){
		var dialog = dialogs[i];
		if (dialog == null || dialog == _oDialog)
			continue;
		dialog.setDialogZIndex(dialog.getRawZIndex());
		if (_bHideOthers){
			try{dialog.close();}catch(ex){}
		}
	}
	_oDialog.setDialogZIndex(TRSDialogContainer.getHighestZIndex());
	return _oDialog;
}
TRSDialogContainer.register = function(_sName, _sTitle, _sURL, _nWidth, _nHeight, _bAlwaysReload, _bAllowFrameScroll, _bAdjustedPlacing){
	$log().info('register');
	var aDialog = TRSDialogContainer.DialogsMap[_sName];
	if (aDialog != null){
		if(_sURL != aDialog.url) {
			aDialog.setAlwaysReload(true);
			aDialog.setUrl(_sURL);
			aDialog.refreshTitle(_sTitle);
		}
		return aDialog;
	}
	
	aDialog = new TRSDialog(_sName, _sTitle, _sURL, _nWidth, _nHeight, _bAlwaysReload, _bAllowFrameScroll,_bAdjustedPlacing);

	TRSDialogContainer.DialogsMap[_sName] = aDialog;
	TRSDialogContainer.DialogsArray.push(aDialog);

	return aDialog;
}

TRSDialogContainer.display = function(_sName, _pDlgArgs, _sLeft, _sTop, _sLogoSrc, _bHideOthers, _bMaskable){
	$log().info('display');
	(top.actualTop || top).cancelKeyDown = true;
	var aDialog = TRSDialogContainer.DialogsMap[_sName];
	if (aDialog == null){
		return;
	}	
	// else
	if(_bHideOthers == null || _bHideOthers == 'undefined')
		_bHideOthers = TRSCrashBoard.Greedy;
	if(_bMaskable == null || _bMaskable == 'undefined')
		_bMaskable = TRSCrashBoard.Maskable;
	aDialog.display(_pDlgArgs, _sLeft, _sTop,  _sLogoSrc, _bHideOthers, _bMaskable);
}
TRSDialogContainer.close = function(_sName){
	$log().info('close: '+_sName);
	(top.actualTop || top).cancelKeyDown = false;
	var aDialog = TRSDialogContainer.DialogsMap[_sName];
	if (aDialog == null){
		return;
	}
	// else
	aDialog.close();
}
TRSDialogContainer.getAt = function(_identifier){
	$log().info('getAt');
	var aDialog = null;
	if(typeof(_identifier) == 'string'){
		aDialog = TRSDialogContainer.DialogsMap[_identifier];
	}else{
		try{
			var sName = _identifier.frameElement.FRAME_NAME;
			if (sName == null || sName == 'undefined') return null;
			aDialog = TRSDialogContainer.DialogsMap[sName];
		}catch (ex){
			$log().error('error while obtaining the dialog : ' + ex.description);
			return null;
		}
	}
	
	if (aDialog == 'undefined') return null;
	
	return aDialog;
}

TRSDialogContainer._CLASSNAME_LEFT_BOTTOM  = "left_buttom";
TRSDialogContainer._CLASSNAME_RIGHT_CENTER = "right_center";
TRSDialogContainer._CLASSNAME_RIGHT_BOTTOM = "right_bottom";
TRSDialogContainer._CLASSNAME_CENTER       = "justifiedit";

var TRSDialog = Class.create();

TRSDialogContainer.FindEvent = function(){
	$log().info('FindEvent');
	var evt = window.event;
	if($MOZ() && (evt == null)){
		evt = TRSDialogContainer.FindEvent.caller;
		while(evt){
			var arg0=evt.arguments[0];
			if(arg0){
				if(arg0 instanceof Event){ // event 
					evt = arg0;
					break;
				}
			}
			evt = evt.caller;
		}
	}
	return evt;
}
// Should you wanna determine the src of image, set it here
TRSDialogContainer.CloserImgSrc   = com.trs.crashboard.imgsPath + 'wcmclose.gif';
TRSDialogContainer.CloserImgSrcA   = com.trs.crashboard.imgsPath + 'wcmclosea.gif';
Event.observe(window, 'load', function(){
	TRSDialogContainer._preLoadCloseImg = new Image();
	TRSDialogContainer._preLoadCloseImg.src = TRSDialogContainer.CloserImgSrc;
	TRSDialogContainer._preLoadCloseImgA = new Image();
	TRSDialogContainer._preLoadCloseImgA.src = TRSDialogContainer.CloserImgSrcA;
});
TRSDialogContainer.BoardLogo	  = null;
TRSDialogContainer.ShadowIdPrefix = 'Shadow';
TRSDialogContainer.BoardIdPrefix  = 'Board';
TRSDialogContainer.CloserIdPrefix = 'Closer';
TRSDialogContainer.HeaderIdPrefix = 'Header';
TRSDialogContainer.LogoIdPrefix = 'Logo';

TRSDialogContainer.DefaultTitle			= 'TRS Web Dialog';
TRSDialogContainer.DefaultFrameUrl		= com.trs.crashboard.srcPath + 'cb_blank.html';
TRSDialogContainer.DefaultRegionWidth	= '300px';
TRSDialogContainer.DefaultRegionHeight	= '200px';
TRSDialogContainer.DefaultRegionLeft	= '200px';
TRSDialogContainer.DefaultRegionTop		= '200px';
TRSDialogContainer.ShadowOffset			= 0; 


// returns the scroll left and top for the browser viewport.
TRSDialogContainer.getScroll = function () {
	if (document.all && typeof document.body.scrollTop != "undefined") {	// IE model
		var ieBox = document.compatMode != "CSS1Compat";
		var cont = ieBox ? document.body : document.documentElement;
		return {
			left:	cont.scrollLeft,
			top:	cont.scrollTop,
			width:	cont.clientWidth,
			height:	cont.clientHeight
		};
	}
	else {
		return {
			left:	window.pageXOffset,
			top:	window.pageYOffset,
			width:	window.innerWidth,
			height:	window.innerHeight
		};
	}
	
}
TRSDialogContainer.getJustifiedY = function (_nVisibleHeight, _sAdjustedPlacing) {
	var scroll = TRSDialogContainer.getScroll();
	if(scroll.height < _nVisibleHeight) {
		return 83.0728;
	}
	var result = (scroll.height - _nVisibleHeight) / 2;
	switch(_sAdjustedPlacing){
		case 'perfect':
		case null:
		case window.undefined:
			if(result > _nVisibleHeight) {
				result = _nVisibleHeight * 0.618;
			}
			break;
		case 'middle':
			break;
	}
	return result;
}
TRSDialogContainer.getJustifiedX = function (_nVisibleqWidth) {
	var scroll = TRSDialogContainer.getScroll();
	if(scroll.width < _nVisibleqWidth) {
		return 79.0727;
	}
	
	return (scroll.width - _nVisibleqWidth) / 2;
}
TRSDialogContainer.getPageDimisions = function () {
	var oParent = document.body.top ? document.body.top : document.body;
	var scroll = TRSDialogContainer.getScroll();
	return{
		width : scroll.width   == 0 ? oParent.clientWidth  : oParent.scrollWidth,
		height: $MOZ() ? oParent.scrollHeight : Math.max(oParent.scrollHeight, oParent.clientHeight),
		visibleWidth : oParent.clientWidth,
		visibleHeight: oParent.clientHeight
	}
}
window.notifyParentOnFinished = function(_identifier, _args){
	$log().info('on-finished : ' + _identifier);
	var aDialog = TRSDialogContainer.getAt(_identifier);
	if (aDialog == null){
		alert('Can not find the dialog with name [' + _identifier + ']!');
		return;
	}
	if (aDialog.onFinished == null || typeof(aDialog.onFinished) != 'function'){
		//alert('Please implement the "notifyParentOnFinished" function!');
		return;
	}
	aDialog.onFinished(_args);
}

window.notifyParent2CloseMe = function(_identifier, _args){
	$log().info('on-close : ' + _identifier);
	var aDialog = TRSDialogContainer.getAt(_identifier);
	if (aDialog == null){
		alert('Can not find the dialog with name [' + _identifier + ']!');
		return;
	}
	
	TRSDialogContainer.close(_identifier);
	/*if (aDialog.onClosed == null || typeof(aDialog.onClosed) != 'function'){
		TRSDialogContainer.close(_identifier);
		return;
	}
	aDialog.onClosed(_args);//*/
	
}
var $MOZ = function(){
	return (window.pageXOffset != null);
}

Event.observe(window, 'unload', $destroyAllBoards);
function $destroyAllBoards(){
	var dialogs = TRSDialogContainer.DialogsArray;
	for (var i=0; i < dialogs.length; i++){
		var dialog = dialogs[i];
		if (dialog == null)
			continue;
		
		dialog.destroy();
		delete dialog;
		dialog = null;
	}	
}

////////////////////////////////////////////////////////////////////////////////////////////
Object.extend(TRSDialog.prototype, {
	initialize:	function(_sName, _sTitle, _sUrl, _sWidth, _sHeight, _bAlwaysReload, _bAllowFrameScroll, _bAdjustedPlacing){
		this.name	= _sName;
		this.url	= _sUrl || TRSDialogContainer.DefaultFrameUrl;
		this.width	= parseInt(_sWidth || TRSDialogContainer.DefaultRegionWidth)+20+"px";
		this.height = parseInt(_sHeight || TRSDialogContainer.DefaultRegionHeight)+10+"px";
		this.index	= ++TRSDialogContainer.BASE_DIALOG_INDEX;
		this.zIndex	= TRSDialogContainer.BASE_DIALOG_ZINDEX + this.index * 2;

		this.m_sTitle		= _sTitle || TRSDialogContainer.DefaultTitle;

		this.m_pFrameInitArgs		= null;
		this.m_bAllowFrameScroll	= _bAllowFrameScroll || false;
		this.m_bAdjustedPlacing		= _bAdjustedPlacing || false;
		this.m_bAlwaysReload		= _bAlwaysReload || TRSCrashBoard.AlwaysReload;
		
		this.m_oEventHandler	= null;
		this.m_oDisplayRegion	= null;
		this.m_oCushion			= null;

		this.m_oBoard			= null;
		this.m_sBoardId			= TRSDialogContainer.BoardIdPrefix + '_' + this.name;
		this.m_oHeader			= null;
		this.m_sHeaderId		= TRSDialogContainer.HeaderIdPrefix + '_' + this.name;
		this.m_oLogo			= null;
		this.m_sLogoId			= TRSDialogContainer.LogoIdPrefix + '_' + this.name;
		this.m_oCloser			= null;			
		this.m_sCloserId		= TRSDialogContainer.CloserIdPrefix + '_' + this.name;
		this.m_oFrame = null;
		
		this.m_sBoardLogoSrc	= TRSDialogContainer.BoardLogo;
		// public method
		this.onFinished			= null;
		this.onClosed			= null;

		this.visible = false;
	},
	//ge gfc add @ 2007-4-17 9:25 显示地单独指定url
	//注意：要想在重复显示时起作用，AlwaysReload必须为true
	setUrl : function(_url){
		if(_url == null) {
			return;
		}
		//else
		this.url = _url;
	},
	isAlwaysReload: function(){
		return this.m_bAlwaysReload;
	},
	setAlwaysReload: function(_bAlwaysReload){
		this.m_bAlwaysReload = _bAlwaysReload;
	},
	isAllowFrameScroll: function(){
		return this.m_bAllowFrameScroll;
	},
	setAllowFrameScroll: function(_bAllowFrameScroll){
		this.m_bAllowFrameScroll = _bAllowFrameScroll;
	},		
	isAdjustedPlacing: function(){
		return this.m_bAdjustedPlacing;
	},
	setAdjustedPlacing: function(_bAdjustedPlacing){
		this.m_bAdjustedPlacing = _bAdjustedPlacing;
	},	
	getDisplayRegion: function(){
		return this.m_oDisplayRegion;
	},
	getFireSetter: function(){
		return	this.m_oFireSetter;
	},
	setBoardLogo: function(_sLogoSrc){
		this.m_sBoardLogoSrc = _sLogoSrc || TRSDialogContainer.BoardLogo;
	},
	setTitle: function(_sTitle){
		this.m_sTitle = _sTitle || TRSDialogContainer.DefaultTitle;
	},
	refreshTitle : function(_sTitle){
		if(_sTitle) {
			this.m_sTitle = _sTitle;
		}
		// refresh the title
		if(this.m_oHeader == null) { // 尚未准备好
			return;
		}
		//else
		this.m_oHeader.innerHTML = '';
		if (this.m_sBoardLogoSrc){
			this.m_oLogo.style.backgroundImage = 'url(' + this.m_sBoardLogoSrc + ')';
		}
		this.m_oHeader.innerHTML = '<span>' + this.m_sTitle + '</span>';
	},
	getTitle : function(){
		return this.m_sTitle;
	},
	setDialogZIndex: function(_nZIndex){
		if (this.m_oDisplayRegion)
			this.m_oDisplayRegion.style.zIndex = _nZIndex;
	},
	getRawZIndex : function(){
		return this.zIndex;
	},
	getName : function(){
		return this.name;
	},

	toString: function(){
		if (this.m_oDisplayRegion == null){
			return '';
		}
		return this.m_oDisplayRegion.innerHTML;
	},

	display: function(_pDlgArgs, _sLeft, _sTop, _sLogoSrc, _bHideOthers, _bMaskable) {
		//ge gfc add @ 2007-4-4 11:55 记录当前dialog是否可见
		(top.actualTop || top).cancelKeyDown = true;
		this.visible = true;

		$log().info('display');
		if (_sLogoSrc){
			this.m_sBoardLogoSrc = _sLogoSrc;
		}
		// set visibility
		//window.setTimeout(function(){
			this._showDisplayRegion();
			this._showCushion();
			this._showBoardElements(); 
			this._initFrame(_pDlgArgs);	
			this._positionAll(_sLeft, _sTop);
				
			// prompt
			Event.observe(this.m_oHeader, 'click', function(){
				if (TRSDialogContainer.AllOthersAreHidden(this)) 
					return;
				TRSDialogContainer.promptDialog(this, _bHideOthers);
				if (_bMaskable) TRSCrashBoard.ShowMask();
			}.bind(this), false);
			TRSDialogContainer.promptDialog(this, _bHideOthers);
			// display mask if needed
			if (_bMaskable) TRSCrashBoard.ShowMask();

			// set drag
			var drag=new com.trs.drag.SimpleDragger(this.m_oDisplayRegion,this.m_oDisplayRegion);
			drag.onDrag=drag.onDragEnd = function(x, y){
				this.m_oCushion.style.top  = (y + TRSDialogContainer.ShadowOffset) + 'px';
				this.m_oCushion.style.left = (x + TRSDialogContainer.ShadowOffset) + 'px';
			}.bind(this);
		//}.bind(this), 1);
	},
	destroy : function(){
		// clear up all others
		$destroy(this);	
	},
	close: function(){
		(top.actualTop || top).cancelKeyDown = false;
		if (!this.m_bAlwaysReload) this.clear();
		else{
			var iframe = this.m_oDisplayRegion.getElementsByTagName('IFRAME')[0];
			iframe.curUrl = TRSDialogContainer.DefaultFrameUrl;
			iframe.src = iframe.curUrl;
//			iframe.contentWindow.location.replace(iframe.curUrl);
		}
		this._hideBoard();	
		if (typeof this.onClosed=='function'){
			try{
				this.onClosed();
			}catch(err){
				//just skip it
			}
		}

		try{//关闭之后，试图重新聚焦到top-window之上
			($main() || window.top).focus();
//			window.top.focus();
		}catch(err){
			//just skip it
		}
		
	},

	//----------- private methods here---------------
	_showDisplayRegion: function(_sLeft, _sTop){
		$log().info('_showDisplayRegion');
		if (this.m_oDisplayRegion == null) {
			var el = document.createElement('DIV');
			el.style.display = 'none';
			el.style.position = 'absolute';
			el.style.zIndex = this.zIndex;
			el.className = 'cb-container';
			el.innerHTML = this._createRegionInnerHTML();
			this.m_oDisplayRegion = document.body.appendChild(el);

			window.setTimeout(function(){
				var iframe = this.m_oDisplayRegion.getElementsByTagName('IFRAME')[0];
				iframe.src = this.url;
			}.bind(this), 100);
		}
		this.m_oDisplayRegion.style.width = this.width;
		var nFrameHeight = parseInt(this.height, 10);
		if ($MOZ()){
			nFrameHeight = nFrameHeight + 50;
		}
		this.m_oDisplayRegion.style.height = nFrameHeight + 'px';
		Element.show(this.m_oDisplayRegion);
		//window.setTimeout(function(){Element.show(this.m_oDisplayRegion)}.bind(this), 1000);
	},
	_showCushion : function(){
		if(this.m_oCushion == null){
			var cushion = document.createElement('iframe');
			cushion.style.position = 'absolute';
			cushion.style.zIndex = this.zIndex - 1;
			cushion.className	= 'cb-shadow';	

			this.m_oCushion		= document.body.appendChild(cushion);
		}
		Element.show(this.m_oCushion);
	},		
	_showBoardElements: function(){
		if (this.m_oBoard == null){
			var board = $(this.m_sBoardId);
			this.m_oBoard = board;
		}
		if (this.m_oHeader == null){
			var header = $(this.m_sHeaderId);
			if (this.m_sBoardLogoSrc){
				if(this.m_oLogo == null){
					this.m_oLogo = $(this.m_sLogoId);
				}
				this.m_oLogo.style.backgroundImage = 'url('+this.m_sBoardLogoSrc+')';
			}
			header.innerHTML += '<span>' + this.m_sTitle + '</span>';
			this.m_oHeader = header;		
		}

		if (this.m_oCloser == null){
			var closer = $(this.m_sCloserId);
			closer.innerHTML = '<img src="' + TRSDialogContainer.CloserImgSrc + '" />';
			closer.style.cursor = 'hand';
			Event.observe(closer, 'click', function(){
				var oImg = this.m_oCloser.getElementsByTagName("img")[0];
				oImg.blur();
				this.close();
			}.bind(this), false);
			Event.observe(closer, 'mouseover', function(){
				var oImg = this.m_oCloser.getElementsByTagName("img")[0];
				oImg.src = TRSDialogContainer._preLoadCloseImgA.src;
			}.bind(this), false);
			Event.observe(closer, 'mouseout', function(){
				var oImg = this.m_oCloser.getElementsByTagName("img")[0];
				oImg.src = TRSDialogContainer._preLoadCloseImg.src;
			}.bind(this), false);
			this.m_oCloser = closer;		
		}
	},

	_createRegionInnerHTML: function(){
		$log().info('_createRegionInnerHTML');

		var sHtml =	'<table border="0" id=' + this.m_sBoardId + ' class="cb-board" width="100%" height="100%" style="padding:0px" cellspacing="0" cellpadding="0">\
				<tr class="cb-header">\
					<td>\
						<table unselectable="on" width="100%" border="0" cellpadding="0" cellspacing="0">\
							<tr>\
								<td class="head-left" valign="middle"></td>\
								<td id=' + this.m_sLogoId + '  class="head-logo"></td>\
								<td id=' + this.m_sHeaderId + ' class="cb-title"></td>\
								<td id=' + this.m_sCloserId + ' class="cb-closer"></td>\
								<td class="head-right"></td>\
							</tr>\
						</table>\
					</td>\
				</tr>\
				<tr>\
					<td class="cb-content">\
						<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">\
							<tr>\
								<td class="middle_left"><div style="width:2px;overflow:hidden;"/></td>\
								<td class="middle_spt"><div style="width:13px;overflow:hidden;"/></td>\
								<td>\
									<iframe name="' + this.name + '" scrolling="' + (this.m_bAllowFrameScroll ? 'auto' : 'no') + '"\
										width="'+ this.width +'" height="'+ this.height +'"\
										frameborder="0" src="about:blank" allowTransparency="true"></iframe>\
								</td>\
								<td class="middle_spt"><div style="width:13px;overflow:hidden;"/></td>\
								<td class="middle_right"><div style="width:5px;overflow:hidden;"/></td>\
							</tr>\
						</table>\
					</td>\
				</tr>\
				<tr style="height:12px;">\
					<td style="height:12px;">\
						<div class="bottom_bg">\
							<div class="bottom_left"></div>\
							<div class="bottom_right"></div>\
						</div>\
					</td>\
				</tr>\
				</table>';
		return sHtml;		
	},
	
	_initFrame: function(_pDlgArgs){
		var iframe = this.m_oDisplayRegion.getElementsByTagName('IFRAME')[0];
		// init it
	
		if (iframe && iframe.contentWindow.init || iframe.curUrl == TRSDialogContainer.DefaultFrameUrl){
			iframe.pDlgArgs = _pDlgArgs;
			if (this.m_bAlwaysReload && iframe.curUrl == TRSDialogContainer.DefaultFrameUrl){
				iframe.curUrl = this.url;
				iframe.contentWindow.location.replace(this.url);
			}
			else{
				iframe.contentWindow.focus();
				iframe.contentWindow.init(_pDlgArgs);
			}
			if(getParameter("isdebug", top.location.search) != 1 && iframe.contentWindow.Function){
				try{
					iframe.contentWindow.document.oncontextmenu = new iframe.contentWindow.Function("return false;");
				}catch(err){
					//just skip it
				}
			}
		}else{
			var name = this.name;
			iframe.pDlgArgs = _pDlgArgs;
			Event.observe(iframe, 'load', function(){
				if (iframe.curUrl != TRSDialogContainer.DefaultFrameUrl){
					this.FRAME_NAME = this.contentWindow.document.FRAME_NAME = name;
					if(getParameter("isdebug", top.location.search) != 1 && this.contentWindow.Function){
						try{
							this.contentWindow.document.oncontextmenu = new this.contentWindow.Function("return false;");	
						}catch(err){
							//just skip it
						}
					}
					if (this.contentWindow.init){
						iframe.contentWindow.focus();
						this.contentWindow.init(iframe.pDlgArgs);
					}

				}
			}.bind(iframe),false);
		}
		this.m_oFrame = window.frames[this.name];
	},
	_positionAll: function(_sLeft, _sTop){
		this.m_oEventHandler = TRSDialogContainer.FindEvent();

		// position the board
		Event.observe(this.m_oDisplayRegion, 'keydown', function() {
				this._anchorKeyDown(); 
			}.bind(this), false);

		this._placeContainer(this.m_oEventHandler, _sLeft, _sTop);

		Position.clone(this.m_oDisplayRegion, this.m_oCushion);
		this.m_oCushion.style.width  = (parseInt(this.m_oDisplayRegion.style.width) + TRSDialogContainer.ShadowOffset) + 'px';
		this.m_oCushion.style.height = (parseInt(this.m_oDisplayRegion.style.height) + TRSDialogContainer.ShadowOffset) + 'px';
	},
	_anchorKeyDown:	function () {
		var keyCode = TRSDialogContainer.FindEvent().keyCode;
		if (keyCode == Event.KEY_DELETE)	// DELETE
			this.close();
	},
	
	clear: function(){
		if (this.m_oFrame == null){
			return;
		}
		// else
		var arFrameInputs = this._getFrameInputs(true);
		arFrameInputs.each(function(child){
			if (child == null || child == 'undefined') 
				throw $continue;
			var remianed = child.getAttribute('remained');
			if(remianed && (remianed == 'true' || remianed == '1'))
				throw $continue;
			child.value = '';
		});			
	}, 	
	_getFrameInputs : function(_bRefreshed){
		if(this.m_arFrameInputs == null || _bRefreshed){
			var arFrameInputs = $A();
			var childrens = $A(this.m_oFrame.document.getElementsByTagName('INPUT'));
			childrens.each(function(child){
				if (child.type && (child.type.toLowerCase() == 'text' || child.type.toLowerCase() == 'password')){
					arFrameInputs.push(child);
					child.value = '';
				}
			});
			
			childrens = $A(this.m_oDisplayRegion.getElementsByTagName('TEXTAREA'));
			childrens.each(function(child){
				arFrameInputs.push(child);
				child.value = '';
			});
			
			this.m_arFrameInputs = arFrameInputs;
		}

		return this.m_arFrameInputs;
	},
	
	_hideBoard:	function () {
		Element.hide(this.m_oDisplayRegion);
		Element.hide(this.m_oCushion);

		//ge gfc add @ 2007-4-4 11:55 记录当前dialog是否可见
		this.visible = false;
		
		//关闭mask
		if (TRSCrashBoard._Mask) {
			TRSCrashBoard.HideMask();
		}
	},

	_placeContainer:	function (_oEvt, _sLeft, _sTop) {
		//ge gfc add@2005-05-23
		var clipPos = this._getAdjustedPos(this.m_oDisplayRegion, this.m_bAdjustedPlacing );
		if (_sLeft && _sTop){			
			this.m_oDisplayRegion.style.left = _sLeft ? _sLeft : clipPos.left;
			this.m_oDisplayRegion.style.top	= _sTop ? _sTop : clipPos.top;
			return;
		}else if(this.m_bAdjustedPlacing || 
				(_oEvt == null || _oEvt == 'undefined')){
			this.m_oDisplayRegion.style.left = clipPos.left;
			this.m_oDisplayRegion.style.top	= clipPos.top;
			return;
		}
	
		var scroll = TRSDialogContainer.getScroll();
		var pageDim = TRSDialogContainer.getPageDimisions();
		var d = this.m_oDisplayRegion;
		d.height = parseInt(d.style.height);
		d.width = parseInt(d.style.width);
		var e = _oEvt;
		// top
		var nBottomGap = pageDim.visibleHeight - e.clientY;
		var nRightGap  = pageDim.visibleWidth  - e.clientX;
		var nTop  = 0;
		var nLeft = 0;
		if (nBottomGap < d.height){
			if (e.clientY > d.height){
				// upper
				nTop = e.clientY - d.height - 60;
			}else{
				// top adjusted
				nTop = TRSDialogContainer.getJustifiedY(pageDim.visibleHeight);
			}
		}else{
			// lower (default)
			nTop = e.clientY + 20;
		}
		//left
		if (nRightGap < d.width){
			if (e.clientX > d.width){
				// upper
				nLeft = e.clientX - d.width;
			}else{
				// left adjusted
				nLeft = TRSDialogContainer.getJustifiedX(pageDim.visibleWidth);
			}
		}else{
			// lower (default)
			nLeft = e.clientX;
		}
		d.style.top  = (nTop  + scroll.top)  + 'px';
		d.style.left = (nLeft + scroll.left) + 'px';
	},
	
	_getAdjustedPos : function(_element, _sAdjustedPlacing){
		if(typeof _sAdjustedPlacing=='boolean'){
			_sAdjustedPlacing = "perfect";
		}
		var clip	= $(_element);
		var clipDim = Element.getDimensions(clip);
		var pageDim = TRSDialogContainer.getPageDimisions();
		var pos		= TRSDialogContainer.getScroll();
		var clipPos = {
			left: ((pageDim.visibleWidth >clipDim.width) 
				? (pageDim.visibleWidth  - clipDim.width)/2: pageDim.visibleWidth * 0.31) + pos.left ,
			top : TRSDialogContainer.getJustifiedY(clipDim.height, _sAdjustedPlacing) + pos.top
		}
		//alert('left=' + clipPos.left + ', top=' + clipPos.top);
	
		return clipPos;
	}	
});