Ext.apply(PageContext, {
	tabEnable : true,
	operEnable : false,
	filterEnable : false,
	draggable : true,
	searchEnable : true,
	listOrderEnable : true,
	contextMenuEnable : true,
	serviceId : 'wcm61_channel',
	methodName : 'jThumbQuery',
	objectType : WCMConstants.OBJ_TYPE_CHANNEL,
	initParams : {
		"PageSize" : -1,
		"FieldsToHTML" : "",
		"SelectFields" : ""
	}
});


wcm.MyThumbItem = Ext.extend(wcm.ThumbItem, {
	itemType : function(){
		return WCMConstants.OBJ_TYPE_CHANNEL;
	},
	itemInfo : {
		itemId : true,
		rightValue : true,
		isVirtual : true,
		chnlType : true
	}
});
Ext.apply(wcm.MyThumbItem, {
	type : "channel"
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
		var indexMapping = {preview:15, edit:13, increasepublish:17};
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
	this.on('hover', function(event){
		var dom = $(this["thumb_id_prefix"] + this.getId());
		//filter for the special channel
		var hideChnlTypes = [1,2,11];
		var chnlType = this.getItemInfo()["chnlType"];
		var bHideOpers = hideChnlTypes.include(chnlType);
		if(!bHideOpers) return;
		var hideOpers = ['preview', 'increasepublish'];
		dom = Element.first(Element.first(dom));
		while(dom){
			if(hideOpers.include(dom.getAttribute("cmd"))){
				Element.hide(dom);
			}
			dom = Element.next(dom);
		}
	});	
	this.on('dblclick', function(event){
		var isVirtual = this.getItemInfo()["isVirtual"];
		$MsgCenter.$main({
			objId : this.getId(),
			objType : WCMConstants.OBJ_TYPE_CHANNEL,
			tabType : Ext.isTrue(isVirtual) ? 'document' : 'channel'
		}).redirect();
	});
});

var myThumbList = new wcm.ThumbList(wcm.MyThumbItem["type"]);

myThumbList.addCmds({
	preview : wcm.domain.ChannelMgr['preview'],
	edit : wcm.domain.ChannelMgr['edit'],
	increasepublish : wcm.domain.ChannelMgr['increasepublish']
});

Ext.apply(PageContext, {
	hostType : (function(){
		return !!PageContext.getParameter("channelid")?
				WCMConstants.OBJ_TYPE_CHANNELMASTER : WCMConstants.OBJ_TYPE_WEBSITE;
	})(),
	getContext : function(){
		var context = this.getContext0();
		var bIsChannel = !!getParameter("ChannelId");
		Ext.apply(context, {
			relateType : bIsChannel ? 'channelHost' : 'websiteHost'
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
		return filters;
	}()),
	pageTabs : (function(){
		if(!PageContext.tabEnable)return null;
		var tabs = wcm.PageTab.getTabs({
			hostType : PageContext.tabHostType,
			displayNum : 6,
			activeTabType : 'channel'
		});
		return tabs;
	}())
});
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.CHANNEL_UNITNAME||'个',
	TypeName : wcm.LANG.CHANNEL_TYPENAME||'栏目'
});
Event.observe(window, 'load', function(){
	ClassicList.makeLoad();
	wcm.ListQuery.register({
		container : 'query_box', 
		appendQueryAll : true,
		autoLoad : PageContext.searchEnable,
		items : [
			{name : 'chnldesc', desc : wcm.LANG.CHANNEL_DESC||'显示名称', type : 'string'},
			{name : 'chnlname', desc : wcm.LANG.CHANNEL_CHNLNAME||'唯一标识', type : 'string'},
			{name : 'cruser', desc : wcm.LANG.CHANNEL_CRUSER||'创建者', type : 'string'},
			{name : 'id', desc : wcm.LANG.CHANNEL_ID||'栏目ID', type : 'int'}
		],
		callback : function(params){
			PageContext.loadList(params);
		}
	});
});
wcm.ListOrder.register({
	container : 'list-order-box', 
	appendTip : true,
	autoLoad : PageContext.listOrderEnable,
	items : [
		{name : 'chnlorder', desc : wcm.LANG.CHANNEL_CHNLORDER||'默认排序', isDefault : true, isActive : true},
		{name : 'chnltype', desc : wcm.LANG.CHANNEL_CHNLTYPE||'栏目类型'},
		{name : 'chnldesc', desc : wcm.LANG.CHANNEL_DESC_LIST_ORDER||'显示名称'},
		{name : 'crtime', desc : wcm.LANG.CHANNEL_CRTIME||'创建时间'},
		{name : 'cruser', desc : wcm.LANG.CHANNEL_CRUSER||'创建者'}
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
	tracesite : true,
	tracesitetype : true
});

function listeningForMyObjs(){
	$MsgCenter.on({
		objType : PageContext.objectType,//WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
		afteradd : function(event){
			//if event.getHost()==PageContext.hostType\hostId then to do, else return;
			var host = event.getHost();
			/*
			*本来这里hostType就应该是满足要求的，但由于其它地方实现的原因，
			*导致这里必须做一些兼容，处理了OBJ_TYPE_CHANNELMASTER和OBJ_TYPE_CHANNEL
			*/
			if((PageContext.hostType != host.getType()
					&& WCMConstants.OBJ_TYPE_CHANNELMASTER != host.getType()
					&& WCMConstants.OBJ_TYPE_CHANNEL != host.getType())
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
		},
		aftermove : function(event){
			//if event.getIds() obtains list to do, else return;
			var objId = event.getObj().getId();
			if(!myThumbList.contain(objId)) return;
			PageContext.loadList(PageContext.params);
		}
	});
}
function listeningForHosts(){
	$MsgCenter.on({
		objType : WCMConstants.OBJ_TYPE_CHANNEL,
		afteredit : function(event){
			//监听从树上过来的消息
			if(PageContext.hostType!=WCMConstants.OBJ_TYPE_CHANNELMASTER)return;
			//if event.getObj().getId() equals hostId to do, else return;
			if(event.getObj().getId()!=PageContext.hostId)return;
			//if list has some selected items may directly return;
			event.getObj().afterselect();
		}
	});
	$MsgCenter.on({
		objType : PageContext.hostType,//WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
		afteredit : function(event){
			var host = event.getObj();
			if(host.getId()!=PageContext.hostId)return;
			if(event.getContext()==null){
				host.setContext(PageContext.getContext());
			}
			host.afterselect();
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
		window.location.href = "../chnlrecycle/chnlrecycle_classic_list.html" + location.search;
	});
});

//init the drag
Event.observe(window, 'load', function(){
	if(!PageContext.draggable) return;
	var chnlThumbDragDrop = new wcm.dd.ChannelThumbDragDrop({
		id:'wcm_table_grid', 
		captureEnable:false
	});
	chnlThumbDragDrop.addListener('startdrag', function(){
		if(!top.DragAcross){
			top.DragAcross = {};
		}
		Ext.apply(top.DragAcross,{
			ObjectType : 101 ,
			ObjectId : this.item.getId()
		});
	});
	chnlThumbDragDrop.addListener('dispose', function(){
		top.DragAcross = null;
	});
	Ext.apply(chnlThumbDragDrop, {
		_getHint : function(item){
			var info = item.getItemInfo();
			var objId = info['objId'];
			var chnlDesc = $(item["desc_id_prefix"] + objId).innerHTML;	
			var showBorder = $(item["cbx_id_prefix"] + objId).checked == true ? 1 : 0;
			var aHtml = [];
			aHtml.push(
				'<div class="thumbDragContainer">',
					'<div class="thumbDrag">',
						'<img src="images/channel/channel_' + info['chnlType'] + '.gif" border=' + showBorder + ' alt="" align="absmiddle" style="height:30px;">',
						chnlDesc,
					'</div>',
				'</div>'
			);
			return aHtml.join("");
		},
		_moveAfter : function(currItem, prevItem){
			if (PageContext.params.ORDERBY) return false;
			var info = currItem.getItemInfo();
			if(info["chnlType"] == 2 || info["chnlType"] == 1) { //头条和图片总是位于前面，不需要移动位置
				return false;
			}
			if(prevItem){
				var preInfo = prevItem.getItemInfo();
				if(preInfo["chnlType"] == 2) { //头条和图片总是位于前面，其他栏目无需放到其前后
					return false;
				}
			}
			if(!confirm(wcm.LANG.CHANNEL_45||'确实要改变当前拖动栏目的排列顺序吗?')) {
				return false;
			}
			BasicDataHelper.call('wcm6_channel', "changeOrder", {
				SrcChannelId : currItem.getId(),
				DstChannelId : prevItem == null ? 0 : prevItem.getId()
			}, true, function(){
				CMSObj.createFrom(info, PageContext.getContext()).afteredit();
			});
			return true;
		},
		_moveTo : function(currItem, dstItem){
			var srcInfo = currItem.getItemInfo();
			var dstInfo = dstItem.getItemInfo();
			if(dstInfo['chnlType'] != 0) { 
				Ext.Msg.$fail(wcm.LANG.CHANNEL_46||'无法将栏目移动成为头条、图片等特殊栏目的子栏目！');
				return false;
			}
			if(wcm.AuthServer.checkRight(srcInfo["right"], 12)//12：移动
					&& wcm.AuthServer.checkRight(dstInfo["right"], 11)){ //11：新建
				var srcChnlDesc = $(currItem["desc_id_prefix"] + srcInfo["objId"]).innerHTML;	
				var dstChnlDesc = $(dstItem["desc_id_prefix"] + dstInfo["objId"]).innerHTML;	
				var sMsg = String.format("确实要将栏目{0}移动为栏目{1}的子栏目吗?",srcChnlDesc,dstChnlDesc);
				Ext.Msg.confirm(sMsg, {
					ok : function(){
						BasicDataHelper.call('wcm6_channel', "moveAsChild", {
							SrcChannelIds : currItem.getId(),
							DstChannelId : dstItem.getId()
						}, true, function(){
							CMSObj.createFrom(srcInfo, PageContext.getContext()).afteredit();
						});
					},
					cancel : function(){
						return false;
					}
				});	
			}
			return false;
		},
		_moveIntoTrashBox : function(item){
			var info = item.getItemInfo();
			if(wcm.AuthServer.checkRight(info.right, 2)){
				var eventObj = CMSObj.createEvent(info, PageContext.getContext());
				return wcm.domain.ChannelMgr.trash(eventObj);
			}
			return false;
		}
	});

	var accrossDragger = new wcm.dd.AccrossFrameDragDrop(chnlThumbDragDrop);
	Ext.apply(accrossDragger, {
		getWinInfos : function(){
			return [
				{win : top}, 
				{
					win : top.$('nav_tree').contentWindow,
					endDrag : function(event, target, opt){
						if(!top.DragAcross || !top.DragAcross.TargetFolderId) return;
						var srcObjId = top.DragAcross.ObjectId;
						var dstObjId = top.DragAcross.TargetFolderId;
						var isSite = top.DragAcross.TargetFolderType == 103;
						var oPostData = {srcChannelIds : srcObjId};
						if(isSite){
							oPostData.DstSiteId = dstObjId;
						}else{
							oPostData.DstChannelId = dstObjId;
						}
						BasicDataHelper.call('wcm6_channel', 'moveAsChild', oPostData, true, function(){
							var info = {objId : srcObjId, objType : WCMConstants.OBJ_TYPE_CHANNEL};
							var context = {dstObjectId : dstObjId, isSite : isSite};
							CMSObj.createFrom(info, context).aftermove();
						});						
					}
			}];	
		}
	});
});
ClassicList.cfg = {
	toolbar : [
		{
			id : 'channel_new',
			fn : function(event, elToolbar){
				wcm.domain.ChannelMgr['new'](event);
			},
			name : wcm.LANG.CHANNEL_34||'新建',
			isHost : true,
			rightIndex : 11
			
		}, {
			id : 'channel_edit',
			fn : function(event, elToolbar){
				wcm.domain.ChannelMgr['edit'](event);
			},
			name : wcm.LANG.CHANNEL_36||'修改',
			isDisabled : function(event){
				if(!event || event.length() != 1) return true;
			},
			rightIndex : 13
			
		},{
			id : 'channel_import',
			fn : function(event, elToolbar){
				wcm.domain.ChannelMgr['import'](event);
			},
			name : wcm.LANG.CHANNEL_IMPORT||'导入栏目',
			isHost : true,
			rightIndex : 11
			
		},{
			id : 'channel_export',
			fn : function(event, elToolbar){
				wcm.domain.ChannelMgr['export'](event);
			},
			name : wcm.LANG.CHANNEL_38||'导出栏目',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 13
			
		},{
			id : 'channel_copy',
			fn : function(event, elToolbar){
				wcm.domain.ChannelMgr['likecopy'](event);
			},
			name : wcm.LANG.CHANNEL_LIKECOPY||'类似创建',
			isDisabled : function(event){
				if(!event || event.length() != 1) return true;
			},
			rightIndex : 13
			
		},{
			id : 'channel_delete',
			fn : function(event, elToolbar){
				wcm.domain.ChannelMgr['trash'](event);
			},
			name : wcm.LANG.CHANNEL_39||'删除',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 12
		},
		'/', {
			id : 'action_refresh',
			fn : function(event, elToolbar){
				PageContext.loadList({CurrPage:1});
			},
			name : wcm.LANG.CHANNEL_41||'刷新'
		}
	],
	listTitle : wcm.LANG.CHANNEL_43||'栏目列表管理',
	path : ''
}

//全选按钮事件绑定
Event.observe($("selectAll"), 'click', function(){
	$("selectAll").checked ? myThumbList.selectAll() : myThumbList.unselectAll();
});