//右键单击导航树上栏目节点的处理
wcm.cms.menu.CMSMenu.on('beforeshow', function(){
	if(this.fromTree()) return;
	if(!this.filter(WCMConstants.OBJ_TYPE_CHANNEL)) return;
	var items = this.getArgs().items;
	items.select('edit', '/', 
			'preview', 'synTemplates', 'increasingpublish', 'solopublish', '/', 
			'refreshpublish', 'fullypublish', 'recallpublish', 'export', '/',
			'likecopy', 'move', 'pubhistory',  'docpositionset', '/',
			'trash', 'region','/'
			);
	try{
		var regExp = new RegExp(wcm.LANG['TREE_REPLACE_THISCHNL'] || '这(?:个|些)栏目(?:的)?');
		items.applyCfgsMapping(function(item){
			var desc = item.desc || '';
			item.desc = desc.replace(regExp, '');
		});
	}catch(error){
		//just skip it.
	}
});
