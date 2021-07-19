//右键单击导航树上站点节点的处理
wcm.cms.menu.CMSMenu.on('beforeshow', function(){
	if(!this.fromTree()) return;
	if(!this.filter(WCMConstants.OBJ_TYPE_CHANNEL)) return;
	var items = this.getArgs().items;
	var wcmEvent = this.getArgs().wcmEvent;
	var context = wcmEvent.getContext();
	if(context.get('sitetype') != 1) return;
	//库结点下上传图片不合理
	items.applyCfgs({
		operItem : ['photoInChannel', 'upload'],
		order : 71.1,
		desc : wcm.LANG['PHOTO_CONFIRM_61'] || '上传图片',
		oprKey : 'uploadphoto'
	});
});
