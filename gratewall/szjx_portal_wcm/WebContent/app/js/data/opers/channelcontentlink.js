//热词管理操作信息和Mgr定义
Ext.ns('wcm.domain.ChannelContentLinkMgr');
(function(){
	var m_oMgr = wcm.domain.ChannelContentLinkMgr;
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}
	Ext.apply(wcm.domain.ChannelContentLinkMgr, {
		/**
		 * @param : wcm.CMSObjEvent event
		 */
		add : function(event){
			var oPageParams = event.getContext();
			var bInChannel = event.getHost().getType() == "website" ? false : true;
			Object.extend(oPageParams,{"ObjectId":0});
			Object.extend(oPageParams,{"ChannelId": bInChannel ? event.getHost().getId() : 0});
			Object.extend(oPageParams,{"SiteId": bInChannel ? 0:event.getHost().getId()});
			Object.extend(oPageParams,{"ContainsSite": PageContext.params["CONTAINSSITE"] ? 1:0});
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'channelcontentlink/channelcontentlink_add_edit.jsp?' + $toQueryStr(oPageParams),
					bInChannel ? (wcm.LANG.CHANNELCONTENTLINK_FN_2 || '新建栏目热词...') : (wcm.LANG.CHANNELCONTENTLINK_FN_31 || '新建站点热词'),
					CMSObj.afteradd(event)
			);
		},
		edit : function(event){
			var oPageParams = event.getContext();
			var bInChannel = event.getHost().getType() == "website" ? false : true;
			var sId = event.getIds().join();
			Object.extend(oPageParams,{"ObjectId":sId});
			Object.extend(oPageParams,{"ChannelId": bInChannel ? event.getHost().getId() : 0});
			Object.extend(oPageParams,{"SiteId": bInChannel ? 0:event.getHost().getId()});
			Object.extend(oPageParams,{"ContainsSite": PageContext.params["CONTAINSSITE"] ? 1:0});
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'channelcontentlink/channelcontentlink_add_edit.jsp?' + $toQueryStr(oPageParams), bInChannel ? (wcm.LANG.CHANNELCONTENTLINK_FN_3 || '修改栏目热词'):(wcm.LANG.CHANNELCONTENTLINK_FN_33 || '修改站点热词'), CMSObj.afteredit(event)
			);
		},
		set : function(event){
			var oPageParams = event.getContext();
			var bInChannel = event.getHost().getType() == "website" ? false : true;
			Object.extend(oPageParams,{"ChannelId": bInChannel ? event.getHost().getId() : 0});
			Object.extend(oPageParams,{"SiteId": bInChannel ? 0:event.getHost().getId()});
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'channelcontentlink/channelcontentlink_import_list.html?' + $toQueryStr(oPageParams), wcm.LANG.CHANNELCONTENTLINK_FN_4 || '按分类导入系统热词', CMSObj.afteradd(event)
			);
		},
		'delete' : function(event){	
			var oPageParams = {};
			//alert(Ext.toSource(oPageParams));
			var sId = event.getIds();

			var bInChannel = event.getHost().getType() == "website" ? false : true;
			Object.extend(oPageParams,{"ChannelId": bInChannel ? event.getHost().getId() : 0});
			Object.extend(oPageParams,{"SiteId": bInChannel ? 0:event.getHost().getId()});
			Ext.Msg.confirm("您确定要删除选定的热词?", {
                yes : function(){
                    Object.extend(oPageParams,{"ObjectId":sId,'ObjectIds':sId});
					BasicDataHelper.call('wcm6_contentlink',"delete",oPageParams,true,function(){
					event.getObjs().afterdelete();
					});
                }
            });
		}
		//*/
		//type here
	});
})();
(function(){
	var pageObjMgr = wcm.domain.ChannelContentLinkMgr;
	var reg = wcm.SysOpers.register;

	reg({
		key : 'edit',
		type : 'channelcontentlink',
		desc : wcm.LANG.CHANNELCONTENTLINK_FN_6 || '编辑这个热词',
		title : wcm.LANG.CHANNELCONTENTLINK_FN_6 || '编辑这个热词...',
		rightIndex : 13,
		order : 1,
		fn : pageObjMgr['edit'],
		quickKey : 'E'
	});
	reg({
		key : 'delete',
		type : 'channelcontentlink',
		desc : wcm.LANG.CHANNELCONTENTLINK_FN_7 || '删除选定热词',
		title : wcm.LANG.CHANNELCONTENTLINK_FN_7 || '删除选定热词...',
		rightIndex : 13,
		order : 2,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'add',
		type : 'channelcontentlinkInChannel',
		desc : wcm.LANG.CHANNELCONTENTLINK_FN_2 || '新建栏目热词',
		title : wcm.LANG.CHANNELCONTENTLINK_FN_2 || '新建栏目热词...',
		rightIndex : 13,
		order : 3,
		fn : pageObjMgr['add'],
		quickKey : 'N'
	});
	reg({
		key : 'add',
		type : 'channelcontentlinkInSite',
		desc : wcm.LANG.CHANNELCONTENTLINK_FN_31 || '新建站点热词',
		title : wcm.LANG.CHANNELCONTENTLINK_FN_31 || '新建站点热词',
		rightIndex : 1,
		order : 3,
		fn : pageObjMgr['add'],
		quickKey : 'N'
	});
	reg({
		key : 'set',
		type : 'channelcontentlinkInChannel',
		desc : wcm.LANG.CHANNELCONTENTLINK_FN_8 || '导入系统热词',
		title : wcm.LANG.CHANNELCONTENTLINK_FN_9 || '导入系统热词到栏目',
		rightIndex : 13,
		order : 4,
		fn : pageObjMgr['set']
	});
	reg({
		key : 'set',
		type : 'channelcontentlinkInSite',
		desc : wcm.LANG.CHANNELCONTENTLINK_FN_8 || '导入系统热词',
		title : wcm.LANG.CHANNELCONTENTLINK_FN_32 || '导入系统热词到站点',
		rightIndex : 1,
		order : 4,
		fn : pageObjMgr['set']
	});
	reg({
		key : 'delete',
		type : 'channelcontentlinks',
		desc : wcm.LANG.CHANNELCONTENTLINK_FN_10 || '删除选定热词',
		title : wcm.LANG.CHANNELCONTENTLINK_FN_10 || '删除选定热词...',
		rightIndex : 13,
		order : 5,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});

})();