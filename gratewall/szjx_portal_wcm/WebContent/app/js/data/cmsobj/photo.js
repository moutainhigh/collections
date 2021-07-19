//图片对象定义
Ext.ns('wcm.photos', 'wcm.photo');
WCMConstants.OBJ_TYPE_PHOTO = 'photo';
wcm.photos = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_PHOTO;
	wcm.photos.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.photos, wcm.CMSObjs, {
});
wcm.photo = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_PHOTO;
	wcm.photo.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_PHOTO, 'wcm.photo');
Ext.extend(wcm.photo, wcm.CMSObj, {
	getDocId : function(){
		return this.getPropertyAsInt("DocId", 0);
	}
});