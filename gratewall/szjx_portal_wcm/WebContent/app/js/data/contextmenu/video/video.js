//右键单击导航树上空白处的处理
wcm.cms.menu.CMSMenu.on('beforeshow', function(){
	if(!this.filter(WCMConstants.OBJ_TYPE_WEBSITE)) return;
	var items = this.getArgs().items;
	var wcmEvent = this.getArgs().wcmEvent;
	var context = wcmEvent.getContext();
	if(context.get('sitetype') != 2) return;
	items.applyCfgs({
		oprKey : 'separate',
		order : 90
	},{
		operItem : ['videoInSite', 'live'],
		order : 99,
		desc : wcm.LANG['VIDEO_PROCESS_220'] || '新建视频直播',
		oprKey : 'live'

	});
	items.applyCfgsMapping(function(item){
		var desc = item.desc || '';
		item.desc = desc.replace(wcm.LANG['TREE_REPLACE_THISSITE'] || '这个站点', '');
	});
});
