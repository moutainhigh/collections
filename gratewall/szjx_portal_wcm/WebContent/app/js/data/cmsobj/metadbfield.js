//元数据对象定义
Ext.ns('wcm.MetaDBFields', 'wcm.MetaDBField');
WCMConstants.OBJ_TYPE_METADBFIELD = 'MetaDBField';
wcm.MetaDBFields = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_METADBFIELD;
	wcm.MetaDBFields.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.MetaDBFields, wcm.CMSObjs, {
});
wcm.MetaDBField = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_METADBFIELD;
	wcm.MetaDBField.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_METADBFIELD, 'wcm.MetaDBField');
Ext.extend(wcm.MetaDBField, wcm.CMSObj, {
});