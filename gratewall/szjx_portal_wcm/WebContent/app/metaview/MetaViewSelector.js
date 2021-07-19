Ext.ns("wcm.MetaViewSelector");
(function(){
	Ext.apply(wcm.MetaViewSelector, {
		selectView : function(params, fCallBack){
			wcm.CrashBoarder.get('viewInfo_Select').show({
				id	: 'viewInfo_Select',
				title : wcm.LANG.METAVIEW_WINDOW_TITLE_4 || "选择视图",
				src : "./metaview/viewinfo_select_list.html",
				width : "500px",
				height : "300px",
     			maskable:true,
				params : params,
				callback : function (args) {
					fCallBack(args);
				}
			});
					
		},
		setChannelView : function(_sId, params, fCallBack){
			var oPostData = Object.extend({ViewId : _sId || 0}, params || {});
			new com.trs.web2frame.BasicDataHelper().call('wcm61_metaview', 'setViewEmployerByChannel', oPostData, true, function(transport, json){
					(fCallBack || Ext.emptyFn)(transport, json);
			});
		}
	});
})();
