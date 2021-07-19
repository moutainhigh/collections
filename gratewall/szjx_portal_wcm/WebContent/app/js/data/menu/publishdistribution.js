//站点分发菜单信息
(function(){
	var reg = wcm.MenuView.register.bind(wcm.MenuView);
	//回调函数,执行新建操作
	function setRegion(event,result){
		var oPostData = {
			SiteId : result.hostIds,
			ObjectId : 0
		}
		setTimeout(function(){
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + 'publishdistribution/publishdistribution_add_edit.jsp?' + $toQueryStr(oPostData),
				title : wcm.LANG.PUBLISHDISTRUBUTION_VALID_1 || '新建站点分发',
				callback : function(info){
					CMSObj['afteradd'](event)({objType:WCMConstants.OBJ_TYPE_PUBLISHDISTRIBUTION, objId:info});
				}
			});
		}, 10);
		return false;
	}
	reg({
		key : 'publishdistribution_add', 
		desc : wcm.LANG['PUBLISHDISTRIBUTION'] || '站点分发',
		parent : 'add',
		order : '6',
		cmd : function(event){
			var host = event.getHost();
			var hostType = host.getIntType();
			var oPostData1 = {
				hidesite : 0,
				hidesitetype : 1,
				hidechannel : 1,
				isRadio : 1,
				rightIndex : 1,
				excludeVirtual : 1
				//selectChannelIds : getParameter('channelids')			
			}
			Ext.apply(oPostData1, wcm.menu.HostSelectAdapter.execute(event));
			FloatPanel.open(WCMConstants.WCM6_PATH +
				'include/host_select.html?' + $toQueryStr(oPostData1),
				wcm.LANG.PUBLISHDISTRUBUTION_VALID_2 || '选择对象', setRegion.bind(this,event));
		}
	});
})();