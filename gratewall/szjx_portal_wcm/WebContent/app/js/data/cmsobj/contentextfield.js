//扩展字段对象定义
Ext.ns('wcm.ContentExtFields', 'wcm.ContentExtField');
WCMConstants.OBJ_TYPE_CONTENTEXTFIELD = 'contentextfield';
wcm.ContentExtFields = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_CONTENTEXTFIELD;
	wcm.ContentExtFields.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.ContentExtFields, wcm.CMSObjs, {
});
wcm.ContentExtField = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_CONTENTEXTFIELD;
	wcm.ContentExtField.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_CONTENTEXTFIELD, 'wcm.ContentExtField');
Ext.extend(wcm.ContentExtField, wcm.CMSObj, {
});