//右键单击文档列表行记录的处理
wcm.cms.menu.CMSMenu.on('beforeshow', function(){
	if(!this.filter(WCMConstants.OBJ_TYPE_CHNLDOC)) return;
	var items = this.getArgs().items;
	items.select('edit','trash', '/', 
			'changestatus', 'docpositionset','detailpublish','directpublish', 'recallpublish', '/', 
			'copy', 'quote', 'move','/',
			'export','logo','settopdocument','settopdocumentforever','trace_document', '/',
			'setright','backup','backupmgr');
	try{
		var regExp = new RegExp(wcm.LANG['TREE_REPLACE_THISDOC'] || '这(?:篇|些)文档(?:的)?');
		items.applyCfgsMapping(function(item){
			var desc = item.desc || '';
			item.desc = desc.replace(regExp, '');
			if(item.oprKey == "backup") item.desc = item.desc.replace(wcm.LANG.chnldoc_1001 || "为", "");
		});
	}catch(error){
		//just skip it.
	}
});
