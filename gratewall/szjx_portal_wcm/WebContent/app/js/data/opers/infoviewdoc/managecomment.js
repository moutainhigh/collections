(function(){
	//TODO
	Ext.apply(wcm.domain.InfoviewDocMgr, {
		 commentmgr : function(event){
			var oParams = Ext.apply({
				DocumentId : event.getObj().getPropertyAsInt('docid'),
				ChannelId :event.getHost().getId() || 0
			}, parseHost(event.getHost()));
			var sUrl = WCMConstants.WCM_ROOTPATH +'comment/comment_mgr.jsp?'
					+ $toQueryStr(oParams);
			$openMaxWin(sUrl);
		}
	});
	function parseHost(host){
		if(host.getType()==WCMConstants.OBJ_TYPE_CHANNEL){
			return {ChannelId:host.getId(),SiteId:0};
		}
		if(host.getType()==WCMConstants.OBJ_TYPE_WEBSITE){
			return {SiteId:host.getId(),ChannelId:0};
		}
		return {};
	}
})();
(function(){
	var pageObjMgr = wcm.domain.InfoviewDocMgr;
	var reg = wcm.SysOpers.register;
	reg({
		key : 'commentmgr',
		type : 'infoviewdoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_137 ||'管理评论',
		title : wcm.LANG.DOCUMENT_PROCESS_138 ||'管理文档的评论',
		rightIndex : 8,
		order : 10,
		fn : pageObjMgr['commentmgr'],
		isVisible : function(event){
			try{
				return $MsgCenter.getActualTop().g_IsRegister['comment'];
			}catch(err){
				return false;
			}
		}
	});
})();