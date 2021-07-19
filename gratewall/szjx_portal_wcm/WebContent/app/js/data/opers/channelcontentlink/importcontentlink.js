//Mgr定义
Ext.ns('wcm.domain.ChannelContentLinkMgr');
(function (){
	Ext.apply(wcm.domain.ChannelContentLinkMgr, {
		createFromFile : function(event){
			var hostId = event.getHost().getId();
			var hostType = event.getHost().getIntType();
			var params = {
				parentid : hostId,
				objecttype : hostType
			}
			FloatPanel.open(WCMConstants.WCM6_PATH + 'channelcontentlink/channelcontentlink_create_fromfile.html?' + $toQueryStr(params),'批量创建',  
				CMSObj.afteradd(event)
			);
		}
	});
})();
(function(){
	var pageObjMgr = wcm.domain.ChannelContentLinkMgr;
	var reg = wcm.SysOpers.register;
	reg({
		key : 'createFromFile',
		type : 'channelcontentlinkInChannel',
		desc : '批量创建热词',
		title : '批量创建热词...',
		rightIndex : 13,
		order : 3.1,
		fn : pageObjMgr['createFromFile']
	});
		reg({
		key : 'createFromFile',
		type : 'channelcontentlinkInSite',
		desc : '批量创建热词',
		title : '批量创建热词...',
		rightIndex : 1,
		order : 3.1,
		fn : pageObjMgr['createFromFile']
	});
})();