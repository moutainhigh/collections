/*
 *	对象锁定工具类
 *
 *	History			Who			What
 *	2006-11-24		wenyh		created.
 *
 */
$import('com.trs.logger.Logger');
function ObjLockerUtil(){
	this.url = "../include/cmsobject_locked.jsp";	
}

/*!
 *	根据ObjectId及ObjectType锁定特定对象
 *	注意解锁
 *
 *	锁定成功返回true
 */
ObjLockerUtil.prototype.lock = function (_nObjId, _nObjType, _fAfterFail){
	if(!(_nObjId && _nObjType)){
		alert("无效的参数！");
		return false;
	}

	var params = {"ObjId":_nObjId,"ObjType":_nObjType,"ActionFlag":"true"};	
	
	var ajaxRequest = new Ajax.Request(
		this.url,
		{method:'get', asynchronous:false, parameters:$toQueryStr(params)}
	);
	
	var isNotLogin = ajaxRequest.header('TRSNotLogin');
	if(isNotLogin=='true'&&window.top.DoNotLogin){
		window.top.DoNotLogin();
		return;
	}

	eval("var json=" + ajaxRequest.transport.responseText);
	if(json.Result == "false"){
		LockerUtil.failedToLock = true;
		var func = _fAfterFail;
		if(func && typeof(func) == 'function') {
			func(json.Message, json);
		}else{
			$timeAlert('<b>提示：</b>' + json.Message, 5, null, null, 3);
		}
		return false;
	}
	LockerUtil.failedToLock = false;
	return true;
}


/*!
 *	根据ObjectId及ObjectType解除特定对象的锁定 
 *
 *	解锁成功返回true
 */
ObjLockerUtil.prototype.unlock = function (_nObjId, _nObjType, _fAfterFail){
	if(!(_nObjId && _nObjType)){
		alert("无效的参数！");
		return false;
	}

	var params = {"ObjId":_nObjId,"ObjType":_nObjType,"ActionFlag":"false"};	

	var ajaxRequest = new Ajax.Request(
		this.url,
		{method:'get', asynchronous:false, parameters:$toQueryStr(params)}
	);

	var isNotLogin = ajaxRequest.header('TRSNotLogin');
	if(isNotLogin=='true'&&window.top.DoNotLogin){
		window.top.DoNotLogin();
		return;
	}
	try{
		eval("var json=" + ajaxRequest.transport.responseText);	
		if(json.Result == "false"){
			var func = _fAfterFail;
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
}

var LockerUtil = new ObjLockerUtil();
//LockerUtil.url = "http://192.9.200.37:7070/wcm/WCMV6/include/cmsobject_locked.jsp"	//for test.

LockerUtil.register = function(_nObjId, _nObjType, _bUnlockWhenHide, _doOnFail, _doLockAtOnce){
	_nObjId = parseInt(_nObjId);
	_nObjType = parseInt(_nObjType);
	// 当加锁/解锁失败的时候，需要做的事情（复写默认的alert提示）
	// failToLock: fun, failToUnlock: fun
	var info = _doOnFail || {}; 
	if(isNaN(_nObjId) || isNaN(_nObjType) || _nObjId == 0)
		return;
	//else
	if(_doLockAtOnce == true) {
		LockerUtil.lock(_nObjId, _nObjType, info.failToLock);
	}else{
		Event.observe(window, 'load', function(){
			LockerUtil.lock(_nObjId, _nObjType, info.failToLock);
		});
	}
	

	if(_bUnlockWhenHide != true || FloatPanel == null) { // 直到页面unload的时候才 unlock
		Event.observe(window, 'unload', function(){
			LockerUtil.unlock(_nObjId, _nObjType, info.failToUnlock);
		});
	}else{ // 关闭floatPanel的时候就 unlock
		FloatPanel.setAfterClose(function(){
			LockerUtil.unlock(_nObjId, _nObjType, info.failToUnlock);
		});
	}
}

LockerUtil.render = function(_nObjId, _nObjType, _bUnlockWhenHide, _doOnFail){
	LockerUtil.register(_nObjId, _nObjType, _bUnlockWhenHide, _doOnFail, true)
}

LockerUtil.register2 = function(_nObjId, _nObjType, _bUnlockWhenHide, _sBtn2Disable){
	if(FloatPanel == null) {
		alert('[LockerUtil.register2]方法只能在[FloatPanel]页面调用，其他使用场景请使用[LockerUtil.register]或[LockerUtil.render]方法！');
		return;
	}
	var doOnFail = {
		failToLock : function(_msg, _json){
			try{
				$timeAlert('<b>提示：</b>' + _msg, 5, function(){
					$dialog().hide();
					FloatPanel.disableCommand(_sBtn2Disable, true, true);
				}, null, 3);
			}catch(error){
				FloatPanel.disableCommand(_sBtn2Disable, true, true);
			}
		}
	}
	LockerUtil.register(_nObjId, _nObjType, _bUnlockWhenHide, doOnFail)
}