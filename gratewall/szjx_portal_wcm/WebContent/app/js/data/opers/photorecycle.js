//废稿箱操作信息和Mgr定义
Ext.ns('wcm.domain.PhotoRecycleMgr');
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
	var m_oMgr = wcm.domain.PhotoRecycleMgr ={
		serviceId : 'wcm61_viewdocument',
		helpers : {},
		getHelper : function(_sServceFlag){
			return new com.trs.web2frame.BasicDataHelper();
		}
	};
	Ext.apply(wcm.domain.PhotoRecycleMgr, {
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
		deleteall : function(event){
			var host = event.getHost();
			var hostId = host.getId();
			var hostType = host.getIntType();
			var params = {
				ChannelId: (hostType == 101)?hostId:'',
				SiteId: (hostType == 103)?hostId:''
			}
			if(confirm(wcm.LANG.PHOTO_CONFIRM_143 || '您当前所执行的操作将彻底删除废稿箱中所有图片,您确定仍要继续清空当前废稿箱?')){
				ProcessBar.start(wcm.LANG.PHOTO_CONFIRM_144 || '删除图片...');
				m_oMgr.getHelper().call(m_oMgr.serviceId, 'clearRecycle', params, true, function(){
					ProcessBar.close();
					$MsgCenter.getActualTop().isDeletingAll = true;
					event.getObjs().afterdelete();
				});
			}
		},
		'delete' : function(event){
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
				if(confirm(wcm.LANG.PHOTO_CONFIRM_146 || "确实要删除这些图片吗?")){
					m_oMgr._delete0(event);
				}
				return;
			}
			m_oMgr.doOptionsAfterDisplayInfo(params, m_oMgr._delete0.bind(this,event));
		},
		_delete0 : function(event){
			ProcessBar.start(wcm.LANG.PHOTO_CONFIRM_144 || '删除图片...');
			var sIds = event.getIds();
			var host = event.getHost();
			var hostId = host.getId();
			var hostType = host.getIntType();
			var _params = {
				ChannelId: (hostType == 101)?hostId:'',
				SiteId: (hostType == 103)?hostId:''
			}; 
			Object.extend(_params, {ObjectIds: sIds, drop: true});
			m_oMgr.getHelper().call(m_oMgr.serviceId, 'delete', _params, true, function(){
				ProcessBar.close();
				event.getObjs().afterdelete();
			});	
		},
		doOptionsAfterDisplayInfo : function(_params, _fDoAfterDisp){
			//var aTop = $MsgCenter.getActualTop();
			var DIALOG_PHOTO_INFO = 'photo_info_dialog';
			wcm.CrashBoarder.get(DIALOG_PHOTO_INFO).show({
				title : wcm.LANG.DOCRECYCLE_SYSTEMINFO || '系统提示信息',
				src : WCMConstants.WCM6_PATH + 'photo/photo_info.jsp',
				width:'520px',
				height:'205px',
				maskable:true,
				params :  _params,
				callback : _fDoAfterDisp
			});
		},
		//restoreall : function(_sIds, _params, _nRecNum){
		restoreall : function(event, operItem){
			if(confirm(wcm.LANG.PHOTO_CONFIRM_142 || '您确定要还原所有图片?')){
				return m_oMgr.restore(event, operItem, true);
			}
		},
		//restore : function(_sIds, _params, _bRestoreAll){
		restore : function(event, operItem, _bRestoreAll){
			//组织获取文档信息的参数
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
				});
				$MsgCenter.getActualTop().isRestoringAll = true;
			}else{
				Object.extend(params, {
					objectids: event.getIds()
				});
			}
			var restore = function(){
				ProcessBar.start(wcm.LANG.PHOTO_CONFIRM_139 || '还原图片..');
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
				m_oMgr.getHelper().call(m_oMgr.serviceId, 'restore', postData, true, function(){
					ProcessBar.close();
					event.getObjs().afterdelete();
				});
			}.bind(this);
			if(_bRestoreAll){
				restore();
			}else{
				m_oMgr.doOptionsAfterDisplayInfo(params, restore.bind(this));
			}
		}
	});
})();
(function(){
	var pageObjMgr = wcm.domain.PhotoRecycleMgr;
	var reg = wcm.SysOpers.register;
	var fnIsVisible = function(event){
		var context = event.getContext();
		if(!context.gridInfo) return true;
		return context.gridInfo.RecordNum > 0;
	};

	reg({
		key : 'restore',
		type : 'photorecycle',
		desc : wcm.LANG.PHOTO_CONFIRM_137 || '还原这幅图片',
		rightIndex : 33,
		order : 1,
		fn : pageObjMgr['restore'],
		quickKey : 'R'
	});
	reg({
		key : 'delete',
		type : 'photorecycle',
		desc :wcm.LANG.PHOTO_CONFIRM_72 || '删除这幅图片',
		title:'删除这幅图片',
		rightIndex : 33,
		order : 2,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'deleteall',
		type : 'photorecycleInChannel',
		desc : wcm.LANG.PHOTO_CONFIRM_140 || '清空废稿箱',
		rightIndex : 33,
		order : 3,
		fn : pageObjMgr['deleteall'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'restoreall',
		type : 'photorecycleInChannel',
		desc : wcm.LANG.PHOTO_CONFIRM_141 || '还原所有图片',
		rightIndex : 33,
		order : 4,
		fn : pageObjMgr['restoreall'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'deleteall',
		type : 'photorecycleInSite',
		desc : wcm.LANG.PHOTO_CONFIRM_140 || '清空废稿箱',
		rightIndex : 33,
		order : 5,
		fn : pageObjMgr['deleteall'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'restoreall',
		type : 'photorecycleInSite',
		desc : wcm.LANG.PHOTO_CONFIRM_141 || '还原所有图片',
		rightIndex : 33,
		order : 6,
		fn : pageObjMgr['restoreall'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'restore',
		type : 'photorecycles',
		desc : wcm.LANG.PHOTO_CONFIRM_138 || '还原这些图片',
		rightIndex : 33,
		order : 7,
		fn : pageObjMgr['restore'],
		quickKey : 'R'
	});
	reg({
		key : 'delete',
		type : 'photorecycles',
		desc : wcm.LANG.PHOTO_CONFIRM_80 || '删除这些图片',
		rightIndex : 33,
		order : 8,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});

})();