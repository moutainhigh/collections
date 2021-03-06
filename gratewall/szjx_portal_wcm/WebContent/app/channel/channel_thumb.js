Ext.apply(PageContext, {
	tabEnable : true,
	operEnable : true,
	filterEnable : true,
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
		Ext.apply(context.host, {
			channelType : bIsChannel ? PageContext.getParameter("ChannelType") : ''
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
	UnitName : wcm.LANG.CHANNEL_UNITNAME||'???',
	TypeName : wcm.LANG.CHANNEL_TYPENAME||'??????'
});

wcm.ListQuery.register({
	container : 'search',
	appendQueryAll : true,
	autoLoad : PageContext.searchEnable,
	items : [
		{name : 'chnldesc', desc : wcm.LANG.CHANNEL_DESC||'????????????', type : 'string'},
		{name : 'chnlname', desc : wcm.LANG.CHANNEL_CHNLNAME||'????????????', type : 'string'},
		{name : 'cruser', desc : wcm.LANG.CHANNEL_CRUSER||'?????????', type : 'string'},
		{name : 'id', desc : wcm.LANG.CHANNEL_ID||'??????ID', type : 'int'}
	],
	callback : function(params){
		PageContext.loadList(params);
	}
});

wcm.ListOrder.register({
	container : 'list-order-box',
	appendTip : true,
	autoLoad : PageContext.listOrderEnable,
	items : [
		{name : 'chnlorder', desc : wcm.LANG.CHANNEL_CHNLORDER||'????????????', isDefault : true, isActive : true},
		{name : 'chnltype', desc : wcm.LANG.CHANNEL_CHNLTYPE||'????????????'},
		{name : 'chnldesc', desc : wcm.LANG.CHANNEL_DESC_LIST_ORDER||'????????????'},
		{name : 'crtime', desc : wcm.LANG.CHANNEL_CRTIME||'????????????'},
		{name : 'cruser', desc : wcm.LANG.CHANNEL_CRUSER||'?????????'}
	],
	callback : function(sOrder){
		PageContext.loadList(Ext.apply(PageContext.params, {
			orderBy : sOrder,
			SelectIds : myThumbList.getIds()
		}));
	}
});

Ext.apply(PageContext.literator, {
	enable : true
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
			*????????????hostType?????????????????????????????????????????????????????????????????????
			*?????????????????????????????????????????????OBJ_TYPE_CHANNELMASTER???OBJ_TYPE_CHANNEL
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
			//??????????????????????????????
			if(PageContext.hostType!=WCMConstants.OBJ_TYPE_CHANNELMASTER)return;
			//if event.getObj().getId() equals hostId to do, else return;
			var host = event.getObj();
			if(host.getId() != PageContext.hostId) return;
			myThumbList.notify(true);
		}
	});
	$MsgCenter.on({
		objType : PageContext.hostType,//WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
		afteredit : function(event){
			var host = event.getObj();
			if(host.getId()!=PageContext.hostId)return;
			myThumbList.notify(true);
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
		window.location.href = "../chnlrecycle/chnlrecycle_list.html" + location.search;
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
			if(info["chnlType"] == 2 || info["chnlType"] == 1) { //?????????????????????????????????????????????????????????
				Ext.Msg.$fail(wcm.LANG.CHANNEL_62||'?????????????????????????????????????????????');
				return false;
			}
			//get the after el
			var dom = Element.first($('wcm_table_grid'));
			if(prevItem){
				dom = Element.next(prevItem.getDom());
			}
			if(dom){
				var nChnlType = dom.getAttribute("chnlType");
				if(nChnlType == 2 || nChnlType == 1) { //?????????????????????????????????????????????????????????????????????
					Ext.Msg.$fail(wcm.LANG.CHANNEL_63||'??????????????????????????????????????????????????????');
					return false;
				}
			}
			if(!confirm(wcm.LANG.CHANNEL_45||'????????????????????????????????????????????????????')) {
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
				Ext.Msg.$fail(wcm.LANG.CHANNEL_46||'????????????????????????????????????????????????????????????????????????');
				return false;
			}
			if(wcm.AuthServer.checkRight(srcInfo["right"], 12)//12?????????
					&& wcm.AuthServer.checkRight(dstInfo["right"], 11)){ //11?????????
				var srcChnlDesc = $(currItem["desc_id_prefix"] + srcInfo["objId"]).innerHTML;
				var dstChnlDesc = $(dstItem["desc_id_prefix"] + dstInfo["objId"]).innerHTML;
				var sMsg = String.format("??????????????????{0}???????????????{1}?????????????",srcChnlDesc,dstChnlDesc);
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
			if(wcm.AuthServer.checkRight(info.right, 12)){
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

//????????????????????????
Event.observe($("selectAll"), 'click', function(){
	$("selectAll").checked ? myThumbList.selectAll() : myThumbList.unselectAll();
});