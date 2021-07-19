//文档同步对象定义
Ext.ns('wcm.ChannelSynCols', 'wcm.ChannelSynCol');
WCMConstants.OBJ_TYPE_CHANNELSYNCOL = 'ChannelSynCol';
wcm.ChannelSynCols = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_CHANNELSYNCOL;
	wcm.ChannelSynCols.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.ChannelSynCols, wcm.CMSObjs, {
});
wcm.ChannelSynCol = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_CHANNELSYNCOL;
	wcm.ChannelSynCol.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_CHANNELSYNCOL, 'wcm.ChannelSynCol');
Ext.extend(wcm.ChannelSynCol, wcm.CMSObj, {
});