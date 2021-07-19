//TO Extend
Object.extend(PageContext,{
	isLocal : false,
	ObjectServiceId : 'wcm6_viewdocument',
	ObjectMethodName : 'query',
	MustSelectRows : true,
	AbstractParams : {
		"FieldsToHTML" : "DocTitle",
		"ContainsRight" : false,
		"PageSize" : 8
	},
	isTopped : function(_sTopped){
		if(_sTopped=='true'){
			return 'document_topped';
		}
		return '';
	},
	_doBeforeRefresh : function(_params){
		Object.extend(PageContext.params, {
			channelids: PageContext.params['channelid'],
			siteids: PageContext.params['siteid']
		});
	}
});
Object.extend(PageContext.PageNav,{
	UnitName : '篇',
	TypeName : '文档'
});
Object.extend(Grid,{
	draggable : false,
	multi : true,
	_getSelectAbleClass : function(_eRow){
		//由模板中输出的权限控制Class,表明只有这个class才可选
		//若不需要,可以直接去掉这个方法,默认所有行可选中
		return 'grid_selectable_row';
	},
	_dealWithCurrRow : function(_eRow){
	},
	_dealWithRow : function(_eRow){
		var sRowId = _eRow.getAttribute('docid', 2);
		var sRelDocId = _eRow.getAttribute('recid', 2);
		if(this.isSelected(_eRow)){
			parent.AddSelectedDoc(sRowId,sRelDocId);
		}
		else{
			parent.RemoveSelectedDoc(sRowId,sRelDocId);
		}
	},
	_dealWithSelectedRows : function(){
		var rows = this.getAllRows();
		for(var i=0;i<rows.length;i++){
			this._dealWithRow(rows[i]);
		}
	},
	selectRows : function(){
		var rows = document.getElementsByClassName(_GRID_['CSS_ROW'],$(this.gridId));
		var allRelIds = [];
		for(var i=0;i<rows.length;i++){
			var sRowId = rows[i].getAttribute('recid', 2);
			allRelIds.push(sRowId);
		}
		var selectedRelDocIds = [];
		var hsDocIds = parent.hsSelectedDocIds;
		for(var sDocId in hsDocIds){
			var sRelDocId = hsDocIds[sDocId];
			if(allRelIds.include(sRelDocId)){
				selectedRelDocIds.push(sRelDocId);
			}
		}
		this.selectRows0(selectedRelDocIds);
	},
	keyUp : null,
	keyDown : null
});
function DisableWhenIsCurrDocId(_sDocId,_iType){
	if(_sDocId==parent.CurrDocId){
		if(_iType=='1')return '';
		if(_iType=='2')return 'disabled';
	}
	else{
		if(_iType=='1')return 'grid_selectable_row';
		if(_iType=='2')return '';
	}
}