//快捷键
//KeyProvider
PageContext.keyProvider = {};
PageContext.prepareKeyProvider = function(){
	if(!wcm.SysOpers)return PageContext.keyProvider;
	var registerKeys = wcm.SysOpers.getQuickKeys();
	var execOper = function(event){
		//排除小键盘的一些事件
		if(event.keyCode >= 97 && event.keyCode <= 122){
			return;
		}
		var wcmEvent = PageContext.event;
		var c = String.fromCharCode(event.keyCode);
		if(event.keyCode==Event.KEY_DELETE && event.shiftKey){
			c = 'Shiftdelete';
		}
		else if(event.keyCode==Event.KEY_DELETE){
			c = 'Delete';
		}
		try{
			var oper = wcm.SysOpers.getOperByQuickKey(wcmEvent, c)
				|| wcm.SysOpers.getOperByQuickKey(wcmEvent, c, {
						right : wcmEvent.getHost().right,
						type : PageContext.relateType || wcmEvent.getContext().relateType
					});
		}catch(error){
			//just skip it.
		}
		if(!oper)return;
		wcmEvent.browserEvent = event;
		wcm.SysOpers.exec(oper, wcmEvent);
		wcmEvent.browserEvent = null;
	}
	for(var key in registerKeys){
		PageContext.keyProvider['key'+key.camelize()] = execOper;
	}
	return PageContext.keyProvider;
};