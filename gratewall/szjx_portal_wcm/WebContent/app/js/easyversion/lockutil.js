//解锁
var LockerUtil = {
	unlock : function (_nObjId, _nObjType, _fAfterFail){
		var params = {
			"ObjId":_nObjId,
			"ObjType":_nObjType,
			"ActionFlag":"false"
		};	
		var r = new ajaxRequest({
			url : WCMConstants.WCM6_PATH + "include/cmsobject_locked.jsp",
			method : 'get', 
			asyn : false, 
			parameters : $toQueryStr(params)
		});
		this.AfterFail = _fAfterFail;
		eval(r.responseText);
		return true;
	},
	unlockCallback : function(json){
		try{
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
	notLogin : window.DoNotLogin || Ext.emptyFn
};