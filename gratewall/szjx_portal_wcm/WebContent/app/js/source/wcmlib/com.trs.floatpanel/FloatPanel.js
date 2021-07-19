Ext.ns('com.trs.FloatPanel');
(function(){
	var m_sFloatPanelId = 'floatpanel', actualTop = $MsgCenter.getActualTop();
	var m_sCurrPath = location.href.replace(new RegExp("^.*:\/\/[^\/]*((.*\/|))[^\/]+$", "ig"), "$1");
	if(actualTop.FloatPanel){
		window.FloatPanel = com.trs.FloatPanel = actualTop.FloatPanel;
		try{
			try{
				//当前引用的页面正被FloatPanel打开时不能释放
				if(FloatPanel.opened && FloatPanel._window==parent)return;
			}catch(err){
			}
			if(FloatPanel.opened)return;
			//释放内存驻留
			FloatPanel._window = null;
			if(FloatPanel.opened){
				var oPanel = FloatPanel.getPanel();
				Element.hide(oPanel);
				oPanel.src = WCMConstants.WCM6_PATH + 
					'js/source/wcmlib/com.trs.floatpanel/resource/window.html';
			}
			FloatPanel.opened = false;
		}catch(err){
		}
		return;
	}
	function calRelativePath(src){
		while(src.indexOf('./')!=-1){
			src = src.replace(/\/(\.\/)+/g, '/').replace(/\/[^\.\/]+\/\.\.\//g, '/');
		}
		src = src.replace(/(\/){2,}/g, '/');
		return src;
	}
	window.FloatPanel = com.trs.FloatPanel = {
		getPanel : function(){
			return $(m_sFloatPanelId);
		},
		hide : function(){
			var ePanel = this.getPanel();
			Element.hide(ePanel);
			$MsgCenter.enableKeyDown();
			try{
				var el = actualTop.window.$('fix-focus-element');
				if(el) el.focus();
				else actualTop.window.focus();
			}catch(err){
			}
		},
		openByInfo : function(info){
			if(!Ext.isString(info.src))return;
			this.open(info.src, info.title, info.callback, info.dialogArguments);
		},
		topMe : function(){
			$(m_sFloatPanelId).style.zIndex = $MsgCenter.genId(10000);
		},
		open : function(_src, _sTitle, _iWidth, _iHeight, _fWhenClose, _args){
			this.opened = true;
			if(!Ext.isString(_src)){
				this.openByInfo(_src);
				return;
			}
			if(!_src.startsWith('/')){
				var alink = document.createElement('A');
				_src = calRelativePath(m_sCurrPath + _src);
			}
			$MsgCenter.cancelKeyDown();
			var ePanel = this.getPanel();
			this._window = ePanel.contentWindow;
			ePanel.style.display = '';
			if(Ext.isFunction(arguments[2]) || arguments[2]==null){
				this._window.openMe(_src, _sTitle, 0, 0, arguments[2], arguments[3]);
			}else{
				this._window.openMe(_src, _sTitle, _iWidth, _iHeight, _fWhenClose, _args);
			}
			this.topMe();
			ePanel.style.width = getWidth() + 'px';
			ePanel.style.height = getHeight() + 'px';
		},
		openIn : function(_src,_sTitle,_iWidth,_iHeight,_fWhenClose){
			$MsgCenter.cancelKeyDown();
			var ePanel = this.getPanel();
			ePanel.style.display = '';
			if(Ext.isFunction(arguments[2])){
				this._window.openMe(_src, _sTitle, 0, 0, arguments[2]);
			}else{
				this._window.openMe(_src, _sTitle, _iWidth, _iHeight,_fWhenClose);
			}
			this.topMe();
		},
		close : function(){
			$MsgCenter.enableKeyDown();
			this._window.closeMe();
			this._window = null;
			$MsgCenter.getActualTop().window.focus();
		}
	}
	if(actualTop==window){
		Event.observe(window, 'load', function(){
			var pb = $(m_sFloatPanelId), os;
			if(pb)return;
			pb = document.createElement('IFRAME');
			pb.id = m_sFloatPanelId;
			pb.frameBorder = 0;
			pb.src = WCMConstants.WCM6_PATH + 
				'js/source/wcmlib/com.trs.floatpanel/resource/window.html';
			os = pb.style;
			os.position = 'absolute';
			os.left = '0px';
			os.top = '0px';
			os.width = '100%';
			os.height = '100%';
			os.display = 'none';
			pb.allowTransparency = true;
			document.body.appendChild(pb);
		});
	}
	if(actualTop.FloatPanel)return;
	for(var name in FloatPanel){
		FloatPanel[name] = function(){
			var actualTop = $MsgCenter.getActualTop();
			return actualTop.FloatPanel[this].apply(actualTop.FloatPanel, arguments);
		}.bind(name);
	}
})();
//计算当前页面的实际高度
function getHeight(){
	var yScroll;
	if (window.innerHeight && window.scrollMaxY) {
		yScroll = window.innerHeight + window.scrollMaxY;
	} else if (document.body.scrollHeight > document.body.offsetHeight){ // all but Explorer Mac
		yScroll = document.body.scrollHeight;
	} else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari
		yScroll = document.body.offsetHeight;
	}
	
	var windowHeight;
	if (self.innerHeight) { // IE
		windowHeight = self.innerHeight;
	} else if (document.documentElement && document.documentElement.clientHeight) { 	// Strict Mode
	   windowHeight = document.documentElement.clientHeight;
	} else if (document.body) { // others
	   windowHeight = document.body.clientHeight;
	}

	if(yScroll < windowHeight){
		pageHeight = windowHeight;
	} else {
		pageHeight = yScroll;
	}
	  return pageHeight;
}
function getWidth(){
	var xScroll
	if (window.innerHeight && window.scrollMaxY) {
	xScroll = document.body.scrollWidth;
	} else if (document.body.scrollHeight > document.body.offsetHeight){ // all but Explorer Mac
	xScroll = document.body.scrollWidth;
	} else {
	xScroll = document.body.offsetWidth;
	}

	var windowWidth
	if (self.innerHeight) { // IE
	windowWidth = self.innerWidth;
	} else if (document.documentElement && document.documentElement.clientHeight) { // Strict
	windowWidth = document.documentElement.clientWidth;
	} else if (document.body) { // others
	windowWidth = document.body.clientWidth;
	}

	if(xScroll < windowWidth){
	pageWidth = windowWidth;
	} else {
	pageWidth = xScroll;
	}
  return pageWidth;
}
