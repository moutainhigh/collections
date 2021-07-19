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
		if(parent.SearchData){// 执行查询
			Object.extend(PageContext.params, parent.SearchData);
		}
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
		var oRelation = {};
		if(this.isSelected(_eRow)){
			oRelation["RelDocId"] = this.getRowId(_eRow);
			oRelation["RelDocChannelId"] = _eRow.getAttribute("ChannelId",2);
			oRelation["RelDocTitle"] = _eRow.getAttribute("DocTitle",2);
			parent.AddRelation(oRelation);
		}
		else{
			parent.DeleteRelationById(this.getRowId(_eRow),2);
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
		var allIds = [];
		for(var i=0;i<rows.length;i++){
			var sRowId = rows[i].getAttribute(_GRID_['ATT_ROWID'],2);
			allIds.push(sRowId);
		}
		var selectedRelDocIds = [];
		var relations = parent.Relations;
		var arr = com.trs.util.JSON.array(relations,"RELATIONS.RELATION")||[];
		for(var i=0;i<arr.length;i++){
			var sRelDocId = com.trs.util.JSON.value(arr[i],"RELDOC.ID");
			if(allIds.include(sRelDocId)){
				selectedRelDocIds.push(sRelDocId);
			}
		}
		this.selectRows0(selectedRelDocIds);
	},
	keyUp : null,
	keyDown : null
});
ProcessBar = (top.actualTop||top).ProcessBar;
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