//视频对象定义
Ext.ns('wcm.Videos', 'wcm.Video');
WCMConstants.OBJ_TYPE_VIDEO = 'video';
wcm.Videos = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_VIDEO;
	wcm.Videos.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Videos, wcm.CMSObjs, {
});
wcm.Video = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_VIDEO;
	wcm.Video.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_VIDEO, 'wcm.Video');
Ext.extend(wcm.Video, wcm.CMSObj, {
});