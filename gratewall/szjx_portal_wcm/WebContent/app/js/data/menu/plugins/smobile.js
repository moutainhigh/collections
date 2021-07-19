(function(){
	var isCanCreateMobile = -1;

	var isCanCreateMobileFn = function(){
		//通过同步的ajax请求，获取当前栏目是否可创建移动栏目
		var transport = ajaxRequest({
			url : WCMConstants.WCM_ROOTPATH + 'center.do?serviceid=wcm61_mobileportal&methodname=isCanCreateMobile',
			parameters : 'objectid=0&objecttype=0',
			asyn : false//执行同步请求
		});
		var json = parseXml(loadXml(transport.responseText));
		if(json.RESULT == 'false'){
			return false;
		}
		return true;
	}


	var reg = wcm.MenuView.register.bind(wcm.MenuView);
	reg({
		key : 'smobile',
		desc : '智能创建移动门户',
		parent : 'XJ',
		cls : function(event, descNode){
			if(isCanCreateMobile === -1){
				isCanCreateMobile = 0;
				if(wcm.AuthServer.isAdmin() && isCanCreateMobileFn()){
					isCanCreateMobile = 1;
				}
			}

			Ext.fly(descNode)[isCanCreateMobile==0 ? 'addClass' : 'removeClass']('disabled');
		},
		cmd : function(event){
			window.open(WCMConstants.WCM6_PATH + 'smobile/index.jsp');
		}
	});
})();	
