$package("com.trs.wcm");

var STYLE_BGCOLOR_ACTIVE = 'buttonface';
com.trs.wcm.iDrag = {
	dragged : null,
	detect:function(event){
		if(com.trs.wcm.iDrag.dragged){
			return false;
		}
		var eRoot = this;
		var cnt = 0;
		var oDragger = eRoot.Dragger;
		if(oDragger.disabled || eRoot.getAttribute('draggable',2)==='false')return;
		com.trs.wcm.iDrag.dragged = eRoot;
		oDragger.init = false;
		event = window.event || event;
		oDragger.lastMouseX = Event.pointerX(event);
		oDragger.lastMouseY = Event.pointerY(event);
		Event.observe(document,'mousemove',com.trs.wcm.iDrag.drag);
		Event.observe(document,'mouseup',com.trs.wcm.iDrag.end);
	},
	start : function(event){
		if(_IE){
			document.body.setCapture(false);
		}
		var eRoot = com.trs.wcm.iDrag.dragged;
		var oDragger = eRoot.Dragger;
		var eDelegate = (oDragger.delegate||Prototype.K).apply(oDragger,[eRoot,event]);
		oDragger.handler = eDelegate;
		Position.prepare();
		event = window.event || event;
		if(oDragger.handler.style.position!='absolute'){
			Position.absolutize(oDragger.handler);
		}
		var y = parseInt(oDragger.handler.style.top);
		var x = parseInt(oDragger.handler.style.left);
		(oDragger.onDragStart||Prototype.emptyFunction).call(oDragger, x, y,[oDragger.lastMouseX,oDragger.lastMouseY],event);
		Event.stop(event);
		oDragger.init = true;
	},
	drag : function(event){
		if(com.trs.wcm.iDrag.dragged==null){
			return false;
		}
		var eRoot = com.trs.wcm.iDrag.dragged;
		var oDragger = eRoot.Dragger;
		event = (oDragger.lastCaptureWindow||window).event || event;
		var ex = Event.pointerX(event);
		var ey = Event.pointerY(event);
		if (oDragger.init == false) {
			if(Math.abs(ex-oDragger.lastMouseX)>=2||Math.abs(ey-oDragger.lastMouseY)>=2){
				com.trs.wcm.iDrag.start(event);
			}
			else{
				return false;
			}
		}
		var y = parseInt(oDragger.handler.style.top);
		var x = parseInt(oDragger.handler.style.left);
		if(oDragger.arrOffset){
			var nx 	= oDragger.arrOffset[0] + ex + 5;
			var ny 	= oDragger.arrOffset[1] + ey + 5;
		}
		else{
			var nx 	= x + ex - oDragger.lastMouseX;
			var ny 	= y + ey - oDragger.lastMouseY;
		}
		oDragger.handler.style["left"] = nx + "px";
		oDragger.handler.style["top"] = ny + "px";
		oDragger.lastMouseX		= ex;
		oDragger.lastMouseY		= ey;
		(oDragger.onDrag||Prototype.emptyFunction).call(oDragger, nx, ny, [ex,ey], event);
		return false;
	},
	end : function(event){
		if(_IE){
			document.body.releaseCapture();
		}
		Event.stopObserving(document,'mousemove',com.trs.wcm.iDrag.drag);
		Event.stopObserving(document,'mouseup',com.trs.wcm.iDrag.end);
		if(com.trs.wcm.iDrag.dragged==null){
			return false;
		}
		var eRoot = com.trs.wcm.iDrag.dragged;
		var oDragger = eRoot.Dragger;
		com.trs.wcm.iDrag.dragged = null;
		if(oDragger.init==false){
			return false;
		}
		oDragger.init = false;
		event = window.event || event;
		var ex = Event.pointerX(event);
		var ey = Event.pointerY(event);
		(oDragger.onDragEnd||Prototype.emptyFunction).call(oDragger,
			parseInt(oDragger.handler.style["left"]),
			parseInt(oDragger.handler.style["top"]),
			[ex,ey], event);
		if(oDragger.handler != eRoot){
			$removeNode(oDragger.handler);
		}
		oDragger.handler = null;
		return false;
	}
}
com.trs.wcm.SimpleDragger = Class.create('wcm.SimpleDragger');
com.trs.wcm._Draggers = [];
com.trs.wcm.SimpleDragger.prototype={
	initialize : function(_eRoot){
		_eRoot.Dragger = this;
		this.root = _eRoot;
		_eRoot.onmousedown = com.trs.wcm.iDrag.detect;
		com.trs.wcm._Draggers.push(this);
	},
	destroy:function(_bUnion){
		if(this.root){
			this.root.Dragger = null;
			this.root.onmousedown = null;
			this.root = null;
		}
		if(!_bUnion&&com.trs.wcm._Draggers){
			com.trs.wcm._Draggers.without(this);
		}
	},
	delegate : function(_eRoot,event){
		if(com.trs.wcm.iDrag.delegate){
			com.trs.wcm.iDrag.delegate.call(this,_eRoot,event);
		}
		else{
			return _eRoot;
		}
	}
}
Event.observe(window,'unload',function(){
	if(com.trs.wcm._Draggers){
		for(var i=0,n=com.trs.wcm._Draggers.length;i<n;i++){
			var oDragger = com.trs.wcm._Draggers[i];
			if(oDragger && oDragger.destroy){
				oDragger.destroy(true);
			}
		}
	}
	com.trs.wcm._Draggers = null;
});
com.trs.wcm.AccrossFrameDragger = Class.create('wcm.AccrossFrameDragger');
com.trs.wcm.AccrossFrameDragger.prototype = Object.extend({},com.trs.wcm.SimpleDragger.prototype);
Object.extend(com.trs.wcm.AccrossFrameDragger.prototype,{
	_isEnableAccross : function(){
		var treeWindow = top.$('nav_tree').contentWindow;
		return !treeWindow.isAdvancedSearchShow();
	},
	stopObserving : function(){
		if(_IE){
			document.body.releaseCapture();
		}
		Event.stopObserving(document,'mousemove',com.trs.wcm.iDrag.drag);
		Event.stopObserving(document,'mouseup',com.trs.wcm.iDrag.end);
		Event.stopObserving(document,'mouseup',com.trs.wcm.AccrossFrameDragger.prototype.endInSelf);	
		var treeWindow = top.$('nav_tree').contentWindow;
		if(_IE){
			treeWindow.document.body.releaseCapture();
		}
		Event.stopObserving(treeWindow.document,'mousemove',com.trs.wcm.iDrag.drag);
		Event.stopObserving(treeWindow.document,'mouseup',
			com.trs.wcm.AccrossFrameDragger.prototype.endInTree);
	},
	endInSelf : function(event){
		com.trs.wcm.AccrossFrameDragger.prototype.stopObserving();
		if(com.trs.wcm.iDrag.dragged==null){
			return false;
		}
		var eRoot = com.trs.wcm.iDrag.dragged;
		var oDragger = eRoot.Dragger;
		com.trs.wcm.iDrag.dragged = null;
		if(oDragger.init==false){
			return false;
		}
		oDragger.init = false;
		event = window.event || event;
		var ex = Event.pointerX(event);
		var ey = Event.pointerY(event);
		(oDragger._onDragEnd||Prototype.emptyFunction).call(oDragger,
			parseInt(oDragger.handler.style["left"]),
			parseInt(oDragger.handler.style["top"]),
			[ex,ey], event);
		if(oDragger.handler != eRoot){
			$removeNode(oDragger.handler);
		}
		oDragger.handler = null;
		top.DragAcross = null;
		return false;
	},
	endInTree : function(event){
		com.trs.wcm.AccrossFrameDragger.prototype.stopObserving();
		if(com.trs.wcm.iDrag.dragged==null){
			return false;
		}
		var eRoot = com.trs.wcm.iDrag.dragged;
		var oDragger = eRoot.Dragger;
		com.trs.wcm.iDrag.dragged = null;
		if(oDragger.init==false){
			return false;
		}
		oDragger.init = false;
		(oDragger.onDrop||Prototype.emptyFunction).call(oDragger);
		if(oDragger.handler != eRoot){
			$removeNode(oDragger.handler);
		}
		oDragger.handler = null;
		top.DragAcross = null;
		return false;
	},
	onDragStart : function(nx,ny,_pXY,_event){
		var arrOffset = Position.cumulativeOffset(window.frameElement);
		this.handler.style.left = arrOffset[0] + _pXY[0] + 5;
		this.handler.style.top = arrOffset[1] + _pXY[1] + 5;
		this.arrOffset = arrOffset;
		var arrDim = Element.getDimensions(window.frameElement);
		this.region = [arrOffset[0],arrOffset[1],arrOffset[0]+arrDim["width"],arrOffset[1]+arrDim["height"]];
		arrOffset = Position.cumulativeOffset(top.$('nav_tree'));
		arrDim = Element.getDimensions(top.$('nav_tree'));
		this.treeRegion = [arrOffset[0],arrOffset[1],arrOffset[0]+arrDim["width"],arrOffset[1]+arrDim["height"]];
		this.lastCaptureWindow = window;
		var treeWindow = this.treeWindow = top.$('nav_tree').contentWindow;
		if(this._isEnableAccross()){
			Event.observe(treeWindow.document,'mousemove',com.trs.wcm.iDrag.drag);
			Event.observe(treeWindow.document,'mouseup',
				com.trs.wcm.AccrossFrameDragger.prototype.endInTree);
		}
		Event.stopObserving(document,'mouseup',com.trs.wcm.iDrag.end);
		Event.observe(document,'mouseup',
			com.trs.wcm.AccrossFrameDragger.prototype.endInSelf);
	},
	onDrag : function(nx,ny,_pXY,_event){
		var treeWindow = this.treeWindow;
		if(!this._isEnableAccross()){
			if(nx>=this.region[0]&&nx<=this.region[2]&&ny>=this.region[1]&&ny<=this.region[3]){
				(this._onDrag||Prototype.emptyFunction).call(this,nx, ny, _pXY, _event);
			}
			return;
		}
		if(nx>=this.treeRegion[0]&&nx<=this.treeRegion[2]&&ny>=this.treeRegion[1]&&ny<=this.treeRegion[3]){
			if(this.lastCaptureWindow == window){
				if(_IE){
					document.body.releaseCapture();
				}
				Event.stopObserving(document,'mousemove',com.trs.wcm.iDrag.drag);
				this.arrOffset = this.treeRegion;
				this.oWindow = treeWindow;
				Event.observe(treeWindow.document,'mousemove',com.trs.wcm.iDrag.drag);
				if(_IE){
					treeWindow.document.body.setCapture(false);
				}
				this.lastCaptureWindow = treeWindow;
			}
			(this._onDragInTree||Prototype.emptyFunction).call(this,nx, ny, _pXY, _event);
		}
		else{
			if(this.lastCaptureWindow != window){
				if(_IE){
					this.lastCaptureWindow.document.body.releaseCapture();
				}
				Event.stopObserving(this.lastCaptureWindow.document,'mousemove',
					com.trs.wcm.iDrag.drag);
				this.arrOffset = this.region;
				Event.observe(document,'mousemove',com.trs.wcm.iDrag.drag);
				if(_IE){
					document.body.setCapture(false);
				}
				this.lastCaptureWindow = window;
			}
			if(nx>=this.region[0]&&nx<=this.region[2]&&ny>=this.region[1]&&ny<=this.region[3]){
				(this._onDrag||Prototype.emptyFunction).call(this,nx, ny, _pXY, _event);
			}
		}
	}
});
com.trs.wcm.ListDragger = Class.create('wcm.ListDragger');
com.trs.wcm.ListDragger.prototype = Object.extend({},com.trs.wcm.AccrossFrameDragger.prototype);
Object.extend(com.trs.wcm.ListDragger.prototype,{
	delegate : function(_eRoot){
		this.shadow = _eRoot;
		this.rows = (window.Grid&&Grid.getAllRows)?Grid.getAllRows():document.getElementsByClassName("grid_row",$(this.daton));
//		this.rows = document.getElementsByClassName("grid_row",$(this.daton));
		var eHandler = top.document.createElement("DIV");
//		eHandler.style.display = 'none';
		this.sortable = this._isSortable(_eRoot);
		eHandler.className = "grid_row_hint";
		this.hint = eHandler.innerHTML = this._getHint(_eRoot);
		this.hintInTree = this._getSelectedHint(_eRoot);
		top.document.body.appendChild(eHandler);
		this._handler = eHandler;
		this.oldBgColor = this.shadow.style.backgroundColor;
		Element.addClassName(this.shadow,"grid_row_dragging");
		this.lastNextSibling = _eRoot.nextSibling;
		return eHandler;
	},
	_getHint : function(_eRoot){
		return 'test';
	},
	_isSortable : function(_eRoot){
		return true;
	},
	_getSelectedHint : function(_eRoot){
		return 'test';
	},
/*
	onDragStart : function(nx,ny,_pXY,_event){
		this._handler.style.left = _pXY[0] + 2;
		this._handler.style.top = _pXY[1] + 2;
	},
*/
	_onDragInTree : function(){
		this._handler.innerHTML = this.hintInTree;
	},
	_onDrag : function(nx,ny,_pXY,_event){
		this._handler.innerHTML = this.hint;
		if(!this.sortable){
			return;
		}
		for(var i=0;i<this.rows.length;i++){
			var eRow = this.rows[i];
			var offset = Position.cumulativeOffset(eRow);
			var rOffset = Position.realOffset(eRow);
			var iTop = offset[1]-rOffset[1]+Position.deltaY;
			var iLeft = offset[0]-rOffset[0]+Position.deltaX;
			var iRight = iLeft+eRow.offsetWidth;
			var iBottom = iTop+eRow.offsetHeight;
			var iCenter = (iTop+iBottom)/2;
			if(eRow!=this.root){
				if(_pXY[0]>=iLeft&&_pXY[0]<=iRight){
					if(_pXY[1]>=iTop&&_pXY[1]<=iCenter){
						eRow.parentNode.insertBefore(this.shadow,eRow);
						break;
					}
					else if(_pXY[1]<=iBottom&&_pXY[1]>iCenter){
						eRow.parentNode.insertBefore(this.shadow,eRow.nextSibling);
						break;
					}
				}
			}
		}
	},
	_move : function(_eCurr,_iPosition,_eTarget,_eTargetMore){
		//_iPosition表示当前元素相对于目标元素的位置,1表示之前,0表示之后
		//alert(_eCurr.innerHTML)
	},
	_onDragEnd : function(nx,ny){
		Element.removeClassName(this.shadow,"grid_row_dragging");
		$removeNode(this._handler);
		if(this.sortable){
			var needMove = false;
			if(this.lastNextSibling!=this.root.nextSibling){
				// liuyou, ls@2007-9-28 09:36 增加tagName是否相符的判断
				if(this.root.previousSibling==null || this.root.previousSibling.tagName != this.root.tagName){
					needMove = this._move(this.root,1,this.root.nextSibling);
				}
				else{
					needMove = this._move(this.root,0,this.root.previousSibling,this.root.nextSibling);
				}
			}
			if(needMove===false){
				this.root.parentNode.insertBefore(this.root,this.lastNextSibling);
			}
		}
		this.lastNextSibling = null;
		this.rows = null;
		this._handler = null;
		this.shadow = null;
	},
	onDrop : function(){
		Element.removeClassName(this.shadow,"grid_row_dragging");
		$removeNode(this._handler);
		if(this.sortable){
			var needMove = false;
			if(needMove===false){
				this.root.parentNode.insertBefore(this.root,this.lastNextSibling);
			}
		}
		this.lastNextSibling = null;
		this.rows = null;
		this._handler = null;
		this.shadow = null;
		this.sortable = false;
		(this._onDrop||Prototype.emptyFunction).call(this);
	}
});
//TODO
com.trs.wcm.ThumbDragger = Class.create('wcm.ThumbDragger');
com.trs.wcm.ThumbDragger.prototype = Object.extend({},com.trs.wcm.AccrossFrameDragger.prototype);
Object.extend(com.trs.wcm.ThumbDragger.prototype,{
	delegate : function(_eRoot){
//		this.shadow = _eRoot;
		this.thumbs = $(this.daton).childNodes;//document.getElementsByClassName("tabular",this.daton);
		this.trashbox = $('trash_box');
		this.elements = [];
		for(var i=0;i<this.thumbs.length;i++){
			var eThumb = this.thumbs[i];
			if(eThumb.nodeType != 1)continue;
			var objectId = eThumb.getAttribute("_chnlId");
			if(eThumb==this.root){
				//this.currCenter = document.getElementsByClassName('tabular_center',eThumb)[0];
				this.currCenter = $('tabular_center_'+objectId);//document.getElementsByClassName('tabular_center',eThumb)[0];
				continue;
			}
			var eThumbLeft = $('tabular_left_'+objectId);//document.getElementsByClassName('tabular_left',eThumb)[0];
			var eThumbCenter = $('tabular_center_'+objectId);//document.getElementsByClassName('tabular_center',eThumb)[0];
			var eThumbRight = $('tabular_right_'+objectId);//document.getElementsByClassName('tabular_right',eThumb)[0];
			if(eThumb.previousSibling!=this.root){
				this.elements.push({p:"left",el:eThumbLeft});
			}
			
			this.elements.push({p:"center",el:eThumbCenter});
			if(eThumb.nextSibling!=this.root){
				this.elements.push({p:"right",el:eThumbRight});
			}
		}
		this.elements.push({p:"center",el:this.trashbox});
//		this.daton = null;
		var eHandler = top.document.createElement("DIV");
		eHandler.innerHTML = this._getHint(_eRoot);
		top.document.body.appendChild(eHandler);
		this._handler = eHandler;
//		this.shadow.style.display = 'none';
//		this.shadow.style.height = '5px';
		this.oldBgColor = this.currCenter.style.backgroundColor;
		this.currCenter.style.backgroundColor = STYLE_BGCOLOR_ACTIVE;
		this.lastNextSibling = _eRoot.nextSibling;
		return eHandler;
	},
	_getHint : function(_eRoot){
		return 'test';
	},
	_moveInto : function(_eCurr,_eTarget){
		//alert(_eTarget.innerHTML);
	},
	_moveAfter : function(_eCurr,_eNext){
		//alert(_eCurr.innerHTML)
	},
/*
	onDragStart : function(nx,ny,_pXY,_event){
		var arrOffset = Position.cumulativeOffset(window.frameElement);
		this._handler.style.left = arrOffset[0] + _pXY[0] + 5;
		this._handler.style.top = arrOffset[1] + _pXY[1] + 5;
		this.arrOffset = arrOffset;
		var arrDim = Element.getDimensions(window.frameElement);
		this.region = [arrOffset[0],arrOffset[1],arrOffset[0]+arrDim["width"],arrOffset[1]+arrDim["height"]];
		arrOffset = Position.cumulativeOffset(top.$('nav_tree'));
		arrDim = Element.getDimensions(top.$('nav_tree'));
		this.treeRegion = [arrOffset[0],arrOffset[1],arrOffset[0]+arrDim["width"],arrOffset[1]+arrDim["height"]];
		this.lastCaptureWindow = window;
		var treeWindow = top.$('nav_tree').contentWindow;
		Event.observe(treeWindow.document,'mousemove',this.document_mousemove);
		Event.observe(treeWindow.document,'mouseup',this.document_mouseup);
//		Grid.handleRowClick.call(this.root,window.event);
	//		this.shadow.style.display = '';
	},
*/
	_onDrag : function(nx,ny,_pXY,_event){
		var maxCount = this.elements.length;
		var hasMoved = false;
		for(var i=0;i<maxCount;i++){
			var element = this.elements[i]["el"];
			var pos = this.elements[i]["p"];
			var sHoverClass = 'tabular_hover';
			if(pos=='center'){
				sHoverClass = 'tabular_center_hover';
			}
			if(this.lastHovers && this.trashbox != this.lastHovers[0]){
				this.trashbox.style.border = '0';
			}
			if(element&&Position.withinIncludingScrolloffsets(element,_pXY[0],_pXY[1])){
				if(!this.lastHovers||!this.lastHovers.include(element)){
					for(var j=0;this.lastHovers&&j<this.lastHovers.length;j+=3){
						if(this.trashbox != this.lastHovers[j]){
							Element.removeClassName(this.lastHovers[j],this.lastHovers[j+1]);
						}
					}
					if(this.trashbox != element){
						Element.addClassName(element,sHoverClass);
					}else{
						this.trashbox.style.border = '1px solid #B4B4B4';
					}
					this.lastHovers = [];
					this.lastHovers.push(element,sHoverClass,pos);
					if(pos=="left"&&i!=0){
						var pre = this.elements[i-1]["el"];
						var offset = Position.cumulativeOffset(pre);
						var rOffset = Position.realOffset(pre);
						var iTop = offset[1]-rOffset[1]+Position.deltaY;
						var iBottom = iTop+pre.offsetHeight;
						if(_pXY[1]<=iBottom&&_pXY[1]>=iTop){
							Element.addClassName(pre,sHoverClass);
							this.lastHovers.push(pre,sHoverClass,"right");
						}
					}
					else if(pos=="right"&&i!=maxCount-1){
						var next = this.elements[i+1]["el"];
						var offset = Position.cumulativeOffset(next);
						var rOffset = Position.realOffset(next);
						var iTop = offset[1]-rOffset[1]+Position.deltaY;
						var iBottom = iTop+next.offsetHeight;
						if(_pXY[1]<=iBottom&&_pXY[1]>=iTop){
							Element.addClassName(next,sHoverClass);
							this.lastHovers.push(next,sHoverClass,"left");
						}
					}
				}
				hasMoved = true;
				break;
			}
		}
		if(!hasMoved){
			for(var j=0;this.lastHovers&&j<this.lastHovers.length;j+=3){
				Element.removeClassName(this.lastHovers[j],this.lastHovers[j+1]);
			}
			this.lastHovers = [];
		}
	},
	_onDragEnd : function(nx,ny){
//		this.shadow.parentNode.insertBefore(this.root,this.shadow);
//		this.root.style.position = 'static';
//		this.root.style.cssFloat = 'left';
//		$removeNode(this.shadow);
		//TODO
		
		for(var j=0;this.lastHovers&&j<this.lastHovers.length;j+=3){
			if(this.trashbox != this.lastHovers[j]){
				Element.removeClassName(this.lastHovers[j],this.lastHovers[j+1]);
			}
		}
		if(this.lastHovers&&this.lastHovers[2]=='center'){
			this._moveInto.call(this,this.root,this.lastHovers[0].parentNode, this.root.previousSibling);
		}
		else if(this.lastHovers&&this.lastHovers[2]=='left'){
			if(this._checkWhenChangeOrder == null || this._checkWhenChangeOrder(this.root, this.lastHovers[0].parentNode) != false) {
				this.root.parentNode.insertBefore(this.root,this.lastHovers[0].parentNode);
				this._moveAfter.call(this,this.root,this.root.previousSibling, this.lastHovers[1]);
			}

		}
		else if(this.lastHovers&&this.lastHovers[2]=='right'){
			if(this._checkWhenChangeOrder == null || this._checkWhenChangeOrder(this.root, this.lastHovers[0].parentNode.nextSibling) != false) {
				this.root.parentNode.insertBefore(this.root,this.lastHovers[0].parentNode.nextSibling);
				this._moveAfter.call(this,this.root,this.root.previousSibling, this.lastHovers[1]);
			}
		}
		this.lastHovers = [];
		this.currCenter.style.backgroundColor = this.oldBgColor;
		delete this.elements;
		$removeNode(this._handler);
		this._handler = null;
		this.shadow = null;
		this.lastNextSibling = null;
	},
	onDrop : function(){
		for(var j=0;this.lastHovers&&j<this.lastHovers.length;j+=3){
			if(this.trashbox != this.lastHovers[j]){
				Element.removeClassName(this.lastHovers[j],this.lastHovers[j+1]);
			}
		}
		this.lastHovers = [];
		this.currCenter.style.backgroundColor = this.oldBgColor;
		delete this.elements;
		$removeNode(this._handler);
		this._handler = null;
		this.shadow = null;
		(this._onDrop||Prototype.emptyFunction).call(this);
	}
});
com.trs.wcm.SiteThumbDragger = Class.create('wcm.SiteThumbDragger');
com.trs.wcm.SiteThumbDragger.prototype = Object.extend({},com.trs.wcm.SimpleDragger.prototype);
Object.extend(com.trs.wcm.SiteThumbDragger.prototype,{
	delegate : function(_eRoot){
		this.shadow = _eRoot;
		this.thumbs = $(this.daton).childNodes;//document.getElementsByClassName("tabular",$(this.daton));
		this.trashBox = $('trash_box');
		var eHandler = document.createElement("DIV");
		eHandler.className = "list_tr_hint";
		eHandler.innerHTML = this._getHint(_eRoot);
		document.body.appendChild(eHandler);
		this._handler = eHandler;
//		this.shadow.style.display = 'none';
//		this.shadow.style.height = '5px';
		this.oldBgColor = this.shadow.style.backgroundColor;
		this.lastNextSibling = _eRoot.nextSibling;
		this.shadow.style.backgroundColor = STYLE_BGCOLOR_ACTIVE;
		return eHandler;
	},
	_getHint : function(_eRoot){
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
	_moveIntoTrashBox : function(_eCurr){
		alert('move into trashbox');
		return true;
	},
	onDragStart : function(nx,ny,_pXY,_event){
		this._handler.style.left = _pXY[0] + 2;
		this._handler.style.top = _pXY[1] + 2;
//		Grid.handleRowClick.call(this.root,window.event);
	//		this.shadow.style.display = '';
	},
	onDrag : function(nx,ny,_pXY,_event){
		var isInTrash = Position.within(this.trashBox,_pXY[0],_pXY[1]);
		if(isInTrash){
			this.trashBox.style.border = '1px solid #B4B4B4';
			return;
		}else{
			this.trashBox.style.border = '0';
		}
		for(var i=0;i<this.thumbs.length;i++){
			var eThumb = this.thumbs[i];
			var offset = Position.cumulativeOffset(eThumb);
			var rOffset = Position.realOffset(eThumb);
			var iTop = offset[1]-rOffset[1]+Position.deltaY;
			var iLeft = offset[0]-rOffset[0]+Position.deltaX;
			var iRight = iLeft+eThumb.offsetWidth;
			var iBottom = iTop+eThumb.offsetHeight;
			var iCenter = (iLeft+iRight)/2;
			if(eThumb!=this.root){
				if(_pXY[1]>=iTop&&_pXY[1]<=iBottom){
					if(_pXY[0]>=iLeft&&_pXY[0]<=iCenter){
						eThumb.parentNode.insertBefore(this.shadow,eThumb);
						this.moved = true;
						break;
					}
					else if(_pXY[0]<=iRight&&_pXY[0]>iCenter){
						eThumb.parentNode.insertBefore(this.shadow,eThumb.nextSibling);
						this.moved = true;
						break;
					}
				}				
			}
		}
	},
	onDragEnd : function(nx,ny,_pXY,_event){
//		this.shadow.parentNode.insertBefore(this.root,this.shadow);
//		this.root.style.position = 'static';
//		this.root.style.cssFloat = 'left';
//		$removeNode(this.shadow);
		var isInTrash = Position.within(this.trashBox,_pXY[0],_pXY[1]);
		var bNeedMove = false;
		if(isInTrash){
			if(this._moveIntoTrashBox(this.shadow)){
				$removeNode(this.shadow);
				bNeedMove = true;
			}
			this.trashBox.style.border = '0';
		}
		else{
			if(this.moved){
				this.moved = false;
				if(this.shadow.nextSibling!=this.lastNextSibling&&this._moveAfter(this.shadow,this.shadow.previousSibling)){
					bNeedMove = true;
				}
			}
		}
		this.shadow.style.backgroundColor = this.oldBgColor;
		if(!bNeedMove){
			this.root.parentNode.insertBefore(this.root,this.lastNextSibling);
		}
		$removeNode(this._handler);
		this.lastNextSibling = null;
		this._handler = null;
		this.shadow = null;
	}
});

com.trs.wcm.TBodyDragger = Class.create('wcm.TBodyDragger');
com.trs.wcm.TBodyDragger.prototype = Object.extend({},com.trs.wcm.SimpleDragger.prototype);
Object.extend(com.trs.wcm.TBodyDragger.prototype,{
	delegate : function(_eRoot){
		this.shadow = _eRoot;
		var eBody = $(this.tbodyId);
		this.rows = eBody.rows;
		var eHandler = document.createElement("SPAN");
		eHandler.innerHTML = this._getHint(_eRoot);
		document.body.appendChild(eHandler);
		this._handler = eHandler;
		Element.addClassName(this.shadow,"tbody_dragging");
		this.lastNextSibling = _eRoot.nextSibling;
		return eHandler;
	},
	_getHint : function(_eRoot){
		return '<div class="tbody_drag_icon"></div>';
	},
	onDragStart : function(nx,ny,_pXY,_event){
		this._handler.style.left = _pXY[0] - 5;
		this._handler.style.top = _pXY[1] - 5;
	},
	onDrag : function(nx,ny,_pXY,_event){
		for(var i=0;i<this.rows.length;i++){
			var eRow = this.rows[i];
			var offset = Position.cumulativeOffset(eRow);
			var rOffset = Position.realOffset(eRow);
			var iTop = offset[1]-rOffset[1]+Position.deltaY;
			var iLeft = offset[0]-rOffset[0]+Position.deltaX;
			var iRight = iLeft+eRow.offsetWidth;
			var iBottom = iTop+eRow.offsetHeight;
			var iCenter = (iTop+iBottom)/2;
			if(eRow!=this.root){
				if(_pXY[0]>=iLeft&&_pXY[0]<=iRight){
					if(_pXY[1]>=iTop&&_pXY[1]<=iCenter){
						eRow.parentNode.insertBefore(this.shadow,eRow);
						break;
					}
					else if(_pXY[1]<=iBottom&&_pXY[1]>iCenter){
						eRow.parentNode.insertBefore(this.shadow,eRow.nextSibling);
						break;
					}
				}
			}
		}
	},
	_move : function(_eCurr,_iPosition,_eTarget){
		//_iPosition表示当前元素相对于目标元素的位置,1表示之前,0表示之后,2表示最后,-1表示删除
		//alert(_eCurr.innerHTML)
	},
	onDragEnd : function(nx,ny){
		Element.removeClassName(this.shadow,"tbody_dragging");
		$removeNode(this._handler);
		var needMove = false;
		if(this.lastNextSibling!=this.root.nextSibling){
			if(this.root.nextSibling==null){
				needMove = this._move(this.root,0,this.root.previousSibling);
			}
			else{
				needMove = this._move(this.root,1,this.root.nextSibling);
			}
		}
		if(needMove===false){
			this.root.parentNode.insertBefore(this.root,this.lastNextSibling);
		}
		this.lastNextSibling = null;
		this.rows = null;
		this._handler = null;
		this.shadow = null;
	}
});
