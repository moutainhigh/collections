//右键单击导航树上站点节点的处理
wcm.cms.menu.CMSMenu.on('beforeshow', function(){
	if(!this.filter(WCMConstants.OBJ_TYPE_CHANNEL)) return;
	var items = this.getArgs().items;
	var wcmEvent = this.getArgs().wcmEvent;
	var context = wcmEvent.getContext();
	if(context.get('sitetype') != 2) return;
	items.applyCfgs({
		operItem : ['videoInChannel', 'upload'],
		order : 71.1,
		desc : wcm.LANG['VIDEO_PROCESS_159'] || '新建视频',
		oprKey : 'uploadVideo'
	});
});
