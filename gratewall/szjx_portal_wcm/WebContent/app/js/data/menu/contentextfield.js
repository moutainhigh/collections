//扩展字段菜单信息
(function(){
	var reg = wcm.MenuView.register.bind(wcm.MenuView);
	//回调函数,执行新建操作
	function setRegion(event,result){
		var oPostData = {
			HostType : result.hostType,
			HostId : result.hostIds,
			ObjectId : 0
		}
		setTimeout(function(){
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + 'contentextfield/contentextfield_addedit.jsp?' + $toQueryStr(oPostData),
				title : wcm.LANG.CONTENTEXTFIELD_CONFIRM_3 || '新建扩展字段',
				callback : function(info){
					CMSObj['afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CONTENTEXTFIELD, objId:info});
				}
			});
		}, 10);
		return false;
	}
		
	reg({
		key : 'contentextfield_add', 
		desc : wcm.LANG['CONTENTEXTFIELD'] || '扩展字段',
		parent : 'add',
		order : '5',
		cmd : function(event){
			var host = event.getHost();
			var hostType = host.getIntType();
			var oPostData1 = {
				isRadio : 1,
				rightIndex : 19,
				excludeVirtual :1,
				ExcludeInfoView : 0
			}
			Ext.apply(oPostData1, wcm.menu.HostSelectAdapter.execute(event));
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'include/host_select.html?' + $toQueryStr(oPostData1),
					wcm.LANG.CONTENTEXTFIELD_CONFIRM_11 || '选择对象', setRegion.bind(this,event));
			//type code here
		}
	});
})();
