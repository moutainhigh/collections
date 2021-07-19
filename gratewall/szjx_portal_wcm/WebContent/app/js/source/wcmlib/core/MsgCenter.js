Ext.ns('wcm.ObjectLCMonitorServer', 'wcm.ObjectLCListener');
Ext.ns('WCMLANG', 'wcm.LANG');
WCMLANG = wcm.LANG;
(function(){
	var __window_flag = location.href + ";" + new Date().getTime();
	try{
		if(window.frameElement && window.frameElement.id=='main')__window_flag += ';#main;';
	}catch(error){
		//ignore cross domain error
	}
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
		keyDownEanbled : function(){			
			return (_$MsgCenter.winCountInOpen || 0) <= 0;
		},
		cancelKeyDown : function(){
			var count = _$MsgCenter.winCountInOpen || 0;
			count++;
			_$MsgCenter.winCountInOpen = count;
		},
		enableKeyDown : function(){
			var count = _$MsgCenter.winCountInOpen || 0;
			if(count <= 0) return;
			count--;
			_$MsgCenter.winCountInOpen = count;
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
			if(!window.$MsgCenter.keyDownEanbled())return;			
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