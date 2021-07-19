/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'infoview_document_list_of_channel.jsp',
	initParams : {
		/*
		"FieldsToHTML" : "*",
		"SelectFields" : "*"
		*/
		"FieldsToHTML" : "DocTitle,DocChannel.Name",
		"ChnlDocSelectFields" : "WCMChnlDoc.DOCKIND,WCMChnlDoc.DOCID,WCMChnlDoc.ChnlId,WCMChnlDoc.DocStatus,WCMChnlDoc.DocChannel,WCMChnlDoc.DocOrderPri,WCMChnlDoc.Modal,WCMChnlDoc.RecId",
		"DocumentSelectFields" : "DOCID,DocTitle,DocType,CrUser,CrTime,AttachPic,FLOWOPERATIONMARK",
		"ChannelIds" : getParameter("ChannelId") || "",// 将传入ChannelId转义为ChannelIds参数,确保符合服务的有效性
		"SiteIds" : getParameter("SiteId") || ""
	}
});
/*-------------指定列表上的过滤器--------------*/
PageContext.setFilters([
	{desc:wcm.LANG['INFOVIEW_FILTER_ALL']||'全部文档', type:0},
	{desc:wcm.LANG['INFOVIEW_FILTER_NEW']||'新稿', type:1},
	{desc:wcm.LANG['INFOVIEW_FILTER_CANPUB']||'可发', type:2},
	{desc:wcm.LANG['INFOVIEW_FILTER_PUBED']||'已发', type:3},
	{desc:wcm.LANG['INFOVIEW_FILTER_REJECTED']||'已否', type:8},
	{desc:wcm.LANG['INFOVIEW_FILTER_MY']||'我创建的', type:4},
	{desc:wcm.LANG['INFOVIEW_FILTER_LAST3']||'最近三天', type:5},
	{desc:wcm.LANG['INFOVIEW_FILTER_LASTWEEK']||'最近一周', type:6},
	{desc:wcm.LANG['INFOVIEW_FILTER_LASTMONTH']||'最近一月', type:7}
], {displayNum : 7});

/*-------------指定各种情况下右侧的操作面板--------------*/
/*
*设置第一块操作面板的类型；
*第二块和第三块面板的类型通过如下方式获取：
*如果列表页面没有选择记录，则为导航树节点对应的类型(如：website,channel);
*如果列表当前选中一条记录，则类型为当前列表的rowType(如：chnldoc);
*如果列表当前选中多条记录，则类型为当前列表的rowType+s(如：chnldocs);
*操作面板中操作的具体定义在文件wcm/app/js/data/opers/xxx.js中；
*/
PageContext.setRelateType(
	'infoviewdocInChannel'
);

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName :	wcm.LANG['INFOVIEW_DOC_UNIT'] || '个',
	TypeName : wcm.LANG['INFOVIEW_DOC']  || '表单文档'
});

/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'chnldoc_browse' : function(event){
		wcm.domain.InfoviewDocMgr['view'](event);
	},
	'preview' : function(event){
		wcm.domain.InfoviewDocMgr['preview'](event);
	},
	'edit' : function(event){
		wcm.domain.InfoviewDocMgr['edit'](event);
	}
});

/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中；
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'document'
});

Ext.apply(PageContext, {
	contextMenuEnable : true,
	objectType : 'infoviewdoc',
	_buildParams : function(wcmEvent, actionType, valueDom){
		if(wcmEvent.length() <= 0) return; 
		if(wcmEvent.getObjs().getType() != WCMConstants.OBJ_TYPE_INFOVIEWDOC) return;
		var obj = wcmEvent.getObjs().getAt(0);
		if(actionType=='save'){
			return {
				Force : {
					ObjectId : obj ? obj.getPropertyAsInt('docid', 0) : 0
				},
				ChannelId : PageContext.getParameter("ChannelId") || 0,
				SiteId : PageContext.getParameter("SiteId") || 0
			}
		}else if(actionType=='changestatus'){
			return {
				objectIds : obj ? obj.getId() : 0,
				StatusId : valueDom.getAttribute("_fieldValue", 2)
			};
		}
	},
	getContext : function(){
		var context = this.getContext0();
		Ext.apply(context, {
			pageDataParams : pageDataParams
		});
		return context;
	}
});
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_INFOVIEWDOC,
	aftersave : function(event){
		PageContext.loadList(Ext.applyIf({
			CURRDOCID : event.getIds().join(),
			SELECTIDS : ''
		}, PageContext.params));
	}
});
var pageDataParams = {};
function outPutArgs(arr){
	pageDataParams = {};
	for(var i=0,n=arr.length;i<n;i++){
		var argInfo = arr[i];
		if(argInfo==null)continue;
		pageDataParams = {
			'm_nCurrChannelId' : argInfo['m_nCurrChannelId'],
			'm_sCurrOutlineFields' : argInfo['m_sCurrOutlineFields'],
			'm_sSearchFields' : argInfo['m_sSearchFields'],
			'm_nDocumentType' : argInfo['m_nDocumentType'],
			'm_sSearchXML' : argInfo['m_sSearchXML'],
			'm_sDocStatus' : argInfo['m_sDocStatus'],
			'm_nInfoViewId' : argInfo['m_nInfoViewId']
		};
	}
}
function CTRSAction_doOrderBy(_sOrderField, _sOrderType){
	var json = getUrlJson();
	json["OrderField"] = _sOrderField;
	json["OrderType"] = _sOrderType;
	json["CurrPage"] = 1;
	PageContext.loadList(json);
}
function getUrlJson(){
	var json = {};
	var sUrl = location.href;
	if(sUrl.indexOf('?') > 0){
		sUrl = sUrl.substring(sUrl.indexOf('?') + 1, sUrl.length);
		json = sUrl.parseQuery();
	}
	return json;
}

function execSearch() {
	m_sTemplateRootName = getRootName(document.getElementById("TemplateXML").value);
	var sSearchFields = getFormatedSearchFields();
	var frmSearch = document.getElementById("frmSearch");
	var oArgs = {
		SearchFields : sSearchFields,
		TemplateRootName   : m_sTemplateRootName,
		SearchXML   : loadXml(frmSearch.SearchXML.value),
		InfoViewId : pageDataParams.m_nInfoViewId
	};
	var sUrl = WCMConstants.WCM6_PATH + 'infoview/infoview_document_list_of_channel_search.jsp';
	var cb = wcm.CrashBoard.get({
		id : 'DocumentSearchDialog',
		title : wcm.LANG['INFOVIEW_SEARCE'] || '检索',
		url : sUrl,
		width: '610px',
		height: '480px',
		params : oArgs,
		callback : function(_args){
			var frmSearch = document.getElementById("frmSearch");
			if(_args.SearchXML == null){
				frmSearch.SearchXML.value = "<SearchXML></SearchXML>";
			}
			else{
				frmSearch.SearchXML.value =  _args.SearchXML.xml;
			}
			frmSearch.DocStatus.value = _args.DocStatus || '';
			var json = getUrlJson();
			json["DocStatus"] = frmSearch.DocStatus.value;
			json["SearchTable"] = 'WCMDOCUMENT';
			json["SearchXML"] = frmSearch.SearchXML.value;
			cb.close();
			PageContext.loadList(json);
		}
	});
	cb.show();
}

Event.observe(window, 'load', function(){
	Event.observe('search', 'click', execSearch); 
});

var m_aFormatedSearchFields = null;
var m_sTemplateRootName = null;
function getFormatedSearchFields(){
	if(m_aFormatedSearchFields==null){
		if(pageDataParams.m_sSearchFields==""){
			m_aFormatedSearchFields = [];
			return m_aFormatedSearchFields;
		}
		var aFields = pageDataParams.m_sSearchFields.split(',');
		for (var i = 0; i < aFields.length; i++){
			var aField = aFields[i];
			if(aField.indexOf(':')!=-1){
				aFields[i] = m_sTemplateRootName + "/" + aField;
			}
		}
		m_aFormatedSearchFields = aFields;
	}
	return m_aFormatedSearchFields;
}
function getRootName(_sXMLString) {
	var arrFields = new Array();
	var oXMLDoc = loadXml(_sXMLString);
	var oRootElement = oXMLDoc.documentElement;
	return "/"+oRootElement.nodeName;
}