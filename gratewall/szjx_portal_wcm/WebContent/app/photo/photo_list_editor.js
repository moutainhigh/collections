Ext.apply(PageContext, {
	tabEnable : false,
	operEnable : false,
	filterEnable : true,
	gridDraggable : false,
	searchEnable : true,
	serviceId : 'wcm61_photo',
	methodName : 'tThumbQuery',
	/**/
	objectType : 'photo',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"pageSize" : 9
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
	//**
	getPageParams : function(info){
		this.params = Ext.Json.toUpperCase(location.search.parseQuery());
		Ext.applyIf(this.params, Ext.Json.toUpperCase(PageContext.initParams));
		Ext.applyIf(this.params, {"SelectIds" : (parent.SelectedPhotoIds || []).join(",")});
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
//检索框信息
Event.observe(window, 'load', function(){
	var nChannelId= PageContext.getParameter("ChannelId") || 0;
	if(nChannelId > 0 && $("tb_newphoto")){
		$("tb_newphoto").style.display = "";
	}
	wcm.ListOrder.register({
		container : 'list-order-box', 
		appendTip : false,
		autoLoad : true,
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
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.PHOTO_CONFIRM_7 || '个',
	TypeName : wcm.LANG.PHOTO_CONFIRM_8 || '图片'
});
function addNewPhoto(){
	var chnlId = PageContext.getParameter("ChannelId") || 0;
	if(!Ext.isIE) {
		var ref = window.showModalDialog("photo_upload_editor.jsp?ChannelId="+chnlId,window,
			"dialogWidth:580px;dialogHeight:450px;dialogTop:" + window.screen.availHeight/3 + ";dialogLeft:" + window.screen.availWidth/3 + ";status:0;resizable:0;help:0;center:1;");
	}else{
		var ref = window.showModalDialog("photo_upload_editor.jsp?ChannelId="+chnlId,window,
			"dialogWidth:580px;dialogHeight:450px;status:0;resizable:0;help:0;center:1;");
	}
	if(ref == "refresh"){
		 PageContext.loadList(PageContext.params);
	}
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
//图片检索信息
function showPhotoSearch(){
	var channelId = getParameter("channelId") || 0 ;
	var siteId = getParameter("siteId") || 0 ;
	wcm.CrashBoarder.get('Dialog_Photo_QuickSearcher').show({
		title : wcm.LANG.PHOTO_CONFIRM_40 || '图片快速检索',
		src : WCMConstants.WCM6_PATH + 'photo/photo_search.htm',
		width: '400px',
		height: '350px',
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
			var nSiteType = getParameter("SiteType") || -1 ;
			if(nSiteType > 0){
				if(queryData.ChannelId == 0){
					delete queryData["ChannelId"]
				}
				if(queryData.SiteId == 0){
					delete queryData["SiteId"]
				}
			}
			PageContext.loadList(Ext.apply(PageContext.params,queryData));
		}
	});
}
//显示所选图片的详细信息
function showDetail(_sSrc,_nId){
	var postParams ={PicName:_sSrc,PhotoId:_nId};
	var targetUrl = "photo_show_editor.jsp?" + $toQueryStr2(postParams);	
	if(Ext.isIE) {
		window.showModalDialog(targetUrl,'',"dialogWidth:530px;dialogHeight:506px;status:0;resizable:0;help:0;center:1;");
	}else{
		window.showModalDialog(targetUrl,'',"dialogWidth:530px;dialogHeight:506px;status:0;resizable:0;help:0;center:1;" + "dialogTop:" + window.screen.availHeight/3 + ';dialogLeft:' + window.screen.availWidth/3);
	}
}
	
function RefreshSelected(){
	var arrDocIds = document.getElementsByName("AppendixId");
	for(var i=0;i<arrDocIds.length;i++){
		arrDocIds[i].checked = parent.SelectedPhotoIds.include(arrDocIds[i].value);
	}
}

//页面加载完成之后，进行整个页面checkbox状态的刷新
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_MAINPAGE,
	afterinit : function(event){
		var sSelectedCache = parent.SelectedPhotoIds;
		var arrDocIds = document.getElementsByName("AppendixId");
		for(var i=0;i<arrDocIds.length;i++){
			if(sSelectedCache.include(arrDocIds[i].value)) {
				myThumbList.get(arrDocIds[i].value).active();
				//arrDocIds[i].setAttribute("checked", true);
			}
		}
	}
});
//捕捉增加/删除图片消息
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_XTHUMBLIST,
	selectedchange : function(event){
		var arrDocIds = document.getElementsByName("AppendixId");
		for(var i=0;i<arrDocIds.length;i++){
			var eTmp = arrDocIds[i];
			var sPhotoSrc = eTmp.getAttribute("photo_srcs",2);
			var sPhotoDesc = eTmp.getAttribute("desc",2);
			var sPhotoUrl = eTmp.getAttribute("photo_url",2);
			var sPhotoDocId = eTmp.getAttribute("photo_docid",2);
			var bIncluded = parent.SelectedPhotoIds.include(eTmp.value);
			if(eTmp.checked&&!bIncluded){
				parent.SetPhotoSelected(eTmp.value,sPhotoSrc,true,sPhotoDesc,sPhotoUrl,sPhotoDocId);
			}
			else if(!eTmp.checked&&bIncluded){
				parent.SetPhotoSelected(eTmp.value,sPhotoSrc,false,sPhotoDesc,sPhotoUrl,sPhotoDocId);
			}
		}
	}
});
//$MsgCenter.on({
	//objType : WCMConstants.OBJ_TYPE_XTHUMBITEM,
	//afterselect : function(event){
		//alert("afterselect");
		//var selectedIds = parent.SelectedPhotoIds;
		//for(var i=0;i<selectedIds.length;i++){
			//var eTmp = arrDocIds[i];
			//var sPhotoSrc = eTmp.getAttribute("photo_srcs",2);
			//var sPhotoDesc = eTmp.getAttribute("desc",2);
			//var sPhotoUrl = eTmp.getAttribute("photo_url",2);
			//var sPhotoDocId = eTmp.getAttribute("photo_docid",2);
			//parent.SetPhotoSelected(eTmp.value,sPhotoSrc,true,sPhotoDesc,sPhotoUrl,sPhotoDocId);
		//}
	//}
//});
//$MsgCenter.on({
	//objType : WCMConstants.OBJ_TYPE_XTHUMBITEM,
	//afterunselect : function(event){
		//alert("afterunselect");
		//var selectedIds = parent.SelectedPhotoIds;
		//for(var i=0;i<selectedIds.length;i++){
			//var eTmp = arrDocIds[i];
			//var sPhotoSrc = eTmp.getAttribute("photo_srcs",2);
			//var sPhotoDesc = eTmp.getAttribute("desc",2);
			//var sPhotoUrl = eTmp.getAttribute("photo_url",2);
			//var sPhotoDocId = eTmp.getAttribute("photo_docid",2);
			//parent.SetPhotoSelected(eTmp.value,sPhotoSrc,false,sPhotoDesc,sPhotoUrl,sPhotoDocId);
		//}
	//}
//});
