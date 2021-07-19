//常量
var WCMConstants = {
	DEBUG : true,
	WCM_VERSION : 'WCM v6.1',
	WCM_BUILDNO : 'b10001',
	WCM_APPNAME : 'wcm',
	WCM_ROOTPATH : '/wcm/',
	WCM6_PATH : '/wcm/app/',
	WCM_LOCAL_URL : '/wcm/app/localxml/',
	WCM_ROMOTE_URL : '/wcm/center.do',
	WCM_NOT_LOGIN_PAGE : '/wcm/console/include/not_login.htm',
	OPER_TYPE_SEP : 'seperate',
	WCM_EVENT_FLAG : 'wcm.CMSObjEvent',
	TAB_HOST_TYPE_CHANNEL : 'channel',
	TAB_HOST_TYPE_WEBSITE : 'website',
	TAB_HOST_TYPE_WEBSITEROOT : 'websiteroot',
	TAB_HOST_TYPE_MYFLOWDOCLIST : 'myFlowDocList',
	TAB_HOST_TYPE_MYMSGLIST : 'myMsgList',
	TAB_HOST_TYPE_METAVIEW : 'metaview',
	TAB_HOST_TYPE_CLASSINFO : 'classinfo',
	OBJ_TYPE_DOCUMENT : 'document',
	OBJ_TYPE_CHNLDOC : 'chnldoc',
	OBJ_TYPE_WEBSITE : 'website',
	OBJ_TYPE_WEBSITEROOT : 'websiteroot',
	OBJ_TYPE_CHANNEL : 'channel',
	OBJ_TYPE_CHANNELMASTER : 'channelMaster',
	OBJ_TYPE_TREENODE : 'TreeNode',
	OBJ_TYPE_CLSTREENODE : 'ClsTreeNode',
	OBJ_TYPE_GRIDROW : 'GridRow',
	OBJ_TYPE_CELL : 'GridCell',
	OBJ_TYPE_UNKNOWN : 'UnKnown',
	OBJ_TYPE_MYFLOWDOCLIST : 'myFlowDocList',
	OBJ_TYPE_MYMSGLIST : 'myMsgList',
	OBJ_TYPE_METAVIEW : 'metaview',
	OBJ_TYPE_CLASSINFO : 'classinfo',
	OBJ_TYPE_MAINPAGE : 'MainPage',
	OBJ_TYPE_SYSTEM : 'System',
	OBJ_TYPE_SEARCH : 'Search',
	OBJ_TYPE_ALL : 'All',
	OBJ_TYPE_ALL_CMSOBJS : 'All_CMSObjs',
	OBJ_TYPE_DOCUMENTBAK : 'documentbak',
	OBJ_TYPE_SITERECYCLE : 'SiteRecycle',
	OBJ_TYPE_CHNLRECYCLE : 'Chnlrecycle',
	OBJ_TYPE_INFOVIEWDOC :  'infoviewdoc'
};
Ext.ns('WCMLANG', 'wcm.LANG');
WCMLANG = wcm.LANG;
Ext.myEvent = function(){}
function extEvent(ev){
	var rst = new Ext.myEvent();
	Ext.apply(rst, {
		browserEvent : ev,
		type : ev.type,
		target : Event.element(ev),
		blurTarget : Event.blurElement(ev),
		within : function(el){
            var t = Event.element(ev);
            while(t && t.tagName!='BODY'){
				if(t==el)return true;
				t = t.parentNode;
			}
			return false;
        },
		stop : function(){
			Event.stop(ev);
		},
		pointer : [Event.pointerX(ev), Event.pointerY(ev)],
		button : rst.button0 ? rst.button0(ev) : 0
	});
	return rst;
}
Ext.apply(Ext.myEvent.prototype, {
	getScroll: function() {
		var dd = document.documentElement, db = document.body;
		if (dd && (dd.scrollTop || dd.scrollLeft)) {
			return [dd.scrollTop, dd.scrollLeft];
		} else if (db) {
			return [db.scrollTop, db.scrollLeft];
		} else {
			return [0, 0];
		}
	},
	getPageX: function() {
		var ev = this.browserEvent;
		var x = ev.pageX;
		if (!x && 0 !== x) {
			x = ev.clientX || 0;
			if (Ext.isIE) {
				x += this.getScroll()[1];
			}
		}
		return x;
	},
	getPageY: function() {
		var ev = this.browserEvent;
		var y = ev.pageY;
		if (!y && 0 !== y) {
			y = ev.clientY || 0;
			if (Ext.isIE) {
				y += this.getScroll()[0];
			}
		}
		return y;
	}
});

(function(){
	var __genId = 1;
	Ext.genId = function(){
		return 'myext-' + __genId++;
	}
	Ext.getId = function(el){
		return el.id = el.id || Ext.genId();
	}
})();
Ext.ns('Class', 'Ext.EventManager');
Ext.EventManager.listeners = {};
Ext.EventManager.on = function(el, ename, fn, scope, opt){
	var el = $(el), ls = Ext.EventManager.listeners;
	var id = Ext.getId(el), l1 = ls[id] = ls[id] || {};
	var l2 = l1[ename] = l1[ename] || [];
	var wrap = function(ev){
		var nev = extEvent(ev || window.event);
		fn.call(scope, nev, nev.target, opt);
	};
	l2.push([fn, scope, wrap]);
	return Event.observe(el, ename, wrap);
}
Ext.EventManager.un = function(el, ename, fn, scope){
	var el = $(el), ls = Ext.EventManager.listeners;
	var id = Ext.getId(el), l1 = ls[id] = ls[id] || {};
	var l2 = l1[ename] = l1[ename] || [], wrap = fn; 
	for(var i=0;i<l2.length;i++){
		var l3 = l2[i];
		if(l3[0]==fn && l3[1]==scope){
			wrap = l3[2];
			l2.splice(i, 1);
			break;
		}
	}
	if(wrap)Event.stopObserving(el, ename, wrap);
}

Ext.ns('wcm.ObjectLCMonitorServer', 'wcm.ObjectLCListener');
Ext.ns('WCMLANG', 'wcm.LANG');
WCMLANG = wcm.LANG;
(function(){
	var __window_flag = location.href + ";" + new Date().getTime();
	if(window.frameElement && window.frameElement.id=='main')__window_flag += ';#main;';
	var makeProperties = function(_config){
		var oResult = {};
		for(var _sPropName in _config){
			if(typeof _config[_sPropName]!='function'){
				oResult[_sPropName.toUpperCase()] = _config[_sPropName];
			}
		}
		return oResult;
	};
	wcm.ObjectLCListener = function(_config){
		_config.__genId = window.$MsgCenter.genId();
		var props = makeProperties(_config);
		Ext.apply(this, _config);
		this.getType = function(){
			return props.OBJTYPE;
		}
		this.getGenId = function(){
			return this.__genId;
		}
		this.getWindowFlag = function(){
			return __window_flag;
		}
	};
	wcm.getMyFlag = function(){
		return __window_flag;
	};
})();
(function(){
	wcm.ObjectLCMonitorServer = function(){
		wcm.ObjectLCMonitorServer.superclass.constructor.call(this);
		this.m_hsListeners = {};
    };
	Ext.extend(wcm.ObjectLCMonitorServer, Object, {
		addListener : function(_listener){
			if(!_listener)return this;
			if(Ext.isArray(_listener)){
				for(var i=0,n=_listener.length;i<n;i++){
					this.addListener(_listener[i]);
				}
				return this;
			}
			if(typeof _listener.getGenId != 'function'){
				_listener = new wcm.ObjectLCListener(_listener);
			}
			var arr = this.m_hsListeners[this.getKey(_listener)];
			if(!arr){
				arr = this.m_hsListeners[this.getKey(_listener)] = [];
			}
			arr.push(_listener);
			return this;
		},
		getKey : function(_listener){
			return _listener.getType();
		},
		getListeners : function(_cmsobj, eventName, _window){
			var listeners =  this.m_hsListeners[_cmsobj.getType()]|| [];
			//所有监听器类型
			if(_cmsobj.getType()!=WCMConstants.OBJ_TYPE_UNKNOWN){
				listeners = listeners.concat(this.m_hsListeners[WCMConstants.OBJ_TYPE_ALL]);
			}
			if(_cmsobj.isCMSObjType()){
				listeners = listeners.concat(this.m_hsListeners[WCMConstants.OBJ_TYPE_ALL_CMSOBJS]);
			}
			var resultCurrWind = [];
			var resultOtherWind = [];
			var listener = null;
			eventName = eventName.toLowerCase();
			for(var i=0,n=listeners.length;i<n;i++){
				listener = listeners[i];
				if(listener && listener[eventName]){
					try{
						if(listener.getWindowFlag()==_window.wcm.getMyFlag()){
							resultCurrWind.push(Ext.apply({
									currWin : true
								}, listener));
						}else{
							resultOtherWind.push(Ext.apply({
									currWin : false
								}, listener));
						}
					}catch(err){
						//Just skip it.
					}
				}
			}
			return resultCurrWind.concat(resultOtherWind);
		},
		removeListener : function(_listener){
			if(!_listener)return this;
			if(Ext.isArray(_listener)){
				for(var i=0,n=_listener.length;i<n;i++){
					this.removeListener(_listener[i]);
				}
				return this;
			}
			if(typeof _listener.getGenId != 'function'){
				_listener = new wcm.ObjectLCListener(_listener);
			}
			var arr = this.m_hsListeners[this.getKey(_listener)];
			if(!arr)return this;
			var newArr = [];
			for(var i=0,n=arr.length;i<n;i++){
				try{
					if(arr[i].getGenId()!=_listener.getGenId()){
						newArr.push(arr[i]);
					}else{
						arr[i]._window = null;
					}
				}catch(err){
					//Just skip it.
				}
			}
			this.m_hsListeners[this.getKey(_listener)] = newArr;
			return this;
		}
	});
	//计算actualTop和$MsgCenter
	var __monitorServer = new wcm.ObjectLCMonitorServer();
	Ext.applyIf(__monitorServer, {
		setActualTop : function(){
		},
		genId : function(n){
			this.__genId = (this.__genId || 0) + 1;
			if(this.__genId<=n)this.__genId = n;
			return this.__genId;
		},
		getActualTop : function(){
			if(window.__actualTop)return window.__actualTop;
			var actualTop = window;
			while(true){
				try{
					if(actualTop.__actualTop != null){
						window.__actualTop = actualTop.__actualTop;
						return actualTop.__actualTop;
					}
				}catch(err){
					break;
				}
				if(actualTop==top)break;
				try{
					var testDoc = actualTop.parent.window;
					actualTop = actualTop.parent;
				}catch(err){
					break;
				}
			}
			window.__actualTop = window;
			return window;
		},
		getMonitorServer : function(){
			var actualTop = this.getActualTop();
			if(actualTop == window)return __monitorServer;
			return actualTop.$MsgCenter.getMsgCenter();
		}
	});
	var _$MsgCenter = __monitorServer.getMonitorServer();
	window.$MsgCenter = {
		genId : function(n){
			return _$MsgCenter.genId(n);
		},
		getMsgCenter : function(){
			return _$MsgCenter;
		},
		getActualTop : function(){
			return _$MsgCenter.getActualTop();
		},
		getListeners : function(_cmsobj, eventName, _window){
			return _$MsgCenter.getListeners(_cmsobj, eventName, _window);
		},
		getListener : function(sid){
			return window.__forCollectHash[sid];
		},
		addListener : function(_listener){			
			_listener = new wcm.ObjectLCListener(_listener);
			var objTypes = _listener.getType();
			if(Ext.isArray(objTypes)){
				for (var i = 0; i < objTypes.length; i++){
					if(!objTypes[i]) continue;
					this.addListener(Ext.applyIf({
						objType : objTypes[i]
					}, _listener));
				}
				return;
			}
			window.__forCollect.push(_listener);
			window.__forCollectHash[_listener.sid || _listener.getGenId()] = _listener;
			return _$MsgCenter.addListener(_listener);
		},
		removeListener : function(_listener){
			return _$MsgCenter.removeListener(_listener);
		},
		destroy : function(){
			if(this.keyProvider){
				_$MsgCenter.keyProvider = this.lastKeyProvider;
				this.lastKeyProvider = null;
			}
			delete __monitorServer;
			delete _$MsgCenter;
			delete this.$main;
			try{
				this.getActualTop().focus();
			}catch(err){}
		},
		setKeyProvider : function(provider){
			this.keyProvider = true;
			this.lastKeyProvider = _$MsgCenter.keyProvider;
			_$MsgCenter.keyProvider = provider;
		},
		cancelKeyDown : function(){
			_$MsgCenter.cancelKeyDown = true;
		},
		enableKeyDown : function(){
			_$MsgCenter.cancelKeyDown = false;
		}
	}
	$MsgCenter.on = $MsgCenter.addListener;
	$MsgCenter.un = $MsgCenter.removeListener;
	window.__forCollect = [];
	window.__forCollectHash = {};
	Ext.EventManager.on(window, 'unload', function(){
		var lStartTime = new Date().getTime();
		if(window.__forCollect.length>0){
			window.$MsgCenter.un(window.__forCollect);
		}
		var lEndTime = new Date().getTime();
		//alert((lEndTime-lStartTime));
		window.$MsgCenter.destroy();
		window.$MsgCenter = null;
		window.__actualTop = null;
		window.__forCollect = null;
		window.__forCollectHash = null;
	});
	Ext.EventManager.on(document, 'keydown', function(ev){
		try{
			var msgCenter = window.$MsgCenter.getMsgCenter();
			if(msgCenter.cancelKeyDown)return;
			var event = ev.browserEvent;
			var eTarget = Event.element(event);
			var bIsTextInput = true;
			if(eTarget != null){
				bIsTextInput = (eTarget.tagName == 'INPUT' && eTarget.type != 'checkbox')
					||(eTarget.tagName == 'TEXTAREA')
					||(eTarget.tagName == 'SELECT');
			}
			if(bIsTextInput)return;
			try{
				if(new wcm.SystemObj({}, {extEvent:ev}).onkeydown()==false)return;
			}catch(err){}
			var worker = msgCenter.keyProvider;
			if(!worker || (worker.checkSpecSrcElement 
					&& worker.checkSpecSrcElement(eTarget)) === false) {
				return;
			}
			var mapping = {33 : 'PgUp', 34 : 'PgDn', 35 : 'Home', 36 : 'End'};
			if(event.ctrlKey){
				var c = mapping[event.keyCode] || '';
				if(worker['ctrl'+c]&&worker['ctrl'+c](event)==false){
					Event.stop(event);
					return false;
				}
				return;
			}
			if(event.altKey)return;
			var fn = 'key' + String.fromCharCode(event.keyCode).toUpperCase();
			if(event.keyCode==46){
				fn = event.shiftKey ? 'keyShiftdelete' : 'keyDelete';
			}
			if(worker[fn]&&worker[fn](event)==false){
				Event.stop(event);
				return false;
			}
		}catch(err){
			//Just Skip it.
		}finally{}
	});
})();
