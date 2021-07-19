//文档同步操作信息和Mgr定义
Ext.ns('wcm.domain.ChannelSynMgr');
(function(){
	var m_oMgr = wcm.domain.ChannelSynMgr;
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}
	Ext.apply(wcm.domain.ChannelSynMgr, {
		/**
		 * @param : wcm.CMSObjEvent event
		 */
		add : function(event){
			var oPageParams = event.getContext();
			Object.extend(oPageParams,{"ObjectId":0});
			Object.extend(oPageParams,{"ChannelId":event.getHost().getId()});
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'channelsyn/docsyn_dis_add_edit.jsp?' + $toQueryStr(oPageParams), wcm.LANG.CHANNELSYN_VALID_6 || '新建栏目分发...', CMSObj.afteradd(event)
			);
		},
		edit : function(event){
			var oPageParams = event.getContext();
			var sId = event.getIds().join();
			Object.extend(oPageParams,{"ObjectId":sId});
			Object.extend(oPageParams,{"ChannelId":event.getHost().getId()});
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'channelsyn/docsyn_dis_add_edit.jsp?' + $toQueryStr(oPageParams), wcm.LANG.CHANNELSYN_VALID_9 || '修改栏目分发', CMSObj.afteredit(event)
			);
		},
		//删除单个和多个对象
		"delete" : function(event){
			var oPageParams = {};
			var sId = event.getIds();
			var nCount = sId.toString().split(',').length;
			var sHint = (nCount==1)?'':' '+nCount+' ';
			var sResult = String.format(wcm.LANG.CHANNELSYN_VALID_39 || '确实要删除这{0}个栏目分发吗?', sHint);
			Ext.Msg.confirm(sResult,{
                yes : function(){
                    BasicDataHelper.call("wcm6_documentSyn", 
						'delete', //远端方法名				
						Object.extend(oPageParams, {"ObjectIds": sId,"channelId":event.getHost().getId()}), //传入的参数
						true, //异步
						function(){//响应函数
							event.getObjs().afterdelete();
						}
					);
                }
            });
		}
		//type here
	});
})();
(function(){
	var pageObjMgr = wcm.domain.ChannelSynMgr;
	var reg = wcm.SysOpers.register;

	reg({
		key : 'edit',
		type : 'channelsyn',
		desc : wcm.LANG.CHANNELSYN_VALID_38 ||'修改这个栏目分发',
		title:'修改这个栏目分发',
		rightIndex : 13,
		order : 1,
		fn : pageObjMgr['edit'],
		quickKey : 'M'
	});
	reg({
		key : 'delete',
		type : 'channelsyn',
		desc : wcm.LANG.CHANNELSYN_VALID_12 ||'删除这个栏目分发',
		title:'删除这个栏目分发',
		rightIndex : 13,
		order : 2,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'add',
		type : 'channelsynInChannel',
		desc : wcm.LANG.CHANNELSYN_VALID_6 ||'新建栏目分发',
		title:'新建栏目分发...',
		rightIndex : 13,
		order : 3,
		fn : pageObjMgr['add'],
		quickKey : 'N'
	});
	reg({
		key : 'delete',
		type : 'channelsyns',
		desc : wcm.LANG.CHANNELSYN_VALID_13 ||'删除这些栏目分发',
		rightIndex : 13,
		order : 4,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});

})();