//文档同步对象定义
Ext.ns('wcm.ChannelSyns', 'wcm.ChannelSyn');
WCMConstants.OBJ_TYPE_CHANNELSYN = 'ChannelSyn';
wcm.ChannelSyns = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_CHANNELSYN;
	wcm.ChannelSyns.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.ChannelSyns, wcm.CMSObjs, {
});
wcm.ChannelSyn = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_CHANNELSYN;
	wcm.ChannelSyn.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_CHANNELSYN, 'wcm.ChannelSyn');
Ext.extend(wcm.ChannelSyn, wcm.CMSObj, {
});