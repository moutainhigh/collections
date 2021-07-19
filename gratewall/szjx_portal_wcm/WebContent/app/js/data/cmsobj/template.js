//模板对象定义
Ext.ns('wcm.Templates', 'wcm.Template');
WCMConstants.OBJ_TYPE_TEMPLATE = 'template';
wcm.Templates = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_TEMPLATE;
	wcm.Templates.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Templates, wcm.CMSObjs, {
});
wcm.Template = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_TEMPLATE;
	wcm.Template.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_TEMPLATE, 'wcm.Template');
Ext.extend(wcm.Template, wcm.CMSObj, {
	getIntType : function(){
		return 102;
	}
});