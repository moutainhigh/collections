//短消息对象定义
Ext.ns('wcm.Messages', 'wcm.Message');
WCMConstants.OBJ_TYPE_MESSAGE = 'message';
wcm.Messages = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_MESSAGE;
	wcm.Messages.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Messages, wcm.CMSObjs, {
});
wcm.Message = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_MESSAGE;
	wcm.Message.superclass.constructor.call(this, _config, _context);
	var arrRights = {
		//TODO type rights here
		"view" : -1
	};
	for(rightName in arrRights){
		var cameRightName = rightName.camelize();
		var nRightIndex = parseInt(arrRights[rightName], 10);
		this['can'+cameRightName] = function(){
			return wcm.AuthServer.hasRight(this.right, nRightIndex);
		}
	}
}
CMSObj.register(WCMConstants.OBJ_TYPE_MESSAGE, 'wcm.Message');
Ext.extend(wcm.Message, wcm.CMSObj, {
});