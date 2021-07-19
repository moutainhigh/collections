var ProcessBar = (top.actualTop||top).ProcessBar;
if(!window.$log){
	$log = (top.actualTop || top).$log;
}
Object.extend(PageContext,{
	init: function(_params){
		Object.extend(this.params,_params || {});
		this.PageLoaded = function(_transport,_json,_ajaxRequest){
//			ProcessBar.next();
			//ge gfc add @ 2007-4-23 19:57 在绑定列表数据之前执行的操作
			if(PageContext._doBeforeBinding) {
				PageContext._doBeforeBinding(_transport, _json);
			}

			if(_json!=null){
				_json = _json["MULTIRESULT"]||_json;
				PageContext.Data = _json;
				var sObjectsName = PageContext.ObjectsTagName;
				var myJson = _json;
				if(!(sObjectsName==null||sObjectsName=='')){
					myJson = _json[sObjectsName];
				}
				// ge gfc add @ 2007-1-16 11:11 增加对数据源不匹配时的提示信息
				if(myJson == null) {
					alert('返回的列表数据结果并不是期望的[' + sObjectsName + ']!');
					//ProcessBar.exit();
					return;
				}

				if(PageContext.renderResult)PageContext.renderResult(myJson);
				PageContext.PageCount = myJson["PAGECOUNT"]||1;
				PageContext.RecordNum = myJson["NUM"]||0;
				PageContext.PageSize = myJson["PAGESIZE"]||20;
				var iStartIndex = parseInt(PageContext.params["CurrPage"]-1)*parseInt(PageContext.PageSize);
				var sValue = TempEvaler.evaluateTemplater('objects_template', _json,
					{"START_INDEX":iStartIndex});
				Grid.StartIndex = iStartIndex;
			}
			else{
				if(PageContext.renderResult)PageContext.renderResult(_json,_transport,_ajaxRequest);
				PageContext.PageCount = _ajaxRequest.header("PageCount")||1;
				PageContext.RecordNum = _ajaxRequest.header("Num")||0;
				PageContext.PageSize = _ajaxRequest.header("PageSize")||20;
				var iStartIndex = parseInt(PageContext.params["CurrPage"]-1)*parseInt(PageContext.PageSize);
				Grid.StartIndex = iStartIndex;
				if(PageContext.RecordNum>0){
					var sValue = _transport.responseText;
				}
				else{
					var sValue = $('divNoObjectFound').innerHTML;
				}
			}
			Element.update($('grid_data'),sValue);
//			ProcessBar.next();
			if(PageContext.HasPageInfo){
				sValue = TempEvaler.evaluateTemplater('pageinfo_template', PageContext.getPageInfo(), {});
				$('pageinfo').style.display = 'none';
				Element.update($('pageinfo'),sValue);
			}
//			ProcessBar.next();
			PageContext.drawNavigator();
//			ProcessBar.next();
			if(PageContext.HasPageInfo){
				PageFilter.bindEvents(PageContext.PageFilterDisplayNum);
				if(PageContext.params['SwitchMode']){
					PageFilter.selectFilterByType(PageContext.params['FilterType']);
					PageContext.params['SwitchMode'] = false;
				}
			}

			Grid.init(!PageContext.loadedObjects);
			Grid.bindEvents(!PageContext.loadedObjects);
			var selectRowIds = (PageContext.params["CurrDocId"])?[PageContext.params["CurrDocId"]]:[];
			delete PageContext.params["CurrDocId"];
			selectRowIds = (PageContext.SelectedRowIds)?PageContext.SelectedRowIds:selectRowIds;
			if(PageContext.MustSelectRows||(selectRowIds!=null&&selectRowIds.length!=0)){
				Grid.selectRows(selectRowIds);
			}
			else{
				Grid._dealWithSelectedRows();
			}
			PageContext.loadedObjects = true;
//			ProcessBar.exit();

			//ge gfc add @ 2007-5-11 15:29 在绑定列表数据之后执行的操作
			if(PageContext._doAfterBound) {
				PageContext._doAfterBound(_transport, _json);
			}
			$endSimpleRB();
		};
		this.ListRefreshed = function(_transport,_json,_ajaxRequest){
			//ge gfc add @ 2007-4-23 19:57 在绑定列表数据之前执行的操作
			if(PageContext._doBeforeBinding) {
				PageContext._doBeforeBinding(_transport,_json);
			}

			var caller = this;
			if(_json!=null){
				_json = _json["MULTIRESULT"]||_json;
				PageContext.Data = _json;
				var sObjectsName = PageContext.ObjectsTagName;
				var myJson = _json;
				if(!(sObjectsName==null||sObjectsName=='')){
					myJson = _json[sObjectsName];
				}
				// ge gfc add @ 2007-1-16 11:11 增加对数据源不匹配时的提示信息
				if(myJson == null) {
					alert('返回的列表数据结果并不是期望的[' + sObjectsName + ']!');
					//ProcessBar.exit();
					return;
				}

	//			ProcessBar.next();
				var sObjectsName = PageContext.ObjectsTagName;
				PageContext.PageCount = myJson["PAGECOUNT"]||1;
				PageContext.RecordNum = myJson["NUM"]||0;
				PageContext.PageSize = myJson["PAGESIZE"]||20;
				var iStartIndex = parseInt(PageContext.params["CurrPage"]-1)*parseInt(PageContext.PageSize);
				var sValue = TempEvaler.evaluateTemplater('objects_template', _json,
					{"START_INDEX":iStartIndex});
				Grid.StartIndex = iStartIndex;
			}
			else{
				if(PageContext.renderResult)PageContext.renderResult(_json,_transport,_ajaxRequest);
				PageContext.PageCount = _ajaxRequest.header("PageCount")||1;
				PageContext.RecordNum = _ajaxRequest.header("Num")||0;
				PageContext.PageSize = _ajaxRequest.header("PageSize")||20;
				var iStartIndex = parseInt(PageContext.params["CurrPage"]-1)*parseInt(PageContext.PageSize);
				Grid.StartIndex = iStartIndex;
				if(PageContext.RecordNum>0){
					var sValue = _transport.responseText;
				}
				else{
					var sValue = $('divNoObjectFound').innerHTML;
				}
			}
			Element.update($('grid_data'),sValue);
//			ProcessBar.next();
			PageContext.drawNavigator();
//			ProcessBar.next();
			Grid.init(!PageContext.loadedObjects);
			Grid.bindEvents(!PageContext.loadedObjects);
//			ProcessBar.next();
			PageContext.loadedObjects = true;
			var selectRowIds = (PageContext.SelectedRowIds)?PageContext.SelectedRowIds:caller["rowids"];
			if(PageContext.MustSelectRows||(selectRowIds!=null&&selectRowIds.length!=0)){
				Grid.selectRows(selectRowIds,caller["DoNotNeedRefresh"]);
			}
			else if(!caller["DoNotNeedRefresh"]){
				Grid._dealWithSelectedRows();
			}
//			ProcessBar.exit();
			
			//ge gfc add @ 2007-5-11 15:29 在绑定列表数据之后执行的操作
			if(PageContext._doAfterBound) {
				PageContext._doAfterBound(_transport, _json);
			}
			$endSimpleRB();
		};
	},
	LoadPageContent : function(_fCallBack){
		var aCombine = [];
		if(PageContext.isLocal){
			//TODO local
			var sQueryMethod = PageContext.ObjectMethodName || 'query';
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			oHelper.LocalCall(PageContext.ObjectServiceId,sQueryMethod,this.params,true,_fCallBack||this.PageLoaded);
		}
		else{
			if(!PageContext.isJspService&&!PageContext.isJspRequest&&PageContext.ObjectServiceId.indexOf('.jsp')==-1){
				// ge gfc modify @ 2006-12-28 14:30 使用注册的方法
				//BasicDataHelper.call(PageContext.ObjectServiceId,'query',this.params,false,fCallBack);
				var sQueryMethod = PageContext.ObjectMethodName || 'query';		
				var oHelper = new com.trs.web2frame.BasicDataHelper();
				oHelper.Call(PageContext.ObjectServiceId,sQueryMethod,this.params,true,_fCallBack||this.PageLoaded);
			}
			else if(PageContext.isJspService){
				var sQueryMethod = PageContext.ObjectMethodName || 'jquery';		
				var oHelper = new com.trs.web2frame.BasicDataHelper();
				oHelper.Call(PageContext.ObjectServiceId,sQueryMethod,this.params,false,_fCallBack||this.PageLoaded);
			}
			else{
				var oHelper = new com.trs.web2frame.BasicDataHelper();
				oHelper.JspRequest(PageContext.ObjectServiceId,this.params,true,_fCallBack||this.PageLoaded);
			}
		}
	},
	RefreshList : function(_selectRowIds,_bDoNotNeedRefresh){
//		ProcessBar.init('进度执行中，请稍候...','document',window);
//		ProcessBar.addState('正在获取数据');
//		ProcessBar.addState('正在加载数据列表');
//		ProcessBar.addState('正在生成数据导航条');
//		ProcessBar.addState('正在绑定列表事件');
//		ProcessBar.addState('加载完成');
//		ProcessBar.start();

		$endSimpleRB();
		$beginSimpleRB("正在刷新列表,请稍候...");
		//ge gfc add @ 2007-1-11 13:28 在刷新列表之前执行的操作
		if(PageContext._doBeforeRefresh) {
			PageContext._doBeforeRefresh(window.location_search||(top.actualTop||top).location_search);
		}
//		PageContext.SelectedRowIds = null;
		var fCallBack = this.ListRefreshed.bind({"rowids":_selectRowIds,"DoNotNeedRefresh":(_bDoNotNeedRefresh||false)});
		this.LoadPageContent(fCallBack);
		// ge gfc modify @ 2006-12-28 14:30 使用注册的方法
		//BasicDataHelper.call(PageContext.ObjectServiceId,'query',this.params,false,fCallBack);
/*
		var sQueryMethod = PageContext.ObjectMethodName || 'query';	
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call(PageContext.ObjectServiceId,sQueryMethod,this.params,true,fCallBack);
*/
	},
	updateCurrRows : function(_currId, _bDoNotNeedRefresh){
		var aRowIds = (_currId!=null)?[_currId]:Grid.getRowIds();
		this.RefreshList(aRowIds, _bDoNotNeedRefresh);
	},
	// ge gfc add @ 2007-2-13 9:25 更新列表，同时更新属性栏，但不改变选中状态
	refreshCurrRows : function(){
		this.updateCurrRows(null, false);
	},
	refresh : function(_params){
//		ProcessBar.addState('正在获取数据');
//		ProcessBar.addState('正在加载过滤器列表');
//		ProcessBar.addState('正在加载数据列表');
//		ProcessBar.addState('正在生成数据导航条');
//		ProcessBar.addState('正在绑定列表事件');
//		ProcessBar.addState('加载完成');
//		ProcessBar.start();
		$endSimpleRB();
		$beginSimpleRB("正在刷新列表,请稍候...");
		if(window.frameElement&&window.frameElement.id=='main'){
			(top.actualTop||top).location_search = (_params)?'?'+_params:location.search;
		}
		else{
			window.location_search = (_params)?'?'+_params:location.search;
		}
		var sChannelId = getParameter("channelid",_params);
		var sSiteId = getParameter("siteid",_params);
		PageContext.params = Object.extend({},PageContext.AbstractParams||{});
		var search = _params||location.search;
		if(search){
			Object.extend(PageContext.params,search.toQueryParams());
		}
		var hostChanged = false;
		if(sChannelId!=''){
			var oldChannelid = (PageContext.params||{})["channelid"];
			Object.extend(PageContext.params,{"channelid":sChannelId});
			if(oldChannelid!=sChannelId)hostChanged=true;
		}
		else if(sSiteId!=''){
			var oldSiteid = (PageContext.params||{})["siteid"];
			Object.extend(PageContext.params,{"siteid":sSiteId});
			if(oldSiteid!=sSiteId)hostChanged=true;
		}
		if(getParameter("RightValue",_params)){
			Object.extend(PageContext.params,{"RightValue":getParameter("RightValue",_params)});
		}		
		if(getParameter("orderby",_params)){
			Object.extend(PageContext.params,{"OrderBy":getParameter("orderby",_params)});
		}
		if(getParameter("searchword",_params)){
			Object.extend(PageContext.params,{"SearchWord":getParameter("searchword",_params)});
		}
		if(PageContext.params["ContainsRight"]==null){
			Object.extend(PageContext.params,{"ContainsRight":true});
		}
		if(!PageContext.params["PageSize"]){
			//设置PageSize个性化, try的目的是为了防止由于
			//页面刷新(open window方式)导致类似“没有权限”的js错误
			try{
				var $personalCon = top.$personalCon;
				if(window.opener){
					$personalCon = window.opener.top.$personalCon;
				}
				if($personalCon) {
					var PageSize = ($personalCon.listPageSize || {}).paramValue;
					if(PageSize){
						PageContext.params["PageSize"] = PageSize;
					}
				}					
			}catch(err){
				// just skip it
			}
		}
		PageContext.loadedObjects = false;

		if(PageContext.params["FilterType"]==null||PageContext.params["FilterType"]==''){
			PageContext.params["FilterType"] = PageContext.getDefaultFilterType();
		}
		//ge gfc add @ 2007-1-11 13:28 在刷新列表之前执行的操作
		if(PageContext._doBeforeRefresh) {
			PageContext._doBeforeRefresh(window.location_search||(top.actualTop||top).location_search);
		}
		Grid.refresh();
		PageContext.LoadPageContent();
	},
	getRightIndex : function(_sFunctionType){
		if(PageContext.rightIndexs){
			return PageContext.rightIndexs[_sFunctionType]||-1;
		}
		return -1;
	},
	AdjustListView : function(){		
		var headCols = document.getElementsByClassName('grid_head_column',$('grid_head'));
		var arrClassNames = [];
		var arrRatios = [];
		var arrWidth = [];
		for(var i=0;i<headCols.length;i++){
			var headCol = headCols[i];
			if(Element.visible(headCol)){
				var dyWidth = headCol.getAttribute("grid_dywidth",2);
				if(dyWidth){
					var arrTmp = dyWidth.split(',');
					arrClassNames.push(arrTmp[0]);
					if(arrTmp[1]){
						arrRatios.push(parseFloat(arrTmp[1]));
					}
				}
				else{
					arrWidth.push(Element.getStyle(headCol,'width'));
				}
			}
		}
		if(arrRatios.length!=0&&arrRatios.length!=arrClassNames.length){
			alert('动态调整列宽出错:grid_dywidth指定的多列中有些指定了所占比例,有些没有.');
			return;
		}
		var dAllRatio = 0;
		if(arrRatios.length>0){
			arrRatios.each(function(_dRatio){
				dAllRatio += _dRatio;
			});
		}
		var sStyleId = 'dy_list_adjust';
		var eStyleDiv = $(sStyleId);
		var cssStr = '';
		var allWidth = Element.getDimensions(document.body)["width"]-25;
		arrWidth.each(function(_iWidth){allWidth -= parseInt(_iWidth);});
		arrClassNames.each(function(_sClassName,_nIndex){
			var iWidth = 0;
			if(arrRatios.length>0){
				iWidth = parseInt(allWidth*arrRatios[_nIndex]/dAllRatio);
			}
			else{
				iWidth = parseInt(allWidth/arrClassNames.length);
			}
			cssStr += '.'+_sClassName+'{width:'+(iWidth-10)+'px!important;width:'+iWidth+'px}';
		});
		var eStyle = $style(cssStr);
		$removeChilds(eStyleDiv);
		eStyleDiv.appendChild(eStyle);
	},
	checkRequired : function(){
		try{
			if($('pageinfo')){
				if(!PageContext.PageFilters){
					alert('指定了过滤器，但没有指定过滤器对象"PageContext.PageFilters"');
					return;
				}
				if(!$('pageinfo_template')){
					alert('指定了过滤器，但没有指定过滤器模板"pageinfo_template"');
					return;
				}
				else {
					PageContext.HasPageInfo = true;
				}
			}
			if(!$('grid_data')||!$('objects_template')){
				alert('未指定数据列表"gird_data"或未指定数据列表模板"objects_template"');
				return;
			}
			PageContext.ObjectsTagName = $('objects_template').getAttribute("select",2).split('.')[0].toUpperCase();
		}catch(err){
			//TODO logger
			//alert('when check pageContext required:'+(err.stack||err.message));
		}
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
	getDefaultFilterType : function(){
		for(var i=0; PageContext.PageFilters && i<PageContext.PageFilters.length;i++){
			var filter = PageContext.PageFilters[i];
			var newFilter = {};
			for(var n in filter){
				newFilter[n.toUpperCase().trim()] = filter[n];
			}
			if(newFilter['ISDEFAULT'])return newFilter["TYPE"];
		}
		return 0;
	},
	LoadPage : function(){
		this.checkRequired();
		this.AdjustListView();
		if(_MOZILLA||_GECKO){
			Event.observe(window,'resize',this.AdjustListView);
		}
		else{
			Event.observe($('table_body'),'resize',this.AdjustListView);			
		}
		this.refresh();
	},
	/*
	getNavigatorHtml : function(){
		var iRecordNum = parseInt(PageContext["RecordNum"]);
		if(iRecordNum==0)return '';
		var iCurrPage = parseInt(PageContext.params["CurrPage"]||1);
		var iPageSize = parseInt(PageContext["PageSize"]);
		var iPages = parseInt(PageContext["PageCount"]);
		var sHtml = '';
		var sTypeDesc = PageContext.PageNav["UnitName"]+PageContext.PageNav["TypeName"];
		sHtml += '<span class="nav_page_detail">共<span class="nav_pagenum">'+iPages+'</span>页'
					+'<span class="nav_recordnum">'+iRecordNum+'</span>'
					+sTypeDesc+','
					+'当前为第<span class="nav_currpage">'+iCurrPage+'</span>页</span>';
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
	},
	drawNavigator : function(){
		var eNavigator = $(PageContext.PageNav.NavId);
		if(eNavigator){
			var sHtml = PageContext.getNavigatorHtml();
			Element.update(eNavigator,sHtml);
		}
	},
	*/
	PageNav : {
		UnitName : '条',
		TypeName : '记录',
		NavId : 'list_navigator',
		go : function(_iPage,_iPageNum){
			alert('must implements');
		},
		goFirst : function(){
			if((PageContext.params["CurrPage"]||1)>1){
				PageContext.PageNav.go(1,PageContext["PageCount"]);
			}
		},
		goPre : function(){
			if((PageContext.params["CurrPage"]||1)>1){
				PageContext.PageNav.go(PageContext.params["CurrPage"]-1,PageContext["PageCount"]);
			}
		},
		goNext : function(){
			if((PageContext.params["CurrPage"]||1)<PageContext["PageCount"]){
				PageContext.PageNav.go((PageContext.params["CurrPage"]||1)+1,PageContext["PageCount"]);
			}
		},
		goLast : function(){
			if((PageContext.params["CurrPage"]||1)<PageContext["PageCount"]){
				PageContext.PageNav.go(PageContext["PageCount"],PageContext["PageCount"]);
			}
		}
	}
});
PageContext.init();
Object.extend(PageContext.PageNav, {
	go : function(_iPage, _maxPage){
		if(_iPage>_maxPage){
			_iPage = _maxPage;
		}
		Object.extend(PageContext.params,{"CurrPage":_iPage});
		PageContext.RefreshList();
	},
	EffectMe : function(event, _oElement){
		event = event || window.event;
		switch(event.type){
			case 'mouseover':
				Element.addClassName(_oElement, 'page_nav_goto');
				break;
			case 'mouseout':
				Element.removeClassName(_oElement, 'page_nav_goto');
				break;
			case 'click':
				_oElement.lastNo = _oElement.innerHTML;
				Element.addClassName(_oElement, 'page_nav_input');
				_oElement.contentEditable = true;
				document.execCommand('selectAll', _oElement);
				break;
			case 'blur':
				_oElement.contentEditable = false;
				var newNo = parseInt(_oElement.innerHTML, 10);
				if(isNaN(newNo)){
					_oElement.innerHTML = _oElement.lastNo;
				}
				else{
					_oElement.innerHTML = newNo;
				}
				document.execCommand('UnSelect', _oElement);
				Element.removeClassName(_oElement, 'page_nav_input');
				if(_oElement.lastNo!=_oElement.innerHTML){
					PageContext.PageNav.go(parseInt(_oElement.innerHTML, 10), PageContext["PageCount"]);
				}
				break;
			case 'keydown':
				if(event.keyCode==13){
					_oElement.blur();
					return;
				}
//				Event.stop(event);
				break;
		}
	}
});
PageContext.getPageNavHtml = function(iCurrPage,iPages){
	var sHtml = '';
	var sActions = ' title="单击当前页可输入跳转页号" onclick="PageContext.PageNav.EffectMe(arguments[0], this);"' 
		+ ' onmouseover="PageContext.PageNav.EffectMe(arguments[0], this);"'
		+ ' onmouseout="PageContext.PageNav.EffectMe(arguments[0], this);"'
		+ ' onpaste="return false;"'
		+ ' onkeydown="PageContext.PageNav.EffectMe(arguments[0], this);"'
		+ ' onblur="PageContext.PageNav.EffectMe(arguments[0], this);"';
	//output first
	if(iCurrPage!=1){
		sHtml += '<span class="nav_page" title="首页" onclick="PageContext.PageNav.goFirst();">1</span>';
	}else{
		sHtml += '<span class="nav_page nav_currpage"'+ sActions +'>1</span>';
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
			sCenterHtml += '<span class="nav_page nav_currpage"'+ sActions +'>'+i+'</span>';
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
		sHtml += '<span class="nav_page nav_currpage" title="尾页"'+ sActions +'>'+iPages+'</span>';
	}
	return sHtml;
}

var Grid = new com.trs.wcm.Grid('objects_grid');
Event.observe(window, 'load', function(){
	if($('objects_grid')==null){
		if($('grid_data')!=null){
			Grid.gridId = 'grid_data';
		}
		else{
			alert('页面中没有指定名称(objects_grid/grid_data)的Grid容器');
		}
	}
});
Object.extend(Grid,{
	draggable : true,
	checkSpecSrcElement : function(_oElement){
		if(_oElement.tagName=='SPAN' && Element.hasClassName(_oElement, 'page_nav_input'))
			return false;
	},
	_sort : function(_sSortField,_sSortOrder){
		Object.extend(PageContext.params,{"CurrPage":1,"OrderBy":(_sSortField+' '+_sSortOrder)});
		PageContext.RefreshList();
	},
	_getRight : function(_eRow){
		var right = _eRow.getAttribute('right', 2);
		delete _eRow;

		return right;
	},
	_mappingFunctionType : function(_sFuncType){
		return _sFuncType.toLowerCase();
	},
	_exec : function(_sFunctionType,_sRowId,_eRow,_eSrcElement){
		var sFunctionType = _sFunctionType.toLowerCase();
		var sRight = this._getRight(_eRow);
		if(sRight != null){
			var nRightIndex = _eRow.getAttribute("right_index",2);
			if(nRightIndex==null||nRightIndex.trim()==''){
				nRightIndex = PageContext.getRightIndex(sFunctionType);
			}
			if(!isAccessable4WcmObject(sRight,nRightIndex)){
				return true;
			}
		}
		PageContext.ObjectMgr[this._mappingFunctionType(_sFunctionType)](_sRowId,PageContext.params);
		return false;
	},
	_dealWithSelectedRows : function(){
		if(PageContext.ObjectType == null) {
			return ;
		}

		if(getParameter("IsMono")=='1'||window.top==window)return;
		var rows = this.getRows();
		var ObjectIds = [];//this.getRowIds();
		var ObjectRights = [];
		rows.each(function(_eRow){
			ObjectIds.push(this.getRowId(_eRow));
			ObjectRights.push(this._getRight(_eRow));
			delete _eRow;
		}.bind(this));
		var oPostData = Object.extend(Object.extend({},PageContext.params),
			{"objecttype":PageContext.ObjectType,"objectids":ObjectIds,"objectrights":ObjectRights});

		// ge gfc add @ 2006-12-28 11:12 增加一个表明当前列表中的记录数的参数
		oPostData.ListRecNum = PageContext.RecordNum;

		// ge gfc add @ 2007-1-16 15:04 增加收集某一行/多行选中时传入的附加参数的方法
		if(this._getAdditionRowSelInfo) {
			var addedInfo = this._getAdditionRowSelInfo(oPostData);
			if(addedInfo != null && typeof(addedInfo) == 'object') {
				Object.extend(oPostData, addedInfo);
			}
			//alert($toQueryStr(oPostData))
		}
		if(PageContext.enableAttrPanel === false) return;
		setTimeout(function(){
			$MessageCenter.sendMessage('oper_attr_panel','PageContext.response',"PageContext",oPostData,true,true);
		},10);
	},
	_DealWithRightDefault_ : function(_sRightType,_bTrue){
		return (_bTrue)?'object_'+_sRightType:'objectcannot_'+_sRightType;
	},
	DealWithRight : function (_sRight,_cmdType){
		var sReturn = '';
		var iRightIndex = 0;
		var sRightType = _cmdType.toLowerCase();
		if(this.MappingRightType){
			sRightType = this.MappingRightType(sRightType);
		}
		var special = "Special"+_cmdType.charAt(0).toUpperCase()+_cmdType.toLowerCase().substring(1);
		return (this[special]||this["_DealWithRightDefault_"])(sRightType,isAccessable4WcmObject(_sRight,PageContext.getRightIndex(sRightType)));
	},
	ctrlA : function(event){
		Grid.toggleAllRows();
		return false;
	},
	checkRight : function(_sFunctionType,_sDisplayCmd){
		return this.filterWithRight(PageContext.getRightIndex(_sFunctionType.toLowerCase()),_sDisplayCmd);
	},
	canAddNew : function(_sFunctionType, _sDisplayCmd, _nRightIndex){
		var rightIndex = _nRightIndex || PageContext.getRightIndex(_sFunctionType.toLowerCase());
		if(rightIndex == -1) {//没有从已经定义的map中找到权限值，直接忽略并返回
			return false;
		}
		var sRights = PageContext.params.RightValue || '0';
		if(!isAccessable4WcmObject(sRights, rightIndex)){
			$timeAlert('您没有权限进行[' + (_sDisplayCmd ? _sDisplayCmd : '添加') + ']操作',5);
			return false;
		}
		return true;
	},
	filterWithRight : function(_nRightIndex,_sDisplayCmd){
		var rows = this.getRows();
		if(rows.length==0)return [];
		var ObjectRights = [];
		var ObjectIds = [];
		rows.each(function(_eRow){
			ObjectIds.push(this.getRowId(_eRow));
			ObjectRights.push(this._getRight(_eRow));
			delete _eRow;
		}.bind(this));
		delete rows;
		var sRight = mergeRights(ObjectRights);
		if(!isAccessable4WcmObject(sRight,_nRightIndex)){
			$timeAlert('您没有权限'+_sDisplayCmd+'当前选中对象.',5);
			return [];
		}
		return ObjectIds;
	},
	keyUp : function(event){
		Grid.selectPreRow(event.ctrlKey);
	},
	keyDown : function(event){
		Grid.selectNextRow(event.ctrlKey);
	},
	ctrlPgUp : function(event){
		PageContext.PageNav.goPre();
	},
	ctrlPgDn : function(event){
		PageContext.PageNav.goNext();
	},
	ctrlHome : function(event){
		PageContext.PageNav.goFirst();
	},
	ctrlEnd : function(event){
		PageContext.PageNav.goLast();
	}
});
Grid._exec0 = Grid._exec;
var PageFilter = new com.trs.wcm.PageFilter('pageinfo');
Object.extend(PageFilter,{
	_loadData : function(_sFilterType){
		// gfc add @ 2007-3-7 9:42 设置要提交的 filtertype 的字段名
		var filterName = PageContext.PageFilterName || 'FilterType';
		PageContext.params['CurrPage'] = 1;
		PageContext.params[filterName] = _sFilterType;
		PageContext.RefreshList();
	}
});


//Event.observe(window,'load',PageContext.refresh);
//Event.onDOMReady(PageContext.refresh);
