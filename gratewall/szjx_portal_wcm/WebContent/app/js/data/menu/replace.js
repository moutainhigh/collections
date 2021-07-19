//替换内容菜单信息
(function(){
	var reg = wcm.MenuView.register.bind(wcm.MenuView);
	function newReplace(event,result){
		var oPostData = {
			ChannelId : result.hostIds,
			ObjectId : 0
		};
		setTimeout(function(){
			FloatPanel.open(
				WCMConstants.WCM6_PATH + 'replace/replace_add_edit.jsp?' + $toQueryStr(oPostData),
				wcm.LANG.REPLACE_8||'新建替换内容',
				CMSObj.afteradd(event)
			);
		}, 10);
		return false;
	}

	reg({
		key : 'replace_add', 
		desc : wcm.LANG['REPLACE'] || '替换内容',
		parent : 'add',
		order : '7',
		cmd : function(event){
			var oPostData = {
				hidesitetype : 1,
				hidesite : 1,
				isRadio : 1,
				rightIndex : 13,
				ExcludeTop : 1,
				ExcludeLink : 1,
				ExcludeInfoView : 0
			}
			var host = event.getHost();
			var hostType = host.getIntType();
			Ext.apply(oPostData, wcm.menu.HostSelectAdapter.execute(event));
			FloatPanel.open(WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData),
				wcm.LANG.SELECT_OBJECT || '选择对象' ,
				newReplace.bind(this,event)
			);
		}
	});
})();