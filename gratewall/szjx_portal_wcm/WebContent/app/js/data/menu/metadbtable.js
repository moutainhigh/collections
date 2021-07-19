//元数据菜单信息
(function(){
	var reg = wcm.MenuView.register.bind(wcm.MenuView);	
	reg({
		key : 'metadbtable_add', 
		desc : wcm.LANG['METADBTABLE'] || '元数据',
		parent : 'add',
		order : '13',
		cls : function(event, descNode){
			var m = 'addClass';
			if(wcm.AuthServer.isAdmin() && RegsiterMgr.isValidPlugin('metadata')){
				m = 'removeClass';
			}
			Ext.fly(descNode)[m]('disabled');
		},
		cmd : function(event){	
			wcm.domain.MetaDBTableMgr.add(event);
		}		
	});	
})();