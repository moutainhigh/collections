//默认排版设置在栏目上的操作信息和Mgr定义
Ext.ns('wcm.domain.DocumentMgr');
(function(){
	//add and edit operators.
	Ext.apply(wcm.domain.DocumentMgr, {
		defaultformat : function(event){
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + 'editor/defaultformat/read.jsp?ObjId=' + event.getHost().getId() + "&ObjType=" + event.getHost().getIntType(),
				title : '默认排版设置',
				callback : function(){
					CMSObj[oParams["objectid"] > 0 ? 'afteredit' : 'afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CHANNEL, objId:objId});
				}
			});
		}
	});
})();
(function(){
	var pageObjMgr = wcm.domain.DocumentMgr;
	var reg = wcm.SysOpers.register;
	reg({
		key : 'defaultformat',
		type : 'documentInChannel',
		desc : '默认排版设置',
		title : '默认排版设置的相关配置',
		rightIndex : 13,
		order : 99,
		fn : pageObjMgr['defaultformat']
	});
})();
