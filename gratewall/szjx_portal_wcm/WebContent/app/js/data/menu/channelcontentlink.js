//热词管理菜单信息
(function(){
	var reg = wcm.MenuView.register.bind(wcm.MenuView);
	//执行回调函数进行菜单项的创建
		function setRegion(event,result){
			var sType = result.hostType;
			var bInSite = sType == 103 ? true : false;
			var oPostData = {
				ChannelId : bInSite ? 0 : result.hostIds,
				SiteId : bInSite ? result.hostIds : 0,
				ObjectId : 0
			}
			setTimeout(function(){
				FloatPanel.open({
					src : WCMConstants.WCM6_PATH + 'channelcontentlink/channelcontentlink_add_edit.jsp?' + $toQueryStr(oPostData),
					title : bInSite ? (wcm.LANG.CHANNELCONTENTLINK_FN_31 || '新建站点热词...') : (wcm.LANG.CHANNELCONTENTLINK_FN_2 || '新建栏目热词...'),
					callback : function(info){
						CMSObj['afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CHANNELCONTENTLINK, objId:info});
					}
				});
			}, 10);
			return false;
		}
		reg({
		key : 'channelcontentlink_add', 
		desc : wcm.LANG['CHANNELCONTENTLINK_FN_28'] || '热词',
		parent : 'add',
		order : '10',
		cmd : function(event){
			var host = event.getHost();
			var hostType = host.getIntType();
			var oPostData1 = {
				hidesitetype : 1,
				isRadio : 1,
				rightIndex : 13,
				excludeVirtual :1,
				ExcludeInfoView : 0
			}
			Ext.apply(oPostData1, wcm.menu.HostSelectAdapter.execute(event));
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'include/host_select.html?' + $toQueryStr(oPostData1),
					wcm.LANG.CHANNELCONTENTLINK_FN_29 || '选择对象', setRegion.bind(this,event));
			//type code here
		}
	});
})();