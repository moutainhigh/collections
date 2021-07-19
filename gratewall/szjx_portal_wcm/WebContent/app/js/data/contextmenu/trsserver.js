//右键单击列表行记录的处理
wcm.cms.menu.CMSMenu.on('beforeshow', function(){
	if(!this.filter(WCMConstants.OBJ_TYPE_TRSSERVER)) return;
	var items = this.getArgs().items;
	items.select('pick');
});