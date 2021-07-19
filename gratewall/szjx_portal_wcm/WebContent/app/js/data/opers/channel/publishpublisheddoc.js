Ext.ns('wcm.domain.ChannelMgr');
(function (){
	var m_oMgr = wcm.domain.ChannelMgr;
	Ext.apply(wcm.domain.ChannelMgr, {
		publishpublisheddoc : function(event){
			wcm.CrashBoarder.get('publish_published_docs').show({
				title : '发布已发文档及其概览',
				src : WCMConstants.WCM6_PATH + 'document/publish_publisheddocs_and_folder.html',
				width:'570px',
				height:'250px',
				maskable:true,
				callback:function(args){
					var extrtParam = {
						'StartDocCrtime' : args['starttime'],
						'EndDocCrtime' : args['endtime']
					}
					var aIds = event.getObjsOrHost().getIds();
					wcm.domain.PublishAndPreviewMgr.publish(aIds, 101, 'publishpublisheddoc', extrtParam);
					this.close();
				}
			}); 
		}
	});
})();
(function(){
	var fnIsVisible = function(event){
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
		var hideChnlTypes = [1,2,11];
		for (var i = 0; i < chnlTypes.length; i++){
			if(hideChnlTypes.include(chnlTypes[i])) return false;
		}
		return true;
	}
	var pageObjMgr = wcm.domain.ChannelMgr;
	var reg = wcm.SysOpers.register;
	reg({
		key : 'publishpublisheddoc',
		type : 'channel',
		desc : '发布已发文档及其概览',
		title : '发布栏目的首页，并且发布指定时间范围的已发文档',
		rightIndex : 17,
		order : 6.1,
		fn : pageObjMgr['publishpublisheddoc'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'publishpublisheddoc',
		type : 'channelmaster',
		desc : '发布已发文档及其概览',
		title : '发布栏目的首页，并且发布指定时间范围的已发文档',
		rightIndex : 17,
		order : 24.1,
		fn : pageObjMgr['publishpublisheddoc'],
		isVisible : fnIsVisible
	});
})();