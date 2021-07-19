//视频菜单信息
(function(){
	var reg = wcm.MenuView.register.bind(wcm.MenuView);
	//如果没注册视频选件，就置灰
	var fCls = function(event, descNode){		
		var m = 'addClass';
		if(RegsiterMgr.isValidPlugin('video')){
			m = 'removeClass';
		}
		Ext.fly(descNode)[m]('disabled');		
	}
	
	reg({
		key : 'video_add', 
		desc : wcm.LANG['VIDEO'] || '视频',
		parent : 'add',
		order : '999',
		cls	: fCls,
		cmd : function(event){
			var host = event.getHost();
			var hostType = host.getIntType();
			var oPostData = {
				isRadio : 1,
				excludeVirtual : 1,
				hidesitetype : 1,
				hidesite : 1,
				siteTypes : '2',
				rightIndex : 31,
				showOneType : 1,
				ExcludeInfoView : 0
			}
			Ext.apply(oPostData, wcm.menu.HostSelectAdapter.execute(event));
			oPostData.ExcludeInfoView = 0;
			FloatPanel.open(WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData),
				wcm.LANG.SELECT_OBJECT || '选择对象' , function(result){
					var params = {
						DocumentId : 0,
						FromEditor : 1,
						ChannelId : result.hostIds
					};
					var iWidth = window.screen.availWidth - 12;
					var iHeight = window.screen.availHeight - 30;
					var sFeature = 'location=no,resizable=yes,menubar=no,scrollbars=no,status=no,titlebar=no,' 
							+ 'toolbar=no,top=0,left=0,border=0,width='+iWidth+',height='+iHeight;
					window.open(WCMConstants.WCM6_PATH + "video/video_addedit.jsp?" + $toQueryStr(params),
						"_blank" , sFeature);
				}
			);
		}
	});
})();