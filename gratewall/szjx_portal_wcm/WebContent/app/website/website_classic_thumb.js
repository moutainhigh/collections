Ext.apply(PageContext, {
	tabEnable : true,
	operEnable : false,
	filterEnable : false,
	draggable : true,
	searchEnable : true,
	listOrderEnable : true,
	contextMenuEnable : true,
	serviceId : 'wcm61_website',
	methodName : 'jThumbQuery',
	objectType : WCMConstants.OBJ_TYPE_WEBSITE,
	initParams : {
		"PageSize" : -1,
		"FieldsToHTML" : "",
		"SelectFields" : ""
	}
});

wcm.MyThumbItem = Ext.extend(wcm.ThumbItem, {
	itemType : function(){
		return WCMConstants.OBJ_TYPE_WEBSITE;
	},
	itemInfo : {
		itemId : true,
		rightValue : true
	}			
});

Ext.apply(wcm.MyThumbItem, {
	type : "website"
});
wcm.ThumbItemMgr.registerType(wcm.MyThumbItem["type"], wcm.MyThumbItem);

wcm.ThumbItemMgr.createListeners(wcm.MyThumbItem, function(){
	this.on('beforehover', function(event){
		var dom = $(this["thumb_id_prefix"] + this.getId());
		if(dom.childNodes.length > 0) return;
		dom.innerHTML = $('thumb_template').innerHTML;
	});
	this.on('hover', function(event){
		var dom = $(this["thumb_id_prefix"] + this.getId());
		//filter for the right
		var right = this.getItemInfo()["right"];
		var indexMapping = {preview:3, edit:1, increasepublish:5};
		dom = Element.first(Element.first(dom));
		while(dom){
			var cmd = dom.getAttribute("cmd");
			if(!cmd || indexMapping[cmd] == null) continue;
			if(wcm.AuthServer.checkRight(right, indexMapping[cmd])){				
				Element.show(dom);
			}
			dom = Element.next(dom);
		}
	});
	this.on('dblclick', function(event){
		$MsgCenter.$main({
			objId : this.getId(),
			objType : WCMConstants.OBJ_TYPE_WEBSITE,
			tabType : 'channel'
		}).redirect();
	});
});

var myThumbList = new wcm.ThumbList(wcm.MyThumbItem["type"]);

myThumbList.addCmds({
	preview : wcm.domain.WebSiteMgr['preview'],
	edit : wcm.domain.WebSiteMgr['edit'],
	increasepublish : wcm.domain.WebSiteMgr['increasepublish']
});

Ext.apply(PageContext, {
	getContext : function(){
		var context = this.getContext0();
		Ext.apply(context, {
			relateType : 'websiteInRoot'
		});
		return context;
	},
	getPageParams : function(info){
		this.params = Ext.Json.toUpperCase(location.search.parseQuery());
		Ext.applyIf(this.params, Ext.Json.toUpperCase(PageContext.initParams));
		Ext.applyIf(this.params, {
			HostType : PageContext.intHostType,
			hostId : PageContext.hostId
		});
		return Ext.apply(this.params, Ext.Json.toUpperCase(info));
	},
	pageFilters : (function(){
		if(!PageContext.filterEnable)return null;
		var filters = new wcm.PageFilters({
			displayNum : 6,
			filterType : getParameter('FilterType') || 0
		});
		filters.register([
			//TODO type filters here
		]);
		return filters;
	}()),
	pageTabs : (function(){
		if(!PageContext.tabEnable)return null;
		var tabs = wcm.PageTab.getTabs({
			hostType : PageContext.tabHostType,
			displayNum : 6,
			activeTabType : 'website'
		});
		return tabs;
	}())
});
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.WEBSITE_UNITNAME||'个',
	TypeName : wcm.LANG.WEBSITE_TYPENAME||'站点'
});
Event.observe(window, 'load', function(){
	ClassicList.makeLoad();
	wcm.ListQuery.register({
		container : 'query_box', 
		appendQueryAll : true,
		autoLoad : PageContext.searchEnable,
		items : [
			{name : 'sitedesc', desc : wcm.LANG.WEBSITE_SITEDESC||'显示名称', type : 'string'},
			{name : 'sitename', desc : wcm.LANG.WEBSITE_SITENAME||'唯一标识', type : 'string'},
			{name : 'cruser', desc : wcm.LANG.WEBSITE_CRUSER||'创建者', type : 'string'},
			{name : 'id', desc : wcm.LANG.WEBSITE_ID||'站点ID', type : 'int'}
		],
		callback : function(params){
			PageContext.loadList(Ext.apply(PageContext.params, params));
		}
	});
});
wcm.ListOrder.register({
	container : 'list-order-box', 
	appendTip : true,
	autoLoad : PageContext.listOrderEnable,
	items : [
		{name : 'siteorder', desc : wcm.LANG.WEBSITE_SITEORDER||'默认排序', isDefault : true, isActive : true},
		{name : 'sitedesc', desc : wcm.LANG.WEBSITE_SITEDESC_LIST_ORDER||'显示名称'},
		{name : 'crtime', desc : wcm.LANG.WEBSITE_CRTIME||'创建时间'},
		{name : 'cruser', desc : wcm.LANG.WEBSITE_CRUSER||'创建者'}
	],
	callback : function(sOrder){
		PageContext.loadList(Ext.apply(PageContext.params, {
			orderBy : sOrder,
			SelectIds : myThumbList.getIds()
		}));
	}
});

Ext.apply(PageContext.literator, {
	enable : true,
	doBefore : function(){
		ClassicList.makeLoad();
	}
});
Ext.apply(PageContext.literator.params, {
	tracesite : true
});

function listeningForMyObjs(){
	$MsgCenter.on({
		objType : PageContext.objectType,//WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
		afteradd : function(event){
			//if event.getHost()==PageContext.hostType\hostId then to do, else return;
			var host = event.getHost();
			if(PageContext.hostType != host.getType() 
					|| PageContext.hostId != host.getId()){
				return;
			}
			//PageContext.refreshList(PageContext.params, [event.getIds()]);
			PageContext.updateCurrRows(event.getIds());
		},
		afteredit : function(event){
			//if event.getIds() obtains list to do, else return;
			var objId = event.getObj().getId();
			if(!myThumbList.contain(objId)) return;
			PageContext.updateCurrRows(event.getIds());
		},
		afterdelete : function(event){
			//if event.getIds() obtains list to do, else return;
			var objId = event.getObj().getId();
			if(!myThumbList.contain(objId)) return;
			PageContext.loadList(PageContext.params);
		}
	});
}

Event.observe(window, 'load', function(){
	var dom = $('trash_box');
	if(!dom){
		return;
	}
	Event.observe(dom, 'mouseover',function(){
		Element.addClassName(dom, 'trash_box_hover');
	});
	Event.observe(dom, 'mouseout',function(){
		Element.removeClassName(dom, 'trash_box_hover');
	});
	Event.observe(dom, 'dblclick', function(){
		window.location.href = "../siterecycle/siterecycle_classic_list.html" + location.search;
	});
});

Event.observe(window, 'load', function(){
	if(!PageContext.draggable) return;
	Ext.apply(new wcm.dd.SiteThumbDragDrop({id:'wcm_table_grid'}), {
		_getHint : function(item){
			var objId = item.getId();
			var siteDesc = $(item["desc_id_prefix"] + objId).innerHTML;	
			var showBorder = $(item["cbx_id_prefix"] + objId).checked == true ? 1 : 0;
			var aHtml = [];
			aHtml.push(
				'<div class="thumbDragContainer">',
					'<div class="thumbDrag">',
						'<img src="../images/website/website.gif" border=' + showBorder + ' alt="" align="absmiddle" style="height:30px;">',
						siteDesc,
					'</div>',
				'</div>'
			);
			return aHtml.join("");
		},
		_moveAfter : function(currItem, prevItem){
			if(PageContext.params.ORDERBY) return false;
			if(!confirm(wcm.LANG.WEBSITE_29||"确实要改变站点的排列顺序吗？")) return false;
			new com.trs.web2frame.BasicDataHelper().call('wcm6_website', "changeOrder", {
				SrcSiteId : currItem.getId(),
				DstSiteId : prevItem == null ? 0 : prevItem.getId()
			}, true, function(){
				CMSObj.createFrom(currItem.getItemInfo(), PageContext.getContext()).afteredit();
			});
			return true;
		},
		_moveIntoTrashBox : function(item){
			var info = item.getItemInfo();
			if(wcm.AuthServer.checkRight(info.right, 2)){
				var eventObj = CMSObj.createEvent(info, PageContext.getContext());
				return wcm.domain.WebSiteMgr.recycle(eventObj);
			}
			return false;
		}
	});
});
ClassicList.cfg = {
	toolbar : [
		{
			id : 'website_new',
			fn : function(event, elToolbar){
				wcm.domain.WebSiteMgr['new'](event);
			},
			name : wcm.LANG.WEBSITE_18||'新建',
			desc : wcm.LANG.WEBSITE_19||'新建一个站点',
			isHost : true,
			rightIndex : -2
			
		}, {
			id : 'website_edit',
			fn : function(event, elToolbar){
				wcm.domain.WebSiteMgr['edit'](event);
			},
			name : wcm.LANG.WEBSITE_20||'修改',
			desc : wcm.LANG.WEBSITE_21||'修改这个站点',
			isDisabled : function(event){
				if(!event || event.length() != 1) return true;
			},
			rightIndex : 1
			
		},{
			id : 'website_import',
			fn : function(event, elToolbar){
				wcm.domain.WebSiteMgr['import'](event);
			},
			name : wcm.LANG.WEBSITE_22||'导入站点',
			desc : wcm.LANG.WEBSITE_22||'导入站点',
			isHost : true,
			rightIndex : -2
			
		},{
			id : 'website_export',
			fn : function(event, elToolbar){
				wcm.domain.WebSiteMgr['export'](event);
			},
			name : wcm.LANG.WEBSITE_23||'导出站点',
			desc : wcm.LANG.WEBSITE_23||'导出站点',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 1
			
		},{
			id : 'website_copy',
			fn : function(event, elToolbar){
				wcm.domain.WebSiteMgr['likecopy'](event);
			},
			name : wcm.LANG.WEBSITE_LIKECOPY||'类似创建',
			desc : wcm.LANG.WEBSITE_3||'类似创建站点',
			isDisabled : function(event){
				if(!event || event.length() != 1) return true;
			},
			rightIndex : -2
			
		},{
			id : 'website_delete',
			fn : function(event, elToolbar){
				wcm.domain.WebSiteMgr['recycle'](event);
			},
			name : wcm.LANG.WEBSITE_24||'删除',
			desc : wcm.LANG.WEBSITE_25||'删除这个/些站点',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 2
		},
		'/', {
			id : 'action_refresh',
			fn : function(event, elToolbar){
				PageContext.loadList({CurrPage:1});
			},
			name : wcm.LANG.WEBSITE_26||'刷新',
			desc : wcm.LANG.WEBSITE_27||'刷新列表'
		}
	],
	listTitle : wcm.LANG.WEBSITE_28||'站点列表管理',
	path : ''
}

//全选按钮事件绑定
Event.observe($("selectAll"), 'click', function(){
	$("selectAll").checked ? myThumbList.selectAll() : myThumbList.unselectAll();
});