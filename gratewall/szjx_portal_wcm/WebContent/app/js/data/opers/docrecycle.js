//废稿箱操作信息和Mgr定义
Ext.ns('wcm.domain.DocRecycleMgr');
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
	var m_oMgr = wcm.domain.DocRecycleMgr ={
		serviceId : 'wcm61_viewdocument',
		helpers : {},
		getHelper : function(_sServceFlag){
			return new com.trs.web2frame.BasicDataHelper();
		}
	};
	Ext.apply(wcm.domain.DocRecycleMgr, {
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
			if(confirm(wcm.LANG.DOCRECYCLE_CONFIRM_1 || '您当前所执行的操作将彻底删除废稿箱中所有文档,您确定仍要继续清空当前废稿箱?')){
				//$beginSimplePB('正在删除文档..', 2);
				ProcessBar.start(wcm.LANG.DOCRECYLE_22 || '删除文档..');
				m_oMgr.getHelper().call(m_oMgr.serviceId, 'clearRecycle', params, true, function(){
					ProcessBar.close();
					$MsgCenter.getActualTop().isDeletingAll = true;
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
				if(confirm(wcm.LANG.DOCRECYLE_27 || "确实要删除这篇/这些文档吗?")){
					m_oMgr._delete0(event);
				}
				return;
			}
			m_oMgr.doOptionsAfterDisplayInfo(params, m_oMgr._delete0.bind(this,event));
		},
		_delete0 : function(event){
			//debugger
			ProcessBar.start(wcm.LANG.DOCRECYLE_22 || '删除文档..');
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
			m_oMgr.getHelper().call(m_oMgr.serviceId, 'delete', _params, true, function(){
				ProcessBar.close();
				event.getObjs().afterdelete();
			});	
		},
		doOptionsAfterDisplayInfo : function(_params, _fDoAfterDisp){
			//var aTop = $MsgCenter.getActualTop();
			wcm.CrashBoarder.get('DIALOG_DOCUMENT_INFO').show({
				title : wcm.LANG.DOCRECYCLE_SYSTEMINFO || '系统提示信息',
				src : WCMConstants.WCM6_PATH + 'docrecycle/document_info.html',
				width:'520px',
				height:'205px',
				maskable:true,
				params :  _params,
				callback : _fDoAfterDisp
			});
		},
		//restoreall : function(_sIds, _params, _nRecNum){
		restoreall : function(event, operItem){
			if(confirm(wcm.LANG.DOCRECYLE_25 || '您确定要还原所有文档?')){
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
					//channelid : event.getObjs().getPropertyAsInt('channelid')
				});
				$MsgCenter.getActualTop().isRestoringAll = true;
			}else{
				Object.extend(params, {
					//objectids: event.getObjs().getPropertyAsInt('DocId')
					objectids: event.getIds()
				});
			}
			var restore = function(){
					//Object.extend(_params, {objectids: sIds});
					//组织提交restore的参数
					ProcessBar.start(wcm.LANG.DOCRECYLE_23 || '还原文档...');
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
			}.bind(this);
			if(_bRestoreAll){
				restore();
			}else{
				m_oMgr.doOptionsAfterDisplayInfo(params, restore);
			}
		}
	});
})();
(function(){
	var pageObjMgr = wcm.domain.DocRecycleMgr;
	var reg = wcm.SysOpers.register;
	var fnIsVisible = function(event){
		var context = event.getContext();
		if(!context.gridInfo) return true;
		return context.gridInfo.RecordNum > 0;
	};

	reg({
		key : 'restore',
		type : 'docrecycle',
		desc : wcm.LANG.DOCRECYLE_1 || '还原这篇文档',
		rightIndex : 33,
		order : 1,
		fn : pageObjMgr['restore'],
		quickKey : 'R'
	});
	reg({
		key : 'delete',
		type : 'docrecycle',
		desc : wcm.LANG.DOCRECYLE_2 || '删除这篇文档',
		rightIndex : 33,
		order : 2,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'deleteall',
		type : 'docrecycleInChannel',
		desc : wcm.LANG.DOCRECYLE_6 || '清空废稿箱',
		rightIndex : 33,
		order : 3,
		fn : pageObjMgr['deleteall'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'restoreall',
		type : 'docrecycleInChannel',
		desc : wcm.LANG.DOCRECYLE_3 || '还原所有文档',
		rightIndex : 33,
		order : 4,
		fn : pageObjMgr['restoreall'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'deleteall',
		type : 'docrecycleInSite',
		desc : wcm.LANG.DOCRECYLE_6 || '清空废稿箱',
		rightIndex : 33,
		order : 5,
		fn : pageObjMgr['deleteall'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'restoreall',
		type : 'docrecycleInSite',
		desc : wcm.LANG.DOCRECYLE_3 || '还原所有文档',
		rightIndex : 33,
		order : 6,
		fn : pageObjMgr['restoreall'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'restore',
		type : 'docrecycles',
		desc : wcm.LANG.DOCRECYLE_4 || '还原这些文档',
		rightIndex : 33,
		order : 7,
		fn : pageObjMgr['restore'],
		quickKey : 'R'
	});
	reg({
		key : 'delete',
		type : 'docrecycles',
		desc : wcm.LANG.DOCRECYLE_5 || '删除这些文档',
		rightIndex : 33,
		order : 8,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});

})();