//替换内容对象定义
Ext.ns('wcm.Replaces', 'wcm.Replace');
WCMConstants.OBJ_TYPE_REPLACE = 'Replace';
wcm.Replaces = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_REPLACE;
	wcm.Replaces.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Replaces, wcm.CMSObjs, {
});
wcm.Replace = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_REPLACE;
	wcm.Replace.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_REPLACE, 'wcm.Replace');
Ext.extend(wcm.Replace, wcm.CMSObj, {
	getIntType : function(){
		return 105;
	}
});