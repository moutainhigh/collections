//站点回收站对象定义
Ext.ns('wcm.SiteRecycles', 'wcm.SiteRecycle');
WCMConstants.OBJ_TYPE_SITERECYCLE = 'SiteRecycle';
wcm.SiteRecycles = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_SITERECYCLE;
	wcm.SiteRecycles.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.SiteRecycles, wcm.CMSObjs, {
});
wcm.SiteRecycle = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_SITERECYCLE;
	wcm.SiteRecycle.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_SITERECYCLE, 'wcm.SiteRecycle');
Ext.extend(wcm.SiteRecycle, wcm.CMSObj, {
});