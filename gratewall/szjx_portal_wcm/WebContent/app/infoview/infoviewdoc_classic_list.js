/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : '/wcm/app/infoview/infoview_document_list_of_channel.jsp',
	/**/
	objectType : WCMConstants.OBJ_TYPE_INFOVIEWDOC,
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

]);

PageContext.setRelateType(
	'infoviewdocInChannel'
);

Ext.apply(PageContext, {
	contextMenuEnable : true,
	objectType : 'infoviewdoc',
	_buildParams : function(wcmEvent, actionType){
		if(wcmEvent.length() <= 0) return; 
		if(actionType=='save' && wcmEvent.getObjs().getType()==WCMConstants.OBJ_TYPE_INFOVIEWDOC){
			var obj = wcmEvent.getObjs().getAt(0);
			var host = wcmEvent.getHost();
			return {
				Force : {
					ObjectId : obj ? obj.getPropertyAsInt('docid', 0) : 0
				},
				ChannelId : PageContext.getParameter("ChannelId") || 0,
				SiteId : PageContext.getParameter("SiteId") || 0
			}
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

Ext.apply(PageContext.literator, {
	enable : true,
	width : 350,
	doBefore : function(){
		ClassicList.makeLoad();
	}
});
Ext.apply(PageContext.literator.params, {
	tracesitetype : true,
	tracesite : true
});

/*-------------指定各种情况下右侧的操作面板--------------*/
/*
*设置第一块操作面板的类型；
*第二块和第三块面板的类型通过如下方式获取：
*如果列表页面没有选择记录，则为导航树节点对应的类型(如：website,channel);
*如果列表当前选中一条记录，则类型为当前列表的rowType(如：chnldoc);
*如果列表当前选中多条记录，则类型为当前列表的rowType+s(如：chnldocs);
*操作面板中操作的具体定义在文件wcm/app/js/data/opers/xxx.js中；
*/
PageContext.operEnable = false; 
 
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

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_INFOVIEWDOC,
	aftersave : function(event){
		PageContext.loadList(Ext.applyIf({
			CURRDOCID : event.getIds().join(),
			SELECTIDS : ''
		}, PageContext.params));
	}
});
ClassicList.cfg = {
	toolbar : [
		{
			id : 'document_add',
			fn : function(event, elToolbar){
				wcm.domain.InfoviewDocMgr['new'](event);
			},
			name : wcm.LANG['INFOVIEW_DOC_1'] || '新建',
			desc : wcm.LANG['INFOVIEW_DOC_2'] || '新建一篇文档',
			rightIndex : 31
		}, {
			id : 'document_publish',
			fn : function(event, elToolbar){
				wcm.domain.InfoviewDocMgr.basicpublish(event);
			},
			name : wcm.LANG['INFOVIEW_DOC_3'] || '快速发布',
			desc : wcm.LANG['INFOVIEW_DOC_4'] || '快速发布这篇/些文档',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 39
		},{
			id : 'document_delete',
			fn : function(event, elToolbar){
				wcm.domain.InfoviewDocMgr.trash(event);
			},
			name : wcm.LANG['INFOVIEW_DOC_5'] || '删除',
			desc : wcm.LANG['INFOVIEW_DOC_6'] || '删除这篇/些文档',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 33
		},{
			id : 'document_preview',
			fn : function(event, elToolbar){
				wcm.domain.InfoviewDocMgr.preview(event);
			},
			name : wcm.LANG.DOCUMENT_PROCESS_20 || '预览',
			desc : wcm.LANG.DOCUMENT_PROCESS_164 || '预览这篇/些文档',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 38
		},{
			id : 'document_quote',
			fn : function(event, elToolbar){
				 wcm.domain.InfoviewDocMgr.quoteDoc(event);
			},
			name : wcm.LANG['INFOVIEW_DOC_7'] || '引用',
			desc : wcm.LANG['INFOVIEW_DOC_8'] || '引用这篇/些文档',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 34
		},{
			id : 'document_move',
			fn : function(event, elToolbar){
				wcm.domain.InfoviewDocMgr.move(event);
			},
			name : wcm.LANG['INFOVIEW_DOC_9'] || '移动',
			desc : wcm.LANG['INFOVIEW_DOC_10'] || '移动这篇/些文档',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 33
		},{
			id : 'document_copy',
			fn : function(event, elToolbar){
				wcm.domain.InfoviewDocMgr.copy(event);
			},
			name : wcm.LANG['INFOVIEW_DOC_11'] || '复制',
			desc : wcm.LANG['INFOVIEW_DOC_12'] || '复制这篇/些文档',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 34
		},{
			id : 'setUserDesignFields',
			fn : function(event, elToolbar){
				wcm.domain.InfoviewDocMgr.setUserDesignFields(event);
			},
			name : wcm.LANG['INFOVIEW_DOC_13'] || '自定义视图',
			desc : wcm.LANG['INFOVIEW_DOC_13'] || '自定义视图',
			rightIndex : 13
		},{
			id : 'document_exportExcel',
			fn : function(event, elToolbar){
				wcm.domain.InfoviewDocMgr['export'](event, elToolbar);				
			},
			name : wcm.LANG['INFOVIEW_DOC_14'] || '导出Excel',
			desc : wcm.LANG['INFOVIEW_DOC_15'] || '导出Excel',
			rightIndex : 30
		},{
			id : 'document_execSearch',
			fn : function(event, elToolbar){
				execSearch();
			},
			name : wcm.LANG['INFOVIEW_SEARCE'] || '检索',
			desc : wcm.LANG['INFOVIEW_SEARCE'] || '检索'
		},
		'/', {
			id : 'action_refresh',
			fn : function(event, elToolbar){
				PageContext.loadList({CurrPage:1});
			},
			name : wcm.LANG['INFOVIEW_DOC_16'] || '刷新',
			desc : wcm.LANG['INFOVIEW_DOC_17'] || '刷新列表'
		}
	],
	listTitle : wcm.LANG['INFOVIEW_DOC_18'] || '栏目表单文档列表',
	path : ''
}
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
	json["PageIndex"] = 1;
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
		width: '680px',
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