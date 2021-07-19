//栏目菜单信息
(function(){
	var reg = wcm.MenuView.register.bind(wcm.MenuView);
	function newChannel(event,result){
		var oPostData = {
			objectid:	0,
			channelid:	0,
			parentid:	result.hostType == 101 ? result.hostIds : 0,
			siteid:		result.hostType == 101 ? 0 : result.hostIds
		}
		setTimeout(function(){
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + 'channel/channel_add_edit.jsp?' + $toQueryStr(oPostData),
				title : wcm.LANG.CHANNEL_9||'新建/修改栏目',
				callback : function(objId){
					CMSObj['afteradd'](event)(objId);
				}
			});
		}, 10);
		return false;
	}
	function setImport(event,result){
		var oPostData = {
			objecttype : result.hostType,
			parentid : result.hostIds
		};
		setTimeout(function(){
			FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_import.html?' + $toQueryStr(oPostData), '栏目导入', function(){
				var items = {objType:WCMConstants.OBJ_TYPE_CHANNEL};
				var contextType = result.hostType == 101 ? WCMConstants.OBJ_TYPE_CHANNEL : WCMConstants.OBJ_TYPE_WEBSITE;
				var context = {objType:contextType, objId:result.hostIds};
				cmsobj = new CMSObj.createEnumsFrom(items, context);
				cmsobj.addElement(items);
				cmsobj.afteradd()
			});
		}, 10);
		return false;
	}
	function exportAll(result){
		var hostId = parseInt(result.hostIds);
		var hostType = parseInt(result.hostType);
		var oPostData = {};
		oPostData[hostType == 101 ? 'parentChannelId' : 'parentSiteId'] = hostId;
		setTimeout(function(){
			wcm.domain.ChannelMgr.export0(oPostData);
		}, 10);
		return false;
	}
	reg({
		key : 'channel_add',
		desc : wcm.LANG['CHANNEL'] || '栏目',
		parent : 'add',
		order : '2',
		//fn : wcm.domain.ChannelMgr['new'] || wcm.domain.ChannelMgr['addedit']
		cmd : function(event){
			//type code here
			var host = event.getHost();
			var hostType = host.getIntType();
			var oPostData1 = {
				hidesitetype : 1,
				isRadio : 1,
				rightIndex : 11,
				ExcludeTop : 1,
				ExcludeLink : 1
			};
			Ext.apply(oPostData1, wcm.menu.HostSelectAdapter.execute(event));
			FloatPanel.open(WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData1), wcm.LANG.SELECT_OBJECT || '选择对象' ,newChannel.bind(this,event));
		}
	});
	reg({
		key : 'channel_import',
		desc : wcm.LANG['CHANNEL'] || '栏目',
		parent : 'import',
		order : '2',
		//fn : wcm.domain.ChannelMgr['import']
		cmd : function(event){
			//type code here
			var host = event.getHost();
			var hostType = host.getIntType();
			var oPostData2 = {
				hidesitetype : 1,
				isRadio : 1,
				rightIndex : 11,
				ExcludeTop : 1,
				ExcludeLink : 1
			};
			Ext.apply(oPostData2, wcm.menu.HostSelectAdapter.execute(event));
			FloatPanel.open(WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData2), wcm.LANG.SELECT_OBJECT || '选择对象' ,setImport.bind(this,event));
		}
	});
	reg({
		key : 'channel_export',
		desc : String.format("所有子栏目"),
		parent : 'export',
		order : '2',
		//fn : wcm.domain.ChannelMgr['export']
		cmd :function(event){
			//type code here
			var oPostData3 = {
				hidesitetype : 1,
				isRadio : 1,
				rightIndex : 13,
				ExcludeTop : 1,
				ExcludeLink : 1,
				ExcludeInfoView : 0
			};
			var host = event.getHost();
			var hostType = host.getIntType();
			Ext.apply(oPostData3, wcm.menu.HostSelectAdapter.execute(event));
			FloatPanel.open(WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData3), wcm.LANG.SELECT_OBJECT || '选择对象' ,exportAll);
		}
	});
})();