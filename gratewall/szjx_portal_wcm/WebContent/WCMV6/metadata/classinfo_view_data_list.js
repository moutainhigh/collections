/**
*关于调试的控制类
*/
var DebugHelper = Class.create();
Object.extend(DebugHelper.prototype, {
	isDebug : false,
	initialize : function(isDebug){
		this.isDebug = isDebug || false;
	},
	fAlert : function(sMsg){
		if(this.isDebug){
			alert(sMsg);
		}
	}
});

/**
*页面上下文控制类
*/
var PageContext = {
	params : {},
	isSortByView	: false,//检索结果是否按视图进行分类
	isContainChild	: false,//检索结果是否包含子分类的内容
	toMore : function(viewId, viewDesc){
		var sParams = "viewId=" + viewId + "&classInfoId=" + TreeHelper.getObjectId();
		sParams += "&viewDesc=" + encodeURIComponent(viewDesc);
		var sURL = "classinfo_view_data_list_more.html?" + sParams;
		window.open(sURL);
	},
	init : function(){
		this.loadData();
		this.bindEvents();
	},
	bindEvents : function(){
		//邦定切换查询类型事件
		Event.observe('queryType', 'click', function(event){
			event = window.event || event;
			var element = Event.element(event);

			if(element.tagName.toUpperCase() != "INPUT"){
				return;
			}
			PageContext[element.name] = element.checked;
			TreeHelper.clickTreeNode(null);

			if(element.id == 'isSortByView' && element.checked){
				Element.update(PageContext.PageNav.NavId, "");			
			}
		});

		//邦定标题检索事件
		Event.observe('searchBtn', 'click', function(event){
			PageContext.loadData();
		});
		Event.observe('docTitle', 'keydown', function(event){
			event = window.event || event;
			if(event.keyCode == Event.KEY_RETURN){
				$('searchBtn').click();
			}
		});

		//邦定单击列表页面分类法事件
		Event.observe('documentContainer', 'click', function(event){
			event = window.event || event;
			var element = Event.element(event);
			if(element.getAttribute("isClassInfo") != null){
				var objectId = element.getAttribute("objectId");
				TreeHelper.locateNode(objectId);
			}
		});
	},
	loadData : function(){
		this.isSortByView = $('isSortByView').checked;
		this.isContainChild = $('isContainChild').checked;
		TreeHelper.loaded = false;
		ViewHelper.loaded = false;
		ViewDataHelper.loaded = false;
		DocumentDataHelper.loaded = false;
		ClassInfoHelper.loaded = false;
		TreeHelper.loadData(null, null, TreeHelper.onLoaded);
	}
};

Event.observe(window, 'load', PageContext.init.bind(PageContext));

/**
*抽象控制类
*/
var AbstractHelper = {
	serviceName		: '',
	queryMethodName	: '',
	objectTemplate	: '',
	objectContainer	: '',
	debugHelper		: new DebugHelper(false),

	getHelper : function(){
		if(!this.helper){
			this.helper = new com.trs.web2frame.BasicDataHelper();
		}
		return this.helper;
	},
	getDefaultParams : function(){
		//get some default params for ajax request. if need, override the method.
		return {};
	},
	loadData : function(oParams, fBeforeCallBack, fAfterCallBack){
		this.debugHelper.fAlert('loadData...');
		if(fBeforeCallBack) fBeforeCallBack();
		oParams = Object.extend(this.getDefaultParams(), oParams || {});
		this.getHelper().call(this.serviceName, this.queryMethodName, oParams, true, function(transport, json){
			try{
				this.dataLoaded(transport, json);
				if(fAfterCallBack) fAfterCallBack(transport, json);
			}catch(error){
				//just skip it.
			}
		}.bind(this));
	},
	dataLoaded : function(transport, json){
		this.debugHelper.fAlert('dataLoaded...');
		if(this.formatJSON){
			json = this.formatJSON(json);
		}
        var sValue = TempEvaler.evaluateTemplater(this.objectTemplate, json);
        Element.update(this.objectContainer, sValue);	
		if(!this.loaded){
			this.loaded = true;
			this._onFirstLoaded(transport, json);
		}
	},
	_onFirstLoaded : function(transport, json){
		this.debugHelper.fAlert('onFirstLoaded...');
		this.onFirstLoaded(transport, json);
		this._bindEvents();
	},
	onFirstLoaded : function(){
		//do something here on the first loaded.
		//if need, override the method.
	},
	_bindEvents : function(){
		this.debugHelper.fAlert('bindEvents...');
		this.bindEvents();
	},
	bindEvents : function(){
		//if need, override the method.
	}
};

/**
*关于分类法的控制类
*/
var TreeHelper = {};
Object.extend(TreeHelper, AbstractHelper);
Object.extend(TreeHelper, {
	serviceName		: 'wcm6_ClassInfo',
	queryMethodName	: 'queryClassInfos',
	pathMethodName	: 'getPath',
	locateMethodName: 'createClassInfoTreeHTMLOfIds',
	loadChildMethodName : 'createClassInfoTreeHTML',
	objectTemplate	: 'treeTemplate',
	objectContainer	: 'treeContainer',
	getDefaultParams : function(){
		return {
			SelectFields : 'CLASSINFOID,CDESC,CNAME',
			FieldsToHTML : 'CDESC,CNAME'
		};
	},
	getObjectId : function(){
		return TreeHelper.objectId || null;	
	},
	onLoaded : function(transport, json){
		DocumentNumGetter.setDocumentNum();
		TreeHelper.clickTreeNode(null, true);
	},
	clickTreeNode : function(oTreeNode, isNotLastTreeNode){
		if(oTreeNode){
			com.trs.tree.TreeNav.expandToNode(oTreeNode);
			com.trs.tree.TreeNav.clickTreeNode(oTreeNode);
			return;
		}
		if(isNotLastTreeNode){
			com.trs.tree.TreeNav.clickTreeNode($('node_0'));
		}else{
			if(com.trs.tree.TreeNav.oPreSrcElementA){
				com.trs.tree.TreeNav.clickTreeNode(com.trs.tree.TreeNav.oPreSrcElementA);
			}
		}

	},
	locateNode : function(nodeId){
		var treeNode = $('node_' + nodeId);
		if(treeNode){
			this.clickTreeNode(treeNode);
			return;
		}
		this.getPath(nodeId, function(nodeIds){
			TreeHelper.getNodeHTML(nodeIds);
		});
	},
	getNodeHTML : function(nodeIds){
		this.getHelper().Call(this.serviceName, this.locateMethodName, {
			objectIds : nodeIds
		}, true, function(transport, json){
			TreeHelper.nodeHTMLGeted(transport, json, nodeIds);
		});
	},
	nodeHTMLGeted : function(transport, json, nodeIds, fCallBack){
		var aNodeId = nodeIds.split(",");
		com.trs.tree.TreeNav.setNodeContent("node_" + aNodeId[0], transport);
		var treeNode = $('node_' + aNodeId.last());
		if(treeNode){
			TreeHelper.clickTreeNode(treeNode);
		}
	},
	getPath : function(nodeId, fCallBack){
		this.getHelper().Call(this.serviceName, this.pathMethodName, {
			objectId : nodeId
		}, true, function(transport, json){
			TreeHelper.pathGeted(transport, json, fCallBack);
		});
	},
	pathGeted : function(transport, json, fCallBack){
		var sPath = transport.responseText;
		if(!sPath) return;
		var aNodeId = sPath.split(",");
		var index;
		for (index = 0; index < aNodeId.length; index++){
			if(!$('node_' + aNodeId[index])){
				index--;
				break;
			}
		}
		if(index < 0) return;
		if(fCallBack) fCallBack(aNodeId.slice(index).join());		
	},
	bindEvents : function(){
		if(com.trs.tree.TreeNav.initTreeBySelf){
			Event.stopAllObserving("treeContainer");
			com.trs.tree.TreeNav.initTree();
		}
	}
});

Object.extend(com.trs.tree.TreeNav, {
	initTreeBySelf : true,
	expandToNode : function(sNodeId){
		var tempNode = $(sNodeId);
		while(tempNode){
			if(tempNode.id == "treeContainer" 
					|| tempNode.tagName == "BODY"){
				break;
			}
			if(tempNode.getAttribute("isRoot")){
				Element.removeClassName(tempNode, "Root");
				Element.addClassName(tempNode, "RootOpened");
				break;
			}
			tempNode = tempNode.parentNode;
			if(!tempNode){
				break;
			}
			tempNode.style.display = '';
			do{
				tempNode = getPreviousHTMLSibling(tempNode);
			}while(tempNode && !tempNode.getAttribute("objectId"));
		}
	},
	clickTreeNode : function(treeNode){
		var treeNode = $(treeNode);
		if(treeNode){
			if(treeNode.tagName != "A"){
				treeNode = treeNode.getElementsByTagName("a")[0];
			}
			treeNode.click();
		}		
	},
	/**
	*设置某个树节点的内容
	*/
	setNodeContent : function(sNodeId, _oOriginRequest){
		var oTopContent = this._getTopChildElement($(sNodeId));
		Element.update(oTopContent, _oOriginRequest.responseText);
		this._initChildrenNodes(oTopContent);
		oTopContent.style.display = '';
		
		//get childids.
		var aNodeId = [];
		var aDivNode = oTopContent.getElementsByTagName("div");
		for (var i = 0; i < aDivNode.length; i++){
			var objectId = aDivNode[i].getAttribute("objectId");
			if(objectId){
				aNodeId.push(objectId);
			}
		}
		DocumentNumGetter.setDocumentNum(aNodeId.join(), true);
	},
	_updateTopChildContent : function(_oOriginRequest, nodeIds){
		//防止页面上出现断横
		if(_oOriginRequest.responseText == ''){
			this.style.display = 'none';
		}
		var caller = com.trs.tree.TreeNav;
		caller._updateTopChildContent0.apply(this, arguments);
		var treeNode = getPreviousHTMLSibling(this);
		if(!treeNode || treeNode.getAttribute("objectId") == null) return;
		DocumentNumGetter.setDocumentNum(treeNode.getAttribute("objectId"));
	},		
	doActionOnClickA : function(_event,_elElementA){
		//change the style.
		if(this.lastSecondSrcElementA){
			if(this.lastSecondSrcElementA.parentNode){
				Element.removeClassName(this.lastSecondSrcElementA.parentNode, 'ActiveItem');
			}
		}
		if(_elElementA && _elElementA.parentNode){
			Element.addClassName(_elElementA.parentNode, 'ActiveItem');
		}

		//1.get classinfoid.
		var sClassInfoId = _elElementA.parentNode.getAttribute("objectId");
		TreeHelper.objectId = sClassInfoId;

		//2.load the view infos.
		//ViewHelper.loadData({ClassInfoId : sClassInfoId}, null, ViewHelper.onLoaded);
		DispatchHelper.loadData({ClassInfoId : sClassInfoId});
		window.event.returnValue = false;

		_elElementA.blur();

		return false;
	},
	makeGetChildrenHTMLAction : function(divElement){
		var urlPrefix = '/'+(location.pathname.split('/')[1]||'wcm')+'/center.do?serviceid=' + TreeHelper.serviceName + '&methodname='; 
		var params = "&parentId=" + divElement.getAttribute("objectId");
		return urlPrefix + TreeHelper.loadChildMethodName + params;
	}
});

/**
*数据载入分发器
*/
var DispatchHelper = {
	loadData : function(oParams){
		if(PageContext.isSortByView){
			ViewHelper.loadData(oParams, null, ViewHelper.onLoaded);
		}else{
			DocumentDataHelper.loadData(oParams, null, DocumentDataHelper.onLoaded);
		}
	}
};

/**
*关于视图的控制类
*/
var ViewHelper = {};
Object.extend(ViewHelper, AbstractHelper);
Object.extend(ViewHelper, {
	serviceName		: 'wcm6_ClassInfoView',
	queryMethodName	: 'queryViews',
	objectTemplate	: 'viewTemplate',
	objectContainer	: 'documentContainer',

	getDefaultParams : function(){
		return {
			PageSize		: -1,
			FieldsToHTML	: 'VIEWNAME,VIEWDESC',
			WithChildren	: PageContext.isContainChild,
			SelectFields	: 'VIEWINFOID,VIEWDESC'
		};
	},
	onLoaded : function(transport, json){
		var aMetaViwes = $a(json, "METAVIEWS.METAVIEW");
		if(!aMetaViwes || aMetaViwes.length <= 0){
			return;
		}
		ViewDataHelper.loadData(null, null, ViewDataHelper.onLoaded, aMetaViwes);
	}
});

/**
*视图数据相关控制类
*/
var ViewDataHelper = {};
Object.extend(ViewDataHelper, AbstractHelper);
Object.extend(ViewDataHelper, {
	serviceName		: 'wcm6_ClassInfoView',
	queryMethodName	: 'queryDocuments',
	objectTemplate	: 'viewDataTemplate',
	objectContainer	: 'viewDataContainer_',
	pageSize		: 5,

	getDefaultParams : function(){
		var params = {
			FieldsToHTML	: 'DocTitle,ViewDesc',
			SelectFields	: 'DocId,DocTitle,CrUser,CrTime,DOCSTATUS,DOCCHANNEL,DocKind,ViewDesc',
			pageSize		: this.pageSize
		};
		var docTitle = $F('docTitle').trim();
		if(docTitle.length != 0){
			params['docTitle'] = docTitle;
		}
		return params;
	},
	loadData : function(oParams, fBeforeCallBack, fAfterCallBack, aMetaViews){
		var oHelper = this.getHelper();
        var aCombine = [];
		for (var i = 0; i < aMetaViews.length; i++){
			var params = {
					WithChildren: PageContext.isContainChild,
					ViewId		: $v(aMetaViews[i], "VIEWINFOID"),
					ClassInfoId	: TreeHelper.getObjectId() || 0
			};
			Object.extend(params, this.getDefaultParams() || {});
			aCombine.push(oHelper.Combine(
					this.serviceName,
					this.queryMethodName,
					params
			));
		}
        oHelper.MultiCall(aCombine, function(transport, json){
			if(fAfterCallBack) fAfterCallBack(transport, json, aMetaViews);
		});        
	},
	onLoaded : function(transport, json, aMetaViews){
		var aDocuments = [];
		var aDocumentss = $a(json, "MULTIRESULT.DOCUMENTS") || $a(json, "DOCUMENTS");
		for (var i = 0; i < aMetaViews.length; i++){
			aDocuments.push.apply(aDocuments, $a(aDocumentss[i], "DOCUMENT") || []);
			var viewId = $v(aMetaViews[i], "VIEWINFOID");
			var sValue = TempEvaler.evaluateTemplater(ViewDataHelper.objectTemplate, aDocumentss[i]);
			Element.update(ViewDataHelper.objectContainer + viewId, sValue);	
			if(parseInt(aDocumentss[i]["NUM"], 10) > ViewDataHelper.pageSize){
				Element.show("viewDataMore_" + viewId);
			}
		}
		//load classinfos.
		ClassInfoHelper.loadData(null, null, ClassInfoHelper.onLoaded, aDocuments);
	}
});


var DocumentDataHelper = {};
Object.extend(DocumentDataHelper, AbstractHelper);
Object.extend(DocumentDataHelper, {
	serviceName		: 'wcm6_ClassInfoView',
	queryMethodName	: 'queryDocuments',
	objectTemplate	: 'viewDataTemplate',
	objectContainer	: 'documentContainer',
	getDefaultParams : function(){
		var params = {
			FieldsToHTML	: 'DocTitle,ViewDesc',
			SelectFields	: 'DocId,DocTitle,CrUser,CrTime,DOCSTATUS,DOCCHANNEL,DocKind,ViewDesc',
			CurrPage		: PageContext.params["CurrPage"] || 1,
			PageSize		: 20,
			WithChildren	: PageContext.isContainChild
		};
		var docTitle = $F('docTitle').trim();
		if(docTitle.length != 0){
			params['docTitle'] = docTitle;
		}
		return params;
	},
	onLoaded : function(transport, json){
		json = json["DOCUMENTS"];
		PageContext.PageCount = json["PAGECOUNT"]||1;
		PageContext.RecordNum = json["NUM"]||0;
		PageContext.PageSize = json["PAGESIZE"]||DocumentDataHelper["pageSize"];
		PageContext.drawNavigator();

		var aDocuments = $a(json, "DOCUMENT");
		if(!aDocuments || aDocuments.length <= 0){
			return;
		}

		//load classinfos.
		ClassInfoHelper.loadData(null, null, ClassInfoHelper.onLoaded, aDocuments)
	},
	formatJSON : function(json){
		return json["DOCUMENTS"];
	}
});

/**
*视图数据相关控制类
*/
var ClassInfoHelper = {};
Object.extend(ClassInfoHelper, AbstractHelper);
Object.extend(ClassInfoHelper, {
	serviceName		: 'wcm6_ClassInfoView',
	queryMethodName	: 'queryClassInfos',
	objectTemplate	: 'classInfoTemplate',
	objectContainer	: 'classinfoContainer_',
	pageSize		: -1,

	loadData : function(oParams, fBeforeCallBack, fAfterCallBack, aDocuments){
		var oHelper = this.getHelper();
        var aCombine = [];
		for (var i = 0; i < aDocuments.length; i++){
			aCombine.push(oHelper.Combine(
					this.serviceName,
					this.queryMethodName,
					{
						SelectFields: 'CLASSINFOID,CDESC,CNAME',
						FieldsToHTML: 'CDESC,CNAME',
						PageSize	: this.pageSize,
						DocId		: $v(aDocuments[i], "DOCID")
					}
			));
		}
        oHelper.MultiCall(aCombine, function(transport, json){
			if(fAfterCallBack) fAfterCallBack(transport, json, aDocuments);
		});        
	},
	onLoaded : function(transport, json, aDocuments){
		var aClassInfos = $a(json, "MULTIRESULT.CLASSINFOS") || $a(json, "CLASSINFOS");
		for (var i = 0; i < aDocuments.length; i++){
			var documentId = $v(aDocuments[i], "DOCID");
			var sValue = TempEvaler.evaluateTemplater(ClassInfoHelper.objectTemplate, aClassInfos[i]);
			Element.update(ClassInfoHelper.objectContainer + documentId, sValue);	
		}
	}
});

/**
*文档数目载入统计信息类
*/
var DocumentNumGetter = {
	serviceName		: 'wcm6_ClassInfoView',
	queryMethodName	: 'queryDocNumMapping',
	setDocumentNum : function(classInfoId, isIds){
		var oParams = {};
		if(isIds){
			oParams["classInfoIds"] = classInfoId;
		}else{
			oParams["classInfoId"] = classInfoId || 0;
		}
		var docTitle = $F('docTitle').trim();

		if(docTitle.length != 0){
			oParams['docTitle'] = docTitle;
		}
		new com.trs.web2frame.BasicDataHelper().Call(
			this.serviceName, 
			this.queryMethodName,
			oParams, 
			true, 
			function(transport, json){
				DocumentNumGetter.appendDocNumToTree(json);
			}	
		);		
	},

	//将文档数目追加到分类法上
	appendDocNumToTree : function(json){
		if(!json) return;
		for(objectId in json){
			var treeNode = $('node_' + objectId);
			if(treeNode == null) continue;
			if(treeNode.getAttribute("isLoaded")) continue;
			treeNode.setAttribute("isLoaded", true);
			var linkNode = treeNode.getElementsByTagName("A")[0];
			if(linkNode == null) continue;
//			linkNode.innerHTML = linkNode.innerHTML + "<font style='font-size:12px;color:gray;'>(" + json[objectId] + ")</font>";
			new Insertion.After(linkNode, "<a style='font-size:12px;color:gray;'>(" + json[objectId] + ")</a>");
		}
	}
}


/*--------------------------------------Page Navigator------------------------------------*/
PageContext.getPageNavHtml = function(iCurrPage,iPages){
	var sHtml = '';
	//output first
	if(iCurrPage!=1){
		sHtml += '<span class="nav_page" title="首页" onclick="PageContext.PageNav.goFirst();">1</span>';
	}else{
		sHtml += '<span class="nav_page nav_currpage" title="首页">1</span>';
	}
	var i,j;
	if(iPages-iCurrPage<=1){
		i = iPages-3;
	}
	else if(iCurrPage<=2){
		i = 2;
	}
	else{
		i = iCurrPage-1;
	}
	var sCenterHtml = '';
	var nFirstIndex = 0;
	var nLastIndex = 0;
	//output 3 maybe
	for(j=0;j<3&&i<iPages;i++){
		if(i<=1)continue;
		j++;
		if(j==1)nFirstIndex = i;
		if(j==3)nLastIndex = i;
		if(iCurrPage!=i){
			sCenterHtml += '<span class="nav_page" onclick="PageContext.PageNav.go('+i+','+iPages+');">'+i+'</span>';
		}else{
			sCenterHtml += '<span class="nav_page nav_currpage">'+i+'</span>';
		}
	}
	//not just after the first page
	if(nFirstIndex!=0&&nFirstIndex!=2){
		sHtml += '<span class="nav_morepage" title="更多">...</span>';
	}
	sHtml += sCenterHtml;
	//not just before the last page
	if(nLastIndex!=0&&nLastIndex!=iPages-1){
		sHtml += '<span class="nav_morepage" title="更多">...</span>';
	}
	//output last
	if(iCurrPage!=iPages){
		sHtml += '<span class="nav_page" title="尾页" onclick="PageContext.PageNav.goLast();">'+iPages+'</span>';
	}else{
		sHtml += '<span class="nav_page nav_currpage" title="尾页">'+iPages+'</span>';
	}
	return sHtml;
}
PageContext.getNavigatorHtml = function(){
	var iRecordNum = parseInt(PageContext["RecordNum"]);
	if(iRecordNum==0)return '';
	var iCurrPage = parseInt(PageContext.params["CurrPage"]||1);
	var iPageSize = parseInt(PageContext["PageSize"]);
	var iPages = parseInt(PageContext["PageCount"]);
	var sHtml = '';
	var sTypeDesc = PageContext.PageNav["UnitName"]+PageContext.PageNav["TypeName"];
	sHtml += '<span class="nav_page_detail">共<span class="nav_pagenum">'+iPages+'</span>页'
				+'<span class="nav_recordnum">'+iRecordNum+'</span>'
				+sTypeDesc+',每页<span class="nav_pagesize">'+iPageSize+'</span>'+sTypeDesc
				+'.</span>';
//				+',当前为第<span class="nav_currpage">'+iCurrPage+'</span>页.</span>';
	/*
	if(iPages>1){
		sHtml += '<span class="nav_go">转到第<input type="text" name="nav_go_num" id="nav_go_num">页'
					+'<span class="nav_go_btn" onclick="PageContext.PageNav.go($(\'nav_go_num\').value,'
					+iPages+')"></span></span>';
	}
	*/
	if(iPages>1){
		sHtml += PageContext.getPageNavHtml(iCurrPage,iPages);
	}
	return sHtml;
	//
	if(iPages>1){
		sHtml += '<span class="nav_specpage_go">';
		if(iCurrPage!=1){
			sHtml += '<span class="wcm_pointer nav_go_first" title="首页" onclick="PageContext.PageNav.goFirst();"></span>';
			sHtml += '<span class="wcm_pointer nav_go_pre" title="上一页" onclick="PageContext.PageNav.goPre();"></span>';
		}else{
			sHtml += '<span class="nav_go_first_disabled"></span>';
			sHtml += '<span class="nav_go_pre_disabled"></span>';
		}
		if(iCurrPage!=iPages){
			sHtml += '<span class="wcm_pointer nav_go_next" title="下一页" onclick="PageContext.PageNav.goNext();"></span>';
			sHtml += '<span class="wcm_pointer nav_go_last" title="尾页" onclick="PageContext.PageNav.goLast();"></span>';
		}else{
			sHtml += '<span class="nav_go_next_disabled"></span>';
			sHtml += '<span class="nav_go_last_disabled"></span>';
		}

		sHtml += '</span>';
	}
	return sHtml;
}
PageContext.drawNavigator = function(){
	var eNavigator = $(PageContext.PageNav.NavId);
	if(!eNavigator)return;
	var sHtml = PageContext.getNavigatorHtml();
	Element.update(eNavigator,sHtml);
}
PageContext.PageNav = {
	UnitName : '条',
	TypeName : '记录',
	NavId : 'list_navigator',
	go : function(_iPage,_iPageNum){
		alert('must implements');
	},
	goFirst : function(){
		PageContext.PageNav.go(1,PageContext["PageCount"]);
	},
	goPre : function(){
		if(PageContext.params["CurrPage"]>1){
			PageContext.PageNav.go(PageContext.params["CurrPage"]-1,PageContext["PageCount"]);
		}
	},
	goNext : function(){
		if((PageContext.params["CurrPage"]||1)<PageContext["PageCount"]){
			PageContext.PageNav.go((PageContext.params["CurrPage"]||1)+1,PageContext["PageCount"]);
		}
	},
	goLast : function(){
		PageContext.PageNav.go(PageContext["PageCount"],PageContext["PageCount"]);
	}
}
Object.extend(PageContext.PageNav,{
	go : function(_iPage,_maxPage){
		if(_iPage>_maxPage){
			_iPage = _maxPage;
		}
		Object.extend(PageContext.params,{"CurrPage":_iPage});
		DocumentDataHelper.loadData({ClassInfoId : TreeHelper.getObjectId() || 0}, null, DocumentDataHelper.onLoaded);
	}
});
