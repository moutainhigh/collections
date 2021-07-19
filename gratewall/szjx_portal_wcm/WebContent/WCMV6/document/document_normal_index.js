//TO Extend
Object.extend(PageContext,{
	enableAttrPanel : true,//能否激活属性面板
	//是否将数据提供者切换到本地的开关
	isLocal : false,
	isJspService : true,
	//远程服务的相关属性
	ObjectServiceId : 'wcm6_viewdocument',
	ObjectMethodName : 'jquery',
	//为了右侧显示操作栏和属性栏,定义右侧面板的类型
	ObjectType : 'chnldoc',
	//为了使页面具有行为,定义Mgr对象
	ObjectMgr : $chnlDocMgr,
	PageFilterDisplayNum : (top.$personalCon.listFilterSize || {}).paramValue || 6,
	PageFilters : (function(){
		var filters = [
			{Name:'全部文档',Type:0},
			{Name:'新稿',Type:1},
			{Name:'可发',Type:2},
			{Name:'已发',Type:3},
			{Name:'已否',Type:8},
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
	AbstractParams : {
		"FieldsToHTML" : "DocTitle,DocChannel.Name",
		"DocumentSelectFields" : "DOCID,DocTitle,DocType,CrUser,CrTime,DocEditor,AttachPic,DocLinkTo,DocMirrorTo"
	},
	rightIndexs : {
		"chnldoc_view":30,
		"view":30,
		"new":31,
		"quicknew":31,
		"import":31,
		"edit":32,
		"chnldoc_edit":32,
		"trash":33,
		"detail":34,
		"copy":34,
		"quote":34,
		"setright":32,
		"preview":38,
		"basicpublish":39,
		"detailpublish":39,
		"recallpublish":39,
		"restore":33,
		"backup":32,
		"export":34,
		"saveorder":32,
		"changestatus":32,
		"changesource":46,
		"open_channel":14
	},
	isTopped : function(_sTopped){
		if(_sTopped=='true'){
			return 'document_topped';
		}
		return '';
	},
	getDefaultFilterType : function(){
		if(this.searchPostData)return 0;
		return this.getDefaultFilterType0();
	},
	_doBeforeRefresh : function(_params){
		var oCookieData = CookieHelper.loadCookie();
		top.RememberAccrossPageIds = oCookieData['RememberAccrossPageIds']=='1';
		if(!top.RememberAccrossPageIds){
			top._rememberedDocInfos = null;
		}else{
			if(!top._rememberedDocInfos){
				var oDocInfos = top._rememberedDocInfos =  new top.Object();
				oDocInfos['ids'] = new top.Array();
				oDocInfos['titles'] = new top.Object();
			}
		}
		if(this.searchPostData){// 执行查询
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
		if(!PageContext.params['channelid']){
			$('ss_location').innerHTML = '所在位置';
		}
		else{
			$('ss_location').innerHTML = '所属栏目';
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

		//ge gfc modify @ 2007-5-27 16:47
		//兼容从其他非文档列表->检索结果列表->切换到文档阅读模式
		if(this.searchPostData) {
			(top.actualTop||top).showHideAttrPanel(false, false);
			var sUrlRead	= 'document/document_readmode_index.html?disable_sheet=1';
			$MessageCenter.changeSrc('main', sUrlRead, this.searchPostData);
		}else{
			$changeSheet(top.location_search);
		}
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
	callBackByEditor : function(_oData){
		if(_oData==null)return;
		var bIsNewDoc = _oData['isNewDoc'];
		var bCurrDocId = _oData['DocumentId'];
		if(bIsNewDoc){
			PageContext.params['CurrPage'] = 1;
		}
		PageContext.params['CurrDocId'] = bCurrDocId;
		this.RefreshList();
	},
	_doAfterBound : function(){
		delete PageContext.params['CurrDocId'];

		var siteType = $nav().findFocusNodeSiteType();
		if(siteType == 0 || siteType == null){
			Element.show("document_switcher");
		}else{
			Element.hide("document_switcher");
		}
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
	},
	RefreshSearchList : function(){
		var bNeedRefresh = (!!PageContext.params['IsVirtual']&&PageContext.params['IsVirtual']!='0')
				|| (!PageContext.params['channelid']&&!!PageContext.params['siteid']);
		if(bNeedRefresh){
			this.RefreshList();
		}
	}
});
Object.extend(PageFilter,{
	filterClass : function(_bDefault,nType){
		if(PageContext.getDefaultFilterType()==nType)return _PAGEFILTER_['CSS_PAGEFILTER_ACTIVE'];
		return _PAGEFILTER_['CSS_PAGEFILTER_DEACTIVE'];
//		return this.filterClass0(_bDefault);
	}
});
Object.extend(PageContext.PageNav,{
	UnitName : '篇',
	TypeName : '文档'
});

Object.extend(Grid,{
	draggable : true,/*是否可拖动*/
	_getSelectAbleClass : function(_eRow){
		//由模板中输出的权限控制Class,表明只有这个class才可选
		//若不需要,可以直接去掉这个方法,默认所有行可选中
		return 'grid_selectable_row';
	},
	updateColumn : function(_oPostData){
		try{
			var sRowId = _oPostData["objectid"];
			var sColumnName = _oPostData["fieldName"];
			var sValue = _oPostData["fieldValue"];
			var sLabel = _oPostData["fieldLabel"];
			var row = this.getRow(sRowId);
			var sFieldName = sColumnName.toLowerCase();
			if(sFieldName == 'doctitle'){
				var element = $(sFieldName + '_' + sRowId);
				var first = element.childNodes[0];
				var second = element.childNodes[1];
				var last = element.childNodes[element.childNodes.length-1];
				$removeChilds(element);
				element.appendChild(first);
				element.appendChild(second);
				element.appendChild(document.createTextNode(sValue));
				element.appendChild(last);
			}
			else if(sFieldName == 'docstatus'){
				var element = $(sFieldName + '_' + sRowId);
				$removeChilds(element);
				element.appendChild(document.createTextNode(sLabel));
			}
		}catch(err){
			//TODO logger
//			alert(err.message);
		}
	},
	/*键盘操作的特殊实现*/
	keyY : function(event){//Preview
		var ObjectIds = this.checkRight('preview','预览');
		if(ObjectIds&&ObjectIds.length>0){
			PageContext.ObjectMgr.preview(ObjectIds.join(','),PageContext.params);
		}
		return false;
	},
	keyD : function(event){//Trash
		var ObjectIds = this.checkRight('trash','删除');
		if(ObjectIds&&ObjectIds.length>0){
			PageContext.ObjectMgr.trash(ObjectIds.join(','),PageContext.params);
		}
		return false;
	},
	keyE : function(event){//Edit
		var rows = this.getRows();
		if(rows.length==1){
			var sRowId = this.getRowId(rows[0]);
			this._exec("chnldoc_edit",sRowId,rows[0]);
		}
		return false;
	},
	keyP : function(event){//BasicPublish
		var checkedObjectIds = this.checkRight('basicpublish','发布');
		if(checkedObjectIds&&checkedObjectIds.length>0){
			PageContext.ObjectMgr.basicpublish(checkedObjectIds.join(','),PageContext.params);
		}
		return false;
	},
	keyC : function(event){
		var checkedObjectIds = this.checkRight('copy', '复制/引用');
		if(checkedObjectIds&&checkedObjectIds.length>0){
			var arrTitles = [];
			for (var i = 0; i < checkedObjectIds.length; i++){
				var element = $('doctitle_' + checkedObjectIds[i]);
				arrTitles.push(element.childNodes[2].nodeValue);
			}
			var oResult = this._renderRemember(checkedObjectIds, arrTitles, this.getAllRowIds());
			checkedObjectIds = oResult[0];
			arrTitles = oResult[1];
			PageContext.ObjectMgr.copyToTop(checkedObjectIds.join(','), arrTitles.join(','), PageContext.params);
		}
		return false;
	},
	_renderRemember : function(checkedObjectIds, arrTitles, _arrExcludeIds){
		if(top._rememberedDocInfos){
			var oDocInfos = top._rememberedDocInfos;
			for(var i=0;i<arrTitles.length;i++){
				oDocInfos["titles"][checkedObjectIds[i]] = arrTitles[i];
			}
			oDocInfos["ids"] = oDocInfos["ids"].without.apply(oDocInfos["ids"], _arrExcludeIds);
			oDocInfos["ids"].push.apply(oDocInfos["ids"], checkedObjectIds);
			checkedObjectIds = oDocInfos["ids"];
			for(var nId in oDocInfos["titles"]){
				if(checkedObjectIds.indexOf(nId)==-1){
					delete oDocInfos["titles"][nId];
				}
			}
			arrTitles = [];
			for(var i=0;i<checkedObjectIds.length;i++){
				arrTitles[i] = oDocInfos["titles"][checkedObjectIds[i]];
			}
		}
		return [checkedObjectIds, arrTitles];
	},
	keyX : function(event){
		var checkedObjectIds = this.checkRight('move', '移动');
		if(checkedObjectIds&&checkedObjectIds.length>0){
			var arrTitles = [];
			for (var i = 0; i < checkedObjectIds.length; i++){
				var element = $('doctitle_' + checkedObjectIds[i]);
				arrTitles.push(element.childNodes[2].nodeValue);
			}
			var oResult = this._renderRemember(checkedObjectIds, arrTitles, this.getAllRowIds());
			checkedObjectIds = oResult[0];
			arrTitles = oResult[1];
			PageContext.ObjectMgr.cutToTop(checkedObjectIds.join(','), arrTitles.join(','), PageContext.params);
		}
		return false;
	},
	keyV : function(event){
		PageContext.ObjectMgr.pasteFromTop(PageContext.params);
		return false;
	},
	keyQ : function(event){
		PageContext.ObjectMgr.quoteFromTop(PageContext.params);
		return false;
	},
	keyShiftDelete : function(event){//Delete
		var checkedObjectIds = this.checkRight('delete','删除');
		if(checkedObjectIds&&checkedObjectIds.length>0){
			PageContext.ObjectMgr['delete'](checkedObjectIds.join(','),PageContext.params);
		}
		return false;
	},
	keyDelete : function(event){//Trash
		var checkedObjectIds = this.checkRight('trash','删除');
		if(checkedObjectIds&&checkedObjectIds.length>0){
			PageContext.ObjectMgr.trash(checkedObjectIds.join(','),PageContext.params);
		}
		return false;
	},
	keyN : function(event){//New
		if(PageContext.searchPostData != null) { // 检索模式下不能新建
			return;
		}
		//else
		if(PageContext.params.IsVirtual==1){
			$timeAlert('虚栏目不允许新建文档.',5);
		}
		else if(!isAccessable4WcmObject(PageContext.params.RightValue||'0',31)){
			$timeAlert('您没有权限在当前栏目新建文档.',5);
		}
		else{
			PageContext.ObjectMgr['new'](0,PageContext.params);
		}
	},
	keyReturn : function(event){//Edit
		return this.keyE(event);
	},
	/* 快捷键重复
	 *
	keyX : function(event){//export
		var ObjectIds = this.checkRight('export', '导出');
		if(ObjectIds && ObjectIds.length>0){
			PageContext.ObjectMgr['export'](ObjectIds.join(','), PageContext.params);
		}
		return false;
	},
	*/
	MappingRightType : function(_sType){
		switch(_sType){
			case 'cb_detail':
			case 'row_detail':
				return 'detail';
			default:
				return _sType;
		}
	},
	/*模板内容替换中的方法的特殊实现*/
	SpecialCb_detail : function(_sType,_bAccessAble){
		//针对{#Right;cb_detail,0;cb_detail,Grid.DealWithRight}的实现
		//方法名为Special+'Cb_detail'(第一个字母大写，后面全小写)
		if(_bAccessAble){
			return 'grid_function="multi"';
		}
		return 'disabled';
	},
	SpecialRow_detail : function(_sType,_bAccessAble){
		//针对{#Right;row_detail,0;row_detail,Grid.DealWithRight}的实现
		if(_bAccessAble){
			return 'grid_selectable_row';
		}
		return 'grid_selectdisable_row';
	},
	_exec : function(_sFunctionType,_sRowId,_eRow,_eSrcElement){
		if(_sFunctionType=='chnldoc_edit'||_sFunctionType=='chnldoc_view'){
			var sFunctionType = _sFunctionType.toLowerCase();
			var sRight = this._getRight(_eRow);
			var nRightIndex = (_eSrcElement)?_eSrcElement.getAttribute("right_index",2):null;
			if(nRightIndex==null||nRightIndex.trim()==''){
				nRightIndex = PageContext.getRightIndex(sFunctionType);
			}
			var sRowId = _eRow.getAttribute('docid', 2);
			var oNewParams = Object.extend({}, PageContext.params);
			Object.extend(oNewParams, {
				chnldocid: _sRowId,
				channelid: _eRow.getAttribute('currchnlid', 2)
			});
		}
		switch(_sFunctionType){
			case 'chnldoc_edit':
				if(sRight!=null&&!isAccessable4WcmObject(sRight,nRightIndex)){
					if(sRight!=null&&!isAccessable4WcmObject(sRight,PageContext.getRightIndex('view'))){
						$alert('您没有权限查看/编辑该文档.');
						return true;
					}
					PageContext.ObjectMgr['view'](sRowId, oNewParams);
				}
				else{
					PageContext.ObjectMgr['edit'](sRowId, oNewParams);
				}
				break;
			case 'chnldoc_view':
				if(sRight!=null&&!isAccessable4WcmObject(sRight,nRightIndex)){
					$alert('您没有权限查看该文档.');
					return true;
				}
				PageContext.ObjectMgr['view'](sRowId, oNewParams);
				break;
			case 'open_channel':
				var sToRight = _eSrcElement.getAttribute('rightValue');
				var nToRightIndex = _eSrcElement.getAttribute("right_index",2);
				if(nToRightIndex==null||nToRightIndex.trim()==''){
					nToRightIndex = PageContext.getRightIndex(sFunctionType);
				}
				if(sToRight!=null&&!isAccessable4WcmObject(sToRight,nToRightIndex)){
					$alert('您没有权限浏览该栏目.');
					return true;
				}
				var iChannelId = _eSrcElement.getAttribute("ext_channelid");
				if(PageContext.searchPostData) {
					PageContext.searchPostData['channelids'] = iChannelId;
					//alert($toQueryStr(PageContext.searchPostData))
					PageContext.search(PageContext.searchPostData);
				}else{
//					var src = window.frameElement.getAttribute("src",2).replace(/\?.*/,'')+'?'+'channelid='+iChannelId;
					var src = 'document/document_list_redirect.jsp?'+'channelid='+iChannelId;
					src += "&ChannelType=" + _eSrcElement.getAttribute('channelType');
					src += "&RightValue=" + sToRight;
					src += "&tab_type=document"
					$MessageCenter.changeSrc('main',src);
				}
				break;
			default:
				return this._exec0(_sFunctionType,_sRowId,_eRow,_eSrcElement);
		}
		return false;
	},
	_doBeforeKeyPressAction : function(_event , _eRow){
		//附加栏目ID等信息
		Object.extend(PageContext.params, {
			//'channelids' : _eRow.getAttribute('channelid', 2)
		});
		
		delete _eRow;
	},
	/**
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
			return "当前文档列表不支持排序";
		}
		if(PageContext.params["OrderBy"]){
			return "自动排序列表不支持手动排序";//'<div style="width:20px;height:20px;" class="forbid_sort"></div>';
		}
		if(!this.sortable){
			return "当前文档没有权限排序";
		}
		return "[文档-"+sDocId+"]";//'<div style="width:20px;height:20px;" class="doctype_'+_eRoot.getAttribute("doctype")+'"></div>';
	},
	_getSelectedHint : function(_eRoot){
		//
		if(top.DragAcross.ObjectIds.length>1){
			return "[引用多篇文档:"+top.DragAcross.ObjectIds+"]";
		}
		else{
			return "[文档-"+top.DragAcross.ObjectId+"]"
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
					$timeAlert('置顶文档与非置顶文档间不能交叉排序.',5);
					return false;
				}
			}
//			if(PageContext.params["channelid"]){
				if(confirm('您确定要调整文档的顺序？')){
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
var CookieHelper = {
	loadCookie : function(){
		var myCookies = document.cookie.split(";");
		var oCookieData = {};
		for(var i=0; i<myCookies.length; i++){
			var cookiePair = myCookies[i].split("=");
			if(cookiePair[0].trim()=='expires')continue;
			oCookieData[cookiePair[0].trim()] = unescape((cookiePair[1]||''));
		}
		return oCookieData;
	},
	clearCookie : function(_sCookieName){
		var myCookie = '';
		var sSaveValue = null;
		myCookie += _sCookieName+"=false";
		var expires = new Date();
		expires.setTime(expires.getTime() - 1);
		myCookie += "; path=/; expires=" + expires.toGMTString() + ";domain=" + document.domain;
		document.cookie = myCookie;
	},
	setCookie : function(_sCookieName,_sCookieValue){
		var myCookie = '';
		var sSaveValue = null;
		sSaveValue = escape(_sCookieValue);
		myCookie += _sCookieName+"="+sSaveValue+"";
		var expires = new Date();
		expires.setTime(expires.getTime() + (24 * 60 * 60 * 1000 * 30));
		myCookie += "; path=/; expires=" + expires.toGMTString()+";domain="+document.domain;
		document.cookie = myCookie;
	}
};
/*
Event.observe(window, 'load', function(){
	var oCookieData = CookieHelper.loadCookie();
	top.RememberAccrossPageIds = $('rollids_pages').checked = oCookieData['RememberAccrossPageIds']=='1';
	Event.observe('rollids_pages', 'click', function(){
		top.RememberAccrossPageIds = $('rollids_pages').checked;
		CookieHelper.setCookie('RememberAccrossPageIds', top.RememberAccrossPageIds?1:0);
	});
	Event.observe('rollids_pages_clearhistory', 'click', function(){
		top._rememberedDocInfos = null;
	});
});
*/