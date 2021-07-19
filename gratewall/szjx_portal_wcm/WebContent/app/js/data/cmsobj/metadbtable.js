//元数据对象定义
Ext.ns('wcm.MetaDBTables', 'wcm.MetaDBTable');
WCMConstants.OBJ_TYPE_METADBTABLE = 'MetaDBTable';
wcm.MetaDBTables = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_METADBTABLE;
	wcm.MetaDBTables.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.MetaDBTables, wcm.CMSObjs, {
});
wcm.MetaDBTable = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_METADBTABLE;
	wcm.MetaDBTable.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_METADBTABLE, 'wcm.MetaDBTable');
Ext.extend(wcm.MetaDBTable, wcm.CMSObj, {
});