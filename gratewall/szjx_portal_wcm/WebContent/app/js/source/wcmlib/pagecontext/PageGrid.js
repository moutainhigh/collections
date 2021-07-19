Ext.ns('wcm.Grid');
/*wcm.Grid*/
//数据表格,消息事件
(function(){
	var Row = function(_row, _objType){
		this.dom = _row;
		this.objId = _row.getAttribute("rowid", 2);
		this.getObjId = this.getId = function(){
			return this.objId;
		}
		this.getObjType = function(){
			return _objType || WCMConstants.OBJ_TYPE_UNKNOWN;
		}
		this.getAttribute = function(_sName){
			return this.dom.getAttribute(_sName, 2);
		}
	};
	Ext.apply(wcm.Grid, {
		functions : {},
		addFunction : function(actionName, funcAction){
			if(!actionName || !Ext.isFunction(funcAction)){
				throw ['Invalid params![wcm.Grid.addFunction(', $A(arguments) ,')]'].join('');
			}
			this.functions[actionName.toLowerCase().trim()] = funcAction;
		},
		getFunction : function(actionName){
			if(!actionName)return null;
			return this.functions[actionName.toLowerCase().trim()];
		},
		applyKeyProvider : function(){
			if(!PageContext.keyProvider)return;
			Ext.apply(PageContext.keyProvider, {
				keyA : function(event){
					wcm.Grid.selectAll();
					try{
						window.focus();
					}catch(err){}
				}
			});
			if(this.applyKeyListener)return;
			Event.observe(document, 'keydown', function(event){
				var event = event || window.event;
				var eTarget = Event.element(event);
				var bIsTextInput = true;
				if(eTarget != null){
					bIsTextInput = (eTarget.nodeName.toUpperCase() == 'INPUT' && eTarget.type != 'checkbox')
						||(eTarget.nodeName.toUpperCase() == 'TEXTAREA')
						||(eTarget.nodeName.toUpperCase() == 'SELECT');
				}
				if(bIsTextInput)return;
				if(event.ctrlKey || event.altKey)return;
				switch(event.keyCode){
					case Event.KEY_UP:
						return wcm.Grid.selectPrevRow(event);
					case Event.KEY_DOWN:
						return wcm.Grid.selectNextRow(event);
					case Event.KEY_RETURN:
						return wcm.Grid.enterRow(event);
				}
			});
			this.applyKeyListener = true;
		},
		applyContextMenu : function(){
			if(PageContext.contextMenuEnable){
				if(this.applyContextMenued) return;
				this.applyContextMenued = true;
				Ext.fly('wcm_table_grid').on('contextmenu', function(event, target){
					var oGridRow = new wcm.GridRow(target);
					oGridRow.extEvent = event;
					oGridRow.targetElement = target;
					oGridRow.contextmenu();
					event.stop();
					return false;
				}, this);
			}			
		},
		/*avoid memory leak, so the function can't be anonymous*/
		gridHeadClick : function(event){
			var info = wcm.Grid.info;
			event = event || window.event;
			var dom = Event.element(event);
			var sortField = null;
			while(dom && dom.tagName != "BODY"){
				sortField = dom.getAttribute('grid_sortby');
				if(sortField){
					break;
				}
				if(dom.tagName == 'TABLE') break;
				dom = dom.parentNode;
			}
			if(!sortField){
				return;
			}
			var orderby = (info.OrderBy || '').toLowerCase();
			var sCurrField = orderby.split(' ')[0];
			var sCurrOrder = orderby.split(' ')[1];
			sortOrder = (sCurrField!=sortField.toLowerCase()||sCurrOrder=='desc')?'asc':'desc';
			PageContext.loadList({
				CurrPage : 1,
				OrderBy : sortField + ' ' + sortOrder
			}, null, true);
		},
		/*avoid memory leak, so the function can't be anonymous*/
		gridBodySelectStart : function(event){
			Event.stop(event.browserEvent || event);
			return false;
		},
		/*avoid memory leak, so the function can't be anonymous*/
		gridBodyClick : function(event){
			event = event || window.event;
			var srcElement = Event.element(event);
			var oGridRow = new wcm.GridRow(srcElement);
			if(oGridRow.click()){
				var row = this.findRow(srcElement);
				if(row == null) return;
				var bubble = this.doClickRow(row , {
					isCtrl : event.ctrlKey,
					isShift : event.shiftKey,
					event : event,
					target : srcElement
				});
				if(bubble===false)return;
				oGridRow.afterclick();
			}
		},
		/*avoid memory leak, althought it's not real memory leak.*/
		destroy : function(){
			if($('grid_head'))Event.stopObserving('grid_head', 'click', this.gridHeadClick);
			if($('grid_body'))Ext.fly('grid_body').un('selectstart', this.gridBodySelectStart);
			if($('grid_body'))Ext.fly('grid_body').un('click', this.gridBodyClick, this);
		},
		init : function(info){
			this.info = info = info || {};
			if(info.RecordNum==0){
				$('grid_body').appendChild(Element.first($('grid_NoObjectFound')));
			}
			//keyProvider
			this.applyKeyProvider();
			this.copyHeader();
			Event.observe('grid_head', 'click', this.gridHeadClick);
			Ext.fly('grid_body').on('selectstart', this.gridBodySelectStart);
			Ext.fly('grid_body').on('click', this.gridBodyClick, this);

			this.applyContextMenu();
			//第一次模拟触发
			var oTmpGridRow = new wcm.GridRow(null);
			oTmpGridRow.afterclick();
		},
		copyHeader : function(){
			return;
			//fix the header position.
			if($('grid_table_copy')){
				Event.stopAllObserving('grid_head_copy', 'click');
				Element.remove('grid_table_copy');
			}else{
				Ext.fly('grid_table').on('resize', this.synHeaderWidth);
			}
			var sHtml = [
				'<table cellspacing=0 border="1" cellpadding=0 id="grid_table_copy" class="grid_table grid_table_copy" borderColor="gray">',
					'<tr id="grid_head_copy" class="grid_head grid_head_copy">{0}</tr>',
				'</table>'				
			].join("");
			var box = $('wcm_table_grid').parentNode;
			new Insertion.Bottom(box, String.format(sHtml, $('grid_head').innerHTML));
			//Sort
			var info = this.info;
			Event.observe('grid_head_copy', 'click', function(event){
				event = event || window.event;
				var srcElement = Event.element(event);
				var sortField = null;
				if(srcElement.tagName!='TD' || !(sortField=srcElement.getAttribute('grid_sortby'))){
					return;
				}
				var orderby = (info.OrderBy || '').toLowerCase();
				var sCurrField = orderby.split(' ')[0];
				var sCurrOrder = orderby.split(' ')[1];
				sortOrder = (sCurrField!=sortField.toLowerCase()||sCurrOrder=='desc')?'asc':'desc';
				PageContext.loadList({
					CurrPage : 1,
					OrderBy : sortField + ' ' + sortOrder
				});
			});
			this.synHeaderWidth();
		},
		synHeaderWidth : function(){
			$('grid_table_copy').style.width = parseInt($('grid_table').clientWidth,10)+2+"px";
		},
		isSelected : function(row){
			var extEleRow = Ext.get(row);
			var sClassActive = 'grid_row_active';
			return extEleRow.hasClass(sClassActive);
		},
		unselectRow : function(row, bQueit){
			var extEleRow = Ext.get(row);
			var sClassActive = 'grid_row_active';
			if(!extEleRow.hasClass(sClassActive))return;
			var checkbox = $('cb_' + row.getAttribute('rowid', 2));
			if(checkbox!=null){
				checkbox.checked = false;
				checkbox.defaultChecked = false;
			}
			extEleRow.removeClass(sClassActive);
			if(!bQueit){
				var oGridRow = new wcm.GridRow(row);
				oGridRow.afterunselect();
			}
		},
		selectRow : function(row, bQueit){
			var extEleRow = Ext.get(row);
			var sClassActive = 'grid_row_active';
			if(extEleRow.hasClass(sClassActive))return;
			var checkbox = $('cb_' + row.getAttribute('rowid', 2));
			if(checkbox!=null){
				checkbox.checked = true;
				checkbox.defaultChecked = true;
			}
			extEleRow.addClass(sClassActive);
			if(!bQueit){
				var oGridRow = new wcm.GridRow(row);
				oGridRow.afterselect();
			}
		},
		toggleRow : function(row, bInCheckbox){
			var extEleRow = Ext.get(row);
			var sClassActive = 'grid_row_active';
			var checkbox = $('cb_' + row.getAttribute('rowid', 2));
			var bToChecked = !extEleRow.hasClass(sClassActive);
			if(checkbox!=null){
				if(!bInCheckbox){
					bToChecked = !checkbox.checked;
					checkbox.checked = bToChecked;
					checkbox.defaultChecked = bToChecked;
				}else{
					bToChecked = checkbox.checked;
				}
			}
			extEleRow[bToChecked?'addClass':'removeClass'](sClassActive);
			var oGridRow = new wcm.GridRow(row);
			oGridRow[bToChecked?'afterselect':'afterunselect']();
			this.lastRowIndex = bToChecked?row.rowIndex:-1;
			return bToChecked;
		},
		_findGFDom : function(dom){
			while(true){
				if(dom.getAttribute('grid_function', 2)){
					break;
				}
				if(dom.tagName=='BODY'){
					return null;
				}
				dom = dom.parentNode;
			}
			return dom;
		},
		doGridFunction : function(row, opt){
			var target = opt.target;
			target = this._findGFDom(target);
			if(!target)return false;
			var oGridCell = new wcm.GridCell(target, opt);
			if(oGridCell.click()){
				oGridCell.afterclick();
			}
			return true;
		},
		doNoObjectFound : function(row, opt){
			var dom = opt.target;
			while(true){
				if(dom.tagName=='TD' && Element.hasClassName(dom, 'no_object_found')){
					return true;
				}
				if(dom.tagName=='TR' || dom.tagName=='BODY'){
					break;
				}
				dom = dom.parentNode;
			}
			return false;
		},
		contain : function(objId){
			return $('tr_' + objId) != null;
		},
		doClickRow : function(row, opt){
			var srcElement = opt.target;
			if(this.doNoObjectFound(row, opt)){
				return false;
			}
			if(this.doGridFunction(row, opt)){
				return false;
			}
			var event = opt.event;
			var extEleRow = Ext.get(row.dom);
			if(!extEleRow.hasClass('grid_row'))return;
			var sClassActive = 'grid_row_active';
			var srcElement = Event.element(event);
			//checkbox
			if(srcElement.tagName=='INPUT' && srcElement.name.equalsI('RowId')){
				if(srcElement.type.toUpperCase()=='RADIO'){//radio 需做特殊处理
					var allRows = this.getGridRows();
					for (var i=0,n=allRows.length; i<n; i++){
						if(allRows[i]!=row.dom){
							this.unselectRow(allRows[i]);
						}
					}
				}
				this.toggleRow(row.dom, true);
				return;
			}
			//checkbox td set ctrl true
			var checkbox = $('cb_' + row.getAttribute('rowid', 2));
			var bInCheckBoxTd = (srcElement.tagName=='TD'
								&& checkbox!=null && checkbox.parentNode==srcElement)
								|| (srcElement.tagName!='TD' 
									&& checkbox!=null && checkbox.parentNode==srcElement.parentNode);
			if(bInCheckBoxTd){
				opt.isCtrl = true;
			}
			if(opt.isCtrl && !opt.isShift){//仅处理自身
				if(checkbox !=null && checkbox.type.toUpperCase()=='RADIO'){
					if(checkbox.checked==true)return;
					var allRows = this.getGridRows();
					for (var i=0,n=allRows.length; i<n; i++){
						if(allRows[i]!=row.dom){
							this.unselectRow(allRows[i]);
						}
					}
				}
				this.toggleRow(row.dom);
				return;
			}
			if(!opt.isCtrl && !opt.isShift){//处理自身并处理其他为不选
				var chkOrRadion = $('cb_' + row.dom.getAttribute('rowid', 2));
				if(checkbox !=null &&  chkOrRadion.type.toUpperCase()=='RADIO'
					&& chkOrRadion.checked==true)return;
				var allRows = this.getGridRows();
				for (var i=0,n=allRows.length; i<n; i++){
					if(allRows[i]!=row.dom){
						this.unselectRow(allRows[i]);
					}
				}
				this.toggleRow(row.dom);
				return;
			}
			if(!opt.isCtrl && opt.isShift && this.lastRowIndex>=0){//处理自身并处理其他
				var nRowIndex = row.dom.rowIndex;
				var max = Math.max(nRowIndex, this.lastRowIndex);
				var min = Math.min(nRowIndex, this.lastRowIndex);
				var allRows = this.getGridRows();
				var bToChecked = this.toggleRow(row.dom);
				for (var i=0,n=allRows.length; i<n; i++){
					var tmpRow = allRows[i];
					if(tmpRow.rowIndex<min || tmpRow.rowIndex>max){
						this.unselectRow(tmpRow);
						continue;
					}
					if(tmpRow!=row.dom){
						this[bToChecked?'selectRow':'unselectRow'](tmpRow);
					}
				}
			}
		},
		findRow : function(row){
			while(true){
				if(row.tagName=='TR' && Element.hasClassName(row, 'grid_row')){
					break;
				}
				if(row.tagName=='BODY'){
					return null;
				}
				row = row.parentNode;
			}
			return new Row(row, this.rowType(row));
		},
		rowType : function(row){
			return WCMConstants.OBJ_TYPE_UNKNOWN;
		},
		selectAll : function(){
			if(!$('grid_body'))return;
			var allRows = this.getGridRows();
			if(allRows.length==1&&!allRows[0].getAttribute('rowid', 2)){
				//No Object Found...
				return;
			}
			var bToChecked = false;
			for (var i=0,n=allRows.length; i<n; i++){
				if(!this.isSelected(allRows[i])){
					bToChecked = true;
					break;
				}
			}
			for (var i=0,n=allRows.length; i<n; i++){
				this[bToChecked?'selectRow':'unselectRow'](allRows[i]);
			}
			//触发消息
			var oGridRow = new wcm.GridRow();
			oGridRow.afterclick();
		},
		getRowIds : function(bIsArray){
			var allRows = this.getGridRows();
			var arrIds = [];
			for (var i=0,n=allRows.length; i<n; i++){
				if(this.isSelected(allRows[i])){
					arrIds.push(allRows[i].getAttribute('rowid', 2));
				}
			}
			return bIsArray ? arrIds : arrIds.join(',');
		},
		filterRows : function(attName, attValue){
			var allRows = this.getGridRows();
			var result = [];
			for (var i=0,n=allRows.length; i<n; i++){
				if(allRows[i].getAttribute(attName, 2)==attValue){
					result.push(allRows[i]);
				}
			}
			return result;
		},
		getRowInfo : function(row, info){
			var result = {};
			for(var name in info){
				if(!info[name])continue;
				result[name] = row.getAttribute(name, 2);
			}
			result.objId = row.getAttribute('rowid', 2);
			result.right = row.getAttribute('right', 2);
			result.objType = this.rowType(row);
			return result;
		},
		getAllRowInfos : function(info){
			var allRows = this.getGridRows();
			var arrInfos = [];
			for (var i=0,n=allRows.length; i<n; i++){
				var tmpRow = allRows[i];
				if(!Element.hasClassName(tmpRow, 'grid_row'))continue;
				arrInfos.push(this.getRowInfo(tmpRow, info));
			}
			return arrInfos;
		},
		getRowInfos : function(info){
			var allRows = this.getGridRows();
			var arrInfos = [];
			for (var i=0,n=allRows.length; i<n; i++){
				var tmpRow = allRows[i];
				if(this.isSelected(tmpRow)){
					arrInfos.push(this.getRowInfo(tmpRow, info));
				}
			}
			return arrInfos;
		},
		defRowInfo : function(){
			return this.rowInfo;
		},
		getGridRows : function(){
			var rows = $('grid_body').rows;
			var result = [];
			for(var i=0,n=rows.length;i<n;i++){
				if(Element.hasClassName(rows[i], "grid_selectdisable_row"))continue;
				if(!Element.hasClassName(rows[i], 'grid_row'))continue;
				result.push(rows[i]);
			}
			return result;
		},
		each : function(iterator){
			var rows = $('grid_body').rows;
			for(var i=0,n=rows.length;i<n;i++){
				if(!Element.hasClassName(rows[i], 'grid_row'))continue;
				try{
					if(iterator(rows[i]) === false) break;
				}catch(error){
				}
			}
		},
		getRow : function(_rowId, _sAttrName){
			_sAttrName = _sAttrName || 'rowid';
			var row = null;
			this.each(function(dom){
				var rowId = dom.getAttribute(_sAttrName, 2);
				if(rowId == _rowId){
					row = dom;
					return false;
				}
			});
			return row;
		},
		initEditedItems : function(ids){
			if(!ids || ids.length <= 0) return;
			var dom = this.getRow(ids[0], wcm.Grid.editedItemAttrName);
			if(!dom) return;
			this.firstEditIds = true;
			this.editIds = ids;
			Element.addClassName(dom, "list-edit-item");
		},
		clearEditedItems : function(){
			if(this.firstEditIds){
				delete this.firstEditIds;
				return;
			}
			var ids = this.editIds;
			if(!ids || ids.length <= 0) return;
			delete this.editIds;
			var dom = this.getRow(ids[0]);
			if(!dom) return;
			Element.removeClassName(dom, "list-edit-item");
		},
		selectRowBy  : function(event, up){
			var allRows = this.getGridRows();
			var arrIds = [];
			var firstSelectRowIdx = -1;
			for (var i=0,n=allRows.length; i<n; i++){
				if(this.isSelected(allRows[i])){
					if(!up){
						firstSelectRowIdx = i;
						continue;
					}
					if(up && firstSelectRowIdx==-1){
						firstSelectRowIdx = i;
						break;
					}
				}
			}
			if(up && firstSelectRowIdx<1)return;
			if(!up && firstSelectRowIdx>=allRows.length-1)return;
			for (var i=0,n=allRows.length; i<n; i++){
				if(this.isSelected(allRows[i])){
					this.unselectRow(allRows[i]);
				}
			}
			this.selectRow(allRows[up?firstSelectRowIdx-1:firstSelectRowIdx+1]);
			//触发消息
			var oGridRow = new wcm.GridRow();
			oGridRow.afterclick();
		},
		selectPrevRow : function(event){
			return this.selectRowBy(event, true);
		},
		selectNextRow : function(event){
			return this.selectRowBy(event, false);
		},
		enterRow : function(event){
		}
	});
})();
/*GridRow*/
Ext.ns('wcm.GridRow', 'wcm.GridCell');
wcm.GridRow = function(_element){
	var context = (_element)?this.buildContext(_element):null;
	this.objType = WCMConstants.OBJ_TYPE_GRIDROW;
	wcm.GridRow.superclass.constructor.call(this, null, context);
	this.addEvents(['click', 'afterclick', 'contextmenu', 'select', 'afterselect', 'unselect', 'afterunselect']);
};
CMSObj.register(WCMConstants.OBJ_TYPE_GRIDROW, 'wcm.GridRow');
Ext.extend(wcm.GridRow, wcm.CMSObj, {
	buildContext : function(_element){
		var row = wcm.Grid.findRow(_element);
		if(row==null)return null;
		var context = (this._buildContext)?this._buildContext(row):null;
		return Ext.applyIf({
			objId : row.getId(),
			objType : row.getObjType(),
			right : row.getAttribute('right'),
			row : row
		}, context);
	}
});
$MsgCenter.on({
	sid : 'sys_gridrow',
	objType : WCMConstants.OBJ_TYPE_GRIDROW,
	beforeclick : function(event){
		if(event.from()!=wcm.getMyFlag())return;
		var context = event.getContext();
		if(context==null)return;
		var dom = context.row.dom;
		if(Element.hasClassName(dom, "grid_selectdisable_row")) return false;
		var cmsobj = CMSObj.createFrom(context);
		return (cmsobj.canView)?cmsobj.canView():true;
	},
	afterclick : function(event){
		if(event.from()!=wcm.getMyFlag())return;
		if(!window["wcmGrid-DT"]){
			window["wcmGrid-DT"] = new wcm.DelayedTask();
		}
		if(!arguments.callee.caller)return;
		window["wcmGrid-DT"].delay(200, function(event){
			var items = wcm.Grid.getRowInfos(wcm.Grid.defRowInfo());
			var context = Ext.apply({sysOpers : wcm.SysOpers, gridInfo : wcm.Grid.info, PageContext : PageContext}, PageContext.getContext());
			Ext.apply(context, wcm.Grid.info || {});
			var oCmsObjs = CMSObj.createEnumsFrom({
				objType : wcm.Grid.rowType()
			}, context);
			oCmsObjs.addElement(items);
			oCmsObjs.afterselect();
		}, null, [event]);
	},
	contextmenu : function(event){
		if(event.from()!=wcm.getMyFlag())return;
		wcm.Grid.ContextMenuHelper.dispatch(event);
		return false;
	}
});

wcm.Grid.ContextMenuHelper = {
	findCMEl : function(dom){
		while(dom && dom.tagName != 'BODY'){
			if(dom.getAttribute("contextmenu")) return dom;
			dom = dom.parentNode;
		}
		return null;
	},
	init : function(event){
		this.gridRow = event.getObj();
		this.extEvent = this.gridRow.extEvent;
		this.browserEvent = this.extEvent.browserEvent;
		this.targetElement = this.gridRow.targetElement;
		this.cmEl = this.findCMEl(this.targetElement);
		this.items = wcm.Grid.getRowInfos(wcm.Grid.defRowInfo());
		this.row = wcm.Grid.findRow(this.targetElement);
	},
	destroy : function(){
		delete this.gridRow;
		delete this.extEvent;
		delete this.browserEvent;
		delete this.items;
		delete this.targetElement;
		delete this.row;
	},
	dispatch : function(event){
		this.init(event);
		if(this.browserEvent.ctrlKey && this.items.length > 0){
			this.moreRows(event);
		}else if(this.cmEl == null){
			this.noRow(event);
		}else{
			this.oneRow(event);
		}
		this.destroy();
	},
	noRow : function(event){//when click the blank area.
		var pcEvent = PageContext.event;
		var context = pcEvent.getContext();
		var relateType = PageContext.relateType || context.relateType;
		var host = context.getHost();
		var info = {
			type : relateType.toLowerCase(),
			right : host.right,
			objs : host,
			frompanel : true
		};
		var arrOpers = wcm.SysOpers.getOpersByInfo(info, pcEvent);		
		//var wcmEvent = CMSObj.createEvent({objType : relateType}, PageContext.getContext());
		var wcmEvent = PageContext.event;
		var context = Ext.applyIf({
			extEvent : this.extEvent,
			event : this.browserEvent,
			targetElement : this.targetElement,
			opers : arrOpers,
			wcmEvent : wcmEvent,
			isHost : true
		}, PageContext.getContext());
		$MsgCenter.$main(context).contextmenu();
	},
	oneRow : function(event){//when click the row.
		var rowInfo = wcm.Grid.getRowInfo(this.row.dom, wcm.Grid.defRowInfo());
		var context = Ext.apply({sysOpers : wcm.SysOpers, gridInfo : wcm.Grid.info, PageContext : PageContext}, PageContext.getContext());
		var wcmEvent = CMSObj.createEvent(rowInfo, context);
		var context = Ext.applyIf({
			extEvent : this.extEvent,
			event : this.browserEvent,
			targetElement : this.targetElement,
			wcmEvent : wcmEvent
		}, PageContext.getContext());
		$MsgCenter.$main(context).contextmenu();
	},
	moreRows : function(event){//when ctrlkey press.
		var context = Ext.applyIf({
			extEvent : this.extEvent,
			event : this.browserEvent,
			targetElement : this.targetElement,
			wcmEvent : PageContext.event
		}, PageContext.getContext());
		$MsgCenter.$main(context).contextmenu();
	}
};

wcm.GridCell = function(_element, opt){
	var context = (_element)?this.buildContext(_element, opt):null;
	this.objType = WCMConstants.OBJ_TYPE_CELL;
	wcm.GridCell.superclass.constructor.call(this, null, context);
	this.addEvents(['click', 'afterclick']);
};
CMSObj.register(WCMConstants.OBJ_TYPE_CELL, 'wcm.GridCell');
Ext.extend(wcm.GridCell, wcm.CMSObj, {
	buildContext : function(_element, opt){
		var row = wcm.Grid.findRow(_element);
		if(row==null)return null;
		var hasRight = true;
		if(_element.getAttribute('rightIndex', 2)){
			hasRight = wcm.AuthServer.hasRight(context.right, context.getAttribute('rightIndex', 2));
		}
		else{
			hasRight = _element.className.indexOf('cannot_')==-1;
		}
		var rowInfo = wcm.Grid.getRowInfo(row.dom, wcm.Grid.defRowInfo());
		var context = Ext.apply({
			hasRight : hasRight,
			opt : opt,
			cmd : _element.getAttribute('grid_function')
		}, rowInfo);
		return Ext.applyIf(context, PageContext.getContext());
	}
});
$MsgCenter.on({
	sid : 'sys_gridcell',
	objType : WCMConstants.OBJ_TYPE_CELL,
	beforeclick : function(event){
		if(event.from()!=wcm.getMyFlag())return;
		var context = event.getContext();
		if(context==null)return;
		if(!context.cmd)return false;
		if(!wcm.Grid.getFunction(context.cmd))return false;
		if(!context.hasRight){
			return false;
		}
		var cmsobj = CMSObj.createFrom(context);
		var rightName = 'can' + context.cmd.camelize();
		return (cmsobj[rightName])?(cmsobj[rightName])():true;
	},
	afterclick : function(event){
		if(event.from()!=wcm.getMyFlag())return;
		var context = event.getContext();
		var oCmsObjs = CMSObj.createEnumsFrom({
			objType : context.objType
		}, context);
		oCmsObjs.addElement(CMSObj.createFrom(context));
		event.objs = oCmsObjs;
		var fn = wcm.Grid.getFunction(context.cmd);
		if(fn)fn(event);
	}
});
$MsgCenter.on({
	sid : 'sys_allcmsobjs',
	objType : WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
	afterselect : function(event){
		//如果caller存在，表示来自本页面的调用，否则如果是其它页面的调用，则直接退出
		if(event.from()!=wcm.getMyFlag())return;
		if(!arguments.callee.caller)return;
		wcm.Grid.clearEditedItems();
		PageContext.event = event;
		var objs = event.getObjs();
		if(wcm.PageOper)wcm.PageOper.render(event);
	}
});

Ext.ns("wcm.DelayedTask");
wcm.DelayedTask = function(fn, scope, args){
    var id = null, d, t;
    var call = function(){
        var now = new Date().getTime();
        if(now - t >= d){
            clearInterval(id);
            id = null;
            fn.apply(scope, args || []);
        }
    };
    this.delay = function(delay, newFn, newScope, newArgs){
        if(id && delay != d){
            this.cancel();
        }
        d = delay;
        t = new Date().getTime();
        fn = newFn || fn;
        scope = newScope || scope;
        args = newArgs || args;
        if(!id){
            id = setInterval(call, d);
        }
    };
    this.cancel = function(){
        if(id){
            clearInterval(id);
            id = null;
        }
    };
};

if(Ext.isIE6 || Ext.isIE7){
	$MsgCenter.on({
		objType : WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
		afterselect : function(event){
			if(event.from()!=wcm.getMyFlag())return;
			if(!arguments.callee.caller)return;
			var dom = $('wcm_table_grid');
			if(!dom) return;
			Element.addClassName(dom, 'fix-ie6-redraw');
			Element.removeClassName(dom, 'fix-ie6-redraw');
		}
	});
}

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_CURRPAGE,
	afterselect : function(event){
		wcm.Grid.destroy();	
	}
});