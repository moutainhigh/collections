//热词管理对象定义
Ext.ns('wcm.ChannelContentLinks', 'wcm.ChannelContentLink');
WCMConstants.OBJ_TYPE_CHANNELCONTENTLINK = 'ChannelContentLink';
wcm.ChannelContentLinks = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_CHANNELCONTENTLINK;
	wcm.ChannelContentLinks.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.ChannelContentLinks, wcm.CMSObjs, {
});
wcm.ChannelContentLink = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_CHANNELCONTENTLINK;
	wcm.ChannelContentLink.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_CHANNELCONTENTLINK, 'wcm.ChannelContentLink');
Ext.extend(wcm.ChannelContentLink, wcm.CMSObj, {
});