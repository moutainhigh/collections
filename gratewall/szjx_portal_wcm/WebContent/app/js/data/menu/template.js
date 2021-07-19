//模板菜单信息
(function(){
	var reg = wcm.MenuView.register.bind(wcm.MenuView);
	function newTemp(result){
		var oPostData = {
			HostType : result.hostType,
			HostId : result.hostIds,
			ObjectId : 0
		};
		setTimeout(function(){
			$openMaxWin(WCMConstants.WCM6_PATH +'template/template_add_edit.jsp?' + $toQueryStr(oPostData));
		}, 10);
		return false;
	}
	function setImport(event,result){
		var oPostData = {
			HostType : result.hostType,
			HostId : result.hostIds,
			ObjectId : 0
		};
		setTimeout(function(){
			FloatPanel.open(
				WCMConstants.WCM6_PATH + 'template/template_import.html?' + $toQueryStr(oPostData), 
				wcm.LANG.TEMPLATE_24 || '模板导入', 
				CMSObj.afteradd(event)
			);
		}, 10);
		return false;
	}
	function exportAll(result){
		var oPostData = {
			HostType : result.hostType,
			HostId : result.hostIds,
			ContainsChildren : true,
			exportAll : true
		};
		wcm.domain.TemplateMgr.getHelper().call(wcm.domain.TemplateMgr.serviceId, 
			'export', oPostData, true, function(_oXMLHttp, _json){
			var sFileUrl = _oXMLHttp.responseText;
			var frm = document.getElementById("ifrmDownload");
			if(frm == null) {
				frm = document.createElement('iframe');
				frm.style.height = 0;
				frm.style.width = 0;
				document.body.appendChild(frm);
			}
			sFileUrl = WCMConstants.WCM_ROOTPATH + "file/read_file.jsp?DownName=TEMPLATE&FileName=" + sFileUrl;
			frm.src = sFileUrl;
		}.bind(this));
		return false;
	}
	
	reg({
		key : 'template_add', 
		desc : wcm.LANG['TEMPLATE'] || '模板',
		parent : 'add',
		order : '4',
		cmd : function(event){
			//type code here
			var host = event.getHost();
			var hostType = host.getIntType();
			var oPostData1 = {
				hidesitetype : 1,
				isRadio : 1,
				rightIndex : 21,
				ExcludeTop : 1,
				ExcludeLink : 1,
				ExcludeInfoView : 0
			};
			Ext.apply(oPostData1, wcm.menu.HostSelectAdapter.execute(event));
			FloatPanel.open(
				WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData1),
				wcm.LANG.SELECT_OBJECT || '选择对象' ,
				newTemp
			);
		}
	});
	reg({
		key : 'template_import', 
		desc : wcm.LANG['TEMPLATE'] || '模板',
		parent : 'import',
		order : '4',
		cmd : function(event){
			//type code here
			var host = event.getHost();
			var hostType = host.getIntType();
			var oPostData2 = {
				hidesitetype : 1,
				isRadio : 1,
				rightIndex : 21,
				ExcludeTop : 1,
				ExcludeLink : 1,
				ExcludeInfoView : 0
			};
			Ext.apply(oPostData2, wcm.menu.HostSelectAdapter.execute(event));
			FloatPanel.open(WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData2), 
				wcm.LANG.SELECT_OBJECT || '选择对象' ,
				setImport.bind(this,event)
			);
		}
	});
	reg({
		key : 'template_export', 
		desc : String.format("所有模板"),
		parent : 'export',
		order : '4',
		cmd : function(event){
			//type code here
			var oPostData3 = {
				hidesitetype : 1,
				isRadio : 1,
				rightIndex : 24,
				ExcludeTop : 1,
				ExcludeLink : 1,
				ExcludeInfoView : 0
			};
			var host = event.getHost();
			var hostType = host.getIntType();
			Ext.apply(oPostData3, wcm.menu.HostSelectAdapter.execute(event));
			FloatPanel.open(WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData3),
				wcm.LANG.SELECT_OBJECT || '选择对象' ,
				exportAll
			);
		}
	});
})();