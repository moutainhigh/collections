//右键单击文档列表行记录的处理
wcm.cms.menu.CMSMenu.on('beforeshow', function(){
	if(!this.filter(WCMConstants.OBJ_TYPE_INFOVIEWDOC)) return;
	var items = this.getArgs().items;
	items.select('edit', 'move','copy', 'quote','/',
			'detailpublish','recallpublish','basicpublish','preview', 'publishinfo','/',
			'trash', 'export','exportexcelselectOne','changestatus','/',
			'docpositionset','setright','startflow','logo'
			);

	try{
		var regExp = new RegExp(wcm.LANG['TREE_REPLACE_THISDOC'] || '这(?:篇|些)文档(?:的)?');
		items.applyCfgsMapping(function(item){
			var desc = item.desc || '';
			item.desc = desc.replace(regExp, '');
			if(item.oprKey == "backup") item.desc = item.desc.replace(wcm.LANG.infoviewdoc_1001 || "为", "");
		});
	}catch(error){
		//just skip it.
	}
});


wcm.cms.menu.CMSMenu.on('beforeshow', function(){
	if(!this.filter(WCMConstants.OBJ_TYPE_INFOVIEWDOC)) return;
	wcm.CrashBoardCopy = wcm.CrashBoard;
	try{
		wcm.CrashBoard = $('main').contentWindow.wcm.CrashBoard;
	}catch(error){
		alert(error.message);
	}
});

wcm.cms.menu.CMSMenu.on('click', function(){
	if(!this.filter(WCMConstants.OBJ_TYPE_INFOVIEWDOC)) return;
	wcm.CrashBoard = wcm.CrashBoardCopy;
});
 