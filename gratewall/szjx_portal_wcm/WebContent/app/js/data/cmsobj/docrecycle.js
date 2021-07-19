//废稿箱对象定义
Ext.ns('wcm.DocRecycles', 'wcm.DocRecycle');
WCMConstants.OBJ_TYPE_DOCRECYCLE = 'DocRecycle';
wcm.DocRecycles = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_DOCRECYCLE;
	wcm.DocRecycles.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.DocRecycles, wcm.CMSObjs, {
});
wcm.DocRecycle = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_DOCRECYCLE;
	wcm.DocRecycle.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_DOCRECYCLE, 'wcm.DocRecycle');
Ext.extend(wcm.DocRecycle, wcm.CMSObj, {
});