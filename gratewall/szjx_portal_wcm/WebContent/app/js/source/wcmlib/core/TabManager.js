Ext.ns('wcm.TabManager');
Ext.applyIf(wcm.TabManager, {
	tabs : {},
	tabsInOrder : {},
	buffers : {},
	_preparedTypes : {},
	prepareTabs : function(hostType, jsonTabs){
		var caller = wcm.TabManager;
		hostType = hostType.toLowerCase();
		if(caller._preparedTypes[hostType])return;
		caller._preparedTypes[hostType] = true;
		var arrTabs = caller.buffers[hostType];
		var result = [];
		caller.tabsInOrder[hostType] = result;
		if(!arrTabs) return;
		while(arrTabs.length>0){
			var minOrder = parseFloat(arrTabs[0].order, 10) || (result.length+1);
			var index = 0;
			for(var i=1, n=arrTabs.length; i<n; i++){
				var tmpOrder = parseFloat(arrTabs[i].order, 10);
				if(tmpOrder &&  tmpOrder< minOrder){
					minOrder = tmpOrder;
					index = i;
				}
			}
			var tabItem = jsonTabs[arrTabs[index].type.toLowerCase()];
			if(tabItem){
				result.push(tabItem);
			}
			arrTabs.splice(index, 1);
		}
	},
	getTabsByInfo : function(hostType, bInOrder, context){
		var caller = wcm.TabManager;
		var tabs = caller.getTabs(hostType, bInOrder, context);
		var result = [];
		context = caller.buildContext(context);
		var items = tabs.items;
		for (var i = items.length - 1; i >= 0; i--){
			var tab = tabs.items[i];
			if(!caller._isVisible(tab, context)){
				items.splice(i, 1);
				delete tabs[tab.type.toLowerCase()];
			}
		}
		return tabs;
	},
	getTabs : function(hostType, bInOrder, context){
		var caller = wcm.TabManager;
		hostType = hostType.toLowerCase();
		var tabs = caller.getTabs0(hostType, context);
		if(!bInOrder)return Ext.apply({}, tabs);
		caller.prepareTabs(hostType, tabs);
		return Ext.applyIf({
			items : caller.tabsInOrder[hostType]
		}, tabs);
	},
	getTabs0 : function(hostType, context){
		if(!hostType)return {};
		var caller = wcm.TabManager;
		context = caller.buildContext(context);
		var tabs = caller.tabs[hostType.toLowerCase()];
		var result = {};
		if(tabs==null)return result;
		var tab = null;
		for(var type in tabs){
			tab = tabs[type];
			tab.hostType = hostType;
			tab = context && tab.wrapper ? tab.wrapper(context, tab) : tab;
			result[tab.type.toLowerCase()] = tab;
		}
		return result;
	},
	getTab : function(hostType, type, context){
		if(!hostType || !type)return {};
		var caller = wcm.TabManager;
		var tabs = caller.getTabs0(hostType, context);
		return tabs[type.toLowerCase()];
	},
	_getParameter : function(params, key){
		return params[key.toUpperCase()];
	},
	buildContext : function(context){
		if(!Ext.isString(context))return context;
		var caller = wcm.TabManager;
		var params = Ext.Json.toUpperCase(context.parseQuery());
		var bIsChannel = !!caller._getParameter(params, "ChannelId");
		var bIsWebSite = !!caller._getParameter(params, "SiteId");
		var hostType = caller._getParameter(params, "HostType") || 
				(bIsChannel ? WCMConstants.OBJ_TYPE_CHANNEL : 
				(bIsWebSite ? WCMConstants.OBJ_TYPE_WEBSITE : 
					WCMConstants.OBJ_TYPE_WEBSITEROOT));
		var intHostType =caller._getParameter(params, "IntHostType") || 
				(bIsChannel ? 101 : 
				(bIsWebSite ? 103 : 1));
		var hostId = caller._getParameter(params, "ChannelId") 
				|| caller._getParameter(params, "SiteId")
				|| caller._getParameter(params, "RootId")
				|| caller._getParameter(params, "SiteType");
		var tabHostType = caller._getParameter(params, "TabHostType") ||
				(bIsChannel ? WCMConstants.TAB_HOST_TYPE_CHANNEL : 
				(bIsWebSite ? WCMConstants.TAB_HOST_TYPE_WEBSITE :
					WCMConstants.TAB_HOST_TYPE_WEBSITEROOT));
		return {
			isChannel : bIsChannel,
			host : {
				objType : hostType,
				objId : hostId,
				intObjType : intHostType,
				right : caller._getParameter(params, "RightValue"),
				isVirtual : caller._getParameter(params, "IsVirtual")
			},
			tabHostType : tabHostType,
			params : params
		};
	},
	_isVisible : function(tab, context){
		var right = context.host.right || context.right;
		return wcm.AuthServer.checkRight(right, tab.rightIndex);
	},
	getDefaultTab : function(hostType, context){
		var caller = wcm.TabManager;
		context = caller.buildContext(context);
		var tabs = caller.getTabs(hostType, true, context);
		for(var type in tabs){
			var tab = tabs[type];
			if(tab==null || !caller.isTabItem(tab))continue;
			if(!caller._isVisible(tab, context)) continue;
			if(Ext.isFunction(tab.isVisible) 
						&& !tab.isVisible(context))continue;
			if(caller.isDefault(tab, hostType))
				return tab;
		}
		if(!tabs.items)return null;
		for(var i=0,n=tabs.items.length;i<n;i++){
			var tab = tabs.items[i];
			if(tab==null)continue;
			if(!caller._isVisible(tab, context)) continue;
			if(Ext.isFunction(tab.isVisible) 
						&& !tab.isVisible(context))continue;
			return tab;
		}
		return null;
	},
	exec : function(tabItem, opt){
		if(!tabItem)return;
		var caller = wcm.TabManager;
		(tabItem.fn || caller._defExec).call(tabItem, tabItem, opt);
		//记忆Tab类型
		caller.rememberTabType(tabItem);
	},
	getTabUrl : function(tab){
		if(tab.fittable===false){
			return [
				WCMConstants.WCM6_PATH,
				'include/tab_fittable.html?tabUrl=',
				encodeURIComponent(tab.url),
				'&tabType=',
				encodeURIComponent(tab.type)
			].join('');
		}
		return tab.url;
	},
	_defExec : function(tabItem, opt){
		var caller = wcm.TabManager;
		var url = caller.getTabUrl(tabItem);
		var cJoin = url.indexOf('?')==-1?'?':'&';
		if(Ext.isString(opt)){//from main
			$('main').src = url + cJoin + opt;
			return;
		}
		if(opt===true || window==$MsgCenter.getActualTop()){//from main
			try{
				$('main').src = url + cJoin + 
					$('main').contentWindow.location.search.substring(1);
				return;
			}catch(err){
			}
		}
		var sSearch = location.search.substring(1);
		var nQIdx = url.indexOf('?');
		if(nQIdx==-1){
			location.href = url + cJoin + sSearch;
			return;
		}
		var params = Ext.Json.toUpperCase(sSearch.parseQuery());
		var params2 = url.substring(nQIdx + 1).parseQuery();
		for(var paramName in params2){
			if(params[paramName.toUpperCase()]){
				sSearch = sSearch.replace(new RegExp(paramName+'=[^&]*(&|$)', 'ig'), '');
			}
		}
		location.href = url + cJoin + sSearch;
	},
	register : function(infos){
		var caller = wcm.TabManager;
		if(Ext.isArray(infos)){
			infos.each(function(info){
				caller.register(info);
			});
			return caller;
		}
		var buffer = caller.buffers[infos.hostType.toLowerCase()];
		if(buffer==null){
			buffer = caller.buffers[infos.hostType.toLowerCase()] = [];
		}
		var tabs = caller.tabs[infos.hostType.toLowerCase()];
		if(!tabs){
			tabs = caller.tabs[infos.hostType.toLowerCase()] = {};
		}
		var items = infos.items;
		if(Ext.isArray(items)){
			items.each(function(item, index){
				item = Ext.applyIf(item, infos);
				delete item.items;
				//由于一个tabItem可能注册到多个节点下，为了避免相互之间的影响，故需要clone
				item = Ext.apply({}, item);
				buffer.push(item);
				tabs[item.type.toLowerCase()] = item;
			});
		}else{
			items = Ext.applyIf(items, infos);
			delete items.items;
			//由于一个tabItem可能注册到多个节点下，为了避免相互之间的影响，故需要clone
			items = Ext.apply({}, items);
			buffer.push(items);
			tabs[items.type.toLowerCase()] = items;
		}
		return caller;
	},
	isTabItem : function(tabItem){
		return Ext.isObject(tabItem) && tabItem.hostType && tabItem.type;
	},
	maxDisplayNum : 100,
	showAll : function(){
		var actualTop = $MsgCenter.getActualTop();
		return !!actualTop.wcm.TabManager._showAll;
	},
	rememberShowAll : function(_bShowAll){
		var actualTop = $MsgCenter.getActualTop();
		actualTop.wcm.TabManager._showAll = _bShowAll;
	},
	defaultTabs : {},
	rememberTabType : function(tabItem){
		var actualTop = $MsgCenter.getActualTop();
		var hostType = tabItem.hostType.toLowerCase();
		var defaults = actualTop.wcm.TabManager.defaultTabs;
		defaults[hostType] = tabItem.type;
	},
	isDefault : function(tabItem, hostType){
		hostType = hostType.toLowerCase();
		var actualTop = $MsgCenter.getActualTop();
		var defaults = actualTop.wcm.TabManager.defaultTabs;
		if(!defaults[hostType])return tabItem.isdefault;
		return tabItem.type.equalsI(defaults[hostType]);
	},
	calDisplayNum : function(nDisplayNum){
		var caller = wcm.TabManager;
		if(caller.showAll()){
			return caller.maxDisplayNum;
		}
		return nDisplayNum;
	},
	createWrapper : function(oTabItem, fWrapper){
		if(!oTabItem || !fWrapper)return;
		var oldWrapper = oTabItem.wrapper;
		oTabItem.wrapper = oldWrapper ? function(context, tabItem){
			return fWrapper(context, oldWrapper(context, tabItem));
		} : fWrapper;
	},
    myCreateInterceptor : function(thisFcn, fcn, scope){
        if(typeof fcn != "function"){
            return thisFcn;
        }
        var method = thisFcn;
        return function() {
			var result = fcn.apply(scope || this || window, arguments);
            if(Ext.isBoolean(result)){
                return result;
            }
            return method.apply(this || window, arguments);
        };
    },
	createInterceptor : function(item){
		var caller = wcm.TabManager;
		if(!Ext.isFunction(item.isVisible))return;
		var tabItem = caller.getTab(item.hostType, item.type);
		if(tabItem==null)return;
		tabItem.isVisible = tabItem.isVisible || function(){
			return true;
		};
		tabItem.isVisible = caller.myCreateInterceptor(tabItem.isVisible, item.isVisible);
	}
});
