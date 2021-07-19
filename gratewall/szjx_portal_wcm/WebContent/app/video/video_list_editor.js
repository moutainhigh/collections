Ext.apply(PageContext, {
	tabEnable : false,
	operEnable : false,
	filterEnable : true,
	gridDraggable : false,
	searchEnable : true,
	serviceId : 'wcm61_video',
	methodName : 'tVideoQuery',
	/**/
	objectType : 'video',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"pageSize" : 9,
		"ChannelIds" : getParameter("channelId"),
		"SiteIds" : getParameter("siteId")
	}
});
wcm.MyThumbItem = Ext.extend(wcm.ThumbItem, {
	itemType : function(){
		return WCMConstants.OBJ_TYPE_VIDEO;
	},
	itemInfo : {
		docId : true,
		itemId : true,
		rightValue : true
	}
});

Ext.apply(wcm.MyThumbItem, {
	type : "video"
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
			relateType : bIsChannel ? 'videoInChannel' :
				(bIsSite ? 'videoInSite' : 'videoInRoot')
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
		if(actionType=='save' && wcmEvent.getObjs().getType()==WCMConstants.OBJ_TYPE_VIDEO){
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
PageContext.searchEnable = true;
Event.observe(window, 'load', function(){
	wcm.ListQuery.register({
		/*检索控件追加到的容器*/
		container : 'search', 
		/*是否追加“全部”这个检索项, default to false*/
		appendQueryAll : true,
		/*是否自动加载, default to true*/
		autoLoad : PageContext.searchEnable,
		/*检索项的内容*/
		items : [
			{name : 'DOCTITLE', desc : '视频标题', type : 'string'}
		],
		/*执行检索按钮时执行的回调函数*/
		callback : function(params){
			PageContext.loadList(Ext.apply({
				//some params must remember here
			}, params));
		}
	});
	var nSiteid= PageContext.getParameter("SiteId") || 0;
	if(nSiteid == 0 && $("tb_newphoto")){
		$("tb_newphoto").style.display = "";
	}
	wcm.ListOrder.register({
		container : 'list-order-box', 
		appendTip : false,
		autoLoad : true,
		items : [
			{name : 'WCMCHNLDOC.crtime', desc : (wcm.LANG.VIDEO_CONFIRM_41 || '创建时间'), isActive : true},
			{name : 'WCMDocument.doctitle', desc : (wcm.LANG.VIDEO_CONFIRM_42 || '视频标题')},
			{name : 'WCMCHNLDOC.cruser', desc : (wcm.LANG.VIDEO_CONFIRM_43 || '创建者')}
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
	UnitName : wcm.LANG.VIDEO_CONFIRM_7 || '个',
	TypeName : wcm.LANG.VIDEO_CONFIRM_8 || '视频'
});
function addNewPhoto(){
	var chnlId = PageContext.getParameter("ChannelId") || 0;
	var ref = window.showModalDialog("photo_upload_editor.jsp?ChannelId="+chnlId,window,
			"dialogWidth:560px;dialogHeight:450px;status:0;resizable:0;help:0;center:1;");
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
//视频检索信息
function showPhotoSearch(){
	var channelId = getParameter("channelId") || 0 ;
	var siteId = getParameter("siteId") || 0 ;
	wcm.CrashBoarder.get('Dialog_Photo_QuickSearcher').show({
		title : wcm.LANG.VIDEO_CONFIRM_40 || '视频快速检索',
		src : WCMConstants.WCM6_PATH + 'video/video_search.htm',
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
			PageContext.loadList(Ext.apply(PageContext.params,queryData));
		}
	});
}
//显示所选视频的详细信息
function showDetail(_sSrc,_nId){
	var postParams ={PicName:_sSrc,PhotoId:_nId};
	var targetUrl = "photo_show_editor.jsp?" + $toQueryStr2(postParams);				
	if(document.all && window.external){ //IE
		window.showModalDialog(targetUrl,'',"dialogWidth:530px;dialogHeight:506px;status:0;resizable:0;help:0;center:1;");
	}
	else{
		window.open(targetUrl);
	}
}
	
function RefreshSelected(){
	var arrDocIds = document.getElementsByName("AppendixId");
	for(var i=0;i<arrDocIds.length;i++){
		arrDocIds[i].checked = parent.SelectedPhotoIds.include(arrDocIds[i].value);
	}
}
//捕捉增加/删除视频消息
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_XTHUMBLIST,
	selectedchange : function(event){
		var arrDocIds = document.getElementsByName("AppendixId");
		for(var i=0;i<arrDocIds.length;i++){
			var eTmp = arrDocIds[i];
			var sPhotoSrc = eTmp.getAttribute("photo_srcs",2);
			var oDocInfo = new Object();
			oDocInfo.iosPlayUrl = eTmp.getAttribute("iosplay_url" , 2);
			oDocInfo.docId = eTmp.getAttribute("docid" , 2);
			oDocInfo.docTitle = eTmp.getAttribute("docTitle" , 2);
			oDocInfo.chnlId = eTmp.getAttribute("channelid" , 2);
			oDocInfo.chnlName = eTmp.getAttribute("chnlname" , 2);
			oDocInfo.chnlDesc = eTmp.getAttribute("chnldesc" , 2);
			oDocInfo.token = eTmp.getAttribute("videoFiles" , 2);
			oDocInfo.sPhotoSrcs = sPhotoSrc;
			var bIncluded = parent.SelectedPhotoIds.include(eTmp.value);
			if(eTmp.checked&&!bIncluded){
				parent.SetPhotoSelected(eTmp.value,oDocInfo,true);
			}
			else if(!eTmp.checked&&bIncluded){
				parent.SetPhotoSelected(eTmp.value,oDocInfo,false);
			}
		}

	}
});