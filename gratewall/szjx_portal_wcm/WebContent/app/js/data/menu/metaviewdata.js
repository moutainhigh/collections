//资源菜单信息
(function(){
	var reg = wcm.MenuView.register.bind(wcm.MenuView);
	var oPostData = {
		isRadio : 1,
		right : 31,
		excludeVirtual : 1,
		hidesitetype : 1,
		hidesite : 1,
		siteTypes : '4',
		showOneType : 1,
		closebyme : 1
	}
	var getViewIdByChannel = function(channelId, fCallback){
		new Ajax.Request(WCMConstants.WCM6_PATH + 'application/common/get_viewid_by_channel.jsp?channelid='+channelId, {
			onSuccess : function(transport, json){
				if(fCallback) fCallback(transport.responseText);
			}
		});
	}
	reg({
		key : 'metaviewdata_add', 
		desc : wcm.LANG.METAVIEWDATA_108 || '记录',
		parent : 'add',
		order : '16',
		cls : function(event, descNode){
			var m = 'addClass';
			if(RegsiterMgr.isValidPlugin('metadata')){
				m = 'removeClass';
			}
			Ext.fly(descNode)[m]('disabled');
		},
		cmd : function(event){
			var host = event.getHost();
			var hostType = host.getIntType();			
			if(hostType==101){
				oPostData.channelids = oPostData.selectedChannelIds = host.getId();
			}else if(hostType==1){
				oPostData.CurrSiteType = 4;
			}else{
				oPostData.CurrSiteId = host.getId();
			}
			FloatPanel.open(WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData),
				wcm.LANG.SELECT_OBJECT || '选择对象' , function(result){	
					var sTitle = (wcm.LANG.METAVIEWDATA_77 || "新建")
					sTitle += (wcm.LANG.METAVIEWDATA_108 || "记录");			
					var oParams = {
						ObjectId:0,
						ChannelId:result.hostIds		
					};
					getViewIdByChannel(result.hostIds, function(viewId){
						if(viewId == 0){
							Ext.Msg.alert(String.format('栏目[{0}]没有绑定视图', result.hostIds));
							return;
						}
						FloatPanel.close();	
						$openMaxWin(WCMConstants.WCM6_PATH + './application/' + viewId + '/metaviewdata_addedit.jsp?' + $toQueryStr2(oParams));
					});
				}
			);
		}
	});	
})();