//资源对象定义
Ext.ns('wcm.MetaViewDatas', 'wcm.MetaViewData');
WCMConstants.OBJ_TYPE_METAVIEWDATA = 'MetaViewData';
wcm.MetaViewDatas = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_METAVIEWDATA;
	wcm.MetaViewDatas.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.MetaViewDatas, wcm.CMSObjs, {
});
wcm.MetaViewData = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_METAVIEWDATA;
	wcm.MetaViewData.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_METAVIEWDATA, 'wcm.MetaViewData');
Ext.extend(wcm.MetaViewData, wcm.CMSObj, {
});