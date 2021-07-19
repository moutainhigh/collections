//图片菜单信息
(function(){
	var reg = wcm.MenuView.register.bind(wcm.MenuView);
	//回调函数，执行新建操作
	function setRegion(result){
		var oPostData = {
			channelid : result.hostIds
		}
		setTimeout(function(){
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'photo/photo_upload.jsp?' + $toQueryStr(oPostData),
					wcm.LANG.PHOTO_CONFIRM_61 || '上传图片', uploadCallback);
		}, 10);
		return false;
	}
	function uploadCallback(fpInfo){
		setTimeout(function(){
			FloatPanel.open(fpInfo.src,
					fpInfo.title);
		}, 10);
		return false;
	}
	reg({
		key : 'photo_add', 
		desc : wcm.LANG['PHOTO'] || '图片',
		parent : 'add',
		order : '12',
		cls : function(event, descNode){
			var m = 'addClass';
			if(RegsiterMgr.isValidPlugin('photo')){
				m = 'removeClass';
			}
			Ext.fly(descNode)[m]('disabled');
		},
		cmd : function(event){
			var host = event.getHost();
			var hostType = host.getIntType();
			var oPostData1 = {
				isRadio : 1,
				excludeVirtual :1,
				SiteTypes : 1,
				hidesite : 1,
				hidesitetype : 1,
				rightIndex : 31
				
			}
			Ext.apply(oPostData1, wcm.menu.HostSelectAdapter.execute(event));
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'include/host_select.html?' + $toQueryStr(oPostData1),
					wcm.LANG.PHOTO_CONFIRM_62 || '选择对象', setRegion);
		}
	});
})();