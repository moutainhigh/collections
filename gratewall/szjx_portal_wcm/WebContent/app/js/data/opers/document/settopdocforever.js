(function(){
	//TODO
	Ext.apply(wcm.domain.ChnlDocMgr, {
		settopdocumentforever : function(event){
			cb = new wcm.CrashBoard({
				title : wcm.LANG.DOCUMENT_PROCESS_276||'置顶设置',
				src : WCMConstants.WCM6_PATH+'document/document_settopforever.jsp',
				width:'600px',
				height:'310px',
				maskable:true,
				appendParamsToUrl : true,
				params : {
					//传递给内置页面src的参数值
					DocumentId : event.getObjs().getDocIds(),
					ChannelId : event.getObj().getPropertyAsInt('currchnlid')
				},
				callback : function(oPostData) {
					//获取到内置页面传出的参数oPostData，在这里进行AJAX数据交换。
					var oHelper = new com.trs.web2frame.BasicDataHelper();
					oHelper.Call("wcm6_document", "settopdocument", oPostData, true,
						function(_transport,_json){
						event.getObjs().afteredit();
						//this.close();
					});
					//发送完AJAX请求后，最后关闭crashboard
					this.close();
					}
			});
			cb.show();
		}
	});
})();
(function(){
	var pageObjMgr = wcm.domain.ChnlDocMgr;
	var reg = wcm.SysOpers.register;

	//如果Host是站点，则不进行显示
	var fnIsVisible = function(event){
			var host = event.getHost();
			if(host.getType()==WCMConstants.OBJ_TYPE_WEBSITE){
				return false;
			}
			return true;
	};
	reg({
		key : 'settopdocumentforever',
		type : 'chnldoc',
		desc : wcm.LANG.DOCUMENT_PROCESS_276||'置顶设置',
		title : '置顶设置...',
		rightIndex : 62,
		order : 16.5,
		fn : pageObjMgr['settopdocumentforever'],
		isVisible : fnIsVisible
	});
})();