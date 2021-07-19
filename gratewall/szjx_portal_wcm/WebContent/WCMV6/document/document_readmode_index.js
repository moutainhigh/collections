ProcessBar = (top.actualTop || top).ProcessBar;
Object.extend(PageContext,{
	ObjectServiceId : 'wcm6_viewdocument',
	ObjectMethodName : 'query',
	m_bFirstLoad : true,
	m_bInited : false,
	init : function(){
		this.m_bInited = true;
		this.extraParams = getExtraExchangeParams() || {
			CurrPage: 1,
			CurrDocId: 0,//将选中第一行		
			FilterType: (top.$personalCon.listDefaultFilter || {}).paramValue || 0
		};//用于页面之间传递的变量
		//添加检索的参数
		if(this.extraParams.searchPostData){
			PageContext.search(this.extraParams.searchPostData);
		}else{
			if(this.extraParams["OrderBy"]){
				PageContext.initOrder(this.extraParams["OrderBy"]);
			}
			PageContext.refresh(null, this.extraParams);
		}
	},
	m_baseQeuryInfo: {
		PageSize : (top.$personalCon.listPageSize || {}).paramValue || 20,
		containsRight : true,
		selectFields : "DocTitle,DocId,CrTime,CrUser,DOCSTATUS,DocType,DocChannel",
		fieldsToHtml : 'DocTitle,DocChannel.Name'	
	},
	initParams : function(){//获得基本参数信息
		PageContext.queryInfo = {};
		if(this.m_bFirstLoad){
			this.m_bFirstLoad = false;
			//gfc
			Object.extend(PageContext.queryInfo, {
				CurrPage : PageContext.extraParams.CurrPage,
				FilterType : PageContext.extraParams.FilterType
			});
		}
	},
	search : function(_queryInfo){//用于高级检索
		if(!this.m_bInited) {
			return;
		}
		//alert('==>' + $toQueryStr(_queryInfo));
		//1 重新指定检索条件
		this.initParams();
		Object.extend(PageContext.queryInfo, _queryInfo);
		PageContext['from_search'] = true;
		//2 加载
		PageContext.loadPage();

		//3 使suggestion无效
		$('locationTxt').value = '检索结果的阅读模式';
		$('locationTxt').disabled = true;
		Element.hide('goToParent');

	},
	refresh : function(_sPageParams, _orderParams){
		//1.1 指定检索条件
		this.initParams();
		window.location_search = (_sPageParams != null) ? ('?' + _sPageParams) : location.search;
		var oParams = window.location_search.toQueryParams();
		//set the query region
		var sChannelids = oParams["channelid"] || PageContext.queryInfo['channelids'];
		var sSiteIds = oParams["siteid"] || PageContext.queryInfo['siteids'];
		Object.extend(PageContext.queryInfo, {
			channelids: sChannelids ||'',
			siteids: sSiteIds || ''
		});
		Object.extend(PageContext.params, oParams);
		PageContext['from_search'] = false;
		//1.2 指定排序条件
		Object.extend(PageContext.queryInfo, this.getOrderInfo(_orderParams));

		//2 加载
		PageContext.loadPage();

		//3 初始化 suggestion
		initSuggestionValue();
	},
	loadPage : function(_bNotLoadFilters){//载入页面所有信息：pageinfo,list
        ProcessBar.init('进度执行中，请稍候...', 'document', window);
        ProcessBar.addState('正在获取数据');
		if(_bNotLoadFilters != true) {
			 ProcessBar.addState('正在加载过滤器列表');
		}
       
        ProcessBar.addState('正在加载文档列表');
        ProcessBar.addState('正在生成事件绑定');
		ProcessBar.addState('正在加载文档详细信息');
        ProcessBar.addState('加载完成');
        ProcessBar.start();	
		$('contentDiv').scrollTop = 0;
		Object.extend(PageContext.queryInfo, {
			'ContainsRight': true
		});
		var postData = Object.extend({}, this.m_baseQeuryInfo);
		Object.extend(postData, PageContext.queryInfo);
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call(PageContext.ObjectServiceId, PageContext.ObjectMethodName, postData, true, function(_transport, _json){
			this.pageLoaded(_transport, _json, _bNotLoadFilters);
		}.bind(this));
	},
	PageFilters : [
		{Name:'全部文档',Type:0,IsDefault:true},
		{Name:'新稿',Type:1},
		{Name:'可发',Type:2},
		{Name:'已发',Type:3},
		{Name:'已否',Type:8},
		{Name:'我创建的',Type:4},
		{Name:'最近三天',Type:5},
		{Name:'最近一周',Type:6},
		{Name:'最近一月',Type:7}
	],
	getOrderInfo : function(_orderParams){
		var result = {};
		if(_orderParams && _orderParams['OrderBy']){
			Object.extend(result, {"OrderBy" : _orderParams['OrderBy']});
		}
		return result;
	},
	getPageInfo : function(){
		var ret = {
			"PAGEINFO":{
				"FILTERS":{
					"FILTER":[
					]
				}
			}
		};
		var arrFilters = ret.PAGEINFO.FILTERS.FILTER;
		for(var i=0;i<PageContext.PageFilters.length;i++){
			var filter = PageContext.PageFilters[i];
			var newFilter = {};
			for(var n in filter){
				newFilter[n.toUpperCase().trim()] = filter[n];
			}
			arrFilters.push(newFilter);
		}
		return ret;
	},
	pageLoaded : function(_transport, _json, _bNotLoadFilters){
		Documents.lastCheckedRow = null;
		this.extraParams.num = _json["VIEWDOCUMENTS"]["NUM"];
		this.extraParams.pagesize = _json["VIEWDOCUMENTS"]["PAGESIZE"];
		
		//alert($toQueryStr(this.extraParams))
		var startIndex = (PageContext.queryInfo['CurrPage'] - 1) * this.extraParams.pagesize;
		ProcessBar.next();
		setTimeout(function(){
			//gfc
			var sValue = '';
			if(_bNotLoadFilters != true) {
				sValue = TempEvaler.evaluateTemplater('pageinfo_template', PageContext.getPageInfo(), {});
				$('pageinfo').style.display = 'none';
				Element.update($('pageinfo'), sValue);
				PageFilter.bindEvents();
				PageFilter.selectFilterByType(PageContext.extraParams['FilterType'] || 0);//选中当前过滤器
				ProcessBar.next();
			}

			setTimeout(function(){
				_json = _json["VIEWDOCUMENTS"];
				sValue = TempEvaler.evaluateTemplater('documents_template', _json, {START_INDEX : startIndex});
				$removeChilds($('documents'));
				new Insertion.Bottom($('documents'), sValue);
				ProcessBar.next();
				setTimeout(function(){
					//gfc
					PageContext.setFooterImg(_json);			
					
					PageContext.bindEvents();
					PageContext.registerKeyEvent();
					ProcessBar.next();
					setTimeout(function(){
						PageContext.loadDocumentDetail();
						ProcessBar.next();
						setTimeout(ProcessBar.next.bind(ProcessBar), 100);
					}, 100);
				}, 100);
			}, 100);
		}, 100);
	},
	/**
	 *按指定方向进行追加文档信息
	 *@direction : forward, backword
	 */
	appendDocuments : function(direction){
		var paramInfo = this.getParamsInfo();
		if(paramInfo == null) return; //当前没有一篇文档,将不会有更多文档
		if((paramInfo.CurrIndex <= 1 && direction == 'backward') ||
				(paramInfo.CurrIndex >= this.extraParams.num && direction == 'forward'))  //文档全部加载完毕			
			return;
		ProcessBar.init('进度执行中，请稍候...', 'document', window);
		ProcessBar.addState('正在获取数据');
		ProcessBar.addState('正在加载文档列表');
		ProcessBar.addState('正在生成事件绑定');
		ProcessBar.addState('正在加载文档详细信息');
		ProcessBar.addState('加载完成');
		ProcessBar.start();
		
		PageContext.queryInfo['CurrPage'] = (direction == 'backward' ? paramInfo.CurrPage - 1 : paramInfo.CurrPage + 1);
		
		var postData = Object.extend({}, this.m_baseQeuryInfo);
		Object.extend(postData, PageContext.queryInfo);
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call(PageContext.ObjectServiceId, PageContext.ObjectMethodName, postData, true, this.documentsAppended.bind(this, direction));	
	},
	documentsAppended : function(direction, _transport, _json){		
		this.extraParams.num = _json["VIEWDOCUMENTS"]["NUM"];//不断修正总条数
		this.extraParams.pagesize = _json["VIEWDOCUMENTS"]["PAGESIZE"];
		var startIndex = (PageContext.queryInfo['CurrPage'] - 1) * this.extraParams.pagesize;
		ProcessBar.next();		
		setTimeout(function(){
			var sValue = TempEvaler.evaluateTemplater('documents_template', _json["VIEWDOCUMENTS"], {"START_INDEX" : startIndex});				
			if(direction == 'backward'){
				new Insertion.Top($('documents'), sValue);
				Documents.selectPreRow();
				Documents.lastCheckedRow.scrollIntoView(false);
			}else{
				new Insertion.Bottom($('documents'), sValue);
				Documents.selectNextRow();
				Documents.lastCheckedRow.scrollIntoView(true);
			}
			ProcessBar.next();
			setTimeout(function(){
				PageContext.bindEvents(Documents.lastCheckedRow, direction);
				ProcessBar.next();
				PageContext.setFooterImg(_json["VIEWDOCUMENTS"]);
				setTimeout(function(){			
					PageContext.loadDocumentDetail();					
					ProcessBar.next();
					setTimeout(ProcessBar.next.bind(ProcessBar), 100);
					eval('PageContext.extraParams.' + direction + 'Locked = false');//取消当前的锁定状态
				}, 100);
			}, 100);				
		}, 100);			
	},
	getParamsInfo : function(){//得到有关当前currPage、currDocId信息
		if(Documents.lastCheckedRow == null) return null; 
		var CurrDocId = Documents.lastCheckedRow.getAttribute("_recid");
		var CurrIndex = parseInt($('indexId_' + CurrDocId).innerText);
		//alert('paramInfo.CurrIndex:' + CurrIndex + '\n paramInfo.CurrPage:' + this.extraParams.pagesize)
		var CurrPage = Math.ceil(CurrIndex / this.extraParams.pagesize);
		return {CurrPage : CurrPage, CurrDocId : CurrDocId, CurrIndex : CurrIndex};
	},
	callBackByEditor : function(oDocInfo){//被文档编辑页面调用
		if(!oDocInfo["isNewDoc"]){
			PageContext.updateCurrRows(oDocInfo["CurrDocId"]);
		}else{
			PageContext.refresh(window.location_search.substring(1));
		}
	},
	updateCurrRows : function(_currId){
		this.RefreshList(PageContext.params.chnldocid, _currId);
	},
	RefreshList : function(_chnldocid, _docid){//对单篇文档进行刷新，未指定ID则刷新当前选中的文档
		if(_chnldocid == undefined && Documents.lastCheckedRow){
			_chnldocid = Documents.lastCheckedRow.getAttribute("_recid");
		}
		if(_chnldocid != undefined && Documents.lastCheckedRow){
			if(Documents.lastCheckedRow.getAttribute("_recid") == _chnldocid){
				this.loadDocumentDetail();
			}
		}
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call(this.ObjectServiceId, 'findbyid', {
			//objectid: _docid,
			objectid:_chnldocid,
			channelid: Documents.lastCheckedRow.getAttribute('_channelid', 2) || 0
		}, false, function(_transport,_json){
			var index = $('indexId_' + _chnldocid).innerText;
			_json["VIEWDOCUMENT"]['RECID'] = _chnldocid; // 附加一个chnldocid信息，以便在页面中输出
			var sValue = TempEvaler.evaluateTemplater('document_template', _json["VIEWDOCUMENT"]);	
			Element.update("_row_" + _chnldocid, sValue);
			$('indexId_' + _chnldocid).innerHTML = index;
		});
	},
	loadDocumentDetail : function(){
		//判断当前详细页面是否是Detail页面本身，如果不是，则将详细页面置为Detail页面
		var detailFrameObj = $('document_detail');

		if(!detailFrameObj.contentWindow.PageContext || !detailFrameObj.contentWindow.PageContext.response){
			detailFrameObj.setAttribute("src", 'document_detail.html');

			if(detailFrameObj.readyState != 'complete'){
				//直到载入完成才执行此方法
				if(_IE){//如果是IE
					detailFrameObj.onreadystatechange = function(){
						if(this.readyState == 'complete'){
							PageContext.loadDocumentDetail();
							this.onreadystatechange = null;
						}
					}
				}else{
					setTimeout(PageContext.loadDocumentDetail, 100);
				}			
				return;
			}
		}

		var currDocObj = Documents.lastCheckedRow;
		if(!currDocObj){
			if(this.extraParams.CurrDocId){
				currDocObj = $('_row_' + this.extraParams.CurrDocId);
			}	
			if(!currDocObj){
				currDocObj = getFirstHTMLChild($('documents'));
				if(!Element.hasClassName(currDocObj, 'document_list_row')){//没有一篇文档
					detailFrameObj.contentWindow.document.body.style.display = 'none';
					return; 
				}
			}			
			detailFrameObj.contentWindow.document.body.style.display = '';
			Documents.selectCurrRow(currDocObj);
			currDocObj.scrollIntoView();
		}
		// 记录一下当前的chnldocid
		PageContext.params.chnldocid = currDocObj.getAttribute('_recid', 2);
		// 读取跟document实体相关的信息
		var docId = currDocObj.getAttribute("_docid");
		var right = currDocObj.getAttribute("_right");
		var channelid = currDocObj.getAttribute("_channelid");
		try{
			$MessageCenter.sendMessage('document_detail', 'PageContext.response', 'PageContext', {
				objectid:docId,
				right:right,
				'channelid': channelid,
				chnldocid:　PageContext.params.chnldocid
			}, true, true);
		}catch(error){
			//TODO logger
			//alert(error.message);
		}
	},
	/*
	 *邦定左边列表行的click事件
	 *@startDomObj 从这个节点开始邦定，未指定时则从第一个开始
	 *@direction 邦定的方向．backward表示向序号减小的方向邦定，反之，则向变大的方向邦定
	 */
	bindEvents : function(startDomObj, direction){
		if(startDomObj == undefined){
			startDomObj = getFirstHTMLChild($('documents'));
			if(!Element.hasClassName(startDomObj, 'document_list_row')){
				return; //当前没有文档
			}
		}
		var directionMethod = (direction == 'backward' ? getPreviousHTMLSibling : getNextHTMLSibling);
		while(startDomObj){
			Event.observe(startDomObj, 'click', function(event){
				Documents.selectCurrRow(this);
				PageContext.loadDocumentDetail();
			}.bind(startDomObj));	
			startDomObj = directionMethod(startDomObj);
		}
	},
	/*
	 *邦定左边列表行的方向键事件
	 */
	registerKeyEvent : function(){
		PageEventHandler.register(Documents);
	},
	/*
	updateCurrRows : function(){
		var aRowIds = Grid.getRowIds();
		try{
			this.refreshDocuments();
		}catch(err){
			alert(err.message);
		}
	},
	*/
	setFooterImg : function(_oJson){
		$('docTotalNum').innerHTML = '共<b>' + _oJson["NUM"] + '</b>篇文档';
		if(_oJson["NUM"] == 0) return;
		var documentsDom = $('documents');
		var firstDocId = getFirstHTMLChild(documentsDom).getAttribute("_recid");
		var lastDocId = getLastHTMLChild(documentsDom).getAttribute("_recid");

		if($('indexId_' + firstDocId).innerText != 1){
			$('backwardImg').style.display = '';
		}else{
			$('backwardImg').style.display = 'none';
		}
		if($('indexId_' + lastDocId).innerText != _oJson["NUM"]){
			$('forwardImg').style.display = '';
		}else{
			$('forwardImg').style.display = 'none';
		}
	},
	clickMoreImg : function(direction){//点击更多图标时执行的操作
		if(this.extraParams.num == 0) return;
		if(direction == 'backward'){
			Documents.selectCurrRow(getFirstHTMLChild($('documents')));		
		}else{
			Documents.selectCurrRow(getLastHTMLChild($('documents')));
		}
		PageContext.appendDocuments(direction);
	},
	doChangeListView : function(){
		(top.actualTop || top).PageContext.mode = "normal";
		Object.extend(PageContext.queryInfo, PageContext.getParamsInfo());
		var params = Object.extend({}, PageContext.queryInfo);
		setExtraExchangeParams(params);
		if(PageContext['from_search'] == true) {
			// 转向检索列表
			var sUrlNormal	= 'document/document_normal_index.html?disable_sheet=1';
			$MessageCenter.changeSrc('main', sUrlNormal, PageContext.queryInfo);
			return;
		}

		//else 转向文档列表
		$changeSheet((top.actualTop || top).location_search);
	},
	toggleOrdersInfo : function(){
		var orderBtn = $('orderBtn');
		var orderList = $('document_list_orders');
		try{
			Position.clone(orderBtn, orderList, {setWidth:false,setHeight:false, offsetTop:$('orderBtn').offsetHeight,offsetLeft:$('orderBtn').offsetWidth});
			Element.toggle(orderList);
			if(Element.visible(orderList)){
				orderList.focus();
			}
		}catch(error){
			//TODO logger
			//alert(error.message);
		}
	},
	dealMenuOrder : function(event){
		Element.hide('document_list_orders');
		try{
			event = window.event || event;			
			var oSrcElement = Event.element(event);
			var orderField = oSrcElement.parentNode.getAttribute("_FieldName");
			if(!orderField) return;

			if(PageContext.lastClicked != oSrcElement){
				if(PageContext.lastClicked){
					Element.removeClassName(PageContext.lastClicked.parentNode, "OrderItemClicked");
				}
				Element.addClassName(oSrcElement.parentNode, "OrderItemClicked");
				PageContext.lastClicked = oSrcElement;
			}
			PageContext.setOrder(orderField, oSrcElement.innerText);
			if(typeof PageContext.isFirst == 'undefined'){
				$('document_list_orderable').style.cursor = 'pointer';
				$('document_list_curr_order').style.display = '';
				$('document_list_orderable').title = '单击切换升序或降序';
				PageContext.isFirst = false;
			}
		}catch(error){
			//TODO logger
			//alert(error.message);
		}
	},
	initOrder : function(orderDesc){
		if(!orderDesc) return;
		var orderField = orderDesc.split(" ")[0].toLowerCase();
		var orderDesc = orderDesc.split(" ")[1].toLowerCase();
		var liDomObjs = $('document_list_orders').getElementsByTagName("LI");
		for (var i = 0; i < liDomObjs.length; i++){
			if(orderField == liDomObjs[i].getAttribute("_FieldName").toLowerCase()){
				PageContext.lastClicked = getFirstHTMLChild(liDomObjs[i]);
				Element.addClassName(liDomObjs[i], "OrderItemClicked");
				$('document_list_orderable').setAttribute("_orderField", liDomObjs[i].getAttribute("_FieldName"));
				$('document_list_curr_fieldname').innerText = liDomObjs[i].innerText;
				if(orderDesc == 'asc'){
					Element.addClassName('document_list_curr_order', "document_list_curr_orderasc");						
				}else{
					Element.addClassName('document_list_curr_order', "document_list_curr_orderdesc");					
				}
				$('document_list_orderable').style.cursor = 'pointer';
				$('document_list_curr_order').style.display = '';
				$('document_list_orderable').title = '单击切换升序或降序';
				break;
			}
		}
	},
	setOrder : function(orderField, orderDesc){
		if(orderField == undefined) return;
		var orderObj = $('document_list_curr_order');
		if(orderDesc){
			$('document_list_curr_fieldname').innerText = orderDesc;
		}
		var orderKey = '';
		if(Element.hasClassName(orderObj, 'document_list_curr_orderdesc')){
			Element.removeClassName(orderObj, 'document_list_curr_orderdesc');
			Element.addClassName(orderObj, "document_list_curr_orderasc");
			orderKey = ' asc';
		}else{
			Element.removeClassName(orderObj, 'document_list_curr_orderasc');
			Element.addClassName(orderObj, "document_list_curr_orderdesc");
			orderKey = ' desc';
		}
		var orderParams = {
			"OrderBy" : orderField + orderKey
		}
		//PageContext.refresh('', orderParams);	
		Object.extend(PageContext.queryInfo, this.getOrderInfo(orderParams));
		PageContext.loadPage();

		orderObj.parentNode.setAttribute("_orderField", orderField);
	},
	//ge gfc add @ 2007-4-4 9:02 同步发布状态
	updateAfterPublish : function(_pubInfo){
		var arIds =  _pubInfo['ObjectIds'];
		if(arIds.length == 0) {
			return;
		}
		// begin to update
		var sStatusName = _pubInfo['StatusName'];
		var sStatusVal = _pubInfo['StatusValue'];
		var element = $('status_' + PageContext.params.chnldocid);
		if(element == null){
			return;
		}
		//else
		$removeChilds(element);
		element.appendChild(document.createTextNode(sStatusName));
		//TODO 同步detail页面中的status-combox
		var detailFrm = $('document_detail');
		var fSychStatus = null;
		if(detailFrm != null 
			&& (fSychStatus = detailFrm.contentWindow.PageContext.sychStatus) != null) {
			fSychStatus(sStatusVal, sStatusName);
		}
	}
});

var PageFilter = new com.trs.wcm.PageFilter('pageinfo');
Object.extend(PageFilter,{
	_loadData : function(_sFilterType){
		Object.extend(PageContext.queryInfo,　{"CurrPage" : 1, "FilterType" : _sFilterType});
		PageContext.loadPage(true);
	}
});

var Documents = {
	selectCurrRow : function(_currTR){//选中指定的某行
		if(Documents.lastCheckedRow && Documents.lastCheckedRow != _currTR){
			Element.removeClassName(Documents.lastCheckedRow,'document_list_row_active');
		}
		if(Documents.lastCheckedRow != _currTR){
			Element.addClassName(_currTR, 'document_list_row_active');
			Documents.lastCheckedRow = _currTR;
		}
		delete _currTR;
	},
	selectPreRow : function(){	
		if(Documents.lastCheckedRow && Documents.lastCheckedRow.previousSibling){
			Documents.selectCurrRow(Documents.lastCheckedRow.previousSibling);
			$('contentDiv').scrollTop -= 55;
			return true;
		}
		if(!PageContext.extraParams.backwardLocked){
			PageContext.extraParams.backwardLocked = true;//防止发送多次请求
			//列表中没有数据或已到达列表最前部,发送请求去获得
			PageContext.appendDocuments("backward");
		}
	},
	selectNextRow : function(){
		if(Documents.lastCheckedRow && Documents.lastCheckedRow.nextSibling){
			Documents.selectCurrRow(Documents.lastCheckedRow.nextSibling);
			$('contentDiv').scrollTop += 55;
			return true;
		}
		if(!PageContext.extraParams.forwardLocked){
			PageContext.extraParams.forwardLocked = true;//防止发送多次请求
			//列表中没有数据或已到达列表最尾部,发送请求去获得		
			PageContext.appendDocuments("forward");
		}
	},
	keyUp : function(event){
		if(this.selectPreRow()){
			PageContext.loadDocumentDetail();
		}
		return false;
	},
	keyDown : function(event){
		if(this.selectNextRow()){
			PageContext.loadDocumentDetail();
		}
		return false;
	}
};
	

Event.observe(window,'load',function(){
	initLocateSuggestion();

	Event.observe('goToParent', 'click', function(){
		var goToParent = $('goToParent');
		if(goToParent.style.cursor == 'default'){
			return true;
		}
		try{
			var treeWindow = $nav();
			var node = treeWindow.com.trs.tree.TreeNav.oPreSrcElementA.parentNode.parentNode.previousSibling;
			var pathNodeInfo = treeWindow.getPathNodeInfo(node.childNodes[0]);	
            treeWindow.focus(pathNodeInfo.nodeType, pathNodeInfo.nodeId);

			if(pathNodeInfo.nodeType == 's'){
				PageContext.refresh("siteid=" + pathNodeInfo.nodeId);
			}else if(pathNodeInfo.nodeType == 'c'){
				PageContext.refresh("channelid=" + pathNodeInfo.nodeId);
            }   
		}catch(error){
			//TODO logger
			//alert("error:" + error.message);
		}
	});

	Event.observe('orderBtn', 'click', function(event){
		PageContext.toggleOrdersInfo();
		Event.stop(window.event || event);
		return false;
	});

	Event.observe('document_list_orders', 'blur', function(event){
		event = window.event || event;
		var orderList = $('document_list_orders');
		if(!Position.within(orderList, Event.pointerX(event), Event.pointerY(event))){
			Element.toggle(orderList);
		}
	});

	Event.observe('document_list_orderable', 'click', function(event){
		PageContext.setOrder($('document_list_orderable').getAttribute("_orderField"));
	});

	Event.observe('contentDiv', 'mousewheel', function(event){
		event = window.event || event;
		if(event.wheelDelta < 0){
			if(Documents.lastCheckedRow && Documents.lastCheckedRow.nextSibling){
				Documents.selectCurrRow(Documents.lastCheckedRow.nextSibling);
				PageContext.loadDocumentDetail();
				$('contentDiv').scrollTop += 55;
				return false;
			}	
			PageContext.appendDocuments("forward");
		}else{
			if(Documents.lastCheckedRow && Documents.lastCheckedRow.previousSibling){
				Documents.selectCurrRow(Documents.lastCheckedRow.previousSibling);
				PageContext.loadDocumentDetail();
				$('contentDiv').scrollTop -= 55;
				return false;
			}
			PageContext.appendDocuments("backward");
		}
	});

	Event.observe('backwardImg', 'click', PageContext.clickMoreImg.bind(PageContext, 'backward'));
	Event.observe('forwardImg', 'click', PageContext.clickMoreImg.bind(PageContext, 'forward'));

	PageContext.init();
});

function initLocateSuggestion(){
	var suggestion = new com.trs.suggestion.Suggestion('locationTxt');
    suggestion.dealWithOnKeyDown = suggestion.dealWithOnKeyUp = function(currSelectObj){
        if(currSelectObj == null)return;
        suggestion.oRelatedElement.setAttribute("json", currSelectObj.getAttribute("_oJson"));
    };
    suggestion.onOptionClick = function(_event, _oJson){
        suggestion.oRelatedElement.setAttribute("json", _oJson);
		if(_event.type == 'click'){
			renderLocate();
			return true;
		}
		var txtValue = suggestion.oRelatedElement.value;
		if(_event.type == 'keyup' && _event.keyCode == Event.KEY_RETURN && txtValue.indexOf("\\") < 0){
			return false;
		}
		return true;
    };
    suggestion.dealWithOnKeyReturn = function(currSelectObj){
		var txtValue = suggestion.oRelatedElement.value;
		if(txtValue.indexOf("\\") < 0){//按栏目名称进行匹配
			suggestion.sendRequest(null, function(otransport, ojson){
				var elements = ojson["ROOT"]["ELEMENT"];
				if(elements.length <= 1){
					$alert('没有找到匹配的结果');
				}
			});
		}else{
			var currSelectDom = suggestion.getCurrSelect();
			if(currSelectDom && currSelectDom.innerHTML == txtValue){
				suggestion.oRelatedElement.setAttribute("json", currSelectDom.getAttribute("_oJson"));
				suggestion.oSuggestionRegionElement.style.display = 'none';
				suggestion.oSuggestionRegionShieldElement.style.display = "none";
				renderLocate();
			}else{
				suggestion.sendRequest(Prototype.emptyFunction, function(otransport, ojson){
					var elements = ojson["ROOT"]["ELEMENT"];
					if(elements[0]["PARENTID"] != "-1"){
						suggestion.oRelatedElement.setAttribute("json", {type:elements[0]["PARENTTYPE"], id:elements[0]["PARENTID"]});						
						renderLocate();
					}else{
						$alert('不能定位到指定的目标对象['+suggestion.oRelatedElement.value+"]");
					}
				});
			}
		}
    };
    suggestion.doOnCommonKey = function(nKeyCode){
        //suggestion.flag = true;
        if(nKeyCode != 8 && nKeyCode != 220){// '\' '<-'
			var txtValue = suggestion.oRelatedElement.value;
			if(txtValue.indexOf("\\") >= 0){
				suggestion.hideNotMatch();
			}
            return false;
        }
        var txtValue = suggestion.oRelatedElement.value;
        if(/(.)*\\$/.test(txtValue) && !/(.)*\\\\$/.test(txtValue)){//keycode == '\'
            this.sendRequest();	
			return true;
        }
		return false;
    };
    suggestion.sendRequest = function(beforeSend, afterSend){//发送请求去获取数据
        if(beforeSend) beforeSend();
		this.clearAllItems();
		var oHelper = new com.trs.web2frame.BasicDataHelper();
        oHelper.Call('wcm6_system', 'findNodesByPath', {
				SiteTypes: 0,//只取文字库的信息
                Path : suggestion.oRelatedElement.value,
				fieldsToHtml:'PATH'
            }, true, function(otransport, ojson){
				var elements = ojson["ROOT"]["ELEMENT"];
				for (var i = 1; i < elements.length; i++){
					suggestion.addItem(elements[i]["PATH"], {type:elements[i]["TYPE"], id:elements[i]["ID"]});
				}
				if(afterSend){
					afterSend(otransport, ojson);
				}
        });
    };
    function renderLocate(){
        var obj = suggestion.oRelatedElement.getAttribute("json");
        if(obj){
			obj.type = obj.type.toLowerCase();
            var type = (obj.type == 'sitetype' ? 'r' : (obj.type == 'site' ? 's' : 'c'));
			$nav().focus(type, obj.id, null, function(){
				if(type == 's'){
					PageContext.refresh('siteid=' + obj.id);
				}else if(type == 'c'){
					PageContext.refresh('channelid=' + obj.id);
				}  		
			});
		}
	}
}

Event.observe(window, 'unload', function(){
	var master = (top.actualTop||top);
	if(master.PageContext.navigate_bar_status != master.isNavAdvanced()){
		master.toggleNav();
	}//*/
	/*
	if(master.PageContext.attribute_bar_status != master.getAttrPanelStatus()){
		master.showHideAttrPanel(false);
	}
	*/
});

function initSuggestionValue(){
	//初始化suggestion控件的值
	try{
		var pathNodeInfo = $nav().getPathNodeInfo();
		if(pathNodeInfo.nodeType == 's'){
			$('goToParent').style.cursor = 'default';
		}else{
			$('goToParent').style.cursor = 'pointer';
		}
		var suggestionTxt = $('locationTxt');
		suggestionTxt.disabled = false;
		Element.show('goToParent');

		suggestionTxt.value = pathNodeInfo.path;
		nodeType = pathNodeInfo.nodeType == "s" ? "site" : "c";
		suggestionTxt.setAttribute("json", {type:nodeType, id:pathNodeInfo.nodeId});

		var queryParams = (top.actualTop||top).location_search.toQueryParams();
		queryParams.RightValue = pathNodeInfo.rightValue;
		
		if(nodeType == "site"){
			queryParams.siteid = pathNodeInfo.nodeId;
			delete queryParams.channelid;
			delete queryParams.ChannelType;
		}else{
			queryParams.ChannelType = pathNodeInfo.channelType;
			queryParams.channelid = pathNodeInfo.nodeId;
			delete queryParams.siteid;		
		}
		(top.actualTop||top).location_search = "?" + $toQueryStr(queryParams);
	}catch(error){
		//TODO logger
		//alert("error:" + error.message);
	}
}

