//页面属性设置在站点文档的操作信息和Mgr定义
Ext.ns('wcm.domain.DocumentMgr');
(function(){
	//add and edit operators.
	Ext.apply(wcm.domain.DocumentMgr, {
		docPropSet : function(event){
			var hostId = event.getHost().getId();
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + 'website/document_props_showset.jsp?WebsiteId='+hostId,
				title : wcm.LANG.CHANNEL_75 ||'文档编辑页面属性定制',
				callback : function(){
					CMSObj.afteredit(event)({objType:WCMConstants.OBJ_TYPE_WEBSITE,objId:hostId});
				}
			});
		},
		docViewProp : function(event){
			var hostId = event.getHost().getId();
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + 'website/document_props_viewset.jsp?WebsiteId='+hostId,
				title : '文档编辑页面属性视图定制',
				callback : function(){
					CMSObj.afteredit(event)({objType:WCMConstants.OBJ_TYPE_WEBSITE,objId:hostId});
				}
			});
		}
	});
})();
(function(){
	var pageObjMgr = wcm.domain.DocumentMgr;
	var reg = wcm.SysOpers.register;
	reg({
		key : 'docPropSet',
		type : 'documentInSite',
		desc : wcm.LANG.CHANNEL_75 ||'文档编辑页面属性定制',
		title:'文档编辑页面属性定制...',
		rightIndex : 13,
		order : 501,
		fn : pageObjMgr['docPropSet']
	});

	reg({
		key : 'docViewProp',
		type : 'documentInSite',
		desc : '页面属性视图定制',
		title:'文档编辑页面属性视图定制...',
		rightIndex : 13,
		order : 502,
		fn : pageObjMgr['docViewProp']
	});
})();
