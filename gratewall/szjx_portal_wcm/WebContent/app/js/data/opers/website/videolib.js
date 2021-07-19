//站点操作信息和Mgr定义
Ext.ns('wcm.domain.WebSiteMgr');
(function(){
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}
	//add and edit operators.
	Ext.apply(wcm.domain.WebSiteMgr, {
	confvideolib : function(){
		var sFeature = 'location=no,resizable=yes,menubar=no,scrollbars=yes,status=yes,titlebar=no,toolbar=no,top=200,left=200,border=0,width=560,height=360';
		window.open(WCMConstants.WCM6_PATH + "video/config.jsp", "_blank", sFeature);
	},
	sample : function(){
		var sFeature = 'location=no,resizable=yes,menubar=no,scrollbars=yes,status=yes,titlebar=no,toolbar=no,top=100,left=100,border=0,width=1100,height=650';
		window.open(WCMConstants.WCM6_PATH + "video/manSample.jsp", "_blank",sFeature);
	},	
	confmas : function(){
		var sFeature = 'location=no,resizable=yes,menubar=no,scrollbars=yes,status=yes,titlebar=no,toolbar=no,top=200,left=200,border=0,width=560,height=360';
		window.open(WCMConstants.WCM6_PATH + "video/configMAS.jsp", "_blank",sFeature);
	},
	deleteFail : function(event){
			window.open(WCMConstants.WCM6_PATH + "video/delete.jsp", "_blank",800,500);
		}
	});

})();
(function(){
	var pageObjMgr = wcm.domain.WebSiteMgr;
	var reg = wcm.SysOpers.register;
	reg({
		key : 'confvideolib',
		type : 'WebSiteInRoot',
		desc : wcm.LANG.VIDEO_PROCESS_219 || '视频库设置',
		title : wcm.LANG.VIDEO_PROCESS_219 || '视频库设置',
		rightIndex : -2,
		order : 4,
		fn : pageObjMgr['confvideolib'],
		isVisible : function(event){
			var host = event.getHost();
			if(host.getId()==2){
				return true;
			}
			return false;
		}
	});
	reg({
		key : 'sample',
		type : 'WebSiteInRoot',
		desc : wcm.LANG.VIDEO_PROCESS_234 || '样本库管理',
		title : wcm.LANG.VIDEO_PROCESS_234 || '样本库管理',
		rightIndex : -2,
		order : 5,
		fn : pageObjMgr['sample'],
		isVisible : function(event){
			var host = event.getHost();			
			var autoclip=$MsgCenter.getActualTop().autoclip;
			if(host.getId()==2 && autoclip==true){
				return true;				
			}
			return false;
		}
	});
	reg({
		key : 'confmas',
		type : 'WebSiteInRoot',
		desc : wcm.LANG.videolib_102 || 'MAS推送设置',
		title : wcm.LANG.videolib_102 || 'MAS推送设置',
		rightIndex : -2,
		order : 4,
		fn : pageObjMgr['confmas'],
		isVisible : function(event){
			var host = event.getHost();
			if(host.getId()==2){
				return true;
			}
			return false;
		}
	});
	reg({
		key : 'deleteFail',
		type : 'WebSiteInRoot',
		desc : wcm.LANG.VIDEO_PROCESS_223 ||'删除转码失败的视频',
		title : wcm.LANG.VIDEO_PROCESS_223 ||'删除转码失败的视频...',
		rightIndex : 39,
		order : 9,
		fn : pageObjMgr['deleteFail'],
		isVisible : function(event){
			var host = event.getHost();
			if(host.getId()==2){
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
			if(host.getId()==2){
				return false;
			}
		}
	})
})();