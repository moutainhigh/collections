/**
  * 电子报
  */
Ext.ns('wcm.domain.ChannelMgr');
(function (){
	Ext.apply(wcm.domain.ChannelMgr, {
		addepress : function(event){
			var hostId = event.getHost().getId();
			var hostType = event.getHost().getIntType();
			var oParams = {			
				parentid:	hostType == 101 ? hostId : 0,
				siteid:		hostType == 101 ? 0 : hostId
			};
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + 'epress/epresschannel_create.jsp?' + $toQueryStr(oParams),
				title : wcm.LANG.epress_1000 || '新建电子报栏目',				
				callback : function(objId){	
					FloatPanel.hide();
					Ext.Msg.confirm(wcm.LANG.epress_1001 || "创建电子报栏目成功,继续设置栏目属性?",{
						ok : function(){
							FloatPanel.close();
							var params = {
								objectid:	objId,
								channelid:	objId,
								parentid:	hostType == 101 ? hostId : 0,
								siteid:		hostType == 101 ? 0 : hostId
							}
							FloatPanel.open({
								src : WCMConstants.WCM6_PATH + 'channel/channel_add_edit.jsp?' + $toQueryStr(params),
								title : wcm.LANG.CHANNEL_9||'新建/修改栏目',
								callback : function(objId){
									CMSObj['afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CHANNEL, objId:objId});
								}
							});							
						},
						no : function(){		
							CMSObj['afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CHANNEL, objId:objId});
							FloatPanel.close();							
						}
					});	
				}
			});
		},
		pubhistory : function(event){
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + 'epress/epresschannel_pubhistory.html?ChannelId=' +  event.getHost().getId(),
				title : wcm.LANG.epress_1003 || '发布电子报历史索引'				
			});
		}
	});
})();
(function(){
	var ePressVisible = function(){
		var ePressEnabled = false;
		try{
			ePressEnabled = $MsgCenter.getActualTop().bEpressMainEnable;
		}catch(error){		
		}
		return ePressEnabled;
	}
	var fnIsVisible = function(event){
		if(!ePressVisible()) {
			return false;
		}
		var objs = event.getObjs();
		var context = event.getContext();
		if(objs.length() > 0){
			var chnlTypes = objs.getPropertyAsInt("chnlType", 0);
		}else{
			var chnlTypes = context.get('ChannelType') || 0;
		}
		if(!Array.isArray(chnlTypes)){
			chnlTypes = [chnlTypes];
		}
		var hideChnlTypes = [1,2,11,13];
		for (var i = 0; i < chnlTypes.length; i++){
			if(hideChnlTypes.include(chnlTypes[i])) return false;
		}
		return true;
	}
	var pageObjMgr = wcm.domain.ChannelMgr;
	var reg = wcm.SysOpers.register;
	reg({
		key : 'seperate',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_SEPERATE || '分隔线',
		title : wcm.LANG.CHANNEL_SEPERATE || '分隔线',
		rightIndex : -1,
		order : 45,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'addepress',
		type : 'channelHost',
		desc :  wcm.LANG.epress_1004 || '创建电子报栏目',
		title : wcm.LANG.epress_1004 || '创建电子报栏目',
		rightIndex : 11,
		order : 46,
		fn : pageObjMgr['addepress'],
		isVisible : ePressVisible
	});	
	reg({
		key : 'addepress',
		type : 'websiteHost',
		desc :  wcm.LANG.epress_1004 || '创建电子报栏目',
		title : wcm.LANG.epress_1004 || '创建电子报栏目',
		rightIndex : 11,
		order : 300,
		fn : pageObjMgr['addepress'],
		isVisible : ePressVisible
	});
	reg({
		key : 'pubhistory',
		type : 'channel',
		desc :  wcm.LANG.epress_1003 || '发布电子报历史索引',
		title : wcm.LANG.epress_1003 || '发布电子报历史索引...',
		rightIndex : 17,
		order : 46,
		fn : pageObjMgr['pubhistory'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'pubhistory',
		type : 'channelMaster',
		desc :  wcm.LANG.epress_1003 || '发布电子报历史索引',
		title : wcm.LANG.epress_1003 || '发布电子报历史索引...',
		rightIndex : 17,
		order : 46,
		fn : pageObjMgr['pubhistory'],
		isVisible : fnIsVisible
	});
})();