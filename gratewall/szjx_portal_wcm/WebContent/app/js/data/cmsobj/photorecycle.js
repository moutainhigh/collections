//废稿箱对象定义
Ext.ns('wcm.photorecycles', 'wcm.photorecycle');
WCMConstants.OBJ_TYPE_PHOTORECYCLE = 'photorecycle';
wcm.photorecycles = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_PHOTORECYCLE;
	wcm.photorecycles.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.photorecycles, wcm.CMSObjs, {
});
wcm.photorecycle = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_PHOTORECYCLE;
	wcm.photorecycle.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_PHOTORECYCLE, 'wcm.photorecycle');
Ext.extend(wcm.photorecycle, wcm.CMSObj, {
});