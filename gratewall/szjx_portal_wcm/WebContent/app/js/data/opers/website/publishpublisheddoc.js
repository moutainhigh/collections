Ext.ns('wcm.domain.WebSiteMgr');
(function (){
	var m_oMgr = wcm.domain.WebSiteMgr;
	Ext.apply(wcm.domain.WebSiteMgr, {
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
					wcm.domain.PublishAndPreviewMgr.publish(aIds, 103, 'publishpublisheddoc', extrtParam);
					this.close();
				}
			}); 
		}
	});
})();
(function(){
	var pageObjMgr = wcm.domain.WebSiteMgr;
	var reg = wcm.SysOpers.register;
		reg({
		key : 'publishpublisheddoc',
		type : 'website',
		desc : '发布已发文档及其概览',
		title : '发布站点和栏目的首页，并且发布指定时间范围的已发文档',
		rightIndex : 5,
		order : 4.1,
		fn : pageObjMgr['publishpublisheddoc']
	});
})();