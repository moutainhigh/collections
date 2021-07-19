//视图字段对象定义
Ext.ns('wcm.MetaViewFields', 'wcm.MetaViewField');
WCMConstants.OBJ_TYPE_METAVIEWFIELD = 'metaviewfield';
wcm.MetaViewFields = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_METAVIEWFIELD;
	wcm.MetaViewFields.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.MetaViewFields, wcm.CMSObjs, {
});
wcm.MetaViewField = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_METAVIEWFIELD;
	wcm.MetaViewField.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_METAVIEWFIELD, 'wcm.MetaViewField');
Ext.extend(wcm.MetaViewField, wcm.CMSObj, {
});