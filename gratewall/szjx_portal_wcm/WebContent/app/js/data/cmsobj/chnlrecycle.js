//栏目回收站对象定义
Ext.ns('wcm.Chnlrecycles', 'wcm.Chnlrecycle');
WCMConstants.OBJ_TYPE_CHNLRECYCLE = 'Chnlrecycle';
wcm.Chnlrecycles = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_CHNLRECYCLE;
	wcm.Chnlrecycles.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Chnlrecycles, wcm.CMSObjs, {
});
wcm.Chnlrecycle = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_CHNLRECYCLE;
	wcm.Chnlrecycle.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_CHNLRECYCLE, 'wcm.Chnlrecycle');
Ext.extend(wcm.Chnlrecycle, wcm.CMSObj, {
});