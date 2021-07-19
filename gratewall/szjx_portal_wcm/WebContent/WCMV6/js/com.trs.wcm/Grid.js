$package('com.trs.wcm');
var _GRID_ = com.trs.wcm.Grid = Class.create('wcm.Grid');
Object.extend(_GRID_,{
	CSS_HEAD : 'grid_head',
	CSS_ROW : 'grid_row',
	CSS_ROW_ODD : 'grid_row_odd',
	CSS_ROW_EVEN : 'grid_row_even',
	CSS_CHECKBOX : 'grid_checkbox',
	CSS_ROW_ACTIVE : 'grid_row_active',
	CSS_HEAD_COLUMN : 'grid_head_column',
	CSS_COLUMN : 'grid_column',
	CSS_HEAD_COLUMN_ORDER_DESC : 'grid_head_column_orderdesc',
	CSS_HEAD_COLUMN_ORDER_ASC : 'grid_head_column_orderasc',
	ATT_SORTBY : 'grid_sortby',
	ATT_ROWID : 'grid_rowid',
	ATT_FUNCTION : 'grid_function',
	CSS_INDEX : 'grid_index'
});
com.trs.wcm.Grid.prototype = {
	initialize: function(_sGridId){
		this.gridId = _sGridId;
		this.StartIndex = 0;
		PageEventHandler.register(this);
	},
	_sort : function(_sSortField,_sSortOrder){
	},
	_exec : function(_sFunctionType,_sRowId,_eRow,_eSrcElement){
	},
	_dealWithSelectedRows : function(){
	},
	_getSelectAbleClass : function(_eRow){
		return _GRID_['CSS_ROW'];
	},
	init : function(_bInitHeader){
		if(_bInitHeader){
			if(this.currSortField==null&&PageContext.params["OrderBy"]){
				this.currSortField = PageContext.params["OrderBy"].split(' ')[0].toLowerCase();
				this.currSortOrder = PageContext.params["OrderBy"].split(' ')[1].toLowerCase();
			}
			var eHeaderColumns = [];
			if($('grid_head')){
				var eTmpColumns = $('grid_head').childNodes;
				for(var i=0;i<eTmpColumns.length;i++){
					if(eTmpColumns[i].tagName&&Element.hasClassName(eTmpColumns[i],_GRID_['CSS_HEAD_COLUMN'])){
						eHeaderColumns.push(eTmpColumns[i]);
					}
				}
			}
			if(eHeaderColumns.length==0){
				eHeaderColumns = document.getElementsByClassName(_GRID_['CSS_HEAD_COLUMN'],$(this.gridId));
			}
			this.headers = eHeaderColumns;
			for(var i=0;i<eHeaderColumns.length;i++){
				var sSortField = eHeaderColumns[i].getAttribute(_GRID_['ATT_SORTBY']);
				if(sSortField&&this.currSortField == sSortField.toLowerCase()){
					this.lastSortElement = eHeaderColumns[i];
					if(this.currSortOrder=="asc"){
						Element.addClassName(eHeaderColumns[i],_GRID_['CSS_HEAD_COLUMN_ORDER_ASC']);
					}
					else{
						Element.addClassName(eHeaderColumns[i],_GRID_['CSS_HEAD_COLUMN_ORDER_DESC']);
					}
				}
			}
			delete eHeaderColumns;
		}
		var eRows = this._QuickGetRows();
		this.rows = eRows;
		for(var i=0;i<eRows.length;i++){
			var element = eRows[i];
			this.setRowIndex(element,i);
			if(i%2==1){
				Element.addClassName(element,_GRID_['CSS_ROW_EVEN']);
			}
			else{
				Element.addClassName(element,_GRID_['CSS_ROW_ODD']);
			}
		}
		delete eRows;
	},
	setRowIndex : function(element,nIndex){
		var eIndexSpan = $(_GRID_['CSS_INDEX']+'_'+this.getRowId(element));
		if(eIndexSpan){
			eIndexSpan.innerHTML = (this.StartIndex||0)+nIndex+1;
			return;
		}
		var eIndexSpans = document.getElementsByClassName(_GRID_['CSS_INDEX'],element);
		if(eIndexSpans!=null&&eIndexSpans.length>0){
			for(var j=0;j<eIndexSpans.length;j++){
				eIndexSpans[j].innerHTML = (this.StartIndex||0)+nIndex+1;
			}
		}
	},
	adjustColors : function(){
		var nStartIndex = $(this.gridId).getAttribute(_GRID_['START_INDEX'],2)||0;
		var eRows = this._QuickGetRows();
		for(var i=0;i<eRows.length;i++){
			var element = eRows[i];
			this.setRowIndex(element,i);
			if(i%2==1){
				Element.removeClassName(element,_GRID_['CSS_ROW_ODD']);
				Element.addClassName(element,_GRID_['CSS_ROW_EVEN']);
			}
			else{
				Element.removeClassName(element,_GRID_['CSS_ROW_EVEN']);
				Element.addClassName(element,_GRID_['CSS_ROW_ODD']);
			}
		}
		delete eRows;
	},
	bindEvents : function(_bBindHeaderEvents){
		if(_bBindHeaderEvents){
			if(this.arrHeadEvOb){
				this.arrHeadEvOb.each(function(element){Event.stopAllObserving(element);});
			}
			this.arrHeadEvOb = [];
			var eTheads = this.headers || document.getElementsByClassName(_GRID_['CSS_HEAD'],$(this.gridId));
			for(var i=0;i<eTheads.length;i++){
				Event.observe(eTheads[i],'click',this.handleHeadClick.bindAsEventListener(this));
				this.arrHeadEvOb.push(eTheads[i]);
			}
			delete eTheads;
		}
		if(this.arrEvOb){
			this.arrEvOb.each(function(element){Event.stopAllObserving(element);});
		}
		this.arrEvOb = [];
		var eRows = this.rows || this._QuickGetRows();
		if(this.draggable){
			if(this.draggers){
				this.draggers.each(function(d){d.destroy();});
			}
			this.draggers = [];
		}
		for(var i=0;i<eRows.length;i++){
			var element = eRows[i];
			Event.observe(element,'click',this.handleRowClick.bindAsEventListener(this,element));
			if(this.draggable){
				var d = new com.trs.wcm.ListDragger(element);
				d.daton = this.gridId;
				this.draggers.push(d);
			}
			this.arrEvOb.push(element);
		}
		delete eRows;
	},
	handleHeadClick : function(event){
		var srcElement = Event.element(event);
		if(Element.hasClassName(srcElement,_GRID_['CSS_HEAD_COLUMN'])&&srcElement.getAttribute(_GRID_['ATT_SORTBY'])){
			var sSortOrder = this.currSortOrder;
			var sSortField = srcElement.getAttribute(_GRID_['ATT_SORTBY']).toLowerCase();
			if(this.currSortField!=sSortField){
				sSortOrder = '';
			}
			if(this.lastSortElement){
				try{
					Element.removeClassName(this.lastSortElement,_GRID_['CSS_HEAD_COLUMN_ORDER_DESC']);
					Element.removeClassName(this.lastSortElement,_GRID_['CSS_HEAD_COLUMN_ORDER_ASC']);
				}catch(err){
					this.lastSortElement = null;
				}
			}
			if(sSortOrder=='desc'){
				sSortOrder = 'asc';
				Element.addClassName(srcElement,_GRID_['CSS_HEAD_COLUMN_ORDER_ASC']);
			}
			else{
				sSortOrder = 'desc';
				Element.removeClassName(srcElement,_GRID_['CSS_HEAD_COLUMN_ORDER_ASC']);
				Element.addClassName(srcElement,_GRID_['CSS_HEAD_COLUMN_ORDER_DESC']);
			}
			this.currSortField = sSortField;
			this.currSortOrder = sSortOrder;
			PageContext.params["OrderBy"] = this.currSortField+' '+this.currSortOrder;
			this.lastSortElement = srcElement;
			this._sort(sSortField,sSortOrder);
			return;
		}
	},
	removeCurrRowClass : function(){
		var eRows = this.rows || this._QuickGetRows();
		for(var i=0;i<eRows.length;i++){
			var element = eRows[i];
			Element.removeClassName(element,'grid_row_curr');
		}
	},
	findGF : function(dom){
		while(dom && dom.tagName != 'BODY'){
			var sFunctionType = dom.getAttribute(_GRID_['ATT_FUNCTION']);
			if(sFunctionType){
				return sFunctionType;
			}
			if(Element.hasClassName(dom, _GRID_['CSS_ROW'])){
				return null;
			}
			dom = dom.parentNode;
		}
		return null;
	},
	handleRowClick : function(event , _eRow){
		if(!Element.hasClassName(_eRow,this._getSelectAbleClass()))return;
		this.removeCurrRowClass();
		var srcElement = Event.element(event);
		var sFunctionType = this.findGF(srcElement);//srcElement.getAttribute(_GRID_['ATT_FUNCTION']);
		var sRowId = _eRow.getAttribute(_GRID_['ATT_ROWID']);
		if(srcElement.nodeName.toUpperCase()=='INPUT'&&srcElement.type=="checkbox"){
			sFunctionType = sFunctionType || 'multi';
		}
		if(_eRow.getAttribute(_GRID_['ATT_FUNCTION'])=='cancelBubble'){
			sFunctionType = sFunctionType || 'cancelBubble';
		}
		// ge gfc add @ 2007-3-7 14:51 在执行前进行预处理
		if(this._doBeforeRowToggle) {
			var retVal = this._doBeforeRowToggle(event , _eRow);
			if(retVal === false) {
				return;
			}
		}
		switch(sFunctionType){
			case 'cancelBubble':
				break;
			case 'multi':
				this.toggleCurrRow(_eRow , true);
				break;
			case null:
				this.toggleCurrRow(_eRow , event.ctrlKey);
				break;
			default:
				// ge gfc add @ 2007-3-7 14:51 在执行前进行预处理
				if(this._doBeforeKeyPressAction) {
					this._doBeforeKeyPressAction(event , _eRow);
				}

				var retValue = this._exec.call(this,sFunctionType,sRowId,_eRow,srcElement);
				if(retValue!=false){
					this.toggleCurrRow(_eRow , event.ctrlKey);
				}
				break;
		}
		this.adjustColors();
	},
	selectFirstRow : function(){
		var rows = this._QuickGetRows(this._getSelectAbleClass());
		this.toggleCurrRow(rows[0]);
	},
	toggleCurrRow : function(_currRow,_bMulti){
		var eCheckBox = (document.getElementsByClassName(_GRID_['CSS_CHECKBOX'],_currRow)||[])[0];
		if(!this.checkedElements){
			this.checkedElements = [];
		}
		var aChecked = this.checkedElements;
		if(this.multi||(!this.radio && _bMulti)){//多选
			if(aChecked.include(_currRow)){
				Element.removeClassName(_currRow,_GRID_['CSS_ROW_ACTIVE']);
				if(eCheckBox){
					eCheckBox.checked = false;
					eCheckBox.defaultChecked = false;
				}
				this.checkedElements = aChecked.without(_currRow,eCheckBox);
			}
			else{
				Element.addClassName(_currRow,_GRID_['CSS_ROW_ACTIVE']);
				if(eCheckBox){
					eCheckBox.checked = true;
					eCheckBox.defaultChecked = true;
				}
				aChecked.push(_currRow,eCheckBox);
			}
		}
		else{
			var bCurrRowActive = aChecked.include(_currRow);
			var count = aChecked.length;
			for(var i=0;i<count;i+=2){
				var eTmpRow = aChecked[i];
				var eTmpCheckBox = aChecked[i+1];
				if(eTmpRow)Element.removeClassName(eTmpRow,_GRID_['CSS_ROW_ACTIVE']);
				if(eTmpCheckBox){
					eTmpCheckBox.checked = false;
					eTmpCheckBox.defaultChecked = false;
				}
			}
			this.checkedElements = aChecked = [];
			if(!bCurrRowActive){
				Element.addClassName(_currRow,_GRID_['CSS_ROW_ACTIVE']);
				if(eCheckBox){
					eCheckBox.checked = true;
					eCheckBox.defaultChecked = true;
				}
				aChecked.push(_currRow,eCheckBox);
			}
		}
		//TODO
		(this._dealWithCurrRow||Prototype.emptyFunction).call(this,_currRow);
		setTimeout(this._dealWithSelectedRows.bind(this),0);
		delete _currRow;
	},
	selectPreRow : function(_bMulti){
		var rows = this._QuickGetRows(this._getSelectAbleClass());
		var count = rows.length;
		var firstSelectedIndex = -1;
		if(!this.checkedElements){
			this.checkedElements = [];
		}
		var aChecked = this.checkedElements;
		var preCanSelectRow = null;
		if(aChecked.length>0){
			for(var i=0;i<count;i++){
				if(aChecked.include(rows[i]))break;
				preCanSelectRow = rows[i];
			}
		}
		if(preCanSelectRow){
			this.toggleCurrRow(preCanSelectRow,_bMulti);
		}
		delete rows;
	},
	selectNextRow : function(_bMulti){
		var rows = this._QuickGetRows(this._getSelectAbleClass());
		var count = rows.length;
		var lastSelectedIndex = count;
		if(!this.checkedElements){
			this.checkedElements = [];
		}
		var aChecked = this.checkedElements;
		var nextCanSelectRow = null;
		if(aChecked.length>0){
			for(var i=count-1;i>=0;i--){
				if(aChecked.include(rows[i]))break;
				nextCanSelectRow = rows[i];
			}
		}
		if(nextCanSelectRow){
			this.toggleCurrRow(nextCanSelectRow,_bMulti);
		}
		delete rows;
	},
	_QuickGetRows : function(_sClassName){
		_sClassName = _sClassName || _GRID_['CSS_ROW'];
		var eRows = [];
		if($('grid_data')){
			var eTmpRows = $('grid_data').childNodes;
			for(var i=0;i<eTmpRows.length;i++){
				if(eTmpRows[i].tagName&&Element.hasClassName(eTmpRows[i],_sClassName)){
					eRows.push(eTmpRows[i]);
				}
			}
		}
		if(eRows.length==0){
			eRows = document.getElementsByClassName(_sClassName,$(this.gridId));
		}
		return eRows;
	},
	selectRows : function(_aRowIds,_bNotDeal){
		var rows = this._QuickGetRows(this._getSelectAbleClass());
		this.checkedElements = [];
		if(!Array.isArray(_aRowIds)) {
			_aRowIds = new Array(_aRowIds);
		}
		var aChecked = this.checkedElements;
		for(var i=0;i<rows.length;i++){
			var sRowId = rows[i].getAttribute(_GRID_['ATT_ROWID']);
			var eCheckBox = document.getElementsByClassName(_GRID_['CSS_CHECKBOX'],rows[i])[0];
			if(_aRowIds.include(sRowId)){
				Element.addClassName(rows[i],_GRID_['CSS_ROW_ACTIVE']);
				if(eCheckBox)eCheckBox.checked = true;
				aChecked.push(rows[i],eCheckBox);
			}
			else{
				Element.removeClassName(rows[i],_GRID_['CSS_ROW_ACTIVE']);
				if(eCheckBox)eCheckBox.checked = false;
				aChecked.without(rows[i],eCheckBox);
			}
		}
		//TODO
		if(!_bNotDeal){
			//this._dealWithSelectedRows();
			setTimeout(this._dealWithSelectedRows.bind(this),0);
		}
		delete rows;
	},
	updateColumn : function(oPostData){
		alert("must implements Grid.updateColumn");
	},
	toggleAllRows : function(){
		var checkboxs = document.getElementsByClassName(_GRID_['CSS_CHECKBOX'],$(this.gridId))||[];
		var rows = this._QuickGetRows(this._getSelectAbleClass());
		if(!this.checkedElements){
			this.checkedElements = [];
		}
		var aChecked = this.checkedElements;
		if(aChecked.length!=2*rows.length){
			for(var i=0;i<rows.length;i++){
				if(!aChecked.include(rows[i])){
					Element.addClassName(rows[i],_GRID_['CSS_ROW_ACTIVE']);
					var eCheckBox = (document.getElementsByClassName(_GRID_['CSS_CHECKBOX'],rows[i])||[])[0];
					if(eCheckBox)eCheckBox.checked = true;
					aChecked.push(rows[i],eCheckBox);
				}
			}
		}
		else{
			for(var i=0;i<rows.length;i++){
				Element.removeClassName(rows[i],_GRID_['CSS_ROW_ACTIVE']);
				var eCheckBox = (document.getElementsByClassName(_GRID_['CSS_CHECKBOX'],rows[i])||[])[0];
				if(eCheckBox)eCheckBox.checked = false;
			}
			this.checkedElements = aChecked = [];
		}
		//TODO
		//this._dealWithSelectedRows();
		setTimeout(this._dealWithSelectedRows.bind(this),0);
		delete rows;
		delete checkboxs;
	},
	getRow : function(_sRowId){
		var rows = this.rows || this._QuickGetRows();
		for(var i=0;i<rows.length;i++){
			var sRowId = rows[i].getAttribute(_GRID_['ATT_ROWID']);
			if(sRowId = _sRowId)return rows[i];
		}
		delete rows;
	},
	getRowId : function(_eRow){
		var sRowId = _eRow.getAttribute(_GRID_['ATT_ROWID']);
		delete _eRow;
		return sRowId;
	},
	isSelected : function(_eRow){
		return Element.hasClassName(_eRow,_GRID_['CSS_ROW_ACTIVE']);
	},
	getRows : function(){
		var rows = this._QuickGetRows(this._getSelectAbleClass());
		var aRows = [];
		for(var i=0;i<rows.length;i++){
			if(Element.hasClassName(rows[i],_GRID_['CSS_ROW_ACTIVE'])){
				aRows.push(rows[i]);
			}
		}
		delete rows;
		return aRows;
	},
	getAllRows : function(){
		var rows = this._QuickGetRows(this._getSelectAbleClass());
		return rows;
	},
	getRowIds : function(){
		var rows = this._QuickGetRows(this._getSelectAbleClass());
		var aRowIds = [];
		for(var i=0;i<rows.length;i++){
			var sRowId = rows[i].getAttribute(_GRID_['ATT_ROWID']);
			if(Element.hasClassName(rows[i],_GRID_['CSS_ROW_ACTIVE'])){
				aRowIds.push(sRowId);
			}
		}
		delete rows;
		return aRowIds;
	},
	getAllRowIds : function(){
		var rows = this._QuickGetRows(this._getSelectAbleClass());
		var aRowIds = [];
		for(var i=0;i<rows.length;i++){
			var sRowId = rows[i].getAttribute(_GRID_['ATT_ROWID']);
			aRowIds.push(sRowId);
		}
		delete rows;
		return aRowIds;
	},
	destroy : function(){
		if(this.draggers){
			this.draggers.each(function(d){d.destroy();});
		}
		PageEventHandler.unregister();
		this.rows = null;
		this.headers = null;
		delete this.draggers;
		delete this.lastSortElement;
		delete this.checkedElements;
	},
	refresh : function(){
		if(this.draggers){
			this.draggers.each(function(d){d.destroy();});
		}
		Element.removeClassName(this.lastSortElement,_GRID_['CSS_HEAD_COLUMN_ORDER_ASC']);
		Element.removeClassName(this.lastSortElement,_GRID_['CSS_HEAD_COLUMN_ORDER_DESC']);
		this.rows = null;
		delete this.currSortField;
		delete this.lastSortElement;
		delete this.checkedElements;
	}
}
