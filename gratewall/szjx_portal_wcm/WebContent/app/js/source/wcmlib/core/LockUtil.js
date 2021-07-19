//对象锁定工具类
function ObjLockerUtil(){
	this.url = WCMConstants.WCM6_PATH + "include/cmsobject_locked.jsp";	
}
Ext.extend(ObjLockerUtil, Object, {
	lock : function(_nObjId, _nObjType, _fAfterFail){
		if(!(_nObjId && _nObjType)){
			Ext.Msg.alert(wcm.LANG['INVALID_PARAM'] || "无效的参数！");
			return false;
		}
		var params = {
			"ObjId":_nObjId,
			"ObjType":_nObjType,
			"ActionFlag":"true"
		};	
		var ajaxRequest = new Ajax.Request(this.url,{
			method:'get', 
			asynchronous:false, 
			parameters:$toQueryStr(params)
		});
		this.AfterFail = _fAfterFail;
		eval(ajaxRequest.transport.responseText);
		return !this.failedToLock;
	},
	lockCallback : function(json){
		if(json.Result == "false"){
			this.failedToLock = true;
			var func = this.AfterFail;
			if(func && typeof(func) == 'function') {
				func(json.Message, json);
			}else{
				Ext.Msg.$timeAlert('<b>' + (wcm.LANG['TIPS'] || '提示：') + '</b>' + json.Message, 5);
			}
			return false;
		}
		this.failedToLock = false;
		return true;
	},
	unlock : function (_nObjId, _nObjType, _fAfterFail){
		if(!(_nObjId && _nObjType)){
			alert(wcm.LANG['INVALID_PARAM'] || "无效的参数！");
			return false;
		}
		var params = {
			"ObjId":_nObjId,
			"ObjType":_nObjType,
			"ActionFlag":"false"
		};	
		var ajaxRequest = new Ajax.Request(this.url, {
			method:'get', 
			asynchronous:false, 
			parameters:$toQueryStr(params)
		});
		this.AfterFail = _fAfterFail;
		eval(ajaxRequest.transport.responseText);
		return true;
	},
	unlockCallback : function(json){
		try{
			eval("var json=" + ajaxRequest.transport.responseText);	
			if(json.Result == "false"){
				var func = this.AfterFail;
				if(func && typeof(func) == 'function') {
					func(json.Message, json);
				}else{
					// do nothin'
				}
				return false;
			}
		}catch(err){
			//do nothing
		}
		return true;
	},
	register : function(_nObjId, _nObjType, _bUnlockWhenHide, _doOnFail, _doLockAtOnce){
		_nObjId = parseInt(_nObjId, 10);
		_nObjType = parseInt(_nObjType, 10);
		// 当加锁/解锁失败的时候，需要做的事情（复写默认的alert提示）
		// failToLock: fun, failToUnlock: fun
		var info = _doOnFail || {}; 
		if(isNaN(_nObjId) || isNaN(_nObjType) || _nObjId == 0)
			return;
		//else
		if(_doLockAtOnce == true) {
			this.lock(_nObjId, _nObjType, info.failToLock);
		}else{
			Event.observe(window, 'load', function(){
				LockerUtil.lock(_nObjId, _nObjType, info.failToLock);
			});
		}

		Event.observe(window, 'unload', function(){
			LockerUtil.unlock(_nObjId, _nObjType, info.failToUnlock);
		});
	},
	render : function(_nObjId, _nObjType, _bUnlockWhenHide, _doOnFail){
		this.register(_nObjId, _nObjType, _bUnlockWhenHide, _doOnFail, true);
	},
	register2 : function(_nObjId, _nObjType, _bUnlockWhenHide, _sBtn2Disable){
		if(window.FloatPanel == null) {
			this.register(_nObjId, _nObjType, _bUnlockWhenHide, null);
			return;
		}
		var doOnFail = {
			failToLock : function(_msg, _json){
				try{
					FloatPanel.disableCommand(_sBtn2Disable, true, true);
					//Ext.Msg.timeAlert
					Ext.Msg.$timeAlert('<b>' + (wcm.LANG['TIPS'] || '提示：')+ '</b>' + _msg, 5);
				}catch(error){
					FloatPanel.disableCommand(_sBtn2Disable, true, true);
				}
			}
		}
		this.register(_nObjId, _nObjType, _bUnlockWhenHide, doOnFail)
	},
	notLogin : window.DoNotLogin || Ext.emptyFn
});

var LockerUtil = new ObjLockerUtil();
