//站点对象定义
Ext.ns('wcm.WebSites', 'wcm.WebSite');
WCMConstants.OBJ_TYPE_WEBSITE = 'website';
wcm.WebSites = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_WEBSITE;
	wcm.WebSites.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.WebSites, wcm.CMSObjs, {
});
wcm.WebSite = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_WEBSITE;
	wcm.WebSite.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_WEBSITE, 'wcm.WebSite');
Ext.extend(wcm.WebSite, wcm.CMSObj, {
});