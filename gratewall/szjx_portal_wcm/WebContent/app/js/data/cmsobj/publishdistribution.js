//站点分发对象定义
Ext.ns('wcm.PublishDistributions', 'wcm.PublishDistribution');
WCMConstants.OBJ_TYPE_PUBLISHDISTRIBUTION = 'PublishDistribution';
wcm.PublishDistributions = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_PUBLISHDISTRIBUTION;
	wcm.PublishDistributions.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.PublishDistributions, wcm.CMSObjs, {
});
wcm.PublishDistribution = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_PUBLISHDISTRIBUTION;
	wcm.PublishDistribution.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_PUBLISHDISTRIBUTION, 'wcm.PublishDistribution');
Ext.extend(wcm.PublishDistribution, wcm.CMSObj, {
});