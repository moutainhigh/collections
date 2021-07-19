//水印对象定义
Ext.ns('wcm.Watermarks', 'wcm.Watermark');
WCMConstants.OBJ_TYPE_WATERMARK = 'Watermark';
wcm.Watermarks = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_WATERMARK;
	wcm.Watermarks.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Watermarks, wcm.CMSObjs, {
});
wcm.Watermark = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_WATERMARK;
	wcm.Watermark.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_WATERMARK, 'wcm.Watermark');
Ext.extend(wcm.Watermark, wcm.CMSObj, {
});