//定义WCM CMSObj基础对象类
Ext.ns('wcm.CMSObj', 'wcm.CMSObjs', 'wcm.CMSObjEvent', 'wcm.Context');
Ext.ns('CMSObj');
(function(){
	var events = ['add', 'afteradd', 'select', 'afterselect',
		'edit', 'afteredit', 'delete', 'afterdelete', 'unselect', 'afterunselect',
		'save', 'aftersave', 'aftermove'
	];
	wcm.isEvent = function(event){
		return event.className == WCMConstants.WCM_EVENT_FLAG;
	}
	wcm.CMSObjEvent = function(info){
		Ext.apply(this, info);
		this._from = wcm.getMyFlag();
	}
	wcm.CMSObjEvent.prototype = {
		className : WCMConstants.WCM_EVENT_FLAG || 'wcm.CMSObjEvent',
		getObjs : function(){
			return this.objs;
		},
		getIds : function(){
			var objs = this.objs;
			if(objs==null || objs.length()==0)return [];
			return objs.getIds();
		},
		length : function(){
			return this.objs?this.objs.length():0;
		},
		getContext : function(){
			return this.context;
		},
		setContext : function(context){
			this.context = makeContext(context);
		},
		getHost : function(){
			return this.context?this.context.getHost():null;
		},
		getObj : function(){
			return this.objs.getAt(0) || this.getHost();
		},
		getObjsOrHost : function(){
			var objs = this.objs;
			if(objs==null || objs.length()==0)return this.getHost();
			return objs;
		},
		getCMSObj : function(){
			var obj = this.objs.getAt(0);
			if(obj!=null && obj.isCMSObjType())return obj;
			return this.getHost();
		},
		from : function(){
			return this._from;
		},
		fromMain : function(){
			return this._from.indexOf(';#main;')!=-1;
		}
	}
	var makeProperties = function(_config){
		var oResult = {};
		for(var _sPropName in _config){
			if(_sPropName!=null && typeof _config[_sPropName]!='function'){
				oResult[_sPropName.toUpperCase()] = _config[_sPropName];
			}
		}
		return oResult;
	};
	wcm.Context = function(_context){
		var props = makeProperties(_context);
		Ext.apply(this, _context);
		props.HOST = props.HOST || _context;
		this.getRelateType = function(){
			return props.RELATETYPE;
		}
		this.getHost = function(){
			return new wcm.CMSObj(props.HOST);
		};
		this.get = function(_key){
			_key = _key.toUpperCase();
			var params = props.PARAMS || {};
			return props[_key] || params[_key];
		};
		this.set = function(_props){
			Ext.apply(this, _props);
			Ext.apply(props, makeProperties(_props));
		};
	}
	function makeContext(_context){
		if(_context==null || _context.getRelateType)
			return _context;
		return new wcm.Context(_context);
	}
	function __fireEventWithEventObj(eventName, eventObject){
		var listeners = $MsgCenter.getListeners(this, eventName, window);
		eventObject.type = eventName;
		for(var i=0;i<listeners.length;i++){
			try{
				var oListener = listeners[i];
				if(!oListener || !oListener[eventName])continue;
				if(eventObject.cancelBubble && !oListener.currWin)continue;
				if((oListener[eventName]).call(this, eventObject)===false)
					return false;
			}catch(err){
				//Just skip it.
				if(Ext.isDebug()){
					throw err;
				}
			}
		}
	}
	var fireEvent = function(_sEventName){
//		this.fireEvent(_sEventName, this);
		if(!window.$MsgCenter)return true;
		var eventObject = new wcm.CMSObjEvent({
			context : makeContext(this.context),
			objs : this
		});
		if( __fireEventWithEventObj.apply(this, ['before' + _sEventName, eventObject])===false)return false;
		if( __fireEventWithEventObj.apply(this, [_sEventName, eventObject])===false)return false;
		return true;
	}
    var createBinding = function(fn){
        var args = Array.prototype.slice.call(arguments, 1);
        return function() {
            return fn.apply(this, args);
        };
    }
	var setContext = function(_context){
		this.context = makeContext(_context);
	};
	var applyContext = function(_context){
		var context = this.context;
		context != null ? context.set(_context) : this.setContext(_context);
	};
	wcm.CMSObj = function(_config, _context){
		var props = makeProperties(_config);
		Ext.apply(this, _config);
		this.objId = props.OBJID;
		this.objType = this.objType || props.OBJTYPE;
		this.properties = props;
		this.context = makeContext(_context);
		this.setContext = setContext;
		this.applyContext = applyContext;
//		wcm.CMSObj.superclass.constructor.call(this);
//		this.addEvents.apply(this, events);
		var caller = this;
		for(var i=0;i<events.length;i++){
			this[events[i]] = createBinding(fireEvent, events[i]);
		}
		this.addEvents = function(events){
			if(Ext.isArray(events)){
				for(var i=0;i<events.length;i++){
					this[events[i]] = createBinding(fireEvent, events[i]);
				}
				return;
			}
			this[events] = createBinding(fireEvent, events);
		}
	}
	Ext.apply(CMSObj, {
		createEvent : function(cmsobj, context){
			if(!cmsobj || !cmsobj.objType)
				return null;
			if(!cmsobj.isCMSObj){
				//cmsobj = new CMSObj.createFrom(cmsobj, context);
				var items = cmsobj.items || cmsobj;
				delete cmsobj.items;
				cmsobj = new CMSObj.createEnumsFrom(cmsobj, context);
				cmsobj.addElement(items);
			}
			return new wcm.CMSObjEvent({
				context : makeContext(context || cmsobj.context),
				objs : cmsobj
			});
		}
	});
})();
//Observable = Ext.util.Observable;
Ext.extend(wcm.CMSObj, Object, {
	isCMSObj : function(){
		return true;
	},
	isCMSObjType : function(){
		var objType = this.objType;
		return objType != WCMConstants.OBJ_TYPE_UNKNOWN
			&& objType != WCMConstants.OBJ_TYPE_GRIDROW
			&& objType != WCMConstants.OBJ_TYPE_CELL
			&& objType != WCMConstants.OBJ_TYPE_TREENODE
			&& objType != WCMConstants.OBJ_TYPE_MYFLOWDOCLIST
			&& objType != WCMConstants.OBJ_TYPE_MYMSGLIST
			&& objType != WCMConstants.OBJ_TYPE_TRSSERVERLIST
			&& objType != WCMConstants.OBJ_TYPE_MAINPAGE;
	},
	privateMe : function(type){
		this.objType = type;
		this.isCMSObjType = function(){return false;}
		return this;
	},
	getAt : function(index){
		return this;
	},
	getIds : function(){
		return [this.getId()];
	},
	getObjs : function(){
		return [this];
	},
	length : function(){
		return 1;
	},
	size : function(){
		return this.length();
	},
	isCMSObjs : function(){
		return false;
	},
	getType : function(){
		return this.objType;
	},
	getTypeName : function(){
		return this.objType;
	},
	getIntType : function(){
		switch(this.objType.toUpperCase()){
			case WCMConstants.OBJ_TYPE_DOCUMENT.toUpperCase():
			case WCMConstants.OBJ_TYPE_CHNLDOC.toUpperCase():
				return 605;
			case WCMConstants.OBJ_TYPE_CHANNEL.toUpperCase():
			case WCMConstants.OBJ_TYPE_CHANNELMASTER.toUpperCase():
				return 101;
			case WCMConstants.OBJ_TYPE_WEBSITE.toUpperCase():
				return 103;
			case WCMConstants.OBJ_TYPE_WEBSITEROOT.toUpperCase():
				return 1;
		}
		return -1;
	},
	getId : function(){
		return this.objId;
	},
	getProperties : function(){
		return this.properties;
	},
	getProperty : function(_sPropName, _sDefault){
		return this.properties[_sPropName.toUpperCase()] || _sDefault || null;
	},
	getPropertyAsString : function(_sPropName, _sDefault){
		return this.properties[_sPropName.toUpperCase()] || _sDefault || '';
	},
	getPropertyAsInt : function(_sPropName, _nDefault){
		return parseInt(this.properties[_sPropName.toUpperCase()] || (''+ _nDefault), 10);
	},
	getClass : function(){
		return CMSObj.getRegistered(this.objType);
	},
	toString : function(){
		return this.getTypeName() + "[ID=" + this.getId() + "]";
	}
});
wcm.CMSObjs = function(_config, _context){
	wcm.CMSObjs.superclass.constructor.call(this, _config, _context);
	this.objId = 0;
	this.objType = this.objType || _config.objType;
	this.m_objs = [];
	this.addElement(_config.items);
}
Ext.extend(wcm.CMSObjs, wcm.CMSObj, {
	createElement : function(info){
		Ext.applyIf(info, {
			objType : this.objType
		});
		return CMSObj.createFrom(info, this.context)
	},
	isCMSObjs : function(){
		return true;
	},
	length : function(){
		return this.m_objs.length;
	},
	addElement : function(_oCmsObjs){
		if(_oCmsObjs==null)return;
		if(Ext.isArray(_oCmsObjs)){
			for(var i=0,n=_oCmsObjs.length;i<n;i++){
				this.m_objs.push(this.createElement(_oCmsObjs[i]));
			}
			return;
		}
		this.m_objs.push(this.createElement(_oCmsObjs));
		return this;
	},
	getAt : function(index){
		return this.m_objs[index];
	},
	remove : function(cmsobj){
		if(cmsobj.getType()!=this.getType())return cmsobj;
		var result = [];
		for(var i=0,n=this.m_objs.length;i<n;i++){
			if(this.m_objs[i].getId()!=cmsobj.getId()){
				result.push(this.m_objs[i]);
			}
		}
		this.m_objs = result;
		return cmsobj;
	},
	removeAt : function(index){
		var cmsobj = this.m_objs[index];
		this.m_objs.splice(index, 1);
		return cmsobj;
	},
	getIds : function(){
		var rstIds = [];
		for(var i=0,n=this.m_objs.length;i<n;i++){
			rstIds.push(this.m_objs[i].getId());
		}
		return rstIds;
	},
	getProperty : function(_sPropName, _sDefault){
		var rst = [];
		for(var i=0,n=this.m_objs.length;i<n;i++){
			rst.push(this.m_objs[i].getProperty(_sPropName, _sDefault));
		}
		return rst;
	},
	getPropertys : function(_sPropName, _sDefault){
		var rst = [];
		for(var i=0,n=this.m_objs.length;i<n;i++){
			rst.push(this.m_objs[i].getProperty(_sPropName, _sDefault));
		}
		return rst;
	},
	getPropertyAsString : function(_sPropName, _sDefault){
		var rst = [];
		for(var i=0,n=this.m_objs.length;i<n;i++){
			rst.push(this.m_objs[i].getPropertyAsString(_sPropName, _sDefault));
		}
		return rst;
	},
	getPropertyAsInt : function(_sPropName, _nDefault){
		var rst = [];
		for(var i=0,n=this.m_objs.length;i<n;i++){
			rst.push(this.m_objs[i].getPropertyAsInt(_sPropName, _nDefault));
		}
		return rst;
	},
	getObjs : function(){
		return this.m_objs;
	},
	toString : function(){
		var arr = [];
		for(var i=0,n=this.m_objs.length;i<n;i++){
			arr.push(this.m_objs[i].toString());
		}
		return this.getTypeName() + "[objs=" + arr.join() + "]";
	}
});
(function(){
	var __extendClasses = {};
	Ext.apply(CMSObj, {
		register : function(_sClassType, _fCons){
			__extendClasses[_sClassType] = _fCons;
		},
		getRegistered : function(_sClassType){
			return __extendClasses[_sClassType];
		},
		createFrom : function(info, _context){
			var sClass = CMSObj.getRegistered(info.objType);
			var fClass = null;
			try{
				if(sClass!=null && (fClass=eval(sClass))){
					return new fClass(info, _context);
				}
			}catch(err){
				//Just skip it.
			}
			return new wcm.CMSObj(info, _context);
		},
		createEnumsFrom : function(info, _context){
			var sClass = CMSObj.getRegistered(info.objType);
			var fClass = null;
			try{
				if(sClass!=null && (fClass=eval(sClass+'s'))){
					return new fClass(info, _context);
				}
			}catch(err){
				//Just skip it.
			}
			return new wcm.CMSObjs(info, _context);
		}
	});
})();
Ext.apply(CMSObj, {
	newObj : function(info, wcmEvent){
		info = Ext.apply({
			objType : wcmEvent.objs.getType()
		}, info);
		return CMSObj.createFrom(info, wcmEvent.context);
	},
	afteradd : function(event){
		return function(objInfo){
			if(Ext.isSimpType(objInfo)){
				objInfo = {objId : objInfo};
			}else if(Ext.isTrans(objInfo)){
				objInfo = {objId : Ext.result(objInfo)};
			}
			objInfo = Ext.applyIf(objInfo || {}, {objId : 0, objType : event.objs.getType()});
			var cmsobj = CMSObj.createFrom(objInfo, event.context);
			cmsobj.afteradd();
		}
	},
	afteredit : function(event){
		return function(){
			event.getObjsOrHost().afteredit();
		}
	},
	afterdelete : function(event){
		return function(){
			event.getObjsOrHost().afterdelete();
		}
	}
});
/*main page*/
Ext.ns('wcm.MainPage');
wcm.MainPage = function(info, context){
	this.objType = WCMConstants.OBJ_TYPE_MAINPAGE;
	wcm.MainPage.superclass.constructor.call(this, info, context);
	this.addEvents(['beforeinit', 'afterinit', 'destroy', 'afterdestroy',
		'afteredit', 'refresh', 'afterrefresh', 'operpanelhide', 'operpanelshow', 'redirect', 'contextmenu']);
}
Ext.extend(wcm.MainPage, wcm.CMSObj);
CMSObj.register(WCMConstants.OBJ_TYPE_MAINPAGE, 'wcm.MainPage');
/*curr page*/
Ext.ns('wcm.CurrPage');
WCMConstants.OBJ_TYPE_CURRPAGE = 'CurrPage_' + wcm.getMyFlag();
wcm.CurrPage = function(info, context){
	this.objType = WCMConstants.OBJ_TYPE_CURRPAGE;
	wcm.CurrPage.superclass.constructor.call(this, info, context);
	this.addEvents(['beforeinit', 'afterinit', 'destroy', 'afterdestroy',
		'afteredit', 'refresh', 'afterrefresh', 'redirect', 'contextmenu']);
}
Ext.extend(wcm.CurrPage, wcm.CMSObj);
CMSObj.register(WCMConstants.OBJ_TYPE_CURRPAGE, 'wcm.CurrPage');
/*main page*/
Ext.ns('wcm.SystemObj');
wcm.SystemObj = function(info, context){
	this.objType = WCMConstants.OBJ_TYPE_SYSTEM;
	wcm.SystemObj.superclass.constructor.call(this, info, context);
	this.addEvents(['onkeydown']);
}
Ext.extend(wcm.SystemObj, wcm.CMSObj);
CMSObj.register(WCMConstants.OBJ_TYPE_SYSTEM, 'wcm.SystemObj');
/*extend MsgCenter*/
(function(){
	$MsgCenter.$main = function(context){
		var info = {
			objId : 0,
			href : location.href,
			params : Ext.Json.toUpperCase(location.search.parseQuery())
		};
		return new wcm.MainPage(info, context || window.mainContext);
	}
	$MsgCenter.$currPage = function(context){
		var info = {
			objId : 0,
			href : location.href,
			params : Ext.Json.toUpperCase(location.search.parseQuery())
		};
		return new wcm.CurrPage(info, context);
	};
})();