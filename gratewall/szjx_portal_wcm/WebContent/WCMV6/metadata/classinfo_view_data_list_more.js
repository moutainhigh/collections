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
	loadData : function(){
		ViewDataHelper.loadData(null, null, ViewDataHelper.onLoaded);
	}
};

Event.observe(window, 'load', PageContext.loadData.bind(PageContext));

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
			this.dataLoaded(transport, json);
			if(fAfterCallBack) fAfterCallBack(transport, json);
		}.bind(this));
	},
	dataLoaded : function(transport, json){
		this.debugHelper.fAlert('dataLoaded...');
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
*视图数据相关控制类
*/
var ViewDataHelper = {};
Object.extend(ViewDataHelper, AbstractHelper);
Object.extend(ViewDataHelper, {
	serviceName		: 'wcm6_ClassInfoView',
	queryMethodName	: 'queryDocuments',
	objectTemplate	: 'viewDataTemplate',
	objectContainer	: 'viewDataContainer',
	pageSize		: 20,

	getDefaultParams : function(){
		return {
			FieldsToHTML	: 'DocTitle,ViewDesc',
			SelectFields: 'DocId,DocTitle,CrUser,CrTime,DOCSTATUS,DOCCHANNEL,DocKind,ViewDesc',
			PageSize	: this.pageSize,
			CurrPage	: PageContext.params["CurrPage"] || 1,
			WithChildren: true,
			ViewId		: getParameter("viewId") || 0,
			ClassInfoId	: getParameter("classInfoId") || 0
		};
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
	},

	onLoaded : function(transport, json){
		var sValue = TempEvaler.evaluateTemplater(ViewDataHelper.objectTemplate, json["DOCUMENTS"]);
		Element.update(ViewDataHelper.objectContainer, sValue);	

		//Init PageContext
		Object.extend(PageContext, {
			PageSize	: ViewDataHelper.pageSize,
			PageCount	: json["DOCUMENTS"]["PAGECOUNT"],
			RecordNum	: json["DOCUMENTS"]["NUM"]
		});

		//load classinfos.
		var aDocuments = $a(json, "DOCUMENTS.DOCUMENT");
		ClassInfoHelper.loadData(null, null, ClassInfoHelper.onLoaded, aDocuments);

		PageContext.drawNavigator();
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
						SelectFields: 'CLASSINFOID,CDESC',
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
		PageContext.loadData();
	}
});
