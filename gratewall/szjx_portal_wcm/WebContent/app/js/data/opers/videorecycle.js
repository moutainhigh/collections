//废稿箱操作信息和Mgr定义
Ext.ns('wcm.domain.VideoRecycleMgr');
(function(){
	function parseHost(host){
		if(host.getType()==WCMConstants.OBJ_TYPE_CHANNEL){
			return {ChannelId:host.getId(),SiteId:0};
		}
		if(host.getType()==WCMConstants.OBJ_TYPE_WEBSITE){
			return {SiteId:host.getId(),ChannelId:0};
		}
		return {};
	}
	var m_oMgr = wcm.domain.VideoRecycleMgr ={
		serviceId : 'wcm61_video',
		helpers : {},
		getHelper : function(_sServceFlag){
			return new com.trs.web2frame.BasicDataHelper();
		}
	};
	Ext.apply(wcm.domain.VideoRecycleMgr, {
		//type here
		view : function(event){
			var host = event.getHost();
			var hostId = host.getId();
			var hostType = host.getIntType();
			var pageContext = event.getContext();
			var oParams = Ext.apply({
				DocumentId : pageContext.docid,
				//ObjectId : pageContext.docid,
				ChnlDocId : event.getIds(),
				FromRecycle : 1
				//ChannelId : pageContext.channelid,
				//SiteId : (hostType==103)?hostId:0
			},parseHost(event.getHost()));
			$openMaxWin(WCMConstants.WCM6_PATH +
					'document/document_show.jsp?' + $toQueryStr(oParams));
		},
		//deleteall : function(_sIds, _params, _nRecNum){
		deleteall : function(event){
			//debugger
			//var params = event.getContext();
			var host = event.getHost();
			var hostId = host.getId();
			var hostType = host.getIntType();
			var params = {
				ChannelId: (hostType == 101)?hostId:'',
				SiteId: (hostType == 103)?hostId:''
			}
			if(confirm(wcm.LANG.DOCRECYCLE_CONFIRM_1 || '您当前所执行的操作将彻底删除废稿箱中所有视频，您确定仍要继续清空当前废稿箱？')){
				//$beginSimplePB('正在删除视频..', 2);
				ProcessBar.start(wcm.LANG.VIDEORECYLE_22 || '删除视频..');
				m_oMgr.getHelper().call(m_oMgr.serviceId, 'clearRecycleVideos', params, true, function(){
					ProcessBar.close();
					event.getObjs().afterdelete();
				});
			}
		},
		//'delete' : function(_sIds, _params){
		'delete' : function(event){
			//var sIds = event.getObjs().getPropertyAsInt('DocId');
			var sIds = event.getIds();
			var hostId = event.getHost().getId();
			var _params = event.getContext();
			var params = {
				objectids: sIds,
				operation: '_delete'
			}
			var aIds = sIds;
			if(String.isString(aIds)){
				aIds = aIds.split(",");
			}
			if(aIds.length >= 50){
				m_oMgr._delete0(event);
				return;
			}
			m_oMgr.doOptionsAfterDisplayInfo(params, m_oMgr._delete0.bind(this,event));
		},
		_delete0 : function(event){
			//debugger
			ProcessBar.start(wcm.LANG.VIDEORECYLE_22 || '删除视频..');
			//var sIds = event.getObjs().getPropertyAsInt('DocId');
			var sIds = event.getIds();
			var host = event.getHost();
			var hostId = host.getId();
			var hostType = host.getIntType();
			var _params = {
				ChannelId: (hostType == 101)?hostId:'',
				SiteId: (hostType == 103)?hostId:''
			}; 
			Object.extend(_params, {ObjectIds: sIds, drop: true});
			m_oMgr.getHelper().call(m_oMgr.serviceId, 'deleteVideos', _params, true, function(){
				ProcessBar.close();
				event.getObjs().afterdelete();
			});	
		},
		doOptionsAfterDisplayInfo : function(_params, _fDoAfterDisp){
			//var aTop = $MsgCenter.getActualTop();
			wcm.CrashBoarder.get('DIALOG_DOCUMENT_INFO').show({
				title : wcm.LANG.DOCRECYCLE_SYSTEMINFO || '系统提示信息',
				src : WCMConstants.WCM6_PATH + 'docrecycle/video_info.html',
				width:'520px',
				height:'205px',
				maskable:true,
				params :  _params,
				callback : _fDoAfterDisp
			});
		},
		//restoreall : function(_sIds, _params, _nRecNum){
		restoreall : function(event, operItem){
			return m_oMgr.restore(event, operItem, true);
		},
		//restore : function(_sIds, _params, _bRestoreAll){
		restore : function(event, operItem, _bRestoreAll){
			//组织获取视频信息的参数
			var host = event.getHost();
			var hostId = host.getId();
			var hostType = host.getIntType();
			var params = {
				operation: '_restore',
				special: true
			}
			if(_bRestoreAll == true) {
				params['restoreAll'] = true;
				Object.extend(params, {
					channelids: (hostType == 101)?hostId:'',
					siteids: (hostType == 103)?hostId:''
					//channelid : event.getObjs().getPropertyAsInt('channelid')
				});
			}else{
				Object.extend(params, {
					//objectids: event.getObjs().getPropertyAsInt('DocId')
					objectids: event.getIds()
				});
			}
			m_oMgr.doOptionsAfterDisplayInfo(params, function(){
				//Object.extend(_params, {objectids: sIds});
				//组织提交restore的参数
				ProcessBar.start(wcm.LANG.VIDEORECYLE_23 || '还原视频...');
				var postData = {};
				if(_bRestoreAll == true) {
					postData['restoreAll'] = true;
					Object.extend(postData, {
						channelid: (hostType == 101)?hostId:'',
						siteid: (hostType == 103)?hostId:''
					});
				}else{
					Object.extend(postData, {
						objectids: event.getIds()
					});
				}
				//var postData = params;
				m_oMgr.getHelper().call(m_oMgr.serviceId, 'restore', postData, true, function(){
					ProcessBar.close();
					event.getObjs().afterdelete();
				});
			}.bind(this));
		}
	});
})();
(function(){
	var pageObjMgr = wcm.domain.VideoRecycleMgr;
	var reg = wcm.SysOpers.register;

	reg({
		key : 'restore',
		type : 'videorecycle',
		desc : wcm.LANG.VIDEORECYLE_1 || '还原这个视频',
		title : wcm.LANG.VIDEORECYLE_7 || '还原这个视频到原位置',
		rightIndex : 33,
		order : 1,
		fn : pageObjMgr['restore'],
		quickKey : 'R'
	});
	reg({
		key : 'delete',
		type : 'videorecycle',
		desc : wcm.LANG.VIDEORECYLE_2 || '删除这个视频',
		title : wcm.LANG.VIDEORECYLE_2 || '删除这个视频',
		rightIndex : 33,
		order : 2,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'deleteall',
		type : 'videorecycleInChannel',
		desc : wcm.LANG.VIDEORECYLE_6 || '清空废稿箱',
		title : wcm.LANG.VIDEORECYLE_8 || '清空当前栏目的废稿箱',
		rightIndex : 33,
		order : 3,
		fn : pageObjMgr['deleteall']
	});
	reg({
		key : 'restoreall',
		type : 'videorecycleInChannel',
		desc : wcm.LANG.VIDEORECYLE_3 || '还原所有视频',
		title : wcm.LANG.VIDEORECYLE_9 || '还原废稿箱中的所有视频',
		rightIndex : 33,
		order : 4,
		fn : pageObjMgr['restoreall']
	});
	reg({
		key : 'deleteall',
		type : 'videorecycleInSite',
		desc : wcm.LANG.VIDEORECYLE_6 || '清空废稿箱',
		title : wcm.LANG.VIDEORECYLE_10 || '清空当前站点的废稿箱',
		rightIndex : 33,
		order : 5,
		fn : pageObjMgr['deleteall']
	});
	reg({
		key : 'restoreall',
		type : 'videorecycleInSite',
		desc : wcm.LANG.VIDEORECYLE_3 || '还原所有视频',
		title : wcm.LANG.VIDEORECYLE_9 || '还原废稿箱中的所有视频',
		rightIndex : 33,
		order : 6,
		fn : pageObjMgr['restoreall']
	});
	reg({
		key : 'restore',
		type : 'videorecycles',
		desc : wcm.LANG.VIDEORECYLE_4 || '还原这些视频',
		title : wcm.LANG.VIDEORECYLE_11 || '还原这些视频到原位置',
		rightIndex : 33,
		order : 7,
		fn : pageObjMgr['restore'],
		quickKey : 'R'
	});
	reg({
		key : 'delete',
		type : 'videorecycles',
		desc : wcm.LANG.VIDEORECYLE_5 || '删除这些视频',
		title : wcm.LANG.VIDEORECYLE_5 || '删除这些视频',
		rightIndex : 33,
		order : 8,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});

})();