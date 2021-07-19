//右键单击导航树上站点节点的处理
wcm.cms.menu.CMSMenu.on('beforeshow', function(){
	if(this.fromTree()) return;
	if(!this.filter(WCMConstants.OBJ_TYPE_WEBSITE)) return;
	var items = this.getArgs().items;
	items.select('edit', 'recycle', '/', 
			'preview', 'synTemplates', '/', 
			'increasepublish', 'homepublish', 'updatepublish', 'completepublish', '/',
			'export', 'likecopy', '/', 
			'keyword');
	try{
		var regExp = new RegExp(wcm.LANG['TREE_REPLACE_THISSITE'] || '这(?:个|些)站点(?:的)?');
		items.applyCfgsMapping(function(item){
			var desc = item.desc || '';
			item.desc = desc.replace(regExp, '');
		});
	}catch(error){
		//just skip it.
	}
});