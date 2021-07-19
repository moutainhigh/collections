//默认排版设置在栏目上的操作信息和Mgr定义
Ext.ns('wcm.domain.DocumentMgr');
(function(){
	//add and edit operators.
	Ext.apply(wcm.domain.DocumentMgr, {
		docViewProp : function(event){
			var hostId = event.getHost().getId();
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + 'channel/document_props_viewset.jsp?WebsiteId='+hostId,
				title : '文档编辑页面属性视图定制',
				callback : function(){
					CMSObj.afteredit(event)({objType:WCMConstants.OBJ_TYPE_CHANNEL,objId:hostId});
				}
			});
		}
	});
})();
(function(){
	var pageObjMgr = wcm.domain.DocumentMgr;
	var reg = wcm.SysOpers.register;
	reg({
		key : 'docViewProp',
		type : 'documentInChannel',
		desc : '页面属性视图定制',
		title : '文档编辑页面属性视图定制...',
		rightIndex : 13,
		order : 99,
		fn : pageObjMgr['docViewProp']
	});
})();
