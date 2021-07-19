//工作流对象定义
Ext.ns('wcm.Flows', 'wcm.Flow');
WCMConstants.OBJ_TYPE_FLOW = 'flow';
wcm.Flows = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_FLOW;
	wcm.Flows.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Flows, wcm.CMSObjs, {
});
wcm.Flow = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_FLOW;
	wcm.Flow.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_FLOW, 'wcm.Flow');
Ext.extend(wcm.Flow, wcm.CMSObj, {
	getIntType : function(){
		return 401;
	}
});