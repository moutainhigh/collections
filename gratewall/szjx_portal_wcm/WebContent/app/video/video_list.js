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

	ObjectServiceId : 'wcm61_video',
	ObjectMethodName : 'vQuery',
//	ObjectMethodName : 'query',
	AbstractParams : {
		"DocumentSelectFields" : "DocTitle,DocId,CrTime,CrUser,DocChannel,DOCSTATUS,DocType",
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
	// TODO: ls@2007-11-18 19:43 rightIndexs here seems useless?
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
//		"changestatus":32,
		"changestatus":47,
		"changesource":46
	},
	// ls@07-1015 是否显示置顶样式
	isTopped : function(_sTopped){
		if(_sTopped=='true'){
			return 'document_topped';
		}
		return '';
	},
	_doBeforeRefresh : function(_params){
		if(this.searchPostData){// 查询模式
			var temp = this.searchPostData;
			if(this.IsRenderSearch) {
				if(temp.queryInfoDetail) {
					this.queryInfoDetail = temp.queryInfoDetail;
				}
				delete temp.queryInfoDetail;
				Object.extend(PageContext.params, temp);
			}else{
				Object.extend(PageContext.params, {
					channelids: temp['channelids'],
					siteids: temp['siteids']
				});				
			}
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
		//alert("[video_list.js] search:\n" + $toQueryStr(_oPostData) + '\n' + $toQueryStr(PageContext.params));
		this.searchPostData = _oPostData;
		
		this.IsRenderSearch = true;
		this.LoadPage();
		this.IsRenderSearch = false;

		var m_top = (top.actualTop || top);
		if(_sRemainedParams) {
			m_top.location_search = _sRemainedParams;
		}

		//set display
		Element.show('divSearchClue');
		if(m_top.showHideAttrPanel) {
			//alert("exe m_top.showHideAttrPanel(false, true)...");
			m_top.showHideAttrPanel(false, true);
		}

	},
	// ls@2007-9-26 9:48 视频列表构造完成后，检查是否有正在转换中的视频
	_doAfterBound : function(){
		checkConvertingVideos();
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
	},
	renderResult : function(_json,_transport,_ajaxRequest){
		if(_json){
			PageContext.CanSort = _json["CANSORT"]=='true';
		}
		else{
			PageContext.CanSort = _ajaxRequest.header('CanSort')=="true";
		}
	}
});

Object.extend(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '视频'
});

Object.extend(Grid,{
	/**
	 * ls@2007-11-18 16:43 覆盖Grid.js中的_getSelectAbleClass方法的默认实现
	 */
	_getSelectAbleClass : function(_eRow){
		return 'grid_selectable_row';
	},

	updateColumn : function(_oPostData){
		//alert(Object.parseSource(_oPostData));
		try{
			var sRowId = _oPostData["objectid"];
			var sColumnName = _oPostData["fieldName"];
			var sValue = _oPostData["fieldValue"];
			var sLabel = _oPostData["fieldLabel"];
			var row = this.getRow(sRowId);
			var sFieldName = sColumnName.toLowerCase();
			var element = $(sFieldName + '_' + sRowId);
			//alert("element=" + element + "\nsFieldName=" + sFieldName);
			if (!element) {
				return;
			}
			// TODO: element is null, is ok?
			if(sFieldName == 'doctitle'){
				element.title = sValue;
				var first = element.childNodes[0];
				var second = element.childNodes[1];
				$removeChilds(element);
				element.appendChild(first);
				element.appendChild(second);
				element.appendChild(document.createTextNode(sValue));
			}
			else if(sFieldName == 'docstatus'){
				$removeChilds(element);
				element.appendChild(document.createTextNode(sLabel));
			}
		}catch(err){
			alert(err);
		}
	},

	// ls@2007-11-19 14:20 查询转换状态后，对转换失败的视频，取消可选中状态等
	setSelectDisable: function(docid) {
		var rows = this.getAllRows();
		//alert("rows.length = " + rows.length + "\nsearch docid=" + docid);
		for(var i=0;i<rows.length;i++){
			var nRowId = this.getRowId(rows[i]);
			var sToken = rows[i].getAttribute("video_token" , 2);
			var sDocId = rows[i].getAttribute("docid" , 2);
			//alert("nRowId=" + nRowId + ", docid=" + sDocId + "\nsToken: " + sToken);
			if (docid == sDocId) {
				Element.removeClassName(rows[i], 'grid_selectable_row');
				Element.addClassName(rows[i], 'grid_selectdisable_row');
				//alert("done. docid: " + sDocId + "\nsToken: " + sToken);
				break;
			}
		}
	},

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
//拖动功能
Object.extend(com.trs.wcm.ListDragger.prototype,{
	_getHint : function(_eRoot){
		//
		if(!top.DragAcross){
			top.DragAcross = {};
		}
		//TODO channelid处理
		var sDocId = _eRoot.getAttribute("grid_rowid",2);
		var aCheckboxs = document.getElementsByName("RecId");
		var bCurrSelected = false;
		var aSelectDocIds = [];
		for(var i=0;i<aCheckboxs.length;i++){
			if(!aCheckboxs[i].checked)continue;
			if(aCheckboxs[i].value==sDocId){
				bCurrSelected = true;
			}
			aSelectDocIds.push(aCheckboxs[i].value);
		}
		if(!bCurrSelected){
			aSelectDocIds.push(sDocId);
		}
		Object.extend(top.DragAcross,{
			ObjectType : 605 ,
			FolderId :  _eRoot.getAttribute("channelid", 2),
			ObjectId : sDocId,
			ObjectIds : aSelectDocIds
		});
		if(!PageContext.CanSort){
			return "当前视频列表不支持排序";
		}
		if(PageContext.params["OrderBy"]){
			return "自动排序列表不支持手动排序";//'<div style="width:20px;height:20px;" class="forbid_sort"></div>';
		}
		if(!this.sortable){
			return "当前视频没有权限排序";
		}
		return "[视频-"+sDocId+"]";//'<div style="width:20px;height:20px;" class="doctype_'+_eRoot.getAttribute("doctype")+'"></div>';
	},
	_getSelectedHint : function(_eRoot){
		//
		if(top.DragAcross.ObjectIds.length>1){
			return "[引用多篇视频:"+top.DragAcross.ObjectIds+"]";
		}
		else{
			return "[视频-"+top.DragAcross.ObjectId+"]"
		}
	},
	_onDrop : function(){
		if(top.DragAcross&&top.DragAcross.TargetFolderId){
			$documentMgr.quoteTo(top.DragAcross.ObjectIds,top.DragAcross.TargetFolderId);
		}
		top.DragAcross = null;
	},
	_isSortable : function(_eRoot){
		//
		if(!PageContext.params["OrderBy"]&&PageContext.CanSort){
			var sRight = _eRoot.getAttribute("right",2);
			var nRightIndex = _eRoot.getAttribute("sortableRightIndex",2);
			if(sRight!=null&&!isAccessable4WcmObject(sRight,nRightIndex)){
				return false;
			}
			return true;
		}
		return false;
	},
	_move : function(_eCurr,_iPosition,_eTarget,_eTargetMore){
		//_iPosition表示当前元素相对于目标元素的位置,1表示之前,0表示之后
		if(PageContext.CanSort&&!PageContext.params["OrderBy"]){
//		if(!PageContext.params["OrderBy"]&&PageContext.params["channelid"]){
			//当前行是否为置顶行?
			var bCurrTopped = _eCurr.getAttribute("isTopped",2)=='true';
			var bTargetTopped = _eTarget.getAttribute("isTopped",2)=='true';
			var docid = _eCurr.getAttribute('docid', 2);
			var targetDocId = _eTarget.getAttribute('docid', 2);
			if(bCurrTopped!=bTargetTopped){//当前行与目标行，一行是置顶，一行是非置顶
				var bFixedUp = true;
				if(_iPosition==0){//有前一行，插入到目标行之后的情况
					if(!bCurrTopped&&_eTargetMore!=null){//非置顶行判断前一行和后一行
						//若下一行为非置顶行，则不交叉
						//反之，则交叉
						var bTargetMoreTopped = _eTargetMore.getAttribute("isTopped",2)=='true';
						if(!bTargetMoreTopped){
							//用后一行的数据，表示插入到它之前
							targetDocId = _eTargetMore.getAttribute('docid', 2);
							_iPosition = (_iPosition==0)?1:0;
							bFixedUp = false;
						}
					}
					else if(!bCurrTopped&&_eTargetMore==null){//非置顶行上一行为置顶行，下一行无
						//相当于至少有n-1个置顶行,而被拖动的那行是非置顶行
						//所以非置顶行本身未移动,此种情况其实不需要考虑
						//考虑的话不计为交叉
						bFixedUp = false;
					}
					else if(bCurrTopped){//置顶行的上一行为非置顶行,必然交叉
						bFixedUp = true;
					}
				}
				else{//无前一行，但有后一行，插入到目标行之前的情况
					if(!bCurrTopped){//当前行非置顶，插在置顶行之前必然交叉
						bFixedUp = true;
					}
					else{//当前行置顶，插在非置顶行之前必然不交叉
						//但此种情况可以不考虑
						//当前置顶行拖动后前无置顶行(在第一行)，后无置顶行
						//可以知道当前只有一个置顶行，且没有交换位置
						//不应该到这里来
						bFixedUp = false;
						//若来到这里就不能按置顶交换顺序的方式处理了
						bCurrTopped = false;
					}
				}
				if(bFixedUp){
					$timeAlert('置顶视频与非置顶视频间不能交叉排序.',5);
					return false;
				}
			}
//			if(PageContext.params["channelid"]){
				if(confirm('您确定要调整视频的顺序？')){
					if(bCurrTopped){
						var oPostData = {
							"TopFlag" : 3,/*表示不改变置顶设置*/
							"ChannelId" : PageContext.params["channelid"],
							"DocumentId" : docid,
							"Position" : _iPosition,
							"TargetDocumentId" : targetDocId
						}
						var oHelper = new com.trs.web2frame.BasicDataHelper();
						oHelper.Call(PageContext.ObjectServiceId,'setTopDocument',oPostData,true);
					}
					else{
						var LastNextSibling = this.lastNextSibling;
						$chnlDocMgr['saveorder'](docid,_iPosition,targetDocId,PageContext.params["channelid"],{
							onFailure : function(trans,json){
								var getNodeVal = com.trs.util.JSON.value;
								FaultDialog.show({
									code		: getNodeVal(json,'fault.code'),
									message		: getNodeVal(json,'fault.message'),
									detail		: getNodeVal(json,'fault.detail'),
									suggestion  : getNodeVal(json,'fault.suggestion')
								}, '与服务器交互时出现错误' , function(){
									_eCurr.parentNode.insertBefore(_eCurr,LastNextSibling);
									Grid.adjustColors();
								});
							}
						});
					}
				}
				else return false;
//			}
		}else return false;
		Grid.adjustColors();
		delete _eCurr;
		delete _eNext;
		return true;
	}
});