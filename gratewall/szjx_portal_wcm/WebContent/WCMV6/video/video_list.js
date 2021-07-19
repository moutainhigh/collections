Object.extend(PageContext,{
	//是否将数据提供者切换到本地的开关
//	isLocal : true,
	isLocal : false,
	
	//远程服务的相关属性
	// ls@2007-4-18 port from wcm6_document to wcm6_viewdocument
	/*
	ObjectServiceId : 'wcm6_document',
	ObjectMethodName : 'query',
	AbstractParams : {
			"SelectFields" : "DocTitle,DocId,CrTime,CrUser,DocChannel,DOCSTATUS,DocType,ATTRIBUTE"
	},//服务所需的参数
	*/

	ObjectServiceId : 'wcm6_viewdocument',
	ObjectMethodName : 'query',
	//为了使页面具有行为,定义Mgr对象
//	ObjectMgr : $chnlDocMgr,
	AbstractParams : {
		"DocumentSelectFields" : "DocTitle,DocId,CrTime,CrUser,DocChannel,DOCSTATUS,DocType,ATTRIBUTE",
		"FieldsToHTML" : "DocTitle,DocChannel.Name"
	},

	ObjectType : 'video',
	//页面过滤器的相关配置
	PageFilterDisplayNum : 6,
	PageFilters : [
		{Name:'全部视频',Type:0,IsDefault:true},
		{Name:'新稿',Type:1},
		{Name:'可发',Type:2},
		{Name:'已发',Type:3},
		{Name:'我创建的',Type:4},
		{Name:'最近三天',Type:5},
		{Name:'最近一周',Type:6},
		{Name:'最近一月',Type:7}
	],
/*
	PageFilterDisplayNum : (top.$personalCon.listFilterSize || {}).paramValue || 6,
	PageFilters : (function(){
		var filters = [
			{Name:'全部视频',Type:0},
			{Name:'最新视频',Type:1},
			{Name:'可发',Type:2},
			{Name:'已发',Type:3},
			{Name:'我创建的',Type:4},
			{Name:'最近三天',Type:5},
			{Name:'最近一周',Type:6},
			{Name:'最近一月',Type:7}
		];
		if(top.$personalCon.listDefaultFilter){
			var paramValue = top.$personalCon.listDefaultFilter.paramValue;
			for (var i = 0; i < filters.length; i++){
				if(filters[i].Type == paramValue){
					filters[i].IsDefault = true;
					break;
				}
			}
		}else{
			filters[0].IsDefault = true;
		}
		return filters;
	}()),
*/

	rightIndexs : {
		"view":30,
		"new":31,
		"quicknew":31,
		"import":31,
		"edit":32,
		"trash":33,
		"detail":34,
		"copy":34,
		"quote":34,
		"setright":40,
		"preview":38,
		"basicpublish":39,
		"detailpublish":39,
		"recallpublish":39,
		"restore":33,
		"backup":32,
		"export":34,
		"saveorder":32,
		"changestatus":47,
		"changesource":46
	},
	isTopped : function(_sTopped){
		if(_sTopped=='true'){
			return 'document_topped';
		}
		return '';
	},
	_doBeforeRefresh : function(_params){
		if(this.searchPostData){// 查询模式
			var temp = this.searchPostData;
			if(temp.queryInfoDetail) {
				this.queryInfoDetail = temp.queryInfoDetail;
			}
			delete temp.queryInfoDetail;
			Object.extend(PageContext.params, temp);
			Grid.draggable = false;
		}else{// 普通列表模式
			Object.extend(PageContext.params, {
				channelids: PageContext.params['channelid'],
				siteids: PageContext.params['siteid']
			});
			Grid.draggable = true;
		}
		//保存现场
		var oParams = getExtraExchangeParams();
		if(oParams){
			Object.extend(PageContext.params,{
					"FilterType": oParams["FilterType"],
					"CurrPage": oParams["CurrPage"],
					"CurrDocId": oParams["CurrDocId"],
					'OrderBy': oParams["OrderBy"],
					"SwitchMode": true
				});
		}
	},
	getQueryInfoDetail : function(){
		return this.queryInfoDetail;
	},
	search : function(_oPostData, _sRemainedParams){
		//alert($toQueryStr(_oPostData) + '\n' + $toQueryStr(PageContext.params))

		this.searchPostData = _oPostData;
		this.LoadPage();

		var m_top = (top.actualTop || top);
		m_top.location_search = _sRemainedParams;

		//set display
		Element.show('divSearchClue');
		if(m_top.showHideAttrPanel) {
			m_top.showHideAttrPanel(false, true);
		}

	},
	switchReadMode : function(){
		var rowids = Grid.getRowIds();
		var oParams = {
			"FilterType":PageContext.params["FilterType"],
			"CurrPage":PageContext.params["CurrPage"],
			"CurrDocId":(rowids.length>0)?rowids[0]:0,
			'OrderBy':PageContext.params["OrderBy"]
		};
		if(this.searchPostData) {
			Object.extend(this.searchPostData, oParams);
			oParams['searchPostData'] = this.searchPostData;
		}

		setExtraExchangeParams(oParams);
		(top.actualTop||top).PageContext.mode="read";
		$changeSheet(top.location_search);
	},
	updateCurrRows : function(_currId, _bDoNotNeedRefresh){
		// 在普通文档列表（非检索结果列表）时，判断channelid
		var params = PageContext.params;
		var isSearchResultList = (params['disable_sheet'] == '1');
		if(!isSearchResultList && (params.channelid != params.channelids)) {
			if(_currId && (_currId + '').split(',').length == 1) {
				PageContext.params['channelid'] = params.channelids;
			}
		}
		// 不再使用docid, 统一为chnldocid
		//_currId = PageContext.params.chnldocid;
		var aRowIds = (_currId != null) ? [PageContext.params.chnldocid]
						: Grid.getRowIds();
		this.RefreshList(aRowIds, _bDoNotNeedRefresh);
	},
	updateAfterPublish : function(_pubInfo){
		var arIds =  _pubInfo['ObjectIds'];
		if(arIds.length == 0) {
			return;
		}
		var sPubFlag = _pubInfo['StatusName'];
		// begin to update 
		var rows = Grid.getRows();
		for (var i = 0; i < rows.length; i++){
			var eRow = rows[i];
			var id = eRow.getAttribute('grid_rowid', 2);
			//alert(arIds + ', ' + eRow.getAttribute('grid_rowid', 2));
			if(arIds.include(id)) {
				var element = $('docstatus_' + id);
				$removeChilds(element);
				element.appendChild(document.createTextNode(sPubFlag));
			}
		}
	}
});

Object.extend(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '视频'
});

Object.extend(Grid,{
	/**
	 * ls@2007-4-19 补充'docids'参数.
	 * @param _oPostData[Object]: {objecttype, objectids, objectrights}
	 */
	_getAdditionRowSelInfo : function(_oPostData){
		var sObjIds = _oPostData.objectids;
		var result = null;
		if(sObjIds.length != 1) {
			var arrRows = this.getRows();
			var  arrDocIds = [], arrChnlIds = [];
			for (var i = 0; i < arrRows.length; i++){
				arrDocIds.push(arrRows[i].getAttribute('docid', 2));

				var sChnlId = arrRows[i].getAttribute('channelid', 2);
				if(!arrChnlIds.include(sChnlId)) {
					arrChnlIds.push(sChnlId);
				}
			}
			result = {
				channelids: arrChnlIds, 
				docids: arrDocIds
			};
		}else{
			var eRow = this.getRows()[0];
			result = {
				channelids: eRow.getAttribute('channelid', 2),
				docids: eRow.getAttribute('docid', 2),
				objectids: eRow.getAttribute('grid_rowid', 2)
			}
			delete eRow;
		}
		return result;
	}
});