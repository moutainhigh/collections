//文档同步菜单信息
(function(){
	var reg = wcm.MenuView.register.bind(wcm.MenuView);
	//回调函数,执行新建操作
	function setRegion(event,result){
		var oPostData = {
			ChannelId : result.hostIds,
			ObjectId : 0
		}
		setTimeout(function(){
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + 'channelsyn/docsyn_dis_add_edit.jsp?' + $toQueryStr(oPostData),
				title : wcm.LANG.CHANNELSYN_VALID_6 || '新建栏目分发',
				callback : function(info){
					CMSObj['afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CHANNELSYN, objId:info});
				}
			});
		}, 10);
		return false;
	}
	reg({
		key : 'channelsyn_add',
		desc : wcm.LANG['CHANNELSYN'] || '栏目分发',
		parent : 'add',
		order : '8',
		cmd : function(event){
			var host = event.getHost();
			var hostType = host.getIntType();
			var oPostData1 = {
				hidesitetype : 1,
				hidesite : 1,
				isRadio : 1,
				rightIndex : 13,
				excludeVirtual : 1,
				siteTypes : "0,4",
				ExcludeInfoView : 0
				//selectChannelIds : getParameter('channelids')
			}
			Ext.apply(oPostData1, wcm.menu.HostSelectAdapter.execute(event));
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'include/host_select.html?' + $toQueryStr(oPostData1),
					wcm.LANG.CHANNELSYN_VALID_7 || '选择对象', setRegion.bind(this,event));
		}
	});
})();