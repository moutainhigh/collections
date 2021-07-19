//右键单击导航树上站点类型节点的处理
wcm.cms.menu.CMSMenu.on('beforeshow', function(){
	if(!this.filter(WCMConstants.OBJ_TYPE_WEBSITEROOT)) return;
	var items = this.getArgs().items;
	var wcmEvent = this.getArgs().wcmEvent;
	if(wcmEvent==null)return;
	var context = wcmEvent.getContext();
	if(context==null) return;
	if(context.get('sitetype') == 3) return;
	items.applyCfgs({
		operItem : ['websiteInRoot', 'new'],
		desc : wcm.LANG['TREE_NEWSITE'] || '新建站点',
		oprKey : 'newSite'
	},{
		operItem : ['websiteInRoot', 'quicknew'],
		desc : wcm.LANG['TREE_QUICKNEWSITE'] || '智能创建站点',
		oprKey : 'quicknewSite'
	},{
		operItem : ['websiteInRoot', 'import'],
		desc : wcm.LANG['TREE_IMPORTSITE'] || '从外部导入站点',
		oprKey : 'importSite'
	},{
		oprKey : 'keywordMgr',
		desc : wcm.LANG['TREE_KEYWORDMGR'] || '管理关键词',
		iconCls : 'keyword',
		cmd : function(event){
			wcm.domain.WebSiteMgr['keyword'](event);
		},
		cls : function(){
			if(wcm.AuthServer.isAdmin())return '';
			return 'display-none';
		}
	},{
		operItem : ['websiteInRoot', 'confphotolib'],
		desc : wcm.LANG['PHOTO_LIB'] || '图片库设置',
		oprKey : 'confphotolib'
	},{
		operItem : ['websiteInRoot', 'confvideolib'],
		desc : wcm.LANG['VIDEO_PROCESS_219'] || '视频库设置',
		oprKey : 'confvideolib'
	},{
		operItem : ['websiteInRoot', 'sample'],
		desc : wcm.LANG['VIDEO_PROCESS_234'] || '样本库管理',
		oprKey : 'sample'
	},{
		operItem : ['websiteInRoot', 'confmas'],
		desc : wcm.LANG.websiteroot_101 || '推送设置',
		oprKey : 'confmas'
	},{
		operItem : ['websiteInRoot', 'deleteFail'],
		desc : wcm.LANG['VIDEO_PROCESS_223'] || '删除转码失败的视频',
		oprKey : 'deleteFail'
	});
});