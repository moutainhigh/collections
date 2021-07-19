//流转文档对象定义
Ext.ns('wcm.TRSServers', 'wcm.TRSServer');
WCMConstants.OBJ_TYPE_TRSSERVER = 'trsServer';
wcm.TRSServers = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_TRSSERVER;
	wcm.TRSServers.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.TRSServers, wcm.CMSObjs, {
});
wcm.TRSServer = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_TRSSERVER;
	wcm.TRSServer.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_TRSSERVER, 'wcm.TRSServer');
Ext.extend(wcm.TRSServer, wcm.CMSObj, {
});