//分类法对象定义
Ext.ns('wcm.classinfos', 'wcm.classinfo');
WCMConstants.OBJ_TYPE_CLASSINFO = 'classinfo';
wcm.classinfos = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_CLASSINFO;
	wcm.classinfos.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.classinfos, wcm.CMSObjs, {
});
wcm.classinfo = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_CLASSINFO;
	wcm.classinfo.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_CLASSINFO, 'wcm.classinfo');
Ext.extend(wcm.classinfo, wcm.CMSObj, {
});