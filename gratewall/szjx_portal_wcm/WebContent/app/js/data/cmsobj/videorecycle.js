//视频对象定义
Ext.ns('wcm.VideoRecycles', 'wcm.VideoRecycle');
WCMConstants.OBJ_TYPE_VIDEORECYCLE = 'videorecycle';
wcm.VideoRecycles = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_VIDEORECYCLE;
	wcm.VideoRecycles.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.VideoRecycles, wcm.CMSObjs, {
});
wcm.VideoRecycle = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_VIDEORECYCLE;
	wcm.VideoRecycle.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_VIDEORECYCLE, 'wcm.VideoRecycle');
Ext.extend(wcm.VideoRecycle, wcm.CMSObj, {
});