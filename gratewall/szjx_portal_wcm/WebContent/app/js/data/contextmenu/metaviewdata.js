//右键单击文档列表行记录的处理
wcm.cms.menu.CMSMenu.on('beforeshow', function(){
	if(!this.filter(WCMConstants.OBJ_TYPE_METAVIEWDATA)) return;
	var items = this.getArgs().items;
	items.select('edit', 'delete', '/',
			'docpositionset', 'setright', '/',
			'preview', 'basicpublish', 'detailpublish','recallpublish', '/', 
			'copy', 'move', 'quote', '/',
			'export', '/',
			'changestatus');
	try{
		var regExp = new RegExp(wcm.LANG['TREE_REPLACE_THISREC'] || '这(?:条|些)记录(?:的)');
		items.applyCfgsMapping(function(item){
			var desc = item.desc || '';
			item.desc = desc.replace(regExp, '');
		});
	}catch(error){
		//just skip it.
	}
});