Object.extend(PageContext.params, window.location.search.toQueryParams());

Object.extend(PageContext.params, {
	tableName		: getParameter('tableName') || '',//对象的ID属性
	selectFields	: getParameter('selectFields') || '',
	viewId	: getParameter('viewId') || 0,
	PageSize		: 10
});

Object.extend(PageContext, {
	gridContainer	: 'objects_grid',
	dataContainer	: "grid_data",
	queryContainer	: 'query_box',
	requestURL		: "related_fields_query.jsp",
	initQueryBox : function(){
		SimpleQuery.baseURL = '../../../';
		var aQueryFields = [{name: 'MetaDataId', desc: '记录ID', type: 'int'}];
		var aSelectFields = this.params.selectFields.split(",");
		var aFieldDescs =  this.params.selectFields;
		if(getParameter("fieldDescs")){
			aFieldDescs = decodeURIComponent(getParameter("fieldDescs"));
		}
		var aFieldDescs = aFieldDescs.split(",");
		for (var i = 0; i < aSelectFields.length; i++){
			aQueryFields.push({name: aSelectFields[i], desc: aFieldDescs[i], type: 'string'});
		}
		SimpleQuery.register(this.queryContainer, aQueryFields, function(_params){
			Object.extend(PageContext.params, _params);
			PageContext.refreshList();
		}, true);
		SimpleQuery.customStyle();
	},
	loadPage : function(){
		this.initQueryBox();
		new Ajax.Request(this.requestURL, {
			method		: 'post',
			parameters	: $toQueryStr(this.params),
			onSuccess	: this.pageLoaded.bind(this)
		});
	},
	pageLoaded : function(transport, json){
		Element.update(this.dataContainer, transport.responseText.stripScripts());
		this.bindEvents();
		this.loadedObjects = true;
		this.adjustWin();
		Object.extend(PageContext, {
			RecordNum : transport.getResponseHeader("Num"),
			PageCount : transport.getResponseHeader("PageCount"),
			PageSize  : transport.getResponseHeader("PageSize")
		});
		this.drawNavigator();
	},
	refreshList : function(){
		new Ajax.Request(this.requestURL, {
			method		: 'post',
			parameters	: $toQueryStr(this.params),
			onSuccess	: this.listRefreshed.bind(this)
		});
	},
	listRefreshed : function(transport, json){
		Element.update(this.dataContainer, transport.responseText.stripScripts());
		var iStartIndex = parseInt(PageContext.params["CurrPage"]-1)*parseInt(PageContext.PageSize);
		Grid.StartIndex = iStartIndex;
		this.bindEvents();
		Object.extend(PageContext, {
			RecordNum : transport.getResponseHeader("Num"),
			PageCount : transport.getResponseHeader("PageCount"),
			PageSize  : transport.getResponseHeader("PageSize")
		});
		this.drawNavigator();
	},
	bindEvents : function(){
		Grid.init(!PageContext.loadedObjects);
		Grid.bindEvents(!PageContext.loadedObjects);
		if(!PageContext.loadedObjects){
//			new com.trs.drag.SimpleDragger($('ListHead'), window.frameElement);
			new com.trs.drag.SimpleDragger($('DragHandler'), window.frameElement);

		}
	},
	adjustWin : function(){
		var styleSheet = $('dynamicStyle').styleSheet;
		var iframeWidth = parseInt(document.body.offsetWidth) || parseInt(Element.getStyle(window.frameElement, 'width'));
		var totalWidth = iframeWidth - 20;
		var columnWidth = Math.floor(totalWidth / this.params.selectFields.split(",").length) + "px";	
		for (var i = 0, length = styleSheet.rules.length; i < length; i++){
			if(styleSheet.rules[i].selectorText == ".columnWidth"){
				styleSheet.rules[i].style.width = columnWidth;
				break;
			}
		}
	}
});

/*-----------------------------------------------Grid Start----------------------------------------*/
var Grid = new com.trs.wcm.Grid(PageContext.dataContainer);
Object.extend(Grid,{
	_sort : function(_sSortField,_sSortOrder){
		Object.extend(PageContext.params,{"CurrPage":1,"OrderBy":(_sSortField+' '+_sSortOrder)});
		PageContext.refreshList();
	},
	_dealWithSelectedRows : function(){
		var aRows = this.getRows();
		if(aRows.length != 1) return;
		var aResult = [];
		aResult.id = aRows[0].getAttribute("grid_rowid");
		Element.eachChild(aRows[0], function(oColumn){
			if(oColumn.tagName == "TEXTAREA"){
				aResult.pop();
				aResult.push(oColumn.value);
				return;
			}
			aResult.push(oColumn.innerText);
		});
		if(window.frameElement){
			window.frameElement.style.display = 'none';
			top.fFieldSelectedCallBack(aResult);
		}
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
/*-----------------------------------------------Grid End----------------------------------------*/

/*-----------------------------------------------Page Navigator Start----------------------------------------*/
Object.extend(PageContext.PageNav,{
	go : function(_iPage,_maxPage){
		if(_iPage>_maxPage){
			_iPage = _maxPage;
		}
		Object.extend(PageContext.params,{"CurrPage":_iPage});
		PageContext.refreshList();
	}
});
/*-----------------------------------------------Page Navigator End ----------------------------------------*/

function getRowInfo(oRow){
	var aResult = [];
	aResult.id = oRow.getAttribute("grid_rowid");
	oRow.eachChild(function(oColumm){
		if(!index){
			aResult.push(oColumm.innerText);
		}
	});
	alert(Object.parseSource(aResult));
}

Event.observe(window, 'load', function(){
	PageContext.loadPage();
});
//Event.observe(window, 'resize', PageContext.adjustWin.bind(PageContext));


/*-----------------------------------------------Simple Query Start ----------------------------------------*/
Object.extend(SimpleQuery, {
	customStyle : function(){
		var aTables = this.Container.getElementsByTagName("table");	
		if(aTables.length != 2) return;
		aTables[0].removeAttribute('background');
		var aTds = aTables[1].getElementsByTagName("td");
		if(aTds.length <= 0) return;
		aTds[aTds.length - 1].innerHTML = "";
		aTds[aTds.length - 1].style.width = '5px';
	},
	changeQueryType : function(){
		this.changeQueryType0();
		return;
		if(window.frameElement){
			setTimeout(function(){
				window.frameElement.style.display = '';
			}, 1);
		}
	}
});
/*-----------------------------------------------Simple Query End ----------------------------------------*/

function unSelect(){
	var aResult = [];
	aResult.id = 0;
	if(window.frameElement){
		window.frameElement.style.display = 'none';
		top.fFieldSelectedCallBack(aResult);
	}
}