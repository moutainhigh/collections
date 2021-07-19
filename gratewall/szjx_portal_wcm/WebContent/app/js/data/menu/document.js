//文档菜单信息
(function(){
	var reg = wcm.MenuView.register.bind(wcm.MenuView);
	function getHelper(_sServceFlag){
		return new com.trs.web2frame.BasicDataHelper();
	}
	reg({
		key : 'document_add', 
		desc : wcm.LANG['DOCUMENT'] || '文档',
		parent : 'add',
		order : '3',
		cmd : function(event){
			var host = event.getHost();
			var hostType = host.getIntType();
			var oPostData = {
				isRadio : 1,
				excludeVirtual : 1,
				hidesitetype : 1,
				hidesite : 1,
				siteTypes : '0',
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
					window.open(WCMConstants.WCM6_PATH + "document/document_addedit.jsp?" + $toQueryStr(params),
						"_blank" , sFeature);
				}
			);
		}
	});
	reg({
		key : 'document_import', 
		desc : wcm.LANG['DOCUMENT'] || '文档',
		parent : 'import',
		order : '3',
		cmd : function(event){
			var host = event.getHost();
			var hostType = host.getIntType();
			var oPostData = {
				isRadio : 1,
				excludeVirtual : 1,
				hidesitetype : 1,
				hidesite : 1,
				siteTypes : '0',
				rightIndex : 31,
				showOneType : 1,
				ExcludeInfoView : 0
			}
			Ext.apply(oPostData, wcm.menu.HostSelectAdapter.execute(event));
			FloatPanel.open(WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData),
				wcm.LANG.SELECT_OBJECT || '选择对象',function(result){
					var params = {
						DocumentId : 0,
						ChannelId : result.hostIds
					};
					setTimeout(function(){
						FloatPanel.open(WCMConstants.WCM6_PATH +
						'document/document_import.jsp?' + $toQueryStr(params),
						'文档-导入文档',function(objId){
							CMSObj['afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CHNLDOC, objId:objId});
						});
					}, 10);
					return false;
			});

		}
	});
	reg({
		key : 'document_export', 
		desc : String.format("所有文档"),
		parent : 'export',
		order : '3',
		cmd : function(event){
			var host = event.getHost();
			var hostType = host.getIntType();
			var oPostData = {
				isRadio : 1,
				ExcludeTop : 1,
				ExcludeLink : 1,
				hidesitetype : 1,
				hidesite : 0,
				siteTypes : '0',
				rightIndex : 34,
				showOneType : 1,
				ExcludeInfoView : 0
			}
			Ext.apply(oPostData, wcm.menu.HostSelectAdapter.execute(event));
			FloatPanel.open(WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData),
				wcm.LANG.SELECT_OBJECT || '选择对象',function(result){
					var oParams ;
					var nChannelId = (result.hostType==101)?result.hostIds:0;
					var nSiteId = (result.hostType==103)?result.hostIds:0;
					if(nChannelId ==0) {
						oParams = {
							SiteIds : nSiteId
						}
					}else {
						 oParams = {
							ChannelIds : nChannelId
						}
					}
					getHelper().call('wcm61_viewdocument', "query",oParams,  true,
						function(_transport,_json){
							var params = {
								ExportAll : 1,
								ChannelId : nChannelId,
								SiteId : nSiteId,
								Count : _json.VIEWDOCUMENTS.NUM
							}
							setTimeout(function(){
								FloatPanel.open(WCMConstants.WCM6_PATH +
								'document/document_export.jsp?' + $toQueryStr(params),
								'文档-导出所有文档');
							}, 10);
						}
					);
					return false;
			});
		}
	});
})();