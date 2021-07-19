//拖动
wcm.dd.SiteThumbDragDrop = Ext.extend(wcm.dd.BaseDragDrop, {
	getThumbList : function(){
		return (PageContext.getThumbList || Ext.emptyFn)() || window.myThumbList;		
	},
	b4StartDrag : function(event){
		var dom = Event.element(event.browserEvent);
		if(dom.tagName == 'INPUT' && dom.type.toUpperCase() == 'TEXT') return false;
		this.item = this.getThumbList().find(dom);
		return this.item != null;
	},
	startDrag : function(x, y, event){
		var item = this.item;
		this.shadow = item.getDom();
		Element.addClassName(this.shadow, 'dragging');
		this.nextSibling = Element.next(this.shadow);
		this.root = $(this.id);
		this.trashBox = $('trash_box');
		this.sortable = this._isSortable(this.item);
		var dom = document.createElement("div");
		dom.style.position = 'absolute';
		dom.innerHTML = this._getHint(this.item);
		document.body.appendChild(dom);
		this.dragEl = dom;
		wcm.dd.SiteThumbDragDrop.superclass.startDrag.apply(this, arguments);
	},
	_isSortable : function(){
		return true;
	},
	_getHint : function(item){
		return 'text';
	},
	_moveAfter : function(_eCurr,_ePrev){
		if(_ePrev!=null){
			alert(_ePrev.innerHTML);
		}
		else{
			alert('i"m the first.');
		}
	},
	_moveIntoTrashBox : function(item){
		alert('move into trashbox');
		return true;
	},
	inTrashBox : function(x, y){
		return this.trashBox && Position.within(this.trashBox, x, y);
	},
	drag : function(x, y, event){
		wcm.dd.SiteThumbDragDrop.superclass.drag.apply(this, arguments);
		this.dragEl.style.left = (x + 2) + "px";
		this.dragEl.style.top = (y + 2) + "px";
		if(!this.sortable) return;
		if(this.inTrashBox(x, y)){
			Element.addClassName(this.trashBox, 'trashbox_dragging_on');
			return;
		}
		if(this.trashBox){
			Element.removeClassName(this.trashBox, 'trashbox_dragging_on');
		}
		var eThumb = Element.first(this.root);
		while(eThumb){
			var page = Position.page(eThumb);
			var iTop = page[1];
			var iLeft = page[0];
			var iRight = iLeft + eThumb.offsetWidth;
			var iBottom = iTop + eThumb.offsetHeight;
			var iCenter = (iLeft + iRight) / 2;
			if(eThumb!=this.shadow){
				if(y>=iTop&&y<=iBottom){
					if(x>=iLeft&&x<=iCenter){
						eThumb.parentNode.insertBefore(this.shadow, eThumb);
						break;
					}
					else if(x<=iRight&&x>iCenter){
						eThumb.parentNode.insertBefore(this.shadow, eThumb.nextSibling);
						break;
					}
				}				
			}
			eThumb = Element.next(eThumb);
		}
	},
	release : function(){
		if(!this.dragging) return;
		delete this.root;
		delete this.shadow;
		delete this.nextSibling;
		delete this.item;
		delete this.trashBox;
		Element.remove(this.dragEl);
		delete this.dragEl;
		wcm.dd.SiteThumbDragDrop.superclass.release.apply(this, arguments);
	},
	endDrag : function(x, y, event){
		wcm.dd.SiteThumbDragDrop.superclass.endDrag.apply(this, arguments);
		var bMoved = true;
		if(this.inTrashBox(x, y)){
			Element.removeClassName(this.trashBox, 'trashbox_dragging_on');
			bMoved = this._moveIntoTrashBox(this.item);
			if(bMoved){
				//handle by page refresh
				//Element.remove(this.shadow);
			}
		}else{
			if(Element.next(this.shadow) != this.nextSibling){	
				var previous = Element.previous(this.shadow);
				bMoved = this._moveAfter(this.item, this.getThumbList().find(previous));
			}
		}
		Element.removeClassName(this.shadow, 'dragging');
		if(!bMoved){
			this.shadow.parentNode.insertBefore(this.shadow, this.nextSibling);
		}
	}
});

wcm.dd.ChannelThumbDragDrop = Ext.extend(wcm.dd.BaseDragDrop, {
	getThumbList : function(){
		return (PageContext.getThumbList || Ext.emptyFn)() || window.myThumbList;		
	},
	b4StartDrag : function(event){
		var dom = Event.element(event.browserEvent);
		if(dom.tagName == 'INPUT' && dom.type.toUpperCase() == 'TEXT') return false;
		this.item = this.getThumbList().find(dom);
		return this.item != null;
	},
	getWin : function(){
		try{
			return $MsgCenter.getActualTop();
		}catch(error){
			return window;
		}
	},
	startDrag : function(x, y, event){
		this.page = Position.getPageInTop(window.frameElement);
		var item = this.item;
		this.shadow = item.getDom();
		Element.addClassName(this.shadow, 'dragging');
		this.nextSibling = Element.next(this.shadow);
		this.root = $(this.id);
		this.trashBox = $('trash_box');
		var win = this.getWin();
		var dom = win.document.createElement("div");
		dom.style.position = 'absolute';
		dom.innerHTML = this._getHint(this.item);
		win.document.body.appendChild(dom);
		this.dragEl = dom;
		var dom = document.createElement("div");
		dom.style.position = 'absolute';
		dom.className = "drag-line";
		document.body.appendChild(dom);
		this.line = dom;
		wcm.dd.ChannelThumbDragDrop.superclass.startDrag.apply(this, arguments);
	},
	_getHint : function(item){
		return 'text';
	},
	_moveAfter : function(_eCurr,_ePrev){
		if(_ePrev!=null){
			alert(_ePrev.innerHTML);
		}
		else{
			alert('i"m the first.');
		}
	},
	_moveTo : function(_eCurr, _eTarget){
		alert('move to');
	},
	_moveIntoTrashBox : function(item){
		alert('move into trashbox');
		return true;
	},
	inTrashBox : function(x, y){
		return this.trashBox && Position.within(this.trashBox, x, y);
	},
	drag : function(x, y, event){
		wcm.dd.ChannelThumbDragDrop.superclass.drag.apply(this, arguments);
		this.isActive = false;
		this.dragEl.style.left = this.page[0] + (x + 5) + "px";
		this.dragEl.style.top = this.page[1] + (y + 5) + "px";
		if(this.inTrashBox(x, y)){
			Element.addClassName(this.trashBox, 'trashbox_dragging_on');
			return;
		}
		if(this.trashBox){
			Element.removeClassName(this.trashBox, 'trashbox_dragging_on');
		}
		if(this.relateEl){
			Element.removeClassName(this.relateEl, 'dragginghover');
			delete this.relateEl;
		}
		Element.hide(this.line);
		var eThumb = Element.first(this.root);
		while(eThumb){
			var page = Position.page(eThumb);
			var iLeft = page[0];
			var iTop = page[1];
			var iRight = iLeft + eThumb.offsetWidth;
			var iBottom = iTop + eThumb.offsetHeight;
			if(eThumb!=this.shadow){
				if(y>=iTop&&y<=iBottom){
					if(x>=iLeft&&x<=iRight){
						Element.addClassName(eThumb, 'dragginghover');
						this.relateEl = eThumb;
						this.bMoveTo = true;
						this.isActive = true;
						break;
					}
					var previous = Element.previous(eThumb);
					if(previous != this.shadow){
						var iPreRight = 0;
						if(previous){
							var previousPage = Position.page(previous);
							if(previousPage[1] == iTop){
								iPreRight = previousPage[0] + previous.offsetWidth;
							}
						}
						if(x < iLeft && x > iPreRight){
							this.line.style.left = (iPreRight + 2)+"px";
							this.line.style.top = iTop+"px";
							this.line.style.height = eThumb.offsetHeight+"px";
							Element.show(this.line);
							this.relateEl = previous;
							this.bMoveTo = false;
							this.isActive = true;
							break;
						}
					}
					var next = Element.next(eThumb);
					if(next != this.shadow){
						var iNextLeft = iRight+20;//Number.MAX_VALUE;
						if(next){
							var nextPage = Position.page(next);
							if(nextPage[1] == iTop){
								iNextLeft = nextPage[0];
							}
						}
						if(x > iRight && x < iNextLeft){
							this.line.style.left = (iRight + 2)+"px";
							this.line.style.top = iTop+"px";
							this.line.style.height = eThumb.offsetHeight+"px";
							Element.show(this.line);
							this.relateEl = eThumb;
							this.bMoveTo = false;
							this.isActive = true;
							break;
						}
					}
				}							
			}
			eThumb = Element.next(eThumb);
		}
	},
	release : function(){
		if(!this.dragging) return;
		delete this.root;
		Element.removeClassName(this.shadow, 'dragging');
		delete this.shadow;
		delete this.nextSibling;
		delete this.item;
		delete this.trashBox;
		Element.remove(this.line);
		delete this.line;
		Element.remove(this.dragEl);
		delete this.dragEl;
		if(this.relateEl){
			Element.removeClassName(this.relateEl, 'dragginghover');
			delete this.relateEl;
		}
		wcm.dd.ChannelThumbDragDrop.superclass.release.apply(this, arguments);
	},
	endDrag : function(x, y, event){
		wcm.dd.ChannelThumbDragDrop.superclass.endDrag.apply(this, arguments);
		var bMoved = true;
		if(this.inTrashBox(x, y)){
			Element.removeClassName(this.trashBox, 'trashbox_dragging_on');
			bMoved = this._moveIntoTrashBox(this.item);
			if(bMoved){
				//handle by page refresh
				//Element.remove(this.shadow);
			}
		}else{
			if(this.isActive){
				var sMethod = this.bMoveTo ? '_moveTo' : '_moveAfter';
				bMoved = this[sMethod](this.item, this.getThumbList().find(this.relateEl));
			}
		}
		if(this.relateEl && this.bMoveTo){
			Element.removeClassName(this.relateEl, 'dragginghover');
		}
		if(!bMoved){
			this.shadow.parentNode.insertBefore(this.shadow, this.nextSibling);
		}
	}
});

wcm.dd.AccrossFrameDragDrop = function(dd){
	dd.addListener('startdrag', this.init, this);
	dd.addListener('enddrag', this.destroy, this);
};

Ext.apply(wcm.dd.AccrossFrameDragDrop.prototype, {
	getWinInfos0 : function(){
		if(!this.winInfos){
			this.winInfos = this.getWinInfos();
		}
		return this.winInfos;
	},
	getWinInfos : function(){
		return [];
	},
	getWin : function(winInfo){
		return winInfo.document ? winInfo : winInfo.win;
	},
	init : function(){
		this.dd = arguments[3];
		var winInfos = this.getWinInfos0();
		for (var i = 0; i < winInfos.length; i++){
			this.addDragWin(winInfos[i]);
		}
	},
	addDragWin : function(winInfo){
		var win = this.getWin(winInfo);
		var doc = win.document;
		win.Ext.EventManager.on(doc, 'mouseover', this.onmouseover, this, winInfo);
		win.Ext.EventManager.on(doc, 'mouseout', this.onmouseout, this, winInfo);
	},
	enterMe : function(event, target, opt){},
	startDrag : function(event, target, opt){},
	onmouseover : function(event, target, opt){
		if(!this.dd || !this.dd.dragging) return;
		if(!opt['-accrossdraginfo-']){
			var dragInfo = opt['-accrossdraginfo-'] = {};
			var win = this.getWin(opt);
			var doc = win.document;		
			win.Ext.EventManager.on(doc, 'mousemove', this.onmousemove, this, opt);
			win.Ext.EventManager.on(doc, 'mouseup', this.onmouseup, this, opt);
			var box = doc.documentElement || doc.body;
			dragInfo['page'] = Position.getPageInTop(box);
			(opt['startDrag'] || this.startDrag).apply(this ,arguments);
		}
		(opt['enterMe'] || this.enterMe).apply(this ,arguments);
	},
	leaveMe : function(event, target, opt){},
	onmouseout : function(event, target, opt){
		if(!this.dd || !this.dd.dragging) return;
		(opt['leaveMe'] || this.leaveMe).apply(this ,arguments);
	},
	onmousemove : function(event, target, opt){
		(opt['drag'] || this.drag).apply(this, arguments);
	},	
	drag : function(event, target, opt){
		var dragInfo = opt['-accrossdraginfo-'];
		var page = dragInfo['page'];
		var xy = this.dd.getXY(event);
		this.dd.dragEl.style.left = page[0] + (xy[0] + 5) + "px";
		this.dd.dragEl.style.top = page[1] + (xy[1] + 5) + "px";
	},
	destroy : function(){
		delete this.dd;
		var winInfos = this.getWinInfos0();
		for (var i = 0; i < winInfos.length; i++){
			this.removeDragWin(winInfos[i]);
		}
		delete this.winInfos;
	},
	removeDragWin : function(winInfo){
		var win = this.getWin(winInfo);
		var doc = win.document;
		win.Ext.EventManager.un(doc, 'mouseover', this.onmouseover, this);
		win.Ext.EventManager.un(doc, 'mouseout', this.onmouseout, this);
		if(!winInfo['-accrossdraginfo-']) return;
		winInfo['-accrossdraginfo-'] = null; 
		win.Ext.EventManager.un(doc, 'mousemove', this.onmousemove, this);
		win.Ext.EventManager.un(doc, 'mouseup', this.onmouseup, this);
	},
	endDrag : function(event, target, opt){},
	onmouseup : function(event, target, opt){
		(opt['endDrag'] || this.endDrag).apply(this, arguments);
		var dd = this.dd;
		this.destroy();
		dd.dispose();
	}
});

wcm.dd.GridDragDrop = Ext.extend(wcm.dd.BaseDragDrop, {
	b4StartDrag : function(event){
		var dom = Event.element(event.browserEvent);
		var row = wcm.Grid.findRow(dom);
		this.row = row;
		if(!row) return false;
		if(Ext.isIE){
			return row != null;
		}
		while(dom && row.dom != dom){
			//fix the link element in not ie browser;
			if(dom.tagName == 'A' || dom.tagName == "BODY") return false;
			dom = dom.parentNode;
		}
		return true;
	},
	getWin : function(){
		try{
			return $MsgCenter.getActualTop();
		}catch(error){
			return window;
		}
	},
	startDrag : function(x, y, event){
		this.page = Position.getPageInTop(window.frameElement);
		var row =this.row;
		this.shadow = row.dom;
		Element.addClassName(this.shadow, 'dragging');
		this.nextSibling = Element.next(this.shadow);
		this.root = $(this.rootId || this.id);
		var win = this.getWin();
		var dom = win.document.createElement("div");
		dom.className = "grid_row_hint";
		this.sortable = this._isSortable(this.row);
		dom.innerHTML = this._getHint(this.row);
		win.document.body.appendChild(dom);
		this.dragEl = dom;
		wcm.dd.GridDragDrop.superclass.startDrag.apply(this, arguments);
	},
	_isSortable : function(){
		return true;
	},
	_getHint : function(row){
		return 'text';
	},
	_move : function(srcRow, iPosition, dstRow, eTargetMore){
		//iPosition表示当前元素相对于目标元素的位置,1表示之前,0表示之后
	},
	drag : function(x, y, event){
		wcm.dd.GridDragDrop.superclass.drag.apply(this, arguments);
		this.dragEl.style.left = this.page[0] + (x + 5) + "px";
		this.dragEl.style.top = this.page[1] + (y + 5) + "px";
		if(!this.sortable) return;
		var eRow = Element.first(this.root);
		while(eRow){
			var offset = Position.page(eRow);//Position.cumulativeOffset(eRow);
			var iTop = parseInt(offset[1],10);
			var iLeft = parseInt(offset[0],10);
			var iRight = iLeft + eRow.offsetWidth;
			var iBottom = iTop + eRow.offsetHeight;
			var iCenter = (iTop + iBottom) / 2;
			if(eRow!=this.shadow){
				if(x>=iLeft&&x<=iRight){
					if(y>=iTop&&y<=iCenter){
						eRow.parentNode.insertBefore(this.shadow, eRow);
						break;
					}
					else if(y<=iBottom&&y>iCenter){
						eRow.parentNode.insertBefore(this.shadow, eRow.nextSibling);
						break;
					}
				}				
			}
			eRow = Element.next(eRow);
		}
	},
	release : function(){
		if(!this.dragging) return;
		delete this.page;
		delete this.root;
		delete this.row;
		Element.removeClassName(this.shadow, 'dragging');
		delete this.shadow;
		Element.remove(this.dragEl);
		delete this.dragEl;
		delete this.nextSibling;
		wcm.dd.GridDragDrop.superclass.release.apply(this, arguments);
	},
	endDrag : function(x, y, event){
		wcm.dd.GridDragDrop.superclass.endDrag.apply(this, arguments);
		var bMoved = true;
		if(Element.next(this.shadow) != this.nextSibling){	
			var previous = Element.previous(this.shadow);
			if(previous == null || previous.tagName != this.shadow.tagName){
				var next = Element.next(this.shadow);
				var nextRow = next ? wcm.Grid.findRow(next) : null;
				bMoved = this._move(this.row, 1, nextRow);
			}
			else{
				var previous = Element.previous(this.shadow);
				var previousRow = previous ? wcm.Grid.findRow(previous) : null;
				var next = Element.next(this.shadow);
				var nextRow = next ? wcm.Grid.findRow(next) : null;
				bMoved = this._move(this.row, 0, previousRow, nextRow);
			}
			if(!bMoved){
				this.shadow.parentNode.insertBefore(this.shadow, this.nextSibling);
			}
		}
	}
});