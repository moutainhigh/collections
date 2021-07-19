/**
(function(){
	var oTabItem = {
		type : '扩展',//类型
		desc : '扩展',//描述
		url : WCMConstants.WCM6_PATH + 'extension/extension.jsp',//页面地址
		rightIndex : '-1',//权限索引位
		fittable : false,//是否适合v6.1结构
		order : 1000,//指定顺序
		isVisible : function(context){//动态控制显示
			var bHasRight = wcm.AuthServer.hasRight(
				context.right, this.rightIndex);
			if(!bHasRight)return false;
			return true;
		},
		fn : function(){//可执行方法
		},
		renderUrl : function(search){//为页面整理必要的参数,fittable为false时有效
			var params = search.parseQuery();
			Ext.apply(params, {
				ObjId : PageContext.hostId,
				ObjType : PageContext.intHostType
			});
			return params;
		}
	}
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
		items : [oTabItem]
	});
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
		items :[oTabItem]
	});
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
		items :[oTabItem]
	});
})();
//*/
