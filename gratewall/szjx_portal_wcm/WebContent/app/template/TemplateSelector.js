Ext.ns("wcm.TemplatSelector");
(function(){
	Ext.apply(wcm.TemplatSelector, {
		/**
		*@_oParams		Object
		*				selectType   表示是多选还是单选,可选值为radio、checkbox
		*				templateIds  默认选中的模板
		*				templateType 需要过滤的模板类型, any(-1),nested(1),outline(1),detail(2)
		*				channelId/siteId 获取的那个对象下的模板
		*@_fCallBack	返回结果之后，执行的回调函数。函数参数为
		*				arg1:{selectedIds: [id1,id2...], selectedNames: [name1,name2...]}
		*				arg2:CrashBoard Object,目的是为了异步的时候，返回false，自己关闭CrashBoard
		*/
		selectTemplate : function(_oParams, _fCallBack){
			new wcm.CrashBoard({
				id : 'TEMPLATE_SELECT_SOLO',
				title : wcm.LANG.TEMPLATE_SELECT || '选择模板',
				src : WCMConstants.WCM6_PATH + 'template/template_select_list.html',
				width:'510px',
				height:'240px',
				maskable:true,
				params : _oParams,
				callback : function(_args){
					if((_fCallBack || Ext.emptyFn)(_args, this) !== false){
						this.close();
					}
				}				
			}).show();
		}
	});
})();
