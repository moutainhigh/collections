Ext.ns('PageContext');
//页面上下文
Ext.apply(PageContext, {
	params : {},
	getContext : function(){
		return this.getContext0();
	},
	gridFunctions : function(){
	},
	getParameter : function(){
		if(this.prepareParams){
			var retVal = this.prepareParams.apply(this, arguments);
			if(retVal!=null)return retVal;
		}
		return getParameter.apply(null, arguments);
	},
	_buildParams : function(event, actionType){
		return {};
		//修复栏目的子栏目列表，在直接修改属性面板属性时将出错。
		var params = Ext.Json.toUpperCase(location.search.parseQuery());
		return Ext.applyIf(params, Ext.Json.toUpperCase(PageContext.initParams));
	},
	getContext0 : function(){
		if(this.context!=null){
			return this.context;
		}
		var bIsChannel = !!PageContext.getParameter("ChannelId");
		this.context = {
			isChannel : bIsChannel,
			host : {
				objType : PageContext.hostType,
				objId : PageContext.hostId,
				right : PageContext.getParameter("RightValue"),
				isVirtual : PageContext.getParameter("IsVirtual")
			},
			href : location.href,
			params : Ext.Json.toUpperCase(location.search.parseQuery())
		};
		return this.context;
	},
	initPage : function(){
		PageContext.m_CurrPage.beforeinit();
		this.context = null;
		if(wcm.PageFilter)wcm.PageFilter.init(PageContext.getFilters());
		if(wcm.PageTab){
			var info = PageContext.getTabs();
			PageContext.activeTabType = info.activeTabType;
			wcm.PageTab.init(info);
		}
		if(wcm.PageOper)wcm.PageOper.init(PageContext.getOpers());
		if(wcm.Grid || wcm.ThumbList){
			this.gridFunctions();
			this.loadList({
				FilterType : (this.pageFilters)?this.pageFilters.filterType:0
			});
		}else{
			PageContext.m_CurrPage.afterinit();
		}
		PageContext.getContext();
		if(PageContext.literator){
			PageContext.drawLiterator();
		}
	},
	_doBeforeLoad : function(query){
	},
	_doBeforeBinding : function(_transport, _json){
	},
	_doAfterBound : function(_transport, _json){
	},
	renderList : function(_transport, _json, _ajaxRequest){
		PageContext._doBeforeBinding(_transport, _json, _ajaxRequest);
		Ext.get('wcm_table_grid').update(_transport.responseText, true);
		PageContext._doAfterBound(_transport, _json, _ajaxRequest);
		//TODO dist afterinit,afterrefresh
		if(PageContext.m_CurrPage)PageContext.m_CurrPage.afterinit();
	},
	getPageInfo : function(){
		return null;
	},
	getPageParams : function(info){
		var locationParams = location.search.parseQuery();
		this.params = Ext.Json.toUpperCase(locationParams);
		Ext.applyIf(this.params, Ext.Json.toUpperCase(PageContext.initParams));
		return Ext.apply(this.params, Ext.Json.toUpperCase(info));
	},
	getPageSize : function(){
		var pagesize = this.params["PAGESIZE"];
		if(wcm.ThumbList && !pagesize) pagesize = -1;
		if(wcm.Grid && !pagesize) pagesize = 20;
		if(wcm.Grid && parent.m_CustomizeInfo) pagesize = parent.m_CustomizeInfo.listPageSize.paramValue;
		return pagesize;
	},
	loadList : function(info, _fCallBack, _bRefresh){
		if(this._doBeforeLoad()===false){
			if(PageContext.m_CurrPage)PageContext.m_CurrPage.afterinit();
			return;
		}
		var aCombine = [];
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		this.params = (_bRefresh && this.params) ? 
			Ext.apply(this.params, Ext.Json.toUpperCase(info)) : this.getPageParams(info);
		this.params = Ext.Json.toUpperCase(this.params);
		this.params["PAGESIZE"] = this.getPageSize();
		if(this.serviceId.indexOf('.jsp')==-1){
			var sQueryMethod = this.methodName || 'jQuery';
			oHelper.Call(this.serviceId, sQueryMethod, 
				this.params, true, _fCallBack||this.renderList);
		}
		else{
			oHelper.JspRequest(this.serviceId,
				this.params, true, _fCallBack||this.renderList);
		}
	},
	refreshList : function(info, _selectRowIds){
		this.loadList(Ext.applyIf({
			"SELECTIDS" : _selectRowIds.join(',')
		}, info), null, true);
	},
	updateCurrRows : function(_currId){
		PageContext.editIds = _currId;
		if(wcm.Grid) var ids = wcm.Grid.getRowIds(true);
		if(window.myThumbList) var ids = window.myThumbList.getIds(true);
		this.refreshList(PageContext.params, ids);
	},
	getFilters : function(){
		return Ext.applyIf({
			enable : this.filterEnable
		}, this.pageFilters);
	},
	getTabs : function(){
		var tab = this.pageTabs;
		return Ext.applyIf({
			enable : this.tabEnable
		}, tab);
	},
	getOpers : function(){
		var oper = this.pageOpers;
		if(!oper && wcm.SysOpers) oper = wcm.SysOpers.opers;
		return Ext.applyIf({
			enable : this.operEnable && oper != null
		}, oper);
	},
	getRightIndex : function(_sFunctionType){
		if(PageContext.rightIndexs){
			return PageContext.rightIndexs[_sFunctionType] || -1;
		}
		return -1;
	}
});


Ext.apply(PageContext, {
	/**
	*@param extraParams Object / Function
	*/
	validExistProperty : function(extraParams){
		return function(){
			var wcmEvent = PageContext.event;
			var obj = wcmEvent.getObjs().getAt(0) || wcmEvent.getHost();
			var oPostData = {
				objId : obj.getId(), 
				objType : obj.getIntType()
			};
			var element = this.field;
			oPostData[element.name] = element.value;

			if(Ext.isFunction(extraParams)){
				oPostData = extraParams(oPostData) || oPostData;
			}else{
				Ext.apply(oPostData, extraParams);
			}
			var warning = oPostData["warning"] || "";
			delete oPostData["warning"];

			warning = warning || ((this.validateObj["desc"] || element.name) + (wcm.LANG.SYSTEM_NOTUNIQUE||"不唯一"));
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			oHelper.JspRequest(
				WCMConstants.WCM6_PATH + "include/property_test_exist.jsp", 
				oPostData,  true, 
				function(transport, json){
					var result = transport.responseText.trim();
					ValidatorHelper.execCallBack(element, result == 'true' ? warning : null);
				}
			); 
		};
	}
});

Ext.apply(PageContext, {
	hostType : (function(){
		return PageContext.getParameter("HostType") || 
			(!!PageContext.getParameter("channelid")?
				WCMConstants.OBJ_TYPE_CHANNEL : 
			(!!PageContext.getParameter("siteid")?
				WCMConstants.OBJ_TYPE_WEBSITE : WCMConstants.OBJ_TYPE_WEBSITEROOT));
	})(),
	intHostType : (function(){
		return PageContext.getParameter("IntHostType") || 
			(!!PageContext.getParameter("channelid")? 101 : 
			(!!PageContext.getParameter("siteid")? 103 : 1));
	})(),
	hostId : (function(){
		return PageContext.getParameter("HostId") 
			|| PageContext.getParameter("ChannelId") 
			|| PageContext.getParameter("SiteId")
			|| PageContext.getParameter("RootId")
			|| PageContext.getParameter("SiteType");
	})(),
	tabHostType : (function(){
		return PageContext.getParameter("TabHostType") || (!!PageContext.getParameter("channelid")?
				WCMConstants.TAB_HOST_TYPE_CHANNEL : 
			(!!PageContext.getParameter("siteid")?
				WCMConstants.TAB_HOST_TYPE_WEBSITE : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT));
	})(),
	filteredOnEdit : function(event){
		return !event.getObjs().getType().equalsI(PageContext.objectType)
					&& event.getObj().getId()!=PageContext.hostId;
	},
	getActiveTabType : function(context){
		return PageContext.activeTabType;
	}
});
PageContext.m_CurrPage = $MsgCenter.$main(PageContext.getContext0());
Event.observe(window, 'load', function(){
	PageContext.initPage();
	//$MsgCenter.enableKeyDown();
	listeningForMyObjs();
	listeningForHosts();
	if(PageContext.literator){
		listeningForLiterator();
	}
	if(PageContext.keyProvider){
		$MsgCenter.setKeyProvider(PageContext.prepareKeyProvider());
	}
});

//if observe unload may be destroyed in MsgCenter.js
Event.observe(window, 'beforeunload', function(){
	//fix ie7,when 302 send redirect to errorpage may lead execute beforeunload twice.
	if(PageContext.m_CurrPage){
		PageContext.m_CurrPage.afterdestroy();
		PageContext.m_CurrPage = null;
	}
	//return false;
});
function listeningForMyObjs(){
	$MsgCenter.on({
		objType : PageContext.objectType,//WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
		afteradd : function(event){
			if(!$('grid_body')){
				PageContext.refreshList(PageContext.params, [event.getIds()]);
			}else{
				PageContext.updateCurrRows(event.getIds());
			}
		},
		afteredit : function(event){
	//		if(PageContext.filteredOnEdit(event))return;
			PageContext.updateCurrRows(event.getIds());
		},
		afterdelete : function(event){
			delete PageContext.params["SELECTIDS"];
			PageContext.loadList(PageContext.params);
		}
	});
}
function listeningForHosts(){
	$MsgCenter.on({
		objType : PageContext.hostType,//WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
		afteredit : function(event){
			var host = event.getObj();
			if(host.getId()!=PageContext.hostId)return;
			PageContext.loadList(null, null, true);
			//host.applyContext(PageContext.getContext());
			//host.afterselect();
		}
	});
}
function listeningForLiterator(){
	$MsgCenter.on({
		id : 'literator_listen',
		objType : [WCMConstants.OBJ_TYPE_WEBSITE, WCMConstants.OBJ_TYPE_CHANNEL, 
			WCMConstants.OBJ_TYPE_CHANNELMASTER],
		afteredit : function(event){
			PageContext.drawLiterator();
		},
		aftermove : function(event){
			PageContext.drawLiterator();
		}
	});
}

(function(){
	try{
		if(!window.frameElement || window.frameElement.id != 'main') return;
	}catch(error){
		//ignore cross domain error
		return;
	}
	$MsgCenter.on({
		objType : WCMConstants.OBJ_TYPE_MAINPAGE,//WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
		afterinit : function(event){
			Ext.apply(event.getContext(), {
				tabEnable : PageContext.tabEnable,
				operEnable : PageContext.operEnable,
				filterEnable : PageContext.filterEnable
			});
		},
		operpanelshow : function(event){
			if(!wcm.Layout)return;
			wcm.Layout.expandByChild('east', 'east_opers');
		},
		operpanelhide : function(event){
			if(!wcm.Layout)return;
			wcm.Layout.collapseByChild('east', 'east_opers');
		}
	});
	$MsgCenter.on({
		objType : WCMConstants.OBJ_TYPE_MAINPAGE,
		afterinit : function(event){
			var ids = PageContext.editIds;
			if(!ids) return;
			delete PageContext.editIds;
			ids = Ext.isArray(ids) ? ids : [ids];
			if(ids.length <= 0) return;
			(wcm.Grid || window.myThumbList).initEditedItems(ids);
		}
	});
})();

//disable the contextmenu.
Event.observe(document, 'contextmenu', function(event){
	if(WCMConstants.DEBUG || PageContext.enableContextMenu) return;
	Event.stop(event || window.event);
});

//compatible for ProcessBar
(function(){
	window.ProcessBar = window.ProcessBar || {};
	Ext.applyIf(ProcessBar, {
		start : function(){
			try{
				var pb = $MsgCenter.getActualTop().ProcessBar;
				pb.start.apply(pb, arguments);
			}catch(error){
			}
		},
		close : function(){
			try{
				var pb = $MsgCenter.getActualTop().ProcessBar;
				pb.close.apply(pb, arguments);
			}catch(error){
			}
		}
	});
})();