//流转文档对象定义
Ext.ns('wcm.IFlowContents', 'wcm.IFlowContent');
WCMConstants.OBJ_TYPE_IFLOWCONTENT = 'IFlowContent';
wcm.IFlowContents = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_IFLOWCONTENT;
	wcm.IFlowContents.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.IFlowContents, wcm.CMSObjs, {
});
wcm.IFlowContent = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_IFLOWCONTENT;
	wcm.IFlowContent.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_IFLOWCONTENT, 'wcm.IFlowContent');
Ext.extend(wcm.IFlowContent, wcm.CMSObj, {
	getId : function(){
		return this.getPropertyAsInt('DocId', 0);
	}
});