Ext.apply(PageContext, {
	tabEnable : true,
	operEnable : false,
	filterEnable : false,
	contextMenuEnable : true,
	gridDraggable : !getParameter("doSearch"),
	serviceId : 'wcm61_viewdocument',
	methodName : 'jQuery',
	/**/
	objectType : WCMConstants.OBJ_TYPE_CHNLDOC,
	initParams : {
		"FieldsToHTML" : "DocTitle,DocChannel.Name",
		"ChnlDocSelectFields" : "WCMChnlDoc.DOCKIND,WCMChnlDoc.DOCID,WCMChnlDoc.ChnlId,WCMChnlDoc.DocStatus,WCMChnlDoc.DocChannel,WCMChnlDoc.DocOrderPri,WCMChnlDoc.Modal,WCMChnlDoc.RecId",
		"DocumentSelectFields" : "DOCID,DocTitle,DocType,CrUser,CrTime,AttachPic,FLOWOPERATIONMARK",
		"ChannelIds" : getParameter("ChannelId") || "",// 将传入ChannelId转义为ChannelIds参数,确保符合服务的有效性
		"SiteIds" : getParameter("SiteId") || ""
	}
});
Ext.apply(wcm.Grid, {
	rowType : function(){
		return WCMConstants.OBJ_TYPE_CHNLDOC;
	},
	rowInfo : {
		docid : true,
		channelid : true,
		currchnlid : true,
		isTopped : true,
		doctype : true
	}
});

Ext.apply(PageContext, {
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
		if(actionType=='save' && wcmEvent.getObjs().getType()==WCMConstants.OBJ_TYPE_CHNLDOC){
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
			displayNum : 6,
			filterType : PageContext.getParameter('FilterType') || 0
		});
		filters.register([
			{desc:wcm.LANG['FILTER_DOCUMENT_ALL']||'全部文档', type:0},
			{desc:wcm.LANG['FILTER_NEW']||'新稿', type:1},
			{desc:wcm.LANG['FILTER_CANPUB']||'可发', type:2},
			{desc:wcm.LANG['FILTER_PUBED']||'已发', type:3},
			{desc:wcm.LANG['FILTER_REJECTED']||'已否', type:8},
			{desc:wcm.LANG['FILTER_MY']||'我创建的', type:4},
			{desc:wcm.LANG['FILTER_LAST3']||'最近三天', type:5},
			{desc:wcm.LANG['FILTER_LASTWEEK']||'最近一周', type:6},
			{desc:wcm.LANG['FILTER_LASTMONTH']||'最近一月', type:7}
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
		var myMgr = wcm.domain.ChnlDocMgr;
		wcm.Grid.addFunction('preview', function(event){
			myMgr['preview'](event);
		});
		wcm.Grid.addFunction('chnldoc_view', function(event){
			myMgr['view'](event);
		});
		wcm.Grid.addFunction('chnldoc_edit', function(event){
			var right = event.getObj().getPropertyAsString("right", 0);
			var sMethod = wcm.AuthServer.hasRight(right, 32) ? 'edit' : 'view';
			wcm.domain.ChnlDocMgr[sMethod](event);
		});
		wcm.Grid.addFunction('open_channel', function(event){
			var channelid;
			if(event.getHost().objType == "website"){
				channelid = event.getObj().getPropertyAsInt('currchnlid', 0);
			}else{
				channelid = event.getObj().getPropertyAsInt('channelid',0);
			}
			// 将当前主页面重定向到新的栏目文档列表上
			$MsgCenter.$main({
				objId : channelid,
				objType : WCMConstants.OBJ_TYPE_CHANNEL
			}).redirect();
		});
	},
	isTopped : function(_sTopped){
		if(_sTopped=='true'){
			return 'document_topped';
		}
		return '';
	},
	filterDocSources : function(event, valueDom, wcmEvent){
		var oHelper = BasicDataHelper;
		var oPostData = {
			k : this.value,
			r : new Date().getTime()
		};
		var elSuggestion = $('suggestion_cnt');
		if(!elSuggestion){
			elSuggestion = document.createElement('DIV');
			elSuggestion.style.position = 'absolute';
			elSuggestion.id = 'suggestion_cnt';
			elSuggestion.style.zIndex = 1000;
			document.body.appendChild(elSuggestion);
			Element.hide(elSuggestion);
		}
		oHelper.JspRequest(WCMConstants.WCM6_PATH + 'system/filter_docsource.jsp', oPostData,
			true, function(_trans){
				var extEvent = new Ext.EventObjectImpl(event);
				var point = extEvent.getPoint();
				var x = point.x + 4;
				var y = point.y + 4;
				var elSuggestion = $('suggestion_cnt');
				var oBubbler = new wcm.BubblePanel(elSuggestion);
				Element.update('suggestion_cnt', _trans.responseText);	
				oBubbler.bubble([x,y], function(_Point){
					return [_Point[0]-this.offsetWidth,_Point[1]];
				});
				Element.show(elSuggestion);
			}
		);
	}
});

/*-------------检索框信息--------------*/
PageContext.searchEnable = true;
function getSearchFieldInfo(queryItem){
	if(arguments.callee.invoked){
		return;
	}
	arguments.callee.invoked = true;
	wcm.ListQuery.register({
		/*检索控件追加到的容器*/
		container : 'query_box', 
		/*是否追加“全部”这个检索项, default to false*/
		appendQueryAll : true,
		/*是否自动加载, default to true*/
		autoLoad : true,
		/*检索项的内容*/
		items : [
			{name: 'DOCTITLE', desc: wcm.LANG.DOCUMENT_PROCESS_177 || '文档标题', type: 'string'},
			{name: 'CRUSER', desc: wcm.LANG.DOCUMENT_PROCESS_68 || '创建者', type: 'string'},
			{name: 'DOCKEYWORDS', desc: wcm.LANG.DOCUMENT_PROCESS_75 || '关键字', type: 'string'}
		],
		/*执行检索按钮时执行的回调函数*/
		callback : function(params){
			PageContext.loadList(Ext.apply(PageContext.params, params));
		}
	});
};

Event.observe(window, 'load', function(){
	ClassicList.makeLoad();
	if(PageContext.hostType == WCMConstants.OBJ_TYPE_CHANNEL){
		Element.show('divDispMode');
	}
});
//详细信息面板中文档来源的suggestion,利用消息机制绑定元素
function showSuggestion(){
	setTimeout(function(){
		if(document.getElementById("DocSourceName")!= null){
			var suggestion = new com.trs.suggestion.Suggestion('DocSourceName');
			suggestion.dealWithOnKeyReturn = function(currSelectObj){
				suggestion.oRelatedElement.value = suggestion.getCurrSelect;
				suggestion.oSuggestionRegionShieldElement.style.display = "none";
			};
			suggestion.sendRequest = function(beforeSend, afterSend){
				var txtValue = suggestion.oRelatedElement.value;
				if(beforeSend) beforeSend();
				this.clearAllItems();
				BasicDataHelper.JspRequest(
					WCMConstants.WCM6_PATH + "nav_tree/source_create.jsp", 
					{SourceName : txtValue},  true, 
					function(transport, json){
						var result = eval(transport.responseText.trim());
						for (var i = 0; i < result.length; i++){
							suggestion.addItem(result[i].title);
						}
						if(afterSend){
							afterSend(transport, json);
						}
						if(result.length == 0){
							suggestion.oRelatedElement.focus();
							suggestion.oRelatedElement.select();
						}
					});
			};
			suggestion.onOptionClick = function(_event, _oJson){
				if(_event.type == 'click'){
					suggestion.oRelatedElement.value = suggestion.getCurrSelect;
					suggestion.oSuggestionRegionShieldElement.style.display = "none";
				}
				return true;
			};
		}
	},10);
}
Ext.apply(PageContext.literator, {
	enable : true,
	width : 350,
	doBefore : function(){
		ClassicList.makeLoad();
	}
});
Ext.apply(PageContext.literator.params, {
	tracesite : true
});
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG['DOC_UNIT'] || '篇',
	TypeName : wcm.LANG['DOCUMENT'] || '文档'
});
//是否显示子对象
function switchChildrenDisp(){
	var _eFirer = $("divDispMode");
	if(_eFirer.className =="view_mode_normal"){
		Object.extend(PageContext.params,{"containsChildren":true});
		_eFirer.title =  wcm.LANG['DOCUMENT_PROCESS_235'] || '隐藏子栏目的文档';
		_eFirer.className = "view_mode_recursive";
	}else{
		Object.extend(PageContext.params,{"containsChildren":false});
		_eFirer.title = wcm.LANG['DOCUMENT_PROCESS_234'] || '显示子栏目的文档';
		_eFirer.className = "view_mode_normal";
	}
	 PageContext.loadList(PageContext.params);
}
function initDisMode(bShowChildren){
	var _eFirer = $("divDispMode");
	if(!bShowChildren){
		_eFirer.title = wcm.LANG['DOCUMENT_PROCESS_234'] || '显示子栏目的文档';
		_eFirer.className = "view_mode_normal";
	}else{
		_eFirer.title =  wcm.LANG['DOCUMENT_PROCESS_235'] || '隐藏子栏目的文档';
		_eFirer.className = "view_mode_recursive";
	}	
}
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	aftersave : function(event){
		PageContext.loadList(Ext.applyIf({
			CURRDOCID : event.getIds().join(),
			SELECTIDS : ''
		}, PageContext.params));
	}
});
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_GRIDROW,
	afterselect : function(event){
		showSuggestion();
	}
});
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_GRIDROW,
	afterunselect : function(event){
		showSuggestion();
	}
});
ClassicList.cfg = {
	toolbar : [
		{
			id : 'document_new',
			fn : function(event, elToolbar){
				wcm.domain.DocumentMgr['new'](event);
			},
			name : wcm.LANG.DOCUMENT_PROCESS_158 || '新建',
			desc : wcm.LANG.DOCUMENT_PROCESS_159 || '新建一篇文档',
			isDisabled : function(event){
				if(Ext.isTrue(PageContext.getParameter("IsVirtual")))return true;
			},
			rightIndex : 31
		}, {
			id : 'document_delete',
			fn : function(event, elToolbar){
				wcm.domain.ChnlDocMgr.trash(event);
			},
			name : wcm.LANG.DOCUMENT_PROCESS_161 || '删除',
			desc : wcm.LANG.DOCUMENT_PROCESS_162 || '删除这篇/些文档',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 33
		},{
			id : 'document_preview',
			fn : function(event, elToolbar){
				wcm.domain.ChnlDocMgr.preview(event);
			},
			name : wcm.LANG.DOCUMENT_PROCESS_20 || '预览',
			desc : wcm.LANG.DOCUMENT_PROCESS_164 || '预览这篇/些文档',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 38
		},{
			id : 'document_publish',
			fn : function(event, elToolbar){
				wcm.domain.ChnlDocMgr.basicpublish(event);
			},
			name : wcm.LANG.DOCUMENT_PROCESS_166 || '快速发布',
			desc : wcm.LANG.DOCUMENT_PROCESS_167 || '发布这篇/些文档',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 39
		},{
			id : 'document_move',
			fn : function(event, elToolbar){
				wcm.domain.ChnlDocMgr.move(event);
			},
			name : wcm.LANG.DOCUMENT_PROCESS_102 || '移动',
			desc : wcm.LANG.DOCUMENT_PROCESS_174 || '移动这篇/些文档到',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 33
		},{
			id : 'document_copy',
			fn : function(event, elToolbar){
				wcm.domain.ChnlDocMgr.copy(event);
			},
			name : wcm.LANG.DOCUMENT_PROCESS_103 || '复制',
			desc : wcm.LANG.DOCUMENT_PROCESS_175 || '复制这篇/些文档到',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 34
		},{
			id : 'document_quote',
			fn : function(event, elToolbar){
				wcm.domain.ChnlDocMgr.quote(event);
			},
			name : wcm.LANG.DOCUMENT_PROCESS_101 || '引用',
			desc : wcm.LANG.DOCUMENT_PROCESS_176 || '引用这篇/些文档到',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 34
		},{
			id : 'document_changestatus',
			fn : function(event, elToolbar){
				wcm.domain.ChnlDocMgr.changestatus(event);
			},
			name : wcm.LANG.DOCUMENT_PROCESS_243 ||'改变状态',
			desc : wcm.LANG.DOCUMENT_PROCESS_147 ||'改变这些文档的状态',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 35
		},{
			id : 'document_setright',
			fn : function(event, elToolbar){
				wcm.domain.ChnlDocMgr.setright(event);
			},
			name : wcm.LANG.DOCUMENT_PROCESS_244 ||'设置权限',
			desc : wcm.LANG.DOCUMENT_PROCESS_148 ||'设置这些文档的权限',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 61
		},{
			id : 'document_detailpublish',
			fn : function(event, elToolbar){
				wcm.domain.ChnlDocMgr.detailpublish(event);
			},
			name : wcm.LANG.DOCUMENT_PROCESS_245 ||'仅发布细览',
			desc : wcm.LANG.DOCUMENT_PROCESS_246 ||'仅发布这篇/些文档细览',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 39
		},{
			id : 'document_directpublish',
			fn : function(event, elToolbar){
				wcm.domain.ChnlDocMgr.directpublish(event);
			},
			name : wcm.LANG.DOCUMENT_PROCESS_247 ||'直接发布',
			desc : wcm.LANG.DOCUMENT_PROCESS_242 ||'发布这些文档，同步发布这些文档所有的引用文档',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 39
		},{
			id : 'document_recallpublish',
			fn : function(event, elToolbar){
				wcm.domain.ChnlDocMgr.recallpublish(event);
			},
			name : wcm.LANG.DOCUMENT_PROCESS_248 ||'撤销发布',
			desc : wcm.LANG.DOCUMENT_PROCESS_249 ||'撤销发布这篇/些文档',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 39
		},{
			id : 'document_directRecallpublish',
			fn : function(event, elToolbar){
				wcm.domain.ChnlDocMgr.directRecallpublish(event);
			},
			name : wcm.LANG.DOCUMENT_PROCESS_259 ||'直接撤销发布',
			desc : wcm.LANG.DOCUMENT_PROCESS_257 ||'撤销发布这些文档，同步撤销这些文档所有的引用文档',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 39
		},{
			id : 'document_backup',
			fn : function(event, elToolbar){
				wcm.domain.ChnlDocMgr.backup(event);
			},
			name : wcm.LANG.DOCUMENT_PROCESS_250 ||'产生版本',
			desc : wcm.LANG.DOCUMENT_PROCESS_251 ||'为这篇/些文档产生版本',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 32
		},{
			id : 'document_export',
			fn : function(event, elToolbar){
				wcm.domain.ChnlDocMgr["export"](event);
			},
			name : wcm.LANG.DOCUMENT_PROCESS_252 ||'导出文档',
			desc : wcm.LANG.DOCUMENT_PROCESS_253 ||'导出这篇/些文档',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 34
		},{
			id : 'document_docpositionset',
			fn : function(event, elToolbar){
				wcm.domain.ChnlDocMgr.docpositionset(event);
			},
			name : wcm.LANG.DOCUMENT_PROCESS_139 ||'调整顺序',
			desc : wcm.LANG.DOCUMENT_PROCESS_139 ||'调整顺序',
			isDisabled : function(event){
				if(!event || event.length() != 1) return true;
				if(event.getHost().getType() != WCMConstants.OBJ_TYPE_CHANNEL)
					return true;
			},
			rightIndex : 62
		},{
			id : 'document_changelevel',
			fn : function(event, elToolbar){
				wcm.domain.ChnlDocMgr.changelevel(event);
			},
			name : wcm.LANG.DOCUMENT_PROCESS_274 || '改变文档的密级',
			desc : wcm.LANG.DOCUMENT_PROCESS_275 || '改变这篇/些文档的密级',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 61
		},{
			id : 'document_backupmgr',
			fn : function(event, elToolbar){
				wcm.domain.ChnlDocMgr.backupmgr(event);
			},
			name : wcm.LANG.DOCUMENT_PROCESS_132 ||'管理这篇文档版本',
			desc : wcm.LANG.DOCUMENT_PROCESS_132 ||'管理这篇文档版本',
			isDisabled : function(event){
				if(!event || event.length() != 1) return true;
			},
			rightIndex : 32
		},{
			id : 'document_settopdocument',
			fn : function(event, elToolbar){
				wcm.domain.ChnlDocMgr.settopdocument(event);
			},
			name :wcm.LANG.DOCUMENT_PROCESS_232 ||'取消置顶',
			desc : wcm.LANG.DOCUMENT_PROCESS_232 ||'取消置顶',
			isDisabled : function(event){
				if(!event || event.length() != 1) return true;
				var docContext = event.getObj();
				if(docContext.getPropertyAsString("isTopped") == "true") {
					return false;
				} else {
					return true;
				}
			},
			rightIndex : 62
		},{
			id : 'document_commentmgr',
			fn : function(event, elToolbar){
				wcm.domain.ChnlDocMgr.commentmgr(event);
			},
			name : wcm.LANG.DOCUMENT_PROCESS_137 ||'管理评论',
			desc : wcm.LANG.DOCUMENT_PROCESS_138 ||'管理文档的评论',
			isDisabled : function(event){
				if(!event || event.length() != 1) return true;
				try{
					return !$MsgCenter.getActualTop().g_IsRegister['comment'];
				}catch(err){
					return true;
				}
			},
			rightIndex : 8
		},{
			id : 'document_trace',
			fn : function(event, elToolbar){
				wcm.domain.ChnlDocMgr.trace_document(event);
			},
			name : wcm.LANG.DOCUMENT_PROCESS_236 ||'跟踪文档',
			desc : wcm.LANG.DOCUMENT_PROCESS_236 ||'跟踪文档',
			isDisabled : function(event){
				if(!event || event.length() != 1) return true;
			},
			rightIndex : 32
		},{
			id : 'document_logo',
			fn : function(event, elToolbar){
				wcm.domain.ChnlDocMgr.logo(event);
			},
			name : wcm.LANG.DOCUMENT_PROCESS_206 ||'文档LOGO',
			desc : wcm.LANG.DOCUMENT_PROCESS_206 ||'文档LOGO',
			isDisabled : function(event){
				if(!event || event.length() != 1) return true;
			},
			rightIndex : 32
		},
		'/', {
			id : 'action_refresh',
			fn : function(event, elToolbar){
				PageContext.loadList({CurrPage:1});
			},
			name : wcm.LANG.DOCUMENT_PROCESS_168 || '刷新',
			desc : wcm.LANG.DOCUMENT_PROCESS_169 || '刷新列表'
		}
	],
	listTitle : wcm.LANG.DOCUMENT_PROCESS_170 || '文档列表管理',
	path : [
			'<div class="view_mode_recursive" style="display:none" id="divDispMode" title="',
			wcm.LANG['DOCUMENT_PROCESS_234'] || "显示子栏目的文档",
			'" onclick="switchChildrenDisp()" WCMAnt:paramattr="title:document_list.html.dispmode"></div>'
			].join('')
}