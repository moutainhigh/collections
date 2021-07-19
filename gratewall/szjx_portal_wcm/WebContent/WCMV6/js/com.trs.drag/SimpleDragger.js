$package("com.trs.drag");

com.trs.drag.SimpleDragger=Class.create('drag.SimpleDragger');
com.trs.drag._simpleDraggers = [];
com.trs.drag.SimpleDragger.prototype={
	initialize: function(o, oRoot){
		this.root = oRoot && oRoot != null ? oRoot : o ;
		this.handle = o;
		o.onmousedown	= this.start.bindAsEventListener(this);//function(ev){caller.start(ev);return false;};
		o.onmousemove	= null;
		com.trs.drag._simpleDraggers.push(this);
	},
	onDragStart : function(){
	},
	onDragEnd : function(){
	},
	onDrag : function(){
	},
	start:function(ev){
		ev = (window.event)?window.event:ev;
		if((ev.button||ev.which)!=1){
			Event.stop(ev);
			return false;
		}
		var o = this.handle;
		Position.absolutize(this.root);
		var y = parseInt(this.root.style.top);
		var x = parseInt(this.root.style.left);
		this.onDragStart(x, y);
		this.lastMouseX	= Event.pointerX(ev);
		this.lastMouseY	= Event.pointerY(ev);
		this.document_mousemove	= this.drag.bindAsEventListener(this);
		this.document_mouseup	= this.end.bindAsEventListener(this);
		Event.observe(o,'mousemove',this.document_mousemove);
		Event.observe(o,'mouseup',this.document_mouseup);
		o.setCapture();
		Event.stop(ev);
		return false;
	},
	drag:function(ev){
		ev=(window.event)?window.event:ev;
		if((ev.button||ev.which)!=1){
			return this.end(ev);
		}
		var ey	= Event.pointerY(ev);
		var ex	= Event.pointerX(ev);
		var y = parseInt(this.root.style.top);
		var x = parseInt(this.root.style.left);
		var nx, ny;
		nx = x + (ex - this.lastMouseX);
		ny = y + (ey - this.lastMouseY);
		this.root.style["left"] = nx + "px";
		this.root.style["top"] = ny + "px";
		this.lastMouseX	= ex;
		this.lastMouseY	= ey;
		this.onDrag(nx, ny, [ex,ey] ,ev);
		Event.stop(ev);
		return false;
	},
	end:function(ev){
		var o = this.handle;
		Event.stopObserving(o,'mousemove',this.document_mousemove);
		Event.stopObserving(o,'mouseup',this.document_mouseup);
		this.onDragEnd(parseInt(this.root.style["left"]),parseInt(this.root.style["top"]),ev);
		o.releaseCapture();
		Event.stop(ev);
		return false;
	},
	destroy:function(_bUnion){
//		this.handle.onmousedown	= null;
		if(this.handler){
			this.handler.onmousedown = this.handler.onmousemove = null;
			this.handler = null;
		}
		this.document_mousemove = this.document_mouseup = null;
		this.onDragStart = this.onDrag = this.onDragEnd = null;
		this.root = null;
		if(!_bUnion&&com.trs.drag._simpleDraggers)com.trs.drag._simpleDraggers.without(this);
		$destroy(this);
	}
}
Event.observe(window,'unload',function(){
	if(com.trs.drag._simpleDraggers){
		for(var i=0,n=com.trs.drag._simpleDraggers.length;i<n;i++){
			var oDragger = com.trs.drag._simpleDraggers[i];
			oDragger.destroy(true);
		}
	}
	com.trs.drag._simpleDraggers = null;
});