//站点菜单信息
(function(){
	var reg = wcm.MenuView.register.bind(wcm.MenuView);
	function newSite(event,result){
		var oPostData = {
			sitetype : result.sitetype[0].id || 0,
			ObjectId : 0
		};
		setTimeout(function(){
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + 'website/website_add_edit.jsp?' + $toQueryStr(oPostData),
				title : wcm.LANG.WEBSITE_1||'新建/修改站点',
				callback : function(info){
					CMSObj['afteradd'](event)({objType:WCMConstants.OBJ_TYPE_WEBSITE, objId:info});
				}
			});
		}, 10);
		return false;
	}
	var fCls = function(event, descNode){		
		var m = 'addClass';
		if(wcm.AuthServer.isAdmin() && RegsiterMgr.isValidPlugin('metadata')){
			m = 'removeClass';
		}
		Ext.fly(descNode)[m]('disabled');		
	};
	reg({
		key : 'website_add', 
		desc : wcm.LANG['WEBSITE'] || '站点',
		parent : 'add',
		order : '1',
		cls	: fCls,
		cmd : function(event){
			//type code here
			var host = event.getHost();
			var hostType = host.getIntType();
			var oPostData1 = {
				hidesite : 1,
				hidechannel : 1,
				rightIndex : -2,
				isRadio : 1
			};
			if(hostType == 1){
				oPostData1.sitetype = host.getId();
			}else{
				oPostData1.sitetype = event.getContext().get('sitetype');
			}
			FloatPanel.open(
				WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData1), 
				wcm.LANG.SELECT_OBJECT || '选择对象' ,
				newSite.bind(this,event)
			);
		}
	});
	reg({
		key : 'website_import', 
		desc : wcm.LANG['WEBSITE'] || '站点',
		parent : 'import',
		order : '1',
		cls	: fCls,
		cmd : function(event){
			wcm.domain.WebSiteMgr['import'](event);
		}
	});
})();