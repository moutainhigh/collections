//栏目对象定义
Ext.ns('wcm.Channels', 'wcm.Channel');
WCMConstants.OBJ_TYPE_CHANNEL = 'channel';
wcm.Channels = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_CHANNEL;
	wcm.Channels.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Channels, wcm.CMSObjs, {
});
wcm.Channel = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_CHANNEL;
	wcm.Channel.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_CHANNEL, 'wcm.Channel');
Ext.extend(wcm.Channel, wcm.CMSObj, {
});