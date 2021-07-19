Ext.apply(PageContext, {
	tabEnable : true,
	operEnable : false,
	filterEnable : false,
	draggable : true,
	searchEnable : false,
	listOrderEnable : true,
	contextMenuEnable : true,
	serviceId : 'wcm61_photo',
	methodName : 'jThumbQuery',
	/**/
	objectType : WCMConstants.OBJ_TYPE_PHOTO,
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"PageSize"	: parent.m_CustomizeInfo.listPageSize.paramValue || "20"
	}
});

wcm.MyThumbItem = Ext.extend(wcm.ThumbItem, {
	itemType : function(){
		return WCMConstants.OBJ_TYPE_PHOTO;
	},
	itemInfo : {
		docId : true,
		itemId : true,
		rightValue : true
	}
});

Ext.apply(wcm.MyThumbItem, {
	type : "photo"
});

wcm.ThumbItemMgr.registerType(wcm.MyThumbItem["type"], wcm.MyThumbItem);
var myThumbList = new wcm.ThumbList(wcm.MyThumbItem["type"]);
wcm.ThumbItemMgr.createListeners(wcm.ThumbItem, function(){
	this.on('beforeclick', function(event){
		var dom = Event.element(event);
		while(dom && dom.tagName != 'BODY'){
			if(dom.getAttribute('itemId')) return true;
			if(Element.hasClassName(dom, 'thumb')){
				return false;
			}
			dom = dom.parentNode;
		}
		return true;
	});
});

Ext.apply(PageContext, {
	getContext : function(){
		var context = this.getContext0();
		var bIsChannel = !!getParameter("ChannelId");
		var bIsSite = !!getParameter("SiteId");
		Ext.apply(context, {
			relateType : bIsChannel ? 'photoInChannel' :
				(bIsSite ? 'photoInSite' : 'photoInRoot')
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
		]);
		return filters;
	}()),
	pageTabs : (function(){
		if(!PageContext.tabEnable)return null;
		var tabs = wcm.PageTab.getTabs({
			hostType : PageContext.tabHostType,
			displayNum : 6,
			activeTabType : 'document'
		});
		return tabs;
	}()),
	gridFunctions : function(){
		/*
		var myMgr = wcm.domain.photoMgr;
		wcm.Grid.addFunction('edit', function(event){
			myMgr['edit'](event);
		});
		//*/
	},
	_buildParams : function(wcmEvent, actionType){
		if(wcmEvent.length() <= 0) return; 
		if(actionType=='save' && wcmEvent.getObjs().getType()==WCMConstants.OBJ_TYPE_PHOTO){
			var obj = wcmEvent.getObjs().getAt(0);
			var host = wcmEvent.getHost();
			return {
				Force : {
					ObjectId : obj ? obj.getDocId() : 0
				},
				ChannelId : PageContext.getParameter("ChannelId") || 0,
				SiteId : PageContext.getParameter("SiteId") || 0
			}
		}
	}
});
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.PHOTO_CONFIRM_7 || '个',
	TypeName : wcm.LANG.PHOTO_CONFIRM_8 || '图片'
});
//检索框信息
Event.observe(window, 'load', function(){
	ClassicList.makeLoad();
	wcm.ListQuery.register({
		/*检索控件追加到的容器*/
		container : 'query_box', 
		/*是否追加“全部”这个检索项, default to false*/
		appendQueryAll : true,
		/*是否自动加载, default to true*/
		autoLoad : PageContext.searchEnable,
		/*检索项的内容*/
		items : [
			//*
			{name :'DOCTITLE', desc : wcm.LANG.PHOTO_CONFIRM_101 || '显示名称', type : 'string'}
			//*/
		],
		/*执行检索按钮时执行的回调函数*/
		callback : function(params){
			PageContext.loadList(Ext.apply({
				//some params must remember here
			}, params));
		}
	});

	wcm.ListOrder.register({
		container : 'list-order-box', 
		appendTip : false,
		autoLoad : PageContext.listOrderEnable,
		items : [
			{name : 'WCMDOCUMENT.crtime', desc : (wcm.LANG.PHOTO_CONFIRM_41 || '创建时间'), isActive : true},
			{name : 'WCMDOCUMENT.doctitle', desc : (wcm.LANG.PHOTO_CONFIRM_42 || '图片标题')},
			{name : 'WCMDOCUMENT.cruser', desc : (wcm.LANG.PHOTO_CONFIRM_43 || '创建者')}
		],
		callback : function(sOrder){
			PageContext.loadList(Ext.apply(PageContext.params, {
				orderBy : sOrder,
				SelectIds : myThumbList.getIds()
			}));
		}
	});
});
//路径信息
Ext.apply(PageContext.literator, {
	enable : false,
	width : 350,
	doBefore : function(){
		ClassicList.makeLoad();
	}
});

function showPhotoSearch(){
	var channelId = getParameter("channelId") || 0 ;
	var siteId = getParameter("siteId") || 0 ;
	wcm.CrashBoarder.get('Dialog_Photo_QuickSearcher').show({
		title : wcm.LANG.PHOTO_CONFIRM_40 || '图片快速检索',
		src : WCMConstants.WCM6_PATH + 'photo/photo_search.htm',
		width: '400px',
		height: '350px',
		top : '40px',
		left : '750px',
		reloadable : false,
		params : [channelId, siteId],
		maskable : true,
		callback : function(_args){	
			if(!_args) return;	
			var queryData = {};
			if(_args["DocStatus"] == 1){
				queryData["DocStatus"] = "10";
			}else if(_args["DocStatus"] == 2){
				queryData["NotDocStatus"] = "10";
			}
			delete _args["DocStatus"];		
			Object.extend(queryData,_args);
			if(PageContext.params["DOCSTATUS"])
				delete PageContext.params["DOCSTATUS"];
			if(PageContext.params["NOTDOCSTATUS"])
				delete PageContext.params["NOTDOCSTATUS"];
			//alert(Ext.toSource(PageContext.params));
			PageContext.loadList(Ext.apply(PageContext.params,queryData));
		}
	});
}
function resizeIfNeed(_imgloaded){
	if(_imgloaded){
		var height = _imgloaded.height;
		var width = _imgloaded.width;		
		if(height > 124 || width > 97){			
			if(height > width){
				_imgloaded.height = 110;				
				width = 110*width/height;
				height = 110;
			}else{
				_imgloaded.width = 97;					
				height = 97 * height/width;
				width = 97;
			}
		}
		_imgloaded.style.left = Math.floor((180-width)/2)+"px";
		_imgloaded.style.top = Math.floor((150-height)/2)+"px";
	}
}
function $openMaxWin(_sUrl, _sName, _bResizable){
	var nWidth	= window.screen.width - 12;//document.body.clientWidth;
	var nHeight = window.screen.height - 60;//document.body.clientHeight;
	var nLeft	= 0;//(window.screen.availWidth - nWidth) / 2;
	var nTop	= 0;//(window.screen.availHeight - nHeight) / 2;
	var sName	= _sName || "";

	var oWin = window.open(_sUrl, sName, "resizable=" + (_bResizable == true ? "yes" : "no") + ",top=" + nTop + ",left=" + nLeft + ",menubar =no,toolbar =no,width=" + nWidth + ",height=" + nHeight + ",scrollbars=yes,location =no,titlebar=no");
	if(oWin)oWin.focus();
}

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_PHOTO,
	afteradd : function(event){
		PageContext.loadList(Ext.applyIf({
			CURRDOCID : event.getIds().join(),
			SELECTIDS : ''
		}, PageContext.params));
	}
});
ClassicList.cfg = {
	toolbar : [
		{
			id : 'photo_new',
			fn : function(event, elToolbar){
				wcm.domain.photoMgr.upload(event);
			},
			name : wcm.LANG.PHOTO_CONFIRM_94 ||'上传',
			desc : wcm.LANG.PHOTO_CONFIRM_78 ||'上传新图片',
			rightIndex : 31,
			isHost	: true
		}, {
			id : 'photo_delete',
			fn : function(event, elToolbar){
				wcm.domain.photoMgr['delete'](event);
			},
			name : wcm.LANG.PHOTO_CONFIRM_96 ||'删除',
			desc : wcm.LANG.PHOTO_CONFIRM_97 ||'删除这个/些图片',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 33
		},{
			id : 'photo_move',
			fn : function(event, elToolbar){
				wcm.domain.photoMgr['move'](event);
			},
			name : wcm.LANG.PHOTO_CONFIRM_75 || '重新分类',
			desc : wcm.LANG.PHOTO_CONFIRM_75 || '重新分类',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 33
		},{
			id : 'photo_quote',
			fn : function(event, elToolbar){
				wcm.domain.photoMgr['quote'](event);
			},
			name : wcm.LANG.PHOTO_CONFIRM_76 || '增加分类',
			desc : wcm.LANG.PHOTO_CONFIRM_76 || '增加分类',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 34
		},{
			id : 'photo_preview',
			fn : function(event, elToolbar){
				wcm.domain.photoMgr['preview'](event);
			},
			name : wcm.LANG.PHOTO_CONFIRM_102 ||'预览',
			desc : wcm.LANG.PHOTO_CONFIRM_103 ||'预览这幅/些图片',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 38
		},{
			id : 'photo_publish',
			fn : function(event, elToolbar){
				//TODO 提示发布
				//wcm.domain.photoMgr['publish'](event);basicpublish
				wcm.domain.photoMgr['basicpublish'](event);
			},
			name : wcm.LANG.PHOTO_CONFIRM_105 ||'发布',
			desc : wcm.LANG.PHOTO_CONFIRM_106 ||'发布这幅/些图片',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 39
		},{
			id : 'photo_changestatus',
			fn : function(event, elToolbar){
				//TODO 提示发布
				wcm.domain.photoMgr['changestatus'](event);
			},
			name : wcm.LANG.PHOTO_CONFIRM_152 ||'改变状态',
			desc : wcm.LANG.PHOTO_CONFIRM_153 ||'改变这幅/些图片的状态',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 35
		},{
			id : 'photo_detailpublish',
			fn : function(event, elToolbar){
				//TODO 提示发布
				wcm.domain.photoMgr['detailpublish'](event);
			},
			name : wcm.LANG.PHOTO_CONFIRM_154 ||'仅发布细览',
			desc : wcm.LANG.PHOTO_CONFIRM_155 ||'仅发布这幅/些图片的细览',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 39
		},{
			id : 'photo_recallpublish',
			fn : function(event, elToolbar){
				//TODO 提示发布
				wcm.domain.photoMgr['recallpublish'](event);
			},
			name : wcm.LANG.PHOTO_CONFIRM_156 ||'撤销发布',
			desc : wcm.LANG.PHOTO_CONFIRM_157 ||'撤销发布这幅/些图片,撤回已发布目录或页面',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 39
		},{
			id : 'photo_setright',
			fn : function(event, elToolbar){
				//TODO 提示发布
				wcm.domain.photoMgr['setright'](event);
			},
			name : wcm.LANG.PHOTO_CONFIRM_158 ||'设置权限',
			desc : wcm.LANG.PHOTO_CONFIRM_159 ||'设置这幅/些图片的权限',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 61
		},{
			id : 'photo_docpositionset',
			fn : function(event, elToolbar){
				//TODO 提示发布
				wcm.domain.photoMgr['docpositionset'](event);
			},
			name : wcm.LANG.PHOTO_CONFIRM_90 || '调整顺序',
			desc : wcm.LANG.PHOTO_CONFIRM_90 || '调整顺序',
			isDisabled : function(event){
				if(!event || event.getHost().getType() != WCMConstants.OBJ_TYPE_CHANNEL)
					return true;
				if(!event || (event.length() <= 0 || event.length() > 1) ) return true;
			},
			rightIndex : 62
		},
		'/', {
			id : 'action_refresh',
			fn : function(event, elToolbar){
				PageContext.loadList({CurrPage:1});
			},
			name : wcm.LANG.PHOTO_CONFIRM_98 ||'刷新',
			desc : wcm.LANG.PHOTO_CONFIRM_99 ||'刷新列表'
		}
	],
	listTitle : wcm.LANG.PHOTO_CONFIRM_100 ||'图片列表管理',
	path : 	'<div class="photoSearch" id="Photosearch" onclick="showPhotoSearch()"></div>'
}

//全选按钮事件绑定
Event.observe($("selectAll"), 'click', function(){
	$("selectAll").checked ? myThumbList.selectAll() : myThumbList.unselectAll();
});

Event.observe(window, 'load', function(){
	if(!PageContext.draggable) return;
	var bSortable = true;
	var sRight = PageContext.getParameter("rightValue");
	if(sRight!=null&&!wcm.AuthServer.checkRight(sRight, 62)){
			bSortable = false;
	}
	Ext.apply(new wcm.dd.SiteThumbDragDrop({id:'wcm_table_grid'}), {
		_getHint : function(item){
			if(!PageContext.getParameter("channelid")){
					return "<span style='font-weight:bold;background:gray;'>" + (wcm.LANG.PHOTO_CONFIRM_128||"当前图片列表不支持拖动排序") + "</span>";
			}
			if(!bSortable){
				return "<span>当前图片没有权限排序</span>";
			}
			var objId = item.getId();
			var siteDesc = $(item["desc_id_prefix"] + objId).innerHTML;
			var showBorder = $(item["cbx_id_prefix"] + objId).checked == true ? 1 : 0;
			var aHtml = [];
			aHtml.push(
				'<div class="thumb">',
					'<div class="thumbDrag">',
						'<img src=' + $("img_src" + objId).src + ' border=' + showBorder + ' alt="" align="absmiddle">',
					'</div>',
				'</div>'
			);
			return aHtml.join("");
		},
		_isSortable : function(item){
			return bSortable;
		},
		_moveAfter : function(currItem, prevItem){
			if(!PageContext.getParameter("channelid")){
				return false;
			}
			var orderBy = PageContext.params.ORDERBY;
			if(orderBy && orderBy.indexOf("siteorder") < 0) return false;
			if(!confirm(wcm.LANG.PHOTO_CONFIRM_127||"确实要改变图片排列顺序吗？")) return false;
			isMove = true;
			var myThumbList = (PageContext.getThumbList || Ext.emptyFn)() || window.myThumbList;
			var itemDom = Element.first($("wcm_table_grid"));
			var nextDom = Element.next(itemDom);
			new com.trs.web2frame.BasicDataHelper().call('wcm61_viewDocument', "changeOrder", {
				FromDocId:currItem.getItemInfo().docId,
				ToDocId: prevItem == null ? nextDom.getAttribute("docid") : prevItem.getItemInfo().docId,
				position:prevItem == null ? 1 : 0,
				channelid: PageContext.getParameter("channelid")
			}, true, function(){
				CMSObj.createFrom(currItem.getItemInfo(), PageContext.getContext()).afteredit();
			});
			return true;
		}
	});
});

