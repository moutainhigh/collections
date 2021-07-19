//视图对象定义
Ext.ns('wcm.MetaViews', 'wcm.MetaView');
WCMConstants.OBJ_TYPE_METAVIEW = 'MetaView';
wcm.MetaViews = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_METAVIEW;
	wcm.MetaViews.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.MetaViews, wcm.CMSObjs, {
});
wcm.MetaView = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_METAVIEW;
	wcm.MetaView.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_METAVIEW, 'wcm.MetaView');
Ext.extend(wcm.MetaView, wcm.CMSObj, {
});