//工作流对象定义
Ext.ns('wcm.Regions', 'wcm.Region');
WCMConstants.OBJ_TYPE_REGION = 'region';
wcm.Flows = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_FLOW;
	wcm.Regions.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Regions, wcm.CMSObjs, {
});
wcm.Region = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_REGION;
	wcm.Region.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_REGION, 'wcm.Region');
Ext.extend(wcm.Region, wcm.CMSObj, { 
});