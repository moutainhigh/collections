Ext.apply(PageContext, {
	tabEnable : false,
	operEnable : false,
	filterEnable : false,
	gridDraggable : false,
	searchEnable : false,
	serviceId : 'wcm61_docbak',
	methodName : 'jQuery',
	/**/
	objectType : 'documentbak',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"DocumentId" : getParameter("DocumentId"),
		"OrderBy" : "baktime desc"
	}
});
Ext.apply(wcm.Grid, {
	rowType : function(){
		return WCMConstants.OBJ_TYPE_DOCUMENTBAK;
	},
	rowInfo : {
	}
});
Ext.apply(PageContext, {
	getContext : function(){
		var context = this.getContext0();
		var bIsChannel = !!getParameter("ChannelId");
		var bIsSite = !!getParameter("SiteId");
		Ext.apply(context, {
			relateType : bIsChannel ? 'docbakInChannel' :
				(bIsSite ? 'docbakInSite' : 'docbakInRoot')
		});
		return context;
	},
	/**
	getPageParams : function(info){
		this.params = Ext.Json.toUpperCase(location.search.parseQuery());
		Ext.applyIf(this.params, Ext.Json.toUpperCase(PageContext.initParams));
		Ext.applyIf(this.params, {
			HostType : PageContext.intHostType,
			hostId : PageContext.hostId
		});
		return Ext.apply(this.params, Ext.Json.toUpperCase(info));
	},
	//*/
	/**/
	pageFilters : (function(){
		if(!PageContext.filterEnable)return null;
		var filters = new wcm.PageFilters({
			displayNum : 6,
			filterType : getParameter('FilterType') || 0
		});
		filters.register([
			//TODO type filters here
		]);
		/*
		filters.register({
			desc : '某个状态',
			type : 1001,
			fn : function(){
				PageContext.loadList({
					"DocStatus" : 1
				});
			},
			order : 5
		});
		*/
		return filters;
	}()),
	pageTabs : (function(){
		if(!PageContext.tabEnable)return null;
		var tabs = wcm.PageTab.getTabs({
			hostType : PageContext.tabHostType,
			displayNum : 6,
			activeTabType : 'documentbak'
		});
		return tabs;
	}())
});
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.DOCUMENT_PROCESS_3 || '个',
	TypeName : wcm.LANG.DOCUMENT_PROCESS_4 || '文档版本'
});
//检索框信息
Event.observe(window, 'load', function(){
	wcm.ListQuery.register({
		/*检索控件追加到的容器*/
		container : 'search', 
		/*是否追加“全部”这个检索项, default to false*/
		appendQueryAll : true,
		/*是否自动加载, default to true*/
		autoLoad : false,
		/*检索项的内容*/
		items : [
			/*
			{name : 'TempDesc', desc : '显示名称', type : 'string'},
			{name : 'TempName', desc : '唯一标识', type : 'string'},
			//*/
		],
		/*执行检索按钮时执行的回调函数*/
		callback : function(params){
			PageContext.loadList(Ext.apply({
				//some params must remember here
			}, params));
		}
	});
});
//路径信息
Ext.apply(PageContext.literator, {
	enable : false,
	width : 350
});
//版本管理列表操作实现 
function $openMaxWin(_sUrl, _sName, _bResizable){
	var nWidth	= window.screen.width - 12;//document.body.clientWidth;
	var nHeight = window.screen.height - 60;//document.body.clientHeight;
	var nLeft	= 0;//(window.screen.availWidth - nWidth) / 2;
	var nTop	= 0;//(window.screen.availHeight - nHeight) / 2;
	var sName	= _sName || "";

	var oWin = window.open(_sUrl, sName, "resizable=" + (_bResizable == true ? "yes" : "no") + ",top=" + nTop + ",left=" + nLeft + ",menubar =no,toolbar =no,width=" + nWidth + ",height=" + nHeight + ",scrollbars=yes,location =no,titlebar=no");
	if(oWin)oWin.focus();
}
function recover(docId,doctitle,versionId,isVisible){
	var oPostData = {
			DocumentId : docId,
			Version : versionId
		};
	BasicDataHelper.call('wcm6_documentBak','recover', oPostData, true,
		function(_transport,_json){
			if(isVisible == "true"){ 
				Ext.Msg.$timeAlert( String.format("成功恢复文档[{0}]版本[版本号={1}]!",$transHtml(doctitle),versionId+1),5);
				notifyFPCallback(_transport);
			}
		},function(_transport,_json){
			Ext.Msg.alert(String.format("没有找到指定的文档版本[ID={0}]",versionId+1));
			PageContext.loadList(PageContext.params);
		}
	);
}
function deleteVersion(docId,versionId){
	var oPostData = {
		DocumentId : docId,
		ObjectIds : versionId
	};
	BasicDataHelper.call('wcm6_documentBak','delete', oPostData,true,
		function(_transport,_json){
			try{
				PageContext.loadList(PageContext.params);
			}catch(err){}
		}
	);
}
function viewBak(docId,versionId){
	var oPostData = {
			DocumentId : docId,
			Version : versionId
		};
	$openMaxWin( WCMConstants.WCM6_PATH + 'document/document_backup_show.jsp?' + $toQueryStr(oPostData),"","yes");
}
