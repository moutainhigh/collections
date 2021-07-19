//站点操作信息和Mgr定义
Ext.ns('wcm.domain.WebSiteMgr');
(function(){
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}
	//add and edit operators.
	Ext.apply(wcm.domain.WebSiteMgr, {
		confphotolib : function(event){
			FloatPanel.open(WCMConstants.WCM6_PATH +"photo/photolib_config.jsp",(wcm.LANG.PHOTO_LIB || "图片库设置"),680,280);
		}
	});

})();
(function(){
	var pageObjMgr = wcm.domain.WebSiteMgr;
	var reg = wcm.SysOpers.register;
	reg({
		key : 'confphotolib',
		type : 'WebSiteInRoot',
		desc : wcm.LANG.PHOTO_LIB || '图片库设置',
		title : wcm.LANG.PHOTO_LIB || '图片库设置',
		rightIndex : -2,
		order : 4,
		fn : pageObjMgr['confphotolib'],
		isVisible : function(event){
			var host = event.getHost();
			if(host.getId()==1){
				return true;
			}
			return false;
		}
	});
	wcm.SysOpers.createInterceptor({
		key : 'quicknew',
		type : 'WebSiteInRoot',
		isVisible : function(event){
			var host = event.getHost();
			if(host.getId()==1){
				return false;
			}
		}
	})
})();