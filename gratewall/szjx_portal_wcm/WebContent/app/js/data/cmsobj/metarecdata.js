//版本管理对象定义
Ext.ns('wcm.MetaRecDatas', 'wcm.MetaRecData');
WCMConstants.OBJ_TYPE_METARECDATA = 'MetaRecData';
wcm.MetaRecDatas = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_METARECDATA;
	wcm.MetaRecDatas.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.MetaRecDatas, wcm.CMSObjs, {
});
wcm.MetaRecData = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_METARECDATA;
	wcm.MetaRecData.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_METARECDATA, 'wcm.MetaRecData');
Ext.extend(wcm.MetaRecData, wcm.CMSObj, {
});