//版本管理对象定义
Ext.ns('wcm.DocBaks', 'wcm.DocBak');
WCMConstants.OBJ_TYPE_DOCBAK = 'DocBak';
wcm.DocBaks = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_DOCBAK;
	wcm.DocBaks.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.DocBaks, wcm.CMSObjs, {
});
wcm.DocBak = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_DOCBAK;
	wcm.DocBak.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_DOCBAK, 'wcm.DocBak');
Ext.extend(wcm.DocBak, wcm.CMSObj, {
});