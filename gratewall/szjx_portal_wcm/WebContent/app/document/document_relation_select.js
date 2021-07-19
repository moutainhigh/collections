Ext.apply(PageContext, {
	tabEnable : true,
	operEnable : true,
	filterEnable : true,
	gridDraggable : !getParameter("doSearch"),
	serviceId : 'wcm61_viewdocument',
	methodName : 'tQuery',
	/**/
	objectType : 'chnldoc',
	initParams : {
		"FieldsToHTML" : "DocTitle,DocChannel.Name",
		"DocumentSelectFields" : "DOCID,DocTitle,DocType,CrUser,CrTime,DocEditor,AttachPic,DOCKEYWORDS",
		"SelectType" : parent.SELECT_TYPE || 'checkbox',
		PageSize : 5
	}
});
Ext.apply(wcm.Grid, {
	rowType : function(){
		return WCMConstants.OBJ_TYPE_CHNLDOC;
	},
	rowInfo : {
		docid : true,
		doctitle : true,
		channelid : true,
		currchnlid : true
	}
});

Ext.apply(PageContext, {
	_doBeforeLoad : function(){
		//禁止发请求
		//return false;
	},
	getContext : function(){
		var context0 = this.getContext0();
		var bIsSearch = !!PageContext.getParameter("IsSearch");
		if(bIsSearch){
			var context = Ext.applyIf({
				isChannel : false,
				relateType : 'docInSearch',
				host : {
					objType : 'docSearchContext',
					objId : 0,
					right : PageContext.getParameter("RightValue"),
					isVirtual : PageContext.getParameter("IsVirtual"),
					detail : location.search.substring(1)
				}
			}, context0);
			return context;
		}
		var bIsChannel = !!PageContext.getParameter("ChannelId");
		var context = Ext.applyIf({
			isChannel : bIsChannel,
			relateType : bIsChannel ? 'documentInChannel' : 'documentInSite',
			host : {
				right : PageContext.getParameter("RightValue"),
				isVirtual : PageContext.getParameter("IsVirtual"),
				objType : bIsChannel ? WCMConstants.OBJ_TYPE_CHANNEL
										: WCMConstants.OBJ_TYPE_WEBSITE,
				objId : bIsChannel ? PageContext.getParameter("ChannelId")
										: PageContext.getParameter("SiteId")
			}
		}, context0);
		return context;
	},
	prepareParams : function(paramName){
		paramName = paramName.toLowerCase();
		if(['channelid', 'siteid'].include(paramName)){
			return getParameter(paramName) || getParameter(paramName + 's') || '';
		}
	},
	_buildParams : function(wcmEvent, actionType){
		if(wcmEvent.length() <= 0) return; 
		if(actionType=='save'){
			var obj = wcmEvent.getObjs().getAt(0);
			var host = wcmEvent.getHost();
			return {
				ObjectId : obj ? obj.getDocId() : 0,
				ChannelId : PageContext.getParameter("ChannelId") || 0,
				SiteId : PageContext.getParameter("SiteId") || 0
			}
		}
	},
	getPageParams : function(info){
		this.params = Ext.Json.toUpperCase(location.search.parseQuery());
		Ext.applyIf(this.params, Ext.Json.toUpperCase(PageContext.initParams));
		Ext.applyIf(this.params, {
			CHANNELIDS : this.params["CHANNELID"] || '',
			SITEIDS : this.params["SITEID"] || ''
		});
		return Ext.apply(this.params, Ext.Json.toUpperCase(info));
	},
	/**/
	pageFilters : (function(){
		if(!PageContext.filterEnable)return null;
		var filters = new wcm.PageFilters({
			displayNum : 4,
			filterType : PageContext.getParameter('FilterType') || 0
		});
		filters.register([
			{desc:wcm.LANG['FILTER_DOCUMENT_ALL']||'全部文档', type:0},
			{desc:wcm.LANG['FILTER_CANPUB']||'可发', type:2},
			{desc:wcm.LANG['FILTER_PUBED']||'已发', type:3}
		]);
		return filters;
	}()),
	pageTabs : null,
	gridFunctions : function(){
	},
	isTopped : function(_sTopped){
		if(_sTopped=='true'){
			return 'document_topped';
		}
		return '';
	}
});
Event.observe(window, 'load', function(){
	wcm.ListQuery.register({
		/*检索控件追加到的容器*/
		container : 'query_box', 
		/*是否追加“全部”这个检索项, default to false*/
		appendQueryAll : true,
		/*是否自动加载, default to true*/
		autoLoad : true,
		/*检索项的内容*/
		items : [
			{name: 'DocTitle', desc: wcm.LANG.DOCUMENT_PROCESS_211 || '标题', type: 'string', length: 50},
			{name: 'CrUser', desc: wcm.LANG.DOCUMENT_PROCESS_212 || '发稿人', type: 'string', length: 50},
			{name: 'DocKeywords', desc: wcm.LANG.DOCUMENT_PROCESS_213 || '关键词', type: 'string', length: 50}
		],
		/*执行检索按钮时执行的回调函数*/
		callback : function(params){
			//alert(Object.parseSource(params));
			PageContext.loadList(Ext.apply(PageContext.params, params));
		}
	});
});
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG['DOC_UNIT'] || '篇',
	TypeName : wcm.LANG['DOCUMENT'] || '文档'
});
var PRV_OBJ_TYPE_DOCUMENT = parent.PRV_OBJ_TYPE_DOCUMENT || WCMConstants.OBJ_TYPE_DOCUMENT;
var PRV_OBJ_TYPE_CHNLDOC = parent.PRV_OBJ_TYPE_CHNLDOC || WCMConstants.OBJ_TYPE_CHNLDOC;
var PRV_OBJ_TYPE_CURRPAGE = parent.PRV_OBJ_TYPE_CURRPAGE || WCMConstants.OBJ_TYPE_CURRPAGE;
$MsgCenter.un($MsgCenter.getListener('sys_gridrow'));
$MsgCenter.un($MsgCenter.getListener('sys_gridcell'));
$MsgCenter.un($MsgCenter.getListener('sys_allcmsobjs'));
PageContext.m_CurrPage = $MsgCenter.$currPage().privateMe(PRV_OBJ_TYPE_CURRPAGE);
wcm.GridRow.prototype._buildContext = function(_row){
	return wcm.Grid.getRowInfo(_row, wcm.Grid.rowInfo);
}
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_GRIDROW,
	afterunselect : function(event){
		if(event.from()!=wcm.getMyFlag())return;
		var context = event.getContext();
		if(context==null)return;
		var cmsobj = CMSObj.createFrom(context);
		cmsobj.privateMe(PRV_OBJ_TYPE_CHNLDOC).afterunselect();
	},
	beforeclick : function(event){
		if(event.from()!=wcm.getMyFlag())return;
		var context = event.getContext();
		if(context==null)return;
		var dom = context.row.dom;
		return (!Element.hasClassName(dom, "grid_selectdisable_row"));
	},
	afterselect : function(event){
		if(event.from()!=wcm.getMyFlag())return;
		var context = event.getContext();
		if(context==null)return;
		var cmsobj = CMSObj.createFrom(context);
		cmsobj.privateMe(PRV_OBJ_TYPE_CHNLDOC).afterselect();
	}
});
$MsgCenter.on({
	objType : PRV_OBJ_TYPE_DOCUMENT,
	afterunselect : function(event){
		var nDocId = event.getIds().join();
		var rows = wcm.Grid.filterRows('docid', nDocId);
		if(rows.length==0)return;
		for(var i=0, n=rows.length; i<n; i++){
			wcm.Grid.unselectRow(rows[i], true);
		}
	},
	afterselect : function(event){
		if(event.from()==wcm.getMyFlag())return;
		var objs = event.getObjs();
		for(var i=0, n=objs.length(); i<n; i++){
			var nDocId = objs.getAt(i).getId();
			var rows = wcm.Grid.filterRows('docid', nDocId);
			if(rows.length==0)continue;
			for(var j=0, jn=rows.length; j<jn; j++){
				wcm.Grid.selectRow(rows[j], true);
			}
		}
	}
});
