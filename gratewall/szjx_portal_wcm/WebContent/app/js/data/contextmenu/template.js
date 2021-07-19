//右键单击文档列表行记录的处理
wcm.cms.menu.CMSMenu.on('beforeshow', function(){
	if(!this.filter(WCMConstants.OBJ_TYPE_TEMPLATE)) return;
	var items = this.getArgs().items;
	items.select('edit', 'wcag2check', 'preview' );
	try{
		var regExp = new RegExp(wcm.LANG['TREE_REPLACE_THISTEMPLATE'] || '这(?:个|些)模板(?:的)?');
		items.applyCfgsMapping(function(item){
			var desc = item.desc || '';
			item.desc = desc.replace(regExp, '');
		});
	}catch(error){
		//just skip it.
	}
});
