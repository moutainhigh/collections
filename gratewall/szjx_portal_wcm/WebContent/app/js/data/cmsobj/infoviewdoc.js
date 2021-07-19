//表单文档对象定义
Ext.ns('wcm.InfoviewDocs', 'wcm.InfoviewDoc');
WCMConstants.OBJ_TYPE_INFOVIEWDOC = 'infoviewdoc';
wcm.InfoviewDocs = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_INFOVIEWDOC;
	wcm.InfoviewDocs.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.InfoviewDocs, wcm.CMSObjs, {
});
wcm.InfoviewDoc = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_INFOVIEWDOC;
	wcm.InfoviewDoc.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_INFOVIEWDOC, 'wcm.InfoviewDoc');
Ext.extend(wcm.InfoviewDoc, wcm.CMSObj, {
});