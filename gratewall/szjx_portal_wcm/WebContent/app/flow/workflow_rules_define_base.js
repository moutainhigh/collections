var iDrag = {
	dragged : null,
	detect:function(event){
		if(iDrag.dragged){
			return false;
		}
		var eRoot = this;
		var cnt = 0;
		var oDragger = eRoot.Dragger;
		if(oDragger.disabled || eRoot.getAttribute('draggable',2)==='false')return;
		iDrag.dragged = eRoot;
		oDragger.init = false;
		event = window.event || event;
		oDragger.lastMouseX = Event.pointerX(event);
		oDragger.lastMouseY = Event.pointerY(event);
		Event.observe(document,'mousemove',iDrag.drag);
		Event.observe(document,'mouseup',iDrag.end);
	},
	start : function(event){
		document.body.setCapture(false);
		var eRoot = iDrag.dragged;
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
		if(iDrag.dragged==null){
			return false;
		}
		var eRoot = iDrag.dragged;
		var oDragger = eRoot.Dragger;
		event = (oDragger.lastCaptureWindow||window).event || event;
		var ex = Event.pointerX(event);
		var ey = Event.pointerY(event);
		if (oDragger.init == false) {
			if(Math.abs(ex-oDragger.lastMouseX)>=2||Math.abs(ey-oDragger.lastMouseY)>=2){
				iDrag.start(event);
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
		document.body.releaseCapture();
		Event.stopObserving(document,'mousemove', iDrag.drag);
		Event.stopObserving(document,'mouseup', iDrag.end);
		if(iDrag.dragged==null){
			return false;
		}
		var eRoot = iDrag.dragged;
		var oDragger = eRoot.Dragger;
		iDrag.dragged = null;
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

var SimpleDragger = Class.create('wcm.SimpleDragger');
_Draggers = [];
SimpleDragger.prototype={
	initialize : function(_eRoot){
		_eRoot.Dragger = this;
		this.root = _eRoot;
		_eRoot.onmousedown = iDrag.detect;
		_Draggers.push(this);
	},
	destroy:function(_bUnion){
		if(this.root){
			this.root.Dragger = null;
			this.root.onmousedown = null;
			this.root = null;
		}
		if(!_bUnion&&_Draggers){
			_Draggers.without(this);
		}
	},
	delegate : function(_eRoot,event){
		if(iDrag.delegate){
			iDrag.delegate.call(this,_eRoot,event);
		}
		else{
			return _eRoot;
		}
	}
}

var TBodyDragger = Class.create('wcm.TBodyDragger');
TBodyDragger.prototype = Object.extend({},SimpleDragger.prototype);
Object.extend(TBodyDragger.prototype,{
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

Object.extend(Event, {
  pointerX: function(event) {
    return event.pageX || (event.clientX +
      (document.documentElement.scrollLeft || document.body.scrollLeft));
  },

  pointerY: function(event) {
    return event.pageY || (event.clientY +
      (document.documentElement.scrollTop || document.body.scrollTop));
  }
});
Object.extend(Position, {
	absolutize: function(element) {
		element = $(element);
		if (element.style.position == 'absolute') return;
		Position.prepare();

		var offsets = Position.positionedOffset(element);
		var top     = offsets[1];
		var left    = offsets[0];
		var width   = element.clientWidth;
		var height  = element.clientHeight;

		element._originalLeft   = left - parseFloat(element.style.left  || 0);
		element._originalTop    = top  - parseFloat(element.style.top || 0);
		element._originalWidth  = element.style.width;
		element._originalHeight = element.style.height;

		element.style.position = 'absolute';
		element.style.top    = top + 'px';;
		element.style.left   = left + 'px';;
		element.style.width  = width + 'px';;
		element.style.height = height + 'px';;
	}
});
function $removeNode(_node){
	if(_node){
		var childs = _node.childNodes;
		for(var i=childs.length-1;i>=0;i--){
			$removeNode(childs[i]);
		}
		childs = [];
		if(_node.parentNode){
			_node.parentNode.removeChild(_node);
		}
//		Event.stopAllObserving(_node);
		delete _node;
	}
}